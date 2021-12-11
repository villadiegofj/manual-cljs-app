(ns app.core
  (:require [reagent.core :as r]
            [reagent.dom :as rd]))

; application STATE
(defonce counter (r/atom 0))

; get main div
(defn app []
  (.getElementById js/document "app"))

; create an HTML element
(defn counter-component []
  [:div {:class ["h3"]}
   "Counter No: " @counter
   [:br]
   [:button {:on-click #(swap! counter inc)
             :class ["btn" "btn-primary"]
             } "Click Me"]])

; meta tag :dev/after-load is for hot-code reloading
(defn ^:dev/after-load start
  "Render the toplevel component for this app."
  []
  (rd/render [counter-component] (app)))

; entry point and called once
(defn ^:export init []
  (start))