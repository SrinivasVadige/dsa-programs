package Algorithms.LinkedListAlgos;

import java.util.ArrayList;
import java.util.List;

/**
 * @see ZipperLinkedLists.java
 * @author Srinvas Vadige, srinivas.vadige@gmail.com
 * @since 13 Oct 2024
 */
public class SwapNodesInPairs {
    public static class ListNode {
        public int val;
        public ListNode next;
        public ListNode() {}
        public ListNode(int val) { this.val = val; }
        public ListNode(int val, ListNode next) { this.val = val; this.next = next; }
    }

    public static void main(String[] args) {
        ListNode head = new ListNode(1, new ListNode(2, new ListNode(3, new ListNode(4, new ListNode(5)))));
        ListNode headBackup = new ListNode(1, new ListNode(2, new ListNode(3, new ListNode(4, new ListNode(5)))));

        System.out.print("GivenLinkedList: ");
        for (ListNode node = head; node != null; node = node.next)
            System.out.print(node.val + " ");
        System.out.println();

        ListNode res = swapPairsUsingPrevAndCurr(head, headBackup);
        System.out.print("swapPairsUsingPrevAndCurr: ");
        for (ListNode node = res; node != null; node = node.next)
            System.out.print(node.val + " ");
        System.out.println();

        head = new ListNode(1, new ListNode(2, new ListNode(3, new ListNode(4, new ListNode(5)))));
        headBackup = new ListNode(1, new ListNode(2, new ListNode(3, new ListNode(4, new ListNode(5)))));
        res = swapPairsUsingPrev(head, headBackup);
        System.out.print("swapPairsUsingPrev: ");
        for (ListNode node = res; node != null; node = node.next)
            System.out.print(node.val + " ");
        System.out.println();
    }

    /**
     * total of 2 pointers -> prev, curr
     * and extra 2 block variables -> nextPairStart, second
     *
     * @TimeComplexity O(n)
     * @SpaceComplexity O(1)
     */
    public static ListNode swapPairsUsingPrevAndCurr(ListNode head, ListNode headBackup) {
        ListNode dummy = new ListNode(-1, head);
        ListNode prev = dummy, curr = head; // or curr = dummy.next
        // dummy=prev=[-1, 1, 2, 3, 4, 5]  and
        // curr=head=dummy.next==prev.next=[1, 2, 3, 4, 5] --- curr ref linked to prev, dummy and head
        // prev always carries the previous element of curr

        while (curr != null && curr.next != null) {
            // save pointers
            ListNode nextPairStart = curr.next.next; // nextP=[3, 4, 5] --- nextP ref linked to curr, prev & head
            ListNode second = curr.next; // sec=[2, 3, 4, 5] -- sec ref linked to curr, prev and head
            // curr == first

            // Manipulation Step 1: Make [2,1...] in "second" var but we get self-ref after this current pair
            second.next = curr; // i.e curr.next.next=curr -> self-ref i.e CircularLinkedList at node val "1" but will change curr.next val to avoid it.
            // sec=[2, |1, "2", 1, "2", 1, "2", 1,.........|]
            // curr=[1, |sec|] = [1, |2, 1, "2", 1, "2", 1, "2", 1,.........|]
            // prev=dummy=[-1,|curr|] = [-1, |1, 2, 1, "2", 1, "2", 1, "2", 1,.........|]  --  i.e CircularLinkedList or infinite SingleLinkedList as "sec" manipulates "curr", "prev", "dummy"and "head"

            // Manipulation Step 2: Remove self-ref in "second" var i.e Skip 2nd head node i.e "2" in "curr" variable --
            curr.next = nextPairStart; // lost node "2" ref here in curr, prev & head -- and we still have sec and curr ref from above second.next = curr;
            // or second.next.next = nextPairStart
            // curr=[1, |3, 4, 5|]
            // sec=[2, 1, |3, 4, 5|]
            // prev=dummy=[-1, 1, |3, 4, 5|] --- i.e curr manipulates "sec", "prev" & "dummy"

            // Manipulation Step 3: Again make [2,1,...] in "prev" var with second var
            prev.next = second; // no more self refs except
            //prev=dummy=[-1, 2, 1, 3, 4, 5], on first loop prev==dummy --- i.e prev manipulates "dummy" and head

            // advance pointers
            prev = curr; // prev=curr=[1, 3, 4, 5]
            //* prev = second.next
            curr = nextPairStart; // curr=nextP=[3, 4, 5]
            //* curr = second.next.next
            // finally prev.next=curr=[3, 4, 5]
        }

        // for easy understanding of the above code 🔥
        head = headBackup;
        dummy = new ListNode(-1, head);
        prev = dummy;
        curr = head;
        while (curr != null && curr.next != null) {
            // save pointers ------------
            ListNode nextPairStart = curr.next.next; // [3, 4, 5]
            // curr == first
            ListNode second = curr.next; // [2, 3, 4, 5]

            // reverse pairs ------------
            second.next = curr; // [2,1,...] self-referential
            second.next.next = nextPairStart; // [2,1,3,4,5]
            prev.next = second; // update prev using second

            // advance pointers ------------
            prev = second.next; // [1,3,4,5]
            curr = second.next.next; // [3,4,5]
        }

        return dummy.next;
    }


    /**
     * 2 pointers -> prev, head
     * and extra 2 block variables -> first, second
     *
     * @see #swapPairsUsingPrevAndCurr
     * @TimeComplexity O(n)
     * @SpaceComplexity O(1)
     */
    public static ListNode swapPairsUsingPrev(ListNode head, ListNode headBackup) {
        // head=[1,2,3,4,5]
        ListNode dummy = new ListNode(0, head);
        ListNode prev = dummy; // p=d=[0,|head|]=[0,1,2,3,4,5] up to here -- head, dummy and prev refs are linked
        while (head != null && head.next != null) {
            ListNode first = head; // f=[1,2,3,4,5]
            ListNode second = head.next; // s=[2,3,4,5]

            // Manipulation Step 1: Skip 1st head node i.e dummy's or prev's 2nd node
            prev.next = second; // p=d=[0,|s|]=[0,2,3,4,5] -- s,p,d,head are now liked

            // Manipulation Step 2: Skip 2nd head node in "first" variable
            first.next = second.next; // -- f,s,p,d,head are now linked
            // same as head.next=head.next.next; -- removed 2nd node
            //f=[1,|s.next|]=[1,3,4,5] d=[0,1,|s.next|]=[0,1,3,4,5]

            // Manipulation Step 3: Now add "1" after "2" Using prev-second reference i.e prev.next.next
            second.next = first; // s=[2,|f|]=[2,1,3,4,5]
            // same as head.next.next=p.next.next=d.next.next=f; p=d=[0,2,|f|]=[0,2,1,3,4,5]
            // same as first.next=first;

            prev = first; // p=f=[1,3,4,5] -- p lost s,d & head refs but again gained f, s, head, dummy refs
            // d=[0,2,|f|]=[0,2,|p|]
            head = first.next; // head=[3,4,5] p=[1]
            // d=[0,2,|f|]=[0,2,|p|]=[0,2,1,|f.next|]=[0,2,1,|head|]
        }

        // for easy understanding of the above code
        head = headBackup;
        dummy = new ListNode(0, head);
        prev = dummy;
        while (head != null && head.next != null) {
            ListNode first = head;
            ListNode second = head.next;
            prev.next = second;
            first.next = second.next;
            second.next = first;
            prev = first;
            head = first.next;
        }
        return dummy.next;
    }


    public static ListNode swapPairsBySwappingValues(ListNode head) {
        for(ListNode trav = head; trav!=null&&trav.next!=null; trav=trav.next.next) {
           int next = trav.next.val;
            trav.next.val = trav.val;
            trav.val = next;
        }
        return head;
    }

    public static ListNode swapPairsUsingList(ListNode head) {
        if(head == null || (head !=null && head.next == null)) return head;
        List<Integer> lst = new ArrayList<>();
        ListNode trav;
        for(trav = head; trav!=null&&trav.next!=null; trav=trav.next.next){
            lst.add(trav.next.val);
            lst.add(trav.val);
        }
        if(trav!=null) lst.add(trav.val);
        System.out.println(lst);

        ListNode dummy = new ListNode(-1);
        trav = dummy;

        for(Integer n: lst){
            trav.next = new ListNode(n);
            trav = trav.next;
        }

        return dummy.next;
    }
}
