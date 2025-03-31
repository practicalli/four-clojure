(ns four-clojure.009-sets-conj)


;; #9 Sets: conj
;;

;; Difficulty:	Elementary
;; Topics:

;; When operating on a set, the conj function returns a new set with one or more keys "added".

;; (= #{1 2 3 4} (conj #{1 4 3} __))


;; Deconstruct the problem
;;

;; A simple challenge to show how `conj` works with sets.

;; REPL experiments
;;

;; As discussed in challenge #8, sets do not really care about order
;; it is only the values within the set that is important

;; So if the result we want is `#{1 2 3 4}`
;; then we just need to conjoin the missing number

(conj #{1 4 3} 2)


;; => #{1 4 3 2}


;; Answers summary
;;

;; Most readable answer
2


;; Superfluous answers

;; As conj can take multiple values you could add any of the values already in the set,
;; so long as you are adding 2 also.
;; Extra values will just be stripped out.

;; 1 2 3 4
;; 1 2 3 4 1 2 3 4 1 2 3 4
