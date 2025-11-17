package Algorithms.BinaryTrees;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 * In Tree, maxDepth and maxHeight are same
 *
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 19 Jan 2025
 * @link 104. Maximum Depth of Binary Tree <a href="https://leetcode.com/problems/maximum-depth-of-binary-tree/">LeetCode Link</a>
 * @topics Tree, Binary Tree, DFS, BFS, Recursion

                                    3
                                   / \
                                  9  20
                                    /  \
                                   15   7
 */
public class MaximumDepthOfBinaryTree {
    public static class TreeNode {int val;TreeNode left, right;TreeNode() {}TreeNode(int val) { this.val = val; }TreeNode(int val, TreeNode left, TreeNode right) {this.val = val;this.left = left;this.right = right;}}
    public static void main(String[] args) {
        TreeNode root = new TreeNode(3);
        root.left = new TreeNode(9);
        root.right = new TreeNode(20);
        root.right.left = new TreeNode(15);
        root.right.right = new TreeNode(7);
        System.out.println("maxDepth using DFS: " + maxDepthUsingDfs(root));
        System.out.println("maxDepth using Recursion: " + maxDepthUsingRecursion(root));
        System.out.println("maxDepth using Recursion2: " + maxDepthUsingRecursion2(root));
        System.out.println("maxDepth using BFS Queue: " + maxDepthUsingBfsQueue(root));
        System.out.println("maxDepth using Stack: " + maxDepthUsingStack(root));
    }


    public static int maxDepthUsingDfs(TreeNode root) {
        return dfs(root, 0);
    }
    private static int dfs(TreeNode node, int prevDepth) {
        if(node==null) return prevDepth;

        int leftDepth = dfs(node.left, prevDepth+1);
        int rightDepth = dfs(node.right, prevDepth+1);

        return Math.max(leftDepth, rightDepth); // max total depth
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



    public static int maxDepthUsingDfs2(TreeNode root) {
        if(root == null) return 0;
        return dfs2(root, 1); // dfs2(root, 1) && return depth-1; in dfs2() or dfs2(root, 0) and return depth; in dfs2()
    }
    private static int dfs2(TreeNode node, int depth) {
        if(node==null) return depth-1;

        int leftDepth = dfs2(node.left, depth+1);
        int rightDepth = dfs2(node.right, depth+1);

        depth = Math.max(depth, leftDepth);
        depth = Math.max(depth, rightDepth);

        return depth;
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




    public static int maxDepthUsingBfsQueue2(TreeNode root) {
        class Pair {
            final TreeNode node;
            final int depth;
            Pair(TreeNode node, int depth) {
                this.node = node;
                this.depth = depth;
            }
        }

        if(root == null) return 0;
        Queue<Pair> q = new LinkedList<>();
        q.offer(new Pair(root, 1));
        int depth = 0;
        while(!q.isEmpty()) {
            Pair pair = q.poll();
            TreeNode node = pair.node;
            depth = pair.depth;
            if(node.left != null) q.add(new Pair(node.left, depth+1));
            if(node.right != null) q.add(new Pair(node.right, depth+1));
        }
        return depth;
    }



    public static int maxDepthUsingStack(TreeNode root) {
        class Pair {
            final TreeNode node;
            final int depth;
            Pair(TreeNode node, int depth) {
                this.node = node;
                this.depth = depth;
            }
        }

        if (root == null) return 0;

        Stack<Pair> stack = new Stack<>();
        stack.push(new Pair(root, 1));
        int depth = 0;
        while (!stack.isEmpty()) {
            Pair pair = stack.pop();
            TreeNode node = pair.node;
            depth = Math.max(depth, pair.depth);
            if (node.left != null) stack.push(new Pair(node.left, pair.depth + 1));
            if (node.right != null) stack.push(new Pair(node.right, pair.depth + 1));
        }
        return depth;
    }
}
