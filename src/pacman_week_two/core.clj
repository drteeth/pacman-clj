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

(defn board-offset [board x y]
  (+ (mod x (:x board)) (* (:x board) (mod y (:y board)))))

(defn get-cell [board x y]
    (nth (:data board) (board-offset board x y)))

(defn put-cell [board x y v]
  (assoc-in board [:data (board-offset board x y)] v))

(def move-x {
  :left -1
  :right 1
  })

(defn move [board character dir]
  (let [
      x-pos (-> board character :x)
      x-offset (dir move-x)
      new-x (+ x-offset x-pos)
      ]
      (assoc-in board [character :x] new-x)
    ))
