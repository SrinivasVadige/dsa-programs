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
 *
 * Here the QuickSelect or QuickSort is "Divide and Conquer" Algorithm
 */
public class KthLargestElementInArray {
    public static void main(String[] args) {
        int[] nums = new int[]{3,2,1,5,6,4};
        int k = 2;
        System.out.println("findKthLargestUsingInBuiltSortMethod => " + findKthLargestUsingInBuiltSortMethod(nums, k));
        nums = new int[]{3,2,1,5,6,4};
        System.out.println("findKthLargest Using PriorityQueue with n size => " + findKthLargestUsingPqWithNSize(nums, k));
        System.out.println("findKthLargest Using PriorityQueue with k size 🔥 => " + findKthLargestUsingPqWithKSize(nums, k));
        System.out.println("findKthLargest Using Min Max Range BucketSort => " + findKthLargestUsingMinMaxRangeBucketSort(nums, k));

        System.out.println("findKthLargestUsingQuickSort => " + findKthLargestUsingQuickSort(nums, k));
        nums = new int[]{3,2,1,5,6,4};
        System.out.println("findKthLargestUsingQuickSort2 => " + findKthLargestUsingQuickSort2(nums, k));
        nums = new int[]{3,2,1,5,6,4};
        System.out.println("findKthLargestUsingMinHeap => " + findKthLargestUsingMinHeap(nums, k));
        nums = new int[]{3,2,1,5,6,4};
        System.out.println("findKthLargestUsingMaxHeap => " + findKthLargestUsingMaxHeap(nums, k));
    }

    /**
     * @TimeComplexity O(nlogn)
     * @SpaceComplexity O(1)
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
     * @TimeComplexity O(nlogn)
     * @SpaceComplexity O(n)
     */
    public static int findKthLargestUsingPqWithNSize(int[] nums, int k) {
        PriorityQueue<Integer> pq = new PriorityQueue<>(Comparator.reverseOrder());
        for(int x: nums) pq.offer(x);
        int res = 0;
        while(k-- > 0) res = pq.poll();
        return res;
    }
    public static int findKthLargestUsingPqWithNSize2(int[] nums, int k) {
        PriorityQueue<Integer> pq = new PriorityQueue<>(Comparator.reverseOrder());
        for(int x: nums) pq.offer(x);
        while(--k > 0) pq.poll();
        return pq.poll();
    }






    /**
     * @TimeComplexity O(nlogk)
     * @SpaceComplexity O(k)
     *
     * Here, the pq.offer() methods sum is not nlogn, it's nlogk
     * Cause if your heap only holds O(k) Elements i.e size==k, then heap operations only cost u O(log k) TimeComplexity, not O(log n)
     * --> for n elements it's nlogk
     */
    public static int findKthLargestUsingPqWithKSize(int[] nums, int k) {
        PriorityQueue<Integer> pq = new PriorityQueue<>();

        for (int num : nums) {
            pq.offer(num);
            if (pq.size() > k) { // maintain k size
                pq.poll(); // removes the smallest element
            }
        }
        return pq.peek(); // or pq.poll(); pq contains only k largest elements & the head will be the kth largest of the array
    }






    /**
     * @TimeComplexity O(n+r) -> n = nums.length, r = range of nums == max-min+1 == Bucket size
     * @SpaceComplexity O(r)
     *
     * How's the Bucket Sort work with negative numbers?
     * cause "num - minValue" will never be negative, it will always be >= 0
     * So, we can use it as an index in the count array.
    */
    public static int findKthLargestUsingMinMaxRangeBucketSort(int[] nums, int k) {
        int minValue = Arrays.stream(nums).min().getAsInt();
        int maxValue = Arrays.stream(nums).max().getAsInt();

        int[] count = new int[maxValue - minValue + 1]; // bucket size

        // bucket sort with dupes
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
     * @TimeComplexity O(n) in average and O(n^2) in worst, but slower than #findKthLargestUsingQuickSort2()
     * @SpaceComplexity O(n)
    */
    public static int findKthLargestUsingQuickSort(int[] nums, int k) {
        List<Integer> list = new ArrayList<>();
        for (int num: nums) {
            list.add(num);
        }

        return quickSelect(list, k);
    }

    private static int quickSelect(List<Integer> nums, int k) {
        int pivotIndex = new Random().nextInt(nums.size());
        int pivot = nums.get(pivotIndex);

        List<Integer> less = new ArrayList<>(); // less than pivot -- LEFT side
        List<Integer> equal = new ArrayList<>(); // equal to pivot -- MIDDLE side
        List<Integer> greater = new ArrayList<>(); // greater than pivot -- RIGHT side

        // 1. Partitioning the given list into three parts -----
        for (int num: nums) {
            if (num > pivot) {
                greater.add(num);
            } else if (num < pivot) {
                less.add(num);
            } else {
                equal.add(num);
            }
        }

        // 2. Choose the partition to search in -----

        // isInside greater?
        if (k <= greater.size()) {
            return quickSelect(greater, k);
        }
        // isInside less?
        else if (greater.size() + equal.size() < k) {
            return quickSelect(less, k - greater.size() - equal.size());
        }
        // so definitely inside equal
        else
            return pivot; // same as (greater.size() < k) && (greater.size() + equal.size() >= k)


        // or just use if statements like below
        // if (k <= greater.size()) {
        //     return quickSelect(greater, k);
        // }

        // if (greater.size() + equal.size() < k) {
        //     return quickSelect(less, k - greater.size() - equal.size());
        // }

        // return pivot; // or return (greater.size() + equal.size() >= k)? equal.get(0) : -1;
    }







    /**
     * TLE for very large arrays
     * @TimeComplexity O(n) in average and O(n^2) in worst
     * @SpaceComplexity O(1)
     */
    public static int findKthLargestUsingQuickSort2(int[] nums, int k) {
        k = nums.length - k; // new k in ascendingOrder & it's index
        return quickSelect(nums, 0, nums.length - 1, k);
    }
    private static int quickSelect(int[] nums, int l, int r, int k) {
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
            return quickSelect(nums, l, p-1, k);
        } else if (p < k) {
            return quickSelect(nums, p+1, r, k);
        } else {
            return nums[p];
        }
    }






    /**
     * @TimeComplexity O(n) in average and O(n^2) in worst
     * @SpaceComplexity O(1)
     */
    public static int findKthLargestUsingQuickSort3(int[] nums, int k) {
        int targetIdx = nums.length - k; // targetIndex in asc sorted nums arr or new k in ascendingOrder i.e it's index after sort
        return quickSelect3(nums, 0, nums.length - 1, targetIdx);
    }
    // quick sort
    private static int quickSelect3(int[] nums, int left, int right, int targetIdx) {
        if (left == right) {
            return nums[left];
        }

        int pivot = nums[new Random().nextInt(right-left+1)+left]; // or nums[right] or nums[left] - it can be anything in the window
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
            return quickSelect3(nums, left, high, targetIdx);
        } else if (targetIdx >= low) {
            return quickSelect3(nums, low, right, targetIdx);
        } else {
            return nums[targetIdx];
        }
    }






    /**
     * @TimeComplexity O(n) in average and O(n^2) in worst
     * @SpaceComplexity O(1)
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
     * @TimeComplexity O(n) in average and O(n^2) in worst
     * @SpaceComplexity O(1)
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
}


