(ns app.pages.register
  (:require [app.auth :as auth]
            [reitit.frontend.easy :as rfe]))


(defn register-page []
  [:div.auth-page>div.container.page>div.row
   [:div.col-md-6.offset-md-3.col-xs-12
    [:h1.text-xs-center "Sign Up"]
    [:p.text-xs-center [:a {:href (rfe/href :login)} "Have an account?"]]
    [:form {:on-submit auth/signup}
     [:fieldset
      [:fieldset.form-group
       [:input.form-control.form-control-lg
        {:type "text" :placeholder "Username"}]]
      [:fieldset.form-group
       [:input.form-control.form-control-lg
        {:type "email" :placeholder "Email"}]]
      [:fieldset.form-group
       [:input.form-control.form-control-lg
        {:type "password" :placeholder "Password"}]]
      [:button.btn.btn-lg.btn-primary.pull-xs-right "Sign Up"]]]]])
