(ns four-clojure.036-let-it-be)


;; ---------------------------------------
;; #036 Let it Be
;; Difficulty:	Elementary
;; Topics:	math syntax

;; Can you bind x, y, and z so that these are all true?

;; (= 10 (let __ (+ x y)))
;; (= 4 (let __ (+ y z)))
;; (= 1 (let __ z))
;; ---------------------------------------

;; ---------------------------------------
;; Deconstruct the problem
;;

;; Delving into let binding in a little more detail

;; http://clojuredocs.org/clojure.core/let

;; Related 4Clojure exercises

;; https://github.com/jr0cket/four-clojure/blob/master/src/four_clojure/030_compress_a_sequence.clj#L137
;; https://github.com/jr0cket/four-clojure/blob/master/src/four_clojure/051_advanced_destructuring.clj
;; ---------------------------------------

;; ---------------------------------------
;; REPL experiments
;;

;; (= 10 (let __ (+ x y)))
;; (= 4 (let __ (+ y z)))
;; (= 1 (let __ z))

;; To figure out the result, we can simply evaluate
;; the let expressions and see what value is returned.

;; Starting with a guess at the values for x and y
(let [x 5
      y 5]
  (+ x y))


;; => 10

;; The last test shows that z has a specific value
;; as its the only name bound.

(let [z 1]
  z)


;; => 1

;; As we now know z, we can work out y with the second test
(let [y 3
      z 1]
  (+ y z))


;; => 4

;; finally we know all three values.

(let [x 7
      y 3]
  (+ x y))


;; => 10
;; ---------------------------------------

;; ---------------------------------------
;; So what do we use as the answer?

;; The bound values are valid even if they are not used
;; in the rest of the let function.
;; So we can simply include the binding of all three names
;; to values and each of the tests will use which ever
;; name bindings they need.

(let [x 7
      y 3
      z 1])


;; ---------------------------------------

;; ---------------------------------------
;; Answers summary

;; [x 7
;;  y 3
;;  z 1]
;; ---------------------------------------
