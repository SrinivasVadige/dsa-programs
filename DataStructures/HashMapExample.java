package DataStructures;

import java.util.*;
import java.util.AbstractMap.SimpleEntry;
import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

// NOTE: In loops (Eg: for counting num of chars in a string) use stream or MERGE or COMPUTE instead of get and put combined
/**
* @author Srinvas Vadige, srinivas.vadige@gmail.com
* @since 21 Sept 2024

 HOW HASHMAP WORKS --> Single Linked List 🔥
 ------------------
 class HashMap<k, v> extends AbstractMap<k, v> implements Map<K,V>, Cloneable, Serializable {}
 class HashSet<k> extends AbstractSet<k> implements Set<K>, Cloneable, Serializable {} --> internally extends HashMap

 new HashMap<>() == new HashMap<>(16, 0.75f);
 new HashSet<>() == new HashSet<>(16, 0.75f);

 int capacity = 16; ---> default initial capacity
 float loadFactor = 0.75f; ---> default load factor
 NOTE:
 capacity is always a power of 2 ---> if we give capacity as 5, then the HashMap will allocate 8 buckets --> which is the next power of 2
 loadFactor is always a float value between 0 to 1

 HashMap is a hash table --> array of nodes - of length capacity
 Node<k, v>[] table = new Node<k, v>[capacity]

 HashMap node looks like this
 public class Node<K, V> {
     K key;
     V value;
     Node<K, V> next; ---> for collision chaining
 }

 bucket index == i
 bucket == table[i]

 but note that bucket holds the entry data --> Node reference
 bucket != entry data --> bucket holds the entry data



 int keyHashCode = key.hashCode();
 bucketIndex = keyHashCode & (capacity - 1);
 bucket = table[bucketIndex]

 Different keys with unique hashCodes might have same bucketIndex --> because bucketIndex = hashcode & (capacity - 1)
 Example:
 "A" -> hashCode = 65 -> bucketIndex = 65 & (16 - 1) = 0
 "B" -> hashCode = 66 -> bucketIndex = 66 & (16 - 1) = 0
 "C" -> hashCode = 67 -> bucketIndex = 67 & (16 - 1) = 0

 So, here even though we have 2 different keys with unique hashCodes, they might have same bucketIndex
 this is called collision

 collision is handled by collision chaining using linked list
 so, Node.next will contains the duplicate bucketIndex nodes like

 Node<k, v>[] table = new Node<k, v>[capacity];
 table[0] = new Node("A", 1);
 table[0].next = new Node("B", 2);
 table[0].next.next = new Node("C", 3);

 Node(A) -> Node(B) -> Node(C)

 And lets say we have 0.75f loadFactor
 then
 resize happens when the new size is less threshold
 threshold = capacity * loadFactor
 threshold = 16 * 0.75f = 12

 if ((size+1) > threshold) {  // ---> "size+1" because we calculate this isResize() before adding a new entry to HashMap
     capacity *= 2;
     Node<k, v>[] newTable = new Node[k, v][capacity];
     for (Node<k, v> node : table) { // ---> iterate current table and distribute to new table with their unique bucketIndex
         while (node != null) {
             Node<k, v> next = node.next;
             int bucketIndex = node.key.hashCode() & (capacity - 1);
             node.next = newTable[bucketIndex];
             newTable[bucketIndex] = node;
             node = next;
         }
     }
     table = newTable;
 }

 so, all the nodes again distributed to new table with their unique bucketIndex with O(n) time complexity
 Node(A) -> Node(B) -> Node(C) will be distributed to it's new bucketIndex in new buckets
 that is how HashMap works as O(1) even the capacity is increased


 NOTE:
 1) All the operations are O(1) "amortized"
-> this means even we do O(n) in resize, when compared to millions of get() and put() with O(1) --> avg = O(1)
 that is why HashMap loop don't maintain the exact order of insertion or sorting







  HOW LINKED HASHMAP WORKS --> Doubly Linked List 🔥
 -------------------------
 Same as hashmap

 class LinkedHashMap<k,v> extends HashMap<k,v> {}

 new LinkedHashMap<>() == new LinkedHashMap<>(16, 0.75f, false);
 int capacity = 16; ---> default initial capacity
 float loadFactor = 0.75f; ---> default load factor
 boolean accessOrder = false; ---> default accessOrder = false --> insertion order
 accessOrder = true --> maintain the order or access

 LinkedHashMap node looks like this
 public class Node<k, v> extends HashMap.Node<k, v> { // ---> along with HashMap.Node properties, we have before & after
     Node<k, v> before;
     Node<k, v> after;
 }

 Node<k,v> head;
 Node<k,v> tail;

 So, if we loop through LinkedHashMap --> we loop head node node table indices
 that is why we can maintain the exact order of insertion or access order

 if inserted new item in bucketIndex and set the next of currentTail to the new item and make this new item as the tail
 then we have the exact order of insertion or access order

 in accessOrder = true, we change the
 prev of currentNode,
 next of currentNode,
 next of (currentNode-1) node
 prev of (currentNode+1) node
 and make the currentNode as the new tail

 this is how LinkedHashMap works ---> same as "Least Recently Used" (LRU) cache algorithm, session management
 just @Override removeEldestEntry() for LRU and 🔥
 but we don't have method to remove "Most Recently Used" (MRU) entry --> to remove friendSuggestion after "not interested"

        Map<Integer, Integer> linkedHashMap5 = new LinkedHashMap<>(16, 0.75f, true); // same as above>

 NOTE:
 1) We can achieve accessOrder inside insertion ordered LinkedHashMap
    ---> just by removing the node from the middle and adding the recent key at tail or end

*/
@SuppressWarnings("unused")
public class HashMapExample {

    public static void main(String[] args) {

        // USING GROUPING_BY: get the all characters occurrences in a string
        String str = "srinivasrepo";
        Map<String, Long> charMap = Arrays.stream(str.split(""))
                                        .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        Map<Character, Integer> charMap2 = Arrays.stream(str.split(""))
                                        .collect(Collectors.groupingBy(i-> i.charAt(0), Collectors.summingInt(e -> 1)));
        Map<Character, Integer> charMap3 = str.chars().mapToObj(e->(char)e)
                                        .collect(Collectors.groupingBy(i-> i, Collectors.summingInt(e -> 1)));
        // Function.identity() or i->i ---- both works
        // Collectors.summingInt(e -> 1) will count but Collectors.summingInt(Integer::valueOf) will sum all the items / values but here the item is char.

        // USING TO_MAP: get the all characters occurrences in a string
        Map<Character, Integer> charMap4 = Arrays.stream(str.split(""))
                                        .collect(Collectors.toMap(i-> i.charAt(0), i->1, Integer::sum));
        Map<Character, Integer> charMap5 = str.chars().mapToObj(e->(char)e)
                                        .collect(Collectors.toMap(i-> i, i->1, Integer::sum));
        Map<Character, Integer> charMap6 = str.chars().mapToObj(e->(char)e)
                                        .collect(Collectors.toMap(i-> i, i->1, Integer::sum, HashMap::new));

        // DIFF BETWEEN GROUPING_BY AND TO_MAP
        List<String> names = List.of("apple", "banana", "apricot", "blueberry");
        Map<Character, List<String>> groupedByFirstChar = names.stream()
            .collect(Collectors.groupingBy(name -> name.charAt(0))); // {a=[apple, apricot], b=[banana, blueberry]}
        System.out.println(groupedByFirstChar);
        Map<Character, String> mapByFirstChar = names.stream()
            .collect(Collectors.toMap(
                name -> name.charAt(0),
                name -> name,
                (existing, replacement) -> existing + ", " + replacement // merge function
            ));
        System.out.println(mapByFirstChar);
        // Use groupingBy when you expect multiple values per key (grouping scenarios).
        // Use toMap when you expect unique keys, or when you can merge values meaningfully.

/*
        Here we cannot use identity (Function::identity) and counting as method references and get error
        Because this identity and counting methods don't have expected number of arguments
        (here expected number of args in classifier.apply(t) is 1 but Function.identity() method has 0 args)
        (and downstream.supplier() return type is Supplier<A> but Collectors.counting() return type is Collector)
*/
        // str.chars().mapToObj(e->(char)e) --- Stream<Character> and in console it's type of IntPipeline$1
        System.out.println(charMap);
        System.out.println(charMap2);
        System.out.println(charMap3);


        HashMap<Character, Integer> map = new HashMap<>();
        HashMap<Character, Integer> map2 = new HashMap<>();
        // IMP NOTE
        // instead of
        map.put('A', map.get('A')!=null? map.get('A')+1 : 1);
        // or
        map.put('A', map.getOrDefault('A', 0) + 1 );
        //use
        map.merge('A', 1, Integer::sum);


        // PUT (add new or update existing)
        map.put('A',1); //=> map.put(k,v) to insert new or update existing
        map.putIfAbsent('A',1); //=> only insert new entry set
        map.putAll(map2); //=> map.putAll(HashMap / TreeMap / any Map obj) to add 2 maps

        // GET
        map.get('A'); //=> returns value or null
        map.getOrDefault('B', 0); //=> map.getOrDefault(k, defaultValue); v or defVal

        // REMOVE
        map.remove('B');

        // REPLACE (only update existing)
        map.replace('B', 0);
        map.replaceAll((key, oldValue)-> oldValue++); // BiFunction

        // FIND ITEM
        map.containsKey('Z'); // => boolean
        map.containsValue(99); // => boolean

        //
        map.isEmpty();
        map.clear();
        map.clone();
        map.size();

        // MERGE    (ALL THESE .MERGE() ARE SAME)
        map.merge('C', 1, Integer::sum); // =>  map.merge(k, v, vdt::sum);
        map.merge('B', 1, (oldVal,newVal)-> oldVal+newVal);
        map.merge('B', 1, (ov,nv)->{ return ov+nv; } );
        map.merge('A', 5, (oldV,newV)-> oldV==null? 1:newV+oldV);

        // COMPUTE
        map.compute('S', (k,v)-> v==null?1:v+1); // map.compute(KeySymbol, (k,v)-> v==null?1:v+1);
        map.computeIfAbsent('S', (v)-> v==null?1:v+1);
        map.computeIfPresent('S', (k,v)-> v==null?1:v+1);

        // COMPUTE IF ABSENT TO MAKE VALUE AS LIST AND ADD NEW VALUE IN ONE LINE
        int[] nums = {1,2,3,1};
        Map<Integer, List<Integer>> indices = new HashMap<>();
        for (int i = 0; i < nums.length; i++)
            indices.computeIfAbsent(nums[i], v -> new ArrayList<>()).add(i);
        // => {1=[0, 3], 2=[1], 3=[2]}


        // TO ITERATE Map.entrySet() & Map.keySet() => In collection or stream Java8 Iterable.forEach() lambda
        // we can only use continue using “return;” but cannot break the look. So, use collection.forEach or stream.forEach or java 5 traditional 'enhanced for loop (for each)' / traditional for loop.

        // MAP TO ENTRY SET
        Set<Map.Entry<Character, Integer>> entrySet = map.entrySet(); // map.entrySet()=> Set<Map.Entry<kdt,vdt>> entry => entry.getKey() and entry.getValue()
        // FYI: "Set" or "HashSet" don't have .get() method. So, iterate or convert to stream, list or iterator
        entrySet.stream().toList().get(0).getKey();
        entrySet.stream().toList().get(0).getValue();
        map.entrySet().iterator().next().getKey();


        // MAP TO KEY SET
        Set<Character> set = map.keySet(); // => Set<kdt> set
        set.stream().toList().get(0); // => key

        // MAP TO VALUES COLLECTION
        Collection<Integer> collectionOfVals = map.values(); // => Collection<ValDataType>

        // MAP FOREACH
        map.forEach( (k,v) -> System.out.printf("%s%s, ", k, v));



        //  TO GET KEY USING VALUE
        for (Map.Entry<Character, Integer> entry : map.entrySet()) {
            if (Objects.equals(99, entry.getValue())) {
                entry.getKey(); // return
            }
        }
        // or
        map.entrySet().stream().filter(entry -> Objects.equals(entry.getValue(), 1)).map(Map.Entry::getKey).collect(Collectors.toList()).get(0); // set or list or get
        map.entrySet().stream().filter(entry -> entry.getValue().equals(1)).toList().get(0).getKey();
        // as we don't need this list just convert to .toList() immutable list


        // LIST TO MAP -------------
        List<Employee> employees = new ArrayList<>();

        // Group employees by department
        Map<Department, List<Employee>> byDept = employees.stream()
                                                    .collect(Collectors.groupingBy(Employee::getDepartment));

        // Compute sum of salaries by department
        Map<Department, Integer> totalByDept = employees.stream()
                                                    .collect(Collectors.groupingBy(Employee::getDepartment,
                                                    Collectors.summingInt(Employee::getSalary)));

        // Partition employees into passing and failing the A band for annual appraisal
        Map<Boolean, List<Employee>> passingFailing = employees.stream()
                                                        .collect(Collectors.partitioningBy(s ->
                                                        s.getGrade() >= 4.0));





       // In Java we have AbstractMap, HashMap, LinkedHashMap, TreeMap, ConcurrentHashMap, IdentityHashMap
       // And in AbstractMap we have AbstractMap.SimpleEntry, AbstractMap.SimpleImmutableEntry
       // i.e just like 'cpp STL <utility> header - pair container' --> SimpleEntry & 'python tuple' --> SimpleImmutableEntry
       // Note that we can also use Map.Entry & map.entry() in the same way
        AbstractMap.SimpleEntry<Integer, String> entry = new SimpleEntry<>(1, "foo");
		entry.getKey();
		entry.getValue();
		entry.setValue("bar");
        // or
        Map.Entry<Integer, String> entry1 = new AbstractMap.SimpleEntry<>(1, "foo");

		AbstractMap.SimpleImmutableEntry<Integer, String> entry2 = new SimpleImmutableEntry<>(1, "foo");
		entry2.getKey();
		entry2.getValue();
		// entry2.setValue("bar"); // java.lang.UnsupportedOperationException

		System.out.println(entry);
		System.out.println(entry2);

        Map.Entry<Integer, String> entry3 = new AbstractMap.SimpleEntry<>(1, "foo");
        entry3.getKey();
        entry3.getValue();
        entry3.setValue("bar");

        Map.Entry<Integer, String> entry4 = Map.entry(1, "foo");
        entry4.getKey();
        entry4.getValue();
        //entry4.setValue("bar"); // java.lang.UnsupportedOperationException

        // But we cannot setKey()
        // and cannot AbstractMap.SimpleEntry<Integer, String> entry3 = Map.entry(1, "foo");

        /**
         * NOTE: new AbstractMap.SimpleEntry<>() is introduces in Java 4
         * and Map.entry() is introduced in Java 9 and Map.entry() is immutable and get java.lang.UnsupportedOperationException in setValue
         * So, always use new AbstractMap.SimpleEntry<>(); but with Immutable Key and Mutable Value
         * 🔥
         * And if you want a Mutable Key & Value then use a custom class with getter and setter.
         */


        // IMMUTABLE MAP
        Map<Integer, String> mapOf = Map.of(1, "foo", 2, "bar");
        Map<Integer, String> mapOfEntries = Map.ofEntries(Map.entry(1, "foo"), Map.entry(2, "bar"));



        /* DOUBLE BRACES INITIALIZATION
         * Double braces initialization creates subclasses and Breaks Serializable, Equals, and HashCode
         */

        Map<String, Integer> doubleBracesMap = new LinkedHashMap<>(){{
            put("M", 1000);
            put("CM", 900);
            put("D", 500);
            put("CD", 400);
            put("C", 100);
            put("XC", 90);
            put("L", 50);
            put("XL", 40);
            put("X", 10);
            put("IX", 9);
            put("V", 5);
            put("IV", 4);
            put("I", 1);
        }};





        /**
         * NOTE:
         * Set<Map<Integer, Integer> set.contains(map1) has time complexity O(n) not O(1)
         * So, use this below custom MapWrapper custom class -- it checks both map.equals() and map.hashCode()
         */

        Set<MapWrapper> mapWrapperSet = new HashSet<>(); // set of maps with O(1) TimeComplexity

        Map<Integer, Integer> m1 = new HashMap<>();
        m1.put(1, 2);
        m1.put(3, 4);

        Map<Integer, Integer> m2 = new HashMap<>();
        m2.put(3, 4);
        m2.put(1, 2);

        mapWrapperSet.add(new MapWrapper(m1));
        System.out.println(mapWrapperSet.contains(new MapWrapper(m2))); // true


        /*
         * LINKED HASH MAP
         */
        Map<Integer, Integer> linkedHashMap = new LinkedHashMap<>(16, 0.75f, true);
        linkedHashMap.put(1, 2);
        linkedHashMap.put(3, 4);
        linkedHashMap.get(1);
        System.out.println(linkedHashMap);

        // all these are same --> LinkedHashMap with insertion order
        Map<Integer, Integer> linkedHashMap2 = new LinkedHashMap<>(16, 0.75f, false);
        Map<Integer, Integer> linkedHashMap3 = new LinkedHashMap<>(16, 0.75f);
        Map<Integer, Integer> linkedHashMap4 = new LinkedHashMap<>(16);
        Map<Integer, Integer> linkedHashMap5 = new LinkedHashMap<>();


    }

    public class Department {}
    public class Employee {
        Department department;
        int salary;
        double grade;
        Department getDepartment(){return department;}
        int getSalary() {return salary;}
        double getGrade(){return grade;}
    }


    public static class MapWrapper {
        private final Map<Integer, Integer> map;
        private final int hash;

        public MapWrapper(Map<Integer, Integer> map) {
            this.map = new HashMap<>(map); // Make defensive copy
            this.hash = computeHash(map);
        }

        private int computeHash(Map<Integer, Integer> map) {
            // Any consistent, order-independent hashing
            return map.hashCode(); // Still O(n), but done ONCE ----- note that this hashCode() may give false positives
        }

        @Override
        public int hashCode() {
            return hash; // O(1)
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (!(obj instanceof MapWrapper)) return false;
            MapWrapper other = (MapWrapper) obj;
            return this.map.equals(other.map); // Still O(n)
        }

        public Map<Integer, Integer> getMap() {
            return map;
        }
    }
}
