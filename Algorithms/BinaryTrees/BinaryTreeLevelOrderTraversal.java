package Algorithms.BinaryTrees;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 19 Jan 2023
 * @link 102. Binary Tree Level Order Traversal <a href="https://leetcode.com/problems/binary-tree-level-order-traversal/">LeetCode Link</a>
 * @topics Tree, Binary Tree, DFS, BFS
 * @companies Meta(7), Google(6), Microsoft(3), Amazon(3), Bloomberg(3), Oracle(2), Apple(5), LinkedIn(4), Palo Alto Networks(3), Adobe(2), Goldman Sachs(2), Yandex(2), PhonePe(2), Gojek(2)
 */
public class BinaryTreeLevelOrderTraversal {
    public static class TreeNode {int val;TreeNode left, right;TreeNode() {}TreeNode(int val) { this.val = val; }TreeNode(int val, TreeNode left, TreeNode right) {this.val = val;this.left = left;this.right = right;}}
    public static void main(String[] args) {
        TreeNode root = new TreeNode(3);
        root.left = new TreeNode(9);
        root.right = new TreeNode(20);
        root.right.left = new TreeNode(15);
        root.right.right = new TreeNode(7);
        // Input: root = [3,9,20,null,null,15,7]
        // Output: [[3],[9,20],[15,7]]
        /*
                            3
                           / \
                          9  20
                            /  \
                           15   7
        */

        System.out.println("levelOrder Using BFS LevelSizeForLoop: " + levelOrderUsingBfs(root));
        System.out.println("levelOrder Using DFS Recursion: " + levelOrderUsingDfs(root));
        System.out.println("levelOrder Using BFS DummyNodeSeparator: " + levelOrderUsingBfsDummyNodeSeparator(root));

    }

    public static List<List<Integer>> levelOrderUsingBfs(TreeNode root) { // level size is different and level is different
        List<List<Integer>> list = new ArrayList<>();

        if (root == null) return list;

        Queue<TreeNode> q = new LinkedList<>();
        q.add(root);

        while(!q.isEmpty()) {
            int n = q.size();
            List<Integer> subList = new ArrayList<>();
            while (n-- > 0) {
                TreeNode node = q.poll();

                subList.add(node.val);

                if(node.left != null) q.add(node.left);
                if(node.right != null) q.add(node.right);
            }
            list.add(subList);
        }
        return list;
    }






    public static List<List<Integer>> levelOrderUsingDfs(TreeNode root) {
        List<List<Integer>> lst = new ArrayList<>();
        dfsPreOrder(root, 0, lst);
        return lst;
    }
    // Note that the level starts with 1 but here we start with 0. And finally list.size() == level+1 in this case. That's how if(lst.size()==level) works
    public static void dfsPreOrder(TreeNode node, int level, List<List<Integer>> lst) { // level == subLst position == index in lst
        if(node==null) return; // base case for leaf node
        if(lst.size()==level) { // when we're new to that specific level i.e we haven't added anything to that level / index in the lst i.e the node.left creates the new index in lst
            lst.add(new ArrayList<>());
        }
        lst.get(level).add(node.val); // if we already visited that level

        dfsPreOrder(node.left, level+1, lst);
        dfsPreOrder(node.right, level+1, lst);
    }








    public static List<List<Integer>> levelOrderUsingBfsDummyNodeSeparator(TreeNode root) {
        List<List<Integer>> lst = new ArrayList<>();
        if(root == null) return lst;
        List<Integer> subLst = new ArrayList<>();
        Queue<TreeNode> q = new LinkedList<>();
        q.add(root);
        q.add(new TreeNode(0, root, root)); // Sentinel node

        while(!q.isEmpty()) {
            TreeNode node = q.poll();
            if (node != null && node.left == root) {
                lst.add(subLst);
                subLst = new ArrayList<>();
                if (!q.isEmpty()) q.add(new TreeNode(0, root, root));
            } else {
                subLst.add(node.val);
                if (node.left != null) q.add(node.left);
                if (node.right != null) q.add(node.right);
            }
        }
        return lst;
    }
}
