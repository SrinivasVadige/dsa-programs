package Algorithms.Matrix;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * At Audible, a subscriber can gift an audio book from his/her library to any other non-subscriber to kick start their audio book journey. The first time subscriber can receive up to a maximum of N audio books from their friends/relatives. When a non-subscriber receives an audio book, we can infer that the two may be related. Similarly, if the non-subscriber receives gifted books from two other subscribers, we can infer that all of them are related and the three of them form a group. More formally, a group is composed of all of the people who know one another, whether directly or transitively. Audible would like your help finding out the number of such distinct groups from the input data.
 *
 * Consider the following input matrix M.
 * n=2
 * 110
 * 110
 * 001
 *
 * Every row corresponds to a subscriber and the value M[i,j] determines if it was gifted a book by i. In the above example, user 0 has gifted a book to user 1 and so they are connected while person 2 has not received a book from anyone or gifted book to anyone. Therefore, there are 2 groups. • j (Each of the people is known to self) Determine the number of groups represented in a matrix. Note: The method signatures may vary slightly depending on the requirements of the chosen language. For example, C language will have an argument that represents the number of rows and columns in the matrix. Also, Java will receive a list rather than an array.
 *
 * Function Description:
 * Complete the function countGroups in the editor below.
 * countGroups has the following parameter(s):
 * int related[n]: an array of strings of binary digits related[i] that represent connections of people
 * Returns: - int: an integer that represents the number of groups of people
 * Constraints
 * 1 <= n <= 100
 * 0 <= i <= n
 * Each related[i] contains a binary string of n zeros and ones. related is a square matrix.
 *
 * Input from stdin will be processed as follows and passed to the function. The first line contains an integer n, the size of the square association matrix, related.
 * The next n lines each contain a binary string of length n that represents a row in the matrix, 0 s i < n.
 *
 * Sample Input - 1
 * STDIN                       Function
 * -----                       --------
 * 4                   -------> size of the related[]
 * 1100
 * 1110
 * 0110
 * 0001
 *
 * ans: 2
 *
 *
 * Sample Input - 2
 * STDIN
 * -----
 * 10000
 * 10000
 * 00100
 * 00010
 * 00001
 *
 * ANS: 5
 *
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 23 Nov 2024
 */
public class GiftingGroups {
    public static void main(String[] args) {
        List<String> related = List.of("1100", "1110", "0110", "0001");
        // List<String> related = List.of("10000", "01000", "00100", "00010", "00001");
        System.out.println("countGroups => ");
        System.out.println(countGroups(related));
        System.out.println("countGroupsMyApproach => ");
        System.out.println(countGroupsMyApproach(related));
        System.out.println("countGroups2 => ");
        System.out.println(countGroups2(related));
    }

    public static int countGroups(List<String> related) {
        HashMap<Integer, List<Integer>> relMap = new HashMap<>(); // key == person and val == group
        for(int i=0; i<related.size(); i++) {
            relMap.put(i, new ArrayList<>());
            for(int j=0; j<related.size(); j++) {
                if (related.get(i).charAt(j) == '1') // is related?
                    relMap.get(i).add(j);
            }
        }

        System.out.println(relMap);

        HashSet<Integer> visited = new HashSet<>();

        int groupCount = 0;
        for(int node: relMap.keySet()){
            if(!visited.contains(node)){
                dfs(relMap, visited, node);
                groupCount++;
            }
        }

        return groupCount;
    }
    private static void dfs(HashMap<Integer, List<Integer>> relMap, HashSet<Integer> visited, int node){
        visited.add(node);
        for(int neighbor: relMap.get(node)) { // all nodes are already present in graph
            if (!visited.contains(neighbor))
                dfs(relMap, visited, neighbor);
        }
    }

    /**
     * Clubbing all peoples into groups
     */
    public static int countGroupsMyApproach(List<String> related) {

        HashMap<Integer, Set<Integer>> relMap = new HashMap<>(); // key == person and val == group
        for(int i=0; i<related.size(); i++) {
            relMap.put(i, new HashSet<>());
            for(int j=0; j<related.size(); j++) {
                if (related.get(i).charAt(j) == '1') { // is related?
                    relMap.get(i).add(j);
                    // or
                    // Set<Integer> set = map.get(i);
                    // set.add(j);
                    // map.put(i, set); ---> no needed, as we manipulate the key value's reference i.e map.get(i)
                }
            }
        }

        System.out.println(relMap);

        HashMap<Integer, Set<Integer>> grpMap = new HashMap<>(relMap); // clone it and if I manipulate same 'map' HashMap then we'll get java.util.ConcurrentModificationException. note that HashMap.clone() returns Object

        for (Map.Entry<Integer, Set<Integer>> entry: relMap.entrySet()) {
            for (int person: entry.getValue()) {
                if (person != entry.getKey()) {
                    Set<Integer> set = new HashSet<>(entry.getValue()); // don't modify relMap values, just take a copy and add
                    set.addAll(grpMap.get(person));
                    grpMap.put(entry.getKey(), set);
                }
            }
        }

        return grpMap.values().stream().collect(Collectors.groupingBy(i->i, Collectors.counting())).size();
    }

    public static int countGroups2(List<String> related) {
        HashMap<Integer, List<Integer>> graph = new HashMap<>();
        for(int i = 0; i < related.size(); i++){
            String relations = related.get(i);
            for(int j = 0; j < relations.length(); j++){
                char isRelated = relations.charAt(j);
                //the two nodes have relation
                if(isRelated == '1'){
                    graph.putIfAbsent(i, new LinkedList<>());
                    graph.putIfAbsent(j, new LinkedList<>());
                    graph.get(i).add(j);
                    graph.get(j).add(i);
                }
            }
        }

        System.out.println(graph);

        //DFS search if graph contains a loop, in each loop we add one to our groupCount
        HashSet<Integer> visited = new HashSet<>();

        int groupCount = 0;
        for(int node: graph.keySet()){
            if(!visited.contains(node)){
                dfs(graph, visited, node);
                groupCount++;
            }
        }

        return groupCount;
    }
}
