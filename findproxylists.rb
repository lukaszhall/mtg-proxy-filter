#!/usr/bin/env ruby

require 'pp'

include_master = false

def read_wsg_lists()
    lists = {}
    Dir.foreach('wsglists') do |file_list|
        next if file_list == '.' or file_list == '..'
        list_cards = File.readlines("wsglists/" + file_list)
        lists[file_list] = list_cards.each {|line| line.strip!}
    end
    lists
end

def read_req_cards()
    needed_cards = []
    File.readlines("needed-cards").each do |line|
        line.strip!
        next if line == '' or line.include? "#"
        needed_cards << line
    end
    needed_cards
end

def find_list_with_card(list_map, card)
    matching_lists = []
    list_map.each {|list_name, card_list|
        matching_lists << list_name if card_list.include? card
    }
    matching_lists
end

def find_lists_foreach_card(list_map, cards) 
    card_list_map = {}
    cards.each {|card|
        lists = find_list_with_card(list_map, card)
        card_list_map[card] = lists
    }
    card_list_map
end

def print_list(cards_list_map)
    cards_list_map.each {|key, value|
        print '%-30s' % key
        value.each {|list|
            print '%-25s' % list
        }
        puts ""
    }
   puts  
end


req_cards = read_req_cards
wsg_lists = read_wsg_lists
wsg_lists.delete("Master") if !include_master

card_lists = find_lists_foreach_card(wsg_lists, req_cards)
print_list(card_lists)