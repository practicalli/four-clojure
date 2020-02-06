(ns four-clojure.028-flatten-a-sequence)

;; #028 Flatten a Sequence
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;; Difficulty:	Easy
;; Topics:	seqs core-functions
;; Special Restrictions: flatten

;; Write a function which flattens a sequence.

;; (= (flatten '((1 2) 3 [4 [5 6]])) '(1 2 3 4 5 6))
;; (= (flatten ["a" ["b"] "c"]) '("a" "b" "c"))
;; (= (flatten '((((:a))))) '(:a))


;; Deconstruct the problem
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;; As `flatten` is restricted, it probably solves the problem for us by itself.


(= (flatten '((1 2) 3 [4 [5 6]])) '(1 2 3 4 5 6))
(= (flatten ["a" ["b"] "c"]) '("a" "b" "c"))
(= (flatten '((((:a))))) '(:a))


;; Definition of `flatten`
;; Takes any nested combination of sequential things (lists, vectors,
;; etc.) and returns their contents as a single, flat sequence.
;; (flatten nil) returns an empty sequence.


;; From the source in `clojure.core`

(fn [x]
  (filter (complement sequential?)
          (rest (tree-seq sequential? seq x))))


;; Trying it with our tests

((fn [x]
   (filter (complement sequential?)
           (rest (tree-seq sequential? seq x))))
 '((1 2) 3 [4 [5 6]]))

((fn [x]
   (filter (complement sequential?)
           (rest (tree-seq sequential? seq x))))
 ["a" ["b"] "c"])


((fn [x]
   (filter (complement sequential?)
           (rest (tree-seq sequential? seq x))))
 '((((:a)))))




;; Useful snippet: "merge" two or more vectors with `(comp vec flatten vector)`
(let [a [{:a "hi"} {:b "hey"}]
      b [{:c "yo"} {:d "hiya"}]
      c {:e ["hola" "bonjour"]}]
  ((comp vec flatten vector) a b c))
;;=> [{:a "hi"} {:b "hey"} {:c "yo"} {:d "hiya"} {:e ["hola" "bonjour"]}]

;; The `merge` function may actually be better in this case.
;; `flatten` can be quite a blunt tool

;; Alex Yakushev @unlog1c
;; Flatten is arbitrary-leveled concat. In most cases, you know how many levels of nesting you want to remove. Flatten, OTOH, can lead to undesired consequences like tuples being flattened out. So, unless the data structure itself is variably nested, it is safer to be explicit.


;; REPL experiments
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;; mapcat calls the function with x if coll? is true but what exactly map+concat mapping

;; The short answer:  the `concat` part of `mapcat` is going to combine all the calls to the `flat` function, starting with the last one which returns `(list x)` and working backwards to the first call.

;; The longer answer:
;; Both `map` and `mapcat` return a lazy sequence.  So actually they dont produce answers until they hit the end of a collection or the end of a recursive function call (when the recursive function call stops calling itself).  So `map` and `mapcat` need some kind of end condition before putting all the results back together.

;; Without an end condition you would just be returned a pointer to a lazy sequence, which could be infinite.  So then something around that lazy sequence would need to make it evaluate.


;; I have tried to change to map and take first example

((fn flat [x]
   (if (coll? x)
     (map flat x)
     (list x)))
 '((1 2) 3 [4 [5 6]]))

;; => (((1) (2)) (3) ((4) ((5) (6))))

;; and it looks like map calls the function with x structure which is () so each element in the collection should be with ()?

;; So probably the main question would be how to write a function that I could get the result
;; (((1) (2)) (3) ((4) ((5) (6))))
;; by using map but not using if statement?


;; Without some kind of break, then the code would never finish executing.  So you could use `when` instead of `if` but you are still doing the same thing.

;; The `if` condition determines if we call the `flat` function again.  Without the `if` then you would call `flat` an infinite number of times (well until your computer ran out of memory).

;; If you look at the implementation of the `flatten` function, it uses tree-seq to navigate through all the levels of the collection and uses a `when` condition to test if the branch is a node (has children) or not.

;; So I dont know how to achieve that without some kind of condition or in a function that uses a condition.

;; I hope this helps, feel free to ask more about this.  It took me ages to get my head around lazy sequences...  explaining this is helping me understand them better.




;; Answers summary
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;



(fn flat [x]
(if (coll? x)
  (mapcat flat x)
  (list x)))
