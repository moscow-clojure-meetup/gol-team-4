(ns gol.core
    (:require [reagent.core :as reagent :refer [atom]]
              [secretary.core :as secretary :include-macros true]
              [accountant.core :as accountant]
              [gol.renderer :as renderer]))

;; -------------------------
;; Views

(defn home-page []
  [:div [:h2 "Welcome to gol"]
   [:nav
    [:button {:id "step" :on-click (fn [] (println "step"))} "step"]
    [:button {:id "play" :on-click (fn [] (println "play"))} "play"]
    [:button {:id "stop" :on-click (fn [] (println "stop"))} "stop"]]
   [renderer/gen-html-grid (renderer/generate-grid renderer/n renderer/m)]])

(defn about-page []
  [:div [:h2 "About gol"]
   [:div [:a {:href "/"} "go to the home page"]]])

;; -------------------------
;; Routes

(defonce page (atom #'home-page))

(defn current-page []
  [:div [@page]])

(secretary/defroute "/" []
  (reset! page #'home-page))

(secretary/defroute "/about" []
  (reset! page #'about-page))

;; -------------------------
;; Initialize app

(defn mount-root []
  (reagent/render [current-page] (.getElementById js/document "app")))

(defn init! []
  (accountant/configure-navigation!
    {:nav-handler
     (fn [path]
       (secretary/dispatch! path))
     :path-exists?
     (fn [path]
       (secretary/locate-route path))})
  (accountant/dispatch-current!)
  (mount-root))
