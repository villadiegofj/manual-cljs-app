(ns app.components.main-view
  (:require [reagent.core :as r]
            [app.articles :refer (articles-state loading-state articles-feed get-articles)]
            [app.components.article-preview :refer (articles)]))


(defonce tab-state (r/atom :your))

(defn handle-feed []
  (reset! tab-state :your)
  (articles-feed))

(defn handle-all []
  (reset! tab-state :all)
  (get-articles))

(defn feed-tabs []
  [:div.feed-toggle
   [:ul.nav.nav-pills.outline-active
    [:li.nav-item
     [:a {:class ["nav-link" (when (= @tab-state :your) "active")]
          :href ""
          :on-click handle-feed} "Your feed"]]
    [:li.nav-item
     [:a {:class ["nav-link" (when (= @tab-state :all) "active")]
          :href ""
          :on-click handle-all} "Global feed"]]]])

(defn main-view []
  [:div.col-md-9
   [feed-tabs]
   [articles (:articles (deref articles-state)) @loading-state]])
