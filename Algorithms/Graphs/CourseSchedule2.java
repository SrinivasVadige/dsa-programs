package Algorithms.Graphs;

import java.util.*;

/**
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 26 July 2025
 * @link 210. Course Schedule II <a href="https://leetcode.com/problems/course-schedule-ii/">LeetCode Link</a>
 * @topics Graph, Topological Sort, DFS, BFS
 * @companies Amazon, Snowflake, Meta, Microsoft, TikTok, Apple, Uber, Anduril, Roblox, Remitly, Snap, Salesforce, Google, Bloomberg, Oracle, Flipkart, Goldman Sachs, Netflix, DoorDash, Qualcomm, Intuit, Walmart Labs, LinkedIn, Nvidia, eBay, IBM, ByteDance, Tesla, Arista Networks, Citadel
 * @see Algorithms.Graphs.CourseSchedule
 */
public class CourseSchedule2 {
    public static void main(String[] args) {
        int numCourses = 4;
        int[][] prerequisites = {{1,0},{2,0},{3,1},{3,2}};
        System.out.println("findOrder using BFS - Using Kahn's Algorithm Topological Sort => " + Arrays.toString(findOrderUsingBfsKahnsAlgorithmTopologicalSort(numCourses, prerequisites)));
        System.out.println("findOrder using DFS => " + Arrays.toString(findOrderUsingDfs(numCourses, prerequisites)));
        System.out.println("findOrder using DFS2 => " + Arrays.toString(findOrderUsingDfs2(numCourses, prerequisites)));
    }


    /**
     * @TimeComplexity O(V+E), v=vertices, e=edges
     * @SpaceComplexity O(V+E)
     */
    public static int[] findOrderUsingBfsKahnsAlgorithmTopologicalSort(int numCourses, int[][] prerequisites) {
        int[] topologicalOrder = new int[numCourses];
        int[] inDegree = new int[numCourses];
        @SuppressWarnings("unchecked")
        List<Integer>[] adjList = new List[numCourses];

        for(int i=0; i<numCourses; i++) {
            adjList[i] = new ArrayList<>();
        }

        for(int[] pre: prerequisites) { // [0,1] means to take 0 you have to first take course 1, 1 is parent 1->0, inDegree[0]
            adjList[pre[1]].add(pre[0]);
            inDegree[pre[0]]++;
        }

        Queue<Integer> zeros = new LinkedList<>();
        for(int i=0; i<numCourses; i++) {
            if(inDegree[i] == 0) {
                zeros.offer(i);
            }
        }


        int nodeCount = 0;
        while(!zeros.isEmpty()) {
            int node = zeros.poll();
            topologicalOrder[nodeCount++] = node;
            for(int neighbour : adjList[node]) {
                if (--inDegree[neighbour] == 0) {
                    zeros.add(neighbour);
                }
            }
        }

        return nodeCount != numCourses ? new int[0] : topologicalOrder;
    }








    public static int[] findOrderUsingDfs(int numCourses, int[][] prerequisites) {
        Map<Integer, List<Integer>> graph = new HashMap<>();
        for (int[] pre : prerequisites) {
            graph.computeIfAbsent(pre[0], k -> new ArrayList<>()).add(pre[1]);
        }

        Boolean[] finished = new Boolean[numCourses];
        List<Integer> order = new ArrayList<>();

        for (int course = 0; course < numCourses; course++) {
            if (!dfs(course, graph, new HashSet<>(), finished, order)) {
                return new int[0]; // cycle detected
            }
        }

        return order.stream().mapToInt(Integer::intValue).toArray();
    }

    private static boolean dfs(int course, Map<Integer, List<Integer>> graph, Set<Integer> seen,
                        Boolean[] finished, List<Integer> order) {

        if (finished[course] != null) {
            return finished[course];
        }
        if (!seen.add(course)) {
            return false; // cycle detected
        }

        boolean isFinished = true;
        for (int child : graph.getOrDefault(course, Collections.emptyList())) {
            if (!dfs(child, graph, seen, finished, order)) {
                isFinished = false;
                break;
            }
        }

        seen.remove(course); // backtrack -- optional as we send new HashSet<>() everytime
        if (isFinished) order.add(course);
        return finished[course] = isFinished;
    }












    static final int WHITE = 1;
    static final int GRAY = 2;
    static final int BLACK = 3;
    static boolean isPossible;

    /**
     * @TimeComplexity O(V+E), v=vertices, e=edges
     * @SpaceComplexity O(V+E)
     */
    public static int[] findOrderUsingDfs2(int numCourses, int[][] prerequisites) {
        isPossible = true;
        Map<Integer, List<Integer>> adjList =  new HashMap<>();
        List<Integer> topologicalOrder = new ArrayList<>();
        Map<Integer, Integer> color = new HashMap<>();
        // By default, all vertices are WHITE
        for (int i = 0; i < numCourses; i++) {
            color.put(i, WHITE);
        }

        for(int i=0; i<numCourses; i++) {
            adjList.put(i, new ArrayList<>());
        }
        for (int[] prerequisite : prerequisites) {
            int dest = prerequisite[0];
            int src = prerequisite[1];
            adjList.get(src).add(dest);
        }

        for (int i = 0; i < numCourses; i++) {
            if (color.get(i) == WHITE) {
                dfs(i, color, adjList, topologicalOrder);
            }
        }

        int[] order;
        if (isPossible) {
            Collections.reverse(topologicalOrder);
            order = topologicalOrder.stream().mapToInt(i -> i).toArray();
        } else {
            order = new int[0];
        }

        return order;
    }

    private static void dfs(int node, Map<Integer, Integer> color, Map<Integer, List<Integer>> adjList, List<Integer> topologicalOrder) {
        if (!isPossible) {
            return;
        }

        color.put(node, GRAY); // Recursion starts

        for (Integer neighbor : adjList.get(node)) {
            if (color.get(neighbor) == WHITE) {
                dfs(neighbor, color, adjList, topologicalOrder);
            } else if (color.get(neighbor) == GRAY) {
                isPossible = false; // An edge to a GRAY vertex represents a cycle
            }
        }

        // Recursion ends. We mark it as black
        color.put(node, BLACK);
        topologicalOrder.add(node);
    }
}
