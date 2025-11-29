package Algorithms.BinaryTrees;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 28th Nov 2025
 * @link 117. Populating Next Right Pointers in Each Node II <a href="https://leetcode.com/problems/populating-next-right-pointers-in-each-node-ii/">LeetCode Link</a>
 * @topics LinkedList, Tree, Binary Tree, DFS, BFS
 * @companies Bloomberg(4), Meta(2), Microsoft(2), Amazon(10), Snowflake(6), Google(5)
 */
public class PopulatingNextRightPointersInEachNode2 {
    public static class Node {int val; Node left; Node right; Node next; public Node() {} public Node(int val) {this.val = val;}}

    public static void main(String[] args) {
        Node root = new Node(1);
        root.left = new Node(2);
        root.right = new Node(3);
        root.left.left = new Node(4);
        root.left.right = new Node(5);
        root.right.left = new Node(6);
        root.right.right = new Node(7);

        System.out.println("connect using Head node: " + printNode(connect(root)));
        System.out.println("connect using Queue: " + printNode(connectUsingQueue(root)));
    }


    /**
     * @TimeComplexity O(n)
     * @SpaceComplexity O(1)

        After you connect nodes on a level using next pointers, that level becomes a "Linked list"

     */
    public static Node connect(Node root) {
        Node head = root;

        while(head != null) { // --- each level ---

            Node dummyChildLinkedList = new Node();
            Node prevChild = dummyChildLinkedList;

            while (head != null) { // --- curr level ---
                if (head.left != null) {
                    prevChild.next = head.left; // currHead's child
                    prevChild = prevChild.next;
                }
                if (head.right != null) {
                    prevChild.next = head.right; // currHead's child
                    prevChild = prevChild.next;
                }
                head = head.next; // --- curr level head move ---
            }

            // --- next level ---
            head = dummyChildLinkedList.next; // dummy.next always points to the first child of the next level
        }

        return root;
    }









    static Node prev, leftmost;

    /**
     * @TimeComplexity O(n)
     * @SpaceComplexity O(1)
     */
    public static Node connect2(Node root) {
        if (root == null) return null;
        leftmost = root;
        Node curr;

        while (leftmost != null) {
            prev = null;
            curr = leftmost;
            leftmost = null;

            while (curr != null) {
                processChild(curr.left);
                processChild(curr.right);

                curr = curr.next; // Move onto the next node.
            }
        }

        return root;
    }
    private static void processChild(Node childNode) {
        if (childNode != null) {
            if (prev != null) {
                prev.next = childNode;
            } else {
                leftmost = childNode;
            }

            prev = childNode;
        }
    }






    /**
     * @TimeComplexity O(n)
     * @SpaceComplexity O(1)
     */
    public Node connect3(Node root) {
        if (root == null) return null;

        Node level = root;

        while (level != null) {
            Node curr = level;
            Node nextLevelHead = null;     // left-most node of next level
            Node nextLevelPrev = null;     // tail for next level

            while (curr != null) {

                if (curr.left != null) { // process left child
                    if (nextLevelHead == null) nextLevelHead = curr.left;
                    if (nextLevelPrev != null) nextLevelPrev.next = curr.left;
                    nextLevelPrev = curr.left;
                }

                if (curr.right != null) { // process right child
                    if (nextLevelHead == null) nextLevelHead = curr.right;
                    if (nextLevelPrev != null) nextLevelPrev.next = curr.right;
                    nextLevelPrev = curr.right;
                }

                curr = curr.next; // move across the current level
            }

            level = nextLevelHead; // move to the next level
        }

        return root;
    }






    /**
     * @TimeComplexity O(n)
     * @SpaceComplexity O(1)
     */
    public static Node connectUsingHeadNodeMyApproach(Node root) {
        Node dummy = new Node(-1);
        dummy.left = root;
        Node headStart = dummy; // Head level start

        while(headStart != null) {

            Node head = headStart;
            Node curr = head.left != null ? head.left : head.right; // Head level start's child

            while (curr != null) {
                Node next = null;

                if (head.left == curr && head.right != null) next = head.right;
                else {
                    do head = head.next; // update head here itself
                    while (head != null && head.left == null && head.right == null);
                    if (head != null) next = head.left != null ? head.left : head.right;
                }

                curr.next = next;
                curr = curr.next; // update curr
            }

            Node leftMostChild = null;
            for (Node trav = headStart; trav != null && leftMostChild==null; trav = trav.next) {
                if (trav.left != null && (trav.left.left != null || trav.left.right != null)) {
                    leftMostChild = trav.left;
                } else if (trav.right != null && (trav.right.left != null || trav.right.right != null)) {
                    leftMostChild = trav.right;
                }
            }
            headStart = leftMostChild;
        }
        return dummy.left;
    }








    /**
     * @TimeComplexity O(n)
     * @SpaceComplexity O(n)
     */
    public static Node connectUsingQueue(Node root) {
        if (root == null) return null;

        Queue<Node> q = new LinkedList<>();
        q.add(root);

        while (!q.isEmpty()) {
            int size = q.size();
            Node dummyPrev = new Node();
            while (size-- > 0) {
                Node node = q.poll();
                dummyPrev.next = node;
                dummyPrev = node; // or dummyPrev = dummyPrev.next;

                if (node.left != null) q.add(node.left);
                if (node.right != null) q.add(node.right);
            }
        }
        return root;
    }




    /**
     * @TimeComplexity O(n)
     * @SpaceComplexity O(n)
     */
    public Node connectUsingQueue2(Node root) {
        if (root == null) return null;

        Queue<Node> q = new LinkedList<>();
        q.add(root);

        while(!q.isEmpty()) {
            int n = q.size();
            Node prev = null;

            for(int i=0; i<n; i++) {
                Node curr = q.poll();
                if (prev != null) prev.next = curr;
                prev = curr;

                if(curr.left != null) q.add(curr.left);
                if(curr.right != null) q.add(curr.right);
            }
        }

        return root;
    }







    /**
     * @TimeComplexity O(n)
     * @SpaceComplexity O(n)
     */
    public Node connectUsingQueue3(Node root) {
        if (root == null) return root;

        Queue<Node> q = new LinkedList<Node>();
        q.add(root);

        while (!q.isEmpty()) {
            int size = q.size();

            for (int i = 0; i < size; i++) {
                Node node = q.poll();
                if (i < size-1) node.next = q.peek();
                if (node.left != null) q.add(node.left);
                if (node.right != null) q.add(node.right);
            }
        }
        return root;
    }










    /**

    Using this dfs approach, we can't connect 7-8

                     1
                  /     \
                2         3
              /   \        \
             4     5        6
            /                 \
           7                   8

           and it makes too much complexity if we do dfs for all possible queues like backtracking

     */
    public Node connectUsingDfsNotWorking(Node root) {
        if (root == null) return null;
        if (root.left != null && root.right != null) dfs(root.left, root.right);
        return root;
    }
    private void dfs(Node left, Node right) {
        left.next = right;

        Queue<Node> q = new LinkedList<>();
        if (left.left != null) q.add(left.left);
        if (left.right != null) q.add(left.right);
        if (right.left != null) q.add(right.left);
        if (right.right != null) q.add(right.right);

        System.out.println(q);

        Node prev = q.poll();
        while(!q.isEmpty()) {
            Node curr = q.poll();
            dfs(prev, curr);
            prev = curr;
        }
    }








    private static List<String> printNode(Node root) {
        List<String> result = new ArrayList<>();
        if (root == null) return result;
        Queue<Node> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                Node node = queue.poll();
                if (node == null) {
                    result.add("null");
                } else {
                    result.add("Node: " + node.val + ", next: " + (node.next == null ? "null" : String.valueOf(node.next.val)));
                    queue.add(node.left);
                    queue.add(node.right);
                }
            }
        }
        return result;
    }
}
