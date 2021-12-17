(ns app.articles
  (:require [reagent.core :as r]
            [app.api :refer (api-url)]
            [ajax.core :refer (GET json-response-format)]))


(defonce article-list (r/atom nil))


(defn handler [resp]
  (reset! article-list resp))

(defn error-handler [{:keys [status status-text]}]
  (.log js/console status-text))

(defn get-articles []
 (GET (str api-url "/articles") {:handler handler
                                 :error-handler error-handler
                                 :response-format (json-response-format {:keywords? true})}))

(comment
  (-> (deref routes-state)
      (:data :view))
  (nth [10 20] 0)
  (get [10 20] 0)
  (get-articles)
  (-> (deref article-list)
      :articles
      (get 0))
)
