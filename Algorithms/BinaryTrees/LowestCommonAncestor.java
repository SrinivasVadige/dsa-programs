package Algorithms.BinaryTrees;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

/**
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 02 Feb 2025
 */
public class LowestCommonAncestor {
    static class TreeNode {int val;TreeNode left, right;TreeNode() {}TreeNode(int val) { this.val = val; }TreeNode(int val, TreeNode left, TreeNode right) {this.val = val;this.left = left;this.right = right;}}

    public static void main(String[] args) {
        TreeNode root = new TreeNode(3);
        root.left = new TreeNode(5);
        root.right = new TreeNode(1);
        root.left.left = new TreeNode(6);
        root.left.right = new TreeNode(2);
        root.right.left = new TreeNode(0);
        root.right.right = new TreeNode(8);
        root.left.right.left = new TreeNode(7);
        root.left.right.right = new TreeNode(4);
        /*

                                    3
                                  /   \
                                 5     1
                                / \   / \
                               6   2 0   8
                                  / \
                                 7   4
         */
        System.out.println("lowestCommonAncestorUsingRecursion(root, 5, 4): " + lowestCommonAncestorUsingRecursion(root, root.left, root.left.right.right).val); // 5,4
        System.out.println("lowestCommonAncestorUsingStackAndParentMap(root, 5, 4): " + lowestCommonAncestorUsingStackAndParentMap(root, root.left, root.left.right.right).val); // 5,4
        System.out.println("lowestCommonAncestorUsingPaths(root, 5, 4 ): " + lowestCommonAncestorUsingPaths(root, root.left, root.left.right.right).val); // 5,4
        System.out.println("lowestCommonAncestorUsingPaths2(root, 5, 4 ): " + lowestCommonAncestorUsingPaths2(root, root.left, root.left.right.right).val); // 5,4
    }

    public static TreeNode lowestCommonAncestorUsingRecursion(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null || root == p || root == q) return root;
        TreeNode left = lowestCommonAncestorUsingRecursion(root.left, p, q);
        TreeNode right = lowestCommonAncestorUsingRecursion(root.right, p, q);
        if (left != null && right != null) return root; // found both p and q
        return left != null ? left : right;
    }

    public static TreeNode lowestCommonAncestorUsingStackAndParentMap(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null) return null;

        // Stack for iterative DFS traversal
        Stack<TreeNode> stack = new Stack<>();
        // HashMap to store parent pointers
        Map<TreeNode, TreeNode> parentMap = new HashMap<>();

        // Root has no parent, start traversal
        parentMap.put(root, null);
        stack.push(root);

        // Traverse the tree until both nodes are found
        while (!parentMap.containsKey(p) || !parentMap.containsKey(q)) {
            TreeNode node = stack.pop();

            if (node.left != null) {
                parentMap.put(node.left, node);
                stack.push(node.left);
            }
            if (node.right != null) {
                parentMap.put(node.right, node);
                stack.push(node.right);
            }
        }

        // Set to store ancestors of p
        Set<TreeNode> ancestors = new HashSet<>();

        // Traverse up from p to root, storing ancestors
        while (p != null) {
            ancestors.add(p);
            p = parentMap.get(p);
        }

        // Traverse up from q until we find an ancestor of p
        while (!ancestors.contains(q)) {
            q = parentMap.get(q);
        }

        return q; // LCA found
    }



    public static TreeNode lowestCommonAncestorUsingPaths(TreeNode root, TreeNode p, TreeNode q) {
        List<TreeNode> pathToP = new ArrayList<>();
        List<TreeNode> pathToQ = new ArrayList<>();

        // Find paths to p and q
        if (!findPath(root, p, pathToP) || !findPath(root, q, pathToQ)) {
            return null; // If either node is not found
        }

        // Compare paths to find the LCA
        int i = 0;
        while (i < pathToP.size() && i < pathToQ.size() && pathToP.get(i) == pathToQ.get(i)) {
            i++;
        }

        return pathToP.get(i - 1); // LCA found
    }

    private static boolean findPath(TreeNode root, TreeNode target, List<TreeNode> path) {
        if (root == null) return false;

        Stack<TreeNode> stack = new Stack<>();
        Stack<List<TreeNode>> pathStack = new Stack<>();
        stack.push(root);
        pathStack.push(new ArrayList<>(Collections.singletonList(root)));

        while (!stack.isEmpty()) {
            TreeNode node = stack.pop();
            List<TreeNode> currentPath = pathStack.pop();

            if (node == target) {
                path.addAll(currentPath);
                return true;
            }

            if (node.right != null) {
                stack.push(node.right);
                List<TreeNode> newPath = new ArrayList<>(currentPath);
                newPath.add(node.right);
                pathStack.push(newPath);
            }

            if (node.left != null) {
                stack.push(node.left);
                List<TreeNode> newPath = new ArrayList<>(currentPath);
                newPath.add(node.left);
                pathStack.push(newPath);
            }
        }

        return false; // Target not found
    }


    public static TreeNode lowestCommonAncestorUsingPaths2(TreeNode root, TreeNode p, TreeNode q) {
        List<TreeNode> pathP = new ArrayList<>();
        Stack<TreeNode> stackP = new Stack<>();
        findPath(root, p, pathP, stackP);

        List<TreeNode> pathQ = new ArrayList<>();
        Stack<TreeNode> stackQ = new Stack<>();
        findPath(root, q, pathQ, stackQ);

        TreeNode lca = null;
        int i = 0;
        while (i < pathP.size() && i < pathQ.size() && pathP.get(i) == pathQ.get(i)) {
            lca = pathP.get(i);
            i++;
        }

        return lca;
    }

    private static boolean findPath(TreeNode node, TreeNode target, List<TreeNode> path, Stack<TreeNode> stack) {
        stack.push(node);
        path.add(node);
        if (node == target) return true;
        if (node.left != null && findPath(node.left, target, path, stack)) return true;
        if (node.right != null && findPath(node.right, target, path, stack)) return true;

        stack.pop(); // Backtrack
        path.remove(path.size() - 1);
        return false;
    }







    // NOT WORKING
    public static TreeNode lowestCommonAncestorUsingStackAndPaths(TreeNode root, TreeNode p, TreeNode q) {
        Stack<TreeNode> stack = new Stack<>();
        Stack<TreeNode> pathToP = new Stack<>();
        Stack<TreeNode> pathToQ = new Stack<>();
        // If either p or q is not found
        if (!findPath(root, p, stack, pathToP) || !findPath(root, q, stack, pathToQ)) return null;

        while (!pathToP.isEmpty() && !pathToQ.isEmpty() && pathToP.peek().equals(pathToQ.peek())) {
            stack.push(pathToP.pop());
            pathToQ.pop();
        }
        return stack.peek();
    }

    private static boolean findPath(TreeNode root, TreeNode target, Stack<TreeNode> stack, Stack<TreeNode> path) {
        if (root == null) return false;

        stack.push(root);

        if (root.equals(target)) {
            path.addAll(stack);
            return true;
        }

        if (root.left != null && findPath(root.left, target, stack, path)) return true;

        if (root.right != null && findPath(root.right, target, stack, path)) return true;

        stack.pop();
        return false;
    }






        /**
         NOT WORKING

         leftPath and rightPath are not correct. Need to do some more debugging and fix it
        */
    public TreeNode lowestCommonAncestor2(TreeNode root, TreeNode p, TreeNode q) {
        int found = 0;

        Stack<TreeNode> stack = new Stack<>();
        TreeNode trav = root, parent = root;
        List<Integer> leftPath = new ArrayList<>();
        List<Integer> rightPath = new ArrayList<>();

        while (found !=2 && (trav!=null || !stack.isEmpty())) {

            while(trav!=null) {
                System.out.println(trav.val);
                stack.push(trav);
                if (found == 0) leftPath.add(trav.val);
                if (found == 1) rightPath.add(trav.val);
                if (trav == p || trav == q) {

                    if(++found == 2) break;
                }
                trav = trav.left;
            }

            if (!stack.isEmpty()) {
                trav = stack.pop();
                trav=trav.right;
            }
        }

        System.out.println("stack:");
        stack.forEach(t->System.out.println(t.val));
        System.out.println("leftPath: " + leftPath);
        System.out.println("rightPath: " + rightPath);

        return parent;
    }
}
