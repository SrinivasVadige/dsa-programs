package Algorithms.BinaryTrees;

import java.util.Arrays;
import java.util.Random;

/**
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 28 Jan 2025
 * @link 108. Convert Sorted Array to Binary Search Tree <a href="https://leetcode.com/problems/convert-sorted-array-to-binary-search-tree/">LeetCode Link</a>
 * @topics Tree, Binary Tree, Binary Search Tree, Array, Divide and Conquer
 * @companies Google(2), Apple(4), Meta(2), Microsoft(2), Amazon(2), Bloomberg(3), TikTok(3), Airbnb(2), Samsung(2), Accenture(2)


        root == l + (r-l)/2; or l + (r-l+1)/2; or (l+r+1)/2; or nums.length/2

        In balanced BST = |height(left) − height(right)| ≤ 1 for every node

        [0, 1, 2, 3, 4, 5, 6, 7, 8, 9]
                     r

        1) Preorder Traversal: Always Choose Left Middle Node as a Root
        int midI = (l + r) / 2;

                                4
                            /       \
                           1         7
                         /  \       /  \
                        0    2    5     8
                              \    \     \
                               3    6     9

            => [4, 1, 7, 0, 2, 5, 8, null, null, null, 3, null, 6, null, 9]

            or
`
            2) Preorder Traversal: Always Choose Right Middle Node as a Root
            int midI = (l + r + 1) / 2;
            or
            int midI = (l + r) / 2;
            if ((l + r) % 2 == 1) midI++;

                                5
                            /       \
                           2         8
                         /  \       /  \
                        1    4     7     9
                       /    /     /
                      0    3     6

            => [5, 2, 8, 1, 4, 7, 9, 0, null, 3, null, 6]


            so, root node is always mid-index-num
            left node is mid-index-num of left part and same for right node

            or

            3) Preorder Traversal: Choose a Random Middle Node as a Root
            int midI = (l + r) / 2;
            if ((l + r) % 2 == 1) midI += new Random().nextInt(2);

 */
public class ConvertSortedArrayToBinarySearchTree {
    public static class TreeNode {int val; TreeNode left, right; TreeNode() {}TreeNode(int val) { this.val = val; } TreeNode(int val, TreeNode left, TreeNode right) {this.val = val;this.left = left;this.right = right;}}

    public static void main(String[] args) {
        int[] nums = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
        TreeNode root = sortedArrayToBST1(nums);
        printTree(root);
    }



    /**
     * @TimeComplexity O(n)
     * @SpaceComplexity O(log n) for recursion stack
     */
    public static TreeNode sortedArrayToBST1(int[] nums) {
        TreeNode root = new TreeNode();
        buildBalancedBST(nums, 0, nums.length-1, root);
        return root;
    }
    private static void buildBalancedBST(int[] nums, int l, int r, TreeNode root) {

        int midI = l + (r-l)/2; // or l + (r-l+1)/2; or (l+r+1)/2; or (l+r)/2;
        root.val = nums[midI];
        // if (l==r) return;

        if (l <= midI-1) {
            root.left = new TreeNode();
            buildBalancedBST(nums, l, midI-1, root.left);
        }

        if (midI+1 <= r) {
            root.right = new TreeNode();
            buildBalancedBST(nums, midI+1, r, root.right);
        }
    }





    /**
     * @TimeComplexity O(n)
     * @SpaceComplexity O(log n) for recursion stack
     */
    public static TreeNode sortedArrayToBST2(int[] nums) {
        return buildBalancedBST(nums, 0, nums.length-1);
    }
    private static TreeNode buildBalancedBST(int[] nums, int l, int r) {
        if (l>r) return null;

        int midI = l + (r-l)/2;
        TreeNode node = new TreeNode(nums[midI]);
        node.left = buildBalancedBST(nums, l, midI-1);
        node.right = buildBalancedBST(nums, midI+1, r);

        return node;
    }






    /**
     * @TimeComplexity O(n log n) for copyOfRange() and O(n) for recursion
     * @SpaceComplexity O(n log n) for recursion stack
     */
    public static TreeNode sortedArrayToBST3(int[] nums) {
        int n = nums.length;
        if (n == 0) return null;

        int midI = n/2;
        TreeNode node = new TreeNode(nums[midI]);
        if (midI-1 >= 0) node.left = sortedArrayToBST3(Arrays.copyOfRange(nums, 0, midI));
        if (midI+1 <= n-1) node.right = sortedArrayToBST3(Arrays.copyOfRange(nums, midI+1, n));

        return node;
    }






    /**
     * @TimeComplexity O(n)
     * @SpaceComplexity O(log n) for recursion stack
     */
    public TreeNode sortedArrayToBST4(int[] nums) {
        return helper(nums, 0, nums.length - 1);
    }
    public TreeNode helper(int[] nums, int left, int right) {
        if (left > right) return null;

        // choose random middle node as a root
        int p = (left + right) / 2;
        if ((left + right) % 2 == 1) p += new Random().nextInt(2);

        // preorder traversal: node -> left -> right
        TreeNode root = new TreeNode(nums[p]);
        root.left = helper(nums, left, p - 1);
        root.right = helper(nums, p + 1, right);
        return root;
    }






    public TreeNode sortedArrayToBSTWrongApproach(int[] nums) {

        int mid = nums.length/2;
        TreeNode root = new TreeNode(nums[mid]);
        TreeNode travL = null;
        TreeNode travR = null;
        if (nums.length > 1) travL = root.left = new TreeNode(nums[mid-1]);
        if (nums.length > 2) travR = root.right = new TreeNode(nums[nums.length-1]);

        int l=mid-2, r=nums.length-2;
        for (; l>-1 && r>mid; l--, r--) {
            System.out.printf("l: %s, r: %s\n", l, r);
            travL.left = new TreeNode(nums[l]);
            travL = travL.left;

            travR.left = new TreeNode(nums[r]);
            travR = travR.left;
        }

        if (l==0) travL.left = new TreeNode(nums[0]);

        return root;
    }










    private static void printTree(TreeNode root) {
        System.out.print("\nTree: ");
        java.util.Queue<TreeNode> queue = new java.util.LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                TreeNode node = queue.poll();
                System.out.print(node == null ? "null " : node.val + " ");
                if (node == null) continue;
                queue.add(node.left);
                queue.add(node.right);
            }
        }
    }
}
