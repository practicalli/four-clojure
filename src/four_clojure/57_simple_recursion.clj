(ns four-clojure.57-simple-recursion)

;; 57 Simple Recursion
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;; Difficulty:	Elementary
;; Topics:	recursion

;; A recursive function is a function which calls itself.
;; This is one of the fundamental techniques used in functional programming.

(= __ ((fn foo [x] (when (> x 0) (conj (foo (dec x)) x))) 5))



;; Deconstruct the problem
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;


;; REPL experiments
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;


(defn my-func-name
  "doc string
  arguments are..."
  [arg1 arg2]
  (,,,  ))

(fn my-inline-func [arg1 arg2]
  (,,,))

(fn my-inline-func [arg1 arg2]
  (my-inline-func (inc 1) 2))



(when (> x 0)
  (conj (foo (dec x)) x))


((fn foo [x]
   (when (> x 0)
     (conj (foo (dec x)) x)))

 5)
;; => (5 4 3 2 1)


;; if you expand the recursive function call
;; then its like you have written code as follows

(fn foo [x]
  (when (> x 0)
    (conj (when (> x 0)
            (conj (when (> x 0)
                    (conj  (when (> x 0)
                             (conj  (when (> x 0)
                                      (conj  x))x))x)) x)) x)))




;; Answers summary
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

'(5 4 3 2 1)

[5 4 3 2 1]
