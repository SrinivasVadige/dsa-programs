package Algorithms.DivideAndConquer;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 29 March 2026
 * @link 918. Maximum Sum Circular Subarray <a href="https://leetcode.com/problems/maximum-sum-circular-subarray/">LeetCode Link</a>
 * @topics Array, Kadane's Algorithm, Prefix Sum, Divide and Conquer, Monotonic Deque / Queue, Dynamic Programming
 * @companies Apple(2), Google(3), Meta(11), Amazon(11), Microsoft(4), Bloomberg(3), Goldman Sachs(2), MakeMyTrip(2), Two Sigma(2), Sprinklr(2)
 * @see Algorithms.DivideAndConquer.MaximumSubArray
 */
public class MaximumSumCircularSubarray {

    public static void main(String[] args) {
        int[] nums = {1,-2,3,-2};
        System.out.println("maxSubArraySumCircular using Kadane's Brute Force TLE => " + maxSubarraySumCircularUsingKadanesBruteForceTLE(nums));
        System.out.println("maxSubArraySumCircular using PrefixSum TLE => " + maxSubarraySumCircularUsingPrefixSumTLE(nums));
        System.out.println("maxSubArraySumCircular using Enumerate Prefix and Suffix Sums => " + maxSubarraySumCircularUsingEnumeratePrefixAndSuffixSums(nums));
        System.out.println("maxSubArraySumCircular using Minimum Subarray 1 => " + maxSubarraySumCircularUsingMinimumSubarray1(nums));
        System.out.println("maxSubArraySumCircular using Minimum Subarray 2 => " + maxSubArraySumCircularUsingMinimumSubarray2(nums));
        System.out.println("maxSubArraySumCircular using PrefixSum and Monotonic Deque => " + maxSubarraySumCircularUsingPrefixSumAndMonotonicDeque(nums));
    }


    /**
     * TLE
     * @TimeComplexity O(n^2)
     * @SpaceComplexity O(1)
     */
    public static int maxSubarraySumCircularUsingKadanesBruteForceTLE(int[] nums) {
        int n = nums.length;

        int max = nums[0];
        for (int i=0; i<n; i++) {
            max = Math.max(max, getSum(nums, i));
        }

        return max;
    }
    private static int getSum(int[] nums, int i) {
        int sum = 0, max = nums[i], n = nums.length;
        for (int idx=i%n, count=0; count<n; ++i, idx=i%n, count++) {
            sum = Math.max(sum + nums[idx], nums[idx]); // or sum = Math.max(sum, 0) + nums[idx];
            max = Math.max(max, sum);
        }
        return max;
    }







    /**
     * TLE
     * @TimeComplexity O(n^2)
     * @SpaceComplexity O(1)
     */
    public static int maxSubarraySumCircularUsingPrefixSumTLE(int[] nums) {
        int max = nums[0], n = nums.length;
        for (int i=0; i<n; i++) {
            int leftSum = getLeftMaxSum(nums, i);
            int rightSum = getRightMaxSum(nums, i);

            max = Math.max(max, nums[i]);
            max = Math.max(max, leftSum);
            max = Math.max(max, rightSum);
        }
        return max;
    }
    private static int getLeftMaxSum(int[] nums, int givenI) {
        int max = nums[givenI], n = nums.length;

        int sum = 0;
        for(int i = givenI; i>=0; i--) {
            sum += nums[i];
            max = Math.max(max, sum);
        }

        for(int i=n-1; i>givenI; i--) {
            sum += nums[i];
            max = Math.max(max, sum);
        }
        return max;
    }
    private static int getRightMaxSum(int[] nums, int givenI) {
        int max = nums[givenI], n = nums.length;

        int sum = 0;
        for(int i = givenI; i<n; i++) {
            sum += nums[i];
            max = Math.max(max, sum);
        }

        for(int i=0; i<givenI; i++) {
            sum += nums[i];
            max = Math.max(max, sum);
        }
        return max;
    }











    /**
     * @TimeComplexity O(n)
     * @SpaceComplexity O(n)
        Enumerate = loop through each possible case one by one

        GET THE MAX OF TWO SCENARIOS:
        1. maxSum(normal kadane algo i.e max sub array in 0 to n-1) and
        2. specialSum(0 to i prefixSum + (i+1) to  n-1 suffixSum) are diff scenarios

        this approach is improved version of {@link #maxSubarraySumCircularUsingKadanesBruteForceTLE}
     */
    public static int maxSubarraySumCircularUsingEnumeratePrefixAndSuffixSums(int[] nums) {
        final int n = nums.length;
        final int[] rightMaxSum = new int[n]; // it's "rightMaxSum" with endI = n=1 ---> but not the max num
        rightMaxSum[n - 1] = nums[n - 1];
        int suffixSum = nums[n - 1]; // runningSum from right to left <-

        for (int i = n - 2; i >= 0; --i) {
            suffixSum += nums[i];
            rightMaxSum[i] = Math.max(rightMaxSum[i + 1], suffixSum); // Math.max("exclude curr i as startI in subarray", "sum till now i.e., i to n-1 subarray") // endI=n-1 is constant
        }

        int curMax = 0;
        int maxSum = nums[0];

        int prefixSum = 0; // runningSum
        int specialSum = nums[0];

        for (int i = 0; i < n; ++i) {
            // SCENARIO 1. CURR SUM - Normal Kadane's
            // This is Kadane's algorithm ---> we know that here, we can change startI and endI - no pointer is constant unlike endI constant in above for loop
            curMax = Math.max(curMax, 0) + nums[i]; // change startI or not?
            maxSum = Math.max(maxSum, curMax);

            // SCENARIO 2. SPECIAL SUM
            prefixSum += nums[i];
            if (i + 1 < n) { // or if (i==n-1) break; ---> cause if i==n-1 then there is no rightMaxSum for i+1=n ---> we calculate that in SCENARIO 1
                specialSum = Math.max(specialSum, prefixSum + rightMaxSum[i + 1]); // "0 to i prefixRunningSum" + "(i+1) to  n-1 suffixMaxSum"
            }
        }

        return Math.max(maxSum, specialSum);
    }











    /**
     * @TimeComplexity O(n)
     * @SpaceComplexity O(1)

        GET THE 3 SUMS:
        1. maxSum(normal kadane algo i.e max sub array in 0 to n-1) and
        2. minSum(min sub array in 0 to n-1) and
        3. totalSum

        return Math.max(maxSum, totalSum - minSum);



        [5, -3, -2, 6, -1, 4]

        Here
        maxSum = 6-1+4 = 9
        minSum = -3-2 = -5
        totalSum = 5-3-2+6-1+4 = 9

        return Math.max(maxSum, totalSum - minSum);
        return Math.max(9, 9 - (-5)) = 14

     */
    public static int maxSubarraySumCircularUsingMinimumSubarray1(int[] nums) {
        int curMax = 0;
        int maxSum = nums[0]; // maxSubArraySum

        int curMin = 0;
        int minSum = nums[0]; // minSubArraySum

        int totalSum = 0; // sum of all elements

        for (int num: nums) {
            // 1. Normal Kadane's ---> startI and endI are not constant
            curMax = Math.max(curMax, 0) + num;
            maxSum = Math.max(maxSum, curMax);

            // 2. Kadane's but with min to find minimum subarray ---> startI and endI are not constant
            curMin = Math.min(curMin, 0) + num;
            minSum = Math.min(minSum, curMin);

            // 3. Total sum
            totalSum += num;
        }

        if (totalSum == minSum) { // i.e all nums are -ve ---> then just return the smallest -ve num using maxSum
            return maxSum;
        }

        return Math.max(maxSum, totalSum - minSum);
    }







    /**
     * @TimeComplexity O(n)
     * @SpaceComplexity O(1)
     */
    public static int maxSubArraySumCircularUsingMinimumSubarray2(int[] nums) { // using Kadane's Algorithm and
        int maxSum = Integer.MIN_VALUE, minSum = Integer.MAX_VALUE;
        int totalSum = 0, currMax = 0, currMin = 0;
        for (int num : nums) {
            currMax = Math.max(currMax + num, num); // or currMax = Math.max(0, currMax) + num;
            maxSum = Math.max(maxSum, currMax);
            currMin = Math.min(currMin + num, num);
            minSum = Math.min(minSum, currMin);
            totalSum += num;
        }
        return maxSum > 0 ? Math.max(maxSum, totalSum - minSum) : maxSum;
    }










    /**
     * @TimeComplexity O(n)
     * @SpaceComplexity O(n)


        Use Deque -> for startI - to maintain minimum prefix in the sliding window
     */
    public static int maxSubarraySumCircularUsingPrefixSumAndMonotonicDeque(int[] nums) {
        int n = nums.length;

        // Step 1: Build the extended array
        int[] arr = new int[2 * n];
        for (int i = 0; i < 2 * n; i++) {
            arr[i] = nums[i % n];
        }

        // Step 2: Prefix sum
        int[] prefixSum = new int[2 * n + 1];
        for (int i = 0; i < 2 * n; i++) {
            prefixSum[i + 1] = prefixSum[i] + arr[i];
        }

        // Step 3: Deque
        Deque<Integer> dq = new ArrayDeque<>(); // for startI
        int maxSum = Integer.MIN_VALUE;

        for (int j = 0; j <= 2 * n; j++) { // endI loop

            // Remove indices out of the window (length > n)
            if (!dq.isEmpty() && dq.peekFirst() < j - n) {
                dq.pollFirst();
            }

            // Calculate max
            if (!dq.isEmpty()) {
                maxSum = Math.max(maxSum, prefixSum[j] - prefixSum[dq.peekFirst()]);
            }

            // Maintain increasing prefixSum values -> to increase the window size
            while (!dq.isEmpty() && prefixSum[dq.peekLast()] >= prefixSum[j]) {
                dq.pollLast();
            }

            dq.offerLast(j);
        }

        return maxSum;
    }
}
