package Algorithms.BinaryTrees;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;

/**
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 23 Sept 2024
 * @link 226. Invert Binary Tree <a href="https://leetcode.com/problems/invert-binary-tree/">LeetCode Link</a>
 * @topics Tree, BinaryTree, BFS, DFS, Recursion
*/
public class InvertBinaryTree {
    public static class TreeNode {int val;TreeNode left, right;TreeNode() {}TreeNode(int val) { this.val = val; }TreeNode(int val, TreeNode left, TreeNode right) {this.val = val;this.left = left;this.right = right;}}
    public static void main(String[] args) {
        /*
                   1
                  / \
                 2   3
                / \ / \
               4  5 6  7
              / \ /
             8  9 10

                   1
                  / \
                 3   2
                / \ / \
               7  6 5  4
                   /\  /\
               null 10 9 8


             1 3 1 5 4 7 6 9 8 null 10
        */
        int[] nums = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        TreeNode root = buildTree(nums);
        System.out.println("Original Tree: ");
        printTree(root).forEach(System.out::println);

        root = invertTreeUsingRecursion(root);
        System.out.println("invertTree using recursion: ");
        printTree(root).forEach(System.out::println);

        root = buildTree(nums);
        root = invertTreeUsingDfs(root);
        System.out.println("invertTree using dfs: ");
        printTree(root).forEach(System.out::println);

        root = buildTree(nums);
        root = invertTreeUsingBfs(root);
        System.out.println("invertTree using bfs: ");
        printTree(root).forEach(System.out::println);


        root = buildTree(nums);
        root = invertTreeUsingDfsStack(root);
        System.out.println("invertTree using dfs stack: ");
        printTree(root).forEach(System.out::println);
    }


    public static TreeNode invertTreeUsingRecursion(TreeNode root) {
        if (root == null) return null;
        TreeNode temp = root.left;
        root.left = root.right;
        root.right = temp;
        invertTreeUsingRecursion(root.left);
        invertTreeUsingRecursion(root.right);
        return root;
    }



    public static TreeNode invertTreeUsingRecursion2(TreeNode root) {
        if (root == null) return null;
        TreeNode left = invertTreeUsingRecursion2(root.left);
        TreeNode right = invertTreeUsingRecursion2(root.right);
        root.left = right;
        root.right = left;
        return root;
    }





    public static TreeNode invertTreeUsingDfs(TreeNode root) {
        dfs(root);
        return root;
    }
    private static void dfs(TreeNode node) { // swapNodes()
        if(node == null) return;
        TreeNode temp = node.left;
        node.left = node.right;
        node.right = temp;
        dfs(node.left);
        dfs(node.right);
    }






    public static TreeNode invertTreeUsingBfs(TreeNode root) {
        if(root == null) return null;

        Queue<TreeNode> q = new LinkedList<>(); // Note: q = new ArrayDeque<>(); q.add(null); throws NullPointerException
        q.add(root);

        while(!q.isEmpty()) {
            TreeNode node = q.poll();
            if(node == null) continue;
            TreeNode temp = node.left;
            node.left = node.right;
            node.right = temp;
            q.add(node.left); // or if(node.left != null) q.add(node.left);
            q.add(node.right);
        }

        return root;
    }



    public static TreeNode invertTreeUsingDfsStack(TreeNode root) {
        if(root == null) return null;

        Stack<TreeNode> stack = new Stack<>();
        stack.push(root);

        while(!stack.isEmpty()) {
            TreeNode node = stack.pop();
            if(node == null) continue;
            TreeNode temp = node.left;
            node.left = node.right;
            node.right = temp;
            stack.push(node.left);
            stack.push(node.right);
        }

        return root;
    }


















    public static TreeNode buildTree(int[] nums) {
        if (nums == null || nums.length == 0) return null;
        TreeNode root = new TreeNode(nums[0]);
        Queue<TreeNode> q = new LinkedList<>();
        q.add(root);
        int i = 1;
        while (i < nums.length) {
            TreeNode curr = q.remove();
            curr.left = new TreeNode(nums[i++]);
            q.add(curr.left);
            if (i < nums.length) {
                curr.right = new TreeNode(nums[i++]);
                q.add(curr.right);
            }
        }
        return root;
    }


    public static List<List<String>> printTree(final TreeNode root) {
        final int width = (int) Math.pow(2, getHeight(root)) - 1;
        final List<List<String>> result = new ArrayList<>();

        dfs(root, result, 0, width, 0, width);

        return result;
    }

    private static void dfs(final TreeNode root, final List<List<String>> result, final int l, final int r, final int level, final int width) {
        if(root != null) {
            if(level >= result.size()) {
                result.add(new ArrayList<>());

                for(int i = 0; i < width; ++i)
                    result.get(level).add("");
            }

            final int mid = (l + r) / 2;

            result.get(level).set(mid, String.valueOf(root.val));

            dfs(root.left, result, l, mid, level + 1, width);
            dfs(root.right, result, mid, r, level + 1, width);
        }
    }

    private static int getHeight(final TreeNode root) {
        if(root == null) return 0;
        return Math.max(getHeight(root.left), getHeight(root.right)) + 1;
    }

}

