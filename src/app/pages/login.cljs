(ns app.pages.login
  (:require [app.auth :as auth :refer (error-state)]
            [app.components.error-list :refer (error-list)]
            [reagent.core :as r]
            [reitit.frontend.easy :as rfe]))



(defn login! [evt input]
  (.preventDefault evt)
  (auth/login! input))

(defn login-page []
  (let [initial-state {:password ""
                       :email ""}
        state (r/atom initial-state)]
   (fn []
     [:div.auth-page>div.container.page>div.row
      [:div.col-md-6.offset-md-3.col-xs-12
       [:h1.text-xs-center "Sign In"]
       [:p.text-xs-center [:a {:href (rfe/href :register)} "Need an account?"]]
       [error-list @error-state]
       [:form {:on-submit #(login! % @state)}
        [:fieldset
         [:fieldset.form-group
          [:input.form-control.form-control-lg
           {:type "email" 
            :placeholder "Username"
            :value (:email @state)
            :on-change #(swap! state assoc :email (.. % -target -value))}]]
         [:fieldset.form-group
          [:input.form-control.form-control-lg
           {:type "password"
            :placeholder "Password"
            :value (:password @state)
            :on-change #(swap! state assoc :password (.. % -target -value))}]]
         [:button.btn.btn-lg.btn-primary.pull-xs-right "Sign In"]]]]])))
