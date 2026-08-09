package Algorithms.IntegerArray;

import java.util.Arrays;

/**
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 29 May 2025
 * @link 27. Remove Element https://leetcode.com/problems/remove-element/
 * @topics Array, Two Pointers
 */
public class RemoveElement {
    public static void main(String[] args) {
        int[] nums = {0,1,2,2,3,0,4,2};
        int val = 2;
        System.out.println("Given Array          : " + Arrays.toString(nums) + " and val: " + val);
        int k = removeElementMyApproach(nums, val);
        System.out.println("Array after removing : " + Arrays.toString(nums));
        Arrays.sort(nums, 0, k);
        System.out.println("Array after sorting  : " + Arrays.toString(nums));
    }


    /**
        i & j pointers

        start i=0 & maintain i at val==nums[i]
        and start j=m-1 & maintain j at val!=nums[i]

        Given Array          : [0, 1, 2, 2, 3, 0, 4, 2] and val: 2
        Array after removing : [0, 1, 4, 0, 3], 2, 2, 2] and k: 5
        Array after sorting  : [0, 0, 1, 3, 4], 2, 2, 2]
     */
    public static int removeElementMyApproach(int[] nums, int val) {
        int n=nums.length, i=0, j=n-1;
        while(i<j){
            while(i<n && nums[i]!=val) i++;
            while(j>0 && nums[j]==val) j--;
            if(i<j) swap(nums, i, j); // swap not needed, in this problem as they don't consider eles more than k size
            i++;
            j--;
        }
        int k=0;
        for(int x: nums) {
            if(x==val) break;
            k++;
        }
        return k;
    }
    private static void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }





    /**
        Given Array          : [0, 1, 2, 2, 3, 0, 4, 2] and val: 2
        Array after removing : [0, 1, 3, 0, 4], 0, 4, 2] and k: 5
        Array after sorting  : [0, 0, 1, 3, 4], 0, 4, 2]
     */
    public static int removeElement(int[] nums, int val) {
        int k=0;
        for(int i=0; i<nums.length; i++) {
            if(nums[i] != val) {
                nums[k] = nums[i];
                k++;
            }
        }
        return k;
    }





    /**
     * @TimeComplexity O(n^2)
     * @SpaceComplexity O(1)
     */
    public int removeElementBruteForce(int[] nums, int val) {
        for(int j=nums.length-1; j>=0; j--) {
            if(nums[j] != val) {
                for(int i=0; i<nums.length; i++) {
                    if(nums[i] == val && (i <= j)) {
                        int temp = nums[i];
                        nums[i] = nums[j];
                        nums[j] = temp;
                    }
                }
            }
        }

        return Math.toIntExact(Arrays.stream(nums).filter(e -> e != val).count()); // Math.toIntExact() is same as (int) cast
    }
}
