package Algorithms.LinkedListAlgos;

/**
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 12 Nov 2024
 * @link 82. Remove Duplicates from Sorted List II <a href="https://leetcode.com/problems/remove-duplicates-from-sorted-list-ii/">LeetCode link</a>
 * @topics Linked List, Recursion, Two pointers
 */
public class RemoveDuplicatesFromSortedList2 {
    public static class ListNode {int val; ListNode next; ListNode(int val){this.val=val;} ListNode(int val, ListNode next){this.val=val; this.next=next;}}

    public static void main(String[] args) {
        ListNode head;
        System.out.println("\ndeleteDuplicates using two pointers => ");
        head = new ListNode(1, new ListNode(2, new ListNode(3, new ListNode(3, new ListNode(4, new ListNode(4, new ListNode(5)))))));
        for (ListNode trav = deleteDuplicatesUsingTwoPointers(head); trav != null; trav = trav.next) System.out.print(trav.val + " ");

        System.out.println("\ndeleteDuplicates using recursion => ");
        head = new ListNode(1, new ListNode(2, new ListNode(3, new ListNode(3, new ListNode(4, new ListNode(4, new ListNode(5)))))));
        for (ListNode trav = deleteDuplicatesUsingRecursion(head); trav != null; trav = trav.next) System.out.print(trav.val + " ");
    }


    /**
     * @TimeComplexity O(n)
     * @SpaceComplexity O(1)
     */
    public static ListNode deleteDuplicatesUsingTwoPointers(ListNode head) {
        if (head == null || head.next == null) return head;

        ListNode dummy = new ListNode(0, head);
        ListNode preWindow = dummy;
        ListNode curr = head;

        while (curr != null) {
            if (curr.next != null && curr.val == curr.next.val) { // duplicateWindow
                int windowStartVal = curr.val; // dupVal
                while (curr != null && curr.val == windowStartVal) {
                    curr = curr.next;
                }
                preWindow.next = curr; // skip duplicateWindow
            } else { // no duplicate — advance normally
                preWindow = curr;
                curr = curr.next;
            }
        }

        return dummy.next;
    }





    public static ListNode deleteDuplicatesUsingTwoPointers2(ListNode head) {
        if (head == null || head.next == null) return head;

        ListNode dummy = new ListNode(0, head);
        ListNode preWindow = dummy;
        ListNode curr = head;

        while(curr != null) {
            if(curr.next != null && curr.val == curr.next.val) {
                while (curr.next != null && curr.val == curr.next.val) {
                    curr = curr.next;
                }
                preWindow.next = curr.next;
            } else {
                preWindow = preWindow.next;
            }
            curr = curr.next;
        }
        return dummy.next;
    }






    /**
     * @TimeComplexity O(n)
     * @SpaceComplexity O(1)
     */
    public static ListNode deleteDuplicatesUsingTwoPointers3(ListNode head) {
        if (head == null || head.next == null) return head;

        ListNode dummy = new ListNode(0, head);
        ListNode preWindow = dummy;
        ListNode curr = head;

        while (curr != null && curr.next != null) {
            if (curr.val == curr.next.val) { // currWindow is dup

                ListNode windowStart = curr;
                while (curr != null && curr.val == windowStart.val) { // skip currWindow
                    curr = curr.next;
                }

                preWindow.next = curr; // now curr is in nextWindow
            }

            if (curr != null && curr.next != null && curr.val != curr.next.val) { // if nextWindow is not dup
                preWindow = curr;
                curr = curr.next;
            }
        }

        return dummy.next;
    }








    /**
     * @TimeComplexity O(n)
     * @SpaceComplexity O(1), O(n) if we consider recursion stack space
     */
    public static ListNode deleteDuplicatesUsingRecursion(ListNode head) {
        if (head == null || head.next == null) return head;

        if (head.val != head.next.val) {
            head.next = deleteDuplicatesUsingRecursion(head.next);
            return head;
        }
        // dup window
        ListNode trav = head;
        while (trav != null && trav.val == head.val) trav = trav.next;
        return deleteDuplicatesUsingRecursion(trav);
    }

}
