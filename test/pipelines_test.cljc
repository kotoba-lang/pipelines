(ns pipelines-test
  (:require [clojure.test :refer [deftest is testing]]
            [pipelines]))
(deftest namespace-loads
  (testing "the restored CLJC namespace loads"
    (is (some? pipelines))))
