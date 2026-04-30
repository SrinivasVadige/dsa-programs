package Algorithms.BitManipulation;

import java.math.BigInteger;

/**
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 28 Apr 2026
 * @link 67. Add Binary <a href="https://leetcode.com/problems/add-binary/">LeetCode link</a>
 * @topics Math, String, Bit Manipulation, Simulation
 * @companies Google(8), Amazon(6), Bloomberg(3), Deloitte(3), Meta(2), Microsoft(2), Visa(2), TCS(5), Apple(5), Walmart Labs(4), IBM(2), Infosys(2), Adobe(2), Wipro(2), Snap(2)
 */
public class AddBinary {
    public static void main(String[] args) {
        String a = "1010", b = "1011";
        System.out.println("addBinary 1 => " + addBinary1(a, b));
        System.out.println("addBinary 2 => " + addBinary2(a, b));
        System.out.println("addBinary 3 => " + addBinary3(a, b));
        System.out.println("addBinary 4 => " + addBinary4(a, b));
        System.out.println("addBinary 5 => " + addBinary5(a, b));
        System.out.println("addBinary 6 => " + addBinary6(a, b));
    }


    /**
     * ⚠️ Limitation: works only for values fitting in int (or long if upgraded)
     */
    public static String addBinary1(String a, String b) {
        return Integer.toBinaryString(
            Integer.parseInt(a, 2) + Integer.parseInt(b, 2)
        );
    }


    /**
     * String + → rebuild every time ❌
     * @TimeComplexity O(n^2) ---> due to String +
     * @SpaceComplexity O(n)
     */
    public static String addBinary2(String a, String b) {
        String sum = "";
        int carry = 0;

        for (int i = a.length() - 1, j = b.length() - 1; i >= 0 || j >= 0; i--, j--) {
            int aBinary = 0, bBinary = 0;

            if (i >= 0) aBinary = a.charAt(i) - '0';
            if (j >= 0) bBinary = b.charAt(j) - '0';

            sum = (aBinary ^ bBinary ^ carry) + sum;
            carry = (aBinary + bBinary + carry >= 2) ? 1 : 0;

            /*
                // or
                int totalSum = aBinary + bBinary + carry;
                int num = totalSum % 2;
                sum = num + sum;
                carry = totalSum / 2;
             */
        }

        if (carry == 1) sum = carry + sum;

        return sum;
    }






    /**
     * @TimeComplexity O(n)
     * @SpaceComplexity O(n)
     */
    public static String addBinary3(String a, String b) {
        StringBuilder result = new StringBuilder();

        int i = a.length() - 1;
        int j = b.length() - 1;
        int carry = 0;

        while (i >= 0 || j >= 0 || carry != 0) {
            int sum = carry;

            if (i >= 0) sum += a.charAt(i--) - '0';
            if (j >= 0) sum += b.charAt(j--) - '0';

            result.append(sum % 2);   // current bit
            carry = sum / 2;          // carry
        }

        return result.reverse().toString();
    }








    /**
     * ⚠️ Limitation: works only for values fitting in int (or long if upgraded)
     * @TimeComplexity O(n)
     * @SpaceComplexity O(n)
     */
    public static String addBinary4(String a, String b) {
        int x = Integer.parseInt(a, 2);
        int y = Integer.parseInt(b, 2);

        while (y != 0) {
            int carry = (x & y) << 1;
            x = x ^ y;
            y = carry;
        }

        return Integer.toBinaryString(x);
    }







    /**
     * @TimeComplexity O(n)
     * @SpaceComplexity O(n)
     */
    public static String addBinary5(String a, String b) {
        int n = a.length(), m = b.length();
        if (n < m) return addBinary5(b, a);

        StringBuilder sb = new StringBuilder();
        int carry = 0, j = m - 1;
        for (int i = n - 1; i > -1; --i) {
            if (a.charAt(i) == '1') ++carry;
            if (j > -1 && b.charAt(j--) == '1') ++carry;

            if (carry % 2 == 1) sb.append('1');
            else sb.append('0');

            carry /= 2;
        }
        if (carry == 1) sb.append('1');
        sb.reverse();

        return sb.toString();
    }







    /**
     * @TimeComplexity O(n)
     * @SpaceComplexity O(n)
     */
    public static String addBinary6(String a, String b) {
        BigInteger x = new BigInteger(a, 2);
        BigInteger y = new BigInteger(b, 2);
        BigInteger zero = new BigInteger("0", 2);
        BigInteger carry, answer;
        while (y.compareTo(zero) != 0) {
            answer = x.xor(y);
            carry = x.and(y).shiftLeft(1);
            x = answer;
            y = carry;
        }
        return x.toString(2);
    }
}
