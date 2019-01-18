(ns clojure-web-app.handler
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [hiccup.page :refer [doctype include-css]]
            [hiccup.core :refer [html]]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]))

(defn page [title & content]
  (str
    (html
      (doctype :html5)
      [:html
       [:head
        [:title title]
        (include-css "main.css")]
       [:body content]])))

(defn index []
  (page "Clojure-web-app"
   [:h1 "Clojure-web-app"]))

(defroutes app-routes
  (GET "/" [] (index))
  (route/not-found "Not Found"))

(def app
  (wrap-defaults app-routes site-defaults))
