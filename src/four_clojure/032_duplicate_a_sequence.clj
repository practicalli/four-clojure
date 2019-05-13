(ns four-clojure.032-duplicate-a-sequence)

;; #032 Duplicate a Sequence
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;; Difficulty:	Easy
;; Topics:	seqs


;; Write a function which duplicates each element of a sequence.

;; Tests

;; (= (__ [1 2 3]) '(1 1 2 2 3 3))
;; (= (__ [:a :a :b :b]) '(:a :a :a :a :b :b :b :b))
;; (= (__ [[1 2] [3 4]]) '([1 2] [1 2] [3 4] [3 4]))

;; Deconstruct the problem
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;; Iterate over the collection and duplicate each value

;; Maintain the same structure as the original collection

;; REPL experiments
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;


;; Clojure does not have a core function called duplicate.
;; There is a function called `repeat` though

(repeat 2 1)
;; => (1 1)

;; so we could pass repeat over the collection

(map (fn [value] (repeat 2 value)) [1 2 3])
;; => ((1 1) (2 2) (3 3))


;; as we need an inline function anyway, we could forget about
;; `repeat` and just return two arguments
;; as there are two values returned, we need to put them into a collection

(map (fn [value] [value value]) [1 2 3])
;; => ([1 1] [2 2] [3 3])

;; we have the right values, but now have a different shape to the collection

;; flatten will remove the extra collections we created

(flatten (map (fn [value] [value value]) [1 2 3]))
;; => (1 1 2 2 3 3)

;; To put this into 4Clojure, we turn this into an inline function

(#(flatten (map (fn [value] [value value]) %)) [1 2 3])
;; => (1 1 2 2 3 3)

;; lets see how this works with the other tests

(#(flatten (map (fn [value] [value value]) %)) [:a :a :b :b])
;; => (:a :a :a :a :b :b :b :b)


(#(flatten (map (fn [value] [value value]) %)) [[1 2] [3 4]])
;; => (1 2 1 2 3 4 3 4)

;; this test fails as flatten just removes all the nested collections
;; flatten is quite a brutal function and is often an indication of
;; missing a better approach that does not require flatten

;; The merge function can be applied over the sub-collections
;; and give the right shape again
(#(apply merge (map (fn [value] [value value]) %)) [[1 2] [3 4]])
;; => [[1 2] [1 2] [[3 4] [3 4]]]


;; unfortunately the first two tests now fail

(#(apply merge (map (fn [value] [value value]) %)) [1 2 3])
;; => ([1 1] [2 2] [3 3])

;; reduce with merge is not much better
(#(reduce merge (map (fn [value] [value value]) %)) [1 2 3])
;; => [1 1 [2 2] [3 3]]


;; concatenate by itself doesn't work

(concat (map (fn [value] [value value]) [1 2 3]))
;; => ([1 1] [2 2] [3 3])

;; but if we apply concat over the resulting collection, it does work

(apply concat (map (fn [value] [value value]) [1 2 3]))
;; => (1 1 2 2 3 3)

(apply concat
       (map (fn [value] [value value]) [[1 2] [3 4]]))
;; => ([1 2] [1 2] [3 4] [3 4])


;; if we change from map to mapcat, then we can
;; concatenate the results as we create them

(mapcat (fn [value] [value value]) [1 2 3])
;; => (1 1 2 2 3 3)


(mapcat (fn [value] [value value]) [[1 2] [3 4]])
;; => ([1 2] [1 2] [3 4] [3 4])


;; interleave values

(clojure.repl/doc interleave)
;; clojure.core/interleave
;; ([] [c1] [c1 c2] [c1 c2 & colls])
;; Returns a lazy seq of the first item in each coll, then the second etc.

;; Interleave with one collection just returns the collection
(interleave [1 2 3])
;; => (1 2 3)

;; If we interleave with two collections then we get a good result
(interleave [1 2 3] [1 2 3])
;; => (1 1 2 2 3 3)

;; If we put interleave into a function and pass the same argument twice
;; then we can interleave it with itself

(fn [collection]
  (interleave collection collection))

((fn [collection]
   (interleave collection collection))
 [1 2 3])
;; => (1 1 2 2 3 3)


((fn [collection]
   (interleave collection collection))
 [[1 2] [3 4]])
;; => ([1 2] [1 2] [3 4] [3 4])



;; Answers summary
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;; Using interleave inside an inline function

(fn [collection]
  (interleave collection collection))

;; or if obsessing about your code golf score,
;; use the short form of a function
;; #(interleave % %)


;; Using mapcat

;; mapcat (fn [value] [value value])

;; or if you were obsessing about your code golf score
;; mapcat #(list % %)


;; Using mapcat with repeat

;; mapcat (partial repeat 2)
