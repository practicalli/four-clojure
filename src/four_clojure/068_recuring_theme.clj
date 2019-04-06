(ns four-clojure.068-recuring-theme)

;; 068: Recurring Theme
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;; Difficulty:	Elementary
;; Topics:	recursion

;; Clojure only has one non-stack-consuming looping construct: recur. Either a function or a loop can be used as the recursion point. Either way, recur rebinds the bindings of the recursion point to the values it is passed. Recur must be called from the tail-position, and calling it elsewhere will result in an error.

;; (= __
;;    (loop [x 5
;;           result []]
;;      (if (> x 0)
;;        (recur (dec x) (conj result (+ 2 x)))
;;        result)))


;; Deconstruct the problem
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;; An opportunity to experiment with loop and recur


;; REPL experiments
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;


(loop [x      5
       result []]
  (if (> x 0)
    (recur (dec x) (conj result (+ 2 x)))
    result))

;; => [7 6 5 4 3]

;; The value of x is bound to 5 and result is bound to an empty vector.
;; If x is greater than zero, then call the loop again, passing two values
;; that replace the initial loop bindings.
;; The conj expression is evaluated before the loop is called again,
;; so the first value added to result is 7
;; When the value of x has be decremented to zero, the current value of
;; result is returned instead of calling the loop again.



;; Answers summary
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

[7 6 5 4 3]
