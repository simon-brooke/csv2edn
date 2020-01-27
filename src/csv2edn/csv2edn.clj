(ns ^{:doc "Conversion from CVS to EDN."
      :author "Simon Brooke"} csv2edn.csv2edn
  (:require [clojure.data.csv :as csv]
            [clojure.data.json :as json]
            [clojure.java.io :as io]
            [clojure.pprint :refer [pprint]]
            [clojure.string :as s]))

(def ^:dynamic *options*
  "Defaults for options used in conversion (essentially, `:separator` is `,`,
  much as you'd expect."
  {:separator \,})

(defn maybe-read
  "If a string represents an integer or real, we'd really like to have that
  integer or real in our data rather than a string representation of it."
  [^String s]
  (try
    (read-string s)
    (catch Exception _ s)))

(defn csv->edn
  "Read a CSV stream from the reader or filename `from`, and write corresponding
  EDN to the reader or filname `to`, using the separator character `sep`. Assume
  column headers are in row 1. If `from` is `nil` or not supplied, do not write.
  It `sep` is not supplied, take the `:separator` value from `*options*`. Returns
  a (possibly-lazy) sequence of maps."
  ([from] (csv->edn from nil))
  ([from to] (csv->edn from to (:separator *options*)))
  ([from to sep]
   (let [data (with-open [reader (if (string? from)(io/reader from) from)]
                (doall
                  (csv/read-csv
                    reader
                    :separator (first (str sep)))))
         headers (map #(keyword
                         (s/replace (s/lower-case %) #"[^a-z0-9]" "-"))
                      (first data))
         result (map
                  #(zipmap headers (map maybe-read %))
                  (rest data))]
     (if to
       (with-open [writer (if (string? to) (io/writer to) to)]
         (pprint
           result
           writer)))
     result)))

(defn csv->json
  "Read a CSV stream from the reader or filename `from`, and write corresponding
  JSON to the reader or filname `to`, using the separator character `sep`. Assume
  column headers are in row 1. If `from` is `nil` or not supplied, do not write.
  It `sep` is not supplied, take the `:separator` value from `*options*`. Returns
  a (possibly-lazy) sequence of maps."
  ([from] (csv->json from nil))
  ([from to] (csv->json from to (:separator *options*)))
  ([from to sep]
   (let [result (json/write-str (csv->edn from nil sep))]
     (if to
       (spit to result))
     result)))
