(ns four-clojure.023-reverse-a-sequence)

;; #023 Reverse a sequence
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;; Difficulty:	Easy
;; Topics:	seqs core-functions
;; Restricted: reverse rseq

;; Write a function which reverses a sequence.

;; (= (__ [1 2 3 4 5]) [5 4 3 2 1])
;; (= (__ (sorted-set 5 7 2 7)) '(7 5 2))
;; (= (__ [[1 2][3 4][5 6]]) [[5 6][3 4][1 2]])


;; Deconstruct the problem
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;; Reversing a collection is simple with the `reverse` function,
;; but we are not allowed to use it or the `rseq` function.

(reverse [1 2 3 4 5])
;; => (5 4 3 2 1)

(rseq [1 2 3 4 5])
;; => (5 4 3 2 1)


;; So this is an opportunity to understand how the reverse function works.

;; Looking at the source code for the reverse function shows
;; quite clearly how it works
;; https://github.com/clojure/clojure/blob/clojure-1.9.0/src/clj/clojure/core.clj#L939

;; We can also use the fact that adding new values onto a list puts them at the front,
;; because that is the most efficient way to add values to a list (especially a long one).


;; REPL experiments
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;; Lets experiment with sequences
;; in earlier 4 Clojure exercises we learnt a little about sequences
;; we also learnt that there was an implementation difference between
;; a list and a vector

;; The `cons` function treat both a list and vector the same, as a sequence
;; A sequence is constructed with the new value at the front
(cons 3 '(1 2))
;; => (3 1 2)

(cons 3 [1 2])
;; => (3 1 2)


;; `conj` acts differently when using vectors, as a vector has an index,
;; so its efficient to add a new value at the end of a vector.
;; A list is a linked list, so its more efficient to add a new value to the start,
;; as otherwise adding a new value at the end would mean walking through all the other values.

(conj '(1 2) 3)
;; => (3 1 2)

(conj [1 2] 3)
;; => [1 2 3]


;; So we can use the `cons` function to reverse the order of our collection,
;; if we iterate over the collection, building a new collection with each value in turn
;; We can also use the `conj` function if we start with a list

(conj () 1)
;; => (1)

(conj
 (conj () 1)
 2)
;; => (2 1)

(conj
 (conj
  (conj () 1)
  2)
 3)
;; => (3 2 1)


;; We can use the `reduce` function to iterate over the values in the collection
;; A simple example of `reduce` is to add all the values together in a collection.

;; (+ [1 2 3 4 5]) ;; plus function takes only numbers

(+ 1 2 3 4 5)
;; => 15

(reduce + [1 2 3 4 5])
;; => 15


;; Back to our challenge and we want to con

(reduce conj () [1 2 3 4 5])
;; => (5 4 3 2 1)

;; To use `reduce` with `cons` is a little bit trickier, due to the signature of `cons`
;; cons value sequence

(reduce cons () [1 2 3 4 5])
;; java.lang.IllegalArgumentException
;; Don't know how to create ISeq from: java.lang.Long

;; so to use `cons` we would need to create an inline function
;; but even that doent quite work either
(reduce
 (fn [value] (cons value ()))
 [1 2 3 4 5])

(reduce #(cons % ()) [1 2 3 4 5])

;; If we use apply rather than reduce, then we also have an issue
;; although the error message also helps indicate the issue,
;; in that when
(apply #(cons % ()) [1 2 3 4 5])
;; clojure.lang.ArityException
;; Wrong number of args (5) passed to:
;; 023-reverse-a-sequence/eval16686/fn--16687

(reduce cons [1 2 3 4 5])
;; java.lang.IllegalArgumentException
;; Don't know how to create ISeq from: java.lang.Long


(reduce #(cons % ) [1 2 3 4 5])
;; clojure.lang.ArityException
;; Wrong number of args (2) passed to:
;; 023-reverse-a-sequence/eval16694/fn--16695

;; There is a useful stack overflow question about reduce verse apply
;; that covers what these two expressions expand to in Clojure
;; https://stackoverflow.com/questions/3153396/clojure-reduce-vs-apply

(reduce + (list 1 2 3 4 5))
;; translates to: (+ (+ (+ (+ 1 2) 3) 4) 5)

(apply + (list 1 2 3 4 5))
;; translates to: (+ 1 2 3 4 5)

;; So for our reduce it would look like this

(reduce #(cons % ()) [1 2 3 4 5])
;; translates to (cons (cons (cons (cons 1 2 () ))))

(cons 1 ())

(cons 1 2 () )
;; clojure.lang.ArityException
;; Wrong number of args (3) passed to: core/cons--5098

(cons () 1 2)
;; clojure.lang.ArityException
;; Wrong number of args (3) passed to: core/cons--5098

(map #(cons % ()) [1 2 3 4 5])


;; So that was a quite trick diversion, the upshot of which is that using `cons` with reduce is a bit tricky in this case.
;; So lets just stick wtih `conj`


(reduce conj () [1 2 3 4 5])

(conj
 (conj
  (conj () 1)
  2)
 3)
;; => (3 2 1)


;; so we have a solution, lets put it into a function definition and try it with 4Clojure


(fn [collection]
  (reduce conj () collection))

;; Calling the function with example test data

((fn [collection]
   (reduce conj () collection))
 [1 2 3 4 5])
;; => (5 4 3 2 1)

;; We can use the short form of defining a function

#(reduce conj () %)

(#(reduce conj () %)
 [1 2 3 4 5])
;; => (5 4 3 2 1)



;; Higher level abstraction
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Looking at the `into` function you will discover that it is essentially an abstraction over
;; the combination of `reduce` and `conj`

(reduce conj () [1 2 3 4 5])
;; => (5 4 3 2 1)

(into () [1 2 3 4 5])
;; => (5 4 3 2 1)


(fn [collection]
  (into () collection))

((fn [collection]
   (into () collection))
 [1 2 3 4 5])
;; => (5 4 3 2 1)


;; You can also use the short form of a function definition,
;; using the reader macro syntax #()

#(into () %)

(#(into () %)
 [1 2 3 4 5])
;; => (5 4 3 2 1)



;; Answers summary
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;; using into as a nice abstraction over reduce conj
(fn [collection]
  (into () collection))

;; or using the short form of function definition

#(into () %)


;; defining a reduce function

(fn [collection]
  (reduce conj () collection))

;; We can use the short form of defining a function

#(reduce conj () %)
