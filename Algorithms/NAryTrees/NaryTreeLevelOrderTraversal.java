package Algorithms.NAryTrees;

import java.util.*;

/**
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 16 October 2026
 * @link 429. N-ary Tree Level Order Traversal <a href="https://leetcode.com/problems/n-ary-tree-level-order-traversal/">LeetCode link</a>
 * @topics Tree, BFS
 * @description Given the root of an n-ary tree, return the level order traversal of its nodes' values.
 * Nary-Tree input serialization is represented in their level order traversal. Each group of children is separated by the null value (See examples)

Example 1:
Input: root = [1,null,3,2,4,null,5,6]
Output: [[1],[3,2,4],[5,6]]


Example 2:
Input: root = [1,null,2,3,4,5,null,null,6,7,null,8,null,9,10,null,null,11,null,12,null,13,null,null,14]
Output: [[1],[2,3,4,5],[6,7,8,9,10],[11,12,13],[14]]

 */
public class NaryTreeLevelOrderTraversal {

    public static void main(String[] args) {
        Integer[] nodes = {1, null, 3, 2, 4, null, 5, 6};
        Node root = Node.createNode(nodes);
        System.out.printf("Given: %s\n", root);

        System.out.printf("levelOrder Using QueueBfs: %s\n", levelOrderUsingQueueBfs(root));
        System.out.printf("levelOrder Using ListBfs: %s\n", levelOrderUsingListBfs(root));
        System.out.printf("levelOrder Using RecursiveBfs: %s\n", levelOrderUsingRecursiveBfs(root));

    }


    /**
     * @TimeComplexity O(n)
     * @SpaceComplexity O(w) where w is the maximum width of the tree
     */
    public static List<List<Integer>> levelOrderUsingQueueBfs(Node root) {
        List<List<Integer>> result = new ArrayList<>();
        if (root == null) return result;

        Queue<Node> q = new LinkedList<>();
        q.add(root);

        while (!q.isEmpty()) {
            int n = q.size();
            List<Integer> level = new ArrayList<>();
            while (n-- > 0) {
                Node node = q.poll();
                level.add(node.val);
                q.addAll(node.children);
            }
            result.add(level);
        }

        return result;
    }






    /**
     * @TimeComplexity O(n)
     * @SpaceComplexity O(w) where w is the maximum width of the tree
     */
    public static List<List<Integer>> levelOrderUsingListBfs(Node root) {
        List<List<Integer>> result = new ArrayList<>();
        if (root == null) return result;

        List<Node> prevLayer = Arrays.asList(root);

        while (!prevLayer.isEmpty()) {
            List<Node> currLayer = new ArrayList<>();
            List<Integer> prevVals = new ArrayList<>();
            for (Node node : prevLayer) {
                prevVals.add(node.val);
                currLayer.addAll(node.children);
            }
            result.add(prevVals);
            prevLayer = currLayer;
        }

        return result;
    }







    /**
     * @TimeComplexity O(n)
     * @SpaceComplexity O(w) where w is the maximum width of the tree
     */
    public static List<List<Integer>> levelOrderUsingRecursiveBfs(Node root) {
        List<List<Integer>> result = new ArrayList<>();
        if (root == null) return result;
        levelRecursion(root, 0, result);
        return result;
    }
    private static void levelRecursion(Node node, int level, List<List<Integer>> result) {
        if (result.size() == level) result.add(new ArrayList<>());
        result.get(level).add(node.val);
        for (Node child: node.children) {
            levelRecursion(child, level+1, result);
        }
    }







    //region Node
    public static class Node {
        public int val;
        public List<Node> children;


        public Node() {
            children = new ArrayList<Node>();
        }

        public Node(int _val) {
            val = _val;
            children = new ArrayList<Node>();
        }

        public Node(int _val, ArrayList<Node> _children) {
            val = _val;
            children = _children;
        }

        /*
            Nodes: [1,null,2,3,4,5,null,null,6,7,null,8,null,9,10,null,null,11,null,12,null,13,null,null,14]

                               1
                  _____________|___________
                 /        /       \        \
                2        3         4        5
                       /   \       |       / \
                      6     7      8      9  10
                            |      |      |
                           11     12     13
                            |
                           14
         */
        public static Node createNode(Integer[] nodes) {
            if (nodes == null || nodes.length == 0 || nodes[0] == null) {
                return null;
            }

            Node root = new Node(nodes[0]);
            Queue<Node> queue = new LinkedList<>();
            queue.offer(root);

            int i = 2; // Index 0 is root, Index 1 is the first null delimiter

            while (i < nodes.length && !queue.isEmpty()) {
                Node parent = queue.poll();

                // Collect all children for the current parent until reaching null or array end
                while (i < nodes.length && nodes[i] != null) {
                    Node child = new Node(nodes[i]);
                    parent.children.add(child);
                    queue.offer(child);
                    i++;
                }

                i++; // Move past the null delimiter that marks the end of this parent's child list
            }

            return root;
        }

        @Override
        public String toString() {
            if (children == null || children.isEmpty()) return "{" + val + "}";
            return "{" + val + ", children=" + children + "}";
        }
    }
    //endregion
}
