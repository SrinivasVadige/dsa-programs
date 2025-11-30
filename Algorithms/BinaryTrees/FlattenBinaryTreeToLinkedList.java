package Algorithms.BinaryTrees;

import java.util.Stack;

/**
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 29 Jan 2025
 * @link 114. Flatten Binary Tree to Linked List <a href="https://leetcode.com/problems/flatten-binary-tree-to-linked-list/">LeetCode Link</a>
 * @topics Tree, Binary Tree, LinkedList, DFS, Stack
 * @companies Amazon(2), Google(4), Meta(2), Bloomberg(2), Microsoft(11), Oracle(3), Yahoo(2)
 */
public class FlattenBinaryTreeToLinkedList {
    public static class TreeNode {int val;TreeNode left, right;TreeNode() {}TreeNode(int val) { this.val = val; }TreeNode(int val, TreeNode left, TreeNode right) {this.val = val;this.left = left;this.right = right;}}
    public static void main(String[] args) {
        TreeNode root;

        /*
                         1
                       /   \
                      2     5
                     / \     \
                    3   4     6

            => 1-2-3-4-5-6


        THOUGHTS:
        --------
        1. preserve root.right in one temp var
        2. now root.right = root.left
        3. at root.right leaf node add that temp var value
        4. repeat this until we no longer have anything to add to temp
        */

        System.out.println("\nFlatten Using Iteration:");
        root = buildTree();
        flattenUsingIteration(root);
        printTree(root);

        System.out.println("\nFlatten Using Recursion:");
        root = buildTree();
        flattenUsingRecursion(root);
        printTree(root);

        System.out.println("\nFlatten Using Dfs:");
        root = buildTree();
        flattenUsingDfs(root);
        printTree(root);

        System.out.println("\nFlatten Using Stack:");
        root = buildTree();
        flattenUsingStack(root);
        printTree(root);

    }





    /**
     * @TimeComplexity O(n)
     * @SpaceComplexity O(1)

                         1 ---> current
                       /   \
                      2     5
                     / \     \
                    3   4     6

     will become

                         1
                          \
                           2 ---> current
                          / \
                         3   4
                              \
                               5
                                \
                                 6

     will become

                         1
                          \
                           2
                            \
                             3  ---> current
                              \
                               4
                                \
                                 5
                                  \
                                   6

     similarly, we traverse all right nodes

     */
    public static void flattenUsingIteration(TreeNode root) {
        while (root != null) {
            if (root.left != null) {

                TreeNode leftLeaf = root.left;
                while (leftLeaf.right != null) leftLeaf = leftLeaf.right;
                leftLeaf.right = root.right; // Morris traversal main step

                root.right = root.left;
                root.left = null;
            }

            root = root.right; // for the next iteration
        }
    }







    /**
     * @TimeComplexity O(n)
     * @SpaceComplexity O(H), where H is the height of the tree ---> for recursion stack
     */
    public static void flattenUsingRecursion(TreeNode root) {
        if (root == null) return;

        flattenUsingRecursion(root.left);
        flattenUsingRecursion(root.right);

        if (root.left == null) return;

        TreeNode rightPart = root.right;
        TreeNode leftPart = root.left;
        TreeNode leftLeaf = root.left;

        root.right = leftPart;
        root.left = null;

        while (leftLeaf.right != null) leftLeaf = leftLeaf.right;
        leftLeaf.right = rightPart;
    }





    public static void flattenUsingRecursion2(TreeNode root) {
        if (root == null) return;

        flattenUsingRecursion2(root.left);
        flattenUsingRecursion2(root.right);

        TreeNode temp = root.right;
        root.right = root.left;
        root.left = null;

        while (root.right != null) root = root.right;
        root.right = temp;
    }






    public void flattenUsingRecursion3(TreeNode root) {
        if (root == null) return;

        TreeNode left = root.left, right = root.right;

        root.left = null;

        if (left != null) {
            root.right = left;
        }

        flattenUsingRecursion3(left);

        TreeNode leftMost = null;
        while (left != null) {
            leftMost = left;
            left = left.right;
        }

        if (leftMost != null) leftMost.right = right;
        else root.right = right;

        flattenUsingRecursion3(right);
    }










    /**
     * @TimeComplexity O(n)
     * @SpaceComplexity O(H), where H is the height of the tree ---> for recursion stack
     */
    public static void flattenUsingDfs(TreeNode root) {
        dfs(root);
    }

    private static TreeNode dfs(TreeNode node) {
        if (node == null || node.left == null && node.right == null) return node;

        TreeNode left = node.left;
        TreeNode right = node.right;
        node.left = null;

        TreeNode leftMost = dfs(left);
        TreeNode rightMost = dfs(right);

        if (leftMost != null) {
            node.right = left;
            leftMost.right = right;
        }

        return rightMost != null ? rightMost : leftMost;
    }





    public static void flattenUsingDfs2(TreeNode root) {
        helper(root);
    }

    private static TreeNode helper(TreeNode node) {
        if (node == null || (node.left==null && node.right==null)) return node;

        TreeNode rTemp = null;
        TreeNode leaf = null;

        // left child
        if (node.left != null) {
            rTemp = node.right;
            node.right = node.left;
            node.left = null;
        }
        leaf = helper(node.right);

        // right child using temp
        if (rTemp != null) {
            leaf.right = rTemp;
            leaf = helper(leaf.right);
        }

        return leaf;
    }







    public static void flattenUsingStack(TreeNode root) {
        if (root == null) return;

        class Pair<K, V> { final K key; final V value; Pair(K a, V b) { this.key = a; this.value = b;} public K getKey() { return this.key; } public V getValue() {return this.value;}}
        int START = 1, END = 2;
        TreeNode tailNode = null;
        Stack<Pair<TreeNode, Integer>> stack = new Stack<>();
        stack.push(new Pair<>(root, START));

        while (!stack.isEmpty()) {
            Pair<TreeNode, Integer> nodeData = stack.pop();
            TreeNode currentNode = nodeData.getKey();
            int recursionState = nodeData.getValue();

            if (currentNode.left == null && currentNode.right == null) { // We reached a leaf node. Record this as a tail node and move on.
                tailNode = currentNode;
                continue;
            }


            if (recursionState == START) { // If the node is in the START state, it means we still haven't processed it's left child yet.
                // If the current node has a left child, we add it
                // to the stack AFTER adding the current node again
                // to the stack with the END recursion state.
                if (currentNode.left != null) {
                    stack.push(new Pair<>(currentNode, END));
                    stack.push(
                        new Pair<>(currentNode.left, START)
                    );
                } else if (currentNode.right != null) {
                    stack.push(new Pair<>(currentNode.right, START)); // In case the current node didn't have a left child, we will add it's right child
                }
            } else {
                // If the current node is in the END recursion state,
                // that means we did process one of it's children. Left
                // if it existed, else right.
                TreeNode rightNode = currentNode.right;

                // If there was a left child, there must have been a leaf
                // node and so, we would have set the tailNode
                if (tailNode != null) {
                    // Establish the proper connections.
                    tailNode.right = currentNode.right;
                    currentNode.right = currentNode.left;
                    currentNode.left = null;
                    rightNode = tailNode.right;
                }

                if (rightNode != null) {
                    stack.push(new Pair<>(rightNode, START));
                }
            }
        }
    }



















    private static TreeNode buildTree() {
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(5);
        root.left.left = new TreeNode(3);
        root.left.right = new TreeNode(4);
        root.right.right = new TreeNode(6);

        return root;
    }
    private static void printTree(TreeNode root) {
        System.out.print("\nTree: ");
        java.util.Queue<TreeNode> queue = new java.util.LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                TreeNode node = queue.poll();
                System.out.print(node == null ? "null " : node.val + " ");
                if (node == null) continue;
                queue.add(node.left);
                queue.add(node.right);
            }
        }
    }
}
