(ns four-clojure.156-map-defaults)

;; 156 - map defaults
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;; Difficulty:	Elementary
;; Topics:	seqs
;; When retrieving values from a map, you can specify default values in case the key is not found:
;;
;; (= 2 (:foo {:bar 0, :baz 1} 2))

;; However, what if you want the map itself to contain the default values? Write a function which takes a default value and a sequence of keys and constructs a map.

#_(= (__ 0 [:a :b :c]) {:a 0 :b 0 :c 0})
#_(= (__ "x" [1 2 3]) {1 "x" 2 "x" 3 "x"})
#_ (= (__ [:a :b] [:foo :bar]) {:foo [:a :b] :bar [:a :b]})

;; Deconstruct the problem
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;


;; REPL experiments
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;


;; Answers summary
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;


(fn [default coll]
  (map #(hash-map % default) coll))


(map merge
     (map #(hash-map % 0) [:a :b :c]))

(reduce merge
        (map #(hash-map % 0) [:a :b :c]))


#(reduce merge
         (map #(hash-map %2 %)))




(fn [default-value coll]
  (reduce merge
          (map #(hash-map % default-value) coll)) )



#_(map #(% 0) {:a :b :c})




(mapcat #(hash-map % 0) )
;; => ([:a 0] [:b 0] [:c 0])

(flatten (mapcat #(hash-map % 0) {:a 0 :b 0 :c 0}))
;; => (:a 0 :b 0 :c 0)


(hash-map
 (interpose 0 {:a 0 :b 0 :c 0}))

;; Solution (readable)

(fn [default-value coll]
  (reduce merge
          (map #(hash-map % default-value) coll)) )

;; Solution (low golf score)

(fn [v c]
  (reduce merge
          (map #(hash-map % v) c)))

;; alternative solutions

(fn [x s] (reduce #(assoc %1 %2 x) {} s))


#(apply hash-map (interleave %2 (repeat %)))
#(apply assoc {}
        (interleave %2 (repeat %1)))
#(into {} (map vector %2 (repeat %)))
#(into {} (map (fn [e] {e %}) %2))
#(into {}
       (for [k %2]
         [k %]))

#(zipmap %2 (repeat %1))
#(zipmap %2 (repeat (count %2) %1))
