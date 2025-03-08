package Algorithms.HeapAlgos;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Random;

/**
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 05 March 2025
 */
public class KthLargestElementInArray {
    public static void main(String[] args) {
        int[] nums = new int[]{3,2,1,5,6,4};
        int k = 2;
        System.out.println("findKthLargestUsingQuickSort(nums, k) => " + findKthLargestUsingQuickSort(nums, k));
        nums = new int[]{3,2,1,5,6,4};
        System.out.println("findKthLargestUsingQuickSort2(nums, k) => " + findKthLargestUsingQuickSort2(nums, k));
        nums = new int[]{3,2,1,5,6,4};
        System.out.println("findKthLargestUsingMinHeap(nums, k) => " + findKthLargestUsingMinHeap(nums, k));
        nums = new int[]{3,2,1,5,6,4};
        System.out.println("findKthLargestUsingMaxHeap(nums, k) => " + findKthLargestUsingMaxHeap(nums, k));
        nums = new int[]{3,2,1,5,6,4};
        System.out.println("findKthLargestUsingRange(nums, k) => " + findKthLargestUsingRange(nums, k));
        nums = new int[]{3,2,1,5,6,4};
        System.out.println("findKthLargestUsingInBuiltSortMethod(nums, k) => " + findKthLargestUsingInBuiltSortMethod(nums, k));
        nums = new int[]{3,2,1,5,6,4};
        System.out.println("findKthLargestUsingPriorityQueue(nums, k) => " + findKthLargestUsingPq(nums, k));
    }


    /**
     * @TimeComplexity: O(n) in average and O(n^2) in worst, but slower than #findKthLargestUsingQuickSort2()
     * @SpaceComplexity: O(1)
    */
    public static int findKthLargestUsingQuickSort(int[] nums, int k) {
        List<Integer> list = new ArrayList<>();
        for (int num: nums) {
            list.add(num);
        }

        return quickSelect(list, k);
    }

    public static int quickSelect(List<Integer> nums, int k) {
        int pivotIndex = new Random().nextInt(nums.size());
        int pivot = nums.get(pivotIndex);

        List<Integer> left = new ArrayList<>(); // greater than pivot
        List<Integer> mid = new ArrayList<>(); // equal to pivot
        List<Integer> right = new ArrayList<>(); // less than pivot

        for (int num: nums) {
            if (num > pivot) {
                left.add(num);
            } else if (num < pivot) {
                right.add(num);
            } else {
                mid.add(num);
            }
        }

        // left
        if (k <= left.size()) {
            return quickSelect(left, k);
        }

        // right
        if (left.size() + mid.size() < k) {
            return quickSelect(right, k - left.size() - mid.size());
        }

        return pivot;
    }





    /**
     * @TimeComplexity: O(n) in average and O(n^2) in worst
     * @SpaceComplexity: O(1)
     */
    public static int findKthLargestUsingQuickSort2(int[] nums, int k) {
        int targetIdx = nums.length - k; // targetIndex in asc sorted nums arr or new k in ascendingOrder i.e it's index after sort
        return quickSelect2(nums, 0, nums.length - 1, targetIdx);
    }
    // quick sort
    private static int quickSelect2(int[] nums, int left, int right, int targetIdx) {
        if (left == right) {
            return nums[left];
        }

        int pivot = nums[new Random().nextInt(right-left+1)+left]; // something in window
        // or int pivot = nums[left]; // we can also choose first window element as pivot
        int low = left;
        int high = right;

        // now move sort nums as per pivot value i.e leftSide < pivot < rightSide
        while (low <= high) {
            while (low <= high && nums[low] < pivot) {
                low++;
            }
            while (low <= high && nums[high] > pivot) {
                high--;
            }
            if (low <= high) {
                // swap
                int temp = nums[low];
                nums[low] = nums[high];
                nums[high] = temp;
                // decrease window
                low++;
                high--;
            }
        }

        if (targetIdx <= high) {
            return quickSelect2(nums, left, high, targetIdx);
        } else if (targetIdx >= low) {
            return quickSelect2(nums, low, right, targetIdx);
        } else {
            return nums[targetIdx];
        }
    }






    /**
     * @TimeComplexity: O(n) in average and O(n^2) in worst
     * @SpaceComplexity: O(1)
     *
     * Down-Heapify (Percolate Down) ---> minHeap
     */
    public static int findKthLargestUsingMinHeap(int[] nums, int k) {
        int[] minHeap = new int[k];
        for (int i = 0; i < k; i++) {
            minHeap[i] = nums[i];
        }
        buildMinHeap(minHeap);
        for (int i = k; i < nums.length; i++) {
            if (nums[i] > minHeap[0]) {
                minHeap[0] = nums[i];
                heapifyDown(minHeap, 0);
            }
        }
        return minHeap[0];
    }

    public static void buildMinHeap(int[] heap) {
        for (int i = heap.length / 2 - 1; i >= 0; i--) {
            heapifyDown(heap, i);
        }
    }

    public static void heapifyDown(int[] heap, int index) {
        int smallest = index;
        int left = 2 * index + 1;
        int right = 2 * index + 2;
        if (left < heap.length && heap[left] < heap[smallest]) {
            smallest = left;
        }
        if (right < heap.length && heap[right] < heap[smallest]) {
            smallest = right;
        }
        if (smallest != index) {
            int temp = heap[index];
            heap[index] = heap[smallest];
            heap[smallest] = temp;
            heapifyDown(heap, smallest);
        }
    }






    /**
     * @TimeComplexity: O(n) in average and O(n^2) in worst
     * @SpaceComplexity: O(1)
     *
     * Up-Heapify (Percolate Up) ---> maxHeap
     */
    public static int findKthLargestUsingMaxHeap(int[] nums, int k) {
            int n = nums.length;
            for (int i = n / 2 - 1; i >= 0; i--) {
                heapify(nums, i, n);
            }
            for (int i = n - 1; i >= n - k; i--) {
                int temp = nums[0];
                nums[0] = nums[i];
                nums[i] = temp;
                heapify(nums, 0, i);
            }
            return nums[n - k];
        }

    private static void heapify(int[] nums, int i, int n) {
        int largest = i;
        int left = 2 * i + 1;
        int right = 2 * i + 2;
        if (left < n && nums[left] > nums[largest]) {
            largest = left;
        }
        if (right < n && nums[right] > nums[largest]) {
            largest = right;
        }
        if (largest != i) {
            int temp = nums[i];
            nums[i] = nums[largest];
            nums[largest] = temp;
            heapify(nums, largest, n);
        }
    }







    /**
     * TLE
     * @TimeComplexity: O(n) in average and O(n^2) in worst
     * @SpaceComplexity: O(1)
     */
    public static int findKthLargestUsingQuickSort3(int[] nums, int k) {
        k = nums.length - k; // new k in ascendingOrder & it's index
        return quickSelect3(nums, 0, nums.length - 1, k);
    }
    // quick sort
    private static int quickSelect3(int[] nums, int l, int r, int k) {
        // if (l == r) {
        //     return nums[l];
        // }

        int pivot = nums[r]; // always select first element as pivot
        int p = l;

        for (int i = l; i < r; i++) {
            if (nums[i] <= pivot) {
                int temp = nums[p];
                nums[p] = nums[i];
                nums[i] = temp;
                p++;
            }
        }

        int temp = nums[p];
        nums[p] = nums[r];
        nums[r] = temp;

        if (p > k) {
            return quickSelect3(nums, l, p-1, k);
        } else if (p < k) {
            return quickSelect3(nums, p+1, r, k);
        } else {
            return nums[p];
        }
    }



    public static int findKthLargest2(int[] nums, int k) {
        int[] n = new int[20001];

        for(int num:nums) {
            n[num+10000]++;
        }

        for(int i=n.length-1; i>=0; i--) {
            k = k-n[i];
            if(k<=0) return i-10000;
        }

        return -1;
    }





    /**
     * @TimeComplexity: O(n+r) -> n = nums.length, r = range of nums
     * @SpaceComplexity: O(r)
    */
    public static int findKthLargestUsingRange(int[] nums, int k) {
        int minValue = Arrays.stream(nums).min().getAsInt();
        int maxValue = Arrays.stream(nums).max().getAsInt();

        int[] count = new int[maxValue - minValue + 1];

        for (int num : nums) {
            count[num - minValue]++;
        }

        int remaining = k;
        for (int i = count.length - 1; i >= 0; i--) {
            remaining -= count[i];

            if (remaining <= 0) {
                return i + minValue;
            }
        }

        return -1; // This line should not be reached
    }





    /**
     * @TimeComplexity: O(nlogn)
     * @SpaceComplexity: O(1)
    */
    public static int findKthLargestUsingInBuiltSortMethod(int[] nums, int k) {
        int kthLarge = Integer.MIN_VALUE;
        Arrays.sort(nums);
        kthLarge = nums[nums.length - k];

        kthLarge = Arrays.stream(nums).sorted().toArray()[nums.length - k]; // Arrays.sort(nums); nums[nums.length - k];

        // or
        kthLarge = Arrays.stream(nums).sorted().skip(nums.length - k).findFirst().getAsInt();

        // or
        kthLarge = Arrays.stream(nums).boxed().sorted(Comparator.reverseOrder()).skip(k - 1).findFirst().get();

        // or
        kthLarge = Arrays.stream(nums).boxed().sorted(Comparator.reverseOrder()).limit(k).findFirst().get();

        // or
        kthLarge = Arrays.stream(nums).boxed().sorted(Comparator.reverseOrder()).mapToInt(i -> i).toArray()[k - 1];

        return kthLarge;
    }




    /**
     * @TimeComplexity: O(nlogk)
     * @SpaceComplexity: O(k)
     */
    public static int findKthLargestUsingPq(int[] nums, int k) {
        PriorityQueue<Integer> pq = new PriorityQueue<>();

        for (int num : nums) {
            pq.add(num);
            if (pq.size() > k) { // maintain k size
                pq.poll(); // removes the smallest element
            }
        }

        return pq.peek(); // pq contains only k largest elements & the head will be the kth largest of the array
    }
}


