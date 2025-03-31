(ns four-clojure.062-re-implement-iterate)


;; #062 Re-implement Iterate
;;

;; Difficulty:	Easy
;; Topics:	seqs core-functions

;; Given a side-effect free function f and an initial value x write a function which returns an infinite lazy sequence of x, (f x), (f (f x)), (f (f (f x))), etc.


;; (= (take 5 (__ #(* 2 %) 1)) [1 2 4 8 16])
;; (= (take 100 (__ inc 0)) (take 100 (range)))
;; (= (take 9 (__ #(inc (mod % 3)) 1)) (take 9 (cycle [1 2 3])))


(= (take 5 (iterate #(* 2 %) 1)) [1 2 4 8 16])


;; => true
(= (take 100 (iterate inc 0)) (take 100 (range)))


;; => true
(= (take 9 (iterate #(inc (mod % 3)) 1)) (take 9 (cycle [1 2 3])))


;; => true



;; Deconstruct the problem
;;

;; Implementation of iterate function in clojure.core

;; (defn iterate
;;   "Returns a lazy sequence of x, (f x), (f (f x)) etc. f must be free of side-effects"
;;   {:added "1.0"
;;    :static true}
;;  [f x] (clojure.lang.Iterate/create f x) )

;; The `clojure.core/iterate` provide a hint about using a lazy-seq in the doc string
;; The source code of the function does not really help though

;; clojure.lang.Iterate is implemented as a Java class
;; and create is a method on that class.
;; https://github.com/clojure/clojure/blob/master/src/jvm/clojure/lang/Iterate.java



;; What is a side effect free function?
;;
;; - uses only the data it is given via its arguments when called
;; - does not use shared data
;; - does not use anything defined outside of the function definition
;; - values are returned directly (not written to external source - log, stdout, db)


;; Laziness in Clojure
;;

;; Clojure is an eager language, when you call most functions, they return a result immediately.

;; Clojure also has some lazy functions, that delay returning a result.

;; `clojure.core` functions that work with sequences are often lazy
;; these functions return a lazy sequence when called.

;; Its usually the verb named functions in `clojure.core` that are lazy
;; map, filter, remove, lazy-seq


;; lazyness leads to greater efficiency, as you only process the data you need

;; laziness allows you to work with large data sets (un-rotated log files, uncomfortably sized data, etc.)
;; as you only pull in the data into memory that you wish to process

;; To get an answer from your lazy sequence, eventually you need a function that is eager.
;; An eager function will trigger the lazy functions / sequences to start being resolved (returning data).



;; What is a lazy sequence
;;

(1 2 3 4 5 "string" ,,,)


;; A lazy sequence is a sequence of values (collection of data)
;; that is only realised (loaded into computer memory)
;; when those values are needed.

;; Classic example

(take 24 (range))


;; => (0 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20 21 22 23)

;; (range) will eagerly (opposite of lazy) generate all the integers to infinity
;; (take 24 ,,,) will take the first 24 values from a sequence
;; with range nested in another function call it will act lazily
;; and range will only generate the required numbers

;; the take function returns a lazy sequence, making this all happen.


;; From a basic implementation perspective
;;

;; lazy sequences are a sequence
;; a sequence in clojure is a linked list
;; In a linked list, each value also has a pointer to the next value

;; A lazy sequence only creates a new value when its pointer is called

;; So, in our previous example
;; (range) will generate the value 0 in memory, along with a pointer to the next value in the sequence
;; the next value does not yet exist in memory
;; (take 24 ,,,) still requires another value as it does not have 24 values yet
;; (range) then generates another value in memory along with a pointer to the next value in the sequence...
;; this continues until (take 24 ,,,) is satisfied
;; then the values are all returned



;; The lazy-seq function
;;
;; https://github.com/clojure/clojure/blob/clojure-1.9.0/src/clj/clojure/core.clj#L2853

;; This code looks a little complicate,
;; although we can ignore the single argument branch as that is for a transducer

;; we are only interested in the last 5 lines,
;; the branch that is called when given a number n and a collection coll.

#_(defn take
    "Returns a lazy sequence of the first n items in coll, or all items if
  there are fewer than n.  Returns a stateful transducer when
  no collection is provided."
    {:added  "1.0"
     :static true}
    ;; Transducer branch
    ([n]
     (fn [rf]
       (let [nv (volatile! n)]
         (fn
           ([] (rf))
           ([result] (rf result))
           ([result input]
            (let [n      @nv
                  nn     (vswap! nv dec)
                  result (if (pos? n)
                           (rf result input)
                           result)]
              (if (not (pos? nn))
                (ensure-reduced result)
                result)))))))

    ([n coll]
     (lazy-seq
       (when (pos? n)
         (when-let [s (seq coll)]
           (cons (first s) (take (dec n) (rest s))))))))


;; Using seq as a test for an empty sequence / collection
;;

;; We can use the return value of the `seq` function
;; to determine if a sequence / collection is empty

;; clojure.core/seq
;; [coll]
;; Added in 1.0
;; Returns a seq on the collection. If the collection is
;; empty, returns nil.  (seq nil) returns nil. seq also works on
;; Strings, native Java arrays (of reference types) and any objects
;; that implement Iterable. Note that seqs cache values, thus seq
;; should not be used on any Iterable whose iterator repeatedly
;; returns the same mutable object.

;; If a collection has values then its returned as a sequence
(seq [1 2 3 4])


;; => (1 2 3 4)

;; If a collection is empty, then nil is returned.
(seq [])


;; => nil


;; Use `when-let` to conditional bind a local name
;;

;; `when-let` will create a local binding and execute the expressions it wraps
;; if the test is truthy

;; We use `seq` on our collection test for truthy or falsey


;; clojure.core/when-let
;; [bindings & body]
;; Macro
;; Added in 1.0
;; bindings => binding-form test

;; When test is true, evaluates body with binding-form bound to the value of test

;; Spec:

;; arguments  : (s/cat
;;                :bindings (s/and
;;                            vector?
;;                            :clojure.core.specs.alpha/binding)
;;                :body (s/* any?))
;; returns    : any?


;; the test is true, so the `str` expression is evaluated

(when-let [value (= 1 1)]
  (str "when-let conditional local binding: " value))


;; => "when-let conditional local binding: true"


;; the test is true, so the `str` expression is evaluated

(when-let [value (seq [1 2 3 4 5])]
  (str "when-let conditional local binding: " value))


;; => "when-let conditional local binding: (1 2 3 4 5)"


;; the test is false, so the `str` expression is not evaluated

(when-let [value (seq [])]
  (str "when-let conditional local binding: " value))


;; => nil


;; the test is false, so the `str` expression is not evaluated

(when-let [value nil]
  (str "when-let conditional local binding: " value))


;; => nil



;; implementing a function that returns a lazy sequence
;;

;; the simplest way to implement a function that returns a lazy sequence
;; is to wrap the function body in a call to `lazy-seq`

;; A function that returns a lazy sequence using lazy-seq funciton

(fn lazy-function
  []
  (lazy-seq (range)))


;; range is eager, but putting a lazy-seq around it makes the function lazy

(first
  ((fn lazy-function
     []
     (lazy-seq (range)))))


;; => 0


(take 10
      ((fn lazy-function
         []
         (lazy-seq (range)))))


;; => (0 1 2 3 4 5 6 7 8 9)



;; Now we use the lazy-seq to start solving our 4Clojure tests

;; Our iterate function takes a function and a collection
;; f is the argument for our function
;; x is the starting value for the sequence we are going to generate.

(fn -iterate
  [f x]
  (lazy-seq
    (cons [] x (-iterate f x))))


;; Test this function with `inc` and the start value of `0`

(take 10
      ((fn -iterate
         [f x]
         (lazy-seq
           (cons [x] (-iterate f x))))
       inc 0)); => ([0] [0] [0] [0] [0] [0] [0] [0] [0] [0])

;; We are creating a sequence, however, we are not feeding the value
;; back into the `-iterate` funciton


;; We could use the `let` function to create a local binding

#_(fn [f x]
    (let [value (f x)]))


;; Add the `let` to our `-iterate` function
;; value is now the result of applying the function `f` to the value `x`

(fn -iterate
  [f x]
  (let [value (f x)]
    (lazy-seq
      (cons value (-iterate f value)))))


;; run the same test again

(take 10
      ((fn -iterate
         [f x]
         (let [value (f x)]
           (lazy-seq
             (cons x (-iterate f value)))))
       inc 0))


;; => (0 1 2 3 4 5 6 7 8 9)

;; success!

;; creating our 4Clojure solution
;;

;; define a function that takes two arguments

(fn [f x])


;; then take the parameters and call the function with the value

(fn [f x]
  (let [new (f x)]))


;; now we need to call the function again with the new value

(fn -iterate
  [f x]
  (let [new (f x)]
    (-iterate f new)))


;; we need to capture the result of the function called with the value each times
;; ensuring we capture the initial value

(fn -iterate
  [f x]
  (let [new (f x)]
    (cons x (-iterate f new))))


;; now we need to make it lazy so that calling our -iterate funciton doesnt
;; go on forever

(fn -iterate
  [f x]
  (let [new (f x)]
    (lazy-seq
      (cons x (-iterate f new)))))


;; now we can call our -iterate function
;; and give it some context on the number of values to generate
;; by using the take function

(take 100
      ((fn -iterate
         [f x]
         (let [new (f x)]
           (lazy-seq
             (cons x (-iterate f new)))))
       inc 0))


;; => (0 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20 21 22 23 24 25 26 27 28 29 30 31 32 33 34 35 36 37 38 39 40 41 42 43 44 45 46 47 48 49 50 51 52 53 54 55 56 57 58 59 60 61 62 63 64 65 66 67 68 69 70 71 72 73 74 75 76 77 78 79 80 81 82 83 84 85 86 87 88 89 90 91 92 93 94 95 96 97 98 99)

(take 100
      ((fn -iterate
         [f value]
         (lazy-seq
           (cons value (f (f value)))))
       inc 0))


;; Refine the example by dropping the let which doesnt really add much

(fn -iterate
  [f x]
  (lazy-seq (cons x (iterate f (f x)))))


;; (-iterate f (f x))
;; (-iterate f (-iterate f (f (f x))))


;; Refine the example using lazy-cat, which removes the need for an explicit cons function

(fn -iterate
  [f x]
  (lazy-cat [x] (-iterate f (f x))))


;; Answers summary
;;

;; Useful references on Lazy sequences
;; https://clojuredesign.club/episode/030-lazy-does-it/
;; https://clojuredesign.club/episode/031-eager-abstraction/
;; https://clojuredesign.club/episode/032-call-me-lazy/

;; preferred result using lazy-cat
;; creates a lazy sequence
;; from concatenating the results of each iteration

(fn -iterate
  [f x]
  (lazy-cat [x] (-iterate f (f x))))


;; using cons to assemble the results and an explicit `lazy-seq`

(fn -iterate
  [f x]
  (let [new (f x)]
    (lazy-seq
      (cons x (-iterate f new)))))


;; using cons without the let function

(fn -iterate
  [f x]
  (lazy-seq (cons x (-iterate f (f x)))))
