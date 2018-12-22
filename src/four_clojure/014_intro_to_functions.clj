(ns four-clojure.014-intro-to-functions)

;; #014 Intro to functions
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;; Difficulty:	Elementary
;; Topics:

;; Clojure has many different ways to create functions.

;; (= __ ((fn add-five [x] (+ x 5)) 3))
;; (= __ ((fn [x] (+ x 5)) 3))
;; (= __ (#(+ % 5) 3))
;; (= __ ((partial + 5) 3))


;; Deconstruct the problem
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;; `defn` is used to define a function that will be used by one or more other functions in a namespace

;; Other functions are lambdas, which are used inline of expressions.
;; To call a lambda function we need to place it at the start of a list

;; defining a function as a lambda
(fn name-optional [arg] (str arg))

;; To use this function we need to put it into a list and give it an argument, the second element of the list

((fn name-optional [arg] (str arg)) "I love lambda")
;; => "I love lambda"


;; We can call the first test and see the result

((fn add-five [x] (+ x 5)) 3)
;; => 8

;; There is a short version of the lambda function definition, using the `#()` reader macro.
;; The arguments in this form are not named, but % placeholders are used for the argument values.
;; % represents the first argument, as does %1
;; %2 represents the second argument and so on.
#(+ % 5)

;; and just like the normal form of function definition, we need to place it in a list to call the function.
(#(+ % 5) 3)

;; We can see what Clojure does when it sees this short form by using the function macroexpand,
;; as the `#()` is a macro, then the Clojure reader passes the code to the macro reader.
;; The macro reader expands the macro into Clojure code and passes it back to the Clojure reader.
(macroexpand
 '#(+ % 5))
;; => (fn* [p1__13678#] (+ p1__13678# 5))


;; partial is a function that can be used like a lambda function,
;; except it will delay the execution of the function until it has the arguments
;; partial is a simple abstraction over a lambda function
;; however you are not able to control the order placement of the argument
;; so sometimes a lambda function will be needed instead.

((partial + 5) 3)
;; => 8


;; Answers summary
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

8
