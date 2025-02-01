package Algorithms.BinaryTrees;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;
import java.util.TreeSet;

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
 */
public class KthSmallestElementInBST {
    static class TreeNode {int val;TreeNode left, right;TreeNode() {}TreeNode(int val) { this.val = val; }TreeNode(int val, TreeNode left, TreeNode right) {this.val = val;this.left = left;this.right = right;}}

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
        System.out.println("kthSmallestUsingStack: " + kthSmallestUsingStack(root, 1));
    }

    // here using stack we we traverse only small nodes, but in below inOrderTrav, dfsTrav => we traverse all nodes
    public static int kthSmallestUsingStack(TreeNode root, int k) {
        Stack<TreeNode> stack = new Stack<>();
        TreeNode trav = root;
        while (trav != null || !stack.isEmpty()) {
            // traverse to left most
            while (trav != null) {
                stack.push(trav);
                trav = trav.left;
            }
            trav = stack.pop(); // pop smallest in stack
            if (--k == 0) return trav.val;
            trav = trav.right; // => if trav.right == null then above while(trav!=null){} will skip and pop the parent node and trav.right and trav will continue
        }
        return -1;
    }

    public int kthSmallestUsingInOrderList(TreeNode root, int k) {
        ArrayList<Integer> list=new ArrayList<>();
        inOrderList(root,list);
        return list.get(k-1);
    }
    public void inOrderList(TreeNode root,ArrayList<Integer> list){
        if(root==null) return;
        inOrderList(root.left, list);
        list.add(root.val);
        inOrderList(root.right, list);
    }

    public int kthSmallestUsingInOrderQueue(TreeNode root, int k) {
        Queue<Integer> q = new LinkedList<>();
        inOrderQueue(root, q);
        for (int i = 1; i<k; i++) q.poll();
        return q.poll();
    }

    private void inOrderQueue(TreeNode node, Queue<Integer> q) {
        if (node == null) return;
        inOrderQueue(node.left, q);
        q.add(node.val);
        inOrderQueue(node.right, q);
    }

    public int kthSmallestUsingDfsTreeSet(TreeNode root, int k) {
        TreeSet<Integer> ts = new TreeSet<>();
        dfs(root, ts);
        for (int i = 1; i<k; i++) ts.pollFirst();
        return ts.pollFirst();
    }

    private void dfs(TreeNode node, TreeSet<Integer> ts) {
        if (node == null) return;
        ts.add(node.val);
        dfs(node.left, ts);
        dfs(node.right, ts);
    }

    int ans = 0;
    int count = 0;
    public void findSmallest(TreeNode root, int k) {
        if(root != null && k != 0) {
            findSmallest(root.left, k);
            count++;
            if(k==count) {
                ans = root.val;
                return;
            }
            findSmallest(root.right,k);
        }
    }
    public int kthSmallest(TreeNode root, int k) {
        findSmallest(root,k);
        return ans;
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
