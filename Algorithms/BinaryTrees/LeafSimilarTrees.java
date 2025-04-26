package Algorithms.BinaryTrees;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 26 April 2025
 */
public class LeafSimilarTrees {
    static class TreeNode {int val; TreeNode left; TreeNode right; TreeNode(int x) { val = x; }}
    public static void main(String[] args) {
        TreeNode root1 = new TreeNode(3);
        root1.left = new TreeNode(5);
        root1.right = new TreeNode(1);
        root1.left.left = new TreeNode(6);
        root1.left.right = new TreeNode(2);
        root1.right.left = new TreeNode(0);
        root1.right.right = new TreeNode(8);
        root1.left.right.left = new TreeNode(7);
        root1.left.right.right = new TreeNode(4);
        TreeNode root2 = new TreeNode(3);
        root2.left = new TreeNode(5);
        root2.right = new TreeNode(1);
        root2.left.left = new TreeNode(6);
        root2.left.right = new TreeNode(2);
        root2.right.left = new TreeNode(0);
        root2.right.right = new TreeNode(8);
        root2.left.right.left = new TreeNode(7);
        root2.left.right.right = new TreeNode(4);
        System.out.println(leafSimilar(root1, root2)); // Output: true
        System.out.println(leafSimilar2(root1, root2)); // Output: true
    }

    public static boolean leafSimilar(TreeNode root1, TreeNode root2) {
        List<Integer> lst1 = new ArrayList<>(), lst2 = new ArrayList<>();

        // dfs
        dfs(root1, lst1);
        dfs(root2, lst2);

        return lst1.equals(lst2);
    }
    private static void dfs(TreeNode node, List<Integer> lst) {
        if(node==null) return;
        if(node.left == null && node.right == null) {
            lst.add(node.val);
            return;
        }
        dfs(node.left, lst);
        dfs(node.right, lst);
    }




    public static boolean leafSimilar2(TreeNode root1, TreeNode root2) {
        StringBuilder leaves1 = new StringBuilder();
        StringBuilder leaves2 = new StringBuilder();
        getLeaves(root1, leaves1);
        getLeaves(root2, leaves2);
        return leaves1.toString().equals(leaves2.toString());
    }

    public static void getLeaves(TreeNode root, StringBuilder leaves) {
        if (root == null) return;
        getLeaves(root.left, leaves);
        getLeaves(root.right, leaves);
        if (root.left == null && root.right == null) leaves.append(root.val);
    }
}
