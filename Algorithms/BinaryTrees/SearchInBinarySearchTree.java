package Algorithms.BinaryTrees;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 29 April 2025
 */
public class SearchInBinarySearchTree {
    static class TreeNode {int val; TreeNode left, right; TreeNode(int x){val = x; left = right = null;}}
    public static void main(String[] args) {
        TreeNode root = new TreeNode(4);
        root.left = new TreeNode(2);
        root.right = new TreeNode(7);
        root.left.left = new TreeNode(1);
        root.left.right = new TreeNode(3);
        root.right.left = new TreeNode(6);
        root.right.right = new TreeNode(9);

        int val = 2;
        System.out.println("searchBST(root, val) => " + searchBST(root, val));
    }

    // Binary Search O(log n)
    public static TreeNode searchBST(TreeNode root, int val) {
        if (root == null || root.val == val) return root;
        if (val < root.val) return searchBST(root.left, val);
        return searchBST(root.right, val);
    }


    // Binary Search O(log n)
    public TreeNode searchBSTMyApproach(TreeNode root, int val) {
        if(root == null) return null;
        else if (root.val == val) return root;
        return root.val > val? searchBST(root.left, val) : searchBST(root.right, val);
    }


    // Binary Search O(log n)
    public TreeNode searchBST2(TreeNode root, int val) {
        while(root != null && root.val != val) {
            root = root.val > val ? root.left : root.right;
        }
        return root;
    }

    // Binary Search O(log n)
    public TreeNode searchBST3(TreeNode root, int val) {
        Queue<TreeNode> q = new LinkedList<>();
        q.add(root);
        while(!q.isEmpty()) {
            TreeNode node = q.poll();
            if(node == null) continue;
            if(node.val == val) return node;
            if(node.val > val) q.add(node.left);
            else q.add(node.right);
        }
        return null;
    }



    /**
     * same like python internal function, we can use internal class (with constructor) and don't need to pass the params
     */
    public TreeNode searchBST4(TreeNode root, int val) {
        final TreeNode[] result = new TreeNode[1]; // array of TreeNode of size 1

        class Traverse{
            Traverse(TreeNode currentNode){
                if(currentNode.val == val){
                    result[0] = currentNode;
                }

                if (currentNode.left != null) {
                    new Traverse(currentNode.left);
                }

                if (currentNode.right != null) {
                    new Traverse(currentNode.right);
                }
            }

        }
        new Traverse(root);
        return result[0];
    }
}
