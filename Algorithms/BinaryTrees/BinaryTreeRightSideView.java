package Algorithms.BinaryTrees;

import java.util.*;

/**
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 30 Jan 2025
 * @link 199. Binary Tree Right Side View <a href="https://leetcode.com/problems/binary-tree-right-side-view/">LeetCode Link</a>
 * @topics Tree, Binary Tree, DFS, BFS
 * @companies Meta(41), Bloomberg(3), Amazon(10), Google(3), Microsoft(2), Oracle(15), TikTok(10), Uber(9), Yandex(9), Walmart Labs(4), Accolite(2), Wix(2), ServiceNow(2)
 */
public class BinaryTreeRightSideView {

    public static class TreeNode {int val;TreeNode left, right;TreeNode() {}TreeNode(int val) { this.val = val; }TreeNode(int val, TreeNode left, TreeNode right) {this.val = val;this.left = left;this.right = right;}}

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

        System.out.println("rightSideView Using LevelSize: " + rightSideViewUsingLevelOrderTraversal1(root));
        System.out.println("rightSideView Using Dfs: " + rightSideViewUsingDfs1(root));
    }



    public static List<Integer> rightSideViewUsingLevelOrderTraversal1(TreeNode root) {
        List<Integer> list = new ArrayList<>();

        if (root == null) return list;

        Queue<TreeNode> q = new LinkedList<>();
        q.add(root);

        while(!q.isEmpty()) {
            int n = q.size();

            for(int i=0; i<n; i++) {
                TreeNode node = q.poll();
                if (i==0) list.add(node.val);

                if (node.right != null) q.add(node.right);
                if (node.left != null) q.add(node.left);
            }
        }

        return list;
    }





    static int levelSeen = 0;
    static List<Integer> list;
    public static List<Integer> rightSideViewUsingDfs1(TreeNode root) {
        list = new ArrayList<>();
        dfs(root, 1);
        return list;
    }
    private static void dfs(TreeNode node, int level) {
        if (node == null) return;

        if (level > levelSeen) { // new level
            levelSeen++;
            list.add(node.val);
        }

        dfs(node.right, level+1);
        dfs(node.left, level+1);
    }







    public List<Integer> rightSideViewUsingDfs2(TreeNode root) {
        list = new ArrayList<>();
        dfs2(root, 0);
        return list;
    }
    public void dfs2(TreeNode node, int level) {
        if (node == null) return;

        if (level == list.size()) { // new level ---> here, list.size() is same as levelSeen variable
            list.add(node.val);
        }

        dfs2(node.right, level + 1);
        dfs2(node.left, level + 1);
    }





    public static List<Integer> rightSideViewUsingLevelOrderTraversal2(TreeNode root) {
        List<Integer> list = new ArrayList<>();
        if(root==null) return list;
        Queue<TreeNode> q = new LinkedList<>();
        q.add(root);
        while(!q.isEmpty()) {
                int n = q.size();
            for(int i=0; i<n; i++){
                TreeNode curr = q.poll();
                if(curr.left != null) q.add(curr.left);
                if(curr.right != null) q.add(curr.right);
                if(i==n-1) list.add(curr.val);
            }
        }
        return list;
    }






    public static List<Integer> rightSideViewUsingLevelOrderTraversal3(TreeNode root) {
        List<Integer> list = new ArrayList<>();
        if (root == null) return list;
        Queue<TreeNode> q = new LinkedList<>();
        q.add(root);
        while (!q.isEmpty()) {
            int size = q.size();
            for (int i = 0; i < size; i++) {
                TreeNode node = q.poll();
                if (i == size - 1) list.add(node.val);
                if (node.left != null) q.add(node.left);
                if (node.right != null) q.add(node.right);
            }
        }
        return list;
    }




    // BFS: One Queue + Sentinel
    public static List<Integer> rightSideViewUsingLevelOrderTraversal4(TreeNode root) {
        List<Integer> list = new ArrayList<>();
        if(root == null) return list;

        Queue<TreeNode> q = new LinkedList<>();
        q.add(root);
        q.add(null); // end of level - Sentinel Node
        Integer prevNum = null;

        while (!q.isEmpty()) {
            TreeNode node = q.poll();
            if (node==null) { // end of level`
                list.add(prevNum);
                if (!q.isEmpty()) q.add(null);
            } else {
                prevNum = node.val;
                if (node.left != null) q.add(node.left);
                if (node.right != null) q.add(node.right);
            }
        }
        return list;
    }




    // BFS: One Queue + Sentinel
    public static List<Integer> rightSideViewUsingLevelOrderTraversal5(TreeNode root) {
        List<Integer> list = new ArrayList<>();
        if (root == null) return new ArrayList<Integer>();

        Queue<TreeNode> q = new LinkedList<>() { // An extra synthetic class at runtime
            {
                offer(root);
                offer(null);
            }
        };
        TreeNode prev, curr = root;

        while (!q.isEmpty()) {
            prev = curr;
            curr = q.poll();

            while (curr != null) { // separate loop for each level
                if (curr.left != null) q.offer(curr.left);
                if (curr.right != null) q.offer(curr.right);

                prev = curr;
                curr = q.poll();
            }

            list.add(prev.val); // now curr == null
            if (!q.isEmpty()) q.offer(null); // sentinel to mark the end
        }
        return list;
    }







    // BFS: Two Queues
    public static List<Integer> rightSideViewUsingLevelOrderTraversal6(TreeNode root) {
        if (root == null) return new ArrayList<Integer>();

        Queue<TreeNode> nextLevel = new ArrayDeque<>();
        nextLevel.add(root);
        Queue<TreeNode> currLevel = new ArrayDeque<>();
        List<Integer> list = new ArrayList<>();

        while (!nextLevel.isEmpty()) {
            currLevel = nextLevel;
            nextLevel = new ArrayDeque<>();

            TreeNode node = null;
            while (!currLevel.isEmpty()) {
                node = currLevel.poll();

                if (node.left != null) nextLevel.offer(node.left);
                if (node.right != null) nextLevel.offer(node.right);
            }

            list.add(node.val); // last node in level order
        }
        return list;
    }
}
