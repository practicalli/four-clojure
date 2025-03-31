(ns four-clojure.060-sequence-reductions)


;; # 060 Sequence Reductions
;;

;; Difficulty:	Medium
;; Topics:	seqs core-functions
;; Restrictions: reductions

;; Write a function which behaves like reduce, but returns each intermediate value of the reduction. Your function must accept either two or three arguments, and the return sequence must be lazy.

;; (= (take 5 (__ + (range))) [0 1 3 6 10])
;; (= (__ conj [1] [2 3 4]) [[1] [1 2] [1 2 3] [1 2 3 4]])
;; (= (last (__ * 2 [3 4 5])) (reduce * 2 [3 4 5]) 120)


;; Deconstruct the problem
;;

;; 1) write a reducing function
;; 2) rather than just the final result, return all the intermediary values too

;; As will most of the 4Clojure challenges that restruct the use of a specific function,
;; that function can be used as the answer

(= (take 5 (reductions + (range))) [0 1 3 6 10])


;; => true

(= (reductions conj [1] [2 3 4]) [[1] [1 2] [1 2 3] [1 2 3 4]])


;; => true

(= (last (reductions * 2 [3 4 5])) (reduce * 2 [3 4 5]) 120)


;; => true


;; So we need to implement our own version of reductions


;; lets look at some simpler reductions examples

(reductions + (range 10))


;; => (0 1 3 6 10 15 21 28 36 45)


;; reductions returns all the intermedate results,
;; including the final result that reduce would return.

(= (reduce + (range 10))
   (last (reductions + (range 10))))


;; => true



;; reduce source code
;;

;; To understand the reduce function, we can look at the source code
;; The code is quite involved though

(defn reduce
  "f should be a function of 2 arguments. If val is not supplied,
  returns the result of applying f to the first 2 items in coll, then
  applying f to that result and the 3rd item, etc. If coll contains no
  items, f must accept no arguments as well, and reduce returns the
  result of calling f with no arguments.  If coll has only 1 item, it
  is returned and f is not called.  If val is supplied, returns the
  result of applying f to val and the first item in coll, then
  applying f to that result and the 2nd item, etc. If coll contains no
  items, returns val and f is not called."
  {:added "1.0"}
  ([f coll]
   (if (instance? clojure.lang.IReduce coll)
     (.reduce ^clojure.lang.IReduce coll f)
     (clojure.core.protocols/coll-reduce coll f)))
  ([f val coll]
   (if (instance? clojure.lang.IReduceInit coll)
     (.reduce ^clojure.lang.IReduceInit coll f val)
     (clojure.core.protocols/coll-reduce coll f val))))


(extend-protocol clojure.core.protocols/IKVReduce
  nil
  (kv-reduce
    [_ f init]
    init)

  ;; slow path default
  clojure.lang.IPersistentMap
  (kv-reduce
    [amap f init]
    (reduce (fn [ret [k v]] (f ret k v)) init amap))

  clojure.lang.IKVReduce
  (kv-reduce
    [amap f init]
    (.kvreduce amap f init)))


;; There is a lot of code here, but essentially its a recursive function



;; reductions source code
;;

;; We can look at the reductions source code for ideas
;; https://github.com/clojure/clojure/blob/clojure-1.9.0/src/clj/clojure/core.clj#L7134


(defn reductions
  "Returns a lazy seq of the intermediate values of the reduction (as
  per reduce) of coll by f, starting with init."
  {:added "1.2"}
  ([f coll]
   (lazy-seq
     (if-let [s (seq coll)]
       (reductions f (first s) (rest s))
       (list (f)))))
  ([f init coll]
   (if (reduced? init)
     (list @init)
     (cons init
           (lazy-seq
             (when-let [s (seq coll)]
               (reductions f (f init (first s)) (rest s))))))))


;; reductions is also a recursive call,
;; except it adds each result onto a lazy sequence


;; REPL experiments
;;



;; lets start with the simplest case
;; calling our reductions function with one function and one collection

;; f is a common shortcut to represent a function as an argument
;; xs is a common shortcut to represent a collection (e.g. list, vector, map or set)


(fn my-reductions
  [f xs]
  (conj (f (first xs))
        (my-reductions f (rest xs))))


;; using the essence of the first test

#_((fn my-reductions
     [f xs]
     (conj (f (first xs))
           (my-reductions f (rest xs))))
   +
   (range 5))


;; This kind of works, however it causes a stack overflow as there is nothing to break out of the recursion

;; We need a way to check if the xs sequence is empty while we are recursing though it.

;; We could jump down to a loop recur approach, but that seems too low level.


;; If we add a second branch to the function definition that takes an accumulator as well,
;; then we can check the sequence if the sequence is empty then we can return the accumulator.


(first [1])


;; => 1

(rest [1])


;; => ()

(rest [])


;; => ()

(rest [1 2 3 4 5])


;; => (2 3 4 5)

(fn my-reductions
  ([f xs]
   (my-reductions f (first xs) (rest xs)))

  ([f acc xs]
   (if (empty? xs)
     acc
     (conj acc
           (my-reductions f (f acc (first xs)) (rest xs))))))


;; we have a check but we still get a stack overflow when using this with out tests.

;; The original problem statement says we should return a lazy sequence

(take 10 (range))


;; => (0 1 2 3 4 5 6 7 8 9)


(fn my-reductions
  ([f xs] (my-reductions f (first xs) (rest xs)))
  ([f acc xs]
   (lazy-seq
     (if (empty? xs)
       (list acc)
       (cons acc (my-reductions f (f acc (first xs)) (rest xs)))))))


((fn my-reductions
   ([f xs] (my-reductions f (first xs) (rest xs)))
   ([f acc xs]
    (lazy-seq
      (if (empty? xs)
        (list acc)
        (cons acc (my-reductions f (f acc (first xs)) (rest xs)))))))
 +
 (range 5))


;; => (0 1 3 6 10)


;; understanding lazyness in Clojure
;;

;; https://clojuredocs.org/clojure.core/lazy-seq
;; http://clojure-doc.org/articles/language/laziness.html
;; https://clojure.org/reference/lazy
;; https://clojure.org/reference/sequences
;; https://medium.com/@pwentz/laziness-in-clojure-3d83645bf7f3
;; https://dev.to/__namc/lazy-seq-inclojure-bmh
;; http://theatticlight.net/posts/Lazy-Sequences-in-Clojure/



;; Answers summary
;;

(fn my-reductions
  ([f xs] (my-reductions f (first xs) (rest xs)))
  ([f a xs]
   (lazy-seq
     (if (empty? xs)
       (list a)
       (cons a (my-reductions f (f a (first xs)) (rest xs)))))))
