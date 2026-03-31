package Algorithms.BinarySearch;

/**
 * Given a sorted array of distinct integers and a target value, return the index if the target is found. If not, return the index where it would be if it were inserted in order
 *
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 08 Feb 2025
 * @link 35. Search Insert Position <a href="https://leetcode.com/problems/search-insert-position/">LeetCode link</a>
 * @topics Array, Binary Search
 * @companies Amazon(10), Google(6), Grammarly(3), Meta(2), Microsoft(2), Bloomberg(2), TCS(5), IBM(2), Zoho(2), Accenture(2), Yandex(2), Cognizant(2)

 @see DataStructures.BinarySearch
 */
public class SearchInsertPosition {
    public static void main(String[] args) {
        int[] nums = {1,3,5,6};
        int target = 5;
        System.out.println("searchInsert(nums, target) => " + searchInsert1(nums, target));
    }


    /**
     * @TimeComplexity O(log n)
     * @SpaceComplexity O(1)
     */
    public static int searchInsert1(int[] nums, int target) {
        int start = 0, end = nums.length - 1, mid = 0;
        while (start <= end) {
            mid = start + (end - start) / 2;
            if (nums[mid] == target) return mid;
            else if (nums[mid] < target) start = mid + 1;
            else end = mid - 1;
        }
        return start; // return the index where it would be if it were inserted in order
    }



    /**
     * @TimeComplexity O(log n)
     * @SpaceComplexity O(1)
     */
    public static int searchInsert2(int[] nums, int target) {
        int l = 0, r = nums.length-1;

        while(l<=r) {
            int mid = l + (r-l)/2;
            if (nums[mid] < target) l = mid+1;
            else r = mid-1;
        }
        return l;
    }
}
