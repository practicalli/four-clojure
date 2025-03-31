(ns four-clojure.031-pack-a-sequence)


;; ---------------------------------------
;; #031 Pack a Sequence
;; Difficulty:	Easy
;; Topics:	seqs

;; Write a function which packs consecutive duplicates into sub-lists.

;; (= (__ [1 1 2 1 1 1 3 3]) '((1 1) (2) (1 1 1) (3 3)))

;; (= (__ [:a :a :b :b :c]) '((:a :a) (:b :b) (:c)))

;; (= (__ [[1 2] [1 2] [3 4]]) '(([1 2] [1 2]) ([3 4])))
;; ---------------------------------------

;; ---------------------------------------
;; Deconstruct the problem

;; we need to group numbers that are consecutively the same together

;; a low level approach would be a loop-recur that keeps track of the
;; previous value as you iterate through the collection.
;; Then you can compare the current value of the collection with the previous one.

;; A higher level approach it to group by identity
;; ---------------------------------------

;; ---------------------------------------
;; REPL experiments
;;
(require '[clojure.repl])

(clojure.repl/doc identity)


;; clojure.core/identity
;; ([x])
;; Returns its argument.

;; so we can use identity to help use group the numbers together,
;; as it always returns the original value of the argument

(clojure.repl/doc group-by)


;; clojure.core/group-by
;; ([f coll])
;; Returns a map of the elements of coll keyed by the result of
;; f on each element. The value at each key will be a vector of the
;; corresponding elements, in the order they appeared in coll.

(group-by identity [1 1 2 1 1 1 3 3])


;; => {1 [1 1 1 1 1], 2 [2], 3 [3 3]}

;; unfortunately group-by breaks the ordering of the original collection

(partition 3
           (range 0 9))


(partition-all 3
               (range 0 9))


(clojure.repl/doc partition-by)


;; clojure.core/partition-by
;; ([f] [f coll])
;; Applies f to each value in coll, splitting it each time f returns a
;; new value.  Returns a lazy seq of partitions.  Returns a stateful
;; transducer when no collection is provided.

;; Using identity with partition causes a new partition to be created
;; each time a new value is reached.

(partition-by identity [1 1 2 1 1 1 3 3])


;; => ((1 1) (2) (1 1 1) (3 3))

(partition-by identity [:a :a :b :b :c])


;; => ((:a :a) (:b :b) (:c))

(partition-by identity [[1 2] [1 2] [3 4]])


;; => (([1 2] [1 2]) ([3 4]))
;; ---------------------------------------

;; ---------------------------------------
;; Answers summary

;; partition-by identity

;; Code Golf Score: 20
;; ---------------------------------------
