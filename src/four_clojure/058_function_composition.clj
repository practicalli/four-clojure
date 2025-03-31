(ns four-clojure.058-function-composition)


;; #058 function composition
;;

;; Difficulty:	Medium
;; Topics:	higher-order-functions core-functions
;; Special Restrictions: comp

;; Write a function which allows you to create function compositions. The parameter list should take a variable number of functions, and create a function that applies them from right-to-left.

;; (= [3 2 1] ((__ rest reverse) [1 2 3 4]))
;; (= 5 ((__ (partial + 3) second) [1 2 3 4]))
;; (= true ((__ zero? #(mod % 8) +) 3 5 7 9))
;; (= "HELLO" ((__ #(.toUpperCase %) #(apply str %) take) 5 "hello world"))


;; Deconstruct the problem
;;

;; This challenge helps you understand functional composition,
;; an important aspect of learning how to write Clojure elegantly.

;; We are restricted from using `comp` for the solution
;; as it would just be the answer

(= [3 2 1] ((comp rest reverse) [1 2 3 4]))


;; => true

(= 5 ((comp (partial + 3) second) [1 2 3 4]))


;; => true

(= true ((comp zero? #(mod % 8) +) 3 5 7 9))


;; => true

(= "HELLO" ((comp #(.toUpperCase %) #(apply str %) take) 5 "hello world"))


;; => true

;; In 4Clojure #20 we also solve with comp:
;; Write a function which returns the second to last element from a sequence.

;; (= (__ (list 1 2 3 4 5)) 4)
;; (= (__ ["a" "b" "c"]) "b")
;; (= (__ [[1 2] [3 4]]) [1 2])

(comp second reverse)


;; Using the first 4Clojure test we would have

((comp second reverse) (list 1 2 3 4 5))


;; => 4


;; So we need to write our own version of `comp`
;; which will teach us more about how `comp` actually works.


;; Sometimes looking at the source code of a function
;; will help us write our own version
;; https://github.com/clojure/clojure/blob/clojure-1.9.0/src/clj/clojure/core.clj#L2549

#_(defn comp
    "Takes a set of functions and returns a fn that is the composition
  of those fns.  The returned fn takes a variable number of args,
  applies the rightmost of fns to the args, the next
  fn (right-to-left) to the result, etc."
    {:added  "1.0"
     :static true}
    ([] identity)
    ([f] f)
    ([f g]
     (fn
       ([] (f (g)))
       ([x] (f (g x)))
       ([x y] (f (g x y)))
       ([x y z] (f (g x y z)))
       ([x y z & args] (f (apply g x y z args)))))
    ([f g & fs]
     (reduce1 comp (list* f g fs))))


;; The source code may seem a bit complex, but it can be broken down
;; to something simpler


;; Theory: Functions returning functions
;;

;; When calling a function, it always returns a value.

;; That value may be a simple value like a number or a string or even nil.
;; Or that value may be a collection such as a map, vector, set or list.

;; A function can also return a function definition,
;; as when the function definition is evaluated it returns a value.

;; You could even have a function that returns a function which returns a function...
;; but at some point this would get very confusing for the programmers.

;; This approach is called higher order functions

;; `comp` returns a function definition when given
;; more than one function as an argument

;; rather than write our own function definition, we can use higher order function approch and compose our functions together



;; Polymorphism - many forms
;;

;; To understand comp we also need to understand polymorphism in Clojure

;; `comp` is a function that takes any number of functions and composes them together.

;; `comp` returns a fn that is the composition of those fns.  The returned fn takes a variable number of args,

;; Arguments are given first to the rightmost function, the result of which is passed back as an argument to the other functions in turn


;; If we call `comp` with no arguments,
;; then this part of the `comp` definition is evaluated
([] identity)


;; For example
((comp) [1 2 3 4 5])


;; This returns the function `identity`,
;; When calling `identity` with an argument, it will return the argument.


;; Calling `comp` with a single argument,
;; will return that argument assuming it is a function that can be called.

([f] f)


;; For example:
((comp first) [1 2 3 4 5])


;; Calling `comp` with two functions,
;; will return a function definition,
;; itself a polymorphic function,
;; which can take different numbers of arguments

([f g]
 (fn
   ([] (f (g)))
   ([x] (f (g x)))
   ([x y] (f (g x y)))
   ([x y z] (f (g x y z)))
   ([x y z & args] (f (apply g x y z args)))))


;; For example
((comp rest reverse) [1 2 3 4])


;; => (3 2 1)


;; Calling `comp` with more than two arguments,
;; will reduce comp over all those arguments
([f g & fs]
 (reduce1 comp (list* f g fs)))


;; Example from 4Clojure #19 Last Element
;;

;; Write a function which returns the last element in a sequence.
;; Special restriction: `last`
;; (= (__ [1 2 3 4 5]) 5)
;; (= (__ '(5 4 3)) 3)
;; (= (__ ["b" "c" "d"]) "d")

;; We can use `comp` to solve this by composing `reverse` and `first`

(comp first reverse)


;; When executing this code, `comp` returns a function definition
;; that will be used with the arguments (in our case, the collection).

;; The function definition `comp` returns adds the functions passed to it as arguments

(fn
  ([] (first (reverse)))
  ([x] (first (reverse x)))
  ([x y] (first (reverse x y)))
  ([x y z] (first (reverse x y z)))
  ([x y z & args] (first (apply reverse x y z args))))


;; This may look a little complicated at first, as its a polymorphic function.  It does different behaviour depending on the number of arguments passed to the function.

;; calling this with our 4Clojure test data, the function path with one argument is called
([x] (first (reverse x)))


;; lets call the function returned by comp with the collection argument from our 4Clojure exercise.
((fn
   ([] (first (reverse)))
   ([x] (first (reverse x)))
   ([x y] (first (reverse x y)))
   ([x y z] (first (reverse x y z)))
   ([x y z & args] (first (apply reverse x y z args))))
 [1 2 3 4 5])


;; => 5



;; Writing our own function
;;


;; Write a function that returns a function definition

(fn []
  (fn [] "A function definition that doesnt do anything interesting"))


;; Calling our example function, returns a function
((fn []
   (fn [] "A function definition that doesnt do anything interesting")))


;; Calling the returned function definition, returns the string result.
(((fn []
    (fn [] "A function definition that doesnt do anything interesting"))))


;; Write a function definition that returns a polymorphic function definition

(fn []
  ([] "Nothing to see here")
  ([f] (str "You wanted to use " f))
  ([f g]
   (fn [x]
     (str "If I were comp, then I would call " g " on argument x"
          " and then call " f "on the result"))))


;; To solve the first test in this challenge

(fn
  ([] "Nothing to see here")
  ([f] (str "You wanted to use " f))
  ([f g]
   (fn [x]
     (f (g x)))))


;; Lets use this function definition in the first test

(((fn
    ([] "Nothing to see here")
    ([f] (str "You wanted to use " f))
    ([f g]
     (fn [x]
       (f (g x)))))
  rest reverse)
 [1 2 3 4])


;; This passes the first two tests in the 4Clojure challenge
;; as they both use two functions

#_(= [3 2 1]
     ((__ rest reverse) [1 2 3 4]))


#_(= 5
     ((__ (partial + 3) second) [1 2 3 4]))


;; The third and fourth tests take 3 arguments
;; so we could simply add another expression to call
;; when 3 arguments are used to call the function

(fn
  ([] "Nothing to see here")
  ([f] (str "You wanted to use " f))
  ([f g]
   (fn [x]
     (f (g x))))
  ([f g h]
   (fn [x]
     (f (g (h x))))))


;; But we see that we also have multiple arguments...

;; We can use the & in the arguments list
;; to turn the arguments into a collection
;; then use reduce to get a single value
;; when calling the first function on the value
;; (the last function in the list we gave to the comp function)

(fn
  ([] "Nothing to see here")
  ([f] (str "You wanted to use " f))
  ([f g]
   (fn [x]
     (f (g x))))
  ([f g h]
   (fn [& x]
     (f (g (reduce h x))))))


;; This gives a golf score of 104



(((fn
    ([] "Nothing to see here")
    ([f] (str "You wanted to use " f))
    ([f g]
     (fn [x]
       (f (g x))))
    ([f g h]
     (fn [& x]
       (f (g (reduce h x))))))
  zero? #(mod % 8) +)
 3 5 7 9)


;; Our code is quite brittle though
;; so we could make it more flexible for multiple functions

(fn [& x]
  ;; a collection of functions to call

  (fn [& y]
    ;; a collection of values to use with the functions

    (reduce
      (fn [last-function rest-of-functions]

        rest-of-functions last-function)

      (apply (last x) y)  ; The last function from the collection of functions

      (rest (reverse x))  ; The rest of the collections of functions in reverse order
      )))


;; Using the syntax sugar for the function definition used with reduce.

(fn [& x]
  (fn [& y]
    (reduce #(%2 %1)
            (apply (last x) y)  ; %1
            (rest (reverse x))  ; %2
            )))


;; Calling the function with test 3 from this 4Clojure challenge

(((fn [& x]
    (fn [& y]
      (reduce #(%2 %1)
              (apply (last x) y)  ; %1
              (rest (reverse x))  ; %2
              )))
  zero? #(mod % 8) +)
 3 5 7 9)


;; => true


;; Answers summary
;;


(fn [& x]
  (fn [& y]
    (reduce #(%2 %1)
            (apply (last x) y) (rest (reverse x)))))


;; Coding Golf Score: 62



;; Other interesting solutions
;;


;; into caught my eye, but otherwise it seems strange...
#(fn [& x]
   ((reduce
      (fn [v f] [(apply f v)])
      x
      (into () %&))
    0))
