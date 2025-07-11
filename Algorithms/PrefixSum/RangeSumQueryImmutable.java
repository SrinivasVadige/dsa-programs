package Algorithms.PrefixSum;

/**
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 11 July 2025
 * @link 303. Range Sum Query - Immutable <a href="https://leetcode.com/problems/range-sum-query-immutable/">LeetCode link</a>
 * @description Calculate the sum of the elements of nums between indices left and right inclusive where left <= right.
 * @topics Array, Design, Prefix Sum
 * @companies Meta, Google, Amazon,Bloomberg, Microsoft, Palantir Technologies, Apple
 */
public class RangeSumQueryImmutable {
    public static void main(String[] args) {
        int[] nums = {-2, 0, 3,-5, 2,-1};
        System.out.println("NumArray => ");
        NumArray numArray = new NumArray(nums);
        System.out.println("sumRange(0, 2) => " + numArray.sumRange(0, 2)); // 1
        System.out.println("sumRange(2, 5) => " + numArray.sumRange(2, 5)); // -1
        System.out.println("sumRange(0, 5) => " + numArray.sumRange(0, 5)); // -3

        nums = new int[]{-2, 0, 3,-5, 2,-1};
        System.out.println("NumArray2 => ");
        NumArray2 numArray2 = new NumArray2(nums);
        System.out.println("sumRange(0, 2) => " + numArray2.sumRange(0, 2)); // 1
        System.out.println("sumRange(2, 5) => " + numArray2.sumRange(2, 5)); // -1
        System.out.println("sumRange(0, 5) => " + numArray2.sumRange(0, 5)); // -3
    }


    /**
     * @SpaceComplexity O(1)
     */
    static class NumArray {
        private final int[] prefixSum;

        public NumArray(int[] nums) {
            this.prefixSum = nums; // modifying the original array
            /*
                [-2, 0, 3,-5, 2,-1]
                [-2,-2, 1,-4,-2,-3] --- prefixSum
             */
            for(int i=1; i<nums.length; i++) {
                nums[i] += nums[i-1];
            }
        }

        /**
         * To get the sum of range from left to right --> sum(1,5) = sum(5) - sum(0); 🔥
         */
        public int sumRange(int left, int right) {
            if(left == 0) {
                return prefixSum[right];
            }
            return prefixSum[right] - prefixSum[left-1];
        }
    }


    /**
     * @SpaceComplexity O(n)
     */
    static class NumArray2 {
        private final int[] prefixSum;

        public NumArray2(int[] nums) {
            prefixSum = new int[nums.length + 1]; // dummy prefixSum[0]=0;
            for(int i = 0; i < nums.length; i++){
                prefixSum[i + 1] = prefixSum[i] + nums[i];
            }
        }

        public int sumRange(int left, int right) {
            return prefixSum[right + 1] - prefixSum[left];
        }
    }
}
