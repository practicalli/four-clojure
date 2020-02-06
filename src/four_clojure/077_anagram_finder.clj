(ns four-clojure.077-anagram-finder)

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; #77 - Anagram Finder
;; http://www.4clojure.com/problem/77

;; Write a function which finds all the anagrams in a vector of words. A word x is an anagram of word y if all the letters in x can be rearranged in a different order to form y. Your function should return a set of sets, where each sub-set is a group of words which are anagrams of each other. Each sub-set should have at least two words. Words without any anagrams should not be included in the result.

;; Tests

#_(= (__ ["meat" "mat" "team" "mate" "eat"])
     #{#{"meat" "team" "mate"}})

#_(= (__ ["veer" "lake" "item" "kale" "mite" "ever"])
     #{#{"veer" "ever"} #{"lake" "kale"} #{"mite" "item"}})


;; Initial thoughs
;; split the first word into a set of characters, then compare that result with splitting each other word into a set of characters.  Then filter where sets match.  This would need to be repeated for each word over the whole set.

(set
 (map char "meat"))


(= '(\a) '(\a))

;; starting to formulate this approach into an algorithm felt a bit fiddly...
(fn [puzzle]
  (for [word puzzle]
    ))


;; epiphany
;; realised this is a grouping exercise, so starting to think about groups and remembered `group-by` function.  But which function to structure the groups?

;; The sort function on a string returns a sequence of characters in alphabetical order
(sort "meet")
;; => (\e \e \m \t)

;; So grouping by the sort function will give us a map, where the key is the sorted sequence of characters and the values are the words that match that sequence.

(group-by sort ["meat" "mat" "team" "mate" "eat"])
;; => {(\a \e \m \t) ["meat" "team" "mate"], (\a \m \t) ["mat"], (\a \e \t) ["eat"]}

;; All the words in the original vector are groups.  The key is the sequence of characters and the value is the groups.  We only want the groups, so we can just get vals from the map

(vals
 (group-by sort ["meat" "mat" "team" "mate" "eat"]))
;; => (["meat" "team" "mate"] ["mat"] ["eat"])


;; According to the rules of the rules of the challenge, we only want to return those groups that have more than one value.

(filter #(> (count %) 1)
        (vals
         (group-by sort ["meat" "mat" "team" "mate" "eat"])))
;; => (["meat" "team" "mate"])

;; To match the output, each group needs to be in a set

(map set
     (vals
      (group-by sort ["meat" "team" "fish" "cat"])))
;; => (#{"meat" "team"} #{"fish"} #{"cat"})


;; and the overall answer should be in a set, so we use into #{}

(into #{}
      (map set
           (vals
            (group-by sort ["meat" "team" "fish" "cat"]))))
;; => #{#{"cat"} #{"fish"} #{"meat" "team"}}


;; Finally, put it into a function so it will work in 4Clojure

(fn [x]
  (into #{}
        (map set
             (filter #(> (count %) 1)
                     (vals
                      (group-by sort x))))))


;; An alternative way to filter the groups for those with more than one word is to
;; compose seq and rest funcitons together.

;; Take the group and use rest to get a collection with everything but the first value.
;; If the rest of the collection has values, then those values are returned as a sequence.

;; Importantly though, if the rest of the collection is an empty sequence, then the seq function returns nil.

(seq (rest ["meat" "team" "mate"]))
;; => ("team" "mate")

(seq (rest ["mat"]))
;; => nil

(seq (rest ["eat"]))
;; => nil

;; Using seq? fails though, as the empty collection is still a seq, therefore is a true condition for the filter function and will return groups with a single value.
(rest ["mat"])
;; => ()

(seq? (rest ["mat"]))
;; => true



;; Another alternative for the filter (in my opinion possibly the clearest intent)

(filter #(not (empty? (rest %))))

(fn [x]
  (into #{}
        (map set
             (filter #(not (empty? (rest %)))
                     (vals
                      (group-by sort x))))))
