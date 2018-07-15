(ns gol.core
    (:require [reagent.core :as reagent :refer [atom]]
              [secretary.core :as secretary :include-macros true]
              [gol.logic :as logic]
              [accountant.core :as accountant]
              [gol.renderer :as renderer]))

;; -------------------------
;; Views

(def timer (atom nil))
(def delta-t 100)

(defn home-page []
  [:div [:h2 "Welcome to gol"]
   [:nav
    [:button {:id "step" :on-click (fn [] (logic/update-state logic/state))} "step"]
    [:button {:id "play" :on-click (fn [] (reset! timer (js/setInterval
                                                         (fn [] (logic/update-state logic/state))
                                                         delta-t)))} "play"]
    [:button {:id "stop" :on-click (fn [] (js/clearInterval @timer))} "stop"]]
   [renderer/gen-html-grid @logic/state]])

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
