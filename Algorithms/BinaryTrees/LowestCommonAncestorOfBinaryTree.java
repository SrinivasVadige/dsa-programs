package Algorithms.BinaryTrees;

import java.util.*;

/**
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 02 Feb 2025
 * @link 236. Lowest Common Ancestor of a Binary Tree <a href="https://leetcode.com/problems/lowest-common-ancestor-of-a-binary-tree/">LeetCode Link</a>
 * @topics Tree, DFS, Binary Tree
 * @companies Meta, Amazon, Google, Atlassian, Microsoft, TikTok, BitGo, Lucid, NewsBreak, Bloomberg, Apple, Yandex, LinkedIn, Oracle, Adobe, Salesforce, GE Healthcare, Flipkart, Goldman Sachs, Wix, Myntra, Yahoo
 * @see Algorithms.BinaryTrees.LowestCommonAncestorOfBinarySearchTree
 */
public class LowestCommonAncestorOfBinaryTree {
    public static class TreeNode {int val; TreeNode left, right; TreeNode(int val) {this.val = val;} TreeNode(int val, TreeNode left, TreeNode right) {this.val = val;this.left = left;this.right = right;}
        @Override public String toString() {return "TreeNode{val=" + val + " }";}
    }

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
        System.out.println("lowestCommonAncestor Using Recursion (root, 5, 4): " + lowestCommonAncestorUsingRecursion(root, root.left, root.left.right.right)); // 5,4
        System.out.println("lowestCommonAncestor Using Recursion2 (root, 5, 4): " + lowestCommonAncestorUsingRecursion2(root, root.left, root.left.right.right)); // 5,4
        System.out.println("lowestCommonAncestor Using ParentPointers (root, 5, 4): " + lowestCommonAncestorUsingParentPointers(root, root.left, root.left.right.right)); // 5,4
        System.out.println("lowestCommonAncestor Using Explicit Stack and State Machine (root, 5, 4): " + lowestCommonAncestorUsingExplicitStackAndStateMachine(root, root.left, root.left.right.right));
        System.out.println("lowestCommonAncestor Using Paths (root, 5, 4 ): " + lowestCommonAncestorUsingPaths(root, root.left, root.left.right.right)); // 5,4
        System.out.println("lowestCommonAncestorUsingPaths2(root, 5, 4 ): " + lowestCommonAncestorUsingPaths2(root, root.left, root.left.right.right)); // 5,4
    }



    /**
                          p=5,q=0

                                3
                              /    \
                      [T]    5      1
                           /   \    /\
                          6     2  0  8 [T]
                               / \
                              7   4



                          p=5,q=4

                                3
                              /    \
                      [T]    5      1
                           /   \    /\
                          6     2  0  8
                               / \
                              7   4 [T]

     */
    static TreeNode res;

    /**
     * @TimeComplexity O(N)
     * @SpaceComplexity O(N)
     */
    public static TreeNode lowestCommonAncestorUsingRecursion(TreeNode root, TreeNode p, TreeNode q) {  // lowestCommonAncestorUsingDfs
        res = null;
        dfs(root, p, q);
        return res;
    }
    public static boolean dfs(TreeNode node, TreeNode p, TreeNode q) {
        if(node == null) return false;

        boolean isCurr = node == p || node == q;
        boolean left = dfs(node.left, p, q);
        boolean right = dfs(node.right, p, q);

        if(isCurr && (left || right)) {
            res = node;
        } else if (left && right) {
            res = node;
        }

        return isCurr || left || right;
    }






    /**
     * 🔥🔥🔥
     * @TimeComplexity O(N)
     * @SpaceComplexity O(1), but we can say O(N) if we count the stack space
     * If p or q == root node, we can return root as LCA, cause we know that p & q are must be in the root tree


     Instead of this decision making

                          p=5,q=4

                                3
                              /    \
                      [T]    5      1
                           /   \    /\
                          6     2  0  8
                               / \
                              7   4 [T]


     Return Node(5), don't traverse inside this Node(5) and if we didn't find the Node(4) in right-side of Node(3)
     then Node(4) must be inside Node(5), as the problem statement says that p & q are must be in the root tree

                              p=5,q=4
                                              ___
                                3              ↑
                              /    \           |
                      [T]    5      1         [F]
                           /   \    /\         |
                          6     2  0  8        ↓
                               / \            ---
                              7   4


     */
    public static TreeNode lowestCommonAncestorUsingRecursion2(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null || root == p || root == q) return root;

        TreeNode left = lowestCommonAncestorUsingRecursion2(root.left, p, q);
        TreeNode right = lowestCommonAncestorUsingRecursion2(root.right, p, q);

        if (left != null && right != null) return root; // found both p and q in curr node's left and right subtree
        return left != null ? left : right; // q is child of p or p is child of q
    }








    private static TreeNode ans=null;
    public static TreeNode lowestCommonAncestorUsingRecursion3(TreeNode root, TreeNode p, TreeNode q) { // lowestCommonAncestorUsingDfs2
        helper(root, p,  q);
        return ans;
    }
    private static boolean helper(TreeNode root, TreeNode p, TreeNode q){
        if(root==null) return false;

        int mid=(root==p || root==q)?1:0;
        int left = helper(root.left, p, q)?1:0;
        int right = helper(root.right, p, q)?1:0;

        if(left+mid+right>=2){
            ans=root;
        }

        return left+mid+right>0;
     }









    /**
     * @TimeComplexity O(N)
     * @SpaceComplexity O(N)


                          p=5,q=4

                                3
                              /    \
                      [T]    5      1
                           /   \    /\
                          6     2  0  8
                               / \
                              7   4 [T]

         pAncestors = [5, 3]
         qAncestors = [4, 2, 5, 3]

     */
    public static TreeNode lowestCommonAncestorUsingParentPointers(TreeNode root, TreeNode p, TreeNode q) { // StackAndParentMap
        Stack<TreeNode> stack = new Stack<>(); // Stack DFS or Recursion DFS
        stack.push(root);
        Map<TreeNode, TreeNode> parent = new HashMap<>(); // HashMap for parent pointers
        parent.put(root, null);

        // Iterate until we find both the nodes p and q
        while (!parent.containsKey(p) || !parent.containsKey(q)) {
            TreeNode node = stack.pop();
            if (node.left != null) {
                parent.put(node.left, node);
                stack.push(node.left);
            }
            if (node.right != null) {
                parent.put(node.right, node);
                stack.push(node.right);
            }
        }

        Set<TreeNode> pAncestors = new HashSet<>(); // all p's ancestors
        while (p != null) {
            pAncestors.add(p);
            p = parent.get(p);
        }

        while (!pAncestors.contains(q)) { // The first ancestor of q which appears in p's ancestor set() is their lowest common ancestor.
            q = parent.get(q);
        }

        return q;
    }







    // Three static flags to keep track of post-order traversal.
    private final static int BOTH_PENDING = 2; // Both left and right traversal pending for a node. Indicates the nodes children are yet to be traversed.
    private final static int LEFT_DONE = 1; // Left traversal done.
    private final static int BOTH_DONE = 0; // Both left and right traversal done for a node. Indicates the node can be popped off the stack.
    public static TreeNode lowestCommonAncestorUsingExplicitStackAndStateMachine(TreeNode root, TreeNode p, TreeNode q) {
        class Pair<K, V> {private final K key; private final V value; public Pair(K key, V value) {this.key = key;this.value = value;}public K getKey() {return key;}public V getValue() {return value;}}
        Stack<Pair<TreeNode, Integer>> stack = new Stack<>(); // Stack<Map.Entry<TreeNode, Integer>> and Map.Entry<TreeNode, Integer> = new AbstractMap.SimpleEntry<>(root, BOTH_PENDING);
        stack.push(new Pair<>(root, BOTH_PENDING));

        boolean one_node_found = false; // This flag is set when either one of p or q is found.
        TreeNode LCA = null;
        TreeNode child_node = null;

        // We do a post order traversal of the binary tree using stack
        while (!stack.isEmpty()) {

            Pair<TreeNode, Integer> top = stack.peek();
            TreeNode parent_node = top.getKey();
            int parent_state = top.getValue();

            // If the parent_state is not equal to BOTH_DONE, this means the parent_node can't be popped off yet.
            if (parent_state != BOTH_DONE) {

                // If both child traversals are pending
                if (parent_state == BOTH_PENDING) {

                    // Check if the current parent_node is either p or q.
                    if (parent_node == p || parent_node == q) {

                        if (one_node_found) { // If one_node_found was set already, this means we have found both the nodes.
                            return LCA;
                        } else {
                            one_node_found = true; // Otherwise, set one_node_found to True, to mark one of p and q is found.
                            LCA = stack.peek().getKey(); // Save the current top element of stack as the LCA.
                        }
                    }
                    child_node = parent_node.left; // If both pending, traverse the left child first
                } else {
                    child_node = parent_node.right; // traverse right child
                }

                // Update the node state at the top of the stack. Since we have visited one more child.
                stack.pop();
                stack.push(new Pair<>(parent_node, parent_state-1));

                // Add the child node to the stack for traversal.
                if (child_node != null) {
                    stack.push(new Pair<>(child_node, BOTH_PENDING));
                }
            } else {

                // If the parent_state of the node is both done, the top node could be popped off the stack. Update the LCA node to be the next top node.
                if (LCA == stack.pop().getKey() && one_node_found) {
                    LCA = stack.peek().getKey();
                }

            }
        }

        return null;
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








    static TreeNode lca;
    public static TreeNode lowestCommonAncestorBruteForce(TreeNode root, TreeNode p, TreeNode q) {
        lca = null;
        if(root == null) return null;
        if(helper(root, p, q, new boolean[2])) lca=root; // this lca value is replaced by all the common ancestors of p and q but final one is Least Common Ancestor
        lowestCommonAncestorBruteForce(root.left, p, q);
        lowestCommonAncestorBruteForce(root.right, p, q);
        return lca;
    }
    private static boolean helper(TreeNode node, TreeNode p, TreeNode q, boolean[] arr) {
        if(arr[0] && arr[1]) return true;
        if(node == null) return false;
        if(node == p) arr[0]=true;
        if(node == q) arr[1]=true;
        boolean left = helper(node.left, p, q, arr);
        boolean right = helper(node.right, p, q, arr);
        return (left || right);
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
