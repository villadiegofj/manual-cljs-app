(ns app.core
  (:require [reagent.core :as r]
            [reagent.dom :as rd]
            [ajax.core :refer (GET POST json-response-format)]
            ;["primereact/button" :refer (Button)]
            ["@chakra-ui/react" :refer (ChakraProvider Flex Button)]))

; import { Button } from 'primereact/button';
; import { InputText } from 'primereact/inputtext';

; application STATE
(defonce counter (r/atom 0))
(defonce article-list (r/atom nil))
(defonce api-url "https://api.realworld.io/api")


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
   [articles (:articles (deref article-list))]])

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
   [home-page]
   [:hr]
   [js-react-component]])

; meta tag :dev/after-load is for hot-code reloading
(defn ^:dev/after-load start
  "Render the toplevel component for this app."
  []
  (rd/render [app] (.getElementById js/document "app")))

; entry point and called once
(defn ^:export init []
  (get-articles)
  (start))


(comment
  (nth [10 20] 0)
  (get [10 20] 0)
  (get-articles)
  (-> (deref article-list)
      :articles
      (get 0))


  )
