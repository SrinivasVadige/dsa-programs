package Algorithms.LinkedListAlgos;

import java.util.HashSet;

/**
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 12 Nov 2024
 * @link 83. Remove Duplicates from Sorted List <a href="https://leetcode.com/problems/remove-duplicates-from-sorted-list/">LeetCode link</a>
 * @topics Linked List, Recursion, Two pointers
 */
public class RemoveDuplicatesFromSortedList {
    public static class ListNode {int val; ListNode next; ListNode() {} ListNode(int val) { this.val = val; } ListNode(int val, ListNode next) { this.val = val; this.next = next; }}
    public static void main(String[] args) {
        ListNode head;

        System.out.println("deleteDuplicates using iteration => ");
        head = new ListNode(1, new ListNode(1, new ListNode(2)));
        for (ListNode trav = deleteDuplicatesUsingIteration(head); trav != null; trav = trav.next) System.out.print(trav.val + " ");

        System.out.println("\ndeleteDuplicates using recursion => ");
        head = new ListNode(1, new ListNode(1, new ListNode(2)));
        for (ListNode trav = deleteDuplicatesUsingRecursion(head); trav != null; trav = trav.next) System.out.print(trav.val + " ");

        System.out.println("\ndeleteDuplicates using two pointers => ");
        head = new ListNode(1, new ListNode(1, new ListNode(2)));
        for (ListNode trav = deleteDuplicatesUsingTwoPointers(head); trav != null; trav = trav.next) System.out.print(trav.val + " ");

        System.out.println("\ndeleteDuplicates using hashset => ");
        head = new ListNode(1, new ListNode(1, new ListNode(2)));
        for (ListNode trav = deleteDuplicatesUsingHashSet(head); trav != null; trav = trav.next) System.out.print(trav.val + " ");
    }


    /**
     * @TimeComplexity O(n)
     * @SpaceComplexity O(1)
     */
    public static ListNode deleteDuplicatesUsingIteration(ListNode head) {
        if (head == null) return null;
        ListNode curr = head;
        while(curr.next != null) {
            if (curr.val == curr.next.val) {
                curr.next = curr.next.next;
            } else {
                curr = curr.next;
            }
        }
        return head;
    }





    /**
     * @TimeComplexity O(n)
     * @SpaceComplexity O(1)
     */
    public static ListNode deleteDuplicatesUsingIteration2(ListNode head) {
        if (head == null) return null;
        ListNode curr = head;
        while(curr != null) {
            while (curr.next != null && curr.val == curr.next.val) {
                curr.next = curr.next.next;
            }
            curr = curr.next;
        }
        return head;
    }






    /**
     * @TimeComplexity O(n)
     * @SpaceComplexity O(n)
     */
    public static ListNode deleteDuplicatesUsingRecursion(ListNode head) {
        if (head == null || head.next == null) return head;
        head.next = deleteDuplicatesUsingRecursion(head.next);
        return (head.val == head.next.val) ? head.next : head;
    }







    /**
     * @TimeComplexity O(n)
     * @SpaceComplexity O(n)
     */
    public static ListNode deleteDuplicatesUsingTwoPointers(ListNode head) {
        if (head == null) return null;
        ListNode prev = head;
        ListNode curr = head.next;

        while (curr != null) {
            if (curr.val == prev.val) {
                prev.next = curr.next; // Skip duplicate
            } else {
                prev = curr; // Move prev forward only when unique found
            }
            curr = curr.next; // Always move curr
        }

        return head;
    }





    /**
     * @TimeComplexity O(n)
     * @SpaceComplexity O(n)
     */
    public static ListNode deleteDuplicatesUsingHashSet(ListNode head) {
        if (head == null) return null;

        HashSet<Integer> set = new HashSet<>();

        ListNode res = new ListNode(head.val);
        set.add(head.val);

        ListNode trav = res;
        while (head != null) {
            if(set.add(head.val)) {
                trav.next = new ListNode(head.val);
                trav = trav.next;
            }
            head = head.next;
        }
        return res;
    }

}
