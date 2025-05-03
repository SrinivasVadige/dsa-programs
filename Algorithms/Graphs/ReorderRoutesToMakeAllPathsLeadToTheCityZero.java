package Algorithms.Graphs;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

/**
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 03 May 2025
 *
 *                       1 → 3 ← 2
 *                     ↗
 *                   0 ← 4 → 5
 *
 * The above graph should convert like
 *
 *                       1 ← 3 ← 2
 *                     ↙
 *                   0 ← 4 ← 5
 *
 * i.e all cities should be connected to city 0.
 * So, num of directions to be changed = 3 ---> 0 ← 1, 1 ← 3 and 4 ← 5
 *
 * TIP:
 * ---
 * Trav from 0 to all other cities
 * We want the cities to come to 0 (0 should not trav to other cities)
 * So, if you can trav from 0 to other city, then increment the count
 */
@SuppressWarnings("unchecked")
public class ReorderRoutesToMakeAllPathsLeadToTheCityZero {
    public static void main(String[] args) {
        int[][] connections = {{0, 1}, {1, 3}, {2, 3}, {4, 0}, {4, 5}};
        int n = 6;
        System.out.println(minReorder(n, connections)); // Output: 3
        System.out.println(minReorder1(n, connections)); // Output: 3
    }

    public static int minReorder(int n, int[][] connections) {
        List<List<int[]>> graph = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            graph.add(new ArrayList<>());
        }
        for (int[] connection : connections) {
            graph.get(connection[0]).add(new int[]{connection[1], 1}); // original direction {nei, 1}
            graph.get(connection[1]).add(new int[]{connection[0], 0}); // reverse direction {nei, 0} -- we set 0 instead of -1 cause we use this in increasing changeDirection counter
        }
        // [
        // 0:[{1,1}, {4,0}],
        // 1:[{0,0}, {3,1}],
        // 2:[{3,1}],
        // 3:[{1,0}, {2,0}],
        // 4:[{0,1}, {5,1}],
        // 5:[{4,0}]
        // ]
        boolean[] visited = new boolean[n];
        return dfs(0, visited, graph); // start from city 0 i.e we dfs - O(n) from 0 to all other cities.
    }
    public static int dfs(int curr, boolean[] visited, List<List<int[]>> graph) {
        visited[curr] = true;
        int count = 0;
        for (int[] nei : graph.get(curr)) {
            int neiNode = nei[0], neiDirection = nei[1];
            if (visited[neiNode]) continue;
            count += neiDirection + dfs(neiNode, visited, graph); // we want to direction form neiNode to curr. so, if dir is curr to neiNode is original direction, then increment the count
        }
        return count;
    }






    public static int minReorder1(int n, int[][] connections) {
        List<List<Integer>> al = new ArrayList<>();
        for(int i = 0; i < n; ++i)
            al.add(new ArrayList<>());
        for (var c : connections) {
            al.get(c[0]).add(c[1]);
            al.get(c[1]).add(-c[0]);
        }
        // [
        // 0:[+1, -4],
        // 1:[-0, +3],
        // 2:[+3],
        // 3:[-1, -2],
        // 4:[-0, +5],
        // 5:[-4]
        // ]
        return dfs(al, new boolean[n], 0);
    }
    private static int dfs(List<List<Integer>> al, boolean[] visited, int from) {
        int change = 0;
        visited[from] = true;
        for (var to : al.get(from)) {
            if (visited[Math.abs(to)]) continue;
            change += dfs(al, visited, Math.abs(to)) + (to > 0 ? 1 : 0); // direction
        }
        return change;
    }








    public static int minReorder2(int n, int[][] connections) {
        List<Integer>[] al = new ArrayList[n];
        for(int i = 0; i < n; ++i) al[i] = new ArrayList<>();
        Set<String> edges = new HashSet<>(); // note that, when Set<int[]> set.add({1,2}); set.contains({1,2}) --> return false. So, use String or custom Pair/Edge class
        for (int[] c : connections) {
            al[c[0]].add(c[1]);
            al[c[1]].add(c[0]);
            edges.add(c[0] + "," + c[1]); // -- to check +ve -ve direction
        }
        Queue<Integer> q = new LinkedList<>();
        q.add(0);
        // List<Integer> curr = new ArrayList<>();
        // curr.add(0);
        int reverse = 0;
        Set<Integer> visited = new HashSet<>();
        visited.add(0);

        while(q.size() > 0) {
            // List<Integer> next = new ArrayList<>();
            // for(int city: curr) {
            int city = q.poll();
            for(int nei: al[city]) {
                if(visited.contains(nei)) continue;
                visited.add(nei);
                q.add(nei);
                if(edges.contains(city + "," + nei)) { // +ve or original direction
                    reverse++;
                }
            }
            // }
            // curr = next;
        }
        return reverse;
    }












    public static int minReorderBFS(int n, int[][] connections) {
        List<List<int[]>> graph = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            graph.add(new ArrayList<>());
        }
        for (int[] connection : connections) {
            graph.get(connection[0]).add(new int[]{connection[1], 1}); // original direction
            graph.get(connection[1]).add(new int[]{connection[0], 0}); // reverse direction
        }
        boolean[] visited = new boolean[n];
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(0);
        visited[0] = true;
        int count = 0;
        while (!queue.isEmpty()) {
            int curr = queue.poll();
            for (int[] nei : graph.get(curr)) {
                int nextNode = nei[0], direction = nei[1];
                if (!visited[nextNode]) {
                    count += direction;
                    visited[nextNode] = true;
                    queue.offer(nextNode);
                }
            }
        }
        return count;
    }










    public int minReorderBFS2(int n, int[][] connections) {
        boolean [] vis = new boolean[n];
        List<List<Integer>> fwd = new ArrayList<>();
        List<List<Integer>> bck = new ArrayList<>();
        for(int i = 0; i < n; i++) {
            fwd.add(new ArrayList<>());
            bck.add(new ArrayList<>());
        }
        for(int[] c: connections) {
            fwd.get(c[0]).add(c[1]);
            bck.get(c[1]).add(c[0]);
        }

        int ans = 0;
        Queue<Integer> q = new LinkedList<>();
        q.add(0);
        vis[0] = true;
        while(!q.isEmpty()) {
            int curr= q.remove();
            for(int nei: fwd.get(curr)) {
                if(!vis[nei]) {
                    ans++;
                    vis[nei] = true;
                    q.add(nei);
                }
            }
            for(int nei: bck.get(curr)) {
                if(!vis[nei]) {
                    vis[nei] = true;
                    q.add(nei);
                }
            }
        }
        return ans;
    }










    // Note that custom Pair or Edge class is faster than AbstractMap.SimpleEntry<>() as this map uses Boxing overhead i.e Integer instead of int and it is has slightly slower hashCode() and equals() due to general-purpose implementation
    class Pair{int dest;int sign;Pair(int d, int s){this.dest = d;this.sign = s;}
        @Override
        public boolean equals(Object o) {
            if (this == o) return true; // checking reference equality
            if (!(o instanceof Pair)) return false;
            Pair pair = (Pair) o;
            return dest == pair.dest && sign == pair.sign;
        }
    }
    int res;
    public int minReorder3(int n, int[][] connections) {
        ArrayList<Pair>[] graph = new ArrayList[n];
        for(int i = 0; i < n; i++) graph[i] = new ArrayList<>();

        for(int[] con : connections){
            int src = con[0];
            int dest = con[1];

            graph[src].add(new Pair(dest,1));
            graph[dest].add(new Pair(src,0));
        }
        res = 0;
        dfs(graph,0,-1);

        return res;
    }
    public void dfs(ArrayList<Pair>[] graph, int cur, int par){
        for(int i = 0; i < graph[cur].size(); i++){
            Pair p = graph[cur].get(i);
            int nei = p.dest;
            int sign = p.sign;

            if(nei != par){
                res += sign;
                dfs(graph,nei,cur);
            }
        }
    }








    public int minReorder4(int n, int[][] connections) {
        List<List<Integer>> al = new ArrayList<>();
        for(int i = 0; i < n; ++i)
            al.add(new ArrayList<>());
        for (var c : connections) {
            al.get(c[0]).add(c[1]);
            al.get(c[1]).add(-c[0]);
        }
        return dfs4(al, new boolean[n], 0);
    }
    private int dfs4(List<List<Integer>> al, boolean[] visited, int from) {
        int change = 0;
        visited[from] = true;
        for (var to : al.get(from))
            if (!visited[Math.abs(to)])
                change += dfs4(al, visited, Math.abs(to)) + (to > 0 ? 1 : 0);
        return change;
    }










    /**
     * Working fine but TLE for large inputs.
     */
    public static int minReorderTLE(int n, int[][] connections) {
        boolean[] visited = new boolean[n];
        int[][] graph = new int[n][n];
        for (int[] connection : connections) {
            graph[connection[0]][connection[1]] = 1; // original direction
            graph[connection[1]][connection[0]] = -1; // reverse direction
        }
        return dfs(0, visited, graph);
    }

    public static int dfs(int curr, boolean[] visited, int[][] graph) { // currNode or row
        visited[curr] = true;
        int count = 0;
        for (int nei = 0; nei < graph.length; nei++) {
            if (!visited[nei]) { // nei row not visited
                if (graph[curr][nei] == 1) { // original direction
                    count += 1 + dfs(nei, visited, graph);
                } else if (graph[curr][nei] == -1) { // reverse direction
                    count += dfs(nei, visited, graph);
                }
            }
        }
        return count;
    }







    /**
     * NOT WORKING FOR LARGE INPUTS i.e when n=50000

        GIVEN:
        ---------
        1) n cities
        2) n-1 edges or roads
        3) directed graph

        UF APPROACH:
        ------------
        1) Connect all edges union(a,b)
        2) count++ if findPar(node)!=0
        3)


        ADJACENCY LIST APPROACH:
        ------------------------
        1) Check if each node can go to 0 or not
        2) If not, then count++

     */
    int count = 0;
    /**
     * @TimeComplexity: O(n^2)
     * @SpaceComplexity: O(n)
     */
    public int minReorderMyApproachNotWorking(int n, int[][] connections) {
        // if(n==50000) return 25066;
        Set<Integer>[] adjLst = new Set[n];
        for(int i=0; i<n; i++) adjLst[i] = new HashSet<>();

        for(int[] edge: connections) {
            int node = edge[0];
            int nei = edge[1];
            adjLst[node].add(nei);
        }

        boolean[] seen = new boolean[n];
        for (int node=1; node<n; node++) {
            if (seen[node]) continue;
            isZero(node, adjLst, seen);
        }

        return count;
    }
    private boolean isZero(int node, Set<Integer>[] adjLst, boolean[] seen) {
        seen[node]=true;
        if(node == 0 || adjLst[node].contains(0)) return true;

        boolean isFound = false;
        for(int nei: adjLst[node]) {
            // if(!seen[nei])
                if(isZero(nei, adjLst, seen)) isFound=true;
        }
        adjLst[node].add(0);
        if(!isFound) {
            count++;
        }
        return isFound;
    }
}
