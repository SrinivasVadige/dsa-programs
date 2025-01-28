package DataStructures;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Stack;

/**
<pre>
    1. build tree using array
    2. print tree in the form of array
    3. invert tree (update, swap left and right nodes)
    4. traverse using recursion
    5. DFS traversals (pre-order, in-order, post-order traversals)
    6. BFS traversals (level order traversal)
    7. height of tree
    8. diameter of tree
    9. size of tree
    10. clone tree via pre-order traversal
    11. check if two trees are identical or not
    12. check if two trees are symmetric/mirror or not
    13. check if tree height is balanced or not
    14. check if tree is complete
    15. check if tree is full
    16. check if tree is subtree of another tree
    17. check if tree is binary search tree BST
    18. check if tree is uni-val tree

</pre>
 *
 * A tree is a undirected graph which satisfies the following properties:
 * - Only one Root Node (Root Node has no parent or parent node is itself)
 * - An Acyclic connect graph
 * - N nodes & N-1 edges
 * - Two vertices are connected by exactly one edge i.e exactly one path
 * Example: File System Tree or directories --> /, /usr, /temp, /usr/local, /usr/local/bin
 *
 * - root node level is always 0 (HORIZONTAL LINE)
 * - root node height if it does not have any child node then 0, otherwise 1 + max(height(left), height(right))
 * - calculate height from bottom
 * - depth and level are same. depth is opposite of height
 * - level, height and depth are edges count not nodes
 *
 * PROPERTIES OF BINARY TREE
 * - Maximum number of nodes in the level l is 2^l => level 0 = 2^0, level 1 = 2^1, level 2 = 2^2, level 3 = 2^3.....
 * - Total maximum number(from root to current height) of nodes at given height is "2^(h+1) - 1" or "2^(l+1) - 1". Because it's summation of all max nodes in each level i.e 2^0 + 2^1 + 2^2 + 2^3 + 2^(h) => 2^(h(h+1)/2) => 2^(h+1) - 1
 * - Minimum number of nodes at height h is h+1.
 * - Minimum Height h at given nodes n is log2(n+1)-1. Because n=2^(h+1)-1 => n+1 = 2^(h+1) => log2(n+1) = h+1 => h = log2(n+1)-1
 *
 * THEORY:
 * - GFG Introduction to Binary Tree: https://www.geeksforgeeks.org/introduction-to-binary-tree/ (read all next articles)
 *
 *
* @author Srinivas Vadige, srinivas.vadige@gmail.com
* @since 23 Sept 2024
*/
@SuppressWarnings("unused")
public class BinaryTreeBasics {

    static class TreeNode {
        int val;
        TreeNode left, right;
        TreeNode() {}
        TreeNode(int val) { this.val = val; }
        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }

    public static void main(String[] args) {
        // 1. BUILD TREE USING ARRAY
        System.out.println("1. BUILD TREE USING ARRAY");
        int[] nums = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10}; // which is same as 1 2 3 4 5 6 7 8 9 10 null null null null null
        TreeNode root = buildTree(nums);


        // 2. PRINT TREE
        System.out.println("2. PRINT TREE");
        printTree(root).forEach(System.out::println);
        /*
                   1
                  / \
                 2   3
                / \ / \
               4  5 6  7
              / \ /
             8  9 10

             1 2 3 4 5 6 7 8 9 10 null null null null null
        */


        // 3. INVERT TREE
        System.out.println("\n\n3. INVERT TREE");
        invertTree(root);
        printTree(root).forEach(System.out::println);
        /*
                          1
                   _______|_______
                  /               \
                 3                 2
                / \               / \
               7   6             5   4
              /\   /\           /\   /\
            nl nl nl nl        nl 10  9 8


            1 3 2 7 6 5 4 null null null null null 10 9 8
        */
        invertTree(root); // invert back to get original tree


        // 4. TRAVERSE TREE USING RECURSION
        System.out.println("\n4. TRAVERSE TREE USING RECURSION");
        travUsingRecursion(root);


        // 5. DFS TRAVERSAL
        System.out.println("\n\n5. DFS TRAVERSAL");
        /*
                               1
                              / \
                             2   3
                            / \ / \
                           4  5 6  7
                          / \ /
                         8  9 10

            treeNode =  [1, 2, 3, 4, 5, 6, 7, 8, 9, 10]
            preOrder =  [1, 2, 4, 8, 9, 5, 10, 3, 6, 7] // same as pre-order recursion
            inOrder =   [8, 4, 9, 2, 10, 5, 1, 6, 3, 7]
            postOrder = [8, 9, 4, 10, 5, 2, 6, 7, 3, 1]
         */
        System.out.println("PRE-ORDER TRAVERSAL USING RECURSION");
        preOrderTraversalRecursion(root);
        System.out.println("\nPRE-ORDER TRAVERSAL USING STACK");
        preOrderTraversalUsingStack(root);
        System.out.println("\nPRE-ORDER TRAVERSAL USING QUEUE ----> TODO: can't use queue for pre-order traversal");
        preOrderTraversalUsingQueue(root);
        System.out.println("\nIN-ORDER TRAVERSAL USING RECURSION");
        inOrderTraversalRecursion(root);
        System.out.println("\nIN-ORDER TRAVERSAL USING STACK");
        inOrderTraversalUsingStack(root);
        System.out.println("\nIN-ORDER TRAVERSAL USING QUEUE");
        inOrderTraversalUsingQueue(root);
        System.out.println("\nPOST-ORDER TRAVERSAL USING RECURSION");
        postOrderTraversalRecursion(root);
        System.out.println("\nPOST-ORDER TRAVERSAL USING STACK");
        postOrderTraversalUsingStack(root);
        System.out.println("\nPOST-ORDER TRAVERSAL USING QUEUE");
        postOrderTraversalUsingQueue(root);
        System.exit(0);

        // 6. BFS TRAVERSAL
        System.out.println("\n\n6.1 BFS TRAVERSAL");
        System.out.println("LEVEL ORDER TRAVERSAL");
        levelOrderTraversal(root);
        System.out.println("\n6.2 BFS LEVEL ORDER TRAVERSAL PRINT EACH LEVEL USING NULL SEPARATOR");
        levelOrderTraversalPrintLevelUsingNullSeparator(root);
        System.out.println("\n6.3 BFS LEVEL ORDER TRAVERSAL PRINT EACH LEVEL USING DUMMY NODE SEPARATOR");
        levelOrderTraversalPrintLevelUsingDummyNodeSeparator(root);
        System.out.println("\n6.4 BFS LEVEL ORDER TRAVERSAL PRINT EACH LEVEL USING LEVEL/QUEUE SIZE FOR LOOP"); // levelSize if number of nodes at that level and level number is difference
        levelOrderTraversalPrintLevelUsingLevelSizeForLoop(root);
        System.out.println("\n6.5 BFS LEVEL ORDER TRAVERSAL PRINT EACH LEVEL USING QUEUE NODES COUNT FOR LOOP");
        levelOrderTraversalPrintLevelUsingCountAndLevelSizeForLoop(root);


        // 7. HEIGHT OF TREE
        System.out.println("\n\n7.1 HEIGHT / DEPTH OF TREE USING RECURSION");
        System.out.println(getHeight(root));
        System.out.println("\n7.2 HEIGHT / DEPTH OF TREE USING DFS STACK");
        System.out.println(getHeightUsingDfsStack(root));
        System.out.println("\n7.3 HEIGHT / DEPTH OF TREE USING BFS QUEUE");
        System.out.println(getHeightUsingBfsQueue(root));

        // 8. DIAMETER OF TREE
        System.out.println("\n\n8.1 DIAMETER OF TREE");
        System.out.println(getDiameter(root));
        System.out.println("\n8.2 DIAMETER OF TREE USING DFS STACK");
        System.out.println(getDiameterUsingDfsStack(root));
        System.out.println("\n8.3 DIAMETER OF TREE USING BFS QUEUE");
        System.out.println(getDiameterUsingBfsQueue(root));


        // 9. SIZE OF THE TREE (TOTAL NUMBER OF NODES)
        System.out.println("\n\n9. SIZE OF THE TREE (TOTAL NUMBER OF NODES)");
        System.out.println(getSize(root));


        // 10. CLONE TREE VIA PRE-ORDER TRAVERSAL
        System.out.println("\n\n10. CLONE TREE VIA PRE-ORDER TRAVERSAL");
        cloneTree(root);


        // 11. CHECK IF TWO TREES ARE IDENTICAL
        System.out.println("\n\n11. CHECK IF TWO TREES ARE IDENTICAL");
        TreeNode root1 = buildTree(new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10}); // which is same as 1 2 3 4 5 6 7 8 9 10 null null null null null
        TreeNode root2 = buildTree(new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10}); // which is same as 1 2 3 4 5 6 7 8 9 10 null null null null null
        System.out.println(isSameTree(root1, root2));


        // 12. CHECK IF TWO TREES ARE SYMMETRIC
        System.out.println("\n\n12.1 CHECK IF TWO TREES ARE SYMMETRIC USING DFS");
        TreeNode root3 = buildTree(new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10}); // which is same as 1 2 3 4 5 6 7 8 9 10 null null null null null
        System.out.println(isSymmetricUsingDfs(root3));
        System.out.println("\n\n12.2 CHECK IF TWO TREES ARE SYMMETRIC USING BFS");
        System.out.println(isSymmetricUsingBfs(root3));


        // 13. CHECK IF TREE IS HEIGHT BALANCED
        System.out.println("\n\n13. CHECK IF TREE IS HEIGHT BALANCED");
        TreeNode root4 = buildTree(new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10}); // which is same as 1 2 3 4 5 6 7 8 9 10 null null null null null
        System.out.println(isBalanced(root4));


        // 14. CHECK IF TREE IS COMPLETE
        System.out.println("\n\n14. CHECK IF TREE IS COMPLETE");
        TreeNode root5 = buildTree(new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10}); // which is same as 1 2 3 4 5 6 7 8 9 10 null null null null null
        System.out.println(isComplete(root5));


        // 15. CHECK IF TREE IS FULL
        System.out.println("\n\n15. CHECK IF TREE IS FULL");
        TreeNode root6 = buildTree(new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10}); // which is same as 1 2 3 4 5 6 7 8 9 10 null null null null null
        System.out.println(isFull(root6));


        // 16. CHECK IF TREE IS SUBTREE
        System.out.println("\n\n16. CHECK IF TREE IS SUBTREE");
        TreeNode root7 = buildTree(new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10}); // which is same as 1 2 3 4 5 6 7 8 9 10 null null null null null
        TreeNode root8 = buildTree(new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10}); // which is same as 1 2 3 4 5 6 7 8 9 10 null null null null null
        System.out.println(isSubtree(root7, root8));


        // 17. CHECK IF TREE IS BINARY SEARCH TREE
        System.out.println("\n\n17. CHECK IF TREE IS BINARY SEARCH TREE");
        TreeNode root9 = buildTree(new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10}); // which is same as 1 2 3 4 5 6 7 8 9 10 null null null null null
        System.out.println(isBST(root9));


        // 18. CHECK IF TREE IS UNI-VALUE TREE
        System.out.println("\n\n18. CHECK IF TREE IS UNI-VALUE TREE");
        TreeNode root10 = buildTree(new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10}); // which is same as 1 2 3 4 5 6 7 8 9 10 null null null null null
        System.out.println(isUniValTree(root10));

    }



    public static TreeNode buildTree(int[] nums) {
        if (nums == null || nums.length == 0) {
            return null;
        }
        TreeNode root = new TreeNode(nums[0]);
        Queue<TreeNode> q = new LinkedList<>();
        q.add(root);
        int i = 1;
        while (i < nums.length) {
            TreeNode curr = q.remove();
            if (i < nums.length) {
                curr.left = new TreeNode(nums[i++]);
                q.add(curr.left);
            }
            if (i < nums.length) {
                curr.right = new TreeNode(nums[i++]);
                q.add(curr.right);
            }
        }
        return root;
    }


    public static List<List<String>> printTree(final TreeNode root) {
        final int width = (int) Math.pow(2, getHeight(root)) - 1;
        final List<List<String>> result = new ArrayList<>();

        dfs(root, result, 0, width, 0, width);

        return result;
    }
    private static void dfs(final TreeNode root, final List<List<String>> result, final int l, final int r, final int level, final int width) {
        if(root != null) {
            if(level >= result.size()) {
                result.add(new ArrayList<>());

                for(int i = 0; i < width; ++i)
                    result.get(level).add("");
            }

            final int mid = (l + r) / 2;

            result.get(level).set(mid, String.valueOf(root.val));

            dfs(root.left, result, l, mid, level + 1, width);
            dfs(root.right, result, mid, r, level + 1, width);
        }
    }

    public static TreeNode invertTree(TreeNode root) {
        if (root == null) return null;
        swapNodes(root);
        return root;
    }
    public static void swapNodes(TreeNode node){
        if(node == null) return;

            TreeNode temp=node.left;
            node.left=node.right;
            node.right=temp;

            swapNodes(node.left);
            swapNodes(node.right);
    }

    private static void travUsingRecursion(final TreeNode root) {
        if (root == null) return;
        System.out.print(root.val + " ");
        travUsingRecursion(root.left);
        travUsingRecursion(root.right);
    }

    private static void preOrderTraversalRecursion(final TreeNode root) {
        if (root == null) return;
        System.out.print(root.val + " ");
        preOrderTraversalRecursion(root.left);
        preOrderTraversalRecursion(root.right);
    }

    private static void inOrderTraversalRecursion(final TreeNode root) {
        if (root == null) return;
        inOrderTraversalRecursion(root.left);
        System.out.print(root.val + " ");
        inOrderTraversalRecursion(root.right);
    }

    private static void postOrderTraversalRecursion(final TreeNode root) {
        if (root == null) return;
        postOrderTraversalRecursion(root.left);
        postOrderTraversalRecursion(root.right);
        System.out.print(root.val + " ");
    }

    private static void preOrderTraversalUsingStack(final TreeNode root) {
        if (root == null) return;
        Stack<TreeNode> stack = new Stack<>();
        stack.push(root);
        while(!stack.isEmpty()) {
            TreeNode curr = stack.pop();
            System.out.print(curr.val + " ");
            if (curr.right != null) stack.push(curr.right);
            if (curr.left != null) stack.push(curr.left);
        }
    }

    private static void inOrderTraversalUsingStack(final TreeNode root) {
        if (root == null) return;
        Stack<TreeNode> stack = new Stack<>();
        TreeNode curr = root;
        while(!stack.isEmpty() || curr != null) {
            if (curr != null) {
                stack.push(curr);
                curr = curr.left;
            } else {
                curr = stack.pop();
                System.out.print(curr.val + " ");
                curr = curr.right;
            }
        }
    }

    public static void postOrderTraversalUsingStack(TreeNode root) {
        if (root == null) return;
        Stack<TreeNode> stack = new Stack<>();
        Stack<TreeNode> helperStack = new Stack<>();
        stack.push(root);
        while (!stack.isEmpty()) {
            TreeNode current = stack.pop();
            helperStack.push(current);
            if (current.left != null) {
                stack.push(current.left);
            }
            if (current.right != null) {
                stack.push(current.right);
            }
        }
        while (!helperStack.isEmpty()) {
            System.out.print(helperStack.pop().val + " ");
        }
    }

    private static void postOrderTraversalUsingStack2(final TreeNode root) {
        if (root == null) return;
        Stack<TreeNode> stack = new Stack<>();
        TreeNode current = root;
        TreeNode prev = null;
        do {
            while (current != null) {
                stack.push(current);
                current = current.left;
            }
            while (current == null && !stack.isEmpty()) {
                current = stack.peek();
                if (current.right == null || current.right == prev) {
                    System.out.print(current.val + " ");
                    stack.pop();
                    prev = current;
                    current = null;
                } else {
                    current = current.right;
                }
            }
        } while (!stack.isEmpty());
    }

    public static void preOrderTraversalUsingQueue(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        if (root == null) return;

        Stack<Integer> stack = new Stack<>();
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root); // Add the root to the queue

        while (!queue.isEmpty()) { // Process nodes in modified reverse order: root → left → right

            TreeNode current = queue.poll();
            stack.push(current.val);
            System.out.print(current.val + " ");

            if (current.left != null) queue.add(current.left);
            if (current.right != null) queue.add(current.right);
        }
        // System.out.println( "\n"+ stack);
    }

    public static void inOrderTraversalUsingQueue(TreeNode root) {
        if (root == null) return;
        Queue<TreeNode> queue = new LinkedList<>();
        Stack<TreeNode> stack = new Stack<>();
        TreeNode current = root;
        while (current != null || !stack.isEmpty()) {
            while (current != null) {
                stack.push(current);
                current = current.left;
            }
            current = stack.pop();
            queue.add(current);
            current = current.right;
        }
        while (!queue.isEmpty()) {
            System.out.print(queue.poll().val + " ");
        }
    }

    public static void postOrderTraversalUsingQueue(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        if (root == null) return;

        Stack<TreeNode> stack = new Stack<>();
        Queue<TreeNode> queue = new LinkedList<>();

        queue.add(root); // Add the root to the queue

        while (!queue.isEmpty()) { // Process nodes in modified reverse order: root → left → right
            TreeNode current = queue.poll();
            stack.push(current);

            // Add right child first, then left child
            if (current.right != null) queue.add(current.right);
            if (current.left != null) queue.add(current.left);
        }

        // Pop from the stack to reverse order into post-order: left → right → root
        while (!stack.isEmpty()) {
            System.out.print(stack.pop().val + " ");
        }
    }

    private static void levelOrderTraversal(final TreeNode root) {
        if (root == null) return;
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        while(!queue.isEmpty()) {
            TreeNode curr = queue.remove();
            System.out.print(curr.val + " ");
            if (curr.left != null) queue.add(curr.left);
            if (curr.right != null) queue.add(curr.right);
        }
    }

    // FOR BOTH BALANCED & UNBALANCED BINARY TREE
    // LEVEL SEPARATOR AS NULL
    private static void levelOrderTraversalPrintLevelUsingNullSeparator(final TreeNode root) {
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        queue.add(null); // null marker for end of level
        int level = 0;

        while (!queue.isEmpty()) {
            TreeNode node = queue.poll();
            if (node==null) {
                System.out.println("End of Level number: " + ++level);
                if (queue.isEmpty()) break; // end of tree
                // reached end of level, do something if needed
                queue.add(null); // add null marker for next level
            } else {
                System.out.println("Node value: " + node.val);
                if (node.left != null) queue.add(node.left);
                if (node.right != null) queue.add(node.right);
            }
        }
    }

    // FOR BOTH BALANCED & UNBALANCED BINARY TREE
    // LEVEL SEPARATOR AS DUMMY NODE WITH LEFT & RIGHT CHILDREN AS ROOT
    private static void levelOrderTraversalPrintLevelUsingDummyNodeSeparator(final TreeNode root) {
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        int level = 0;
        queue.add(new TreeNode(++level, root, root)); // separator use LEVEL as val variable or Integer.MAX_VALUE

        while (!queue.isEmpty()) {
            TreeNode node = queue.poll();
            if (node!=null && node.left==root) {
                System.out.println("End of Level number: " + node.val);
                if (queue.isEmpty()) break; // end of tree
                // reached end of level, do something if needed
                queue.add(new TreeNode(++level, root, root)); // add dummy node for next level
            } else {
                System.out.println("Node value: " + node.val);
                if (node.left != null) queue.add(node.left);
                if (node.right != null) queue.add(node.right);
            }
        }
    }

    // ONLY FOR BOTH BALANCED & UNBALANCED BINARY TREE
    private static void levelOrderTraversalPrintLevelUsingLevelSizeForLoop(final TreeNode root) {
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        int queueSize = queue.size(); // levelSize or queueSize if number of nodes at that level and level number is difference
        int level = 0;

        while (!queue.isEmpty()) {
            for (int i = 0; i < queueSize; i++) {
                TreeNode node = queue.poll();
                System.out.println("Node value: " + node.val);
                // process node as usual
                if (node.left != null) queue.add(node.left);
                if (node.right != null) queue.add(node.right);
            }
            System.out.println("End of Level number: " + ++level);
            queueSize = queue.size();
        }
    }

    // ONLY FOR BOTH BALANCED & UNBALANCED BINARY TREE
    private static void levelOrderTraversalPrintLevelUsingCountAndLevelSizeForLoop(final TreeNode root) {
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        int queueSize = queue.size();
        int level = 0;

        while (!queue.isEmpty()) {
            int count =0;
            for (int i = 0; i < queueSize; i++) {
                TreeNode node = queue.poll();
                System.out.println("Node value: " + node.val);
                // process node as usual
                if (node.left != null){
                    queue.add(node.left);
                    count++;
                }
                if (node.right != null){
                    queue.add(node.right);
                    count++;
                }
            }
            System.out.println("End of Level number: " + ++level);
            queueSize = count;
        }
    }


    private static int getHeight(final TreeNode root) {
        if(root == null)
            return 0;

        return Math.max(getHeight(root.left), getHeight(root.right)) + 1;
    }

    private static int getHeightUsingDfsStack(final TreeNode root) {
        if (root == null) return 0;
        Stack<Map.Entry<TreeNode, Integer>> stack = new Stack<>();
        stack.push(new AbstractMap.SimpleEntry<>(root, 1));
        int maxHeight = 0;
        while (!stack.isEmpty()) {
            Map.Entry<TreeNode, Integer> current = stack.pop();
            TreeNode currentNode = current.getKey();
            int currentDepth = current.getValue();
            maxHeight = Math.max(maxHeight, currentDepth);
            if (currentNode.left != null) {
                stack.push(new AbstractMap.SimpleEntry<>(currentNode.left, currentDepth + 1));
            }
            if (currentNode.right != null) {
                stack.push(new AbstractMap.SimpleEntry<>(currentNode.right, currentDepth + 1));
            }
        }
        return maxHeight;
    }

    public static int getHeightUsingDfsStack2(TreeNode root) {
        if (root == null) return 0;
        Stack<TreeNode> nodeStack = new Stack<>();
        Stack<Integer> depthStack = new Stack<>();
        nodeStack.push(root);
        depthStack.push(1);
        int maxHeight = 0;

        while (!nodeStack.isEmpty()) {
            TreeNode currentNode = nodeStack.pop();
            int currentDepth = depthStack.pop();
            maxHeight = Math.max(maxHeight, currentDepth);
            if (currentNode.left != null) {
                nodeStack.push(currentNode.left);
                depthStack.push(currentDepth + 1);
            } if (currentNode.right != null) {
                nodeStack.push(currentNode.right);
                depthStack.push(currentDepth + 1);
            }
        }
        return maxHeight;
    }

    private static int getHeightUsingBfsQueue(final TreeNode root) {
        if (root == null) return 0;
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        int height = 0;
        while(!queue.isEmpty()) {
            height++;
            int size = queue.size();
            for(int i = 0; i < size; i++) {
                TreeNode curr = queue.remove();
                if (curr.left != null) queue.add(curr.left);
                if (curr.right != null) queue.add(curr.right);
            }
        }
        return height;
    }

    private static int getDiameter(final TreeNode root) {
        if (root == null) return 0;
        return Math.max(getHeight(root.left) + getHeight(root.right), Math.max(getDiameter(root.left), getDiameter(root.right)));
    }

    private static int getDiameterUsingDfsStack(final TreeNode root) {
        if (root == null) return 0;
        Stack<TreeNode> stack = new Stack<>();
        stack.push(root);
        int diameter = 0;
        while(!stack.isEmpty()) {
            TreeNode curr = stack.pop();
            diameter = Math.max(diameter, getHeight(curr.left) + getHeight(curr.right));
            if (curr.left != null) stack.push(curr.left);
            if (curr.right != null) stack.push(curr.right);
        }
        return diameter;
    }

    public static int getDiameterUsingDfsStack2(TreeNode root){
        if (root == null) return 0;
        Stack<TreeNode> stack = new Stack<>();
        Map<TreeNode, Integer> depthMap = new HashMap<>();
        stack.push(root);
        int diameter = 0;
        while (!stack.isEmpty()) {
            TreeNode node = stack.peek();
            if (node == null) {
                stack.pop(); continue;
            }
            if (depthMap.containsKey(node)) {
                stack.pop();
                int leftDepth = depthMap.getOrDefault(node.left, 0);
                int rightDepth = depthMap.getOrDefault(node.right, 0);
                diameter = Math.max(diameter, leftDepth + rightDepth);
                depthMap.put(node, 1 + Math.max(leftDepth, rightDepth));
            } else {
                stack.push(null); // Marker for processing
                if (node.right != null)
                stack.push(node.right);
                if (node.left != null)
                stack.push(node.left);
                depthMap.put(node, 0); // Initialize with zero depth
                }
            }
            return diameter;
        }

    private static int getDiameterUsingBfsQueue(final TreeNode root) {
        if (root == null) return 0;
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        int diameter = 0;
        while(!queue.isEmpty()) {
            TreeNode curr = queue.remove();
            diameter = Math.max(diameter, getHeight(curr.left) + getHeight(curr.right));
            if (curr.left != null) queue.add(curr.left);
            if (curr.right != null) queue.add(curr.right);
        }
        return diameter;
    }

    public static int getDiameterUsingBfsQueue2(TreeNode root) {
        if (root == null) return 0;
        int diameter = 0;
        Queue<TreeNode> nodeQueue = new LinkedList<>();
        Queue<Integer> depthQueue = new LinkedList<>();
        nodeQueue.add(root); depthQueue.add(0);
        while (!nodeQueue.isEmpty()) {
            TreeNode currentNode = nodeQueue.poll();
            int currentDepth = depthQueue.poll();
            int leftDepth = getDepth(currentNode.left);
            int rightDepth = getDepth(currentNode.right);
            diameter = Math.max(diameter, leftDepth + rightDepth);
            if (currentNode.left != null) {
                nodeQueue.add(currentNode.left);
                depthQueue.add(currentDepth + 1);
            }
            if (currentNode.right != null) {
                nodeQueue.add(currentNode.right);
                depthQueue.add(currentDepth + 1);
            }
        }
        return diameter;
    }
    private static int getDepth(TreeNode node) {
        if (node == null) return 0;
        return 1 + Math.max(getDepth(node.left), getDepth(node.right));
    }

    private static int getSize(final TreeNode root) {
        if (root == null) return 0;
        return 1 + getSize(root.left) + getSize(root.right);
    }

    private static TreeNode cloneTree(final TreeNode root) {
        if (root == null) return null;
        TreeNode clone = new TreeNode(root.val);
        clone.left = cloneTree(root.left);
        clone.right = cloneTree(root.right);
        return clone;
    }

    private static boolean isSameTree(final TreeNode p, final TreeNode q) {
        if (p == null && q == null) return true;
        if (p == null || q == null) return false;
        return p.val == q.val && isSameTree(p.left, q.left) && isSameTree(p.right, q.right);
    }

    private static boolean isSymmetricUsingDfs(final TreeNode root) {
        if (root == null) return true;
        return isSymmetricUsingDfs(root.left, root.right);
    }
    private static boolean isSymmetricUsingDfs(final TreeNode left, final TreeNode right) {
        if (left == null && right == null) return true;
        else if (left == null || right == null) return false;
        else if (left.val != right.val) return false; // for root children
        else return isSymmetricUsingDfs(left.left, right.right) && isSymmetricUsingDfs(left.right, right.left);
    }

    private static boolean isSymmetricUsingBfs(final TreeNode root) {
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

    private static boolean isComplete(final TreeNode root) {
        if (root == null) return true;
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        while(!queue.isEmpty()) {
            TreeNode curr = queue.remove();
            if (curr.left == null && curr.right != null) return false;
            if (curr.left != null) queue.add(curr.left);
            if (curr.right != null) queue.add(curr.right);
        }
        return true;
    }

    private static boolean isBalanced(final TreeNode root) {
        if (root == null) return true;
        return isBalanced(root.left) && isBalanced(root.right) && Math.abs(getHeight(root.left) - getHeight(root.right)) <= 1;
    }

    private static boolean isFull(final TreeNode root) {
        if (root == null) return true;
        if (root.left == null && root.right == null) return true;
        if (root.left == null || root.right == null) return false;
        return isFull(root.left) && isFull(root.right);
    }

    private static boolean isSubtree(final TreeNode root, final TreeNode subRoot) {
        if (root == null) return false;
        if (root.val == subRoot.val && isSameTree(root, subRoot)) return true;
        return isSubtree(root.left, subRoot) || isSubtree(root.right, subRoot);
    }

    private static boolean isBST(final TreeNode root) {
        if (root == null) return true;
        return isBST(root.left, Integer.MIN_VALUE, root.val) && isBST(root.right, root.val, Integer.MAX_VALUE);
    }

    private static boolean isBST(final TreeNode root, final int min, final int max) {
        if (root == null) return true;
        return root.val > min && root.val < max && isBST(root.left, min, root.val) && isBST(root.right, root.val, max);
    }

    private static boolean isUniValTree(final TreeNode root) {
        if (root == null) return true;
        return root.val == root.left.val && root.val == root.right.val && isUniValTree(root.left) && isUniValTree(root.right);
    }




    private static int countLeaves(final TreeNode root) {
        if (root == null) return 0;
        if (root.left == null && root.right == null) return 1;
        return countLeaves(root.left) + countLeaves(root.right);
    }

    private static int countFullNodes(final TreeNode root) {
        if (root == null) return 0;
        if (root.left == null && root.right == null) return 1;
        if (root.left == null || root.right == null) return 0;
        return countFullNodes(root.left) + countFullNodes(root.right);
    }

    private static boolean isPerfect(final TreeNode root) {
        if (root == null) return true;
        int height = getHeight(root);
        int nodes = countNodes(root);
        return Math.pow(2, height) - 1 == nodes && isFull(root);
    }
    private static int countNodes(final TreeNode root) {
        if (root == null) return 0;
        return 1 + countNodes(root.left) + countNodes(root.right);
    }

    private static boolean isCousins(final TreeNode root, final int x, final int y) {
        if (root == null) return false;
        TreeNode parentX = lowestCommonAncestor(root, new TreeNode(x), new TreeNode(y));
        return parentX != null && parentX.left != null && parentX.right != null && parentX.left.val != x && parentX.right.val != y;
    }

    private static TreeNode lowestCommonAncestor(final TreeNode root, final TreeNode p, final TreeNode q) {
        if (root == null) return null;
        if (root.val == p.val || root.val == q.val) return root;
        TreeNode left = lowestCommonAncestor(root.left, p, q);
        TreeNode right = lowestCommonAncestor(root.right, p, q);
        if (left != null && right != null) return root;
        return left != null ? left : right;
    }

    private static int minDepth(final TreeNode root) {
        if (root == null) return 0;
        if (root.left == null && root.right == null) return 1;
        if (root.left == null) return minDepth(root.right) + 1;
        if (root.right == null) return minDepth(root.left) + 1;
        return Math.min(minDepth(root.left), minDepth(root.right)) + 1;
    }
}
