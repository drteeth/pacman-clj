(ns pacman-week-two.core)

(def empty-cell {})
(def dot-cell {:points 10})

(def pacman {:x 0 :y 0})
(def board {
            :x 28
            :y 31
            :data (vec (repeat 868 empty-cell))
            :pacman pacman
            })

(defn board-offset [board [x y]]
  (+ (mod x (:x board)) (* (:x board) (mod y (:y board)))))

(defn get-cell [board pos]
  (nth (:data board) (board-offset board pos)))

(defn put-cell [board pos v]
  (assoc-in board [:data (board-offset board pos)] v))

(def dirs {:left {:x -1 :y 0}
           :right {:x 1 :y 0}
           :up {:x 0 :y -1}
           :down {:x 0 :y 1}})

(defn move [board character-key dir]
  (let [transform (dir dirs) 
        character (character-key board)
                  
        new-pos {:x (mod (+ (:x character) (:x transform)) (:x board))
                 :y (mod (+ (:y character) (:y transform)) (:y board))}]
    (-> board
        (assoc-in [character-key :x] (:x new-pos))
        (assoc-in [character-key :y] (:y new-pos)))))
