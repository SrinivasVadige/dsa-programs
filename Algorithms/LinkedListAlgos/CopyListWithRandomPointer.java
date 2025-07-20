package Algorithms.LinkedListAlgos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 20 Nov 2024
 * @link 138. Copy List with Random Pointer <a href="https://leetcode.com/problems/copy-list-with-random-pointer/">LeetCode Link</a>
 * @topics LinkList, HashMap
 * @companies Meta, Amazon, Google, Bloomberg, Microsoft, Nvidia, Snowflake, Arista Networks, Intel, Adobe, Apple, Oracle, Walmart Labs, Docusign, Morgan Stanley, TikTok, Goldman Sachs, Wix, Uber
 * @notes copy list == clone list
 */
public class CopyListWithRandomPointer {

    public static class Node {
        int val; Node next; Node random;
        public Node(int val) {this.val = val;this.next = null;this.random = null;}
        @Override
        public String toString() {return String.format("[val=%s, next=%s, random=%s], ", val, next == null? null: next.val, random == null? null: random.val);}
    }


    public static void main(String[] args) {
        Node head = new Node(1);
        head.next = new Node(2);
        head.next.next = new Node(3);
        head.next.next.next = new Node(4);
        head.next.next.next.next = new Node(5);
        head.next.next.next.next.random = head.next;
        head.next.next.random = head.next.next.next;
        head.next.random = head.next.next.next.next;
        head.random = head.next;

        System.out.print("\nGiven ----------- \n");
        for (Node trav = head; trav != null; trav = trav.next) System.out.print(trav);

        System.out.println("\n\ncopyRandomList Using OldNodeToNewNode HashMap -----------");
        for (Node trav = copyRandomListUsingOldNodeToNewNodeHashMap(head); trav != null; trav = trav.next) System.out.print(trav);

        System.out.println("\n\ncopyRandomList Using Iterative NoSpace -----------");
        for (Node trav = copyRandomListUsingIterativeNoSpace(head); trav != null; trav = trav.next) System.out.print(trav);

        System.out.println("\n\ncopyRandomList Using Recursion -----------");
        for (Node trav = copyRandomListUsingRecursion(head); trav != null; trav = trav.next) System.out.print(trav);

        System.out.println("\n\ncopyRandomList Using IndexNode HashMap -----------");
        for (Node trav = copyRandomListUsingIndexNodeHashMap(head); trav != null; trav = trav.next) System.out.print(trav);
    }



    /**
     * @TimeComplexity O(n)
     * @SpaceComplexity O(n)
     */
    public static Node copyRandomListUsingOldNodeToNewNodeHashMap(Node head) {
        Map<Node, Node> oldNodeToNewNode = new HashMap<>();

        for (Node oldNode = head; oldNode!=null; oldNode = oldNode.next) {
            oldNodeToNewNode.put(oldNode, new Node(oldNode.val));
        }

        for (Node oldNode = head; oldNode != null; oldNode = oldNode.next) {
            Node newNode = oldNodeToNewNode.get(oldNode);
            newNode.next = oldNodeToNewNode.get(oldNode.next);
            newNode.random = oldNodeToNewNode.get(oldNode.random);
        }

        /*
        // or
        for(Node oldNode = head, newNode = oldNodeToNewNode.get(head); oldNode!=null; oldNode=oldNode.next, newNode=newNode.next) {
            newNode.next = oldNodeToNewNode.get(oldNode.next);
            newNode.random = oldNodeToNewNode.get(oldNode.random);
        }
         */

        return oldNodeToNewNode.get(head);
    }







    /**
     * @TimeComplexity O(n)
     * @SpaceComplexity O(1)
     */
    public static Node copyRandomListUsingIterativeNoSpace(Node head) {
        if (head == null) {
            return null;
        }

        // Step 1: Interleave copied nodes
        // If A->B->C is the original linked list, Linked list after weaving cloned nodes would be A->A'->B->B'->C->C'
        for (Node curr = head; curr != null; curr = curr.next.next) { // A->B->C and B->C
            Node copy = new Node(curr.val); // A'
            copy.next = curr.next; // A'->B->C
            curr.next = copy; // A->A'->B->C
        }

        // Step 2: Assign random pointers -- curr == oldNode then curr.next == newNode or copy
        for (Node curr = head; curr != null; curr = curr.next.next) {
            if (curr.random != null) {
                curr.next.random = curr.random.next;
            }
        }

        // Step 3: Detach original and copied lists i.e. A->A'->B->B'->C->C' would be broken to A->B->C and A'->B'->C'
        // Node curr = head; // A->B->C
        // Node copy = head.next; // A'->B'->C'
        Node newHead = head.next;
        for (Node curr = head, copy = head.next; curr!=null && copy!=null; curr = curr.next, copy = copy.next) {
            curr.next = curr.next.next;
            copy.next = copy.next != null ? copy.next.next : null; // EDGE CASE: C -> C' -> null i.e., for last or right-most node right
        }
        return newHead;


        /*
        // or
        // Step 3: Detach original and copied lists
        Node dummyHead = new Node(0);
        Node copyCurr = dummyHead;
        for (Node curr = head; curr != null; ) {
            Node copy = curr.next;
            curr.next = copy.next;         // restore original list
            copyCurr.next = copy;          // build copied list
            copyCurr = copy;
            curr = curr.next;
        }

        return dummyHead.next;
         */
    }





    /**
     * @TimeComplexity O(n)
     * @SpaceComplexity O(n)
     * same like above OldNodeToNewNode HashMap method but here we iterate once instead of two times
     */
    public static Node copyRandomListUsingOldNodeToNewNodeHashMap2(Node head) {
        if (head == null) {
            return null;
        }

        HashMap<Node, Node> visited = new HashMap<Node, Node>(); // <oldNode, newNode>
        Node newNode = new Node(head.val);
        visited.put(head, newNode);

        for (Node oldNode=head; oldNode != null; oldNode=oldNode.next, newNode=newNode.next) {
            newNode.random = getClonedNode(oldNode.random, visited);
            newNode.next = getClonedNode(oldNode.next, visited);
        }
        return visited.get(head);
    }

    private static Node getClonedNode(Node node, HashMap<Node, Node> visited) { // just like cache-aside caching using oldNodeToNewNode
        if(node == null) {
            return null;
        }

        if (visited.containsKey(node)) {
            return visited.get(node);
        } else {
            visited.put(node, new Node(node.val));
            return visited.get(node);
        }
    }









    static HashMap<Node, Node> visitedHash = new HashMap<>(); // <oldNode, newNode>
    /**
     * @TimeComplexity O(n)
     * @SpaceComplexity O(n) for recursion stack
     */
    public static Node copyRandomListUsingRecursion(Node head) {
        if (head == null) {
            return null;
        }

        if (visitedHash.containsKey(head)) {
            return visitedHash.get(head);
        }

        Node newNode = new Node(head.val);

        visitedHash.put(head, newNode);

        newNode.next = copyRandomListUsingRecursion(head.next);
        newNode.random = copyRandomListUsingRecursion(head.random);

        return newNode;
    }







    public static Node copyRandomListUsingIndexNodeHashMap(Node head) { // UsingIndexNodeHashMap
        Node dummy = new Node(-1);
        Node newNode = dummy;

        Map<Integer, Node> indexToNewNode = new HashMap<>();
        Map<Node, Integer> oldNodeToIndex = new HashMap<>();
        List<Integer> randomNodeIndexes = new ArrayList<>();
        int i=0;

        for (Node oldNode = head; oldNode != null; oldNode = oldNode.next,i++) {
            oldNodeToIndex.put(oldNode, i);
        }


        // set "nextNode" in newNode and randomNode Indexes
        i=0;
        for (Node oldNode = head; oldNode != null; oldNode = oldNode.next, i++) {
            newNode.next = new Node(oldNode.val);
            randomNodeIndexes.add(oldNodeToIndex.get(oldNode.random));

            newNode = newNode.next; // prevNodeNode = currNewNode
            indexToNewNode.put(i, newNode); // currNewNode
        }

        // set "randomNode" in newNode
        i=0;
        for (newNode = dummy.next; newNode != null; newNode = newNode.next,i++) {
            newNode.random = indexToNewNode.get(randomNodeIndexes.get(i));
        }
        return dummy.next;
    }








    public static Node copyRandomListUsingOldNodeToNewNodeHashMap3(Node head) {
        Map<Node, Node> oldToCopy = new HashMap<>(); // i.e old node as key and the new node as value

        Node curr = head;
        while(curr != null){
            Node copy = new Node(curr.val);
            oldToCopy.put(curr, copy); // or oldToCopy.put(curr, new Node(curr.val));
            curr = curr.next;
        }

        curr = head;
        while(curr != null){
            Node copy = oldToCopy.get(curr);
            copy.next = oldToCopy.get(curr.next); // or oldToCopy.get(curr).next = oldToCopy.get(curr.next);
            copy.random = oldToCopy.get(curr.random); // or oldToCopy.get(curr).random = oldToCopy.get(curr.random);
            curr = curr.next;
        }
        return oldToCopy.get(head);
    }







    public static Node copyRandomListUsingOldNodeToNewNodeHashMap4(Node head) {
        Map<Node, Node> oldToCopy = new HashMap<>();
        oldToCopy.put(null,null);

        Node curr = head;
        while(curr != null){
            // Step1 - start - for curr
            if(!oldToCopy.containsKey(curr)){
                oldToCopy.put(curr, new Node(0));
            }
            //oldToCopy.get(curr).val = curr.val;
            Node copy = oldToCopy.get(curr);
            copy.val = curr.val;


            // Step2 - start - for curr.next
            if(!oldToCopy.containsKey(curr.next)){
                oldToCopy.put(curr.next, new Node(0));
            }

            copy.next = oldToCopy.get(curr.next); //oldToCopy.get(curr).next = oldToCopy.get(curr.next);


            // Step3 - start - for curr.random
            if(!oldToCopy.containsKey(curr.random)){
                oldToCopy.put(curr.random, new Node(0));
            }

            copy.random = oldToCopy.get(curr.random); //oldToCopy.get(curr).random = oldToCopy.get(curr.random);

            curr = curr.next;
        }

        return oldToCopy.get(head);
    }








    /**
     * Working -- only for unique values i.e failing if we have duplicate values in node.val
     */
    public Node copyRandomListMyOldApproach(Node head) {
        List<Integer> lst = new ArrayList<>();
        Map<Integer, Node> map = new HashMap<>();
        Node dummy = new Node(-1);
        Node prev = dummy;

        for (Node trav = head; trav != null; trav = trav.next) {
            prev.next = new Node(trav.val);
            prev.next.random = trav.random == null? null: new Node(-1);
            lst.add(trav.random == null? null: trav.random.val);

            map.put(prev.next.val, prev.next);
            prev = prev.next;
        }
            System.out.println(map);
            System.out.println(lst);

        int i=0;
        for (Node trav = dummy.next; trav != null; trav = trav.next,i++) {
            if (trav.random != null) {
                trav.random=map.get(lst.get(i));
            }
        }
        return dummy.next;
    }
}