(ns four-clojure.053-longest-increasing-sub-seq)

;; #053 Longest increasing sub-sequence
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;; Difficulty:	Hard
;; Topics:	seqs

;; Given a vector of integers, find the longest consecutive sub-sequence of increasing numbers. If two sub-sequences have the same length, use the one that occurs first. An increasing sub-sequence must have a length of 2 or greater to qualify.

;; (= (__ [1 0 1 2 3 0 4 5]) [0 1 2 3])
;; (= (__ [5 6 1 3 2 7]) [5 6])
;; (= (__ [2 3 3 4 5]) [3 4 5])
;; (= (__ [7 6 5 4]) [])


;; Deconstruct the problem
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;; This challenge took quite a bit of thinking about as its quite an imperative problem, which can easily lead to an imperative solution.

;; Initial reading of the challenge suggests we iterate through the given collection values, keeping a record of each sub sequence as we go.  This can easily lead to use state and I was tempted at one point to use an atom.

;; By transforming the data to more relevantly shaped structures we can used simpler functions (algorithms) to create the desired results.


;; REPL experiments
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;; Taking a low abstraction approach with loop and recur

;; Our function takes the collection to be processed as an argument.

((fn longest-sub [collection]
   (loop
       ;; `temporary-sub` is the current sub-sequence being processed
       ;; `sub-collection` will contain the sub-sequences found
       ;; `remaining-collection` is used to iterate through the collection
       [temporary-sub        []
        sub-collection       []
        remaining-collection collection]

     (if (empty? remaining-collection)

       ;; If no more numbers in the collection, return the current sub-collection
       sub-collection

       ;; else if there are still numbers in the collection
       (recur
        ;; temporary-sub for building a sequence of consecutive numbers
        (cond
          (=    temporary-sub              [])                           [(first remaining-collection)]
          (=    (inc (last temporary-sub)) (first remaining-collection)) (conj temporary-sub (first remaining-collection))
          (not= (inc (last temporary-sub)) (first remaining-collection)) [(first remaining-collection)]

          ;; sub-collection holds the largest sequence found so far
          (if (> (count temporary-sub) (count sub-collection))
            temporary-sub
            sub-collection))

        ;; remaining collection
        (rest remaining-collection)))))
 [1 0 1 2 3 0 4 5])
;; => [0 1 2 3]

;; passes the first two tests, but fails the third test.  Returns [2 3] for the third test, instead of [3 4 5] because we drop out of the loop without checking if the temporary-sub is larger than the sub-collection.


;; works for all tests except the last one...
((fn longest-sub [collection]
   (loop
       ;; `temporary-sub` is the current sub-sequence being processed
       ;; `sub-collection` will contain the sub-sequences found
       ;; `remaining-collection` is used to iterate through the collection
       [temporary-sub        []
        sub-collection       []
        remaining-collection collection]

     ;; If no more numbers in the collection, return the current sub-collection
     (if (empty? remaining-collection)

       ;; As the temporary-sub value doesnt get compared until the recur call,
       ;; we need to evaluate which is bigger when processing the last value from the original collection.
       (if (> (count temporary-sub) (count sub-collection))
         temporary-sub
         sub-collection)

       ;; else if there are still numbers in the collection
       (recur
        ;; temporary-sub for building a sequence of consecutive numbers
        (cond
          (=    temporary-sub              [])                           [(first remaining-collection)]
          (=    (inc (last temporary-sub)) (first remaining-collection)) (conj temporary-sub (first remaining-collection))
          (not= (inc (last temporary-sub)) (first remaining-collection)) [(first remaining-collection)])

        ;; sub-collection holds the largest sequence found so far
        (if (> (count temporary-sub) (count sub-collection))
          temporary-sub
          sub-collection)

        ;; remaining collection
        (rest remaining-collection)))))
 [2 3 3 4 5])
;; => [3 4 5]


((fn longest-sub [collection]
   (loop
       ;; `temporary-sub` is the current sub-sequence being processed
       ;; `sub-collection` will contain the sub-sequences found
       ;; `remaining-collection` is used to iterate through the collection
       [temporary-sub        []
        sub-collection       []
        remaining-collection collection]

     ;; If no more numbers in the collection, return the current sub-collection
     (if (empty? remaining-collection)

       ;; As the temporary-sub value doesnt get compared until the recur call,
       ;; we need to evaluate which is bigger when processing the last value from the original collection.
       ;; If all the sub-sequences are the same lenght, then we need to return an empty collection (as in the final test)
       (cond
         (> (count temporary-sub) (count sub-collection)) temporary-sub
         (> (count sub-collection) (count temporary-sub)) sub-collection
         (= 1 (count temporary-sub) (count sub-collection)) [])

       ;; else if there are still numbers in the collection
       (recur
        ;; temporary-sub for building a sequence of consecutive numbers
        (cond
          (=    temporary-sub              [])                           [(first remaining-collection)]
          (=    (inc (last temporary-sub)) (first remaining-collection)) (conj temporary-sub (first remaining-collection))
          (not= (inc (last temporary-sub)) (first remaining-collection)) [(first remaining-collection)])

        ;; sub-collection holds the largest sequence found so far
        (if (> (count temporary-sub) (count sub-collection))
          temporary-sub
          sub-collection)

        ;; remaining collection
        (rest remaining-collection)))))
 [7 6 5 4])
;; => []


;; Whew... that was quite a lot of work and a lot of code for someone to maintain.

;; Hopefully we can find some abstractions that will make this code much simpler to work with

;; thinking through the algorithm again, a more functional approach would be something like:

(let [sequence [1 0 1 2 3 0 4 5]]
  ;; divide into a growing sequence of numbers [[1] [1 0] [1 0 1 2]]
  ;; sort-by count
  ;; reverse
  ;; first
  )




;; Solving #53 with partitioning and filters
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Can we group the numbers together so they are grouped sequentially?

;; Lets partition the first collection
;; Partition into pairs, one step at a time,
;; so each pair of numbers can be compared

(partition 2 1 [1 0 1 2 3 0 4 5])
;; => ((1 0) (0 1) (1 2) (2 3) (3 0) (0 4) (4 5))

;; We have a simple partition of the collection
;; Now we can partition again using a function
;; that compares values, so that we can again group by an increasing sequence

;; We want to see which pair has the second number higher than the first
;; so greater-than function is a simple test.
;; We need to apply the greater-than function to each pair in the partitioned collection
;; As each pair is in a collection then we need to map over each vector of tha tcollection

(map #(apply < %) [[1 0] [0 1] [1 2]])
;; => (false true true)

;; So this gives us a promising looking function to partition with

(partition-by #(apply < %)
              (partition 2 1 [1 0 1 2 3 0 4 5]))
;; (((1 0))
;; ((0 1) (1 2) (2 3)) ((3 0))
;; ((0 4) (4 5)))

;; we now have a collection that is partitioned where ever
;; the second number of a piair was not greater than the first.

;; We still have one pair that does not increase,
;; so we can filter that out using another function

(filter
 (fn [[[number1 number2]]]
   (< number1 number2))
 (partition-by #(apply < %)
               (partition 2 1 [1 0 1 2 3 0 4 5])))
;; => (((0 1) (1 2) (2 3))
;;     ((0 4) (4 5)))

;; Now we only have two collections of pairs,
;; each of which increases

;; So we can just sort these collections by count,
;; so the biggest collection is first
;; then get the first one.

(sort-by count >
         (filter
          (fn [[[number1 number2]]]
            (< number1 number2))
          (partition-by #(apply < %)
                        (partition 2 1 [1 0 1 2 3 0 4 5]))))
;; => (((0 1) (1 2) (2 3)) ((0 4) (4 5)))

;; No change in the order of elements returned in this example,
;; but it ensures we have the correct order.
;; So lets get the first element

(first
 (sort-by count >
          (filter
           (fn [[[number1 number2]]]
             (< number1 number2))
           (partition-by #(apply < %)
                         (partition 2 1 [1 0 1 2 3 0 4 5])))))
;; => ((0 1) (1 2) (2 3))


;; So now we just need to combine our remaining collection to get the right shape.

;; We cant just flatten, as we have extra values
(flatten [[0 1] [1 2] [2 3]])
;; => (0 1 1 2 2 3)


;; If we take the first collection that contains a pair
;; and add the last value from each of the following pairs
;; then we will get the right result

(concat
 (first [[0 1] [1 2] [2 3]])
 (map last (rest [[0 1] [1 2] [2 3]])))
;; => (0 1 2 3)


;; It would lead to a lot of repeated code to fit this around the function so far
;; so using the let function will help


((fn [coll]
   (let [partitioned (partition-by #(apply < %) (partition 2 1 coll))
         filtered    (filter (fn [[[x1 x2]]] (< x1 x2)) partitioned)
         sorted      (first (sort-by count > filtered))]
     (concat (first sorted) (map last (rest sorted)))))
 [1 0 1 2 3 0 4 5])
;; => (0 1 2 3)



;; Answers summary
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;; Partition and Filter approach (medium abstraction)

(fn [coll]
  (let [a (partition-by #(apply < %) (partition 2 1 coll))
        b (filter (fn [[[x1 x2]]] (< x1 x2)) a)
        c (first (sort-by count > b))]
    (concat (first c) (map last (rest c)))))



;; loop recur approach (low level of abstraction)

(fn longest-sub [collection]
   (loop
       ;; `temporary-sub` is the current sub-sequence being processed
       ;; `sub-collection` will contain the sub-sequences found
       ;; `remaining-collection` is used to iterate through the collection
       [temporary-sub        []
        sub-collection       []
        remaining-collection collection]

     ;; If no more numbers in the collection, return the current sub-collection
     (if (empty? remaining-collection)

       ;; As the temporary-sub value doesnt get compared until the recur call,
       ;; we need to evaluate which is bigger when processing the last value from the original collection.
       ;; If all the sub-sequences are the same lenght, then we need to return an empty collection (as in the final test)
       (cond
         (> (count temporary-sub) (count sub-collection)) temporary-sub
         (> (count sub-collection) (count temporary-sub)) sub-collection
         (= 1 (count temporary-sub) (count sub-collection)) [])

       ;; else if there are still numbers in the collection
       (recur
        ;; temporary-sub for building a sequence of consecutive numbers
        (cond
          (=    temporary-sub              [])                           [(first remaining-collection)]
          (=    (inc (last temporary-sub)) (first remaining-collection)) (conj temporary-sub (first remaining-collection))
          (not= (inc (last temporary-sub)) (first remaining-collection)) [(first remaining-collection)])

        ;; sub-collection holds the largest sequence found so far
        (if (> (count temporary-sub) (count sub-collection))
          temporary-sub
          sub-collection)

        ;; remaining collection
        (rest remaining-collection)))))



;; Interesting 4Clojure answers

(fn [s]
  (->>
   (for [a (range (count s))
         b (range (inc a) (count s))]
     (subvec s a (inc b)))
   (filter #(apply < %))
   (sort-by count >)
   first
   vec))
