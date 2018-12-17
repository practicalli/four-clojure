(ns four-clojure.048-intro-to-some)

;; #48 Intro to some
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;; Difficulty:	Easy
;; Topics:

;; The some function takes a predicate function and a collection.
;; It returns the first logical true value of (predicate x) where x is an item in the collection.

;; (= __ (some #{2 7 6} [5 6 7 8]))
;; (= __ (some #(when (even? %) %) [5 6 7 8]))


;; Deconstruct the problem
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;; The `some` function is used to see if some value or values are contained within a collection.

;; `some` is often a better choice than what would seem to be the more obvious `contains?` function.

;; REPL experiments
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;; Lets experiment with the `some` function

;; Using a predicate function, then if the predicate returns `true` anywhere in the collection,
;; `true` will be returned
(some odd? [1 2 3 4 5])
;; => true

(some even? [1 2 3 4 5])
;; => true

;; for specific values, we would need to use a lambda function (inline function)
(some #(= 3 %) [1 2 3 4 5])
;; => true


;; Using a set as a predicate function
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(some #{3} #{1 2 3 4 5})
;; => 3


;; The first value found in both the sets is returned
(some #{2 7 6} [5 6 7 8])
;; => 6

;; only the order of the collection is relevant,
;; not the order of values in the set used as a predicate
;; each value in the set is tested with the first value in vector
;; continuing until there is a match or the end of the vector is reached.
(some #{2 7 6 8} [8 6 7 5])
;; => 8


;; Answers summary
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

6


;; production examples
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;; A simple production example is to check if a user id is part of a role

;; userid is defined as uuids

;; Generate some UUIDs for user accounts

(java.util.UUID/randomUUID)
;; => #uuid "db2ad2a0-a046-4366-a7e6-b9b16010c546"
;; => #uuid "85dddefd-58a7-401b-a5da-b0fa7070047e"

;; Put the account ids into a set and define a name
(def admin-set
  #{#uuid "db2ad2a0-a046-4366-a7e6-b9b16010c546"
    #uuid "85dddefd-58a7-401b-a5da-b0fa7070047e"})

;; or we can also use a vector
(def admin-vector
  [#uuid "db2ad2a0-a046-4366-a7e6-b9b16010c546"
   #uuid "85dddefd-58a7-401b-a5da-b0fa7070047e"])

;; we can see if a userid is in the admin set using the `some` function
(some #{#uuid "db2ad2a0-a046-4366-a7e6-b9b16010c546"} admin-set)
;; => #uuid "db2ad2a0-a046-4366-a7e6-b9b16010c546"

;; If the userid is in the admin set, it returns the uuid.
;; so we can check if the return value is a uuid type.
(uuid?
 (some #{#uuid "db2ad2a0-a046-4366-a7e6-b9b16010c546"} admin-set))
;; => true

;; and the same works for a vector
(some #{#uuid "db2ad2a0-a046-4366-a7e6-b9b16010c546"} admin-vector)
;; => #uuid "db2ad2a0-a046-4366-a7e6-b9b16010c546"

(uuid?
 (some #{#uuid "db2ad2a0-a046-4366-a7e6-b9b16010c546"} admin-vector))
;; => true

;; using a simple lambda function we can create a predicate
;; to test if a specific userid is part of the set
(some #(= #uuid "db2ad2a0-a046-4366-a7e6-b9b16010c546" %)
      admin-set)
;; => true

(some #(= #uuid "db2ad2a0-a046-4366-a7e6-b9b16010c546" %)
      admin-vector)
;; => true
