(ns four-clojure.096-beauty-is-symmetry)

;; #96 - Beauty is Symmetry
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;; http://www.4clojure.com/problem/96

(= (__ '(:sam
         (:joe
          1
          0)
         (:joe
          0
          1)))
   true)

#(= % (
       (fn mirror
         [[a b c :as coll]]
         (when coll [a (mirror c) (mirror b)])) %))

(defn mirror
  [[a b c] coll]
  (when coll [a (mirror c) (mirror b)]))

(= tree (mirror tree))

;; (get {"key" "value"} "key")
;; (:keyword {:keyword "string"})
