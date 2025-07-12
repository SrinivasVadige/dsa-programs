package Algorithms.PrefixSum;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * CONTIGUOUS SUB-ARRAY SUM ----
 * Given an array of integers nums and an integer k, return the total number of sub-arrays whose sum equals to k.
 * A sub-array is a contiguous non-empty sequence of elements within an array.
 * And these numbers can be positive and negative
 * nums = [1,1,1], k = 2 => Output: 2
 * nums = [1,2,3], k = 3 => Output: 2
 *
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 25 Sept 2024
 * @link 560. Subarray Sum Equals K <a href="https://leetcode.com/problems/subarray-sum-equals-k/">LeetCode link</a>
 * @topics Array, Hash Table, Prefix Sum
 * @companies Amazon, Google, Microsoft, Bloomberg, tcs, Yandex, TikTok, Apple, Zoho, ByteDance, Goldman Sachs, Nvidia, Adobe, Oracle, Uber, PayPal, Flipkart, Tesla, Yahoo, J.P. Morgan, IBM, Walmart Labs
 */
public class SubArraySumEqualsK {

    public static void main(String[] args) {
		int[] nums = new int[]{1, 2, 1, -1, 2, -1, 2};
		int k = 3;
        System.out.println("subArraySum using PrefixSumFrequencies => " + subArraySumUsingPrefixSumFrequencies(nums, k));
        System.out.println("subArraySum using TwoPointerBruteForce => " + subArraySumUsingTwoPointerBruteForce(nums, k));
	}




    /**
     * @Approach Prefix-Sum
     * @TimeComplexity O(n)
     * @SpaceComplexity O(n)
     *
     * To get the sum of range from left to right --> sum(1,5) = sum(5) - sum(0); 🔥
     * sum(i to j) = prefixSum[j] - prefixSum[i - 1]
     * so, prefixSum[i-1] = prefixSum[j] - sum(i to j)
     * int subtract = prefixSum[i-1]

     * @see #subarraySumUsingPrefixSumHashSetNotWorking to understand why we need prefixSum frequency / counter not Hashset more
     * We need prefixSum frequency / counter not the Hashset to track previous prefix to subtract
     * cause when
     * nums = [1, 2, 1, -1, 2, -1, 2], k = 3
     * ---> when i=6 and prefixSum=6, then k_target=3
     * ---> we have {3:2} 3 frequency is 2
     * And target=3 can be formed by [2, -1, 2] i.e., sumOf(4,6)=prefixSum(6)-prefixSum(3) -- formed by removing [1, 2, 1, -1]
     * and [1, -1, 2, -1, 2] i.e., sumOf(2,6)=prefixSum(6)-prefixSum(1) -- formed by removing [1, 2]
     * and
     * another example nums = [1, -1, 0], k=0
     * it's happening because of -ves and 0s
     *
    */
    public static int subArraySumUsingPrefixSumFrequencies(int[] nums, int k) {
        int prefixSum = 0; // prefixSum or runningSum or cumulativeSum
        int count = 0;
        Map<Integer, Integer> prefixSumFrequencies = new HashMap<>();
        prefixSumFrequencies.put(0, 1); // it'll help when prefixSum==k, then subtractSum=0

        for(int num: nums) {
            prefixSum += num;

            int subtractSum = prefixSum - k;
            count += prefixSumFrequencies.getOrDefault(subtractSum, 0);

            prefixSumFrequencies.merge(prefixSum, 1, Integer::sum);
        }
        return count;
    }






    /**
     * @TimeComplexity O(n^3) --> TLE
     * @SpaceComplexity O(1)
     */
    public int subArraySumUsingThreePointerBruteForce(int[] nums, int k) {
        int count = 0;
        for (int start = 0; start < nums.length; start++) {
            for (int end = start + 1; end <= nums.length; end++) {
                int sum = 0;
                for (int i = start; i < end; i++)
                    sum += nums[i];
                if (sum == k)
                    count++;
            }
        }
        return count;
    }




    /**
     * @TimeComplexity O(n^2)
     * @SpaceComplexity O(n)
     */
    public int subarraySumUsingCumulativeSum(int[] nums, int k) {
        int count = 0;
        int[] sum = new int[nums.length + 1];
        sum[0] = 0;
        for (int i = 1; i <= nums.length; i++)
            sum[i] = sum[i - 1] + nums[i - 1];
        for (int start = 0; start < nums.length; start++) {
            for (int end = start + 1; end <= nums.length; end++) {
                if (sum[end] - sum[start] == k)
                    count++;
            }
        }
        return count;
    }





    /**
     * @Approach Cumulative Sum without Space --> this is not TLE, works fine
     * @TimeComplexity O(n^2)
     * @SpaceComplexity O(1)
    */
	public static int subArraySumUsingTwoPointerBruteForce(int[] nums, int k) {
        int count = 0;
        for (int i=0; i< nums.length; i++){
            int sum = 0;
            for (int j=i; j< nums.length; j++){
                sum += nums[j];
                if (sum == k){
                    count++;
                }
            }
        }
        return count;
    }









    /**
     * NOT WORKING:
     * when
     * nums = [1, 2, 1, -1, 2, -1, 2], k = 3
     * nums = [1, -1, 0], k=0
     * it's happening because of -ves and 0s
     * @see #subArraySumUsingPrefixSumFrequencies for better understanding
     */
    public int subarraySumUsingPrefixSumHashSetNotWorking(int[] nums, int k) {
        int count = 0;
        Set<Integer> set = new HashSet<>();
        set.add(0);
        set.add(nums[0]);
        if(nums[0] ==  k) {
            count++;
        }

        for(int i=1; i<nums.length; i++) {
            int num = nums[i];
            if(num==k) {
                count++;
            }
            nums[i] += nums[i-1];
            int subtract = nums[i] - k;
//            System.out.printf("i: %s, num: %s, prefixSum: %s, prevSet: %s,  subtract: %s\n", i, num, nums[i], set, subtract);

            if(set.contains(subtract)) {
                count++;
            }
            set.add(nums[i]);
        }

        return count;
    }
}
