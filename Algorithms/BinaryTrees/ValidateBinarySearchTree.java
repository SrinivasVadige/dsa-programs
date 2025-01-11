package Algorithms.BinaryTrees;

import java.util.LinkedList;

/**
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 07 Jan 2025
 */
public class ValidateBinarySearchTree {
    static class TreeNode {int val;TreeNode left, right;TreeNode() {}TreeNode(int val) { this.val = val; }TreeNode(int val, TreeNode left, TreeNode right) {this.val = val;this.left = left;this.right = right;}}
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
        System.out.println("isValidBST: " + isValidBST(root)); // false
        System.out.println("isValidBSTUsingStack: " + isValidBSTUsingStack(root)); // false
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
    public static boolean isValidBST(TreeNode root) {
        return isValidBST(root, Long.MIN_VALUE, Long.MAX_VALUE);
    }

    public static boolean isValidBST(TreeNode parent, long min, long max) {
        if (parent == null) return true;
        if (parent.val <= min || parent.val >= max) return false; // because of this condition, we can't use Integer min and max vals and it fails when root=[2147483647] ---> EXPECTED TRUE BUT OUTPUT IS FALSE

        return isValidBST(parent.left, min, parent.val) && isValidBST(parent.right, parent.val, max);
    }


    public static boolean isValidBSTUsingStack(TreeNode root) {
        LinkedList<TreeNode> stack = new LinkedList<>();

        TreeNode prev = null;
        TreeNode current = root;

        while (current != null || !stack.isEmpty()) {
            while (current != null) {
                stack.push(current);
                current = current.left;
            }
            current = stack.pop();
            if (prev != null && prev.val >= current.val ) {
                return false;
            }
            prev = current;
            current = current.right;
        }

        return true;
    }











    /**
     * Failing for [5,4,6,null,null,3,7]
     */
    public boolean isValidBSTMyApproach(TreeNode root) {
        if (root == null) return false;
        boolean[] isValid = new boolean[]{true};
        dfs2(root, isValid);
        return isValid[0];
    }

    void dfs2(TreeNode node, boolean[] isValid) {
        if(node==null || isValid[0]==false) return;

        if((node.left != null && node.left.val >= node.val)
        || (node.right != null && node.right.val <= node.val)) {
            isValid[0]=false;
            return;
        }

        dfs2(node.left, isValid);
        dfs2(node.right, isValid);
    }
}
