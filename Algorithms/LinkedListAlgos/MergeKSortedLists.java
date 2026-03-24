package Algorithms.LinkedListAlgos;

import java.util.*;

/**
 * Given the heads of k sorted linked lists list1, list2, ..., list k, return the sorted list
 * containing all the lists in sorted order.
 *
 *
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 11 Nov 2024
 * @link 23. Merge k Sorted Lists <a href="https://leetcode.com/problems/merge-k-sorted-lists/">LeetCode link</a>
 * @topics Linked List, Divide and Conquer, Heap(Priority Queue), Merge Sort
 * @companies Amazon(17), Meta(10), Microsoft(9), Bloomberg(5), Google(3), Cloudflare(3), Apple(2), Oracle(2), Nvidia(2), Rippling(6), TikTok(5), Deloitte(3), Flipkart(2), Snowflake(2), MongoDB(2), Yandex(6), Uber(5), Palantir Technologies(5), LinkedIn(4), Walmart Labs(4), WarnerMedia(4), Anduril(4), TCS(3), Goldman Sachs(3), SoFi(3)
 * @see Algorithms.LinkedListAlgos.MergeTwoSortedLists#mergeTwoListsUsingSort(Algorithms.LinkedListAlgos.MergeTwoSortedLists.ListNode, Algorithms.LinkedListAlgos.MergeTwoSortedLists.ListNode)
 */
public class MergeKSortedLists {

    public static class ListNode {
        int val;
        ListNode next;
        ListNode() {}
        ListNode(int val) { this.val = val; }
        ListNode(int val, ListNode next) { this.val = val; this.next = next; }
    }

    public static void main(String[] args) {
        ListNode list1 = new ListNode(1, new ListNode(4, new ListNode(5)));
        ListNode list2 = new ListNode(1, new ListNode(3, new ListNode(4)));
        ListNode list3 = new ListNode(2, new ListNode(6));

        System.out.println("mergeKLists Using Sort: ");
        for(ListNode trav = mergeKListsUsingListSortBruteForce(new ListNode[]{list1, list2, list3}); trav != null; trav = trav.next) {
            System.out.print(trav.val + " ");
        }

        System.out.println("\nmergeKLists Using Divide and Conquer Merge Sort Approach 1: ");
        for(ListNode trav = mergeKListsUsingDivideAndConquerMergeSortApproach1(new ListNode[]{list1, list2, list3}); trav != null; trav = trav.next) {
            System.out.print(trav.val + " ");
        }

        System.out.println("\nmergeKLists Using Divide and Conquer Merge Sort Approach 2: ");
        for(ListNode trav = mergeKListsUsingDivideAndConquerMergeSortApproach2(new ListNode[]{list1, list2, list3}); trav != null; trav = trav.next) {
            System.out.print(trav.val + " ");
        }

        System.out.println("\nmergeKLists Using Divide and Conquer Merge Sort Approach 3: ");
        for(ListNode trav = mergeKListsUsingDivideAndConquerMergeSortApproach3(new ListNode[]{list1, list2, list3}); trav != null; trav = trav.next) {
            System.out.print(trav.val + " ");
        }

        System.out.println("\nmergeKLists Using Priority Queue Optimized: ");
        for(ListNode trav = mergeKListsUsingPriorityQueueOptimized(new ListNode[]{list1, list2, list3}); trav != null; trav = trav.next) {
            System.out.print(trav.val + " ");
        }
    }



    /**
     * @TimeComplexity O(n log n)
     * @SpaceComplexity O(n)
     */
    public static ListNode mergeKListsUsingListSortBruteForce(ListNode[] lists) {
        List<Integer> lst = new ArrayList<>();
        for(ListNode node: lists) {
            for(ListNode trav=node; trav!=null; trav=trav.next)
                lst.add(trav.val);
        }

        if(lst.size()==0) return null;

        Collections.sort(lst);

        ListNode dummy = new ListNode(-1);
        ListNode trav = dummy;
        for (Integer n: lst){
            trav.next=new ListNode(n);
            trav = trav.next;
        }
        return dummy.next;
    }





        /**
         * @TimeComplexity O(n * k), where n = total nodes and k = k lists - lists.length ===> ((((l1 + l2) + l3) + l4) + l5) ...
         * Here we are re-merging the same nodes again and again
         * (N/K) + (2N/K) + (3N/K) + ... + (KN/K)
         * = N * (1 + 2 + 3 + ... + K) / K
         * = N * (K(K+1)/2) / K
         * O(N * K)
         * @SpaceComplexity O(n)



          Convert this to binary tree approach---> Divide and Conquer Brute force

                 l1    l2     l3      l4     l5
                 |_____|       |       |      |
                    |__________|       |      |
                          |____________|      |
                                 |____________|
                                        |
                                       res
            this {@link #mergeKListsUsingDivideAndConquerMergeSortApproach1} is slower than {@link #mergeKListsUsingDivideAndConquerMergeSortApproach2}
            cause in this approach we're doing extra work of re-merging the same nodes again and again
         */
        public static ListNode mergeKListsUsingDivideAndConquerMergeSortApproach1(ListNode[] lists) { // MY APPROACH
            if (lists.length == 0) return null;

            ListNode dummy = new ListNode(), node = dummy;
            ListNode left = lists[0];

            for (int i=1; i<lists.length; i++) {
                ListNode right = lists[i];
                while (left != null && right != null) {
                    if (left.val < right.val) {
                        node.next = left;
                        node = node.next;
                        left = left.next;
                    } else {
                        node.next = right;
                        node = node.next;
                        right = right.next;
                    }
                }

                if (left != null) node.next = left;
                else node.next = right;

                left = dummy.next;
                dummy = new ListNode();
                node = dummy;

            }

            return left;
        }







    /**
     * @TimeComplexity O(n log k), where n = total nodes and k = k lists - lists.length
     * Here instead of above approach we do → (l1 + l2), (l3 + l4), (l5 + l6) → then merge results → then merge again
     * Each node is processed only log K times
     * logK levels
     * @SpaceComplexity O(n)

        l1+l2   l3+l4   l5+l6
          ↓       ↓       ↓
           merge pairs evenly
                 ↓
            final merge


     Convert this to binary tree best approach---> improved

              l1    l2     l3      l4     l5
               |_____|      |_______|      |
              l1  |_____________| l3       |
                      l1 |_________________| l5
                                   |
                                   l1

     That's why this {@link #mergeKListsUsingDivideAndConquerMergeSortApproach2} is faster than {@link #mergeKListsUsingDivideAndConquerMergeSortApproach1}

     */
    public static ListNode mergeKListsUsingDivideAndConquerMergeSortApproach2(ListNode[] lists) {
        int n = lists.length;
        if (n == 0) return null;

        while (n > 1) {
            List<ListNode> mergedLists = new ArrayList<>(); // use int interval variable instead of mergedLists --> see {@link #mergeKListsUsingDivideAndConquerMergeSortApproach3}

            for (int i=0; i<n; i+=2) { // do two and skip to third node
                ListNode l1 = lists[i];
                ListNode l2 = (i+1) < n? lists[i+1]: null; // for i == n-1, l2 = null
                mergedLists.add(mergeTwoListNodes(l1, l2));
            }
            lists = mergedLists.toArray(ListNode[]::new);
            n = lists.length;
        }

        return lists[0];
    }

    private static ListNode mergeTwoListNodes(ListNode l1, ListNode l2) {
//        if (l1 == null) return l2;
//        if (l2 == null) return l1;

        ListNode head = new ListNode(), tail = head;
        while(l1 != null && l2 !=null) {
            if(l1.val < l2.val) {
                tail.next = l1;
                l1 = l1.next;
            } else {
                tail.next = l2;
                l2 = l2.next;
                /*
                // or
                tail.next = l2;
                l2 = l1;
                l1 = tail.next.next;
                 */
            }
            tail = tail.next;
        }
        tail.next = (l1 == null)? l2: l1;

        return head.next;
    }








    /**
     * @TimeComplexity O(n log k) where k = number of linked lists and O(log k) for every pop
     * @SpaceComplexity O(1)


        interval = 1 → merge pairs of size 1
        interval = 2 → merge pairs of size 2
        interval = 4 → merge pairs of size 4

        i increments like
        interval = 1 → i = 0, 2, 4....
        interval = 2 → i = 0, 4, 8....
        interval = 4 → i = 0, 8, 16....

     */
    public static ListNode mergeKListsUsingDivideAndConquerMergeSortApproach3(ListNode[] lists) {
        int n = lists.length;
        if (n == 0) return null;
        int interval = 1;
        while (interval < n) {
            for (int i = 0; i < n-interval; i += interval*2) {
                lists[i] = mergeTwoListNodes(lists[i], lists[i + interval]);
            }
            interval *= 2;
        }
        return lists[0];
    }








    public static ListNode mergeKListsUsingDivideAndConquerMergeSortApproach4(ListNode[] lists) {
        if (lists.length == 0) return null;

        while (lists.length > 1) {
            ListNode[] mergedLists = new ListNode[lists.length / 2]; // we already know how many ListNodes we have in "Convert this to binary tree best approach---> improved"
            for (int i = 0; i < lists.length; i += 2) {
                mergedLists[i / 2] = mergeTwoLists2(lists[i], lists[i + 1]);
            }
            lists = mergedLists;
        }

        return lists[0];
    }

    private static ListNode mergeTwoLists2(ListNode list1, ListNode list2) {
        if (list1 == null) return list2;
        if (list2 == null) return list1;
        if (list1.val < list2.val) {
            list1.next = mergeTwoLists2(list1.next, list2);
            return list1;
        } else {
            list2.next = mergeTwoLists2(list1, list2.next);
            return list2;
        }
    }








    /**
     * @TimeComplexity O(n log n)
     * @SpaceComplexity O(n)
     */
    public ListNode mergeKListsUsingPriorityQueue(ListNode[] lists) {
        PriorityQueue<Integer> max = new PriorityQueue<>();
        for(ListNode node : lists){
            while(node!=null){
                max.offer(node.val);
                node = node.next;
            }
        }

        ListNode dummy = new ListNode(0);
        ListNode trav = dummy;
        while(!max.isEmpty()){
            trav.next = new ListNode(max.poll());
            trav = trav.next;
        }
        return dummy.next;
    }





    /**
     * @TimeComplexity O(n log k) where k = number of linked lists and O(log k) for every pop
     * @SpaceComplexity O(n)
     */
    public static ListNode mergeKListsUsingPriorityQueueOptimized(ListNode[] lists) {
        PriorityQueue<ListNode> pq = new PriorityQueue<>(
            new Comparator<>() {
                @Override
                public int compare(ListNode o1, ListNode o2) {
                    if (o1.val > o2.val) {
                        return 1;
                    } else if (o1.val == o2.val) {
                        return 0;
                    } else {
                        return -1;
                    }
                }
            }
            /*
            // or
                (o1, o2) -> {
                    if (o1.val > o2.val) {
                        return 1;
                    } else if (o1.val == o2.val) {
                        return 0;
                    } else {
                        return -1;
                    }
                }


             */
        );
        for (ListNode node : lists) {
            if (node != null) {
                pq.add(node);
            }
        }

        ListNode head = new ListNode(0);
        ListNode tail = head;
        while (!pq.isEmpty()) {
            tail.next = pq.poll();
            tail = tail.next;
            if (tail.next != null) {
                pq.add(tail.next);
            }
        }
        return head.next;
    }






    /**
     * @TimeComplexity O(n+r) -> n = total nodes, r = range of values == max-min+1 == Bucket size
     * @SpaceComplexity O(r)
     */
    public static ListNode mergeKListsUsingBucketSort(ListNode[] lists) {
        int min = Integer.MAX_VALUE, max = Integer.MIN_VALUE;
        for (ListNode list : lists) {
            while (list != null) {
                min = Math.min(min, list.val);
                max = Math.max(max, list.val);
                list = list.next;
            }
        }
        int[] bucket = new int[max - min + 1];
        for (ListNode list : lists) {
            while (list != null) {
                bucket[list.val - min]++;
                list = list.next;
            }
        }
        ListNode dummy = new ListNode(-1), trav = dummy;
        for (int i = 0; i < bucket.length; i++) {
            while (bucket[i]-- > 0) {
                trav.next = new ListNode(i + min);
                trav = trav.next;
            }
        }
        return dummy.next;
    }

    






    public static ListNode mergeKListsUsingPriorityQueue2(ListNode[] lists) {
        Queue<ListNode> queue = new PriorityQueue<>((a, b) -> a.val - b.val);
        for (ListNode list : lists) {
            if (list != null) {
                queue.add(list);
            }
        }
        ListNode dummy = new ListNode(-1), trav = dummy;
        while (!queue.isEmpty()) {
            ListNode node = queue.poll();
            trav.next = node;
            trav = trav.next;
            if (node.next != null) {
                queue.add(node.next);
            }
        }
        return dummy.next;
    }









    public ListNode mergeKListsUsingTable(ListNode[] lists) {
        int min = Integer.MAX_VALUE, max = Integer.MIN_VALUE;
        for (ListNode list : lists) {
            while (list != null) {
                int val = list.val;
                if (val > max) {
                    max = val;
                }
                if (val < min) {
                    min = val;
                }
                list = list.next;
            }
        }
        // bucket where each bucket corresponds to a specific value.
        /*
         Each position in table acts as a bucket, storing nodes of the same value by
         chaining them together using the next pointers
         */
        ListNode[] table = new ListNode[max - min + 1];
        for (int i = lists.length - 1; i >= 0; i--) {
            ListNode node = lists[i], temp;
            while (node != null) {
                temp = node.next;
                node.next = table[node.val - min];
                table[node.val - min] = node;
                node = temp;
            }
        }
        ListNode result = new ListNode();
        ListNode pointer = result;
        for (ListNode node : table) {
            if (node != null) {
                pointer.next = node;
                pointer = pointer.next;
                while (node.next != null)
                    node = node.next;
                pointer = node;
            }
        }
        return result.next;
    }







    public ListNode mergeKListsUsingArrayDeque(ListNode[] lists) {
        Queue<ListNode> queue = new ArrayDeque<>();

        for (ListNode l : lists) {
            if (l != null) {
                queue.offer(l);
            }
        }

        while (queue.size() >= 1) {
            if (queue.size() == 1) {
                return queue.peek();
            }

            ListNode merged = merge(queue.poll(), queue.poll());
            queue.offer(merged);
        }

        return null;
    }

    private ListNode merge(ListNode head1, ListNode head2) {
        ListNode merged = new ListNode();
        ListNode temp = merged;

        while (head1 != null && head2 != null) {
            if (head1.val < head2.val) {
                temp.next = head1;
                head1 = head1.next;
            } else {
                temp.next = head2;
                head2 = head2.next;
            }
            temp = temp.next;
        }

        if (head1 != null) {
            temp.next = head1;
        }
        if (head2 != null) {
            temp.next = head2;
        }

        return merged.next;
    }


}
