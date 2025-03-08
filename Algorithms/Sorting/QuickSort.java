package Algorithms.Sorting;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
// import java.util.Random;

/**
 * using PIVOT and take all left array items less than pivot and right as greater than pivot
 * same like merge sort + binary sort middleIndex, it is recursive algorithm, divide pivot and two left and right subarrays, then again each of these sub arrays again be divided into a pivot (of sub-array) and 2 sub-arrays (of sub-array) and so on.
 *
* @author Srinvas Vadige, srinivas.vadige@gmail.com
* @since 21 Sept 2024
*/
public class QuickSort{
    public static void main(String[] args) {
        //int len = 10; int[] items = new int[len]; for (int i = 0; i < len; i++) items[i] = new Random().nextInt(0,len+1);
        int[] items = new int[]{8, 2, 6, 4, 3, 10, 6, 2, 8, 7};

        System.out.println("Quick sort 1 ------------");
        System.out.println("Array before sort: \n" + Arrays.toString(items) + "\n");
        getCurrentTimeStamp();
        quickSort(items, 0, items.length-1);
        getCurrentTimeStamp();
        System.out.println("\nArray after sort: \n" + Arrays.toString(items));

        System.out.println("\n\nQuick sort 2 ------------");
        items = new int[]{3, 2, 4, -1, 1000, 100, 3, 1};
        System.out.println("Array before sort: " + Arrays.toString(items));
        quickSort2(items, 0, items.length-1);
        System.out.println("Array after sort: " + Arrays.toString(items));



        System.out.println("\n\nQuick sort 3 ------------");
        items = new int[]{3, 2, 4, -1, 1000, 100, 3, 1};
        System.out.println("Array before sort: " + Arrays.toString(items));
        quickSort3(items, 0, items.length-1);
        System.out.println("Array after sort: " + Arrays.toString(items));




        System.out.println("\n\nQuick sort 4 ------------");
        items = new int[]{3, 2, 4, -1, 1000, 100, 3, 1};
        System.out.println("Array before sort: " + Arrays.toString(items));
        quickSort4(items, 0, items.length-1);
        System.out.println("Array after sort: " + Arrays.toString(items));




        System.out.println("\n\nQuick sort 5 ------------");
        items = new int[]{3, 2, 4, -1, 1000, 100, 3, 1};
        System.out.println("Array before sort: " + Arrays.toString(items));
        quickSort5(items, 0, items.length-1);
        System.out.println("Array after sort: " + Arrays.toString(items));

    }

    /**
     * ------------ MOVE SMALLER THAN PIVOT VALS TO LEFT ---------------
     * Always consider pivot as last element of the array
     * Do 2 operations when you find the j pointer element is smaller than pivot
     * Operation 1: increment i index i.e i++
     * Operation 2: swap i&j elements
     * i.e moving all pivot small values to left of the fist big num.
     * therefore first big number index is always i+1;
     *
     *     __[0][1][2][3][4] -- indices
     *     i  j
     *
     * move i only if jElement is smaller than pivot
     */
    public static void quickSort(int[] items, int low, int high){

        System.out.println(String.format("--------- sort(low:%s, high: %s) ---------", low, high));

        if (low >= high ) return;

        int i = low-1; //and int j = low; just like _i_[j0][1][2][3][4] -- indices
        int pivot = items[high]; // last element, but pivot can be anything but to be consistent all over the recursions, take the last element of the array or sub-array

        for (int j = low; j <= high; j++) {
            if (items[j] < pivot) {
                i++; // move i only if jElement is smaller than pivot
                swap(items, i, j);
                System.out.println("\nincremented i from "+ (i-1) + " to " + i +" and swapped(i:" +i+ " and j: "+j+")");
            } else System.out.println("\nSkipping");

            System.out.println("Array at j= " + j + " is: " + Arrays.toString(items));
        }

        // Currently pivot index is len-1 but now, it must be i+1. Because up to i, all elements are smaller than pivotVal
        swap(items, i+1, high);
        System.out.println("\nArray at new pivot " + (i+1) + " is: " + Arrays.toString(items) + "\n");

        // skip the i+1 => index current pivot value by skipping the new pivot index
        System.out.println("\nnext child are");
		System.out.println(low + "," + i);
		System.out.println(i+2 + "," + high);
        quickSort(items, low, i);
        quickSort(items, i+2, high); // i+1 is the new current pivot and it's already sorted
    }





    // Same as above quickSort i.e two pointer, sliding window but use partition method
    public static void quickSort2(int[] arr, int low, int high) {
        if (low < high) {
            int pivotIndex = partition2(arr, low, high);
            // Now pivot is at correct sorted position. Now, skip it and sort left and right sections
            quickSort2(arr, low, pivotIndex - 1);
            quickSort2(arr, pivotIndex + 1, high);
        }
    }
    private static int partition2(int[] arr, int low, int high) {
        int pivot = arr[high]; // Choosing the last element as the pivot. And i & j will be traversing the array
        int i = low - 1; // Index for elements smaller than pivot

        for (int j = low; j < high; j++) {
            if (arr[j] < pivot) {
                i++;
                swap(arr, i, j);
            }
        }
        swap(arr, i + 1, high); // Move pivot to correct position
        return i + 1; // Return pivot index
    }






    // lowI, highI as 2p sliding window, but lowI starts from leftI & highI starts from rightI
    public static void quickSort3(int[] nums, int left, int right) {
        if (left < right) {
            int pivotIndex = partition3(nums, left, right);
            quickSort3(nums, left, pivotIndex);  // Sort left partition
            quickSort3(nums, pivotIndex + 1, right);  // Sort right partition
        }
    }
    private static int partition3(int[] nums, int left, int right) {
        int pivot = nums[right]; // don't use nums[left]
        int low = left, high = right;

        while (low <= high) {
            // Move low forward until finding an element >= pivot
            while (low <= high && nums[low] < pivot) {
                low++;
            }
            // Move high backward until finding an element <= pivot
            while (low <= high && nums[high] > pivot) {
                high--;
            }
            if (low <= high) {
                // Swap misplaced elements
                swap(nums, low, high);
                // Move pointers
                low++;
                high--;
            }
        }
        return high; // Correct partition index
    }







    // public static void quickSort8(int[] nums, int left, int right) {
    //     if (left < right) {
    //         int pivotIndex = partition8(nums, left, right);
    //         quickSort8(nums, left, pivotIndex);  // Sort left partition (up to pivot)
    //         quickSort8(nums, pivotIndex + 1, right);  // Sort right partition (after pivot)
    //     }
    // }

    // private static int partition8(int[] nums, int left, int right) {
    //     int pivot = nums[right]; // Use the rightmost element as the pivot
    //     int low = left, high = right; // high starts just before the pivot

    //     while (low <= high) {
    //         // Move low forward until finding an element >= pivot
    //         while (low <= high && nums[low] < pivot) {
    //             low++;
    //         }
    //         // Move high backward until finding an element <= pivot
    //         while (low <= high && nums[high] > pivot) {
    //             high--;
    //         }
    //         if (low <= high) {
    //             // Swap misplaced elements
    //             swap(nums, low, high);
    //             low++;
    //             high--;
    //         }
    //     }
    //     // Swap pivot into correct position (just after the high pointer)
    //     swap(nums, low, right);
    //     return low;  // Return the pivot index
    // }







    // Use i,j as two pointers sliding window but i starts from lowI and j starts from highI
    private static void quickSort4(int[] arr, int low, int high) {
        if (low < high) {
            int pivotIndex = partition4(arr, low, high);
            quickSort4(arr, low, pivotIndex - 1);
            quickSort4(arr, pivotIndex + 1, high);
        }
    }
    private static int partition4(int[] nums, int low, int high) {
        int pivot = nums[low];  // Choosing the first element as pivot
        int i = low + 1; // Index for elements smaller than pivot
        int j = high; // Index for elements greater than pivot

        while (true) {
            while (i <= j && nums[i] <= pivot) i++;
            while (i <= j && nums[j] > pivot) j--;
            if (i <= j) {
                // Swap elements at indices i and j
                swap(nums, i, j);
            } else {
                // Swap pivot element with element at index j
                nums[low] = nums[j];
                nums[j] = pivot;
                return j;
            }
        }






    // int pivot = nums[low];  // Choosing the first element as pivot
    //     while (low <= high) {
    //         while (low <= high && nums[low] < pivot) low++;
    //         while (low <= high && nums[high] > pivot) high--;

    //         if (low <= high) { // Swap misplaced elements
    //             swap(nums, low, high);
    //             low++;
    //             high--;
    //         }
    //     }
    //     return low; // Return the partition index
    }






    public static void quickSort5(int[] arr, int low, int high) {
        if (low < high) {
            int pivotIndex = partition5(arr, low, high);  // Get pivot index
            quickSort5(arr, low, pivotIndex);  // Sort left partition
            quickSort5(arr, pivotIndex + 1, high);  // Sort right partition
        }
    }
    private static int partition5(int[] arr, int low, int high) {
        int pivot = arr[low]; // Choosing the first element as pivot
        int i = low - 1, j = high + 1; // Hoare's partition requires out-of-bounds start

        while (true) {
            // Move i to the right until an element >= pivot is found
            while (++i < high && arr[i] < pivot);

            // Move j to the left until an element <= pivot is found
            while (--j > low && arr[j] > pivot);

            if (i >= j) return j; // Return the partition index

            // Swap misplaced elements
            swap(arr, i, j);
        }
    }






    public static void quickSort6(int[] arr, int low, int high) {
        if (low < high) {
            int pivotIndex = partition6(arr, low, high);  // Get pivot index
            quickSort6(arr, low, pivotIndex);  // Sort left partition
            quickSort6(arr, pivotIndex + 1, high);  // Sort right partition
        }
    }
    private static int partition6(int[] arr, int low, int high) {
        int pivot = arr[low];
        int i = low - 1, j = high + 1;
        while (true) {
            // Move i to the right until an element >= pivot is found
            do {
                i++;
            } while (arr[i] < pivot);

            // Move j to the left until an element <= pivot is found
            do {
                j--;
            } while (arr[j] > pivot);

            if (i >= j) return j; // Return partition index

            // Swap arr[i] and arr[j] if they are on the wrong side
            swap(arr, i, j);
        }
    }




    static void swap(int[] items, int a, int b){
        int temp = items[a];
        items[a]=items[b];
        items[b]=temp;
    }


    public static void getCurrentTimeStamp() {
        System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date()));
    }

}