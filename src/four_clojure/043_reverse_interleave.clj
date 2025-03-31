(ns four-clojure.043-reverse-interleave)


;; 43 - Reverse interleaving
;;

;; Difficulty:	Medium
;; Topics:	seqs

;; Write a function which reverses the interleave process into x number of subsequences.

;; (= (__ [1 2 3 4 5 6] 2) '((1 3 5) (2 4 6)))
;; (= (__ (range 9) 3) '((0 3 6) (1 4 7) (2 5 8)))
;; (= (__ (range 10) 5) '((0 5) (1 6) (2 7) (3 8) (4 9)))


;; Deconstruct the problem
;;

;; Given a collection, return that collection as the number of sub-sequences

;; All the given numbers divide equally, so we dont need to manage uneven sub-sequences

;; Values from each sub-sequence should be interleaved

;; REPL experiments
;;

;; The challenge says reverse interleave,
;; so lets see if there is an interleave funciton

;; https://clojuredocs.org/clojure.core/interleave

;; clojure.core/interleave
;; []
;; [c1]
;; [c1 c2]
;; [c1 c2 & colls]
;; Added in 1.0
;; Returns a lazy seq of the first item in each coll, then the second etc.


(interleave)

(interleave [1 2])


;; => (1 2)

(interleave [1 2] [3 4])


;; => (1 3 2 4)

(interleave [1 2] [3 4] [5 6])


;; => (1 3 5 2 4 6)


;; Change the shape of our collection so it works with interleave


;; partition
;; partition will split a collection into sub-sequences

(partition 2 [1 2 3 4 5 6])


;; => ((1 2) (3 4) (5 6))


;; This gives us the right shape for our data for interleave,
;; although its actually wrapped in a sequence.
;; the sequence makes it a single argument to interleave

;; we want this
(interleave [1 2] [3 4] [5 6])


;; but actually we have this instead

(interleave '([1 2] [3 4] [5 6]))


;; interleave will not work on the sequence returned by parttion


(map interleave (partition 3 [1 2 3 4 5 6]))


(apply interleave (partition 3 [1 2 3 4 5 6]))


;; We could take a more manual approach, using `first` and `second` to get each sub-sequence

(map first (partition 2 [1 2 3 4 5 6]))


;; => (1 3 5)

(map second (partition 2 [1 2 3 4 5 6]))


;; => (2 4 6)


;; So we can get the first and second parts separately, but what about at the same time

(let [values (partition 2 [1 2 3 4 5 6])
      one    (map first values)
      two    (map second values)]
  [one two])


;; or we can use a `for` expression instead of `map` to create the values in parallel

(for [values (partition 2 [1 2 3 4 5 6])
      :let   [one (first values)
              two (second values)]]
  [one two])


;; => ([1 2] [3 4] [5 6])


(for [values (partition 2 [1 2 3 4 5 6])
      :let   [one (first values)
              two (second values)]]
  (interleave [one two]))


;; => ((1 2) (3 4) (5 6))



(let [values (partition 2 [1 2 3 4 5 6])
      one    (map first values)
      two    (map second values)]
  (conj one two))


;; => ((2 4 6) 1 3 5)

(let [values (partition 2 [1 2 3 4 5 6])
      one    (map first values)
      two    (map second values)]
  (concat one two))


;; => (1 3 5 2 4 6)


(let [values (partition 2 [1 2 3 4 5 6])
      one    (map first values)
      two    (map second values)]
  (list one two))


;; so this passes for the first test, but other tests have more partitions


;; Using `list`
;;

;; In the last expression we used a list, perhaps this gives us another approach

;; if we can map the list function over each of the partitions
;; then we can can generate the right values

(map list '(1 2) '(3 4) '(5 6))


;; => ((1 3 5) (2 4 6))



;; this is just like adding values from collections together with `+`
(map + '(1 2) '(3 4) '(5 6))


;; => (9 12)



;; we cant do this directly because partition returns
;; a single list containing partitions
;; so we dont get the right answer


(map list ('(1 2) '(3 4) '(5 6)))

(map list (partition 2 [1 2 3 4 5 6]))


;; => (((1 2)) ((3 4)) ((5 6)))



;; applying the list is not quite right either
(apply list (partition 2 [1 2 3 4 5 6]))


;; however if we use the way that apply works, then we can
;; create an expression with map list that
;; has the right shape of arguments (individual collections)

(apply map list (partition 2 [1 2 3 4 5 6]))


;; => ((1 3 5) (2 4 6))


;; To write this for our 4Clojure challenge

(fn [collection number]
  (apply map list (partition number collection)))


;; test it works using the repl

((fn [collection number]
   (apply map list (partition number collection)))
 [1 2 3 4 5 6] 2)


;; => ((1 3 5) (2 4 6))



;; Reviewing how apply works
;;


;; lets take a simple example
(apply + '(1 2 3))


;; this is the same as inserting the `+` function inside of the collection
(+ 1 2 3)


;; so for the 4Clojure problem

(apply map list (partition 2 [1 2 3 4 5 6]))


;; if we just evaluate the `partition` part of the expression

(apply map list '('(1 2) '(3 4) '(5 6)))


;; If we transform the code in the way that apply works

(map list '(1 2) '(3 4) '(5 6))


;; => ((1 3 5) (2 4 6))


;; apply strips out the outer list of the result from partition
;; and make an expression that looks like this

;; https://stackoverflow.com/questions/3153396/clojure-reduce-vs-apply


;; Answers summary
;;

(fn [collection number]
  (apply map list (partition number collection)))


#(apply map list (partition %2 %1))


;; off at a tangent
;;

(map (partial interleave) [[1 2] [3 4] [5 6]])

(map #(interleave %) [[1 2 3] [4 5 6]])


;; Splitting the collection into sub-sequences

;; We can find the right number to partition the collection by a little calculation
;; count the elements in a collection
;; divide the count by the number given, just taking the quotent (whole number of the division)
(quot 6 2)


(fn [collection number]
  (partition
    (quot (count collection) number)
    collection))


;; try this out in the REPL

((fn [collection number]
   (partition
     (quot (count collection) number)
     collection))
 [1 2 3 4 5 6] 2)


((fn [collection number]
   (partition
     (quot (count collection) number)
     collection))
 [1 2 3 4 5 6] 4)


(partition 3 [1 2 3 4 5 6])


;; => ((1 2 3) (4 5 6))


(interleave (partition 3 [1 2 3 4 5 6]))


;; => ((1 2 3) (4 5 6))



;; Further explination


{:fred 2}


(take 25
      (range))


;; => (0 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20 21 22 23 24)

(take 25
      (iterate inc 0))


;; => (0 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20 21 22 23 24)

(map + (iterate inc 1) (range 1 100000) (list 1 2 3 4) [1])


;; => (4)


(defn how-many-values
  [number]
  (map + (iterate inc 1) (range 1 100000) (range 1 number)))


;; => #'four-clojure.043-reverse-interleave/how-many-values

(how-many-values 25)


;; => (3 6 9 12 15 18 21 24 27 30 33 36 39 42 45 48 51 54 57 60 63 66 69 72)


(map #(str "Hello " % "!") ["Ford" "Arthur" "Tricia"])

(map (fn [arg] (str "Hello " arg "!")) ["Ford" "Arthur" "Tricia"])


;; => ("Hello Ford!" "Hello Arthur!" "Hello Tricia!")


(map inc [1 2 3 4])


;; Clojure will interpret this as

'((inc 1) (inc 2) (inc 3) (inc 4))

(map + [1 2 3 4 5])


;; => (1 2 3 4 5)


(map + [1 2 3 4 5] [6 7 8 9 10])


;; => (7 9 11 13 15)


(map + [1 2 3 4 5] [6 7 8 9 10] [11 12 13 14 15])


;; => (18 21 24 27 30)



(map + [1 2 3 4 5] [6 7 8 9])


;; => (7 9 11 13)



(+ 4 5 6 7 8)


;; As (inc 1) will return a value, as will all the other inc function calls, then we get

'(1 2 3 4)


(map list [1 2 3 4 5 6 7 8])


;; Lets look at the more complexe example, but take a similar approach to breaking it down

(apply map list (partition 2 [1 2 3 4 5 6]))


;; => ((1 3 5) (2 4 6))

;; Sometimes its easier to work from the inside out.
;; The inside part is

(partition 2 [1 2 3 4 5 6])


;; This partitions the values in the collection into groups of 2
;; As there are two values, then we need to put them into a collection
;; by default, Clojure uses the list as its the simplest collection to work with.
;; So we get:

;; => ((1 2) (3 4) (5 6))


;; If we put that back into our original expression then we get

(apply map list '('(1 2) '(3 4) '(5 6)))


;; we need to quote each list when we write it out manually, to treat each collection as just data


;; lets have a quick look at what apply does

(apply + '(1 2 3 4 5))


;; translates to:

(+ 1 2 3 4 5)


;; Apply is like inserting the function + as the first element of the collection
;; turning the data into a function call with the data as arguments

;; so back to our example


(apply map list '('(1 2) '(3 4) '(5 6)))


;; translates to

(map list '(1 2) '(3 4) '(5 6))


;; we have seen what map does with the inc example, so the above translates to

'((list 1 3 5) (list 2 4 6))


;; which evaluates to

'('(1 3 5) '(2 4 6))


;; Does this make sense?  Please let me know if you would like to explain further (it did take a while for me to understand this too)


(map vector [1 2 3])


;; => ([1] [2] [3])


(map vector [[1 2 3]])


;; => ([[1 2 3]])



(map vector [[1 2 3] [4 5 6]])


;; => ([[1 2 3]] [[4 5 6]])


(apply map vector [[1 2 3] [4 5 6]])


;; => ([1 4] [2 5] [3 6])

(map vector [1 2 3] [4 5 6])


;; => ([1 4] [2 5] [3 6])

(map vector 1 2 3)

(apply map vector [1 2 3] [4 5 6])

(map vector [1 2 3])


(map vector [[:a :b :c]
             [:d :e :f]
             [:g :h :i]])


;; => ([[:a :b :c]] [[:d :e :f]] [[:g :h :i]])


(apply map vector [[:a :b :c]
                   [:d :e :f]
                   [:g :h :i]])


;; => ([:a :d :g] [:b :e :h] [:c :f :i])

(map vector [:a :b :c] [:d :e :f] [:g :h :i])


(vector :a :d :g)


;; => [:a :d :g]
