(ns four-clojure.019-last-element)

;; #019 Last Element
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;; Difficulty:	Easy
;; Topics:	seqs core-functions
;; Restriction: `last`

;; Write a function which returns the last element in a sequence.

;; (= (__ [1 2 3 4 5]) 5)
;; (= (__ '(5 4 3)) 3)
;; (= (__ ["b" "c" "d"]) "d")


;; Deconstruct the problem
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;; This exercise would be easy to solve using the `last` function, however, to help us learn about the last function 4Clojure has prevented us from using it.

;; We can see the docs and source code for the `last` function to help, but lets think about how to solve this first.

;; Taking an imperative approach, you would have a loop recur that iterated through

;; REPL experiments
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;


;; A simple loop recur to solve the challenge
(loop [collection [1 2 3 4 5]]
  (if (= 1 (count collection))
    (first collection)
    (recur (rest collection))))
;; => 5

;; The above code works but its quite a low level of abstraction and feels fairly procedural and prescriptive.

;; Using the sequence functions we should be able to do something more functional

(first [1 2 3 4 5])
;; => 1

(last [1 2 3 4 5])
;; => 5

;; unfortunately we cannot use the `last` function in our 4Clojure answer.
;; However, if we reverse the sequence, then we can use the `first` function to get the right answer

(reverse [1 2 3 4 5])
;; => (5 4 3 2 1)

(first
 (reverse [1 2 3 4 5]))
;; => 5

;; This is a much simpler approach than the loop recur and more functional.
;; As we are also using functions from Clojure core namespace, then there is no maintenance required.

;; We need to put this code into a function, so we can pass the collection as an argument to reverse.

(fn [arg]
  (first (reverse arg)))

;; calling this function with an argument gives the result for that specific collection

((fn [arg]
   (first (reverse arg)))
 [1 2 3 4 5])
;; => 5


;; short form of function definition

#(first (reverse %))

;; calling the short form

(#(first (reverse %)) [1 2 3 4 5])
;; => 5


;; using higher order functions in ClojureScript

;; rather than write our own function definition, we can use higher order function approch and compose our functions together

;; `comp` is a function that takes any number of functions and composes them together.
;; `comp` returns a fn that is the composition of those fns.  The returned fn takes a variable number of args,
;; Arguments are given first to the rightmost function, the result of which is passed back as an argument to the other functions in turn

;; So for our 4Clojure exercise we can use:

(comp first reverse)

;; When executing this code, `comp` returns a function that will be used with the arguments (in our case, the collection).

(fn
  ([] (first (reverse)))
  ([x] (first (reverse x)))
  ([x y] (first (reverse x y)))
  ([x y z] (first (reverse x y z)))
  ([x y z & args] (first (apply reverse x y z args))))

;; This may look a little complicated at first, as its a polymorphic function.  It does different behaviour depending on the number of arguments passed to the function.

;; calling this with our 4Clojure test data, the function path with one argument is called
([x] (first (reverse x)))

;; lets call the function returned by comp with the collection argument from our 4Clojure exercise.
((fn
   ([] (first (reverse)))
   ([x] (first (reverse x)))
   ([x y] (first (reverse x y)))
   ([x y z] (first (reverse x y z)))
   ([x y z & args] (first (apply reverse x y z args))))
 [1 2 3 4 5])
;; => 5


;; Answers summary
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;; nice functional approch with comp and functional composition

(comp first reverse)

;; normal form of function definition

(fn [arg]
  (first (reverse arg)))

;; short form of function definition

#(first (reverse %))
