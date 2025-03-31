(ns four-clojure.008-intro-to-sets)


;; #008 Intro to Sets
;;

;; Difficulty:	Elementary
;; Topics:

;; Sets are collections of unique values.

;; (= __ (set '(:a :a :b :c :c :c :c :d :d)))
;; (= __ (clojure.set/union #{:a :b :c} #{:b :c :d}))

;; Deconstruct the problem
;;

;; A simple introduction to creating sets, a collection of unique elements.

;; Clojure sets are not ordered, although there is an ordered-set function.

;; Sets are very useful to determine if a value is contained within a collection,
;; either with the ~contains~ function or just using the set as a function itself.

;; REPL experiments
;;

;; The set function will not include duplicates of values,
;; so once `:a` keyword is added to the set, another will not be added

(set '(:a :a :b :c :c :c :c :d :d))


;; => #{:c :b :d :a}

;; Ordering of values in a set is not generally considered important.
;; Sets are primarily focused on uniqueness of its values.
;; A value is either in a set or its is not.

;; The `clojure.set` namespace contains many functions useful for manipulating sets.
(require '[clojure.set])

(clojure.set/union #{:a :b :c} #{:b :c :d})


;; => #{:c :b :d :a}

;; When every you use a function to create a set, then duplicates should be eliminated by default.

;; using `#{}` form to create a set requires you to ensure no duplicate values are contained
;; it is therefore more common to use a specific set function.

;; Expanding on Sets a little more, with respect to the values it contains
;; Is a value within a set

;; If a value is in the set, then return the value
(#{1 2 3 4} 4)


;; => 4

;; If a value is not in a set, then return `nil`
(#{1 2 3 4} 5)


;; => nil

;; If you require a boolean answer for the set containing a value,
;; the `contains` function works as expected.

(contains? #{1 2 3 4} 4)


;; => true

(contains? #{1 2 3 4} 5)


;; => false

;; `contains` does not work as you may expect with vector collections

(contains? [1 2 3 4] 4)


;; => false

;; `contains?` refers to the index of the vector, not the values contained within
;; whilst there is no value at index 4 (vector index starts at 0),
;; the value 4 is at vector index 3, so true is returned.
(contains? [1 2 3 4] 3)


;; => true

;; Answers summary
;;

;; Most readable answer
#{:a :b :c :d}

(set [:a :b :c :d])


;; => #{:c :b :d :a}

;; Overthought answers

;; Least valuable answer
