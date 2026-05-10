package Algorithms.BitManipulation;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 01 May 2026
 * @link 190. Reverse Bits <a href="https://leetcode.com/problems/reverse-bits/">LeetCode link</a>
 * @topics Bit Manipulation, Divide and Conquer
 * @companies Google(6), Amazon(4), Bloomberg(2), Meta(9), Apple(9), Nvidia(6), Microsoft(4), Qualcomm(4), Airbnb(2), Accenture(2)

    constraints:
    -------------
    0 <= n <= 2^31 - 2
    n is even and 32 bits signed integer

    Example:
    ---------
    n = 4
    binary = 100
    reversed = 001
    reversedN = 1

    n = 4
    32 bits binary = 00000000000000000000000000000100
    reversed = 00100000000000000000000000000000
    reversedN = 536870912


    we know that to get the kth bit, we can use "n & (1 << k)"
    and it's like to get the last rightMost bit we use "n & 1"
    cause 1 & 1 = 1 and 1 & 0 = 0 ---> so, we get the last rightMost bit



 */
public class ReverseBits {
    public static void main(String[] args) {
        int n = 43261596;
        System.out.println("reverseBits using binary string reverse: " + reverseBitsUsingBinaryStringReverse(n));
        System.out.println("reverseBits using bit by bit: " + reverseBitsUsingBitByBit1(n));
        System.out.println("reverseBits using byte by byte with memoization: " + reverseBitsUsingByteByByteWithMemoization(n));
        System.out.println("reverseBits using mask and shift: " + reverseBitsUsingMaskAndShift(n));
        /*
            🥇 Mask & Shift (best)
            🥈 Lookup Table
            🥉 Bit-by-bit
            ❌ String (avoid unless forced)
         */
    }


    /**
     * it's similar to {@link #reverseBitsUsingMaskAndShift}
     * @TimeComplexity O(1)
     * @SpaceComplexity O(1)
     */
    public static int reverseBitsUsingInbuiltIntegerReverseMethod(int n) {
        return Integer.reverse(n);
    }




    /**
     * @TimeComplexity O(n) = O(32)
     * @SpaceComplexity O(n) = O(32)
     */
    public static int reverseBitsUsingBinaryStringReverse(int n) {
        String binary = Integer.toBinaryString(n);
        int prefixZeros = 32 - binary.length(); // remaining zeros ---> cause, we're filling all 32 bits
        binary = String.join("", Collections.nCopies(prefixZeros, "0")) + binary;
        // or String binary = String.format("%32s", Integer.toBinaryString(n)).replace(' ', '0'); // %32s is left & %-32S is right padding
        String reversedBinary = new StringBuilder(binary).reverse().toString();
        return Integer.parseInt(reversedBinary, 2);
    }





    /**
     * @TimeComplexity O(n) = O(32)
     * @SpaceComplexity O(1)

        n & 1 will get the last rightMost bit
        n & (1 << k) will get the kth bit

        so, we do n&1 to get the last rightMost bit
        and use n>>1 to shift n to the right and then if we do n&1 again we get the next rightMost bit ---> repeat this process 32 times

        and save the last rightMost bit "n&1" by | or + the result 🔥

        before adding the n&1 last bit in result, we shift result left by 1 ---> make space for the n&1 bit

        repeat this process 32 times

        if we initialize the result = 0 ---> "000..0" 32 zeros
        result << 1 ---> does nothing as result = 0
        result | (n & 1) ---> adds the last rightMost bit of n




     */
    public static int reverseBitsUsingBitByBit1(int n) {
        int result = 0;
        for (int i = 0; i < 32; i++) {
            result <<= 1;         //or result = result << 1; or result = result * 2;    ---> shift result left
            result |= (n & 1);    //or result = result | (n & 1);                       ---> add last bit of n
            n >>= 1;              //or n = n >> 1; or n >>>= 1; or n = n / 2;           --->  shift n right
        }
        return result;
    }


    /**
     * @TimeComplexity O(n) = O(32)
     * @SpaceComplexity O(1)
     */
    public static int reverseBitsUsingBitByBit2(int n) {
        int result = 0;
        for (int i = 0; i < 32; i++) {
            int lastBit = (n>>i) & 1;
            result = result | (lastBit << (31 - i));
        }
        return result;
    }





    /**
     * @TimeComplexity O(n) = O(32)
     * @SpaceComplexity O(1)
     */
    public static int reverseBitsUsingBitByBit3(int n) {
        int result = 0;
        for (int i = 0; i < 32; i++) {
            result = (result << 1) + (n & 1); // + or |
            n >>= 1;
        }
        return result;
    }





    /**
     * @TimeComplexity O(n) = O(32)
     * @SpaceComplexity O(1)
     */
    public static int reverseBitsUsingBitByBit4(int n) {
        int ret = 0, power = 31;
        while (n != 0) {
            ret += (n & 1) << power;
            n = n >>> 1;
            power -= 1;
        }
        return ret;
    }





    /**
     * reverseBitsUsingLookupTable
     * // you need treat n as an unsigned value
     * @TimeComplexity O(n) = O(32)
     * @SpaceComplexity O(1)
     */
    public static int reverseBitsUsingByteByByteWithMemoization(int n) {
        int ret = 0;
        int power = 24;
        Map<Integer, Integer> cache = new HashMap<>();
        while (n != 0) {
            ret += reverseByte(n & 0xff, cache) << power;
            n >>>= 8; // Use unsigned shift
            power -= 8;
        }
        return ret;
    }
    private static int reverseByte(int byteVal, Map<Integer, Integer> cache) {
        if (cache.containsKey(byteVal)) {
            return cache.get(byteVal);
        }
        int value = (int)(((byteVal * 0x0202020202L) & 0x010884422010L) % 1023);
        cache.put(byteVal, value);
        return value;
    }







    /**
     * reverseBitsUsingBitwiseDivideAndConquer
     * reverseBitsUsingMaskSwapping
     * @TimeComplexity O(1)
     * @SpaceComplexity O(1)
     */
    public static int reverseBitsUsingMaskAndShift(int n) {
        n = (n >>> 16) | (n << 16);
        n = ((n & 0xff00ff00) >>> 8) | ((n & 0x00ff00ff) << 8);
        n = ((n & 0xf0f0f0f0) >>> 4) | ((n & 0x0f0f0f0f) << 4);
        n = ((n & 0xcccccccc) >>> 2) | ((n & 0x33333333) << 2);
        n = ((n & 0xaaaaaaaa) >>> 1) | ((n & 0x55555555) << 1);
        return n;
    }


}
