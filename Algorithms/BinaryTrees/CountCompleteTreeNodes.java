package Algorithms.BinaryTrees;

/**
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 10 Dec 2025
 * @link 222. Count Complete Tree Nodes <a href="https://leetcode.com/problems/count-complete-tree-nodes/">LeetCode Link</a>
 * @topics Tree, BinaryTree, BinarySearchTree, BinarySearch, DFS
 * @companies Google(14), Meta(8), Microsoft(7), Amazon(5), Bloomberg(5), Apple(3), Flipkart(2), Yahoo(2)
 */
public class CountCompleteTreeNodes {
    public static class TreeNode {int val; TreeNode left, right; TreeNode() {}TreeNode(int val) { this.val = val; }TreeNode(int val, TreeNode left, TreeNode right) {this.val = val;this.left = left;this.right = right;}}

    public static void main(String[] args) {
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(3);
        root.left.left = new TreeNode(4);
        root.left.right = new TreeNode(5);
        root.right.left = new TreeNode(6);

        /*
                         1
                       /   \
                      2     3
                     / \   /
                    4   5 6
         */

        System.out.println("countNodes using recursion => " + countNodesUsingRecursion(root));
        System.out.println("countNodes using dfs => " + countNodesUsingDfs(root));
        System.out.println("countNodes using left and right heights => " + countNodesUsingLeftAndRightHeights(root));
        System.out.println("countNodes using binary search => " + countNodesUsingBinarySearch(root));
    }





    /**
     * @TimeComplexity O(n)
     * @SpaceComplexity O(n)
     */
    public static int countNodesUsingRecursion(TreeNode root) {
        if (root == null) return 0;
        return 1 + countNodesUsingRecursion(root.left) + countNodesUsingRecursion(root.right);
    }







    /**
     * @TimeComplexity O(n) in worst case scenario
     * @SpaceComplexity O(h), where h is the height of the tree
     */
    static int h = -1;
    static boolean isStop = false;
    static int leaves = 0; // only the last level leaves
    public static int countNodesUsingDfs(TreeNode root) {
        // int lh = 0; for (TreeNode trav = root; trav!=null; trav=trav.left) lh++;
        // int rh = 0; for (TreeNode trav = root; trav!=null; trav=trav.right) rh++;
        // if (lh == rh) return (int) Math.pow(2, lh) - 1;
        if (root == null) return 0;
        dfs(root, 0);
        return (int) Math.pow(2, --h) - 1 + leaves;
    }

    private static void dfs(TreeNode node, int currH) {
        if (node == null || isStop) return;
        currH++;

        if (node.left == null && node.right == null) {
            if (h == -1) {
                h = currH;
            } else if (currH < h) {
                isStop = true;
                return;
            }
            leaves++;
        }

        dfs(node.left, currH);
        dfs(node.right, currH);
    }









    /**
     * @TimeComplexity O(log(n)^2)
     * @SpaceComplexity O(h), where h is the height of the tree
     */
    public static int countNodesUsingLeftAndRightHeights(TreeNode root) {
        if (root == null) return 0;

        int lh = getLeftHeight(root);
        int rh = getRightHeight(root);

        if (lh == rh) return (1 << lh) - 1;
        // if (lh == rh) return (int)Math.pow(2, lh) - 1;

        return 1 + countNodesUsingLeftAndRightHeights(root.left) + countNodesUsingLeftAndRightHeights(root.right);
    }
    private static int getLeftHeight(TreeNode node) {
        int h = 0;
        while (node != null) {
            node = node.left;
            h++;
        }
        return h;
    }
    private static int getRightHeight(TreeNode node) {
        int h = 0;
        while (node != null) {
            node = node.right;
            h++;
        }
        return h;
    }










    /**
     * @TimeComplexity O(log(n)^2)
     * @SpaceComplexity O(h), where h is the height of the tree
     */
    public static int countNodesUsingBinarySearch(TreeNode root) {
        if (root == null) return 0;

        int h = getHeight(root); // ignore curr node in the depth i.e., h = h-1
        if (h == 0) return 1;

        int left = 1, right = (int) Math.pow(2, h) - 1; // the number of nodes in the last level is "2^(h-1) - 1"
        int pivot;
        while (left <= right) {
            pivot = left + (right - left) / 2; // mid-index of the last level in l & r range
            if (exists(pivot, h, root)) left = pivot + 1;
            else right = pivot - 1;
        }

        return (int) Math.pow(2, h) - 1 + left;
    }
    private static int getHeight(TreeNode node) { // skip curr level i.e., return h-1
        int h = 0; // skip curr level
        while (node.left != null) {
            node = node.left;
            h++;
        }
        return h;
    }
    private static boolean exists(int idx, int h, TreeNode node) {
        int l = 0, r = (int) Math.pow(2, h) - 1; // the number of nodes in the last level is "2^(h-1) - 1"
        while (h-- > 0) {
            int pivot = l + (r - l) / 2;
            if (pivot >= idx) {
                node = node.left;
                r = pivot; // maintain the node - cause we need node inside l to r
            } else {
                node = node.right;
                l = pivot + 1;
            }
        }
        return node != null; // if node exists or not at the "idx" index
    }














    public static int countNodesUsingHeightNotWorking(TreeNode root) {
        int lh = 0;
        for (TreeNode trav = root; trav!=null; trav=trav.left) {
            lh++;
        }

        int rh = 0;
        for (TreeNode trav = root; trav!=null; trav=trav.right) {
            rh++;
        }

        if (lh == rh) return (int) Math.pow(2, lh) - 1;
        else {
            return (int) Math.pow(2, lh) - 2;


        }
    }
}
