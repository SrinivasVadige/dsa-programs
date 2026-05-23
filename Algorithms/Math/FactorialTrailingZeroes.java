package Algorithms.Math;

import java.math.BigInteger;

/**
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 19 May 2026
 * @link 172. Factorial Trailing Zeroes <a href="https://leetcode.com/problems/factorial-trailing-zeroes/"> LeetCode link</a>
 * @topics Math
 * @companies Google(2), Amazon(2), Bloomberg(9), Microsoft(5), Meta(2)
 */
public class FactorialTrailingZeroes {
    public static void main(String[] args) {
        int n = 30;
        System.out.printf("trailingZeroes Using ComputeTheFactorial NotWorking: %s\n", trailingZeroesUsingComputeTheFactorialTLE(n));
        System.out.printf("trailingZeroes Using ComputeTheFactorial TLE: %s\n", trailingZeroesUsingComputeTheFactorialTLE(n));
        System.out.printf("trailingZeroes Using FactorsOfTwosAndFives InAllNFactorialNumbers: %s\n", trailingZeroesUsingFactorsOfTwosAndFivesInAllNFactorialNumbers(n));
        System.out.printf("trailingZeroes Using FactorsOfFives InAllNFactorialNumbers: %s\n", trailingZeroesUsingFactorsOfFivesInAllNFactorialNumbers(n));
        System.out.printf("trailingZeroes Using FactorsOfFives InAllMultiplesOfFive 1: %s\n", trailingZeroesUsingFactorsOfFivesInAllMultiplesOfFive1(n));
        System.out.printf("trailingZeroes Using FactorsOfFives InAllMultiplesOfFive 2: %s\n", trailingZeroesUsingFactorsOfFivesInAllMultiplesOfFive2(n));
        System.out.printf("trailingZeroes Using FactorsOfFiveEffectively 1: %s\n", trailingZeroesUsingFactorsOfFiveEffectively1(n));
        System.out.printf("trailingZeroes Using FactorsOfFiveEffectively 2: %s\n", trailingZeroesUsingFactorsOfFiveEffectively2(n));
    }

    /**

        The factorial values will overflow the int, long, double range - never compute the n! value directly

     * @TimeComplexity O(n)
     * @SpaceComplexity O(1)
    */
    public static int trailingZeroesUsingComputeTheFactorialNotWorking(int n) {
        long num = 1;
        while(n != 0) {
            num *= n--;
        }

        int zeros = 0;
        while (num % 10 == 0) {
            zeros++;
            num /= 10;
        }

        return zeros;
    }





    /**
     * @TimeComplexity ~O(n²) =
     * @SpaceComplexity O(n log5(n))
     */
    public static int trailingZeroesUsingComputeTheFactorialTLE(int n) {
        BigInteger nFactorial = BigInteger.ONE;
        for (int i = 2; i <= n; i++) {
            nFactorial = nFactorial.multiply(BigInteger.valueOf(i));
        }

        int zeroCount = 0;
        while (nFactorial.mod(BigInteger.TEN).equals(BigInteger.ZERO)) {
            zeroCount++;
            nFactorial = nFactorial.divide(BigInteger.TEN);
        }

        return zeroCount;
    }






    /**
        We can see that to get "0" at the end - 10 -> we need 2 * 5
        So, just count the number of 2s and 5s in the n!
        or
        count the numbers of 2s and 5s in all n! numbers i.e 1 * 2 * 3 * ... * n

     * @TimeComplexity O(n(log2(n)+log5(n)))=O(nlog5(n))
     * @SpaceComplexity O(1)
     */
    public static int trailingZeroesUsingFactorsOfTwosAndFivesInAllNFactorialNumbers(int n) {
        int twos = 0, fives = 0;
        while(n != 0) {
            int num = n;
            while (num % 2 == 0) {
                twos++;
                num /= 2;
            }

            num = n;
            while (num % 5 == 0) {
                fives++;
                num /= 5;
            }

            n--;
        }


        return Math.min(twos, fives);
    }



    /**
        Same like above {@link #trailingZeroesUsingFactorsOfTwosAndFivesInAllNFactorialNumbers}
        And using the above approach we can see that 2s count is always greater than 5s count
        and we are just returning the 5s count indirectly
        So, just count 5s in n! or in all n! numbers i.e 1 * 2 * 3 * ... * n

     * @TimeComplexity O(nlog5(n))
     * @SpaceComplexity O(1)
     */
    public static int trailingZeroesUsingFactorsOfFivesInAllNFactorialNumbers(int n) {
        int fives = 0;
        while(n != 0) {
            int num = n;
            while (num % 5 == 0) {
                fives++;
                num /= 5;
            }

            n--;
        }

        return fives;
    }




    /**

        1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20 21 22 23 24 25 26 27 28 29 30....

        here we can see that 5, 10, 15, 20, 25, 30.... are the numbers that have 5 as a factor

        just count 5s only in the numbers that are 5s multiple till N ---> 5, 10, 15, 20, 25, 30.... n

     * @TimeComplexity O(nlog5(n))
     * @SpaceComplexity O(1)
     */
    public static int trailingZeroesUsingFactorsOfFivesInAllMultiplesOfFive1(int n) {
        int zeroCount = 0;
        for (int i = 5; i <= n; i += 5) {
            int currentFactor = i;
            while (currentFactor % 5 == 0) {
                zeroCount++;
                currentFactor /= 5;
            }
        }
        return zeroCount;
    }




    /**
        Same as above {@link #trailingZeroesUsingFactorsOfFivesInAllMultiplesOfFive1}

     * @TimeComplexity O(nlog5(n))
     * @SpaceComplexity O(1)
     */
    public static int trailingZeroesUsingFactorsOfFivesInAllMultiplesOfFive2(int n) {
        int zeroCount = 0;
        for (int i = 5; i <= n; i += 5) {
            int powerOf5 = 5;
            while (i % powerOf5 == 0) {
                zeroCount++;
                powerOf5 *= 5;
            }
        }
        return zeroCount;
    }




    /**

     Trailing Zero count Formula = n/5 + n/25 + n/125 + ...
                                    l to r direction --->


     this formula is correct, cause

     1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20 21 22 23 24 25 26 27 28 29 30....
             -       | -              -              -              -              -
             1       | 2              3              4              5              6   = number of 5s till (5^i + 4)
                     |                                              |
                till 9 we only have 1 5s i.e only 1 "0" at the end  |
                                                                    |
                                                                    |
                                                                    |
                                  till here the number of 5s is 1 and here number of 5s in 25 is 2

     so, in 125 we have 3 5s, 5^i will have i number of 5s

     till
     n / 5^k = 1
     n = 5^k
     k = log5(n)

     * @TimeComplexity O(log5(n))
     * @SpaceComplexity O(1)
     */
    public static int trailingZeroesUsingFactorsOfFiveEffectively1(int n) {
        int count = 0;

        for(int i=5; i<=n; i*=5) {
            count += n/i; // n/5^k, where k starts from 1
        }

        return count;
    }



    /**

     Same as above {@link #trailingZeroesUsingFactorsOfFiveEffectively1}


     Trailing Zero count Formula = n/5 + n/25 + n/125 + ...
                                    <--- r to l direction

     * @TimeComplexity O(log5(n))
     * @SpaceComplexity O(1)
     */
    public static int trailingZeroesUsingFactorsOfFiveEffectively2(int n) {
        int count = 0;

        while (n > 0) {
            n /= 5;
            count += n;
        }

        return count;
    }
}
