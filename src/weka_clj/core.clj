(ns weka-clj.core
  (:require [clojure.core.reducers :as r])
  (:import [weka.core Attribute DenseInstance Instances]))

(defn- ->attrs
  ([] {})
  ([xs k v]
   (let [attr (if (every? number? v)
                (Attribute. (name k))
                (Attribute. (name k) v))]
     (assoc xs k attr))))

(defn- collect-raw [acc k v]
  (update-in acc [k] conj v))

;; @TODO: implement fold here
(defn- ->raw
  ([] {})
  ([xs x]
   (r/reduce collect-raw xs x)))

(defn reduce-attributes [data]
  (let [comp-fn (comp (partial r/fold ->attrs)
                      (partial r/fold ->raw))]
    (comp-fn data)))

(defn- ->instances [attrs capacity]
    (let [dataset (Instances. "users"
                              (java.util.ArrayList. (vals attrs))
                              capacity)]
      (fn
        ([] (list))
        ([xs x]
         (let [num-attrs (count (keys attrs))
               instance  (DenseInstance. num-attrs)]
           (.setDataset instance dataset)
           (loop [kws (keys x)
                  cnt 0]
             (let [k (first kws)]
               (if (nil? k)
                 ;; @TODO: should this return the Instances DataSet?
                 (conj xs instance)
                 ;; else
                 (let [a (get x k)
                       v (if (number? a) (double a) a)]
                   (.setValue instance cnt v)
                   (recur (rest kws) (+ 1 cnt)))))))))))

(defn maps->instances [list]
  (let [attrs (reduce-attributes list)]
    (r/fold (->instances attrs (count list)) list)))
