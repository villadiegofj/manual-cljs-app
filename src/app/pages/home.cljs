(ns app.pages.home
  (:require [app.components.banner :refer (banner)]
            [app.components.main-view :refer (main-view)]))


(defn home-page []
  [:div.home-page
   [banner "counduit" "auth-user-token"]
   [:div.container.page>div.row
    [main-view]
    [:div.col-md-3
     [:div.sidebar
      [:p "Popular tags"]]]]])
