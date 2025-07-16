package Algorithms.SlidingWindow;

/**
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 11 April 2025
 * @link 643. Maximum Average Subarray I <a href="https://leetcode.com/problems/maximum-average-subarray-i/">LeetCode link</a>
 * @topics Array, Sliding Window / Fixed Sliding Window
 * @companies Meta, Google, Amazon, Apple, Bloomberg, Microsoft, Adobe
 */
public class MaximumAverageSubArrayI {
    public static void main(String[] args) {
        int[] nums = {1, 12, -5, -6, 50, 3};
        int k = 4;
        System.out.println("findMaxAverage Using FixedSlidingWindow => " + findMaxAverageUsingFixedSlidingWindow(nums, k)); // Output: 12.75
        System.out.println("findMaxAverage Using PrefixSum / CumulativeSum => " + findMaxAverageUsingPrefixSum(nums, k)); // Output: 12.75
    }

    public static double findMaxAverageUsingFixedSlidingWindow(int[] nums, int k) {
        int max, sum = 0, i=0;
        while(i<k) {
            sum += nums[i++];
        }
        max = sum;

        while(i<nums.length) {
            sum -= nums[i-k];
            sum += nums[i++];
            max = Math.max(max, sum);
        }

        return (double)max/k;
    }




    public static double findMaxAverageUsingFixedSlidingWindow2(int[] nums, int k) {
        double sum=0;
        for(int i=0;i<k;i++) {
            sum+=nums[i];
        }
        double res=sum;
        for(int i=k;i<nums.length;i++){
            sum+=nums[i]-nums[i-k];
            res=Math.max(res,sum);
        }
        return res/k;
    }






	public static double findMaxAverageUsingPrefixSum(int[] nums, int k) {
		int[] sum = new int[nums.length];
		sum[0] = nums[0];
		for (int i = 1; i < nums.length; i++) { // prefix sum or cumulative sum
		    sum[i] = sum[i - 1] + nums[i];
        }
		double res = sum[k - 1] * 1.0 / k;
		for (int i = k; i < nums.length; i++) {
			res = Math.max(res, (sum[i] - sum[i - k]) * 1.0 / k);
		}
		return res;
	}
}
