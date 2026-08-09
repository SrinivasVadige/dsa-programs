package Algorithms.LinkedListAlgos;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * HashSet won't work as we check for palindrome not the anagram
 * But we can also use List or Array and use start and end pointers
 *
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 05 Jan 2025
 */
public class PalindromeLinkedList {
    static class ListNode {int val; ListNode next; ListNode(int val){this.val=val;} ListNode(int val, ListNode next){this.val=val; this.next=next;}}

    public static void main(String[] args) {
        ListNode head = new ListNode(1, new ListNode(2, new ListNode(2, new ListNode(1))));
        System.out.println("\nisPalindromeUsingReverseList My Approach: " + isPalindromeUsingReverseListMyApproach(head));
    }

    public static boolean isPalindromeUsingReverseList(ListNode head) {
        if (head == null || head.next==null ) return true;
        ListNode slow = head, fast = head, prev, temp;

        // find middle
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }

        // reverse the second half
        prev = slow;
        slow = slow.next;
        prev.next = null;
        while (slow != null) {
            temp = slow.next;
            slow.next = prev;
            prev = slow;
            slow = temp;
        }

        // connect the two halves
        fast = head;
        slow = prev;

        // compare
        while (slow != null) {
            if (fast.val != slow.val) return false;
            fast = fast.next;
            slow = slow.next;
        }
        return true;
    }


    /**
     * It's working fine with RunTime as 5ms but we can improve it more with above approach
     * @TimeComplexity O(n)
     * @SpaceComplexity O(1)
     */
    public static boolean isPalindromeUsingReverseListMyApproach(ListNode head) {
        if (head == null || head.next==null ) return true;
        if (head.next.next==null) {
            return head.val == head.next.val;
        }
        ListNode left = head;
        ListNode right = getMiddleNode(head);

        // reverse left node --- or we can also reverse the right node without breaking connection
        ListNode prev = null;
        ListNode curr = left;
        while(curr!=null){
            ListNode next = curr.next;
            curr.next = prev;
            prev = curr;
            curr = next;
        }
        left=prev;

        System.out.println("left: ");
        for(ListNode t=left; t!=null; t=t.next) System.out.print(t.val + " ");
        System.out.println("\nright: ");
        for(ListNode t=right; t!=null; t=t.next) System.out.print(t.val + " ");
        System.out.println();

        for(ListNode lTrav=left, rTrav=right; lTrav!=null&&rTrav!=null; lTrav=lTrav.next,rTrav=rTrav.next) {
            if (lTrav.val != rTrav.val) return false;
        }

        return true;
    }

    private static ListNode getMiddleNode(ListNode node) {
        ListNode s=node, f=node;
        while(f!=null && f.next!=null) {
            s=s.next;
            f=f.next.next;
        }
        // or for(s=f=node; f!=null && f.next!=null; f=f.next.next, s=s.next){}

        // break connection
        ListNode trav = node;
        while(trav.next!=s) trav=trav.next;
        trav.next = null;

        return f==null?s:s.next; // f==null means even
    }

    /**
     * @TimeComplexity O(n)
     * @SpaceComplexity O(n) -- but we need only O(1)
    */
    public static boolean isPalindromeUsingList(ListNode head) {
        if (head == null || head.next==null ) return true;

        List<Integer> lst = new ArrayList<>();

        for(ListNode trav = head; trav!=null; trav=trav.next) lst.add(trav.val);

        for(int i=0; i<lst.size()/2; i++) {
            if (lst.get(i) != lst.get(lst.size()-1-i)) return false;
        }

        return true;
    }

    /**
     * @TimeComplexity O(n)
     * @SpaceComplexity O(n) -- but we need only O(1)
    */
    public static boolean isPalindromeUsingStack(ListNode head) {
        Stack<Integer> stack = new Stack<>();

        for(ListNode trav = head; trav!=null; trav=trav.next) stack.push(trav.val);

        int popped;
        while(head!=null && !stack.empty()){
            popped=stack.pop();
            if(head.val!=popped) return false;
            head=head.next;
        }
        return true;
    }
}
