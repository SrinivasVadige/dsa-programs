package Algorithms.Graphs;

import java.util.*;

/**
 * Given an integer numCourses and a list of prerequisite pairs, where prerequisites[i] = [ai, bi] indicates that you must take course ai first if you want to take course "bi".
 * For example, the pair [0, 1], indicates that to take course 0 you have to first take course 1.
 * This problem statement resembles more of the Khan's Algorithm using Topological sort problem ✅ and, it's not related to Disjoint Sets Union Find (DSU)❌ ---> cause in DSU — a node can have many children (but it only has one parent) — DAGs are not possible

 🔥Topological sort 🔥 is only possible if the graph has no cycles --- only one direction
   It means one or more things should be done before the current thing / or one or more things are dependent on the current thing
   Examples:
   1) Dressing up for party
   2) Course Schedule with prerequisites
   3) Even scheduling
   4) Assembly instructions
   5) Program build dependencies
   6) Spring beans initialization -> @DependsOn
   7) Detect circular dependencies


    SHIRT 👕 ------------------------> HOODIE 🦸 -------
                                                        |
                                                        |
    INNER 🩳 --------> PANTS 👖----                     --------> DRIVE 🚗 -----> PARTY 🎊
                                  |                     |
                                  ↓                     |
                                SHOES 🥾-----------------
                                  ↑
                                  |
    SOCKS 🧦 ----------------------

    or

    SOCKS 🧦 ---> SHIRT 👕 ---> HOODIE 🦸 ---> INNER 🩳 ---> PANTS 👖 ---> SHOES 🥾 ---> DRIVE 🚗 ---> PARTY 🎊

    or

    INNER 🩳 ---> PANTS 👖 ---> SHIRT 👕 ---> HOODIE 🦸 ---> SOCKS 🧦 ---> SHOES 🥾 ---> DRIVE 🚗 ---> PARTY 🎊


    NOTE:
    1) Topological Ordering ---> A valid linear order (straight line) of nodes in a Directed Acyclic Graphs (DAGs) where all dependencies are respected
    2) Topological Sort ---> The process or algorithm used to compute that ordering
    3) Pre-requisites ---> Parent nodes ---> dependencies
    3) InDegree (Number of incoming edges) ---> how dependencies/prerequisites/parents does the current node have, Ex: inDegree[to]++ --→ "to" has one more prerequisite InDegree
    4) OutDegree (Number of outgoing edges) ---> for how nodes does the current node is dependency/prerequisite/parent, outDegree[from]++ --→ "from" is a prerequisite for one more nodeOutDegree
    3) We need Directed Acyclic Graphs (DAGs) ---> is a finite graph that contains directed edges and no cycles
    4) Topological ordering can be done in BFS or DFS
    5) Topological ordering is not unique
    6) Any straight line order (Topological ordering) works as long as it doesn't disturb the dependencies
    7) Just traverse from a node to it's all adjacent nodes and the other left out nodes till all nodes are traversed or until there is a cycle
    8) Degree of a node is the number of incoming edges
    9) So, it's better to start traversal from these inDegree of 0 nodes first (maintain a inDegree array int[])


    So, a topological sort is an ordering of the nodes in a directed graph where for each directed edge from nodeU to nodeV u → v, u comes before v in the ordering.
 🔥 Khan's algorithm 🔥 is a simple topological sort algorithm that can find a topological ordering in O(V + E) time.




        INITIAL THOUGHTS:
        -----------------
        1) numCourses is unique numbers in prerequisites[][]
        2) prerequisites[i].length == 2
        3) to complete prerequisites[i][0], we need to complete prerequisites[i][1]
        4) use hashMap to note every possible pre-reqs
        5) [[1,4],[2,4],[3,1],[3,2]] is valid --> one course(4) is used to complete multiple courses(1,2) and to complete one course(3) we may need to complete multiple courses(1,2) ---> i.e one course can have multiple pre-reqs
        6) Now I see some graph structure like below ---> "Graph Nodes"

                            3
                          __|___
                         /      \
                        2        1
                        |__    __|
                           \  /
                             4

        7) [[1,0],[0,2],[2,1]] => {0=[2], 1=[0], 2=[1]} ---> so dfs?
        8) if we get loop, return false
        9) And the graph is also represented as below ---> "Adjacency List"

            numCourses == num of unique courses = n = 5;
            pre=[[1,4],[2,4],[3,1],[3,2]]

                       n
             __________|_________________
             |     |       |            |
             0     1       2            3
                   |       |        ____|____
                   4       4       1         2


 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 14 Feb 2025
 * @link 207. Course Schedule <a href="https://leetcode.com/problems/course-schedule/">LeetCode Link</a>
 * @topics Graph, Topological Sort, DFS, BFS
 * @companies Amazon, Meta, Google, TikTok, Microsoft, Apple, Bloomberg, Tesla, Nordstrom, DoorDash, Coupang, Anduril, Oracle, Snowflake, PayPal, Swiggy, Cruise, IXL, CrowdStrike, State Farm, Uber, Adobe, ByteDance, Flipkart, eBay, Karat, Walmart Labs, Visa, Snap, Yelp
 */
public class CourseSchedule {
    public static void main(String[] args) {
        int numCourses = 20;
        int[][] prerequisites = {{0,10},{3,18},{5,5},{6,11},{11,14},{13,1},{15,1},{17,4}};

        numCourses = 5;
        prerequisites = new int[][]{{1,3},{0,1},{2,0},{1,2}};
        System.out.println("canFinish using BFS - Using Kahn's Algorithm Topological Sort => " + canFinishUsingBfsKahnsAlgorithmTopologicalSort(numCourses, prerequisites));
        System.out.println("canFinish using DFS => " + canFinishUsingDfs(numCourses, prerequisites));
        System.out.println("canFinish using Graph Class => " + canFinishUsingGraphClass(numCourses, prerequisites) );
    }



    /**
     * @TimeComplexity O(u + v), where u = numCourses / nodes and v = children list / vertices
     * @SpaceComplexity O(u + v)



     Here
     preReqs = [[1,3], [0,1], [2,0], [1,2]]
     numCourses = 5; ---> preReqs didn't mention any pre-reqs of 4, but we still have this node in the graph - separate graph

     [1,3] means - to complete course 1, we need to complete course 3 first
     so, 3 is parent of 1

     =============================== DECISION TREE ===============================

     INITIALLY
                                 3           4 (no pre-requisites)
                                 ↓
                                 1
                               /  ↖
                              ↓    |
                              0 -→ 2

     inDegree = incoming arrows = how dependencies/prerequisites/parents does the current node have

                 0  1  2  3  4
     inDegree = [1, 2, 1, 0, 0] ---> i.e., 3 and 4 have inDegree=0

     queue = [3, 4];                                 0  1  2  3  4
     int currNode = 3; then queue=[4,1]; inDegree = [1, 1, 1, 0, 0]

                                 3           4

                                 1
                               /  ↖
                              ↓    |
                              0 -→ 2

     queue = [4, 1];                              0  1  2  3  4
     int currNode = 4; then queue=[]; inDegree = [1, 1, 1, 0, 0]

                                 3           4

                                 1
                               /  ↖
                              ↓    |
                              0 -→ 2

     queue = []; ❌ ---> stop iteration

     nodesVisited ≠ numCourses --> 2 ≠ 5

     */
    public static boolean canFinishUsingBfsKahnsAlgorithmTopologicalSort(int numCourses, int[][] prerequisites) {
        int[] inDegree = new int[numCourses]; // by default all nodes have inDegree=0
        List<List<Integer>> adj = new ArrayList<>(numCourses);

        // prepare adjacency list for all nodes -- no children yet
        for (int i = 0; i < numCourses; i++) {
            adj.add(new ArrayList<>());
        }

        // prepare adjacency list with children
        for (int[] prereq : prerequisites) {
            adj.get(prereq[1]).add(prereq[0]);
            inDegree[prereq[0]]++; // prereq[0] has one more dependency/prereq/parent ---> [0,1] means to complete course 0, we need to complete course 1 first. So, 1 is parent of 0;  inDegree = incoming arrows = how dependencies/prerequisites/parents does the current node have
        }

        Queue<Integer> queue = new LinkedList<>(); // for BFS
        // Push all the nodes with indegree zero in the queue.
        for (int i = 0; i < numCourses; i++) {
            if (inDegree[i] == 0) {
                queue.offer(i); // i.e start traversal from inDegree=0 nodes i.e., no pre-requisites
            }
        }

        int nodesVisited = 0;
        while (!queue.isEmpty()) {
            int node = queue.poll();
            nodesVisited++;

            for (int neighbor : adj.get(node)) {
                inDegree[neighbor]--; // currNode's preReq dependency is satisfied in currNeighbor; So, Delete the edge "curr node -> curr neighbor" ---> decrease preReqs of neighbour
                if (inDegree[neighbor] == 0) { // is curr neighbor has no preReqs anymore?
                    queue.offer(neighbor);
                }
            }
        }

        return nodesVisited == numCourses; // is no cycle?
    }












    /**
     * @TimeComplexity O(u + v), where u = numCourses / nodes and v = children list / vertices
     * @SpaceComplexity O(u + v)
     */
    public static boolean canFinishUsingDfs(int numCourses, int[][] prerequisites) {
        List<List<Integer>> adjList = new ArrayList<>(numCourses);

        for (int i = 0; i < numCourses; i++) { // all nodes from 0 to numCourses i.e inDegree=0 nodes don't have any prereqs
            adjList.add(new ArrayList<>());
        }

        for (int[] prerequisite : prerequisites) {
            adjList.get(prerequisite[1]).add(prerequisite[0]);
        }

        boolean[] visit = new boolean[numCourses];
        boolean[] inStack = new boolean[numCourses];
        for (int i = 0; i < numCourses; i++) {
            if (dfs(i, adjList, visit, inStack)) { // isCyclic?
                return false;
            }
        }
        return true;
    }

    public static boolean dfs(int node, List<List<Integer>> adj, boolean[] visit, boolean[] inStack) {
        // If the node is already in the stack, we have a cycle.
        if (inStack[node]) {
            return true;
        }
        if (visit[node]) {
            return false;
        }
        // Mark the current node as visited and part of current recursion stack.
        visit[node] = true;
        inStack[node] = true;
        for (int neighbor : adj.get(node)) {
            if (dfs(neighbor, adj, visit, inStack)) {
                return true;
            }
        }
        // Remove the node from the stack.
        inStack[node] = false;
        return false;
    }






    public static boolean canFinishUsingDfs2(int numCourses, int[][] prerequisites) {
        Map<Integer, List<Integer>> graph = new HashMap<>(); // prepare graph using adjacencyList List[] or Map<node, List<childNode>>
        for(int[] pre: prerequisites) {
            graph.computeIfAbsent(pre[0], k-> new ArrayList<>()).add(pre[1]);
        }

        Set<Integer> detectCycle = new HashSet<>(); // no need to pass brand new "new HashSet<>()" visited in each for loop iteration because, we are resetting the seen nodes every time i.e., mark before the dfs() and unmark after the dfs()
        for (Integer c: graph.keySet()) {
            if (!dfs(c, detectCycle, graph)) return false;
        }
        return true;
    }

    private static boolean dfs(int c, Set<Integer> visited, Map<Integer, List<Integer>> graph) {
        if (visited.contains(c)) return false;
        else if (!graph.containsKey(c) || graph.get(c) == null) return true; // noChildren || completed

        visited.add(c);
        for (int preReq: graph.get(c)) {
            if (!dfs(preReq, visited, graph)) return false;
        }
        visited.remove(c);
        graph.put(c, null); // mark the node as "completed" ---- NODE: if you do graph.remove(c), you'll get ConcurrentModificationException. So, remove the children nodes List instead

        return true;
    }






    public boolean canFinishUsingDfs3(int numCourses, int[][] prerequisites) {
        Map<Integer, List<Integer>> graph = new HashMap<>(); // prepare graph using adjacency list int[] or hashMap
        for(int[] pre: prerequisites) {
            graph.computeIfAbsent(pre[0], k-> new ArrayList<>()).add(pre[1]);
        }

        Boolean[] finished = new Boolean[numCourses];
        for(int course=0; course<numCourses; course++) {
            if(!dfs(course, graph, new HashSet<>(), finished)) {
                return false;
            }
        }
        return true;
    }

    private boolean dfs(int course, Map<Integer, List<Integer>> graph, Set<Integer> seen, Boolean[] finished) {
        if (finished[course] != null) {
            return finished[course];
        } else if(!graph.containsKey(course)) { // we can complete it without any issue
            return true;
        } else if(!seen.add(course)) {
            return false;
        }

        boolean isFinished = true;
        for (int child: graph.get(course)) {
            if(!dfs(child, graph, seen, finished)) {
                isFinished = false;
                break;
            }
        }

        return finished[course] = isFinished;
    }











    static class Node {
        int vertice;
        Node next;
        Node(int vertice, Node next) {
            this.vertice = vertice;
            this.next = next;
        }
    }
    static class Graph {
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
    private static Graph graph;
    private static boolean[] checkedCourses;
    private static boolean[] reachedCourses;
    public static boolean canFinishUsingGraphClass(int numCourses, int[][] prerequisites) {
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
    private static boolean hasCycle(int course) {
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












    /**
     * Working but TLE
     */
    public static boolean canFinishUsingBruteForce(int numCourses, int[][] prerequisites) {
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
}
