(ns four-clojure.064-intro-to-reduce)


;; #64 Intro to Reduce
;;

;; Difficulty:	Elementary
;; Topics:	seqs


;; Reduce takes a 2 argument function and an optional starting value.
;; It then applies the function to the first 2 items in the sequence
;; (or the starting value and the first element of the sequence).
;; In the next iteration the function will be called on the previous return value
;; and the next item from the sequence,
;; thus reducing the entire collection to one value.

;; Don't worry, it's not as complicated as it sounds.


;; (= 15 (reduce __ [1 2 3 4 5]))
;; (=  0 (reduce __ []))
;; (=  6 (reduce __ 1 [2 3]))


;; Deconstruct the problem
;;

;; With some simple mathematics, we can solve the 4Clojure tests fairly easily.

;; reduce calls a function on each of the values in the sequence.

;; so if we want the result `15` from the numbers `[1 2 3 4 5]`
;; then we can use the `+` function with reduce


(= 15 (reduce + [1 2 3 4 5]))


;; => true

;; We can see the single value that this produces
(reduce + [1 2 3 4 5])


;; => 15

;; and we can see how its working it all out using `reductions`

(reductions + [1 2 3 4 5])


;; => (1 3 6 10 15)


(reduce + [1 2 3 4 5])


;; translates to:

(+ (+ (+ (+ 1 2) 3) 4) 5)

(apply + [1 2 3 4 5])

(+ 1 2 3 4 5)


;; References
;;
;; https://clojuredocs.org/clojure.core/reduce
;; https://github.com/clojure/clojure/blob/clojure-1.9.0/src/clj/clojure/core.clj#L6730



;; Answers summary
;;

;; +
