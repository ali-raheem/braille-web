(ns braille-web.handler
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]
            [clojure.string :as str]
            [hiccup.page :refer [html5 include-css include-js]]))

(def braille-mod-map
  {58 :number, 40 :letter, 32 :caps})

(def braille-num-map
  {1 "1", 5 "2", 3 "3", 11 "4", 9 "5",
   7 "6", 15 "7", 13 "8", 6 "9", 14 "0"})

(def braille-map
  {0 " ", 1 "a", 5 "b", 3 "c", 11 "d", 9 "e", 7 "f", 15 "g", 13 "h", 6 "i",
   14 "j", 17 "k", 21 "l", 19 "m", 27 "n", 25 "o", 23 "p", 31 "q", 29 "r",
   22 "s", 30 "t", 49 "u", 53 "v", 46 "w", 51 "x", 59 "y", 57 "z",
   55 "and", 63 "for", 61 "of", 54 "the", 62 "with",
   33 "ch", 37 "gh", 35 "sh", 43 "th"
   41 "wh", 39 "ed", 47 "er", 45 "ou", 38 "ow", 24 "in"
   4 ",", 28 "!", 44 "."})

(defn post-page [& body]
  (html5
   [:head
    [:title "Braille Translator"]
    (include-css "/css/braille.css")
    (include-js "/js/check.js")]
   [:body
    [:div {:class "title-bar"}
     "Braille Translator"]
    [:div {:class "page"}
     [:div {:class "braille-tile"}
      [:input {:type "checkbox" :id "cb1"}] 
      [:input {:type "checkbox" :id "cb2"}] [:br]
      [:input {:type "checkbox" :id "cb4"}] 
      [:input {:type "checkbox" :id "cb8"}] [:br]
      [:input {:type "checkbox" :id "cb16"}]
      [:input {:type "checkbox" :id "cb32"}]]
     [:form {:action "/decode" :method "post" :id "braille-form"}
      [:input#braille-input {:type "hidden" :name "text" :value body}]
      [:input#braille-input {:type "hidden" :name "data" :id "braille-data" :value "0"}]]
     [:button.button.add {:type "submit" :onclick "decodeBraille()"} "Add letter"]
     [:div {:class "text-output"}
      [:textarea
       body]]]
    [:div {:class "footer"} "2017 -- Dr. Ali Raheem"]] ))

(defn get-page [& request]
  (post-page ""))

(def get-in-bmap
  #(get braille-map % "???"))

(def str->bin
  #(Integer/parseInt (str/trim %) 2))

(def braille->ascii (comp get-in-bmap str->bin #(apply str %) reverse))

(defn braille-decode [request]
  (let [data (get-in request [:params :data])]
    (post-page (apply str (map get-in-bmap (map #(Integer. %) (str/split data #",")))))))

(defroutes app-routes
  (GET "/" [] get-page)
  (POST "/decode" [] braille-decode)
  (route/not-found "404: Page not found."))


(def app
  (wrap-defaults app-routes (assoc-in site-defaults [:security :anti-forgery] false)))
