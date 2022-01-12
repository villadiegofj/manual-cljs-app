(ns app.auth
  (:require [reagent.core :as r]
            [reitit.frontend.easy :as rfe]
            [app.api :refer (api-url)]
            [ajax.core :refer (POST GET PUT json-request-format json-response-format)]))

(defonce auth-state (r/atom nil))
(defonce error-state (r/atom nil))

(defn auth-success! [{{:keys [token] :as user} :user}]
  (.setItem js/localStorage "auth-token" token)
  (reset! auth-state user)
  (reset! error-state nil)
  (rfe/push-state :home))

(defn auth-error! [{{:keys [errors]} :response} ]
  (reset! error-state errors))


(defn register! [register-input]
  (POST (str api-url "/users")
    {:params {:user register-input}
     :format (json-request-format)
     :handler auth-success!
     :error-handler auth-error!
     :response-format (json-response-format {:keywords? true})}))

(defn login! [login-input]
  (POST (str api-url "/users/login")
    {:params {:user login-input}
     :format (json-request-format)
     :handler auth-success!
     :error-handler auth-error!
     :response-format (json-response-format {:keywords? true})}))

(defn logout! []
  (.removeItem js/localStorage "auth-token")
  (reset! auth-state nil)
  (rfe/push-state :login))

(defn get-auth-header []
  (let [token (.getItem js/localStorage "auth-token")]
    [:Authorization (str "Token " token)]))

(defn get-me-success! [{user :user}]
  (println user)
  (reset! auth-state user))

(defn get-me-error! [error]
  (js/console.log error)
  (rfe/push-state :login))

(defn me []
  (GET (str api-url "/user")
    {:handler get-me-success!
     :error-handler get-me-error! 
     :response-format (json-response-format {:keywords? true})
     :headers (get-auth-header)}))

(defn save-user! [user]
  (PUT (str api-url "/user")
    {:params {:user user}
     :format (json-request-format)
     :handler get-me-success!
     :error-handler get-me-error!
     :headers (get-auth-header)
     :response-format (json-response-format {:keywords? true})}))

(comment
  (register! {:username "villa.06@condor.com"
              :email "villa.06@condor.com"
              :password "12345"})

  (login! {:email "villa.05@condor.com"
           :password "12345"})
  

  (save-user! {:image "https://avatars.githubusercontent.com/u/1940195?s=40&v=4"})

  (:user (deref auth-state))
  (deref error-state)
  (.setItem js/localStorage "auth-token" "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJlbWFpbCI6InZpbGxhLjAyQGNvbmRvci5jb20iLCJ1c2VybmFtZSI6InZpbGxhLjAyQGNvbmRvci5jb20iLCJiaW8iOm51bGwsImltYWdlIjoiaHR0cHM6Ly9hcGkucmVhbHdvcmxkLmlvL2ltYWdlcy9zbWlsZXktY3lydXMuanBlZyIsImlhdCI6MTYzOTk2MTg3OSwiZXhwIjoxNjQ1MTQ1ODc5fQ.nHisPAUt7y3-hNH7LI5hEEFknPqez5SI3937iLFpLSw")
  (.getItem js/localStorage "auth-token")
  )
