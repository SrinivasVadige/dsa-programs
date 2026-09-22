package Algorithms.DynamicProgramming;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Srinvas Vadige, srinivas.vadige@gmail.com
 * @since 06 Sept 2026
 * @link 376. Wiggle Subsequence <a href="https://leetcode.com/problems/wiggle-subsequence/">LeetCode link</a>
 * @topics Array, Dynamic Programming, Greedy
 * @description A wiggle sequence / ZigZag is a sequence where the differences between successive numbers strictly alternate between positive and negative. The first difference (if one exists) may be either positive or negative. A sequence with one element and a sequence with two non-equal elements are trivially wiggle sequences.
 * For example, [1, 7, 4, 9, 2, 5] is a wiggle sequence because the differences (6, -3, 5, -7, 3) alternate between positive and negative.
 * In contrast, [1, 4, 7, 2, 5] and [1, 7, 4, 5, 5] are not wiggle sequences. The first is not because its first two differences are positive, and the second is not because its last difference is zero.
 * A subsequence is obtained by deleting some elements (possibly zero) from the original sequence, leaving the remaining elements in their original order.
 * Given an integer array nums, return the length of the longest wiggle subsequence of nums.

Input: nums = [1,17,5,10,13,15,10,5,16,8]
Output: 7
Explanation: There are several subsequences that achieve this length.
One is [1, 17, 10, 13, 10, 16, 8] with differences (16, -7, 3, -3, 6, -8)

 <p>
    🔥
    Wiggle Subsequence Problem - in LeetCode
    or
    ZigZag Problem              - in TopCoder
 */
public class WiggleSubsequence {
    static void main(String[] args) {
        int[] nums = {1,17,5,10,13,15,10,5,16,8};


        System.out.printf("wiggleMaxLength Using Backtracking1: %d\n", wiggleMaxLengthUsingBacktracking1(nums));
        System.out.printf("wiggleMaxLength Using TopDownMemoDp1: %d\n", wiggleMaxLengthUsingTopDownMemoDp1(nums));

        System.out.printf("wiggleMaxLength Using Backtracking2: %d\n", wiggleMaxLengthUsingBacktracking2(nums));
        System.out.printf("wiggleMaxLength Using TopDownMemoDp2: %d\n", wiggleMaxLengthUsingTopDownMemoDp2(nums));
        System.out.printf("wiggleMaxLength Using BottomUpTabulationDp2: %d\n", wiggleMaxLengthUsingBottomUpTabulationDp2(nums));
        System.out.printf("wiggleMaxLength Using BottomUpTabulationDp2 Improved: %d\n", wiggleMaxLengthUsingBottomUpTabulationDp2Improved(nums));
        System.out.printf("wiggleMaxLength Using BottomUpTabulationDp2 Improved OptimizedSpace: %d\n", wiggleMaxLengthUsingBottomUpTabulationDp2ImprovedOptimizedSpace(nums));

        System.out.printf("wiggleMaxLength Using GreedyApproach: %d\n", wiggleMaxLengthUsingGreedyApproach(nums));
    }




    /**



        [1,17,5,10,13,15,10,5,16,8]
                                                        [ ]             -1
                                                    _____|_____
                                                    |         |
                                                    0        [ ]
                                              ______|______ __|__
                                              |           | |   |
                                             0,1          0 1  [ ]
                                       _______|_______ ___|___
                                       |             | |     |
                                      0,1,2        0,1 0,2   0




        diff == 0 is not wiggle

     * @TimeComplexity O(2^n)
     * @SpaceComplexity O(n) - recursion stack
     */
    public static int wiggleMaxLengthUsingBacktracking1(int[] nums) {
        return Math.max(backtrack(nums, 0, -1, true), backtrack(nums, 0, -1, false));
    }
    private static int backtrack(int[] nums, int i, int prevI, boolean isPosDiff) {
        if (i == nums.length) return 0;

        if (prevI != -1) { // ignore isPositiveDiff if prevI==-1
            if (isPosDiff && nums[i] - nums[prevI] >= 0 || !isPosDiff && nums[i] - nums[prevI] <= 0) return 0;
        }

        int include = backtrack(nums, i+1, i, !isPosDiff) + 1;
        int exclude = backtrack(nums, i+1, prevI, isPosDiff);

        return Math.max(include, exclude);
    }



    /**
     * @TimeComplexity O(n^2)
     * @SpaceComplexity O(n) - recursion stack
     */
    public static int wiggleMaxLengthUsingTopDownMemoDp1(int[] nums) {
        Map<String, Integer> memo = new HashMap<>();
        return Math.max(dfs(nums, 0, -1, true, memo), dfs(nums, 0, -1, false, memo));
    }
    private static int dfs(int[] nums, int i, int prevI, boolean isPosDiff, Map<String, Integer> memo) {
        if (i == nums.length) return 0;
        String key = new StringBuilder().append(i).append(",").append(prevI).append(",").append(isPosDiff).toString();

        if (memo.containsKey(key)) return memo.get(key);

        if (prevI != -1) { // ignore isPositiveDiff if prevI==-1
            if (isPosDiff && nums[i] - nums[prevI] >= 0 || !isPosDiff && nums[i] - nums[prevI] <= 0) return 0;
        }

        int include = dfs(nums, i+1, i, !isPosDiff, memo) + 1;
        int exclude = dfs(nums, i+1, prevI, isPosDiff, memo);
        int max = Math.max(include, exclude);
        memo.put(key, max);
        return max;
    }






    /**
     * @TimeComplexity O(2^n)
     * @SpaceComplexity O(n) - recursion stack
     */
    public static int wiggleMaxLengthUsingBacktracking2(int[] nums) {
        if (nums.length < 2) return nums.length;
        return 1+Math.max(calculate(nums, 0, true), calculate(nums, 0, false));
    }
    private static int calculate(int[] nums, int index, boolean isUp) {
        int maxCount = 0;
        for (int i = index + 1; i < nums.length; i++) {
            if (isUp && nums[i] > nums[index] || !isUp && nums[i] < nums[index]) {
                maxCount = Math.max(maxCount, 1 + calculate(nums, i, !isUp));
            }
        }
        return maxCount;
    }




    /**
     * @TimeComplexity O(n^2)
     * @SpaceComplexity O(n) - recursion stack
     */
    public static int wiggleMaxLengthUsingTopDownMemoDp2(int[] nums) {
        Integer[][] memo = new Integer[nums.length][2];
        if (nums.length < 2) return nums.length;
        return 1+Math.max(calculate(nums, 0, 1, memo), calculate(nums, 0, 0, memo));
    }
    private static int calculate(int[] nums, int index, int isUp, Integer[][] memo) {
        if (memo[index][isUp] != null) return memo[index][isUp];

        int maxCount = 0;
        for (int i = index + 1; i < nums.length; i++) {
            if (isUp==1 && nums[i] > nums[index] || isUp==0 && nums[i] < nums[index]) {
                maxCount = Math.max(maxCount, 1 + calculate(nums, i, (isUp+1)%2, memo));
            }
        }
        return memo[index][isUp] = maxCount;
    }





    /**
     * @TimeComplexity O(n^2)
     * @SpaceComplexity O(n)
     */
    public static int wiggleMaxLengthUsingBottomUpTabulationDp2(int[] nums) {
        if (nums.length < 2) return nums.length;
        int[] up = new int[nums.length];
        int[] down = new int[nums.length];
        for (int i = 1; i < nums.length; i++) {
            for(int j = 0; j < i; j++) {
                if (nums[i] > nums[j]) {
                    up[i] = Math.max(up[i],down[j] + 1);
                } else if (nums[i] < nums[j]) {
                    down[i] = Math.max(down[i],up[j] + 1);
                }
            }
        }
        return 1 + Math.max(down[nums.length - 1], up[nums.length - 1]);
    }



    /**
     * @TimeComplexity O(n)
     * @SpaceComplexity O(n)
     */
    public static int wiggleMaxLengthUsingBottomUpTabulationDp2Improved(int[] nums) {
        if (nums.length < 2) return nums.length;
        int[] up = new int[nums.length];
        int[] down = new int[nums.length];
        up[0] = down[0] = 1;
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] > nums[i - 1]) {
                up[i] = down[i - 1] + 1;
                down[i] = down[i - 1];
            } else if (nums[i] < nums[i - 1]) {
                down[i] = up[i - 1] + 1;
                up[i] = up[i - 1];
            } else {
                down[i] = down[i - 1];
                up[i] = up[i - 1];
            }
        }
        return Math.max(down[nums.length - 1], up[nums.length - 1]);
    }




    /**
     * @TimeComplexity O(n)
     * @SpaceComplexity O(1)
     */
    public static int wiggleMaxLengthUsingBottomUpTabulationDp2ImprovedOptimizedSpace(int[] nums) {
        if (nums.length < 2) return nums.length;
        int down = 1, up = 1;
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] > nums[i - 1])
                up = down + 1;
            else if (nums[i] < nums[i - 1])
                down = up + 1;
        }
        return Math.max(down, up);
    }




    /**
     * @TimeComplexity O(n)
     * @SpaceComplexity O(1)
     */
    public static int wiggleMaxLengthUsingGreedyApproach(int[] nums) {
        if (nums.length < 2) return nums.length;
        int prevDiff = nums[1] - nums[0];
        int count = prevDiff != 0 ? 2 : 1;
        for (int i = 2; i < nums.length; i++) {
            int diff = nums[i] - nums[i - 1];
            if ((diff > 0 && prevDiff <= 0) || (diff < 0 && prevDiff >= 0)) {
                count++;
                prevDiff = diff;
            }
        }
        return count;
    }

}
