package Algorithms.LinkedListAlgos;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

/**
 * @see Algorithms.LinkedListAlgos.SwapNodesInPairs
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 17 Nov 2024
 * @link 25. Reverse Nodes in k-Group <a href="https://leetcode.com/problems/reverse-nodes-in-k-group/">25. Reverse Nodes in k-Group</a>
 * @topics Linked List, Recursion, Stack
 */
public class ReverseNodesInKGroup {
    public static class ListNode { int val; ListNode next; ListNode() {} ListNode(int val) { this.val = val; } ListNode(int val, ListNode next) { this.val = val; this.next = next; } }

    public static void main(String[] args) {
        ListNode head; // = new ListNode(1, new ListNode(2, new ListNode(3, new ListNode(4, new ListNode(5, new ListNode(6, new ListNode(7)))))));

        System.out.println("reverseKGroup using remaining nodes and break & reconnect: ");
        head = new ListNode(1, new ListNode(2, new ListNode(3, new ListNode(4, new ListNode(5, new ListNode(6))))));
        for (ListNode trav = reverseKGroupUsingRemainingNodesAndBreakReconnect(head, 3); trav != null; trav = trav.next) System.out.print(trav.val + " ");

        System.out.println("\nreverseKGroup using kth node: ");
        head = new ListNode(1, new ListNode(2, new ListNode(3, new ListNode(4, new ListNode(5, new ListNode(6))))));
        for (ListNode trav = reverseKGroupUsingKthNode(head, 3); trav != null; trav = trav.next) System.out.print(trav.val + " ");

        System.out.println("\nreverseKGroup using kth node 2: ");
        head = new ListNode(1, new ListNode(2, new ListNode(3, new ListNode(4, new ListNode(5, new ListNode(6, new ListNode(7)))))));
        for (ListNode trav = reverseKGroupUsingKthNode2(head, 3); trav != null; trav = trav.next) System.out.print(trav.val + " ");

        System.out.println("\nreverseKGroup using recursion: ");
        head = new ListNode(1, new ListNode(2, new ListNode(3, new ListNode(4, new ListNode(5, new ListNode(6, new ListNode(7)))))));
        for (ListNode trav = reverseKGroupUsingRecursion(head, 3); trav != null; trav = trav.next) System.out.print(trav.val + " ");

        System.out.println("\nreverseKGroup using list: ");
        head = new ListNode(1, new ListNode(2, new ListNode(3, new ListNode(4, new ListNode(5, new ListNode(6, new ListNode(7)))))));
        for (ListNode trav = reverseKGroupUsingList(head, 3); trav != null; trav = trav.next) System.out.print(trav.val + " ");

        System.out.println("\nreverseKGroup using stack: ");
        head = new ListNode(1, new ListNode(2, new ListNode(3, new ListNode(4, new ListNode(5, new ListNode(6, new ListNode(7)))))));
        for (ListNode trav = reverseKGroupUsingStack(head, 3); trav != null; trav = trav.next) System.out.print(trav.val + " ");

    }





    public static ListNode reverseKGroupUsingRemainingNodesAndBreakReconnect(ListNode head, int k) {
        int n = nodeLength(head);
        ListNode dummy = new ListNode(-1);
        dummy.next = head;
        ListNode preWindow = dummy;

        for (int rem = n; rem>=k; rem-=k) { // remaining
            ListNode windowEndAfterReverse = preWindow.next; // windowStartBeforeReverse
            ListNode prev = windowEndAfterReverse;
            ListNode curr = prev.next;
            for (int kCount = 1; kCount < k; kCount++) {
                ListNode next = curr.next;
                curr.next = prev;
                prev = curr;
                curr = next;
            }

            preWindow.next = prev; // windowStartAfterReverse
            windowEndAfterReverse.next = curr; // next K-Group
            preWindow = windowEndAfterReverse;
        }

        return dummy.next;
    }

    private static int nodeLength(ListNode node) {
        int len = 0;
        for (ListNode trav = node; trav !=null; trav = trav.next) len++;
        return len;
        // if (node == null) return 0; return 1 + nodeLength(node.next);
    }











    /**
     * HERE, WE DIVIDE THE GROUPS INTO PIECES AND LINK "BEFORE GROUP LAST NODE" WITH "CURRENT GROUP FIRST NODE"

     * Use 3 pointers: groupStart, prevGroupLast, kthNode
     * kthNode == groupEnd == groupStart + (k -1)

     * when k = 3
     * Initially
                         group
                       _________
               -1     |         |
              null -> 1 -> 2 -> 3 -> 4 -> 5 -> 6 -> 7 -> null   ------ START
               ↓      ↓         ↓    ↓
              prevL  trav      kth  nextN


       Just after the first subList is reversed
              null -> 3 -> 2 -> 1 -> null null -> 4 -> 5 -> 6 -> 7 -> null ----- i.e if trav==head then head=kthNode
               ↓      ↓         ↓                 ↓
             prevL   kth       trav              nextN


       Just after the second subList is reversed
              null -> 3 -> 2 -> 1 -> null null -> 6 -> 5 -> 4 -> null null -> 7 -> null ----- i.e if trav==head then head=kthNode
                                ↓                 ↓         ↓                 ↓
                              prevL              kth       trav              nextN

     * @TimeComplexity O(n)
     * @SpaceComplexity O(1)
     */
    public static ListNode reverseKGroupUsingKthNode(ListNode head, int k){
        ListNode groupStart = head;
        ListNode prevGroupLast = null;
        while(groupStart != null){

            // PREPARE 2 POINTERS (K, nextNode)
            ListNode kthNode = getKth(groupStart, k); // this will return kthNode or last node i.e null
                // REMAINING SEQUENCE IF CONDITION
            if(kthNode == null) { // reached the end
                // if (prevGroupLast != null) // when len%k != 0 --- OPTIONAL as even if we don't use it, won't cause any problem as
                // i.e remaining sequence because it's less than k --- i.e len!=k --> i.e len%k!=0
                prevGroupLast.next = groupStart; // then link after the remaining sequence
                break;
            }
            ListNode nextNode = kthNode.next; // before unlinking, store the next sequence
            kthNode.next = null; // --------> "unlink after". Therefore we got "null -> 1 -> 2 -> 3" independent subList

            // REVERSE
            ListNode prev = null, curr = groupStart; // --------> "unlink before" by using pre=null
            while (curr != null) {
                ListNode tempNext = curr.next; // temp hold the next sequence
                curr.next = prev; // from -> to <-
                prev = curr;
                curr = tempNext;
            }

            // LINK BEFORE
            if(groupStart==head){ // -- only used for 1st subList as prevLast==null -- head only changes once i.e 3 and then stays the same
                head = kthNode;
            }else{
                prevGroupLast.next = kthNode; // link before -- new list
            }

            // UPDATE POINTERS
            prevGroupLast = groupStart;
            groupStart = nextNode;
        }
        return head;

        /*
        if (kthNode == null){
            if (prevGroupLast != null)
                   prevGroupLast.next = groupStart;
            break;
        }


        Just use

        if (kthNode == null){
            prevGroupLast.next = groupStart;
            break;
        }

        "if (prevGroupLast != null)" ---- This if condition is redundant and can be safely removed, as

        when len % k == 0 then ----> groupStart is null in the last loop and then ----> while (groupStart != null )  will exit the while loop

        when len % k !=0 then it comes to the above condition(to add the remaining nodes) and then "prevGroupLast != null" will always be true

        And if use while(true) instead then we need that condition
         */
    }

    private static ListNode getKth(ListNode trav, int k) {
        while (trav != null && --k > 0) // as kthIndex-travIndex=k-1
            trav = trav.next; // trav lost reference connection
        return trav;
    }

    private static ListNode getKth2(ListNode groupPrev, int k) {
        while (groupPrev != null && k-- > 0)
            groupPrev = groupPrev.next;
        /*
         // or
        while (groupPrev != null && k > 0) { --- to loop k times and k is a pass by value
            groupPrev = groupPrev.next;
            k--;
        }
         */
        return groupPrev;
    }

        /**
           3 Pointers -> groupPrev, groupNext, kth

           when k = 3
                             group
                           _________
                   -1     |         |
                  null -> 1 -> 2 -> 3 -> 4 -> 5 -> 6 -> 7 -> null   ------ START
                   ↓                ↓    ↓
                  prev             kth  next

                  null -> 3 -> 2 -> 1 -> 4 -> 5 -> 6 -> 7 -> null
                                    ↓              ↓    ↓
                                   prev           kth  next

           when len % k == 0
                 null -> 3 -> 2 -> 1 -> 6 -> 5 -> 4 -> null         ------ END while(groupPrev.next != null)
                                                  ↓
                                                 prev

           when len % k != 0
                 null -> 3 -> 2 -> 1 -> 6 -> 5 -> 4 -> 7 -> null    ------ END getKth() returns null
                                                  ↓          ↓
                                                 prev        kth
     */
    public static ListNode reverseKGroupUsingKthNode2(ListNode head, int k) {
        ListNode dummy = new ListNode(-1, head);
        ListNode groupPrev = dummy;
        while (groupPrev.next != null) { // or while(true)
            // prepare 3 pointers
            ListNode kth = getKth2(groupPrev, k);
            if (kth == null)
                break;
            ListNode groupNext = kth.next;

            // reverse the group
            ListNode prev = groupNext, curr = groupPrev.next;
            while (curr != groupNext) {
                ListNode next = curr.next; // temp hold the next sequence
                curr.next = prev; // from -> to <-
                prev = curr;
                curr = next;
            }

            // update groupPrev and dummy indirectly --- as dummy=[-1,1,4,5,6,7], [-1,3,2,1,4]
            ListNode temp = groupPrev.next;
            groupPrev.next = kth; // updates dummy to [-1,3,2,1,4,5], [-1,3,2,1,6,5,4,7]
            groupPrev = temp;
        }
        return dummy.next;
    }




    public static ListNode reverseKGroupUsingRecursion(ListNode head, int k) {
        if (head == null || head.next == null) return head;

        ListNode curr = head;
        ListNode prev = null;
        int count = 0;

        // are the remaining nodes count >= k ?
        for (ListNode trav = head; trav !=null && count < k; trav = trav.next) count++;
        if(count != k) return head;

        // reverse the group
        count = 0;
        while (curr != null && count < k) {
            ListNode next = curr.next;
            curr.next = prev;
            prev = curr;
            curr = next;
            count++;
        }

        if (count == k) {
            head.next = reverseKGroupUsingRecursion(curr, k);
            return prev;
        } else {
            return head;
        }
    }






    public static ListNode reverseKGroupSuggested(ListNode head, int k) {
        ListNode dummy = new ListNode(-1);
        dummy.next = head;

        ListNode trav = dummy;

        while (trav.next != null) {
            ListNode start = trav;
            ListNode end = trav;
            for (int i = 0; i < k && end != null; i++) {
                end = end.next;
            }
            if (end == null) break;
            ListNode next = end.next;
            end.next = null;
            start.next = reverseKGroupSuggested(next, k);
            trav.next = end;
            trav = start;
        }
        return dummy.next;
    }






    public static ListNode reverseKGroupUsingList(ListNode head, int k) {

        List<Integer> lst = new ArrayList<>();
        List<Integer> kLst = new ArrayList<>();

        for(ListNode trav=head; trav!=null; trav=trav.next) {
            kLst.add(trav.val);
            if(k==kLst.size()) {
                Collections.reverse(kLst);
                lst.addAll(kLst);
                kLst.clear();
            }
        }
        lst.addAll(kLst);

        ListNode dummy = new ListNode(-1), trav=dummy;
        for(Integer n: lst) {
            trav.next = new ListNode(n);
            trav=trav.next;
        }

        return dummy.next;
    }




    public static ListNode reverseKGroupUsingStack(ListNode head, int k) {
        Stack<ListNode> st = new Stack<>();

        int size = 0;
        ListNode node = head;
        while (node != null) {
            size++;
            node = node.next;
        }

        node = head;
        ListNode ans = new ListNode(-1);
        ListNode ansHead = ans;
        int n = size/k;
        System.out.println(size);

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < k; j++) {
                st.push(node);
                node = node.next;
            }

            while (st.size() > 0) {
                ans.next = st.pop();
                ans = ans.next;
            }
        }

        while (node != null) {
            ans.next = node;
            ans = ans.next;
            node = node.next;
        }
        ans.next = null;
        return ansHead.next;
    }
}
