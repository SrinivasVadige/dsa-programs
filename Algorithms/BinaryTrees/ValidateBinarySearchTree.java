package Algorithms.BinaryTrees;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 07 Jan 2025
 * @link 98. Validate Binary Search Tree <a href="https://leetcode.com/problems/validate-binary-search-tree/">LeetCode Link</a>
 * @topics Tree, Binary Tree, DFS
 * @companies Bloomberg(10), Millennium(2), Amazon(6), Meta(5), Microsoft(5), Google(3), Oracle(2), Citadel(2), Apple(5), Yandex(5), Salesforce(4), IBM(3), Uber(3), Yahoo(3), Adobe(2), Goldman Sachs(2), Wix(2), DE Shaw(2)
 */
public class ValidateBinarySearchTree {
    public static class TreeNode {int val;TreeNode left, right;TreeNode() {}TreeNode(int val) { this.val = val; }TreeNode(int val, TreeNode left, TreeNode right) {this.val = val;this.left = left;this.right = right;}}
    public static void main(String[] args) {
        /*
         * root = [5,4,6,null,null,3,7]
         *
         *               5
         *              / \
         *             4   6
         *                / \
         *               3   7
         *
         * Here 3 should be 4's left child
         */
        TreeNode root = new TreeNode(5);
        TreeNode four = new TreeNode(4);
        TreeNode six = new TreeNode(6);
        TreeNode three = new TreeNode(3);
        TreeNode seven = new TreeNode(7);
        root.left = four;
        root.right = six;
        six.left = three;
        six.right = seven;
        System.out.println("isValidBstUsingDfsAndRange1 : " + isValidBstUsingDfsAndRange1(root)); // false
        System.out.println("isValidBstUsingInOrderDfs1: " + isValidBstUsingInOrderDfs1(root)); // false
    }

    /**
     * Image Upper boundary as +Infinity and Lower boundary as -Infinity for 5
     *
     * And for next child upper and lower boundaries will be changed
     * Choose root left node's lb as -∞ and ub as root.val and vice-versa for root.right
     * Now in nodeThree scenario choose lb as root.val and ub as it's parent.val i.e 6
     * Finally for all leftNodes max is parent.val and for rightNodes min is parent.val.
     * And logically update leftNode min and rightNode max. Ex: NodeThree
     *
     *                  5 (-∞ < 5 < +∞)
     *                 / \
     *   (-∞ < 4 < 5) 4   6 (5 < 6 < +∞)
     *                   / \
     *      (5 < 3 < 6) 3   7 (6 < 7 < +∞)
     */
    public static boolean isValidBstUsingDfsAndRange1(TreeNode root) {
        return dfs(root, Long.MIN_VALUE, Long.MAX_VALUE);
    }
    private static boolean dfs(TreeNode node, long min, long max) {
        if (node == null) return true;

        boolean isCurr = min < node.val && node.val < max; // or return if false;
        boolean isLeft = dfs(node.left, min, node.val);
        boolean isRight = dfs(node.right, node.val, max);

        return isCurr && isLeft && isRight;
    }




    public static boolean isValidBstUsingDfsAndRange2(TreeNode root) {
        return dfs2(root, null, null);
    }
    private static boolean dfs2(TreeNode node, Integer min, Integer max) {
        if (node == null) return true;

        boolean isCurr = (min == null || min < node.val) && (max == null || node.val < max); // or return if false;
//        if (min != null && min >= node.val || max != null && node.val >= max) isCurr = false;
        boolean isLeft = dfs2(node.left, min, node.val);
        boolean isRight = dfs2(node.right, node.val, max);

        return isCurr && isLeft && isRight;
    }




    public boolean isValidBstUsingInOrderList(TreeNode root) {
       List<Integer> list=new ArrayList<>();
       return  inorderDfs(root,list);
    }

    public boolean inorderDfs(TreeNode root, List<Integer> list) {
        if(root==null) {
            return true;
        }
        if (!inorderDfs(root.left, list)) {
            return false;
        }
        if(!list.isEmpty() && list.get(list.size()-1)>=root.val) {
            return false;
        }
        list.add(root.val);
        return inorderDfs(root.right,list);
    }





    private static Integer prev;
    public static boolean isValidBstUsingInOrderDfs1(TreeNode root) {
        prev = null;
        return inOrderDfs(root);
    }
    private static boolean inOrderDfs(TreeNode node) {
        if (node == null) return true;
        else if (!inOrderDfs(node.left)) return false; // left
        else if (prev != null && node.val <= prev) return false; // curr
        prev = node.val;
        return inOrderDfs(node.right); // right
    }





    public static boolean isValidBstUsingInOrderDfs2(TreeNode root) {
        LinkedList<TreeNode> stack = new LinkedList<>();

        TreeNode prev = null;
        TreeNode curr = root;

        while (curr != null || !stack.isEmpty()) {
            while (curr != null) {
                stack.push(curr);
                curr = curr.left;
            }
            curr = stack.pop(); // in-order pop
            if (prev != null && prev.val >= curr.val ) { // prev must be smaller
                return false;
            }
            prev = curr;
            curr = curr.right;
        }

        return true;
    }










    private final Queue<TreeNode> q = new LinkedList<>();
    private final Queue<Integer> upperLimits = new LinkedList<>();
    private final Queue<Integer> lowerLimits = new LinkedList<>();
    public boolean isValidBstUsingQueueAndLimits(TreeNode root) {
        Integer low = null, high = null, val;
        update(root, low, high);

        while (!q.isEmpty()) {
            root = q.poll();
            low = lowerLimits.poll();
            high = upperLimits.poll();

            if (root == null) continue;
            val = root.val;
            if (low != null && val <= low) return false;
            if (high != null && val >= high) return false;
            update(root.right, val, high);
            update(root.left, low, val);
        }
        return true;
    }
    private void update(TreeNode root, Integer low, Integer high) {
        q.add(root);
        lowerLimits.add(low);
        upperLimits.add(high);
    }











    /**
     * Failing for [5,4,6,null,null,3,7]
     */
    boolean isValid = true;
    public boolean isValidBstNotWorking(TreeNode root) {
        dfs(root);
        return isValid;
    }
    private void dfs(TreeNode node) {
        if(node==null || !isValid) return;

        boolean isLeft = node.left == null || node.left.val < node.val;
        boolean isRight = node.right == null || node.right.val > node.val;
        isValid = isLeft && isRight;

        dfs(node.left);
        dfs(node.right);
    }
}
