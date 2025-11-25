package Algorithms.BinaryTrees;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 24 Nov 2025
 * @link 116. Populating Next Right Pointers in Each Node <a href="https://leetcode.com/problems/populating-next-right-pointers-in-each-node/">LeetCode Link</a>
 * @topics Tree, BinaryTree, LinkedList, DFS, BFS, Recursion
 * @companies Amazon(6), Bloomberg(5), Meta(4), Google(3), Microsoft(2), Walmart Labs(2), Snowflake(2), Oracle(3), ServiceNow(2), Salesforce(2)



                   1 → NULL
                /    \
               2   →  3 → NULL
              / \     / \
             4 → 5 → 6 → 7 → NULL



 */
public class PopulatingNextRightPointersInEachNode {
    public static class Node {public int val; public Node left; public Node right; public Node next; public Node() {} public Node(int _val) { val = _val; } public Node(int _val, Node _left, Node _right, Node _next) { val = _val; left = _left; right = _right; next = _next; }}

    public static void main(String[] args) {
        Node root = new Node(1);
        root.left = new Node(2);
        root.right = new Node(3);
        root.left.left = new Node(4);
        root.left.right = new Node(5);
        root.right.left = new Node(6);
        root.right.right = new Node(7);

        System.out.println("connect valid BT using recursion: " + printNode(connectUsingRecursion(root)));
        System.out.println("connect valid BT using recursion2: " + printNode(connectUsingRecursion2(root)));
        System.out.println("connect valid BT using iteration: " + printNode(connectUsingIteration(root)));
        System.out.println("connect valid BT using iteration2: " + printNode(connectUsingIteration2(root)));
        System.out.println("connect valid BT using dfs: " + printNode(connectUsingDfs(root)));
        System.out.println("connect valid BT using bfs queue: " + printNode(connectUsingBfsQueue(root)));
    }


    public static Node connectUsingRecursion(Node root) {
        if (root == null) return null;

        Node left = root.left;
        Node right = root.right;

        while (left != null) { // loop to connect "current left & right" and all "left.right & right.left" nodes like 5 → 6
            left.next = right;
            left = left.right;
            right = right.left;
        }

        connectUsingRecursion(root.left);
        connectUsingRecursion(root.right);

        return root;
    }



    public static Node connectUsingRecursion2(Node root) {
        if (root == null) return null;
        Node left = root.left;
        Node right = root.right;
        if (left != null) left.next = right;
        if (right != null) right.next = root.next == null ? null : root.next.left; // to connect middle "left.right & right.left" like 5 → 6
        connectUsingRecursion2(root.left);
        connectUsingRecursion2(root.right);
        return root;
    }




    public static Node connectUsingIteration(Node root) {
        if (root == null) return null;
        Node leftmost = root; // leftMost node of each level

        while (leftmost.left != null) {
            Node head = leftmost;

            // here focus on head and head.left nodes
            while (head != null) {
                Node left = head.left;
                Node right = head.right;
                left.next = right;
                if (head.next != null) right.next = head.next.left;  // to connect middle "left.right & right.left" like 5 → 6
                head = head.next;
            }

            leftmost = leftmost.left; // move to the next level
        }

        return root;
    }




    public static Node connectUsingIteration2(Node root) {
        if (root == null) return null;

        Node head = root;
        Node nxt = root.left;   // next level's first node

        while (nxt != null) {

            head.left.next = head.right; // connect left → right
            if (head.next != null) head.right.next = head.next.left; // connect right → next.left
            head = head.next; // move across the current level

            if (head == null) { // if end of level: move down
                head = nxt;
                nxt = head.left;
            }
        }

        return root;
    }






    public static Node connectUsingDfs(Node root) {
        if (root == null || root.left == null) return root;
        dfs(root.left, root.right);
        return root;
    }
    private static void dfs(Node left, Node right) {
        if (left == null || right == null) return;
        left.next = right;
        dfs(left.left, left.right);
        dfs(left.right, right.left);
        dfs(right.left, right.right);
    }





    public static Node connectUsingBfsQueue(Node root) {
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
