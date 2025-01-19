package Algorithms.BinaryTrees;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 18 Jan 2025
 */
@SuppressWarnings("unused")
public class SymmetricTree {
    static class TreeNode {int val;TreeNode left, right;TreeNode() {}TreeNode(int val) { this.val = val; }TreeNode(int val, TreeNode left, TreeNode right) {this.val = val;this.left = left;this.right = right;}}

    public static void main(String[] args) {

        TreeNode root = new TreeNode(1);
        TreeNode two1 = new TreeNode(2);
        TreeNode two2 = new TreeNode(2);
        TreeNode three1 = new TreeNode(3);
        TreeNode three2 = new TreeNode(3);
        TreeNode four1 = new TreeNode(4);
        TreeNode four2 = new TreeNode(4);

        /*
                  1
                 / \
                2   2
               / \ / \
              3  4 4  3
        */
        // root.left = two1;
        // root.right = two2;
        // two1.left = three1;
        // two1.right = four1;
        // two2.left = four2;
        // two2.right = three2;

        /*
                  1
                 / \
                2   2
               / \ / \
                 3    3
        */
        root.left = two1;
        root.right = two2;
        two1.left = null;
        two1.right = three1;
        two2.left = null;
        two2.right = three2;

        System.out.println("isSymmetricUsingDfs: " + isSymmetricUsingDfs(root));
        System.out.println("isSymmetricUsingBfsQueue: " + isSymmetricUsingBfsQueue(root));
        System.out.println("isSymmetricBfsMyApproach: " + isSymmetricBfsMyApproach(root));
    }

    public static boolean isSymmetricUsingDfs(TreeNode root) {
        return dfs(root.left, root.right);
    }

    public static boolean dfs(TreeNode left, TreeNode right) {
        if (left == null && right == null) return true;
        else if (left == null || right == null) return false;
        else if (left.val != right.val) return false; // for root children
        else return dfs(left.left, right.right) && dfs(left.right, right.left);
    }

    public static boolean isSymmetricUsingBfsQueue(TreeNode root) {
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


    public static boolean isSymmetricBfsMyApproach(TreeNode root) {
        if(root == null || (root!=null && root.left == null && root.right==null)) return true;
        if (root!=null && (root.left==null || root.right==null)) return false;

        Queue<TreeNode> q = new LinkedList<>();
        TreeNode trav = null;
        q.add(root.left);
        q.add(root.right);
        q.add(new TreeNode(Integer.MAX_VALUE, root, root)); // or use levelSize-for-loop as this is a unique node in an binary tree, we can use this for level separator in level order traversal
        // String str="" + root.left.val + "" + root.right.val;
        String str = "";

        while(!q.isEmpty()) {
            trav = q.poll();

            if (trav == null || (trav != null && trav.val != Integer.MAX_VALUE)) { // skipping level-separator-node
                str += (trav==null?"null":trav.val) + ",";
                if (trav != null) q.add(trav.left);
                if (trav != null) q.add(trav.right);
            }

            if (trav != null && trav.val == Integer.MAX_VALUE) { // we get level-separator-node after each level
                if(str.length()==0) return true;
                str = str.substring(0, str.length()-1);
                int strMid = str.length()/2;
                String lStr = str.substring(0, strMid);
                String rStr = "";
                for (String s: str.substring(strMid+1).split(",")) {
                    rStr = s + "," + rStr;
                }
                rStr = rStr.substring(0, rStr.length()-1);
                if (lStr.length() != rStr.length() ||  !lStr.equals(rStr)) return false;
                str="";
                q.add(new TreeNode(Integer.MAX_VALUE));
            }
        }

        return true;
    }



    /**
     * Failing for [1,2,2,null,3,null,3]
     *                     1                                      1
     *                    / \                                    / \
     *                   2   2   cause it is not symmetric as   2   2   is symmetric
     *                  /\   /\                                  \ /
     *               null 3 nl 3                                 3 3
     * i.e consider null positions
     */
    public static boolean isSymmetricBfsMyOldApproach(TreeNode root) {
        if(root == null || (root!=null && root.left == null && root.left==null)) return true;
        if (root!=null && (root.left==null || root.right==null)) return false;

        Queue<TreeNode> q = new LinkedList<>();
        TreeNode trav = null;
        q.add(root.left);
        q.add(root.right);
        q.add(null);
        // String str="" + root.left.val + "" + root.right.val;
        String str = "";

        while(!q.isEmpty() || (q.size()==1 && q.peek()!=null) ) {
            trav = q.poll();
            if (trav != null) {
                str += trav.val + (q.peek()==null?"":",");
                if (trav.left != null) q.add(trav.left);
                if (trav.right != null) q.add(trav.right);
            }
            else {
                // check width
                if(str.length()==0) return true;
                int strMid = str.length()/2;
                String lStr = str.substring(0, strMid);
                String rStr = "";
                for (String s: str.substring(strMid+1).split(",")) {
                    rStr = s + "," + rStr;
                }
                rStr = rStr.substring(0, rStr.length()-1);
                if (lStr.length() != rStr.length() ||  !lStr.equals(rStr)) return false;
                str="";
                q.add(null);
            }
        }

        return true;
    }
}
