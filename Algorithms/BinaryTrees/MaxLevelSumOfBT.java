package Algorithms.BinaryTrees;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

/**
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 28 April 2025
 */
public class MaxLevelSumOfBT {
    static class TreeNode{int val; TreeNode left, right; TreeNode(int x){val = x; left = right = null;}}

    public static void main(String[] args) {
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(3);
        root.left.left = new TreeNode(4);
        root.left.right = new TreeNode(5);
        root.right.left = new TreeNode(6);
        root.right.right = new TreeNode(7);

        System.out.println("maxLevelSumUsingBfs(root): " + maxLevelSumUsingBfs(root));
        System.out.println("maxLevelSumUsingDfs(root): " + maxLevelSumUsingDfs(root));
    }

    public static int maxLevelSumUsingBfs(TreeNode root) {
        int level = 1;
        int maxSum = Integer.MIN_VALUE;
        int maxSumLevel = 1;
        Queue<TreeNode> q = new LinkedList<>();
        q.add(root);
        while(!q.isEmpty()) {
            int n = q.size(), sum = 0;
            for(int i=0; i<n; i++) {
                TreeNode curr = q.poll();
                sum+=curr.val;
                if(curr.left != null) q.add(curr.left);
                if(curr.right != null) q.add(curr.right);
            }
            if(sum>maxSum) {
                maxSum=sum;
                maxSumLevel=level;
            }
            level++;
        }
        return maxSumLevel;
    }




    static Map<Integer, Integer> levelSums = new HashMap<>();
    public static int maxLevelSumUsingDfs(TreeNode root) {
        int maxSum = Integer.MIN_VALUE;
        int maxLevel = 1;
        levelSums.clear();
        dfs(root, 1);
        for(int level: levelSums.keySet()){
            int sum = levelSums.get(level);
            if(sum > maxSum) {
                maxSum=sum;
                maxLevel=level;
            }
        }
        return maxLevel;
    }

    private static void dfs(TreeNode node, int level) {
        if (node == null) return;

        int currLevelSum = levelSums.getOrDefault(level, 0) + node.val;
        levelSums.put(level, currLevelSum);

        dfs(node.left, level + 1);
        dfs(node.right, level + 1);
    }
}
