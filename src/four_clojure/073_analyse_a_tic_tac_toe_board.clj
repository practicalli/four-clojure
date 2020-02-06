(ns four-clojure.073-analyse-a-tic-tac-toe-board)

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; problem 73 - analyse tic-tac-toe board

;; A tic-tac-toe board is represented by a two dimensional vector. X is represented by :x, O is represented by :o, and empty is represented by :e.
;; A player wins by placing three Xs or three Os in a horizontal, vertical, or diagonal row.
;; Write a function which analyzes a tic-tac-toe board and returns :x if X has won, :o if O has won, and nil if neither player has won.


;; Define some tic-tac-toe board pattern to test

(def board-empty [[:e :e :e]
                  [:e :e :e]
                  [:e :e :e]])
;; => #'clojure-through-code.four-clojure/board-empty

(def board-x-first-row [[:x :x :x]
                        [:e :e :e]
                        [:e :e :e]])

(def board-o-diagonal [[:o :e :e]
                       [:e :o :e]
                       [:e :e :o]])

((fn ttt-valid [[[a b c] [d e f] [g h i]]]
   (let [winners [[a b c] [d e f] [g h i]
                  [a d g] [b e h] [c f i]
                  [a e i] [c e g]]
         same-key (fn [v] (apply = v))
         not-e    (fn [v] (some #(not= :e %) v))]
     (->> winners
          (filter same-key)
          (filter not-e)
          (#(if (seq %)
              (apply first %)
              nil))))) board-empty)



;; Algorithm
;; 1) Extract all the values from the board  (destructuring with let)
;; 2) Pattern match the values to winning combinations (=)
;; 3) If any combination matched with :x or :o then return winner (or)


;; Destructuring will bind a local name to a value from the board pattern.
;; In this case, a b and c will each represent a line of the board

(let [[a b c] board-empty]
  [a b c])
;; => [[:e :e :e] [:e :e :e] [:e :e :e]]

;; Now a b and c will represent the elements of the first row of the board pattern.

(let [[[a b c]] board-x-first-row]
  [a b c])
;; => [:o :e :e]


;; So we can pull out the whole board using the following let expression

(let [[[a b c]
       [d e f]
       [g h i]] board-o-diagonal]
  [[a b c] [d e f] [g h i]])
;; => [[:o :e :e] [:e :o :e] [:e :e :o]]


;; let can be used with functions as well as values
;; we can call the function using the local name winner,
;; passing a board and a player character to see if they have won.
;; We just use a single winning pattern here for simplicity.
(let [winner (fn [[[a b c]
                   [d e f]
                   [g h i]]
                  player]
               (= player a b c))]
  (winner board-x-first-row :x))
;; => true


;; We can streamline the let binding and anonymous function definition a little
;; letfn creates a function within the scope of the let expression.
;; letfn takes the name of the function to create followed by its arguments,
;; in this case the board patter we are destructuring and the player character.
;; For example:

(letfn [(winner [[[a b c]
                  [d e f]
                  [g h i]]
                 player])])

;; Using macro-expansion we can see the letfn creates a function called winner
;; with two arguments: a board pattern and player
(letfn* [winner (fn winner [[[a b c] [d e f] [g h i]] player])])

;; We can call the local winner function whilst inside the letfn expression,
;; however we have not yet added any behaviour to that function.


;; Adding a check to see if a player has one the first row of the board as a first
;; attempt at defining the function behaviour.
;; If the first row matches the player character then we should get true.

(letfn [(winner [[[a b c]
                  [d e f]
                  [g h i]]
                 player]
          (println (str "first row: " [a b c])) ;; debugging
          (= player a b c))]
  (winner board-x-first-row :x))
;; => true

;; Note: A simple println statement was added to show the intermediate values,
;; as an example of a simple debugging statement

;; Now we have the basic logic, we can add the patterns for all the winning combinations
;; We only need one pattern to match, so we wrap the patterns in an or function
;; To test both players, we add a condition around calling the winner function for each player


(letfn [(winner [[[a b c]
                  [d e f]
                  [g h i]]
                 player]
          (or (= player a b c)
              (= player d e f)
              (= player g h i)
              (= player a d g)
              (= player b e h)
              (= player c f i)
              (= player a e i)
              (= player c e g)))]
  (cond (winner board-x-first-row :x) :x
        (winner board-x-first-row :o) :o
        :else (str "No Winner for this board: " board-x-first-row)))
;; => :x

;; The letfn expression was tested with each of the three board patterns defined, giving the correct results.

;; 4Clojure entered solution

(fn [board]
  (letfn [(winner [[[a b c]
                    [d e f]
                    [g h i]]
                   player]
            (or (= player a b c)
                (= player d e f)
                (= player g h i)
                (= player a d g)
                (= player b e h)
                (= player c f i)
                (= player a e i)
                (= player c e g)))]
    (cond (winner board :x) :x
          (winner board :o) :o
          :else nil)))


;; Alternative solutions

;; I quite like the solution by tclamb, its similar to the submitted solution in approach

(fn winner [[[a b c :as top]
             [d e f :as middle]
             [g h i :as bottom]]]
  (let [left     [a d g]
        center   [b e h]
        right    [c f i]
        forward  [a e i]
        backward [c e g]]
    (when-let [winner (some #(when (or (every? #{:x} %) (every? #{:o} %)) %)
                            [top middle bottom
                             left center right
                             forward backward])]
      (first winner))))







(= (map #(get-in board1 %) [[0 0] [1 0] [2 0]]))



;; some answers from the internet

;; https://github.com/ardumont/org/blob/master/clojure/4clojure-73-analyze-a-tic-tac-toe-board.org
;;
(defn ttt [b]
  (letfn [(w [[[a b c]
               [d e f]
               [g h i]] p] (or (= p a b c)
                               (= p d e f)
                               (= p g h i)
                               (= p a d g)
                               (= p b e h)
                               (= p c f i)
                               (= p a e i)
                               (= p c e g)))]
    (cond (w b :x) :x
          (w b :o) :o
          :else nil)))




(fn an [board] (let [vic (first (first (filter #(= (count %) 1) (map distinct
                                                                     (concat (list
                                                                              (map first board)
                                                                              (map second board)
                                                                              (map #(nth % 2) board)
                                                                              (map-indexed #(nth %2 %) board)
                                                                              (map-indexed #(nth %2 (- 2 %)) board)) board)))))] (if (= vic :e) nil vic)))



;; fronx's solution to Analyze a Tic-Tac-Toe Board
;; https://4clojure.com/problem/73

(fn [m]
  (first
   (filter
    (fn win? [player]
      (->> (let [idx [0 1 2]]
             (concat m                               ; rows
                     (map (fn [i] (map #(% i) m)) idx)     ; columns
                     (vector (map #((m %) %) idx))         ; diagonal 1
                     (vector (map #((m (- 2 %)) %) idx)))) ; diagonal 2
           (map (fn [row] (count (filter #(= player %) row))))
           (filter #(= % 3))
           seq))
    [:x :o])))

                                        ; [
                                        ;  ; rows
                                        ;  [a b c]
                                        ;  [d e f]
                                        ;  [g h i]
                                        ;  ; columns
                                        ;  [a d g]
                                        ;  [b e h]
                                        ;  [c f i]
                                        ;  ; diagonals
                                        ;  [a e i]
                                        ;  [g e c]
                                        ; ]
