(ns four-clojure.063-group-a-sequence)

;; #063 Group a Sequence
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;; Difficulty:	Easy
;; Topics:	core-functions
;; Restriction: group-by

;; Given a function f and a sequence s, write a function which returns a map.

;; The keys should be the values of f applied to each item in s. The value at each key should be a vector of corresponding items in the order they appear in s.

;; (= (__ #(> % 5) [1 3 6 8]) {false [1 3], true [6 8]})
;; (= (__ #(apply / %) [[1 2] [2 4] [4 6] [3 6]]) {1/2 [[1 2] [2 4] [3 6]], 2/3 [[4 6]]})
;; (= (__ count [[1] [1 2] [3] [1 2 3] [2 3]]) {1 [[1] [3]], 2 [[1 2] [2 3]], 3 [[1 2 3]]})


;; Deconstruct the problem
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(= (group-by #(> % 5) [1 3 6 8]) {false [1 3], true [6 8]})

(= (group-by #(apply / %) [[1 2] [2 4] [4 6] [3 6]]) {1/2 [[1 2] [2 4] [3 6]], 2/3 [[4 6]]})

(= (group-by count [[1] [1 2] [3] [1 2 3] [2 3]]) {1 [[1] [3]], 2 [[1 2] [2 3]], 3 [[1 2 3]]})


;; We cant use group-by, so we could look at the source or similar functions

;; group-by suggests:
;; partition-by
;; frequencies


;; Go to the source
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;; https://github.com/clojure/clojure/blob/clojure-1.9.0/src/clj/clojure/core.clj#L7066

(defn group-by
  "Returns a map of the elements of coll keyed by the result of
  f on each element. The value at each key will be a vector of the
  corresponding elements, in the order they appeared in coll."
  {:added  "1.2"
   :static true}
  [f coll]
  (persistent!
    (reduce
      (fn [ret x]
        (let [k (f x)]
          (assoc! ret k (conj (get ret k []) x))))
      (transient {}) coll)))


;; the group-by function definition is a reducing function

;; it reduces over a collection, `coll`
;; and returns a hash-map, `ret`

;; it is assumed a transient hash-map is used for performance concerns.

;; The reducing function uses a lambda function to get a key and its values

;; The lambda function uses `let` to call the function originally passed as an argument with each value in the collection passed as an argument.

;; for each value in the collection a new value is associated with the key in the map `ret` using conj.
;; the `conj` function `get`s the existing values from `ret` that match the key
;; If there is no key found in `ret` then an empty vector is returned.


;; So we could simply take the implementation of group-by
;; simplify it a bit by removing the performance code
;; and past it into 4Clojure


(fn [f coll]
  (reduce
    (fn [ret x]
      (let [k (f x)]
        (assoc ret k (conj (get ret k []) x))))
    {} coll))


;; Success!

;; But did we learn enough??



;; Starting from scratch
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; If we can create a map for each of the values, where the key for the value is the result of the calling the function on that value.

;; Using `let` we get a new local value that is the result of calling the function passed as an arguments
;; to the value passed as an argument

;; Create a map data structure using `hash-map`

(fn [f value]
  (let [new-key (f value)]
    (hash-map new-key value)))

;; Or we can explicity put the values into a map using `{}`

(fn [f value]
  (let [new-key (f value)]
    {new-key value}))

;; Now call our function definition with the arguments from test 1

((fn [f value]
   (let [new-key (f value)]
     {new-key value}))
 #(> % 5) 1)
;; => {false 1}


;; Iterating over the collection
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; we need to iterate over our collection
;; ideally we should use one of the `clojure.core` functions

(map #(> % 5) [1 3 6 8])
;; => (false false true true)

(filter #(> % 5) [1 3 6 8])
;; => (6 8)

(remove #(> % 5) [1 3 6 8])
;; => (1 3)

(keep #(> % 5) [1 3 6 8])
;; => (false false true true)

(partition-by #(> % 5) [1 3 6 8])
;; => ((1 3) (6 8))

;; It doesnt seem there is anything that does exactly what we want
;; except of course `group-by`


(let [correct-results   (filter f value)
      incorrect-results (remove f value)]
  (hash-map false incorrect-results true correct-results))

;; define as a function so we can capture the arguments

(fn [f value]
  (let [correct-results (filter f value)
        incorrect-results (remove f value)]
    (hash-map false incorrect-results true correct-results)))

;; call the funciton with the first test values (this wont work for other tests, as its hard-coded to test the first)

(
 (fn [f value]
   (let [correct-results   (filter f value)
         incorrect-results (remove f value)]
     (hash-map false incorrect-results true correct-results)))
 #(> % 5) [1 3 6 8])
;; => {true (6 8), false (1 3)}


;; well we can create something that looks like it passes the first test, but its hard coded to only pass the first test



;; Ideas
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; idea
;; parition-by identity and then cons the results of passing the given function over partitioned data
;; cons the result of the function to the beginning of each partition.


;; idea
;; use list comprehension, `for` function
;; to create a sequence of maps
;; each map key is the value of applying the function to the current value
;; each value is the value placed into a vector (to match the structure of the required result)


;; `for` - list comprehension
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; f is the function passed as an argument
;; xs is the collection passed as an argument
;; x is the individual value pulled from the collection by `for`

(fn [f xs]
  (for [x xs] {(f x) [x]}))

;; Call the function with the arguments from the first test

((fn [f xs]
   (for [x xs] {(f x) [x]}))
 #(> % 5) [1 3 6 8])
;; => ({false [1]} {false [3]} {true [6]} {true [8]})


;; Then merge the result of the `for` function
;; giving a unique set of keys with a vector of the combined values
;; `merge-with` called with `into` will create the desired result


(fn [f xs]
  (apply merge-with into (for [x xs] {(f x) [x]})))

;; Call the function definition with arguments from the first 4Clojure testing
;; use debugging to see what is happening

((fn [f xs]
   (apply merge-with into (for [x xs] {(f x) [x]})))
 #(> % 5) [1 3 6 8])
;; => {false [1 3], true [6 8]}


((fn [f xs]
   (apply merge-with concat (for [x xs] {(f x) [x]})))
 #(> % 5) [1 3 6 8])
;; => {false (1 3), true (6 8)}


;; Answers summary
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;; A nice
(fn [f xs]
  (apply merge-with into (for [x xs] {(f x) [x]})))


(fn [f coll]
  (reduce
    (fn [ret x]
      (let [k (f x)]
        (assoc ret k (conj (get ret k []) x))))
    {} coll))



;; Interesting solutions

(fn [f s]
  (apply merge-with concat (map #(hash-map (f %1) [%1]) s)))


#(apply merge-with concat (map (fn [x] {(%1 x) [x]}) %2))


#(apply merge-with into (for [v %2] {(% v) [v]}))


(fn [f xs]
  (reduce (fn [acc v]
            (merge-with
              concat acc {(f v) [v]}))
          {}
          xs))

#(apply merge-with into
        (for [x %2]
          {(% x) [x]}))


(fn [f x]
  (let [part (partition-by f (sort-by f x))]
    (zipmap (map #(f (first %)) part)
            (map #(into (empty x) %) part))))


(comp
  #(zipmap (map ffirst %)
           (map (partial map peek) %))

  (partial partition-by first) sort (fn [f x]
                                      (map-indexed #(vector (f %2) %1 %2) x)))
