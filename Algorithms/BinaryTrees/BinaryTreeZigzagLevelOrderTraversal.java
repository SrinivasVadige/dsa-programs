package Algorithms.BinaryTrees;

import java.util.*;

/**
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 13 Dec 2025
 * @link 103. Binary Tree Zigzag Level Order Traversal <a href="https://leetcode.com/problems/binary-tree-zigzag-level-order-traversal/">LeetCode Link</a>
 * @topics Tree, Binary Tree, DFS, BFS
 * @companies Google(7), Amazon(3), Sigmoid(2), Microsoft(5), Meta(2), Bloomberg(2), Goldman Sachs(2), Oracle(8), Adobe(6), Walmart Labs(6), LinkedIn(4), TikTok(4), Citadel(4), Yandex(4), ByteDance(2), Accenture(2), ServiceNow(2)
 */
public class BinaryTreeZigzagLevelOrderTraversal {
    public static class TreeNode {int val;TreeNode left, right;TreeNode() {}TreeNode(int val) { this.val = val; }TreeNode(int val, TreeNode left, TreeNode right) {this.val = val;this.left = left;this.right = right;}}

    public static void main(String[] args) {
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(3);
        root.left.left = new TreeNode(4);
        root.right.right = new TreeNode(5);
        /*
                        1
                       / \
                      2   3
                     /     \
                    4       5
         */

        System.out.println("zigzagLeverOrder using BFS 1 -> " + zigzagLevelOrderUsingBfs1(root));
        System.out.println("zigzagLeverOrder using BFS 2 -> " + zigzagLevelOrderUsingBfs2(root));
        System.out.println("zigzagLeverOrder using BFS 3 -> " + zigzagLevelOrderUsingBfs3(root));
        System.out.println("zigzagLeverOrder using DFS 1 -> " + zigzagLevelOrderUsingDfs1(root));
        System.out.println("zigzagLeverOrder using DFS 2 -> " + zigzagLevelOrderUsingDfs2(root));
    }



    public static List<List<Integer>> zigzagLevelOrderUsingBfs1(TreeNode root) {
        List<List<Integer>> list = new ArrayList<>();
        if (root == null) return list;

        Queue<TreeNode> q = new LinkedList<>();
        q.add(root);

        int level = 1; // or get level by list.size()
        while(!q.isEmpty()) {
            int n = q.size();
            List<Integer> subList = new ArrayList<>();

            while (n-- > 0) {
                TreeNode node = q.poll();
                subList.add(node.val);

                    if(node.left != null) q.add(node.left);
                    if(node.right != null) q.add(node.right);
            }
            if (level % 2 == 0) Collections.reverse(subList);
            list.add(subList);
            level++;
        }
        return list;
    }




    public static List<List<Integer>> zigzagLevelOrderUsingBfs2(TreeNode root) {
        List<List<Integer>> results = new ArrayList<>();
        if (root == null) return results;

        Queue<TreeNode> q = new LinkedList<>();
        q.add(root);
        q.add(null); // Sentinel node

        boolean isOrder = true; // or list.size() % 2 == 0
        List<Integer> subList = new LinkedList<>();

        while (!q.isEmpty()) {
            TreeNode node = q.poll();
            if (node != null) {
                if (isOrder) subList.add(node.val);
                else subList.add(0, node.val); // O(1) time, as we use LinkedList subList instead of ArrayList

                if (node.left != null) q.add(node.left);
                if (node.right != null) q.add(node.right);
            } else { // Level end
                results.add(subList);
                subList = new LinkedList<>();
                if (!q.isEmpty()) q.add(null); // Sentinel node
                isOrder = !isOrder;
            }
        }
        return results;
    }





    public static List<List<Integer>> zigzagLevelOrderUsingBfs3(TreeNode root) {
        List<List<Integer>> list = new ArrayList<>();
        if (root==null) return list;
        Deque<TreeNode> dq = new LinkedList<>();
        dq.add(root);

        while(!dq.isEmpty()) {
            int n = dq.size();
            int level = list.size() + 1;
            List<Integer> subList = new ArrayList<>();
            Deque<TreeNode> nextDq = new LinkedList<>();
            while(n-- > 0) {
                TreeNode node = dq.pollFirst();
                subList.add(node.val);
                if (level % 2 == 1) {
                    if (node.left != null) nextDq.addFirst(node.left);
                    if (node.right != null) nextDq.addFirst(node.right);
                } else {
                    if (node.right != null) nextDq.addFirst(node.right);
                    if (node.left != null) nextDq.addFirst(node.left);
                }
            }
            list.add(subList);
            dq = nextDq;
        }

        return list;
    }






    static List<List<Integer>> list;
    public static List<List<Integer>> zigzagLevelOrderUsingDfs1(TreeNode root) {
        list = new ArrayList<>();
        dfs(root, 0);
        for (int i=0; i<list.size(); i++) {
            if (i % 2 == 1) Collections.reverse(list.get(i));
        }
        return list;
    }
    private static void dfs(TreeNode node, int i) {
        if (node == null) return;

        if (list.size() == i) list.add(new ArrayList<>());
        list.get(i).add(node.val);
        dfs(node.left, i+1);
        dfs(node.right, i+1);
    }




    public static List<List<Integer>> zigzagLevelOrderUsingDfs2(TreeNode root) {
        List<List<Integer>> results = new ArrayList<>();
        if (root == null) return results;
        dfs(root, 0, results);
        return results;
    }
    private static void dfs(TreeNode node, int level, List<List<Integer>> results) {
        if (level >= results.size()) {
            LinkedList<Integer> subList = new LinkedList<>();
            subList.add(node.val);
            results.add(subList);
        } else {
            if (level % 2 == 0) results.get(level).add(node.val);
            else results.get(level).add(0, node.val); // O(1) time, as we use LinkedList subList instead of ArrayList
        }

        if (node.left != null) dfs(node.left, level + 1, results);
        if (node.right != null) dfs(node.right, level + 1, results);
    }
}
