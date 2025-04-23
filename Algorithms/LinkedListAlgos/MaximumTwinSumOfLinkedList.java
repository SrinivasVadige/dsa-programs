package Algorithms.LinkedListAlgos;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 23 April 2025
 *
 *         0   1   2   3   4   5   6   7   null
 *
 * (n-1-i)th is the twin of ith node
 * if n=8
 * if i=0, twin=(8-1-0)=7 -- i0 & i7
 * if i=1, twin=(8-1-1)=6 -- i1 & i6
 * if i=2, twin=(8-1-2)=5 -- i2 & i5
 * if i=3, twin=(8-1-3)=4 -- i3 & i4
 *
 *
 */
public class MaximumTwinSumOfLinkedList {
    static class ListNode{int val; ListNode next; ListNode(int x) { val = x; } ListNode(int x, ListNode next) { val = x; this.next = next; }}
    public static void main(String[] args) {
        ListNode head = new ListNode(5, new ListNode(4, new ListNode(2, new ListNode(1))));
        System.out.println( "pairSum(head) => " + pairSum(head)); // Output: 6 (5 + 1)
    }






    public int pairSumUsingLst(ListNode head) {
        if(head.next.next==null) return head.val + head.next.val;
        ListNode s = head, f = head;
        List<Integer> lst = new ArrayList<>();
        while(f!=null) {
            lst.add(s.val);
            s=s.next;
            f=f.next.next;
        }
        int max = 0;
        for(int i=lst.size()-1; s!=null; i--, s=s.next) {
            // System.out.printf("lst num: %s, s.val: %s \n", lst.get(i), s.val);
            max=Math.max(max, (lst.get(i)+s.val));
        }
        return max;
    }





    public int pairSumMyApproach(ListNode head) {
        if(head.next.next==null) return head.val + head.next.val;
        ListNode s = head, f = head;
        while(f.next.next!=null) {
            s=s.next;
            f=f.next.next;
        }
        ListNode temp = s.next;
        s.next = null; // break connection
        s=temp;


        // reverse head
        ListNode prev = null, curr = head;
        while(curr != null) {
            ListNode next = curr.next;
            curr.next = prev;
            prev = curr;
            curr = next;
        }
        head = prev;

        // head is now reversed
        int max = 0;
        while(s!=null) {
            max = Math.max(max, head.val+s.val);
            s=s.next;
            head=head.next;
        }
        return max;
    }





    public static int pairSum(ListNode head) {
        ListNode prev = null;
        ListNode s = head;
        ListNode f = head;

        // combination of slow, fast pointer and reverse linked list
        while (f != null && f.next != null) {
            f = f.next.next;

            ListNode next = s.next;
            s.next = prev;
            prev = s;
            s = next;
        }

        int max = 0;
        // now prev is in reverse order
        while (s != null) {
            max = Math.max(max, s.val + prev.val);
            s = s.next;
            prev = prev.next;
        }

        return max;
    }





    public static int pairSum2(ListNode head) {
        ListNode slow = head;
        ListNode fast = head;
        while(fast!=null && fast.next!=null){
            slow = slow.next;
            fast = fast.next.next;
        }
        ListNode SecondHalf = reverse(slow);
        int maxSum=0;
        ListNode first = head,second = SecondHalf;
        while(second!=null){
            int twinSum = first.val + second.val;
            maxSum = Math.max(twinSum,maxSum);
            first =first.next;
            second = second.next;
        }
        return maxSum;
    }
    private static ListNode reverse(ListNode head){
        ListNode prev=null;
        ListNode curr = head;
        while(curr!=null){
            ListNode next = curr.next;
            curr.next=prev;
            prev = curr;
            curr = next;
        }
        return prev;
    }






    public static int pairSum3(ListNode head) {
        ListNode slow = head, fast = head;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        ListNode prev = null;
        while (slow != null) {
            ListNode next = slow.next;
            slow.next = prev;
            prev = slow;
            slow = next;
        }
        int maxSum = 0;
        while (prev != null) {
            maxSum = Math.max(maxSum, head.val + prev.val);
            head = head.next;
            prev = prev.next;
        }
        return maxSum;
    }






    public int pairSum4(ListNode head) {

        ListNode slow = head;
        ListNode fast = head;

        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }


        ListNode prev = null;
        ListNode current = slow;
        while (current != null) {
            ListNode nextTemp = current.next;
            current.next = prev;
            prev = current;
            current = nextTemp;
        }


        ListNode firstHalf = head;
        ListNode secondHalf = prev;
        int maxTwinSum = 0;

        while (secondHalf != null) {
            maxTwinSum = Math.max(maxTwinSum, firstHalf.val + secondHalf.val);
            firstHalf = firstHalf.next;
            secondHalf = secondHalf.next;
        }

        return maxTwinSum;
    }
}