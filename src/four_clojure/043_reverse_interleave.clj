(ns four-clojure.043-reverse-interleave)

;; 43 - Reverse interleaving
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;; Difficulty:	Medium
;; Topics:	seqs

;; Write a function which reverses the interleave process into x number of subsequences.

;; (= (__ [1 2 3 4 5 6] 2) '((1 3 5) (2 4 6)))
;; (= (__ (range 9) 3) '((0 3 6) (1 4 7) (2 5 8)))
;; (= (__ (range 10) 5) '((0 5) (1 6) (2 7) (3 8) (4 9)))


;; Deconstruct the problem
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;; Given a collection, return that collection as the number of sub-sequences

;; All the given numbers divide equally, so we dont need to manage uneven sub-sequences

;; Values from each sub-sequence should be interleaved

;; REPL experiments
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;


;; The challenge says reverse interleave,
;; so lets see if there is an interleave funciton


;; https://clojuredocs.org/clojure.core/interleave

;; clojure.core/interleave
;; []
;; [c1]
;; [c1 c2]
;; [c1 c2 & colls]
;; Added in 1.0
;; Returns a lazy seq of the first item in each coll, then the second etc.


(interleave)

(interleave [1 2])

(interleave [1 2] [3 4])

(interleave [1 2] [3 4] [5 6])


;; Change the shape of our collection so it works with interleave


;; partition
;; partition will split a collection into sub-sequences

(partition 2 [1 2 3 4 5 6])


;; This gives us the right shape for our data for interleave,
;; although its actually wrapped in a sequence.
;; the sequence makes it a single argument to interleave

;; we want this
(interleave [1 2] [3 4] [5 6])

;; but actually we have this instead

(interleave '([1 2] [3 4] [5 6]))



;; interleave will not work on the sequence returned by parttion


(map interleave (partition 3 [1 2 3 4 5 6]))


(apply interleave (partition 3 [1 2 3 4 5 6]))


;; We could take a more manual approach, using `first` and `second` to get each sub-sequence

(map first (partition 2 [1 2 3 4 5 6]))
;; => (1 3 5)

(map second (partition 2 [1 2 3 4 5 6]))
;; => (2 4 6)


;; So we can get the first and second parts separately, but what about at the same time

(let [values (partition 2 [1 2 3 4 5 6])
      one    (map first values)
      two    (map second values)]
  [one two])

;; or we can use a `for` expression instead of `map` to create the values in parallel

(for [values (partition 2 [1 2 3 4 5 6])
      :let   [one (first values)
              two (second values)]]
  [one two])


(for [values (partition 2 [1 2 3 4 5 6])
      :let   [one (first values)
              two (second values)]]
  (interleave [one two]))



(let [values (partition 2 [1 2 3 4 5 6])
      one    (map first values)
      two    (map second values)]
  (conj one two))

(let [values (partition 2 [1 2 3 4 5 6])
      one    (map first values)
      two    (map second values)]
  (concat one two))


(let [values (partition 2 [1 2 3 4 5 6])
      one    (map first values)
      two    (map second values)]
  (list one two))

;; so this passes for the first test, but other tests have more partitions


;; Using `list`
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;; In the last expression we used a list, perhaps this gives us another approach

;; if we can map the list function over each of the partitions
;; then we can can generate the right values

(map list '(1 2) '(3 4) '(5 6))



;; this is just like adding values from collections together with `+`
(map + '(1 2) '(3 4) '(5 6))



;; we cant do this directly because partition returns
;; a single list containing partitions
;; so we dont get the right answer

(map list (partition 2 [1 2 3 4 5 6]))



;; applying the list is not quite right either
(apply list (partition 2 [1 2 3 4 5 6]))




;; however if we use the way that apply works, then we can
;; create an expression with map list that
;; has the right shape of arguments (individual collections)

(apply map list (partition 2 [1 2 3 4 5 6]))


;; To write this for our 4Clojure challenge

(fn [collection number]
  (apply map list (partition number collection)))


;; test it works using the repl

((fn [collection number]
   (apply map list (partition number collection)))
 [1 2 3 4 5 6] 2)



;; Reviewing how apply works
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;


;; lets take a simple example
(apply + '(1 2 3))

;; this is the same as inserting the `+` function inside of the collection
(+ 1 2 3)


;; so for the 4Clojure problem

(apply map list (partition 2 [1 2 3 4 5 6]))


;; if we just evaluate the `partition` part of the expression

(apply map list '('(1 2) '(3 4) '(5 6)))


;; If we transform the code in the way that apply works

(map list '(1 2) '(3 4) '(5 6))


;; apply strips out the outer list of the result from partition
;; and make an expression that looks like this

;; https://stackoverflow.com/questions/3153396/clojure-reduce-vs-apply


;; Answers summary
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(fn [collection number]
  (apply map list (partition number collection)))

#(apply map list (partition %2 %1))






;; off at a tangent
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(map (partial interleave) [[1 2] [3 4] [5 6]])

(map #(interleave %) [[1 2 3] [4 5 6]])


;; Splitting the collection into sub-sequences

;; We can find the right number to partition the collection by a little calculation
;; count the elements in a collection
;; divide the count by the number given, just taking the quotent (whole number of the division)
(quot 6 2)


(fn [collection number]
  (partition
    (quot (count collection) number)
    collection))

;; try this out in the REPL

((fn [collection number]
   (partition
     (quot (count collection) number)
     collection))
 [1 2 3 4 5 6] 2)


((fn [collection number]
   (partition
     (quot (count collection) number)
     collection))
 [1 2 3 4 5 6] 4)


(partition 3 [1 2 3 4 5 6])
;; => ((1 2 3) (4 5 6))


(interleave (partition 3 [1 2 3 4 5 6]))
;; => ((1 2 3) (4 5 6))
