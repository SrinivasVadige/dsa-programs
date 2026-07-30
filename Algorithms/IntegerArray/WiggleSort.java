package Algorithms.IntegerArray;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 30 July 2026
 * @link 280. Wiggle Sort <a href="https://leetcode.com/problems/wiggle-sort/">LeetCode link</a>
 * @description Reorder given array in such a manner that every element at an odd index is greater than or equal to its adjoining elements at even indices. [3,5,2,1,0,4] => [0,5,1,4,2,3]. Like heart beat spikes
 * @topics Array, Greedy, Sorting
 * @companies Google(4), Amazon(2), TikTok(2), Myntra(2)
 */
public class WiggleSort {
    public static void main(String[] args) {
        int[] nums = {3,5,2,1,0,4};

        wiggleSortUsingSortAndList(Arrays.copyOf(nums, nums.length));
    }


    /**
     * @TimeComplexity O(nlogn)
     * @SpaceComplexity O(n)
     */
    public static void wiggleSortUsingSortAndList(int[] nums) {
        int n = nums.length;
        Arrays.sort(nums);
        int l = 0, r = n-1;

        List<Integer> list = new ArrayList<>();
        while (l<r) {
            list.add(nums[l++]);
            list.add(nums[r--]);
        }
        if (l==r) {
            list.add(nums[l]);
        }

        for(int i=0; i<n; i++) {
            nums[i] = list.get(i);
        }
    }




    /**
     * @TimeComplexity O(nlogn)
     * @SpaceComplexity O(1)
     */
    public static void wiggleSortUsingSort(int[] nums) {
        Arrays.sort(nums);
        for (int i = 1; i < nums.length - 1; i += 2) {
            swap(nums, i, i + 1);
        }
    }
    private static void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }





    /**

        [3,5,2,6,1,1,1,4]
        [3,5,2,1,6,4]

        even i = i <= i+1
        odd  i = i >= i+1
         .   .   .
         0 1 2 3 4 5
        [6,6,5,6,3,8]

     * @TimeComplexity O(n)
     * @SpaceComplexity O(1)
     */
    public static void wiggleSortUsingGreedyLinearTime1(int[] nums) {
        int n = nums.length, l = 0;
        while(l < n) {
            if (!isValid(nums, l, l+1)) swap(nums, l, l+1);
            l++;
        }
    }
    private static boolean isValid(int[] nums, int l, int r) {
        if (l == nums.length-1) return true;
        return (l % 2 == 0) ? nums[l] < nums[r] : nums[l] > nums[r];
    }




    public static void wiggleSortUsingGreedyLinearTime2(int[] nums) {
        for (int i = 0; i < nums.length - 1; i++) {
            if ( (i % 2 == 0) && nums[i] > nums[i + 1] || (i % 2 == 1) && nums[i] < nums[i + 1] ) {
                swap(nums, i, i + 1);
            }
        }
    }



    public static void wiggleSortUsingGreedyLinearTime3(int[] nums) {
        for (int i = 0; i < nums.length - 1; i++) {
            if ((i % 2 == 0) == (nums[i] > nums[i + 1])) {
                swap(nums, i, i+1);
            }
        }
    }
}
