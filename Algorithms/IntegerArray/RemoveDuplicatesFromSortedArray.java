package Algorithms.IntegerArray;

import java.util.Arrays;

/**
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 30 May 2025
 * @link 26. Remove Duplicates from Sorted Array https://leetcode.com/problems/remove-duplicates-from-sorted-array/
 * @topic Array, Two Pointers
 *
 * Given an integer array nums sorted in non-decreasing order
 */
public class RemoveDuplicatesFromSortedArray {

    public static void main(String[] args) {
        int[] nums = {0,0,1,1,1,2,2,3,3,4};
        System.out.println("arr before : " + Arrays.toString(nums));
        int k = removeDuplicates(nums);
        System.out.println("arr after : " + Arrays.toString(Arrays.copyOf(nums, k)));
    }


    /**
     * left pointer will & maintain the new unique value position ---> output param
     * right pointer will trav the whole array
     * and assign r value to l value when we say the 'r' unique val for the first time ---> if(nums[r-1] != nums[r]) nums[l++] = nums[r];
        Given =
        [0, 0, 1, 1, 1, 2, 2, 3, 3, 4]
            r
            l

        [0, 0, 1, 1, 1, 2, 2, 3, 3, 4]
            l  r

        [0, 1, 1, 1, 1, 2, 2, 3, 3, 4]
               l  r

        [0, 1, 1, 1, 1, 2, 2, 3, 3, 4]
               l        r

        [0, 1, 2, 1, 1, 2, 2, 3, 3, 4]
                  l        r

        [0, 1, 2, 1, 1, 2, 2, 3, 3, 4]
                  l           r

        [0, 1, 2, 3, 1, 2, 2, 3, 3, 4]
                     l           r

        [0, 1, 2, 3, 1, 2, 2, 3, 3, 4]
                     l              r

        [0, 1, 2, 3, 4, 2, 2, 3, 3, 4]
                        l              r

     */
    public static int removeDuplicates(int[] nums) {
        if (nums.length == 0) return 0;

        int l = 1;
        for (int r = 1; r < nums.length; r++) {
            if (nums[r-1] != nums[r]) { // prev != curr
                nums[l++] = nums[r];
            }
        }
        return l;
    }








    /**
     * BY MARKING THE DUPS
     *
        Given = [0, 0, 1, 1, 1, 2, 2, 3, 3, 4]

        mark the dups in first for loop
                [0,-1, 1,-1,-1, 2,-1, 3,-1, 4]

        In second while loop, maintain nums[i]==-1 and nums[j]=num --> then swap
                [0,-1, 1,-1,-1, 2,-1, 3,-1, 4]
                    i  j
                [0, 1,-1,-1,-1, 2,-1, 3,-1, 4]
                       i        j
                [0, 1, 2,-1,-1,-1,-1, 3,-1, 4]
                          i           j
                [0, 1, 2, 3,-1,-1,-1,-1,-1, 4]
                             i              j
                [0, 1, 2, 3, 4,-1,-1,-1,-1, -1]
                             i              j
     */
    public static int removeDuplicatesMyApproach(int[] nums) {
        int n = nums.length;
        // mark dups
        int num = nums[0];
        for(int i=1; i<n; i++) {
            if(nums[i]==num) nums[i] = Integer.MAX_VALUE;
            else num = nums[i];
        }

        // initial i & j positions
        int i=0;
        while(i<n && nums[i] != Integer.MAX_VALUE) i++;
        int j=i+1;

        while(i<n && j<n) {
            while(i<n && nums[i]!=Integer.MAX_VALUE) i++;
            while(j<n && nums[j]==Integer.MAX_VALUE) j++;
            if(i<n && j<n) swapAndMark(nums, i, j);
            i++;
            j++;
        }

        int k = 0;
        for(int x: nums) {
            if(x==Integer.MAX_VALUE) break;
            k++;
        }
        return k;
    }
    private static void swapAndMark(int[] nums, int i, int j) {
        nums[i] = nums[j];
        nums[j] = Integer.MAX_VALUE;
    }






    /**
     *

        i-while-loop
        need nums[i-1]==nums[i]

        j-while-loop
        need nums[i] < nums[j]

        Given =
        [0, 0, 1, 1, 1, 2, 2, 3, 3, 4]
            i  j

        swap i and j
        i++
        j++
        if (nums[i-1] > nums[i]) nums[i]=nums[i-1]; or if(i<n) nums[i]=nums[i-1];

        [0, 1, 1, 1, 1, 2, 2, 3, 3, 4]
               i  j
        while loop
        [0, 1, 1, 1, 1, 2, 2, 3, 3, 4]
               i        j
        [0, 1, 2, 1, 1, 2, 2, 3, 3, 4]
               i        j

        swap
        i++
        j++
        if (nums[i-1] > nums[i]) nums[i]=nums[i-1]; or if(i<n) nums[i]=nums[i-1];

        [0, 1, 2, 2, 1, 2, 2, 3, 3, 4]
                  i           j
        [0, 1, 2, 3, 3, 2, 2, 3, 3, 4]
                     i              j
        [0, 1, 2, 3, 4, 2, 2, 3, 3, 4] x
                        i              j

     */
    public int removeDuplicatesMyApproach2(int[] nums) {
        int n=nums.length, i=1, j=1;
        while(j<n) {
            while(i<n && nums[i-1]!=nums[i]) i++;
            if(i==n) break;
            while(j<n && nums[i]>=nums[j]) j++;
            if(j==n) break;

            // swap
            nums[i]=nums[j];
            i++;
            j++;
            if(i<n) nums[i]=nums[i-1];
        }

        return i;
    }
}