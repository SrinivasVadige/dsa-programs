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
        System.out.println("bucketSortForNonNegativesWithRange => " + Arrays.toString(nums));

        nums = new int[]{2, 0, 2, 1, 1, 0, -1, -500};
        findKthLargestUsingMinMaxRangeBucketSort(nums);
        System.out.println("findKthLargestUsingMinMaxRangeBucketSort => " + Arrays.toString(nums));

        nums = new int[]{2, 0, 2, 1, 1, 0, -1, -500};
        bucketSort(nums);
        System.out.println("bucketSort => " + Arrays.toString(nums));

        nums = new int[]{2, 0, 2, 1, 1, 0, -1, -500};
        bucketSort2(nums);
        System.out.println("bucketSort2 => " + Arrays.toString(nums));
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




    /**
     * @TimeComplexity O(n+r) -> n = nums.length, r = range of nums == max-min+1 == Bucket size
     * @SpaceComplexity O(r)
     *
     * How's the Bucket Sort work with negative numbers?
     * cause "num - minValue" will never be negative, it will always be >= 0
     * So, we can use it as an index in the count array.
     *
     * NOTE:
     * Java int range: Integer.MIN_VALUE to Integer.MAX_VALUE
     * Minimum: -2³¹ = -2,147,483,648 ≈ −2 × 10⁹
     * Maximum: 2³¹ − 1 = 2,147,483,647 ≈ 2 × 10⁹
     *
     * So, this bucketSort works when  -10⁹ <= nums[i] <= 10⁹. Because if the range is more than that int[] will not be able to hold the values
    */
    public static void findKthLargestUsingMinMaxRangeBucketSort(int[] nums) {
        int minValue = Arrays.stream(nums).min().getAsInt();
        int maxValue = Arrays.stream(nums).max().getAsInt();

        int[] bucket = new int[maxValue - minValue + 1]; // bucket size

        // bucket sort with dupes
        for (int num : nums) {
            bucket[num - minValue]++; // if num==-500 then num-minValue will be 0 and if num==2 then num-minValue will be 502
        }

        int i = nums.length - 1; // fill the nums[] in reverse order
        for(int bucketIndex = bucket.length - 1; bucketIndex >= 0; bucketIndex--) { // descending order loop --- i.e num=bucketIndex+minValue is the largest number
            if (bucket[bucketIndex] == 0) continue; // skip empty buckets
            int num = bucketIndex + minValue;
            int numCount = bucket[bucketIndex];
            while (numCount-- > 0) {
                nums[i--] = num; // fill the nums[] with sorted order
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
