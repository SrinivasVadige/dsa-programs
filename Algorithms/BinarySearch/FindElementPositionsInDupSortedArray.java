package Algorithms.BinarySearch;

import java.util.Arrays;

/**
 * Find First and Last Position of Element in Sorted Array
 *
 *
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 06 Feb 2025
 * @link 34. Find First and Last Position of Element in Sorted Array <a href="https://leetcode.com/problems/find-first-and-last-position-of-element-in-sorted-array/">LeetCode link</a>
 * @topics Array, Binary Search
 * @companies amazon, google, microsoft, linkedin, applied, instacart, bloomberg, goldman, apple, oracle, tiktok, splunk, atlassian, capgemini, airtel, adobe, uber, tinkoff, yahoo, accenture, paypal, tcs, de, pinterest, zillow
 */
public class FindElementPositionsInDupSortedArray {
    public static void main(String[] args) {
        int[] nums = {5,7,7,8,8,10};
        int target = 8;
        System.out.println("searchRange(nums, target) => " + Arrays.toString(searchRange(nums, target)));
        System.out.println("searchRangeMyApproach(nums, target) => " + Arrays.toString(searchRangeMyApproach(nums, target)));
    }

    public static int[] searchRange(int[] nums, int target) {
        int[] res = new int[2];
        res[0] = binarySearch(nums, target, true);
        res[1] = binarySearch(nums, target, false);
        return res;
    }
    // this is the combination of below #findFirst and #findLast methods
    private static int binarySearch(int[] nums, int target, boolean isFirst) {
        int n = nums.length, l = 0, r = n-1, res = -1, mid;
        while(l<=r) {
            mid = l + (r-l)/2;
            if(nums[mid]==target) {
                res = mid;
                if(isFirst) {
                    r=mid-1;
                } else {
                    l=mid+1;
                }
            } else if(nums[mid] < target) {
                l = mid + 1;
            } else {
                r = mid - 1;
            }
        }
        return res;
    }

    private static int findFirst(int[] nums, int target) {
        int l = 0, r = nums.length - 1, res = -1;
        while (l <= r) {
            int mid = (r + l) / 2;
            if (nums[mid] == target) {
                res = mid;
                r = mid - 1;
            } else if (nums[mid] < target) l = mid + 1;
            else r = mid - 1;
        }
        return res;
    }
    private static int findLast(int[] nums, int target) {
        int l = 0, r = nums.length - 1, res = -1;
        while (l <= r) {
            int mid = (r + l) / 2;
            if (nums[mid] == target) {
                res = mid;
                l = mid + 1;
            } else if (nums[mid] < target) l = mid + 1;
            else r = mid - 1;
        }
        return res;
    }









    public int[] searchRange2(int[] nums, int target) {
        int n = nums.length;
        int[] res = new int[2];
        res[0] = findFirstOccurence(nums, target, 0, n-1);
        res[1] = findLastOccurence(nums, target, res[0], n-1);

        return res;
    }
    private int findFirstOccurence(int[] nums, int target, int l, int r) {
        int n = nums.length;

        while(l<=r) {
            int m = l + (r-l)/2;
            if(nums[m] < target) {
                l = m+1;
            } else {
                r = m-1;
            }
        }
        return (l == n || nums[l] != target)? -1 : l;
    }
    private int findLastOccurence(int[] nums, int target, int l, int r){
        if(l == -1) {
            return -1;
        }

        while(l<=r) {
            int mid = l + (r-l)/2;
            if(nums[mid]==target) {
                l = mid + 1;
            } else {
                r = mid - 1;
            }
        }
        return r;
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
