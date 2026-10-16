package Algorithms.NAryTrees;

import java.util.*;

/**
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 16 October 2026
 * @link 590. N-ary Tree Postorder Traversal <a href="https://leetcode.com/problems/n-ary-tree-postorder-traversal/">LeetCode link</a>
 * @topics Stack, Tree, DFS
 * @description Given the root of an n-ary tree, return the postorder traversal of its nodes' values.
 * Nary-Tree input serialization is represented in their level order traversal. Each group of children is separated by the null value (See examples)

Example 1:
Input: root = [1,null,3,2,4,null,5,6]
Output: [5,6,3,2,4,1]


Example 2:
Input: root = [1,null,2,3,4,5,null,null,6,7,null,8,null,9,10,null,null,11,null,12,null,13,null,null,14]
Output: [2,6,7,11,14,3,8,12,4,9,13,10,5,1]

 */
public class NaryTreePostorderTraversal {





    public static void main(String[] args) {
        Integer[] nodes = {1,null,3,2,4,null,5,6};
        Node root = Node.createNode(nodes);
        System.out.printf("Given: %s%n", root);

        System.out.printf("postorder Using IterativeDfs4: %s%n", postorderUsingIterativeDfs4(root));
        System.out.printf("postorder Using RecursiveDfs: %s%n", postorderUsingRecursiveDfs(root));
        System.out.printf("postorder Using IterativeDfs1: %s%n", postorderUsingIterativeDfs1(root));
        System.out.printf("postorder Using IterativeDfs2: %s%n", postorderUsingIterativeDfs2(root));
        System.out.printf("postorder Using IterativeDfs3: %s%n", postorderUsingIterativeDfs3(root));
    }


    /**
     * @TimeComplexity O(n)
     * @SpaceComplexity O(h) where h is the height of the tree
     */
    public static List<Integer> postorderUsingRecursiveDfs(Node root) {
        List<Integer> list = new ArrayList<>();
        dfs(root, list);
        return list;
    }
    private static void dfs(Node node, List<Integer> list) {
        if (node == null) return;
        for(Node child: node.children) {
            dfs(child, list);
        }
        list.add(node.val);
    }





    /**
     * @TimeComplexity O(n)
     * @SpaceComplexity O(h) where h is the height of the tree
     */
    public static List<Integer> postorderUsingIterativeDfs1(Node root) {
        List<Integer> list = new LinkedList<>();
        Stack<Node> stack = new Stack<>();

        if (root != null) stack.push(root);

        while (!stack.isEmpty()) {
            Node node = stack.pop();
            list.addFirst(node.val);

            int n = node.children.size();
            for(Node child: node.children) {
                stack.push(child);
            }
        }

        return list;
    }








    /**
     * @TimeComplexity O(n)
     * @SpaceComplexity O(h) where h is the height of the tree
     */
    public static List<Integer> postorderUsingIterativeDfs2(Node root) {
        List<Integer> result = new ArrayList<>();

        if (root == null) return result;

        Stack<Node> stack = new Stack<>();
        stack.add(root);

        while (!stack.isEmpty()) {
            Node node = stack.pop();
            result.add(node.val);
            for (Node child : node.children) stack.add(child);
        }
        Collections.reverse(result);
        return result;
    }








    /**
     * @TimeComplexity O(n)
     * @SpaceComplexity O(h) where h is the height of the tree
     */
    public static List<Integer> postorderUsingIterativeDfs3(Node root) {
        List<Integer> result = new ArrayList<>();

        if (root == null) return result;

        Stack<Node> nodeStack = new Stack<>(); // Stack for traversal
        Stack<Node> reverseStack = new Stack<>(); // Stack to reverse the order

        nodeStack.push(root);

        while (!nodeStack.isEmpty()) {
            Node node = nodeStack.pop();
            reverseStack.push(node);

            for (Node child : node.children) {
                nodeStack.push(child);
            }
        }

        while (!reverseStack.isEmpty()) {
            Node node = reverseStack.pop();
            result.add(node.val);
        }

        return result;
    }







    /**
     * @TimeComplexity O(n)
     * @SpaceComplexity O(h) where h is the height of the tree
     */
   public static List<Integer> postorderUsingIterativeDfs4(Node root) {
        List<Integer> result = new ArrayList<>();
        if (root == null) return result;

        Stack<NodeVisitPair> nodeStack = new Stack<>();
        nodeStack.push(new NodeVisitPair(root, false));

        while (!nodeStack.isEmpty()) {
            NodeVisitPair currentPair = nodeStack.pop();

            if (currentPair.isVisited) {
                result.add(currentPair.node.val); // If the node has been visited, add its value to the result
            } else { // Mark the current node as visited and push it back to the stack
                currentPair.isVisited = true;
                nodeStack.push(currentPair);
                // Push all children to the stack in reverse order
                List<Node> children = currentPair.node.children;
                for (int i = children.size() - 1; i >= 0; i--) {
                    nodeStack.push(new NodeVisitPair(children.get(i), false));
                }
            }
        }

        return result;
    }

    private static class NodeVisitPair {
        Node node;
        boolean isVisited;
        NodeVisitPair(Node node, boolean isVisited) {
            this.node = node;
            this.isVisited = isVisited;
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
