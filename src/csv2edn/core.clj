(ns ^{:doc "Conversion from CVS to EDN: standalone command line interface."
      :author "Simon Brooke"}
  csv2edn.core
  (:require [clojure.java.io :as io]
            [clojure.tools.cli :refer [parse-opts]]
            [csv2edn.csv2edn :refer [csv->edn csv->json *options*]])
  (:gen-class))


(def cli-options
  "Command line argument specification."
  [["-f" "--format FORMAT" "Format the ouput as `FORMAT`
    (expected to be either `edn` or `json`, defaults to `edn`)"
    :default :edn
    :parse-fn #(keyword %)
    :validate [#(#{:edn :json} %) "Format should be one of `edn`, `json`."]]
   ["-h" "--help" "Print this message and exit."]
   ["-i" "--input INPUT" "The path name of the CSV file to be read.
    If no input file is specified, input will be read from standard input."
    :validate [#(and
                  (.exists (io/file %))
                  (.canRead (io/file %)))
               "Could not find input file"]]
   ["-o" "--output OUTPUT" "The path name of the EDN file to be written.
    If no output file is specified, output will be written to standard output."]
   ["-s" "--separator SEPARATOR"
    "The character to treat as a separator in
    the CSV file"
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
        (let [from (or (:input *options*) (io/reader *in*))
              to (or (:output *options*) (io/writer *out*))]
          (case (:format *options*)
            :json (doall (csv->json from to))
            :edn (doall (csv->edn from to) )))))))

