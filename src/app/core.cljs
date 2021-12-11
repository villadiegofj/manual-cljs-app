(ns app.core
  (:require [reagent.core :as r]
            [reagent.dom :as rd]))

; application STATE
(defonce counter (r/atom 0))

(defonce mock-articles
   [{:title "Backpacking is fun"}])

(defn articles [items]
  (if-not (seq items)
    [:div.article-preview "Loading..."]
    (if (= 0 (count items))
      [:div.article-preview "No articles are here."]
      [:div
       (for [article items]
          [:h2 (:title article)])])))

(defn header []
  [:nav.navbar.navbar-light
   [:div.container]
   [:a.navbar-brand "conduit"]])

(defn banner [token]
  (when token
    [:div.banner>div.container
     [:h1.logo-front "conduit"]
     [:p "A place to share your knowledge"]]))

(defn main-view []
  [:div.col-md-9
   [:div.feed-toggle
    [:ul.nav.nav-pills.outline-active
     [:li.nav-item
      [:a.nav-link.active {:href ""} "Global feed"]]]]
   [articles mock-articles]])

(defn home-page []
  [:div.home-page
   [banner "auth-user-token"]
   [:div.container.page>div.row
    [main-view]
    [:div.col-md-3
     [:div.sidebar
      [:p "Popular tags"]]]]])

(defn app []
  [:div
   [header]
   [home-page]])

; meta tag :dev/after-load is for hot-code reloading
(defn ^:dev/after-load start
  "Render the toplevel component for this app."
  []
  (rd/render [app] (.getElementById js/document "app")))

; entry point and called once
(defn ^:export init []
  (start))