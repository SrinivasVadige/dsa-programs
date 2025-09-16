package Algorithms.Hashing;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 16 Sept 2025
 * @link 202. Happy Number <a href="https://leetcode.com/problems/happy-number/">Leetcode link</a>
 * @topics Hash Table, Math, Two Pointers
 * Number of iterations ≤ number of unique states (≤ 810).
 * 2 → 4 → 16 → 37 → 58 → 89 → 145 → 42 → 20 → 4 ... this is how the loop continues
 */
public class HappyNumber {
    public static void main(String[] args) {
        int n = 19;
        System.out.println("isHappy using HashSet => " + isHappyUsingHashSet(n));
        System.out.println("isHappy using Recurrence Detection => " + isHappyUsingRecurrenceDetection(n));
        System.out.println("isHappy using Two Pointers Cycle Detection => " + isHappyUsingTwoPointersCycleDetection(n));
        System.out.println("isHappy using Count => " + isHappyUsingCount(n));
    }


    /**
     * @TimeComplexity O(log n)
     * @SpaceComplexity O(log n)
     */
    public static boolean isHappyUsingHashSet(int n) {
        Set<Integer> seen = new HashSet<>();
        while (seen.add(n)) {
            int sum = 0; // or sum(n);
            while (n>0) {
                int lastDigit = n%10;
                n /= 10;
                sum += lastDigit*lastDigit;
                /*
                // or
                int tens = (int) Math.pow(10, (int) Math.log10(n));
                int firstDigit = n / tens;
                sum += firstDigit*firstDigit;
                n = n - firstDigit*tens;
                */
            }

            if(sum == 1) {
                return true;
            }
            n = sum;
        }

        return false;
    }


    /**
     * @TimeComplexity O(log n)
     * @SpaceComplexity O(1)
     */
    public static boolean isHappyUsingRecurrenceDetection(int givenNum) { // by comparing against the original number
        int n = givenNum;
        if(n==1 || n==7) {
            return true;
        }
        while(n>9) {
           int sum = sum(n);
            if(sum ==1 || sum==7) {
                return true;
            } else if(sum==givenNum) {
                return false;
            }
            n=sum;
        }
        return false;
    }

    public static int sum(int n) {
        int sum=0;
        while(n>0) {
            int rem=n%10; // remainder or lastDigit
            sum +=rem*rem;
            n=n/10;
        }
        return sum;
    }






    /**
     * @TimeComplexity O(log n)
     * @SpaceComplexity O(1)
     */
    public static boolean isHappyUsingTwoPointersCycleDetection(int n) {

        int slow = n;
        int fast = sum(n);

        while (slow != fast) {
            fast = sum(sum(fast));
            slow = sum(slow);
        }

        return fast == 1;

        /*
        // or

        int slow = sum(n);
        int fast = sum(sum(n));

        while (slow != fast) {
            if (fast == 1) return true;
            slow = sum(slow);
            fast = sum(sum(fast));
        }

        return slow == 1;


        */
    }






    /**
     * @TimeComplexity O(log n)
     * @SpaceComplexity O(1) -- ⚠️ But correctness is not guaranteed — “1000” is just an arbitrary bound.
     */
    public static boolean isHappyUsingCount(int n) {
        int count = 0;

        while (n != 1) {
            int sum = sum(n);
            if (count++ > 1000) return false;
            n = sum;
        }

        return true;
    }
}
