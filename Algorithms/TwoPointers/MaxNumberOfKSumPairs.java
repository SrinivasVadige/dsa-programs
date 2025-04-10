package Algorithms.TwoPointers;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 10 April 2025
 */
public class MaxNumberOfKSumPairs {
    public static void main(String[] args) {
        int[] nums = {1, 2, 3, 4, 5};
        int k = 5;
        System.out.println("maxOperations(nums, k) => " + maxOperations(nums, k)); // Output: 2
        System.out.println("maxOperationsUsingHashMap(nums, k) => " + maxOperationsUsingHashMap(nums, k)); // Output: 2
        System.out.println( "maxOperationsMyApproach(nums, k) => " + maxOperationsMyApproach(nums, k)); // Output: 2
    }

    /**
     * @TimeComplexity O(nlogn)
     * @SpaceComplexity O(1)
     */
    public static int maxOperations(int[] nums, int k) {
        Arrays.sort(nums);
        int left = 0, right = nums.length - 1;
        int count = 0;
        while (left < right) {
            int sum = nums[left] + nums[right];
            if (sum == k) {
                count++;
                left++;
                right--;
            } else if (sum < k) {
                left++;
            } else {
                right--;
            }
        }
        return count;
    }





    /**
     * @TimeComplexity O(n)
     * @SpaceComplexity O(n)
     */
    public static int maxOperationsUsingHashMap(int[] nums, int k) {
        Map<Integer, Integer> map = new HashMap<>();
        int count = 0;

        for (int num : nums) {
            int complement = k - num;
            if (map.getOrDefault(complement, 0) > 0) {
                count++;
                map.merge(complement, -1, Integer::sum);
            } else {
                map.merge(num, 1, Integer::sum);
            }
        }
        return count;
    }



    /**
     * @TimeComplexity O(n)
     * @SpaceComplexity O(n)
     */
    public static int maxOperationsMyApproach(int[] nums, int k) {
        int res = 0;
        Map<Integer, Integer> map = new HashMap<>(); //counter
        for(int x: nums) map.merge(x, 1, Integer::sum);

        for(int curr: map.keySet()) {
            int currCount = map.get(curr);
            if (currCount < 1) continue;

            int need = k-curr;
            int needCount = map.getOrDefault(need, 0);
            if (needCount < 1) continue;

            int use = (curr==need)? currCount/2 : Math.min(needCount, currCount);
            res += use;
            map.merge(curr, -use, Integer::sum); // or if (curr==need) map.merge(curr, -(use*2), Integer::sum); else {map.merge(curr, -use, Integer::sum); map.merge(curr, -use, Integer::sum);}
            map.merge(need, -use, Integer::sum);
        }
        return res;
    }






    /**
     * @TimeComplexity O(n)
     * @SpaceComplexity O(n)
     */
    public static int maxOperationsMyApproach2(int[] nums, int k) {
        int res = 0;
        Map<Integer, Integer> map = new HashMap<>(); //counter
        for(int x: nums) map.merge(x, 1, Integer::sum);

        for(int curr: map.keySet()) {
            int currCount = map.getOrDefault(curr, 0);
            if (currCount < 1) continue;

            int need = k-curr;
            int needCount = map.getOrDefault(need, 0);

            if (curr == need) {
                int use = currCount / 2;
                if (currCount>1) {
                    res += use;
                    map.merge(curr, -(use*2), Integer::sum);
                }
                continue;
            }

            if(needCount > 0) {
                int use = Math.min(needCount, currCount);
                res += use;
                map.merge(curr, -use, Integer::sum);
                map.merge(need, -use, Integer::sum);
            }
        }
        return res;
    }
}
