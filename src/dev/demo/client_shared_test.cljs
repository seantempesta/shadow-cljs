(ns demo.client-shared-test
  (:require
    [cljs.test :refer (deftest is testing)]
    [shadow.cljs.devtools.client.shared :as client]))

(deftest reconnect-delay-remains-bounded-after-repeated-failures
  (testing "a long watcher outage never disables the next reconnect"
    (doseq [failed-attempts [0 1 3 4 1000]]
      (is (= 5000 (client/reconnect-delay-ms failed-attempts))))))
