(ns braille-web.handler-test
  (:require [clojure.test :refer :all]
            [ring.mock.request :as mock]
            [braille-web.handler :refer :all]))

(deftest test-app
  (testing "main route"
    (let [response (app (mock/request :get "/"))]
      (is (= (:status response) 200))))
  (testing "decode"
    (let [response (app (mock/request :post "/decode" {"data" "1"}))]
      (is (= (:status response) 200))
      (is (= (clojure.string/includes?  (:body response) "<textarea>a</")))
      (is (= (clojure.string/includes?  (:body response) "Ali Raheem")))))
  (testing "not-found route"
    (let [response (app (mock/request :get "/invalid"))]
      (is (= (:status response) 404)))))
