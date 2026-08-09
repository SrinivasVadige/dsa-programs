package Algorithms.SlidingWindow;

/**
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 09 Aug 2026
 * @link 485. Max Consecutive Ones <a href="https://leetcode.com/problems/max-consecutive-ones/">LeetCode link</a>
 * @topics Array, Sliding Window, Mid Level
 * @description Given a binary array nums, return the maximum number of consecutive 1's in the array.
 * For example, nums = [1,1,0,1,1,1] => 3
 * @see Algorithms.SlidingWindow.MaxConsecutiveOnesIII
 */
public class MaxConsecutiveOnes {
    public static void main(String[] args) {
        int[] nums = {1,1,0,1,1,1};
        System.out.printf("findMaxConsecutiveOnes Using SlidingWindow 1: %s", findMaxConsecutiveOnesUsingSlidingWindow1(nums));
        System.out.printf("findMaxConsecutiveOnes Using SlidingWindow 2: %s", findMaxConsecutiveOnesUsingSlidingWindow2(nums));
    }


    /**
     * @TimeComplexity O(n)
     * @SpaceComplexity O(1)
     */
    public static int findMaxConsecutiveOnesUsingSlidingWindow1(int[] nums) {
        int n = nums.length, l = 0, max = 0;

        for(int r = 0; r<n; r++) {
            if (nums[r] == 0) {
                max = Math.max(max, r-l);
                l = r+1;
            }
        }

        return Math.max(max, n-l);
    }




    /**
     * @TimeComplexity O(n)
     * @SpaceComplexity O(1)
     */
    public static int findMaxConsecutiveOnesUsingSlidingWindow2(int[] nums) {
        int count = 0, maxCount = 0;
        for(int i = 0; i < nums.length; i++) {
            if(nums[i] == 1) {
                count += 1;
            } else { // 0
                maxCount = Math.max(maxCount, count);
                count = 0;
            }
        }
        return Math.max(maxCount, count);
    }
}
