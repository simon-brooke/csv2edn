(defproject csv2edn "0.1.5-SNAPSHOT"
  :description "Simple command line utility to convert CSV files to EDN."
  :url "https://github.com/simon-brooke/csv2edn"
  :license {:name "GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.gnu.org/licenses/old-licenses/gpl-2.0.en.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [org.clojure/data.csv "0.1.4"]
                 [org.clojure/data.json "0.2.7"]
                 [org.clojure/tools.cli "0.4.2"]]
  :main ^:skip-aot csv2edn.core
  :target-path "target/%s"
  :plugins [[lein-codox "0.10.7"]
            [lein-kibit "0.1.6"]
            [lein-release "1.0.5"]]
  :profiles {:uberjar {:aot :all}}

  :deploy-repositories [["releases" :clojars]
                        ["snapshots" :clojars]]

  ;; `lein release` doesn't play nice with `git flow release`. Run `lein release` in the
  ;; `develop` branch, then reset the `master` branch to the release tag.

  :release-tasks [["vcs" "assert-committed"]
                  ["clean"]
                  ["codox"]
                  ["change" "version" "leiningen.release/bump-version" "release"]
                  ["vcs" "commit"]
                  ["uberjar"]
                  ["deploy" "clojars"]
                  ["change" "version" "leiningen.release/bump-version"]
                  ["vcs" "commit"]])
