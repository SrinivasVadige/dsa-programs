package Algorithms.BinaryTrees;

import java.util.*;

/**
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 30 Nov 2025
 * @link 112. Path Sum <a href="https://leetcode.com/problems/path-sum/">LeetCode Link</a>
 * @topics Tree, Binary Tree, DFS, BFS
 * @companies Google(2), Bloomberg(2), Amazon(4), Meta(2), Microsoft(2), Adobe(2), TikTok(2), Goldman Sachs(2), Datadog(2)
 */
public class PathSum {
    public static class TreeNode {int val; TreeNode left; TreeNode right; public TreeNode(int val){this.val = val;}}

    public static void main(String[] args) {
        TreeNode root = new TreeNode(5);
        root.left = new TreeNode(4);
        root.right = new TreeNode(8);
        root.left.left = new TreeNode(11);
        root.right.left = new TreeNode(13);
        root.right.right = new TreeNode(4);
        root.left.left.left = new TreeNode(7);
        root.left.left.right = new TreeNode(2);
        root.right.right.left = new TreeNode(5);
        root.right.right.right = new TreeNode(1);

        System.out.println("TreeNode: " + printTree(root));

        System.out.println("pathSum Using Recursion (root, 22): " + pathSumUsingRecursion(root, 22));
        System.out.println("pathSum Using Dfs (root, 22) " + hasPathSumUsingDfs(root, 22));
        System.out.println("pathSum Using Stack Dfs (root, 22) " + hasPathSumUsingStackDfs(root, 22));
        System.out.println("pathSum Using Queue Bfs (root, 22) " + hasPathSumUsingQueueBfs(root, 22));
    }

    public static boolean pathSumUsingRecursion(TreeNode root, int targetSum) {
        if (root == null) return false;
        if (root.left == null && root.right == null) return root.val == targetSum;
        return pathSumUsingRecursion(root.left, targetSum - root.val) || pathSumUsingRecursion(root.right, targetSum - root.val);
    }



    public static boolean hasPathSumUsingRecursion2(TreeNode root, int sum) {
        if (root == null) return false;

        sum -= root.val;
        if ((root.left == null) && (root.right == null)) return (sum == 0);
        return hasPathSumUsingRecursion2(root.left, sum) || hasPathSumUsingRecursion2(root.right, sum);
    }





    public static boolean hasPathSumUsingDfs(TreeNode root, int targetSum) {
        return dfs(root, targetSum);
    }
    private static boolean dfs(TreeNode node, int sum) {
        if (node == null) return false;
        else if(node.left == null && node.right == null && node.val == sum) return true;
        return dfs(node.left, sum-node.val) || dfs(node.right, sum-node.val);
    }





    /**
                            5
                         /     \
                        4       8
                      /        / \
                     11      13   4
                    /  \           \
                   7    2           1


        [5, null, 4, 8, null, 13, 4, 11, null, 7, 2, 1, ]
             l1          l2               l3

     */
    public static boolean hasPathSumUsingStackDfs(TreeNode root, int targetSum) {
        if (root == null) return false;

        class Pair {final TreeNode node; final int sum; Pair(TreeNode node, int sum){this.node=node; this.sum=sum;}}
        Stack<Pair> stack = new Stack<>();
        stack.push(new Pair(root, targetSum-root.val));

        while (!stack.isEmpty()) {
            int n = stack.size();
            while(n-- > 0) {
                Pair pair = stack.pop();
                TreeNode node = pair.node;
                int sum = pair.sum;

                if (node.left == null && node.right == null && sum == 0) return true;

                if (node.left != null) stack.push(new Pair(node.left, sum - node.left.val));
                if (node.right != null) stack.push(new Pair(node.right, sum - node.right.val));
            }
        }
        return false;
    }






    public static boolean hasPathSumUsingStackDfs2(TreeNode root, int sum) {
        if (root == null) return false;

        Deque<TreeNode> node_stack = new LinkedList<>();
        Deque<Integer> sum_stack = new LinkedList<>();
        node_stack.add(root);
        sum_stack.add(sum - root.val);

        TreeNode node;
        int curr_sum;
        while (!node_stack.isEmpty()) {
            node = node_stack.pollLast(); // same like stack.pop()
            curr_sum = sum_stack.pollLast();
            if (node.right == null && node.left == null && curr_sum == 0) return true;

            if (node.right != null) {
                node_stack.add(node.right);
                sum_stack.add(curr_sum - node.right.val);
            }
            if (node.left != null) {
                node_stack.add(node.left);
                sum_stack.add(curr_sum - node.left.val);
            }
        }
        return false;
    }





public static boolean hasPathSumUsingQueueBfs(TreeNode root, int targetSum) {
    if (root == null) return false;

    Queue<TreeNode> nodeQ = new LinkedList<>();
    Queue<Integer> sumQ = new LinkedList<>();

    nodeQ.add(root);
    sumQ.add(targetSum - root.val);

    while (!nodeQ.isEmpty()) {
        TreeNode node = nodeQ.poll();
        int sum = sumQ.poll();

        if (node.left == null && node.right == null && sum == 0) {
            return true;
        }

        if (node.left != null) {
            nodeQ.add(node.left);
            sumQ.add(sum - node.left.val);
        }
        if (node.right != null) {
            nodeQ.add(node.right);
            sumQ.add(sum - node.right.val);
        }
    }
    return false;
}












    public static List<String> printTree(TreeNode root) {
        List<String> result = new ArrayList<>();
        if (root == null) return result;
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                TreeNode node = queue.poll();
                if (node == null) {
                    result.add("null");
                } else {
                    result.add(String.valueOf(node.val));
                    queue.add(node.left);
                    queue.add(node.right);
                }
            }
        }
        return result;
    }
}
