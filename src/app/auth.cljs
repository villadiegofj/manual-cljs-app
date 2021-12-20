(ns app.auth
  (:require [reagent.core :as r]
            [app.api :refer (api-url error-handler)]
            [ajax.core :refer (POST json-request-format json-response-format)]))

(defonce auth-state (r/atom nil))

(defn auth-success! [{{:keys [token] :as user} :user}]
  (.setItem js/localStorage "auth-token" token)
  (reset! auth-state user))

(defn register! [register-input]
  (POST (str api-url "/users") {:params {:user register-input}
                                :format (json-request-format)
                                :handler auth-success!
                                :error-handler error-handler
                                :response-format (json-response-format {:keywords? true})}))

(defn login! [login-input]
  (POST (str api-url "/users/login") {:params {:user login-input}
                                :format (json-request-format)
                                :handler auth-success!
                                :error-handler error-handler
                                :response-format (json-response-format {:keywords? true})}))


(comment
  (register! {:username "villa.04@condor.com"
              :email "villa.04@condor.com"
              :password "12345"})

  (login! {:email "villa.05@condor.com"
           :password "12345"})

  (:user (deref auth-state))
  (.setItem js/localStorage "auth-token" "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJlbWFpbCI6InZpbGxhLjAyQGNvbmRvci5jb20iLCJ1c2VybmFtZSI6InZpbGxhLjAyQGNvbmRvci5jb20iLCJiaW8iOm51bGwsImltYWdlIjoiaHR0cHM6Ly9hcGkucmVhbHdvcmxkLmlvL2ltYWdlcy9zbWlsZXktY3lydXMuanBlZyIsImlhdCI6MTYzOTk2MTg3OSwiZXhwIjoxNjQ1MTQ1ODc5fQ.nHisPAUt7y3-hNH7LI5hEEFknPqez5SI3937iLFpLSw")
  (.getItem js/localStorage "auth-token")
  )
