(ns four-clojure.47-contain-yourself)

;; #047 Contain Yourself
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;; Difficulty:	Easy
;; Topics:

;; The contains? function checks if a KEY is present in a given collection.
;; This often leads beginner clojurians to use it incorrectly with numerically indexed collections like vectors and lists.

;; (contains? #{4 5 6} __)
;; (contains? [1 1 1 1 1] __)
;; (contains? {4 :a 2 :b} __)
;; (not (contains? [1 2 4] __))

;; Deconstruct the problem
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;; The aim of this challenge is to help you become familiar with the `contains?` function.
;; As the description of this challenge states, most people get confused by the name
;; as it suggests it is checking a collection contains a value.
;; However, as the doc string states, its looking for a key.
;; The keys in a collection are usually different to the values in a collection (except for sets).

;; `contains?` is a predicate function, in that it returns a boolean answer, either true or false
;; It is convention to name the predicate functions with a question mark, `?`, at the end of their name.


;; Alternative - `some`
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; The `some` function is covered in 4Clojure challenge #48
;; http://www.4clojure.com/problem/48


;; REPL experiments
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;; Sets work as expected with the `contains?` function
;; as the keys in a set are actually the values

;; If the value is not in the set, then return false
(contains? #{4 5 6} 1)
;; => false

;; If the value is in the set, then return true
(contains? #{4 5 6} 4)
;; => true

;; Vectors trip developers up
;; The keys in a vector are its index

;; The value 1 gives the right result
(contains? [1 1 1 1 1] 1)
;; => true

;; but so does two, because there is a value at index 2,
;; it does not matter what the value is at index 2, so long as there is a value
(contains? [1 1 1 1 1] 2)
;; => true

;; As an index is separate from the contents of the collection,
;; we can get very interesting results.

;; Although the value 5 is in the collection,
;; there is no corresponding key as the index starts at zero.
(contains? [1 2 3 4 5] 5)
;; => false

;; to add to the confusion we can make it look like its working,
;; if the index is the same as the values then we get true
(contains? [0 1 2 3 4 5] 5)
;; => true


;; Maps arguably make the most sense as we are used to thinking about keys
;; as a map is a collection of key value pairs.

;; So if I key is in the collection, then its true.
(contains? {4 :a 2 :b} 4)


;; The not function inverts the boolean result,
;; so although contains? gives false, the not function turns that to true.
(not (contains? [1 2 4] 4))



;; Answers summary
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

4
