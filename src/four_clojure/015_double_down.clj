(ns four-clojure.015-double-down)


;; #015 Double down
;;

;; Solutions
;; Difficulty:	Elementary
;; Topics:

;; Write a function which doubles a number.

;; (= (__ 2) 4)
;; (= (__ 3) 6)
;; (= (__ 11) 22)
;; (= (__ 7) 14)

;; Deconstruct the problem
;;

;; A chance to use the function definitions we have just learnt

;; we need to write a function that takes a single argument

;; (fn [arg])

;; The behaviour of the function is to double the value of the argument,
;; so we can do this by multiplying by 2

(fn [arg]
  (* arg 2))


;; Now we can call this definition with the 4Clojure tests

((fn [arg]
   (* arg 2))
 2)


;; => 4

;; and this works for all the tests.

;; we can also write the function definition using the short form
#(* % 2)


;; and call the short form with the same test data
(#(* % 2) 2)


;; => 4

;; Answers summary
;;

;; Normal function definition
(fn [arg]
  (* arg 2))


;; short form function definition
#(* % 2)
