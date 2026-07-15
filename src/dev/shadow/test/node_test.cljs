(ns shadow.test.node-test
  (:require
    [cljs.test :refer (deftest is)]
    [shadow.test.node-util :as node-util]))

(defn- test-var [n]
  (with-meta (fn [])
    {:ns 'shadow.test.node-test
     :name (symbol (str "selected-" n))}))

(deftest exact-var-selection-survives-hash-set-promotion
  (let [test-vars (mapv test-var (range 10))
        selectors (mapv #(symbol (str "shadow.test.node-test/selected-" %))
                        (range 10))]
    (is (= test-vars
           (vec (node-util/find-matching-test-vars test-vars selectors))))))
