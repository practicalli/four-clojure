(ns four-clojure.121-sequs-horribilis)

;; # 112 Sequs horribilis
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;; Difficulty:	Medium
;; Topics:	seqs

;; Create a function which takes an integer and a nested collection of integers as arguments. Analyze the elements of the input collection and return a sequence which maintains the nested structure, and which includes all elements starting from the head whose sum is less than or equal to the input integer.

;; (=  (__ 10 [1 2 [3 [4 5] 6] 7])
;;     '(1 2 (3 (4))))
;; (=  (__ 30 [1 2 [3 [4 [5 [6 [7 8]] 9]] 10] 11])
;;     '(1 2 (3 (4 (5 (6 (7)))))))
;; (=  (__ 9 (range))
;;     '(0 1 2 3))
;; (=  (__ 1 [[[[[1]]]]])
;;     '(((((1))))))
;; (=  (__ 0 [1 2 [3 [4 5] 6] 7])
;;     '())
;; (=  (__ 0 [0 0 [0 [0]]])
;;     '(0 0 (0 (0))))
;; (=  (__ 1 [-10 [1 [2 3 [4 5 [6 7 [8]]]]]])
;;     '(-10 (1 (2 3 (4)))))


;; Deconstruct the problem
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;


;; REPL experiments
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;


;; Answers summary
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;




((fn [& args] (map true? args)) true false)



((fn [& args]
   (= 0 (count (filter false? args))))
 true false)

((fn [& args]
   (= 0 (count (filter false? args))))
 true true)


(= false false)
;; => false

(= false false)
;; => false

(= false false)
;; => false





[ 1 2 3 4 5 6 7 8 9]


[ [1 2 3] [4 5 6] [7 8 9]]
[ [1 2]   [4 5]   [7 8] ]
