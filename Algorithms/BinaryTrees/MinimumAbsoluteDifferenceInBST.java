package Algorithms.BinaryTrees;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 15 Dec 2025
 * @link 530. Minimum Absolute Difference in BST <a href="https://leetcode.com/problems/minimum-absolute-difference-in-bst/">LeetCode Link 1</a>
 * @link 783. Minimum Distance Between BST Nodes <a href="https://leetcode.com/problems/minimum-distance-between-bst-nodes/description/">LeetCode Link 2</a>
 * @topics Tree, Binary Tree, Binary Search Tree, DFS, BFS
 * @companies Google(4), Meta(2), Amazon(2), Wix(2)
 * LeetCode 530 & 783 problems are same
 */
public class MinimumAbsoluteDifferenceInBST {
    public static class TreeNode {int val;TreeNode left, right;TreeNode() {}TreeNode(int val) { this.val = val; }TreeNode(int val, TreeNode left, TreeNode right) {this.val = val;this.left = left;this.right = right;}}

    public static void main(String[] args) {
        TreeNode root = new TreeNode(4);
        root.left = new TreeNode(2);
        root.right = new TreeNode(6);
        root.left.left = new TreeNode(1);
        root.left.right = new TreeNode(3);

        System.out.println("getMinimumDifference Using Recursion -> " + getMinimumDifferenceUsingRecursion(root));
        System.out.println("getMinimumDifference Using Dfs -> " + getMinimumDifferenceUsingDfs(root));
        System.out.println("getMinimumDifference Using SortedList 🔥 -> " + getMinimumDifferenceUsingSortedList(root));
        System.out.println("getMinimumDifference Using InOrderList 🔥 -> " + getMinimumDifferenceUsingInOrderList(root));
        System.out.println("getMinimumDifference Using InOrderDfs 🔥 -> " + getMinimumDifferenceUsingInOrderDfs(root));
        System.out.println("getMinimumDifference Using InOrderRecursion -> " + getMinimumDifferenceUsingInOrderRecursion(root));
        System.out.println("getMinimumDifference Using DfsWithRange -> " + getMinimumDifferenceUsingDfsWithRange(root));
    }




    /**
     * @TimeComplexity O(nh) → O(n log n), cause in balanced BST h=logn and in unbalanced it's O(n^2)
     * @SpaceComplexity O(h) → O(log n) for balanced and O(n) for unbalanced
     */
    public static int getMinimumDifferenceUsingRecursion(TreeNode root) {
        if (root == null) return Integer.MAX_VALUE;

        int min = Integer.MAX_VALUE;

        // leftsRightMost -> near smaller number in BST
        if (root.left != null) {
            TreeNode prev = root.left;
            while (prev.right != null) prev = prev.right;
            min = Math.min(min, root.val - prev.val);
        }

        // rightsLeftMost -> near bigger number in BST
        if (root.right != null) {
            TreeNode next = root.right;
            while (next.left != null) next = next.left;
            min = Math.min(min, next.val - root.val);
        }


        min = Math.min(min, getMinimumDifferenceUsingRecursion(root.left));
        min = Math.min(min, getMinimumDifferenceUsingRecursion(root.right));

        return min;
    }






    /**
     * @TimeComplexity O(nh) → O(n log n), cause in balanced BST h=logn and in unbalanced it's O(n^2)
     * @SpaceComplexity O(h) → O(log n) for balanced and O(n) for unbalanced
     */
    static int min;
    public static int getMinimumDifferenceUsingDfs(TreeNode root) {
        min = Integer.MAX_VALUE;
        dfs1(root);
        return min;
    }
    private static void dfs1(TreeNode node){
        if (node == null) return;

        if (node.left != null) {
            TreeNode prev = node.left; // leftsRightMost
            while (prev.right != null) prev = prev.right;
            min = Math.min(min, node.val - prev.val);
        }

        if (node.right != null) {
            TreeNode next = node.right; // rightsLeftMost
            while (next.left != null) next = next.left;
            min = Math.min(min, next.val - node.val);
        }

        dfs1(node.left);
        dfs1(node.right);
    }







    /**
     * @TimeComplexity O(n log n)
     * @SpaceComplexity O(n)
     */
    static List<Integer> nodeValues;
    public static int getMinimumDifferenceUsingSortedList(TreeNode root) {
        nodeValues = new ArrayList<>();
        dfs(root); // we can use dfs or bfs for traversal
        Collections.sort(nodeValues);

        int min = Integer.MAX_VALUE;
        for (int i = 1; i < nodeValues.size(); i++) {
            min = Math.min(min, nodeValues.get(i) - nodeValues.get(i - 1));
        }

        return min;
    }
    private static void dfs(TreeNode node) {
        if (node == null) return;
        nodeValues.add(node.val);
        dfs(node.left);
        dfs(node.right);
    }







    /**
     * @TimeComplexity O(n)
     * @SpaceComplexity O(n)
     */
    static List<Integer> inorderNodes;
    public static int getMinimumDifferenceUsingInOrderList(TreeNode root) {
        inorderNodes = new ArrayList<>();
        inorderDfsWithList(root); // In BST, in-order traversal gives the sorted order 🔥

        int min = Integer.MAX_VALUE;
        for (int i = 1; i < inorderNodes.size(); i++) {
            min = Math.min(min, inorderNodes.get(i) - inorderNodes.get(i-1));
        }

        return min;
    }
    private static void inorderDfsWithList(TreeNode node) {
        if (node == null) return;
        inorderDfsWithList(node.left);
        inorderNodes.add(node.val);
        inorderDfsWithList(node.right);
    }








    /**
     * @TimeComplexity O(n)
     * @SpaceComplexity O(h)
     */
    static int minVal = Integer.MAX_VALUE;
    static Integer prev = null;
    public static int getMinimumDifferenceUsingInOrderDfs(TreeNode root) {
        inOrderDfs(root); // In BST, in-order traversal gives the sorted order 🔥
        return minVal;
    }
    private static void inOrderDfs(TreeNode root){
        if (root == null) return;

        inOrderDfs(root.left);
        if (prev != null) {
            minVal = Math.min(minVal, Math.abs(root.val - prev));
        }
        prev = root.val;
        inOrderDfs(root.right);
    }


    /**
     * @TimeComplexity O(n)
     * @SpaceComplexity O(h)
     */
    // Integer prev = null;
    public static int getMinimumDifferenceUsingInOrderRecursion(TreeNode root) {
        if (root == null) return minVal;

        getMinimumDifferenceUsingInOrderRecursion(root.left);
        if (prev != null) {
            minVal = Math.min(minVal, root.val - prev);
        }
        prev = root.val;
        getMinimumDifferenceUsingInOrderRecursion(root.right);

        return minVal;
    }






    /**
     * @TimeComplexity O(n)
     * @SpaceComplexity O(h)
     */
    public static int getMinimumDifferenceUsingDfsWithRange(TreeNode root) {
        return dfs(root, 1000001, -100001);
    }

    public static int dfs(TreeNode root, int min, int max) {
        if (root == null) return Integer.MAX_VALUE;

        int minDiff = Math.abs(root.val-min);
        int maxDiff = Math.abs(max-root.val);
        int diff = Math.min(minDiff, maxDiff);
        int subMin = Math.min(dfs(root.left, min, root.val), dfs(root.right, root.val, max));
        return Math.min(diff, subMin);
    }
}
