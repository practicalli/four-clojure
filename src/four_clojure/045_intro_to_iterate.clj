(ns four-clojure.045-intro-to-iterate)


;; #045 Intro to Iterate
;;

;; Difficulty:	Easy
;; Topics:	seqs


;; The iterate function can be used to produce an infinite lazy sequence.

;; (= __ (take 5 (iterate #(+ 3 %) 1)))



;; Deconstruct the problem
;;

;; Evaluate the expression to see what it does

(take 5 (iterate #(+ 3 %) 1))


;; => (1 4 7 10 13)

;; So how do this work

(clojure.repl/doc iterate)


;; [f x]
;; Returns a lazy sequence of x, (f x), (f (f x)) etc. f must be free of side-effects

;; `iterate` creates a lazy sequence by calling the function with the value as an argument,
;; then calls the same function with the result of calling the function with the value
;; and so on infinitely

;; Lets look at the function
#(+ 3 %)


;; The short-hand syntax for a function definition

(fn [number]
  (+ 3 number))


;; So we are just adding three.

;; Therefore iterate will keep adding 3 to the previous function call results

;; As `iterate` creates a lazy sequence, we should wrap it with an expression.

(take 5 (iterate #(+ 3 %) 1))


;; => (1 4 7 10 13)


(take 50 (iterate #(+ 3 %) 1))


;; => (1 4 7 10 13 16 19 22 25 28 31 34 37 40 43 46 49 52 55 58 61 64 67 70 73 76 79 82 85 88 91 94 97 100 103 106 109 112 115 118 121 124 127 130 133 136 139 142 145 148)

;; Answers summary
;;

'(1 4 7 10 13)


;; Or use a vector and get 1 point lower on your golf score

[1 4 7 10 13]
