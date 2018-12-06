(ns four-clojure.002-simple-math)

;; Simple Math
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Difficulty:	Elementary
;; Topics: numbers, arithmetic

;; If you are not familiar with polish notation, simple arithmetic might seem confusing.

;; Note: Enter only enough to fill in the blank (in this case, a single number) - do not retype the whole problem.

;; Test cases

;; (= (- 10 (* 2 3)) __)


;; Deconstruct the problem
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;; Polish notation is where the operator (+, -, /, *) is placed before the numbers.

;; Polish notation is also called pre-fix, as the operator is placed before (pre) the values.

;; As operators are actually function calls, then Polish notation arithmetic fits perfectly.

;; Using operators as function calls also ensures there is no ambiguity in order of precedence (which calculation is done first).  The code is written within list structures, `()`, providing scope to the order of calculation.

;; To solve the challenge, we simply need to calculate the result of the expression and put that value into the 4Clojure code window.


;; REPL experiments
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;; The maths in the expression is relatively basic, but to be sure you can copy the calculation into the REPL.

(- 10 (* 2 3))
;; => 4

;; To break that down further, take the innermost expression and evaluate

(* 2 3)
;; => 6

;; Then substitute that expression with the result (your editor may also provide this feature, eg. in Spacemacs CIDER its `, e w`)

(- 10 6)
;; => 4


;; Answers summary
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;; Most readable answer
4
;; => 4

;; Overthought answers

16/4                                    ; A ratio type that equals 4
;; => 4

(* 2 2)
(fn [] (* 2 2))


;; Least valuable answer

(- 10 (* 2 3))
;; => 4

;; The question itself asks you not to use this as an answer
;; Very little is learned with this as an answer, except two identical things are identical
