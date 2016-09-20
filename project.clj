(defproject weka-clj "0.1.0"
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [nz.ac.waikato.cms.weka/weka-dev "3.9.0"]]
  :plugins [[lein-auto "0.1.2"]]
  :source-paths ["src"]
  :test-paths ["test"]
  :main weka-clj.core)
