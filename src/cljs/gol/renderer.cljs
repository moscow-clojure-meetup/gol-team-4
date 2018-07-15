(ns gol.renderer)

(def n 100)

(def m 100)

(defn generate-grid [n m]
  (js->clj (make-array Boolean/TYPE n m))
  )

(defn gen-cell [index cell]
  [:div {:key index :class ["cell" (if (= 0 cell) "dead" "live")]}])

(defn gen-row [index row]
  [:div {:key index :class "row"} (map-indexed gen-cell row)])

(defn add-noise [grid]
  (map (fn [row]
         (map #(if (> 0.9 (js/Math.random)) 0 1) row))
       grid))

(println (js/Math.random))

(defn gen-html-grid [grid]
  (let [randomized-grid (add-noise grid)]
    [:div {:class "main-grid"}
      (map-indexed gen-row randomized-grid)]))
