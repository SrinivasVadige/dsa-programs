package Algorithms.BinaryTrees;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
/**
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 27 April 2025
 */
public class LongestZigZagPathInBinaryTree {
    static class TreeNode {int val;TreeNode left;TreeNode right;TreeNode(int x) {val = x;}}
    public static void main(String[] args) {
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(3);
        root.left.left = new TreeNode(4);
        root.left.right = new TreeNode(5);
        root.right.left = new TreeNode(6);
        root.right.right = new TreeNode(7);
        root.left.left.left = new TreeNode(8);
        root.left.left.right = new TreeNode(9);
        root.left.right.left = new TreeNode(10);
        root.left.right.right = new TreeNode(11);
        root.right.left.left = new TreeNode(12);
        root.right.left.right = new TreeNode(13);
        root.right.right.left = new TreeNode(14);
        root.right.right.right = new TreeNode(15);
        System.out.println("longestZigZag: " + longestZigZag(root)); // Output: 4
        System.out.println("longestZigZag 2: " + longestZigZag2(root)); // Output: 4
        System.out.println("longestZigZag 3: " + longestZigZag3(root)); // Output: 4
        System.out.println("longestZigZag MyApproach: " + longestZigZagMyApproach(root)); // Output: 4
    }



    /**
     * Let's have a look at the tree below:
     *
     *                                      1
     *                                    /   \
     *                                   2      3
     *                                        /   \
     *                                      4       5
     *                                    /   \       \
     *                                   6      7       8
     *                                           \
     *                                             9
     *
     * Imagine the tree above. The longest zigzag path is:
     *
     *                                      1 "0"                ------ initially 0, cause we need path length or edges, not node count
     *                                    /   \
     *                             'l1'  2      3  'r1'          ----- In "2" & "3", l1 & r1 are just increment of path length
     *                                        /   \
     *                                      4 'l2'  5 'r1'       ----- "5" reset to 1
     *                                    /   \       \
     *                                   6 'l1' 7 'r3'  8 'r1'   ----- "6" and "8" reset to 1
     *                                           \
     *                                             9 r1          ----- "9" reset to 1
     *
     * Here don't concentrate on l & r but just concentrate on the maxLength of the path.
     * r and l are just the left and right children of the node.
     * If you see the zigzag pattern, then increase the maxLength by 1
     * and if you don't see the zigzag pattern, then reset the maxLength to 1.
     */
    private static int maxLength = 0;
    public static int longestZigZag(TreeNode root) {
        maxLength = 0;
        if (root == null) return 0; // If n==0 or n==1, return 0
        dfs(root.left, 1, true); // root to left child path length is 1
        dfs(root.right, 1, false); // root to right child path length is 1
        return maxLength;
    }
    private static void dfs(TreeNode node, int length, boolean isLeft) {
        if (node == null) return;
        maxLength = Math.max(maxLength, length);
        if (isLeft) {
            dfs(node.right, length + 1, false); // zigzag-pattern i.e ++
            dfs(node.left, 1, true); // not-zigzag-pattern i.e reset and continue going left
        } else {
            dfs(node.left, length + 1, true); // zigzag-pattern i.e ++
            dfs(node.right, 1, false); // not-zigzag-pattern i.e reset and continue going left
        }
    }






    public static int longestZigZag2(TreeNode root) {
        maxLength = 0;
        solve(root, 0, 0);
        return maxLength;
      }
      public static void solve(TreeNode root, int left, int right){
          if(root == null) return;
          maxLength = Math.max(maxLength, Math.max(left, right));
          solve(root.left, right + 1, 0);
          solve(root.right , 0, left + 1);
      }




    public static int longestZigZag3(TreeNode root) {
        return Math.max(helper(root.left, true, 0), helper(root.right, false, 0));
    }
    private static int helper(TreeNode node, boolean isLeft, int depth) {
        if(node == null) return depth;
        return isLeft ?
            Math.max(helper(node.right, false, depth+1), helper(node.left, true, 0))
            :
            Math.max(helper(node.left, true, depth+1), helper(node.right, false, 0));
    }








    /**
        PATTERNS:
        ---------
        1) It can start from anywhere
        2) Use max var or HashMap to check biggest ZigZag
        3) Node value is not needed to be same for this zigzag path
        4) If n nodes in the maxPath, then n-1 edges in the path. So return n-1

        APPROACHES:
        -----------
        1) Brute force --> trav each node and check zigzag
        2)

     */
    private static int maxPath = 0;
    public static int longestZigZagMyApproach(TreeNode root) {
        if(root == null) return 0;
        maxPath = Math.max(maxPath, zigzag(root.left, true));
        maxPath = Math.max(maxPath, zigzag(root.right, false));
        longestZigZag(root.left);
        longestZigZag(root.right);
        return maxPath;
    }
    public static int longestZigZagMyApproach2(TreeNode root) {
        int maxPath = 0;
        Queue<TreeNode> q = new LinkedList<>();
        q.add(root);
        while(!q.isEmpty()) {
            TreeNode node = q.poll();
            if(node.left!=null) {
                q.add(node.left);
                maxPath = Math.max(maxPath, zigzag(node.left, true));
            }
            if(node.right!=null) {
                q.add(node.right);
                maxPath = Math.max(maxPath, zigzag(node.right, false));
            }
        }
        return maxPath;
    }
    static Map<String, Integer> map = new HashMap<>();
    private static int zigzag(TreeNode node, boolean isLeft) {
        if(node==null) return 0;

        String key = new StringBuilder().append(node).append(isLeft).toString();
        if(map.containsKey(key)) return map.get(key);

        int res = 0;
        if(isLeft) {
            res = zigzag(node.right, false) + 1;
        } else {
            res = zigzag(node.left, true) + 1;
        }
        map.put(key, res);
        return res;
    }
}
