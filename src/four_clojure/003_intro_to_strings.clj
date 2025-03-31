(ns four-clojure.003-intro-to-strings)


;; 003: Intro to Strings
;;

;; Difficulty:	Elementary
;; Topics:

;; Clojure strings are Java strings. This means that you can use any of the Java string methods on Clojure strings.
;; test not run

;; (= __ (.toUpperCase "hello world"))


;; Deconstruct the problem
;;

;; An opportunity to look at the interoperability that Clojure provides with the host platform, in this case Java JVM.

;; The `.toUpperCase` in this expression is evaluated as a function call, although it is actually a method call on the java.lang.String object.
;; https://docs.oracle.com/javase/8/docs/api/java/lang/String.html

;; All string types in Clojure use the java.lang.String object, so we can call any of that objects methods on a Clojure string.

;; All Clojure projects on the JVM include all Java objects defined in the `java.lang` package.

;; To solve the challenge we simply need to compare the result of the `.toUpperCase` expression with a string containing the upper case version of the "hello world" string.

;; REPL experiments
;;

;; It should be fairly obvious that this expression returns a string with all the characters in upper case.

(.toUpperCase "hello world")


;; => "HELLO WORLD"

;; We can also check to see what type a Clojure string uses

(type "hello world")


;; => java.lang.String

;; And the type of the result from evaluating the `.toUpperCase` function

(type (.toUpperCase "hello world"))


;; => java.lang.String



;; Answers summary
;;

;; Most readable answer
"HELLO WORLD"


;; Overthought answers

;; Least valuable answer
(.toUpperCase "hello world")
