package Algorithms.BinaryTrees;

import java.util.Arrays;

/**
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 27 Jan 2025
 */
public class BinaryTreeUsingPreOrderInOrderTraversals {
    static class TreeNode {int val; TreeNode left, right; TreeNode() {}TreeNode(int val) { this.val = val; } TreeNode(int val, TreeNode left, TreeNode right) {this.val = val;this.left = left;this.right = right;}}
    public static void main(String[] args) {
        int[] preOrder = {3, 9, 20, 15, 7};
        int[] inOrder = {9, 3, 15, 20, 7};

        printTree(buildTree(preOrder, inOrder));
        printTree(buildTreeUsingIndices(preOrder, inOrder));
    }

    /**
     * preOrder: 3 9 20 15 7
     * inOrder: 9 3 15 20 7
     *
     * in preOrder, 1st element is always the root node
     * in inOrder, find this root node and split into left and right parts.
     * Here, left part is the left subtree and right part is the right subtree of the root node
     *
     *                          3
     *                        /   \
     *                       9     20
     *                            /  \
     *                           15   7
     */
    public static TreeNode buildTree(int[] preOrder, int[] inOrder) {
        if (preOrder.length == 0) return null;

        System.out.println("preOrder: " + Arrays.toString(preOrder) + ", inOrder: " + Arrays.toString(inOrder));

        TreeNode root = new TreeNode(preOrder[0]);
        // if (preOrder.length == 1) return root; // optional

        int inOrderRootNodeIndex = -1;
        for (int i = 0; i < inOrder.length; i++)
            if (inOrder[i] == root.val) inOrderRootNodeIndex = i;

        int leftPartLen = inOrderRootNodeIndex+1;

        root.left = buildTree(
            Arrays.copyOfRange(preOrder, 1, leftPartLen),
            Arrays.copyOfRange(inOrder, 0, inOrderRootNodeIndex));

        root.right = buildTree(
            Arrays.copyOfRange(preOrder, leftPartLen, preOrder.length),
            Arrays.copyOfRange(inOrder, leftPartLen, inOrder.length));

        return root;
    }

    /** same as above buildTree(), but using indices */
    public static TreeNode buildTreeUsingIndices(int[] preOrder, int[] inOrder) {
        return helper(preOrder, inOrder, 0, preOrder.length-1, 0, inOrder.length-1);
    }

    private static TreeNode helper(int[] preOrder, int[] inOrder, int preOrderStart, int preOrderEnd, int inOrderStart, int inOrderEnd) {
        if (preOrderStart > preOrderEnd || inOrderStart > inOrderEnd) return null;

        int rootVal = preOrder[preOrderStart];
        int rootIndexInInOrder = -1;
        for (int i = inOrderStart; i <= inOrderEnd; i++)
            if (inOrder[i] == rootVal) rootIndexInInOrder = i;

        TreeNode root = new TreeNode(rootVal);
        int leftPartLen = rootIndexInInOrder - inOrderStart;

        root.left = helper(preOrder, inOrder, preOrderStart+1, preOrderStart+leftPartLen, inOrderStart, rootIndexInInOrder-1);
        root.right = helper(preOrder, inOrder, preOrderStart+leftPartLen+1, preOrderEnd, rootIndexInInOrder+1, inOrderEnd);

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
