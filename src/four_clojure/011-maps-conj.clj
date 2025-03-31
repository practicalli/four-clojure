;; # 011 - Maps: conj
;;

;; Difficulty:	Elementary
;; Topics:


;; When operating on a map, the conj function returns a new map with one or more key-value pairs "added".

;; (= {:a 1, :b 2, :c 3} (conj {:a 1} __ [:c 3]))


;; Deconstruct the problem
;;

;; Simply fill in a collection that contains the right values.

;; Maps need to include values in pairs, a key and a value that the key is associated with.
;; Maps can conj from values in a vector, so long as the vector contains key/value pairs

;; So this expression works

(conj {:a 1} [:b 2] [:c 3])


;; => {:a 1, :b 2, :c 3}

;; and of course using a map with a key value pair would also work

(conj {:a 1} {:b 2} [:c 3])


;; Using a vector that contains an a key without a value will cause and error.

#_(conj {:a 1} [:b 2 :d] [:c 3])


;; java.lang.IllegalArgumentException
;; Vector arg to map conj must be a pair

;; The same expression with a map this time does return something, which is surprising.
;; Although evaluating it also throws a reader exception.
;; This is because second map in the expression has a syntax error,
;; {:b 2 :d} is not valid syntax.

#_(conj {:a 1} {:b 2 :d} [:c 3])


;; => [:c 3]
;; clojure.lang.LispReader$ReaderException
;; java.lang.RuntimeException: Unmatched delimiter: )

;; Trying to construct a map using the hash-map function and an incorrect set of key value pairs
;; also creates an error.  However, this time its much clearer as to the error.
#_(conj {:a 1} (hash-map :b 2 :d) [:c 3])

#_{hash-map :b 2 :d}


;; java.lang.IllegalArgumentException
;; No value supplied for key: :d

;; NOTE: The above code has been evaluated with Clojure 1.9.  Version 1.10 may have improved error messages when using ill-formed maps.

;; Answers summary
;;

;; Simplest answers

[:b 2]
{:b 2}
(hash-map :b 2)


;; Overthought answers

;; Least valuable answer
