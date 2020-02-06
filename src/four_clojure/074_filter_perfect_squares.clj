(ns four-clojure.074-filter-perfect-squares)


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; #74 Filter perfect squares

;; http://www.4clojure.com/problem/74

;; Given a string of comma separated integers, write a function which returns a new comma separated string that only contains the numbers which are perfect squares.

;; Tests
;; (= (__ "4,5,6,7,8,9") "4,9")
;; (= (__ "15,16,25,36,37") "16,25,36")

;; Initial idea:
;; Use a square root function to filter out the values that are not squares

;; Challenges
;; Values are in a string and need to be returned as a string
;; There is no square root function in clojure.core or libraries that would be available to 4clojure.


;; extracting numbers from a string
;; unfortunately read-string causes an error on evaluation

(read-string "4,5,6,7,8,9" #",")


;; Using clojure.string/split we can generate a vector of just the numbers
(clojure.string/split "4,5,6,7,8,9" #",")
;; => ["4" "5" "6" "7" "8" "9"]


;; The numbers are still as string types, so lets convert each number using the Java Integer/parseInt function.
(map #(Integer/parseInt %)
     (clojure.string/split "4,5,6,7,8,9" #","))
;; => (4 5 6 7 8 9)

;; Now we need to filter this result with a function that check for numbers that are perfect squares
