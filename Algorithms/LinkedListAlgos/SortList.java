package Algorithms.LinkedListAlgos;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 04 Jan 2025
 * @link 148. Sort List <a href="https://leetcode.com/problems/sort-list/">LeetCode link</a>
 * @topics Linked List, Two Pointers, Divide and Conquer, Sorting, Merge Sort
 * @companies Google(3), Amazon(3), Bloomberg(3), Meta(3), Microsoft(2), TikTok(5), Lyft(2), Oracle(2), ByteDance(2)
 * @see Algorithms.LinkedListAlgos.MergeTwoSortedLists
 */
public class SortList {

    public static class ListNode {int val; ListNode next; ListNode(){} ListNode(int val){this.val=val;} ListNode(int val, ListNode next){this.val=val; this.next=next;}}

    public static void main(String[] args) {
        ListNode head = new ListNode(4, new ListNode(2, new ListNode(1, new ListNode(5, new ListNode(3)))));
        System.out.println("sortList Using List Sort: ");
        for (ListNode trav = sortListUsingListSort(head); trav != null; trav = trav.next) System.out.print(trav.val + " ");

        head = new ListNode(4, new ListNode(2, new ListNode(1, new ListNode(5, new ListNode(3)))));
        System.out.println("\nsortList Using Top Down Merge Sort: ");
        for (ListNode trav = sortListUsingTopDownMergeSort1(head); trav != null; trav = trav.next) System.out.print(trav.val + " ");

        head = new ListNode(4, new ListNode(2, new ListNode(1, new ListNode(5, new ListNode(3)))));
        System.out.println("\nsortList Using Bottom Up Merge Sort: ");
        for (ListNode trav = sortListUsingBottomUpMergeSort1(head); trav != null; trav = trav.next) System.out.print(trav.val + " ");

    }



    /**
     * @TimeComplexity O(n log n)
     * @SpaceComplexity O(n)
     */
    public static ListNode sortListUsingListSort(ListNode head) {
        List<Integer> lst = new ArrayList<>();
        for(ListNode trav = head; trav !=null; trav = trav.next) lst.add(trav.val);
        Collections.sort(lst);
        int i =0;
        for(ListNode trav = head; trav!=null; trav=trav.next, i++) trav.val = lst.get(i);

        return head;
    }





    /**
     * @TimeComplexity O(n^2)
     * @SpaceComplexity O(1)
    */
    private static ListNode sortListUsingDivideAndConquer1(ListNode head) {
        return divideHelper(head);
    }

    private static ListNode divideHelper(ListNode node) { // divide into little pieces
        if (node == null || node.next == null) return node;
        ListNode nextNode = divideHelper(node.next);
        return mergeHelper(node, nextNode);
    }

    private static ListNode mergeHelper(ListNode node, ListNode nextNode) { // combine those little pieces
        if (node.val <= nextNode.val) {
            node.next = nextNode;
            return node;
        } else {
            ListNode tempNextNode = nextNode;
            while (tempNextNode.next != null) {
                if (node.val <= tempNextNode.next.val) break;
                tempNextNode = tempNextNode.next;
            }
            node.next = tempNextNode.next;
            tempNextNode.next = node;
            return nextNode;
        }
    }






    /**
     * @TimeComplexity O(n log n)
     * @SpaceComplexity O(log n) for recursion stack
     */
    public static ListNode sortListUsingTopDownMergeSort1(ListNode head) {
        if (head == null || head.next == null) return head;

        ListNode left = head;
        ListNode middle = getMiddle(head);
        ListNode right = middle.next;

        // break l & r connection
        middle.next = null; // so left = head to mid and right = mid.next to end

        // rec to break into single node pieces and merge below
        left = sortListUsingTopDownMergeSort1(left);
        right = sortListUsingTopDownMergeSort1(right);

        // merge
        return mergeLists(left, right);
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
    static ListNode getMiddle(ListNode node) { // O(n) time
        ListNode s = node, f = node.next.next; // for {3,4} list return 3 as middle
        while (f != null && f.next != null) {
            s = s.next;
            f = f.next.next;
        }
        return s;
    }










    /**
     * @TimeComplexity O(n log n)
     * @SpaceComplexity O(log n) for recursion stack
     */
    public ListNode sortListUsingTopDownMergeSort2(ListNode head) {
        if (head == null || head.next == null) return head;
        ListNode mid = getMid(head);
        ListNode left = sortListUsingTopDownMergeSort2(head);
        ListNode right = sortListUsingTopDownMergeSort2(mid);
        return mergeTopDown(left, right);
    }

    ListNode mergeTopDown(ListNode list1, ListNode list2) {
        ListNode dummyHead = new ListNode();
        ListNode tail = dummyHead;
        while (list1 != null && list2 != null) {
            if (list1.val < list2.val) {
                tail.next = list1;
                list1 = list1.next;
                tail = tail.next;
            } else {
                tail.next = list2;
                list2 = list2.next;
                tail = tail.next;
            }
        }
        tail.next = (list1 != null) ? list1 : list2;
        return dummyHead.next;
    }

    ListNode getMid(ListNode head) {
        ListNode midPrev = null;
        while (head != null && head.next != null) {
            midPrev = (midPrev == null) ? head : midPrev.next;
            head = head.next.next;
        }
        ListNode mid = midPrev.next;
        midPrev.next = null;
        return mid;
    }













    static ListNode tail = new ListNode();
    static ListNode nextSubList = new ListNode();
    /**
     * @TimeComplexity O(n log n)
     * @SpaceComplexity O(1)
     */
    public static ListNode sortListUsingBottomUpMergeSort1(ListNode head) {
        if (head == null || head.next == null) return head;
        int n = getCount(head);
        ListNode start = head;
        ListNode dummyHead = new ListNode();
        for (int size = 1; size < n; size = size * 2) {
            tail = dummyHead;
            while (start != null) {
                if (start.next == null) {
                    tail.next = start;
                    break;
                }
                ListNode mid = split(start, size);
                mergeBottomUp(start, mid);
                start = nextSubList;
            }
            start = dummyHead.next;
        }
        return dummyHead.next;
    }

    static ListNode split(ListNode start, int size) {
        ListNode midPrev = start;
        ListNode end = start.next;
        //use fast and slow approach to find middle and end of second linked list
        for (
            int index = 1;
            index < size && (midPrev.next != null || end.next != null);
            index++
        ) {
            if (end.next != null) {
                end = (end.next.next != null) ? end.next.next : end.next;
            }
            if (midPrev.next != null) {
                midPrev = midPrev.next;
            }
        }
        ListNode mid = midPrev.next;
        midPrev.next = null;
        nextSubList = end.next;
        end.next = null;
        // return the start of second linked list
        return mid;
    }

    static void mergeBottomUp(ListNode list1, ListNode list2) {
        ListNode dummyHead = new ListNode();
        ListNode newTail = dummyHead;
        while (list1 != null && list2 != null) {
            if (list1.val < list2.val) {
                newTail.next = list1;
                list1 = list1.next;
                newTail = newTail.next;
            } else {
                newTail.next = list2;
                list2 = list2.next;
                newTail = newTail.next;
            }
        }
        newTail.next = (list1 != null) ? list1 : list2;
        // traverse till the end of merged list to get the newTail
        while (newTail.next != null) {
            newTail = newTail.next;
        }
        // link the old tail with the head of merged list
        tail.next = dummyHead.next;
        // update the old tail to the new tail of merged list
        tail = newTail;
    }

    static int getCount(ListNode head) {
        int cnt = 0;
        ListNode ptr = head;
        while (ptr != null) {
            ptr = ptr.next;
            cnt++;
        }
        return cnt;
    }
}
