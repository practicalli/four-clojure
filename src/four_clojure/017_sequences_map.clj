(ns four-clojure.017-sequences-map)

;; #17 Sequences: map
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;; Difficulty:	Elementary
;; Topics:

;; The map function takes two arguments: a function (f) and a sequence (s). Map returns a new sequence consisting of the result of applying f to each item of s. Do not confuse the map function with the map data structure.

;; (= __ (map #(+ % 5) '(1 2 3)))


;; Deconstruct the problem
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;; Discovering how to use the map function to process all the elements of a collection, using a given function.

;; REPL experiments
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;; map will work on a single collection, assuming that the function we use with `map` works with a single argument.
;; each element of the collection will be passed to the function in turn and the result put into a new collection.
;; once all the elements of the collection have been passed to the function, the resulting colleciton is returned.
;; The `map` function is responsible for building up the new collection.

(map inc [1 2 3])
;; => (2 3 4)

;; The map function can work over multiple collections, assuming the function can take multiple arguments
(map + [1 2 3] [3 4 5])
;; => (4 6 8)

(map + [1 2 3] [3 4 5] [6 7 8])
;; => (10 13 16)

;; The `+` function can take multiple arguments, so it can be mapped over multiple collections
(map + [1 2 3] [3 4 5] [6 7 8] [9 10 11])
;; => (19 23 27)


;; As well as using a function from Clojure core, or any other library,
;; we can write our own lambda function to be used by the `map` function.

;; our function simply needs to add five to the argument given

(fn [arg]
  (+ arg 5))

;; In the test this is written in the short form

#(+ % 5)

;; So when we map this function over the collection
;; the map function returns a new collection with each element increased by 5
(map #(+ % 5) '(1 2 3))
;; => (6 7 8)



;; Answers summary
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(6 7 8)
