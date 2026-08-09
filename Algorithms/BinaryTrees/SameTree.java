package Algorithms.BinaryTrees;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 17 Nov 2025
 * @link 100. Same Tree <a href="https://leetcode.com/problems/same-tree/">LeetCode Link</a>
 * @topics Tree, Binary Tree, DFS, BFS, Recursion
 */
public class SameTree {
    public static class TreeNode {int val; TreeNode left, right; TreeNode() {} TreeNode(int val) { this.val = val; } TreeNode(int val, TreeNode left, TreeNode right) { this.val = val; this.left = left; this.right = right; }}

    public static void main(String[] args) {
        TreeNode p = new TreeNode(1);
        p.left = new TreeNode(2);
        p.right = new TreeNode(3);

        TreeNode q = new TreeNode(1);
        q.left = new TreeNode(2);
        q.right = new TreeNode(3);

        System.out.println("isSameTree using recursion => " + isSameTreeUsingRecursion(p, q));
        System.out.println("isSameTree using dfs => " + isSameTreeUsingDfs(p, q));
        System.out.println("isSameTree using bfs => " + isSameTreeUsingBfs(p, q));
    }




    public static boolean isSameTreeUsingRecursion(TreeNode p, TreeNode q) { // isSameTreeUsingRecursion
        if (p == null && q == null) return true;
        else if (p == null || q == null) return false;
        else if (p.val != q.val) return false;

        boolean left = isSameTreeUsingRecursion(p.left, q.left);
        boolean right = isSameTreeUsingRecursion(p.right, q.right);
        return left && right;
    }






    public static boolean isSameTreeUsingDfs(TreeNode p, TreeNode q) { // isSameTreeUsingDfs
        AtomicBoolean isSame = new AtomicBoolean(true);
        dfs(p, q, isSame);
        return isSame.get();
    }
    private static void dfs(TreeNode p, TreeNode q, AtomicBoolean isSame) {
        if (!isSame.get() || p == null && q == null) return;
        else if (p == null || q == null || p.val != q.val) {
            isSame.set(false);
            return;
        }
        dfs(p.left, q.left, isSame);
        dfs(p.right, q.right, isSame);
    }






    public static boolean isSameTreeUsingBfs(TreeNode p, TreeNode q) { // isSameTreeUsingBfs
        Queue<TreeNode> pQueue = new LinkedList<>();
        Queue<TreeNode> qQueue = new LinkedList<>();

        pQueue.add(p);
        qQueue.add(q);

        while (!pQueue.isEmpty() || !qQueue.isEmpty()) {
            if (pQueue.size() != qQueue.size()) return false;

            int size = pQueue.size();
            for(int i=0; i<size; i++) {
                TreeNode pNode = pQueue.poll();
                TreeNode qNode = qQueue.poll();

                if (pNode == null && qNode == null) continue;
                else if (pNode == null || qNode == null) return false;
                else if (pNode.val != qNode.val) return false;

                pQueue.add(pNode.left);
                pQueue.add(pNode.right);
                qQueue.add(qNode.left);
                qQueue.add(qNode.right);
            }
        }

        return true;
    }




    public static boolean isSameTreeUsingBfs2(TreeNode p, TreeNode q) {
        if (p == null || q == null) return p == q;

        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(p);
        queue.add(q);

        while (!queue.isEmpty()) {
            TreeNode node1 = queue.poll();
            TreeNode node2 = queue.poll();

            if (node1 == null || node2 == null) return node1 == node2;
            if (node1.val != node2.val) return false;

            queue.add(node1.left);
            queue.add(node2.left);
            queue.add(node1.right);
            queue.add(node2.right);
        }

        return true;
    }
}
