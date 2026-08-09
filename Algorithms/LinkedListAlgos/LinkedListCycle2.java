package Algorithms.LinkedListAlgos;

import java.util.HashMap;
import java.util.Map;

/**
 * Return the node where the cycle begins. If there is no cycle, return null
 *
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 08 Dec 2024
 */
public class LinkedListCycle2 {
    private static class ListNode{int val;ListNode next; ListNode(int val){this.val = val;}}

    public static void main(String[] args) {
        ListNode head = new ListNode(3);
        head.next = new ListNode(2);
        head.next.next = new ListNode(0);
        head.next.next.next = new ListNode(-4);
        head.next.next.next.next = head.next;
        System.out.println("\ndetectCycleUsingHashMap: " + detectCycleUsingHashMap(head).val);
        System.out.println("detectCycle: " + detectCycle(head).val);

    }

    public static ListNode detectCycleUsingHashMap(ListNode head) { // similarly we can also use HashSet as set.add() -> returns false is already present
        Map<ListNode, Boolean> map = new HashMap<>();
        for(ListNode trav = head; trav !=null; trav=trav.next){
            if (map.containsKey(trav)) return trav;
            map.put(trav, true);
        }
        return null;
    }

    /**
     *     <--- l1 --><---- l2 ------>
     *   entry      e==s           s==f
     *      1 -> 2 -> 3 -> 4 -> 5 -> 6 -> 7
     *                ↑___________________|
     *
     * when s == f
     * slow pointer traveled distance sd = l1 + l2
     * fast pointer traveled distance fd = l1 + l2 + extra distance = l1 + l2 + cl -- where l is length original list and c is some constant
     * and we know that fd = 2*sd;
     * l1 + l2 + cl = 2*(l1 + l2)
     * cl = l1 + l2
     * l1 = cl - l2
     *
     * Basically it's some mathematical calculation --> l1 = l+1 - (l1+l2)
     */
    public static ListNode detectCycle(ListNode head) {
        ListNode s = head, f=head;
        while(f != null && f.next != null) {
            s = s.next;
            f = f.next.next;

            if (s == f) break; // s & f collided
        }
        if (s != f) return null; // or if (f == null || (f != null && f.next == null)) return null;

        ListNode entry = head;
        while(entry != s) { // will collide again at entry == s
            s = s.next;
            entry = entry.next;
        }

        return entry;
    }
}
