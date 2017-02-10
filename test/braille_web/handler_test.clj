(ns braille-web.handler-test
  (:require [clojure.test :refer :all]
            [ring.mock.request :as mock]
            [braille-web.handler :refer :all]))

(deftest braille-web-app-test
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

(deftest get-in-bmap-test
  (testing "Testing get-in-bmap."
    (is (= (get-in-bmap 30) "t"))
    (is (= (get-in-bmap 10) "???")))
  (testing "Testing str->bin."
    (is (= (str->bin "1101") 13)))
  (testing "Functional test str to braille"
    (is (= (apply str (map braille->ascii (partition 6 "101100011000001000000000110000101010100000011000101110100100001110"))) "hi, claire!"))))
