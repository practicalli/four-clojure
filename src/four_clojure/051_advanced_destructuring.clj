(ns four-clojure.051-advanced-destructuring)


;; #051 Advanced Destructuring
;;

;; Difficulty:	Easy
;; Topics:	destructuring

;; Here is an example of some more sophisticated destructuring.

;; (= [1 2 [3 4 5] [1 2 3 4 5]] (let [[a b & c :as d] __] [a b c d]))

;; Deconstruct the problem
;;

(= [1 2 [3 4 5] [1 2 3 4 5]] (let [[a b & c :as d] __] [a b c d]))


;; Lets see what the let statement does with an initial data set.
;; As the result contains [1 2 3 4 5], we will start with that data

(let [[a b & c :as d] [1 2 3 4 5]] [a b c d])


;; => [1 2 (3 4 5) [1 2 3 4 5]]

;; Well that worked... and we are done....

;; Well, lets just see what that is doing


;; REPL experiments
;;

;; a matches the first element in the collection of [1 2 3 4 5]

(let [a [1 2 3 4 5]] a)


;; => [1 2 3 4 5]

(let [[a] [1 2 3 4 5]] [a])


;; => [1]

;; b matches the second element in the collection
(let [[a b] [1 2 3 4 5]] [a b])


;; => [1 2]

;; c matches everything after the first and second elements, so [3 4 5]
(let [[a b & c] [1 2 3 4 5]] [a b c])


;; => [1 2 (3 4 5)]


;; and finally d matches the whole collection
;; and is placed at the end of the other results

(let [[a b & c :as d] [1 2 3 4 5]] [a b c d])


;; => [1 2 (3 4 5) [1 2 3 4 5]]


;; Answers summary
;;

[1 2 3 4 5]
