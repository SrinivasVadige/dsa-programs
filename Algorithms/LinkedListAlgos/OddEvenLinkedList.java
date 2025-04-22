package Algorithms.LinkedListAlgos;

/**
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 22 April 2025
 */
public class OddEvenLinkedList {
    static class ListNode {int val; ListNode next; ListNode(int x) { val = x; } ListNode(int x, ListNode next) { val = x; this.next = next; } }
    public static void main(String[] args) {
        System.out.println("oddEvenList: ");
        ListNode head = new ListNode(1, new ListNode(2, new ListNode(3, new ListNode(4, new ListNode(5)))));
        oddEvenList(head);
        for (ListNode node = head; node != null; node = node.next) System.out.print(node.val + " ");

        System.out.println("\noddEvenListMyApproach: ");
        head = new ListNode(1, new ListNode(2, new ListNode(3, new ListNode(4, new ListNode(5)))));
        oddEvenListMyApproach(head);
        for (ListNode node = head; node != null; node = node.next) System.out.print(node.val + " ");

        // GIVEN: [1, 2, 3, 4, 5]
        // RETURN: [1, 3, 5, 2, 4]
    }

    public static ListNode oddEvenList(ListNode head) {
        if (head == null) return null;
        ListNode odd = head, even = head.next, evenHead = even;
        while (even != null && even.next != null) { // or while (odd.next != null && odd.next.next != null)
            odd.next = even.next;
            odd = odd.next;
            even.next = odd.next;
            even = even.next;
        }
        odd.next = evenHead;
        return head;
    }

    public static ListNode oddEvenListMyApproach(ListNode head) {
        if(head == null) return head; // or if(head == null || head.next==null || head.next.next==null) return head;
        ListNode odd = head, even = odd.next, evenHead = even;
        while(odd.next!=null && odd.next.next!=null) {
            // even temp var
            even = odd.next;

            // update odd using even temp var
            odd.next = even.next;

            // increment odd
            odd = odd.next;

            // update even
            even.next = odd.next;
        }

        odd.next = evenHead;

        return head;
    }
}
