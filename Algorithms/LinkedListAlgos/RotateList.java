package Algorithms.LinkedListAlgos;

/**
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 13 Nov 2024
 * @link 61. Rotate List <a href="https://leetcode.com/problems/rotate-list/">LeetCode link</a>
 * @topics Linked List, Two Pointers
 */
public class RotateList {
    public static class ListNode {int val; ListNode next; ListNode(int val) {this.val = val;} ListNode(int val, ListNode next){this.val=val; this.next=next; } }

    public static void main(String[] args) {
        ListNode head;

        System.out.println("rotateRight using window approach => ");
        head = new ListNode(1, new ListNode(2, new ListNode(3, new ListNode(4, new ListNode(5)))));
        for (ListNode trav = rotateRightUsingTwoPointers(head, 2); trav != null; trav = trav.next) System.out.print(trav.val + " ");

        System.out.println("\nrotateRight using circular linked list => ");
        head = new ListNode(1, new ListNode(2, new ListNode(3, new ListNode(4, new ListNode(5)))));
        for (ListNode trav = rotateRightUsingCircularLinkedList(head, 2); trav != null; trav = trav.next) System.out.print(trav.val + " ");
    }




    /**
     * @TimeComplexity O(n)
     * @SpaceComplexity O(1)
     * It's also a window approach
     */
    public static ListNode rotateRightUsingTwoPointers(ListNode head, int k) {
        if (head == null || head.next == null) return head;

        int n = 0;
        for(ListNode trav = head; trav!=null; trav = trav.next) n++;

        k = k % n;
        if (k == 0) return head;

        ListNode dummy = new ListNode(0, head);
        ListNode preW = dummy;
        ListNode wStart = head;
        ListNode wEnd = dummy;

        for(int i=1; i<=k; i++) wEnd = wEnd.next;

        while(wEnd.next != null) {
            preW = preW.next;
            wStart =  wStart.next;
            wEnd = wEnd.next;
        }

        preW.next = null;
        wEnd.next = dummy.next;
        dummy.next = wStart;

        return dummy.next;
    }








    /**
     * @TimeComplexity O(n)
     * @SpaceComplexity O(1)
     * It's a window approach
     */
    public static ListNode rotateRightUsingTwoPointers2(ListNode head, int k) {
        if(head == null || head.next == null) return head;

        int n = 1;
        for (ListNode trav = head; trav.next != null; trav = trav.next) n++;

        k = k % n;
        if(k == 0) return head;

        ListNode preW = head, wEnd = head; // slow & fast
        for (int i = 0; i < k; i++) wEnd = wEnd.next;

        while (wEnd.next != null) {
            preW = preW.next;
            wEnd = wEnd.next;
        }

        wEnd.next = head;
        head = preW.next;
        preW.next = null;

        return head;
    }









    /**
     * @TimeComplexity O(n)
     * @SpaceComplexity O(1)
     */
    public static ListNode rotateRightUsingCircularLinkedList(ListNode head, int k) {
        if (head == null || head.next == null) return head;

        int n = 1;
        ListNode tail = head;
        while(tail.next != null) {
            tail = tail.next;
            n++;
        }

        tail.next = head; // make a circular SinglyLinkedList

        k = k % n;
        int newHeadIndex = n - k;
        ListNode newTail = head;
        for (int i=1; i<newHeadIndex; i++) {
            newTail = newTail.next;
        }

        ListNode newHead = newTail.next;
        newTail.next = null; // break the circular linked list

        return newHead;
    }

}
