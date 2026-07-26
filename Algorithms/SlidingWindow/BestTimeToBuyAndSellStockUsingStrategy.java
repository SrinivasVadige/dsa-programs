package Algorithms.SlidingWindow;

/**
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 26 July 2026
 * @link 3652. Best Time to Buy and Sell Stock using Strategy <a href="https://leetcode.com/problems/best-time-to-buy-and-sell-stock-using-strategy/">LeetCode link</a>
 * @topics Senior, Array, Sliding Window, Prefix Sum, Weekly Contest 463
 * @companies Meta(6), Amazon(4), Visa(3), Microsoft(2)
 * @see Algorithms.DynamicProgramming.BestTimeToBuyAndSellStock
 */
public class BestTimeToBuyAndSellStockUsingStrategy {
    public static void main(String[] args) {
        int[] prices = {4, 2, 8, 7};
        int[] strategy = {-1, 0, 1, 1};
        int k = 2;

        System.out.println("maxProfit Using BruteForce: " + maxProfitUsingBruteForce(prices, strategy, k));
        System.out.println("maxProfit Using SlidingWindow1: " + maxProfitUsingSlidingWindow1(prices, strategy, k));
        System.out.println("maxProfit Using SlidingWindow2: " + maxProfitUsingSlidingWindow2(prices, strategy, k));

        System.out.println("maxProfit Using SlidingWindowWithPrefixSum1: " + maxProfitUsingSlidingWindowWithPrefixSum1(prices, strategy, k));
        System.out.println("maxProfit Using SlidingWindowWithPrefixSum2: " + maxProfitUsingSlidingWindowWithPrefixSum2(prices, strategy, k));
    }

    /**
                 0  1  2  3
    prices =   [ 4, 2, 8, 7]
    strategy = [-1, 0, 1, 1]
    k = 2
    n = 4


    Math.max (og, 0 to k-1, 1 to k, 2 to k+1) till i <= n-k

     * @TimeComplexity O(n^2)
     * @SpaceComplexity O(1)
     */
    public static long maxProfitUsingBruteForce(int[] prices, int[] strategy, int k) {
        int max = 0;
        int n = prices.length;

        for (int i=0; i<n; i++) {
            max += prices[i] * strategy[i];
        }


        for (int wStartI=0, wEndI = wStartI+k-1; wStartI <= n-k; wStartI++, wEndI++) { // k window
            int tempMax = 0;
            for (int startToW=0; startToW<wStartI; startToW++) { // 0 to windowStart-1
                tempMax += prices[startToW] * strategy[startToW];
            }
            for (int i = wStartI; i < wStartI + k/2; i++) { // 0s window
                tempMax += prices[i] * 0;
            }
            for (int i = wStartI + k/2; i <= wEndI; i++) {  // 1s window
                tempMax += prices[i] * 1;
            }
            for (int wEndToEnd = wEndI+1; wEndToEnd < n; wEndToEnd++) { // windowEnd+1 to n-1
                tempMax += prices[wEndToEnd] * strategy[wEndToEnd];
            }
            max = Math.max(max, tempMax);
        }

        return max;
    }



    /**
                 0  1  2  3
    prices =   [ 4, 2, 8, 7]
    strategy = [-1, 0, 1, 1]
    k = 2
    n = 4


    Math.max (og, 0 to k-1, 1 to k, 2 to k+1) till i <= n-k


            [ 4, 2, 8, 7]
              |________|    => og

            [ 4, 2, 8, 7]
              |__|          => windowStart = 0, windowEnd = windowStart+k-1 = 1

            [ 4, 2, 8, 7]
                 |__|       => windowStart = 1, windowEnd = windowStart+k-1 = 2

            [ 4, 2, 8, 7]
                    |__|    => windowStart = 2, windowEnd = windowStart+k-1 = 3


Window 0: [ 0,  0,  1,  1 ]  (indices 0..3)
Window 1:     [ 0,  0,  1,  1 ]  (indices 1..4)
                ^   ^
                |   |-- Index 2 was '1' in Window 0, but is now '0' in Window 1!
                |------ Index 0 leaves the window (restores original strategy).

     * @TimeComplexity O(n)
     * @SpaceComplexity O(1)
     */
    public static long maxProfitUsingSlidingWindow1(int[] prices, int[] strategy, int k) {
        long max = 0;
        int n = prices.length;

        for (int i=0; i<n; i++) {
            max += prices[i] * strategy[i];
        }


        long tempMax = max;
        for (int wStartI=0, wEndI = wStartI+k-1; wStartI <= n-k; wStartI++, wEndI++) { // k window

            if (wStartI == 0) {
                for (int i = wStartI; i < wStartI + k/2; i++) { // 0s window
                    tempMax += prices[i] * 0 - prices[i] * strategy[i];
                }
                for (int i = wStartI + k/2; i <= wEndI; i++) {  // 1s window
                    tempMax += prices[i] * 1 - prices[i] * strategy[i];
                }

                max = Math.max(max, tempMax);
                continue;
            }

            int i = wStartI-1;
            tempMax += prices[i] * strategy[i] - prices[i] * 0;

            i = wStartI - 1 + k/2;
            tempMax += prices[i] * 0 - prices[i] * 1;

            i = wEndI;
            tempMax += prices[i] * 1 - prices[i] * strategy[i];

            max = Math.max(max, tempMax);
        }

        return max;
    }



    /**
     * @TimeComplexity O(n)
     * @SpaceComplexity O(1)
     */
    public static long maxProfitUsingSlidingWindow2(int[] prices, int[] strategy, int k) {
        long total = 0;
        long maxS = 0;
        long s = 0;
        int n = prices.length;

        for (int i = 0; i < n; i++) {
            int p = prices[i];
            int st = strategy[i];

            total += p * st; // profit without modification
            s += (long) p * (1 - st); // 1. (p, st) enters the right half, and its strategy changes from st to 1.

            if (i < k - 1) { // the first window has not been formed yet.
                // In the next iteration, the element at index (i - k / 2 + 1) moves from the right half to the left half,
                // and its strategy changes from 1 to 0.
                if (i >= k / 2 - 1) {
                    s -= prices[i - k / 2 + 1];
                }
                continue;
            }

            // 2. Update (extra profit with modification).
            maxS = Math.max(maxS, s);

            // 3. Prepare for the next window.
            // - The element at index (i - k / 2 + 1) moves from the right half to the left half,
            //   and its strategy changes from 1 to 0.
            // - The element at index (i - k + 1) leaves the window from the left half,
            //   and its strategy is restored from 0 to strategy[i - k + 1].
            s -= prices[i - k / 2 + 1] - (long) prices[i - k + 1] * strategy[i - k + 1];
        }

        return total + maxS;
    }





    /**

                 0  1  2  3
    prices =   [ 4, 2, 8, 7]
    strategy = [-1, 0, 1, 1]
    k = 2
    n = 4


        Indices:  [ 0 ... i-k ]  |  [ i-k+1 ... i-k/2 ]  |  [ i-k/2+1 ... i ]  |  [ i+1 ... n-1 ]
        Zone:     LEFT           |  WINDOW FIRST HALF    |  WINDOW SECOND HALF |  RIGHT
        Strategy: Unchanged      |  Forced to 0          |  Forced to 1        |  Unchanged



    In profitSum[i+1] == sum till prices[i]

    so, in profitSum & priceSum -> i+1 will i in prices indexes


     * @TimeComplexity O(n)
     * @SpaceComplexity O(n)
     */
    public static long maxProfitUsingSlidingWindowWithPrefixSum1(int[] prices, int[] strategy, int k) {
        int n = prices.length;
        long[] profitSum = new long[n + 1]; // ogProfitPrefixSum - prefix sum with original strategy profit
        long[] priceSum = new long[n + 1];  // pricePrefixSum - without strategy (or) modPrefixSum - prefix sum if strategy was forced to 1

        for (int i = 0; i < n; i++) {
            profitSum[i + 1] = profitSum[i] + prices[i] * strategy[i];
            priceSum[i + 1] =  priceSum[i]  + prices[i];                // modPrefixSum[i] + prices[i] * 1;
        }

        long max = profitSum[n];
        for (int i = k-1; i < n; i++) {                                     // start from first_window_end
            long leftProfit = profitSum[i - k + 1];                         // left of windowStart
            long rightProfit = profitSum[n] - profitSum[i + 1];             // right of windowEnd
            long changeProfit = priceSum[i + 1] - priceSum[i - k/2 + 1];    // remove 1s sum from the window as (0s is left half & 1s is right half)

            max = Math.max(max, leftProfit + changeProfit + rightProfit);
        }
        return max;
    }


    /**
     * @TimeComplexity O(n)
     * @SpaceComplexity O(n)
     */
    public static long maxProfitUsingSlidingWindowWithPrefixSum2(int[] prices, int[] strategy, int k) {
        int n = prices.length;
        long[] ogPrefixSum = new long[n + 1]; // ogPrefixSum[i] stores prefix sum of original strategy profit
        long[] modPrefixSum = new long[n + 1]; // modPrefixSum[i] stores prefix sum if strategy was forced to 1

        for (int i = 0; i < n; i++) {
            ogPrefixSum[i + 1]  = ogPrefixSum[i]  + prices[i] * strategy[i];
            modPrefixSum[i + 1] = modPrefixSum[i] + prices[i] * 1;
        }

        long ogProfit = ogPrefixSum[n];
        long maxProfit = ogProfit;

        int half = k / 2;
        for (int i = 0; i <= n - k; i++) {
            // Range 1: [i, i + half - 1] forced to 0 -> profit from this part is 0
            // Range 2: [i + half, i + k - 1] forced to 1 -> use modPrefixSum

            long ogWindowProfit = ogPrefixSum[i + k] - ogPrefixSum[i];
            long newWindowProfit = modPrefixSum[i + k] - modPrefixSum[i + half]; // 0-half contributes 0

            long currentProfit = ogProfit - ogWindowProfit + newWindowProfit;
            maxProfit = Math.max(maxProfit, currentProfit);
        }

        return maxProfit;
    }
}
