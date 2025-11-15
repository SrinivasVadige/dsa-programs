package Algorithms.LinkedListAlgos;

/**
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 15th Nov 2025
 * @link 86. Partition List <a href="https://leetcode.com/problems/partition-list/">LeetCode link</a>
 * @topics Linked List, Two Pointers
 */
public class PartitionList {
    public static class ListNode {int val; ListNode next; ListNode(){} ListNode(int val){this.val = val;} ListNode(int val, ListNode next){this.val = val; this.next = next;}}

    public static void main(String[] args) {
        ListNode head;
        int x = 3;

        System.out.println("Partition List using Two Pointers => ");
        head = new ListNode(1, new ListNode(4, new ListNode(3, new ListNode(2, new ListNode(5, new ListNode(2))))));
        for (ListNode trav = partitionUsingTwoPointers(head, x); trav!=null; trav=trav.next) System.out.print(trav.val + " ");


        System.out.println("\nPartition List using Two Pointers & In-place => ");
        head = new ListNode(1, new ListNode(4, new ListNode(3, new ListNode(2, new ListNode(5, new ListNode(2))))));
        for (ListNode trav = partitionUsingTwoPointers2(head, x); trav!=null; trav=trav.next) System.out.print(trav.val + " ");
    }


    /**
     * @TimeComplexity O(n)
     * @SpaceComplexity O(1)

        Two pointers & Two-List Partition (a.k.a. Stable Partition Using Two Pointers / Two Linked Lists)

     */
    public static ListNode partitionUsingTwoPointers(ListNode head, int x) {
        ListNode smallDummy = new ListNode();
        ListNode largeDummy = new ListNode();

        ListNode smallTail = smallDummy;
        ListNode largeTail = largeDummy;

        while (head != null) {
            if (head.val < x) {
                smallTail.next = head;
                smallTail = smallTail.next;
            } else {
                largeTail.next = head;
                largeTail = largeTail.next;
            }
            head = head.next;
        }

        largeTail.next = null; // important
        smallTail.next = largeDummy.next;

        return smallDummy.next;
    }





    /**
     * @TimeComplexity O(n)
     * @SpaceComplexity O(1)

    Two pointers & In-place Stable Partition Using Pointer Surgery

    Here, maintain the "r" pointer just before r.next.val < x nodes
    ---> then it's easy to break r.next and connect it as l.next

        d - 1 - 4 - 3 - 0 - 2 - 5 - 2
        x=3

        d - 1 - 4 - 3 - 0 - 2 - 5 - 2
        lr

        d - 1 - 4 - 3 - 0 - 2 - 5 - 2 ---> 1st while loop
            lr

        d - 1 - 4 - 3 - 0 - 2 - 5 - 2
            l   r

        d - 1 - 4 - 3 - 0 - 2 - 5 - 2 ---> r.next.val < x
            l       r

        d - 1 - 0 - 4 - 3 - 2 - 5 - 2 ---> after swap & increment
                l       r

        d - 1 - 0 - 4 - 3 - 2 - 5 - 2 ---> r.next.val < x
                l       r

        d - 1 - 0 - 2 - 4 - 3 - 5 - 2 ---> after swap & increment
                    l       r

        d - 1 - 0 - 2 - 4 - 3 - 5 - 2
                    l           r

        d - 1 - 0 - 2 - 2 - 4 - 3 - 5
                        l           r

     */

    public static ListNode partitionUsingTwoPointers2(ListNode head, int x) {

        if (head == null || head.next == null) return head;

        ListNode dummy = new ListNode(0, head);
        ListNode l = dummy, r = dummy;

        while (l==r && r.next != null && r.next.val < x) {
            l = l.next;
            r = r.next;
        }

        while (r.next!=null) {
            if (r.next.val < x) {
                ListNode smallNode = r.next;
                r.next = r.next.next;
                smallNode.next = l.next;
                l.next = smallNode;

                l = l.next;
            } else {
                r = r.next;
            }
        }
        return dummy.next;
    }
}
