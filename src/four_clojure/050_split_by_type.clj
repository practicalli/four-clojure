(ns four-clojure.050-split-by-type)


;; #050 Split by Type
;;

;; Difficulty:	Medium
;; Topics:	seqs

;; Write a function which takes a sequence consisting of items with different types and splits them up into a set of homogeneous sub-sequences. The internal order of each sub-sequence should be maintained, but the sub-sequences themselves can be returned in any order (this is why 'set' is used in the test cases).

;; (= (set (__ [1 :a 2 :b 3 :c])) #{[1 2 3] [:a :b :c]})
;; (= (set (__ [:a "foo"  "bar" :b])) #{[:a :b] ["foo" "bar"]})
;; (= (set (__ [[1 2] :a [3 4] 5 6 :b])) #{[[1 2] [3 4]] [:a :b] [5 6]})


;; Deconstruct the problem
;;

;; As the problem states, so long as we return the groupings in order,
;; the order within each grouping is not important

;; All our test data has very specific types, so its unambiguous

;; REPL experiments
;;

;; Clojure does not have a group function, but auto-complete suggested `group-by` which does what I was looking for.


;; We could group by identity, this simply returns a duplicate of all the values though

(group-by identity [1 :a 2 :b 3 :c])


;; => {1 [1], :a [:a], 2 [2], :b [:b], 3 [3], :c [:c]}

;; The `class` and `type` functions return the underlying types used in Clojure.
;; Remember, although Clojure is a dynamically typed language, it is still strongly typed.
;; It just means you do not have to explicitly define the types in your code, as Clojure can
;; infer them.

(class "string")


;; => java.lang.String

(type "string")


;; => java.lang.String

(group-by type [1 :a 2 :b 3 :c])


;; => {java.lang.Long [1 2 3], clojure.lang.Keyword [:a :b :c]}


(group-by class [1 :a 2 :b 3 :c])


;; => {java.lang.Long [1 2 3], clojure.lang.Keyword [:a :b :c]}


;; This has create a map with the types as keys
;; but we only want the values.

;; `vals` is a handy function that just returns the values, without the keys

(vals {1 :a 2 :b 3 :c})


;; => (:a :b :c)


;; NOTE: you can also just get the keys using the `key` funciton.
;; and only specific keys and their values by using select-keys


;; Answers summary
;;

#_
(fn [data]
  (vals (group-by type data)))


;; or for a low golf score, use the short syntax for a funciton definition

#(vals (group-by type %))


;; Interesting solutions from 4Clojure top 100
;;

(comp vals (partial group-by type))


(comp (partial map second)
      (partial group-by class))


;; ->> typically used for sequence operations
#(->> % (group-by type) (vals))

#(map last (group-by type %))
