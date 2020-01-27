(defproject csv2edn "0.1.0"
  :description "Simple command line utility to convert CSV files to EDN"
  :url "http://example.com/FIXME"
  :license {:name "GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.gnu.org/licenses/old-licenses/gpl-2.0.en.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [org.clojure/data.csv "0.1.4"]
                 [org.clojure/tools.cli "0.4.2"]]
  :main ^:skip-aot csv2edn.core
  :target-path "target/%s"
  :plugins [[lein-codox "0.10.7"]
            [lein-kibit "0.1.6"]
            [lein-release "1.0.5"]]
  :profiles {:uberjar {:aot :all}}

  ;; `lein release` doesn't play nice with `git flow release`. Run `lein release` in the
  ;; `develop` branch, then reset the `master` branch to the release tag.

  :release-tasks [["vcs" "assert-committed"]
                  ["change" "version" "leiningen.release/bump-version" "release"]
                  ["vcs" "commit"]
                  ["clean"]
                  ["uberjar"]
                  ["change" "version" "leiningen.release/bump-version"]
                  ["vcs" "commit"]])
