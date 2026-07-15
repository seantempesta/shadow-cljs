(ns shadow.test.node-util)

(defn find-matching-test-vars [test-vars test-syms]
  ;; FIXME: should have some kind of wildcard support
  (let [test-namespaces
        (->> test-syms (filter simple-symbol?) (set))
        test-var-syms
        (->> test-syms (filter qualified-symbol?) (set))]

    (->> test-vars
         (filter (fn [the-var]
                   (let [{:keys [name ns]} (meta the-var)]
                     (or (contains? test-namespaces ns)
                         (contains? test-var-syms
                           (symbol (str ns) (str name)))))))
         )))
