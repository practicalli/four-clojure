(ns four-clojure.065-black-box-testing)


;; #065 Black Box Testing
;;

;; Difficulty:	Medium
;; Topics:	seqs testing
;; Restrictions: class type Class vector? sequential? list? seq? map? set? instance? getClass

;; Clojure has many sequence types, which act in subtly different ways.

;; The core functions typically convert them into a uniform "sequence" type and work with them that way,
;; but it can be important to understand the behavioral and performance differences
;; so that you know which kind is appropriate for your application.

;; Write a function which takes a collection and returns one of :map, :set, :list, or :vector
;; - describing the type of collection it was given.

;; You won't be allowed to inspect their class or use the built-in predicates like list?
;; - the point is to poke at them and understand their behavior.

;; (= :map (__ {:a 1, :b 2}))

;; (= :list (__ (range (rand-int 20))))

;; (= :vector (__ [1 2 3 4 5 6]))

;; (= :set (__ #{10 (rand-int 5)}))

;; (= [:map :set :vector :list] (map __ [{} #{} [] ()]))



;; Deconstruct the problem
;;

;; Understand the behavioural differences between different types of collections

;; list       ()
;; vector     []
;; hash-map   {}
;; set        #{}

(= '(1 2 3) [1 2 3])


;; Sequences can contain any value that is a Clojure type

(list 1 2 3 4 :cat "dog" (clojure.string/join " " (repeat 3 "rabbit")))


;; Its common to explicitly define a sequence, rather than create it with a function.

;; (1 2 3 4  :cat "dog" (clojure.string/join " " (repeat 3 "rabbit") ))


;; The first value in a list is treated as a function call by the Clojure reader
;; so we need to tell the Clojure reader to treat the first value as data
;; when not calling a function

(quote (1 2 3 4  :cat "dog" (clojure.string/join " " (repeat 3 "rabbit"))))

'(1 2 3 4  :cat "dog" (clojure.string/join " " (repeat 3 "rabbit")))


;; The underlying types in Clojure
;;

;; Clojure is a strongly typed language
;; everything has a type

;; Clojure is dynamically typed
;; so you do not generally specify the type


;; Clojure uses Java's object types for booleans, strings and numbers as these are immutable.

;; Use `class` or `type` to inspect them.
;; `type` will return type metadata, if none then the `class` is returned.


;; Types of values


(type 10)


;; => java.lang.Long
(type 10000000000000000000000)


;; => clojure.lang.BigInt
(type 3.14)


;; => java.lang.Double
(type "I am a string")


;; => java.lang.String
(type :keyword)


;; => clojure.lang.Keyword
(type false)


;; => java.lang.Boolean
(type nil)


;; => nil



;; Functions have a type too

(defn i-am-a-function
  []
  (str "Hello, you have just called me"))


(type i-am-a-function)


;; => four_clojure.065_black_box_testing$i_am_a_function


;; We can add type information to function as metadata
(defn order
  [order-id date amount]
  ^{:type ::PurchaseOrder} ; metadata
  {:id order-id :date date :amount amount})


;; The function itself is still its own type
(type order)


;; => four_clojure.065_black_box_testing$order

;; If we define an instance of calling that function

(def my-order (order 1 (java.util.Date.) 99.99))


;; => #'four-clojure.065-black-box-testing/my-order

;; Then the type of that instance uses the type metadata

(type my-order)


;; => :four-clojure.065-black-box-testing/PurchaseOrder

;; Interop with the host platform (i.e. Java) may benefit from explicit type coercion



;; Types of Sequence

(type '(1 2 3))


;; => clojure.lang.PersistentList
(type [1 2 3])


;; => clojure.lang.PersistentVector
(type {:one 1 :two 2})


;; => clojure.lang.PersistentArrayMap
(type #{1 2 3 4 5})


;; => clojure.lang.PersistentHashSet


(type '(1 2 3 4  :cat "dog" (clojure.string/join " " (repeat 3 "rabbit"))))


;; => clojure.lang.PersistentList

;; All the values in the sequence have a type
;; they can all be different types

(map type '(1 2 3 4  :cat "dog" (clojure.string/join " " (repeat 3 "rabbit"))))


;; => (java.lang.Long java.lang.Long java.lang.Long java.lang.Long clojure.lang.Keyword java.lang.String clojure.lang.PersistentList)


;; We can also test for a sequence

(= (type [1 2 3]) (type [3 4 5]))

(instance? clojure.lang.PersistentVector [1 2 3])


;; => true


;; some things are multiple types

(instance? clojure.lang.IFn +)


;; => true
(instance? clojure.lang.Keyword :a)


;; => true
(instance? clojure.lang.IFn :a)


;; => true
(instance? clojure.lang.IFn {:a 1})


;; => true

(get {:a 1} :a)


;; => 1

(:a {:a 1})


;; => 1

({"a" 1} "a")


;; => 1


;; If we could use the restricted functions
;;

;; map? list? vector? set? are restricted functions to solve this challenge,
;; they would make solving the challenge easier

(defn what-sequence-is-it
  [xs]
  (cond
    (map?  xs)   :map
    (list? xs)   :list
    (vector? xs) :vector
    (set? xs)    :set))


(what-sequence-is-it [1 2 3])


;; => :vector


(what-sequence-is-it {:a 1, :b 2})


;; => :map
(what-sequence-is-it (range (rand-int 20)))


;; => nil
(what-sequence-is-it [1 2 3 4 5 6])


;; => :vector
(what-sequence-is-it #{10 (rand-int 5)})


;; => :set
(map what-sequence-is-it [{} #{} [] ()])


;; => (:map :set :vector :list)

;; Range has its own type

(type (range (rand-int 20)))

(type (range 2))


(defn what-sequence-is-it-range-hack
  [xs]
  (cond
    (map?  xs)   :map
    (vector? xs) :vector
    (set? xs)    :set
    :else        :list))


(what-sequence-is-it-range-hack (range (rand-int 20)))


;; => :list



;; Without the restricted functions
;;


;; If we get the first element of a map,
;; it returns the first key/value pair as a sequence

(first [1 2 3 4])


;; => 1

(first {:a 1 :b 2})


;; => [:a 1]

(first {})


;; => nil


(count
  (first {:a 1 :b 2}))


(defn what-sequence-no-restricted
  [xs]
  (cond
    (= 2 (count (first xs))) :map
    (vector? xs)             :vector
    (set? xs)                :set
    :else                    :list))


(what-sequence-no-restricted {:a 1, :b 2})


;; => :map


;; This causes a problem using `count` with the other sequences,
;; at the `first` function does not return a sequence

(what-sequence-no-restricted [1 2 3 4 5 6])
(what-sequence-no-restricted (range (rand-int 20)))


;; As a map sequence type is the only one that returns a collection,
;; we can test with `coll?`

(defn what-sequence-no-restricted-coll
  [xs]
  (cond
    (coll? (first xs)) :map
    (vector? xs)       :vector
    (set? xs)          :set
    :else              :list))


(what-sequence-no-restricted-coll {:a 1, :b 2})


;; => :map
(what-sequence-no-restricted-coll [1 2 3 4 5 6])


;; => :vector
(what-sequence-no-restricted-coll (range (rand-int 20)))


;; => :list

;; coll? doest solve our empty map
(what-sequence-no-restricted-coll {})


;; => :list


(defn what-sequence-no-restricted-reverse
  [xs]
  (cond
    (coll? (first xs))  :map
    (vector? xs)        :vector
    (= xs (reverse xs)) :set
    :else               :list))


;; => #'four-clojure.065-black-box-testing/what-sequence-no-restricted-reverse


(reverse
  #{10 (rand-int 5)})


;; => (10 1)

(what-sequence-no-restricted-reverse
  #{10 (rand-int 5)})


(defn what-sequence-no-restricted-reverse-let
  [xs]
  (cond
    (coll? (first xs))                  :map
    (vector? xs)                        :vector
    (let [-xs xs] (= -xs (reverse -xs))) :set
    :else                               :list))


(what-sequence-no-restricted-reverse-let
  #{10 (rand-int 5)})


(defn what-sequence-no-restricted-peek
  [xs]
  (cond
    (coll? (first xs))     :map
    (= (peek xs) (last xs)) :vector
    (set? xs)              :set
    :else                  :list))


;; => #'four-clojure.065-black-box-testing/what-sequence-no-restricted-peek


(what-sequence-no-restricted-peek [1 2 3 4 5 6])


;; => :vector

(what-sequence-no-restricted-peek (range (rand-int 20)))


;; clojure.lang.Range cannot be cast to clojure.lang.IPersistentStack

(peek '(1 2 3 4 5))


;; peek does not like range
(peek (range 20))


(defn what-sequence-no-restricted-conj
  [xs]
  (cond
    (coll? (first xs))        :map
    (= 1 (last (conj xs 1)))  :vector
    (= 1 (first (conj xs 1))) :list
    (set? xs)                 :set))


(what-sequence-no-restricted-conj [1 2 3 4 5 6])


;; => :vector

(what-sequence-no-restricted-conj (range (rand-int 20)))


;; => :list


(fn what-sequence-no-restricted-conj
  [xs]
  (cond
    (coll? (first xs))        :map
    (= 1 (last (conj xs 1)))  :vector
    (= 1 (first (conj xs 1))) :list
    :else                     :set))


;; Parser for collections
;;

;; We could write a very simple parser to read in part of the code and determin what type of collection it is.

;; First, convert the code in to a string

(str {})


;; => "{}"

;; then get the first character of the string, which will be the opening parenthesis

(first (str {}))


;; => \{


;; Now we have a character for the opening parenthesis, we can use it to look up what kind of collection type it represents

;; we will create a map to help us do the lookup,
;; the keys will be the opening paren of each collection type
;; and the values will be `:map` `:list` `:vector` `:set` as the

;; lets try with just a lookup for the map collection

(get {\{ :map} \{)


;; => :map


;; lets try this for a list collection type

(first (str ()))


;; => \(


(get {\( :list} \()


;; => :list


;; putting this all together

#_{\( :list \{ :map \[ :vector \# :set }


(fn [coll]
  (get {\# :set \{ :map \[ :vector \( :list}
       (first (str coll))))


((fn [coll]
   (get {\# :set \{ :map \[ :vector \( :list}
        (first (str coll))))
 ())


;; => :list



;; Something strange is happens in 4Clojure
;;

;; 4Clojure uses Clojure version 1.4 which gives different results from the `range` expression

(str (range (rand-int 20)))
"clojure.lang.LazySeq@9ebadac6"


;; so we can see that the first character is `c` and not `(`



;; Refining the code using `comp`
;;

;; We can use comp to compose functions together
;; the result of applying each function is passed as an argument to the next function
;; the functions are applied

(comp {\# :set \{ :map \[ :vector \( :list} first str)


((comp {\# :set \{ :map \[ :vector \( :list} first str)
 (range (rand-int 20)))


;; => :list


;; use \c instead to represent the bracket
((comp {\# :set \{ :map \[ :vector \c :list} first str)
 (range (rand-int 20)))


;; using condp

#(condp = (nth (str %) 0)
   \{ :map
   \( :list
   \[ :vector
   \# :set)


;; Alternative approaches
;;


;; Comparing with empty sequences
;;

(empty {})


;; => {}

(empty {:a 1 :b 2})


;; => {}


;; Check the results over all the types of sequences in the 4Clojure tests

(map empty [{:a 1} #{1 2 3} [4 5 6] (range 10)])


(coll? [])


;; Using an anonymous function version that could be used as the 4Clojure answer.
#(if (keyword? (first (first %))) :map)


;; This expression now passes the first test.  However, the next test fails.

(= :list (coll? (range (rand-int 20))))

(range (rand-int 20))


;; => (0 1 2 3 4 5 6 7 8 9 10 11 12 13)



;; What if we write some kind of conditional that


(conj '(1) -1)


;; => (-1 1)


(conj [1] -1)


;; => [1 -1]

#(let [coll (first (conj % -1))]
   (cond
     (= -1 coll) :list
     (=  1 coll) :vector
     :else       :doh))


;; The cond expression works fine for a list

(#(let [coll (first (conj % -1))]
    (cond
      (= -1 coll) :list
      (=  1 coll) :vector
      :else       :doh))
 (range (rand-int 20)))


;; => :list


;; And it works for a vector
(#(let [coll (first (conj % -1))]
    (cond
      (= -1 coll) :list
      (=  1 coll) :vector
      :else       :doh))
 [1 2 3 4 5 6])


;; => :vector



(conj {:a 1} -1)


;; Dont know how to create an ISeq from java.lang.Long

(first {:a 1})


;; => [:a 1]



;; As maps fail if you try to conj a map, how about adding a map or two values in a collection, rather than a single value.

(conj '(1 2 3 4) {-1 -2})


;; => ({-1 -2} 1 2 3 4)

(conj [1 2 3 4] {-1 -2})


;; => [1 2 3 4 {-1 -2}]


(conj {1 2 3 4} {-1 -2})


;; => {1 2, 3 4, -1 -2}


(conj #{1 2 3 4} {-1 -2})


;; => #{1 {-1 -2} 4 3 2}
;; => #{1 {-1 -2} 4 3 2}


(conj [1 2 3] 4)

(conj ["Can" "we" "join"] ["two" "collections"])

(conj ["Can" "we" "join"] "multiple" "values")

(conj ["Can" "we" "join"] (map str ["two" "collections"]))

(def favourite-ice-cream-flavors ["Mint" "Vanilla"])
(conj favourite-ice-cream-flavors "Chocolate")
favourite-ice-cream-flavors


(= {} {})


;; => true


(fn [coll]
  (let [xs (empty coll)]
    (if (= {} xs)
      :map)))


(fn [coll]
  (let [xs (empty coll)]
    (cond
      (= {} xs)  :map
      (= () xs)  :list
      (= [] xs)  :vector
      (= #{} xs) :set)))


(= () [])


;; => true

'(1 2 3 4 5)

[1 2 3 4 5]

(nth [1 2 3 4 5] 2)


;; => 3

(reversible?
  [1 2 3 4 5])


;; => true

(reversible? '(1 2 3 4 5))


;; => false

(reversible? #{1 2 3})


;; => false

(reversible? {:a 1 :b 2})


;; => false

(fn seq-type
  [coll]
  (let [ec (empty coll)]
    (cond
      (= ec {})  :map
      (= ec #{}) :set
      (= ec ())  (if (reversible? coll)
                   :vector
                   :list))))


(fn seq-type
  [coll]
  (let [ec (empty coll)]
    (cond
      (= ec {})          :map
      (= ec #{})         :set
      (reversible? coll) :vector
      :else              :list)))


(fn seq-type
  [coll]
  (let [ec (empty coll)]
    (cond
      (= ec {})          :map
      (= ec #{})         :set
      (reversible? coll) :vector
      :else              :list)))
