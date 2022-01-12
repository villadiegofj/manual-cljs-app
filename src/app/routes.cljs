(ns app.routes
  (:require [reagent.core :as r]
            [reitit.frontend :as rf]
            [reitit.frontend.easy :as rfe]
            [reitit.frontend.controllers :as rfc]
            [reitit.coercion.spec :as rss]
            [app.auth :as auth :refer (error-state)]
            [app.pages.home :refer (home-page)]
            [app.pages.login :refer (login-page)]
            [app.pages.register :refer (register-page)]
            [app.pages.settings :refer (settings-page)]
            [app.pages.profile :refer (profile-page)]))

(defonce routes-state (r/atom nil))

(def routes
  [
   ["/" {:name :home
         :view home-page
         :controllers [{:start #(js/console.log "Enter home-page...")
                        :stop #(js/console.log "Exit home-page...")}]}]
   ["/login" {:name :login
              :view login-page
              :controllers [{:stop (fn []
                                     (reset! error-state nil))}]}]
   ["/register" {:name :register
                 :view register-page
                 :controllers [{:stop (fn []
                                        (reset! error-state nil))}]}]
   ["/settings" {:name :settings
                 :view settings-page}]
   
   ["/user/@:username" {:name :profile
                        :view #'profile-page
                        :parameters {:path {:username string?}}
                        :controllers [{:params (fn [match]
                                                 (println "match:" match)
                                                 (:path (:parameters match)))
                                       :start (fn [{:keys [username] :as props}]
                                                (println "props:" username " -props:" props))}]}]])


(def main-router 
  (rf/router routes {:data {:coercion rss/coercion
                            :controllers [{:start (fn [] 
                                                    (js/console.log "Enter root...")
                                                    (auth/me))
                                           :stop #(js/console.log "Exit root...")}]}}))

(def on-navigate 
  (fn [new-match]
    (swap! routes-state 
           (fn [old-match]
             (when new-match
               (assoc new-match
                      :controllers (rfc/apply-controllers (:controllers old-match) new-match)))))))

;; reitit docs
;; (start! router on-navigate opts)

(defn router-start! []
  (rfe/start! main-router on-navigate
   {:use-fragment false})) ;; use true for hashed routes


(comment
  (rfe/push-state :home))
  