package Algorithms.BinaryTrees;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

/**
    THOUGHTS
    --------
    1) Node value can be negative
    2) Calculate the path of each node with all possibilities until leaf
    3) Here we calculate the from leaf to upper level and save the sum like
    dynamic programming?
    4) while you're calculating the 1st level node then we add in 2nd level  we add 1st level + 2nd level node and also we need a dedicated sum for 2nd level cause in 3rd level we need to check 1st+2nd+3rd, 2nd+3rd and just 3rd sum. Here the sums variable are increasing ---> maintain list for sums?
    5) Use brute force for each node until leaf?

 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 03 Feb 2025
 */
public class PathSumIII {
    static class TreeNode {int val;TreeNode left, right;TreeNode() {}TreeNode(int val) { this.val = val; }TreeNode(int val, TreeNode left, TreeNode right) {this.val = val;this.left = left;this.right = right;}}


    public static void main(String[] args) {
        TreeNode root = new TreeNode(5);
        root.left = new TreeNode(4);
        root.right = new TreeNode(8);
        root.left.left = new TreeNode(11);
        root.left.left.left = new TreeNode(7);
        root.left.left.right = new TreeNode(2);
        root.right.left = new TreeNode(13);
        root.right.right = new TreeNode(4);
        root.right.right.left = new TreeNode(5);
        root.right.right.right = new TreeNode(1);
        /*

                    5
                   / \
                  4   8
                 /   / \
                11  13  4
               /  \    / \
              7    2  5   1

            and targetSum = 22
        */

        System.out.println("pathSumMyApproach(root, 22): " + pathSumMyApproach(root, 22));
        System.out.println("pathSumUsingPreOrderDfs(root, 22): " + pathSumUsingPreOrderDfs(root, 22));
        System.out.println("pathSumUsingHashMap(root, 22): " + pathSumUsingHashMap(root, 22));
    }


    public static int pathSumMyApproach(TreeNode root, int targetSum) {
        if (root == null) return 0;
        int[] count = new int[]{0}; // or instead of reference-variable use class-variable
        Queue<TreeNode> q = new LinkedList<>();
        q.add(root);
        TreeNode trav = root;

        // trav each node and check for sum with all possible combinations
        while (!q.isEmpty()) {
            for (int i=0; i<q.size(); i++) {
                trav = q.poll();
                checkSum(trav, count, 0, targetSum);
                if (trav.left!=null) q.add(trav.left);
                if (trav.right!=null) q.add(trav.right);
            }
        }
        return count[0];
    }

    private static void checkSum(TreeNode node, int[] count, long sum, int targetSum) { // long sum cause we might sum-up more than Integer.MAX_VALUE
        if (node == null) return;
        sum+=node.val;
        if(sum==targetSum) count[0]++;

        checkSum(node.left, count, sum, targetSum);
        checkSum(node.right, count, sum, targetSum);
    }



    public static int pathSumUsingPreOrderDfs(TreeNode root, int targetSum) {
        if(root == null) return 0;
        return count(root,targetSum,0)+pathSumUsingPreOrderDfs(root.left, targetSum)+pathSumUsingPreOrderDfs(root.right, targetSum);
    }

    public static int count(TreeNode root, long targetSum,int c) {
        if(root == null) return 0;
        targetSum -= root.val;
        if(targetSum == 0) c++;
        c += count(root.left,targetSum,0);
        c += count(root.right,targetSum,0);
        return c;
    }



    /**
     * DOUBT: Using this prefixSum HashMap, how does it know the specific path? In hashMap, we add all possible sums
     */
    private static int counter = 0;
    private static long tSum = 0;
    private static Map<Long, Integer> map = new HashMap<>(); // will all possible currSum values
    public static int pathSumUsingHashMap(TreeNode root, int targetSum) {
        if(root == null) return 0;
        tSum = targetSum;
        counter = 0;
        map.put(0l, 1); // i.e 0 needed sum count is 1.
        dfs(root, 0);
        System.out.println(map);
        return counter;
    }

    private static void dfs(TreeNode root, long currSum) {
        if(root == null) return;
        currSum += root.val;
        counter += map.getOrDefault(currSum-tSum, 0); // neededSum = currSum - targetSum
        map.put(currSum, map.getOrDefault(currSum, 0)+1);
        dfs(root.left, currSum);
        dfs(root.right, currSum);
        if (map.get(currSum) == 1) map.remove(currSum);
        else map.put(currSum, map.get(currSum)-1);
        // or instead of if-else map.put(currSum, map.getOrDefault(currSum, 0)-1);
    }


}
