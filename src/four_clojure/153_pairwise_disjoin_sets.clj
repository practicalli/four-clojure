(ns four-clojure.153-pairwise-disjoin-sets)

;; #153 Pairwise disjoin sets
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;


;; http://www.4clojure.com/problem/153

(= (__ #{
         #{\U}
         #{\s}
         #{\e \R \E}
         #{\P \L}
         #{\.}})
   true)


(def set1 #{1 2})
(def set2 #{2 3})
(def set3 #{4 3})

(def intersect clojure.set/intersection)
(intersect set1 set3)

(defn dj [set]
  (empty? (apply concat
                 (for [i set
                       j set]
                   (when (not= i j)
                     (clojure.set/intersection i j))))))

;; These sets do not intersect, so true
(dj #{set1 set3})

;; These sets do intersect, so false
(dj #{set2 set3})
