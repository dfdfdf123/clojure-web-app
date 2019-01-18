(defproject clojure-web-app "0.1.0-SNAPSHOT"
  :description "clojure web application"
  :url "https://github.com/johnromanoff/clojure-web-app"
  :min-lein-version "2.0.0"
  :dependencies [[org.clojure/clojure "1.9.0"]
                 [compojure "1.6.1"]
                 [ring/ring-defaults "0.3.2"]]
  :plugins [[lein-ring "0.12.4"]]
  :ring {:handler clojure-web-app.handler/app}
  :profiles
  {:dev {:dependencies [[javax.servlet/servlet-api "2.5"]
                        [ring/ring-mock "0.3.2"]]}})
