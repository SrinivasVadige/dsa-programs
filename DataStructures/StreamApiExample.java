package DataStructures;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.*;

/**
<pre>
STREAM
=======
import java.util.stream.Stream; IntStream; LongStream; BaseStream; interfaces
import java.util.stream.Collectors; final class and Collector; interface

1. Stream can only be used once. We cannot re-use stream variable second time.
2. Stream are not collections & cannot loop the Stream with for loop like for(int i=0; i< strStream.count(); i++) or for(item: streamVar) as Java Streams don't have index and there is no option to get item with index. So, use map, forEach and iterator() stream methods or just filter().findFirst().orElse() to get the specific element.
3. Use IntStream.range(startInclusive, endExclusive) instead of traditional for loop same like Python. Use forEach() not map().
IntStream.range(0, intArr.length).forEach(i->{
       intArr[i] = i;
});
4. To print the Stream just use ".toList()", no need to use forEach(System.out::println)

STREAM CONVERSION
————————————-
IntStream.of(1,2,3) or IntStream.of(int[])
Arrays.stream(arr)
Stream.of(ele1, ele2 …..) or Stream.of(arr or lst)
list.stream() or map.stream() or set.stream()
to get the all characters occurrences in a string
        String str = "srinivasrepo";
        Map<String, Long> charMap = Arrays.stream(str.split(""))
                                        .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
Note: Function.identity() or i->i ---- both works

METHODS
—————-
 Stream.concat(Stream1, Stream2)
 Stream.of(ele) or Stream.of(eles) or Stream.of(arr or lst)
 Arrays.stream(arr) or IntStream.of(int[]) or List.stream(lst) or Set.stream(set) or Map.stream(map)
.filter(Predicate)
.allMatch(Predicate)
.anyMatch(Predicate)
.noneMatch(Predicate)
.peek(Consumer) —- only when there is a change
.forEach(Consumer)
.forEachOrdered(Consumer)
.findAny()
.findFirst()
.count() - size but for list it's size()
.distinct() --- like set
.empty()
.limit(intMaxSize)
.skip(longVal)
.min().getAsInt() // ---- for IntStream
.min(Comparator) --> for Stream<Integer>  --> .stream().min(Integer::compare).get() or .stream().min(Comparator.naturalOrder()).get() or Collections.min(lst)
.max().getAsInt() // ---- for IntStream
.max(Comparator) --> for Stream<Integer>
.sum() // ----- only for IntStream
.reduce(BinaryOperator accumulator) // for Stream<Integer> -- reduce(0, (t, u) -> t+u) to compute the min, max, sum, or product. Eg: lst.stream().reduce(0, Integer::sum) or lst.stream().mapToInt(i->i).sum() or collect(Collectors.summingInt(Integer::intValue)) 
.reduce(T identity, BinaryOperator accumulator)
.reduce(U identity, BiFunction<U,? super T,U> accumulator, BinaryOperator<U> combiner)
.sorted() --- Tim Sort algorithm
.sorted(Comparator) --- for ClassObjs using a field
.builder() --- From Stream class
.peek(Consumer)
.toArray()
.toArray(EleDataType[]::new)
.map(Function)
.map().distinct()
.map().summaryStatistics() => stats
.mapToInt()
.mapToDouble(ToDoubleFunction)
.mapToLong(ToLongFunction)
.flatMap(Function<T, Stream> mapper)
.flatMapToInt(Function<T, IntStream>)
.flatMapToDouble(Function<T, DoubleStream>)
.flatMapToLong(Function<T, LongStream>)
.collect(Collector<? super T, A, R> collector) —- Collectors not collections
.collect(Supplier<R> supplier, BiConsumer<R,? super T> accumulator, BiConsumer<R,R> combiner)
.boxed() —- for arrays to convert IntStream to Integer Stream
 Stream.generate(Supplier)
 Stream.iterate(seed, UnaryOperator)

Java 9 methods
.ofNullable(ele) --- From Stream class
.takeWhile(Predicate) -- take all the elements while the predicate is true and don't trav the rest [1, 2, 3, 4, 1, 2] .takeWhile(n -> n < 3) will return [1, 2]
.dropWhile(Predicate)
.iterate(seed, UnaryOperator)
.iterate(T seed, Predicate<? super T> hasNext, UnaryOperator<T> next)

Java 16 methods
.mapMulti()
.mapMultiToInt()
.mapMultiToDouble()
.mapMultiToLong()
.toList() — to immutable list

Java 22 methods
.gather(GathererInPreview)

Methods inherited from interface java.util.stream.BaseStream
.close()
.onClose()
.isParallel()
.parallel()
.iterator()
.spliterator()
.sequential()
.unordered()

Stream class methods and interfaces
----------------------------------------------------
Stream.of()
Stream.concat()
Stream.builder()
Stream.Builder --- interface for Stream.builder() return type
Stream.generate()
Stream.iterate()
Stream.ofNullable()
Stream.empty()

.collect() method
—————————-
1. collect(Collector<? super T, A, R> collector) --- 1 param
2. collect(Supplier<R> supplier, BiConsumer<R, ? super T> accumulator, BiConsumer<R, R> combiner) --- 3 param

.collect(Collectors.toList())
.collect(Collectors.toList(EleDataType[]::new))
.collect(Collectors.groupingBy(i -> list.get(i), Collectors.toList()))
.collect(Collectors.toMap()) 🔥same as groupingBy()
Eg:
Stream.of(s.split("")).collect(Collectors.toMap(e->e, e->1)) -- throws java.lang.IllegalStateException: for Duplicate key
so use merge function param
Map<String, Integer> map = Stream.of(s.split("")).collect(Collectors.toMap(e->e, e->1, Integer::sum)) 🔥
.collect(Collectors.joining(“”))
.collect(Collectors.summingInt(e->e or Integer::valueOf) or summingLong); --- returns the sum
.collect(Collectors.summingInt(e->1)) --- returns eles count in Integer
.collect(Collectors.counting()) --- returns eles count in Long, here no need to e->1. And we don't have countingInt()
.collect(Collectors.summarizingInt(Integer::intValue)); --- returns IntSummaryStatistics{count=3, sum=5, min=1, average=1.666667, max=2}

groupingBy() and partitioningBy() methods
---------------------------------------------------------------------
1. Map< K, List<T> > groupingBy(Function<? super T, ? extends K> classifier) --- 1 param
2. Map<K, D> groupingBy(Function<? super T, ? extends K> classifier, Collector<? super T, A, D> downstream) -- 2 p
3. Map<K, D> groupingBy(Function<? super T, ? extends K> classifier, Supplier<M> mapFactory, Collector<? super T, A, D> downstream) -- 3 params
4. Map< Boolean, List<T> > partitioningBy(Predicate<? super T> predicate) --- 1 param
5. Map<Boolean, D> partitioningBy(Predicate<? super T> predicate, Collector<? super T, A, D> downstream) -- 2 p

people.stream().collect(Collectors.groupingBy(Person::getAge)); --- Map<Object, List<Integer>> {19=["a","b"], 25=["seenu", "kajal"], 30=["iliana"]}
.collect(Collectors.partitioningBy(s -> s.getGrade() >= 4.0)); --- Map<Boolean, List<Person>> split your data into two categories. i.e same like groupingBy() with single param but key will be Boolean
Note that Collectors.groupingBy(i->i%2==0) is same as Collectors.partitioningBy(i->i%2==0)

.collect(Collectors.groupingBy(Function.identity(), Collectors.counting())) - Map<E,Long> for item k with repitions v
.collect(Collectors.groupingBy(Function.identity(), Collectors.summingInt(Integer::intValue) )); --  Map<E,Integer> -- returns the sum not the count like counting() for item k with total sum of that item v
Map<Character, Integer> charMap3 = str.chars().mapToObj(e->(char)e).collect(Collectors.groupingBy(i-> i, Collectors.summingInt(e -> 1)));
or Arrays.stream(str.split("")).map(e->e.charAt(0)).collect(Collectors.groupingBy(i-> i, Collectors.summingInt(e -> 1)))
--- works fine i.e best alternative for Long 🔥
or str.chars().mapToObj(i->(char)i).collect(Collectors.toMap(i->i, i->1, Integer::sum)); 🔥
.collect(Collectors.groupingBy(Function.identity(), Collectors.summarizingInt(Integer::intValue) )); -- Map<E, IntSummaryStatistics>
.collect(Collectors.groupingBy(Employee::getDepartment));
.collect(Collectors.groupingBy(Employee::getDepartment, Collectors.summingInt(Employee::getSalary))); -- Map<E,Integer> -- CTC of each department


 NOTE:
 1) We don't have CharStream. So, use
 Stream<Character> Stream.of("abc".split("")).map(e->e.charAt(0)); or "abc".chars().mapToObj(i->(char)i);
 or Stream<String> Stream.of("abc".split(""))
 or IntStream "abc".chars()

</pre>
* @author Srinvas Vadige, srinivas.vadige@gmail.com
* @since 21 Sept 2024
*/
public class StreamApiExample {

    public static void main(String[] args) {
        int[] pArr = {1, 2, 3, 5, 7}; // primitive array
        Integer[] npArr = {1, 2, 3, 5, 7}; // non-primitive array
        int[][] p2dArr = {{1, 2, 3}, {5, 7}}; // non-primitive array -- 2D primitive array
        List<Integer> list = Arrays.asList(1, 2, 3, 5, 7);
        List<int[]> list2 = Arrays.asList(new int[]{1, 2, 3}, new int[]{5, 7});
        @SuppressWarnings("unchecked")
        List<Integer>[] list3 = new List[2];
        list3[0] = Arrays.asList(1, 2, 3, 5, 7);
        list3[1] = Arrays.asList(1, 2, 3, 5, 7);
        Object[] objArray = new Object[1];
        objArray[0] = Arrays.asList("a", "b");
        List<List<Integer>> listOfLists = new ArrayList<>();

        //region test few stream api methods here


        //endregion

        // CREATE A STREAM using Stream.of(), Stream.concat(), Arrays.stream(), IntStream.of(), IntStream.rangeClosed(), IntStream.range(), IntStream.iterate(), IntStream.empty()
        Stream<Integer> emptyStream = Stream.empty();
        Stream<Integer> stream1 = Stream.of(1, 2, 3, 5, 7);
        Stream<int[]> stream2 = Stream.of(new int[]{1, 2, 3}, new int[]{5, 7});
        IntStream intStream = IntStream.of(1, 2, 3, 5, 7);

        // Java 9 .ofNullable(), to stick to Java 8, just use
        Stream.ofNullable("hello").forEach(System.out::println);
        Stream<String> nullableStream = Stream.ofNullable(null); // empty stream
        String val = null;
        Stream<String> stream = val == null ? Stream.empty() : Stream.of("apple");

        /*
        IntStream is a stream of primitive int values
        where as Stream<Integer> is a stream of Integer objects -->  Because Integer is a reference type, both methods treat each element as an individual stream element
        note that Stream.of(pArr) is not same as Arrays.stream(pArr),
        as it wraps the whole array as a single object, so you get Stream<int[]> instead of IntStream.
        */
        Stream<int[]> stream3 = Stream.of(pArr); // not good for a primitive array and it's a single-element stream containing the whole array
        IntStream intStream2 = IntStream.of(pArr);
        IntStream intStream4 = Arrays.stream(pArr);
        Stream<int[]> stream4 = Stream.of(p2dArr);
        Stream<Integer> stream5 = Stream.of(npArr);
        Stream<List<Integer>> stream6 = Stream.of(list); //  This creates a stream with a single element — the whole list. You're not streaming over the list's elements; you're streaming over one item: the list itself.
        Stream<Integer> stream7 = list.stream();
        Stream<Character> characterStream = Stream.of('a', 'b', 'c'); // not good for a primitive array and it's a single-element stream containing the whole array>

        // IntStream range
        IntStream rangeStream = IntStream.range(1, 10); // 1 to 9
        IntStream rangeClosedStream = IntStream.rangeClosed(1, 10); // 1 to 10

        // builder
        Stream.Builder<Integer> streamBuilder = Stream.builder();
        streamBuilder.add(1);
        streamBuilder.add(2);
        streamBuilder.add(3);
        Stream<Integer> stream8 = streamBuilder.build();
        stream8.forEach(System.out::println);

        // Java 8 INFINITE STREAM using generate() and iterate() methods
        System.out.println("Stream.generate()");
        Stream<Integer> generatestream = Stream.generate(() -> 1).limit(5);
        generatestream.forEach(System.out::println);

        System.out.println("Stream.iterate()");
        Stream<Integer> iteratestream = Stream.iterate(1, i -> i + 1).limit(5);
        iteratestream.forEach(System.out::println);


        // limit() and skip()
        Stream<Integer> stream9 = Stream.of(1, 2, 3, 5, 7);
        stream9.limit(3).forEach(System.out::println); // 1,2,3
        stream9 = Stream.of(1, 2, 3, 5, 7); // --- note that stream can only be used once
        stream9.skip(3).forEach(System.out::println); // 5,7


        // map(), mapToInt(), mapToLong(), mapToDouble(), mapToObj() or boxed()
        Stream<Integer> mapStream1 = Stream.of(1, 2, 3, 5, 7);
        mapStream1.map(i -> i * 2).forEach(System.out::println); // 2,4,6,10,14
        IntStream mapStream2 = list.stream().mapToInt(i->i); // to ints
        LongStream mapStream3 = list.stream().mapToLong(i->i); // to longs
        DoubleStream mapStream4 = list.stream().mapToDouble(i->i); // to doubles
        Integer[] fooArr = Arrays.stream(pArr).mapToObj(i->i).toArray(Integer[]::new);
        Stream<Integer> mapStream6 = Arrays.stream(pArr).boxed();
        Stream<String> mapStream7 = Stream.of(1, 2, 3, 5, 7).map(i -> i + "");


        // map vs flatMap
        Stream<Integer> stream10 = Stream.of(1, 2, 3, 5, 7);
        stream10.map(i -> Arrays.asList(i, i * 2)).forEach(System.out::println); // [[1, 2], [2, 4], [3, 6], [5, 10], [7, 14]]
        Stream<List<Integer>> stream11 = Stream.of(Arrays.asList(1, 2), Arrays.asList(3, 5), Arrays.asList(7, 11));
        stream11.flatMap(Collection::stream).forEach(System.out::println); // [1, 2, 3, 5, 7, 11]
        Stream<Optional<Integer>> stream12 = Arrays.asList(Optional.of(1), Optional.of(2)).stream(); // or Stream.of(Optional.of(1), Optional.of(2));
        stream12.flatMap(Optional::stream).forEach(System.out::println); // [1, 2]

        // match methods
        Stream.of(1, 2, 3, 5, 7).allMatch(i -> i % 2 == 0); // true
        Stream.of(1, 2, 3, 5, 7).anyMatch(i -> i % 2 == 0); // true
        Stream.of(1, 2, 3, 5, 7).noneMatch(i -> i % 2 == 0); // false

        // filter()
        Stream.of(1, 2, 3, 5, 7).filter(i -> i % 2 == 0).forEach(System.out::println); // 2,4
        int findFirstFromIntStream = IntStream.of(1, 2, 3, 5, 7).filter(i -> i % 2 == 0).findFirst().getAsInt(); // handle OptionalInt
        int findFirstFromIntStream2 = IntStream.of(1, 2, 3, 5, 7).filter(i -> i % 2 == 0).findFirst().orElse(0);
        int findAnyFromStreamOfIntegers = Stream.of(1, 2, 3, 5, 7).filter(i -> i % 2 == 0).findFirst().get(); // handle Optional<Integer>
        int findAnyFromStreamOfIntegers2 = Stream.of(1, 2, 3, 5, 7).filter(i -> i % 2 == 0).findFirst().orElse(0);
        int findAnyFromStreamOfIntegers3 = Stream.of(1, 2, 3, 5, 7).filter(i -> i % 2 == 0).findFirst().orElseGet(()->0);

        // OptionalInt vs Optional<Integer> in Streams
        Stream<Integer> optionalIntInStream1 = Stream.of(1, 2, 3, 5, 7).filter(i -> i % 2 == 0);
        Stream<Integer> optionalIntInStream2 = Stream.of(1, 2, 3, 5, 7).filter(i -> i % 2 == 0);
        int findFirst = optionalIntInStream1.findFirst().isPresent() ? optionalIntInStream2.findFirst().get() : 0;


        // sum(), count(), max(), min(), findFirst(), findAny(), orElse()
        /*
          Note that sum(), count(), max(), min(), findFirst(), findAny(), orElse() are terminal operations
          they only work for IntStream only
         */
        int sum = IntStream.of(1, 2, 3, 5, 7).sum();
        int sum2 = Stream.of(1, 2, 3, 5, 7).mapToInt(i -> i).sum();
        long count = IntStream.of(1, 2, 3, 5, 7).count();
        int max = IntStream.of(1, 2, 3, 5, 7).max().orElse(0); // or .max().getAsInt(); handle OptionalInt
        int min = IntStream.of(1, 2, 3, 5, 7).min().orElse(0);
        int first = IntStream.of(1, 2, 3, 5, 7).findFirst().orElse(0);
        int last = IntStream.of(1, 2, 3, 5, 7).findAny().orElse(0);

        // Grouping with Collectors
        Stream<Integer> stream13 = Stream.of(1, 2, 3, 5, 7);
        Map<Integer, Integer> map = stream13.collect(Collectors.toMap(i -> i, i -> 1, Integer::sum));
        Stream<Integer> stream14 = Stream.of(1, 2, 3, 5, 7);
        Map<Integer, Integer> map2 = stream14.collect(Collectors.groupingBy(i -> i, Collectors.summingInt(i -> 1)));
        Stream<Integer> stream15 = Stream.of(1, 2, 3, 5, 7);
        Map<Integer, Long> map3 = stream15.collect(Collectors.groupingBy(i -> i, Collectors.counting()));
        Stream<String> stream16 = Stream.of("apple", "banana", "bat", "ant", "cat");
        Map<Character, List<String>> map4 = stream16.collect(Collectors.groupingBy(str -> str.charAt(0))); // {a=[apple, ant], b=[banana, bat], c=[cat]}

        // Partitioning for Boolean Conditions
        Stream<Integer> stream17 = Stream.of(1, 2, 3, 5, 7);
        Map<Boolean, List<Integer>> map5 = stream17.collect(Collectors.partitioningBy(i -> i % 2 == 0)); // {false=[1, 3, 5], true=[2, 7]}

        // Custom Collectors
        Collector<String, ?, String> joinWithPrefix = Collector.of(
            StringBuilder::new,
            (sb, s) -> sb.append("prefix-").append(s),
            StringBuilder::append,
            StringBuilder::toString
        );
        List<String> items = List.of("a", "b");
        String result = items.stream().collect(joinWithPrefix); // Output: prefix-aprefix-b


        // reduce()
        int sumUsingReduce = Stream.of(1, 2, 3, 5, 7).reduce(0, Integer::sum);
        int product = Stream.of(1, 2, 3, 5, 7).reduce(1, (a, b) -> a * b);
        int maxUsingReduce = Stream.of(1, 2, 3, 5, 7).reduce(Integer::max).get(); // use .get() if didn't specify "identity" / initial value
        int minUsingReduce = Stream.of(1, 2, 3, 5, 7).reduce(Integer::min).get();
        int countUsingReduce = Stream.of(1, 2, 3, 5, 7).reduce(0, (a, b) -> a + 1);


        // Java 9 takeWhile() and dropWhile() methods
        Stream<Integer> stream18 = Stream.of(1, 2, 3, 5, 7);
        stream18.takeWhile(n -> n < 3).forEach(System.out::println); // 1, 2
        stream18 = Stream.of(1, 2, 3, 5, 7);
        stream18.dropWhile(n -> n < 3).forEach(System.out::println); // 3, 5, 7
        // takeWhile() and dropWhile() are not available in Java 8. So, use the custom methods:
        List<Integer> takeWhile = takeWhile(Arrays.asList(1, 2, 3, 5, 7), n -> n < 3);
        takeWhile.forEach(System.out::println); // 1, 2
        List<Integer> dropWhile = dropWhile(Arrays.asList(1, 2, 3, 5, 7), n -> n < 3);
        dropWhile.forEach(System.out::println); // 3, 5, 7



        // stream vs parallel stream, parallel stream is faster but it's not thread safe
        Stream<Integer> stream19 = Stream.of(1, 2, 3, 5, 7);
        stream19.parallel().forEach(System.out::println); // [1, 2, 3, 5, 7]
        int parallelSum = list.parallelStream().reduce(0, Integer::sum);



        // Java 12 Teeing Collector for Multiple Reductions
        List<Integer> numbers = List.of(1, 2, 3, 4);
        String result2 = numbers.stream()
                               .collect(Collectors.teeing(
                                   Collectors.summingInt(Integer::intValue),
                                   Collectors.counting(),
                                   (totalSum, totalCount) -> "Sum: " + totalSum + ", Count: " + totalCount
                               )); // Output: Sum: 10, Count: 4



    }
    public static <T> List<T> takeWhile(List<T> list, Predicate<T> predicate) {
        List<T> result = new ArrayList<>();
        for (T item : list) {
            if (!predicate.test(item)) break;
            result.add(item);
        }
        return result;
    }

    public static <T> List<T> dropWhile(List<T> list, Predicate<T> predicate) {
        List<T> result = new ArrayList<>();
        boolean dropping = true;
        for (T item : list) {
            if (dropping && predicate.test(item)) {
                continue;
            }
            dropping = false;
            result.add(item);
        }
        return result;
    }
}