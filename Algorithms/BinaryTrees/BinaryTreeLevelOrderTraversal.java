package Algorithms.BinaryTrees;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 19 Jan 2023
 */
public class BinaryTreeLevelOrderTraversal {
    static class TreeNode {int val;TreeNode left, right;TreeNode() {}TreeNode(int val) { this.val = val; }TreeNode(int val, TreeNode left, TreeNode right) {this.val = val;this.left = left;this.right = right;}}
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

        System.out.println("levelOrderUsingLevelSizeForLoop: " + levelOrderUsingLevelSizeForLoop(root));
        System.out.println("levelOrderUsingDummyNodeSeparator: " + levelOrderUsingDummyNodeSeparator(root));
        System.out.println("levelOrderUsingRecursion: " + levelOrderUsingRecursion(root));

    }

    public static List<List<Integer>> levelOrderUsingLevelSizeForLoop(TreeNode root) { // level size is different and level is different
        List<List<Integer>> lst = new ArrayList<>();
        if(root == null) return lst;
        Queue<TreeNode> q = new LinkedList<>();
        q.add(root);
        while(!q.isEmpty()) {
            int size = q.size();
            List<Integer> subLst = new ArrayList<>();
            for(int i=0; i<size; i++) {
                TreeNode node = q.poll();
                subLst.add(node.val);
                if(node.left != null) q.add(node.left);
                if(node.right != null) q.add(node.right);
            }
            lst.add(subLst);
        }
        return lst;
    }

    public static List<List<Integer>> levelOrderUsingDummyNodeSeparator(TreeNode root) {
        List<List<Integer>> lst = new ArrayList<>();
        if(root == null) return lst;
        List<Integer> subLst = new ArrayList<>();
        Queue<TreeNode> q = new LinkedList<>();
        q.add(root);
        q.add(new TreeNode(0, root, root));

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

    public static List<List<Integer>> levelOrderUsingRecursion(TreeNode root) {
        List<List<Integer>> lst =new ArrayList<>();
        dfsPreOrder(root, 0, lst);
        return lst;
    }
    // Note that the level starts with 1 but here we start with 0. And finally list.size() == level+1 in this case. That's how if(lst.size()==level) works
    public static void dfsPreOrder(TreeNode node, int level, List<List<Integer>> lst) { // level == subLst position == index in lst
        if(node==null) return; // base case for leaf node
        if(lst.size()==level) { // when we're new to to that specific level i.e we haven't added anything to that level / index in the lst i.e the node.left creates the new index in lst
            List<Integer> subLst =new ArrayList<>();
            subLst.add(node.val);
            lst.add(subLst);
        }
        else lst.get(level).add(node.val); // if we already visited that level

        dfsPreOrder(node.left, level+1, lst);
        dfsPreOrder(node.right, level+1, lst);
    }
}
