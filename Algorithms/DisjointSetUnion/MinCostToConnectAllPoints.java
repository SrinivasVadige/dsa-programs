package Algorithms.DisjointSetUnion;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.TreeMap;

/**
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 28 March 2025
 *
 * Similar to Minimum Spanning Tree problem MST
 *
 * Popular Algorithms for Finding Minimum Spanning Tree MST:
 * 1. Prim’s Algorithm (Greedy, uses Priority Queue) and
 * 2. Kruskal's Algorithm (Greedy, uses Union-Find)
 */
public class MinCostToConnectAllPoints {
    public static void main(String[] args) {
        int[][] points = {{0, 0}, {2, 2}, {3, 10}, {5, 2}, {7, 0}};
        System.out.println("Min cost My Approach: " + minCostConnectPointsKruskalsMyApproach(points));
        System.out.println( "Min cost Kruskals MST: " + minCostConnectPointsKruskalsMST(points));
        System.out.println( "Min cost Prim's MST AdjLst: " + minCostConnectPointsPrimsAdjList(points));

    }
    /**
        PATTERNS:
        ---------
        1) Kruskal's MST algo
        2) Sort all edges by weight, with all possible combinations
        3) Now trav the weights in asc order and add non-cyclic edges until we connected all nodes i.e numOfComps == 1
        4) Consider i index in points as node.
     */
    public static int minCostConnectPointsKruskalsMyApproach(int[][] points) {
        int n = points.length;
        Map<Integer, List<int[]>> map = new TreeMap<>(); // sorted order
        for (int i=0; i<n; i++) {
            for (int j=i+1; j<n; j++) {
                int weight = dist(points[i], points[j]);
                map.computeIfAbsent(weight, _-> new ArrayList<>()).add(new int[]{i,j});
            }
        }

        par = new int[n];
        rank = new int[n];
        for(int i=0; i<n; i++) par[i]=i;
        int count=0, comps=n;

        main:
        for(int i: map.keySet()) {
            List<int[]> edges = map.get(i);
            for(int[] edge: edges) {
                if (union(edge[0], edge[1])) {
                    count+=i;
                    if (comps-- == 1) break main;
                }
            }
        }

        return count;
    }
    static int[] par, rank;
    private static boolean union(int a, int b) {
        int pa = find(a);
        int pb = find(b);

        if (pa == pb) return false;

        if (rank[pb] < rank[pa]) par[pb]=pa;
        else if(rank[pa] < rank[pb]) par[pa]=pb;
        else {
            par[pb]=pa;
            rank[pa]++;
        }
        return true;
    }
    private static int find(int i){
        if (i != par[i]) {
            i = par[i] = find(par[i]);
        }
        return i;
    }
    private static int dist(int[] a, int[] b) {
        return Math.abs(a[0] - b[0]) + Math.abs(a[1] - b[1]);
    }














    public int minCostConnectPointsKruskalsMyApproach2(int[][] points) {
        Map<Integer, Set<String> > map = new TreeMap<>();
        for (int[] i: points) {
            for (int[] j: points) {
                if (i.equals(j)) continue;
                int weight = dist2(i, j);
                String edge = edge2(i,j);
                String reverseEdge = edge2(j,i);
                map.putIfAbsent(weight, new HashSet<>());

                if (!map.get(weight).contains(reverseEdge))
                    map.get(weight).add(edge);
            }
        }

        UnionFind2 uf = new UnionFind2(points);
        int c=0;
        main:
        for(int i: map.keySet()) {
            List<String> lst = new ArrayList<>(map.get(i));
            for(String s: lst) {
                String[] sArr = s.split(",");
                String a = sArr[0] + "," + sArr[1];
                String b = sArr[2] + "," + sArr[3];
                if (uf.union(a,b)) {
                    c+=i;
                    if (uf.comps == 1) break main;
                }
            }
        }

        return c;
    }
    private int dist2(int[] a, int[] b) {
        return Math.abs(a[0] - b[0]) + Math.abs(a[1] - b[1]);
    }
    private String edge2(int[] a, int[] b) {
        return String.format("%s,%s,%s,%s",a[0],a[1],b[0],b[1]);
    }
    class UnionFind2 {
        Map<String, String> par = new HashMap<>();
        Map<String, Integer> rank = new HashMap<>();
        int comps;

        UnionFind2 (int[][] points) {
            for (int[] p: points) {
                String s = p[0] +","+ p[1];
                par.put(s,s);
                rank.put(s,0);
            }
            comps=points.length;
        }

        private boolean union(String a, String b) {
            String pa = find(a);
            String pb = find(b);

            if (pa.equals(pb)) return false;

            if (rank.get(pb) < rank.get(pa)) par.put(pb, pa);
            else if(rank.get(pa) < rank.get(pb)) par.put(pa, pb);
            else {
                par.put(pb, pa);
                rank.put(pa, rank.get(pa)+1);
            }
            comps--;
            return true;
        }
        private String find(String key){
            if (!key.equals(par.get(key))) {
                par.put(key, find(par.get(key))); // Path compression
            }
            return par.get(key);
        }
    }












    public static int minCostConnectPointsKruskalsMST(int[][] points) {
        int n = points.length;
        List<int[]> edges = new ArrayList<>(); // [weight, node1, node2]

        // Create all possible edges and calculate their Manhattan distance
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                int weight = Math.abs(points[i][0] - points[j][0]) + Math.abs(points[i][1] - points[j][1]);
                edges.add(new int[]{weight, i, j});
            }
        }

        // Sort edges by weight
        Collections.sort(edges, Comparator.comparingInt(a -> a[0]));

        // Use Kruskal's algorithm to calculate the MST cost
        return kruskalsMST(edges, n);
    }


    public static int kruskalsMST(List<int[]> edges, int n) {
        int[] parent = new int[n];
        int[] rank = new int[n];
        for (int i = 0; i < n; i++) {
            parent[i] = i;
            rank[i] = 1;
        }

        int cost = 0;
        int edgesUsed = 0;

        for (int[] edge : edges) {
            int weight = edge[0], u = edge[1], v = edge[2];
            if (union(parent, rank, u, v)) { // If union is successful (no cycle)
                cost += weight;
                edgesUsed++;
                if (edgesUsed == n - 1) break; // MST is complete
            }
        }

        return cost;
    }

    private static boolean union(int[] parent, int[] rank, int u, int v) {
        int rootU = find(parent, u);
        int rootV = find(parent, v);

        if (rootU == rootV) return false; // Already connected

        if (rank[rootU] > rank[rootV]) {
            parent[rootV] = rootU;
        } else if (rank[rootU] < rank[rootV]) {
            parent[rootU] = rootV;
        } else {
            parent[rootV] = rootU;
            rank[rootU]++;
        }

        return true;
    }

    private static int find(int[] parent, int x) {
        if (parent[x] != x) {
            parent[x] = find(parent, parent[x]); // Path compression
        }
        return parent[x];
    }












    public static int minCostConnectPointsKruskalsMST2(int[][] points) {
        int n = points.length;
        List<int[]> edges = new ArrayList<>(); // [weight, node1, node2]

        // Create all possible edges and calculate their Manhattan distance
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                int weight = Math.abs(points[i][0] - points[j][0]) + Math.abs(points[i][1] - points[j][1]);
                edges.add(new int[]{weight, i, j});
            }
        }

        // Sort edges by weight
        Collections.sort(edges, Comparator.comparingInt(a -> a[0]));

        // Union-Find (Disjoint Set) to detect cycles
        UnionFind uf = new UnionFind(n);
        int mstCost = 0;
        int edgesUsed = 0;

        for (int[] edge : edges) {
            int weight = edge[0], u = edge[1], v = edge[2];
            if (uf.union(u, v)) { // If union is successful (no cycle)
                mstCost += weight;
                edgesUsed++;
                if (edgesUsed == n - 1) break; // MST is complete
            }
        }
        return mstCost;
    }
    static class UnionFind {
        private int[] parent, rank;

        public UnionFind(int n) {
            parent = new int[n];
            rank = new int[n];
            for (int i = 0; i < n; i++) parent[i] = i;
        }

        public int find(int x) {
            if (parent[x] != x) parent[x] = find(parent[x]); // Path compression
            return parent[x];
        }

        public boolean union(int x, int y) {
            int rootX = find(x), rootY = find(y);
            if (rootX == rootY) return false; // Already connected
            if (rank[rootX] > rank[rootY]) parent[rootY] = rootX;
            else if (rank[rootX] < rank[rootY]) parent[rootX] = rootY;
            else {
                parent[rootY] = rootX;
                rank[rootX]++;
            }
            return true;
        }
    }










    /**
     * Prim's Algorithm using min-heap or Frontier
     *
     * @TimeComplexity: O(n^2 * log(n))
     * @SpaceComplexity: O(n)
     *
     * Here, except the root node, each node must be child for one node but it can be parent for multiple nodes
     * So, mark this child as visited using array[n] or Set
     */
    @SuppressWarnings({"unchecked"})
    public static int minCostConnectPointsPrimsAdjList(int[][] points) {
        int n = points.length;
        List<int[]>[] adjList = new List[n]; // List[] will throws ==> List is a raw type. References to generic type List<E> should be parameterized
        for(int i=0; i<n; i++) adjList[i]=new ArrayList<>();

        // each node is connected to all other nodes, just like graph
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                int weight = Math.abs(points[i][0] - points[j][0]) + Math.abs(points[i][1] - points[j][1]);
                adjList[i].add(new int[]{j, weight});
                adjList[j].add(new int[]{i, weight});
            }
        }

        Set<Integer> visited = new HashSet<>(); // To track visited nodes in the MST
        int mstCost = 0;
        PriorityQueue<int[]> frontier = new PriorityQueue<>(Comparator.comparingInt(a -> a[1])); // Min-Heap: [node, weight]
        frontier.add(new int[]{0, 0}); // Start from node 0 with cost 0 by considering it as root

        while (visited.size() < n) {
            int[] curr = frontier.poll();
            int i = curr[0], weight = curr[1];

            if (visited.contains(i)) continue; // Skip if already visited
            visited.add(i); // Mark node as visited
            mstCost += weight; // Add weight to MST cost

            // Add all "i" node edges
            for (int[] edge : adjList[i]) {
                int v = edge[0], edgeWeight = edge[1];
                if (!visited.contains(v)) frontier.add(new int[]{v, edgeWeight});
            }
        }

        return mstCost;
    }














    public int minCostConnectPointsPrimsPQ(int[][] points) {
        int n = points.length;
        boolean[] visited = new boolean[n]; // To track visited nodes
        // Min-Heap: [node, weight]
        PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparingInt(a -> a[1]));
        pq.add(new int[]{0, 0}); // Start from node 0 with cost 0 by considering it as root
        // if you want you can also save {weight, par, child}

        int mstCost = 0;
        int edgesUsed = 0; // above method have visitedSet which replaces both visitedArray and edgesUsed
        while (!pq.isEmpty() && edgesUsed < n) { // && edgesUsed < n ---> is optional but it improves performance
            int[] curr = pq.poll();
            int u = curr[0], weight = curr[1];

            if (visited[u]) continue;
            visited[u] = true;
            mstCost += weight;
            edgesUsed++;

            // U children
            // Add all "u" node edges / children (but skip "v" node that is already) in PQ
            for (int v = 0; v < n; v++) {
                if (!visited[v]) {
                    int dist = Math.abs(points[u][0] - points[v][0]) + Math.abs(points[u][1] - points[v][1]);
                    pq.add(new int[]{v, dist});
                }
            }
        }
        return mstCost;
    }














    public int minCostConnectPointsPrimsArray(int[][] points) {
        int n = points.length;
        int[] key = new int[n]; // Stores minimum cost to reach each node
        Arrays.fill(key, Integer.MAX_VALUE);
        key[0] = 0; // Start with node 0
        boolean[] visited = new boolean[n]; // Tracks visited nodes
        int mstCost = 0;

        for (int i = 0; i < n; i++) {
            int u = -1;

            // Find the minimum key node not yet included in MST
            for (int j = 0; j < n; j++) {
                if (!visited[j] && (u == -1 || key[j] < key[u])) {
                    u = j;
                }
            }

            visited[u] = true;
            mstCost += key[u];

            // Update the keys for neighbors
            for (int v = 0; v < n; v++) {
                if (!visited[v]) {
                    int dist = Math.abs(points[u][0] - points[v][0]) + Math.abs(points[u][1] - points[v][1]);
                    if (dist < key[v]) {
                        key[v] = dist;
                    }
                }
            }
        }
        return mstCost;
    }










    public int minCostConnectPointsUsingDFS(int[][] points) {
        if (points.length == 0) {
            return 0;
        }

        boolean[] visited = new boolean[points.length];
        int[] cost = new int[points.length];
        Arrays.fill(cost, Integer.MAX_VALUE);

        int total = 0;
        int next = 0;
        visited[next] = true;
        for (int i = points.length - 1; i > 0; --i) {
            next = dfs(points, visited, cost, next);
            visited[next] = true;
            total += cost[next];
        }

        return total;
    }
    private int dfs(int[][] points, boolean[] visited, int[] cost, int idx) {
        int min = Integer.MAX_VALUE;
        int minIdx = -1;
        for (int i = 0; i < points.length; ++i) {
            if (visited[i]) {
                continue;
            }
            cost[i] = Math.min(cost[i], distance(points[i], points[idx]));
            if (cost[i] < min) {
                minIdx = i;
                min = cost[i];
            }
        }

        return minIdx;
    }
    private int distance(int[] a, int[] b) {
        return Math.abs(a[0] - b[0]) + Math.abs(a[1] - b[1]);
    }

















    /**
        NOT WORKING

        THOUGHTS:
        ---------
        1) Can we assume UF rank[i] as |val| or |xi+yi|? ---> small
        2) Note |xi - xj| + |yi - yj| != | (xi + yi) - (xj+yj) |
        3) All points must connect, just like DSU
        4) Use map instead of par[]

     */
    public int minCostConnectPointsMyOldApproach(int[][] points) {
        Map<Map.Entry<Integer, Integer>, Map.Entry<Integer, Integer>> map = new HashMap<>();
        for (int[] p: points) {
            map.put(
                new AbstractMap.SimpleEntry<>(p[0], p[1]),
                new AbstractMap.SimpleEntry<>(-1,-1)
            );
        }

        for (Map.Entry<Integer, Integer> i: map.keySet()) {
            int min = Integer.MAX_VALUE;
            Map.Entry<Integer, Integer> need = new AbstractMap.SimpleEntry<>(-1,-1);
            for (Map.Entry<Integer, Integer> j: map.keySet()) {
                if (!i.equals(j) && !map.get(j).equals(i)){
                    int val =(
                        Math.abs(i.getKey() - j.getKey())
                        +
                        Math.abs(i.getValue() - j.getValue())
                    );
                    if (val < min) {
                        min=val;
                        need = j;
                    }
                }
            }
            map.put(i, need);
        }
        System.out.println(map);

        int c=0;
        UF uf = new UF(map);
        for (Map.Entry<Integer, Integer> i: map.keySet()) {
            Map.Entry<Integer, Integer> j = map.get(i);
            c+=uf.union(i,j);
            System.out.println(c);
        }
        return c;
    }


    class UF {
        Map<Map.Entry<Integer, Integer>, Map.Entry<Integer, Integer>> par = new HashMap<>();
        Map<Map.Entry<Integer, Integer>, Integer> rank = new HashMap<>();

        UF (Map<Map.Entry<Integer, Integer>, Map.Entry<Integer, Integer>> map) {
            for (Map.Entry<Integer, Integer> i: map.keySet()) {
                par.put(i,i);
                rank.put(i,0);
            }
        }

        private int union(Map.Entry<Integer, Integer> a, Map.Entry<Integer, Integer> b) {
            Map.Entry<Integer, Integer> pa = find(a);
            Map.Entry<Integer, Integer> pb = find(b);

            if (pa.equals(pb)) return 0;

            if (rank.get(pb) < rank.get(pa)) par.put(pb, pa);
            else if(rank.get(pa) < rank.get(pb)) par.put(pa, pb);
            else {
                par.put(pb, pa);
                rank.put(pa, rank.get(pa)+1);
            }
            int val =(
                Math.abs(a.getKey() - b.getKey())
                +
                Math.abs(a.getValue() - b.getValue())
            );
            return val;
        }
        private Map.Entry<Integer, Integer> find(Map.Entry<Integer, Integer> k){
            while(!k.equals(par.get(k))) k = par.get(k);
            return k;
        }
    }
}
