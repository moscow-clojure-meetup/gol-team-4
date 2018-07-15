(ns gol.logic)

(def state
  (atom [[0 0 0 0 0 0 0 0 0]
         [0 0 0 0 0 0 0 0 0]
         [0 0 0 0 0 0 0 0 0]
         [0 0 0 0 0 0 0 0 0]
         [0 0 0 0 0 0 0 0 0]
         [0 0 0 0 0 0 0 0 0]
         [0 0 0 0 0 0 0 0 0]
         [0 0 0 0 0 0 0 0 0]
         [0 0 0 0 0 0 0 0 0]
         [0 0 0 0 0 0 0 0 0]
        ]))


(defn logic [liveness count]
  (case liveness
    1 (case count
        (0 1) 0
        (2 3) 1
        :else 0)
    0 (case  count
        3 1
        :else 0))
  )

(defn get_cell [x y state]
    (let [get (fnil get 0)]
    (-> state
      (get y)
      (get x))))

(defn sum_neibers [x y state]
  (+
       (get_cell [(inc x y) state])
       (get_cell [(dec x) y state])
       (get_cell [(dec x 1) (inc y) state])
       (get_cell [(inc x 1) (inc y) state])
       (get_cell [(inc x 1) (dec y) state])
       (get_cell [(dec x 1) (dec y) state])
       (get_cell [x (dec y) state])
       (get_cell [x (inc y) state])))

(defn update
  [atom]
  (let [y-l (-> @atom count)
        x-l (-> @atom (get 0) count)]
    (reset! atom
      (vec (for [x (range x-l)]
        (vec (for [y (range y-l)]
          (let [neibors (neibors x y state)
            live? (logic (get_cell x y state) neibors)]
            live?))))))))
