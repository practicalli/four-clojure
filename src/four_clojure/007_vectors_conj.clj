(ns four-clojure.007-vectors-conj)


;; #007 Vectors: conj
;;

;; Difficulty:	Elementary
;; Topics:

;; When operating on a Vector, the conj function will return a new vector with one or more items "added" to the end.

;; (= __ (conj [1 2 3] 4))
;; (= __ (conj [1 2] 3 4))


;; Deconstruct the problem
;;

;; We have already touched on conj with vectors in challenge #005-lists-conj
;; This challenge reminds us that values are added to the end of a vector
;; and this is efficient because vectors have an index (like an array).

;; REPL experiments
;;

(conj [1 2 3] 4)


;; => [1 2 3 4]

(conj [1 2] 3 4)


;; => [1 2 3 4]


;; As lists are equal to vectors if their elements are the same, then
;; we could also use a list as the answer

(= (list 1 2 3 4) (conj [1 2 3] 4))


;; => true

(= '(1 2 3 4) (conj [1 2] 3 4))


;; => true


;; Answers summary
;;

;; Most readable answer
[1 2 3 4]
'(1 2 3 4)


;; Overthought answers

;; Least valuable answer
