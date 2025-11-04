package Algorithms.LinkedListAlgos;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 02 Nov 2025
 * @link 92. Reverse Linked List II <a href="https://leetcode.com/problems/reverse-linked-list-ii/">LeetCode link</a>
 * @topics Linked List
 */
public class ReverseLinkedList2 {
    public static class ListNode {
        int val;
        ListNode next;
        ListNode() {}
        ListNode(int val) { this.val = val; }
        ListNode(int val, ListNode next) { this.val = val; this.next = next; }
    }
    public static void main(String[] args) {
        ListNode head = new ListNode(1, new ListNode(2, new ListNode(3, new ListNode(4, new ListNode(5)))));
        System.out.println("reverseBetween using reverse node one by one: ");
        for (ListNode trav = reverseBetweenUsingReverseNodeOneByOne(head, 2, 4); trav != null; trav = trav.next) System.out.print(trav.val + " ");


        System.out.println("\nreverseBetween Using Break And Reconnect Window 3: ");
        head = new ListNode(1, new ListNode(2, new ListNode(3, new ListNode(4, new ListNode(5)))));
        for (ListNode trav = reverseBetweenUsingBreakAndReconnectWindow3(head, 2, 4); trav != null; trav = trav.next) System.out.print(trav.val + " ");


        System.out.println("\nreverseBetween Using List: ");
        head = new ListNode(1, new ListNode(2, new ListNode(3, new ListNode(4, new ListNode(5)))));
        for (ListNode trav = reverseBetweenUsingList(head, 2, 4); trav != null; trav = trav.next) System.out.print(trav.val + " ");
    }


    /**    preW                         postW
     * 1 -> 2 -> 30 -> 40 -> 50 -> 60 -> 7
     *           l                 r
     * We are not reversing by pointer flipping through the whole window
     * Instead, we repeatedly extract the next node after curr (windowStartBeforeReverse node) and insert it right after preWindow.
     * This simulates reversal in-place one node at a time
     * i.e
     * 1 -> 2 -> 40 -> 30 -> 50 -> 60 -> 7 --- we pluck 40 and inserted after preWindow
     * 1 -> 2 -> 50 -> 40 -> 30 -> 60 -> 7 --- we pluck 50 and inserted after preWindow
     * 1 -> 2 -> 60 -> 50 -> 40 -> 30 -> 7 --- we pluck 60 and inserted after preWindow
     */
    public static ListNode reverseBetweenUsingReverseNodeOneByOne(ListNode head, int left, int right) {
        if (head == null || left == right) return head;

        ListNode dummy = new ListNode();
        dummy.next = head;
        ListNode preWindow = dummy;

        // Move preWindow to node before the left
        for (int i = 1; i < left; i++) {
            preWindow = preWindow.next;
        }

        ListNode curr = preWindow.next; // windowStartBeforeReverse - left node
        // Reverse [left, right]
        for (int i = 0; i < right - left; i++) {
            ListNode next = curr.next; // pluck 40
            curr.next = next.next; // remove the plucked node from the list by connecting 30 -> 50 & we already have 40.next = 50
            next.next = preWindow.next; // 40 -> 30
            preWindow.next = next; // 2 -> 40 -> 30
        }

        return dummy.next;
    }





    public static ListNode reverseBetweenUsingBreakAndReconnectWindow1(ListNode head, int left, int right) { // reverseBetweenUsingBreakAndReconnectWindow1
        ListNode dummy = new ListNode();
        dummy.next = head;
        ListNode prev = null, curr = dummy;

        // 1) GET ALL WINDOW VARS ---
        for (int i=1; i <= left; i++) {
            prev = curr;
            curr = curr.next;
        }

        ListNode preWindow = prev; // PRE
        ListNode postWindow = curr.next; // POST - update later
        ListNode windowStart = curr;
        ListNode windowEnd = curr; // - update later

        for (int i=left; i < right; i++) {
            windowEnd = windowEnd.next;
            postWindow = postWindow.next;
        }


        // 2) BREAK LEFT AND RIGHT WINDOW ---
        preWindow.next = null;
        windowEnd.next = null;


        // 3) REVERSE WINDOW ---
        prev = null;
        for (int i=left; i <= right; i++) {
            ListNode next = curr.next; // temp hold the next sequence
            curr.next = prev; // from -> to <-
            prev = curr;
            curr = next;
        }

        // 4) RECONNECT WINDOW ---
        // as we reversed start and end
        // ---> prev is the last node before reverse and after reverse prev is the windowStart node
        // ---> windowStart is the windowEnd now
        preWindow.next = prev;
        windowStart.next = postWindow;

        return dummy.next;
    }






    public static ListNode reverseBetweenUsingBreakAndReconnectWindow2(ListNode head, int left, int right) { // reverseBetweenUsingBreakAndReconnectWindow2
        if (head == null || left == right) return head;

        ListNode dummy = new ListNode();
        dummy.next = head;

        // 1) Move prev to (left-1) and curr to left
        ListNode preWindow = dummy;
        for (int i = 1; i < left; i++) {
            preWindow = preWindow.next;
        }

        ListNode curr = preWindow.next;
        ListNode next = curr.next;
        ListNode windowEndAfterReverse = curr; // i.e windowStartBeforeReverse

        // 2) Reverse from left to right (in-place)
        for (int i = left; i < right; i++) {
            ListNode tempNextMost = next.next;
            next.next = curr;
            curr = next;
            next = tempNextMost;
        }

        // 3) Reconnect
        preWindow.next = curr; // curr == windowStartAfterReverse
        windowEndAfterReverse.next = next;

        return dummy.next;
    }





    /**
     * 🔥
     */
    public static ListNode reverseBetweenUsingBreakAndReconnectWindow3(ListNode head, int left, int right) {
        if (head == null || left == right) return head;

        ListNode dummy = new ListNode(0);
        dummy.next = head;

        // 1) Move preWindow to node before `left`, cur to node at `left`
        ListNode preWindow = dummy;
        for (int i = 1; i < left; i++) {
            preWindow = preWindow.next;
        }

        // 2) Reverse [left .. right]
        ListNode prev = null;
        ListNode cur = preWindow.next;
        ListNode windowEndAfterReverse = cur; // i.e windowStartBeforeReverse
        for (int i = 0; i <= right - left; i++) {
            ListNode next = cur.next;
            cur.next = prev; // from -> to <-
            prev = cur;
            cur = next;
        }

        // 3) reconnect
        preWindow.next = prev; // prev == windowStartAfterReverse
        windowEndAfterReverse.next = cur; // curr == postWindow
        /*
        // or
        preWindow.next.next = cur; // preWindow.next is windowEndAfterReverse & curr == postWindow
        preWindow.next = prev; // prev == windowStartAfterReverse - prev is the new head of reversed part
         */

        return dummy.next;
    }









    public static ListNode reverseBetweenUsingList(ListNode head, int left, int right) {
        List<Integer> list = new ArrayList<>();
        while(head!=null){
            list.add(head.val);
            head=head.next;
        }

        Collections.reverse(list.subList(left-1, right)); // left inclusive & right exclusive

        ListNode dummy = new ListNode();
        ListNode trav = dummy;
        for(int i:list){
            trav.next=new ListNode(i);
            trav=trav.next;
        }
        return dummy.next;
    }
}
