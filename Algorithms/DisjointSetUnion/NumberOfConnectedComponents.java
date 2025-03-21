package Algorithms.DisjointSetUnion;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Collections;

/**
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 21 March 2025
 *
 * Number of Connected Components in an Undirected Graph - Union Find - Leetcode 323
 */
public class NumberOfConnectedComponents {
    private static int[] parent;
    private static int[] rank;
    public static void main(String[] args) {
        int n = 12;
        int[][] edges = {{0,1},{1,2},{2,3},{3,0},{4,5},{6,7},{7,4},{8,9},{10,11}};
        System.out.println("countComponents(n, edges) => " + countComponents(n, edges)); // 2
        System.out.println("countComponentsMyApproach(n, edges) => " + countComponentsMyApproach(n, edges)); // 2
        System.out.println("countComponentsUsingListOfSets(edges, n) => " + countComponentsUsingListOfSets(edges, n)); // 2
    }

    public static int countComponents(int n, int[][] edges) {
        parent = new int[n];
        rank = new int[n];
        for (int i = 0; i < n; i++) parent[i] = i; // or -1
        for (int[] edge : edges) union(edge[0], edge[1]);
        return numComponents();
    }
    public static void union(int x, int y) {
        int rootX = find(x);
        int rootY = find(y);
        if (rootX == rootY) return;
        // compare roots and update roots, not x and y
        if (rank[rootX] < rank[rootY]) parent[rootX] = rootY;
        else if (rank[rootX] > rank[rootY]) parent[rootY] = rootX;
        else {
            parent[rootY] = rootX;
            rank[rootX]++;
        }
    }
    public static int find(int x) {
        if (parent[x] != x) parent[x] = find(parent[x]);
        return parent[x];
    }
    public static int numComponents() {
        int count = 0;
        for (int i = 0; i < parent.length; i++) if (parent[i] == i) count++;
        return count;
    }







    /**
     * Same as above countComponents() but here initializing parent with -1 and return count of number -1's in parent
     */
    public static int countComponentsMyApproach(int n, int[][] edges) {
        parent=new int[n];
        rank=new int[n];
        for(int i=0; i<n; i++) parent[i]=-1;
        for (int[] e: edges) unionMyApproach(e[0],e[1]);
        return (int) Arrays.stream(parent).filter(p -> p == -1).count();
        // or return set.size(); by using Set<Integer> set = new HashSet<>(); for (int i=0; i<n; i++) set.add(findMyApproach(i)); set.size();
    }
    private static void unionMyApproach(int a, int b){
        int rootA = findMyApproach(a);
        int rootB = findMyApproach(b);
        if (rootA != rootB) {
            // compare roots in rank and update only roots, not a and b
            if (rank[rootA] < rank[rootB]) parent[rootA] = rootB;
            else if (rank[rootA] > rank[rootB]) parent[rootB] = rootA;
            else {
                parent[rootB] = rootA;
                rank[rootA]++;
            }
        }
    }
    private static int findMyApproach(int i) {
        if (parent[i]==-1) return i;
        return findMyApproach(parent[i]);
    }



    /**
     * WORKING
     *
     * But consider this case as well
     * n=96
     * edges=[[6,27],[83,9],[10,95],[48,67],[5,71],[18,72],[7,10]]
     * Expected output: 89
     */
    public static int countComponentsUsingListOfSets(int[][] edges, int n) {
        List<Set<Integer>> listOfSets = new ArrayList<>();

        for (int[] edge : edges) {
            int u = edge[0];
            int v = edge[1];
            boolean added = false;

            // Check if either u or v is already in an existing set
            for (Set<Integer> set : listOfSets) {
                if (set.contains(u) || set.contains(v)) {
                    set.add(u);
                    set.add(v);
                    added = true;
                    break;
                }
            }

            // If neither u nor v is in any set, create a new set
            if (!added) {
                Set<Integer> newSet = new HashSet<>();
                newSet.add(u);
                newSet.add(v);
                listOfSets.add(newSet);
            }
        }

        // Merge overlapping sets
        boolean merged;
        do {
            merged = false;
            for (int i = 0; i < listOfSets.size(); i++) {
                for (int j = i + 1; j < listOfSets.size(); j++) {
                    Set<Integer> set1 = listOfSets.get(i);
                    Set<Integer> set2 = listOfSets.get(j);

                    // Check if the two sets overlap
                    if (!Collections.disjoint(set1, set2)) {
                        set1.addAll(set2);
                        listOfSets.remove(j);
                        merged = true;
                        break;
                    }
                }
                if (merged) break;
            }
        } while (merged);


        int notFoundCount = 0; // some nodes may not be in any set
        for(int i=0; i<n; i++) {
            boolean found = false;
            for (Set<Integer> set : listOfSets) {
                if (set.contains(i)) {
                    found = true;
                    break;
                }
            }
            if (!found) notFoundCount++;
        }
        return listOfSets.size() + notFoundCount;
    }






    /**
     * INTERVALS WON'T WORK FOR DISJOINT SET UNION
     *
     * Eg:
     * n = 96
     * edges=[[6,27],[83,9],[10,95],[48,67],[5,71],[18,72],[7,10]]
     *
     * Here after sort we get
     * [5, 71] [6, 27] [7, 10] [9, 83] [10, 95] [18, 72] [48, 67]
     * this will form a final intervals list => [[5,95]]
     */
    public static int countComponentsUsingIntervals(int n, int[][] edges) {
        for (int i=0; i<edges.length; i++) {
            if (edges[i][0] > edges[i][1]) {
                int t = edges[i][0];
                edges[i][0] = edges[i][1];
                edges[i][1] = t;
            }
        }
        Arrays.sort(edges, (a, b) -> a[0] - b[0]);

        int s = edges[0][0], e = edges[0][1];
        int i = 1;
        List<List<Integer>> lst = new ArrayList<>();
        while (i <= edges.length) {
            while(i<edges.length && s <= edges[i][0] && e >= edges[i][1]) {
                e = Math.max(e, edges[i][1]);
                i++;
            }
            lst.add(new ArrayList<>(Arrays.asList(s, e)));

            if (i < edges.length) {
                if(e >= edges[i][0]) {
                    e = Math.max(e, edges[i][1]);
                    if (lst.size() > 0) lst.remove(lst.size()-1);
                } else {
                    s = edges[i][0];
                    e = edges[i][1];
                    if (i == edges.length) lst.add(new ArrayList<>(Arrays.asList(s, e)));
                }
            }
            i++;
        }
        return lst.size();
    }
}
