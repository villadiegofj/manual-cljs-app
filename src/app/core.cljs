(ns app.core
  (:require [reagent.core :as r]
            [reagent.dom :as rdom]))

; application STATE
(defonce title (atom "HOLA MUNDO!!"))

; get main div
(defn app []
  (.getElementById js/document "app"))

; create an HTML element
(defn simple-component []
  [:div
    [:p "Hello Reagent"]])

; meta tag :dev/after-load is for hot-code reloading
(defn ^:dev/after-load start
  "Render the toplevel component for this app."
  []
  (rdom/render [simple-component] (app)))

; entry point and called once
(defn ^:export init []
  (start))