(ns four-clojure.005-lists-conj)

;; Lists: conj
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;; Difficulty:	Elementary
;; Topics:

;; When operating on a list, the conj function will return a new list with one or more items "added" to the front.

;; Note that there are two test cases, but you are expected to supply only one answer, which will cause all the tests to pass.

;; (= __ (conj '(2 3 4) 1))
;; (= __ (conj '(3 4) 2 1))


;; Deconstruct the problem
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;; Joining values on to a collection (sequence).

;; conj and cons are simple functions to join one or more values onto a collection, specifically a sequence (list, vector)


;; REPL experiments
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;


(conj '(2 3 4) 1)
;; => (1 2 3 4)

(conj '(3 4) 2 1)
;; => (1 2 3 4)

;; the above is also conceptually the same as

(conj
 (conj '(3 4) 2)                        ; returns (2 3 4)
 1)
;; => (1 2 3 4)

;; So we could simply use '(1 2 3 4) as the answer.

(= '(1 2 3 4) (conj '(2 3 4) 1))
;; => true

(= '(1 2 3 4) (conj '(3 4) 2 1))
;; => true


;; conj works with vectors in general, however the ordering of the results are differnt
;; A vector type can be used as a sequence, although as it has an index (like an array)
;; then it is efficient to add values to the end of the sequence.

(conj [2 3 4] 1)
;; => [2 3 4 1]

(conj [3 4] 2 1)
;; => [3 4 2 1]

;; You can use a vector as the answer though, as the `=` function is checking the values
;; and not the underlying type of the collection.

(= [1 2 3 4] (conj '(2 3 4) 1))
;; => true

(= [1 2 3 4] (conj '(3 4) 2 1))
;; => true


;; conj does not work with maps, as they are an associative collection

(conj {:a 1 :b 2 :c 3} :d 4)
;; => IllegalArgumentException
;; Don't know how to create ISeq from: clojure.lang.Keyword


;; conj does work with sets, but does not guarantee order
;; sets care about uniqueness rather than order
;; sets are useful to check if a value is contained in a set of numbers

(conj #{1 2 3} 4)
;; => #{1 4 3 2}


;; Answers summary
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;; Most readable answer
[1 2 3 4]
'(1 2 3 4)

;; Overthought answers

(conj
 (conj '(3 4) 2)
 1)


;; Least valuable answer
