package DataStructures;

import java.util.*;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.CopyOnWriteArrayList;


/**
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 12 July 2025

    Best ways to handle / avoid 🔥 "ConcurrentModificationException CME" 🔥
        1) java.util.Iterator / ListIterator interface
        2) Custom toRemove collection like "List<T> toRemove = new ArrayList<>();" and do toRemove.add(ele or index) and loop this toRemove to remove the original list/set/map elements
        3) java.util.concurrent.CopyOnWriteArrayList - a thread-safe implementation of ArrayList


    🧠 What actually causes ConcurrentModificationException?
    When you're iterating using an Iterator or enhanced for-each loop, Java internally tracks a field called expectedModCount.
    The collection (like ArrayList) has a field modCount, which is incremented on every structural change (add/remove).
    When the Iterator is created, it stores this value in expectedModCount.
    On every next() or hasNext(), it checks:
    if (modCount != expectedModCount) → throw ConcurrentModificationException
    So if you modify the collection directly (not using iterator.remove()), the internal mod count changes, and the next iterator.next() throws.


    Use java.util.Iterator,
    It's because
    --> Iterator itself does not occupy O(n) space just by existing
    --> just like a pointer or a cursor into the collection.
    --> it holds 2 things
                1) A reference to the collection itself (or its internal structure),
                2) And a position indicator (like an index or a node reference) to track the current position

    Collection  ⟵- Iterator
                     ↓
                 current position

    --> i.e., for ArrayList, LinkedList, HashSet... etc., the iterator just holds pointers/indices — so O(1) space
    --> The space used by an iterator is generally O(1) — constant space, regardless of collection size


    Different types of Iterators:
    1) Iterator
    2) ListIterator
    3) Spliterator
    4) Enumeration
    5) Other Concurrent Iterators like CopyOnWriteArrayList, ConcurrentHashMap


    NOTE:
    1) Java 8 enhanced for-each loop internally uses an Iterator -- for collections
    2) But where as, for arrays — that’s just a disguised index loop 🔥
    3) javac rewrites enhanced for / for-each loops into Iterator hasNext loops — for anything that implements Iterable (like List, Set, etc.) and for-i lop in arrays
    4) list.forEach() and stream.forEach() also uses Iterator internally





    ITERATOR
    ---------
    Iterator works for all Collections -> List(ArrayList, LinkedList), Set(HashSet, LinkedHashSet), Map(HashMap, LinkedHashMap), Queue(LinkedList, PriorityQueue), Deque(LinkedList), Stack
    ⚠️ But there's a catch: Order of iteration
    -> For a Stack (like java.util.Stack), iterator() iterates from bottom to top (FIFO order), not LIFO. So popping manually gives a different order than iterating.
    -> For a Queue (LinkedList, PriorityQueue, etc.), the iterator() goes from head to tail, respecting insertion order (unless it’s a priority-based structure).
    -> For PriorityQueue, iteration order ≠ poll order — it's not sorted in iteration.
    -> Arrays don't have iterators, as we can't add or remove elements from them -- it's not needed


    .iterator() vs .iterator(int index):
    1) .iterator() returns Iterator<E> with all elements of the collection
    2) .iterator(int index) returns Iterator<E> with only elements after index


    Methods in java.util.Iterator:
    1) boolean hasNext()
    2) T next()
    3) void remove() -> this works only after next() is called -> in Set and LinkedList it's O(1) and in ArrayList it's O(n)
    4) void forEachRemaining(Consumer<? super T> action)
    5) And we have other methods inherited from java.lang.Object like (boolean equals(Object o), int hashCode(), String toString(), Class<?> getClass(), void notify(), void notifyAll(), void wait(), void wait(long timeout) and void wait(long timeout, int nanos) )


    Methods in java.util.ListIterator: -- ListIterator extends Iterator
    ... all methods in Iterator plus below ones
    1) boolean hasPrevious()
    2) T previous()
    3) int nextIndex()
    4) int previousIndex()
    5) void add(T e) -> this works only after next() is called -> in Set and LinkedList it's O(1) and in ArrayList it's O(n)
    6) void set(T e) -> this works only after next() is called -> in Set and LinkedList it's O(1) and in ArrayList it's O(n)
    7) void remove() -> this works only after next() is called -> in Set and LinkedList it's O(1) and in ArrayList it's O(n)






    SPLITERATOR
    -----------
    ---> Spliterator itself is not not fail-fast behavior i.e., not designed to detect or prevent ConcurrentModificationException (CME).
    A Spliterator (split + iterator) is a special iterator designed to:
          1) Traverse elements sequentially like an iterator, and
          2) Split itself into multiple parts to support parallel processing efficiently.
    To enable efficient parallel processing in Java Streams (Java 8+).
    Regular iterators don’t support splitting data for parallelism.
    Spliterator can partition data for multiple threads to work on independently.

    Methods in java.util.Spliterator:
    1) boolean tryAdvance(Consumer<? super T> action) ---> Traverse one element at a time
    2) Spliterator<T> trySplit() ---> Split into two spliterators for parallel processing
    3) long estimateSize() ---> Estimate the number of elements -Helps optimize parallel workloads
    4) int characteristics() ---> Provides info about the data (ordered, sized, sorted, etc.)
    5) long getExactSizeIfKnown()
    6) void forEachRemaining(Consumer<? super T> action)


 * @see Algorithms.SlidingWindow.LongestSubstringWithoutRepeatingCharacters#lengthOfLongestSubstringUsingLinkedHashSet
 */
public class IteratorExample {
    public static void main(String[] args) {

        // ConcurrentModificationException CME in List
        System.out.println("Started List modification over Iterator -- will throw CME");
        List<Integer> trues = new ArrayList<>(); // --- just like queue
        trues.add(0);
        for (int l : trues) { // lInclusive
            if (trues.size() < 5) {
                trues.remove(Integer.valueOf(l+5)); // no CME
                // trues.add(l+1); // CME ❌
            }
        }



        /**
            Handle ConcurrentModificationException CME just by using i-loop instead of endhanced for-loop
            NOTE:
            1) if we use list.add() in enhanced for-loop and then immediately use break; statement, it doesn't throw CME
                check out {@link Algorithms.DynamicProgramming.WordBreak#wordBreakUsingBottomUpTabulationDpWithBfsOverS3
         */
        System.out.println("Started List modification over i-loop -- won't throw CME");
        trues = new ArrayList<>();
        int idx = 0;
        trues.add(idx);
        while (idx++ < trues.size()) {
            if (trues.size() < 5) {
                trues.add(idx+1); // ✅ Safe: we're not using iterator here
                trues.add(idx+2); // ✅
                trues.add(idx+3); // ✅
            }
        }



        /**
            Handle ConcurrentModificationException CME just by using ListIterator instead of enhanced for-loop
            NOTE:
            1) Even {@link ListIterator#add(Object)} doesn't throw CME but it silently skips it ❌ not good
            2) So, use {@link ListIterator#add(Object)} and then{@link ListIterator#previous()} right after
                cause -- it inserts the element immediately before the element that would be returned by next()—that is, at the current iterator cursor position.
                It can cause newly added elements to be skipped by the iterator if you're not careful with how you manage the cursor.
            3) Don't use .previous() first and then .add() -- ❌ it adds the previous element twice
         */
        System.out.println("Started List modification over ListIterator -- won't throw CME");
        trues = new ArrayList<>();
        trues.add(0);
        ListIterator<Integer> listIt = trues.listIterator();
        while (listIt.hasNext()) {
            int l = listIt.next();
            if (trues.size() < 5) {
                // trues.add(l+1); // CME ❌
                listIt.add(l+1); // ✅ safe but it skips ❌ the element due to the cursor position at already incremented ele
            }
        }
        System.out.println(trues);
        // So, use ListIterator#add(Object) and then ListIterator#next()
        trues = new ArrayList<>();
        trues.add(0);
        listIt = trues.listIterator();
        while (listIt.hasNext()) {
            int l = listIt.next();
            if (trues.size() < 5) {
                listIt.add(l+1); // ✅ Safe: we're not using iterator here
                listIt.previous(); // ✅ Safe: we're not using iterator here
            }
        }
        System.out.println(trues);








        // HANDLE ConcurrentModificationException CME in LinkedHashSet / HashSet
        System.out.println("Started Set modification over Iterator");
        String str = "pwwkew";
        Set<Character> set = new LinkedHashSet<>();
        set.add(str.charAt(0));
        set.add(str.charAt(1));
        set.add(str.charAt(2));
        for(char c : str.toCharArray()) {
            if(!set.add(c)) {
                Iterator<Character> it = set.iterator();
                while(it.hasNext() && it.next() != c) {
                    it.remove(); // ✅
                    // set.remove(...); // will throw ConcurrentModificationException ❌
                }
                set.remove(c);
                set.add(c);
            }
        }



        // HANDLE ConcurrentModificationException CME in HashMap
        Map<Character, Integer> map = new HashMap<>();
        map.put('a', 1);
        Iterator<Character> it = map.keySet().iterator(); // to avoid ConcurrentModificationException
        while(it.hasNext()) {
            char c = it.next();
            map.merge(c, -1, Integer::sum);
            if(map.get(c) == 0) {
                it.remove();  // safe removal via iterator ✅
                // map.remove(c); // will throw ConcurrentModificationException ❌
            }
        }




        // CREATE Iterator and ListIterator
        List<Integer> list = new ArrayList<>(Arrays.asList(1,2,3,4,5));
        Iterator<Integer> iterator = list.iterator(); // It’s fail-fast: if the list is structurally modified during iteration (outside of iterator's own remove()), it throws ConcurrentModificationException.
        Iterator<Integer> streamIterator = list.stream().iterator(); // Applies all intermediate operations (like filter(), map(), distinct(), etc.) lazily as you call next().
        Iterator<Integer> iterator2 = list.listIterator();
        ListIterator<Integer> listIterator = list.listIterator(); // here we have extra methods like .previous(), .hasPrevious(), nextIndex(), previousIndex(), etc.



        // CREATE Spliterator
        Spliterator<Integer> spliterator = list.spliterator();
        Spliterator<Integer> streamSpliterator = list.stream().spliterator();

        Spliterator<Integer> split1 = spliterator.trySplit();
        if (split1 != null) {
            split1.forEachRemaining(System.out::println); // prints part of the list
        }
        spliterator.forEachRemaining(System.out::println); // prints the rest




        // CREATE Enumeration
        Enumeration<Integer> enumeration = Collections.enumeration(list);
        Vector<String> v = new Vector<>(Arrays.asList("a", "b"));
        Enumeration<String> enumeration2 = v.elements();
        while (enumeration2.hasMoreElements()) {
            System.out.println(enumeration2.nextElement());
        }
        Iterator<String> enumerationIterator = enumeration2.asIterator();
        // enumeration.remove(); ❌ ----> Enumeration does not have a remove() method.








        // ConcurrentModificationException CME in list
        List<String> strList = new ArrayList<>(List.of("A", "B", "C", "D"));
        for(int i=0; i<strList.size(); i++) {
            strList.remove(strList.get(i)); // this will not throw ConcurrentModificationException ✅
            i--; // optional: to recheck shifted elements -- Otherwise, you may skip elements
        }

        strList = new ArrayList<>(List.of("A", "B", "C", "D"));
        for (int i=0; i<strList.size(); i++) {
            strList.remove(i); // this will not throw ConcurrentModificationException ✅
            i--; // optional: to recheck shifted elements -- Otherwise, you may skip elements
        }

        strList = new ArrayList<>(List.of("A", "B", "C", "D"));
		for (String s : strList) {
            // strList.remove(s); // ---> this will throw ConcurrentModificationException ❌
		}

        // NOTE: In a for-i (index-based) loop, you do NOT get ConcurrentModificationException, even if you modify the list during iteration.
        // so use for-i loop or Iterator to avoid CME in list ----> for-each uses an Iterator, for-i does not

        strList = new ArrayList<>(List.of("A", "B", "C", "D"));
        Iterator<String> strIterator = strList.iterator();
        while (strIterator.hasNext()) {
            String s = strIterator.next();
//            strList.remove(s); // ---> this will throw ConcurrentModificationException ❌
            strIterator.remove(); // this will not throw ConcurrentModificationException ✅
        }
        strList.forEach(System.out::println);








        // ConcurrentModificationException CME in CopyOnWriteArrayList
        List<String> copyOnWriteArrayList = new CopyOnWriteArrayList<>(List.of("A", "B", "C", "D"));
        for (String s : copyOnWriteArrayList) {
            copyOnWriteArrayList.remove(s); // this will not throw ConcurrentModificationException ✅
        }

        copyOnWriteArrayList = new CopyOnWriteArrayList<>(List.of("A", "B", "C", "D"));
        Iterator<String> it2 = copyOnWriteArrayList.iterator();
        while (it2.hasNext()) {
            String s = it2.next();
            // it2.remove(); // CopyOnWriteArrayList allows structural modifications during iteration, but❗️Its Iterator.remove() is unsupported — it always throws UnsupportedOperationException.
            copyOnWriteArrayList.remove(s); // this will not throw ConcurrentModificationException ✅
        }






        // ConcurrentModificationException CME in ConcurrentHashSet
        Set<String> concurrentHashSet = new ConcurrentSkipListSet<>(List.of("A", "B", "C", "D"));
        for (String s : concurrentHashSet) {
            concurrentHashSet.remove(s); // this will not throw ConcurrentModificationException ✅
        }

        concurrentHashSet = new ConcurrentSkipListSet<>(List.of("A", "B", "C", "D"));
        Iterator<String> it3 = concurrentHashSet.iterator();
        while (it3.hasNext()) {
            String s = it3.next();
            it3.remove(); // this will not throw ConcurrentModificationException ✅
            concurrentHashSet.remove(s);
        }
    }
}
