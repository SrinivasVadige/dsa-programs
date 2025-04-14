package Algorithms.PrefixSum;

import java.util.HashMap;
import java.util.Map;

/**
 * CONTIGUOUS SUB-ARRAY SUM ----
 *
 * Given an array of integers nums and an integer k, return the total number of sub-arrays whose sum equals to k.
 * A sub-array is a contiguous non-empty sequence of elements within an array.
 * And these numbers can be positive and negative
 * nums = [1,1,1], k = 2 => Output: 2
 * nums = [1,2,3], k = 3 => Output: 2
 *
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 25 Sept 2024
 */
public class SubArraySumEqualsK {
    public static void main(String[] args) {
		// int[] arr = new int[]{1,2,1,2,1};
		// int k = 3;
		int[] arr = new int[]{14,9,8,4,3,2};
		int k = 20;
        System.out.println("subArraySumUsingPrefixSumIndex: " + subArraySumUsingPrefixSumIndex(arr, k));
        System.out.println("subArraySumTwoPointerBruteForce: " + subArraySumTwoPointerBruteForce(arr, k));
        System.out.println("subArraySumByStoringNeededSum: " + subArraySumUsingPrefixSum(arr, k));
	}

        /**
     * x is small running prefixSum in hashmap and y is the current big running prefixSum 
     * and if y-x == k then y-k == x. x and y can be anywhere in the array index
    */
    public static int subArraySumUsingPrefixSumIndex(int[] arr, int k) {
        Map<Integer,Integer> sumsMap = new HashMap<>();
        int prefixSum = 0, count = 0;
        sumsMap.put(0, 1);
        for (int i=0; i<arr.length; i++) {
            prefixSum += arr[i]; // running sum

            int prevPrefixSum = prefixSum - k; // y-x == k then y-k == x

            // no need to compare arr[i] == k as prefixSum -k means the previous prevPrefixSum and it is already present in hashmap
            // prefixSum == k if it is the first time note that we already have sumsMap.put(0, 1);

            if(sumsMap.containsKey(prevPrefixSum))
                count += sumsMap.get(prevPrefixSum); // we need to add the sum as we can have same prevPrefixSum multiple times.. why not ++ instead??

            sumsMap.merge(prevPrefixSum, 1, Integer::sum); // update the map +1 otherwise prev sums will override

            System.out.println(sumsMap);
        }
        return count;
    }

    /**
     *
     * @Technique Prefix-Sum or Cumulative Sum
     * @TimeComplexity O(n)
    */
    public static int subArraySumUsingPrefixSum(int[] arr, int k) {
        Map<Integer,Integer> sumsMap = new HashMap<>();
        int prefixSum = 0, cnt = 0; // cumulative sum
        sumsMap.put(0, 1); // By default we are assign the prefix sum count as 1 i.e '0' needed sum count is 1.
        for (int i=0; i<arr.length; i++) {
            prefixSum += arr[i]; // cumulative sum
                                                    // 0-3=-3 or 3-3=0 or 6-3=3 ... in second scenario, we already have 0:1 in map and adding 3:1 to the map. In third scenario 6-3=3 we use the same 3:1 map and it'll become 3:2
            int checkPreSum = prefixSum - k; // ps-k but not k-ps because k is fixed. And we need to substract the total sum with target to get 3:1 in map instead of 0:1. Note that 0:1 is only used once

            cnt += sumsMap.getOrDefault(checkPreSum, 0);
            sumsMap.put(prefixSum, sumsMap.getOrDefault(prefixSum, 0) + 1); // the number of times that cumulative sum has been seen so far

            System.out.println(sumsMap);
        }
        return cnt;
    }

    /**
     * @Technique Brute Force, 2 pointer sliding window technique -> calculate the sum of eles in a contiguous segment of an array 
     *
     * @TimeComplexity O(n^2)
    */
	public static int subArraySumTwoPointerBruteForce(int[] arr, int k) {
        int count = 0;
        for (int i=0; i< arr.length; i++){
            int tempSum = 0;
            for (int j=i; j< arr.length; j++){
                tempSum += arr[j];
                if (tempSum == k){
                    count++;
                }
            }
        }
        return count;
    }
}
