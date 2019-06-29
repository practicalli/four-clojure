(ns four-clojure.052-intro-to-destructuring)

;; #052 Intro to Destructuring
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;; Difficulty:	Elementary
;; Topics:	destructuring

;; Let bindings and function parameter lists support destructuring.

;; (= [2 4] (let [[a b c d e] [0 1 2 3 4]] __))


;; Deconstruct the problem
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;; This challenge introduces us to the basics of destructuring in clojure.
;; https://clojure.org/guides/destructuring

;; Destructuring is a way to get values from collections by using pattern matching.
;; Destructuring is used to get values from function arguments as local names
;; and also used with the `let` function for the same purpose.

;; Lets look at the examples in
;; https://clojure.org/guides/destructuring


;; Define a name for a collection that represents the x & y co-ordinates for a line,
;; for a start and stop point of the line.

(def my-line [[5 10] [10 20]])
;; => #'four-clojure.052-intro-to-destructuring/my-line


;; We can use the let function to pull out the individual x and y values

(let [point1 (first my-line)
      point2 (second my-line)
      x1     (first point1)
      y1     (second point1)
      x2     (first point2)
      y2     (second point2)]
  (str "Line from (" x1 "," y1 ") to (" x2 ", " y2 ")"))
;; => "Line from (5,10) to (10, 20)"


;; This leads to a lot of code and we can simplify this using destructuring
;; We use a vector as our pattern to destructure `my-line`
;; The names we use in our vector pattern become the local names we can use in the rest of the `let` function.

(let [[point1 point2] my-line
      [x1 y1]         point1
      [x2 y2]         point2]
  (str "Line from (" x1 "," y1 ") to (" x2 ", " y2 ")"))
;; => "Line from (5,10) to (10, 20)"

;; we can use a more specific pattern and simplify the code even further

(let [[[x2 y1] [x2 y2]] my-line]
  (str "Line from (" x1 "," y1 ") to (" x2 ", " y2 ")"))
;; => "Line from (5,10) to (10, 20)"

;; So using the right pattern we can simplify the extraction of values into local names.


;; REPL experiments
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;; Although we can probably guess the values for a to e,
;; lets see what the repl tells us

(let [[a b c d e] [0 1 2 3 4]]
  (str "a: " a " b: " b " c: " c " d: " d " e: " e))
;; => "a: 0 b: 1 c: 2 d: 3 e: 4"

;; we need to think how to return the right values
;; we need c and e.

;; Using them both together as individual evaluations will only return the last one

(let [[a b c d e] [0 1 2 3 4]]
  c e)
;; => 4

;; If we evaluate c and 3 inside a collection, then both are evaluated and returned in that collection

(let [[a b c d e] [0 1 2 3 4]]
  [c e])
;; => [2 4]


;; Answers summary
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

[c e]
