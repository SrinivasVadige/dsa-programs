package DataStructures;

import java.util.*;


/**
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 12 July 2025

    Best ways to handle / avoid 🔥 "ConcurrentModificationException CME" 🔥
        1) java.util.Iterator / ListIterator interface
        2) Custom toRemove collection like "List<T> toRemove = new ArrayList<>();" and do toRemove.add(ele or index) and loop this toRemove to remove the original list/set/map elements
        3) java.util.concurrent.CopyOnWriteArrayList - a thread-safe implementation of ArrayList


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





    ITERATOR
    ---------
    Iterator works for all Collections -> List(ArrayList, LinkedList), Set(HashSet, LinkedHashSet), Map(HashMap, LinkedHashMap), Queue(LinkedList, PriorityQueue), Deque(LinkedList), Stack
    ⚠️ But there's a catch: Order of iteration
    -> For a Stack (like java.util.Stack), iterator() iterates from bottom to top (FIFO order), not LIFO. So popping manually gives a different order than iterating.
    -> For a Queue (LinkedList, PriorityQueue, etc.), the iterator() goes from head to tail, respecting insertion order (unless it’s a priority-based structure).
    -> For PriorityQueue, iteration order ≠ poll order — it's not sorted in iteration.


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


        // HANDLE ConcurrentModificationException CME in LinkedHashSet / HashSet
        String s = "pwwkew";
        Set<Character> set = new LinkedHashSet<>();
        for(char c : s.toCharArray()) {
            if(!set.add(c)) {
                Iterator<Character> it = set.iterator();
                while(it.hasNext() && it.next() != c) {
                    it.remove();
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
                it.remove();  // safe removal via iterator. map.remove(c); will throw ConcurrentModificationException
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
    }
}
