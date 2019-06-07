(ns four-clojure.039-interleave-two-seqs)

;; #039 Interleave two seqs
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;; Difficulty:	Easy
;; Topics:	seqs core-functions
;; Special Restrictions: interleave


;; Write a function which takes two sequences and returns the first item from each, then the second item from each, then the third, etc.

;; (= (__ [1 2 3] [:a :b :c]) '(1 :a 2 :b 3 :c))
;; (= (__ [1 2] [3 4 5 6]) '(1 3 2 4))
;; (= (__ [1 2 3 4] [5]) [1 5])
;; (= (__ [30 20] [25 15]) [30 25 20 15])


;; Deconstruct the problem
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;; Write a function that takes two seqs as arguments

;; Iterate over each sequence in parallel, taking an item for each and returning them together.

;; We cant use interleave, so its an opportunity to see how that function works.

;; REPL experiments
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;; What is a seq?

;; Put simply its a sequence of elements.
;; Elements can be numbers, characters, strings, symbols, function calls, etc

;; Lists and Vectors can both be used as sequences
;; There are common functions that work with sequences
;; `first`, `rest`, `conj`, ...


;; our test data are all seqs

[1 2 3]

[:a :b :c]


;; Interleave
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; This gives the correct answer, but we cannot use it for our solution

(interleave  [1 2 3]
             [:a :b :c])
;; => (1 :a 2 :b 3 :c)



;; loop - recur
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; You can do pretty much anything with loop recur...
;; but it can be a lot of work...


;; The `map` function
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; `map` - Iterating through two (or more) sequences

;; form of map
;; (map function sequence)
;; (map function sequence)


(map
  (fn [seq1 seq2] [seq1 seq2])
  [1 2 3]
  [:a :b :c])
;; => ([1 :a] [2 :b] [3 :c])

;; We have the right combinations of values, but not the right shape of data

;; There are several funcitons we can use to combine values and sequences
;; cons - wrong type of arguments
;; conj - wrong type of arguments
;; into - reduce conj
;; concat - concatenation of the elements in supplied colls (returns lazy seq).

(concat [1 :a] [2 :b] [3 :c])
;; => (1 :a 2 :b 3 :c)


;; so if we pass the results of our map expression to concat
;; we should get the right structure for our data

(concat
  (map
    (fn [seq1 seq2] [seq1 seq2])
    [1 2 3]
    [:a :b :c]))
;; => ([1 :a] [2 :b] [3 :c])


;; Oh, thats not good...

;; Actually we have a slightly different form of data,
;; as map returns a single sequence.
;; So we need to use concat over the elements of the sequence

#_(map concat
       ([1 :a] [2 :b] [3 :c]))
;; Wrong number of args (2) passed to: PersistentVector
;; Dont forget the quote !!

(map concat
     '([1 :a] [2 :b] [3 :c]))
;; => ((1 :a) (2 :b) (3 :c))


(reduce concat '([1 :a] [2 :b] [3 :c]))
;; => (1 :a 2 :b 3 :c)


(apply concat '([1 :a] [2 :b] [3 :c]))
;; => (1 :a 2 :b 3 :c)


#_(mapcat [] '([1 :a] [2 :b] [3 :c]))
;; java.lang.IllegalArgumentException
;; Key must be integer


;; So, we can use reduce concat with our orignal `map` expression

(reduce
  concat
  (map
    (fn [seq1 seq2] [seq1 seq2])
    [1 2 3]
    [:a :b :c]))
;; => (1 :a 2 :b 3 :c)


;; It works.  Can we refactor to make it simpler or more elegant?

;; map and concat are the two main functions
;; lets look for similar functions on clojuredocs.org
;; searching for `map` we see a function called `mapcat`

;; https://clojuredocs.org/clojure.core/mapcat


;; so lets try mapcat

(mapcat
  (fn [seq1 seq2] [seq1 seq2])
  [1 2 3]
  [:a :b :c])
;; => (1 :a 2 :b 3 :c)


;; Can we refine further?

;; Yes, our function here is simply putting two values in order into a vector
;; there are functions that do that already, `vector` and `vec`


(vector 1 2)


(vector [1 2] [3 4])



(mapcat vec '([1 :a] [2 :b] [3 :c]))


;; References
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Don't mix lazy and eager evaluation
;; https://stuartsierra.com/2015/04/26/clojure-donts-concat

;; Cons, Conj, Concat, Oh my?
;; https://medium.com/@greg_63957/conj-cons-concat-oh-my-1398a2981eab


;; Answers summary
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

mapcat vec
