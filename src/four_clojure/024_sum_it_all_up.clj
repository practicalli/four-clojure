(ns four-clojure.024-sum-it-all-up)


;; #024 Sum it all up
;;

;; Difficulty:	Easy
;; Topics:	seqs

;; Write a function which returns the sum of a sequence of numbers.

;; (= (__ [1 2 3]) 6)
;; (= (__ (list 0 -2 5 5)) 8)
;; (= (__ #{4 2 1}) 7)
;; (= (__ '(0 0 -1)) -1)
;; (= (__ '(1 10 3)) 14)

;; Deconstruct the problem
;;

;; We could write our own function as the exercise description suggests.  However, there is a Clojure core function that we could use


;; REPL experiments
;;

;; Writing our own function

(fn sum-of-sequence
  [collection]
  (loop [-collection collection
         -sum-total  0]
    (if (empty? -collection)
      -sum-total
      (recur (rest -collection)
             (+ -sum-total (first -collection))))))


;; test this function by calling it with some of the 4Clojure test data as an argument

((fn sum-of-sequence
   [collection]
   (loop [-collection collection
          -sum-total  0]
     (if (empty? -collection)
       -sum-total
       (recur (rest -collection)
              (+ -sum-total (first -collection))))))
 [1 2 3])


;; => 6

;; It works !


;; Higher order approach with reduce
;;

;; In previous 4Clojure exercises we have seen the `reduce` function in action, for example

(reduce * [1 2 3 4 5])


;; => 120

;; We can simply use `reduce` to add up the values in our collection and return the total.

(reduce + [1 2 3])


;; => 6


;; Answers summary
;;

;; reduce approach

reduce +


;; low level loop recur

(fn sum-of-sequence
  [collection]
  (loop [-collection collection
         -sum-total  0]
    (if (empty? -collection)
      -sum-total
      (recur (rest -collection)
             (+ -sum-total (first -collection))))))
