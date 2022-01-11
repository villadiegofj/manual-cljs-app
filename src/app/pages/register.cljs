(ns app.pages.register
  (:require [app.auth :as auth :refer (error-state)]
            [app.components.error-list :refer (error-list)]
            [reitit.frontend.easy :as rfe]
            [reagent.core :as r]))

(defn register! [evt input]
  (.preventDefault evt)
  (auth/register! input))


(defn register-page []
  (let [initial-state {:username ""
                       :password ""
                       :email ""}
        state (r/atom initial-state)]
    (fn []
      [:div.auth-page>div.container.page>div.row
       [:div.col-md-6.offset-md-3.col-xs-12
        [:h1.text-xs-center "Sign Up"]
        [:p.text-xs-center [:a {:href (rfe/href :login)} "Have an account?"]]
        [error-list @error-state]
        [:form {:on-submit #(register! % @state)}
         [:fieldset
          [:fieldset.form-group
           [:input.form-control.form-control-lg
            {:type "text"
             :placeholder "Username"
             :value (:username @state)
             :on-change #(swap! state assoc :username (.. % -target -value))}]]
          [:fieldset.form-group
           [:input.form-control.form-control-lg
            {:type "email"
             :placeholder "Email"
             :value (:email @state)
             :on-change #(swap! state assoc :email (.. % -target -value))}]]
          [:fieldset.form-group
           [:input.form-control.form-control-lg
            {:type "password"
             :placeholder "Password"
             :value (:password @state)
             :on-change #(swap! state assoc :password (.. % -target -value))}]]
          [:button.btn.btn-lg.btn-primary.pull-xs-right "Sign Up"]]]]])))
