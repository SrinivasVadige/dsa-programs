package Algorithms.Math;

/**
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 01 June 2026
 * @link 50. Pow(x, n) <a href="https://leetcode.com/problems/powx-n/">LeetCode Link</a>
 * @topics Math, Recursion
 * @companies Meta(9), Amazon(4), Bloomberg(4), Google(3), Microsoft(2), LinkedIn(23), TCS(7), Goldman Sachs(4), eBay(4), Oracle(3), Qualcomm(3), Infosys(2), Adobe(2), Walmart Labs(2), EPAM Systems(2)
 */
public class Pow {
    public static void main(String[] args) {
        double x = 2;
        int n = 10;
        System.out.printf("myPow using BruteForce = %s\n", myPowUsingBruteForce(x, n));
        System.out.printf("myPow using Recursive Binary Exponentiation - NOT WORKING = %s\n", myPowUsingRecursiveBinaryExponentiationNotWorking(x, n));
        System.out.printf("myPow using Recursive Binary Exponentiation 1 = %s\n", myPowUsingRecursiveBinaryExponentiation1(x, n));
        System.out.printf("myPow using Recursive Binary Exponentiation 2 = %s\n", myPowUsingRecursiveBinaryExponentiation2(x, n));
        System.out.printf("myPow using Iterative Binary Exponentiation 1 = %s\n", myPowUsingIterativeBinaryExponentiation1(x, n));
    }


    /**
     * @TimeComplexity O(n)
     * @SpaceComplexity O(1)
     */
    public static double myPowUsingBruteForce(double x, int n) {
        double power = 1;
        int count = 0;

        while (count++ < Math.abs(n)) {
            power *= x;
        }

        if (n >= 0) return power;
        return 1/power;
    }




    /**
     * @TimeComplexity O(log n)
     * @SpaceComplexity O(log n) - for recursion stack


        5^50

        5
        10
        15
        20
        25
        ......


                         (1, 50)
                            |
                         (5, 49)
                            |
                   (10, 24) + (10, 25)
                        |         |
                     () + ()   () + ()


        we know that 5^50 = 5^25 + 5^25;
        or
        5^50 = (5^25)^2 = 5^25 + 5^25;

        pow(5, 50) = pow(5, 25) * pow(5, 25)


        failing only when n = Integer.MIN_VALUE;

        Integer.MAX_VALUE = 2147483647
        Integer.MIN_VALUE = -2147483648

        java.lang.NumberFormatException: Infinite or NaN
            at line 1008, java.base/java.math.BigDecimal.<init>
            at line 987, java.base/java.math.BigDecimal.<init>
                at __Serializer__.serialize(Unknown Source)
            at line 94, __Driver__.main

     */
    public static double myPowUsingRecursiveBinaryExponentiationNotWorking(double x, int n) {
        if (n == 0) return 1;

        int absN = Math.abs(n);
        boolean isPos = n >= 0;
        boolean isOdd = absN % 2 == 1;

        double val = myPowUsingRecursiveBinaryExponentiationNotWorking(x, absN/2);
        val *= val; // instead of doing val*val we can just pass x*x in the recursive call like func(x*x, absN/2) - as per algebra

        if (isOdd) val *= x;

        if (isPos) return val;

        return 1/val;
    }






    /**
     * @TimeComplexity O(log n)
     * @SpaceComplexity O(log n) - for recursion stack
     */
    public static double myPowUsingRecursiveBinaryExponentiation1(double x, int n) {
        long N = n;

        if (N < 0) {
            x = 1 / x;
            N = -N;
        }

        return pow(x, N);
    }

    private static double pow(double x, long n) {
        if (n == 0) return 1;

        double val = pow(x, n / 2);
        val *= val;
        if (n % 2 == 1) val *= x; // odd

        return val;
    }







    /**
     * @TimeComplexity O(log n)
     * @SpaceComplexity O(log n) - for recursion stack
     */
    public static double myPowUsingRecursiveBinaryExponentiation2(double x, int n) {
        return binaryExp(x, (long) n);
    }
    private static double binaryExp(double x, long n) {
        if (n == 0) return 1;

        if (n < 0) return 1.0 / binaryExp(x, -1 * n);

        if (n % 2 == 1) return x * binaryExp(x * x, (n - 1) / 2); // or return x * val * val; where val = binaryExp(x, n/2) ---> as per algebra
        else return binaryExp(x * x, n / 2);
    }








    /**
     * @TimeComplexity O(log n)
     * @SpaceComplexity O(1)
     */
    public static double myPowUsingIterativeBinaryExponentiation1(double x, int n) {
        long N = n;
        if (N == 0) return 1;

        if (N < 0) {
            N = -1 * N;
            x = 1.0 / x;
        }

        // Perform Binary Exponentiation.
        double result = 1;
        while (N != 0) {
            // If 'N' is odd we multiply result with 'x' and reduce 'N' by '1'.
            if (N % 2 == 1) {
                result = result * x;
                N -= 1;
            }
            // We square 'x' and reduce 'N' by half, x^N => (x^2)^(N/2).
            x = x * x;
            N = N / 2;
        }
        return result;
    }
}
