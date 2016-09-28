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

(defn make-instance
  "Takes a dataset (`weka.core.Instances`), a map of attributes and a new
  data point to be converted to an instance. Returns a `weka.core.Instance`." 
  [dataset attrs item]
  (let [num-attrs (count attrs)
        instance  (DenseInstance. num-attrs)]
    (.setDataset instance dataset)
    ;; @TODO: core.reducers here?
    (doseq [[k v] item]
      (let [attr (get attrs k)
            v    (if (number? v) (double v) v)]
        ;; @TODO: what if this isn't a dataset viable attribute
        (when (some? attr)
          (if (some? v)
            (.setValue instance attr v)
            (.setMissing instance attr))))) ;; set the as missing when value is null
    instance))

(defn- ->instances [ds-name attrs capacity]
  (fn
    ([] (Instances. ds-name
                    (java.util.ArrayList. (vals attrs))
                    capacity))
    ([dataset item] ()
      (let [instance (make-instance dataset attrs item)]
        (.add dataset instance)
        dataset))))

;; @TODO: implement fold here
(defn attributize
  "Takes a sequence of maps and returns:
  `clojure.lang.PresistentList<weka.core.Attribute>`"
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
