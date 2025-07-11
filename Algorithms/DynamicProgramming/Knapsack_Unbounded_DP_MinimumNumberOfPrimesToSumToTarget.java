package Algorithms.DynamicProgramming;

import java.util.*;

/**
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 09 July 2025
 * @link 3610. Minimum Number of Primes to Sum to Target <a href="https://leetcode.com/problems/minimum-number-of-primes-to-sum-to-target/">Leetcode link</a>
 * @link same as "Unbounded Knapsack Coin Change problem" - {@link Algorithms.DynamicProgramming.Knapsack_Unbounded_DP_CoinChange}
 * @description same like CoinChange problem - with duplication
 * @topics Array, Dynamic Programming, Math, Backtracking, Bitmask, Number Theory
 * @companies amazon
 */
public class Knapsack_Unbounded_DP_MinimumNumberOfPrimesToSumToTarget {
    public static void main(String[] args) {
        int n = 5;
        int m = 15;
        System.out.println("minNumberOfPrimesUsingBottomUpTabulationDpOptimizedSpace => " + minNumberOfPrimesUsingBottomUpTabulationDpOptimizedSpace(n, m));
    }

    /**
     * @TimeComplexity O(mn)
     */
    public static int minNumberOfPrimesUsingBottomUpTabulationDpOptimizedSpace(int n, int m) {
        int[] primes = new int[m];
        getPrimes(m, primes);

        int[] dp = new int[n+1];
        Arrays.fill(dp, n+1);
        dp[0]=0;

        for(int prime: primes) {
            for(int sum=prime; sum<=n; sum++) {
                dp[sum] = Math.min(dp[sum], 1+dp[sum-prime]);
            }
        }

        return dp[n]==n+1? -1 : dp[n];
    }
    private static void getPrimes(int m, int[] primes) {
        int i = 0;
        for(int num=2; i<m; num++) {
            if(isPrime(num)) {
                primes[i++]=num;
            }
        }
    }

    /**
     * @see BasicPrograms.PrimeNumber methods for better understanding of optimalized trial division
     */
    private static boolean isPrime(int num) {
        if (num < 2) return false;
        if (num == 2) return true;
        if (num % 2 == 0) return false; // --> This will validate all even numbers of "num". All even numbers (except 2) are not prime
        double end = Math.sqrt(num);
        for (int i = 3; i<=end; i += 2) { // i*i<=num means i<=Math.sqrt(num) --- and check if num is divisible by odd numbers of i
            if (num % i == 0) return false;
        }
        return true;
    }












    /**
     *




        using m=5, first find the set of prime numbers
        [2, 3, 5, 7, 11]

        now reverse it
        [11, 7, 5, 3, 2]
        target sum = 15


        [11, 7, 5, 3, 2]
          i              ---> sum - num => 15-11 => 4 ---> while loop till newSum <= num
             i           ---> sum - num => 4-7 = -ve skip
                 i       ---> sum - num => 4-5 = -ve skip
                   i     ---> sum - num => 4-3 = 1
                      i  ---> sum - num => 1-2 = -ve

        --> this approach won't work

                                                                                                                            [11, 7, 5, 3, 2]
                                                                                   ______________________________________________|______________________________________________
                                                                                   |                       |                        |                       |                  |
                                                                                 [11]                     [7]                      [5]                     [3]                [2]
                                                ___________________________________|___________________________________
                                                |                |              |               |                 |
                                            [11, 11]          [11,7]          [11,5]        [11,3]             [11,2]
                                               ❌               ❌             ❌             |
                                                     __________________________________________|_______________________________________
                                                    |                |                       |                    |                   |
                                              [11,3,11]             [11,3,7]             [11,3,5]             [11,3,3]             [11,3,2]
                                                  ❌                   ❌                  ❌                   ❌                   ❌



     or



                                                                                                                    [11, 7, 5, 3, 2]
                                                                           ______________________________________________|______________________________________________
                                                                           |                                             |                                              |
                                                                           []                                            []                                            [11]
                                            _______________________________|____________________________
                                            |                              |                            |
                                           []                              []                          [7]
                                                                         _______________________________|_____________________________
                                                                         |                              |                            |
                                                                        [7]                            [7,7]                        [7,5]
                                                                         |                               ❌                          ❌
                                          _______________________________|_____________________________
                                          |                              |                            |
                                          [7]                           [7,7]                        [7,3]
                                                                          ❌                          |
                                                                       _______________________________|_____________________________
                                                                       |                              |                            |
                                                                     [7,3]                         [7,3,7]                     [7,3,2]






     */
    public static int minimumPrimesUsingBacktracking(int m, int n) {
        List<Integer> primes = new ArrayList<>();
        getPrimes(m, primes);

        Map<String, Integer> map = new HashMap<>();
        backtrack(primes, 0, n, new HashMap<>(), 0, map, new HashSet<>());
        // System.out.println(map);

        return map.values().stream().min(Comparator.naturalOrder()).orElse(-1);

    }
    private static void backtrack(List<Integer> primes, int sum, int target, Map<Integer, Integer> counter, int numCount, Map<String, Integer> map, Set<String> seen) {
        int n = primes.size();
        // System.out.println(counter);
        if(sum == target) {
            map.put(counter.toString(), numCount);
        } else if(sum > target || seen.contains(counter.toString()) || !map.isEmpty()) {
            return;
        }

        for(int idx = n-1; idx>=0; idx--) {
            counter.merge(primes.get(idx), 1, Integer::sum);

            backtrack(primes, sum+primes.get(idx), target, counter, numCount+1, map, seen);
            seen.add(counter.toString());

            counter.merge(primes.get(idx), -1, Integer::sum);
            if(counter.get(primes.get(idx)) == 0){
                counter.remove(primes.get(idx));
            }
        }
    }

    private static void getPrimes(int m, List<Integer> primes) {
        int start = 2;
        for(int num=start; primes.size() < m; num++) {
            if(isPrime(num)) {
                primes.add(num);
            }
        }
    }
}
