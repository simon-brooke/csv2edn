(ns ^{:doc "Conversion from CVS to EDN: standalone command line interface."
      :author "Simon Brooke"}
  csv2edn.core
  (:require [clojure.java.io :as io]
            [clojure.tools.cli :refer [parse-opts]]
            [csv2edn.csv2edn :refer [csv->edn *options*]])
  (:gen-class))


(def cli-options
  "Command line argument specification."
  [["-h" "--help" "Print this message and exit."]
   ["-i" "--input INPUT" "The path name of the CSV file to be read.
    If no input file is specified, input will be read from standard input."
    :validate [#(and
                  (.exists (io/file %))
                  (.canRead (io/file %)))
               "Could not find input file"]]
   ["-o" "--output OUTPUT" "The path name of the EDN file to be written.
    If no output file is specified, output will be written to standard output."]
   ["-s" "--separator SEPARATOR"
    "The character to treat as a separator in the CSV file"
    :parse-fn #(first (str %))
    :default \,]
   ])


(defn -main
  "Parse command line arguments if any, run the conversion, and exit."
  [& opts]
  (let [args (parse-opts opts cli-options)]
    (println
      (if
        (:help (:options args))
        (:summary args) "")
      (if (:errors args)
        (apply str (interpose "; " (:errors args))) ""))
    (if-not (or (:errors args) (:help (:options args)))
      (binding [*options* (:options args)]
        (doall
          (csv->edn
            (or
              (:input *options*)
              (io/reader *in*))
            (or (:output *options*) (io/writer *out*)))
          )))))

