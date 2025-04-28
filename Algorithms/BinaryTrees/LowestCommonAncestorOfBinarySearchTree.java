package Algorithms.BinaryTrees;

/**
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 27 April 2025
 */
public class LowestCommonAncestorOfBinarySearchTree {
    static class TreeNode {int val; TreeNode left; TreeNode right; TreeNode(int x) { val = x; }}
    public static void main(String[] args) {
        TreeNode root = new TreeNode(6);
        root.left = new TreeNode(2);
        root.right = new TreeNode(8);
        root.left.left = new TreeNode(0);
        root.left.right = new TreeNode(4);
        root.left.right.left = new TreeNode(3);
        root.left.right.right = new TreeNode(5);
        root.right.left = new TreeNode(7);
        root.right.right = new TreeNode(9);

        int pVal = 2;
        int qVal = 8;
        TreeNode p = findNode(root, pVal);
        TreeNode q = findNode(root, qVal);

        TreeNode lca = lowestCommonAncestor(root, p, q);
        System.out.println("Lowest Common Ancestor of " + pVal + " and " + qVal + " is: " + lca.val);
    }

    /**
     * @TimeComplexity O(H), where H is the height of the tree.
     * @SpaceComplexity O(1) for iterative approach, O(H) for recursive approach due to call stack.
     */
    public static TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        TreeNode curr = root;
        while (curr != null) {
            if (curr.val < p.val && curr.val < q.val) {
                curr = curr.right; // both p and q are in the right subtree
            } else if (curr.val > p.val && curr.val > q.val) {
                curr = curr.left; // both p and q are in the left subtree
            } else {
                return curr; // found the split point, i.e., the LCA
            }
        }
        return null; // if p and q are not in the tree
    }




    // Recursive approach
    public static TreeNode lowestCommonAncestor2(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null) return null;

        if (root.val > p.val && root.val > q.val) {
            return lowestCommonAncestor2(root.left, p, q);
        } else if (root.val < p.val && root.val < q.val) {
            return lowestCommonAncestor2(root.right, p, q);
        } else {
            return root;
        }
    }






    /**
     * Instead of BST, as the tree is binary tree, we can use BT {@link Algorithms.BinaryTrees.LowestCommonAncestorOfBinaryTree} approach as well
     * @TimeComplexity O(N), where N is the number of nodes in the tree.
     * @SpaceComplexity O(N) for recursive approach and java consumes some space for recursion stack
     */
    public TreeNode lowestCommonAncestorBT(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null || root == p || root == q) return root;

        TreeNode left = lowestCommonAncestorBT(root.left, p, q);
        TreeNode right = lowestCommonAncestorBT(root.right, p, q);

        if (left != null && right != null) return root;
        if (left == null) return right;
        return left;
    }




    private static TreeNode findNode(TreeNode root, int val) {
        if (root == null || root.val == val) return root;
        if (val < root.val) return findNode(root.left, val);
        return findNode(root.right, val);
    }
}
