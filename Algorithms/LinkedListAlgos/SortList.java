package Algorithms.LinkedListAlgos;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 04 Jan 2025
 */
public class SortList {

    static class ListNode {int val; ListNode next; ListNode(int val){this.val=val;} ListNode(int val, ListNode next){this.val=val; this.next=next;}}

    public static void main(String[] args) {
        ListNode head = new ListNode(4, new ListNode(2, new ListNode(1, new ListNode(5, new ListNode(3)))));
        System.out.println("sortListUsingListSort: ");
        for (ListNode trav = sortListUsingListSort(head); trav != null; trav = trav.next) System.out.print(trav.val + " ");
        head = new ListNode(4, new ListNode(2, new ListNode(1, new ListNode(5, new ListNode(3)))));
        System.out.println("\nsortList: ");
        for (ListNode trav = sortList(head); trav != null; trav = trav.next) System.out.print(trav.val + " ");

    }

    public static ListNode sortListUsingListSort(ListNode head) {
        List<Integer> lst = new ArrayList<>();
        for(ListNode trav = head; trav !=null; trav = trav.next) lst.add(trav.val);
        Collections.sort(lst);
        int i =0;
        for(ListNode trav = head; trav!=null; trav=trav.next, i++) trav.val = lst.get(i);

        return head;
    }

    public static ListNode sortList(ListNode head) {
        if (head == null || head.next == null) return head;

        ListNode left = head;
        ListNode middle = getMiddle(head);
        ListNode right = middle.next;

        // break l & r connection
        middle.next = null;

        // rec to break into single node pieces and merge below
        left = sortList(left);
        right = sortList(right);

        // merge
        return mergeLists(left, right);
    }

    static ListNode getMiddle(ListNode node) {
        ListNode s = node, f = node.next.next; // for {3,4} list return 3 as middle
        while (f != null && f.next != null) {
            s = s.next;
            f = f.next.next;
        }
        return s;
    }

    static ListNode mergeLists(ListNode l, ListNode r) {
        ListNode dummy = new ListNode(-1), trav = dummy;
        while (l != null && r != null) {
            if(l.val < r.val) {
                trav.next = l;
                l = l.next;
            } else {
                trav.next = r;
                r = r.next;
            }
            trav = trav.next;
        }

        if (l != null) trav.next = l;
        if (r != null) trav.next = r;

        return dummy.next;
    }
}
