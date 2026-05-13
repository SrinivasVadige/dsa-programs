package Algorithms.BitManipulation;

import java.util.*;

/**
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 12 May 2026
 * @link 137. Single Number II <a href="https://leetcode.com/problems/single-number-ii/">LeetCode Link</a>
 * @topics Bit Manipulation, Array
 * @companies Google(4), Amazon(4), Meta(3), Microsoft(3), Bloomberg(12)

 the main idea is that
    Example 1 -> the number of times a number is repeated is 3:
        nums = [2, 2, 2, 3]
    Binary:
        2 = 10
        2 = 10
        2 = 10
        3 = 11

    Example 2 -> the number of times a number is repeated is 4:
        nums = [5,5,5,5,2]

    Binary:
        5 = 101
        5 = 101
        5 = 101
        5 = 101
        2 = 010

    so, when ever we have something like this -> 1 loaner and rest repeated k times
    then just do sumOfSet % k in each bit position to get the loaner binary bit


 */
public class SingleNumber2 {
    public static void main(String[] args) {
        int[] nums = {2,2,3,2};
        System.out.println("singleNumber using Map: " + singleNumberUsingMap(nums));
        System.out.println("singleNumber using Sort 1: " + singleNumberUsingSort1(nums));
        System.out.println("singleNumber using Sort 2: " + singleNumberUsingSort2(nums));
        System.out.println("singleNumber using Set: " + singleNumberUsingSet(nums));
        System.out.println("singleNumber using Array Bit Count 1: " + singleNumberArrayBitCount1(nums));
        System.out.println("singleNumber using Array Bit Count 2: " + singleNumberArrayBitCount2(nums));
        System.out.println("singleNumber using Bit Manipulation: " + singleNumberUsingBitManipulation1(nums));
        System.out.println("singleNumber using Equation for Bitmask: " + singleNumberUsingEquationForBitmask(nums));
        System.out.println("singleNumber using Boolean Algebra and Karnaugh Map: " + singleNumberUsingBooleanAlgebraAndKarnaughMap(nums));
    }



    /**
     * @TimeComplexity O(n)
     * @SpaceComplexity O(n) for map
     */
    public static int singleNumberUsingMap(int[] nums) {
        // Map<Integer, Integer> counter = Arrays.stream(nums).boxed().collect(Collectors.groupingBy(Function.identity(), Collectors.summingInt(e->1)));
        Map<Integer, Integer> counter = new HashMap<>();
        for (int num: nums) counter.merge(num, 1, Integer::sum);
        for (int key: counter.keySet()) {
            if (counter.get(key) == 1) return key;
        }
        return 0;
    }









    /**
     * @TimeComplexity O(nlogn)
     * @SpaceComplexity O(1)
     */
    public static int singleNumberUsingSort1(int[] nums) {
        Arrays.sort(nums);
        int n = nums.length;
        for(int i=0; i<n-1; i++) {
            if (nums[i] == nums[i+1]) {
                i += 2;
            } else {
                return nums[i];
            }
        }
        return nums[n-1];
    }


    /**
     * @TimeComplexity O(nlogn)
     * @SpaceComplexity O(1)
     */
    public static int singleNumberUsingSort2(int[] nums) {
        Arrays.sort(nums);

        for (int i = 0; i < nums.length - 1; i += 3) {
            if (nums[i] == nums[i + 1]) {
                continue;
            } else {
                return nums[i];
            }
        }

        return nums[nums.length - 1];
    }






    /**
     * @TimeComplexity O(n)
     * @SpaceComplexity O(n) for set
     */
    public static int singleNumberUsingSet(int[] nums) {
        Set<Long> numsSet = new HashSet<>();
        long sumNums = 0;
        for (int num : nums) {
            numsSet.add((long) num);
            sumNums += num;
        }

        long sumSet = 0;
        for (long num : numsSet) {
            sumSet += num;
        }

        return (int) ((3 * sumSet - sumNums) / 2);
    }






    /**
     * @TimeComplexity O(n)
     * @SpaceComplexity O(1)
     */
    public static int singleNumberArrayBitCount1(int[] nums) {
        int[] bitCount = new int[32];
        for (int num: nums) {
            String binary = Integer.toBinaryString(num);
            for (int i=binary.length()-1, idx = 31; i>=0; i--, idx--) {
                // bitCount[idx] = (bitCount[idx] + binary.charAt(i)-'0') % 3; // then extra "bit % 3 for loop" is not needed
                bitCount[idx] += binary.charAt(i)-'0';
            }
        }

        for (int i=0; i<32; i++) bitCount[i] = bitCount[i] % 3;

        StringBuilder resBinary = new StringBuilder();
        for (int bit : bitCount) resBinary.append(bit);
        return (int) Long.parseLong(resBinary.toString(), 2);
    }



    /**
     * @TimeComplexity O(n)
     * @SpaceComplexity O(1)
     */
    public static int singleNumberArrayBitCount2(int[] nums) {
        int[] bitCount = new int[32];
        for (int num: nums) {
            for(int i=31; num!=0 && i>=0; i--) {
                // bitCount[i] = (bitCount[i] + (num&1)) % 3; // then extra "bit % 3 for loop" is not needed
                bitCount[i] += num&1;
                num>>=1;
            }
        }

        for (int i=0; i<32; i++) bitCount[i] = bitCount[i] % 3;

        int ans = 0;
        for (int i = 0; i < 32; i++) {
            if (bitCount[31-i] != 0) ans = ans | (1 << i); // (1 << i) will return binary with "1" at ith position
        }
        return ans;
    }






    /**
     * this method is same like {@link #singleNumberArrayBitCount2} but we replaced int[32] with extra for loop calculating each bit position sum % 3
     * @TimeComplexity O(n)
     * @SpaceComplexity O(1)
     */
    public static int singleNumberUsingBitManipulation1(int[] nums) {
        int loner = 0;

        for (int shift = 0; shift < 32; shift++) { // Iterate over all 32 bits
            int bitSum = 0;

            for (int num : nums) { // For this bit, iterate over all integers
                bitSum += (num >> shift) & 1; // Compute the curr bit position of num, and add it to bitSum
            }

            // Compute the bit of loner and place it
            int lonerBit = bitSum % 3;
            loner = loner | (lonerBit << shift);
        }
        return loner;
    }




    /**
     * Here
     * In ones = store the nums that repeated only once
     * In twos = store the nums that repeated only twice
     * We dont have threes to track
     *
     *
     * @TimeComplexity O(n)
     * @SpaceComplexity O(1)
     */
    public static int singleNumberUsingEquationForBitmask(int[] nums) {
        int ones = 0;
        int twos = 0;

        for (final int num : nums) {
            ones ^= (num & ~twos); // or ones = (ones ^ num) & ~twos
            twos ^= (num & ~ones); // or twos = (twos ^ num) & ~ones
        }

        return ones;
    }




    /**
     * @TimeComplexity O(n)
     * @SpaceComplexity O(1)
     */
    public static int singleNumberUsingBooleanAlgebraAndKarnaughMap(int[] nums) {
        // Count (modulo 3) bits
        int msb = 0, lsb = 0;

        // Process Every Num and update count bits
        for (int num : nums) {
            int new_lsb = (~msb & ~lsb & num) | (lsb & ~num);
            int new_msb = (lsb & num) | (msb & ~num);
            lsb = new_lsb;
            msb = new_msb;
        }

        // Return lsb as answer
        return lsb;
    }
}
