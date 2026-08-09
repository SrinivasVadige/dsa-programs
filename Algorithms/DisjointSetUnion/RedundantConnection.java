package Algorithms.DisjointSetUnion;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 23 March 2025
 *
 * NOTE:
 * 1. edges[i][j]={ai, bi} and ai >= bi
 */
public class RedundantConnection {
    static int[] par, rank;
    public static void main(String[] args) {
        int[][] edges = {{1, 2}, {1, 3}, {2, 3}};
        System.out.println("findRedundantConnection using UNION & FIND => " + Arrays.toString(findRedundantConnection(edges)));
        System.out.println("findRedundantConnection using Map => " + Arrays.toString(findRedundantConnectionUsingMap(edges)));
        System.out.println("findRedundantConnection using GraphAdjList With Map => " + Arrays.toString(findRedundantConnectionUsingGraphAdjListWithMap(edges)));
        System.out.println("findRedundantConnection using GraphAdjList With Array => " + Arrays.toString(findRedundantConnectionUsingGraphAdjListWithArray(edges)));
    }

    /**
     * Just from one of the edges it'll become cyclic. So, stop doing union & return that edge
     *
     * Here the num/vertices in edges[i][j] starts from 1 not 0. So, use num-1 or take 0 index as dummy
    */
    public static int[] findRedundantConnection(int[][] edges) {
        int n = edges.length;
        par = new int[n+1]; // 0 index is dummy
        rank = new int[n+1]; // 0 index is dummy
        for (int i=0; i<=n; i++) par[i]=i;

        for (int[] e: edges) {
            if(!union(e[0],e[1])) return e;
        }
        return new int[0];
    }
    private static boolean union(int a, int b) {
        a = find(a);
        b = find(b); // roots
        if (a == b) return false;

        if (rank[a]<rank[b]) par[a]=b;
        else if (rank[b]<rank[a]) par[b]=a;
        else {
            par[a]=b;
            rank[b]++;
        }
        return true;
    }
    private static int find(int i){
        while(i != par[i]) i=par[i];
        return i;
        // or
        // if (i!=par[i]) {
        //     par[i] = par(find[i]);
        // }
        // return par[i];
    }






    /**
     * This works as edges[i][j]={ai, bi} and ai >= bi
     * But this hashMap wil fail when we have edges like {7,4} ---> use union find in this scenario
     */
    public static int[] findRedundantConnectionUsingMap(int[][] edges) {
        HashMap<Integer, Integer> map = new HashMap<>(); // <parent, child>

        for (int[] edge : edges) {
            int x = findParent(edge[0], map);
            int y = findParent(edge[1], map);

            if (x == y) return new int[]{edge[0], edge[1]};
            else map.put(y, x);
        }
        return new int[]{-1, -1};
    }
    public static int findParent(int i, HashMap<Integer, Integer> map) {
        if (!map.containsKey(i)) return i;
        int root = findParent(map.get(i), map);
        map.put(i, root); // root parent
        return root;
    }








    /**
     * Graph Adjacency List means for each vertex, we have a list of vertices that are connected to it.
     * Here, we are using Map<Integer, List<Integer>> to represent the graph.
     */
    public static int[] findRedundantConnectionUsingGraphAdjListWithMap(int[][] edges) {
        int n = edges.length;
        Map<Integer, List<Integer>> map = new HashMap<>(); // adjacency list, graph representation
        for (int i = 0; i <= n; i++) map.put(i, new ArrayList<>()); // 0 is dummy & to avoid map.putIfAbsent(u, new ArrayList<>());

        for (int[] edge : edges) {
            int u = edge[0], v = edge[1];

            if (map.containsKey(u) && map.containsKey(v)) {
                boolean[] visited = new boolean[n + 1]; // 0 is dummy
                if (dfs(map, u, v, visited)) return edge;
            }

            // u is connected to v and v is connected to u i.e u -> v and v -> u
            map.get(u).add(v);
            map.get(v).add(u);
        }
        return new int[0];
    }
    public static boolean dfs(Map<Integer, List<Integer>> map, int u, int v, boolean[] visited) {
        visited[u] = true;
        if (u == v) return true;

        for (int uEdge : map.get(u)) { // trav all edges of u
            if (visited[uEdge]) continue;
            if (dfs(map, uEdge, v, visited)) return true; // check if any of the u's edge is connected to v
        }
        return false;
    }






    /**
     * Same as findRedundantConnectionUsingDFSMap but using List<Integer>[] instead of Map<Integer, List<Integer>>
     *
     * Performs DFS and returns true if there's a path between src and target.
     *
     * In this method, 0 is not dummy like above findRedundantConnection method.
     * So, we need to do num-1 for edges[i][j] to get the correct index.
     */
    public static int[] findRedundantConnectionUsingGraphAdjListWithArray(int[][] edges) {
        final int N = edges.length;
        @SuppressWarnings("unchecked")
        List<Integer>[] mapList = new List[N]; // adjacency list, graph representation
        for (int i = 0; i < N; i++) mapList[i] = new ArrayList<>();
        boolean[] visited = new boolean[N];

        for (int[] edge : edges) {

            // If DFS returns true, we will return the edge.
            if (isConnected(edge[0] - 1, edge[1] - 1, visited, mapList)) {
                return new int[] { edge[0], edge[1] };
            }

            // u -> v and v -> u
            mapList[edge[0] - 1].add(edge[1] - 1);
            mapList[edge[1] - 1].add(edge[0] - 1);
        }

        return new int[]{};
    }
    private static boolean isConnected( int src, int target, boolean[] visited, List<Integer>[] mapList ){
        visited[src] = true;

        if (src == target) return true;

        boolean isFound = false;
        for (int i : mapList[src]) {
            if (!visited[i]) isFound = (isFound || isConnected(i, target, visited, mapList));
        }
        visited[src] = false;

        return isFound;
    }
}
