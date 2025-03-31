(ns four-clojure.055-count-occurances)


;; #054 Count occurances
;;

;; Difficulty:	Medium
;; Topics:	seqs core-functions
;; Special Restrictions: frequencies

;; Write a function which returns a map containing the number of occurences of each distinct item in a sequence.

;; (= (__ [1 1 2 3 2 1 1]) {1 4, 2 2, 3 1})
;; (= (__ [:b :a :b :a :b]) {:a 2, :b 3})
;; (= (__ '([1 2] [1 3] [1 3])) {[1 2] 1, [1 3] 2})



;; Deconstruct the problem
;;

;; Define a function that takes a sequence as an argument and returns a map

;; The keys of the map are the unique numbers from the sequence

;; The value of each key represents the occurances of the key in the original sequence


;; REPL experiments
;;


;; In the previous challenge we tried `group-by` but it didn't help.  It does help in this challenge though.

(group-by identity [1 1 2 3 2 1 1])


;; => {1 [1 1 1 1], 2 [2 2], 3 [3]}


;; Now we just need to update the values in a map and replace the elements with a count of the elements

;; If we replace the value of each key, a vector of occurances, with the count of each vector, then we have the right result

;; We can use a reducing function to update all the values in the map.
;; The reducing function will create a new map and associate new values as reduce iterates



;; names used
;; k - key
;; v - value
;; xs - sequence

;; a reduce that takes an empty maps and the grouped elements of the sequence
;; associates the existing key with the result of calling the count function on the value into the new map

{:k "v" ,,,,}


(reduce
  (fn [xs [k v]]
    (assoc xs k (count v)))
  {} (group-by identity [1 1 2 3 2 1 1]))


;; Put this into a function that takes a sequence as an argument

(fn [xs]
  (reduce
    (fn [reducing-map [k v]]
      (assoc reducing-map k (count v))) {} (group-by identity xs)))


;; test out the function with one of the 4Clojure tests

((fn [xs]
   (reduce
     (fn [reducing-map [k v]]
       (assoc reducing-map k (count v))) {} (group-by identity xs)))
 [1 1 2 3 2 1 1])


;; refactoring the code to use `into`

;; all of the results of mapping the function that counts the value
;; are put into the {}

(fn [xs]
  (into {}
        (map
          (fn [[k v]] {k (count v)})
          (group-by identity xs))))


((fn [xs]
   (into {}
         (map (fn [[k v]] {k (count v)}) (group-by identity xs))))
 [1 1 2 3 2 1 1])


;; Answers summary
;;

;; into and map

(fn [xs]
  (into {}
        (map
          (fn [[k v]] {k (count v)})
          (group-by identity xs))))


;; assoc and reduce

(fn [xs]
  (reduce
    (fn [reducing-map [k v]]
      (assoc reducing-map k (count v))) {} (group-by identity xs)))
