(ns app.components.header
  (:require [reitit.frontend.easy :as rfe]
            [goog.string :as gstr]))


(defn un-auth-header []
  [:nav.navbar.navbar-light>div.container
   [:a.navbar-brand {:href (rfe/href :home)} "conduit"]
   [:ul.nav.navbar-nav.pull-xs-right
    [:li.nav-item
     [:a.nav-link {:href (rfe/href :home)} "Home"]]
    [:li.nav-item
     [:a.nav-link {:href (rfe/href :login)} "Login"]]
    [:li.nav-item
     [:a.nav-link {:href (rfe/href :register)} "Register"]]]])


(defn auth-header [{:keys [username image]}]
  [:nav.navbar.navbar-light>div.container
   [:a.navbar-brand {:href (rfe/href :home)} "conduit"]
   [:ul.nav.navbar-nav.pull-xs-right
    [:li.nav-item
     [:a.nav-link {:href (rfe/href :home)} "Home"]]
    [:li.nav-item
     [:a.nav-link {:href (rfe/href :login)} 
      [:i.ion-compose]
      (gstr/unescapeEntities "&nbsp;") "New Article"]]
    [:li.nav-item
     [:a.nav-link {:href (rfe/href :settings)}
     [:i.ion-gear-a]
      (gstr/unescapeEntities "&nbsp;") "Settings"]]
    [:li.nav-item
     [:a.nav-link {:href (rfe/href :settings)}
      [:img.user-pic {:src image}]
      username]]]])


(defn header [auth-user]
  (if auth-user 
    [auth-header auth-user] 
    [un-auth-header]))
