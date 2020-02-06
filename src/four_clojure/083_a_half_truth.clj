(ns four-clojure.083-a-half-truth)

;; #83: a half truth
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;; Write a function which takes a variable number of booleans.
;; Your function should return true if some of the parameters are true, but not all of the parameters are true. Otherwise your function should return false.

;; Difficulty:	Easy
;; Topics:

;; Write a function which takes a variable number of booleans. Your function should return true if some of the parameters are true, but not all of the parameters are true. Otherwise your function should return false.

;; (= false (__ false false))
;; (= true (__ true false))
;; (= false (__ true))
;; (= true (__ false true false))
;; (= false (__ true true true))
;; (= true (__ true true true false))


;; Deconstruct the problem
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;; If we take the instructions as is, then we can write a function that counts how many arguments we have
;; and then figure out how many `true` values there are.
;; If there are less `true` values than the total count of arguments and
;; there are more `true` values than zero, then we return `true`.
;; Everything else we return false.

;; REPL experiments
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;; a function that takes a variable number of booleans

(fn [& args]
  (if (and (> (count args)
              (filter true? args))
           (> (filter true? args)
              0))
    true
    false))

;; java.lang.ClassCastException: clojure.lang.LazySeq cannot be cast to java.lang.Number


(fn [& args]
  (let [number-of-true-values (filter true? args)]
    (if (and (> (count args)
                number-of-true-values)
             (> number-of-true-values
                0))
      true
      false)))

;; java.lang.ClassCastException: clojure.lang.LazySeq cannot be cast to java.lang.Number

(fn [& args]
  (let [number-of-true-values (filter true? args)
        number-of-args        (count args)]
    (if (and (> number-of-args
                number-of-true-values)
             (> number-of-true-values
                0))
      true
      false)))

;; java.lang.ClassCastException: clojure.lang.LazySeq cannot be cast to java.lang.Number



((fn [& args]
   (count args))
 true true false)
;; => 3




(fn [& args]
  (let [number-of-true-values (filter true? args)
        number-of-args        (count args)
        is-true               (and (> number-of-args
                                      number-of-true-values)
                                   (> number-of-true-values
                                      0))]
    (if is-true
      true
      false)))

;; java.lang.ClassCastException: clojure.lang.LazySeq cannot be cast to java.lang.Number



;; Revising the approach
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;


;; a full way of describing the algorithm and thinking behind it
(fn [& args]
  (let [not-all-true (not (every? true? args))
        any-true     (first (filter true? args))]
    (= not-all-true any-true)))
;; => #function[clojure-through-code.four-clojure/eval14617/fn--14618]

(fn [& args]
  (= (not (every? true? args))
     (first (filter true? args))))

;; To get a better golf score (fewer characters) an anonymous function can be used
;; The %& is a placeholder for all the parameters
(#(=
   (not (every? true? %&))
   (first (filter true? %&))) true)


;; From the first example, we can see that comparing true and false values
;; will give a boolean result.  So what about just comparing the values

;; If all the values are true => false
;; If all the values are false => false
;; If there is at lease one true & one false => true

;; So by replacing the not and first expressions with their results I would get the following combinations of results

(= true true)   ;; => true
(= true false)  ;; => false
(= false false) ;; => true


;; These combinations give the opposite result we are looking for (false when it should be true, etc).
;; So we can invert the result using not or better still use the not= function

(not= true true)   ;; => false
(not= true false)  ;; => true
(not= false false) ;; => false



;; 4Clojure entered solution
;; not=
