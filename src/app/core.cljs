(ns app.core
  (:require [reagent.core :as r]
            [reagent.dom :as rd]
            [reitit.frontend :as rf]
            [reitit.frontend.easy :as rfe]
            [reitit.coercion.spec :as rss]
            [spec-tools.data-spec :as ds]
            [ajax.core :refer (GET POST json-response-format)]
            ;["primereact/button" :refer (Button)]
            ["@chakra-ui/react" :refer (ChakraProvider Flex Button)]))

; import { Button } from 'primereact/button';
; import { InputText } from 'primereact/inputtext';

; application STATE
(defonce counter (r/atom 0))
(defonce article-list (r/atom nil))
(defonce api-url "https://api.realworld.io/api")
(defonce routes-state (r/atom nil))

(defn handler [resp]
  (reset! article-list resp))

(defn error-handler [{:keys [status status-text]}]
  (.log js/console status-text))

(defn get-articles []
 (GET (str api-url "/articles") {:handler handler
                                 :error-handler error-handler
                                 :response-format (json-response-format {:keywords? true})}))



(defonce mock-articles
   [{:title "Backpacking is fun"}])



(defn article-preview [{:keys [title description author favoritesCount tagList createdAt]}]
  [:div.article-preview
   [:div.article-meta
    [:a
     [:img {:src (:image author)}]]
    [:div.info
     [:a.author (:username author)]
     [:span.date (.toDateString (new js/Date createdAt))]]
    [:div.pull-xs-right
     [:button.btn.btn-sm.btn-outline-primary
      [:i.ion-heart favoritesCount]]]]
   [:a.preview-link
    [:h1 title]
    [:p description]
    [:span "Read more..."]
    [:ul.tag-list
     (for [tag tagList]
       ^{:key tag}
       [:li.tag-default.tag-pill.tag-outline])]]])

(defn articles [items]
  (if-not (seq items)
    [:div.article-preview "Loading..."]
    (if (= 0 (count items))
      [:div.article-preview "No articles are here."]
      [:div
       (for [{:keys [slug] :as article} items]
         ^{:key slug} ;; buena tecnica para aislar el key del componente
         [article-preview article])])))

(defn js-react-component []
  [:> Flex {:style {:background-color "hsl(196 73% 90%)"}}
   [:div {:id "js-react-comp"}
    [:p "Native JS React Components"]
    [:> Button {:on-click #(js/alert "Hello")
                :style {:background-color "hsl(196, 73%, 62%)"}} "Click Me"]]])

(defn header []
  [:nav.navbar.navbar-light>div.container
   [:a.navbar-brand {:href (rfe/href ::home)} "conduit"]
   [:ul.nav.navbar-nav.pull-xs-right
    [:li.nav-item
     [:a.nav-link {:href (rfe/href ::home)} "Home"]]
    [:li.nav-item
     [:a.nav-link {:href (rfe/href ::login)} "Login"]]
    [:li.nav-item
     [:a.nav-link {:href (rfe/href ::register)} "Register"]]]])

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
   [articles (:articles (deref article-list))]])

(defn home-page []
  [:div.home-page
   [banner "auth-user-token"]
   [:div.container.page>div.row
    [main-view]
    [:div.col-md-3
     [:div.sidebar
      [:p "Popular tags"]]]]])

(defn auth-signin [evt]
  (.preventDefault evt)
  (.log js/console "LOGIN"))

(defn auth-signup [evt]
  (.preventDefault evt)
  (.log js/console "REGISTER"))

(defn login-page []
  [:div.auth-page>div.container.page>div.row
   [:div.col-md-6.offset-md-3.col-xs-12
    [:h1.text-xs-center "Sign In"]
    [:p.text-xs-center [:a {:href (rfe/href ::register)} "Need an account?"]]
    [:form {:on-submit auth-signin}
     [:fieldset
      [:fieldset.form-group
       [:input.form-control.form-control-lg
        {:type "email" :placeholder "john@gmail.com"}]]
      [:fieldset.form-group
       [:input.form-control.form-control-lg
        {:type "password"}]]
      [:button.btn.btn-lg.btn-primary.pull-xs-right "Sign In"]]]]])

(defn register-page []
  [:div.auth-page>div.container.page>div.row
   [:div.col-md-6.offset-md-3.col-xs-12
    [:h1.text-xs-center "Sign Up"]
    [:p.text-xs-center [:a {:href (rfe/href ::login)} "Have an account?"]]
    [:form {:on-submit auth-signup}
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

(def routes
  [
   ["/" {:name ::home
         :view home-page}]
   ["/login" {:name ::login
              :view login-page}]
   ["/register" {:name ::register
                 :view register-page}]])

(defn router-start! []
  (rfe/start!
   (rf/router routes {:data {:coercion rss/coercion}})
   (fn [matched-route] (reset! routes-state matched-route))
   {:use-fragment true}))

(defn app []
  [:div
   [header]
   (let [current-view (-> @routes-state :data :view)]
     [current-view])
   ;[:hr]
   ;[js-react-component]
   ])

; meta tag :dev/after-load is for hot-code reloading
(defn ^:dev/after-load start
  "Render the toplevel component for this app."
  []
  (rd/render [app] (.getElementById js/document "app")))

; entry point and called once
(defn ^:export init []
  (router-start!)
  (get-articles)
  (start))


(comment
  (-> (deref routes-state)
      (:data :view))
  (nth [10 20] 0)
  (get [10 20] 0)
  (get-articles)
  (-> (deref article-list)
      :articles
      (get 0))


  )
