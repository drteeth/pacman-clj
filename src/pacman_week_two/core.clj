(ns pacman-week-two.core)

(def empty-cell {:points 0})
(def dot-cell {:points 10})
(def wall-cell (merge empty-cell {:wall true}))

(def pacman {:x 0 :y 0})
(def board {
            :x 28
            :y 31
            :data (vec (repeat 868 empty-cell))
            :pacman pacman
            :points 0
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

(defn char-pos [pos-hash]
  (let [{:keys [x y]} pos-hash]
    [x y]))

(defn pacman-pos [board]
  (char-pos (:pacman board)))

(defn eat-dot [board]
  (let [pos (pacman-pos board)]
    (put-cell board pos empty-cell)))

(defn gain-points [board]
  (let [pos (pacman-pos board)
        {found-points :points} (get-cell board pos)]
    (update-in board [:points]  + found-points)))

(defn wall? [cell]
  (:wall cell))

(defn wall-test [board new-pos]
  (let [current-pos (pacman-pos board)
        current-cell (get-cell board current-pos)
        new-cell (get-cell board new-pos)]
    (if (wall? new-cell)
        current-pos
        new-pos)))

(defn move [board character-key dir]
  (let [transform (dir dirs)
        character (character-key board)
        new-pos {:x (mod
                      (+ (:x character)
                         (:x transform))
                      (:x board))
                 :y (mod
                      (+ (:y character)
                         (:y transform))
                      (:y board))}
        final-pos (wall-test board (char-pos new-pos))]
    (-> board
        (assoc-in [character-key :x] (first final-pos))
        (assoc-in [character-key :y] (last final-pos))
        gain-points
        eat-dot)))

