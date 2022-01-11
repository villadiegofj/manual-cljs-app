(ns app.components.error-list
  (:require [clojure.string :as str]))

(defn error-list [errors]
  (when (seq errors)
    [:ul.error-messages
     (for [[key value] errors]
       ^{:key key}
       [:li
        (-> key
            name
            str/capitalize
            (str " - " (str/join ", " value)))])]))