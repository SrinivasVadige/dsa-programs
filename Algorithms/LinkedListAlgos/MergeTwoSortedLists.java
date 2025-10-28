package Algorithms.LinkedListAlgos;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Given the heads of two sorted linked lists list1 and list2, return the merge in sorted order.
 *
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 11 Nov 2024
 * @link 21. Merge Two Sorted Lists <a href="https://leetcode.com/problems/merge-two-sorted-lists/">LeetCode link</a>
 * @topics Linked List, Recursion
 */
public class MergeTwoSortedLists {

    public static class ListNode {
        int val;
        ListNode next;
        ListNode() {}
        ListNode(int val) { this.val = val; }
        ListNode(int val, ListNode next) { this.val = val; this.next = next;}
    }

    public static void main(String[] args) {
        ListNode list1 = new ListNode(1, new ListNode(2, new ListNode(4)));
        ListNode list2 = new ListNode(1, new ListNode(3, new ListNode(4)));

        System.out.println("mergeTwoLists: ");
        ListNode mergedList = mergeTwoLists(list1, list2);
        while (mergedList != null) {
            System.out.print(mergedList.val + " ");
            mergedList = mergedList.next;
        }

        System.out.println("\nmergeTwoLists Recursively: ");
        list1 = new ListNode(1, new ListNode(2, new ListNode(4)));
        list2 = new ListNode(1, new ListNode(3, new ListNode(4)));
        mergedList = mergeTwoListsRecursively(list1, list2);
        for (ListNode trav = mergedList; trav != null; trav = trav.next) {
            System.out.print(trav.val + " ");
        }



        System.out.println("\nmergeTwoLists Using Sort: ");
        list1 = new ListNode(1, new ListNode(2, new ListNode(4)));
        list2 = new ListNode(1, new ListNode(3, new ListNode(4)));
        mergedList = mergeTwoListsUsingSort(list1, list2);
        for (ListNode trav = mergedList; trav != null; trav = trav.next) {
            System.out.print(trav.val + " ");
        }
    }

    /**
     * @TimeComplexity O(m+n)
     * @SpaceComplexity O(1)
     */
    public static ListNode mergeTwoLists(ListNode list1, ListNode list2) {
        ListNode dummy = new ListNode(-1);
        // If we use dummy then, in while loop we can assign with (trav.next=list1 and trav=trav.next)
        // instead of (trav.val and trav.next = list1.next != null ? new ListNode() : null;)
        // so, we can avoid the edge of inserting the trav.next into an empty list new ListNode() if list1 is empty i.e null
        ListNode trav = dummy;
        while (list1 != null && list2 != null) {
            if (list1.val < list2.val) {
                trav.next = list1; // or trav.next = new ListNode(list1.val);
                // or trav.next = new ListNode(list1.val, list1.next != null ? new ListNode() : null);
                // and even though we assign total list1 values to our trav.next we'll change that trav.next.next values in next iteration
                list1 = list1.next;
            } else {
                trav.next = list2; // or trav.next = new ListNode(list2.val);
                list2 = list2.next;
            }
            trav = trav.next;
        }
        trav.next = list1 != null ? list1 : list2; // when one of the lists is empty
        return dummy.next;
    }





    public static ListNode mergeTwoListsRecursively(ListNode list1, ListNode list2) {
        if (list1 == null) return list2;
        if (list2 == null) return list1;
        if (list1.val < list2.val) {
            // now curr list1 node is the smallest node. So, use curr and calculate the next node
            list1.next = mergeTwoListsRecursively(list1.next, list2);
            return list1;
        } else {
            list2.next = mergeTwoListsRecursively(list1, list2.next);
            return list2;
        }
    }






    /**
     * @TimeComplexity O((m+n)long(m+n))
     * @SpaceComplexity O(m+n)
     */
    public static ListNode mergeTwoListsUsingSort(ListNode list1, ListNode list2) {

        List<Integer> lst = new ArrayList<>();

        for(ListNode trav=list1; trav!=null; trav=trav.next)
            lst.add(trav.val);
        for(ListNode trav=list2; trav!=null; trav=trav.next)
            lst.add(trav.val);

        if(lst.size()==0) return null;

        Collections.sort(lst);

        ListNode res = new ListNode();
        ListNode trav = res;
        for(int i=0; i<lst.size(); i++){
            trav.val = lst.get(i);

            if(i != lst.size()-1) {
                trav.next = new ListNode();
                trav = trav.next;
            }
        }

        // above loop can be written using dummy node approach 🔥
        // So we can avoid the edge of inserting the trav.next into an empty list null
        // i.e no need for trav.next = list1.next != null ? new ListNode() : null;
        ListNode head = new ListNode(lst.get(0));
        trav = head;
        for(int i=1; i<lst.size(); i++){
            trav.next = new ListNode(lst.get(i)); // i.e we avoided trav.next = new ListNode(lst.get(i), list1.next != null ? new ListNode() : null);
            trav = trav.next;
        }
        //return head;

        return res;
    }
}
