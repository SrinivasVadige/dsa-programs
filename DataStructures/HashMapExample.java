package DataStructures;

import java.util.AbstractMap;
import java.util.AbstractMap.SimpleEntry;
import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

// NOTE: In loops (Eg: for counting num of chars in a string) use stream or MERGE or COMPUTE instead of get and put combined
/**
* @author Srinvas Vadige, srinivas.vadige@gmail.com
* @since 21 Sept 2024
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







        /**
         * NOTE:
         * Set<Map<Integer, Integer> set.contains(map1) has time complexity O(n) not O(1)
         * So, use this below custom MapWrapper custom class -- it checks both map.equals() and map.hashCode()
         */

        Set<MapWrapper> setMapWrapper = new HashSet<>();

        Map<Integer, Integer> m1 = new HashMap<>();
        m1.put(1, 2);
        m1.put(3, 4);

        Map<Integer, Integer> m2 = new HashMap<>();
        m2.put(3, 4);
        m2.put(1, 2);

        setMapWrapper.add(new MapWrapper(m1));
        System.out.println(setMapWrapper.contains(new MapWrapper(m2))); // true

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
