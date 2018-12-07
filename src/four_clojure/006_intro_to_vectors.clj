(ns four-clojure.006-intro-to-vectors)

;; #006 Intro to Vectors
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;; Difficulty:	Elementary
;; Topics:

;; Vectors can be constructed several ways. You can compare them with lists.

;; Note: the brackets [] surrounding the blanks __ are part of the test case.

;; (= [__] (list :a :b :c) (vec '(:a :b :c)) (vector :a :b :c))


;; Deconstruct the problem
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;; There are three complete expressions, all of which create a collection.
;; The first complete expression returns a list containing :a :b and :c.
;; The other complete expressions return a vector with the same values.

;; As we saw in challenge #005, vectors and lists are equal if their elements are equal

;; So we need to return a list or vector for the incomplete expression.
;; As that expression is a vector already, then we just need to add the right values in the right order.

;; REPL experiments
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(list :a :b :c)
;; => (:a :b :c)

(vector :a :b :c)
;; => [:a :b :c]

(vec '(:a :b :c))
;; => [:a :b :c]

(= [:a :b :c] (list :a :b :c))
;; => true

(= [:a :b :c] (vector :a :b :c))
;; => true

(= [:a :b :c] (vec '(:a :b :c)))
;; => true

;; Answers summary
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;; Most readable answer

:a :b :c


;; Overthought answers

;; Least valuable answer
