(ns four-clojure.041-drop-every-nth-item)

;; #41 Drop Every Nth Item
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;; Difficulty:	Easy
;; Topics:	seqs

;; Write a function which drops every Nth item from a sequence.

;; (= (__ [1 2 3 4 5 6 7 8] 3) [1 2 4 5 7 8])
;; (= (__ [:a :b :c :d :e :f] 2) [:a :c :e])
;; (= (__ [1 2 3 4 5 6] 4) [1 2 3 5 6])


;; Deconstruct the problem
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;; If it was just a single test with consecutive integer numbers,
;; then we could use filter or remove to just return the values we wanted

(filter #(not= 0 (rem % 3)) [1 2 3 4 5 6 7 8])
;; => (1 2 4 5 7 8)

(remove #(= 0 (rem % 3)) [1 2 3 4 5 6 7 8])
;; => (1 2 4 5 7 8)

;; As the values in the tests are not all integers then its a bit more tricky
;; because we would need to use a different function for each kind of data.

;; If we can take a positional approach, that would work regardless of type of values.


;; REPL experiments
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Using partition we can group the values of the collection together.
;; A partition of 2 will return values grouped in pairs.
;; Using a step of three will make the partition get the next two values after the third
;; so we create a new collection without each third value from the original collection.

(partition 2 [1 2 3 4 5 6 7 8])
;; => ((1 2) (3 4) (5 6) (7 8))

(partition 2 3 [1 2 3 4 5 6 7 8])
;; => ((1 2) (4 5) (7 8))

(flatten
  (partition 2 3 [1 2 3 4 5 6 7 8]))
;; => (1 2 4 5 7 8)


(fn [collection step]
  (flatten (partition (dec step) step collection)))

#(flatten (partition (dec %2) %2 %1))


(partition 2 [1 2 3])
;; => ((1 2))

;; This passes the first two tests, however, we are dropping some of the values
;; in the third test as its not a complete partition.

;; The partition function does have a pad value, so it will create all the partitions
;; and fill any missing elements of the partition with a specified value (the pad value).

;; But what to pad with?
;; We need a value that is not really a value or we can easily drop

;; We are already using flatten to get the right shape,
;; so are there any values that flatten will get rid of for us

(flatten 0)
;; => ()

;; Unfortunately 0 is not a legal value for a partition pad value
;; java.lang.IllegalArgumentException: Don't know how to create ISeq from: java.lang.Long

;; so partition needs a sequence type for the pad, so we could use

(flatten '())
;; => ()
(flatten nil)
;; => ()
(flatten "")
;; => ()

(#(flatten (partition (dec %2) %2 nil %1))
 [1 2 3 4 5 6] 4)
;; => (1 2 3 5 6)
;; => (1 2 3 5 6)


;; Applying this flatten approach to our answer so far

#(flatten (partition (dec %2) %2 nil %1))

;; This passes all tests and gives a Code Golf Score of 34



(partition-all 2 [1 2 3])
;; => ((1 2) (3))

;; Rather than using a pad value with partition,
;; we can use partition-all which does not drop incomplete partitions
;; this gives a nice abstraction of partition with a pad

#(flatten (partition-all (dec %2) %2 %1))


;; Other Answers
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;; Keep indexed

(fn [s x]
  (keep-indexed
    (fn [i a] (when (> (mod (inc i) x) 0) a))
    s))

((fn [s x]
   (keep-indexed
     (fn [i a] (when (> (mod (inc i) x) 0) a))
     s))
 [1 2 3 4 5 6 7 8] 3)


;; Answers summary
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;; Most readable answer




#(apply concat (partition-all (dec %2) %2 %1))


(fn [collection step]
  (flatten (partition-all (dec step) step collection)))


#(flatten (partition-all (dec %2) %2 %1))


;; Overthought answers

;; Least valuable answer
