(ns app.core)

; meta tag :dev/after-load is for hot-code reloading
(defn ^:dev/after-load start []
  (js/console.log "Hello CLJS"))

; entry point and called once
(defn ^:export init []
  (start))