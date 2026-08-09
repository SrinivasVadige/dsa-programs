package Algorithms.BitManipulation;

/**
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 18 May 2025
 * @link https://leetcode.com/problems/minimum-bit-flips-to-convert-number
 *
 * Here Flip means to flip 0 to 1 or 1 to 0 bit
 */
public class MinimumBitFlipsToConvertNumber {
    public static void main(String[] args) {
        int start = 10, goal = 7;
        System.out.println("minBitFlips => " + minBitFlips(start, goal));

    }


    // Brian Kernighan's Algorithm "n & (n-1)" --> removes one '1' from n's binary representation
    public static int minBitFlips(int start, int goal) {
        int count = 0;
        int n = start ^ goal; // xor - if we have same, then xor returns 0
        while (n > 0) {
            count++;
            n = n & (n-1); // clear the rightmost set bit
        }
        return count;
    }

    public static int minBitFlips2(int start, int goal) {
        int count = 0, xor = start ^ goal; // if we have same then xor returns 0
        while (xor > 0) {
            count += xor & 1; // or if (xor % 2 == 1) count++; if the last bit is 1, increment count by 1
            xor = xor >> 1; // or xor/=2; shift the bits to the right by 1
        }
        return count;
    }


    public static int minBitFlips3(int start, int goal) {
        int ans = start ^ goal;
        int count = 0;
        while (ans > 0) {
            if (ans % 2 == 1) count++; // Count 1s, not 0s
            ans = ans / 2;
        }
        return count;
    }



    public static int minBitFlips4(int start, int goal) {
        int count = 0;
        int x = start ^ goal; // xor
        while (x > 0) {
            x = x & (x-1);
            count++;
        }
        return count;
    }




    public static int minBitFlipsMyApproach(int start, int goal) {
        int c=0;
        while(start>0 || goal>0) {
            if (start%2 != goal%2) c++; // or if ((start&1) != (goal&1)) c++;
            start/=2; // or start>>=1;
            goal/=2; // or goal>>=1;
        }
        return c;
    }


    public static int minBitFlipsMyApproach2(int start, int goal) {
        int c=0;
        while(start>0 && goal>0) {
            if (start%2 != goal%2) c++;
            start/=2;
            goal/=2;
        }
        while(start>0) {
            if(start%2==1) c++;
            start/=2;
        }
        while(goal>0) {
            if(goal%2==1) c++;
            goal/=2;
        }
        return c;
    }


    public static int minBitFlipsMyApproach3(int start, int goal) {
        // max binary length of a number is 32 bits
        char[] b1 = new char[32]; // it initializes with '\0' but not with '0'
        char[] b2 = {'0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0'};


        int c=0;
        int i1 = 31;
        int i2 = 31;
        while(start>0 && goal>0) {
            if (start%2 != goal%2) c++;
            b1[i1--]=start%2==0?'0':'1';
            b2[i2--]=goal%2==0?'0':'1';
            start/=2;
            goal/=2;
        }
        while(start>0) {
            if(start%2==1) c++;
            b1[i1--]=start%2==0?'0':'1';
            start/=2;
        }
        while(goal>0) {
            if(goal%2==1) c++;
            b2[i2--]=goal%2==0?'0':'1';
            goal/=2;
        }
        System.out.println(b1);
        System.out.println(b2);
        return c;
    }
}
