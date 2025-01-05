package Algorithms.LinkedListAlgos;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

/**
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 05 Jan 2025
 */
public class IntersectionOfTwoLinkedLists {
    private static class ListNode{int val;ListNode next; ListNode(int val){this.val = val;} ListNode(int val, ListNode next){this.val = val;this.next = next;}}

    public static void main(String[] args) {
        ListNode headA = new ListNode(4, new ListNode(1, new ListNode(8, new ListNode(4, new ListNode(5)))));
        // ListNode headB = new ListNode(5, new ListNode(6, new ListNode(1, headA.next.next)));
        ListNode headB = new ListNode(5, new ListNode(6, new ListNode(1)));
        System.out.println("getIntersectionNodeUsingHashSet: " + Optional.ofNullable(new IntersectionOfTwoLinkedLists().getIntersectionNodeUsingHashSet(headA, headB)).map(e->e.val).orElse(null));
        System.out.println("getIntersectionNode: " + Optional.ofNullable(new IntersectionOfTwoLinkedLists().getIntersectionNode(headA, headB)).map(e->e.val).orElse(null));

    }

    /**
     * Loops until a==b and if connected it'll return connected node or null as a and b == null once the loop completes O(m+n) iterations
     * Just like cyclic linked list
     *
     * @TimeComplexity: O(m+n)
     * @SpaceComplexity: O(1)
     */
    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        ListNode a = headA, b = headB;
        while(a != b){
            a = a == null ? headB : a.next;
            b = b == null ? headA : b.next;
        }
        return a;
    }

    // Both HashSet and HashMap .add() and get() are O(1) time complexity.Note that HashSet don't have indices. And if the element is not present then .add() returns false and .get() returns null
    public ListNode getIntersectionNodeUsingHashSet(ListNode headA, ListNode headB) {
        Set<ListNode> set = new HashSet<>();
        for(ListNode trav=headA; trav!=null; trav=trav.next) {
            set.add(trav);
        }

        for(ListNode trav=headB; trav!=null; trav=trav.next) {
            if(!set.add(trav))
                return trav;
        }
        return null;
    }

    public ListNode getIntersectionNodeUsingHashMap(ListNode headA, ListNode headB) {
        Map<ListNode, Boolean> map = new HashMap<>();
        for(ListNode trav=headA; trav!=null; trav=trav.next) {
            map.put(trav, true);
        }

        for(ListNode trav=headB; trav!=null; trav=trav.next) {
            if(map.containsKey(trav))
                return trav;
        }
        return null;
    }

    public ListNode getIntersectionNodeUsingCount(ListNode headA, ListNode headB) {
        int ac = 0;
        int bc = 0;
        ListNode a = headA;
        ListNode b = headB;
        while(a != null){
            ac++;
            a = a.next;
        }
        while(b != null){
            bc++;
            b = b.next;
        }
        while(ac > bc){
            ac--;
            headA = headA.next;
        }
        while(bc > ac){
            bc--;
            headB = headB.next;
        }

        while(headA != headB){
            headA = headA.next;
            headB = headB.next;
        }
        return headA;
    }
}
