package Algorithms.BackTracking;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 06 Oct 2024
 */
public class NonContiguousSubArraySum {
    public static void main(String[] args) {
        int[] arr = {14,9,8,4,3,2};
        int k = Arrays.stream(arr).sum()/2;
        System.out.println(backtrack(arr, k, 0, 0, new HashMap<>()));
    }

    /**
     * TLE in LeetCode
     */
    private static boolean backtrack(int[] nums, int sum, int index, int currentSum, Map<String, Boolean> memo) {
        if (currentSum == sum)
            return true;

        if (index >= nums.length || currentSum > sum)
            return false;

        String key = index + "," + currentSum;

        if (memo.containsKey(key))
            return memo.get(key);

        boolean include = backtrack(nums, sum, index + 1, currentSum + nums[index], memo);
        boolean exclude = backtrack(nums, sum, index + 1, currentSum, memo);
        memo.put(key, include || exclude);
        return include || exclude;
    }
}
