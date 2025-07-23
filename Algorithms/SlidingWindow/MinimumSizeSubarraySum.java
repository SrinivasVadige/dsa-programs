package Algorithms.SlidingWindow;

/**
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 22 July 2025
 * @link 209. Minimum Size Subarray Sum <a href="https://leetcode.com/problems/minimum-size-subarray-sum/">LeetCode link</a>
 * @topics Array, Dynamic Sliding Window, Prefix Sum, Binary Search
 * @companies Microsoft, Amazon, Google, Meta, Bloomberg, DoorDash, BCG, TikTok, Goldman Sachs, Oracle, Yahoo, Adobe, Uber, DE Shaw, Darwinbox, Nvidia
 */
public class MinimumSizeSubarraySum {
    public static void main(String[] args) {
        int[] nums = {2,3,1,2,4,3};
        int target = 7;
        System.out.println("minSubArrayLen using PrefixSum and Dynamic Sliding Window : " + minSubArrayLenUsingPrefixSumAndDynamicSlidingWindow(target, nums));
        System.out.println("minSubArrayLen using PrefixSum and Binary Search : " + minSubArrayLenUsingPrefixSumAndBinarySearch(target, nums));
    }


    /**
     * @TimeComplexity O(n)
     * @SpaceComplexity O(1)

        target = 7
        2, 3, 1, 2, 4, 3
        2  5  6  8 10 13 --- prefix sum
     */
    public static int minSubArrayLenUsingPrefixSumAndDynamicSlidingWindow(int target, int[] nums) {
        int l = 0, prefixSum = 0;
        int res = Integer.MAX_VALUE; // cause, we need to return 0 if targetSum is not possible

        for(int r = 0; r < nums.length; r++) {
            prefixSum += nums[r];

            while (prefixSum >= target) { // move l pointer
                res = Math.min(res, r-l+1);
                prefixSum -= nums[l++];
            }
        }

        return res == Integer.MAX_VALUE ? 0 : res;
    }



    /**
     * @TimeComplexity O(nlogn)
     * @SpaceComplexity O(n)
     */
    public static int minSubArrayLenUsingPrefixSumAndBinarySearch(int target, int[] nums) {
        int n = nums.length + 1; // prefixSum length
        int[] prefixSum = new int[n];
        for (int i = 1; i < n; i++) {
            prefixSum[i] = prefixSum[i - 1] + nums[i - 1];
        }

        int minLen = Integer.MAX_VALUE;
        for (int i = 0; i < n; i++) {
            int end = binarySearch(i+1, n-1, prefixSum[i]+target, prefixSum);
            if (end == n) break;
            minLen = Math.min(minLen, end-i);
        }
        return minLen == Integer.MAX_VALUE ? 0 : minLen;
    }

    private static int binarySearch(int lo, int hi, int target, int[] sums) {
        while (lo <= hi) {
            int mid = (lo + hi) / 2;
            if(sums[mid]==target) return mid;
            else if (sums[mid] >= target){
                hi = mid - 1;
            } else {
                lo = mid + 1;
            }
        }
        return lo;
    }
}
