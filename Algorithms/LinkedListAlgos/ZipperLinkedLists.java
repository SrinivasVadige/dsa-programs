package Algorithms.LinkedListAlgos;

/**
 * If result head node index is even i..e from 0, then add the head1 node and head2 node if odd
 *
 * @see SwapNodesInPairs.java
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 17 Nov 2024
 */
public class ZipperLinkedLists {

    static class ListNode { int val; ListNode next; ListNode() {} ListNode(int val) { this.val = val; } ListNode(int val, ListNode next) { this.val = val; this.next = next; } }

    public static void main(String[] args) {
        ListNode head1 = new ListNode(1, new ListNode(3, new ListNode(5, new ListNode(7))));
        ListNode head2 = new ListNode(2, new ListNode(4, new ListNode(6)));

        System.out.println("zipLists: ");
        for (ListNode trav = zipLists(head1, head2); trav != null; trav = trav.next) System.out.print(trav.val + " ");

        System.out.println("\nzipListsUsingIndex: ");
        head1 = new ListNode(1, new ListNode(3, new ListNode(5, new ListNode(7))));
        head2 = new ListNode(2, new ListNode(4, new ListNode(6)));
        for (ListNode trav = zipListsUsingIndex(head1, head2); trav != null; trav = trav.next) System.out.print(trav.val + " ");

        System.out.println("\nzipListsUsingRecursion: ");
        head1 = new ListNode(1, new ListNode(3, new ListNode(5, new ListNode(7))));
        head2 = new ListNode(2, new ListNode(4, new ListNode(6)));
        for (ListNode trav = zipListsUsingRecursion(head1, head2); trav != null; trav = trav.next) System.out.print(trav.val + " ");
    }

    /**
     *          1 -> 3 -> 5 -> 7 -> null
     *          2 -> 4 -> 6 -> null
     *
     *          1 -> 2 -> 3 -> 4 -> 5 -> 6 -> 7 -> null
     */
    public static ListNode zipLists(ListNode head1, ListNode head2) {
        ListNode dummy = new ListNode(), prev = dummy, curr1 = head1, curr2 = head2;
        while (curr1 != null && curr2 != null) {
            // prev.next = curr1;
            // prev.next.next = curr2; // --- this is manipulating curr1.next sequence

            prev.next = new ListNode(curr1.val);
            prev.next.next = new ListNode(curr2.val);

            curr1 = curr1.next;
            curr2 = curr2.next;
            prev = prev.next.next;
        }
        if (curr1 != null) prev.next = curr1; else prev.next = curr2;
        return dummy.next;
    }

    public static ListNode zipListsUsingIndex(ListNode head1, ListNode head2) {
        ListNode dummy = new ListNode(), prev = dummy, curr1 = head1, curr2 = head2;
        int index=0;
        while (curr1 != null && curr2 != null) {
            if((index&1)==0) { // index/2==0
                prev.next = curr1;
                curr1 = curr1.next;
            } else {
                prev.next = curr2;
                curr2 = curr2.next;
            }
            prev = prev.next;
            index++;
        }
        if (curr1 != null) prev.next = curr1; else prev.next = curr2;
        return dummy.next;
    }

    public static ListNode zipListsUsingRecursion(ListNode head1, ListNode head2) {
        if(head1==null && head2==null) return null;
        if (head1 == null) return head2;
        else if (head2 == null) return head1;
        else {
            ListNode next1 = head1.next;
            ListNode next2 = head2.next;
            head1.next = head2;
            head2.next = zipListsUsingRecursion(next1, next2);
            return head1;
        }
    }
}
