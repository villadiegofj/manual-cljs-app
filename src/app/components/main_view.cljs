(ns app.components.main-view
  (:require [app.articles :refer (article-list)]
            [app.components.article-preview :refer (articles)]))


(defn main-view []
  [:div.col-md-9
   [:div.feed-toggle
    [:ul.nav.nav-pills.outline-active
     [:li.nav-item
      [:a.nav-link.active {:href ""} "Global feed"]]]]
   [articles (:articles (deref article-list))]])
