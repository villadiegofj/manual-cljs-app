(ns app.auth)


(defn signin [evt]
  (.preventDefault evt)
  (.log js/console "LOGIN"))

(defn signup [evt]
  (.preventDefault evt)
  (.log js/console "REGISTER"))
