(defproject weka-clj "0.1.8"
  :description "Tools for weka written in Clojure"
  :license {:name "MIT License"
            :url "http://www.opensource.org/licenses/mit-license.php"
            :distribution :repo}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [nz.ac.waikato.cms.weka/weka-dev "3.9.0"]]
  :min-lein-version "2.0.0"
  :plugins [[lein-auto "0.1.2"]
            [lein-codox "0.10.0"]]
  :codox {:source-paths ["src/"]
          :output-path "docs/"
          :source-uri "https://github.com/comamitc/weka-clj/tree/v{version}/{filepath}#L{line}"
          :metadata {:doc/format :markdown}}
  :url "https://comamitc.github.io/weka-clj/"
  :source-paths ["src"]
  :test-paths ["test"])
