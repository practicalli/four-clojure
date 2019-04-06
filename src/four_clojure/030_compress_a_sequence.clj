(ns four-clojure.030-compress-a-sequence)

;; #30 compressing a sequence
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;; Difficulty:	Easy
;; Topics:	seqs

;; Write a function which removes consecutive duplicates from a sequence.

;; (= (apply str (__ "Leeeeeerrroyyy")) "Leroy")
;; (= (__ [1 1 2 3 3 2 2 3]) '(1 2 3 2 3))
;; (= (__ [[1 2] [1 2] [3 4] [1 2]]) '([1 2] [3 4] [1 2]))


;; Deconstruct the problem
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;; Ouch, this is a bit of a tricky one.
;; At first glance, this challenge seems to be quite imperative in that
;; you need to keep a mutating state (an accumulator) as you iterate
;; through each collection.
;; As we iterate through the collection, we put every element that is
;; different to the previous element into a new collection


;; REPL experiments
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;; loop through the original sequence
;; create a compressed-sequence as we loop

(loop [sequence            [1 1 2 3 3 2 2 3]
       compressed-sequence []]

  ;; if the sequence is empty, then we have completed all the iterations and should return the new sequence
  (if (empty? sequence)
    compressed-sequence

    ;; If there are still elements in the sequence, then iterate,
    ;; removing the first value from the sequence and adding it to the new sequence
    (recur (rest sequence)
           (if (= (last compressed-sequence)
                  (first sequence))
             compressed-sequence
             (conj compressed-sequence (first sequence))))))
;; => [1 2 3 2 3]



;; using partition
;;;;;;;;;;;;;;;;;;;;;;;;;

;; I thought that I could divide up the pattern
;; as a way to identify duplicates

;; partition by two creates pairs
;; but doesnt help with identifying duplicates
(partition 2 [1 1 2 3 3 2 2 3])
;; => ((1 1) (2 3) (3 2) (2 3))


;; partition by 2, but only stepping through by 1
;; creates all the possible sequence of pairs
;; its now easy to see the duplicates
(partition 2 1 [1 1 2 3 3 2 2 3])
;; => ((1 1) (1 2) (2 3) (3 3) (3 2) (2 2) (2 3))

;; We could remove partitions we do not want using the `filter` function
;; Here is a simple example of `filter`, to remove odd values
;;(filter odd? [1 2 3 4 5])
;; => (1 3 5)

;; Lets work out what sort of function we need for our filter
;; function to use with our filter
(fn [collection]
  (not= (first collection) (second collection)))

((fn [collection]
   (not= (first collection) (second collection)))
 [1 1])
;; => false



;; so if we filter out the duplicates then we should be close
(filter
  #(not= (first %) (second %))
  (partition 2 1 [1 1 2 3 3 2 2 3]))
;; => ((1 2) (2 3) (3 2) (2 3))



;; The structure is not quite right, so lets flatten it
(flatten
  (filter
    #(not= (first %) (second %))
    (partition 2 1 [1 1 2 3 3 2 2 3])))
;; => (1 2 2 3 3 2 2 3)

;; Oh, thats not right.  It feels close, but how to merge correctly?


;; changing track a little
;; lets use a variation of partition called partition-by
;; use the identity function to create a sequence for each value
(partition-by identity [1 1 2 3 3 2 2 3])
;; => ((1 1) (2) (3 3) (2 2) (3))

;; Then we can get the first value from each sequence
(map
 first
 (partition-by identity [1 1 2 3 3 2 2 3]))
;; => (1 2 3 2 3)

;; This works for the vector of vectors test too
(map
 first
 (partition-by identity [[1 2] [1 2] [3 4] [1 2]]))
;; => ([1 2] [3 4] [1 2])

;; But it doesnt quite work for a string, but its so close
(map
 first
 (partition-by identity "Leeeeeerrroyyy"))
;; => (\L \e \r \o \y)

;; so it seems we need to treat the results slightly differently
;; two results are a collection, one is a sequence of characters
;; If we test the result to see if it contains characters
;; then we can post-process the result, reducing it to a string
;; So lets put the result of our partition into a local name
;; then process the result if it contains chars.

((fn [pattern]
   (let [compressed (map
                     first
                     (partition-by identity pattern))]
     (if (char? (first compressed))
       (reduce str compressed)
       compressed)))
 "Leeeeeerrroyyy")
;; => "Leroy"

((fn [pattern]
   (let [compressed (map
                      first
                      (partition-by identity pattern))]
     (if (char? (first compressed))
       (reduce str compressed)
       compressed)))
 [1 1 2 3 3 2 2 3])
;; => (1 2 3 2 3)


((fn [pattern]
   (let [compressed (map
                     first
                     (partition-by identity pattern))]
     (if (char? (first compressed))
       (reduce str compressed)
       compressed)))
 [[1 2] [1 2] [3 4] [1 2]])
;; => ([1 2] [3 4] [1 2])

;; A check for a string type (sequence of characters) is not required as the first test includes
;; a conversion to a string, so we can just return the collection as is.

((fn [pattern]
   (let [compressed (map
                     first
                     (partition-by identity pattern))]
     compressed))
 [[1 2] [1 2] [3 4] [1 2]])
;; => ([1 2] [3 4] [1 2])


;; The let statement is now a bit redundant, so we can just remove it


((fn [pattern]
   (map
     first
     (partition-by identity pattern)))
 [[1 2] [1 2] [3 4] [1 2]])
;; => ([1 2] [3 4] [1 2])



;; Alternative: distinct
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; distinct will return a lazy sequence of the elements
;; of a collection with duplicates removed

(distinct [1 1])
;; => (1)

(distinct [1 2])
;; => (1 2)

(distinct [1 1 2 3 3 2 2 3])
;; => (1 2 3)

;; distinct doesnt solve the problem by itself as it removes too many values
;; however using it with partition-by identity as before, gives a closer result

(distinct (partition-by identity [1 1 2 3 3 2 2 3]))
;; => ((1 1) (2) (3 3) (2 2) (3))

;; We can take the first element as before, using map

(map
  first
  (distinct (partition-by identity [1 1 2 3 3 2 2 3])))
;; => (1 2 3 2 3)


;; if we just mapped distinct over the result of partition-by identity
;; we get the correct values, but not in the right shape

(map distinct (partition-by identity [1 1 2 3 3 2 2 3]))
;; => ((1) (2) (3) (2) (3))

;; if we could concatonate the results of map
;; then we would get the right shape of the results.
(concat
  (map distinct (partition-by identity [1 1 2 3 3 2 2 3])))
;; => ((1) (2) (3) (2) (3))

;; We need to apply concat to each of the elements in turn.
(apply concat
       (map distinct (partition-by identity [1 1 2 3 3 2 2 3])))
;; => (1 2 3 2 3)

;; as `apply concat` is a commonly used combination of functions,
;; `mapcat` provides a convenience function

(mapcat distinct (partition-by identity [1 1 2 3 3 2 2 3]))
;; => (1 2 3 2 3)

;; so we can define a function using mapcat and distinct
(fn [xs]
  (mapcat distinct (partition-by identity %)))


;; Answers summary
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;; Partitions a sequence `xs` by identity to group the same values together,
;; creating a sequence of unique values by taking the first value of each group.

(fn
  [xs]
  (map first (partition-by identity xs)))

;; example call with test data
((fn
   [xs]
   (map first (partition-by identity xs)))
 [1 1 2 3 3 2 2 3])
;; => (1 2 3 2 3)


;; Alternative to the above, using distinct and mapcat

(fn [xs]
  (mapcat distinct (partition-by identity %)))


;; Overthought answers
;; The check for a string is not needed as its managed by the code in the challenge.

(fn [pattern]
  (let [compressed (map
                     first
                     (partition-by identity pattern))]
    (if (char? (first compressed))
      (reduce str compressed)
      compressed)))
