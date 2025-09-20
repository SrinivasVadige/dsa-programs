package Algorithms.Hashing;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 19 Sept 2025
 * @link 219. Contains Duplicate II <a href="https://leetcode.com/problems/contains-duplicate-ii/">Leetcode link</a>
 * @topics Array, Hash Table, Sliding Window
 */
public class ContainsDuplicate2 {
    public static void main(String[] args) {
        int[] nums = {1, 2, 3, 1};
        int k = 3;
        System.out.printf("containsNearbyDuplicate(nums) = %b\n", containsNearbyDuplicate(nums, k));
        System.out.printf("containsNearbyDuplicate2(nums) = %b\n", containsNearbyDuplicate2(nums, k));
        System.out.printf("containsNearbyDuplicate3(nums) = %b\n", containsNearbyDuplicate3(nums, k));
    }

    public static boolean containsNearbyDuplicate(int[] nums, int k) {
        Set<Integer> seen = new HashSet<>();

        for(int l=0, r=0; r<nums.length; r++) {
            if (r-l > k) {
                seen.remove(nums[l]);
                l++;
            }

            if (!seen.add(nums[r])) {
                return true;
            }
        }

        return false;
    }




    public static boolean containsNearbyDuplicate2(int[] nums, int k) {
        Map<Integer, Integer> map = new HashMap<>();
        for(int i=0; i<nums.length; i++) {
            int num = nums[i];

            if(map.containsKey(num) && i - map.get(num) <= k) return true;
            // if (Math.abs(i - map.getOrDefault(num, i+1+k)) <= k) return true;

            map.put(num, i);

        }

        return false;
    }





    public static boolean containsNearbyDuplicate3(int[] nums, int k) {
        Map<Integer, Integer> map = new HashMap<>();
        for(int i=nums.length-1; i>=0; i--) {
            int num = nums[i];
            if (map.getOrDefault(num, i+1+k) - i <= k) return true;
            map.put(num, i);

        }

        return false;
    }
}
