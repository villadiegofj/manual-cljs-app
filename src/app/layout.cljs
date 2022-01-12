(ns app.layout
  (:require [app.components.header :refer (header)]
            [app.routes :refer (routes-state)]
            [app.auth :refer (auth-state)]))


(defn app []
  [:div
   [header @auth-state]
   [:hr]
   (let [current-view (-> @routes-state :data :view)]
     [current-view @routes-state])
   ;[:hr]
   ;[js-react-component]
   ])
