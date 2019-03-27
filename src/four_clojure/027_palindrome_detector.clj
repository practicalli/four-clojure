(ns four-clojure.027-palindrome-detector)

;; Difficulty:	Easy
;; Topics:	seqs

;; Write a function which returns true if the given sequence is a palindrome.

;; Hint: "racecar" does not equal '(\r \a \c \e \c \a \r)

;; (false? (__ '(1 2 3 4 5)))
;; (true? (__ "racecar"))
;; (true? (__ [:foo :bar :foo]))
;; (true? (__ '(1 1 3 3 1 1)))
;; (false? (__ '(:a :b :c)))


;; Deconstruct the problem
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;; A palindrome is something that reads the same when written in reverse

;; So we need to:
;; reverse the argument
;; compare the reversed argument with the original
;; return true or false

;; The hint tells us that a string is not equivalent to a collection of characters.
;; Some Clojure functions will treat a string as a collection of characters,
;; so we may have to do something specific for string.

;; REPL experiments
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; clojure.core/reverse

;; Using the `reverse` function looks promising
(reverse '(1 2 3 4 5));; => (5 4 3 2 1)

;; and we can check for equality quite easily
(= '(1 2 3 4 5) (reverse '(1 2 3 4 5)))
;; => false

;; we can define a function to use for the comparison,
;; which will be our answer to the 4Clojure challenge

(fn [pattern]
  (= pattern (reverse pattern)))

;; then test the function definition by calling it with an argument
((fn [pattern]
   (= pattern (reverse pattern)))
 '(1 2 3 4 5))
;; => false

;; and with an argument that should be true
((fn [pattern]
   (= pattern (reverse pattern)))
 '(1 2 3 2 1))
;; => true


;; so we have a general solution to the 4Clojure challenge:
(fn [pattern]
  (= pattern (reverse pattern)))


;; When using this general solution with a string though,
;; we find the limitation to this approach.
((fn [pattern]
   (= pattern (reverse pattern)))
 "racecar")
;; => false

;; lets diagnose the problem by seeing the result of  `reverse` on a string
(reverse "racecar")
;; => (\r \a \c \e \c \a \r)

;; We can see the underlying types are different using the `type` function
(type \r)
;; => java.lang.Character
(type "string")
;; => java.lang.String

;; so as the hint in the 4Clojure question suggested, a string is not the same as a sequence of characters

;; Converting all values to a sequence
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;; What if we just converted everything to a sequence and then compared it with the result of `reverse`
;; we can use the `seq` function to create a sequence

(seq "racecar")
;; => (\r \a \c \e \c \a \r)


;; so we can update our general solution to use `seq` on the argument and compare the two values

(fn [pattern]
  (= (seq pattern) (reverse pattern)))

;; test our updated function definition with a string
((fn [pattern]
   (= (seq pattern) (reverse pattern)))
 "racecar")
;; => true

;; and testing it with numbers

((fn [pattern]
   (= (seq pattern) (reverse pattern)))
 '(1 2 3 4 5))

((fn [pattern]
   (= (seq pattern) (reverse pattern)))
 '(1 2 3 2 1))


(#(= (seq %) (reverse %))
  [1 2 3 2 1])


;; Using functions specifically for working with strings in Clojure
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; clojure.string/reverse

;; The `clojure.string` namespace has many functions specifically designed to work with string arguments.
;; These functions will treat a string as a string and return the result as a string too.

(clojure.string/reverse "racecar")
;; => "racecar"

;; Rather than convert everything to a sequence,
;; we could use an if condition to test for a string
;; It would not make sense to convert other types of values to a string

;; our new function would look like this:

(fn [pattern]
  (if (string? pattern)
    (= pattern (clojure.string/reverse pattern))
    (= pattern (reverse pattern))))


((fn [pattern]
   (if (string? pattern)
     (= pattern (clojure.string/reverse pattern))
     (= pattern (reverse pattern))))
 "racecar")
;; => true

;; This approach minimises the conversion of data into sequences,
;; although it does need to check the type of data it received as an argument.




;; Answers summary
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;; My preferred answer, as it describes the intent clearly

(fn [pattern]
  (if (string? pattern)
    (= pattern (clojure.string/reverse pattern))
    (= pattern (reverse pattern))))

;; a shorter form of writing a function definition inline
#(if (string? %)
   (= % (clojure.string/reverse %))
   (= % (reverse %)))


;; If we were going to use a palindrome function multiple times
;; we could define a function in the namespace scope.
(defn palindrome? [pattern]
  (if (string? pattern)
    (= pattern (clojure.string/reverse pattern))
    (= pattern (reverse pattern))))

(palindrome? [1 2 3 2 1])


;; An answer that simply converts everything to seq regardless

(fn [pattern]
  (= (seq pattern) (reverse pattern)))

;; a shorter form of writing a function definition inline
#(= (seq %) (reverse %))




;; Miscellaneous examples

(#(str %1 %2 %3) 1 2 3)
;; => "123"

((fn [a b c]
   (str a b c)) 1 2 3)


;; defining a function
(fn [a b c]
  (str a b c))

;; calling a function with an argument
((fn [a b c]
   (str a b c)) 1 2 3)

;; defining a function
#(str %1 %2 %3)

;; calling a function with an argument
(#(str %1 %2 %3) 1 2 3);; => "123"
