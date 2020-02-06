(ns four-clojure.134-a-nil-key)

;; 134 A nil key
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; http://www.4clojure.com/problem/134


;; Write a function which, given a key and map, returns true iff the map contains an entry with that key and its value is nil.

#_(true?  (__ :a {:a nil :b 2}))
#_(false? (__ :b {:a nil :b 2}))
#_(false? (__ :c {:a nil :b 2}))

;; Deconstruct the problem
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;


;; REPL experiments
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;


(nil? nil)
;; => true

(nil? 2)
;; => false

;; using get doesnt work by itself, as the arguments are in the wrong order
(get :a {:a nil :b 2})
;; => nil

(get :b {:a nil :b 2})
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


(#(if (nil? (get %2 %1 false))
    true
    false) :a {:a nil :b 2})






(fn [k m]
  (if (= nil (get m k false))
    true
    false))



;; Answers summary
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

#(if (nil? (get %2 %1 false)) true false)


#(if (nil? (%2 %1 0)) true false)

(#(nil? (%2 % 1))
  :a {:a nil :b 2})
;; => true


(#(nil? (%2 % 0))
  :a {:a nil :b 2})
