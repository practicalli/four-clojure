(ns four-clojure.033-replicate-a-sequence)

;; #033 Replicate a Sequence
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;; Difficulty:	Easy
;; Topics:	seqs

;; Write a function which replicates each element of a sequence a variable number of times.
;; (= (__ [1 2 3] 2) '(1 1 2 2 3 3))
;; (= (__ [:a :b] 4) '(:a :a :a :a :b :b :b :b))
;; (= (__ [4 5 6] 1) '(4 5 6))
;; (= (__ [[1 2] [3 4]] 2) '([1 2] [1 2] [3 4] [3 4]))
;; (= (__ [44 33] 2) [44 44 33 33])


;; Deconstruct the problem
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;




;; REPL experiments
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;; `repeat` will create a given number of a value or a collection

(repeat 4 [1 2 3])

(repeat 2 [1 2 3])
;; => ([1 2 3] [1 2 3])

;; and when we have the right number of collections,
;; we can interleave those collections to get the right result.

(interleave [1 2 3] [1 2 3])
;; => (1 1 2 2 3 3)


;; due to the lazyness of these functions, we are not
;; quite getting the right result.

(interleave (repeat 2 [1 2 3]))

;; Using apply or reduce will make the evaluation eager.

(apply interleave (repeat 2 [1 2 3]))
;; => (1 1 2 2 3 3)

(reduce interleave (repeat 2 [1 2 3]))
;; => (1 1 2 2 3 3)


;; lets make this into an answer for 4Clojure

(fn [coll reps]
  (apply interleave (repeat reps coll)))

((fn [coll reps]
   (apply interleave (repeat reps coll)))
 [1 2 3] 2)
;; => (1 1 2 2 3 3)

((fn [coll reps]
   (apply interleave (repeat reps coll)))
 [:a :b] 4)
;; => (:a :a :a :a :b :b :b :b)


((fn [coll reps]
   (apply interleave (repeat reps coll)))
 [4 5 6] 1)
;; => (4 5 6)


#(apply interleave (repeat %2 %1))

;; this works for the first two tests

;; The third test is failing because it doesn't like interpose with one argument
;; This is strange as the code works in the local REPL for this project

((fn [coll reps]
   (apply interleave (repeat reps coll)))
 [[1 2] [3 4]] 2)
;; => ([1 2] [1 2] [3 4] [3 4])


;; Adding a condition for only 1 replication

(fn [coll reps]
  (if (= 1 reps)
    coll
    (apply interleave (repeat reps coll))))


((fn [coll reps]
   (if (= 1 reps)
     coll
     (apply interleave (repeat reps coll))))
 [4 5 6] 1)
;; => [4 5 6]




;; mapcat approach

;; Instead of interpose, we can use mapcat to concatenate values from all the
;; replicated collections

;; Actually `concat` by itself gives a partial answer.

(concat [1 2 3] [1 2 3])
;; => (1 2 3 1 2 3)


(mapcat (fn [coll] (repeat 2 coll)) [1 2 3])


(mapcat (partial repeat 2) [1 2 3])
;; => (1 1 2 2 3 3)

;; turning this into a 4Clojure answer

(fn [s n]
  (mapcat (partial repeat n) s))


((fn [s n]
   (mapcat (partial repeat n) s))
 [4 5 6] 1)
;; => (4 5 6)

;; Answers summary
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;


(fn [s n]
  (mapcat (partial repeat n) s))


#(mapcat (partial repeat %2 ) %)
