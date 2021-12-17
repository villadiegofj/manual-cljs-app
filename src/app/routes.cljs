(ns app.routes
  (:require [reitit.frontend :as rf]
            [reagent.core :as r]
            [reitit.frontend.easy :as rfe]
            [reitit.coercion.spec :as rss]
            [app.pages.home :refer (home-page)]
            [app.pages.login :refer (login-page)]
            [app.pages.register :refer (register-page)]
            [app.pages.settings :refer (settings-page)]))

(defonce routes-state (r/atom nil))

(def routes
  [
   ["/" {:name :home
         :view home-page}]
   ["/login" {:name :login
              :view login-page}]
   ["/register" {:name :register
                 :view register-page}]
   ["/settings" {:name :settings
                 :view settings-page}]])

(defn router-start! []
  (rfe/start!
   (rf/router routes {:data {:coercion rss/coercion}})
   (fn [matched-route] (reset! routes-state matched-route))
   {:use-fragment false})) ;; use true for hashed routes
