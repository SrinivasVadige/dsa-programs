package Algorithms.LinkedListAlgos;

import java.util.ArrayList;
import java.util.List;

/**
 * Given the head of a singly linked list, return the middle node of the linked list.
 * If there are two middle nodes, return the second middle node.
 *
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 09 Nov 2024
 */
public class MiddleOfTheLinkedList {

    @SuppressWarnings("unused")
    private static class ListNode {
        int val;
        ListNode next;
        ListNode() {}
        ListNode(int val) { this.val = val; }
        ListNode(int val, ListNode next) { this.val = val; this.next = next;}
    }

    public static void main(String[] args) {
        ListNode head = new ListNode(1, new ListNode(2, new ListNode(3, new ListNode(4, new ListNode(5)))));
        System.out.println(middleNode(head).val);
    }

    public static ListNode middleNode(ListNode head) {
        ListNode fastPtr = head;
        ListNode slowPtr = head;
        while(fastPtr!=null && fastPtr.next!=null){
            fastPtr = fastPtr.next.next;
            slowPtr = slowPtr.next;
        }
        return slowPtr;
    }
    public static ListNode middleNode2(ListNode head) {
        List<ListNode> list = new ArrayList<>();
        for(ListNode trav = head; trav!=null; trav=trav.next) {
            list.add(trav);
        }
        return list.get(list.size()>>1);
    }
}
