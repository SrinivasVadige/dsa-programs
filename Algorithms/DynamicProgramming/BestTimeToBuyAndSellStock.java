package Algorithms.DynamicProgramming;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 27 Feb 2025
 * @link 121. Best Time to Buy and Sell Stock https://leetcode.com/problems/best-time-to-buy-and-sell-stock/
 * @topics Two Pointers, Dynamic Programming, Array
 */
public class BestTimeToBuyAndSellStock {
    public static void main(String[] args) {
        int[] prices = {7,1,5,3,6,4};
        System.out.println("maxProfit using l,r pointers => " + maxProfitUsingTwoPointers(prices));
        System.out.println("maxProfit using two pointers (minPrice, i) => " + maxProfitUsingTwoPointers2(prices));
        System.out.println("maxProfit using two pointers (effectiveBuyPrice, maxProfit) => " + maxProfitUsingTwoPointersEffectiveBuyPrice(prices));
        System.out.println("maxProfit using bottom up tabulation dp => " + maxProfitUsingBottomUpTabulationDp(prices));
        System.out.println("maxProfit using brute force => " + maxProfitUsingBruteForce(prices));
    }

    /**
     * @TimeComplexity O(n)
     * @SpaceComplexity O(1)
     *
     * Given [7,1,5,3,6,4]
     * Now draw the graph based i on x-axis and item on y-axis
     *
     *  prices[i] item
     *      ▲
     *      |
     *      |
     *      |
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
     *                0      1      2       3       4       5          -----► index i
     *                l      r
     *                       l      r
     *                       l              r
     *                       l                      r
     *                       l                              r
     *
     * here we use if-else but not in {@link #maxProfitUsingTwoPointers2} cause it's optional, as minPrice(7,1) is 1
     * and maxProfit(prevMaxProfit, 1-1) is always prev prevMaxProfit -- so no need to calculate again
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
     *
     * And if you check {@link #maxProfitUsingBottomUpTabulationDp(int[])}, both uses dp, but here it is "Bottom-Up NoMemory DP".
     * That's why this is two pointers approach
     */
    public static int maxProfitUsingTwoPointers2(int[] prices) {
        int maxProfit = 0;
        int minPrice = Integer.MAX_VALUE;
        for (int i = 0; i < prices.length; i++) {
            minPrice = Math.min(minPrice, prices[i]); // the minPrice up to i
            maxProfit = Math.max(maxProfit, prices[i] - minPrice);
        }
        return maxProfit;
    }




    /**
        minNum=p[0]=7 or Integer.MAX_VALUE
        currSum=0

        [7,1,5,3,6,4]
           i
        minNum=Math.min(minNum,p[i])=(7,1)=1
        curSum=max(currSum,p[i]-minNum)= (0,1-1)

        [7,1,5,3,6,4]
             i
        minNum=Math.min(minNum,p[i])=(1,5)=1
        curSum=max(currSum,p[i]-minNum)=(0,5-1)=4

        [7,1,5,3,6,4]
               i
        minNum=Math.min(minNum,p[i])=(1,3)=1
        curSum=max(currSum,p[i]-minNum)=(4,3-1)=4

        [7,1,5,3,6,4]
                 i
        minNum=Math.min(minNum,p[i])=(1,6)=1
        curSum=max(currSum,p[i]-minNum)=(4,6-1)=5

        [7,1,5,3,6,4]
                   i
        minNum=Math.min(minNum,p[i])=(1,4)=1
        curSum=max(currSum,p[i]-minNum)=(5,4-1)=5
     */
    public int maxProfitUsingTwoPointerMyApproach(int[] prices) {
        int minNum = Integer.MAX_VALUE; // pointer 1
        int currSum = 0;
        for(int price: prices) { // pointer 2
            minNum = Math.min(minNum, price);
            currSum = Math.max(currSum, price-minNum);
        }
        return currSum;
    }





    // using effective buy price approach
    /**
     * @TimeComplexity O(n)
     * @SpaceComplexity O(1)
     *
     * This approach is same as {@link #maxProfitUsingTwoPointers2} and {@link #maxProfitUsingTwoPointerMyApproach(int[])}
     * but instead of minPrice, it uses effectiveBuyPrice
     *
     * @see {@link Algorithms.DynamicProgramming.BestTimeToBuyAndSellStockII#maxProfitUsingTwoPointersEffectiveBuyPrice} for easier understanding
     */
    public static int maxProfitUsingTwoPointersEffectiveBuyPrice(int[] prices) {
        int maxProfit = 0, effectiveBuyPrice = Integer.MAX_VALUE;
        for (int i = 0; i < prices.length; i++) {
            effectiveBuyPrice = Math.min(effectiveBuyPrice, prices[i]); // the effective buy price up to i
            maxProfit = Math.max(maxProfit, prices[i] - effectiveBuyPrice);
        }
        return maxProfit;
    }





    /**
     * @TimeComplexity O(n)
     * @SpaceComplexity O(n)
     *
     * Same as {@link #maxProfitUsingTwoPointers(int[])} but use two arrays minBuys & maxSells instead of l & r
     */
    public static int maxProfitUsingTwoPointers3(int[] prices) { // [7, 1, 5, 3, 6, 4]
        int n = prices.length;
        int[] minBuys = new int[n], maxSells = new int[n];
        minBuys[0] = prices[0];
        maxSells[n-1] = prices[n-1];

        // left to right for minBuys till each index
        for(int i=1; i<n; i++) minBuys[i] = Math.min(minBuys[i-1], prices[i]); // [7, 1, 1, 1, 1, 1]

        // right to left for maxSells till each index
        for(int i=n-2; i>=0; i--) maxSells[i] = Math.max(maxSells[i+1], prices[i]); // [7, 6, 6, 6, 6, 4]

        // now calculate the max profit
        int maxProfit = 0;
        for(int i=0; i<n; i++) maxProfit = Math.max(maxProfit, maxSells[i]-minBuys[i]);
        return maxProfit;
    }





    /**
     * @TimeComplexity O(n)
     * @SpaceComplexity O(n)
     *
     * 2D dp array, dp[i][0] is val of minPrice till now, and dp[i][1] is the maxProfit till now
     *
     * i=0  [  7,      1,      5,   3,    6,    4]
     * dp = [[7][0], [][],  [][], [][], [][], [][]]
     *
     * i=1  [  7,        1,      5,    3,    6,    4]
     * dp = [[7][0],  [1][0],  [][], [][], [][], [][]]
     *        [max(7,1)][max(0, 1-7)]
     *
     * i=2  [  7,        1,         5,    3,    6,    4]
     * dp = [[7][0],  [1][0],    [1][4], [][], [][], [][]]
     *                    [max(1,5)][max(0, 5-1)]
     *
     * i=3  [  7,        1,      5,      3,      6,    4]
     * dp = [[7][0], [1][0],  [1][4],  [1][4],  [][], [][]]
     *                          [max(1,3)][max(4, 3-1)]
     *
     * i=4  [  7,        1,      5,      3,        6,     4]
     * dp = [[7][0], [1][0],  [1][4],   [1][4],  [-1][5], [][]]
     *                                     [max(1,6)][max(4, -1+6)]
     *
     * i=4  [  7,        1,         5,    3,        6,        4]
     * dp = [[7][0], [1][0],  [1][4],   [1][4],  [1][5],   [1][5]]
     *                                              [max(1,4)][max(5, 4-1)]
     *
     */
    public static int maxProfitUsingBottomUpTabulationDp(int[] prices) {
        int n = prices.length;
        if (n == 0) return 0;
        int[][] dp = new int[n][2];
        dp[0][0] = prices[0]; // minPrice till now --> initially assume that we bought at i=0 i.e prices[0]
        dp[0][1] = 0; // maxProfit till now - if we sold
        for (int i = 1; i < n; i++) {
            int prevMinPrice = dp[i - 1][0];
            int prevMaxProfit = dp[i - 1][1];
            // CURR minPrice till now
            dp[i][0] = Math.min(prevMinPrice, prices[i]);
            // CURR maxProfit till now
            dp[i][1] = Math.max(prevMaxProfit, prices[i]-prevMinPrice);
        }
        return dp[n - 1][1];
    }


    /**
     * same as {@link #maxProfitUsingBottomUpTabulationDp(int[])}
     *
     * but dp[i][0] is maxHold till now (i.e minPrice) and dp[i][1] is maxNotHold till now (i.e maxProfit)
     */
    public static int maxProfitUsingBottomUpTabulationDp2(int[] prices) {
        int n = prices.length;
        if (n == 0) return 0;
        int[][] dp = new int[n][2];
        dp[0][0] = -prices[0]; // minPrice till now - hold --> initially assume that we bought at i=0 i.e prices[0] so the amount we spent will be negative i.e -7
        dp[0][1] = 0; // maxProfit till now - if we sold or not hold or never bought
        for (int i = 1; i < n; i++) {
            int prevMaxHold = dp[i - 1][0];
            int prevMaxNotHold = dp[i - 1][1];
            // CURR maxHold
            dp[i][0] = Math.max(prevMaxHold, -prices[i]); // hold the min price -- here we use max as the hold is in -ve value
            // CURR maxNotHold
            dp[i][1] = Math.max(prevMaxNotHold, prevMaxHold + prices[i]); // Either keep "not holding" (carry over previous), or sell today (add today's price to previous holding).
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
    public int maxProfitMyApproachOld(int[] prices) {

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
