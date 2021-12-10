(ns app.core)

; application STATE
(defonce title (atom "HOLA MUNDO!!"))

; get main div
(defn app []
  (.getElementById js/document "app"))

; create an HTML element
(defn h1 []
  (doto
    (.createElement js/document "H1")
    (aset "innerHTML" @title)))

; meta tag :dev/after-load is for hot-code reloading
(defn ^:dev/after-load start []
  (js/console.log "Hello CLJS 2")
  (.appendChild (app) (h1)))

; entry point and called once
(defn ^:export init []
  (start))