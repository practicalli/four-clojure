(ns four-clojure.025-find-the-odd-number)

;; Find the odd numbersSolutions
;; Difficulty:	Easy
;; Topics:	seqs

;; Write a function which returns only the odd numbers from a sequence.
;; test not run

;; (= (__ #{1 2 3 4 5}) '(1 3 5))
;; (= (__ [4 2 1 6]) '(1))
;; (= (__ [2 2 4 6]) '())
;; (= (__ [1 1 1 3]) '(1 1 1 3))


;; Deconstruct the problem
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;; We could iterate over the collection and keep only those values that we tested to be odd.

;; REPL experiments
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;


;; `clojure.core` namespace has a function called `odd?`
;; which returns a boolean value of `true` if the value is odd
;; and `false` if the value is not odd.
(odd? 1)
;; => true

(odd? 2)
;; => false

;; NOTE: Function names with a `?` at the end by convention should return a boolean value,
;; so either `true` or `false`.


;; In 4Clojure #017 we see the `map` function
;; `map` takes a function and a sequence (collection)
;; returning a new sequence that is the result of applying the function to each element in turn.

;; So if we map `odd?` over the collection
;; we get a sequence showing which elements are odd (true) or not odd (false)
(map odd?
     [4 2 1 6])
;; => (false false true false)

;; we could also write this more specifically, using a lambda function
;; using the debugger we can see how the individual elements are evaluated

(map (fn [number] (odd? number))
     [4 2 1 6])
;; => (false false true false)

;; Although map does correctly find the odd numbers,
;; we want to just have the numbers and not the result of applying `odd?` to each number


;; `filter` function
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; We met the `filter` function in 4Clojure challenge #018
;; Filter will create a new sequence by applying a function to a sequence,
;; keeping only those values that evaluate to `true` with the given function.

(filter odd? [4 2 1 6])
;; => (1)

;; There is a similar function called `remove`,
;; which returns a sequence that removes any values that
;; evaluate to true with the given function.

(remove odd? [4 2 1 6])
;; => (4 2 6)


(filter odd? [4 2 1 6])
;; => (1)

(filter even? [4 2 1 6]);; => (4 2 6)

;; Answers summary
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

filter odd?
