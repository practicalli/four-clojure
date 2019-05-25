(ns four-clojure.038-maximum-value)

;; #38 Maximum value
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;; Difficulty:	Easy
;; Topics:	core-functions
;; Restrictions: max max-key

;; Write a function which takes a variable number of parameters and returns the maximum value.

;; (= (__ 1 8 3 4) 8)
;; (= (__ 30 20) 30)
;; (= (__ 45 67 11) 67)

;; Deconstruct the problem
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;; We need to define a function that takes a variable number of integer arguments

;; Our function should return the largest value from those arguments.

;; We cannot use the `max` or `max-key` functions to find the maximum value.

;; We could iterate (loop/recur) over all the values, comparing each in turn.

;; As our values are numeric, they can be sorted by their value and the maximum found


;; REPL experiments
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;; A function is usually defined with a specific number of arguments

(defn [one-arg]
  (str "I have only one argument, " one-arg))

(fn [one-arg]
  (str "I have only one argument, " one-arg))

;; If there are multiple arguments, you can include them specifically

;; or you can pass a collection of values


;; The ampersand character `&` means the next name is for all the other arguments

;; `&` can be used by itself or with specifically named arguments

;; Defining a function that takes a variable number of arguments

(defn variable-args [one-arg & any-other-args]
  (str "A specific argument, " one-arg " and all the other arguments" any-other-args))

(variable-args "one")
;; => "A specific argument, one and all the other arguments"

(variable-args "one" 2 3 4 5 6)
;; => "A specific argument, one and all the other arguments(2 3 4 5 6)"


(defn always-variable [& all-the-arguments]
  (str "All the arguments in one collection: " all-the-arguments))


;; loop recur approach
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;; We could use loop/recur

;; Take all arguments as a collection and pass that to the loop
;; recur until all those values are processed
;; if the next value in the collection is bigger than the max-value,
;; then update the max-value

;; 1 8 3 4

(fn [& args]
  (loop [values    args
         max-value 0]
    (if (empty? values)
      max-value
      (recur (rest values)
             (if (> max-value (first values))
               max-value
               (first values))))))

;; Lets test this code by calling it with the values from the first 4Clojure test

((fn [& args]
   (loop [values    args
          max-value 0]
     (if (empty? values)
       max-value
       (recur (rest values)
              (if (> max-value (first values))
                max-value
                (first values))))))
 1 8 3 4)
;; => 8

;; There is a lot of code though and it is very procedural (not very functional)




;; Finding the value by sorting
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;; we cant pass individual values to sort
(sort 1 8 3 4)
;; clojure.lang.ArityException
;; Wrong number of args (4) passed to: core/sort

(fn [& arguments]
  (sort arguments))

;; but we can sort a collection
(sort [1 8 3 4])
;; => (1 3 4 8)

;; with a sorted collection we can get the maximum number
;; as it will be the last one
(last (sort [1 8 3 4]))
;; => 8

;; we can put this behaviour into a function definition
(fn [& args]
  (last (sort [1 8 3 4])))

;; The [& args] part of the definition creates a local collection
;; of all the arguments bound to the name args


;; Call our function definition with arguments to test it
((fn [& args] (last (sort args))) 1 8 3 4)
;; => 8


;; Answers summary
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(fn [& args] (last (sort args)))

;; or the short-hand for
#(last (sort &))
