(ns four-clojure.021-nth-element)


;; #021 Nth Element
;;

;; Difficulty:	Easy
;; Topics:	seqs core-functions
;; Restricted: `nth`

;; Write a function which returns the Nth element from a sequence.

;; (= (__ '(4 5 6 7) 2) 6)
;; (= (__ [:a :b :c] 0) :a)
;; (= (__ [1 2 3 4] 1) 2)
;; (= (__ '([1 2] [3 4] [5 6]) 2) [5 6])


;; Deconstruct the problem
;;

;; Solving this would be done just with the `nth` function, however, its use is restricted in this example.

;; We could iterate through the collection and keep a track of where we are, but that is quite an imperitive and stateful approach.  Not very functional.

;; So can we think about this in terms of the data we want and transform the data into a nicer shape, making our logic simpler.


;; REPL experiments
;;

;; We have seen functions such as `take` previously that get a certain number of elements from a function
;; we could try that.

(take 2 [4 5 6 7])


;; => (4 5)

;; `take` does split the collection in the correct place, but returns the elements we don't actually want and loses the elements of the collection we do want.

;; luckily there is a function called `drop` that does the opposite of `take`.

(drop 2 [4 5 6 7])


;; => (6 7)

;; So now we can just get the first element as the answer.
(first
  (drop 2 [4 5 6 7]))


;; => 6


;; we need to put this into a function definition so that we can pass the collection and the index value to `drop`
;; Notice that we have to change the order of arguments for the drop function to that which we received in the function.
(fn [collection number] (first (drop number collection)))


;; or it its short form
#(first (drop %2 %1))


;; Using functional composition

;; We can use `comp` approach with this example too, although because we have to reverse the arguments to drop,
;; we put drop into a lambda function and manually swap the arguments around.
(comp first #(drop %2 %1))


;; Answers summary
;;

;; Functional composition approach

(comp first #(drop %2 %1))


;; function definition approach
(fn [collection number] (first (drop number collection)))


;; or it its short form
#(first (drop %2 %1))


(defn swapping
  [collection number]
  (first (drop number collection)))


(swapping '(4 5 6 7) 2)


;; (= (__ '(4 5 6 7) 2) 6)



((fn [& args]
   (first (drop (second args) (first args))))
 '(4 5 6 7) 2)


((fn [xs n]
   (first (drop n xs)))
 '(4 5 6 7) 2)


(#(first (drop %2 %1))
 '(4 5 6 7) 2)
