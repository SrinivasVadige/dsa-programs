package Algorithms.BinaryTrees;

import java.util.*;

/**
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 27 Jan 2025
 * @link 105. Construct Binary Tree from Preorder and Inorder Traversal <a href="https://leetcode.com/problems/construct-binary-tree-from-preorder-and-inorder-traversal/">LeetCode Link</a>
 * @topics Array, Hash Table, Divide and Conquer, Tree, Binary Tree
 * @companies Google(2), Microsoft(2), Amazon(2), Meta(3), TikTok(3), Bloomberg(13), Adobe(8), Apple(2), Uber(2), Snowflake(2), Salesforce(2), Pure(2)
 */
public class ConstructBinaryTreeFromPreorderAndInorderTraversal {
    public static class TreeNode {int val; TreeNode left, right; TreeNode() {}TreeNode(int val) { this.val = val; } TreeNode(int val, TreeNode left, TreeNode right) {this.val = val;this.left = left;this.right = right;}}
    public static void main(String[] args) {
        int[] preOrder = {3, 9, 20, 15, 7};
        int[] inOrder = {9, 3, 15, 20, 7};

        printTree(buildTree(preOrder, inOrder));
        printTree(buildTreeUsingMyDistanceApproach(preOrder, inOrder));
    }


        /**
         * @see #buildTreeUsingMyDistanceApproach
         * @see Algorithms.BinaryTrees.ConstructBinaryTreeFromInorderAndPostorderTraversal#buildTree

                     0 1 2  3  4
        inorder =   [9,3,15,20,7]     l  val  r
        postorder = [9,15,7,20,3]     l   r  val
        preorder =  [3,9,20,15,7]     val  l   r

                        3
                       /  \
                      9    20
                          /  \
                         15   7


                            {node, leftDistance, rightDistance}
                                        {3, 1, 3}
                       _____________________|_____________________
                       |                                         |
                 {9, 0, 0}                                  {20, 1, 1}
                                                 ________________|________________
                                                 |                               |
                                            {15, 0, 0}                       {7, 0, 0}
     */
    static int preorderIndex;
    static Map<Integer, Integer> inorderIndexMap;
    public static TreeNode buildTree(int[] preorder, int[] inorder) {
        preorderIndex = 0;
        inorderIndexMap = new HashMap<>();
        for (int i = 0; i < inorder.length; i++) inorderIndexMap.put(inorder[i], i);
        return arrayToTree(preorder, 0, preorder.length - 1);
    }

    private static TreeNode arrayToTree(int[] preorder, int left, int right) {
        if (left > right) return null;

        int rootValue = preorder[preorderIndex++];
        TreeNode root = new TreeNode(rootValue);

        root.left = arrayToTree(preorder, left, inorderIndexMap.get(rootValue) - 1);
        root.right = arrayToTree(preorder, inorderIndexMap.get(rootValue) + 1, right);
        return root;
    }






    /**


        preOrder = [1,2,4,6,5,3,11,7,8,9,10] = val  l   r ---> root.next = left or right
        inOrder  = [4,2,5,6,1,11,3,8,9,7,10] =  l  val  r
                    0 1 2 3 4 5  6 7 8 9 10

        result = [1,2,3,4,6,11,7,null,null,5,null,null,null,8,10,null,null,null,9] = Level-order traversal

                          1
                        /    \
                       2      3
                      / \    / \
                     4   6  11  7
                        /      / \
                       5      8   10
                               \
                                9

                      i
        preOrder = [3,9,10,20,15,11,7] = val  l   r
        inOrder  = [10,9,3,11,15,20,7] =  l  val  r

        result = [3,9,20,10,null,15,7,null,null,11] = Level-order traversal

                         3
                        / \
                       9   20
                      /   /  \
                    10   15   7
                        /
                       11
     */

    public static TreeNode buildTreeUsingMyDistanceApproach(int[] preOrder, int[] inOrder) {
        int n = inOrder.length;
        Map<Integer, Integer> inOrderNumToI = new HashMap<>();
        for (int i=0; i<n; i++) inOrderNumToI.put(inOrder[i], i);
        TreeNode root = new TreeNode(preOrder[0]);
        dfs(preOrder, 1, n, root, inOrderNumToI.get(root.val), n-1, inOrderNumToI); // -1 to exclude current node count in parentDistanceRemaining

        return root;
    }
    // childI (pre-order), currNode, parentI (in-order)
    private static void dfs(int[] preOrder, int childI, int n, TreeNode node, int parentI, int parentDistanceRemaining, Map<Integer, Integer> inOrderNumToI) {
        if (childI >= n) return; // or return childI; and childI = dfs(preOrder, childI+1, n, left ...);

        int currI = inOrderNumToI.get(node.val);
        int leftDistance = 0;
        int rightDistance = 0;

        if (currI < parentI) { // leftNode
            rightDistance = parentI-currI-1;
            leftDistance = parentDistanceRemaining - rightDistance;
        } else if (currI > parentI) { // rightNode
            leftDistance = currI-parentI-1;
            rightDistance = parentDistanceRemaining - leftDistance;
        } else { // root ---> currI == parentI
            leftDistance = inOrderNumToI.get(node.val);
            rightDistance = parentDistanceRemaining - leftDistance;
        }

        if (leftDistance > 0) {
            TreeNode left = new TreeNode(preOrder[childI]); // numToNode.get(preOrder[childI]);
            node.left = left;
            dfs(preOrder, childI+1, n, left, inOrderNumToI.get(node.val), leftDistance-1, inOrderNumToI);
        }
        if (rightDistance > 0) {
            childI += leftDistance;
            TreeNode right = new TreeNode(preOrder[childI]); // numToNode.get(preOrder[childI]);
            node.right = right;
            dfs(preOrder, childI+1, n, right, inOrderNumToI.get(node.val), rightDistance-1, inOrderNumToI);
        }
    }









    public static TreeNode buildTree3(int[] preorder, int[] inorder) {
        return helper(preorder, inorder, 0, preorder.length-1, 0, inorder.length-1);
    }

    public static TreeNode helper(int[] preOrder, int[] inOrder, int preOrderStart, int preOrderEnd, int inOrderStart, int inOrderEnd) {
        if (preOrderStart > preOrderEnd || inOrderStart > inOrderEnd) return null;

        int rootVal = preOrder[preOrderStart];
        int rootIndexInInOrder = -1; // or inOrderNumToI.get(rootVal);
        for (int i = inOrderStart; i <= inOrderEnd; i++) {
            if (inOrder[i] == rootVal) rootIndexInInOrder = i;
        }

        TreeNode root = new TreeNode(rootVal);
        int leftDistance = rootIndexInInOrder - inOrderStart;

        root.left = helper(preOrder, inOrder, preOrderStart+1, preOrderStart+leftDistance, inOrderStart, rootIndexInInOrder-1);
        root.right = helper(preOrder, inOrder, preOrderStart+leftDistance+1, preOrderEnd, rootIndexInInOrder+1, inOrderEnd);

        return root;
    }









    public static TreeNode buildTree4(int[] preorder, int[] inorder) {
        if (preorder.length == 0) return null;

        Map<Integer, Integer> childI = new HashMap<>();
        for (int i = 0; i < inorder.length; i++)
            childI.put(inorder[i], i);

        TreeNode root = new TreeNode(preorder[0]);
        Stack<TreeNode> stack = new Stack<>();
        stack.push(root);

        for (int i = 1; i < preorder.length; i++) {
            int currVal = preorder[i];
            TreeNode curr = new TreeNode(currVal);

            // Compare inorder indexes
            if (childI.get(currVal) < childI.get(stack.peek().val)) {
                // LEFT child
                stack.peek().left = curr;
            } else {
                // Find correct parent for RIGHT child
                TreeNode parent = null;
                while (!stack.isEmpty() && childI.get(currVal) > childI.get(stack.peek().val)) {
                    parent = stack.pop();
                }
                parent.right = curr;
            }

            stack.push(curr);
        }

        return root;
    }






    public static TreeNode buildTree5(int[] preorder, int[] inorder) {
        if (preorder.length == 0) return null;

        TreeNode root = new TreeNode(preorder[0]);
        if (preorder.length == 1) return root;

        int rootNodeIndexInorder = -1;
        for (int i = 0; i < inorder.length; i++)
            if (inorder[i] == root.val) rootNodeIndexInorder = i;

        int leftDistance = rootNodeIndexInorder+1;

        root.left = buildTree5(Arrays.copyOfRange(preorder, 1, leftDistance), Arrays.copyOfRange(inorder, 0, rootNodeIndexInorder));

        root.right = buildTree5(Arrays.copyOfRange(preorder, leftDistance, preorder.length), Arrays.copyOfRange(inorder, leftDistance, inorder.length));

        return root;

    }














    /**
        It's failing for right skewed TreeNode ---> left works fine but we can't check whether the parent is leaf node or child is right-side

        preOrder = [1,2,3,4] = val  l   r
        inOrder  = [1,2,3,4] =  l  val  r

        1
         \
          2
           \
            3
             \
              4

        In this below logic, the left-side checking works fine but right-side checking is wrong



        Here check the relation between preOrder's curr and curr's parent
            - left
            - or right
            - or no relation --> if no relation, then move to the next parent

                                    i
        preOrder = [3,9,10,20,15,11,7] = val  l   r
        inOrder  = [10,9,3,11,15,20,7] =  l  val  r
                                  j
        result = [3,9,20,10,null,15,7,null,null,11] = Level-order traversal

                         3
                        / \
                       9   20
                      /   /  \
                    10   15   7
                        /
                       11



        stack = []

                                          i
        preOrder = [1,2,4,6,5,3,11,7,8,9,10] = val  l   r ---> root.next = left
        inOrder  = [4,2,5,6,1,11,3,8,9,7,10] =  l  val  r
                            j
        result = [1,2,3,4,6,11,7,null,null,5,null,null,null,8,10,null,null,null,9] = Level-order traversal

                          1
                        /    \
                       2      3
                      / \    / \
                     4   6  11  7
                        /      / \
                       5      8   10
                               \
                                9





        preorder = [1,2,3]
        inorder =  [2,3,1]

        result = [1,2,null,null,3]

                     1
                    /
                    2
                    \
                     3

     */
    public TreeNode buildTreeNotWorking(int[] preOrder, int[] inOrder) {
        int n = preOrder.length;
        if (n==1) return new TreeNode(preOrder[0]);

        Map<Integer, Integer> inOrderNumToIndex = new HashMap<>();
        Map<Integer, TreeNode> numToNode = new HashMap<>();
        Map<Integer, Set<Integer>> inOrderLefties = new HashMap<>();
        Set<Integer> set = new HashSet<>();

        for(int i=0; i<n; i++) {
            int num = inOrder[i];
            numToNode.put(num, new TreeNode(num) {
                @Override
                public String toString() {
                    return String.format("val: %s, left: %s, right : %s", val, Optional.ofNullable(left).map(n->n.val+"").orElse("null"), Optional.ofNullable(right).map(n->n.val+"").orElse("null"));
                }
            });
            inOrderNumToIndex.put(num, i);
            set.add(num);
            inOrderLefties.put(num, new HashSet<>(set));
        }

        Map<Integer, TreeNode> parents = new HashMap<>();
        Stack<Integer> stack = new Stack<>();

        TreeNode root = numToNode.get(preOrder[0]);
        stack.push(preOrder[0]);
        parents.put(preOrder[0], root);

        System.out.println(parents);

        for(int i=1; i<n; i++) {

            int currNum = preOrder[i];
            TreeNode currNode = numToNode.get(currNum);

            while(!stack.isEmpty()) {
                System.out.println(parents);
                TreeNode parent = numToNode.get(stack.peek());
                System.out.println("stack  ---> " + stack);
                System.out.println("parent ---> " + parent);
                System.out.println(currNum);

                // is parent's left?
                if (inOrderLefties.get(parent.val).contains(currNum)) {
                    System.out.println("left");
                    parent.left = currNode;
                    parents.put(currNum, parent);
                    break;
                } else if (parent==root || isRightSide(inOrder, inOrderNumToIndex.get(parents.get(parent.val).val), inOrderNumToIndex.get(parent.val), currNum) ) {
                    System.out.println("right");
                    parent.right = currNode;
                    parents.put(currNum, parent);
                    stack.pop();
                    break;
                } else {
                    System.out.println("not found");
                    stack.pop();
                    if (i==n-1 && stack.isEmpty()) {
                        parent.right = currNode;
                        break;
                    }
                }
            }
            stack.push(currNum);
        }

        return root;
    }

    private boolean isRightSide(int[] inOrder, int i, int j, int val) {
        int start = Math.min(i, j) + 1;
        int end = Math.max(i, j);
        for (; start<end; start++) {
            if(inOrder[start] == val) return true;
        }
        return false;
    }








    private static void printTree(TreeNode root) {
        System.out.print("\nTree: ");
        java.util.Queue<TreeNode> queue = new java.util.LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                TreeNode node = queue.poll();
                System.out.print(node == null ? "null " : node.val + " ");
                if (node == null) continue;
                queue.add(node.left);
                queue.add(node.right);
            }
        }
    }
}
