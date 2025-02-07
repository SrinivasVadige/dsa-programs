package Algorithms.BinarySearch;

import java.util.Arrays;

/**
 * Find First and Last Position of Element in Sorted Array
 *
 *
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 06 Feb 2025
 */
public class FindElementPositionsInDupSortedArray {
    public static void main(String[] args) {
        int[] nums = {5,7,7,8,8,10};
        int target = 8;
        System.out.println("searchRange(nums, target) => " + Arrays.toString(searchRange(nums, target)));
        System.out.println("searchRangeMyApproach(nums, target) => " + Arrays.toString(searchRangeMyApproach(nums, target)));
    }

    public static int[] searchRange(int[] nums, int target) {
        int[] res = new int[]{-1, -1};
        res[0] = findFirst(nums, target);
        if (res[0] == -1) return res; // if firstOccurrence not found then we don't have lastOccurrence
        res[1] = findLast(nums, target);
        return res;
    }

    public static int findFirst(int[] nums, int target) {
        int start = 0, end = nums.length - 1, res = -1;
        while (start <= end) {
            int mid = start + (end - start) / 2; // or (end + start) / 2;
            if (nums[mid] == target) {
                res = mid;
                end = mid - 1;
            } else if (nums[mid] < target) start = mid + 1;
            else end = mid - 1;
        }
        return res;
    }

    public static int findLast(int[] nums, int target) {
        int start = 0, end = nums.length - 1, res = -1;
        while (start <= end) {
            int mid = start + (end - start) / 2;
            if (nums[mid] == target) {
                res = mid;
                start = mid + 1;
            } else if (nums[mid] < target) start = mid + 1;
            else end = mid - 1;
        }
        return res;
    }



    public static int[] searchRangeMyApproach(int[] nums, int target) {
        int l=0, r=nums.length-1;

        while(l<=r) {
            int mid = (l+r)/2;
            if (target == nums[mid]) {
                l = r = mid;
                break;
            } else if (target > nums[mid]) {
                l = mid + 1;
                if (l < nums.length && target == nums[l]){
                    r = l;
                    break;
                }
                // optional: if (l > nums.length-1) continue; int num = nums[l]; while (l+1 < nums.length && nums[l+1] == num) l++;
            }
            else {
                r = mid-1;
                if (r >= 0 && target == nums[r]){
                    l = r;
                    break;
                }
                // optional: if (r < 0) continue; int num = nums[r]; while (r-1 >= 0 && nums[r-1] == num) r--;
            }
        }

        if (l>r) return new int[]{-1, -1}; // not found

        while (l-1 >= 0 && nums[l-1] == target) l--;
        while (r+1 < nums.length && nums[r+1] == target) r++;

        return new int[]{l,r};
    }
}
