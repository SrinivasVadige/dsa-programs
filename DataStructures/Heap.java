package DataStructures;

import java.util.*;
import java.util.concurrent.PriorityBlockingQueue;

/**
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 17 April 2026
 * @see DataStructures.QueueDequeuePriorityQueue


    Heaps are 2 types
    1. Min Heap (Parent <= Child)
    2. Max Heap (Parent >= Child)

    In java we use PriorityQueue to implement Heap ---> check out {@link DataStructures.QueueDequeuePriorityQueue}

    ——————————————
    PRIORITY QUEUE
    ——————————————
    It’s an Abstract Data Structure
    PriorityQueue uses heap internally.
    Arranges the push() values in asc order and poll() the smallest number accordingly as per the priority.
    PriorityQueue<Integer> pq = new PriorityQueue<>();
    pq.add(5);
    pq.add(1);
    pq.add(7);
    pq.poll(); —> removes 1
    pq.isEmpty();

    Create a PriorityQueue with the custom comparator
    PriorityQueue<Person> pq = new PriorityQueue<>(byAgeComparatorFuncInterfaceMethod);

    This next smallest that gets pulled in the PQ is maintained by Heap.

    PQ is used in
        Dijkstra’s Shortest Path Algorithm,
        Dynamically fetch ‘next best’ or ‘next worst’ ele
        Huffman Coding (lossless data compression)
        A* DFS algo to grab next most promising node
        Minimum Spanning Tree MST algo

    Construction - O(n)
    Polling - O(logn)
    Peeking - O(n)
    Adding - O(logn)
    Removing - O(n)
    Removing using HashTable - O(logn)
    Contains - O(n)
    Contains with HashTable - O(1)

    As heap have min & max heap, we have min & max PQ. By default the PQ is min PQ i.e returns smallest number. We can convert min PQ to max PQ by negating the numbers so that bigger numbers will be the smallest number.

 */
public class Heap {

    public static void main(String[] args) {
        Queue<Integer> priorityQueueWithQueueInterface = new PriorityQueue<>();
        PriorityQueue<Integer> priorityQueueMinHeap = new PriorityQueue<>(); // or new PriorityQueue<Collections.naturalOrder()>();
        PriorityQueue<Integer> priorityQueueMaxHeap = new PriorityQueue<>(Collections.reverseOrder());
        Queue<Integer> priorityQueueBlocking = new PriorityBlockingQueue<>(); // thread-safe & blocking but others are not thread-safe & non-blocking

        System.out.println("--- PriorityQueue class ---");
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        pq.add(1);
        pq.addAll(new ArrayList<>(Arrays.asList(1,2,3)));
        pq.addAll(new LinkedList<>(Arrays.asList(4,5,6)));
        pq.remove();
        pq.remove(1);
        pq.removeAll(new LinkedList<>(Arrays.asList(7,8,9)));
        pq.retainAll(new LinkedList<>(Arrays.asList(1,2,3)));
        pq.poll(); // remove the first element and returns it i. same as queue.remove()
        pq.offer(4); // add the element at the end i.e same as queue.add(4)
        pq.element(); // returns the first element
        pq.peek();
        pq.size();
        pq.clear();
        pq.contains(1);
        pq.containsAll(new LinkedList<>(Arrays.asList(1,2,3)));
        pq.isEmpty();
        pq.forEach(System.out::println);
        pq.iterator();
        pq.toArray();
        pq.toString();


        System.out.println("\n--- Manual heap construction ---");
        int[] nums = new int[] {3, 1, 4, 1, 5, 9, 2, 6, 5, 3, 5}, heap;

        heap = Arrays.copyOf(nums, nums.length);
        System.out.println("\nMIN HEAP:");
        System.out.printf("Given => " + Arrays.toString(heap));
        buildMinHeap(heap);
        System.out.printf("\nOutput => " + Arrays.toString(heap));

        heap = Arrays.copyOf(nums, nums.length);
        System.out.println("\n\nMAX HEAP:");
        System.out.printf("Given => " + Arrays.toString(heap));
        buildMaxHeap(heap);
        System.out.printf("\nOutput => " + Arrays.toString(heap));



        System.out.println("\n\n--- HEAP SORTING ---");

        System.out.println("\nMAX HEAP SORT - Ascending order");
        heap = Arrays.copyOf(nums, nums.length);
        maxHeapSort(heap);
        System.out.printf(" => " + Arrays.toString(heap));

        System.out.println("\nMIN HEAP SORT - Descending order");
        heap = Arrays.copyOf(nums, nums.length);
        minHeapSort(heap);
        System.out.printf(" => " + Arrays.toString(heap));
    }


    public static int findKthLargestUsingMinHeapHeapifyDown1(int[] nums, int k) {
        int[] minHeap = Arrays.copyOf(nums, k);
        buildMinHeap(minHeap);

        for (int i = k; i < nums.length; i++) {
            if (nums[i] > minHeap[0]) {
                minHeap[0] = nums[i];

                minHeapHeapifyDown(minHeap, 0);
            }
        }
        return minHeap[0];
    }

    public static int findKthLargestUsingMinHeapHeapifyDown2(int[] nums, int k) {
        buildMinHeap(nums);

        for (int i = nums.length - 1; i >= k - 1; i--) {
            int temp = nums[0];
            nums[0] = nums[i];
            nums[i] = temp;

            minHeapHeapifyDown(nums, 0, i);
        }
        return nums[k - 1];
    }

    /** use {@link #maxHeapHeapifyDown(int[] heap, int i, int n)} instead of {@link #maxHeapHeapifyDown(int[] heap, int i)} */
    public static int findKthLargestUsingMaxHeapHeapifyUp(int[] nums, int k) {
        buildMaxHeap(nums);

        for (int i = nums.length - 1; i >= nums.length - k; i--) {
            int temp = nums[0];
            nums[0] = nums[i];
            nums[i] = temp;

            maxHeapHeapifyDown(nums, 0, i);
        }
        return nums[nums.length - k];
    }


    /**
     use {@link #minHeapHeapifyDown(int[] heap, int i, int n)} instead of {@link #minHeapHeapifyDown(int[] heap, int i)}
     DESCENDING ORDER
     */
    public static void minHeapSort(int[] nums) {
        buildMinHeap(nums);
        for (int i = nums.length - 1; i > 0; i--) {
            int temp = nums[0];
            nums[0] = nums[i];
            nums[i] = temp;

            minHeapHeapifyDown(nums, 0, i);
        }
    }


    /**
     use {@link #maxHeapHeapifyDown(int[] heap, int i, int n)} instead of {@link #maxHeapHeapifyDown(int[] heap, int i)}
     ASCENDING ORDER
     */
    public static void maxHeapSort(int[] nums) {
        buildMaxHeap(nums);
        for (int i = nums.length - 1; i > 0; i--) {
            int temp = nums[0];
            nums[0] = nums[i];
            nums[i] = temp;

            maxHeapHeapifyDown(nums, 0, i);
        }
    }


    /**
      DESCENDING ORDER
     */
    public static void minHeapSortUsingHeapifyUp(int[] nums) {
        buildMinHeapUsingHeapifyUp(nums);

        for (int i = nums.length - 1; i > 0; i--) {
            // Move root (smallest) to end
            int temp = nums[0];
            nums[0] = nums[i];
            nums[i] = temp;

            // Rebuild heap using heapifyUp
            for (int j = 1; j < i; j++) {
                minHeapHeapifyUp(nums, j);
            }
        }
    }






    public static void buildMinHeap(int[] heap) {
        for (int i = heap.length / 2 - 1; i >= 0; i--) {
            minHeapHeapifyDown(heap, i, heap.length); // or minHeapHeapifyDown(heap, i);
        }
    }
    /**

                              i
               0  1  2  3  4  5  6  7  8  9  10
    Given =>  [3, 1, 4, 1, 5, 9, 2, 6, 5, 3, 5]
    Output => [1, 1, 2, 3, 3, 9, 4, 6, 5, 5, 5]

                    1
                /      \
              1         2
            /   \      / \
           3     3    9   4
          / \   / \
         6   5 5   5


     */
    public static void minHeapHeapifyDown(int[] heap, int i) {
        int smallest = i;
        int left = 2 * i + 1;
        int right = 2 * i + 2;
        if (left < heap.length && heap[left] < heap[smallest]) {
            smallest = left;
        }
        if (right < heap.length && heap[right] < heap[smallest]) {
            smallest = right;
        }
        if (smallest != i) {
            int temp = heap[i];
            heap[i] = heap[smallest];
            heap[smallest] = temp;
            minHeapHeapifyDown(heap, smallest);
        }
    }

    public static void minHeapHeapifyDown(int[] heap, int i, int n) {
        int smallest = i;
        int left = 2 * i + 1;
        int right = 2 * i + 2;
        if (left < n && heap[left] < heap[smallest]) {
            smallest = left;
        }
        if (right < n && heap[right] < heap[smallest]) {
            smallest = right;
        }
        if (smallest != i) {
            int temp = heap[i];
            heap[i] = heap[smallest];
            heap[smallest] = temp;
            minHeapHeapifyDown(heap, smallest, n);
        }
    }





    public static void buildMaxHeap(int[] heap) {
        for (int i = heap.length / 2 - 1; i >= 0; i--) {
            maxHeapHeapifyDown(heap, i, heap.length); // or maxHeapHeapifyDown(heap, i);
        }
    }
    /**
                              i
               0  1  2  3  4  5  6  7  8  9  10
    Given =>  [3, 1, 4, 1, 5, 9, 2, 6, 5, 3, 5]
    Output => [9, 6, 4, 5, 5, 3, 2, 1, 1, 3, 5]

                    9
                /      \
              6         4
            /   \     /   \
           5     5   3     2
          / \   / \
         1   1 3   5


     */
    private static void maxHeapHeapifyDown(int[] heap, int i) {
        int largest = i;
        int left = 2 * i + 1;
        int right = 2 * i + 2;
        if (left < heap.length && heap[left] > heap[largest]) {
            largest = left;
        }
        if (right < heap.length && heap[right] > heap[largest]) {
            largest = right;
        }
        if (largest != i) {
            int temp = heap[i];
            heap[i] = heap[largest];
            heap[largest] = temp;
            maxHeapHeapifyDown(heap, largest);
        }
    }


    private static void maxHeapHeapifyDown(int[] heap, int i, int n) {
        int largest = i;
        int left = 2 * i + 1;
        int right = 2 * i + 2;
        if (left < n && heap[left] > heap[largest]) {
            largest = left;
        }
        if (right < n && heap[right] > heap[largest]) {
            largest = right;
        }
        if (largest != i) {
            int temp = heap[i];
            heap[i] = heap[largest];
            heap[largest] = temp;
            maxHeapHeapifyDown(heap, largest, n);
        }
    }






    public static void buildMinHeapUsingHeapifyUp(int[] nums) {
        for (int i = 1; i < nums.length; i++) {
            minHeapHeapifyUp(nums, i);
        }
    }

    public static void minHeapHeapifyUp(int[] heap, int i) {
        while (i > 0) {
            int parent = (i - 1) / 2;
            if (heap[parent] > heap[i]) { // min heap condition
                int temp = heap[parent];
                heap[parent] = heap[i];
                heap[i] = temp;
                i = parent;
            } else {
                break;
            }
        }
    }
}
