(ns csv2edn.csv2edn
  (:require [clojure.data.csv :as csv]
            [clojure.java.io :as io]
            [clojure.pprint :refer [pprint]]
            [clojure.string :refer [lower-case]]))

(def ^:dynamic *options* {:separator \,})

(defn maybe-read
  "If a string represents an integer or real, we'd really like to have that
  integer or real in our data rather than a string representation of it."
  [^String s]
  (try
    (read-string s)
    (catch Exception _ s)))

(defn csv2edn
  "Read a CSV stream from the reader or filename `in`, and write corresponding
  EDN to the reader or filname `out`. Assume column headers are in row 1"
  [from to]
  (let [data (with-open [reader (if (string? from)(io/reader from) from)]
               (doall
                 (csv/read-csv
                   reader
                   :separator (:separator *options*))))
        headers (map #(keyword (lower-case %)) (first data))]
    (with-open [writer (if (string? to) (io/writer to) to)]
      (pprint
        (map
          #(zipmap headers (map maybe-read %))
          (rest data))
        writer))))
