package Algorithms.BinaryTrees;

import java.util.LinkedList;
import java.util.Queue;

/**
 * In Tree, maxDepth and maxHeight are same
 *
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 19 Jan 2025
 */
public class MaximumDepthOfBinaryTree {
    static class TreeNode {int val;TreeNode left, right;TreeNode() {}TreeNode(int val) { this.val = val; }TreeNode(int val, TreeNode left, TreeNode right) {this.val = val;this.left = left;this.right = right;}}
    public static void main(String[] args) {
        TreeNode root = new TreeNode(3);
        root.left = new TreeNode(9);
        root.right = new TreeNode(20);
        root.right.left = new TreeNode(15);
        root.right.right = new TreeNode(7);
        System.out.println("maxDepthUsingRecursion: " + maxDepthUsingRecursion(root));
        System.out.println("maxDepthUsingBfsQueue: " + maxDepthUsingBfsQueue(root));
    }

    public static int maxDepthUsingRecursion(TreeNode root) {
        if(root == null) return 0;
        int left = maxDepthUsingRecursion(root.left);
        int right = maxDepthUsingRecursion(root.right);
        return Math.max(left, right) + 1; // +1 for current level
    }

    public static int maxDepthUsingBfsQueue(TreeNode root) {
        if(root == null) return 0;
        Queue<TreeNode> q = new LinkedList<>();
        q.offer(root);
        int depth = 0;
        while(!q.isEmpty()) {
            int size = q.size();
            for(int i=0; i<size; i++) {
                TreeNode node = q.poll();
                if(node.left != null) q.add(node.left);
                if(node.right != null) q.add(node.right);
            }
            depth++;
        }
        // q.offer(null);
        return depth;
    }
}
