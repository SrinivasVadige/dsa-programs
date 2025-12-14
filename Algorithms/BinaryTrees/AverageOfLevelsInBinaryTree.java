package Algorithms.BinaryTrees;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 13 Dec 2025
 * @link 637. Average of Levels in Binary Tree <a href="https://leetcode.com/problems/average-of-levels-in-binary-tree/">LeetCode Link</a>
 * @topics Tree, Binary Tree, DFS, BFS
 * @companies Meta(4), Google(4), Microsoft(2), Amazon(2)
 */
public class AverageOfLevelsInBinaryTree {
    public static class TreeNode {int val;TreeNode left, right;TreeNode() {}TreeNode(int val) { this.val = val; }TreeNode(int val, TreeNode left, TreeNode right) {this.val = val;this.left = left;this.right = right;}}

    public static void main(String[] args) {
        TreeNode root = new TreeNode(3);
        root.left = new TreeNode(9);
        root.right = new TreeNode(20);
        root.right.left = new TreeNode(15);
        root.right.right = new TreeNode(7);

        System.out.println("averageOfLevels using BFS: " + averageOfLevelsUsingBfs1(root));
        System.out.println("averageOfLevels using DFS: " + averageOfLevelsUsingDfs1(root));
    }

    public static List<Double> averageOfLevelsUsingBfs1(TreeNode root) {
        List<Double> list = new ArrayList<>();

        Queue<TreeNode> q = new LinkedList<>();
        q.add(root);

        while (!q.isEmpty()) {
            int n = q.size();
            double sum = 0;

            for (int i=0; i<n; i++) {
                TreeNode node = q.poll();
                sum += node.val;

                if (node.left != null) q.add(node.left);
                if (node.right != null) q.add(node.right);
            }

            list.add(sum/n);
        }

        return list;
    }






    public static List<Double> averageOfLevelsUsingBfs2(TreeNode root) {
        List<Double> list = new ArrayList<>();
        Queue<TreeNode> q = new LinkedList<>();
        q.add(root);
        while (!q.isEmpty()) {
            long sum = 0, n = 0;
            Queue<TreeNode> nextQ = new LinkedList<>();
            while (!q.isEmpty()) {
                TreeNode node = q.remove();
                sum += node.val;
                n++;
                if (node.left != null) nextQ.add(node.left);
                if (node.right != null) nextQ.add(node.right);
            }
            q = nextQ;
            list.add(sum * 1.0 / n);
        }
        return list;
    }






    static List<Double> list;
    static int[] levelCount;
    public static List<Double> averageOfLevelsUsingDfs1(TreeNode root) {
        int h = getHeight(root);
        list = new ArrayList<>();
        levelCount = new int[h];
        for (int i=0; i<h; i++) list.add(0d);
        dfs(root, 0);

        for (int i=0; i<h; i++) list.set(i, list.get(i)/levelCount[i]);

        return list;
    }
    private static void dfs(TreeNode node, int i) { // "i" == "level"
        if (node == null) return;

        list.set(i, list.get(i) + node.val);
        levelCount[i]++;

        dfs(node.left, i+1);
        dfs(node.right, i+1);
    }

    private static int getHeight(TreeNode node) {
        if (node == null) return 0;
        return 1 + Math.max(getHeight(node.left), getHeight(node.right));
    }







    public static List<Double> averageOfLevelsUsingDfs2(TreeNode root) {
        List<Integer> levelCount = new ArrayList<>();
        List<Double> res = new ArrayList<>();
        dfs2(root, 0, res, levelCount);
        for (int i = 0; i < res.size(); i++) res.set(i, res.get(i) / levelCount.get(i));
        return res;
    }
    public static void dfs2(TreeNode t, int i, List<Double> sum, List<Integer> levelCount) { // "i" == "level"
        if (t == null) return;

        if (i == sum.size()) { // here we traversed curr level for the first time
            sum.add(1.0 * t.val);
            levelCount.add(1);
        } else {
            sum.set(i, sum.get(i) + t.val);
            levelCount.set(i, levelCount.get(i) + 1);
        }

        dfs2(t.left, i + 1, sum, levelCount);
        dfs2(t.right, i + 1, sum, levelCount);
    }

}
