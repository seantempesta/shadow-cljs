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
           (vec (node-util/find-matching-test-vars test-vars selectors))))
    (is (= {:requested-exact 10
            :matched-exact 10
            :all-exact-matched? true}
           (node-util/exact-selection-counts test-vars selectors)))
    (is (= {:requested-exact 11
            :matched-exact 10
            :all-exact-matched? false}
           (node-util/exact-selection-counts
             test-vars
             (conj selectors 'shadow.test.node-test/not-registered))))))
