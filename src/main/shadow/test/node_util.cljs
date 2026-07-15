(ns shadow.test.node-util)

(defn- test-var-symbol [the-var]
  (let [{:keys [name ns]} (meta the-var)]
    (symbol (str ns) (str name))))

(defn find-matching-test-vars [test-vars test-syms]
  ;; FIXME: should have some kind of wildcard support
  (let [test-namespaces
        (->> test-syms (filter simple-symbol?) (set))
        test-var-syms
        (->> test-syms (filter qualified-symbol?) (set))]

    (filter (fn [the-var]
              (let [{:keys [ns]} (meta the-var)]
                (or (contains? test-namespaces ns)
                    (contains? test-var-syms
                      (test-var-symbol the-var)))))
      test-vars)))

(defn exact-selection-counts [test-vars test-syms]
  (let [requested (->> test-syms (filter qualified-symbol?) set)
        matched (->> test-vars
                     (map test-var-symbol)
                     (filter requested)
                     set)]
    {:requested-exact (count requested)
     :matched-exact (count matched)
     :all-exact-matched? (= requested matched)}))
