(ns four-clojure.020-penultimate-element)


;; #020 Penultimate Element
;;

;; Difficulty:	Easy
;; Topics:	seqs

;; Write a function which returns the second to last element from a sequence.

;; (= (__ (list 1 2 3 4 5)) 4)
;; (= (__ ["a" "b" "c"]) "b")
;; (= (__ [[1 2] [3 4]]) [1 2])


;; Deconstruct the problem
;;

;; This exercise follows on nicely from #19 - last element.

;; In this exercise we do not have a restricted function, however, the function that would solve this problem does not exist in Clojure core.

;; The sequence functions by themselves do not give the answer we are looking for, but perhaps combining them will help.

(first [1 2 3 4 5])


;; => 1

(second [1 2 3 4 5])


;; => 2


;; but there is not a function called `second-to-last`
;; Like in #19, if we reverse the collection, then the `second` function will give us the correct answer.

(second (reverse [1 2 3 4 5]))


;; => 4


;; We can put this into a function so the argument is passed to the reverse function

(fn [arg]
  (second (reverse arg)))


;; or use the short form
#(second (reverse %))


;; Using the nth function
;; The `nth` function could be used as it gives us a way to access a collection by an index (even if the data structure type doesnt have an index).

;; In the 4Clojure exercise, the expression does not provide the size of the collection as an argument.
;; so we have to write a function that gets the size of the collection,
;; then we reduce that number by 2 so we get the second to last value using the `nth` function.
;; `nth` uses an index that starts at zero, so we need to minus the count of the elements by 2 (one for the index starting at zero, and one for the second to last element)

(nth [1 2 3 4 5] 3)


;; => 4

(fn [collection]
  (nth collection (- (count collection) 2)))


;; using the function definition short form
#(nth % (- (count %) 2))


;; Taking a functional composition approach we can instead use the `comp` function

(comp second reverse)


;; Using the first 4Clojure test we would have

((comp second reverse) (list 1 2 3 4 5))


;; => 4


;; and we could even use the low level loop and recur approach, but this doent really give us any advantage.

(loop [collection [1 2 3 4 5]]
  (if (= 2 (count collection))
    (first collection)
    (recur (rest collection))))


;; Answers summary
;;

;; Functional composition approach

(comp second reverse)


;; function definition
(fn [arg]
  (second (reverse arg)))


;; or the short form for function definition
#(second (reverse %))


;; Using `nth` function to access the collection via an index

(fn [collection]
  (nth collection (- (count collection) 2)))


;; using the function definition short form
#(nth % (- (count %) 2))


;; low level loop recur
(loop [collection [1 2 3 4 5]]
  (if (= 2 (count collection))
    (first collection)
    (recur (rest collection))))


;; interesting 4Clojure answers

;; `buttlast` is similar to `rest`, but drops the last element of the collection, rather than the first.
;; If you wanted to iterate backwards through a collection using a loop recur or recursive function,
;; then `buttlast` would be very useful.

(comp last butlast)
