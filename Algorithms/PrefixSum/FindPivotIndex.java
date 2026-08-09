package Algorithms.PrefixSum;

/**
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 15 April 2025
 */
public class FindPivotIndex {

    public static void main(String[] args) {
        int[] nums = {1, 7, 3, 6, 5, 6};
        System.out.println("pivotIndex(nums) => " + pivotIndex(nums));
        System.out.println("pivotIndexMyApproach(nums) => " + pivotIndexMyApproach(nums));
    }

    /**
     * @TimeComplexity O(N)
     * @SpaceComplexity O(1)
     */
    public static int pivotIndex(int[] nums) {
        int sum = 0;
        for (int num : nums) sum += num; // total sum

        int leftSum = 0;
        for (int i = 0; i < nums.length; i++) {
            if (leftSum == sum - leftSum - nums[i]) return i;
            leftSum += nums[i];
        }
        return -1;
    }


    /**
     * @TimeComplexity O(N)
     * @SpaceComplexity O(1)
     */
    public static int pivotIndex2(int[] nums) {
        int rightSum=0;
        for(int num:nums) rightSum+=num;

        int leftSum=0;
        for(int i=0;i<nums.length;i++){
            rightSum-=nums[i];
            if(rightSum==leftSum) return i;
            leftSum+=nums[i];
        }
        return -1;
    }


    /**
     * @TimeComplexity O(N)
     * @SpaceComplexity O(N)
     */
    public static int pivotIndexMyApproach(int[] nums) {
        int n = nums.length;
        int[] lSum = new int[n+1];
        int[] rSum = new int[n+1];

        for(int i=0; i<n; i++) {
            lSum[i+1] += nums[i]+lSum[i];
            rSum[n-i-1] += nums[n-i-1]+rSum[n-i];
        }

        for (int i=0; i<n; i++) {
            if(lSum[i] == rSum[i+1]) return i;
        }
        return -1;
    }


    /**
     * @TimeComplexity O(N^2)
     * @SpaceComplexity O(1)
     */
    public static int pivotIndexBruteForce(int[] nums) {
        int n = nums.length;
        for (int i = 0; i < n; i++) {
            int leftSum = 0, rightSum = 0;
            for (int j = 0; j < i; j++) leftSum += nums[j];
            for (int j = i + 1; j < n; j++) rightSum += nums[j];
            if (leftSum == rightSum) return i;
        }
        return -1;
    }
}