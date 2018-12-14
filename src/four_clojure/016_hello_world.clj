(ns four-clojure.016-hello-world)

;; #16 Hello World
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;; Difficulty:	Elementary

;; Write a function which returns a personalized greeting.

;; (= (__ "Dave") "Hello, Dave!")
;; (= (__ "Jenn") "Hello, Jenn!")
;; (= (__ "Rhea") "Hello, Rhea!")

;; Deconstruct the problem
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;; Looking for a function that will join values together to form a string.

;; Additionally we need to define a function that inserts a string inside the sentence.  We cannot simply put Hello in front of the name, we need to add an exclamation mark afterwards


;; REPL experiments
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;; The `str` function will join values and turn them into a string.

;; Joining two strings together is straight forward.
(str "Hello," "Dave")
;; => "Hello,Dave"

;; The `str` function does exactly what you specify.  So in the previous example there is no space between the strings that are joined.

;; Spaces need to be explicitly specified
(str "Hello, " "Dave")
;; => "Hello, Dave"

;; or you can include spaces separately, depending on which seems more readable.

(str "Hello," " " "Dave")
;; => "Hello, Dave"



;; Define a function that takes a persons name and inserts it between Hello, and !

(fn [person-name]
  (str "Hello, " person-name "!"))


;; and call that function definition with a persons name as an argument
((fn [person-name]
   (str "Hello, " person-name "!")) "Dave")
;; => "Hello, Dave!"


;; There is a short form for defining a function, using a reader macro.

#(str "I am a function definition using the reader macro")

;; And to call this function definition with an argument it needs to be put inside a list, `()`
;; Argument are used by placing a percent symbol, `%`, and the argument number within the function definition
;; `%1` is the first argument passed to the function.  Or you can just use `%`

(#(str %1 ", " "I am a function definition using the reader macro.  I take one argument")
 "Dave")
;; => "Dave, I am a function definition using the reader macro.  I take one argument"

;; Applying this short form to the 4Clojure answer we get:

;; using the short form of a function definition
#(str "Hello, " % "!")

;; and the short form of calling a function
(#(str "Hello, " % "!")
 "Mani")



;; Answers summary
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;; The normal form for a function definition is nicely readable
(fn [person-name]
  (str "Hello, " person-name "!"))

;; As this funciton is relatively simple, then even the short form is quite readable and succinct
#(str "Hello, " % "!")
