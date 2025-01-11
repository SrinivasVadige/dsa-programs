package DataStructures;

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
.of(ele) or .of(eles) or .of(arr or lst)
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

Java 9 methods
.ofNullable(ele) --- From Stream class
.takeWhile(Predicate)
.dropWhile(Predicate)
.iterate(seed, UnaryOperator)
.iterate​(T seed, Predicate<? super T> hasNext, UnaryOperator<T> next)

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

</pre>
* @author Srinvas Vadige, srinivas.vadige@gmail.com
* @since 21 Sept 2024
*/
public class StreamApiExample {

    public static void main(String[] args) {

    }
}