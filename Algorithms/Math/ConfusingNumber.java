package Algorithms.Math;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 28 July 2026
 * @link 1056. Confusing Number <a href="https://leetcode.com/problems/confusing-number/">LeetCode link</a>
 * @topics Senior, Math
 * @companies Google(5), Microsoft(3)
 */
public class ConfusingNumber {
    static void main() {
        int n = 6;
        System.out.println("confusingNumber() => " + confusingNumber(n));
    }



    /**
        0 = 0
        1 = 1
        2 ❌
        3 ❌
        4 ❌
        5 ❌
        6 = 9
        7 ❌
        8 = 8
        9 = 6

     * @TimeComplexity O(L) - number of digits in n
     * @SpaceComplexity O(L) - number of digits in n - for n_copy var & reversed_n var
     */
    public static boolean confusingNumber(int n) {
        int givenN = n;
        int reversedN = 0;
        Map<Integer, Integer> invertMap = new HashMap<>() {{
            put(0, 0);
            put(1, 1);
            put(6, 9);
            put(8, 8);
            put(9, 6);
        }};

        while (n > 0) {
            int lastDigit = n % 10;
            if (!invertMap.containsKey(lastDigit)) return false;

            reversedN = reversedN*10 + invertMap.get(lastDigit);
            n = n/10;
        }
        return givenN != reversedN;
    }
}
