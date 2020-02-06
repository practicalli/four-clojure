(ns four-clojure.other-answers)


                                        ; 1. Nothing but the Truth
                                        ; https://www.4clojure.com/problem/1
                                        ; This is a clojure form.  Enter a value which will make the form
                                        ; evaluate to true.  Don't over think it!  If you are confused, see the
                                        ; <a href="/directions">getting started</a> page.  Hint: true is equal
                                        ; to true.

true



                                        ; 2. Simple Math
                                        ; https://www.4clojure.com/problem/2
                                        ; <p>If you are not familiar with <a
                                        ; href="http://en.wikipedia.org/wiki/Polish_notation">polish
                                        ; notation</a>, simple arithmetic might seem
                                        ; confusing.</p><p><strong>Note:</strong> Enter only enough to fill in
                                        ; the blank (in this case, a single number) - do not retype the whole
                                        ; problem.</p>

4



                                        ; 3. Intro to Strings
                                        ; https://www.4clojure.com/problem/3
                                        ; Clojure strings are Java strings.  This means that you can use any of
                                        ; the Java string methods on Clojure strings.

"HELLO WORLD"



                                        ; 4. Intro to Lists
                                        ; https://www.4clojure.com/problem/4
                                        ; Lists can be constructed with either a function or a quoted form.

:a :b :c



                                        ; 5. Lists: conj
                                        ; https://www.4clojure.com/problem/5
                                        ; When operating on a list, the conj function will return a new list
                                        ; with one or more items "added" to the front.

'(1 2 3 4)



                                        ; 6. Intro to Vectors
                                        ; https://www.4clojure.com/problem/6
                                        ; Vectors can be constructed several ways.  You can compare them with
                                        ; lists.

:a :b :c



                                        ; 7. Vectors: conj
                                        ; https://www.4clojure.com/problem/7
                                        ; When operating on a Vector, the conj function will return a new vector
                                        ; with one or more items "added" to the end.

[1 2 3 4]



                                        ; 8. Intro to Sets
                                        ; https://www.4clojure.com/problem/8
                                        ; Sets are collections of unique values.

#{:a :b :c :d}



                                        ; 9. Sets: conj
                                        ; https://www.4clojure.com/problem/9
                                        ; When operating on a set, the conj function returns a new set with one
                                        ; or more keys "added".

2



                                        ; 10. Intro to Maps
                                        ; https://www.4clojure.com/problem/10
                                        ; Maps store key-value pairs.  Both maps and keywords can be used as
                                        ; lookup functions. Commas can be used to make maps more readable, but
                                        ; they are not required.

20



                                        ; 11. Maps: conj
                                        ; https://www.4clojure.com/problem/11
                                        ; When operating on a map, the conj function returns a new map with one
                                        ; or more key-value pairs "added".

[:b 2]



                                        ; 12. Intro to Sequences
                                        ; https://www.4clojure.com/problem/12
                                        ; All Clojure collections support sequencing.  You can operate on
                                        ; sequences with functions like first, second, and last.

3



                                        ; 13. Sequences: rest
                                        ; https://www.4clojure.com/problem/13
                                        ; The rest function will return all the items of a sequence except the
                                        ; first.

[20 30 40]



                                        ; 14. Intro to Functions
                                        ; https://www.4clojure.com/problem/14
                                        ; Clojure has many different ways to create functions.

8



                                        ; 15. Double Down
                                        ; https://www.4clojure.com/problem/15
                                        ; Write a function which doubles a number.

#(* % 2)



                                        ; 16. Hello World
                                        ; https://www.4clojure.com/problem/16
                                        ; Write a function which returns a personalized greeting.

#(format "Hello, %s!" %)



                                        ; 17. Sequences: map
                                        ; https://www.4clojure.com/problem/17
                                        ; The map function takes two arguments: a function (f) and a sequence
                                        ; (s).  Map returns a new sequence consisting of the result of applying
                                        ; f to each item of s.  Do not confuse the map function with the map
                                        ; data structure.

'(6 7 8)



                                        ; 18. Sequences: filter
                                        ; https://www.4clojure.com/problem/18
                                        ; The filter function takes two arguments: a predicate function (f) and
                                        ; a sequence (s).  Filter returns a new sequence consisting of all the
                                        ; items of s for which (f item) returns true.

'(6 7)



                                        ; 19. Last Element
                                        ; https://www.4clojure.com/problem/19
                                        ; Write a function which returns the last element in a sequence.

#(first(reverse %))



                                        ; 20. Penultimate Element
                                        ; https://www.4clojure.com/problem/20
                                        ; Write a function which returns the second to last element from a
                                        ; sequence.

(comp first rest reverse)



                                        ; 21. Nth Element
                                        ; https://www.4clojure.com/problem/21
                                        ; Write a function which returns the Nth element from a sequence.

(fn [col n] (last (take (+ n 1) col)))



                                        ; 23. Reverse a Sequence
                                        ; https://www.4clojure.com/problem/23
                                        ; Write a function which reverses a sequence.

(fn rr [coll] (map #(nth %1 1) (sort #(> (first %1) (first %2)) (map-indexed list coll))))



                                        ; 24. Sum It All Up
                                        ; https://www.4clojure.com/problem/24
                                        ; Write a function which returns the sum of a sequence of numbers.

reduce +



                                        ; 25. Find the odd numbers
                                        ; https://www.4clojure.com/problem/25
                                        ; Write a function which returns only the odd numbers from a sequence.

filter #(> (mod % 2) 0)



                                        ; 26. Fibonacci Sequence
                                        ; https://www.4clojure.com/problem/26
                                        ; Write a function which returns the first X fibonacci numbers.

(fn fib [x]
  (take x (map first (iterate (fn [[a b]] [b (+ a b)]) [1 1])))
  )



                                        ; 27. Palindrome Detector
                                        ; https://www.4clojure.com/problem/27
                                        ; Write a function which returns true if the given sequence is a
                                        ; palindrome.

(fn [x] (every? true? (map = x  (reverse x))))



                                        ; 28. Flatten a Sequence
                                        ; https://www.4clojure.com/problem/28
                                        ; Write a function which flattens a sequence.

(fn flat [x]
  (let [elem (first x) coll (rest x)]
    (cond
      (empty? x)         '()
      (sequential? elem) (concat (flat elem) (flat coll))
      :else              (conj (flat coll) elem)
      )
    )
  )



                                        ; 33. Replicate a Sequence
                                        ; https://www.4clojure.com/problem/33
                                        ; Write a function which replicates each element of a sequence a
                                        ; variable number of times.

(fn [coll num]
  (mapcat (fn dup
            ([elem] (dup elem num []))
            ([elem cont dupcoll]
             (if (= cont 0)
               dupcoll
               (dup elem (- cont 1) (conj dupcoll elem)))))

          coll))



                                        ; 35. Local bindings
                                        ; https://www.4clojure.com/problem/35
                                        ; Clojure lets you give local names to values using the special let-
                                        ; form.

7



                                        ; 36. Let it Be
                                        ; https://www.4clojure.com/problem/36
                                        ; Can you bind x, y, and z so that these are all true?

[x 7 y 3 z 1]



                                        ; 37. Regular Expressions
                                        ; https://www.4clojure.com/problem/37
                                        ; Regex patterns are supported with a special reader macro.

"ABC"



                                        ; 38. Maximum value
                                        ; https://www.4clojure.com/problem/38
                                        ; Write a function which takes a variable number of parameters and
                                        ; returns the maximum value.

(fn mm
  ([arg] arg)
  ([arg & rargs]
   (loop [max_arg arg next_arg (first rargs) moreargs (rest rargs)]
     (cond
       (nil? next_arg)      max_arg
       (> max_arg next_arg) (recur max_arg (first moreargs) (rest moreargs))
       :else                (recur next_arg (first moreargs) (rest moreargs))))))



                                        ; 40. Interpose a Seq
                                        ; https://www.4clojure.com/problem/40
                                        ; Write a function which separates the items of a sequence by an
                                        ; arbitrary value.

(fn myfunc [s coll] (rest (reduce #(into %1 [s %2]) [] coll)))



                                        ; 41. Drop Every Nth Item
                                        ; https://www.4clojure.com/problem/41
                                        ; Write a function which drops every Nth item from a sequence.

(fn myfunc [coll n] (keep-indexed #(if (> (mod (+ 1 %1) n) 0) %2) coll))



                                        ; 42. Factorial Fun
                                        ; https://www.4clojure.com/problem/42
                                        ; Write a function which calculates factorials.

#(apply * (range 1 (inc %)))



                                        ; 43. Reverse Interleave
                                        ; https://www.4clojure.com/problem/43
                                        ; Write a function which reverses the interleave process into x number
                                        ; of subsequences.

(fn foo [coll n]
  (map #(take-nth n (drop % coll)) (range n)))



                                        ; 52. Intro to Destructuring
                                        ; https://www.4clojure.com/problem/52
                                        ; Let bindings and function parameter lists support destructuring.

[c e]



                                        ; 53. Longest Increasing Sub-Seq
                                        ; https://www.4clojure.com/problem/53
                                        ; Given a vector of integers, find the longest consecutive sub-sequence
                                        ; of increasing numbers. If two sub-sequences have the same length, use
                                        ; the one that occurs first. An increasing sub-sequence must have a
                                        ; length of 2 or greater to qualify.

(fn [x]
  (letfn [(choose-best [a b]
            (if (> (count a) (count b)) a b))]
    (loop [coll (rest x) solution [(first x)] best (vec solution)]
      (let [prev (peek solution) next (first coll)]
        (cond
          (empty? coll) (if (> (count best) 1) best [])
          (= (+ prev 1) next)
          (recur (rest coll) (conj solution next) (choose-best (conj solution next) best))
          :else         (recur (rest coll) [next] best))))))



                                        ; 54. Partition a Sequence
                                        ; https://www.4clojure.com/problem/54
                                        ; Write a function which returns a sequence of lists of x items each.
                                        ; Lists of less than x items should not be returned.

(fn [psize coll]
  (filter #(= (count %) psize) (partition-by #(int (/ % psize)) coll)))



                                        ; 55. Count Occurrences
                                        ; https://www.4clojure.com/problem/55
                                        ; Write a function which returns a map containing the number of
                                        ; occurences of each distinct item in a sequence.

#(reduce (fn [counter x]
           (assoc counter x (inc (counter x 0))))
         {} %)



                                        ; 56. Find Distinct Items
                                        ; https://www.4clojure.com/problem/56
                                        ; Write a function which removes the duplicates from a sequence. Order
                                        ; of the items must be maintained.

#((reduce (fn [[result, seen] x]
            (if (seen x)
              [result, seen]
              [(conj result x) (conj seen x)]))
          [[], #{}] %)
  0)



                                        ; 57. Simple Recursion
                                        ; https://www.4clojure.com/problem/57
                                        ; A recursive function is a function which calls itself.  This is one of
                                        ; the fundamental techniques used in functional programming.

'(5 4 3 2 1)



                                        ; 64. Intro to Reduce
                                        ; https://www.4clojure.com/problem/64
                                        ; <a href="http://clojuredocs.org/clojure_core/clojure.core/reduce">Redu
                                        ; ce</a> takes a 2 argument function and an optional starting value. It
                                        ; then applies the function to the first 2 items in the sequence (or the
                                        ; starting value and the first element of the sequence). In the next
                                        ; iteration the function will be called on the previous return value and
                                        ; the next item from the sequence, thus reducing the entire collection
                                        ; to one value. Don't worry, it's not as complicated as it sounds.

+



                                        ; 68. Recurring Theme
                                        ; https://www.4clojure.com/problem/68
                                        ; Clojure only has one non-stack-consuming looping construct: recur.
                                        ; Either a function or a loop can be used as the recursion point.
                                        ; Either way, recur rebinds the bindings of the recursion point to the
                                        ; values it is passed.  Recur must be called from the tail-position, and
                                        ; calling it elsewhere will result in an error.

[7 6 5 4 3]



                                        ; 71. Rearranging Code: -&gt;
                                        ; https://www.4clojure.com/problem/71
                                        ; The -&gt; macro threads an expression x through a variable number of
                                        ; forms. First, x is inserted as the second item in the first form,
                                        ; making a list of it if it is not a list already.  Then the first form
                                        ; is inserted as the second item in the second form, making a list of
                                        ; that form if necessary.  This process continues for all the forms.
                                        ; Using -&gt; can sometimes make your code more readable.

last



                                        ; 72. Rearranging Code: -&gt;&gt;
                                        ; https://www.4clojure.com/problem/72
                                        ; The -&gt;&gt; macro threads an expression x through a variable number
                                        ; of forms. First, x is inserted as the last item in the first form,
                                        ; making a list of it if it is not a list already.  Then the first form
                                        ; is inserted as the last item in the second form, making a list of that
                                        ; form if necessary.  This process continues for all the forms.  Using
                                        ; -&gt;&gt; can sometimes make your code more readable.

reduce +



                                        ; 84. Transitive Closure
                                        ; https://www.4clojure.com/problem/84
                                        ; Write a function which generates the <a
                                        ; href="http://en.wikipedia.org/wiki/Transitive_closure">transitive
                                        ; closure</a> of a <a
                                        ; href="http://en.wikipedia.org/wiki/Binary_relation">binary
                                        ; relation</a>.  The relation will be represented as a set of 2 item
                                        ; vectors.

(fn tclosure [coll]
  (let [relations (into {} coll)]
    (letfn
        [(find-path [src]
           (loop [x src paths #{}]
             (if-let [dest (get relations x)]
               (recur dest (conj paths [src dest]))
               paths)))]
      (reduce clojure.set/union
              (for [elem (keys relations)]
                (find-path elem))))))



                                        ; 85. Power Set
                                        ; https://www.4clojure.com/problem/85
                                        ; Write a function which generates the <a
                                        ; href="http://en.wikipedia.org/wiki/Power_set">power set</a> of a given
                                        ; set.  The power set of a set x is the set of all subsets of x,
                                        ; including the empty set and x itself.

(fn powerset
  ([coll] (powerset coll #{#{}}))
  ([coll results]
   (if (seq coll)
     (let [x (first coll)
           ncoll (set (rest coll))
           nresults (set (map #(conj % x) results))]
       (recur ncoll
              (clojure.set/union results nresults)))
     results)))



                                        ; 86. Happy numbers
                                        ; https://www.4clojure.com/problem/86
                                        ; Happy numbers are positive integers that follow a particular formula:
                                        ; take each individual digit, square it, and then sum the squares to get
                                        ; a new number. Repeat with the new number and eventually, you might get
                                        ; to a number whose squared sum is 1. This is a happy number. An unhappy
                                        ; number (or sad number) is one that loops endlessly. Write a function
                                        ; that determines if a number is happy or not.

(fn happy? [num]
  (letfn [(digits [x] (map #(-> % str read-string) (str x)))
          (square [x] (map #(int (Math/pow % 2)) x))]
    (loop [x num visited #{}]
      (let [sum (reduce + (square (digits x)))]
        (cond
          (= 1 sum) true
          (contains? visited sum) false
          :else (recur sum (conj visited sum)))))))



                                        ; 88. Symmetric Difference
                                        ; https://www.4clojure.com/problem/88
                                        ; Write a function which returns the symmetric difference of two sets.
                                        ; The symmetric difference is the set of items belonging to one but not
                                        ; both of the two sets.

(fn sdiff [a b]
  (clojure.set/union
    (clojure.set/difference a b)
    (clojure.set/difference b a)))



                                        ; 90. Cartesian Product
                                        ; https://www.4clojure.com/problem/90
                                        ; Write a function which calculates the <a
                                        ; href="http://en.wikipedia.org/wiki/Cartesian_product"> Cartesian
                                        ; product</a> of two sets.

(fn [a b]
  (set (for [x a y b] [x y])))



                                        ; 134. A nil key
                                        ; https://www.4clojure.com/problem/134
                                        ; Write a function which, given a key and map, returns true <a
                                        ; href="http://en.wikipedia.org/wiki/If_and_only_if">iff</a> the map
                                        ; contains an entry with that key and its value is nil.

(fn [k dict]
  (= (dict k true) nil))



                                        ; 145. For the win
                                        ; https://www.4clojure.com/problem/145
                                        ; Clojure's <a
                                        ; href="http://clojuredocs.org/clojure_core/clojure.core/for">for</a>
                                        ; macro is a tremendously versatile mechanism for producing a sequence
                                        ; based on some other sequence(s). It can take some time to understand
                                        ; how to use it properly, but that investment will be paid back with
                                        ; clear, concise sequence-wrangling later. With that in mind, read over
                                        ; these <code>for</code> expressions and try to see how each of them
                                        ; produces the same result.

(range 1 40 4)



                                        ; 156. Map Defaults
                                        ; https://www.4clojure.com/problem/156
                                        ; When retrieving values from a map, you can specify default values in
                                        ; case the key is not found:

(fn [v coll] (reduce #(assoc %1 %2 v) {} coll))



                                        ; 161. Subset and Superset
                                        ; https://www.4clojure.com/problem/161
                                        ; Set A is a subset of set B, or equivalently B is a superset of A, if A
                                        ; is "contained" inside B. A and B may coincide.

#{1 2}



                                        ; 162. Logical falsity and truth
                                        ; https://www.4clojure.com/problem/162
                                        ; In Clojure, only nil and false representing the values of logical
                                        ; falsity in conditional tests - anything else is logical truth.

1
