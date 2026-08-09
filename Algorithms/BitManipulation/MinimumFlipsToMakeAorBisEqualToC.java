package Algorithms.BitManipulation;

/**
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 18 May 2025
 * @link 1318 https://leetcode.com/problems/minimum-flips-to-make-a-or-b-equal-to-c
 *
 * Example 1:
 * Input: a = 2, b = 6, c = 5
 * Output: 3
 *
 * 2=0010
 * 6=0110
 * 5=0101
 *
 *          2 = 0   0   *1   *0
 *          6 = 0   1   *1    0
 *              ________________
 *          5 = 0   1    0    1
 *
 * Here, we need to flip the above marked *s to make "a | b = c"
 *
 * And if we consider the truth table for a, b and c
 *
 * a b c  a|b
 * 0 0 0   0
 * 0 0 1   0    ---> expected c = 1, but got a|b = 0
 * 0 1 0   1    ---> expected 0, but got 1
 * 0 1 1   1
 * 1 0 0   1    ---> expected 0, but got 1
 * 1 0 1   1
 * 1 1 0   1    ---> expected 0, but got 1
 * 1 1 1   1
 *
 * Here, we need to focus on these 4 cases:
 * to get c='1', a|b - one of th a or b bit has to be '1'
 * to get c='0', a|b - both a and b bits has to be '0'
 *
 * Note that n&1 and n%2 are same --> returns the first bit (rightmost bit) of n
 */
public class MinimumFlipsToMakeAorBisEqualToC {
    public static void main(String[] args) {
        int a = 2, b = 6, c = 5;
        System.out.println("minFlipsMyApproach => " + minFlipsMyApproach(a, b, c));
        System.out.println("minFlips => " + minFlips(a, b, c));
    }

    public static int minFlipsMyApproach(int a, int b, int c) {
        int flips = 0;
        while (a>0 || b>0 || c>0) {
            int aBit = a&1;
            int bBit = b&1;
            int cBit = c&1;

            if ((aBit | bBit) != cBit) { // if (aBit|bBit == cBit) continue;
                if (cBit==1 && aBit==0 && bBit==0) flips++;
                if (cBit==0 && aBit==1 && bBit==1) flips+=2;
                if (cBit==0 && (aBit==0 && bBit==1 || aBit==1 && bBit==0)) flips++;
            }

            a >>= 1;
            b >>= 1;
            c >>= 1;
        }
        return flips;
    }




    public static int minFlips(int a, int b, int c) {
        int flips = 0;
        while (a>0 || b>0 || c>0) {
            int aBit = a&1;
            int bBit = b&1;
            int cBit = c&1;

            // aBit | bBit == cBit

            if (cBit==1) { // at least one bit has to be '1'
                if(aBit==0 && bBit==0) flips++; // or if(aBit | bBit != 1) flips++;
            } else if (cBit==0) { // both bits has to be '0'
                if(aBit==1) flips++;
                if(bBit==1) flips++;
                // or flips += (aBit+bBit);
            }

            a >>= 1;
            b >>= 1;
            c >>= 1;
        }
        return flips;
    }



    public static int minFlips2(int a, int b, int c) {
        int flips=0;
        while(a>0 || b>0 || c>0) {
            int a1=a&1;
            int b1=b&1;
            int c1=c&1;
            if((a1|b1)!=c1) {
                if((a1&b1)==1) flips+=2;
                else flips+=1;
            }
            a>>=1;
            b>>=1;
            c>>=1;
        }
        return flips;
    }






    public static int minFlips3(int a, int b, int c) {
        int flips = 0;
        while (a>0 || b>0 || c>0) {
            int aBit = a%2;
            int bBit = b%2;
            int cBit = c%2;

            if (cBit==1) flips += ((aBit | bBit)==0?1:0); // or if(cBit==1 && aBit==0 && bBit==0) flips++;
            else flips += (aBit + bBit);
            a/=2;
            b/=2;
            c/=2;
        }
        return flips;
    }





    /**
     * NOT WORKING
     *
     * In some cases, we need to change both a and b bit to match with c bit
     */
    public static int minFlipsNotWorking(int a, int b, int c) {
        int count = 0;
        int x = a | b; // x will be the result of a | b
        int y = c ^ x; // y will be the result of c ^ x
        while (y > 0) {
            y = y & (y - 1);
            count++;
        }
        return count;
    }
}
