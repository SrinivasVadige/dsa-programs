package Algorithms.BinarySearch;

/**
 * Given a sorted array of distinct integers and a target value, return the index if the target is found. If not, return the index where it would be if it were inserted in order
 *
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 08 Feb 2025
 */
public class SearchInsertPosition {
    public static void main(String[] args) {
        int[] nums = {1,3,5,6};
        int target = 5;
        System.out.println("searchInsert(nums, target) => " + searchInsert(nums, target));
    }

    public static int searchInsert(int[] nums, int target) {
        int start = 0, end = nums.length - 1, mid = 0;
        while (start <= end) {
            mid = start + (end - start) / 2;
            if (nums[mid] == target) return mid;
            else if (nums[mid] < target) start = mid + 1;
            else end = mid - 1;
        }
        return start; // return the index where it would be if it were inserted in order
    }
}
