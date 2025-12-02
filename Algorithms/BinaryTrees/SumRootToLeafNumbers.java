package Algorithms.BinaryTrees;

import DataStructures.BinaryTreeBasics;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 30 Nov 2025
 * @link 129. Sum Root to Leaf Numbers <a href="https://leetcode.com/problems/sum-root-to-leaf-numbers/">LeetCode Link</a>
 * @topics Tree, Binary Tree, DFS
 * @companies Meta(23), Google(4), Amazon(2), Microsoft(9), Bloomberg(3)
 */
public class SumRootToLeafNumbers {
    public static class TreeNode {int val; TreeNode left; TreeNode right; TreeNode(int val) {this.val = val;}}

    public static void main(String[] args) {
        TreeNode root = new TreeNode(4);
        root.left = new TreeNode(9);
        root.right = new TreeNode(0);
        root.left.left = new TreeNode(5);
        root.left.right = new TreeNode(1);
        System.out.println("sumNumbers using Morris PreOrder Traversal " + sumNumbersUsingMorrisPreorderTraversal(root));
        System.out.println("sumNumbers using Morris PreOrder DFS " + sumNumbersUsingPreOrderDfs(root));
        System.out.println("sumNumbers using Morris Queue BFS " + sumNumbersUsingQueueBfs(root));
    }

/**
 * @see DataStructures.BinaryTreeBasics#morrisInOrderTraversal
 * @TimeComplexity O(n)
 * @SpaceComplexity O(1)
<pre>

                     4
                   /   \
                  9     0
                 / \
                5   1


    Here, we have two pointers - 1.root/currRootNode & 2.predecessorNode
    and we create link using ---> "predecessorNode.right = root"

    1. if root.left != null then "predecessor = root.left" ---> i.e connect the current root node and predecessor vars
    2. "predecessor.right = root" is the link
    3. If we have no link, then go to the left sub-tree i.e root = root.left
    4. If we have the link, break it and go to right sub-tree i.e root = root.right



--------- 1st iteration: ---------

     root = 4
     predecessor = 1 (curr root's left child's rightmost leaf node)
     currSum = 0
     totalSum = 9

                         (4r)<-----
                        /   \     |
                       9     0    |
                      / \         |
                     5  (1p)-------

    There is left child -->

    1. Predecessor is one step left and then right till you can:
           predecessor = 1

    2. There is no link (predecessor.right != root)
           --> set the link
           --> go to the left subtree: root = root.left = 9 ---> new root



--------- 2nd iteration: ---------

    root = 9
    predecessor = 5  (curr root's left child's rightmost node)
    currSum = 4
    totalSum = 0


                          4 <--------
                        /   \       |
                ----> (9r)   0      |
                |      / \          |
                --- (5p)  1 ---------


    There is left child -->

    1. Predecessor is one step left and then right till you can:
           predecessor = 5

    2. There is no link (predecessor.right != root)
           --> set the link  (5p.right = 9r)
           --> go to the left subtree: root = root.left = 5   ---> new root




--------- 3rd iteration: ---------

    root = 5
    predecessor = null  (as 5 node don't have any left child)
    currSum = 49
    totalSum = 0


                          4 <-------
                        /   \       |
                ---->   9    0      |
                |      / \          |
                --- (5r)  1 ---------


    There is no left child -->

    1. go to the right side of curr root (it'll be 9 - prev root): root = root.right = 9  ---> new root



--------- 4th iteration: ---------

    root = 9
    predecessor = 5  (curr root's left child's rightmost node)
    currSum = 495
    totalSum = 495


                          4 <-------
                        /   \       |
                ----> (9r)   0      |
                |      / \          |
                --- (5p)  1 ---------

                          4 <-------
                        /   \       |
                      (9r)   0      |
                      / \           |
                    (5p)  1 ---------


    There is left child -->

    1. There is a link (predecessor.right == root)
           --> break the link
           --> now predecessor is leaf --> add 495 to totalSum
           --> go to the right side of curr root --> root = root.right = 1 ---> new root



--------- 5th iteration: ---------

     root = 1
     predecessor = null (as 1 node don't have any left child)
     currSum = 49
     totalSum = 495

                          4 <-----
                        /   \     |
                       9     0    |
                      / \         |
                     5  (1r)-------

    There is no left child -->

    1. go to the right side of curr root (it'll be 9 - prev root): root = root.right = 4  ---> new root



--------- 6th iteration: ---------

     root = 4
     predecessor = 1 (curr root's left child's rightmost node)
     currSum = 491
     totalSum = 986

                         4(r) <----
                        /   \     |
                       9     0    |
                      / \         |
                     5  (1p)-------

                         4(r)
                        /   \
                       9     0
                      / \
                     5  (1p)

    There is left child -->

    1. There is a link (predecessor.right == root)
           --> break the link
           --> now predecessor is leaf --> add 491 to totalSum
           --> go to the right side of curr root --> root = root.right = 0 ---> new root



--------- 7th iteration: ---------

     root = 0
     predecessor = null (as 0 node don't have any left child)
     currSum = 40
     totalSum = 126

                         4
                        /  \
                       9   (0r)
                      / \
                     5   1

    There is no left child -->

    1. Add 40 to totalSum as it's a leaf
    2. Go to right side of curr root -> root = root.right = null  ---> new root



--------- 8th iteration: ---------
    As root = null, break the loop


</pre>
 */
public static int sumNumbersUsingMorrisPreorderTraversal(TreeNode root) {
        int totalSum = 0, currSum = 0;
        int steps;
        TreeNode predecessor;

        while (root != null) {
            if (root.left == null) {
                currSum = currSum * 10 + root.val;
                if (root.right == null) totalSum += currSum; // root is leaf node -> ➕ totalSum
                root = root.right;
            } else {
                predecessor = root.left;
                steps = 1;
                while (predecessor.right != null && predecessor.right != root) {
                    predecessor = predecessor.right;
                    steps++;
                }

                if (predecessor.right == null) { // --- NOT LINKED ---
                    currSum = currSum * 10 + root.val; // don't update totalSum yet -> as we do not know if the predecessor is leaf or not
                    predecessor.right = root;
                    root = root.left;
                } else { // i.e., "predecessor.right == root" --- LINKED ---
                    if (predecessor.left == null) totalSum += currSum; // predecessor is leaf node -> ➕ totalSum
                    for (int i = 0; i < steps; ++i) currSum /= 10;
                    predecessor.right = null;
                    root = root.right;
                }
            }
        }
        return totalSum;
    }





    static int sum = 0;
    /**
     * @TimeComplexity O(n)
     * @SpaceComplexity O(h), where h is the height of the tree - for recursion stack
     */
    public static int sumNumbersUsingPreOrderDfs(TreeNode root) {
        sum = 0;
        dfs(root, 0);
        return sum;
    }
    private static void dfs(TreeNode node, int currSum) {
        if (node == null) return;

        currSum = currSum*10 + node.val;
        if (node.left == null && node.right == null) {
            sum += currSum;
            return;
        }
        dfs(node.left, currSum);
        dfs(node.right, currSum);
    }





    public static int sumNumbersUsingPreOrderDfs2(TreeNode root) {
        return dfs2(root, 0);
    }
    private static int dfs2(TreeNode node, int currSum) {
        if (node == null) return 0;

        currSum = currSum*10 + node.val;
        if (node.left == null && node.right == null) {
            return currSum;
        }
        return dfs2(node.left, currSum) + dfs2(node.right, currSum);
    }





    /**
     * @TimeComplexity O(n)
     * @SpaceComplexity O(w), where w is the width of the tree - for queue
     */
    public static int sumNumbersUsingQueueBfs(TreeNode root) {
        if (root == null) return 0;
        int totalSum = 0;
        class Pair {final TreeNode node; final int currSum; Pair(TreeNode node, int currSum){this.node=node; this.currSum=currSum;}}
        Queue<Pair> q = new LinkedList<>();
        q.add(new Pair(root, 0));

        while(!q.isEmpty()) {
            int n = q.size();
            while(n-- > 0) {
                Pair pair = q.poll();
                TreeNode node = pair.node;
                int currSum = pair.currSum * 10 + node.val;
                if (node.left == null && node.right == null) {
                    totalSum += currSum;
                } else {
                    if (node.left != null) q.add(new Pair(node.left, currSum));
                    if (node.right != null) q.add(new Pair(node.right, currSum));
                }
            }
        }
        return totalSum;
    }
}
