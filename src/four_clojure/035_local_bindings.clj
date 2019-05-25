(ns four-clojure.035-local-bindings)

;; #035 Local bindings
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;; Difficulty:	Elementary
;; Topics:	syntax

;; Clojure lets you give local names to values using the special let-form.

;; (= __ (let [x 5] (+ 2 x)))
;; (= __ (let [x 3, y 10] (- y x)))
;; (= __ (let [x 21] (let [y 3] (/ x y))))


;; Deconstruct the problem
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;; An introduction to local binding

;; Discuss binding of names and the difference between
;; local and shared bindings (let, defn)
;; local bindings in fn and loop


;; REPL experiments
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(let [x 5]
  (+ 2 x))
;; => 7

(let [x 3
      y 10]
  (- y x))
;; => 7


(let [x 21]
  (let [y 3]
    (/ x y)))
;; => 7


(let [x 21
      y 3]
  (/ x y))


(= (+ 2 5)
   (let [x 5]
     (+ 2 x)))


;; def
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(def x-coords 5)


;; defn
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defn my-function
  [arg]
  (str "I do very little with my argument, " arg))

(my-function "my little argument")
;; => "I do very little with my argument, my little argument"


;; loop/recur
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;; bindings are also used in loop/recur
;; Each time the loop is called, a new binding
;; is created, enabling iteration through a
;; collection of values

(loop [values    [1 2 3 4 5]
       max-value 0]
  (if (empty? values)
    max-value
    (recur (rest values)
           (if (> max-value (first values))
             max-value
             (first values)))))


;; Answers summary
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

7
