(ns four-clojure.049-split-a-sequence)

;; #049 Split a sequence
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;; Difficulty:	Easy
;; Topics:	seqs core-functions
;; Restriction: split-at

;; Write a function which will split a sequence into two parts.

;; (= (__ 3 [1 2 3 4 5 6]) [[1 2 3] [4 5 6]])
;; (= (__ 1 [:a :b :c :d]) [[:a] [:b :c :d]])
;; (= (__ 2 [[1 2] [3 4] [5 6]]) [[[1 2] [3 4]] [[5 6]]])

;; Deconstruct the problem
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;; It should be easy enough to break apart a collection in Clojure

;; We are given a number of elements to get as the first part of the result,
;; which will be the same as the number of elements we dont want in the second part

;; REPL experiments
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;; take will give us the first part of the answer
(take 3 [1 2 3 4 5 6])
;; => (1 2 3)

(drop 3 [1 2 3 4 5 6])
;; => (4 5 6)

;; so if we combine these two, we can create the right result.

[(take 3 [1 2 3 4 5 6])
 (drop 3 [1 2 3 4 5 6])]
;; => [(1 2 3) (4 5 6)]

;; Now just put this into a lambda function and we should have a solution for all tests

(fn [size data]
  [(take size data)
   (drop size data)])


;; Abstracting higher

;; Takes a set of functions and returns a fn that is the juxtaposition
;; of those fns.  The returned fn takes a variable number of args, and
;; returns a vector containing the result of applying each fn to the
;; args (left-to-right).
;; ((juxt a b c) x) => [(a x) (b x) (c x)]

(juxt take drop)

;; when we call this with the tests, we get the following

((juxt take drop) 3 [1 2 3 4 5 6])
;; => [(1 2 3) (4 5 6)]

;; This expands to:

[(take 3 [1 2 3 4 5 6]) (drop 3 [1 2 3 4 5 6])]


;; Answers summary
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(fn [size data]
  [(take size data)
   (drop size data)])

;; or a higher abstraction with `juxt`

(juxt take drop)
