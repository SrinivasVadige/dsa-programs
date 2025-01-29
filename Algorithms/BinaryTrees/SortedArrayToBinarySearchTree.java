package Algorithms.BinaryTrees;

/**
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 28 Jan 2025
 */
public class SortedArrayToBinarySearchTree {
    static class TreeNode {int val; TreeNode left, right; TreeNode() {}TreeNode(int val) { this.val = val; } TreeNode(int val, TreeNode left, TreeNode right) {this.val = val;this.left = left;this.right = right;}}

    public static void main(String[] args) {
        int[] nums = {0,1,2,3,4,5};
        /*

                                  3
                                /   \
                               1     5
                              / \    /
                             0   2  4

            => 3 1 5 0 2 4

         */
        TreeNode root = sortedArrayToBST(nums);
        printTree(root);
    }

    public static TreeNode sortedArrayToBST(int[] nums) {
        return helper(0, nums.length-1, nums);
    }

    private static TreeNode helper(int s, int e, int[] nums) {
        if (s > e) return null;
        int mid = (s+e+1)/2; // or (s+e)/2; or s+ ((e-s+1)/2);
        TreeNode node = new TreeNode(nums[mid]);

        //System.out.printf("s:%s, e:%s, mid:%s \n", s, e, mid);

        node.left = helper(s, mid-1, nums);
        node.right = helper(mid+1, e, nums);

        return node;
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
