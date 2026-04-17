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

        System.out.println("PriorityQueue class ----------");
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


        System.out.println("\nManual heap construction:");
        int[] heap = new int[] {3, 1, 4, 1, 5, 9, 2, 6, 5, 3, 5};
        System.out.println("\nMIN HEAP --->");
        System.out.printf("Given => " + Arrays.toString(heap));
        buildMinHeap(heap);
        System.out.printf("\nOutput => " + Arrays.toString(heap));
        heap = new int[] {3, 1, 4, 1, 5, 9, 2, 6, 5, 3, 5};
        System.out.println("\n\nMAX HEAP --->");
        System.out.printf("Given => " + Arrays.toString(heap));
        buildMaxHeap(heap);
        System.out.printf("\nOutput => " + Arrays.toString(heap));
    }


    public static void buildMinHeap(int[] heap) {
        for (int i = heap.length / 2 - 1; i >= 0; i--) {
            minHeapUsingHeapifyDown(heap, i);
        }
    }

    public static void buildMaxHeap(int[] heap) {
        for (int i = heap.length / 2 - 1; i >= 0; i--) {
            maxHeapUsingHeapifyUp(heap, i);
        }
    }


    /**


    Given => [3, 1, 4, 1, 5, 9, 2, 6, 5, 3, 5]
    Output => [1, 1, 2, 3, 3, 9, 4, 6, 5, 5, 5]

                    1
                /      \
              1         2
            /   \      / \
           3     3    9   4
          / \   / \
         6   5 5   5


     */
    public static void minHeapUsingHeapifyDown(int[] heap, int i) {
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
            minHeapUsingHeapifyDown(heap, smallest);
        }
    }


    /**


    Given => [3, 1, 4, 1, 5, 9, 2, 6, 5, 3, 5]
    Output => [9, 6, 4, 5, 5, 3, 2, 1, 1, 3, 5]

                    9
                /      \
              6         4
            /   \     /   \
           5     5   3     2
          / \   / \
         1   1 3   5


     */
    private static void maxHeapUsingHeapifyUp(int[] heap, int i) {
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
            maxHeapUsingHeapifyUp(heap, largest);
        }
    }


    public static void maxHeapUsingHeapifyUp2(int[] heap, int i) {
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
