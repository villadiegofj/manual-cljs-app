(ns app.pages.profile
  (:require [app.profile :refer (profile-state follow! un-follow!)]
            [app.articles :as articles :refer (articles-state loading-state)]
            [app.components.article-preview :refer (articles)]
            [app.auth :refer (auth-state)]
            [goog.string :as gstr]
            [reagent.core :as r]
            [reitit.frontend.easy :as rfe]))

(defonce tab-state (r/atom :my))

(defn handle-by-author [username]
  (println "reseting articles")
  (reset! articles-state nil)
  (reset! tab-state :my)
  (articles/fetch-by-author username 0))

(defn handle-by-favorited [username]
  (reset! tab-state :fav)
  (articles/fetch-by-favorited username 0))

(defn toggle-follow [{:keys [following username]}]
  (if following
    (un-follow! username)
    (follow! username)))

(defn edit-profile-settings [user?]
  (when user?
    [:a.btn.btn-sm.btn-outline-secondary.action-btn
     {:href (rfe/href :settings)}
     [:i.ion-gear-a]
     (gstr/unescapeEntities "&nbsp;") "Edit Profile Settings"]))

(defn follow-user-button [user? user]
  (println "user: " user)
  (when-not user?
    [:button {:class ["btn btn-sm action-btn" (if (:following user) "btn-secondary" "btn-outline-secondary")]
              :on-click #(toggle-follow user)}
     [:i.ion-plus-round]
     (if (:following user) " Unfollow " " Follow ")]))

(defn profile-page []
  (let [user? (= (:username @auth-state) (:username @profile-state))]
    (when @profile-state
      [:div.profile-page
       [:div.user-info>div.container
        [:div.row>div.col-xs-12.col-md-10.offset-md-1
         [:img.user-img {:src (:image @profile-state)}]
         [:h4 (:username @profile-state)]
         [:p (:bio @profile-state)]
         [edit-profile-settings user?]
         (gstr/unescapeEntities "&nbsp;")
         [follow-user-button user? @profile-state]]]
       [:div.container>div.row>div.col-xs-12.col-md-10.offset-md-1
        [:div.articles-toggle
         [:ul.nav.nav-pills.outline-active
          [:li.nav-item
           [:a {:class ["nav-link" (when (= @tab-state :my) "active")]
                :href ""
                :on-click #(handle-by-author (:username @auth-state))} "My Articles"]]
          [:li.nav-item
           [:a {:class ["nav-link" (when (= @tab-state :fav) "active")]
                :href ""
                :on-click #(handle-by-favorited (:username @auth-state))} "Favourited Articles"]]]]
        [articles (:articles (deref articles-state)) @loading-state]]])))