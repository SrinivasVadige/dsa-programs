package Algorithms.DisjointSetUnion;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
There is a tree (i.e. a connected, undirected graph with no cycles) consisting of n nodes numbered from 0 to n - 1 and exactly n - 1 edges.

You are given a 0-indexed integer array vals of length n where vals[i] denotes the value of the ith node. You are also given a 2D integer array edges where edges[i] = [ai, bi] denotes that there exists an undirected edge connecting nodes ai and bi.

A good path is a simple path that satisfies the following conditions:

The starting node and the ending node have the same value.
All nodes between the starting node and the ending node have values less than or equal to the starting node (i.e. the starting node's value should be the maximum value along the path).
Return the number of distinct good paths.

Note that a path and its reverse are counted as the same path. For example, 0 -> 1 is considered to be the same as 1 -> 0. A single node is also considered as a valid path.
 */
public class NumberOfGoodPaths {
    public static void main(String[] args) {
        int[] vals = {1,1,2,2,3};
        int[][] edges = {{0,1},{1,2},{2,3},{2,4}};
        System.out.println("numberOfGoodPaths(vals, edges) => " + numberOfGoodPaths(vals, edges));
        System.out.println("numberOfGoodPathsMyApproach(vals, edges) => " + numberOfGoodPathsMyApproach(vals, edges));
    }
    static int[] parent, count; // count array to keep track of number of nodes in each set
    static int res;
    public static int numberOfGoodPaths(int[] vals, int[][] edges) {
        // sort edges based on the maximum value of the nodes in the edge
        Arrays.sort(edges, (o1, o2) -> Integer.compare(Math.max(vals[o1[0]], vals[o1[1]]), Math.max(vals[o2[0]], vals[o2[1]])));
        int n = vals.length;
        res = n; // initialize number of good paths to be equal to number of nodes
        parent = new int[n];
        count = new int[n];

        Arrays.fill(count, 1); // initialize count of nodes in each set to be 1

        for(int i = 0; i < n; i++) parent[i] = i; // initialize parent of each node to be itself
        for(int[] edge: edges) {
            union(edge[0], edge[1], vals);
        }
        return res;
    }
    // function to merge two sets
    private static void union(int x, int y, int[] vals) {
        int px = find(x);
        int py = find(y);
        if(px == py) return;
        if(vals[px] == vals[py]) {
            // if the values of the parents of x and y are equal, update number of good paths
            res += count[px]*count[py];
            count[px] += count[py];
            parent[py] = px;
        }
        else if(vals[px] > vals[py]) {
            parent[py] = px;
        } else {
            parent[px] = py;
        }
    }
    public static int find(int i) {
        if (parent[i] != i) {
            parent[i] = find(parent[i]); // Path compression
        }
        return parent[i];
    }











    static int[] par, rank;
    @SuppressWarnings("unchecked")
    public static int numberOfGoodPaths2(int[] vals, int[][] edges) {
        int n = vals.length;
        par = new int[n];
        rank = new int[n];
        for (int i = 0; i < n; i++) {
            par[i] = i;
            rank[i] = 1;
        }

        // Group nodes by their values
        Map<Integer, List<Integer>> valueToNodes = new TreeMap<>();
        for (int i = 0; i < n; i++) {
            valueToNodes.computeIfAbsent(vals[i], _ -> new ArrayList<>()).add(i);
        }

        // Build adjacency list
        List<Integer>[] graph = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            graph[i] = new ArrayList<>();
        }
        for (int[] edge : edges) {
            graph[edge[0]].add(edge[1]);
            graph[edge[1]].add(edge[0]);
        }

        int goodPaths = 0;

        // Process nodes in increasing order of their values
        for (int value : valueToNodes.keySet()) {
            List<Integer> nodes = valueToNodes.get(value);

            // Union nodes within the same connected component
            for (int node : nodes) {
                for (int neighbor : graph[node]) {
                    if (vals[neighbor] <= value) {
                        union2(node, neighbor);
                    }
                }
            }

            // Count good paths for the current value
            Map<Integer, Integer> componentSize = new HashMap<>();
            for (int node : nodes) {
                int root = find(node);
                componentSize.put(root, componentSize.getOrDefault(root, 0) + 1);
            }

            for (int size : componentSize.values()) {
                goodPaths += size * (size + 1) / 2; // Add combinations of nodes in the same component
            }
        }

        return goodPaths;
    }

    public static void union2(int i, int j) {
        int rootI = find2(i);
        int rootJ = find2(j);
        if (rootI != rootJ) {
            if (rank[rootI] > rank[rootJ]) {
                par[rootJ] = rootI;
            } else if (rank[rootI] < rank[rootJ]) {
                par[rootI] = rootJ;
            } else {
                par[rootJ] = rootI;
                rank[rootI]++;
            }
        }
    }

    public static int find2(int i) {
        if (par[i] != i) {
            par[i] = find2(par[i]); // Path compression
        }
        return par[i];
    }














    /**
     * WORKING FINE BUT TLE
     *
        PATTERNS:
        ---------
        1) First form disjoint set using UnionFind
        2) Now trav from each node to the root parent with these 2 conditions
        childNum >= parent -- loop
        when childNum == parent ----> count++
        till childNum < parent
        3) Maintain a memo to see which nodes count doesn't change to improve performance


        // trav from END NODE to START NODE

        [0, 1, 2, 3, 4, 5, 6, 7, 8, 9] -- i
        [0, 0, 1, 2, 3, 3, 5, 1, 4, 7] -- par
        [2, 5, 5, 1, 5, 2, 3, 5, 1, 5] -- vals

        Here, in these scenarios, we need to trav parent and as well parent's children as well ---> USE DFS
        But note that don't trav same child in parent's children recursion

     */
    static List<Integer>[] adjList;
    static int c;
    static int[] valsArr;
    static Set<String> set = new HashSet<>();
    @SuppressWarnings("unchecked")
    public static int numberOfGoodPathsMyApproach(int[] vals, int[][] edges) {
        int n = vals.length;
        valsArr=vals;
        // prepare tree
        par=new int[n];
        rank=new int[n];
        adjList = new List[n];
        for (int i=0; i<n; i++) {
            par[i]=i;
            adjList[i]=new ArrayList<>();
        }

        // System.out.println(Arrays.toString(vals));
        // System.out.println(Arrays.toString(par));
        for(int[] e: edges) {
            union(Math.min(e[0], e[1]), Math.max(e[0],e[1]));
        }
        // System.out.println(Arrays.toString(par));
        // System.out.println(Arrays.toString(adjList));

        Map<Integer, List<Integer>> map = new HashMap<>(); // TO TRAV ONLY ELIGIBLE NODES
        for (int i=0; i<n; i++) map.computeIfAbsent(vals[i], _->new ArrayList<>()).add(i);
        c=0;
        for (int key: map.keySet()) {
            List<Integer> val = map.get(key);
            if(val.size()>1) {
                for (int i:val) {
                    if (i==par[i]) continue; // skip root
                    dfs(i, vals[i], i, true);
                    // System.out.printf("i:%s, c:%s%n", i, c);
                }
            }
        }
        // for (int i=0; i<n; i++) {
        //     if (i==par[i]) continue;
        //     dfs(i, vals[i], i, true);
            // System.out.printf("i:%s, c:%s%n", i, c);
        // }
        return c+n;

    }

    // trav only parents and siblings. DON'T TRAV CHILDREN
    private static void dfs (int endI, int endNum, int parChildI, boolean isMain) {
        // System.out.printf("endI:%s, endNum:%s, parChildI:%s%n", endI, endNum, parChildI);
        int pi = parChildI;
        while (pi != par[pi]) {
            if (isMain) // endI == parChildI
                pi=par[pi];

            if (endNum < valsArr[pi]) break;

            if (endNum == valsArr[pi]) {
                if (endI == pi) return; // AFTER RECURSION, IF THE PARENT CHOOSE SAME endI NODE
                String key = Math.min(endI, pi) + "," +Math.max(endI, pi);
                if (set.add(key)) c++;
                else return; // IF ALREADY CALCULATED
            }

            // parent children
            if (adjList[pi].size() == 0) return;
            for (int child: adjList[pi]) {
                if (child==parChildI || endNum < valsArr[child]) continue;
                dfs(endI, endNum, child, false);
            }
            // pi == parChildI means pi completed it's above child for loop
            if (pi == parChildI) return;
        }
    }



    private static void union(int a, int b){
        int pa = findMy(a);
        int pb = findMy(b);
        if (pa==pb) return;
        par[b]=a;
        adjList[a].add(b);

        // if (rank[b] < rank[a]) par[b]=a;
        // else if (rank[a] < rank[b]) par[a]=b;
        // else {
            // par[b]=a;
            // rank[a]++;
        // }
    }
    private static int findMy(int i) {
        while(i != par[i]) i=par[i];
        return i;
    }












    public int numberOfGoodPathsMyApproachOld(int[] vals, int[][] edges) {
        int n = vals.length;
        // prepare tree
        par=new int[n];
        rank=new int[n];
        for (int i=0; i<n; i++) {
            par[i]=i;
        }

        System.out.println(Arrays.toString(par));
        for(int[] e: edges) {
            union(Math.min(e[0], e[1]), Math.max(e[0],e[1]));
        }
        System.out.println(Arrays.toString(par));
        System.out.println(Arrays.toString(vals));

        int c=n;
        Set<Integer> skip = new HashSet<>();
        // trav each node
        /**

        [0, 1, 2, 3, 4, 5, 6, 7, 8, 9] -- i
        [0, 0, 1, 2, 3, 3, 5, 1, 4, 7] -- par
        [2, 5, 5, 1, 5, 2, 3, 5, 1, 5] -- vals

        Here, in these scenarios, we need to trav parent and as well parent's children as well

         */
        for (int i=0; i<n; i++) {
            if (i==par[i] || skip.contains(i)) continue;

            int num = vals[i];

            int pi = i;
            while (pi != par[pi]) {
                pi=par[pi];

                if (num > vals[pi]) {
                    // skip.add(pi);
                    break;
                }

                if (num == vals[pi]) c++;
            }
            System.out.printf("i:%s, c:%s%n", i, c);
        }
        return c;

    }
}
