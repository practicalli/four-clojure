(ns four-clojure.038-maximum-value)

;; #38 Maximum value
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;; Difficulty:	Easy
;; Topics:	core-functions
;; Restrictions: max max-key

;; Write a function which takes a variable number of parameters and returns the maximum value.

;; (= (__ 1 8 3 4) 8)
;; (= (__ 30 20) 30)
;; (= (__ 45 67 11) 67)

;; Deconstruct the problem
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;; we need to define a function that takes a variable number of integer arguments

;; our function should return the largest value from those arguments.

;; we cannot use the `max` or `max-key` functions to find the maximum value.

;; as our values are numeric, they can be sorted by their value and the maximum found


;; REPL experiments
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;; we cant pass individual values to sort
(sort 1 8 3 4)
;; clojure.lang.ArityException
;; Wrong number of args (4) passed to: core/sort

;; but we can sort a collection
(sort [1 8 3 4])
;; => (1 3 4 8)

;; with a sorted collection we can get the maximum number
;; as it will be the last one
(last (sort [1 8 3 4]))
;; => 8

;; we can put this behaviour into a function definition
(fn [& args]
  (last (sort [1 8 3 4])))

;; The [& args] part of the definition creates a local collection
;; of all the arguments bound to the name args


;; Call our function definition with arguments to test it
((fn [& args] (last (sort args))) 1 8 3 4)
;; => 8


;; Answers summary
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(fn [& args] (last (sort args)))
