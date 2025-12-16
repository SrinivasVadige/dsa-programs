package Algorithms.BinaryTrees;

import java.util.*;

/**
 *  THOUGHTS
 * --------
 * 1) APPROACH 1: add all vals in PQ and for(int i=1; i<=k; i++) pq.pop()
 * 2) APPROACH 2:
 * In BST = inOrderTrav is ascending order?? NO
 * In BST traverse through small path and maintain a queue with k size by inserting the trav values and once you reach the end then .poll() the last ele
 *
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 01 Feb 2025
 * @link 230. Kth Smallest Element in a BST <a href="https://leetcode.com/problems/kth-smallest-element-in-a-bst/">LeetCode Link</a>
 * @topics Tree, BinaryTree, BinarySearchTree, DFS, InOrderTraversal
 * @companies Microsoft(2), Oracle(2), Uber(2), Google(8), Amazon(5), Meta(2), Bloomberg(2), LinkedIn(2), TikTok(2), Cisco(2)
 */
public class KthSmallestElementInBST {
    public static class TreeNode {int val;TreeNode left, right;TreeNode() {}TreeNode(int val) { this.val = val; }TreeNode(int val, TreeNode left, TreeNode right) {this.val = val;this.left = left;this.right = right;}}

    public static void main(String[] args) {
        TreeNode root = new TreeNode(3);
        TreeNode one = new TreeNode(1);
        TreeNode four = new TreeNode(4);
        TreeNode two = new TreeNode(2);
        /*
         *         3
         *        / \
         *       1   4
         *        \
         *         2
         */

        root.left = one;
        root.right = four;
        one.right = two;
        System.out.println("kthSmallest Using In-Order Dfs Recursion 1: " + kthSmallestUsingInOrderDfsRecursion1(root, 1));
    }




    private static int i;
    private static Integer res;
    public static int kthSmallestUsingInOrderDfsRecursion1(TreeNode root, int k) {
        i=0;
        dfs(root, k);
        return res;
    }

    private static void dfs(TreeNode node, int k) {
        if (node == null || res != null) return;
        dfs(node.left, k);
        if (++i == k) res = node.val;
        // System.out.printf("node: %s, i: %s, k : %s\n", node.val, i, k);
        dfs(node.right, k);
    }




    public static int kthSmallestUsingInOrderDfsRecursion2(TreeNode root, int k) {
        Stack<TreeNode> stack = new Stack<>(); // parentStack

        while (!stack.isEmpty() || root != null) {
            while (root != null) {
                // System.out.println(root.val); // pre-order
                stack.push(root);
                root = root.left;
            }

            root = stack.pop();
            // System.out.println(root.val); // in-order
            if (--k == 0) break; // or return root.val;
            root = root.right;
        }

        return root.val;
    }





    public static int kthSmallestUsingInOrderDfsAndStack(TreeNode root, int k) {
        Stack<Integer> stack = new Stack<>();
        dfs(root, k, stack);
        return stack.pop();
    }
    private static void dfs(TreeNode node, int k, Stack<Integer> stack) {
        // if (node == null) return; // O(n) time
        if (node == null || stack.size() == k) return; // -> O(h) time
        dfs(node.left, k, stack);
        stack.push(node.val);
        if (stack.size() > k) stack.pop();
        dfs(node.right, k, stack);
    }





    public static int kthSmallestUsingMorrisTraversal(TreeNode root, int k) {
        TreeNode curr = root;

        while (curr != null) {
            if (curr.left == null) {
                if (--k == 0) break; // BREAK 1 ---
                curr = curr.right;
            } else {
                TreeNode prev = curr.left; // predecessor
                while (prev.right != null && prev.right != curr) {
                    prev = prev.right;
                }

                if (prev.right == null) { // create thread
                    prev.right = curr;
                    curr = curr.left;
                } else { // remove thread
                    prev.right = null;
                    if (--k == 0) break; // BREAK 2 ---
                    curr = curr.right;
                }
            }
        }
        return curr.val;
    }






    public static int kthSmallestUsingInOrderDfsAndPq(TreeNode root, int k) {
        PriorityQueue<Integer> pq = new PriorityQueue<>(Collections.reverseOrder());
        dfs(root, k, pq);
        return pq.poll();
    }
    private static void dfs(TreeNode node, int k, PriorityQueue<Integer> pq) {
        if (node == null || pq.size() == k) return;
        dfs(node.left, k, pq);
        pq.add(node.val);
        if (pq.size() > k) pq.poll();
        dfs(node.right, k, pq);
    }






    public static int kthSmallestUsingInOrderList(TreeNode root, int k) {
        ArrayList<Integer> list=new ArrayList<>();
        inOrderList(root,list);
        return list.get(k-1);
    }
    public static void inOrderList(TreeNode root,ArrayList<Integer> list){
        if(root==null) return;
        inOrderList(root.left, list);
        list.add(root.val);
        inOrderList(root.right, list);
    }







    public static int kthSmallestUsingInOrderQueue(TreeNode root, int k) {
        Queue<Integer> q = new LinkedList<>();
        inOrderQueue(root, q);
        for (int i = 1; i<k; i++) q.poll();
        return q.poll();
    }

    private static void inOrderQueue(TreeNode node, Queue<Integer> q) {
        if (node == null) return;
        inOrderQueue(node.left, q);
        q.add(node.val);
        inOrderQueue(node.right, q);
    }







    public static int kthSmallestUsingInOrderDfsRecursion3(TreeNode root, int k) {
        Stack<TreeNode> stack = new Stack<>();

        while (!stack.isEmpty() || root != null) {
            if (root != null) {
                stack.push(root);
                root = root.left;
            } else {
                root = stack.pop();
                // System.out.println(root.val);
                if (--k == 0) break;
                root = root.right;
            }
        }
        return root.val;
    }





    public static int kthSmallestUsingInOrderDfsRecursion4(TreeNode root, int k) {
        Stack<TreeNode> stack = new Stack<>(); // parentStack

        mainLoop:
        while (root != null) {
            while (root != null) {
                // System.out.println(root.val); // pre-order
                stack.push(root);
                root = root.left;
            }

            while (!stack.isEmpty() && root == null) {
                root = stack.pop();
                // System.out.println(root.val); // in-order
                if (--k == 0) break mainLoop; // or return root.val;
                root = root.right;
            }
        }

        return root.val;
    }






    public static int kthSmallestUsingInOrderDfsRecursion5(TreeNode root, int k) {
        LinkedList<TreeNode> stack = new LinkedList<>(); // parentStack

        while (true) { // or while (!stack.isEmpty() || root != null) {
            while (root != null) {
                // System.out.println(root.val);
                stack.push(root);
                root = root.left;
            }
            root = stack.pop();
            // System.out.println(root.val);
            if (--k == 0) return root.val; // or break;
            root = root.right;
        }
    }











    /**
     * WON'T WORK
     *
     *                             1
     *                              \
     *                               2
     *
     * here when k=2 then kth smallest is 2 which is on right side not left child tree
     */
    public int kthSmallest2(TreeNode root, int k) {
        Queue<Integer> q = new LinkedList<>();
        TreeNode trav = root;
        for (trav = root; trav!=null; trav = trav.left) {
            q.add(trav.val);
            if (q.size() > k) q.poll();
        }
        return q.poll();
    }
}
