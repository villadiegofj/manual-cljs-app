(ns app.layout
  (:require [app.components.header :refer (header)]
            [app.routes :refer (routes-state)]))


(defn app []
  [:div
   [header]
   [:hr]
   (let [current-view (-> @routes-state :data :view)]
     [current-view])
   ;[:hr]
   ;[js-react-component]
   ])
