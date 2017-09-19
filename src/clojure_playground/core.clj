(ns clojure-playground.core
  (:require [clojure.string :as str]))

(defn initBoard
  []
  [[\- \- \-]
   [\- \- \-]
   [\- \- \-]])

(defn getPos
  [posX posY board]
  (nth (nth board posY) posX))

(defn vecInput
  [input]
  (vec
    (map read-string
      (str/split input #" "))))

(defn x
  [input]
  (get (vecInput input) 0))

(defn y
  [input]
  (get (vecInput input) 1))

(defn putMark
  [input board player]
  (do
    (let [posX (x input), posY (y input)]
      (assoc-in board [posY posX] player))))

(defn printBoard
  [board]
  (map println board))

(defn isWin
  [b]
  (cond
    (= (getPos 0 0 b) (getPos 0 1 b) (getPos 0 2 b) "x") true
    (= (getPos 1 0 b) (getPos 1 1 b) (getPos 1 2 b) "x") true
    (= (getPos 2 0 b) (getPos 2 1 b) (getPos 2 2 b) "x") true
    (= (getPos 0 0 b) (getPos 1 0 b) (getPos 2 0 b) "x") true
    (= (getPos 0 1 b) (getPos 1 1 b) (getPos 2 1 b) "x") true
    (= (getPos 0 2 b) (getPos 1 2 b) (getPos 2 2 b) "x") true
    (= (getPos 0 0 b) (getPos 1 1 b) (getPos 2 2 b) "x") true
    (= (getPos 0 2 b) (getPos 1 1 b) (getPos 2 0 b) "x") true

    (= (getPos 0 0 b) (getPos 0 1 b) (getPos 0 2 b) "o") true
    (= (getPos 1 0 b) (getPos 1 1 b) (getPos 1 2 b) "o") true
    (= (getPos 2 0 b) (getPos 2 1 b) (getPos 2 2 b) "o") true
    (= (getPos 0 0 b) (getPos 1 0 b) (getPos 2 0 b) "o") true
    (= (getPos 0 1 b) (getPos 1 1 b) (getPos 2 1 b) "o") true
    (= (getPos 0 2 b) (getPos 1 2 b) (getPos 2 2 b) "o") true
    (= (getPos 0 0 b) (getPos 1 1 b) (getPos 2 2 b) "o") true
    (= (getPos 0 2 b) (getPos 1 1 b) (getPos 2 0 b) "o") true
    :else false))

(defn isDraw
  [[row0 row1 row2]]
  (cond
    (or
      (some #(= \- %) row0)
      (some #(= \- %) row1)
      (some #(= \- %) row2)) false
    :else true))

(defn greeting
  []
  (do
    (println "XO in clojure 1.0")
    (println "author: dodadoa")
    (println "----------------")))

(defn gameover
  [player]
  (do
    (println "game over") ;then
    (println (if (= player "x") "o" "x") " win")))

(defn displayPlayer
  [player]
  (do
    (println "player: " player)
    (println "pX pY")))

(defn isAbleToPut
  [board input]
  (cond
    (= (getPos (x input) (y input) board) "x") false
    (= (getPos (x input) (y input) board) "o") false
    :else true))

(defn game
  []
  (do
    (greeting)
    (loop [isGameOver nil player "x" board (initBoard)]
      (if (= (isDraw board) true)
        (println "game draw")
        (if (= isGameOver true)
          (gameover player) ;then
          (do ;else
            (displayPlayer player)
            (let [input (try
                          (read-line)
                          (catch Exception e
                            (str "caught exception: " (.getMessage e))))]
              (if (= (isAbleToPut board input) true)
                (do
                  (println (printBoard (putMark input board player)))
                  (recur
                    (isWin (putMark input board player))
                    (if (= player "x") "o" "x")
                    (putMark input board player)))
                (do
                  (println "you can't put here")
                  (recur
                    false
                    player
                    board))))))))))

(defn -main
  []
  (game))
