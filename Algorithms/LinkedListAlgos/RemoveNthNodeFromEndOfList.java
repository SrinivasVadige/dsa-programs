package Algorithms.LinkedListAlgos;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Given the head of a linked list and an integer n, remove the nth node from the end of the list and return its head.
 * i.e., remove node from the right side and did not know the size of the LinkedList
 * One pass means only for loop
 * In-place means no extra space
 *
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 11 Nov 2024
 * @link 19. Remove Nth Node From End of List <a href="https://leetcode.com/problems/remove-nth-node-from-end-of-list/">LeetCode Link</a>
 * @topics Linked List, Two Pointers
 */
public class RemoveNthNodeFromEndOfList {
    public static class ListNode {
        int val;
        ListNode next;
        ListNode() {}
        ListNode(int val) { this.val = val; }
        ListNode(int val, ListNode next) { this.val = val; this.next = next; }
    }

    public static void main(String[] args) {
        ListNode head;
        int n = 2;

        head = new ListNode(1, new ListNode(2, new ListNode(3, new ListNode(4, new ListNode(5)))));
        System.out.println("Removed Node using two pointers - one pass, no memory => " + getNodeVal(removeNthFromEndUsingTwoPointers(head, n)));

        head = new ListNode(1, new ListNode(2, new ListNode(3, new ListNode(4, new ListNode(5)))));
        System.out.println("Removed Node using two pass, no memory => " + getNodeVal(removeNthFromEndUsingTwoPassNoMemory(head, n)));

        head = new ListNode(1, new ListNode(2, new ListNode(3, new ListNode(4, new ListNode(5)))));
        System.out.println("Removed Node using list => " + getNodeVal(removeNthFromEndUsingList(head, n)));
    }

    private static String getNodeVal (ListNode node) {return Optional.ofNullable(node).map(x -> x.val + "").orElse("null");}


    /**
     * @TimeComplexity O(n)
     * @SpaceComplexity O(1)
     * one pass, no memory

        1 -> 2 -> 3 -> 4 -> 5
        n = 2;        ⬆️

        dummy -> 1 -> 2 -> 3 -> 4 -> 5 -> null
           l     r

        dummy -> 1 -> 2 -> 3 -> 4 -> 5 -> null
           l               r

        // NOTE: Now trav both "l" & "r", till "r" == null

        dummy -> 1 -> 2 -> 3 -> 4 -> 5 -> null
                           l               r

        just skip the l's next node

     */
    public static ListNode removeNthFromEndUsingTwoPointers(ListNode head, int n) {
        ListNode dummy = new ListNode(0, head);
        ListNode l = dummy;
        ListNode r = head;

        for (; n > 0; n--) {
            r = r.next;
        }

        /* NOTE: Now, the distance between l and r is "n+1" */

        while (r != null) {
            l = l.next;
            r = r.next;
        }

        l.next = l.next.next;
        return dummy.next;
    }


    /**
     * @TimeComplexity O(n)
     * @SpaceComplexity O(1)
     */
    public static ListNode removeNthFromEndUsingTwoPointers2(ListNode head, int n) {
        ListNode slow = head;
        ListNode fast = head;

        for(int i=0; i<n; i++) { // skip uto n
            fast = fast.next;
        }

        if(fast == null) return head.next;

        while(fast.next != null) { // i.e (length - n) node for slow pointer
            slow = slow.next;
            fast = fast.next;
        }

        slow.next = slow.next.next;

        return head;
    }






    /**
     * @TimeComplexity O(n)
     * @SpaceComplexity O(1)
     */
    public static ListNode removeNthFromEndUsingTwoPassNoMemory(ListNode head, int n) {
        // FIRST PASS / LOOP
        int i = getLength(head) - n; // removeNode index or distance from removeNode
        if (i==0) return head.next;

        // SECOND PASS / LOOP
        ListNode trav = head;
        for (; trav != null && i > 1; trav = trav.next) {
            i--;
        }

        if (trav!=null && trav.next != null) trav.next = trav.next.next; // i==1 -> preNode - node before removeNode

        return head;
    }



    public static ListNode removeNthFromEndUsingTwoPassNoMemory2(ListNode head, int n) {
        int i = getLength(head) - n; // removeNode index or distance from removeNode
        if (i==0) return head.next;

        for (ListNode trav = head; trav != null && i > 0; trav = trav.next, i--) {
            if (i == 1) trav.next = trav.next.next; // preNode - node before removeNode
        }

        return head;
    }



    private static int getLength(ListNode node) {
        int len = 0;
        for (ListNode trav = node; trav != null; trav = trav.next) len++;
        return len;
    }








    /**
     * @TimeComplexity O(n)
     * @SpaceComplexity O(n)
     * One pass with O(n) space
     */
    public static ListNode removeNthFromEndUsingList(ListNode head, int n) {
        List<ListNode> list = new ArrayList<>();
        for (ListNode trav = head; trav != null; trav = trav.next) {
            list.add(trav);
        }

        int i = list.size() - n - 1; // removeIndex - 1 === preRemoveNodeIndex
        if (i == -1) return list.get(0).next;
        list.get(i).next = list.get(i).next.next;
        return head;
    }










    /**
     * @TimeComplexity O(n)
     * @SpaceComplexity O(1)
     * Two pass
     */
    public static ListNode removeNthFromEndMyApproachOld(ListNode head, int n) {
        if (head.next == null && n==1) return null;
        ListNode sp=head, fp=head; // slow pointer and fast pointer
        int spIndex = 0;
        int len = 0;
        while(fp != null) { // we can't use fp and sp in the same while loop cause we might remove 0th index or index which was already crossed by sp
            if (fp.next != null){
                fp = fp.next.next;
                len+=2;
            } else {
                len++;
                fp = fp.next;
            }
        }
        System.out.println("len: " + len);


        if (len == n) { // 0th index
            return head.next;
        }

        while(sp != null) {
            System.out.println("spIndex: " + spIndex);
            System.out.println("logic: " + (len - spIndex -1));

            if(len - spIndex -1 == n){
                remove(sp);
                break;
            }
            sp = sp.next;
            spIndex++;
        }
        return head;
    }

    private static void remove(ListNode node) {
        if(node.next != null) node.next = node.next.next;
    }
}
