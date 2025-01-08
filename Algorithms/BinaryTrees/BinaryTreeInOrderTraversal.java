package Algorithms.BinaryTrees;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 07 Jan 2025
 */
 public class BinaryTreeInOrderTraversal {
    static class TreeNode {int val;TreeNode left, right;TreeNode() {}TreeNode(int val) { this.val = val; }TreeNode(int val, TreeNode left, TreeNode right) {this.val = val;this.left = left;this.right = right;}}

    public static void main(String[] args) {
        TreeNode root = new TreeNode(1);
        TreeNode two = new TreeNode(2);
        TreeNode three = new TreeNode(3);
        TreeNode four = new TreeNode(4);
        TreeNode five = new TreeNode(5);
        TreeNode six = new TreeNode(6);
        TreeNode seven = new TreeNode(7);
        TreeNode eight = new TreeNode(8);
        TreeNode nine = new TreeNode(9);

        /*
         *         1
         *        / \
         *       2   3
         *      / \   \
         *     4   5   8
         *        / \ /
         *       6  7 9
         *
         * Binary Tree In Order Traversal = [4, 2, 6, 5, 7, 1, 3, 9, 8]
         */
        root.left = two;
        root.right = three;
        two.left = four;
        two.right = five;
        three.right = eight;
        five.left = six;
        five.right = seven;
        eight.left = nine;


        System.out.println("inOrderTraversalUsingRecursion: " + inOrderTraversalUsingRecursion(root));
        System.out.println("inOrderTraversalUsingRecursion2: " + inOrderTraversalUsingRecursion2(root));
        System.out.println("inOrderTraversalUsingStack: " + inOrderTraversalUsingStack(root));
    }

    public static List<Integer> inOrderTraversalUsingRecursion(TreeNode root) {
        if (root == null) return new ArrayList<>();

        List<Integer> lst = new ArrayList<>();
        lst.addAll(inOrderTraversalUsingRecursion(root.left));
        lst.add(root.val);
        lst.addAll(inOrderTraversalUsingRecursion(root.right));
        return lst;
    }

    public static List<Integer> inOrderTraversalUsingRecursion2(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        inOrder(root, res);
        return res;
    }
    private static void inOrder(TreeNode node, List<Integer> res) {
        if (node == null) return;
        inOrder(node.left, res);
        res.add(node.val);
        inOrder(node.right, res);
    }

    public static List<Integer> inOrderTraversalUsingStack(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        Stack<TreeNode> st = new Stack<>();
        TreeNode current = root;

        while(current!=null || !st.isEmpty()){
            while(current!=null){
                st.push(current);
                current =  current.left;
            }
            current = st.pop();
            res.add(current.val);
            current = current.right;
        }
        return res;
    }
}
