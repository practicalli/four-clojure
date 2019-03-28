(ns four-clojure.029-get-the-caps)

;; #029 Get the Caps
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;; Difficulty:	Easy
;; Topics:	strings

;; Write a function which takes a string and returns a new string containing only the capital letters.

;; (= (__ "HeLlO, WoRlD!") "HLOWRD")
;; (empty? (__ "nothing"))
;; (= (__ "$#A(*&987Zf") "AZ")



;; Deconstruct the problem
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;; Return only the characters that are upper-case from a given string

;; Essentially we want to filter out anything that is not an upper-case character and return what is left.

;; We could convert everything to ascii numbers and keep only those numbers that are within the range of upper-case characters.  This feels a bit low level though.

;; Regular Expressions are relatively simple and arguably more expressive.
;; https://lispcast.com/clojure-regex/


;; REPL experiments
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;; We can make stings upper-case
(clojure.string/upper-case "make me upper-case")
;; => "MAKE ME UPPER-CASE"

;; Testing if a collection contains an upper-case character

(contains? "ABCDEFG" \A)
;; java.lang.IllegalArgumentException
;; contains? not supported on type: java.lang.String

;; `contains?` is also not giving the results we want

(contains? #{"A"} "ABCDEFG")
;; => false

(contains? #{"A"} #{"A" "B" "C"})
;; => false


;; the `some` function is an alternative to `contains?`

(some "ABCDEFG" \A)
;; java.lang.IllegalArgumentException
;; Don't know how to create ISeq from: java.lang.Character

(some A "ABCDEFG" )
;; java.lang.ClassCastException
;; java.lang.String cannot be cast to clojure.lang.IFn

(some #{\A} "ABCDEFG" )
;; => \A


;; regular expression (regex)
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; This is a regular expression in clojure,
;; its a string prefixed with a reader macro, `#`
;; The string defines a pattern, such as all upper-case characters
#"[A-Z]+"

;; regular expressions do not work with `contains?`, `some`, or `filter` functions

(contains? #"[A-Z]+" "A")
;; java.lang.IllegalArgumentException
;; contains? not supported on type: java.util.regex.Pattern

(some #"[A-Z]+" "AbcdE")
;; java.lang.ClassCastException
;; java.util.regex.Pattern cannot be cast to clojure.lang.IFn

(filter #"[A-Z]+" "AbcdE")
;; java.lang.ClassCastException
;; java.util.regex.Pattern cannot be cast to clojure.lang.IFn

(filter #(= #"[A-Z]+" %) "AbcdE")
;; => ()



;; using re-seq
;;;;;;;;;;;;;;;
;; Create a new sequence using a regular expression as a filter

;; Returns a lazy sequence of successive matches of pattern in string,
;; using java.util.regex.Matcher.find(), each such match processed with
;; re-groups.

;; A simple re-seq with a regex pattern for upper-case characters
(re-seq #"[A-Z]+" "AbcdE")
;; => ("A" "E")


;; With the 4Clojure test data

(re-seq  #"[A-Z]+" "HeLlO, WoRlD!")
;; => ("H" "L" "O" "W" "R" "D")

;; We need to combine the result into a string
;; although `str` by itself doesnt give the right result.
(str '("H" "L" "O" "W" "R" "D"))
;; => "(\"H\" \"L\" \"O\" \"W\" \"R\" \"D\")"

;; We can use a `reduce` function...
(reduce + [1 2 3 4 5])
;; => 15

;; and for our challenge we can `reduce` using the `str` function
(reduce str '("H" "L" "O" "W" "R" "D") )
;; => "HLOWRD"


;; Putting these two parts together


;; Answers summary
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;; If you are okay with using regular expression
;; Probably more appropriate where the pattern is more complex
(fn [pattern]
  (apply str (re-seq #"[A-Z]+" pattern)))

;; or using the function definition short-hand syntax
#(apply str
        (re-seq #"[A-Z]+" %))

;; A more descriptive alternative
(fn [pattern]
  (clojure.string/join (re-seq #"[A-Z]+" pattern)))
