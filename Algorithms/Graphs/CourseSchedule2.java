package Algorithms.Graphs;

import java.util.*;

/**
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 26 July 2025
 * @link 210. Course Schedule II <a href="https://leetcode.com/problems/course-schedule-ii/">LeetCode Link</a>
 * @topics Graph, Topological Sort, DFS, BFS
 * @companies Roblox(8), Apple(5), Google(4), Microsoft(4), Netflix(4), Amazon(3), Bloomberg(3), Oracle(2), Arista Networks(2), Snowflake(2), Meta(4), TikTok(3), Citadel(2), Uber(18), DoorDash(12), Snap(11), Intuit(10), Salesforce(8), Anduril(6), LinkedIn(5), IBM(3), Walmart Labs(3), Nvidia(3)
 * @see Algorithms.Graphs.CourseSchedule
 */
public class CourseSchedule2 {
    public static void main(String[] args) {
        int numCourses = 4;
        int[][] prerequisites = {{1,0},{2,0},{3,1},{3,2}};
        System.out.println("findOrder using BFS - Using Kahn's Algorithm Topological Sort => " + Arrays.toString(findOrderUsingBfsKahnsAlgorithmTopologicalSort(numCourses, prerequisites)));
        System.out.println("findOrder using post order DFS => " + Arrays.toString(findOrderUsingDfs1(numCourses, prerequisites)));
        System.out.println("findOrder using DFS2 => " + Arrays.toString(findOrderUsingDfs4(numCourses, prerequisites)));
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








    /**
     * @TimeComplexity O(V+E), v=vertices, e=edges
     * @SpaceComplexity O(V+E)


        [[2,0],[1,2],[3,1],[3,2]]
        3 ---> 2 --> 0
        |      ↑
        -----> 1

        [[1,3],[2,1],[0,1],[0,2]]
        0 ---> 1 --> 3
        |      ↑
        -----> 2

        Instead of preparing graph like topological sort ---> in [u,v] v as parent ---> al[v].add(u)
        Here, in this DFS use ---> in [u,v] u as parent ---> al[u].add(v)

        here the graph preparation is reverse of
        {@link #findOrderUsingBfsKahnsAlgorithmTopologicalSort} and {@link #findOrderUsingDfs3}
        but the neighbour and linking traversal is same as {@link #findOrderUsingDfs3}
        i.e., the curr Iteration comes after the prev Iteration & stop moving forward dfs() if already seen that node or path
        and if the full traversal is done then add the node to the returning order[]

        🔥 in post-order dfs traversal, it looks like we're traversing from bottom to top
     */
    static List<Integer>[] al;
    static int[] state; // 0: UnvisitedState, 1: VisitingState, 2: TraversalFinished - All children traversal are done
    static int[] order;
    static int i = 0;
    public static int[] findOrderUsingDfs1(int numCourses, int[][] prerequisites) {
        al = new List[numCourses];
        for (int idx=0; idx<numCourses; idx++) al[idx] = new ArrayList<>();
        state = new int[numCourses];
        order = new int[numCourses];

        for (int[] pre : prerequisites) {
            al[pre[0]].add(pre[1]);
        }

        i=0;
        for (int course = 0; course < numCourses; course++) {
            if (!postOrderDfs(course)) return new int[0]; // isFinishedDfs() == false then cycle detected
        }

        return order;
    }

    private static boolean postOrderDfs(int course) {

        if (state[course] == 2) return true;
        if (state[course] == 1) return false; // cycle detected ---> stop moving forward

        state[course] = 1; // add in backtrack ---> visiting state
        for (int nei : al[course]) {
            // or if (state[nei] == 1) return false; // cycle detected
            if (!postOrderDfs(nei)) return false;
        }
        state[course] = 2; // remove from backtrack ---> visited state or finished

        order[i++] = course;
        return true;
    }











    public static int[] findOrderUsingDfs2(int numCourses, int[][] prerequisites) {
        List<Integer>[] al = new List[numCourses];
        for (int i=0; i<numCourses; i++) al[i] = new ArrayList<>();

        for (int[] pre : prerequisites) {
            al[pre[0]].add(pre[1]);
        }

        Boolean[] finished = new Boolean[numCourses];
        List<Integer> order = new ArrayList<>();

        for (int course = 0; course < numCourses; course++) {
            if (!dfs(course, al, new boolean[numCourses], finished, order)) { // isFinishedDfs()?
                return new int[0]; // cycle detected
            }
        }

        return order.stream().mapToInt(Integer::intValue).toArray();
    }

    private static boolean dfs(int course, List<Integer>[] al, boolean[] seen, Boolean[] finished, List<Integer> order) {

        if (finished[course] != null) return finished[course];
        if (seen[course]) return false; // cycle detected

        seen[course]=true; // add in backtrack
        boolean isFinished = true;
        for (int child : al[course]) {
            // or if (seen[nei]) return false; // cycle detected
            if (!dfs(child, al, seen, finished, order)) {
                isFinished = false;
                break;
            }
        }
        seen[course]=false; // remove from backtrack

        if (isFinished) order.add(course);
        return finished[course] = isFinished;
    }













    /**

        [[0,6],[5,0],[4,5],[1,0],[3,1],[4,3],[2.0],[3,2]]

        5 -----------------
        ↑                 ↓
  6 --> 0 --> 1 --> 3 --> 4
        ↓           ↑
        2 --------> 7 --> 9
                    ↓
                    8


        [[2,0],[2,1]]

                  1
                  ↓
            0 --> 2

        topologinxal order = [1,0,2], but not = [0,2.1]

        Here, in this DFS prepare graph like topological sort ---> in [u,v] v as parent ---> al[v].add(u)

        TRICK ---> the currernt linkedList should come before the prev LinkedList
        1. currTail.next = prevHead
        2. prevHead = currHead

     */
    static class Node {
        int val;
        Node next;
        Node(){}
        Node(int v) {val=v;}
    }
    public static int[] findOrderUsingDfs3(int numCourses, int[][] prerequisites) {

        int[] parent = new int[numCourses];
    List<Integer>[] al = new List[numCourses];
        for (int i=0; i<numCourses; i++) {
            al[i] = new ArrayList<>();
            parent[i]=i;
        }

        for (int[] pair: prerequisites) {
            int second = pair[0];
            int first = pair[1]; // parent

            parent[second] = first;
            al[first].add(second);
        }

        if (isCyclic(numCourses, al)) return new int[0];


        Node dummy = new Node(-1);
        Node prevHead = null;
        boolean[] seen = new boolean[numCourses];
        for (int i=0; i<numCourses; i++) { // all the 0 in-degrees or self-parents
            if (i != parent[i]) continue;

            Node currDummy = new Node(-1);
            seen[i]=true;
            Node currTail = dfs(currDummy, i, seen, al);
            Node currHead = currDummy.next;

            currTail.next = prevHead;
            prevHead = currHead;

            dummy.next = currHead; // always maintain the lastest LinkeList head

            // System.out.println("tail: " + currTail + " -------------------");
        }

        int[] order = new int[numCourses];
        int idx = 0;
        dummy = dummy.next;
        while(dummy != null) {
            order[idx++] = dummy.val;
            dummy = dummy.next;
        }

        return order;
    }
    private static Node dfs(Node head, int course, boolean[] seen, List<Integer>[] al) {
        // System.out.println("head: " + head);
        Node courseNode = new Node(course);
        head.next = courseNode;
        Node finalTail = courseNode;
        Node prevHead = null;

        for (int nei: al[course]) {

            if (seen[nei]) continue;
            seen[nei] = true;

            courseNode.next = null; // preparing for current iteration
            Node currTail = dfs(courseNode, nei, seen, al);
            Node currHead = courseNode.next;

            currTail.next = prevHead;
            prevHead = currHead;

            if (finalTail == courseNode) finalTail = currTail; // initially
        }

        return finalTail;
    }
    private static boolean isCyclic(int n, List<Integer>[] al) {
        Boolean[] completed = new Boolean[n];
        boolean[] path = new boolean[n];
        for (int i=0; i<n; i++) {
            // if (isCyclic(i, path, completed)) return true; // using seenPath & completedNode
            if (isCyclic(i, new int[n], al)) return true;
        }
        return false;
    }
    private static boolean isCyclic(int course, boolean[] path, Boolean[] completed, List<Integer>[] al) {
        if (completed[course] != null) return completed[course];

        path[course] = true;
        for(int nei: al[course]) {
            if (path[nei]) return completed[course] = true;
            if(isCyclic(nei, path, completed, al)) return completed[course] = true;
        }
        path[course] = false;

        return completed[course] = false;
    }

    private static boolean isCyclic(int course, int[] state, List<Integer>[] al) {
        if (state[course] == 2) return false; // it's path trav completed and it's not cyclic
        if (state[course] == 1) return true; // visiting the same node agian without completing it

        state[course] = 1; // 1st visit
        for(int nei: al[course]) {
            // or if (state[nei] == 1) return true;
            if(isCyclic(nei, state, al)) return true;

        }
        state[course] = 2; // completed
        return false;
    }













    static final int WHITE = 1;
    static final int GRAY = 2;
    static final int BLACK = 3;
    static boolean isPossible;

    /**
     * @TimeComplexity O(V+E), v=vertices, e=edges
     * @SpaceComplexity O(V+E)
     */
    public static int[] findOrderUsingDfs4(int numCourses, int[][] prerequisites) {
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
