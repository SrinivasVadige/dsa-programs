package Algorithms.BitManipulation;

/**
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 17 May 2025
 */
public class CountingBits {
    public static void main(String[] args) {
        int n = 5;
        System.out.println("countBits => " + countBits(n));
    }


    /**
     *  0   -      0      dp[0] = 0
     *  1   -      1      dp[n-1] + 1
     *  2   -     10      dp[n-2] + 1
     *  3   -     11      dp[n-2] + 1
     *  4   -    100      dp[n-4] + 1
     *  5   -    101      dp[n-4] + 1
     *  6   -    110      dp[n-4] + 1
     *  7   -    111      dp[n-4] + 1
     *  8   -   1000      dp[n-8] + 1
     *  9   -   1001      dp[n-8] + 1
     *  10  -   1010      dp[n-8] + 1
     *  11  -   1011      dp[n-8] + 1
     *  12  -   1100      dp[n-8] + 1
     *  13  -   1101      dp[n-8] + 1
     *  14  -   1110      dp[n-8] + 1
     *  15  -   1111      dp[n-8] + 1
     *  16  -  10000      dp[n-16] + 1
     *  17  -  10001
     *  18  -  10010
     *  19  -  10011
     *  20  -  10100
     *  21  -  10101
     *  22  -  10110
     *  23  -  10111
     *  24  -  11000
     *  25  -  11001
     *  26  -  11010
     *  27  -  11011
     *  28  -  11100
     *  29  -  11101
     *  30  -  11110
     *  31  -  11111
     *  32  - 100000
     *  33  - 100001
     *
     * We see that 00, 01, 10, 11, 100, 101, 110, 111 pattern is repeating --> 8
     */
    public static int[] countBits(int n) {
        int[] dp = new int[n + 1];
        int offset = 1;

        for (int i=1; i<=n; i++) {
            if (offset * 2 == i) {
                offset = i;
            }
            dp[i] = 1 + dp[i - offset];
        }
        return dp;
    }


    /**
     * @TimeComplexity O(n)
     * @SpaceComplexity O(n)
     */
    public static int[] countBits2(int n) {
        int[] res = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            res[i] = res[i & (i - 1)] + 1;
        }
        return res;
    }





    public int[] countBits3(int n) {
        int[] res = new int[n + 1];

        for (int i = 1; i <= n; i++) {
            res[i] = res[i >> 1] + (i & 1); // or res[i/2] + i%2  ---> dividing by 2 is same as right shift by 1 bit i.e i>>1
        }

        return res;
    }





    public int[] countBits4(int n) {
        int[] res = new int[n+1];
        load(res,1,1);

        return res;
    }
    private void load(int[] res,int ind,int val){
        if(ind >= res.length)
            return;

        res[ind] = val;
        load(res,ind*2,val);
        load(res,ind*2+1,val+1);
    }







    public int[] countBits5(int n) {
        int[] ret = new int[n + 1];
        int off = 1;
        for (int i = 0; i < ret.length - 1; i++){
            if (off * 2 == i + 1) off *= 2;
            ret[i + 1] = ret[i + 1 - off] + 1;
        }
        return ret;
    }





    public int[] countBits6(int n) {
        int[] res=new int[n+1];
        for(int i=0;i<=n;i++) res[i]=gSB(i);
        return res;
    }
    private int gSB(int n){
        int count=0;
        for(int i=0;i<=31;i++){
            if((n&(1<<i))>0) count++;
        }
        return count;
    }














    /**
     * @TimeComplexity O(nlogn)
     * @SpaceComplexity O(n)
     */
    public int[] countBitsUsingBinaryString(int n) {
        int[] ans = new int[n+1];
        for(int i=0; i<=n; i++) {
            int num = i;
            while(num>0) { // O(log n) TimeComplexity because of num=num/2
                if(num%2 == 1) ans[i]++;
                num=num/2;
            }
            /**
            String binary = "";
            int num = 2;
            while (num > 0) {
                binary = (num % 2) + binary; // O(log n) TimeComplexity
                num /= 2;
            }
            System.out.println(binary);
            // Integer.toBinaryString(10) ---> to convert num to binary
            // Integer.parseInt(binaryStr, 2) ---> to convert binary to num
            */
        }
        return ans;
    }



    public int[] countBitsUsingBinaryString2(int n) {
       int[] result = new int[n+1];
       result[0]=0;
       for(int i=1;i<=n;i++){
        int count = 0;
        int temp = i;
        while(temp>0){
            if((temp&1) == 1) count++;
            temp= temp>>1; // or temp>>=1
        }
        result[i] = count;
       }
       return result;
    }
}
