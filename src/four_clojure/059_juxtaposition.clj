(ns four-clojure.059-juxtaposition)

;; 059 - Juxtaposition
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;; Difficulty:	Medium
;; Topics:	higher-order-functions core-functions
;; Restrictions: juxt


;; Take a set of functions and return a new function that takes a variable number of arguments and returns a sequence containing the result of applying each function left-to-right to the argument list.

;; (= [21 6 1] ((__ + max min) 2 3 5 1 6 4))
;; (= ["HELLO" 5] ((__ #(.toUpperCase %) count) "hello"))
;; (= [2 6 4] ((__ :a :c :b) {:a 2, :b 4, :c 6, :d 8 :e 10}))


;; Deconstruct the problem
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;; There is a special restrictions on juxt
;; which means that juxt is probably the answer.

(= [21 6 1] ((juxt + max min) 2 3 5 1 6 4))
;; => true

(= ["HELLO" 5] ((juxt #(.toUpperCase %) count) "hello"))
;; => true

(= [2 6 4] ((juxt :a :c :b) {:a 2, :b 4, :c 6, :d 8 :e 10}))
;; => true

;; Looking at the tests, what does juxt do?

;; juxt takes multiple functions as arguments
;; each function is applied to values
;; and the results are returned
;; Each function is applied to the original values
;; The results of each function are not used by the other functions

;; Clojure docs - juxt
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; https://clojuredocs.org/clojure.core/juxt

;; Takes a set of functions and returns a fn that is the juxtaposition
;; of those fns.

;; The returned fn takes a variable number of args, and
;; returns a vector containing the result of applying each fn to the
;; args (left-to-right).

;; ((juxt a b c) x) => [(a x) (b x) (c x)]


;; juxtaposition - dictionary definition
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; the fact of two things being seen or placed close together with contrasting effect.


;; Other examples of juxt
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

((juxt first count) "Practicalli Clojure Rocks")
;; => [\P 25]

(def separate
  "Separate values using a given function.
  filter will keep the matching values
  remove will keep values that do not match, removes those that do match"
  (juxt filter remove))

(separate pos? [-1 2 -5 10])
;; => [(2 10) (-1 -5)]

;; https://blog.klipse.tech/clojure/2017/04/22/clojure-juxt-some-reduced.html
;; http://blog.jenkster.com/2016/06/clojure-fu-with-juxt.html



;; source code for juxt function
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; https://github.com/clojure/clojure/blob/clojure-1.9.0/src/clj/clojure/core.clj#L2568

#_(defn juxt
    "Takes a set of functions and returns a fn that is the juxtaposition
  of those fns.  The returned fn takes a variable number of args, and
  returns a vector containing the result of applying each fn to the
  args (left-to-right).
  ((juxt a b c) x) => [(a x) (b x) (c x)]"
    {:added  "1.1"
     :static true}
    ([f]
     (fn
       ([] [(f)])
       ([x] [(f x)])
       ([x y] [(f x y)])
       ([x y z] [(f x y z)])
       ([x y z & args] [(apply f x y z args)])))
    ([f g]
     (fn
       ([] [(f) (g)])
       ([x] [(f x) (g x)])
       ([x y] [(f x y) (g x y)])
       ([x y z] [(f x y z) (g x y z)])
       ([x y z & args] [(apply f x y z args) (apply g x y z args)])))
    ([f g h]
     (fn
       ([] [(f) (g) (h)])
       ([x] [(f x) (g x) (h x)])
       ([x y] [(f x y) (g x y) (h x y)])
       ([x y z] [(f x y z) (g x y z) (h x y z)])
       ([x y z & args] [(apply f x y z args) (apply g x y z args) (apply h x y z args)])))
    ([f g h & fs]
     (let [fs (list* f g h fs)]
       (fn
         ([] (reduce1 #(conj %1 (%2)) [] fs))
         ([x] (reduce1 #(conj %1 (%2 x)) [] fs))
         ([x y] (reduce1 #(conj %1 (%2 x y)) [] fs))
         ([x y z] (reduce1 #(conj %1 (%2 x y z)) [] fs))
         ([x y z & args] (reduce1 #(conj %1 (apply %2 x y z args)) [] fs))))))




;; REPL experiments
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;; using higher order functions
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; return a function that reduces one or more functions over the same arguments (values)
;; and combines the results


;; In previous examples we have seen we can return a function as a result.

(fn higher-order-function [arg]
  (fn returned-function [value]
    (arg value)))

;; When we evaluate the higher order function with an argument,
;; then we get the returned function as the result
;; The returned function then takes another argument as the value
;; and returns a value as a result

(defn juxt-for-single-function-and-single-value
  "An implementation of a basic juxt function that will act upon a single value"
  [f]
  (fn [x]
    (f x)))


(defn juxt-for-multiple-functions-and-single-value
  "An implementation of a basic juxt function that will act upon a single value"

  ;; evaluated when called with 1 function
  ([f]
   (fn [x]
     (f x)))

  ;; evaluated when called with 2 functions
  ([f g]
   (fn [x]
     (let [result1 (f x)
           result2 (g x)]
       [result1 result2])))
  )


;; using a let function creates local names that we can manage without.
;; instead wrap the two calling functions in a vector


(defn juxt-for-multiple-functions-and-single-value
  "An implementation of a basic juxt function that will act upon a single value"

  ;; evaluated when called with 1 function
  ([f]
   (fn [x]
     (f x)))

  ;; evaluated when called with 2 functions
  ([f g]
   (fn [x]
     [(f x) (g x)]))
  )


;; we have a solution that should work with the second and third 4Clojure tests
;; lets write it as a 4Clojure solution



(fn
  ([f]
   (fn [x]
     (f x)))

  ([f g]
   (fn [x]
     [(f x) (g x)])))


;; 4Clojure test two

(((fn
    ([f]
     (fn [x]
       (f x)))
    ([f g]
     (fn [x]
       [(f x) (g x)])))

  #(.toUpperCase % ) count)

 "hello")
;; => ["HELLO" 5]



;; 4Clojure test three
;; we need to add other branch for three functions

(((fn
    ([f]
     (fn [x]
       (f x)))
    ([f g]
     (fn [x]
       [(f x) (g x)]))
    ([f g h]
     (fn [x]
       [(f x) (g x) (h x)])))

  ;; functions
  :a :b :c)

 ;; values the functions will use
 {:a 2, :b 4, :c 6, :d 8 :e 10})
;; => [2 4 6]


;; We still need a way to solve 4Clojure test 1
;; this test takes multiple values
;; a simple way to do this is place all values into a collection
;; as part of the fiction definition

(fn [& args]
  ,,,)

;; args within the function definition is a collection of all the values passed
;; when calling the function.


;; we can use the apply function to enable our supplied functions work
;; with multiple values in the collection
#_(apply f [1 2 3 4])



;; Refactor the above to work with multiple values,

(defn juxt-with-multiple-values
  "An implementation of the juxt function that will act upon a single value"

  ;; with one function we simply return a reducing function
  ([f]
   (fn [& xs]
     (apply f xs)))

  ;; with two functions, we combine each result in a vector
  ([f g]
   (fn [& xs]
     [(apply f xs) (apply g xs)]))

  ([f g h]
   (fn [& xs]
     [(apply f xs)
      (apply g xs)
      (apply h xs)])))
;; => #'four-clojure.059-juxtaposition/juxt-with-multiple-values


;; trying this with the first 4Clojure test


(((fn
    ([f]
     (fn [& xs]
       (apply f xs)))

    ;; with two functions, we combine each result in a vector
    ([f g]
     (fn [& xs]
       [(apply f xs) (apply g xs)]))

    ([f g h]
     (fn [& xs]
       [(apply f xs)
        (apply g xs)
        (apply h xs)])))

  + max min)

 2 3 5 1 6 4)
;; => [21 6 1]


;; lets try this in 4Clojure

(fn
  ([f]
   (fn [& xs]
     (apply f xs)))

  ;; with two functions, we combine each result in a vector
  ([f g]
   (fn [& xs]
     [(apply f xs) (apply g xs)]))

  ([f g h]
   (fn [& xs]
     [(apply f xs)
      (apply g xs)
      (apply h xs)])))


;; All tests pass, yay!


;; Refactor the answer
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;; The answer feels a bit too hard coded and its limited to three functions.
;; I am sure we can do something more elegant.


;; Rather than hard code numerous similar branches in our code,
;; we can capture all the given functions as a collect (just like we did with values)

(fn [& fs]
  ,,,)

;; What ever we pass to this function becomes a vector of those arguments

((fn [& fs]
   fs)
 + max min)


;; Rather than apply each the function in turn over the collection,
;; we could map the collection of values over the collection of functions

;; For example, with the first test we would need something like this


(map (fn [f] (apply f [2 3 5 1 6 4])) [+ max min])

;; or using the function definition short form syntax

(map #(apply % [2 3 5 1 6 4]) [+ max min])


;; Then wrap this up into an anonymous function that we return to pick up the collection of values

#_(fn [& values]
    (map #(apply % values) [+ max min]))

;; finally wrap that function to be returned in its own function,
;; so we can get the sequence of functions to be applied as arguments

#_(fn [& fns]
    (fn [& values]
      (map #(apply % values) fns)))


;; So our generic answer for all tests would be

(fn juxt-with-multiple-function
  [& fs]

  (fn return-function [& xs]
    (map (fn [f] (apply f xs)) fs)))


;; as above but using the short form syntax
(fn [& fs]
  (fn [& xs]
    (map #(apply % xs) fs)))



;; Answers summary
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(fn [& fs]
  (fn [& xs]
    (map #(apply % xs) fs)))

;; golf score: 36
