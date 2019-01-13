(ns four-clojure.026-fibonacci-sequence)

;; #26 - Fibonacci Sequence
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;; http://www.4clojure.com/problem/26#prob-title
;; Difficulty:	Easy
;; Topics:	Fibonacci seqs

;; Write a function which returns the first X fibonacci numbers.

;; Tests
#_(= (__ 3) '(1 1 2))
#_(= (__ 6) '(1 1 2 3 5 8))
#_(= (__ 8) '(1 1 2 3 5 8 13 21))

;;;; Some references
;; https://en.wikipedia.org/wiki/Fibonacci_number
;; http://squirrel.pl/blog/2010/07/26/corecursion-in-clojure/

;; NOTE: use a fibonacci sequence to draw a spiral using SVG for the clojurebridge exercises

;; Deconstruct the problem
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;; First though is that we want to generate a range of numbers

(range 1 6)
;; => (1 2 3 4 5)

;; Looking at the range source code does not help that much as range is just a wrapper for clojure.lang.Range which is a Java class.

;; We could 'hard code' the fibonacci series and just take the numbers we need.

(fn [lenght-of-series]
  (take lenght-of-series [1 1 2 3 5 8 13 21]))

;; Calling with up to 8 values will return the correct result.
((fn [lenght-of-series]
   (take lenght-of-series [1 1 2 3 5 8 13 21]))
 8)
;; => (1 1 2 3 5 8 13 21)

;; This is of course easy to break by requiring a greater number of values than hard coded.
((fn [lenght-of-series]
   (take lenght-of-series [1 1 2 3 5 8 13 21]))
 9)
;; => (1 1 2 3 5 8 13 21)


;; Looking at the tests we see that all fibonacci sequences start with `1 1`.
;; To get the third value in the sequence we add the first numbers together.  This is specific to the third value in the sequence.
;; To get each new value in the sequence we add the last to values in the sequence together

;; The problem asks us to write a function, so lets start there.

;; The function takes the size of fibonacci sequence to generate as an argument
;; and creates a local name to represent the initial fibonacci sequence.

(fn fibbonacci [length-of-series]
  (let [fib [1 1]]
    fib))


;; lets call the function with a lenght of 3

((fn fibbonacci [length-of-series]
   (let [fib [1 1]]
     fib)) 3)
;; => [1 1]

;; Add test to see if the fibonacci sequence is big enough and iterate if its too small.
(fn fibbonacci [length-of-series]
  (let [fib [1 1]]
    (if (< (count fib) length-of-series)
      "iterate... to implement"
      fib)))

;; Now we just need to iterate until we get a big enough fibonacci sequence
;; A simple way to iterate is to use loop recur, as we only only pass the length-of-series of fibonacci to generate as an argument to the function we are writing.
;; In each iteration we conjoin the result of adding all the values in the fib sequence together.

(fn fibonacci [length-of-series]
  (loop [fib [1 1]]
    (if (< (count fib) length-of-series)
      (recur (conj fib (reduce + fib)))
      fib)))

;; Test our function, generating a fibonacci sequence of length-of-series 3
((fn fibs [length-of-series]
   (loop [fib [1 1]]
     (if (< (count fib) length-of-series)
       (recur (conj fib (reduce + fib)))
       fib)))
 3)
;; => [1 1 2]

;; Test our function, generating a fibonacci sequence of length-of-series 6
((fn fibs [length-of-series]
   (loop [fib [1 1]]
     (if (< (count fib) length-of-series)
       (recur (conj fib (reduce + fib)))
       fib)))
 6)
;; => [1 1 2 4 8 16]

;; Although this generates a sequence of the right length-of-series, it is not the fibonacci sequence.
;; We need to add the last two values in the current sequence for each iteration

;; Change our recur to take the last and second to last values from the current value of fib.
;; As there is no second-last function, we reverse the sequence and take the second value.

(fn fibs [length-of-series]
  (loop [fib [1 1]]
    (if (< (count fib) length-of-series)
      (recur (conj fib (+ (second (reverse fib)) (last fib))))
      fib)))



((fn fibs [length-of-series]
   (loop [fib [1 1]]
     (if (< (count fib) length-of-series)
       (recur (conj fib (+ (second (reverse fib)) (last fib))))
       fib)))
 8)
;; => [1 1 2 3 5 8 13 21]



;; Using recursion
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;; reduce combines all the values of a collection using the given function.

(reduce + [1 1 1 1])
;; => 4

;; reductions will return all the values (combinations) produced
;; in a reduce function

(reductions + [1 1 1 1])
;; => (1 2 3 4)

;; lets look at some bigger answers

(reduce * [1 2 3 4 5 6 7 8 9])
;; => 362880

(reductions * [1 2 3 4 5 6 7 8 9])
;; => (1 2 6 24 120 720 5040 40320 362880)


;; reduce and reductions can both take a starting value

(reduce conj [] '(1 2 3))
;; => [1 2 3]

(reductions conj [] '(1 2 3))
;; => ([] [1] [1 2] [1 2 3])


;; Lets apply this to our fibonacci series.

;; Here is a function that generates our series
;; Our reductions applies a function that adds the first and second numbers
;; from our range generated sequence (1 to size of series)

;; The first value of each generated value represents the correct fibonacci value.

(fn [size-of-series]
  (map first (reductions
              (fn [[primary secondary] _] [secondary (+ primary secondary)])
                [1 1]
                (range 1 size-of-series))))

(reductions
 (fn [[primary secondary] _] [secondary (+ primary secondary)])
 [1 1]
 (range 1 9))
;; => ([1 1] [1 2] [2 3] [3 5] [5 8] [8 13] [13 21] [21 34] [34 55])


(map first '([1 1] [1 2] [2 3] [3 5] [5 8] [8 13] [13 21] [21 34] [34 55]))
;; => (1 1 2 3 5 8 13 21 34)


((fn [size-of-series]
   (map first (reductions
                (fn
                  [[primary secondary] _]
                  [secondary (+ primary secondary)])
                [1 1]
                (range 1 size-of-series))))
 8)
;; => (1 1 2 3 5 8 13 21)



;; similar sequence generating functions include
;; partition, partition-all,


;; Answers summary
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;; using loop recur - low level of abstraction

(fn fibs [size-of-series]
  (loop [fib [1 1]]
    (if (< (count fib) size-of-series)
      (recur (conj fib (+ (second (reverse fib)) (last fib))))
      fib)))

;; Code Golf Score: 101


;; reduce approach

(fn [size-of-series]
  (map first (reductions
              (fn [[primary secondary] _] [secondary (+ primary secondary)])
              [1 1]
              (range 1 size-of-series))))

;; similar to reductions
#(take % (map first (iterate (fn [[a b]] [(+ a b) a]) [1 0])))


;; Hard coded answer

(fn [i] (take i '(1 1 2 3 5 8 13 21)))

;; Obviously as soon as the requirements change to be a larger number than 8 for the fibonacci sequence, then this code is going to fail.








;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Other solutions to fibonachi

;;Programming in Clojure p. 136

;; (defn fibo [] (map first (iterate (fn [[a b]] [b (+ a b)]) [0 1])))

;; (defn proj-euler2 []
;;   (reduce + (for [x (fibo) :when (even? x) :while (< x 4000000)] x)))


;; Solutions from http://en.wikibooks.org/wiki/Clojure_Programming/Examples/Lazy_Fibonacci

;; A function which lazily produces Fibonacci numbers:

;; (def fib-seq
;;   ((fn rfib [a b]
;;      (lazy-seq (cons a (rfib b (+ a b)))))
;;    0 1))

;; (take 20 fib-seq)

;; We can use iterate to generate pairs of [a b] and then take the first of each one.

(defn fib-step [[a b]]
  [b (+ a b)])

(defn fib-seq []
  (map first (iterate fib-step [0 1])))

;; (take 20 (fib-seq))


;; Recursive Fibonacci with any start point and the amount of numbers that you want[edit]
;; (defn fib [start range]
;;   "Creates a vector of fibonnaci numbers"
;;   (if (<= range 0)
;;     start
;;     (recur (let[subvector (subvec start (- (count start) 2))
;;                 x (nth subvector 0)
;;                 y (nth subvector 1)
;;                 z (+ x y)]
;;              (conj start z))
;;            (- range 1))))


;; Self-Referential Version[edit]
;; Computes the Fibonacci sequence by mapping the sequence with itself, offset by one element.

;; (def fib (cons 1 (cons 1 (lazy-seq (map + fib (rest  fib))))))


;; From stack exchange

;; initial solution

;; (defn fib [x, n]
;;   (if (< (count x) n)
;;     (fib (conj x (+ (last x) (nth x (- (count x) 2)))) n)
;;     x))

;; To use this I need to seed x with [0 1] when calling the function. My question is, without wrapping it in a separate function, is it possible to write a single function that only takes the number of elements to return?

;; Doing some reading around led me to some better ways of achieving the same funcionality:

;; (defn fib2 [n]
;;   (loop [ x [1 1]]
;;     (if (< (count x) n)
;;       (recur (conj x (+ (last x) (nth x (- (count x) 2)))))
;;       x)))

;; and

;; (defn fib3 [n]
;;   (take n
;;     (map first (iterate (fn [[a b]] [b (+ a b)]) [0 1]))))

;; Multi-arity approach

;; (defn fib
;;   ([n]
;;      (fib [0 1] n))
;;   ([x, n]
;;      (if (< (count x) n)
;;        (fib (conj x (+ (last x) (nth x (- (count x) 2)))) n)
;;        x)))


;;;;; Thrush operator

;; You can use the thrush operator to clean up #3 a bit (depending on who you ask; some people love this style, some hate it; I'm just pointing out it's an option):

;; (defn fib [n]
;;   (->> [0 1]
;;     (iterate (fn [[a b]] [b (+ a b)]))
;;     (map first)
;;     (take n)))
;; That said, I'd probably extract the (take n) and just have the fib function be a lazy infinite sequence.

;; (defn fib
;;   (->> [0 1]
;;     (iterate (fn [[a b]] [b (+ a b)]))
;;     (map first)))

;; ;;usage
;; (take 10 fib)
;; ;;output (0 1 1 2 3 5 8 13 21 34)


;;;;; Avoiding recursion ?

;; In Clojure it's actually advisable to avoid recursion and instead use the loop and recur special forms.
;; This turns what looks like a recursive process into an iterative one, avoiding stack overflows and
;; improving performance.

;; Here's an example of how you'd implement a Fibonacci sequence with this technique:

(defn fib [n]
  (loop [fib-nums [0 1]]
    (if (>= (count fib-nums) n)
      (subvec fib-nums 0 n)
      (let [[n1 n2] (reverse fib-nums)]
        (recur (conj fib-nums (+ n1 n2)))))))

;; The loop construct takes a series of bindings, which provide initial values, and one or more body forms.
;; In any of these body forms, a call to recur will cause the loop to be called recursively with the
;; provided arguments.
