package Algorithms.BinaryTrees;

/**
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 26 April 2025
 *
 *                                        3*
 *                                      /   \
 *                                     1     4*
 *                                    /     / \
 *                                   3*    1   5*
 *                                        /
 *                                       5*
 *
 * Here, the nodes marked with * are good nodes.
 */
public class CountGoodNodesInBinaryTree {
    static class TreeNode {TreeNode left, right; int val; TreeNode(int x) {val = x;}}
    public static void main(String[] args) {
        TreeNode root = new TreeNode(3);
        root.left = new TreeNode(1);
        root.right = new TreeNode(4);
        root.left.left = new TreeNode(3);
        root.right.left = new TreeNode(1);
        root.right.left.left = new TreeNode(5);
        root.right.right = new TreeNode(5);
        System.out.println("goodNodes(root) => " + goodNodes(root));
        System.out.println("goodNodesMyApproach(root) => " + goodNodesMyApproach(root));
    }

    public static int goodNodes(TreeNode root) {
        return dfs(root, root.val);
    }
    private static int dfs(TreeNode node, int max) {
        if (node == null) return 0;
        int count = node.val >= max ? 1 : 0;
        max = Math.max(max, node.val);
        count += dfs(node.left, max) + dfs(node.right, max);
        return count;
    }




    static int c = 0;
    public static int goodNodesMyApproach(TreeNode root) {
        dfs2(root, root.val);
        return c;
    }
    private static void dfs2(TreeNode x, int maxVal) {
        if(x == null) return;
        if(x.val >= maxVal) c++;

        maxVal = Math.max(maxVal, x.val);

        dfs2(x.left, maxVal);
        dfs2(x.right, maxVal);
    }
}
