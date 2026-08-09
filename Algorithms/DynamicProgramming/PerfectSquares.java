package Algorithms.DynamicProgramming;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <pre>
 *
 * It is same as Coin Change problem
 *
 * for n = 13
 * => 3² + 2² = 9 + 4 ---> 2 count
 * => 3² + 1² + 1² + 1² + 1² = 9 + 1 + 1 + 1 + 1 --> 5 count
 * => 2² + 2² + 1² + 1² + 1² + 1² + 1² = 4 + 4 + 1 + 1 + 1 + 1 + 1 --> 7 count
 * => 2² + 2² + 2² + 1² = 4 + 4 + 4 + 1 --> 4 count
 * => 1² + 1² + 1² + 1² + 1² + 1² + 1² + 1² + 1² + 1² + 1² + 1² --> 12 count
 * ... so on
 * Finally min square root sum pair count is 2 (because of n=9+4)
 *
 * similarly for n=12, check the need graph
 *                                                     0² need 12
 *                                    ____________________|_____________________________________________
 *                                   |                       |                                          |
 *                        1² -> 12-1 -> need 11        2²->12-4 -> need 8 ___ 1²-7, 2²-4, 3²-❌       3²-> 12-9 -> need 3
 *         _______________________|_______________________________                                      |
 *        |                           |                           |                             1²-2, 2²-❌, 3²-❌
 *        1² -> 11-1 -> need 10      2² -> 11-4 -> need 7         3² -> 11-9 -> need 3
 *    _______|_______           ________|________         __________|______
 *    |      |       |         |        |        |        |        |       |
 * 1²-9    2²-6    3²-❌     1²-6     2²-3     3²-❌     1²-2    2²-❌    3²-❌
 *                                                        |
 *                                                        1²->2-1->need 1
 *
 * Here, we can find some patterns like need 3, 5, 7... some numbers are repeated
 * -> so, use dp array instead of calculating it again
 * for 12 => 4 + 4 + 4 i.e 3 min pairs to sum for 12
 *
 * </pre>
 * @author Srinivas Vadige srinivas.vadige@gmail.com
 * @since 02 Nov 2024
 */
public class PerfectSquares {
    public static void main(String[] args) {
        int n = 13;
        System.out.println(numSquares(n));
        System.out.println(numSquaresMyApproach(n));
    }

    /**
     * @TimeComplexity: O(n * sqrt(n)) -- 2nd loop is only for squares
     * @SpaceComplexity: O(n)
     */
    public static int numSquares(int n) {
        int[] dp = new int[n + 1]; // each number min pair count i.e from 1 to n & 0 is dummy
        Arrays.fill(dp, Integer.MAX_VALUE); // or Arrays.fill(dp, n);
        dp[0] = 0; // to reach 0 sum we don't need to add any pairs to get sum '0'.. and this dp[0]=0 is used in dp[target - square]
        for (int target = 1; target <= n; target++) { // target = targetSum and dp[target]= min pair count of that target
            for (int i = 1; i * i <= target; i++) { // all possible perfect squares but not exceed the targetSum
                int square = i * i;
                int remainingTarget = target - square; // always remainingTarget < target and we already calculated it in previous target loop
                dp[target] = Math.min(dp[target], 1 + dp[remainingTarget]); // +1 is for current square count
                // here we check min & override the dp[target] value in each square loop
                // And Math.min(dp[target], ..) -- cause we already calculated same target dp[target] as we already have looped with previous squares
                // Eg: when target is 9 then we already calculated 1² and 2² scenarios when current square is 3²
            }
        }
        return dp[n];
    }

    public static int numSquares2(int n) {
        int[] dp = new int[n + 1];
        Arrays.fill(dp, n);
        dp[0] = 0;
        for (int target = 1; target <= n; target++) {
            for (int i=1; i <= target; i++) { // or  for (int i=1, square = i * i; i * i <= target; i++, square = i * i) { // -- square val needs to calculate every time
                if(target - i*i < 0) break;
                dp[target] = Math.min(dp[target], 1 + dp[target - i*i]);
            }
        }
        return dp[n];
    }







    /**
     * Not working
     */
    public static int numSquaresMyApproach(int n) {
        List<Integer> psList = new ArrayList<>();
        for (int i=1; i*i<=n; i++) { // prepare squares
            psList.add(i);
        }
        int[] needArr = new int[n];
        System.out.println(psList);
        needArr[needArr.length-1] = rec(n, psList, needArr);
        return Math.max(needArr[needArr.length-1], 1);
    }

    public static int rec(int sum, List<Integer> psList, int[] needArr){
        if(needArr[sum] != 0) return needArr[sum];

        // needSum min count hashMap or dp
        for (int i = psList.size()-1; i >= 0; i--) {
            int sqr = psList.get(i);
            int num = sqr*sqr; // square
            // how many times num as to add??
            int tempC = 0;
            int needSum = 0;

            for(int j =1; num*j <= sum; j++) {
                tempC++;

                needSum = sum-(num*j);
                // check need sum
            }

            needSum = sum-(tempC*num);
            System.out.println("needSum: " + needSum + ", tempC: " + tempC);
            if(needSum == 0) return tempC;
        }
        return 0;
    }

    public int recDummy(int n, int[] dp){
        if (n < 1 || n>dp.length-1) return 0;
        if (dp[n] > 0) return dp[n]; // bc

        int ps = perfectSquare(n);
        dp[n] = ps;
        return recDummy(n-ps, dp);
    }

    /**
     * NOT WORKING
     */
    public int numSquaresBottomsUpMyApproach(int n) {
        int[] dp = new int[n];
        dp[1] = 1; // number as index and value as (perfect square count)
        for (int i=4; i>0; i++) {
            int ps = perfectSquare(i); // optimize later
            // n-ps => need perfect squares count -- sub problem
            if(ps != 0) {
                int count = (n % ps == 0)? n/ps: 0;
                dp[i] = count;
                // n-ps -- dp?
            }
        }
        return 0;
    }

    private int perfectSquare(int i) {
        // for or any built-ins?
        for (int j=2; j<=i; j++) {
            if(j*j == i) return j;
        }
        return 0;
    }

}
