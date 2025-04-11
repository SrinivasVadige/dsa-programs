package Algorithms.SlidingWindow;

/**
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 11 April 2025
 */
public class MaximumAverageSubArrayI {
    public static void main(String[] args) {
        int[] nums = {1, 12, -5, -6, 50, 3};
        int k = 4;
        System.out.println("findMaxAverage(nums, k) => " + findMaxAverage(nums, k)); // Output: 12.75
        System.out.println("findMaxAverageMyApproach(nums, k) => " + findMaxAverageMyApproach(nums, k)); // Output: 12.75
    }

    public static double findMaxAverage(int[] nums, int k) {
        double sum = 0.0;
        for (int i = 0; i < k; i++) {
            sum += nums[i];
        }
        double maxSum = sum;
        for (int i = k; i < nums.length; i++) {
            sum += nums[i] - nums[i - k];
            maxSum = Math.max(maxSum, sum);
        }
        return maxSum / k;
    }

    public static double findMaxAverageMyApproach(int[] nums, int k) {
        int max = Integer.MIN_VALUE;
        int sum = 0;

        for (int i=0; i<k; i++) sum += nums[i];
        max = sum;

        for (int i=k; i<nums.length; i++) {
            sum -= nums[i-k];
            sum += nums[i];
            max = Math.max(max, sum);
        }
        return max/(double)k;
    }
}
