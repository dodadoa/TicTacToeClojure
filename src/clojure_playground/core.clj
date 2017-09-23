(ns clojure-playground.core
  (:require [clojure.string :as str]))

(defn init-board
  []
  [[\- \- \-]
   [\- \- \-]
   [\- \- \-]])

(defn get-pos
  [posX posY board]
  (nth (nth board posY) posX))

(defn vec-input
  [input]
  (vec
    (map read-string
      (str/split input #" "))))

(defn x
  [input]
  (get (vec-input input) 0))

(defn y
  [input]
  (get (vec-input input) 1))

(defn put-mark
  [input board player]
  (do
    (let [posX (x input), posY (y input)]
      (assoc-in board [posY posX] player))))

(defn print-board
  [board]
  (map println board))

(defn is-win
  [b]
  (cond
    (= (get-pos 0 0 b) (get-pos 0 1 b) (get-pos 0 2 b) "x") true
    (= (get-pos 1 0 b) (get-pos 1 1 b) (get-pos 1 2 b) "x") true
    (= (get-pos 2 0 b) (get-pos 2 1 b) (get-pos 2 2 b) "x") true
    (= (get-pos 0 0 b) (get-pos 1 0 b) (get-pos 2 0 b) "x") true
    (= (get-pos 0 1 b) (get-pos 1 1 b) (get-pos 2 1 b) "x") true
    (= (get-pos 0 2 b) (get-pos 1 2 b) (get-pos 2 2 b) "x") true
    (= (get-pos 0 0 b) (get-pos 1 1 b) (get-pos 2 2 b) "x") true
    (= (get-pos 0 2 b) (get-pos 1 1 b) (get-pos 2 0 b) "x") true

    (= (get-pos 0 0 b) (get-pos 0 1 b) (get-pos 0 2 b) "o") true
    (= (get-pos 1 0 b) (get-pos 1 1 b) (get-pos 1 2 b) "o") true
    (= (get-pos 2 0 b) (get-pos 2 1 b) (get-pos 2 2 b) "o") true
    (= (get-pos 0 0 b) (get-pos 1 0 b) (get-pos 2 0 b) "o") true
    (= (get-pos 0 1 b) (get-pos 1 1 b) (get-pos 2 1 b) "o") true
    (= (get-pos 0 2 b) (get-pos 1 2 b) (get-pos 2 2 b) "o") true
    (= (get-pos 0 0 b) (get-pos 1 1 b) (get-pos 2 2 b) "o") true
    (= (get-pos 0 2 b) (get-pos 1 1 b) (get-pos 2 0 b) "o") true
    :else false))

(defn is-draw
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

(defn display-player
  [player]
  (do
    (println "player: " player)
    (println "pX pY")))

(defn is-able-to-put
  [board input]
  (cond
    (= (get-pos (x input) (y input) board) "x") false
    (= (get-pos (x input) (y input) board) "o") false
    :else true))

(defn game
  []
  (do
    (greeting)
    (loop [is-game-over nil player "x" board (init-board)]
      (if (= (is-draw board) true)
        (println "game draw")
        (if (= is-game-over true)
          (gameover player) ;then
          (do ;else
            (display-player player)
            (let [input (try
                          (read-line)
                          (catch Exception e
                            (str "caught exception: " (.getMessage e))))]
              (if (= (is-able-to-put board input) true)
                (do
                  (println (print-board (put-mark input board player)))
                  (recur
                    (is-win (put-mark input board player))
                    (if (= player "x") "o" "x")
                    (put-mark input board player)))
                (do
                  (println "you can't put here")
                  (recur
                    false
                    player
                    board))))))))))

(defn -main
  []
  (game))
