package Algorithms.BinaryTrees;

import java.util.Queue;

/**
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 30 April 2025
 *
 * when root = [50,30,60,20,40,null,70,null,null,35,45], key = 30
 *
 *                                       50
 *                                      / \
 *                                    (30) 60
 *                                    / \   \
 *                                   20  40  70
 *                                      /  \
 *                                      35  45
 *
 * If you delete (30) in BST, the new BST can be formed by by two ways
 *
 *                         50                             50
 *                        / \                            /  \
 *                       40  60                         35  60
 *                      / \   \          OR            / \    \
 *                     35  45  70                    20  40   70
 *                    /                                   \
 *                   20                                   45
 *
 * In Formation 1
 * 1) A
 * hl;ttach delNode.left to delNode.right left-most or smallest number
 */
public class DeleteNodeInBST {
    static class TreeNode{int val; TreeNode left, right; TreeNode(int x){val = x;} TreeNode(int x, TreeNode left, TreeNode right){val = x; this.left = left; this.right = right;}}
    public static void main(String[] args) {
        TreeNode root = prepareTree(); int key = 30;
        // Input: root = [50,30,60,20,40,null,70,null,null,35,45], key = 30
        System.out.println("Before Deletion: ");
        printTree(root);

        deleteNodeMyApproach(root, key);
        System.out.println("\nAfter Deletion: ");
        printTree(root);
        // Output: [50,40,60,35,45,null,70,20] --- formation 1
    }


    /**
     * FORMATION 1:
     *
     *          50                           50
     *         / \                          / \
     *       (30) 60                       40  60
     *       / \   \           TO         / \   \
     *      20  40  70                   35  45  70
     *         /  \                     /
     *         35  45                  20

        APPROACH:
        --------
        Find parNode instead of delNode, using key:
            1) Trav until node.left.val or node.right.val == key
            2) Now this node is parNode and it is the parent of delNode

        Save delNode left-sub-tree
            3) To delete the delNode, save temp = delNode.left; and delNode.left==null;

        If delNode.right!=null
            4) Trav till delNode.right's left-most node and left-most.left = temp; --- smallest of delNode.right is always bigger than delNode.left.val

        If delNode.right==null
            5) delNode.right = delNode.left

        Skip delNode by connecting parNode to delNode.right
            6) Node parNode.left or parNode.right = delNode.right


        when root = [50,30,60,20,40,null,70,null,null,35,45], key = 30

                                              50
                                             / \
                                           (30) 60
                                           / \   \
                                          20  40  70
                                             /  \
                                             35  45


        Connect delNode.left node to delNode.right left-most or smallest number --> 20 to 35

                                              50
                                             / \
                                            30 60
                                           / \   \
                                        [20]  40  70
                                          ↓  /  \
                                          ↓ [35] 45
                                          ↓__↑

        Finally, delete the delNode 30 and connect parNode 50 with resultant delNode.right

                                             50
                                            / \
                                           40  60
                                          / \   \
                                         35  45  70
                                        /
                                       20
     */
    public static TreeNode deleteNodeMyApproach(TreeNode root, int key) {
        if(root == null ) return null;
        TreeNode dummy = new TreeNode(Integer.MAX_VALUE, root, null); // val > key
        TreeNode parNode = dummy;
        // if(parNode.left.val == key) deleteNode(parNode, parNode.left, true); else parNode = parNode.left; // -- optional

        while(parNode!=null) {
            TreeNode delNode = (parNode.val < key) ? parNode.right : parNode.left;
            if(delNode != null && delNode.val == key) {
                deleteNode(parNode, delNode, parNode.left==delNode);
                break;
            }
            parNode = delNode; // key not found, so continue trav
        }
        return dummy.left;
    }
    private static void deleteNode(TreeNode parNode, TreeNode delNode, boolean isLeft) {
        TreeNode temp = delNode.left;
        delNode.left = null;
        // trav to left most node in delNode.right
        TreeNode trav = delNode.right;
        while(trav!=null && trav.left != null) {
            trav = trav.left;
        }
        if(trav !=null) trav.left = temp; // delNode.right != null
        else delNode.right = temp; // delNode.right == null

        if(isLeft) parNode.left = delNode.right; else parNode.right = delNode.right;
        delNode.right = null; // -- optional
    }






/**
 * FORMATION 2:
 *
 *              50                         50
 *             / \                        /  \
 *           (30) 60                     35  60
 *           / \   \          TO        / \    \
 *          20  40  70                20  40   70
 *             /  \                        \
 *             35  45                      45
 *
 *
 * delNode = 4
 *                          7
 *                       /     \
 *                     (4)      10
 *                    /  \     /  \
 *                   2    6   8    12
 *                  / \   /    \   / \
 *                 1   3 5      9 11 13
 *
 * Here, delNode.left's right-most node (3) is always smaller than
 * delNode.right's 1st node (6)
 *
  *                         7
 *                       /     \
 *                      4       10
 *                    /      /  \
 *                   2      8    12
 *                  / \      \   / \
 *                 1   3      9 11 13
 *                      \
 *                       6
 *                       /
 *                      5
 *
 *
 *                        7
 *                    /        \
 *                   2         10
 *                  / \      /  \
 *                 1   3    8    12
 *                      \    \   / \
 *                      6     9 11 13
 *                      /
 *                      5
 *
 */
    public static TreeNode deleteNode(TreeNode root, int key) {
        if (root == null) return null;
        if (key < root.val) {
            root.left = deleteNode(root.left, key);
        } else if (key > root.val) {
            root.right = deleteNode(root.right, key);
        } else {
            // Node with only one child or no child
            if (root.left == null) return root.right;
            else if (root.right == null) return root.left;

            // Node with two children: Get the in-order successor (smallest in the right subtree)
            TreeNode minNode = findMin(root.right);
            root.val = minNode.val; // Copy the in-order successor's value to this node
            root.right = deleteNode(root.right, minNode.val); // Delete the in-order successor
        }
        return root;
    }
    private static TreeNode findMin(TreeNode root) {
        while(root.left != null) root = root.left;
        return root;
    }












    private static TreeNode prepareTree() {
        /**
         *     [50,30,60,20,40,null,70,null,null,35,45]
         *
         *                           50
         *                          / \
         *                         30  60
         *                        / \   \
         *                       20  40  70
         *                          / \
         *                         35  45
        */
        TreeNode fifty = new TreeNode(50);
        TreeNode thirty = new TreeNode(30);
        TreeNode sixty = new TreeNode(60);
        TreeNode twenty = new TreeNode(20);
        TreeNode forty = new TreeNode(40);
        TreeNode seventy = new TreeNode(70);
        TreeNode thirtyFive = new TreeNode(35);
        TreeNode fortyFive = new TreeNode(45);
        fifty.left = thirty;
        fifty.right = sixty;
        thirty.left = twenty;
        thirty.right = forty;
        sixty.right = seventy;
        forty.left = thirtyFive;
        forty.right = fortyFive;
        return fifty;
    }
    private static void printTree(TreeNode root) {
        Queue<TreeNode> queue = new java.util.LinkedList<>();
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
