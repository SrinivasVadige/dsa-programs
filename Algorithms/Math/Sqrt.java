package Algorithms.Math;

/**
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 31 May 2026
 * @link 69. Sqrt(x) <a href="https://leetcode.com/problems/sqrtx/">LeetCode Link</a>
 * @topics Math, BinarySearch
 * @companies Google(5), Bloomberg(4), Microsoft(2), Amazon(2), Meta(5), Goldman Sachs(2), TikTok(16), Zoho(10), Grammarly(8), LinkedIn(7), Citadel(5), TCS(4), Apple(3), Oracle(3), Uber(3), Infosys(2)
 */
public class Sqrt {
    public static void main(String[] args) {
        int x = 4;
        System.out.printf("mySqrt using in-built method = %s\n", mySqrtUsingInbuiltMethod(x));
        System.out.printf("mySqrt using pocket calculator algorithm = %s\n", mySqrtUsingPocketCalculatorAlgorithm(x));
        System.out.printf("mySqrt using brute force = %s\n", mySqrtUsingBruteForce(x));
        System.out.printf("mySqrt using binary search 1 = %s\n", mySqrtUsingBinarySearch1(x));
        System.out.printf("mySqrt using binary search 2 = %s\n", mySqrtUsingBinarySearch2(x));
        System.out.printf("mySqrt using binary search 3 = %s\n", mySqrtUsingBinarySearch3(x));
        System.out.printf("mySqrt using recursion bit shifts = %s\n", mySqrtUsingRecursionBitShifts(x));
        System.out.printf("mySqrt using newton's method = %s\n", mySqrtUsingNewtonsMethod(x));
    }




    /**
     * @TimeComplexity O(1)
     * @SpaceComplexity O(1)
     */
    public static int mySqrtUsingInbuiltMethod(int x) {
        return (int) Math.sqrt(x);
    }





    /**
     * @TimeComplexity O(1)
     * @SpaceComplexity O(1)
     */
    public static int mySqrtUsingPocketCalculatorAlgorithm(int x) {
        if (x < 2) return x;

        int left = (int) Math.pow(Math.E, 0.5 * Math.log(x));
        int right = left + 1;
        return (long) right * right > x ? left : right;
    }







    /**
     * @TimeComplexity O(n) => O(sqrt(x))
     * @SpaceComplexity O(1)
     */
    public static int mySqrtUsingBruteForce(int x) {
        int l = 0, r = x;

        int res = l;
        while(l <= r) {
            if ((l > 0 && l > Integer.MAX_VALUE / l) || Integer.compare(l*l, x) == 1) break;
            res = l++;
        }
        return res;
    }






    /**
     * @TimeComplexity O(log n) => O(log sqrt(x))
     * @SpaceComplexity O(1)
     */
    public static int mySqrtUsingBinarySearch1(int x) {
        int l = 0, r = x, res = l;

        while(l <= r) {
            int mid = l + (r-l)/2;
            if ((mid > 0 && mid > x / mid) || mid*mid > x) r = mid-1;
            else if (mid*mid < x) {
                res = mid;
                l = mid+1;
            }
            else return mid; // mid*mid == x
        }
        return res; // or r; ---> cause in the last iteration (l<=r) we increment l++ then l > r break and now return r cause r never incremented in (l==r) case
    }







    /**
     * @TimeComplexity O(log n) => O(log sqrt(x))
     * @SpaceComplexity O(1)
     */
    public static int mySqrtUsingBinarySearch2(int x) {
        if (x < 2) return x;

        long num;
        int mid, l = 2, r = x;
        while (l <= r) {
            mid = l + (r - l) / 2;
            num = (long) mid * mid;

            if (num > x) r = mid - 1;
            else if (num < x) l = mid + 1;
            else return mid;
        }

        return r; // r; ---> cause in the last iteration (l<=r) we increment l++ then l > r break and now return r cause r never incremented in (l==r) case
    }







    /**
     * @TimeComplexity O(log n) => O(log sqrt(x))
     * @SpaceComplexity O(1)
     */
    public static int mySqrtUsingBinarySearch3(int x) {
        if (x < 2) return x;

        int m, l = 2, r = x;
        while (l <= r) {
            m = l + (r - l) / 2;

            if ((long) m * m <= x) l = m + 1;
            else r = m - 1;
        }

        return r;
    }







    /**
     * @TimeComplexity O(log n) => O(log sqrt(x))
     * @SpaceComplexity O(log n)
     */
    public static int mySqrtUsingRecursionBitShifts(int x) {
        if (x < 2) return x;

        int left = mySqrtUsingRecursionBitShifts(x >> 2) << 1;
        int right = left + 1;
        return (long) right * right > x ? left : right;
    }







    /**
     * @TimeComplexity O(log n) => O(log sqrt(x))
     * @SpaceComplexity O(1)
     */
    public static int mySqrtUsingNewtonsMethod(int x) {
        if (x < 2) return x;

        double x0 = x;
        double x1 = (x0 + x / x0) / 2.0;
        while (Math.abs(x0 - x1) >= 1) { // diff >= 1
            x0 = x1;
            x1 = (x0 + x / x0) / 2.0;
        }

        return (int) x1;
    }
}
