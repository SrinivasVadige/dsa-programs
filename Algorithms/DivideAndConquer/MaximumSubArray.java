package Algorithms.DivideAndConquer;

import java.util.Arrays;

/**
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 13 March 2025
 * @link 53. Maximum Subarray <a href="https://leetcode.com/problems/maximum-subarray/">LeetCode link</a>
 * @topics Array, Greedy, Kadane's Algorithm, Prefix Sum, Divide and Conquer
 * @companies amazon, google, facebook, microsoft, bloomberg, linkedin, apple, goldman, nvidia, upstart, tcs, tiktok, tekion, ibm, deloitte, tesla, autodesk, accenture, intel, uber, adobe, cisco, oracle, infosys, samsung, yahoo, jpmorgan, walmart, zoho

    PATTERNS:
    --------
    Maximum SubArray can be solved using
    1. Brute Force
    2. Kadane’s Algorithm, ---> Greedy algorithm
    3. Prefix Sum
    4. Divide and Conquer approach

    It's not related to sort and duplicates as {-2,1,-3,4,-1,2,1,-5,4} max sub-array sum return 6
    because of {4,-1,2,1} even though we have {4,4,2,1,1} in sorted array
    So, we have to calculate the sum of sub-array without sorting

    i.e start from index 0 and keep adding elements in to get that index maxSum
    and start from index 1 and keep adding elements in to get that index maxSum
    and so on..

    Finally return the maxSum of all the indices
    Check {@link #maxSubArrayBruteForce} method for easy understanding
    And check {@link #maxSubArrayUsingKadanesAlgorithm} and {@link #maxSubArrayUsingKadanesAlgorithm2} methods for Kadane's Algorithm explanation

 */
public class MaximumSubArray {
    public static void main(String[] args) {
        int[] nums = {-2,1,-3,4,-1,2,1,-5,4};
        System.out.println("maxSubArray Brute Force => " + maxSubArrayBruteForce(nums));
        System.out.println("maxSubArray Using Kadanes Algorithm => " + maxSubArrayUsingKadanesAlgorithm(nums));
        System.out.println("maxSubArray Using PrefixSum => " + maxSubArrayUsingPrefixSum(nums));
        System.out.println("maxSubArray Using DivideAndConquer => " + maxSubArrayUsingDivideAndConquer(nums));
    }

    /**
     * TLE
     * @TimeComplexity O(n^2)
     * @SpaceComplexity O(1)
     */
    public static int maxSubArrayBruteForce(int[] nums) {
        int max = Integer.MIN_VALUE;
        for(int i=0; i<nums.length; i++){
            int sum=0;
            for(int j=i; j<nums.length; j++){
                sum += nums[j];
                max = Math.max(max, sum);
            }
        }
        return max;
    }




    /**
     * @TimeComplexity O(n)
     * @SpaceComplexity O(1)
     *
     * Kadane's Algorithm
     * PATTERNS:
     * --------
     * 1) Here we are calculating the sum till the current index --> i.e., end of the sub-array -- not the start
     * 2) Start adding the sum and try to maintain the +ve sum
     * 3) If the sum is -ve, then reset the sum to 0
     * 4) i.e., forget the sum and start freshly from this +ve num as we no longer need -ve sum
     * 5) NOTE: Resultant sub-array is (the items from non-zero sum index to maximumSum index)

     * Example:
     *      [1, -3, 2, 1, -1]
     *       i                ---> sum = 1, maxSum = 1
     *           i            ---> sum = -2 => 0, maxSum = 1 --- reset sum
     *              i         ---> sum = 2 => 0, maxSum = 2
     *                 i      ---> sum = 3 => 0, maxSum = 3
     *                     i  ---> sum = 2 => 0, maxSum = 3
     */
    public static int maxSubArrayUsingKadanesAlgorithm(int[] nums) {
        int maxSum = nums[0]; // cause, we might have nums.length == 1
        int sum = 0;
        for (int num : nums) {
            if (sum < 0) { // --> reset, NOTE: we can also reset the sum after "max = Math.max(maxSum, sum);" statement
                sum = 0;
            }
            sum += num;
            maxSum = Math.max(maxSum, sum);
        }
        return maxSum;
    }


    /**
     * My own Approach of Kadane's Algorithm
     */
    public int maxSubArrayUsingKadanesAlgorithm2(int[] nums) {
        int maxSum = nums[0];
        int currSum = 0;
        for(int num: nums) {
            currSum += num;
            maxSum = Math.max(maxSum, currSum);
            currSum = Math.max(currSum, 0);
        }
        return maxSum;
    }



    public static int maxSubArrayUsingKadanesAlgorithm3(int[] nums) {
        int maxSum = nums[0];
        int sum = nums[0];
        for (int i = 1; i < nums.length; i++) {
            sum = Math.max(sum + nums[i], nums[i]); // nums[i] max means sub-array started at i and ended at i
            maxSum = Math.max(maxSum, sum);
        }
        return maxSum;
    }






    /**
     * @TimeComplexity O(n)
     * @SpaceComplexity O(n)
     */
    public static int maxSubArrayUsingPrefixSum(int[] nums) {
        int n = nums.length;
        int[] prefixSum = new int[n]; // or nums.clone();
        int maxSum = prefixSum[0] = nums[0];

        for (int i=1; i<nums.length; i++) {
            prefixSum[i] = Math.max(nums[i], prefixSum[i-1]+nums[i]);
            maxSum = Math.max(maxSum, prefixSum[i]);
        }
        return maxSum;
    }


    /**
     * In-place space --> as we are modifying the given array
     * we can go from 0 to n-1 indexes or n-1 to 0 indexes
     */
    public static int maxSubArrayUsingPrefixSum2(int[] nums) {
        int max = nums[nums.length-1];
        for (int i=nums.length-2; i>=0; i--) {
            nums[i] = Math.max(nums[i], nums[i+1]+nums[i]);
            max = Math.max(max, nums[i]);
        }
        return max;
    }









    /**
     * @TimeComplexity O(nlogn)
     * @SpaceComplexity O(logn)

        Explanation:
        ------------
        - The array is split recursively.

        - For each split:

            - Calculate:

                - Maximum sum in left half

                - Maximum sum in right half

                - Maximum sum that crosses the midpoint

        - Return the maximum of these three.
     */
    public static int maxSubArrayUsingDivideAndConquer(int[] nums) {
        return maxSubArrayHelper(nums, 0, nums.length-1);
    }

    public static int maxSubArrayHelper(int[] nums, int start, int end) {
        if (start == end) return nums[start];
        int mid = (start + end) / 2;
        int leftSum = maxSubArrayHelper(nums, start, mid);
        int rightSum = maxSubArrayHelper(nums, mid + 1, end);
        int crossSum = maxCrossingSum(nums, start, mid, end);
        return Math.max(Math.max(leftSum, rightSum), crossSum);
    }

    public static int maxCrossingSum(int[] nums, int start, int mid, int end) {
        int leftSum = Integer.MIN_VALUE, sum = 0;
        for (int i = mid; i >= start; i--) {
            sum += nums[i];
            leftSum = Math.max(leftSum, sum);
        }
        sum = 0;
        int rightSum = Integer.MIN_VALUE;
        for (int i = mid + 1; i <= end; i++) {
            sum += nums[i];
            rightSum = Math.max(rightSum, sum);
        }
        return leftSum + rightSum;
    }







	/**
	    The linear method is actually a greedy algorithm.
	    Though the idea is similar, we write it in the Dynamic Programming way which has a more rigorous proof.

	    Let A be an array whose size is n, and for any given position k
        we define a new operation called positiveSum(k), which denotes
	    summing up from element 1 to k and setting the sum to 0 whenever it becomes negative.
	    	   postiveSum(k) = max(positiveSum(k-1) + A[k] , 0)
	    So the positiveSum value is actually the sum of the longest sub-array that ends at k
	    whose sub-sum value at each element is non-negative when adding up from left to right.
	    Based on the definition we know that at each position of that sub-array, the positiveSum value is always non-negative.
	    Let Sol(A(k)) denote the maximum subsequence sum at position k, and we can deduce that
	        Sol(A(k)) = max( Sol(A(k-1)), positiveSum(k) )
	    This can be proved with a series of conclusions that contradict each other.
	    Suppose the formula is not true, then there exists another maximum subsequence sum for array A(k) and its value neither equals to the maximum subsequence sum
	    of array A(k-1), nor equals to the positiveSum value at k.
	    Not maximum subsequence sum of array A(k-1) ==> the subsequence ends with A[k]
	    Not positiveSum value at k                  ==> the subsequence does not end with A[k]
	    So the contradiction voids the assumption and the formula is true.

	    DP can be implemented in either the bottom up tabulation manner or the top down memoization manner.
	 */

	// Usage of results from sub-recursive calls makes this method not tail recursive, hence new stack frame
	// is generated for every recursive call and StackOverflowError is easily
	// encountered when the array size is relatively large.
	private static int recursiveMaxSubseqSumDP(int[] array, int size, int[] positiveSum) {
		if (size == 1) return Math.max(0, array[size - 1]);
		else return  Math.max(recursiveMaxSubseqSumDP(array, size - 1, positiveSum), positiveSum[size - 1]);
	}
	private static int recursiveMaxSubseqSumDPDriver(int[] array) {
		int[] positiveSum = new int[array.length];
		positiveSum[0] = Math.max(0, array[0]);
		for (int i=1; i<array.length; i++) positiveSum[i] = Math.max(positiveSum[i-1] + array[i], 0);
		return recursiveMaxSubseqSumDP(array, array.length, positiveSum);
	}






    /**
     * TLE
     * @TimeComplexity O(n^3)
     * @SpaceComplexity O(1)
     */
    public static int maxSubArrayBruteForce2(int[] nums) {
        int max = Integer.MIN_VALUE;
        for(int i=0; i<nums.length; i++){
            for (int j=i; j<nums.length; j++) {
                int sum = 0;
                for (int k=i; k<=j; k++) {
                    sum += nums[k];
                }
                max = Math.max(max, sum);
            }
            }
        return max;
    }







    /**
     * TLE
     * @TimeComplexity O(n^2)
     * @SpaceComplexity O(n)
     */
    public int maxSubArrayUsingPrefixSum3(int[] nums) {
        int[] prefixSum = new int[nums.length+1];
        prefixSum[0] = 0;
        for(int i=0; i<nums.length; i++){
            prefixSum[i+1] = prefixSum[i]+nums[i];
        }
        int maxSum = Integer.MIN_VALUE;
        for(int i=0; i<nums.length; i++){
            for(int j=i+1; j<=nums.length; j++){
                maxSum = Math.max(maxSum, prefixSum[j]-prefixSum[i]);
            }
        }
        return maxSum;
    }






    // WON'T WORK AS IT MANIPULATED THE CURR INDICES
    public int maxSubArrayUsingSortNotWorking(int[] nums) {
        Arrays.sort(nums);
        int tempSum=0, sum=Integer.MIN_VALUE;
        for (int i=nums.length-1; i>=0; i--) {
            tempSum+=nums[i];
            sum=Math.max(sum, tempSum);
        }
        return sum;
    }
}
