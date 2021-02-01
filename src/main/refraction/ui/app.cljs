(ns ^:dev/always refraction.ui.app
  (:require [reagent.core :as r]
            [reagent.dom :as dom]
            ;; Scoped names require Cljs 1.10.439
            [reagent.impl.template :as rtpl]
            ;; server
            [refraction.server :as s]))

(defn app []
  [:div.container {:style {:font-color "white"}} "hot reload!!!"])

(defn ^:export main []
  (dom/render [app] (js/document.getElementById "app")))

;; Hooks
;; -----

(defn ^:dev/after-load start []
  (js/console.log "renderer - start")
  (main))

(defn init []
  (js/console.log "renderer - init")
  ;; init is only called once, live reload will call stop then start
  (start))

(defn ^:dev/before-load stop
  "Hot code reload hook to shut down resources so hot code reload can work"
  [done]
  (js/console.warn "stop called")
  (when-some [srv @s/server]
    (.close srv
            (fn [err]
              (js/console.log "stop completed" err)
              (done)))))



