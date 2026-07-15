(ns build
  (:require [clojure.tools.build.api :as b]))

(def class-dir "target/classes")
(def basis (b/create-basis {:project "deps.edn"}))

(defn compile-java
  "Compile Shadow's Java implementation for git and local consumers."
  [_]
  (b/javac {:src-dirs ["src/main"]
            :class-dir class-dir
            :basis basis
            :javac-opts ["--release" "21"]}))
