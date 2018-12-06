(ns four-clojure.004-intro-to-lists)

;; Intro to Lists
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;; Difficulty:	Elementary
;; Topics:


;; Lists can be constructed with either a function or a quoted form.
;; test not run

;; Test cases
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;; (= (list __) '(:a :b :c))


;; Deconstruct the problem
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;; The list is the most used data structure in Clojure, as the language is defined and used within the context of lists.

;; This problem is simply making us aware of the syntax for creating a list, using `()` and the `list` function.


;; REPL experiments
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;; Using the quote in front of a list tells Clojure not to evaluate the first element as a function call.
;; So just the list is returned.
'(:a :b :c)
;; => (:a :b :c)

;; So a list can be defined explictly, using the `()` parens, or
;; less commonly, a list can be defined using the `list` function, passing individual values to make up that list.
;; A list does not care about the type of things added to a list (so long as they are any legal type in Clojure).
;; A list can therefore contain a multitude of different types in the same list.


;; Some interesting results of evaluating a list where the first element is a keyword:

(:a :b :c)
;; => :c

;; I am not sure about this result.
;; As the expression is a list, the first element is a function call.
;; :a is the first element and is of type keyword.  A keyword called as a function will return itself.

;; Assumption: when calling :a as a function, the argument :b is taken as the namespace and :c as the keyword
;; so the result returned is the :c keyword.


;; Calling a keyword as a function with any other number of arguments fails, so its assumed the keyword function signature
;; is the same as calling the function called `keyword`.

(:a :b)
;; => nil

(:a :b :c :d)
;; Error: wrong number of arguments passed to keyword :a

(:a)
;; Error: wrong number of arguments passed to keyword :a

(:a :b :c :d :e)
;; Error: wrong number of arguments passed to keyword :a



;; Answers summary
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;; Most readable answer
:a :b :c

;; Overthought answers

(keyword "a") (keyword "b") (keyword "c")

;; Least valuable answer
