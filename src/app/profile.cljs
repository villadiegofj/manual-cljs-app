(ns app.profile
  (:require [reagent.core :as r]
            [clojure.string :as str]
            [app.api :refer (api-url)]
            [ajax.core :refer (GET json-response-format)]))

(defonce profile-state (r/atom nil))
(defonce error-state (r/atom nil))


(defn- fetch-success! [{:keys [profile]}]
  (reset! profile-state profile))

(defn- fetch-error! [error]
  (reset! error-state error))


(defn fetch-profile! [username]
  (GET (str api-url "/profiles/" username)
    {:handler fetch-success!
     :error-handler fetch-error!
     :response-format (json-response-format {:keywords? true})}))


(comment
  (fetch-profile! "condorapp1")
  (deref profile-state)
  (reset! profile-state nil)
  )