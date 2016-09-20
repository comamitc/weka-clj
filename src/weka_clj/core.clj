(ns weka-clj.core
  (:require [clojure.core.reducers :as r])
  (:import [weka.core Attribute]))

(defn- ->attrs [acc k v]
  (let [attr (if (every? number? v)
               (Attribute. (name k))
               (Attribute. (name k) v))]
    (assoc acc k attr)))

(defn- collect-raw [acc k v]
  (update-in acc [k] conj v))

(defn- ->raw [acc curr]
  (r/reduce collect-raw acc curr))

(defn reduce-attributes [attrs]
  (let [comp-fn (comp (partial r/reduce ->attrs {})
                      (partial r/reduce ->raw {}))]
    (comp-fn attrs)))

(defn maps->instances [list]
  (let [attrs (reduce-attributes list)]
    nil))
