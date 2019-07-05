(ns four-clojure.054-partition-a-sequence)

;; #054 Partition a Sequence
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;; Difficulty:	Medium
;; Topics:	seqs core-functions
;; Special Restrictions: partition partition-all

;; Write a function which returns a sequence of lists of x items each. Lists of less than x items should not be returned.

;; (= (__ 3 (range 9)) '((0 1 2) (3 4 5) (6 7 8)))
;; (= (__ 2 (range 8)) '((0 1) (2 3) (4 5) (6 7)))
;; (= (__ 3 (range 8)) '((0 1 2) (3 4 5)))


;; Deconstruct the problem
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;; Write a function that takes a number and sequence as an argument

;; The sequence should be transformed into sub-sequences.  Each sequence should contain the number of items given by the first argument

;; If a sub-sequence does not contain as many items as given by the first argument, then that sequence should not be returned.

;; We will need to return these sub-sequences as a collection.

;; This is the behaviour of the `partition` function in `clojure.core`

(partition 3 (range 9))
;; => ((0 1 2) (3 4 5) (6 7 8))

(partition 2 (range 8))
;; => ((0 1) (2 3) (4 5) (6 7))

;; When the partition is not evenly divisible by the size of the sequence, partition drops the incomplete sub-sequence
(partition 3 (range 8))
;; => ((0 1 2) (3 4 5))

;; `partition-all` works in the same way, however, it will keep incomplete sub-sequences

(partition-all 3 (range 9))
;; => ((0 1 2) (3 4 5) (6 7 8))

(partition-all 2 (range 8))
;; => ((0 1) (2 3) (4 5) (6 7))

;; The `(6 7)` sub-sequence is returned this time, even though it does not have 3 items.
(partition-all 3 (range 8))
;; => ((0 1 2) (3 4 5) (6 7))
                                        ;


;; REPL experiments
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;


;; loop recur

;; iterate through the sequence
;; - check to see there are enough elements left
;; - take elements to make a new sub sequence of the right size size
;; - drop the taken elements and pass the remaining back into the loop

;; xs is a common Clojure abbreviation referring to a sequence

(fn [-sub-sequence-size
     xs]
  (loop [-sub-sequences []
         -sequence      xs]

    ;; check to see if we have too few elements left in the sequence
    (if (< (count -sequence) -sub-sequence-size)

      ;; Not enough elements left, return what we have found so far
      -sub-sequences

      ;; Still have a enough elements left in our sequence
      (recur

        ;; Add sub-sequence
        (conj -sub-sequences (take -sub-sequence-size -sequence))

        ;; new value for sequence - iterating by sub-sequence size
        (drop -sub-sequence-size -sequence)))))



;; logic of the `if` expression

(if (< (count (range 9)) 3)
  :stop
  :continue)

(if (< (count (range 1)) 3)
  :stop
  :continue)


;; How the conj in the recur expression works

(conj [] [0 1 2])

(conj (conj [] [0 1 2])
[3 4 5])

(conj
  (conj (conj [] [0 1 2])
        [3 4 5])
  [6 7 8])


;; lets test the function definition

((fn [-sub-sequence-size
     xs]
   (loop [-sub-sequences []
          -sequence      xs]

     ;; check to see if we have too few elements left in the sequence
     (if (< (count -sequence) -sub-sequence-size)

       ;; Not enough elements left, return what we have found so far
       -sub-sequences

       ;; Still have a enough elements left in our sequence
       (recur

         ;; Add sub-sequence
         (conj -sub-sequences (take -sub-sequence-size -sequence))

         ;; new value for sequence - iterating by sub-sequence size
         (drop -sub-sequence-size -sequence)))))

 3 (range 9))



;; recursive function




;; group-by
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;; `(group-by f coll)`
;; Returns a map of the elements of coll keyed by the result of
;; f on each element. The value at each key will be a vector of the
;; corresponding elements, in the order they appeared in coll.


(group-by odd? (range 9))
;; => {false [0 2 4 6 8], true [1 3 5 7]}


(group-by identity (range 9))
;; => {0 [0], 7 [7], 1 [1], 4 [4], 6 [6], 3 [3], 2 [2], 5 [5], 8 [8]}


;; `group-by` is not useful in solving this challenge


;; partition-by - similar issue to group-by
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;




;; take and drop approach
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(take 3 (range 9))
;; => (0 1 2)

(take 3 [1 2])


(drop 3 (range 9))

(take 3 (drop 3 (range 9)))

;; if we can identify start points for each sub-sequence,
;; then we can take just the number of elements for a sub-sequence
;; then repeat until the end.

[0 1 2 3 4 5 6 7 8]
|----| |---| |---|



;; The `range` function has an optional argument that is a step size
;; so if our sub-sequences should be size 3 and the sequence is size 9,
;; we can create the following

(range 0 9 3)
;; => (0 3 6)

;; This gives the starting point of each of the sub-sequences

;; We are given the step size for each 4Clojure tests




;; List comprehension - `for`
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;; `for` is a macro for iterating though a sequence and creating a new structure.

;; A classic example is a combination lock generator.
;; Imagine you have a three tumbler lock with each tumbler having the numbers 0 to 9
;; Using a for function you can generate all the combinations possible

(for [tumbler-1 (range 10)
      tumbler-2 (range 10)
      tumbler-3 (range 10)]
  [tumbler-1 tumbler-2 tumbler-3])


;; back to 4Clojure


(fn [sub-sequence-size xs]
  (for [start-point (range 0 (count xs) sub-sequence-size)]
    (str "Start point " start-point)))


((fn [sub-sequence-size xs]
   (for [start-point (range 0 (count xs) sub-sequence-size)]
     (str "Start point: " start-point)))
 3 (range 9))


(fn [sub-sequence-size xs]
  (for [start-point (range 0 (count xs) sub-sequence-size)]
    (take sub-sequence-size (drop start-point xs))))


((fn [sub-sequence-size xs]
   (for [start-point (range 0 (count xs) sub-sequence-size)]
     (take sub-sequence-size (drop start-point xs))))
 3 (range 9))


;; This does not work for the third test though

((fn [sub-sequence-size xs]
   (for [start-point (range 0 (count xs) sub-sequence-size)]
     (take sub-sequence-size (drop start-point xs))))
 3 (range 8))


;; We can calculate how many complete sub-sequences there are in the sequence

(quot (count (range 8)) 3)

;; Then we can run our list comprehension over just those complete start points

(fn [sub-sequence-size xs]
  (let [complete-sub-sequences (quot (count xs) sub-sequence-size)]
    (for [start-point (take complete-sub-sequences
                            (range 0 (count xs) sub-sequence-size))]
      (take sub-sequence-size
            (drop start-point xs)))))

((fn [sub-sequence-size xs]
   (let [complete-sub-sequences (quot (count xs) sub-sequence-size)]
     (for [start-point (take complete-sub-sequences
                             (range 0 (count xs) sub-sequence-size))]
       (take sub-sequence-size
             (drop start-point xs)))))
 3 (range 8))


;; Answers summary
 ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;(



(fn [n x]
  (let [h (quot (count x) n)]
    (for [i (take h (range 0 (count x) n))]
      (take n (drop i x)))))


;; loop recur solution

(fn [-sub-sequence-size xs]
  (loop [-sub-sequences []
         -sequence      xs]
    (if (< (count -sequence) -sub-sequence-size)
      -sub-sequences
      (recur
        (conj -sub-sequences (take -sub-sequence-size -sequence))
        (drop -sub-sequence-size -sequence)))))
