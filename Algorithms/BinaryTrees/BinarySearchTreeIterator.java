package Algorithms.BinaryTrees;

import java.util.*;

/**
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 02 Dec 2025
 * @link 173. Binary Search Tree Iterator <a href="https://leetcode.com/problems/binary-search-tree-iterator/">LeetCode Link</a>
 * @topics Tree, Binary Tree, Stack, Design, Binary Search Tree, Iterator
 * @companies Meta(3), Amazon(5), Google(2), Apple(2), Microsoft(9), Adobe(6), Bloomberg(3), LinkedIn(2), Uber(2)
 */
public class BinarySearchTreeIterator {
    public static class TreeNode {int val;TreeNode left, right;TreeNode() {}TreeNode(int val) { this.val = val; }TreeNode(int val, TreeNode left, TreeNode right) {this.val = val;this.left = left;this.right = right;}}

    public static void main(String[] args) {
        TreeNode root = new TreeNode(7);
        TreeNode three = new TreeNode(3);
        TreeNode fifteen = new TreeNode(15);
        TreeNode nine = new TreeNode(9);
        TreeNode twenty = new TreeNode(20);
        root.left = three;
        root.right = fifteen;
        fifteen.left = nine;
        fifteen.right = twenty;

        System.out.println("BSTIterator using Stack: ");
        BSTIterator bstIterator = new BSTIterator(root);
        while (bstIterator.hasNext()) System.out.print(bstIterator.next() + " ");
        System.out.println();

        System.out.println("BSTIterator using Stack Full Space: ");
        BSTIteratorUsingStackFullSpace bstIteratorUsingStackFullSpace = new BSTIteratorUsingStackFullSpace(root);
        while (bstIteratorUsingStackFullSpace.hasNext()) System.out.print(bstIteratorUsingStackFullSpace.next() + " ");
        System.out.println();

        System.out.println("BSTIterator using Morris Traversal: ");
        BSTIteratorUsingMorrisTraversal bstIteratorUsingMorrisTraversal = new BSTIteratorUsingMorrisTraversal(root);
        while (bstIteratorUsingMorrisTraversal.hasNext()) System.out.print(bstIteratorUsingMorrisTraversal.next() + " ");
        System.out.println();
    }




    /**
     * @TimeComplexity O(1)
     * @SpaceComplexity O(h), h = height of the tree - Stack
    */
    static class BSTIterator {

        Stack<TreeNode> stack = new Stack<>();

        public BSTIterator(TreeNode root) {
            pushNodes(root);
        }

        public int next() {
            TreeNode popped = stack.pop();
            if (popped.right != null) pushNodes(popped.right);
            return popped.val;
        }

        public boolean hasNext() {
            return !stack.isEmpty();
        }

        private void pushNodes(TreeNode node) {
            while (node != null) {
                stack.push(node);
                node = node.left;
            }
        }
    }




    /**
     * @TimeComplexity O(1)
     * @SpaceComplexity O(n), n = number of nodes in the tree - Stack
    */
    static class BSTIteratorUsingStackFullSpace {

        Stack<Integer> stack = new Stack<>();

        public BSTIteratorUsingStackFullSpace(TreeNode root) {
            addNumsToStack(root);
        }

        public int next() {
            return stack.pop();
        }

        public boolean hasNext() {
            return !stack.isEmpty();
        }

        private void addNumsToStack(TreeNode node) {
            if (node == null) return;
            addNumsToStack(node.right);
            stack.push(node.val);
            addNumsToStack(node.left);
        }
    }






    /**
     * @TimeComplexity O(h) where h = height of the tree -> for each next() call
     * @SpaceComplexity O(1)
    */
    static class BSTIteratorUsingMorrisTraversal {

        TreeNode cur;

        public BSTIteratorUsingMorrisTraversal(TreeNode root) {
            cur = root;
        }

        public int next() {
            int res = -1;

            while(cur != null && res == -1) {
                if(cur.left == null) {
                    res = cur.val;
                    cur = cur.right;
                } else {
                    TreeNode predecessor = cur.left;
                    while(predecessor.right != null && predecessor.right != cur) { // TimeComplexity O(h)
                        predecessor = predecessor.right;
                    }

                    if(predecessor.right == null) {
                        predecessor.right = cur;
                        cur = cur.left;
                    } else {
                        predecessor.right = null;
                        res = cur.val;
                        cur = cur.right;
                    }
                }
            }

            return res;
        }

        public boolean hasNext() {
            return cur != null;
        }
    }





    static class BSTIteratorUsingMorrisTraversalNotWorking {

        TreeNode prevRoot;
        Set<TreeNode> predecessorSet;
        Map<TreeNode, TreeNode> parentToPredecessor;
        TreeNode root;
        public BSTIteratorUsingMorrisTraversalNotWorking(TreeNode root) {
            predecessorSet = new HashSet<>();
            parentToPredecessor = new HashMap<>();

            this.prevRoot = root;
            morrisTraversal(root);

            while (root.left != null) root = root.left;
            this.root = root;
        }

        public int next() {
            if (root == null) return 0;

            int val = root.val;

            TreeNode next = root.right;

            // if (predecessorSet.contains(root)) {
            //     root.right = null;
            //     predecessorSet.remove(root);
            // }

            if (parentToPredecessor.containsKey(root)) {
                root = prevRoot = next;
                while (root != null && root.left != null) root = root.left;
            } else root = next;

            return val;
        }

        public boolean hasNext() {
            return root != null;
        }

        private void morrisTraversal(TreeNode node) {
            while (node != null) {
                if (node.left == null) {
                    node = node.right;
                } else {
                    TreeNode predecessor = node.left;
                    while (predecessor.right != null && predecessor.right != node) predecessor = predecessor.right;

                    if (predecessor.right == null) {
                        predecessor.right = node;
                        predecessorSet.add(predecessor);
                        parentToPredecessor.put(node, predecessor);
                        node = node.left;
                    } else {
                        node = node.right;
                    }
                }
            }
        }
    }
}
