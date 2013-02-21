(ns pacman-week-two.core-test
  (:use clojure.test
        pacman-week-two.core))

(deftest board-tests
  (testing "board tests"
    (is (= (* 28 31) (-> board :data count)))
    (is (= empty-cell (nth (board :data) 10)))
    )
  (testing "get coordinate from board"
    (is (= empty-cell (get-cell board [10 10])))
    )
  (testing "board offset"
    (is (= 0 (board-offset board [0 0])))
    (is (= 1 (board-offset board [1 0])))
    (is (= 28 (board-offset board [0 1])))
    (is (= 29 (board-offset board [1 1])))
    (is (= 0 (board-offset board [28 0])))
    (is (= 1 (board-offset board [29 0])))
    (is (= 0 (board-offset board [0 31])))
    (is (= 28 (board-offset board [0 32])))
    (is (= 0 (board-offset board [28 31])))
  )
  (testing "put-cell"
    (is (= "x" (-> board
                 (put-cell [0 0] "x")
                 (get-cell [0 0]))))
    (is (= nil (-> board
                 (put-cell [0 0] nil)
                 (get-cell [0 0])))))
  (testing "pacman"
    (is (= pacman (:pacman board)))
    (is (= nil (:not-pacman board))))
  (testing "moving pacman right"
    (let [moved (move board :pacman :right)
          p (:pacman moved)]
      (is (= 1 (:x p)))
      (is (= 0 (:y p)))
      ))
  (testing "moving pacman left with a wrap-around"
    (let [moved (move board :pacman :left)
      p (:pacman moved)
      ]
      (is (= 27 (:x p)))
      (is (= 0 (:y p)))
      ))
  (testing "moving pacman up with a wrap-around"
    (let [moved (move board :pacman :up)
      p (:pacman moved)
      ]
      (is (= 0 (:x p)))
      (is (= (- (:y board) 1) (:y p)))
      ))
  (testing "moving pacman down with a wrap-around"
    (let [moved (move board :pacman :down)
      p (:pacman moved)
      ]
      (is (= 0 (:x p)))
      (is (= 1 (:y p)))
      ))
  )



