package Algorithms.DynamicProgramming;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 27 Feb 2025
 */
public class BestTimeToBuyAndSellStock {
    public static void main(String[] args) {
        int[] prices = {7,1,5,3,6,4};
        System.out.println("maxProfit using l,r pointers => " + maxProfitUsingTwoPointers(prices));
        System.out.println("maxProfit using one pointer - minPrice => " + maxProfitUsingOnePointer(prices));
        System.out.println("maxProfit using bottom up tabulation dp => " + maxProfitUsingBottomUpTabulationDp(prices));
        System.out.println("maxProfit using brute force => " + maxProfitUsingBruteForce(prices));
    }

    /**
     * @TimeComplexity O(n)
     * @SpaceComplexity O(1)
     *
     *          |
     *          |
     *      7   |     *
     *          |     |
     *      6   |     |                             *
     *          |     |                             |
     *      5   |     |             *               |
     *          |     |             |               |
     *      4   |     |             |               |       *
     *          |     |             |               |       |
     *      3   |     |             |       *       |       |
     *          |     |             |       |       |       |
     *      2   |     |             |       |       |       |
     *          |     |             |       |       |       |
     *      1   |     |      *      |       |       |       |
     *          |_____|______|______|_______|_______|_______|
     *                0      1      2       3       4       5
     *                l      r
     *                       l      r
     *                       l              r
     *                       l                      r
     *                       l                              r
     */
    public static int maxProfitUsingTwoPointers(int[] prices) {
        int maxProfit = 0, l = 0, r = 1;
        while(r<prices.length){
            if(prices[l] > prices[r]) l = r;
            else maxProfit = Math.max(maxProfit, prices[r] - prices[l]); // or if(tempProfit > maxProfit) maxProfit = tempProfit;
            r++;
        }
        return maxProfit;
    }


    /**
     * @TimeComplexity O(n)
     * @SpaceComplexity O(1)
     *
     * This approach is same as above {@link #maxProfitUsingTwoPointers(int[])}
     * but instead of l & r use minPrice and for loop
     */
    public static int maxProfitUsingOnePointer(int[] prices) {
        int maxProfit = 0, minPrice = Integer.MAX_VALUE;
        for (int i = 0; i < prices.length; i++) {
            minPrice = Math.min(minPrice, prices[i]); // the minPrice up to i
            maxProfit = Math.max(maxProfit, prices[i] - minPrice);
        }
        return maxProfit;
    }



    /**
     * @TimeComplexity O(n)
     * @SpaceComplexity O(n)
     */
    public static int maxProfitUsingBottomUpTabulationDp(int[] prices) {
        int n = prices.length;
        if (n == 0) return 0;
        int[][] dp = new int[n][2];
        dp[0][0] = -prices[0]; // hold -- profit
        dp[0][1] = 0; // not hold -- sold or never bought
        for (int i = 1; i < n; i++) {
            dp[i][0] = Math.max(dp[i - 1][0], -prices[i]); // hold the min price
            dp[i][1] = Math.max(dp[i - 1][1], dp[i - 1][0] + prices[i]); // Either keep "not holding" (carry over previous), or sell today (add today's price to previous holding).
        }
        return dp[n - 1][1];
    }




    /**
     * @TimeComplexity O(n^2)
     * @SpaceComplexity O(1)
     */
    public static int maxProfitUsingBruteForce(int[] prices) {
        int n = prices.length;
        int maxProfit = 0;
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                maxProfit = Math.max(maxProfit, prices[j] - prices[i]);
            }
        }
        return maxProfit;
    }











    /**
     * WORKING BUT ALL TEST CASES ARE NOT PASSING
        THOUGHTS:
        ---------
        1) Need max diff at any point of day
        2) So, calculate at each i --> brute force n^2 => 10^10 which is not good
        3) or maintain the currMax
        3) Or use dp[] to maintain the max num
     */
    public int maxProfitMyApproach(int[] prices) {

        Map<Integer, List<Integer>> map = new HashMap<>();
        for (int i=0; i<prices.length; i++) {
            List<Integer> l = map.getOrDefault(prices[i], new ArrayList<>());
            l.add(i);
            map.put(prices[i], l);
        }

        List<Integer> lst = new ArrayList<>(map.keySet()); // or get sorted keys in reverse order

        int diff = 0;
        for (int p: prices) {
            // System.out.println(map);
            System.out.println(lst);

            boolean isPMax = p == lst.get(lst.size()-1);
            map.get(p).remove(0);
            if (map.get(p).size() == 0 ) {
                map.remove(p);
                lst=new ArrayList<>(map.keySet());
            }

            if (isPMax) continue;

            diff = Math.max(diff, lst.get(lst.size()-1)-p);// lst.get(n-1) is the max num


            System.out.println("p: " + p + ", diff: "+ diff + ", max: " + lst.get(lst.size()-1) + "\n");
        }

        return diff;
    }
}
