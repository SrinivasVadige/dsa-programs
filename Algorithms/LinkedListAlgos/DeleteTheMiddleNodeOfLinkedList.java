package Algorithms.LinkedListAlgos;

/**
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 21 April 2025
 */
public class DeleteTheMiddleNodeOfLinkedList {
    static class ListNode {int val;ListNode next;ListNode(int x) {val = x;} ListNode(int x, ListNode next) { val = x; this.next = next;}}
    public static void main(String[] args) {
        System.out.println("deleteMiddleUsingSize(head) => ");
        ListNode head = new ListNode(1, new ListNode(3, new ListNode(4, new ListNode(7, new ListNode(1)))));
        deleteMiddleUsingSize(head);
        for (ListNode trav = head; trav != null; trav = trav.next) {System.out.print(trav.val + " ");}

        System.out.println("\ndeleteMiddleUsingThreePointers(head) => ");
        head = new ListNode(1, new ListNode(3, new ListNode(4, new ListNode(7, new ListNode(1)))));
        deleteMiddleUsingThreePointers(head);
        for (ListNode trav = head; trav != null; trav = trav.next) {System.out.print(trav.val + " ");}

        head = new ListNode(1, new ListNode(3, new ListNode(4, new ListNode(7, new ListNode(1)))));
        System.out.println("\ndeleteMiddleUsingSlowFastPointers(head) => ");
        deleteMiddleUsingSlowFastPointers(head);
        for (ListNode trav = head; trav != null; trav = trav.next) {System.out.print(trav.val + " ");}
    }


    public static ListNode deleteMiddleUsingSize(ListNode head) {
        if(head.next == null) return null;
        ListNode trav = head;
        int n=0;
        for(; trav!=null; trav=trav.next) n++;

        int mid = n/2;
        for(trav=head; mid>1; trav=trav.next, mid--) {}
        trav.next = trav.next.next;
        return head;
    }

    public static ListNode deleteMiddleUsingThreePointers(ListNode head) {
        if (head == null || head.next == null) return null;

        ListNode slow = head, fast = head, prev = null;

        while (fast != null && fast.next != null) {
            prev = slow; // Keep track of the previous node
            slow = slow.next; // Move slow pointer one step
            fast = fast.next.next; // Move fast pointer two steps
        }

        prev.next = slow.next;
        return head;
    }




    public static ListNode deleteMiddleUsingSlowFastPointers(ListNode head) {
        if(head.next == null) return null;
        ListNode s=head, f=head.next.next;
        for(; f!=null && f.next!=null; s=s.next,f=f.next.next) {}
        // or while (f != null && f.next != null) {s = s.next; f = f.next.next;}
        s.next=s.next.next;
        return head;
    }

    public static ListNode deleteMiddleUsingSlowFastPointers2(ListNode head) {

        if(head == null || head.next == null) return null;

        ListNode dummyHead = new ListNode(0, head);
        ListNode slow = dummyHead;
        ListNode fast = head;

        while(fast != null && fast.next != null){
            slow = slow.next;
            fast = fast.next.next;
        }

        slow.next = slow.next.next;

        return head;
    }
}
