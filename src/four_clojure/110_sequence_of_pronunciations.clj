(ns four-clojure.110-sequence-of-pronunciations)

(fn [data]
  (lazy-seq (partition-by identity data) ))

(partition-by identity [1 2 2 3 3 3])
;; => ((1) (2 2) (3 3 3))


((fn [data]
   (lazy-seq (partition-by identity data) ))
 [1])
;; => ((1))


((fn [data]
   (lazy-seq (count (partition-by identity data)) ))
 [1])
;; lazy-seq cannot work with numbers

((fn [data]
   (lazy-seq (map count (partition-by identity data)) ))
 [1])
;; => (1)


((fn [data]
   (lazy-seq
    (map
     (fn [grouping]
       [(count grouping) (first grouping)])
     (partition-by identity data)) ))
 [1])
;; => ([1 1])


((fn [data]
   (lazy-seq
    (map
     (fn [grouping]
       [(count grouping) (first grouping)])
     (partition-all identity data)) ))
 [1])

;; looping was interesting, but didnt get far
;; how do we continue looping without blowing up
;; the stack (not lazy enough)

(fn [data]
  (loop [groupings (partition-by identity data)
         pronounces [(count groupings) (first groupings)]]
    ;; how do we break out?
    (recur )))

;; going back to the mapping of a function
;; over partition-by identity
;; this time adding a recursive function call

;; defining the pronounce function

(fn pronounce [data]
  (let [numbers
        (flatten
         (map
          #(vector (count %) (first %))
          (partition-by identity data)))]
    (lazy-seq
     (cons numbers (pronounce numbers)))))

;; test the function with the simplest test

((fn pronounce [data]
   (let [numbers
         (flatten
          (map
           #(vector (count %) (first %))
           (partition-by identity data)))]
     (lazy-seq
      (cons numbers (pronounce numbers)))))
 [1])


;; simulate the full expression from the first
;; 4Clojure test

(take 3
      ((fn pronounce [data]
         (let [numbers
               (flatten
                (map
                 #(vector (count %) (first %))
                 (partition-by identity data)))]
           (lazy-seq
            (cons numbers (pronounce numbers)))))
       [1]))
;; => ((1 1) (2 1) (1 2 1 1))



;; using concat rather than flatten
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(fn pronunciations [number-sequence]
  (lazy-seq
   (let [pronouced-numbers
         (apply
          concat
          (map
           #(vector (count %) (first %))
           (partition-by identity number-sequence)))]
     (cons pronouced-numbers (pronunciations pronouced-numbers)))))

(take 3
      ((fn pronunciations [number-sequence]
         (lazy-seq
          (let [pronouced-numbers
                (apply
                 concat
                 (map
                  #(vector (count %) (first %))
                  (partition-by identity number-sequence)))]
            (cons pronouced-numbers (pronunciations pronouced-numbers)))))
       [1]))
;; => ((1 1) (2 1) (1 2 1 1))


;; Using mapcat to make it even cleaner
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(take 3
      ((fn pronunciations [number-sequence]
         (let [pronouced-numbers
               (mapcat
                #(vector (count %) (first %))
                (partition-by identity number-sequence))]
           (lazy-seq
            (cons pronouced-numbers (pronunciations pronouced-numbers)))))
       [1]))
;; => ((1 1) (2 1) (1 2 1 1))


;; Using iterate rather than lazy sequence
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
