(ns four-clojure.018-sequences-filter)

;; #18 - Sequences: filter
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;; Difficulty:	Elementary
;; Topics:

;; The filter function takes two arguments: a predicate function (f) and a sequence (s). Filter returns a new sequence consisting of all the items of s for which (f item) returns true.

;; (= __ (filter #(> % 5) '(3 4 5 6 7)))


;; Deconstruct the problem
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;; filter works in a similar way to the `map` function in that it uses a function over the elements of a collection.

;; The `filter` function only returns the values in the collection that return true from the given function.

(filter odd? [1 2 3 4 5])
;; => (1 3 5)

;; The function used in the 4Clojure test checks to see if a value is greater than 5
;; in its full form, the function is
(fn [arg]
  (> arg 5))

;; in the 4Clojure test, it uses the short form
#(> % 5)

;; So when we filter the collection with this function only values greater than 5 are returned
(filter #(> % 5) '(3 4 5 6 7))
;; => (6 7)


;; Answers summary
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(6 7)
