(ns runner
  (:require [clojure.test :refer :all]
            [clojure.core.reducers :as r]
            [weka-clj.core :refer [maps->instances reduce-attributes]])
  (:import [weka.core Instance Attribute]))

; (deftest test-list
;   (let [instances (maps->instances [{:foo "bar" :baz 1} {:foo "boo" :baz 2}])]
;     (is (instance? Instance (first instances)))))

(deftest test-attribute-creation
  (let [attrs (reduce-attributes [{:foo "bar" :baz 1} {:foo "boo" :baz 2}])]
    (is (= (count (keys attrs)) 2))
    (is (instance? Attribute (:foo attrs)))
    (is (= true (.isNumeric (:baz attrs))))
    (is (= true (.isNominal (:foo attrs))))))
