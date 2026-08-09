package Algorithms.BackTracking;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 23 March 2025
 */
public class FindUniqueBinaryString {
    public static void main(String[] args) {
        String[] nums = {"01", "10"};
        System.out.println("findDifferentBinaryString(nums) => " + findDifferentBinaryString(nums));
    }

    /**
     * @TimeComplexity  O(n)
     * @SpaceComplexity O(1)
     *
     * PATTERNS:
     * 1. Uses Cantor's Diagonalization to construct a missing string
     * 2. Flip the diagonal elements (nums[i][i]): 0 -> 1, 1 -> 0
     */
    public static String findDifferentBinaryString(String[] nums) {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < nums[0].length(); i++) {
            s.append((nums[i].charAt(i) == '0')?1:0);
        }
        return s.toString();
    }


    /**
     * @TimeComplexity  O(2^n)
     * @SpaceComplexity O(n)
     */
    public String findDifferentBinaryStringUsingBacktrack(String[] nums) {
        Set<String> set  = new HashSet<>(Arrays.asList(nums));

        backtrack(nums, set, "");

        for(String s: nums) {
            set.remove(s);
        }
        return set.stream().findFirst().get();
    }
    private void backtrack(String[] nums, Set<String> set, String curr) {
        if (curr.length() == nums[0].length()) {
            if(!set.add(curr)) return;
        }
        if (nums.length < set.size()) return;

        backtrack(nums, set, curr+0);
        backtrack(nums, set, curr+1);
    }




    /**
     * @TimeComplexity  O(2^n)
     * @SpaceComplexity O(2^n)
     *
     * PATTERNS:
     * 1. Store nums in a set for quick lookup.
     * 2. Generate all possible n-bit binary strings.
     * 3. Check if each one exists in the set.
     * 4. Return the first missing binary string.
     */
    public static String findDifferentBinaryStringBruteForce(String[] nums) {
        Set<String> seen = new HashSet<>(Arrays.asList(nums));
        int n = nums.length;

        for (int i = 0; i < (1 << n); i++) {
            String binary = String.format("%" + n + "s", Integer.toBinaryString(i)).replace(' ', '0');
            if (!seen.contains(binary)) {
                return binary;
            }
        }
        return "";
    }
}
