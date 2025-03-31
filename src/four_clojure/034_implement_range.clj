(ns four-clojure.034-implement-range)


;; #034 Implement range
;;

;; Difficulty:	Easy
;; Topics:	seqs core-functions
;; Restrictions: range

;; Write a function which creates a list of all integers in a given range.

;; (= (__ 1 4) '(1 2 3))
;; (= (__ -2 2) '(-2 -1 0 1))
;; (= (__ 5 8) '(5 6 7))

;; Deconstruct the problem
;;

;; The range function generates a sequence of Integer values
;; A start number can be given and is the first value of the returned sequence
;; An end number is given, and all numbers up to but not including that number
;; Range will also take a step value, but this is not part of the 4Clojure tests

;; Using the first test, we see that the `range` function would
;; easily solve this challenge, but its restricted.

(= (range 1 4) '(1 2 3))


;; So we have to go and implement our own version of the `range` function.

;; REPL experiments
;;

;; Range is pretty straight forward function to use

(range 1 4)


;; => (1 2 3)

;; Be careful when using `range` without arguments,
;; as it will generate an infinite sequence straight away.

;; However, if we use `take` to give range a constraint,
;; then `range` will only generate the values needed to
;; satisfy take.

(take 10 (range))


;; => (0 1 2 3 4 5 6 7 8 9)

(range 10)


;; => (0 1 2 3 4 5 6 7 8 9)

(range 1 11)


;; => (1 2 3 4 5 6 7 8 9 10)

;; Range can use a step value to put a gap in between the numbers generated.
(range 10 100 10)


;; => (10 20 30 40 50 60 70 80 90)

;; `range` has an interesting edge case,
;; if both start and end values are the same
;; then an empty sequence is returned.

(range 1 1)


;; => ()

;; Loop/recur approach
;;
;; loop until you have created the sequence of numbers

(loop [start   1
       end     4
       numbers []]
  ;; a condition to end the loop/recur
  (if (= start end)
    numbers  ; return the current collection of numbers
    (recur   ; call loop with new values for start and numbers
     (inc start) end (conj numbers start))))


;; Put loop/recur in a function so we can call it with different arguments

(fn [start end]
  (loop [-start  start
         -end    end
         numbers []]
    (if (= -start -end)
      numbers
      (recur (inc -start) -end (conj numbers -start)))))


;; test our function by calling it with arguments
((fn [start end]
   (loop [-start  start
          -end    end
          numbers []]

     (if (= -start -end)
       numbers
       (recur (inc -start) -end (conj numbers -start)))))
 1 4)


;; => [1 2 3]

;; The loop/recur works (it usually does as its so flexible).

;; There are some concerns about the loop/recur approach though
;; 1) Its quite verbose, so takes a bit of reading and code maintenance can be harder
;; 2) It uses several local names which is not as efficient (and naming is hard)
;; 3) Its quite a low level of abstraction and we can make it more elegant.

;; range as a recursive function
;;

;; A recursive function is a function that calls itself,
;; usually with updated values as arguments for each successive call

;; the logic of our recursive function is very similar to loop/recur
;; however we do not need to define local names

;; if start and end are equal, then we return an empty vector.
;; else we add the start value to a vector, along with the result
;; of calling `-range` with updated arguments

(fn -range
  [start end]
  (if (= start end)
    []
    (conj [] start (-range (inc start) end))))


;; lets call this function with arguments from the first test

((fn -range
   [start end]
   (if (= start end)
     []
     (conj [] start (-range (inc start) end))))
 1 4) ; arguments to the function call
;; => [1 [2 [3 []]]]

;; The call to `-range` within the `conj` expression would
;; itself expand to a `conj` expression with specific values

;; So, if we expanded this function call, it would look like this

(conj [] 1 (conj [] (inc 1) (conj [] (inc (inc 1)) [])))


;; => [1 [2 [3 []]]]

;; We have the right values, but the structure is nested,
;; `flatten` is a very general way to just have a flat structure.

((fn -range
   [start end]
   (if (= start end)
     []
     (flatten (conj [] start (-range (inc start) end)))))
 1 4)


;; => (1 2 3)

;; To avoid flattening, we can use `cons` to construct
;; the collection we want during the recursion

;; `cons` takes a value and then a sequence as its arguments,
;; so we can use the `if` condition to return a list when the
;; start and end are equal and all the other values will be
;; become part of it as we evaluate up the recursive calls.

(cons 1 '())


;; => (1)

(cons 1 '(2))


;; => (1 2)

(cons 1 (cons 2 '()))


;; => (1 2)

;; Using `cons` does feel a little nicer with recursive functions.
;; And we get slightly shorter code

(fn -range
  [start end]
  (if (= start end)
    '()
    (cons start (-range (inc start) end))))


;; and calling this with 4Clojure test data
((fn -range
   [start end]
   (if (= start end)
     '()
     (cons start (-range (inc start) end))))
 1 4)


;; => (1 2 3)

;; Creating the right structure then iterating
;;

;; A more lateral thinking approach to solving this challenge
;; is to create the right size of data first,
;; then update the values to be correct.

;; Using `repeat` we can generate any number of a given value

(repeat 5 "Clojure")


;; => ("Clojure" "Clojure" "Clojure" "Clojure" "Clojure")

;; Taking the start and end values from the first test

(repeat (- 4 1) 1)


;; => (1 1 1)

;; We get the right structure, three elements in a collection,
;; but the values are not correct

;; The `map-indexed` function will iterate over a collection,
;; using the supplied function and the index value of each element
;; to update the value of each element.

;; We can use `map-indexed` with the `+` function to add the
;; value of the index to each number.

;; The index of a collection starts at zero.

(map-indexed + [1 1 1])


;; => (1 2 3)

;; Using this with the `repeat` function gives the same result

(map-indexed + (repeat (- 4 1) 1))


;; => (1 2 3)

;; Now we put it in a function definition,
;; so we can take the arguments for each 4Clojure test

(fn [start end]
  (map-indexed + (repeat (- end start) start)))


;; Creating a lazy sequence and taking the values we need
;;

;; My preferred approach is to generate a lazy sequence of numbers,
;; starting from the start value.
;; Then taking just the values we need up to the end
;; (but not including the end value)

;; The `iterate` function returns a lazy sequence from
;; the given function and a starting value.
;; The function is applied to successive results to create the next value
;; in the sequence

;; We use `take` to control how many elements iterate will create in the sequence.

(take 5 (iterate inc 0))


;; => (0 1 2 3 4)

;; Using values from the first 4Clojure test:

(take (- 4 1)
      (iterate inc 1))


;; => (1 2 3)

;; lets make this work for all the tests by putting it in a function definition

(fn [start end]
  (take (- end start)
        (iterate inc start)))


((fn [start end]
   (take (- end start)
         (iterate inc start)))
 1 4)


;; => (1 2 3)

;; Answers summary
;;

;; My preferred answer

;; Take the values needed from a lazy sequence of incremented values
(fn [start end]
  (take (- end start)
        (iterate inc start)))


;; golf-score of 48

;; or golf-score obsessed version
#(take (- %2 %) (iterate inc %))


;; golf-score of 26

;; Creating the right structure then updating the values using their index values

(fn [start end]
  (map-indexed + (repeat (- end start) start)))


;; recursive function

(fn -range
  [start end]
  (if (= start end)
    '()
    (cons start (-range (inc start) end))))


;; low level loop/recur

(fn [start end]
  (loop [-start  start
         -end    end
         numbers []]
    (if (= -start -end)
      numbers
      (recur (inc -start) -end (conj numbers -start)))))
