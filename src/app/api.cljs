(ns app.api)


(defonce api-url "https://api.realworld.io/api")

(defn error-handler [error]
  (println error)
  (.log js/console error))
