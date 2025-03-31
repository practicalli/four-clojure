(ns four-clojure.046-flipping-out)


;; #46 - Flipping out
;;
;; http://www.4clojure.com/problem/46

;; Difficulty:	Medium
;; Topics:	higher-order-functions

;; Write a higher-order function which flips the order of the arguments of an input function.

;; (= 3 ((__ nth) 2 [1 2 3 4 5]))
;; (= true ((__ >) 7 8))
;; (= 4 ((__ quot) 2 8))
;; (= [1 2 3] ((__ take) [1 2 3 4 5] 3))

;; Hint
;; A lesson in calling funcitons and defining functions

;; Clojure syntax - functions that return functions
;;
;; How to write a function that returns a function


;; (fn higher-order
;;   [arg]
;;   (fn [a b]
;;     (+ a b)))

;; (higher-order intial val-a val-b)
;; ((fn [a b]
;;    (+ a b))
;;  val-a val-b)

;; (fn [arg]
;;   (fn [arg2 arg3]
;;     (str "The outer function returns the inner function definition")))

;; This can go very deep, although if you do then there may be an abstraction you missed

;; (fn [arg]
;;   (fn [arg1 arg2]
;;     (fn [arg3 arg4 arg5]
;;       (str "Matryoshka dolls, also known as Russian nesting dolls, stacking dolls"))))

;; Now we know how to write a function that returns a function

;; deconstructing the problem
;;

;; We need to define a function that takes one argument,
;; and returns a function definition

(fn [function]
  (fn []))


;; our return function should do something with the argument it received
;; as the argument is a function we want to call, then we should put it in a list

(fn [function]
  (fn []
    (function)))


;; we are not finished yet, but if we use data from the first test,
;; and called out function, then we would get:

;; (nth 2 [1 2 3 4 5])

;; This wouldnt work because the arguments are in the wrong order.
;; Also the function we return is not taking arguments, so would cause an error when called

;; So lets get our function definition to return a function that takes two arguments

(fn [function]
  (fn [arg1 arg2]
    (function arg2 arg1)))


;; We are almost there.

;; The function we return needs to swap around the arguments
;; otherwise we get the wrong results

;; We could use the `reverse` function to swap the arguments around
;; if we get the arguments as a collection,
;; changing our function signature to (fn [& args])

(reverse [:arg1 :arg2])


;; => (:arg2 :arg1)

(reverse [2 [1 2 3 4 5]])


;; => ([1 2 3 4 5] 2)

;; This adds more complexity to our code and we would have to apply our function
;; over our reversed arguments as they are now in a collection.

;; However, we can make the swap by simply changing the order of the arguments in the return function body

(fn [function]
  (fn [arg1 arg2]
    (function arg2 arg1)))


;; Answers summary
;;

(fn [function]
  (fn [arg1 arg2]
    (function arg2 arg1)))


;; Lowest golf score
(fn [f] #(f %2 %1))


;; NOTE: nesting the #() inside another #() just gets very confusing and probably will not work correctly
