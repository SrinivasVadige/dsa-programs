package Algorithms.BinarySearch;

import java.util.Arrays;

/**
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 05 Feb 2025
 * @link 33. Search in Rotated Sorted Array <a href="https://leetcode.com/problems/search-in-rotated-sorted-array/description/">LeetCode Link</a>
 * @topics Array, Binary Search
 * @companies Google(9), Amazon(9), Microsoft(6), Meta(5), Bloomberg(2), LinkedIn(2), Goldman Sachs(2), Yandex(2), PayPal(4), Infosys(2), TCS(2), Oracle(2), TikTok(2), Grammarly(15), Walmart Labs(10), Apple(9), Nvidia(8), Zoho(6), Flipkart(5), Autodesk(5), NewsBreak(5), Criteo(4), Uber(4)
 */
public class SearchInRotatedSortedArray {

    public static void main(String[] args) {
        int[] nums = {4,5,0,1,2,3};
        int target = 1;
        System.out.println("binarySearch using find pivot index and binary search => " + searchUsingFindPivotIndexAndBinarySearch1(nums, target));
        System.out.println("binarySearch using find pivot index and binary search with shift => " + searchUsingFindPivotIndexAndBinarySearchWithShift(nums, target));
        System.out.println("binarySearch using one binary search => " + searchUsingOneBinarySearch1(nums, target));
    }


    /**
     * @TimeComplexity O(log n)
     * @SpaceComplexity O(1)
     */
    public static int searchUsingFindPivotIndexAndBinarySearch1(int[] nums, int target) {
        int rotatedI = getRotatedIndex(nums);
        int left = Arrays.binarySearch(nums, 0, rotatedI, target);
        if (left > -1) return left;
        int right = Arrays.binarySearch(nums, rotatedI, nums.length, target);
        return right > -1 ? right : -1; // or Math.max(-1, Math.max(left, right)); // or if (left >= 0) return left; else if (right >= 0) return right; else return -1;
    }
    private static int getRotatedIndex(int[] nums) {
        int l = 0, r = nums.length - 1;
        while (l < r) {
            int m = l + (r - l) / 2;
            if (nums[m] > nums[r]) l = m + 1;
            else r = m;
        }
        return l;
    }
    private static int getRotatedIndexOldApproach(int[] nums) {
        int n = nums.length;
        if (n==1) return 0;
        int l = 0, r = n-1, m;

        while(l<=r) {
            m = l + (r-l)/2;
            if (nums[l] <= nums[m] && nums[m] <= nums[r]) return l;
            else if (m-1 >= l && m+1 <= r && nums[m-1] >= nums[m] && nums[m] <= nums[m+1]) return m;
            else if (nums[l] <= nums[m] && nums[m] >= nums[r]) l = m+1;
            else r=m+1;
        }
        return l;
    }








    /**
     * @TimeComplexity O(log n)
     * @SpaceComplexity O(1)
     */
    public static int searchUsingFindPivotIndexAndBinarySearch2(int[] nums, int target) {
        int i = getPivotIndex(nums);
        int answer = Arrays.binarySearch(nums, 0, i, target);
        if (answer > -1) return answer;
        return Math.max(-1, Arrays.binarySearch(nums, i, nums.length, target));
    }
    private static int getPivotIndex(int[] nums) {
        int n = nums.length;
        int l = 0, r = n-1;
        while (l <= r) {
            int mid = (l + r) / 2;
            if (nums[mid] > nums[n-1]) l = mid + 1;
            else r = mid - 1;
        }
        return l;
    }






    /**
     * @TimeComplexity O(log n)
     * @SpaceComplexity O(1)
     */
    public static int searchUsingFindPivotIndexAndBinarySearchWithShift(int[] nums, int target) {
        int n = nums.length;
        int l = 0, r = n - 1;

        // Find the index of the pivot element (the smallest element)
        while (l <= r) {
            int mid = (l + r) / 2;
            if (nums[mid] > nums[n - 1]) {
                l = mid + 1;
            } else {
                r = mid - 1;
            }
        }

        return shiftedBinarySearch(nums, l, target);
    }
    private static int shiftedBinarySearch(int[] nums, int pivot, int target) { // Shift elements in a circular manner, with the pivot element at index 0. Then perform a regular binary search
        int n = nums.length;
        int shift = n - pivot;
        int l = (pivot + shift) % n;
        int r = (pivot - 1 + shift) % n;

        while (l <= r) {
            int mid = (l + r) / 2;
            if (nums[(mid - shift + n) % n] == target) {
                return (mid - shift + n) % n;
            } else if (nums[(mid - shift + n) % n] > target) {
                r = mid - 1;
            } else {
                l = mid + 1;
            }
        }
        return -1;
    }






    /**
     * @TimeComplexity O(log n)
     * @SpaceComplexity O(1)
     */
    public static int searchUsingOneBinarySearch1(int[] nums, int target) {
        int l = 0, r = nums.length - 1;
        while (l <= r) {
            int mid = (l + r) / 2;
            if (nums[mid] == target) return mid;
            if (nums[l] <= nums[mid]) { // isLeftPortionSorted?
                if (nums[l] <= target && target < nums[mid]) r = mid - 1; // target is in left portion
                else l = mid + 1;
            } else { // isRightPortionSorted?
                if (nums[mid] < target && target <= nums[r]) l = mid + 1; // target is in right portion && isRightPortionSorted?
                else r = mid - 1;
            }
        }
        return -1;
    }



    /**
     * @TimeComplexity O(log n)
     * @SpaceComplexity O(1)
     */
    public static int searchUsingOneBinarySearch2(int[] nums, int target) {
        int l = 0, r = nums.length - 1;
        while (l <= r) {
            int mid = (l + r) / 2;
            if (nums[mid] == target) return mid;
            if (nums[l] <= nums[mid]) { // isLeftPortionSorted?
                if (target < nums[l] || nums[mid] < target) l = mid + 1; // target is in left portion
                else r = mid - 1;
            } else { // isRightPortionSorted?
                if (target < nums[mid] || nums[r] < target) r = mid - 1; // target is in right portion && isRightPortionSorted?
                else l = mid + 1;
            }
        }
        return -1;
    }







    /**
     * @TimeComplexity O(n)
     * @SpaceComplexity O(1)
     */
    public static int binarySearchUsingUsingLinearAndBinarySearch(int[] nums, int target) {
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







    /**
     * @TimeComplexity O(n)
     * @SpaceComplexity O(1)
     */
    public static int searchUsingLinearSearch(int[] nums, int target) {
        for(int i=0, j=nums.length-1-i; i<=j; i++, j--){
            if(nums[i] == target) return i;
            if(nums[j] == target) return j;
        }
        return -1;
    }


}
