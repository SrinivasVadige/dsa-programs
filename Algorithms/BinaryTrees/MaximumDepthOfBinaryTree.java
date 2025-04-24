package Algorithms.BinaryTrees;

import java.util.LinkedList;
import java.util.Queue;

/**
 * In Tree, maxDepth and maxHeight are same
 *
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 19 Jan 2025
 *
 *
 *                                   3
 *                                  / \
 *                                 9  20
 *                                   /  \
 *                                  15   7
 */
public class MaximumDepthOfBinaryTree {
    static class TreeNode {int val;TreeNode left, right;TreeNode() {}TreeNode(int val) { this.val = val; }TreeNode(int val, TreeNode left, TreeNode right) {this.val = val;this.left = left;this.right = right;}}
    public static void main(String[] args) {
        TreeNode root = new TreeNode(3);
        root.left = new TreeNode(9);
        root.right = new TreeNode(20);
        root.right.left = new TreeNode(15);
        root.right.right = new TreeNode(7);
        System.out.println("maxDepthUsingDFS: " + maxDepthUsingDFS(root));
        System.out.println("maxDepthUsingRecursion: " + maxDepthUsingRecursion(root));
        System.out.println("maxDepthUsingRecursion2: " + maxDepthUsingRecursion2(root));
        System.out.println("maxDepthUsingBfsQueue: " + maxDepthUsingBfsQueue(root));
    }

    public static int maxDepthUsingDFS(TreeNode root) {
        if(root == null) return 0;
        return dfs(root, 1); // dfs(root, 1) && return depth-1; in dfs() or dfs(root, 0) and return depth; in dfs()
    }
    private static int dfs(TreeNode node, int depth) {
        if(node==null) return depth-1;

        int leftDepth = dfs(node.left, depth+1);
        int rightDepth = dfs(node.right, depth+1);

        depth = Math.max(depth, leftDepth);
        depth = Math.max(depth, rightDepth);

        return depth;
    }



    public static int maxDepthUsingRecursion(TreeNode root) {
        if(root == null) return 0;
        int left = maxDepthUsingRecursion(root.left);
        int right = maxDepthUsingRecursion(root.right);
        return Math.max(left, right) + 1; // +1 for current level
    }

    public static int maxDepthUsingRecursion2(TreeNode root) {
        if(root == null) return 0;
        return Math.max(maxDepthUsingRecursion2(root.left), maxDepthUsingRecursion2(root.right)) +1;
    }



    public static int maxDepthUsingBfsQueue(TreeNode root) {
        if(root == null) return 0;
        Queue<TreeNode> q = new LinkedList<>();
        q.offer(root);
        int depth = 0;
        while(!q.isEmpty()) {
            int size = q.size();
            for(int i=0; i<size; i++) { // constant "i<size" means one level
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
