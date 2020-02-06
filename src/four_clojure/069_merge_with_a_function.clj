(ns four-clojure.069-merge-with-a-function)


;; #069 - Merge with a Function
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;; Difficulty:	Medium
;; Topics:	core-functions


;; Write a function which takes a function f and a variable number of maps. Your function should return a map that consists of the rest of the maps conj-ed onto the first. If a key occurs in more than one map, the mapping(s) from the latter (left-to-right) should be combined with the mapping in the result by calling (f val-in-result val-in-latter)

;; (= (__ * {:a 2, :b 3, :c 4} {:a 2} {:b 2} {:c 5})
;;    {:a 4, :b 6, :c 20})


;; (= (__ - {1 10, 2 20} {1 3, 2 10, 3 15})
;;    {1 7, 2 10, 3 15})


;; (= (__ concat {:a [3], :b [6]} {:a [4 5], :c [8 9]} {:b [7]})
;;    {:a [3 4 5], :b [6 7], :c [8 9]})


;; Deconstruct the problem
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;


;; REPL experiments
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;


(get :b {:a nil :b 2});; => nil
;; => nil

;; use an anonymous function to change the argument order
((fn [k m]
   (get m k)) :a {:a nil :b 2})
;; => nil
;; This should be nil as the value in the map is nil


((fn [k m]
   (get m k)) :b {:a nil :b 2})
;; => 2

((fn [k m]
   (get m k)) :c {:a nil :b 2})
;; => nil
;; This should not be nil as the test fails

;; Use a default value that is not nil for the get function
;; lets return false if the key is not in the map
((fn [k m]
   (get m k false)) :c {:a nil :b 2})
;; => false


(defn []
  (map inc [1 2 3 4])
  (map inc [1 2 3 4])
  (map inc [1 2 3 4])
  )



;; Answers summary
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
