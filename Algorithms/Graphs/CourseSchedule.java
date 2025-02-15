package Algorithms.Graphs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Given an integer numCourses and a list of prerequisite pairs, where prerequisites[i] = [ai, bi] indicates that you must take course ai first if you want to take course bi.
 *
 * For example, the pair [0, 1], indicates that to take course 0 you have to first take course 1.

    THOUGHTS:
    ---------
    1) numCourses is unique numbers in prerequisites[][]
    2) prerequisites[i].length == 2
    3) to complete prerequisites[i][0], we need to complete prerequisites[i][1]
    4) use hashMap to note every possible pre-reqs
    5) [[1,4],[2,4],[3,1],[3,2]] is valid --> one course(4) is used to complete multiple courses(1,2) and to complete one course(3) we may need to complete multiple courses(1,2) ---> i.e one course can have multiple pre-reqs
    6) Now I see some graph structure

                        3
                      __|___
                     /      \
                    2        1
                    |__    __|
                       \  /
                         4

    7) [[1,0],[0,2],[2,1]] => {0=[2], 1=[0], 2=[1]} ---> so dfs?
    8) if we get loop, return false

 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 14 Feb 2025
 */
public class CourseSchedule {
    public static void main(String[] args) {
        int numCourses = 20;
        int[][] prerequisites = {{0,10},{3,18},{5,5},{6,11},{11,14},{13,1},{15,1},{17,4}};
        System.out.println("canFinish => " + canFinish(numCourses, prerequisites));
        System.out.println("canFinishMyApproach => " + canFinishMyApproach(numCourses, prerequisites));
    }

    public static boolean canFinish(int numCourses, int[][] prerequisites) {
        // prepare graph using adjacency list int[] or hashMap
        Map<Integer, Set<Integer>> graph = new HashMap<>();
        for (int[] cpr: prerequisites) { // course pre-req
            int c = cpr[0]; // course
            int pr = cpr[1]; // current pre-req
            if (c == pr) return false;
            if (graph.containsKey(c)) graph.get(c).add(pr);
            else graph.put(c, new HashSet<>(Arrays.asList(pr) ) );
        }
        // dfs
        Set<Integer> visited = new HashSet<>(); // visited course
        for (Integer c: graph.keySet()) {
            if (!dfs(c, visited, graph)) return false;
        }
        return true;
    }
    private static boolean dfs(int c, Set<Integer> visited, Map<Integer, Set<Integer>> graph) {
        if (visited.contains(c)) return false;
        if (graph.getOrDefault(c, new HashSet<>()).isEmpty()) return true;

        visited.add(c);
        for (int preReq: graph.getOrDefault(c, new HashSet<>())) {
            if (!dfs(preReq, visited, graph)) return false;
        }
        visited.remove(c);
        graph.getOrDefault(c, new HashSet<>()).clear();

        return true;
    }



    /**
     * Working but TLE
     */
    public static boolean canFinishMyApproach(int numCourses, int[][] prerequisites) {
        Map<Integer, Set<Integer>> map = new HashMap<>(); // [c, pre-reqs]

        for (int[] cpr: prerequisites) { // course pre-req
            int c = cpr[0]; // course
            int pr = cpr[1]; // current pre-req
            if (c == pr || isRelatedUsingCsParents(c, pr, map)) return false;
            if (map.containsKey(c)) {
                map.get(c).add(pr); // add pre-reqs
            } else {
                map.put(c, new HashSet<>(Arrays.asList(pr) ) );
            }
        }

        // System.out.println(map);

        return true;
    }
    // {0=[2], 1=[0], 2=[1]} --> c=2, pr=1 | DFS the related courses
    private static boolean isRelatedUsingCsParents(int c, int pr, Map<Integer, Set<Integer>> map) {
        boolean res = false;
        for (Map.Entry<Integer, Set<Integer>> e: map.entrySet()) {
            if (e.getValue().contains(c)) { // is this c is pre-req of another c?
                if (e.getKey()==pr) res = true; // if c-parent == pr?
                else res = isRelatedUsingPrPrs(e.getKey(), pr, map); // check c-parent is pre-req of any other c?
            }
            if(res) break;
        }
        return res;
    }
    private static boolean isRelatedUsingPrPrs(int c, int pr, Map<Integer, Set<Integer>> map) {
        // System.out.printf("c:%s, pr:%s\n", c, pr);
        boolean res = false;
        // does pr have any pre-reqs?
        if (map.containsKey(pr)) {
            res = map.get(pr).contains(c);
            if (res) return true;
            else {
                for (int i: map.get(pr)) {
                    res = isRelatedUsingPrPrs(c, i, map);
                    if (res) break;
                }
            }
        }
        return res;
    }




    class Node {
        int vertice;
        Node next;
        Node(int vertice, Node next) {
            this.vertice = vertice;
            this.next = next;
        }
    }
    class Graph {
        Node[] nodes;
        Graph(int n, int[][] edges) {
            nodes = new Node[n];
            for (int[] edge : edges) {
                int fromVertice = edge[0];
                int toVertice = edge[1];
                nodes[fromVertice] = new Node(toVertice, nodes[fromVertice]);
            }
        }
    }
    private Graph graph;
    private boolean[] checkedCourses;
    private boolean[] reachedCourses;
    public boolean canFinishUsingGraphClass(int numCourses, int[][] prerequisites) {
        graph = new Graph(numCourses, prerequisites);
        checkedCourses = new boolean[numCourses];
        reachedCourses = new boolean[numCourses];

        for (int i = 0; i < numCourses; i++) {
            if (hasCycle(i)) {
                return false;
            }
        }

        return true;
    }
    private boolean hasCycle(int course) {
        if (reachedCourses[course]) {
            return true;
        } else if (checkedCourses[course]) {
            return false;
        }
        reachedCourses[course] = true;
        checkedCourses[course] = true;

        Node node = graph.nodes[course];
        while (node != null) {
            if (hasCycle(node.vertice)) {
                return true;
            }

            node = node.next;
        }

        reachedCourses[course] = false;
        return false;
    }



    public boolean canFinishUsingAdjacencyList(int numCourses, int[][] prerequisites) {
        int[] vis = new int[numCourses];  // Visited array
        ArrayList<ArrayList<Integer>> arr = new ArrayList<>(numCourses);

        // Initialize adjacency list
        for (int i = 0; i < numCourses; i++) {
            arr.add(new ArrayList<>());
        }

        // Build the graph
        for (int i = 0; i < prerequisites.length; i++) {
            int first = prerequisites[i][1];  // Prerequisite course
            int second = prerequisites[i][0]; // Course that depends on it
            arr.get(first).add(second); // Directed edge first → second
        }

        // Check for cycles in all components
        for (int i = 0; i < numCourses; i++) {
            if (dfs(i, arr, vis)) {
                return false; // Cycle detected → courses cannot be finished
            }
        }
        return true; // No cycles found → all courses can be finished
    }
    boolean dfs(int node, ArrayList<ArrayList<Integer>> arr, int[] vis) {
        if (vis[node] == 1) return true;  // Cycle detected
        if (vis[node] == 2) return false; // Already processed (safe node)

        vis[node] = 1; // Mark as visiting (part of current DFS stack)

        for (int neighbor : arr.get(node)) {
            if (dfs(neighbor, arr, vis)) {
                return true; // Cycle detected in recursion
            }
        }

        vis[node] = 2; // Mark as fully processed (safe)
        return false;  // No cycle found
    }




    public boolean canFinishUsingPathAndVisitedArr(int n, int[][] pre) {
        List<List<Integer>> adj = new ArrayList<>();
        for(int i=0;i<n;i++)adj.add(new LinkedList<>());
        for(int[] i: pre){
            adj.get(i[0]).add(i[1]);
        }
        boolean[] path = new boolean[n];
        boolean[] visited = new boolean[n];
        for(int i=0;i<n;i++)
            if(!visited[i]){
                if(dfs(adj, path, visited, i))return false;
            }
        return true;
    }
    public boolean dfs(List<List<Integer>> adj, boolean[] path, boolean[] vis, int cur){
        if(path[cur])return true;
        if(vis[cur])return false;
        path[cur]=true; vis[cur]=true;
        List<Integer> list = adj.get(cur);
        for(int i:list){
            if(dfs(adj,path,vis,i))return true;
        }
        path[cur]=false;
        return false;
    }




    public boolean canFinishUsingCycle(int numCourses, int[][] pre) {
        Map<Integer, ArrayList<Integer>> graph = new HashMap<>();
        for(int i = 0; i< numCourses; i++){
            graph.put(i,new ArrayList<Integer>());
        }
        for(int i=0;i<pre.length;i++){
            graph.get(pre[i][0]).add(pre[i][1]);
        }
        return !hasCycle(numCourses, graph);
    }
    public static boolean hasCycle(int l, Map<Integer, ArrayList<Integer>> graph){
        boolean[] vis= new boolean[l];
        boolean[] restack= new boolean[l];
        for(int i: graph.keySet()){
            if(!vis[i]){
                if(hasCycleUtil(graph, i, l, vis, restack)) return true;
            }
        }
        return false;
    }
    public static boolean hasCycleUtil( Map<Integer, ArrayList<Integer>> graph, int src, int length, boolean[] vis, boolean[] restack){
        if(restack[src]){
            return true;
        }
        if(vis[src]) return false;
        vis[src]=true;
        restack[src]=true;
        if(graph.containsKey(src)){
            for(int i=0;i<graph.get(src).size();i++){
                if(hasCycleUtil(graph, graph.get(src).get(i), length, vis, restack)){
                    return true;
                }
                restack[graph.get(src).get(i)]=false;
            }
        }
        restack[src]=false;
        return false;
    }
}
