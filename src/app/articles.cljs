(ns app.articles
  (:require [reagent.core :as r]
            [app.auth :refer (get-auth-header)]
            [app.api :refer (api-url)]
            [ajax.core :refer (GET json-response-format)]))


(defonce articles-state (r/atom nil))
(defonce loading-state (r/atom false))

(defn handler [resp]
  (reset! loading-state false)
  (reset! articles-state resp))

(defn error-handler [error]
  (println error)
  (reset! loading-state false)
  (.log js/console error))

(defn get-articles []
  (reset! loading-state true)
  (GET (str api-url "/articles?limit=2")
    {:handler handler
     :error-handler error-handler
     :response-format (json-response-format {:keywords? true})}))

(defn articles-feed []
  (reset! loading-state true)
  (GET (str api-url "/articles/feed?limit=2")
    {:handler handler
     :error-handler error-handler
     :headers (get-auth-header)
     :response-format (json-response-format {:keywords? true})}))


(defn limit [rows page]
  (str "limit=" rows "&offset=" (or (* page rows) 0)))

(defn fetch-by-author [author page]
  (reset! loading-state true)
  (GET (str api-url "/articles?author=" (js/encodeURIComponent author) "&" (limit 5 page) )
    {:handler handler
     :error-handler error-handler
     :response-format (json-response-format {:keywords? true})}))

(defn fetch-by-favorited [author page]
  (reset! loading-state true)
  (GET (str api-url "/articles?favorited=" (js/encodeURIComponent author) "&" (limit 5 page))
    {:handler handler
     :error-handler error-handler
     :response-format (json-response-format {:keywords? true})}))

(comment
  (-> (deref routes-state)
      (:data :view))
  (nth [10 20] 0)
  (get [10 20] 0)
  (get-articles)
  (articles-feed)
  (deref articles-state)
  (fetch-by-author "Gerome" 0)
  (fetch-by-favorited "condorapp1" 0)
  (-> (deref articles-state)
      :articles
      (get 0))
  )

