(ns four-clojure.037-regular-expression)

;; #037 Regular Expressions
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;; Difficulty:	Elementary
;; Topics:	regex syntax

;; Regex patterns are supported with a special reader macro.

;; (= __ (apply str (re-seq #"[A-Z]+" "bA1B3Ce ")))


;; Deconstruct the problem
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;; The easiest way to solve this problem is to evaluate the right hand side and get the result.

;; We will explore regular expressions in Clojure with a few examples.
;; Regular expressions, or regexs for short, are a powerful language for matching text patterns.


;; REPL experiments
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;; lets find out the answer by evaluating the right hand side

(apply str (re-seq #"[A-Z]+" "bA1B3Ce "))
;; => "ABC"

(= "ABC" (apply str (re-seq #"[A-Z]+" "bA1B3Ce ")))

(re-seq #"[A-Z]+" "bA1B3Ce ")
;; => ("A" "B" "C")

(reduce str '("A" "B" "C"))
;; => "ABC"


;; Regular Expressions (regex) - overview
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;; Writing regular expressions is like asking the question:
;; "Does a group of characters match a specific pattern?"

;; In Clojure, a regex pattern is contained within #""
;; So the pattern cat would be expressed as

#",,,"

#"cat"

;; We can check this is correct by looking at the type

(type #"cat")
;; => java.util.regex.Pattern


(re-find #"cat"  "I like cats")

(re-find #"cat"  "I like to concatonate")
;; => "cat"


(re-find #"cat"  "I like dogs instead")
;; => nil

;; Several Clojure functions take a regular expression pattern and a string,
;; searching for the regex pattern within the string.

;; If the search is successful, the matching string is returned.
;; If no match is found, `nil` is returned.
;; `nil` acts as a falsey value, so we can test if the search was successful.

;; Clojure uses the host language regular expressions, so Clojure on the JVM uses Java regexes and for ClojureScript, it’s Javascript regexes.
;; Clojure: java.util.regex.Pattern
;; ClojureScript: RegExp



;; What are regular expressions used for
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;; Validating information
;; - phone numbers
;; - email addresses  john@practical.li john.stevenson@megacorp.com
;; - passwords (size, contents) - special character !@...

;; Searching strings
;; - words in sentences
;; - incorrect or undesirable characters
;; - extracting sections of text
;; - substituting text
;; - reformatting / cleaning text


;; General Tools
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;; https://regexr.com/
;; https://regex101.com/



;; Functions used with regular expressions
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;; clojure.string/
;; split
;; replace
;; replace-first
;; re-quote-replacement

;; re-find
;; re-seq
;; re-matches
;; re-groups

;; Not used much
;; re-pattern  - `re-seq` typically used instead
;; re-matcher

;; The most commonly used functions are:

;; clojure.core/re-find
;; Returns the next regex match, if any, of string to pattern, using
;; java.util.regex.Matcher.find().  Uses re-groups to return the
;; groups.


;; clojure.core/re-seq
;; ([re s])
;; Returns a lazy sequence of successive matches of pattern in string,
;; using java.util.regex.Matcher.find(), each such match processed with
;; re-groups.

;; use re-seq to see if there is one and only one @ in an email


;; Checking phone numbers
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;


;; Assume we have a website that takes international orders for businesses
;; and we want to check the phone number of a customer matches the location of the address

;; In London the regional telephone code is 020
;; So if our number starts with 020 and the address is London, then we are more secure about sending the parcel.

;; Number formats are not universally the same, so we could have any of these

(def phone-no-gaps "0201231234")
(def phone-one-gap "020 1231234")
(def phone-two-gaps "020 123 1234")
(def phone-two-dash "020-123-1234")

;; And lets have a non-London number

(def not-london "0178 1231234")

;; Literal matches
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;; Using the specific numbers for the London region, we can easily do a check.

(re-find #"020" phone-no-gaps)

(re-find #"020" phone-one-gap)


(re-find #"020" not-london)
;; => nil


(map #(re-find #"020" %)
     [phone-no-gaps phone-one-gap phone-two-gaps phone-two-dash])
;; => ("020" "020" "020" "020")


;; refactor out the numbers into its own def for further use
(def all-london-number-formats [phone-no-gaps phone-one-gap phone-two-gaps phone-two-dash])


(map #(re-find #"020" %) all-london-number-formats)
;; => ("020" "020" "020" "020")

(count
  (map #(re-find #"020" %) all-london-number-formats) )
;; => 4

(= 4
   (count
     (filter  ,,, )
     (map #(re-find #"020" %) all-london-number-formats) )  )
;; => true


(def all-numbers (conj all-london-number-formats not-london))


(map #(re-find #"020" %) all-numbers)
;; => ("020" "020" "020" "020" nil)


;; How would we check numbers if we didnt use regex ?
;; If we use clojure.string/split it still uses a regex


(clojure.string/split "020 123    1234" #"\s+")
;; => ["020" "123" "1234"]

#" "

;; Alternatively, if we know the position of the characters in the string,
;; we can use `subs` to extract start and end points

;; clojure.core/subs
;; [s start]
;; [s start end]

;; Returns the substring of s beginning at start inclusive, and ending
;; at end (defaults to length of string), exclusive.

(subs "0201231234" 0 3)
;; => "020"


;; This is of course very limiting as our text could be anywhere in the string
;; for example there could be spaces at the start

(subs "    0201231234" 0 3)
;; => "   "

(count [1 nil 3])
;; => 3




;; Getting just part of a string
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;; If you just want an initial of the first word
(re-find #"\w" "Jenny Jetpack")
;; => "J"


;; If you want the start of someones name, say the first three letters of the first word.

(re-find #"\w\w\w" "Jenny Jetpack")
;; => "Jen"


;; This even works if you have other values before the first word
;; numbers are not words in regex

(re-find #"\w\w\w" "1 2 3 Jenny Jetpack")
;; => "Jen"


;; If I want the digits, then I can use \d


(re-find #"\d" "1 2 3 Jenny Jetpack")
;; => "1"


(re-find #"\d\d\d" "123 Jenny Jetpack")
;; => "123"


;; Matching several patterns - using OR
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(re-find #"cat|kitten" "Sometimes a cat is called pussycat")
;; => "cat"


(re-find #"cat|kitten" "My kitten has grown up into a big cat")
;; => "kitten"


(re-find #"cat|kitten" "A young cat is called a kitten")
;; => "cat"


;; re-find returns the first match


(re-seq #"cat|kitten" "Sometimes a cat is called pussycat")
;; => ("cat" "cat")


(re-seq #"cat|kitten" "My kitten has grown up into a big cat")
;; => ("kitten" "cat")


(re-seq #"cat|kitten" "A young cat is called a kitten")
;; => ("cat" "kitten")



;; Checking login / registration forms text
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;


;; Define a function to check for multiple login names

(defn validate-user
  [username]
  (if (re-find #"jenny|sally|rachel|michelle" username)
    true
    false))

(validate-user "jenny")
;; => true

(validate-user "brian")
;; => false


(defn validate-similar-usernames
  [username]
  (if (re-find #"jen+" username)
    true
    false))

(validate-similar-usernames "je")
;; => false

(validate-similar-usernames "jen")
;; => true

(validate-similar-usernames "jenn")
;; => true

(validate-similar-usernames "jenny")
;; => true

(validate-similar-usernames "jennifer")
;; => true




;; Matching all characters of the alphabet
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;; It is inefficient to define a literate pattern for the alphabet

#"a|b|c|d|e|f|g|h|i|j|k|l|m|n|n|o|p|q|r|s|t|u|v|w|x|y|z"

;; Instead, you can use a range of characters

#"[a-z]"

;; [ ] defines a character set
;; a is the start of the range
;; z is the end of the range



(defn match-any-word
  [word]
  (if (re-find #"[a-z]" word)
    true
    false))

(match-any-word "Hello regex")

(match-any-word "djoieriueriueo")

(match-any-word "HELLO")
;; => false


(defn match-any-word-any-case
  [word]
  (if (re-find #"[a-zA-Z]" word)
    true
    false))



(defn match-word-partial-alphabet
  [word]
  (if (re-find #"[a-c]" word)
    true
    false))

(match-word-partial-alphabet "alphabet")

(match-word-partial-alphabet "cat")

(match-word-partial-alphabet "dog")
;; => false



(defn only-vowels
  [word]
  (re-seq #"a|e" word))

(only-vowels "alphabet")

(only-vowels "beetroot")

(only-vowels "zoo")


;; Matching multiple times
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;; Using the + we can look for the previous character multiple times

(re-seq #"a+" "aardvark")
;; => ("aa" "a")


;; aardvark matches twice,
;; first for the aa
;; and then for the second a


;; Managing case (upper and lower)

(re-seq #"a+" "Apple")

(re-seq #"a+" "Aardvark")

;; we can be literal and use A, B, C
;; or we can use a range as before


(re-seq #"[a-mA-M]" "Apple")


(re-seq #"[a-mA-M]" "Aardvark")


;; Use match multiple times with a range
(re-seq #"[a-mA-M]+" "Aardvark")
;; => ("Aa" "d" "a" "k")




;; Matching spaces
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

#"\s"


;; Match two words with a space in between
#"(\w+)\s(\w+)"


(re-matches #"\w+\s\w+" "Jenny Jetpack")
;; => "Jenny Jetpack"


(re-matches #"(\w+)\s(\w+)" "Jenny Jetpack")
;; => ["Jenny Jetpack" "Jenny" "Jetpack"]


;; Destructure the results of the re-matches function
;; so we only use the two words

(let [[_ first-name last-name] (re-matches #"(\w+)\s(\w+)" "Jenny Jetpack")]
  (if first-name ;; successful match
    (str "First name: " first-name " , Last name: " last-name)
    (str "Unparsable name")))
;; => "First name: Jenny , Last name: Jetpack"


(clojure.string/split  "Jenny Jetpack" #" ")
;; => ["Jenny" "Jetpack"]





;; Matching numbers
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

#"[0-9]"






;; Other Examples
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;


;; Find all the links inside a web page

;; TODO needs fixing
(let [str_response (slurp "https://slashdot.org")]
  (map #(println (str "Match: " %))
       (re-seq #"(?sm)href=\"([a-zA-Z.:/]+)\"" str_response)))



;; Answers summary
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
