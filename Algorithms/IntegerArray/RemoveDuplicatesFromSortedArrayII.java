package Algorithms.IntegerArray;

import java.util.Arrays;

/**
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 08 June 2025
 * @link 80. Remove Duplicates from Sorted Array II https://leetcode.com/problems/remove-duplicates-from-sorted-array-ii/
 * @topics Array, Two Pointers
 * @description Given a sorted array nums, remove the duplicates in-place such that duplicates appeared at most twice and return the new length
 */
public class RemoveDuplicatesFromSortedArrayII {
    public static void main(String[] args) {
        int[] nums = {1,1,1,2,2,2,3,3};
        System.out.println("Array before removeDuplicates() " + Arrays.toString(nums));
        int newLen = removeDuplicates(nums);
        System.out.println("Array after removeDuplicates() with newLen = " + newLen + " => " + Arrays.toString(nums));

        nums = new int[]{1,1,1,2,2,2,3,3};
        System.out.println("Array before removeDuplicates() " + Arrays.toString(nums));
        newLen = removeDuplicatesMyApproach(nums);
        System.out.println("Array after removeDuplicatesMyApproach() with newLen = " + newLen + " => " + Arrays.toString(nums));
    }

    /**
     * Given
         0 1 2 3 4 5 6 7
        [1,1,1,2,2,2,3,3]

        Use two pointers, k and i

        i=2
        [1,1,1,2,2,2,3,3] ---> nums[k - 2] != nums[i] -> 1 != 1
             k
             i

        i=3
        [1,1,1,2,2,2,3,3] ---> nums[k - 2] != nums[i] -> 1 != 2 -> "swap" && k++
             k
               i

        i=4
        [1,1,2,2,2,2,3,3] ---> nums[k - 2] != nums[i] -> 1 != 2 -> "swap" && k++
               k
                 i

        i=5
        [1,1,2,2,2,2,3,3] ---> nums[k - 2] != nums[i] -> 2 != 2
                 k
                   i

        i=6
        [1,1,2,2,2,2,3,3] ---> nums[k - 2] != nums[i] -> 2 != 3 -> "swap" && k++
                 k
                     i

        i=7
        [1,1,2,2,3,2,3,3] ---> nums[k - 2] != nums[i] -> 2 != 3 -> "swap" && k++
                   k
                       i

        i=8 - end of array
        [1,1,2,2,3,3,3,3]
                     k
                          i
    */
    public static int removeDuplicates(int[] nums) {
        int k = 2; // Start placing elements at the third position
        if (nums.length <= k) return nums.length;

        for (int i=2; i < nums.length; i++) {
            // If current element differs from element two positions back, include it
            if (nums[i] != nums[k - 2]) {
                nums[k] = nums[i];
                k++;
            }
        }
        return k;
    }





    /**
    same number can appear once or twice but not more than twice

    Given
    [1,1,1,1,2,2,3]



    START A PARENT WHILE LOOP (r<n) -----
    l
    need (l-1 < l) || (l-2 == l-1 == l)
    while( l<n && nums[l-1]==nums[l] && l>1?(nums[l-2]<=nums[l]):true) l++;

    r
    need (r-1 != r) || (r-1==r && r-2 != r)
    while(r<n && ((r-1 != r) || (r-1==r && r-2 != r))) r++;

    swap
    n[l]=n[r]
    l++
    r++

    l=2, r=3;

    l-loop
    [1,1,1,1,2,2,3]
         l r

    r-loop
    [1,1,1,1,2,2,3]
         l   r

    swap && increment
    [1,1,2,1,2,2,3]
           l   r

    l-loop
    [1,1,2,1,2,2,3]
           l   r

    r-loop
    [1,1,2,1,2,2,3]
           l   r

    swap && increment
    [1,1,2,2,2,1,3]
             l   r

    l-loop
    [1,1,2,2,2,1,3]
             l   r

    r-loop
    [1,1,2,2,2,1,3]
             l   r

    swap && increment
    [1,1,2,2,3,1,3]
               l   r
     */
    public static int removeDuplicatesMyApproach(int[] nums) {
        int n = nums.length, l=2, r=3;
        if(n==2) return 2;
        while(l<n && r<n) {

            while(l<n && !(nums[l-2] == nums[l] || nums[l-1] > nums[l])) l++;
            // or
            // while(l<n) {
            //     if(nums[l-2]==nums[l] || nums[l-1]>nums[l]) break;
            //     l++;
            // }

            if(l>r) r=l+1;
            while(r<n && (nums[l]==nums[r] || nums[l-2]==nums[r]) ) r++;

            if(l<n && r<n) {
                int temp = nums[l];
                nums[l]=nums[r];
                nums[r]=temp;
            }

            l++;
            r++;
        }

        int k = 2;
        for(int i=2; i<n; i++) {
            if(nums[i-1]>nums[i] || nums[i-2]==nums[i-1] && nums[i-1]==nums[i]) break;
            k++;
        }
        return k;
    }
}
