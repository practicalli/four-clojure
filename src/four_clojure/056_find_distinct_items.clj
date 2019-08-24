(ns four-clojure.056-find-distinct-items)

;; #056 Find distinct items
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

Difficulty:	Medium
Topics:	seqs core-functions
Restrictions: distinct


Write a function which removes the duplicates from a sequence. Order of the items must be maintained.

(= (__ [1 2 1 3 1 2 4]) [1 2 3 4])
(= (__ [:a :a :b :b :c :c]) [:a :b :c])
(= (__ '([2 4] [1 2] [1 3] [1 3])) '([2 4] [1 2] [1 3]))
(= (__ (range 50)) (range 50))


;; Deconstruct the problem
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;; We are given a sequence of values which we need to transform
;; into the same shape of sequence, but with unique values

;; This can easily be done using the distinct

(distinct [1 2 1 3 1 2 4])
;; => (1 2 3 4)

(distinct [:a :a :b :b :c :c])
;; => (:a :b :c)

(distinct  '([2 4] [1 2] [1 3] [1 3]))
;; => ([2 4] [1 2] [1 3])

(distinct (range 50))
;; => (0 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20 21 22 23 24 25 26 27 28 29 30 31 32 33 34 35 36 37 38 39 40 41 42 43 44 45 46 47 48 49)


;; Unfortunately the destinct function is restricted in this solution

;; We could loop/recur over the sequence (high maintenance, low abstraction)

;; We could reduce a function over the sequence, keeping only one of each value.

(fn [xs]
  (reduce ,,,))


;; REPL experiments
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;; Question: how to find unique elements in a sequence


;; In previous solutions we have used partition
;; however, that was for helping find increasing values
;; it does not seem to be useful here

(partition 2 1 [1 2 1 3 1 2 4])
;; => ((1 2) (2 1) (1 3) (3 1) (1 2) (2 4))


;; How does distinct work?


;; If we give distinct a collection / sequence
;; then this expression is evaluated

#_([coll]
   (let [step (fn step [xs seen]
                (lazy-seq
                  ((fn [[f :as xs] seen]
                     (when-let [s (seq xs)]
                       (if (contains? seen f)
                         (recur (rest s) seen)
                         (cons f (step (rest s) (conj seen f))))))
                   xs seen)))]
     (step coll #{})))


;; The comparison is essentially an if condition

(if (contains? seen f)
  (recur (rest s) seen)
  (cons f (step (rest s) (conj seen f))))


;; So we can use this to start writing our own
;; version of distinct

;; what is the simplest condition we can use with if

;; if arg1 we already have and arg2 is the next value

(if (= arg1 arg2)
  arg1
  arg2)

;; Duplicates are not next to each other,
;; so we need to look at them as a whole
;; evaluating if a value is anywhere in the sequence

;; We have seen the contains function before, but that works on indexes
;; in sequences

(contains? [1 2 1 3 1 2 4] 5)
;; => true

(some even? '(1 2 3 4))
;; => true


(some (fn [x] (= 5 x)) [1 2 3 4 5])
;; => true


;; An interesting case is when you use set

(some #{2} (range 0 10))
;; => 2

(some #{6 2 4} (range 0 10))
;; => 2
(some #{2 4 6} (range 3 10))
;; => 4
(some #{200} (range 0 10))
;; => nil

;; If we are processing our list we can see the first two values
;; are unique
;; [1 2 1 3 1 2 4]

;; when we get to the third, if we compare
;; the set of that value
;; to the vector we create of the first two values
;; then we see if that value is already present

(some #{1} [1 2])
;; => 1

;; Putting this in an if statement
;; we can then choose to add it to our list of seen values

(if (some #{1} [1 2])
  1
  (conj [1 2] 1))
;; => 1



(if (some #{3} [1 2])
  3
  (conj [1 2] 3))
;; => [1 2 3]


;; Coming back to the recur

(fn [xs]
  (reduce ,,,))

;; If we give reduce an empty vector and the sequence to process
;; then we can use the if statement to conditionally add the next value
;; from the sequence to the vector.
;; Each value we get from the sequence is compared to the values added
;; to the vector

(fn [xs]
  (reduce
    #(if (some #{%2} %1)
       %1
       (conj %1 %2))
    [] xs))

;; lets use the debugging to see this
;; `M-x cider-debug`
;; `, d b` in Spacemacs

((fn [x]
   (reduce
     #(if (some #{%2} %1)
        %1
        (conj %1 %2))
     [] x))
 [1 2 1 3 1 2 4])


;; Answers summary
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(fn [x]
  (reduce
    #(if (some #{%2} %1)
       %1
       (conj %1 %2))
    []
    x))




;; we can drop the function definition that wraps the reduce
;; as the missing part is already at the start of the list

reduce #(if (some #{%2} %) % (conj % %2)) []

reduce
(fn [seen value]
  (if (some #{value} seen)
    seen
    (conj seen value)))
[]
,,,


(= (reduce
     #(if (some #{%2} %) % (conj % %2))
     []
     [1 2 1 3 1 2 4])

   [1 2 3 4])


;;;;;;;;

#(loop[seen #{} result [] remaining %]
   (if-let[value (first remaining)]
     (if (seen value)
       (recur seen result (rest remaining))
       (recur (conj seen value) (conj result value) (rest remaining)))
     result))
