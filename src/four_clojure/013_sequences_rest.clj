(ns four-clojure.013-sequences-rest)

;; #013 - Sequences - rest
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;; Difficulty:	Elementary
;; Topics:

;; The rest function will return all the items of a sequence except the first.

;; (= __ (rest [10 20 30 40]))


;; Deconstruct the problem
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;; More functions focused on sequences, this time the `rest` function.

(rest [10 20 30 40])
;; => (20 30 40)

;; As we can see the `rest` function returns everything but the first element of the sequence.

;; The `rest` function is very useful for iterating through a collection,
;; By passing the rest of a collection back through a loop recur,
;; or back to a function that calls itself,
;; we can process all the elements in a collection without having to keep a track of our position in the collection.


;; Answers summary
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(20 30 40)
