package Algorithms.BinaryTrees;

/**
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 29 Jan 2025
 */
public class FlattenBinaryTreeToLinkedList {
    static class TreeNode {int val;TreeNode left, right;TreeNode() {}TreeNode(int val) { this.val = val; }TreeNode(int val, TreeNode left, TreeNode right) {this.val = val;this.left = left;this.right = right;}}
    public static void main(String[] args) {
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(5);
        root.left.left = new TreeNode(3);
        root.left.right = new TreeNode(4);
        root.right.right = new TreeNode(6);

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

        flattenMyApproach(root);
        printTree(root);

        root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(5);
        root.left.left = new TreeNode(3);
        root.left.right = new TreeNode(4);
        root.right.right = new TreeNode(6);

        flatten(root);
        printTree(root);
    }

    public static void flatten(TreeNode root) {
        if (root == null) return;

        flatten(root.left);
        flatten(root.right);

        TreeNode temp = root.right;
        root.right = root.left;
        root.left = null;

        while (root.right != null) root = root.right;
        root.right = temp;
    }


    public static void flattenMyApproach(TreeNode root) {
        helper(root);
    }

    private static TreeNode helper(TreeNode node) {
        System.out.println("node: " + (node==null?"null":node.val));
        if (node == null || (node !=null && node.left==null && node.right==null)) return node;

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
