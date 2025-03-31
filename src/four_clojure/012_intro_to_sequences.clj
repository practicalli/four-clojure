(ns four-clojure.012-intro-to-sequences)


;; #012 Intro to Sequences
;;

;; Difficulty:	Elementary
;; Topics:

;; All Clojure collections support sequencing. You can operate on sequences with functions like first, second, and last.

;; Tests

;; (= __ (first '(3 2 1)))
;; (= __ (second [2 3 4]))
;; (= __ (last (list 1 2 3)))


;; Deconstruct the problem
;;

;; A simple exploration of data structures as sequences.

;; Notice that the sequence functions work on both lists and vectors in the same way.

(first '(3 2 1))


;; => 3

(second [2 3 4])


;; => 3

(last (list 1 2 3))


;; => 3

;; Answers summary
;;

3
