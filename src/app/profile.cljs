(ns app.profile
  (:require [reagent.core :as r]
            [clojure.string :as str]
            [app.auth :refer (get-auth-header)]
            [app.api :refer (api-url)]
            [ajax.core :refer (GET POST DELETE json-response-format)]))

(defonce profile-state (r/atom nil))
(defonce error-state (r/atom nil))


(defn- fetch-success! [{:keys [profile]}]
  (reset! profile-state profile))

(defn- fetch-error! [error]
  (reset! error-state error))


(defn fetch-profile! [username]
  (reset! profile-state nil)
  (GET (str api-url "/profiles/" username)
    {:handler fetch-success!
     :error-handler fetch-error!
     :headers (get-auth-header)
     :response-format (json-response-format {:keywords? true})}))

(defn follow! [username]
  (POST (str api-url "/profiles/" username "/follow")
    {:handler fetch-success!
     :error-handler fetch-error!
     :headers (get-auth-header)
     :response-format (json-response-format {:keywords? true})}))

(defn un-follow! [username]
  (DELETE (str api-url "/profiles/" username "/follow")
    {:handler fetch-success!
     :error-handler fetch-error!
     :headers (get-auth-header)
     :response-format (json-response-format {:keywords? true})}))


(comment
  (fetch-profile! "condorapp1")
  (follow! "Gerome")
  (un-follow! "Gerome")
  (deref profile-state)
  (reset! profile-state nil)
  )