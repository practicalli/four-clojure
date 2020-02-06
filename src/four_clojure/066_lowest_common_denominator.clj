(ns four-clojure.066-lowest-common-denominator)

;; problem 66 - lowest common denominator
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;; Given two integers, write a function which returns the greatest common divisor.

;; (= (__ 2 4) 2)

;; (= (__ 10 5) 5)

;; (= (__ 5 7) 1)

;; (= (__ 1023 858) 33)

;; Thoughts

;; Use range to generate a sequence of whole numbers lazily
;; use each number in turn to see if its a common divisor for all the arguments.

;; Lets start with 2 and 4, which has a divisor of 2
;; quot give the number of times a number can be divided.
;; rem shows the remainder when dividing
(quot 2 2)
;; => 1

(quot 4 2)
;; => 2

(rem 2 2)
;; => 0

(rem 3 2)
;; => 1

(rem 4 2)
;; => 0

;; so when we divide all the arguments and get a remainder of zero, we have a winner

(loop
    (let [denominator 2]))

(filter odd?
        (map #(rem % 2) [2 3 4 5]))


((fn [numbers-to-divide denominator]

   (map #(rem % divide-by) numbers-to-divide)) [2 4] 2)

(defn lowest-denominator [numbers-to-divide]
  (loop [divide-by denominator
         result 0]
    (if (= (rem 4 divide-by) 0)
      (str "denominator is: " 2))))
