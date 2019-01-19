(ns clojure-web-app.core       
  (:require [ring.adapter.jetty :as jetty]
            [clojure-web-app.handler :refer [app]]))

(defn -main []
  (jetty/run-jetty app {:port 3000}))
