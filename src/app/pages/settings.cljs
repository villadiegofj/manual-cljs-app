(ns app.pages.settings
  (:require [app.auth :as auth :refer (logout! auth-state)]
            [reagent.core :as r]))


(defn save-user [evt user]
  (.preventDefault evt)
  (auth/save-user! user))

(defn settings-form [user]
  (let [state (r/atom user)]
    (fn []
     [:form {:on-submit #(save-user % @state)}
      [:fieldset
       [:fieldset.form-group
        [:input.form-control 
         {:type "text"
          :placeholder "URL of profile picture"
          :value (:image @state)
          :on-change #(swap! state assoc :image (.. % -target -value))}]]
       [:fieldset.form-group
        [:input.form-control.form-control-lg
         {:type "text"
          :placeholder "Username"
          :value (:username @state)
          :on-change #(swap! state assoc :username (.. % -target -value))}]]
       [:fieldset.form-group
        [:textarea.form-control.form-control-lg
         {:type "text"
          :rows 8
          :placeholder "Short bio about you"
          :value (:bio @state)
          :on-change #(swap! state assoc :bio (.. % -target -value))}]]
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
       [:button.btn.btn-lg.btn-primary.pull-xs-right 
        {:type "submit"} 
        "Update Settings"]]])))


(defn settings-page []
  [:div.settings-page>div.container.page>div.row
   [:div.col-md-6.offset-md-3.col-xs-12
    [:h1.text-xs-center "Your settings"]    
    (when @auth-state 
      [settings-form @auth-state])
    [:hr]
    [:button.btn.btn-outline-danger
     {:on-click logout!}
     "Or click here to logout."]]])


(comment
  (deref auth-state)
  )