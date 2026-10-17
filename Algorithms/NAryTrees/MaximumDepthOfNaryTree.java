package Algorithms.NAryTrees;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 16 October 2026
 * @link 559. Maximum Depth of N-ary Tree <a href="https://leetcode.com/problems/maximum-depth-of-n-ary-tree/">LeetCode link</a>
 * @topics Tree, DFS, BFS
 * @description Given a n-ary tree, find its maximum depth.
 * The maximum depth is the number of nodes along the longest path from the root node down to the farthest leaf node.
 * Nary-Tree input serialization is represented in their level order traversal, each group of children is separated by the null value (See examples).

Example 1:
Input: root = [1,null,3,2,4,null,5,6]
Output: 3


Example 2:
Input: root = [1,null,2,3,4,5,null,null,6,7,null,8,null,9,10,null,null,11,null,12,null,13,null,null,14]
Output: 5

 */
public class MaximumDepthOfNaryTree {
    public static void main(String[] args) {
        Integer[] nodes = {1, null, 3, 2, 4, null, 5, 6};
        Node root = Node.createNode(nodes);
        System.out.printf("Given: %s%n", root);
        System.out.printf("Maximum depth (recursive DFS): %d%n", maxDepthUsingRecursiveDfs(root));
        System.out.printf("Maximum depth (iterative BFS): %d%n", maxDepthUsingIterativeBfs(root));
    }


    /**
     * @TimeComplexity O(n)
     * @SpaceComplexity O(h) where h is the height of the tree
     */
    public static int maxDepthUsingRecursiveDfs(Node root) {
        if (root == null) return 0;
        int max = 1;
        for(Node child: root.children) {
            max = Math.max(max, maxDepthUsingRecursiveDfs(child)+1);
        }
        return max;
    }


    /**
     * @TimeComplexity O(n)
     * @SpaceComplexity O(w) where w is the maximum width of the tree
     */
    public static int maxDepthUsingIterativeBfs(Node root) {
        if (root == null) return 0;
        int levels = 0;
        Queue<Node> q = new LinkedList<>();
        q.add(root);

        while (!q.isEmpty()) {
            levels++;
            int n = q.size();
            while (n-- > 0) {
                Node node = q.poll();
                q.addAll(node.children);
            }
        }

        return levels;
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
