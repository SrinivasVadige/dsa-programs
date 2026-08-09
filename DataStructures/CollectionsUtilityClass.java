package DataStructures;

/**
<pre>
COLLECTIONS UTILITY CLASS
=========================
"public class Collections extends Object" added in 1.2 i.e JDK 2.
COLLECTIONS UTILITY CLASS: "Collections" is a utility class present in java.util package which is a member of the Java Collections Framework. Contains static methods and provides API for working with Collections, Lists, Maps, and Sets.
COLLECTION INTERFACE: "Collection" is a interface present in java.util package which is a group of objects represent as a single entity. It is used to represent a group of individual objects as a single unit. It is similar to the container in the C++ language. The collection is considered as the root interface of the collection framework. It provides several classes and interfaces to represent a group of individual objects as a single unit. 
COLLECTION FRAMEWORK: set of classes and interfaces used for collection. List, Set, Map.... these kind of all classes and interfaces are generically called as collection.


Using Collections class static methods we can do lot more than list, HashMap, set, collections, stream default methods/operations i.e
This class consists exclusively of static methods that operate on or return collections. It contains polymorphic algorithms that operate on collections, "wrappers", which return a new collection backed by a specified collection, and a few other odds and ends.

FIELDS / VARIABLES
---------------------------------
Collections.EMPTY_LIST -- but .emptyList() recommended
Collections.EMPTY_MAP
Collections.EMPTY_SET


METHODS
-----------------
reference => https://docs.oracle.com/javase/8/docs/api/?java/util/Collections.html
.addAll(Collection<? super T> c, T... elements) ****
.copy(List<? super T> dest, List<? extends T> src) ***
.sort(List<T> list) *** -- merge sort algorithm
.swap(List<?> list, int i, int j) *** 🔥
.max(Collection<? extends T> coll) ***
.max(Collection<? extends T> coll, Comparator<? super T> comp) ***
.min(Collection<? extends T> coll) ***
.fill(List<? super T> list, T obj) ***
.reverse() ***
.shuffle(List<?> list) ***
.emptyList() ***
.binarySearch(List<? extends Comparable<? super T>> list, T key) --- eg: .binarySearch(list1, "item") 🔥
.binarySearch(List<? extends T> list, T key, Comparator<? super T> c)
.singleton("someString") -- immutable set 🔥and throws UnsupportedOperationException if edited
.singletonList("someString")
.singletonMap("someString")
.frequency()
.asLifoQueue(Deque<T> deque)
.checkedCollection(Collection<E> c, Class<E> type)
.checkedList(List<E> list, Class<E> type)
.checkedMap(Map<K,V> m, Class<K> keyType, Class<V> valueType)
.checkedNavigableMap(NavigableMap<K,V> m, Class<K> keyType, Class<V> valueType)
.checkedNavigableSet(NavigableSet<E> s, Class<E> type)
.checkedQueue(Queue<E> queue, Class<E> type)
.checkedSet(Set<E> s, Class<E> type)
.checkedSortedMap(SortedMap<K,V> m, Class<K> keyType, Class<V> valueType)
.checkedSortedSet(SortedSet<E> s, Class<E> type)
.disjoint(Collection<?> c1, Collection<?> c2)
.emptyEnumeration()
.emptyIterator()
.emptyListIterator()
.emptyMap()
.emptyNavigableMap()
.emptySet()
.list(Enumeration<T> e)
.replaceAll(List<T> list, T oldVal, T newVal) 🔥
.sort(List<T> list) ----> Collections.sort(lst)
.sort(List<T> list, Comparator<? super T> c) ------>
.sort(lst, Collections.reverseOrder()) or .sort(lst, Comparator.reverseOrder())
.sort(lst, Comparator.comparingInt(Integer::intValue));
.sort(lst, (o1, o2) -> o1-o2 ) or sort(lst, (o1, o2) -> o1.compareTo(o2) ) or .sort(lst, (o1, o2) -> o1 > o2? 1: -1) // ASC 
.sort(lst, (o1, o2) -> o2-o1 ) or sort(lst, (o1, o2) -> o2.compareTo(o1) ) or .sort(lst, (o1, o2) -> o2 > o1? 1: -1) // DESC
.sychronizedList(lst) ---> use this instead of java.util.Vector for thread-safety


Egs:
List<String> mapValues = new ArrayList<String>(myHashMap.values());
Collections.sort(mapValues);

Note:
1.  Collections.EMPTY_LIST returns an old-style List and Collections.emptyList() uses type-inference and therefore returns List<T>. Collections.emptyList() was added in Java 1.5 and it is probably always preferable. This way, you don't need to unnecessarily cast @SuppressWarnings("unchecked") around within your code. Collections.emptyList() intrinsically does the cast for you.

</pre>
 * @author Srinvas Vadige, srinivas.vadige@gmail.com
 * @since 21 Sept 2024
 */
public class CollectionsUtilityClass {
    public static void main(String[] args) {

    }
}
