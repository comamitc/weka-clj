(ns weka-clj.feature.extraction
  (:require [clojure.core.reducers :as r])
  (:import [weka.core Attribute DenseInstance Instances]))

(defn- ->attrs
  ([] {})
  ([xs k v]
   (let [v    (filter some? (distinct v))
         attr (if (every? number? v)
                (Attribute. (name k))
                (Attribute. (name k) v))]
     (assoc xs k attr))))

(defn- collect-raw [acc k v]
  (update-in acc [k] conj v))

(defn- ->raw
  ([] {})
  ([xs x] (r/reduce collect-raw xs x)))

(defn- ->instances [attrs capacity]
(defn- ->instances [ds-name attrs capacity]
  (fn
    ([] (Instances. ds-name
                    (java.util.ArrayList. (vals attrs))
                    capacity))
    ([xs x]
     (let [num-attrs (count (keys attrs))
           instance  (DenseInstance. num-attrs)]
       (.setDataset instance xs)
       (loop [kws (keys x)
              cnt 0]
         (let [k (first kws)]
           (if (nil? k)
             ;; then
             (do
               (.add xs instance)
               xs)
             ;; else
             (let [a (get x k)
                   v (if (number? a) (double a) a)]
               (if (some? v)
                (.setValue instance cnt v)
                ;; set the value as missing when value is null
                (.setMissing instance cnt))
               (recur (rest kws) (+ 1 cnt))))))))))

;; @TODO: implement fold here
(defn attributize
  "Takes a sequence of maps and returns a `list` of `weka.core.Attributes`."
  [data]
  (let [comp-fn (comp (partial r/reduce ->attrs {})
                      (partial r/reduce ->raw {}))]
   (comp-fn data)))

(defn maps->instances
  "Takes a sequence of maps and a dataset relation name.

  Returns a map of shape:

  `{:attributes clojure.lang.PresistentList<weka.core.Attribute>
   :instances weka.core.Instances}`"
  ([data] (maps->instances data "default"))
  ([data ds-name]
   (let [attrs (attributize data)]
     {:attributes attrs
      :instances  (r/fold (->instances ds-name attrs (count data)) data)})))
