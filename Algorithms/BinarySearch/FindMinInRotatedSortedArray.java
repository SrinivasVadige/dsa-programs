package Algorithms.BinarySearch;

/**
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 12 Feb 2025
 */
public class FindMinInRotatedSortedArray {
    public static void main(String[] args) {
        int[] nums = {4,5,1,2,3};
        System.out.println("findMin(nums) => " + findMin(nums));
        System.out.println("findMinMyApproach(nums) => " + findMinMyApproach(nums));
    }

    public static int findMin(int[] nums) {
        int l = 0, r = nums.length - 1, mid = 0;
        while (l < r) {
            mid = (l+r) / 2;
            if (nums[mid] > nums[r]) l = mid + 1; // if right not sorted? then pivot point is somewhere in the right portion
            else r = mid; // to shrink width and also to break the loop as while (l<r)
        }
        return nums[l];
    }

    public static int findMinMyApproach(int[] nums) {
        int l=0, r=nums.length-1;
        int min = Integer.MAX_VALUE;

        while(l<=r) {
            int mid = (l+r)/2;

            if(nums[l]<=nums[mid]) { // if left sorted
                if (min > nums[l]) min=nums[l];
                l = mid +1;
            } else { // if left not sorted then right is sorted
                if (min > nums[mid]) min=nums[mid];
                r = mid-1;
            }
        }

        return min;
    }
}
