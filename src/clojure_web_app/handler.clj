(ns clojure-web-app.handler
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [compojure.coercions :refer [as-int]]
            [hiccup.page :refer [doctype include-css]]
            [hiccup.core :refer [html]]
            [hiccup.form :as f]
            [hiccup.element :refer [link-to]]
            [clojure-web-app.domain :refer [all-todos todo-by-id add-todo! remove-todo!]]
            [ring.middleware.anti-forgery :refer [*anti-forgery-token*]]
            [ring.util.response :refer [redirect]]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]))

(defn page [title & content]
  (str
    (html
      (doctype :html5)
      [:html
       [:head
        [:title title]
        (include-css "main.css")]
       [:body [:h1 title] content]])))

(defn anti-forgery-field []
  (f/hidden-field "__anti-forgery-token" *anti-forgery-token*))

(defn index []
  (page "Clojure-web-app"
   [:ul
    (for [todo (all-todos)]
      [:li (link-to (str "/" (:id todo)) (:text todo))])]
   (f/form-to [:post "/"]
     (anti-forgery-field)
     (f/text-field "todo")
     (f/submit-button "Add"))))

(defn add-todo [todo]
  (when (not (clojure.string/blank? todo))
    (add-todo! todo))
  (redirect "/"))

(defn show-todo [id]
  (let [todo (todo-by-id id)]
    (when todo
      (page (str "TODO " id)
        [:h2 (:text todo)]
        (f/form-to [:delete (str "/" id)]
          (anti-forgery-field)
          (f/submit-button "Delete"))))))

(defn delete-todo [id]
  (remove-todo! id)
  (redirect "/"))

(defroutes app-routes
  (GET "/" [] (index))
  (POST "/" [todo] (add-todo todo))
  (GET "/:id" [id :<< as-int] (show-todo id))
  (DELETE "/:id" [id :<< as-int] (delete-todo id))
  (route/not-found "Not Found"))

(def app
  (wrap-defaults app-routes site-defaults))
