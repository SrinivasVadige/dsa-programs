package Algorithms.Graphs;

import java.util.*;

/**
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 18 Dec 2025
 * @link 133. Clone Graph <a href="https://leetcode.com/problems/clone-graph">LeetCode Link</a>
 * @topics Graph, Hash Table, DFS, BFS
 * @companies Meta(10), Amazon(2), Google(2), Microsoft(2), Flexport(11), Bloomberg(7), Apple(3), Adobe(2), Oracle(2), Wix(2), Uber(2), Nvidia(2), Nutanix(2), Grammarly(2)
 */
public class CloneGraph {
    public static class Node {
        int val;
        List<Node> neighbors;

        Node() {
        }

        Node(int v) {
            val = v;
            neighbors = new ArrayList<>();
        }

        Node(int v, List<Node> nie) {
            val = v;
            neighbors = nie;
        }
    }

    public static void main(String[] args) {

        Node node = prepareGraph();
        /*
                1 -- 2
                |    |
                3 -- 4

         */
        System.out.println("cloneGraph using DFS: ");
        printGraph(cloneGraphUsingDfs1(node));

        System.out.println("cloneGraph using recursion: ");
        printGraph(cloneGraphUsingRecursion(node));

        System.out.println("cloneGraph using BFS: ");
        printGraph(cloneGraphUsingBfs(node));
    }


    /**
     * @TimeComplexity O(V + E)
     * @SpaceComplexity O(V + E)
     */
    public static Node cloneGraphUsingDfs1(Node node) {
        if (node == null) return null;

        Map<Integer, List<Integer>> adjList = new HashMap<>();
        adjList.put(node.val, new ArrayList<>());
        dfs(node, adjList);

        Map<Integer, Node> valToNode = new HashMap<>();
        for (Map.Entry<Integer, List<Integer>> entry : adjList.entrySet()) {
            int val = entry.getKey();
            if (!valToNode.containsKey(val)) valToNode.put(val, new Node(val));

            for (int nei : entry.getValue()) {
                if (!valToNode.containsKey(nei)) valToNode.put(nei, new Node(nei));
                valToNode.get(val).neighbors.add(valToNode.get(nei));
            }

        }

        return valToNode.get(node.val);
    }

    private static void dfs(Node node, Map<Integer, List<Integer>> adjList) {
        for (Node nei : node.neighbors) {
            adjList.get(node.val).add(nei.val);

            if (!adjList.containsKey(nei.val)) {
                adjList.put(nei.val, new ArrayList<>());
                dfs(nei, adjList);
            }
        }
    }







    /**
     * @TimeComplexity O(V + E)
     * @SpaceComplexity O(V)
     */
    private final static HashMap<Node, Node> oldToNew = new HashMap<>(); // visited

    public static Node cloneGraphUsingRecursion(Node node) {
        if (node == null) return null;

        if (oldToNew.containsKey(node)) return oldToNew.get(node);

        Node cloneNode = new Node(node.val);
        oldToNew.put(node, cloneNode);

        for (Node neighbor : node.neighbors) {
            cloneNode.neighbors.add(cloneGraphUsingRecursion(neighbor));
        }
        return cloneNode;
    }







    /**
     * @TimeComplexity O(V + E)
     * @SpaceComplexity O(V)
     */
    public static Node cloneGraphUsingDfs2(Node node) {
        if (node == null) return null;

        HashMap<Node, Node> oldToNew = new HashMap<>();
        return dfs(node, oldToNew);
    }

    private static Node dfs(Node node, HashMap<Node, Node> oldToNew) {
        if (oldToNew.containsKey(node)) return oldToNew.get(node);

        Node cloneNode = new Node(node.val);
        oldToNew.put(node, cloneNode);

        for (Node neighbor : node.neighbors) {
            cloneNode.neighbors.add(dfs(neighbor, oldToNew)); // if get nei node by oldToNew map or it'll created and calculate it's neighbors as well
        }
        return cloneNode;
    }







    /**
     * @TimeComplexity O(V + E)
     * @SpaceComplexity O(V)
     */
    public static Node cloneGraphUsingBfs(Node node) {
        if (node == null) return null;

        HashMap<Node, Node> oldToNew = new HashMap<>(); // visited
        bfs(node, oldToNew);

        return oldToNew.get(node);
    }

    private static void bfs(Node node, HashMap<Node, Node> oldToNew) {
        LinkedList<Node> queue = new LinkedList<>();
        queue.add(node);
        oldToNew.put(node, new Node(node.val, new ArrayList<>()));

        while (!queue.isEmpty()) {
            Node curr = queue.remove();
            for (Node neighbor : curr.neighbors) {
                if (!oldToNew.containsKey(neighbor)) {
                    oldToNew.put(neighbor, new Node(neighbor.val));
                    queue.add(neighbor);
                }
                oldToNew.get(curr).neighbors.add(oldToNew.get(neighbor));
            }
        }
    }


    private static Node prepareGraph() {
        Node one = new Node(1);
        Node two = new Node(2);
        Node three = new Node(3);
        Node four = new Node(4);

        one.neighbors.add(two);
        one.neighbors.add(four);

        two.neighbors.add(one);
        two.neighbors.add(three);

        three.neighbors.add(two);
        three.neighbors.add(four);

        four.neighbors.add(one);
        four.neighbors.add(three);

        return one;
    }

    private static void printGraph(Node node) {
        Map<Integer, List<Integer>> adjList = new HashMap<>();
        adjList.put(node.val, new ArrayList<>());
        dfs(node, adjList);
        System.out.println(adjList);
    }


}
