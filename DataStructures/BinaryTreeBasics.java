package DataStructures;

import java.util.*;

/**
<pre>
    1. build tree using array
    2. print tree in the form of array
    3. invert tree (update, swap left and right nodes)
    4. traverse using recursion
    5. DFS traversals (pre-order, in-order, post-order dfs() traversals & stack traversals) ---> O(h) Space Complexity, where h is height of tree - for recursion stack space
    6. BFS traversals (level order traversal using queue & stack) ---> O(w) Space Complexity, where w is width of tree
    7. Morris Traversals (pre-order, in-order, post-order O(1) dfs iteration traversals) ---> O(1) Space Complexity
    8. height of tree
    9. diameter of tree
    10. size of tree
    11. clone tree via pre-order traversal
    12. check if two trees are identical or not
    13. check if two trees are symmetric/mirror or not
    14. check if tree height is balanced or not
    15. check if tree is complete
    16. check if tree is full
    17. check if tree is subtree of another tree
    18. check if tree is binary search tree BST
    19. check if tree is uni-val tree

</pre>
 *
 * A tree is a undirected graph which satisfies the following properties:
 * - Only one Root Node (Root Node has no parent or parent node is itself)
 * - An Acyclic connect graph
 * - N nodes & N-1 edges
 * - Two vertices are connected by exactly one edge i.e exactly one path
 * Example: File System Tree or directories --> /, /usr, /temp, /usr/local, /usr/local/bin

 * - root node level is always 0 (HORIZONTAL LINE)
 * - root node height if it does not have any child node then 0, otherwise 1 + max(height(left), height(right))
 * - calculate height from bottom
 * - depth and level are same. depth is opposite of height
 * - level, height and depth are edges count not nodes

 * PROPERTIES OF BINARY TREE
 * - Maximum number of nodes in the level l is 2^l => level 0 = 2^0, level 1 = 2^1, level 2 = 2^2, level 3 = 2^3.....
 * - Total maximum number(from root to current height) of nodes at given height is "2^(h+1) - 1" or "2^(l+1) - 1". Because it's the summation of all max nodes in each level
 * => 2^0 + 2^1 + 2^2 + ... + 2^(h)
 * =>  1  +  2  +  4  + ... + 2^h
 * 1  +  2  +  4  + ... + 2^n is a geometric progression with formula a(r^(n+1)-1)/(r-1) where a = 1, r = 2
 * => 1(2^(h+1)-1)/(2-1)
 * => ( 2^(h+1)-1 )/(2-1)
 * => (2^(h+1)-1 )/1 => 2^(h+1)-1
 * - Maximum number of nodes at height h is 2^(h+1)-1 🔥
 * - Minimum number of nodes at height h is h+1.
 * - Minimum Height h at given nodes n is log2(n+1)-1. Because n=2^(h+1)-1 => n+1 = 2^(h+1) => log2(n+1) = h+1 => h = log2(n+1)-1

 * THEORY:
 * - GFG Introduction to Binary Tree: <a href="https://www.geeksforgeeks.org/introduction-to-binary-tree/">Link</a> (read all next articles)
 *
 *
* @author Srinivas Vadige, srinivas.vadige@gmail.com
* @since 23 Sept 2024
*/
@SuppressWarnings("unused")
public class BinaryTreeBasics {

    public static class TreeNode {
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

            DFS TRAVERSAL is of 3 types:
            1. PRE-ORDER TRAVERSAL
            2. IN-ORDER TRAVERSAL
            3. POST-ORDER TRAVERSAL

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
        System.out.println("\nPRE-ORDER TRAVERSAL USING STACK LEFT SCAN APPROACH"); // 🔥
        preOrderTraversalUsingStackLeftScan(root);
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




        // 6. BFS TRAVERSAL
        System.out.println("\n\n6 BFS TRAVERSAL");
        System.out.println("LEVEL ORDER TRAVERSAL");
        levelOrderTraversal(root);
        System.out.println("\n6.1 BFS LEVEL ORDER TRAVERSAL PRINT EACH LEVEL USING NULL SEPARATOR");
        levelOrderTraversalPrintLevelUsingNullSeparator(root);
        System.out.println("\n6.2 BFS LEVEL ORDER TRAVERSAL PRINT EACH LEVEL USING DUMMY NODE SEPARATOR");
        levelOrderTraversalPrintLevelUsingDummyNodeSeparator(root);
        System.out.println("\n6.3 BFS LEVEL ORDER TRAVERSAL PRINT EACH LEVEL USING LEVEL/QUEUE SIZE FOR LOOP"); // levelSize if number of nodes at that level and level number is difference
        levelOrderTraversalPrintLevelUsingLevelSizeForLoop(root);
        System.out.println("\n6.4 BFS LEVEL ORDER TRAVERSAL PRINT EACH LEVEL USING QUEUE NODES COUNT FOR LOOP");
        levelOrderTraversalPrintLevelUsingCountAndLevelSizeForLoop(root);


        // 7. MORRIS TRAVERSALS
        System.out.println("\n\n7 MORRIS TRAVERSALS");
        /*
            Morris is a type of DFS

            Same like DFS Traversals Morris Traversals are of 3 types:
            1. PRE-ORDER TRAVERSAL
            2. IN-ORDER TRAVERSAL
            3. POST-ORDER TRAVERSAL

            check in code for explanation

         */
        System.out.println("\n7.1 IN-ORDER TRAVERSAL USING MORRIS TRAVERSAL");
        morrisInOrderTraversal(root);
        System.out.println("\n7.2 PRE-ORDER TRAVERSAL USING MORRIS TRAVERSAL");
        morrisPreOrderTraversal(root);
        System.out.println("\n7.3 POST-ORDER TRAVERSAL USING MORRIS TRAVERSAL");
        morrisPostOrderTraversal(root);



        // 8. HEIGHT OF TREE
        System.out.println("\n\n 8. HEIGHT OF A BINARY TREE");
        System.out.println("\n8.1 HEIGHT / DEPTH OF TREE USING RECURSION");
        System.out.println(getHeight(root));
        System.out.println("\n8.2 HEIGHT / DEPTH OF TREE USING DFS STACK");
        System.out.println(getHeightUsingDfsStack(root));
        System.out.println("\n8.3 HEIGHT / DEPTH OF TREE USING BFS QUEUE");
        System.out.println(getHeightUsingBfsQueue(root));

        // 9. DIAMETER OF TREE
        System.out.println("\n\n9 DIAMETER OF TREE");
        System.out.println("\n9.1 DIAMETER OF TREE USING RECURSION");
        System.out.println(getDiameter(root));
        System.out.println("\n9.2 DIAMETER OF TREE USING DFS STACK");
        System.out.println(getDiameterUsingDfsStack(root));
        System.out.println("\n9.3 DIAMETER OF TREE USING BFS QUEUE");
        System.out.println(getDiameterUsingBfsQueue(root));


        // 10. SIZE OF THE TREE (TOTAL NUMBER OF NODES)
        System.out.println("\n\n10. SIZE OF THE TREE (TOTAL NUMBER OF NODES)");
        System.out.println(getSize(root));


        // 11. CLONE TREE VIA PRE-ORDER TRAVERSAL
        System.out.println("\n\n11. CLONE TREE VIA PRE-ORDER TRAVERSAL");
        cloneTree(root);


        // 12. CHECK IF TWO TREES ARE IDENTICAL
        System.out.println("\n\n12. CHECK IF TWO TREES ARE IDENTICAL");
        TreeNode root1 = buildTree(new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10}); // which is same as 1 2 3 4 5 6 7 8 9 10 null null null null null
        TreeNode root2 = buildTree(new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10}); // which is same as 1 2 3 4 5 6 7 8 9 10 null null null null null
        System.out.println(isSameTree(root1, root2));


        // 13. CHECK IF TWO TREES ARE SYMMETRIC
        System.out.println("\n\n13. SYMMETRIC TREES");
        System.out.println("\n13.1 CHECK IF TWO TREES ARE SYMMETRIC USING DFS");
        TreeNode root3 = buildTree(new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10}); // which is same as 1 2 3 4 5 6 7 8 9 10 null null null null null
        System.out.println(isSymmetricUsingDfs(root3));
        System.out.println("\n13.2 CHECK IF TWO TREES ARE SYMMETRIC USING BFS");
        System.out.println(isSymmetricUsingBfs(root3));


        // 14. CHECK IF TREE IS HEIGHT BALANCED
        System.out.println("\n\n14. CHECK IF TREE IS HEIGHT BALANCED");
        TreeNode root4 = buildTree(new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10}); // which is same as 1 2 3 4 5 6 7 8 9 10 null null null null null
        System.out.println(isBalanced(root4));


        // 15. CHECK IF TREE IS COMPLETE
        System.out.println("\n\n15. CHECK IF TREE IS COMPLETE");
        TreeNode root5 = buildTree(new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10}); // which is same as 1 2 3 4 5 6 7 8 9 10 null null null null null
        System.out.println(isComplete(root5));


        // 16. CHECK IF TREE IS FULL
        System.out.println("\n\n16. CHECK IF TREE IS FULL");
        TreeNode root6 = buildTree(new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10}); // which is same as 1 2 3 4 5 6 7 8 9 10 null null null null null
        System.out.println(isFull(root6));


        // 17. CHECK IF TREE IS SUBTREE
        System.out.println("\n\n17. CHECK IF TREE IS SUBTREE");
        TreeNode root7 = buildTree(new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10}); // which is same as 1 2 3 4 5 6 7 8 9 10 null null null null null
        TreeNode root8 = buildTree(new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10}); // which is same as 1 2 3 4 5 6 7 8 9 10 null null null null null
        System.out.println(isSubtree(root7, root8));


        // 18. CHECK IF TREE IS BINARY SEARCH TREE
        System.out.println("\n\n18. CHECK IF TREE IS BINARY SEARCH TREE");
        TreeNode root9 = buildTree(new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10}); // which is same as 1 2 3 4 5 6 7 8 9 10 null null null null null
        System.out.println(isBST(root9));


        // 19. CHECK IF TREE IS UNI-VALUE TREE
        System.out.println("\n\n19. CHECK IF TREE IS UNI-VALUE TREE");
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

    public static void preOrderTraversalUsingStackLeftScan(TreeNode root) { // 🔥
        Stack<TreeNode> stack = new Stack<>();
        TreeNode trav = root;
        while (trav != null || !stack.isEmpty()) {
            // traverse to left most
            while (trav != null) {
                System.out.print(trav.val + " ");
                stack.push(trav);
                trav = trav.left;
            }
            trav = stack.pop(); // pop top one in stack
            trav = trav.right; // => if trav.right == null then above while(trav!=null){} will skip and pop the parent node and trav.right and trav will continue
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


    /**
     <pre>


     MORRIS TRAVERSALS:
     ------------------

    Here, we have two pointers - 1.root (curr) & 2.predecessor (curr root's temp parent)
    🔥 if (root.left==null) "no need to trav left then root=root.right"; else "CHECK PREDECESSOR LINK TYPE"
    🔥 predecessor is curr root's left child or left child's rightmost leaf node
        - last node in left subtree
        - we need this predecessor node to make it as root's temp parent
    🔥 create the temp link for the first time using "predecessorNode.right = currRoot"
    🔥 LINK TYPE 1 ---> predecessor.right == null
        - not linked yet
        - left traversal is not done
        - visiting root for the first time
        - then -> link & move left
    🔥 LINK TYPE 2 ---> predecessor.right == currRoot
        - we already linked it before
        - left traversal is done
        - re-visited the same root
        - then -> unlink & move right
    🔥 if(root.left==null) "move right"
        - this main condition can be satisfied when root is either a normal current root node or previous predecessor node as current root
        - i.e., root.right can be right child node or previous iteration parent node (as per link)

    NOTE: Just remember that if root.left==null or predecessor.right==root then move root=root.right.


    STEP WISE EXPLANATION:
    ----------------------

Given:

                                     1
                                   /   \
                                  2     3
                                 / \     \
                                4   5     6
                                   /       \
                                  7         8


1st iteration:

    root: 1
    predecessor: 5
    link: 5.right = 1
    root = root.left = 2
                                    (1r)
                                   /   \
                                  2     3
                                 / \     \
                                4  (5p)   6
                                   /       \
                                  7         8

                                    (1r)
                                   / ↑ \
                                  2  |  3
                                 / \ |   \
                                4  (5p)   6
                                   /       \
                                  7         8

2nd iteration:

    root: 2
    predecessor: 4
    link: 4.right = 2
    root = root.left = 4

                                      1
                                   /  ↑  \
                                (2r)  |   3
                                /   \ |    \
                               (4p)  5      6
                                    /        \
                                   7          8
                                      1
                                   /  ↑  \
                                (2r)  |   3
                                / ↑ \ |    \
                               (4p)  5      6
                                    /        \
                                   7          8


3rd iteration:

    root: 4
    predecessor: N/A - as root.left == null
    link: N/A
    root = root.right = 2
                                      1
                                   /  ↑  \
                                  2   |   3
                                / ↑ \ |    \
                               (4r)  5      6
                                    /        \
                                   7          8


4th iteration:

    root: 2
    predecessor: 4
    link: already linked, so remove it
    root = root.left = 5


                                      1
                                   /  ↑  \
                                (2r)  |   3
                                / ↑ \ |    \
                               (4p)  5      6
                                    /        \
                                   7          8

                                     1
                                   / ↑ \
                                (2r) |  3
                                 / \ |   \
                               (4p) 5     6
                                   /       \
                                  7         8



5th iteration:

    root: 5
    predecessor: 7
    link: 7.right = 5
    root = root.left = 7
                                     1
                                   / ↑ \
                                  2  |  3
                                 / \ |   \
                                4  (5r)   6
                                   /       \
                                  (7p)      8

                                     1
                                   / ↑ \
                                  2  |  3
                                 / \ |   \
                                4  (5r)   6
                                   / ↑     \
                                  (7p)      8


6th iteration:

    root: 7
    predecessor: N/A - as root.left == null
    link: N/A
    root = root.left = 5

                                     1
                                   / ↑ \
                                  2  |  3
                                 / \ |   \
                                4   5     6
                                   / ↑     \
                                  (7r)      8


7th iteration:

    root: 5
    predecessor: 7
    link: already linked, so remove it
    root = root.right = 1

                                     1
                                   / ↑ \
                                  2  |  3
                                 / \ |   \
                                4  (5r)   6
                                   / ↑     \
                                  (7p)      8

                                     1
                                   / ↑ \
                                  2  |  3
                                 / \ |   \
                                4  (5r)   6
                                   /       \
                                 (7p)       8


8th iteration:

    root: 1
    predecessor: 5
    link: already linked, so remove it
    root = root.right = 3

                                    (1r)
                                   / ↑ \
                                  2  |  3
                                 / \ |   \
                                4  (5p)   6
                                   /       \
                                  7         8

                                    (1r)
                                   /   \
                                  2     3
                                 / \     \
                                4  (5p)   6
                                   /       \
                                  7         8

and so on... the loop is the same for 3, 6, 8 as "root.left" is always null for them
     </pre>
     */
    public static void morrisInOrderTraversal(final TreeNode root) {
        if (root == null) return;
        TreeNode curr = root;
        while (curr != null) {
            if (curr.left == null) {
                System.out.print(curr.val + " ");
                curr = curr.right;
            } else { // CHECK PREDECESSOR LINK TYPE - linked or not linked
                TreeNode predecessor = curr.left; // to make root's left last child as root's parent / predecessor
                while (predecessor.right != null && predecessor.right != curr) predecessor = predecessor.right;

                if (predecessor.right == null) { // not linked yet & left traversal is not done & visiting root for the first time
                    predecessor.right = curr; // link
                    curr = curr.left;
                } else { // predecessor.right == curr root -> we already linked it before & left traversal is done & re-visited the same root
                    System.out.print(curr.val + " ");
                    predecessor.right = null; // unlink
                    curr = curr.right;
                }
            }
        }
    }


    public static void morrisPreOrderTraversal(final TreeNode root) {
        if (root == null) return;
        TreeNode curr = root;
        while (curr != null) {
            if (curr.left == null) {
                System.out.print(curr.val + " ");
                curr = curr.right;
            } else { // CHECK PREDECESSOR LINK TYPE - linked or not linked
                TreeNode predecessor = curr.left; // to make root's left last child as root's parent / predecessor
                while (predecessor.right != null && predecessor.right != curr) predecessor = predecessor.right;

                if (predecessor.right == null) { // not linked yet & left traversal is not done & visiting root for the first time
                    System.out.print(curr.val + " ");
                    predecessor.right = curr; // link
                    curr = curr.left;
                } else { // predecessor.right == curr root -> we already linked it before & left traversal is done & re-visited the same root
                    predecessor.right = null; // unlink
                    curr = curr.right;
                }
            }
        }
    }
    // Morris PostOrder Traversal is bit hard, as you cannot print node when you visit it (postorder prints after exploring children).
    public static void morrisPostOrderTraversal(TreeNode root) {
        ArrayList<Integer> res = new ArrayList<>();
        TreeNode current = root;

        while (current != null) {
            if (current.right == null) {
                res.add(current.val);
                current = current.left;
            } else {
                TreeNode predecessor = current.right;
                while (predecessor.left != null
                       && predecessor.left != current) {
                    predecessor = predecessor.left;
                }

                // If left child doesn't point to this node, then put in res this node and make left child point to this node
                if (predecessor.left == null) {
                    res.add(current.val);
                    predecessor.left = current;
                    current = current.right;
                }
                // If the left child of inorder predecessor already points to this node
                else {
                    predecessor.left = null;
                    current = current.left;
                }
            }
        }

        Collections.reverse(res);

        System.out.println(res);
    }

    public static void morrisPostOrderTraversal2(TreeNode root) {
        if (root == null) return;

        TreeNode dummy = new TreeNode(0);
        dummy.left = root;
        TreeNode curr = dummy;

        while (curr != null) {
            if (curr.left == null) {
                curr = curr.right;
            } else {
                TreeNode predecessor = curr.left;

                // go to rightmost node of left subtree OR stop if link exists
                while (predecessor.right != null && predecessor.right != curr) {
                    predecessor = predecessor.right;
                }

                if (predecessor.right == null) {
                    // create link
                    predecessor.right = curr;
                    curr = curr.left;
                } else {
                    // link exists → remove it AND print reverse path
                    predecessor.right = null;
                    printReverse(curr.left, predecessor);
                    curr = curr.right;
                }
            }
        }
    }

    // reverse right pointers from 'from' to 'to'
    private static void reverse(TreeNode from, TreeNode to) {
        if (from == to) return;
        TreeNode prev = null;
        TreeNode curr = from;
        TreeNode next;

        while (curr != to) {
            next = curr.right;
            curr.right = prev;
            prev = curr;
            curr = next;
        }

        // reverse the last node
        curr.right = prev;
    }

    // print reversed path and restore it
    private static void printReverse(TreeNode from, TreeNode to) {
        // reverse from → to
        reverse(from, to);

        // print from to to
        TreeNode node = to;
        while (true) {
            System.out.print(node.val + " ");
            if (node == from) break;
            node = node.right;
        }

        // restore original order
        reverse(to, from);
    }















    /**
     * same as maxDepth
     * @see {@link Algorithms.BinaryTrees.MaximumDepthOfBinaryTree}
     */
    private static int getHeight(final TreeNode root) {
        if(root == null) return 0;
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
                if (node.right != null) stack.push(node.right);
                if (node.left != null) stack.push(node.left);
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
