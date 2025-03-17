package Algorithms.Sorting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Summary:
 * Step 1: Create buckets.
 * Step 2: Distribute elements.
 * Step 3: Sort each bucket.
 * Step 4: Merge all buckets.
 *
* @author Srinvas Vadige, srinivas.vadige@gmail.com
* @since 21 Sept 2024
*/
public class BucketSort {

    public static void main(String[] args) {
        int[] nums = {2, 0, 2, 1, 1, 0};
        bucketSortForNonNegativesWithRange(nums);
        System.out.println("nums => " + Arrays.toString(nums));

        nums= new int[]{2, 0, 2, 1, 1, 0, -1, -500};
        bucketSort(nums);
        System.out.println("nums => " + Arrays.toString(nums));

        nums= new int[]{2, 0, 2, 1, 1, 0, -1, -500};
        bucketSort2(nums);
        System.out.println("nums => " + Arrays.toString(nums));
    }

    public static void bucketSortForNonNegativesWithRange(int[] nums) {
        int[] buckets = new int[nums.length]; //
        for (int num : nums) {
            buckets[num]++; // allocate the bucket for each num
        }
        int i = 0;
        for (int j = 0; j < buckets.length; j++) {
            for (int k = 0; k < buckets[j]; k++) {
                nums[i++] = j;
            }
        }
    }

    public static void bucketSortForNegativesWithRange(int[] nums) {
        int[] buckets = new int[nums.length]; //
        for (int num : nums) {
            buckets[num + nums.length]++; // allocate the bucket for each num
        }
        int i = 0;
        for (int j = 0; j < buckets.length; j++) {
            for (int k = 0; k < buckets[j]; k++) {
                nums[i++] = j - nums.length;
            }
        }
    }


    /**
     * if the Integer.MIN_VALUE <= num <= Integer.MAX_VALUE
     *
     * @TimeComplexity Best/Average Case: O(n + k), where k is bucket count. And Worst Case: O(n²), if all elements fall into one bucket and a slow sort is used inside (like insertion sort).
     * @SpaceComplexity O(n)
     */
    public static void bucketSort(int[] arr) {
        if (arr.length == 0) return;

        // 1. Find maximum and minimum values in the array
        int maxValue = arr[0];
        int minValue = arr[0];
        for (int num : arr) {
            if (num > maxValue) maxValue = num;
            if (num < minValue) minValue = num;
        }

        // 2. Create buckets and distribute elements
        int bucketCount = (maxValue - minValue) / arr.length + 1; // k
        @SuppressWarnings("unchecked")
        List<Integer>[] buckets = new List[bucketCount];
        for (int i = 0; i < bucketCount; i++) {
            buckets[i] = new ArrayList<>();
        }

        // 3. Distribute elements into buckets
        for (int num : arr) {
            int bucketIndex = (num - minValue) / arr.length;
            buckets[bucketIndex].add(num);
        }

        // 4. Sort individual buckets
        for (List<Integer> bucket : buckets) {
            Collections.sort(bucket);
        }

        // 5. Merge buckets back into original array
        int index = 0;
        for (List<Integer> bucket : buckets) {
            for (int num : bucket) {
                arr[index++] = num;
            }
        }
    }






    /**
     * NOT WORKING
     * Here tried to use int[] buckets instead of List<Integer>[], but it doesn't work
     */
    public static void bucketSort2(int[] arr) {
        if (arr.length == 0) return;

        // Step 1: Find maximum and minimum values in the array
        int maxValue = arr[0];
        int minValue = arr[0];
        for (int num : arr) {
            if (num > maxValue) maxValue = num;
            if (num < minValue) minValue = num;
        }

        // Step 2: Calculate range and shift value to handle negative values
        int range = maxValue - minValue + 1;  // +1 to include max value
        int shift = Math.abs(minValue);  // Make sure all values become positive

        // Step 3: Initialize int[] array (buckets) to count occurrences
        int bucketCount = (range / arr.length) + 1; // Calculate number of buckets
        int[] buckets = new int[bucketCount];

        // Step 4: Distribute elements into buckets (counting occurrences)
        for (int num : arr) {
            int bucketIndex = (num + shift) / arr.length; // Use shifted value to index
            buckets[bucketIndex]++;
        }

        // Step 5: Rebuild the sorted array using bucket counts
        int index = 0;
        for (int i = 0; i < bucketCount; i++) {
            while (buckets[i] > 0) {
                arr[index++] = i * arr.length + minValue + shift;
                buckets[i]--;
            }
        }
    }

}
