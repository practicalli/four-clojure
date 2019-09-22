(ns four-clojure.044-rotate-sequence)

;; #044 Rotate sequence
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; http://www.4clojure.com/problem/44

;; Difficulty:	Medium
;; Topics:	seqs

;; Write a function which can rotate a sequence in either direction.

;; (= (__ 2 [1 2 3 4 5]) '(3 4 5 1 2))
;; (= (__ -2 [1 2 3 4 5]) '(4 5 1 2 3))
;; (= (__ 6 [1 2 3 4 5]) '(2 3 4 5 1))
;; (= (__ 1 '(:a :b :c)) '(:b :c :a))
;; (= (__ -4 '(:a :b :c)) '(:c :a :b))


;; Deconstruct the problem
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;; We need to write a function that takes two arguments
;; - the number of elements we need to rotate
;; - a collection of numbers or keywords

;; The direction of rotation is based on the number
;;   - positive numbers rotate elements to the left
;;   - negative numbers rotate elements to the right

;; Assumption: if the number is zero we could just return the original collection



;; REPL experiments
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;



;; Going loopy...
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;; Iterate through the collection with loop recur

(fn [rotate-by collection]
  (loop [rotate-count      rotate-by
         elements-kept     collection
         elements-rotating []]
    (if (> 1 rotate-count)
      elements
      (recur (dec rotate-count)
             ))))

;; hmm, this is a bit of a challenge, as we have both negative and positive numbers, so we cant just decrement the value.

;; Its also got very messy very quickly, surely we can do better.



;; `take` function
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(take 2 [1 2 3 4 5])
;; => (1 2)


;; Looking at the `take` description on clojuredocs.org, it suggests we also see `drop`
;; Returns a lazy sequence of all but the first n items in coll.

(drop 2 [1 2 3 4 5])
;; => (3 4 5)

;; If we concatinate these results, then we get the answer we were looking

(concat [3 4 5] [1 2])

;; so lets write a function to test this approach
;; drop, take, concatenate

(fn [rotate-by collection]
  (concat (drop rotate-by collection) (take rotate-by collection)))


;; give our function definition some arguments

((fn [rotate-by collection]
   (concat (drop rotate-by collection) (take rotate-by collection)))
 2 [1 2 3 4 5])
;; => (3 4 5 1 2)



;; Does this pass other tests?

((fn [rotate-by collection]
   (concat (drop rotate-by collection) (take rotate-by collection)))
 -2 [4 5 1 2 3 ])
;; => (4 5 1 2 3)

(take -2 [1 2 3 4 5])
;; => ()


(drop -2 [4 5 1 2 3])
;; => (4 5 1 2 3)

;; No, so we will need to take another approach



;; Lets try some mathematics
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;; lets add the size of the collection to the rotation number
;; in the first 4Clojure test
(+ 2 5)
;; => 7

;; from the second test
(+ -2 5)
;; => 3

;; `mod` will also allow us to "subtract" the rotatiion from the collection size
;; but we always get a number within the size of the collection

(mod 2 5)
;; => 2

(mod -2 5)
;; => 3

;; so we can use mod to find the right values for `take` and `drop`
;; as mod is essentially cycling through the elements


;; `cycle` - not just for peddling
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;; The concept of `mod` cycling reminded me of the cycle function.

;; `cycle`
;; Returns a lazy (infinite!) sequence of repetitions of the items in coll.


;; Calling cycle with a collection will create an infinate sequence of numbers from 1 to 5,
;; each time it gets to 5, it will start at 1 again.

(cycle [1 2 3 4 5])

;; 1 2 3 4 5 1 2 3 4 5
;; . . s       e


;; `cycle` is not very useful by itself.
;; we can use `cycle` with take and drop to fetch the right number of values


(take 2 (cycle [1 2 3 4 5]))
;; => (1 2)

(drop 2 (cycle [1 2 3 4 5]))
;; infinite list!

;; hmm, not particularly useful


;; If we take the whole collection and all the rotatated number,
;; we would have all the numbers we need.
;; Then we can drop the extra numbers at the start that have been already added to the end.

;; Lets use the example 2 and [1 2 3 4 5]
;; as covered in the maths section, we can use mod to find the right values for `take` and `drop`


(drop (mod 2 (count [1 2 3 4 5]))
      (take (+ (mod 2 (count [1 2 3 4 5])) (count [1 2 3 4 5]))
            (cycle [1 2 3 4 5])))

(drop 2
      (take (+ (mod 2 5) 5)
            (cycle [1 2 3 4 5])))

(drop 2
      (take (+ 2 5)
            (cycle [1 2 3 4 5])))


;; Define a function for this algorithm

(fn [rotate-by collection]
  (drop (mod rotate-by (count collection))
        (take (+ (mod rotate-by (count collection))
                 (count collection))
              (cycle collection))))


;; refactor this a little

(fn [rotate-by collection]
  (let [size     (count collection)
        distance (mod rotate-by size)]
    (drop distance
          (take (+ distance size) (cycle collection)))))


((fn [rotate-by collection]
   (let [size     (count collection)
         distance (mod rotate-by size)]
     (drop distance
           (take (+ distance size) (cycle collection)))))
 2 [1 2 3 4 5])
;; => (3 4 5 1 2)

;; NOTE: when debugging an expression with a lazy fuction,
;; use `o` for out to skip evaluating the lazy function and avoid blowing up your computer



;; Answers summary
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(fn [rotate-by collection]
  (let [size     (count collection)
        distance (mod rotate-by size)]
    (drop distance
          (take (+ distance size) (cycle collection)))))
