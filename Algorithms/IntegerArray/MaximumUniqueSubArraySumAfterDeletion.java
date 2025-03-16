package Algorithms.IntegerArray;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 15 March 2025
 */
public class MaximumUniqueSubArraySumAfterDeletion {
    public static void main(String[] args) {
        int[] nums = {4,2,4,5,6};
        System.out.println(maxSumMyApproach(nums));
    }

    public static int maxSumMyApproach(int[] nums) {
        Set<Integer> set = new HashSet<>();
        int max = Integer.MIN_VALUE;
        for(int x: nums) {
            set.add(x);
            max=Math.max(max, x);
        }
         // if all -ve nums scenario
        if (max <=0) return max;

        int sum=0;
        for(int x: set) if (x>0) sum+=x;
        return sum;
    }



    public static int maxSum(int[] nums) {
        Set<Integer> set = new HashSet<>();
        int min = -101;
        for(int num : nums) {
            if(num >= 0) set.add(num);
            if(num < 0) min = Math.max(min, num);
        }
        if(set.isEmpty()) return min;
        int sum = 0;
        for(int key : set) sum += key;
        return sum;
    }
}
