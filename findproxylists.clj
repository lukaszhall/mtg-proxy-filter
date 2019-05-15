#!/usr/bin/env boot

(set-env! :dependencies '[[com.taoensso/timbre "4.10.0"]])

(require '[boot.cli :refer [defclifn]])
(require '[com.taoensso/timber :refer [spy]])

(def PROXY-LIST_DIR "wsglists")
(def NEEDED-CARDS-FILE "needed-cards")

(defn read-wsg-lists []
  (let [dir    (clojure.java.io/file PROXY-LIST_DIR)
        files  (map #(str %)
                    (.list dir))
        filenames  (map #(str PROXY-LIST_DIR "/" %) 
                        (.list dir))
        ;;_ (clojure.pprint/pprint (type files))
        ;;_ (clojure.pprint/pprint (type filenames))
        ]
    (into {}
          (map #(with-open [rdr  (clojure.java.io/reader (str PROXY-LIST_DIR "/" %))]
                  {% (into [] (line-seq rdr))})
               files))
    ))

(defn read-req-cards []
  (let [cards   (->> (with-open [rdr (clojure.java.io/reader NEEDED-CARDS-FILE)]
                       (into [] (line-seq rdr)))
                     (filter #(not (= "" %))))
        ;;_ (clojure.pprint/pprint cards)
        ]
   cards
   ))


(defn find-lists-with-card
  [lists-map card]
(let [;;_ (clojure.pprint/pprint "lists-map:")
      ;;_ (clojure.pprint/pprint lists-map)
      _ (clojure.pprint/pprint "card:")
      _ (clojure.pprint/pprint card)
      _ (clojure.pprint/pprint (type lists-map))
      _ (clojure.pprint/pprint (keys lists-map))
      

      filtered (filter (comp (timbre/spy #(.contains % card)) last) lists-map)
      filteredtest (.contains (get lists-map "wsglists/Master") card)
      _ (clojure.pprint/pprint "Filtered:")
      _ (clojure.pprint/pprint filteredtest)
      ;;_ (clojure.pprint/pprint filtered)
      filtered-names (map first filtered)
      _ (clojure.pprint/pprint filtered-names)
      ]
  (filter (comp #(.contains % card) last) lists-map)  
  ))





(defn -main [& args]
  (let [wsg-list-map (read-wsg-lists)
        ;;_ (clojure.pprint/pprint wsg-list-map)
        req-cards    (read-req-cards)
        a-card       (first req-cards)

        _ (clojure.pprint/pprint a-card)
        _ (clojure.pprint/pprint (find-lists-with-card wsg-list-map a-card))
        ;;list-with-card (filter #())
        
        ;;_ (clojure.pprint/pprint wsg-list-map)
        ;;_ (clojure.pprint/pprint (vals wsg-list-map))
        ]
    #_(clojure.pprint/pprint (find-lists-with-card wsg-list-map a-card))
    )
)
