(ns four-clojure.080-perfect-numbers)


;; #80 Perfect numbers
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;; Description
;; A number is "perfect" if the sum of its divisors equal the number itself. 6 is a perfect number because 1+2+3=6. Write a function which returns true for perfect numbers and false otherwise.

;; Tests
#_(= (__ 6) true)
#_(= (__ 7) true)
#_(= (__ 496) true)
#_(= (__ 500) false)
#_(= (__ 8128) true)


;; Decompose the challenge
;; Find the range of numbers up to the number being tested as perfect.
;; Identify which of the numbers in the range are divisors
;; Add all the numbers that are divisors to get a total
;; Test if total and number being tested are equal


;; Passing the first test
;; We can see how the first test works, as its mentioned in the description

;; Put simply, we can add the range of numbers that we know are the divisors,
;; then compare them with the potential perfect number.
(= (+ 1 2 3) 6)
;; => true

;; This expression also works for the second test
(= (+ 1 2 3) 7)
;; => false

;; So far we have just hard-coded the range of divisors, which we knew in advance.
;; Very few of us just know the range of divisors for numbers as large as 496.  So we need a way to calculate the divisors


;; Lets start with the first test case again, as its easy for us to check we are getting the right result.

;; The `range` function generates a range of interger values
;; We can specify a start point (zero by default)
;; The end of the range is around the half of the perfect number

;; So we can generate the whole range of numbers from 1 to 6
(range 1 6)

;; or just the range from 1 to half way
(range 1 3)
;; => (1 2)

;; Range is exclusive of the last number, so it stops before it gets 3.  However, if we use one number higher, then we get the right results.
(range 1 4)
;; => (1 2 3)

;; We can get the half way point by dividing the perfect number by 2
(range 1 (/ 6 2))
;; => (1 2)


;; although we have to add 1 to the result of dividing the perfect number by 2
(range 1 (+ 1 (/ 6 2)))
;; => (1 2 3)

(range 1 (/ 7 2))
;; => (1 2 3)

(range 1 3.5)
;; => (1 2 3)

;; When reviewing the code after the dojo, I tried a decimal number with the range function and it worked just fine
;; Looking at the code for range, it checks if the arguments are of type Long and calls the relevant function.
#_(if (and (instance? Long start) (instance? Long end))
    (clojure.lang.LongRange/create start end)
    (clojure.lang.Range/create start end))

;; The way I chose to deal with numbers that do not divide into the perfect number exactly is to use the quot function.
;; The quot function returns the number of times one number goes into another.  Any remainder is discarded.
(quot 6 2)
;; => 3

(quot 7 2)
;; => 3

;; combining range and quot we can get can get the range
;; Lets put them into an anonymous function and test it out

((fn [number] (range 1 (+ 1 (quot number 2)))) 6)
;; => (1 2 3)


((fn [number] (range 1 (+ 1 (quot number 2)))) 7)
;; => (1 2 3)

;; now we have the range of numbers we can add them up using reduce to iterate the + function over the sequence.
(reduce + ((fn [number] (range 1 (+ 1 (quot number 2)))) 6))
;; => 6

;; add a comaparison to see if the result of adding up the divisors passes the test
((fn [number]
   (= number
      (reduce + ((fn [number] (range 1 (+ 1 (quot number 2)))) number)))) 6)
;; => true

;; Passes test as we are expecting false
((fn [number]
   (= number
      (reduce + ((fn [number] (range 1 (+ 1 (quot number 2)))) number)))) 7)
;; => false


;; Using a larger number that is perfect fails the test though
((fn [number]
   (= number
      (reduce + ((fn [number] (range 1 (+ 1 (quot number 2)))) number)))) 496)
;; => false

;; So what is going on?  Lets take a look at what is happening inside this expression.

;; If we see the total number of all the numbers in the range we see that number is far higher than the perfect number.
(reduce + ((fn [number] (range 1 (+ 1 (quot number 2)))) 496))
;; => 30876

;; Looking at the range of numbers we see that some of these numbers are not divisors
((fn [number] (range 1 (+ 1 (quot number 2)))) 496)
;; => (1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20 21 22 23 24 25 26 27 28 29 30 31 32 33 34 35 36 37 38 39 40 41 42 43 44 45 46 47 48 49 50 51 52 53 54 55 56 57 58 59 60 61 62 63 64 65 66 67 68 69 70 71 72 73 74 75 76 77 78 79 80 81 82 83 84 85 86 87 88 89 90 91 92 93 94 95 96 97 98 99 100 ...)


;; If we filter out only those number that divide into our perfect number with a remainder of zero, then we will have a range of just divisors for the perfect number
(filter #(= 0 (rem 496 %))
        ((fn [number] (range 1 (+ 1 (quot number 2)))) 496))
;; => (1 2 4 8 16 31 62 124 248)

;; Adding this filter to our expression should now pass the test for any number
(fn [number]
  (= number
     (reduce + (filter #(= 0 (rem number %))
                       ((fn [number] (range 1 (+ 1 (quot number 2)))) number)))))


;; alternative - use ratio? as the filter function to remove all numbers that are not an integer value

;; If we use the divide function on our perfect number then we will get a ratio type for each number in the range that does not divide equally

;; For example
(/ 496 37)
;; => 496/37

;; So we could use a filter that checks if the result is a ratio

(filter #(ratio? (/ 496 %))
        ((fn [number] (range 1 (+ 1 (quot number 2)))) 496))
;; => (3 5 6 7 9 10 11 12 13 14 15 17 18 19 20 21 22 23 24 25 26 27 28 29 30 32 33 34 35 36 37 38 39 40 41 42 43 44 45 46 47 48 49 50 51 52 53 54 55 56 57 58 59 60 61 63 64 65 66 67 68 69 70 71 72 73 74 75 76 77 78 79 80 81 82 83 84 85 86 87 88 89 90 91 92 93 94 95 96 97 98 99 100 101 102 103 104 105 106 107 ...)

;; filter generates all the numbers from the range that are ratio? types, so we want the inverse of this.
;; We could use not

(filter #(not (ratio? (/ 496 %)))
        ((fn [number] (range 1 (+ 1 (quot number 2)))) 496))
;; => (1 2 4 8 16 31 62 124 248)


;; Or we can use the a function that does the inverse of filter, a function called remove
(remove #(ratio? (/ 496 %)))
((fn [number] (range 1 (+ 1 (quot number 2)))) 496)
;; => (1 2 4 8 16 31 62 124 248)


;; Further thoughts
;; Unless we need to optimise the processing of the range of numbers, we can just generate the whole range with a simpler expression
;; (range 1 number) instead of (range 1 (+ 1 (quot number 2)))

;; With a lazy sequence, we can still be fairly optimal in the way we work as the filter or remove function could be used to minimise the range being generated

(remove #(ratio? (/ 2305843008139952128 %))
        ((fn [number] (range 1 (+ 1 (quot number 2)))) 2305843008139952128))
