(defproject csv2edn "0.1.0-SNAPSHOT"
  :description "Simple command line utility to convert CSV files to EDN"
  :url "http://example.com/FIXME"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [org.clojure/data.csv "0.1.4"]
                 [org.clojure/tools.cli "0.4.2"]]
  :main ^:skip-aot csv2edn.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
