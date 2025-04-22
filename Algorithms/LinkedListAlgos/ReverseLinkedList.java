package Algorithms.LinkedListAlgos;

/**
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 17 Nov 2024
 */
class ReverseLinkedList {
    static class ListNode {int val; ListNode next; ListNode(int val){this.val=val;} ListNode(int val, ListNode next){this.val=val; this.next=next;}}

    public static void main(String[] args) {
        ListNode head = new ListNode(1, new ListNode(2, new ListNode(3, new ListNode(4, new ListNode(5)))));
        System.out.println("reverseListMyApproach: ");
        for (ListNode trav = reverseListMyApproach(head); trav != null; trav = trav.next) System.out.print(trav.val + " ");

        head = new ListNode(1, new ListNode(2, new ListNode(3, new ListNode(4, new ListNode(5)))));
        System.out.println("reverseListUsing3Pointers: ");
        for (ListNode trav = reverseListUsing3Pointers(head); trav != null; trav = trav.next) System.out.print(trav.val + " ");

        System.out.println("\nreverseListUsing2Pointers: ");
        head = new ListNode(1, new ListNode(2, new ListNode(3, new ListNode(4, new ListNode(5)))));
        for (ListNode trav = reverseListUsing2Pointers(head); trav != null; trav = trav.next) System.out.print(trav.val + " ");

        System.out.println("\nreverseListUsingRecursion: ");
        head = new ListNode(1, new ListNode(2, new ListNode(3, new ListNode(4, new ListNode(5)))));
        for (ListNode trav = reverseListUsingRecursion(head); trav != null; trav = trav.next) System.out.print(trav.val + " ");

        System.out.println("\nreverseList: ");
        head = new ListNode(1, new ListNode(2, new ListNode(3, new ListNode(4, new ListNode(5)))));
        for (ListNode trav = reverseList(head); trav != null; trav = trav.next) System.out.print(trav.val + " ");
    }

    /**
     *
     *             1 -> 2 -> 3 -> 4 -> 5
     *             5 -> 4 -> 3 -> 2 -> 1
     *
     *     null <- 1 -> 2 -> 3 -> 4 -> 5 -> null         -------- START
     *             ↓    ↓
     *            curr  next
     *
     *
     *     null <- 1 <- 2 <- 3 <- 4 <- 5  -> null        -------- END
     *                                 ↓      ↓
     *                                curr   next
     *
     *
     * So, we can return prev as head and return it     */
    public static ListNode reverseListMyApproach(ListNode head) {
        if(head==null) return head;
        ListNode curr = head, next = curr.next;
        curr.next = null;

        while(next != null) {
            ListNode temp = next.next; // temp hold the next sequence
            next.next = curr; // from -> to <-
            curr=next;
            next = temp;
        }
        return curr;
    }




    /**
     *
     *             1 -> 2 -> 3 -> 4 -> 5
     *             5 -> 4 -> 3 -> 2 -> 1
     *
     *     null -> 1 -> 2 -> 3 -> 4 -> 5 -> null         -------- START
     *      ↓      ↓    ↓
     *     prev  curr  next
     *
     *
     *     null <- 1 <- 2 <- 3 <- 4 <- 5 -> null         -------- END
     *                                 ↓      ↓     ↓
     *                                prev   curr  next
     *
     *
     * So, we can return prev as head and return it
     */
    public static ListNode reverseListUsing3Pointers(ListNode head) {
        ListNode prev = null;
        ListNode curr = head;
        while (curr != null) {
            ListNode next = curr.next; // temp hold the next sequence
            curr.next = prev; // from -> to <-
            prev = curr;
            curr = next;
        }
        return prev;
    }

    /**
     * same as
     * @see #reverseListUsing3Pointers but without curr and use head as curr instead
     *
     *     null -> 1 -> 2 -> 3 -> 4 -> 5 -> null         -------- START
     *      ↓      ↓    ↓
     *     prev  head  next
     *
     *
     *     null <- 1 <- 2 <- 3 <- 4 <- 5 -> null         -------- END
     *                                 ↓      ↓     ↓
     *                                prev   head  next
    */
    public static ListNode reverseListUsing2Pointers(ListNode head) {
        ListNode prev = null;
        while (head != null) {
            ListNode next = head.next; // temp hold the next sequence
            head.next = prev; // from -> to <-
            prev = head;
            head = next;
        }
        return prev;
    }

    /**
     * same as
     * @see #reverseListUsing2Pointers()
     */
    public static ListNode reverseListUsingRecursion(ListNode head) {
        return reverseListUsingRecursion(head, null);
    }
    public static ListNode reverseListUsingRecursion(ListNode curr, ListNode prev) {
        if (curr == null) return prev;
        ListNode next = curr.next; // temp hold the next sequence
        curr.next = prev; // from -> to <-
        prev = curr;
        return reverseListUsingRecursion(next, prev);
    }


    /**
     * same SortList Approach
     */
    public static ListNode reverseList(ListNode head) {
        if (head == null || head.next == null) return head;
        ListNode left = head;
        ListNode middle = getMiddleNode(head);
        ListNode right = middle.next;

        // to break l & r connection
        middle.next = null;

        left = reverseList(left);
        right = reverseList(right);

        return mergeLists(left, right);
    }

    private static ListNode getMiddleNode(ListNode node) {
        ListNode s=node, f = node; // initial f=node.next.next returns 3 for {3,4,5} list
        if (f!=null && f.next!=null && f.next.next==null) return f; // for {3,4} list return 3 as middle
        while (f!=null && f.next!=null) {
            s=s.next;
            f=f.next.next;
        }
        return s;
    }

    private static ListNode mergeLists(ListNode l, ListNode r) {
        ListNode trav = r;
        while(trav.next!=null) trav=trav.next; // loop to last and connect the right most of the rightSection and connect it to first node of leftSection
        trav.next=l;
        return r;
    }
}