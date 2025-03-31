(ns four-clojure.022-count-a-sequence)


;; #22 Count a Sequence
;;

;; Difficulty:	Easy
;; Topics:	seqs core-functions
;; Restricted: `count`

;; Write a function which returns the total number of elements in a sequence.

;; (= (__ '(1 2 3 3 1)) 5)
;; (= (__ "Hello World") 11)
;; (= (__ [[1 2] [3 4] [5 6]]) 3)
;; (= (__ '(13)) 1)
;; (= (__ '(:a :b :c)) 3)

;; Deconstruct the problem
;;

;; The `count` function would solve this exercise all by itself, but its restricted, so we have a chance to think about how the `count` function works.



;; REPL experiments
;;

;; using loop recur

(loop [collection [1 2 3 3 1]
       accumulator 0]
  (if (empty? collection)
    accumulator
    (recur (rest collection)
           (inc accumulator))))


;; => 5

;; Wrapping the loop recur in a function will allow us to use it for all our 4Clojure tests

(fn [xs]
  (loop [collection xs
         accumulator 0]
    (if (empty? collection)
      accumulator
      (recur (rest collection)
             (inc accumulator)))))


;; This worked for all of the 4Clojure tests in this exercise.
;; It is quite procedural and verbose though.

;; As well as a loop recur, you could also use a recursive function
;; This is a little cleaner code, but still a little low level.

(fn -count
  [xs]
  (if (empty? xs)
    0
    (inc (-count (rest xs)))))


;; Using reduce and a bit of lateral thinking
;;

;; Reducing a function over a collection will give a total value, based on the logic of that function.
;; for example

(reduce + [1 2 3 4 5])


;; => 15

;; the `reduce` function can take a value as an argument as well as a collection.
;; this value can be used to create a counter as we reduce over the collection

;; If a value is supplied to `reduce` it returns the result of applying the function to value as well as the first item in the collection.
;; The next iteration, the function is applied to the result of applying the function to the value and the 2nd item in the collection etc.
;; Once the collection is empty, reduce returns the result of applying the functions on the value.

;; so we can use a zero value as an argument to the `reduce` function for our counter
;; but we need to ignore the value of applying the function to the collection (we only care about the counter)

;; Using the `or` function will return the first value that is ether true or does not evaluate to anything other than itself.
;; Numbers evaluate to themselves, so if you use a number with or, it will just return the number.

(or 1 2)


;; => 1

;; using `or` function we can just return the value that we increment each time as we reduce through the collection

(reduce (fn [value element]
          (or (+ value 1)
              element))
        0
        [1 2 3 3 1])


;; => 5


;; we could also use the inc function rather than `(+ value 1)`
(reduce (fn [value element]
          (or (inc value)
              element))
        0
        [1 2 3 3 1])


;; we can also write this using the short form of a function definition

reduce #(or (inc %) %2) 0

(reduce #(or (inc %) %2) 0 '(1 2 3 3 1))


;; A bit more lateral thinking
;;

;; If all the values in our collection were the number one, then we could simply add all the numbers up and get the correct result.

;; So if we have a collection such as:
[1 1 1 1 1]


;; we can use the `reduce` function to add up the numbers and get the total number of elements in the collection.
(reduce + [1 1 1 1 1])


;; So how do we transform all the elements in a collection to 1, regardless of their current value.

;; `constantly` is a very interesting function that will return a specific value, no matter how its used.

(def always-one (constantly 1))


;; => #'four-clojure.022-count-a-sequence/always-one

(always-one)


;; => 1

(= 1 (always-one (* 2 2)))


;; => true

(str (always-one "is always one"))


;; => "1"

;; So we can map constantly to change all the elements in a collection
(map (constantly 1) '(4 5 6 7))


;; => (1 1 1 1)

(map (constantly 1) [[1 2] [3 4] [5 6]])


;; => (1 1 1)

;; map will treat the "hello" string as a collection of characters,
;; and constantly will return all those characters as the number 1
(map (constantly 1) "hello")


;; => (1 1 1 1 1)


;; This approach works for all our tests, so we can simply add up the elements to get the result.
(reduce +
        (map (constantly 1) [[1 2] [3 4] [5 6]]))


;; Now we just put that into a function
(fn [collection]
  (reduce +
          (map (constantly 1) collection)))


;; or its short form

#(reduce + (map (constantly 1) %))


;; Answers summary
;;

;; some nice lateral thinking

#(reduce + (map (constantly 1) %))


;; a reduce using or to just return a counter

reduce


(fn [value element]
  (or (inc value)
      element))


0


;; we can also write this using the short form of a function definition

reduce #(or (inc %) %2) 0


;; a low level loop recur approach

(fn [xs]
  (loop [collection xs
         accumulator 0]
    (if (empty? collection)
      accumulator
      (recur (rest collection)
             (inc accumulator)))))
