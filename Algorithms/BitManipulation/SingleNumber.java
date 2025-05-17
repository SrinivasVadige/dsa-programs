package Algorithms.BitManipulation;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Given a non-empty array of integers nums, every element appears twice except for one. Find that single one.
 *
 * @author: Srinivas Vadige, srinivas.vadige@gmail.com
 * @since: 11 Feb 2025
 */
public class SingleNumber {
    public static void main(String[] args) {
        int[] nums = {4,1,2,1,2};
        System.out.println("singleNumber: " + singleNumber(nums));
    }

    /**
     * we know that a^b returns 0 if same and "bitwise comparison" if different. So, 0^a = a and a^a = 0
     * So, a^a^b = b and at some point, we all sum up the nums and we get 0 and the 0^a = a
     * {4,1,2,1,2}
     * 4 => 100
     * 1 => 001
     * 2 => 010
     * 1 => 001
     * 2 => 010
     *     -----
     *      100
     *     -----
     * cause 100 ^ 001 = 101
     *       101 ^ 010 = 111
     *       111 ^ 001 = 110
     *       110 ^ 010 = 100
     *
     * 1 => 001
     * 2 => 010
     * 3 => 011
     * 4 => 100
     */
    public static int singleNumber(int[] nums) {
        int result = 0;
        for (int num : nums) {
            result ^= num;
        }
        return result;
    }

    public static int singleNumberUsingStreamReduce(int[] nums) {
        return Arrays.stream(nums).reduce(0, (a, b) -> a ^ b);
        // or Arrays.stream(nums).reduce((a,b)->a^b).getAsInt();
    }

    public static int singleNumberUsingRecursion(int[] nums) {
        return singleNumberUsingRecursion(nums, 0, nums.length - 1);
    }

    public static int singleNumberUsingRecursion(int[] nums, int start, int end) {
        if (start == end) {
            return nums[start];
        }
        int mid = (start + end) / 2;
        int left = singleNumberUsingRecursion(nums, start, mid);
        int right = singleNumberUsingRecursion(nums, mid + 1, end);
        return left ^ right;
    }

    public static int singleNumberUsingXorRecursion(int[] nums) {
        return xor(0, nums);
    }
    static int xor(int i, int[] nums){
        if(i>=nums.length) return 0;
        return nums[i] ^ xor(i++,nums);
    }

    public static int singleNumberUsingHashMap(int[] nums) {
        // set and map is not constant time
        Map<Integer, Integer> map = new HashMap<>();
        for (int n: nums)
            map.merge(n, 1, Integer::sum);

        for (Map.Entry<Integer, Integer> e: map.entrySet())
            if (e.getValue()==1) return e.getKey();

        return -1;
    }

    public static int singleNumberUsingSet(int[] nums) {
        Set<Integer> set = new HashSet<>();
        for (int n: nums)
            if (!set.add(n)) set.remove(n);

        return set.iterator().next();
    }

    public static int singleNumberUsingSort(int[] nums) {
        int n = nums.length;
        if(n == 1) return nums[0];
        Arrays.sort(nums);
        for(int i = 0; i < n - 1; i += 2) {
            if(nums[i] != nums[i + 1]) {
                return nums[i];
            }
        }
        return nums[n - 1];
    }
}
