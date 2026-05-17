package Algorithms.BitManipulation;

/**
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 14 May 2026
 * @link 201. Bitwise AND of Numbers Range <a href="https://leetcode.com/problems/bitwise-and-of-numbers-range/">LeetCode link</a>
 * @topics Bit Manipulation
 * @companies Google(8), Amazon(5), Microsoft(3), Meta(2), Bloomberg(2)
 */
public class BitwiseAndOfNumbersRange {
    public static void main(String[] args) {
        int left = 5, right = 7;
        System.out.println("rangeBitwiseAnd 1 => " + rangeBitwiseAndUsing32Bit(left, right));
    }



    /**
     * @TimeComplexity O(right - left) = O(n)
     * @SpaceComplexity O(1)
     */
    public static int rangeBitwiseAndUsingLoopTLE1(int left, int right) {
        int res = left;
        for (int i = left+1; i<=right; i++) {
            res &= i;
        }
        return res;
    }


    /**
     * @TimeComplexity O(right - left) = O(n)
     * @SpaceComplexity O(1)
     */
    public static int rangeBitwiseAndUsingLoopTLE2(int left, int right) {
        if (left == 0) return 0;
        int leftBit = left++, rightBit = right--;
        while(left < right+1) {
            leftBit &= left;
            left++;

            rightBit &= right;
            right--;
        }

        return leftBit&rightBit;
    }


    /**
        Here, we use the "res" var and save the "&" of all the numbers in the range.

        We actually dont need each and every number in the range.

        We know the first bit of left.

        7   =   0 1 1 1
        8   =   1 0 0 0
        9   =   1 0 0 1
        10  =   1 0 1 0
     ---------------------

        and if the r-l > remaining bits diff



     * @TimeComplexity O(32) = O(1)
     * @SpaceComplexity O(1)
     */
    public static int rangeBitwiseAndUsing32Bit(int left, int right) {
        int res = 0;

        for (int i=0; i<32; i++) {
            int bit = (left >> i) & 1;
            if (bit == 0) continue; // if left curr bit is 0 then & of all nums bits will be 0

            int remain = left % (1 << (i+1));
            int diff = (1 << (i+1)) - remain;

            if (right - left < diff){
                res = res | (1 << i);
            }
        }

        return res;
    }




    /**
     * @TimeComplexity O(log(right-left) = O(logn)
     * @SpaceComplexity O(1)
     */
    public static int rangeBitwiseAndUsingBitShift(int left, int right) {
        int shift = 0;

        // Find common prefix
        while (left != right) {
            left >>= 1;
            right >>= 1;
            shift++;
        }
        // Shift back to the left to restore the prefix position
        return left << shift;
    }




    /**
     * @TimeComplexity O(log(right-left) = O(logn)
     * @SpaceComplexity O(1)
     */
    public static int rangeBitwiseAndUsingBrainKernighansAlgorithm(int left, int right) {
        while (left < right) {
            right = right & (right - 1);
        }
        return right;
    }






}
