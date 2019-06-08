(ns four-clojure.040-interpose-a-seq)

;; #040 Interpose a seq
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;; Difficulty:	Easy
;; Topics:	seqs core-functions
;; Special Restrictions: interpose


;; Write a function which separates the items of a sequence by an arbitrary value.

;; (= (__ 0 [1 2 3]) [1 0 2 0 3])
;; (= (apply str (__ ", " ["one" "two" "three"])) "one, two, three")
;; (= (__ :z [:a :b :c :d]) [:a :z :b :z :c :z :d])


;; Deconstruct the problem
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;; Write a function that takes two arguments, a value and a collection

;; The function needs to work irrespective of the type of values

;; The function should place the value in between the elements of the collection,
;; but not before the first element or after the last element of the collection.

(apply str
       (reverse "jenny"))
;; => "ynnej"

(clojure.string/reverse "jenny")
;; => "ynnej"

;; REPL experiments
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;


;; Interpose works, but we cannot use it

(= (interpose 0 [1 2 3]) [1 0 2 0 3])

(= (apply str (interpose ", " ["one" "two" "three"])) "one, two, three")

(= (interpose :z [:a :b :c :d]) [:a :z :b :z :c :z :d])


;; loop / recur
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; with loop recur comes a lot of lines of code

(fn [value collection]
  (loop [current-collection collection
         new-collection     []]
    (if (> 1 (count current-collection))
      (recur (rest current-collection)
             (conj
               new-collection
               (first current-collection)
               value))
      new-collection)))


((fn [value collection]
   (loop [current-collection collection
          new-collection     []]
     (if (< 1 (count current-collection))
       (recur (rest current-collection)
              (conj
                new-collection
                (first current-collection)
                value))
       new-collection)))
 0 [1 2 3])
;; => [1 0 2 0]

;; nearly right...


((fn [value collection]
   (loop [current-collection collection
          new-collection     []]
     (if (< 1 (count current-collection))
       (recur (rest current-collection)
              (conj
                new-collection
                (first current-collection)
                value))
       (conj new-collection
             (first current-collection)))))
 0 [1 2 3])
;; => [1 0 2 0 3]



;; Recursive function
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Let get a little more abstract


#_(fn impose [value collection]
    (conj []
          (first collection)
          value
          (impose 0 (rest collection))))

;; infinite loop, doh!
#_((fn impose [value collection]
     (conj []
           (first collection)
           value
           (impose 0 (rest collection))))
   0 [1 2 3])



(fn impose [value collection]
  (cond
    (>= 1 (count collection)) collection
    :else
    (concat
      [(first collection) value]
      (impose value (rest collection)))))


((fn impose [value collection]
   (cond
     (>= 1 (count collection)) collection
     :else
     (concat
       [(first collection) value]
       (impose value (rest collection)))))
 0 [1 2 3])
;; => (1 0 2 0 3)



;; Interleave
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;


(fn [value collection]
  (butlast
    (interleave collection (repeat value))))

((fn [value collection]
   (butlast
     (interleave collection (repeat value))))
 0 [1 2 3])

(butlast [1 0 2 0 3 0])
;; => (1 0 2 0 3)


(fn [value collection]
  (next (interleave (repeat value) collection)))


((fn [value collection]
   (next (interleave (repeat value) collection)))
 0 [1 2 3])
;; => (1 0 2 0 3)


;; Mapcat
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(fn [value collection]
  (drop 1 (mapcat #(list value %) collection)))






;; Answers summary
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;


(fn [value collection]
  (next (interleave (repeat value) collection)))

(fn [value collection]
  (butlast
    (interleave collection (repeat value))))
