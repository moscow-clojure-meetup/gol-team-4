(ns gol.logic
  (:require [reagent.core :as reagent :refer [atom]]))

(defn add-noise [grid]
  (map (fn [row]
         (map #(if (> 0.9 (js/Math.random)) 0 1) row))
       grid))

(def n 100)

(def m 100)

(defn generate-grid [n m]
  (js->clj (make-array Boolean/TYPE n m))
  )

(def state
  (atom (add-noise (generate-grid n m))))


(defn logic [liveness count]
  (case liveness
    1 (case count
        (0 1) 0
        (2 3) 1
        0)
    0 (case  count
        3 1
        0))
  )


(defn get_cell [[x y state]]
  (try
    (-> state
      (nth y)
      (nth x))
    (catch js/Error e
      0)))

(defn sum_neibers [x y state]
  (+
       (get_cell [(inc x) y state])
       (get_cell [(dec x) y state])
       (get_cell [(dec x) (inc y) state])
       (get_cell [(inc x) (inc y) state])
       (get_cell [(inc x) (dec y) state])
       (get_cell [(dec x) (dec y) state])
       (get_cell [x (dec y) state])
       (get_cell [x (inc y) state])))

(defn update-state
  [atom]
  (let [y-l (-> @atom count)
        x-l (-> @atom first count)]
    (reset! atom
      (vec (for [y (range y-l)]
        (vec (for [x (range x-l)]
          (let [neibors (sum_neibers x y @atom)
                live? (logic (get_cell [x y @atom]) neibors)]
            live?))))))))
