(ns refraction.server
  (:require [cljs.nodejs :as node]
            ["express" :as express]
            ["http" :as http]))

(defonce server (atom nil))

(def express (node/require "express"))

(defn main [& args]
  (js/console.log "starting server")
  (let [app (express)]
    (.get app "/hello" (fn [req res] (.send res "Hello, world")))
    (.listen app 3000 (fn [] (println "Example app listening on port 3000!")))))



;; HMR Hooks
;; 

(defn start
  "Hook to start. Also used as a hook for hot code reload."
  []
  (js/console.warn "start called")
  (main))

(defn stop
  "Hot code reload hook to shut down resources so hot code reload can work"
  [done]
  (js/console.warn "stop called")
  (when-some [srv @server]
    (.close srv
            (fn [err]
              (js/console.log "stop completed" err)
              (done)))))

