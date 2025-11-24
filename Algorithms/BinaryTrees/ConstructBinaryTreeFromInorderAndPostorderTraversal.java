package Algorithms.BinaryTrees;

import java.util.*;

/**
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 23 Nov 2025
 * @link 106. Construct Binary Tree from Inorder and Postorder Traversal <a href="https://leetcode.com/problems/construct-binary-tree-from-inorder-and-postorder-traversal/">LeetCode Link</a>
 * @topics Array, Hash Table, Divide and Conquer, Tree, Binary Tree
 * @companies Google(2), Amazon(2), Microsoft(9), Walmart Labs(4), Bloomberg(2)
 * @see Algorithms.BinaryTrees.ConstructBinaryTreeFromPreorderAndInorderTraversal ConstructBinaryTreeFromPreorderAndInorderTraversal --- for easier understanding
 */
public class ConstructBinaryTreeFromInorderAndPostorderTraversal {
    public static class TreeNode {int val; TreeNode left, right; TreeNode() {} TreeNode(int val) { this.val = val; } TreeNode(int val, TreeNode left, TreeNode right) { this.val = val; this.left = left; this.right = right; }}

    public static void main(String[] args) {
        int[] inorder = new int[]{9,3,15,20,7};
        int[] postorder = new int[]{9,15,7,20,3};
        System.out.println("buildTree => " + (printTree(buildTree(inorder, postorder))));
    }


    /**
     * @see Algorithms.BinaryTrees.ConstructBinaryTreeFromPreorderAndInorderTraversal ConstructBinaryTreeFromPreorderAndInorderTraversal

                     0 1 2  3  4
        inorder =   [9,3,15,20,7]     l  val  r
        postorder = [9,15,7,20,3]     l   r  val

                        3
                       /  \
                      9    20
                          /  \
                         15   7


                            {node, leftDistance, rightDistance}
                                        {3, 1, 3}
                       _____________________|_____________________
                       |                                         |
                 {9, 0, 0}                                  {20, 1, 1}
                                                 ________________|________________
                                                 |                               |
                                            {15, 0, 0}                       {7, 0, 0}
     */


    public static TreeNode buildTree(int[] inOrder, int[] postOrder) {
        int n = inOrder.length;
        Map<Integer, Integer> inOrderNumToI = new HashMap<>();
        for(int i=0; i<n; i++) inOrderNumToI.put(inOrder[i], i);
        return dfs(postOrder, n-1, 0, n-1, inOrderNumToI);
    }
    private static TreeNode dfs(int[] postOrder, int postOrderI, int leftI, int rightI, Map<Integer, Integer> inOrderNumToI) {
        if (postOrderI < 0 || leftI > rightI) return null;

        int nodeVal = postOrder[postOrderI];
        int nodeI = inOrderNumToI.get(nodeVal); // nodeIndexInInOrder or mid
        int leftDistance = nodeI - leftI; // inOrderLeftDistance from nodeIndexInInOrder
        int rightDistance = rightI - nodeI; // inOrderRightDistance from nodeIndexInInOrder

        TreeNode node = new TreeNode(nodeVal);
        node.left  = dfs(postOrder, postOrderI-1-rightDistance, leftI,   nodeI-1, inOrderNumToI);
        node.right = dfs(postOrder, postOrderI-1,               nodeI+1, rightI,  inOrderNumToI);
        /*
        // or
        node.left  = dfs(postOrder, postOrderI-1-rightDistance, nodeI-leftDistance, nodeI-1,             inOrderNumToI);
        node.right = dfs(postOrder, postOrderI-1,               nodeI+1,            nodeI+rightDistance, inOrderNumToI);
        */
        return node;
    }








    int post_idx;
    int[] postorder;
    int[] inorder;
    HashMap<Integer, Integer> idx_map;
    public TreeNode buildTree2(int[] inorder, int[] postorder) {
        this.postorder = postorder;
        this.inorder = inorder;
        post_idx = postorder.length - 1;
        idx_map = new HashMap<>();

        int idx = 0;
        for (Integer val : inorder) idx_map.put(val, idx++);
        return helper(0, inorder.length - 1);
    }
    private TreeNode helper(int in_left, int in_right) {
        if (in_left > in_right) return null;
        int root_val = postorder[post_idx];
        TreeNode root = new TreeNode(root_val);
        int index = idx_map.get(root_val);
        post_idx--;
        root.right = helper(index + 1, in_right);
        root.left = helper(in_left, index - 1);
        return root;
    }










    public static List<String> printTree(TreeNode root) {
        List<String> result = new ArrayList<>();
        if (root == null) return result;
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                TreeNode node = queue.poll();
                if (node == null) {
                    result.add("null");
                } else {
                    result.add(String.valueOf(node.val));
                    queue.add(node.left);
                    queue.add(node.right);
                }
            }
        }
        return result;


    }
}
