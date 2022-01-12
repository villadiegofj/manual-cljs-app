(ns app.pages.profile)



(defn profile-page [{{:keys [username]} :path-params}]
  [:div "PAGE - profile : " username])