(ns four-clojure.061-map-construction)


;; #61 - Map Construction
;;
;; Difficulty:	Easy
;; Topics:	core-functions
;; Special Restrictions: zipmap

;; Write a function which takes a vector of keys and a vector of values and constructs a map from them.

;; Tests
;; (= (__ [:a :b :c] [1 2 3]) {:a 1, :b 2, :c 3})
;; (= (__ [1 2 3 4] ["one" "two" "three"]) {1 "one", 2 "two", 3 "three"})
;; (= (__ [:foo :bar] ["foo" "bar" "baz"]) {:foo "foo", :bar "bar"})


;; Using zipmap
;;

;; If we could use zipmap then the answer would be simple

(= (zipmap [:a :b :c] [1 2 3]) {:a 1, :b 2, :c 3})


;; => true

(= (zipmap [:a :b :c] [1 2 3]) {:a 1, :b 2, :c 3})


;; => true

(= (zipmap [:foo :bar] ["foo" "bar" "baz"]) {:foo "foo", :bar "bar"})


;; => true

;; check if keys are unique, duplicates will overwrite previous associations
(zipmap [:a :b :a] [1 2 3])


;; => {:a 3, :b 2}



;; So we could figure out the algorithm that zipmap uses,
;; or just go and look at the source code
;; https://github.com/clojure/clojure/blob/clojure-1.9.0/src/clj/clojure/core.clj#L3063


#_(defn zipmap
    "Returns a map with the keys mapped to the corresponding vals."
    {:added  "1.0"
     :static true}
    [keys vals]
    (loop [map {}
           ks  (seq keys)
           vs  (seq vals)]
      (if (and ks vs)
        (recur (assoc map (first ks) (first vs))
               (next ks)
               (next vs))
        map)))


;; Essentially the zipmap function
;; - iterates over the sequence of keys and values using loop recur
;; - associates a pair of values onto a map for each iteration

;; Other ideas
;; Partition - this only works on a single collection, as does other partition variants.  Also saw group-by but this is only one collection as well.



;; Analyse the problem
;;

;; Take the first element from each of the collections passed as arguments
;; Combine each element into a pair of values and keep them safe
;; get the next element from each collection to make a pair of values(the first of the rest of the collection)
;; continue until one of the collections is empty

;; Lets take a very simple approach to start with...

;; We can combine two collections of values using the map and vector functions

;; vector puts its arguments into a vector

(vector 1 2 3 4)


;; => [1 2 3 4]


;; map will iterate a function over one or more collections

(map vector [:a :b :c] [1 2 3])


;; => ([:a 1] [:b 2] [:c 3])


;; map can also iterate over several collections
;; so long as the function used with it can take all the aguments
;; that map passes to that function each time.

(map vector [:a :b :c] [1 2 3] ["carrot" "beetroot" "kale"])


;; => ([:a 1 "carrot"] [:b 2 "beetroot"] [:c 3 "kale"])

;; If there is a different number of values in the collections,
;; map will stop when it reaches the end of the collection with the fewest values.

(map vector [:a :b :c] [1 2 3 4] ["carrot" "beetroot" "kale" "celery"])


;; => ([:a 1 "carrot"] [:b 2 "beetroot"] [:c 3 "kale"])


;; so we can join values together from multiple collections in the way we need.
;; However, we are not returning them as a map.


;; REPL experiments
;;
;; We want to create a paring of values from the first and second vectors
;; Then each pair should be made into a key value pair within a map data structure.

;; The map function will work over multiple collections, returning a single collection

;; The hash-map function creates a map type collection

(hash-map :a 1 :b 2)


;; => {:b 2, :a 1}

;; we could use the map fuction with hash-map to create each pair in a map.

(map hash-map [:a :b :c] [1 2 3])


;; => ({:a 1} {:b 2} {:c 3})

;; now we just need to put all the maps into one map, so perhaps merge will work

(merge (map hash-map [:a :b :c] [1 2 3]))


;; => ({:a 1} {:b 2} {:c 3})

;; merge takes multiple maps as arguments, it does not merge a sequence of maps

(reduce merge (map hash-map [:a :b :c] [1 2 3]))


;; => {:c 3, :b 2, :a 1}

;; Any other approaches?

(flatten (map hash-map [:a :b :c] [1 2 3]))


;; => ({:a 1} {:b 2} {:c 3})

(mapcat hash-map [:a :b :c] [1 2 3])


;; => ([:a 1] [:b 2] [:c 3])


(conj (map hash-map [:a :b :c] [1 2 3]))


;; => ({:a 1} {:b 2} {:c 3})

(reduce conj (map hash-map [:a :b :c] [1 2 3]))


;; => {:c 3, :b 2, :a 1}


;; So to solve the 4Clojure challenge, we can use


(fn [ks vs]
  (reduce conj (map hash-map ks vs)))


;; lets test that function definition with one of the challenges

((fn [ks vs]
   (reduce conj (map hash-map ks vs)))
 [:a :b :c] [1 2 3])


;; => {:c 3, :b 2, :a 1}


;; we can also use the short form of a function definition

#(reduce conj (map hash-map % %2))


;; reduce conj is an idiom in clojure
;;

;; using reduce conj on sequences of values in Clojure is very common,
;; so much so that there is a general function that does this

;; into
;; https://clojuredocs.org/clojure.core/into
;; https://github.com/clojure/clojure/blob/clojure-1.9.0/src/clj/clojure/core.clj#L6807

;; into returns a new collection that we give as the first argument
;; the new collection contains the results of the second argument, usually a function call (or composite function calls)

;; We can slightly simplify our solution using into


(fn [ks vs]
  (into {} (map hash-map ks vs)))


((fn [ks vs]
   (into {}
         (map hash-map ks vs)))
 [:a :b :c] [1 2 3])


;; => {:a 1, :b 2, :c 3}



;; When to use zipmap
;;


;; Use zipmap when you want to directly construct a hashmap from separate sequences of keys and values. The output is a hashmap:

(zipmap [:k1 :k2 :k3] [10 20 40])


;; => {:k3 40, :k2 20, :k1 10}

;; Use (map vector ) when you are trying to merge multiple sequences. The output is a lazy sequence of vectors:

(map vector [1 2 3] [4 5 6] [7 8 9])


;; => ([1 4 7] [2 5 8] [3 6 9])


;; Some extra notes to consider:

;; Zipmap only works on two input sequences (keys + values) whereas map vector can work on any number of input sequences.

;; If your input sequences are not key value pairs then it's probably a good hint that you should be using map vector rather than zipmap
;; zipmap will be more efficient and simpler than doing map vector and then subsequently creating a hashmap from the key/value pairs
;; - e.g. (into {} (map vector  [:k1 :k2 :k3] [10 20 40])) is quite a convoluted way to do zipmap

;; map vector is lazy - so it brings a bit of extra overhead but is very useful in circumstances where you actually need laziness
;; (e.g. when dealing with infinite sequences)

;; You can do (seq (zipmap ....)) to get a sequence of key-value pairs rather like (map vector ...), however be aware that this may re-order the sequence of key-value pairs (since the intermediate hashmap is unordered)
;; shareimprove this answer

;; The two may appear similar but in reality are very different.

;; zipmap creates a map
;; (map vector ...) creates a LazySeq of n-tuples (vectors of size n)
;; These are two very different data structures. While a lazy sequence of 2-tuples may appear similar to a map, they behave very differently.

;; Say we are mapping two collections, coll1 and coll2. Consider the case coll1 has duplicate elements. The output of zipmap will only contain the value corresponding to the last appearance of the duplicate keys in coll1. The output of (map vector ...) will contain 2-tuples with all values of the duplicate keys.

;; A simple REPL example:

;; => (zipmap [:k1 :k2 :k3 :k1] [1 2 3 4])
;; {:k3 3, :k2 2, :k1 4}

;; =>(map vector [:k1 :k2 :k3 :k1] [1 2 3 4])
;; ([:k1 1] [:k2 2] [:k3 3] [:k1 4])
;; With that in mind, it is trivial to see the danger in assuming the following:

;; But basically -- apart from the order of the resulting pairs -- these two methods are equivalent, because the seq'd map becomes a sequence of vectors.

;; The seq'd map becomes a sequence of vectors, but not necessarily the same sequence of vectors as the results from (map vector ...)

;; For completeness, here are the seq'd vectors sorted:

;; => (sort (seq (zipmap [:k1 :k2 :k3 :k1] [1 2 3 4])))
;; ([:k1 4] [:k2 2] [:k3 3])

;; => (sort (seq (map vector [:k1 :k2 :k3 :k1] [1 2 3 4])))
;; ([:k1 1] [:k1 4] [:k2 2] [:k3 3])
;; I think the closest we can get to a statement like the above is:

;; The set of the result of (zip map coll1 coll2) will be equal to the set of the result of (map vector coll1 coll2) if coll1 is itself set.

;; That is a lot of qualifiers for two operations that are supposedly very similar. That is why special care must be taken when deciding which one to use. They are very different, serve different purposes and should not be used interchangeably.


;; could be written thus:

;; (defn zip-map [ks vs]
;;   (into {} (map vector ks vs)))
;; when

;; (zip-map [:a :b :c] [1 2 3])
;; => {:a 1, :b 2, :c 3}
;; as before.

;; The function imitates the standard zipmap, which you can find explained, complete with source code, in the official docs or ClojureDocs, which also gives examples. Both these sites help you to pick your way through the Clojure vocabulary.

;; As is often the case, the standard function is faster though more complex than the simple one-liner above.



;; Answer
;;

(fn [ks vs]
  (into {}
        (map vector ks vs)))


;; Code Golf Score:



;; functional composition approach

(comp (partial apply sorted-map) interleave)


((comp (partial apply sorted-map) interleave)
 [:a :b :c] [1 2 3]); => {:a 1, :b 2, :c 3}
