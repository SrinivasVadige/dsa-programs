package Algorithms.BinarySearch;

/**
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 05 Feb 2025
 */
public class SearchInRotatedSortedArray {

    public static void main(String[] args) {
        int[] nums = {4,5,0,1,2,3};
        int target = 1;
        System.out.println("binarySearch(nums, target) => " + binarySearch(nums, target));
        System.out.println("binarySearchUsingPivot(nums, target) => " + binarySearchUsingPivot(nums, target));
        System.out.println("normalSearch(nums, target) => " + normalSearch(nums, target));

    }

    // {4,5,0,1,2,3} => {4,5,0}, {1,2,3}
    public static int binarySearch(int[] nums, int target) {
        int start = 0, end = nums.length - 1;
        while (start <= end) {
            int mid = start + (end - start) / 2; // or (start + end) / 2;
            if (nums[mid] == target) return mid; // 2 portions => left{start, mid} and right{mid+1, end}
            if (nums[start] <= nums[mid]) { // isLeftPortionSorted? && pivot is not found & it's in right portion
                if (target >= nums[start] && target < nums[mid]) end = mid - 1; // target is in left portion
                else start = mid + 1;
            } else { // if left portion is not sorted then right portion is sorted
                if (target > nums[mid] && target <= nums[end]) start = mid + 1; // target is in right portion
                else end = mid - 1;
            }
        }
        return -1;
    }

    public static int binarySearchUsingPivot(int[] nums, int target) {
        if (nums.length==0) return -1;
        if (nums.length==1) return nums[0]==target?0:-1;

        int start = -1; // actualZeroIndex or pivot index
        int end = nums.length-1;
        for (int i=1; i<nums.length; i++) {
            if (nums[i-1] > nums[i]) start = i; // pivot
        }
        if (start == -1) start = 0; // i.e not rotated

        // in rotated array {4,5,6,7,0,1,2}, if target > 2 that means target is before pivot index and update the start and end indices
        if (target > nums[end]) {
            end = start-1;
            start = 0;
        }

        // now we have two portions and do binary search accordingly
        while(start<=end) {
            int mid = (start+end)/2;
            if (target==nums[mid]) return mid;
            else if (target > nums[mid]) start = mid+1;
            else end = mid-1;
        }

        return -1;
    }

    public static int normalSearch(int[] nums, int target) {
        for(int i=0, j=nums.length-1-i; i<=j; i++, j--){
            if(nums[i] == target) return i;
            if(nums[j] == target) return j;
        }
        return -1;
    }


}
