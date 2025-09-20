package Algorithms.Hashing;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 19 Sept 2025
 * @link 217. Contains Duplicate <a href="https://leetcode.com/problems/contains-duplicate/">Leetcode link</a>
 * @topics Array, Hash Table, Sorting
 */
public class ContainsDuplicate {
    public static void main(String[] args) {
        int[] nums = {1, 2, 3, 4};
        System.out.printf("containsDuplicate(nums) = %b\n", containsDuplicate(nums));
        System.out.printf("containsDuplicate2(nums) = %b\n", containsDuplicate2(nums));
        System.out.printf("containsDuplicate3(nums) = %b\n", containsDuplicate3(nums));
        System.out.printf("containsDuplicate4(nums) = %b\n", containsDuplicate4(nums));
    }


    public static boolean containsDuplicate(int[] nums) {
        Set<Integer> seen = new HashSet<>();
        for (int num: nums) {
            if (!seen.add(num)) return true;
        }
        return false;
    }




    public static boolean containsDuplicate2(int[] nums) {
        Set<Integer> seen = new HashSet<>();
        for (int num: nums) {
            if (seen.contains(num)) return true;
            seen.add(num); // or else seen.add(num);
        }
        return false;
    }




    public static boolean containsDuplicate3(int[] nums) {
        return nums.length != (int) Arrays.stream(nums).distinct().count();
    }





    public static boolean containsDuplicate4(int[] nums) {
        Arrays.sort(nums);
        for (int i=0; i<nums.length-1; i++) {
            if (nums[i] == nums[i+1]) return true;
        }
        return false;
    }
}
