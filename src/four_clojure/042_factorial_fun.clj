(ns four-clojure.042-factorial-fun)


;; #042 Factorial Fun
;;

;; Difficulty:	Easy
;; Topics:	math

;; Write a function which calculates factorials.

;; (= (__ 1) 1)
;; (= (__ 5) 120)
;; (= (__ 8) 40320)


;; Deconstruct the problem
;;

;; factorials
;; the product of an integer and all the integers below it; e.g. factorial four ( 4! ) is equal to 24.
;; the product of a series of factors in an arithmetical progression.

;; https://en.wikipedia.org/wiki/Factorial

;; Product
;; We need to multiply all the numbers together to get the product
;; If we start at zero, then our result will always by zero,
;; as anything multiplied by zero is zero.

;; Examples
;; 4! = 4 × 3 × 2 × 1 = 24
;; 7! = 7 × 6 × 5 × 4 × 3 × 2 × 1 = 5040
;; 1! = 1
;; 0! = 1  ; as generally agreed in maths (makes sense if you work backwards and divide).

;; Usage
;; Factorials are used in many areas of mathematics, but particularly in Combinations and Permutations

;; Permutations Example:
;; How many ways can 7 people come 1st, 2nd and 3rd?
;; The list is quite long, if the people are a,b,c,d,e,f,g then the list includes abc,abd,abe,abf,abg,acb,acd,ace,acf,... etc up to gfe.

;; The formula is  7! divided by (7-3)!  =  7! divided by 4!

;; Let us write the multiplies out in full:

;; 7 × 6 × 5 × 4 × 3 × 2 × 1 divided by 4 × 3 × 2 × 1   =  7 × 6 × 5

;; When dividing one factorial by another, you simply take away the numbers that are in both progressions.
;; So the 4 × 3 × 2 × 1 is "dropped", leaving only 7 × 6 × 5.

;; 7 × 6 × 5  =  210

;; So there are 210 different ways that 7 people could come 1st, 2nd and 3rd.


;; Random trivia
;; There are 52! ways to shuffle a deck of cards.
;; There are about 60! atoms in the observable Universe.
;; 70! is just larger than a Googol (the digit 1 followed by one hundred zeros).
;; https://en.wikipedia.org/wiki/Googol



;; Algorithm for 4Clojure
;; Generate all the numbers from one to the number
;; Then multiply all the numbers together

;; REPL experiments
;;

;; lets take a low level procedural approach with loop and recur

(fn [number]
  (loop [current-number number
         total          1]  ; start at 1 because we are multiplying
    (if (<= 1 current-number)
      total
      (recur
        (dec current-number)
        (* total current-number)))))


;; Test this function with an argument
;; 5! should give 120 as a result
((fn [number]
   (loop [current-number number
          total          1]
     (if (>= 1 current-number)
       total
       (recur
         (dec current-number)
         (* total current-number)))))
 5)


;; Use some nicer abstractions
;;


;; `range` will generate an incremental set of numbers
;; range is exclusive of the end number
;; so we would need to increment the number given as an argument

(range 1 (inc 10))


(fn progression
  [number]
  (range 1 (inc number)))


((fn progression
   [number]
   (range 1 (inc number)))
 5)


;; => (1 2 3 4 5)


(fn factorial
  [number]
  (reduce
    *
    (range 1 (inc number))))


((fn factorial
   [number]
   (reduce
     *
     (range 1 (inc number))))
 5)


;; Answers summary
;;

(fn factorial
  [number]
  (reduce
    *
    (range 1 (inc number))))


;; or the golf score minimised code

#(reduce * (range 1 (inc %)))


;; Code Golf Score: 24

;; or
#(apply * (range 1 (inc %)))


;; Code Golf Score: 23


;; procedural approach - low level abstraction

(fn [number]
  (loop [current-number number
         total          1]  ; start at 1 because we are multiplying
    (if (<= 1 current-number)
      total
      (recur
        (dec current-number)
        (* total current-number)))))


;; other 4Clojure solutions
;;

;; Using iterate to create a progression from the number backwards
;; adding a condition via the `take-while` its a positive number

#(reduce * (take-while pos? (iterate dec %)))


;; iterate forward to generate the progression
;; use the argument to get just the size of progression needed
;; then calculate the product.
#(->> (iterate inc 1)
      (take %)
      (reduce *))


;; in the full function definition syntax

(fn factorial
  [number]
  (reduce
    *
    (take number (iterate inc 1))))


;; Factorial fun facts
;;

;; Six weeks is exactly 10! seconds (=3,628,800)

;; Seconds in 6 weeks:    60 × 60 × 24 × 7 × 6
;; Factor some numbers:   (2 × 3 × 10) × (3 × 4 × 5) × (8 × 3) × 7 × 6
;; Rearrange:   2 × 3 × 4 × 5 × 6 × 7 × 8 × 3 × 3 × 10
;; Lastly 3×3=9:    2 × 3 × 4 × 5 × 6 × 7 × 8 × 9 × 10

(* 60 #_seconds
   60 #_minutes
   24 #_hours
   7  #_days
   6  #_weeks)


;; 60 is also (* 2 3 10) or (* 3 4 5)
;; 24 is also (* 8 3)

;; replacing these calculations in our expressions

(* (* 2 3 10) #_seconds
   (* 3 4 5)  #_minutes
   (* 8 3)    #_hours
   7  #_days
   6  #_weeks)


;; sort the values in increasing numeric order
;; its mostly a progression for 10!

(* 2 3 4 5 6 7 8 3 3 10)


;; we have several 3 values, so we could combine
;; (* 3 3) equals 9

;; so replace again to get a complete progression
;; with 1 missing you still get the same result

(* 2 3 4 5 6 7 8 9 10)
