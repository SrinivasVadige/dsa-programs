package Algorithms.BinaryTrees;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

/**
 *  1. build tree using array
    2. print tree in the form of array
    3. invert tree (update, swap left and right nodes)
    4. traverse using recursion
    5. DFS traversals (pre-order, in-order, post-order traversals)
    6. BFS traversals (level order traversal)
    7. height of tree
    8. clone tree via pre-order traversal
    9. check if two trees are identical or not
    10. check if two trees are symmetric/mirror or not
    11. check if tree height is balanced or not
    12. check if tree is complete
    13. check if tree is full
    14. check if tree is subtree of another tree
    15. check if tree is binary search tree BST
    16. check if tree is uni-val tree

 *
 *
 *
 *
* @author Srinivas Vadige, srinivas.vadige@gmail.com
* @since 23 Sept 2024
*/
@SuppressWarnings("unused")
public class BinaryTree {

    static class TreeNode {
        int val;
        TreeNode left, right;
        TreeNode() {}
        TreeNode(int val) { this.val = val; }
        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }

    public static void main(String[] args) {
        // 1. BUILD TREE USING ARRAY
        System.out.println("1. BUILD TREE USING ARRAY");
        int[] nums = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10}; // which is same as 1 2 3 4 5 6 7 8 9 10 null null null null null
        TreeNode root = buildTree(nums);

        // 2. PRINT TREE
        System.out.println("2. PRINT TREE");
        printTree(root).forEach(System.out::println);
        /*
                   1
                  / \
                 2   3
                / \ / \
               4  5 6  7
              / \ /
             8  9 10


             1 2 3 4 5 6 7 8 9 10 null null null null null

        */

        // 3. INVERT TREE
        System.out.println("\n\n3. INVERT TREE");
        invertTree(root);
        printTree(root).forEach(System.out::println);
        /*
                          1
                   _______|_______
                  /               \
                 3                 2
                / \               / \
               7   6             5   4
              /\   /\           /\   /\
            nl nl nl nl        nl 10  9 8


            1 3 2 7 6 5 4 null null null null null 10 9 8
        */
        invertTree(root); // invert back to get original tree


        // 4. TRAVERSE TREE USING RECURSION
        System.out.println("\n4. TRAVERSE TREE USING RECURSION");
        travUsingRecursion(root);


        // 5. DFS TRAVERSAL
        System.out.println("\n\n5. DFS TRAVERSAL");
        System.out.println("PRE-ORDER TRAVERSAL USING RECURSION");
        preOrderRecursion(root);
        System.out.println("\nPRE-ORDER TRAVERSAL USING STACK");
        preOrderUsingStack(root);
        System.out.println("\nIN-ORDER TRAVERSAL USING RECURSION");
        inOrderRecursion(root);
        System.out.println("\nIN-ORDER TRAVERSAL USING STACK");
        inOrderUsingStack(root);
        System.out.println("\nPOST-ORDER TRAVERSAL USING RECURSION");
        postOrderRecursion(root);
        System.out.println("\nPOST-ORDER TRAVERSAL USING STACK");
        postOrderUsingStack(root);


        // 6. BFS TRAVERSAL
        System.out.println("\n\n6. BFS TRAVERSAL");
        System.out.println("LEVEL ORDER TRAVERSAL");
        levelOrderTraversal(root);


        // 7. HEIGHT OF TREE
        System.out.println("\n\n7. HEIGHT OF TREE");
        System.out.println(getHeight(root));


        // 8. CLONE TREE VIA PRE-ORDER TRAVERSAL
        System.out.println("\n\n8. CLONE TREE VIA PRE-ORDER TRAVERSAL");
        cloneTree(root);

        // 9. CHECK IF TWO TREES ARE IDENTICAL
        System.out.println("\n\n9. CHECK IF TWO TREES ARE IDENTICAL");
        TreeNode root1 = buildTree(new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10}); // which is same as 1 2 3 4 5 6 7 8 9 10 null null null null null
        TreeNode root2 = buildTree(new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10}); // which is same as 1 2 3 4 5 6 7 8 9 10 null null null null null
        System.out.println(isSameTree(root1, root2));


        // 10. CHECK IF TWO TREES ARE SYMMETRIC
        System.out.println("\n\n10.1 CHECK IF TWO TREES ARE SYMMETRIC USING DFS");
        TreeNode root3 = buildTree(new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10}); // which is same as 1 2 3 4 5 6 7 8 9 10 null null null null null
        System.out.println(isSymmetricUsingDfs(root3));

        System.out.println("\n\n10.2 CHECK IF TWO TREES ARE SYMMETRIC USING BFS");
        System.out.println(isSymmetricUsingBfs(root3));


        // 11. CHECK IF TREE IS HEIGHT BALANCED
        System.out.println("\n\n11. CHECK IF TREE IS HEIGHT BALANCED");
        TreeNode root4 = buildTree(new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10}); // which is same as 1 2 3 4 5 6 7 8 9 10 null null null null null
        System.out.println(isBalanced(root4));


        // 12. CHECK IF TREE IS COMPLETE
        System.out.println("\n\n12. CHECK IF TREE IS COMPLETE");
        TreeNode root5 = buildTree(new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10}); // which is same as 1 2 3 4 5 6 7 8 9 10 null null null null null
        System.out.println(isComplete(root5));


        // 13. CHECK IF TREE IS FULL
        System.out.println("\n\n13. CHECK IF TREE IS FULL");
        TreeNode root6 = buildTree(new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10}); // which is same as 1 2 3 4 5 6 7 8 9 10 null null null null null
        System.out.println(isFull(root6));


        // 14. CHECK IF TREE IS SUBTREE
        System.out.println("\n\n14. CHECK IF TREE IS SUBTREE");
        TreeNode root7 = buildTree(new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10}); // which is same as 1 2 3 4 5 6 7 8 9 10 null null null null null
        TreeNode root8 = buildTree(new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10}); // which is same as 1 2 3 4 5 6 7 8 9 10 null null null null null
        System.out.println(isSubtree(root7, root8));


        // 15. CHECK IF TREE IS BINARY SEARCH TREE
        System.out.println("\n\n15. CHECK IF TREE IS BINARY SEARCH TREE");
        TreeNode root9 = buildTree(new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10}); // which is same as 1 2 3 4 5 6 7 8 9 10 null null null null null
        System.out.println(isBST(root9));


        // 16. CHECK IF TREE IS UNI-VALUE TREE
        System.out.println("\n\n16. CHECK IF TREE IS UNI-VALUE TREE");
        TreeNode root10 = buildTree(new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10}); // which is same as 1 2 3 4 5 6 7 8 9 10 null null null null null
        System.out.println(isUniValTree(root10));

    }



    public static TreeNode buildTree(int[] nums) {
        if (nums == null || nums.length == 0) {
            return null;
        }
        TreeNode root = new TreeNode(nums[0]);
        Queue<TreeNode> q = new LinkedList<>();
        q.add(root);
        int i = 1;
        while (i < nums.length) {
            TreeNode curr = q.remove();
            if (i < nums.length) {
                curr.left = new TreeNode(nums[i++]);
                q.add(curr.left);
            }
            if (i < nums.length) {
                curr.right = new TreeNode(nums[i++]);
                q.add(curr.right);
            }
        }
        return root;
    }


    public static List<List<String>> printTree(final TreeNode root) {
        final int width = (int) Math.pow(2, getHeight(root)) - 1;
        final List<List<String>> result = new ArrayList<>();

        dfs(root, result, 0, width, 0, width);

        return result;
    }
    private static void dfs(final TreeNode root, final List<List<String>> result, final int l, final int r, final int level, final int width) {
        if(root != null) {
            if(level >= result.size()) {
                result.add(new ArrayList<>());

                for(int i = 0; i < width; ++i)
                    result.get(level).add("");
            }

            final int mid = (l + r) / 2;

            result.get(level).set(mid, String.valueOf(root.val));

            dfs(root.left, result, l, mid, level + 1, width);
            dfs(root.right, result, mid, r, level + 1, width);
        }
    }

    public static TreeNode invertTree(TreeNode root) {
        if (root == null) return null;
        swapNodes(root);
        return root;
    }
    public static void swapNodes(TreeNode node){
        if(node == null) return;

            TreeNode temp=node.left;
            node.left=node.right;
            node.right=temp;

            swapNodes(node.left);
            swapNodes(node.right);
    }

    private static void travUsingRecursion(final TreeNode root) {
        if (root == null) return;
        System.out.print(root.val + " ");
        travUsingRecursion(root.left);
        travUsingRecursion(root.right);
    }

    private static void preOrderRecursion(final TreeNode root) {
        if (root == null) return;
        System.out.print(root.val + " ");
        preOrderRecursion(root.left);
        preOrderRecursion(root.right);
    }

    private static void inOrderRecursion(final TreeNode root) {
        if (root == null) return;
        inOrderRecursion(root.left);
        System.out.print(root.val + " ");
        inOrderRecursion(root.right);
    }

    private static void postOrderRecursion(final TreeNode root) {
        if (root == null) return;
        postOrderRecursion(root.left);
        postOrderRecursion(root.right);
        System.out.print(root.val + " ");
    }

    private static void preOrderUsingStack(final TreeNode root) {
        if (root == null) return;
        Stack<TreeNode> stack = new Stack<>();
        stack.push(root);
        while(!stack.isEmpty()) {
            TreeNode curr = stack.pop();
            System.out.print(curr.val + " ");
            if (curr.right != null) stack.push(curr.right);
            if (curr.left != null) stack.push(curr.left);
        }
    }

    private static void inOrderUsingStack(final TreeNode root) {
        if (root == null) return;
        Stack<TreeNode> stack = new Stack<>();
        TreeNode curr = root;
        while(!stack.isEmpty() || curr != null) {
            if (curr != null) {
                stack.push(curr);
                curr = curr.left;
            } else {
                curr = stack.pop();
                System.out.print(curr.val + " ");
                curr = curr.right;
            }
        }
    }

    private static void postOrderUsingStack(final TreeNode root) {
        if (root == null) return;
        Stack<TreeNode> stack = new Stack<>();
        stack.push(root);
        while(!stack.isEmpty()) {
            TreeNode curr = stack.pop();
            System.out.print(curr.val + " ");
            if (curr.left != null) stack.push(curr.left);
            if (curr.right != null) stack.push(curr.right);
        }
    }

    private static void levelOrderTraversal(final TreeNode root) {
        if (root == null) return;
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        while(!queue.isEmpty()) {
            TreeNode curr = queue.remove();
            System.out.print(curr.val + " ");
            if (curr.left != null) queue.add(curr.left);
            if (curr.right != null) queue.add(curr.right);
        }
    }

    private static int getHeight(final TreeNode root) {
        if(root == null)
            return 0;

        return Math.max(getHeight(root.left), getHeight(root.right)) + 1;
    }

    private static TreeNode cloneTree(final TreeNode root) {
        if (root == null) return null;
        TreeNode clone = new TreeNode(root.val);
        clone.left = cloneTree(root.left);
        clone.right = cloneTree(root.right);
        return clone;
    }

    private static boolean isSameTree(final TreeNode p, final TreeNode q) {
        if (p == null && q == null) return true;
        if (p == null || q == null) return false;
        return p.val == q.val && isSameTree(p.left, q.left) && isSameTree(p.right, q.right);
    }

    private static boolean isSymmetricUsingDfs(final TreeNode root) {
        if (root == null) return true;
        return isSymmetricUsingDfs(root.left, root.right);
    }
    private static boolean isSymmetricUsingDfs(final TreeNode left, final TreeNode right) {
        if (left == null && right == null) return true;
        else if (left == null || right == null) return false;
        else if (left.val != right.val) return false; // for root children
        else return isSymmetricUsingDfs(left.left, right.right) && isSymmetricUsingDfs(left.right, right.left);
    }

    private static boolean isSymmetricUsingBfs(final TreeNode root) {
        if (root == null) return true;

        Queue<TreeNode> q = new LinkedList<>();
        q.add(root.left);
        q.add(root.right);

        while (!q.isEmpty()) {
            TreeNode t1 = q.poll();
            TreeNode t2 = q.poll();

            if (t1 == null && t2 == null) continue;
            if (t1 == null || t2 == null) return false;
            if (t1.val != t2.val) return false;

            q.add(t1.left);
            q.add(t2.right);
            q.add(t1.right);
            q.add(t2.left);
        }

        return true;
    }

    private static boolean isComplete(final TreeNode root) {
        if (root == null) return true;
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        while(!queue.isEmpty()) {
            TreeNode curr = queue.remove();
            if (curr.left == null && curr.right != null) return false;
            if (curr.left != null) queue.add(curr.left);
            if (curr.right != null) queue.add(curr.right);
        }
        return true;
    }

    private static boolean isBalanced(final TreeNode root) {
        if (root == null) return true;
        return isBalanced(root.left) && isBalanced(root.right) && Math.abs(getHeight(root.left) - getHeight(root.right)) <= 1;
    }

    private static boolean isFull(final TreeNode root) {
        if (root == null) return true;
        if (root.left == null && root.right == null) return true;
        if (root.left == null || root.right == null) return false;
        return isFull(root.left) && isFull(root.right);
    }

    private static boolean isSubtree(final TreeNode root, final TreeNode subRoot) {
        if (root == null) return false;
        if (root.val == subRoot.val && isSameTree(root, subRoot)) return true;
        return isSubtree(root.left, subRoot) || isSubtree(root.right, subRoot);
    }

    private static boolean isBST(final TreeNode root) {
        if (root == null) return true;
        return isBST(root.left, Integer.MIN_VALUE, root.val) && isBST(root.right, root.val, Integer.MAX_VALUE);
    }

    private static boolean isBST(final TreeNode root, final int min, final int max) {
        if (root == null) return true;
        return root.val > min && root.val < max && isBST(root.left, min, root.val) && isBST(root.right, root.val, max);
    }

    private static boolean isUniValTree(final TreeNode root) {
        if (root == null) return true;
        return root.val == root.left.val && root.val == root.right.val && isUniValTree(root.left) && isUniValTree(root.right);
    }




    private static int countLeaves(final TreeNode root) {
        if (root == null) return 0;
        if (root.left == null && root.right == null) return 1;
        return countLeaves(root.left) + countLeaves(root.right);
    }

    private static int countFullNodes(final TreeNode root) {
        if (root == null) return 0;
        if (root.left == null && root.right == null) return 1;
        if (root.left == null || root.right == null) return 0;
        return countFullNodes(root.left) + countFullNodes(root.right);
    }

    private static boolean isPerfect(final TreeNode root) {
        if (root == null) return true;
        int height = getHeight(root);
        int nodes = countNodes(root);
        return Math.pow(2, height) - 1 == nodes && isFull(root);
    }
    private static int countNodes(final TreeNode root) {
        if (root == null) return 0;
        return 1 + countNodes(root.left) + countNodes(root.right);
    }

    private static boolean isCousins(final TreeNode root, final int x, final int y) {
        if (root == null) return false;
        TreeNode parentX = lowestCommonAncestor(root, new TreeNode(x), new TreeNode(y));
        return parentX != null && parentX.left != null && parentX.right != null && parentX.left.val != x && parentX.right.val != y;
    }

    private static TreeNode lowestCommonAncestor(final TreeNode root, final TreeNode p, final TreeNode q) {
        if (root == null) return null;
        if (root.val == p.val || root.val == q.val) return root;
        TreeNode left = lowestCommonAncestor(root.left, p, q);
        TreeNode right = lowestCommonAncestor(root.right, p, q);
        if (left != null && right != null) return root;
        return left != null ? left : right;
    }

    private static int minDepth(final TreeNode root) {
        if (root == null) return 0;
        if (root.left == null && root.right == null) return 1;
        if (root.left == null) return minDepth(root.right) + 1;
        if (root.right == null) return minDepth(root.left) + 1;
        return Math.min(minDepth(root.left), minDepth(root.right)) + 1;
    }

    private static int getDiameter(final TreeNode root) {
        if (root == null) return 0;
        return Math.max(getHeight(root.left) + getHeight(root.right), Math.max(getDiameter(root.left), getDiameter(root.right)));
    }

}
