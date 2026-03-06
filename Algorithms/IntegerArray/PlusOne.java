package Algorithms.IntegerArray;

import java.util.Arrays;

/**
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 05 March 2026
 * @link 66. Plus One <a href="https://leetcode.com/problems/plus-one/">LeetCode Link</a>
 * @topics Array, Math
 * @companies Google(17), Amazon(8), Bloomberg(6), Meta(4), Microsoft(3), Intuit(4), Capgemini(2), TCS(6), Accenture(4), IBM(2), TikTok(2), Visa(2)


  9
+ 9
----
 18

+ 1 (carry)
  9
+ 9
------
 19

So two numbers additions carry can never exceed 1



When could carry be 2 or more?

  9
  9
  9
----
 27

Only if you add more numbers in the same column.

 */
public class PlusOne {
    public static void main(String[] args) {
        int[] digits = {1,2,3};
        System.out.println("plusOne [1,2,3] -> " + Arrays.toString(plusOne(digits))); // Output: [1, 2, 4]

        digits = new int[]{9,9,9};
        System.out.println("plusOne [9,9,9] -> " + Arrays.toString(plusOne(digits))); // Output: [1, 0, 0, 0]
    }

    public static int[] plusOne(int[] digits) {
        return plusOneUsingGenericCarryForward(digits);
    }


    /**
     * @TimeComplexity O(n)
     * @SpaceComplexity O(1)
     */
    public static int[] plusOneUsingGenericCarryForward(int[] digits) {
        int n = digits.length;
        int carryForward = 1;
        for (int i=n-1; i>=0; i--) {
            int digit = digits[i];
            digit += carryForward;
            digits[i] = digit % 10;
            carryForward = digit / 10;
        }

        if (carryForward == 0) {
            return digits;
        }

        int[] result = new int[n+1];
        result[0] = 1;
        // System.arraycopy(digits, 0, result, 1, n);
        for (int i=1; i<n+1; i++) {
            result[i] = digits[i-1];
        }
        return result;
    }


    /**
     * @TimeComplexity O(n)
     * @SpaceComplexity O(1)
     */
    public static int[] plusOneUsingSchoolbookAdditionWithCarry1(int[] digits) {

        for (int i=digits.length-1; i>=0; i--){
            if (digits[i]<9){
                digits[i]++; // digits[i]+=1;
                return digits;
            }
            digits[i]=0;
        }

        int[] result = new int[digits.length+1];
        result[0] = 1;
        return result;
    }



    /**
     * @TimeComplexity O(n)
     * @SpaceComplexity O(1)
     */
    public static int[] plusOneUsingSchoolbookAdditionWithCarry2(int[] digits) {
        int n = digits.length;

        for (int i = n - 1; i >= 0; --i) {
            if (digits[i] == 9) {
                digits[i] = 0;
            } else {
                digits[i]++; // digits[i]+=1;
                return digits;
            }
        }

        digits = new int[n + 1];
        digits[0] = 1;
        return digits;
    }
}
