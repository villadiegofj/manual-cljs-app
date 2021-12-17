(ns app.core
  (:require [reagent.dom :as rd]
            [spec-tools.data-spec :as ds]
            [app.layout :refer (app)]
            [app.routes :refer (router-start!)]
            [app.articles :refer (get-articles)]))


; meta tag :dev/after-load is for hot-code reloading
(defn ^:dev/after-load render
  "Render the toplevel component for this app."
  []
  (rd/render [app] (.getElementById js/document "app")))

; entry point and called once
(defn ^:export main []
  (router-start!)
  (get-articles)
  (render))
