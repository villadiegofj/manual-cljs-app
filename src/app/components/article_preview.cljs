(ns app.components.article-preview)


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
