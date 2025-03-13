package Algorithms.MiscAlgos;

import java.util.Arrays;

/**
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 13 March 2025
 *
 * PATTERNS:
 * --------
 * Kadane's Algorithm
 * It's not related to sort and duplicates as {-2,1,-3,4,-1,2,1,-5,4} return 6 because of {4,-1,2,1} even though we have {4,4,2,1,1} in sorted array
 * So, we have to calculate the sum of sub-array without sorting
 * i.e start from index 0 and keep adding elements in to get that index maxSum
 * and start from index 1 and keep adding elements in to get that index maxSum
 * and so on..
 * Finally return the maxSum of all the indices
 * Check #maxSubArrayBruteForce(int[] nums) method for easy understanding
 */
public class MaximumSubArray {
    public static void main(String[] args) {
        int[] nums = {-2,1,-3,4,-1,2,1,-5,4};
        System.out.println("maxSubArrayUsingPrefixSum => " + maxSubArrayUsingPrefixSum(nums));
        System.out.println("maxSubArray => " + maxSubArray(nums));
        System.out.println("maxSubArrayUsingDivideAndConquer => " + maxSubArrayUsingDivideAndConquer(nums));
    }

    /**
     * TLE
     * @TimeComplexity: O(n^2)
     * @SpaceComplexity: O(1)
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
     * TLE
     * @TimeComplexity: O(n^3)
     * @SpaceComplexity: O(1)
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
     * @TimeComplexity: O(n)
     * @SpaceComplexity: O(n)
     */
    public static int maxSubArrayUsingPrefixSum(int[] nums) {
        int n = nums.length;
        int[] prefixSum = new int[n];
        int max = Integer.MIN_VALUE;
        prefixSum[n-1] = max = nums[n-1];

        for (int i=n-2; i>=0; i--) {
            prefixSum[i] = Math.max(nums[i], prefixSum[i+1]+nums[i]);
            max = Math.max(max, prefixSum[i]);
        }
        return max;
    }

    /**
     * @TimeComplexity: O(n)
     * @SpaceComplexity: O(1)
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
     * @TimeComplexity: O(n)
     * @SpaceComplexity: O(1)
     *
     * Kadane's Algorithm
     * PATTERNS:
     * --------
     * 1) If the sum is -ve, start adding the sum until you find a +ve num
     * 2) Then you can forgot the currSum and start freshly from this +ve num as we no longer need -ve sum
     */
    public static int maxSubArray(int[] nums) {
        int max = nums[0];
        int currSum = 0;
        for (int i = 0; i < nums.length; i++) {
            if (currSum < 0) currSum = 0;
            currSum += nums[i];
            max = Math.max(max, currSum);
        }
        return max;
    }

    public static int maxSubArray2(int[] nums) {
        int maxSum = nums[0];
        int currSum = nums[0];
        for (int i = 1; i < nums.length; i++) {
            currSum = Math.max(currSum + nums[i], nums[i]);
            maxSum = Math.max(maxSum, currSum);
        }
        return maxSum;
    }


    public static int maxSubArray3(int[] nums) {
        int max = Integer.MIN_VALUE;
        int sum=0;
        if(nums.length == 1) return nums[0];
        for(int i=0; i<nums.length; i++){
            sum += nums[i];
            max = Math.max(max, sum);
            sum = sum<0? 0:sum;
        }
        return max;
    }


    public int maxSubArray4(int[] nums) {
        int max = nums[0];
        int sum =0;

        for(int val : nums){
            sum +=val;
            max = (sum>max)? sum:max;
            if(sum<0) sum=0;
        }
        return max ;
    }




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
     * TLE
     * @TimeComplexity: O(n^2)
     * @SpaceComplexity: O(n)
     */
    public int maxSubArrayUsingPrefixSum3(int[] nums) {
        int[] prefixSum = new int[nums.length+1];
        prefixSum[0] = 0;
        for(int i=0; i<nums.length; i++){
            prefixSum[i+1] = prefixSum[i]+nums[i];
        }
        int max = Integer.MIN_VALUE;
        for(int i=0; i<nums.length; i++){
            for(int j=i+1; j<=nums.length; j++){
                max = Math.max(max, prefixSum[j]-prefixSum[i]);
            }
        }
        return max;
    }






    // WON'T WORK AS IT MANIPULATED THE CURR INDICES
    public int maxSubArrayUsingSort(int[] nums) {
        Arrays.sort(nums);
        int tempSum=0, sum=Integer.MIN_VALUE;
        for (int i=nums.length-1; i>=0; i--) {
            tempSum+=nums[i];
            sum=Math.max(sum, tempSum);
        }
        return sum;
    }
}
