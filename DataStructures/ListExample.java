package DataStructures;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Collections;
import java.util.stream.Collectors;

/**
* @author Srinvas Vadige, srinivas.vadige@gmail.com
* @since 21 Sept 2024
*/
public class ListExample {

    @SuppressWarnings({ "unused", "removal" })
    public static void main(String[] args) {

        System.out.println("DECLARE & INITIALIZE LIST ---------- ");
        List<Integer> list1 = List.of(1,2); //=> immutable just use it for looping i.e acts as tuples in python
        List<Integer> list2 = new ArrayList<>( List.of(1,2,3) );
        List<Integer> list3 = Arrays.asList(1,2,3);
        List<Integer> list4 = new ArrayList<>( Arrays.asList(1,2,3) );
        List<Integer> list5 = new ArrayList<>();
        list5.add(1);
        list5.add(0, 100);
        list5.set(0, 200);
        int[] intArr = {1,2,3};
        List<Integer> list = Arrays.stream(intArr).boxed().toList();
        Integer[] integerArr = {1,2,3};
        list = Arrays.stream(integerArr).toList(); // boxed() is not required as Integer is already a wrapper
        list = Arrays.stream(integerArr).collect(Collectors.toList()); // are IMMUTABLE. So, use only Stream.collect(Collectors.toList()) for mutable list.
        // colors list
        List<String> colors = Arrays.asList("red", "blue", "green");





        System.out.println("1) LIST STREAM FILTER ---------- ");
        ArrayList<String> filteredColors = (ArrayList<String>) colors.stream().filter(x -> x.contains("o")).collect(Collectors.toList());
        // or
        filteredColors.removeIf(x -> !x.contains("o"));




        System.out.println("2) CLONE ---------- "); // and remove unwanted(TRADITIONAL FILTERING)
        filteredColors = new ArrayList<>(colors); // note that filteredColors = (ArrayList<String>) colors.clone(); ---- won't work, as we don't have clone() method in Java Lis
        //or
        filteredColors = new ArrayList<>();
        filteredColors.addAll(colors);
        //or
        filteredColors = (ArrayList<String>) colors.stream().collect(Collectors.toList()); // (ArrayList<String>) casting because of ArrayList<String> filteredColors. So, always use List<> interface as type
        // or
        filteredColors.removeIf(x -> !x.contains("o"));
        // or com.google.gson.Gson
        // or for loop
        // or custom class implements Cloneable




        System.out.println("3) FIND ELEMENT ---------- ");
        colors.stream().filter(x -> x.contains("ora")).findFirst().get();




        System.out.println("4) IMMUTABLE LISTS ---------- ");
        Arrays.asList(1, 2);
        //or
        List.of(1,2);
        //or
        Collections.unmodifiableList(Arrays.asList(1,2));
        // or
        Arrays.stream(integerArr).toList();





        System.out.println("5) IMMUTABLE TO MUTABLE LIST---------- ");
        new ArrayList<>(Arrays.asList());
        // or
        new ArrayList<>(List.of()); // to make them as mutable.
        // or
        Arrays.stream(intArr).boxed().collect( Collectors.toList()); // => to convert to List
        // or
        Arrays.stream(intArr).boxed().collect( Collectors.toCollection(ArrayList::new) ); //=> to ArrayList




        System.out.println("6) LIST TO ARRAY ---------- ");
        String[] arr = {"foo", "bar"};
        List<String> lst = new ArrayList<>(Arrays.asList(arr));
        lst.toArray(); // => returns Object[] not String[].
        arr = lst.toArray(String[]::new);




        System.out.println("7) REMOVE ELE FROM INTEGER LIST ---------- ");
        // .remove() method’s param takes Object and as well as index. So, what happens in List<Integer> ???
        // By default in List<Integer> list.remove(2) will remove the ele at index 2. So in order to remove the 2 from the list use below options:
        list.remove(new Integer(2));
        list.remove(Integer.valueOf(2));
        list.remove((Integer) 2);
        list.remove(list.indexOf(2));
        list.removeIf(element -> element == 2);
        list.removeAll(Arrays.asList(2)); // removes all occurrences of 2
    }
}

/*

ALL LIST METHODS
=================
import from Java.util.List, ArrayList, LinkedList;
Always define type as List and initialize with ArrayList<>()
.add(ele)
.addAll(list)
.addAll(index, list)
.add(index, ele)
.set(index, ele)
.get(index)
.remove(index) or .remove(ele)
.removeAll(list)
.removeIf( -> )
.indexOf(ele)
.lastIndexOf(ele)
.contains(ele) or .contains(list)
.clear()
.clone()
.size()
.isEmpty()
.equals(list)
.toArray() or toArray(EleDataType[])
.forEach()
.stream()
.parallelStream()
.hashCode()
.iterator()
.listIterator()
.spliterator()
.subList(fromIndex, toIndex)
.replaceAll(UnaryOperator<E> operator)
.retainAll(list)
.sort(Comparator<super E> c)
 */



