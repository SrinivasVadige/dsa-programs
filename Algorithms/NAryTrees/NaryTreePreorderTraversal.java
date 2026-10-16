package Algorithms.NAryTrees;

import java.util.*;

/**
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 16 October 2026
 * @link 589. N-ary Tree Preorder Traversal <a href="https://leetcode.com/problems/n-ary-tree-preorder-traversal/">LeetCode link</a>
 * @topics Stack, Tree, DFS
 * @description Given the root of an n-ary tree, return the preorder traversal of its nodes' values.
 * Nary-Tree input serialization is represented in their level order traversal. Each group of children is separated by the null value (See examples)

Example 1:
Input: root = [1,null,3,2,4,null,5,6]
Output: [1,3,5,6,2,4]


Example 2:
Input: root = [1,null,2,3,4,5,null,null,6,7,null,8,null,9,10,null,null,11,null,12,null,13,null,null,14]
Output: [1,2,3,6,7,11,14,4,8,12,5,9,13,10]

 */
public class NaryTreePreorderTraversal {

    public static void main(String[] args) {
        Integer[] nodes = {1, null, 3, 2, 4, null, 5, 6};
        Node root = Node.createNode(nodes);
        System.out.printf("Given: %s\n", root);

        System.out.printf("preorder Using Recursive Dfs: %s\n", preorderUsingRecursiveDfs(root));
        System.out.printf("preorder Using Iterative Dfs: %s\n", preorderUsingIterativeDfs(root));
    }


    /**
     * @TimeComplexity O(n)
     * @SpaceComplexity O(h) where h is the height of the tree
     */
    public static List<Integer> preorderUsingRecursiveDfs(Node root) {
        List<Integer> list = new ArrayList<>();
        dfs(root, list);
        return list;
    }
    private static void dfs(Node node, List<Integer> list) {
        if(node == null) return;
        list.add(node.val);
        for(Node child: node.children){
            dfs(child, list);
        }
    }




    /**
     * @TimeComplexity O(n)
     * @SpaceComplexity O(h) where h is the height of the tree
     */
    public static List<Integer> preorderUsingIterativeDfs(Node root) {
        List<Integer> list = new ArrayList<>();
        Stack<Node> stack = new Stack<>();
        if (root != null) stack.push(root);

        while(!stack.isEmpty()) {
            Node node = stack.pop();
            list.add(node.val);
            int n = node.children.size();
            for(int i=n-1; i>=0; i--) {
                stack.add(node.children.get(i));
            }
        }


        return list;
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
