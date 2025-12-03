package Algorithms.BinaryTrees;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

/**
 * A path in a binary tree is a sequence of nodes where each pair of adjacent nodes in the sequence has an edge connecting them. A node can only appear in the sequence at most once. Note that the path does not need to pass through the root
 * The path sum of a path is the sum of the node's values in the path.
 * Given the root of a binary tree, return the maximum path sum of any non-empty path.
 *
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 11 Feb 2025
 * @link 124. Binary Tree Maximum Path Sum <a href="https://leetcode.com/problems/binary-tree-maximum-path-sum/">LeetCode Link</a>
 * @topics Tree, Binary Tree, Dynamic Programming, DFS
 * @companies Amazon(8), DoorDash(8), Bloomberg(3), Salesforce(3), Google(2), Meta(2), TikTok(2), Microsoft(22), Oracle(10), Yandex(7), Apple(4), Flipkart(4), Nvidia(4), Arista Networks(3), Citadel(3), Datadog(3), tcs(2)


     THOUGHTS:
     ---------
     1) Calculate each sum using pre-order traversal for each node? --> TLE?
     2) if left node sub-sum is smaller than both parent_sum and right node then ignore it & vice versa or if parent_sum is smaller then both left and right
     3) Check subtree sum
     4) And carry forward the current max sum to the parent but we need to exclude either left or right subtree sum

     top down dp? as it is a subproblem
     using recursion, we can traverse from leaf to node instead of node to leaf
 *
 */
public class BinaryTreeMaximumPathSum {
    public static class TreeNode {int val;TreeNode left, right;TreeNode() {}TreeNode(int val) { this.val = val; }TreeNode(int val, TreeNode left, TreeNode right) {this.val = val;this.left = left;this.right = right;}}
    public static void main(String[] args) {
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(3);
        root.left.left = new TreeNode(4);
        root.left.right = new TreeNode(5);
        root.right.left = new TreeNode(6);
        root.right.right = new TreeNode(7);
        System.out.println("maxPathSum Using BruteForce With Memo => " + maxPathSumUsingBruteForceWithMemo(root));
        System.out.println("maxPathSum(root) => " + maxPathSum1(root));
    }


    /**
     * @TimeComplexity O(n^2)
     * @SpaceComplexity O(n) + O(w) --> O(n) where w is the width of the tree - for Queue size

        Here we traverse each node and consider it as the root node i.e., it'll be the top of the bend in the full path

        Use Queue to traverse each node
        Ignore -ve contributions but always check for maxSum with each node value


     */
    public static int maxPathSumUsingBruteForce(TreeNode root) {
        int max = root.val;
        Queue<TreeNode> q = new LinkedList<>();
        q.add(root);
        while(!q.isEmpty()) {
            TreeNode node = q.poll();
            if (node.left != null) q.add(node.left);
            if (node.right != null) q.add(node.right);
            int left = helper(node.left, node.val); // topSum = node.val i.e., currNode value is already added
            int right = helper(node.right, node.val);
            max = Math.max(max, node.val); // only curr node - don't consider left and right subtrees -> always check for maxSum with each node value
            max = Math.max(max, left); // curr node + left subtree
            max = Math.max(max, right); // curr node + right subtree
            max = Math.max(max, left + right - node.val); // full path - (curr node + left + right subtrees)
        }
        return max;
    }
    private static int helper(TreeNode node, int topSum) {
        if (node == null) return topSum;

        int left = helper(node.left, topSum + node.val);
        int right = helper(node.right, topSum + node.val);

        int bestChild = Math.max(left, right); // parent + bestChild subtree
        return Math.max(bestChild, topSum); // ignore child subtree if it is less than topSum
    }





    /**
     * @TimeComplexity O(n)
     * @SpaceComplexity O(n) + O(w) + O(h) = O(n) where w & h are the width (Queue) & height (Recursion Stack) of the tree

        Here don't pass topSum to helper method & it's easy to memo the result as we don't need to calculate "node + topSum" as key in map

        Use Queue to traverse each node

        🔥 To calculate maxPathSum, we definitely need a superParent node / curr root node - the bent node
        Without this root node, we can't traverse the path -> we can only traverse from parent to child but not vice versa

       We can't return fullPathSum to the parent -> we must return maxDir (currNodeVal + bestChild)


     */
    static Map<TreeNode, Integer> map;
    public static int maxPathSumUsingBruteForceWithMemo(TreeNode root) {
        map = new HashMap<>();
        int max = root.val;
        Queue<TreeNode> q = new LinkedList<>();
        q.add(root);
        while (!q.isEmpty()) {
            TreeNode node = q.poll();
            if (node.left != null) q.add(node.left);
            if (node.right != null) q.add(node.right);
            int left = helper(node.left);
            int right = helper(node.right);
            int through = node.val + Math.max(0, left) + Math.max(0, right); // in through fullPathSum -> if a child is -ve then ignore it
            max = Math.max(max, through); // full path - best through this node (bend)
        }
        return max;
    }
    private static int helper(TreeNode node) {
        if (node == null) return 0;
        else if (map.containsKey(node)) return map.get(node);

        int left = helper(node.left);
        int right = helper(node.right);
        int bestChild = Math.max(left, right);
        bestChild = Math.max(0, bestChild); // ignore -ve contributions

        int maxDir = bestChild + node.val; // returns "best downward path from this node" - but not the through fullPathSum
        map.put(node, maxDir);
        return maxDir; // maxDirection = bestChild + currNodeVal
    }





    /**
     * @TimeComplexity O(n)
     * @SpaceComplexity O(h) where h is the height of the tree - Recursion Stack

        In the above DFS approach, we're already checking all nodes when we traverse "helper(root)" node in the 1st recursion itself

        Here we just have 2 things:
        1) maxSum - traverse each node and check for maxSum with fullPathSum
        2) maxDir (currNodeVal + bestChild) - return maxDirection to parent

       We can't return fullPathSum to the parent -> we must return maxDir (currNodeVal + bestChild)

     */
    private static int ans;
    public static int maxPathSum1(TreeNode root) {
        ans = root.val;
        dfs(root);
        return ans;
    }
    private static int dfs(TreeNode node) {
        if (node == null) return 0;

        int leftDir = dfs(node.left);
        int rightDir = dfs(node.right);

        int maxDir = Math.max(rightDir, leftDir) + node.val; // bestChild + currNodeVal
        maxDir = Math.max(maxDir, node.val); // if bestChild is -ve then ignore it

        int fullPathSum = Math.max(maxDir, node.val+leftDir+rightDir); // full path - best through this node (bend)
        ans = Math.max(ans, fullPathSum);

        return maxDir; // maxDirection = bestChild + currNodeVal
    }







    /**
     * @TimeComplexity O(n)
     * @SpaceComplexity O(h) where h is the height of the tree - Recursion Stack
     */
    private static int maxSum;
    public static int maxPathSum2(TreeNode root) {
        maxSum = Integer.MIN_VALUE;
        gainFromSubtree(root);
        return maxSum;
    }
    private static int gainFromSubtree(TreeNode root) {
        if (root == null) return 0;

        int lGain = Math.max(gainFromSubtree(root.left), 0); // ignore -ve contributions
        int rGain = Math.max(gainFromSubtree(root.right), 0); // ignore -ve contributions

        maxSum = Math.max(maxSum, lGain + rGain + root.val); // full path - best through this node (bend)

        return Math.max(lGain, rGain) + root.val; // bestChild + currNodeVal
    }
}
