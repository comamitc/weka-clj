(ns runner
  (:require [clojure.test :refer :all]
            [weka-clj.feature.extraction :refer [maps->instances attributize]])
  (:import [weka.core Instance Instances Attribute]))

(defn- test-instances [instances]
  (is (instance? Instances instances))
  (is (every? #(instance? Instance %) instances))
  (is (every? #(= 2 (.numValues %)) instances)))

(let [attributes (attributize [{:foo "bar" :baz 1} {:foo "boo" :baz 2}])
      instances  (:instances (maps->instances [{:foo "bar" :baz 1} {:foo "boo" :baz 2}]))
      dup-values (:instances (maps->instances [{:foo "bar" :baz 1}
                                               {:foo "bob" :baz 2}
                                               {:foo "bob" :baz 3}]))
      nil-values (:instances (maps->instances [{:foo nil :baz 1} {:foo "boo" :baz nil}]))]

  (deftest ?attributize

    (testing "happy path"
      (is (= (count (keys attributes)) 2))
      (is (instance? Attribute (:foo attributes)))
      (is (= true (.isNominal (:foo attributes))))))

  (deftest ?maps->instances

    (testing "happy path" (test-instances instances))

    (testing "duplicate values" (test-instances dup-values))

    (testing "nil-values" (test-instances nil-values))))
