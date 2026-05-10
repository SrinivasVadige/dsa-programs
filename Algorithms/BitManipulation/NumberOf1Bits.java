package Algorithms.BitManipulation;

/**
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 10 May 2026
 * @link 191. Number of 1 Bits <a href="https://leetcode.com/problems/number-of-1-bits/">LeetCode link</a>
 * @topics Bit Manipulation, Divide and Conquer
 * @companies Google(2), Amazon(3), Meta(2), Apple(8), Box(7), Microsoft(5), Bloomberg(4), Nvidia(3), Verkada(3)
 */
public class NumberOf1Bits {
    public static void main(String[] args) {
        int n = 11;
        System.out.println("number of 1 bits using in-built method: " + hammingWeightUsingInbuiltMethod(n));
        System.out.println("number of 1 bits using bit by bit 1: " + hammingWeightUsingBitByBit1(n));
        System.out.println("number of 1 bits using bit by bit 2: " + hammingWeightUsingBitByBit2(n));
        System.out.println("number of 1 bits using mask: " + hammingWeightUsingMask(n));
        System.out.println("number of 1 bits using divide and conquer: " + hammingWeightUsingBitwiseDivideAndConquer(n));
        System.out.println("number of 1 bits using string manipulation: " + hammingWeightUsingStringManipulation(n));
    }


    public static int hammingWeightUsingInbuiltMethod(int n) {
        return Integer.bitCount(n);
    }


    /**
     * @TimeComplexity O(32) = O(1) ---> as the input is always 32 bits
     * @SpaceComplexity O(1)
     */
    public static int hammingWeightUsingBitByBit1(int n) {
        int count = 0;
        while(n>0){
            if ((n&1)==1) count++; // or n%2==1 or count += (n&1)
            n>>=1;
        }
        return count;
    }



    /**
        It's improved version of {@link #hammingWeightUsingBitByBit1}

        cause if we check set bits in "1000001" then why we need to check all bits?
        so, use n&(n-1)

        n =         "1100"
        n-1 =       "1011"
        n&(n-1) =   "1000" ---> removes rightmost set bit and just loop until n becomes 0

        Example walkthrough
        n = 13 = 1101
        Iteration 1
        1101
        1100
        ----
        1100
        count = 1

        Iteration 2
        1100
        1011
        ----
        1000
        count = 2

        Iteration 3
        1000
        0111
        ----
        0000
        count = 3

     * @TimeComplexity O(1) ---> as time as above {@link #hammingWeightUsingBitByBit1} but this is improved
     * @SpaceComplexity O(1)
     */
    public static int hammingWeightUsingBitByBit2(int n) {
        int sum = 0;
        while (n != 0) {
            sum++;
            n &= (n - 1);
        }
        return sum;
    }






    /**
     * @TimeComplexity O(1)
     * @SpaceComplexity O(1)
     */
    public static int hammingWeightUsingMask(int n) {
        int bits = 0;
        int mask = 1;
        for (int i = 0; i < 32; i++) {
            if ((n & mask) != 0) {
                bits++;
            }
            mask <<= 1;
        }
        return bits;
    }






    /**
     * @TimeComplexity O(1)
     * @SpaceComplexity O(1)
     */
    public static int hammingWeightUsingBitwiseDivideAndConquer(int n) {
        if (n==0) return 0;
        return (n & 1) + hammingWeightUsingBitwiseDivideAndConquer(n >>> 1);
    }







    /**
     * @TimeComplexity O(1)
     * @SpaceComplexity O(n)
     */
    public static int hammingWeightUsingStringManipulation(int n){
        int count=0;
        String binary = Integer.toBinaryString(n);
        for(char c: binary.toCharArray()) {
            if (c=='1') count++;
        }
        return count;
    }
}
