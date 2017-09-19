(ns clojure-playground.core-test
  (:require [clojure.test :refer :all]
            [clojure-playground.core :refer :all]))

(deftest initBoard-test
  (testing "should init board"
    (is (= (initBoard)
           [[\- \- \-]
            [\- \- \-]
            [\- \- \-]]))))

(deftest putMark-test
  (testing "putMark 2 0"
    (is (= (putMark "2 0"
             [[\- \- \-]
              [\- \- \-]
              [\- \- \-]]
             "x")
           [[\- \- "x"]
            [\- \- \-]
            [\- \- \-]])))
  (testing "putMark 0 2"
    (is (= (putMark "0 2"
             [[\- \- \-]
              [\- \- \-]
              [\- \- \-]]
             "o")
           [[\- \- \-]
            [\- \- \-]
            ["o" \- \-]]))))

(deftest isWin-test
  (testing "isWin false"
    (is (= (isWin
             [[\- \- \-]
              [\- \- \-]
              [\- \- \-]])
           false)))
  (testing "isWin true"
    (is (= (isWin
             [[\- \- \-]
              ["x" "x" "x"]
              [\- \- \-]])
           true)))
  (testing "isWin true"
    (is (= (isWin
             [[\- \- "x"]
              [\- "x" \-]
              ["x" \- \-]])
           true)))
  (testing "isWin true"
    (is (= (isWin
             [["x" \- \-]
              ["x" \- \-]
              ["x" \- \-]])
           true)))
  (testing "isWin true"
    (is (= (isWin
             [["o" \- \-]
              ["o" \- \-]
              ["o" \- \-]])
           true)))
  (testing "isWin false"
    (is (= (isWin
             [[\- \- "x"]
              [\- "x" \-]
              ["o" \- \-]])
           false))))

(deftest isDraw-test
  (testing "isDraw true"
    (is (= (isDraw
             [["x" "o" "x"]
              ["o" "x" "x"]
              ["o" "x" "o"]])
           true)))
  (testing "isDraw false"
    (is (= (isDraw
             [["x" \- "x"]
              ["o" "x" "x"]
              ["o" "x" "o"]])
           false)))
  (testing "isDraw true"
    (is (= (isDraw
             [["o" "x" "o"]
              ["x" "x" "o"]
              ["x" "o" "x"]])
           true)))
  (testing "isDraw false"
    (is (= (isDraw
             [["x" "x" \-]
              ["o" "x" "x"]
              [\- "x" "o"]])
           false)))
  (testing "isDraw false"
    (is (= (isDraw
             [[\- \- \-]
              ["x" "x" "o"]
              ["x" "o" "x"]])
           false)))
  (testing "isDraw false"
    (is (= (isDraw
             [[\- "o" "x"]
              ["x" \- "o"]
              ["x" "o" \-]])
           false))))

(deftest isAbleToPut-test
  (testing "isAbleToPut true"
    (is (= (isAbleToPut
             [[\- "x" \-]
              ["o" "x" "x"]
              [\- "x" "o"]]
             "0 0")
           true)))
  (testing "isAbleToPut false"
    (is (= (isAbleToPut
             [["x" "x" \-]
              ["o" "x" "x"]
              [\- "x" "o"]]
             "0 0")
           false)))
  (testing "isAbleToPut true"
    (is (= (isAbleToPut
             [["x" "x" \-]
              ["o" "x" "x"]
              ["o" "x" "o"]]
             "2 0")
           true)))
  (testing "isAbleToPut false"
    (is (= (isAbleToPut
             [["x" "x" \-]
              ["o" "x" "x"]
              ["o" "x" "o"]]
             "0 2")
           false))))
