package Algorithms.BinaryTrees;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 30 Jan 2025
 */
public class BinaryTreeRightSideView {
    static class TreeNode {int val;TreeNode left, right;TreeNode() {}TreeNode(int val) { this.val = val; }TreeNode(int val, TreeNode left, TreeNode right) {this.val = val;this.left = left;this.right = right;}}

    public static void main(String[] args) {
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(3);
        root.left.left = new TreeNode(4);
        root.left.left.left = new TreeNode(5);

        /*
                    1 ←-
                   / \
                  2   3 ←-
                 /
                4 ←-
               /
              5 ←-

              if we see this tree from right side, we'll see => [1, 3, 4, 5]
         */

        System.out.println("rightSideViewUsingLevelSize: " + rightSideViewUsingLevelSize(root)
        + "\nrightSideViewUsingQueueNull: " + rightSideViewUsingQueueNull(root)
        + "\nrightSideViewUsingRecursion: " + rightSideViewUsingRecursion(root)
        );
    }

    public static List<Integer> rightSideViewUsingLevelSize(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        if (root == null) return res;
        Queue<TreeNode> q = new LinkedList<>();
        q.add(root);
        while (!q.isEmpty()) {
            int size = q.size();
            for (int i = 0; i < size; i++) {
                TreeNode node = q.poll();
                if (i == size - 1) res.add(node.val);
                if (node.left != null) q.add(node.left);
                if (node.right != null) q.add(node.right);
            }
        }
        return res;
    }

    public static List<Integer> rightSideViewUsingQueueNull(TreeNode root) {
        List<Integer> lst = new ArrayList<>();
        if(root == null) return lst;

        Queue<TreeNode> q = new LinkedList<>();
        q.add(root);
        q.add(null);
        Integer num = null;

        while (!q.isEmpty()) {
            TreeNode node = q.poll();
            if (node==null) {
                lst.add(num);
                if (!q.isEmpty()) q.add(null);
            } else {
                num = node.val;
                if (node.left != null) q.add(node.left);
                if (node.right != null) q.add(node.right);
            }
        }
        return lst;
    }


    static int maxDepth=0;
    public static List<Integer> rightSideViewUsingRecursion(TreeNode root) {
        List<Integer>lst=new ArrayList<>();
        rightView(root, lst, 1);
        return lst;
    }
    public static void rightView(TreeNode node, List<Integer> lst, int currLevel){
        if(node==null) return;
        if(maxDepth<currLevel){
            lst.add(node.val);
            maxDepth=currLevel;
        }
        rightView(node.right, lst, currLevel+1);
        rightView(node.left, lst, currLevel+1);
    }
}
