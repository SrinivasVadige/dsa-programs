package Algorithms.LinkedListAlgos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 26 Nov 2024
 * @link 141. Linked List Cycle <a href="https://leetcode.com/problems/linked-list-cycle">LeetCode link</a>
 * @topics Linked List, Two Pointers, Hash Table
 */
public class LinkedListCycle {
    public static class ListNode{int val;ListNode next; ListNode(int val){this.val = val;}}

    public static void main(String[] args) {
        ListNode head = new ListNode(3);
        head.next = new ListNode(2);
        head.next.next = new ListNode(0);
        head.next.next.next = new ListNode(-4);
        head.next.next.next.next = head.next;
        System.out.println("hasCycleUsing2PointersWhileLoop: " + hasCycleUsing2PointersWhileLoop(head));
        System.out.println("hasCycleUsing2PointersForLoop: " + hasCycleUsing2PointersForLoop(head));
        System.out.println("hasCycleUsingIntegerMaxValue: " + hasCycleUsingIntegerMaxValue(head));

    }

    /**
     * At some point, the fast pointer will definitely meet the slow pointer if it's a cyclic
     */
    public static boolean hasCycleUsing2PointersForLoop(ListNode head) {
        if(head == null) return false;
        for(ListNode s=head, f=head.next; s!=null && f!=null && f.next!=null; s=s.next, f=f.next.next) {
            if(s==f) return true; // or s==f is faster than s.equals(f) as == compares the references
        }
        return false;
    }

    public static boolean hasCycleUsing2PointersWhileLoop(ListNode head) {
        ListNode s=head, f=head;
        while(f!=null && f.next!=null) {
            s=s.next;
            f=f.next.next;
            if(s==f) return true; // or s==f is faster than s.equals(f) as == compares the references
        }
        return false;
    }

    public static boolean hasCycleUsingIntegerMaxValue(ListNode head) {
        for (ListNode trav = head; trav != null; trav = trav.next) {
            if (trav.val == Integer.MAX_VALUE) return true;
            trav.val=Integer.MAX_VALUE;
        }
        return false;
    }

    public static boolean hasCycleUsingSet(ListNode head) {
        Set<ListNode> set = new HashSet<>();
        while (head != null) {
            if (!set.add(head)) return true;
            head = head.next;
        }

        return false;
    }

    public static boolean hasCycleUsingHashMap(ListNode head) {
        Map<ListNode, Boolean> map = new HashMap<>();
        for(ListNode trav = head; trav != null; trav = trav.next) {
            if(map.containsKey(trav)) return true;
            map.put(trav, true);
        }
        return false;
    }

    public static boolean hasCycleUsingList(ListNode head) {
        List<ListNode> lst = new ArrayList<>();
        for(ListNode trav = head; trav != null; trav = trav.next) {
            if(lst.contains(trav)) return true;
            lst.add(trav);
        }
        return false;
    }
}
