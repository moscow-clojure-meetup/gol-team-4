(ns gol.renderer)

(defn gen-cell [index cell]
  [:div {:key index :class ["cell" (if (= 0 cell) "dead" "live")]}])

(defn gen-row [index row]
  [:div {:key index :class "row"} (map-indexed gen-cell row)])


(defn gen-html-grid [grid]
    [:div {:class "main-grid"}
      (map-indexed gen-row grid)])
