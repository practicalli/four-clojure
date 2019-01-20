(ns four-clojure.173-intro-to-destructuring-part2)

;; # 173 Intro to destructuring - part 2
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; http://www.4clojure.com/problem/173

;; Difficulty:	Easy
;; Topics:	Destructuring


;; Sequential destructuring allows you to bind symbols to parts of sequential things (vectors, lists, seqs, etc.): (let [bindings* ] exprs*) Complete the bindings so all let-parts evaluate to 3.

;; (= 3
;;   (let [[__] [+ (range 3)]] (apply __))
;;   (let [[[__] b] [[+ 1] 2]] (__ b))
;;   (let [[__] [inc 2]] (__)))


;; Deconstruct the problem
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;; We need to get a function and a collection from the collection in each `let` binding.
;; Each collection has the function first, followed by the the collection the function will take as an argument


;; Before we start, lets continue the investigating into destructuring
;; started in 052_intro_to_destructuring.clj

;; Create a collection of names

(def names ["Michael" "Amber" "Aaron" "Nick" "Earl" "Joe"])


;; We can get the first persons name by using destructuring
;; and assign any other elements in the collection from the argument
;; to the name `remaining-people`

(let [[person1 & remaining-people] names]
  (str "The first person in the queue is: " person1
       ".  The rest of the people queing are: " (apply str remaining-people)))
;; => "The first person in the queue is: Michael.  The rest of the people queing are: AmberAaronNickEarlJoe"


;; `str` on a collection of strings doesnt quite give the result you may expect.
;; it creates a string of the collection, rather than of the elements in the collection.

(str ["Michael" "Amber" "Aaron" "Nick" "Earl" "Joe"])
;; => "[\"Michael\" \"Amber\" \"Aaron\" \"Nick\" \"Earl\" \"Joe\"]"

;; using `apply` with `str` will create a string made from each element of the collection

(apply str ["Michael" "Amber" "Aaron" "Nick" "Earl" "Joe"])
;; => "MichaelAmberAaronNickEarlJoe"

;; we can use `interpose` to put a space in between each name before we join then as a single string.
(apply str (interpose " " names))
;; => "Michael Amber Aaron Nick Earl Joe"


;; So now our output is more human readable

(let [[person1 & remaining-people] names]
  (str "The first person in the queue is: " person1
       ".  The rest of the people queing are: "
       (apply str (interpose ", " remaining-people))))
;; => "The first person in the queue is: Michael.  The rest of the people queing are: Amber, Aaron, Nick, Earl, Joe"


;; Another example of selective destructuring
;; This time we don't want all of the values
;; and we can use `_` the underscore character to ignore an element of the collection

(def numbers [1 2 3 4 5])

(let [[x _ z & remaining :as all] numbers]
  (apply str (interpose " " [x z remaining all])))
;; => "1 3 (4 5) [1 2 3 4 5]"



;; REPL experiments
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;; Here are the tests we need to pass to solve the challenge

;; (let [[__] [+ (range 3)]] (apply __))
;; (let [[[__] b] [[+ 1] 2]] (__ b))
;; (let [[__] [inc 2]] (__))


;; Lets look at the first test

;; `range` generates a sequential collection of integer numbers,
;; up to but not including the specified number,
(range 3)
;; => (0 1 2)

;; `range` will also take a starting point
(range 5 11)
;; => (5 6 7 8 9 10)

;; `range` also takes a step, allowing you to be more selective about the sequence of numbers.
(range 1 30 5)
;; => (1 6 11 16 21 26)

;; using `apply` with the `+` function adds together all the numbers in the collection.
(apply + (range 3))
;; => 3


;; so we need to have two names in our let binding,
;; the first name taking the function
;; the second name taking the collection

(let [[function collection] [+ (range 3)]]
  (apply function collection))
;; => 3

;; so the 4Clojure answer is simply the two names
;; function collection

;; this works for all the tests


;; Answers summary
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;; we just need two names to bind the values that represent the function that
;; will be appli and the collection
;; function collection

;; or if you want a low golf score in 4Clojure, you could use
;; f xs





;; Associative deconstruction
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;; Associative destructuring is used with map data structures (hash-map)
;; We can use the key names to automatically create matching local names


;; Lets define a client that we our company does business with.

(def client {:name        "Super Co."
             :location    "Philadelphia"
             :description "The worldwide leader in plastic tableware."})

;; we can use the `get` function to get a value from a map using a key.

(get client :name)
;; => "Super Co."

;; there is a shorter form of this, assuming the key is a keyword

(:name client)
;; => "Super Co."

;; and you can also use the map as a function with any kind of key

(client :name)
;; => "Super Co."


;; Without destructuring we need to specify the local names in our `let` binding.

(let [name        (:name client)
      location    (:location client)
      description (:description client)]
  (str name location "-" description))
;; => "Super Co.Philadelphia-The worldwide leader in plastic tableware."

;; we can simplify by using a map pattern
(let [{location    :location
       name        :name
       description :description} client]
  (str name location "-" description))
;; => "Super Co.Philadelphia-The worldwide leader in plastic tableware."

;; using the keys keyword allows us to specify which keywords we want from the map,
;; by providing names that match the names of the keys

(let [{:keys [name location description]} client]
  (str name location "-" description))
;; => "Super Co.Philadelphia-The worldwide leader in plastic tableware."
