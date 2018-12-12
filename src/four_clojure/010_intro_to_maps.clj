(ns four-clojure.010-intro-to-maps)

;; #10 Intro to maps
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;; Difficulty:	Elementary
;; Topics:

;; Maps store key-value pairs. Both maps and keywords can be used as lookup functions. Commas can be used to make maps more readable, but they are not required.

;; (= __ ((hash-map :a 10, :b 20, :c 30) :b))
;; (= __ (:b {:a 10, :b 20, :c 30}))


;; Deconstruct the problem
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;; Looking at ways to create a map in Clojure,
;; also know as a hash-map or a dictionary

;; The result can be seen by simply evaluating
;; the right hand side of the expression in the REPL


;; REPL experiments
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;; The `hash-map` function will construct a map from pairs of values
;; A map must contain zero or more pairs of values
;; The first value of pair is the key
;; The second value of the pair is a value
;; The key is used to look up the value in the map.

;; Here we create a map using the `hash-map` funtion
(hash-map :a 10 :b 20 :c 30)
;; => {:c 30, :b 20, :a 10}

;; Now we use that map as a function with a key as its argument,
;; returning the value that the key points too.
((hash-map :a 10 :b 20 :c 30) :b)
;; => 20

;; The above code can also be written specifically as:
({:c 30, :b 20, :a 10} :b)
;; => 20

;; Looking at the second test, we can see that the key can also be used as a function call
;; However, this only works when the key is a `keyword`, eg. it starts with a colon, `:`.
(:b {:a 10 :b 20 :c 30})

;; Using the map or keyword as a function call is a short-cut for using the `get` function.

(get {:a 10 :b 20 :c 30} :b)
;; => 20

;; The `get` function is often considered more readable, especially by those still
;; learning Clojure.
;; Using the short form is acceptable as simple in-line functions (lambda functions),
;; particularly where that function is passed to another function as an argument (eg. map, filter)

;; There is a similar function to `get` called `get-in` which elegantly helps you traverse a nested map.

;; You may have noticed I have not used commas `,` in my maps during the REPL experiments,
;; `,` are treated as white-space by Clojure and rarely used by Clojure developers.



;; Answers summary
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;; Most readable answer
20
