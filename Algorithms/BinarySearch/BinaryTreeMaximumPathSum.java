package Algorithms.BinarySearch;

/**
 * A path in a binary tree is a sequence of nodes where each pair of adjacent nodes in the sequence has an edge connecting them. A node can only appear in the sequence at most once. Note that the path does not need to pass through the root
 * The path sum of a path is the sum of the node's values in the path.
 * Given the root of a binary tree, return the maximum path sum of any non-empty path.
 *
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 11 Feb 2025
 */
public class BinaryTreeMaximumPathSum {
    static class TreeNode {int val;TreeNode left, right;TreeNode() {}TreeNode(int val) { this.val = val; }TreeNode(int val, TreeNode left, TreeNode right) {this.val = val;this.left = left;this.right = right;}}
    public static void main(String[] args) {
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(3);
        root.left.left = new TreeNode(4);
        root.left.right = new TreeNode(5);
        root.right.left = new TreeNode(6);
        root.right.right = new TreeNode(7);
        System.out.println("maxPathSumMyApproach(root) => " + maxPathSumMyApproach(root));
        System.out.println("maxPathSum(root) => " + maxPathSum(root));
    }

    /**
     THOUGHTS:
     ---------
     1) Calculate each sum using pre-order traversal for each node? --> TLE?
     2) if left node sub-sum is smaller than both parent_sum and right node then ignore it & vice-versa or if parent_sum is smaller then both left and right
     3) Check sub-tree sum
     4) And carry forward the current max sum to the parent but we need to exclude either left or right sub-tree sum

     top down dp? as it is a sub-problem
     using recursion we can traverse from leaf to node instead of node to leaf
     */
    static int max = Integer.MIN_VALUE;
    public static int maxPathSumMyApproach(TreeNode root) {
        helper(root);
        return max;
    }

    private static int helper(TreeNode node) {
        if (node == null) return 0;

        int left = Math.max(helper(node.left), 0);
        int right = Math.max(helper(node.right), 0);

        int sum = node.val + left + right;
        max = Math.max(sum, max);

        sum = node.val + Math.max(left, right);
        return sum;
    }

    public static int maxPathSum(TreeNode root) {
        int[] maxSum = new int[]{Integer.MIN_VALUE};
        dfs(root, maxSum);
        return maxSum[0];
    }

    public static int dfs(TreeNode root, int[] maxSum) {
        if (root == null) return 0;
        int leftMax = Math.max(0, dfs(root.left, maxSum));
        int rightMax = Math.max(0, dfs(root.right, maxSum));
        maxSum[0] = Math.max(maxSum[0], root.val + leftMax + rightMax);
        return root.val + Math.max(leftMax, rightMax);
    }
}
