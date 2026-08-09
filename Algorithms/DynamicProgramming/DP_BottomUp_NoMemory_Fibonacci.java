package Algorithms.DynamicProgramming;

/**
 * <p> Same like Bottom-Up Tabulation DP but no need to store all the node values. Just two vars are enough
 * <p> And it can be normal loop or recursion or recursion with loop(Eg: LongestPalindromicSubstring)
 * @Approach Bottom Up - No Memory
 * @TimeComplexity O(n)
 *
 * @see Algorithms.DynamicProgramming.DP_BottomUp_Tabulation_Fibonacci
 * @see Algorithms.DynamicProgramming.LongestPalindromicSubstring
 *
* @author Srinvas Vadige, srinivas.vadige@gmail.com
* @since 06 Oct 2024
*/
public class DP_BottomUp_NoMemory_Fibonacci {
    public static void main(String[] args) {
        int n = 5;
        fibonacci(n);
    }


    // Normal for loop and note that we cannot do top to bottom in this approach
    public static int fibonacci(int n) {
        if (n <= 1) {
            return n;
        }

        int prev = 0;
        int curr = 1; // or int[] dp = new int[2]; --> dp[0] is prev and dp[1] is curr
        System.out.print("0 1 ");

        for (int i = 2; i <= n; i++) { // move prev & curr values to next
            int temp = prev + curr;
            prev = curr;
            curr = temp;

            System.out.print(temp + " ");
        }

        return curr;
    }


}
