(ns four-clojure.001-nothing-but-the-truth)

;; Difficulty:	Elementary
;; Topics:

;; This is a clojure form. Enter a value which will make the form evaluate to true. Don't over think it! If you are confused, see the getting started page. Hint: true is equal to true.
;; test not run

;; (= __ true)


;; Deconstruct the problem
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;; This is a very simple challenge that allows you to explore what is true (or false).

;; true and false are boolean types in Clojure and can simply be used as a value and in an expression.

;; The simple answer is to use the boolean type called true.  However, we can explore other ways of expressing true and false values.


;; Answers summary
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;; Very Readable
true

;; Over thought but interesting
(not false)
;; => true

(not nil)
;; => true

(true? 1)
;; => false

(not (false? 1))
;; => true

((fn [] true))
;; => true
