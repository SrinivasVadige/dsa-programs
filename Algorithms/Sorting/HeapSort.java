package Algorithms.Sorting;

import java.util.Arrays;

/**
 * @author Srinvas Vadige, srinivas.vadige@gmail.com
 * @since 21 Sept 2024

<pre>
    But in java we use PriorityQueue to implement Heap
    ---> check out {@link DataStructures.QueueDequeuePriorityQueue} and {@link DataStructures.Heap}

    HEAP:
    Heap means ordered binary tree
    Heaps are 2 types
    1. Min Heap (Parent <= Child)
    2. Max Heap (Parent >= Child)

    HEAPIFY:
    Heapify direction = direction in which index i travels
    1. Heapify Down ---> we move curr i's num to the down
    2. Heapify Up ---> we move curr i's num to the up

    CHILD & PARENT RELATION WITH INDEX:
    1. leftI = 2 * i + 1
    2. rightI = 2 * i + 2
    3. parentI = (i - 1) / 2
    4. Last non-leaf node = (n - 1) / 2
    5. First leaf node = n / 2


                    9
                /      \
              6         4
            /   \     /   \
           5     5   3     2
          / \   / \
         1   1 3   5


    [9, 6, 4, 5, 5, 3, 2, 1, 1, 3, 5]
     ↑  ↑  ↑
     i  L  R

    [9, 6, 4, 5, 5, 3, 2, 1, 1, 3, 5]
        ↑     ↑  ↑
        i     L  R

    [9, 6, 4, 5, 5, 3, 2, 1, 1, 3, 5]
           ↑        ↑  ↑
           i        L  R

    [9, 6, 4, 5, 5, 3, 2, 1, 1, 3, 5]
              ↑           ↑  ↑
              i           L  R

    [9, 6, 4, 5, 5, 3, 2, 1, 1, 3, 5]
                 ↑              ↑  ↑
                 i              L  R


    STEPS FOR HEAP SORT:
    1. Build heap tree -> creates the min/max heap accordingly from the given unsorted array
    2. In heapify -> similar to build min/max heap, but assumes part of the array is already sorted
    3. In heapify down -> if the current i's num is greater/smaller than leftI/rightI then we move accordingly
    4. We know that after building heap tree, the first element is already sorted -> minHeap has smallest ele and maxHeap has largest ele
    5. Now the array first element is what we need -> move that ele to the last then call heapify down
    6. Repeat the same step again by decreasing the i--, as the required num will move to the top after heapify then all the last elements will be sorted

    So, as we're moving first ele to the last indices i.e., we sort the array from right to left
    then if we want ascending order, build maxHeap and then move all the bigger nums to the right side

 </pre>
    @TimeComplexity O(nlogn) worst case, O(nlogn) best case
    Building heap -> O(n)
    Heapify -> O(logn) -> we call it n-1 times
    @SpaceComplexity O(1)
 * @see <a href="https://youtu.be/jtshKZDqeYo?si=X0pyCCx9FeNmWmsg">Heap Sort Algorithm | Simple Explanation (Youtube video explanation)</a>
*/
public class HeapSort {
    public static void main(String[] args) {
        System.out.println("\n--- Manual heap construction ---");
        int[] nums = new int[] {3, 1, 4, 1, 5, 9, 2, 6, 5, 3, 5}, heap;

        heap = Arrays.copyOf(nums, nums.length);
        heap = Arrays.copyOf(nums, nums.length);
        System.out.println("\n\nMAX HEAP:");
        System.out.printf("Given => " + Arrays.toString(heap));
        buildMaxHeap(heap);
        System.out.printf("\nOutput => " + Arrays.toString(heap));

        System.out.println("\nMIN HEAP:");
        System.out.printf("Given => " + Arrays.toString(heap));
        buildMinHeap(heap);
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

        System.out.println("\nMIN HEAP SORT USING HEAPIFY UP - Descending order");
        heap = Arrays.copyOf(nums, nums.length);
        minHeapSortUsingHeapifyUp(heap);
        System.out.printf(" => " + Arrays.toString(heap));
    }






    /**
     use {@link #maxHeapHeapifyDown(int[] heap, int i, int n)} instead of {@link #maxHeapHeapifyDown(int[] heap, int i)}
     ASCENDING ORDER

     * @TimeComplexity O(nlogn)
     * @SpaceComplexity O(1)
     */
    public static void maxHeapSort(int[] nums) {
        buildMaxHeap(nums);
        for (int i = nums.length - 1; i > 0; i--) {
            swap(nums, 0, i);
            maxHeapHeapifyDown(nums, 0, i);
        }
    }



    /**
     use {@link #minHeapHeapifyDown(int[] heap, int i, int n)} instead of {@link #minHeapHeapifyDown(int[] heap, int i)}
     DESCENDING ORDER

     * @TimeComplexity O(nlogn)
     * @SpaceComplexity O(1)
     */
    public static void minHeapSort(int[] nums) {
        buildMinHeap(nums);
        for (int i = nums.length - 1; i > 0; i--) {
            swap(nums, 0, i);
            minHeapHeapifyDown(nums, 0, i);
        }
    }



    /**
      DESCENDING ORDER

     * @TimeComplexity O(n²logn)
     * @SpaceComplexity O(1)
     */
    public static void minHeapSortUsingHeapifyUp(int[] nums) {
        buildMinHeapUsingHeapifyUp(nums);

        for (int i = nums.length - 1; i > 0; i--) {
            swap(nums, 0, i); // Move root (smallest) to end
            for (int j = 1; j < i; j++) { // Rebuild heap using heapifyUp
                minHeapHeapifyUp(nums, j);
            }
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
    public static void buildMaxHeap(int[] heap) {
        for (int i = heap.length / 2 - 1; i >= 0; i--) { // Last non-leaf node = (n - 1) / 2
            maxHeapHeapifyDown(heap, i, heap.length); // or maxHeapHeapifyDown(heap, i);
        }
    }

    private static void maxHeapHeapifyDown(int[] heap, int i, int n) {
        int largest = i;
        int left = 2 * i + 1; // LEFT CHILD OF CURR I
        int right = 2 * i + 2; // RIGHT CHILD OF CURR I
        if (left < n && heap[left] > heap[largest]) {
            largest = left;
        }
        if (right < n && heap[right] > heap[largest]) {
            largest = right;
        }
        if (largest != i) {
            swap(heap, i, largest);
            maxHeapHeapifyDown(heap, largest, n);
        }
    }

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
            swap(heap, i, largest);
            maxHeapHeapifyDown(heap, largest);
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
    public static void buildMinHeap(int[] heap) {
        for (int i = heap.length / 2 - 1; i >= 0; i--) {
            minHeapHeapifyDown(heap, i, heap.length); // or minHeapHeapifyDown(heap, i);
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
            swap(heap, i, smallest);
            minHeapHeapifyDown(heap, smallest, n);
        }
    }

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
            swap(heap, i, smallest);
            minHeapHeapifyDown(heap, smallest);
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
    public static void buildMinHeapUsingHeapifyUp(int[] nums) {
        for (int i = 1; i < nums.length; i++) {
            minHeapHeapifyUp(nums, i);
        }
    }

    public static void minHeapHeapifyUp(int[] heap, int i) {
        while (i > 0) {
            int parent = (i - 1) / 2;
            if (heap[parent] > heap[i]) { // min heap condition
                swap(heap, i, parent);
                i = parent;
            } else {
                break;
            }
        }
    }




    public static void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
}
