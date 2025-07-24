package Algorithms.DynamicProgramming;

import java.util.*;
import java.util.stream.Collectors;

/**
 * <pre>
 * Given two sequences, find the length of longest subsequence present in both of them.
 *
 * Examples:
 * LCS for input Sequences “abcdgh” and “aedfhr” is “adh” of length 3.
 *
 *
 * Instead of constructing a 2^n graph, we can use dp[][] as table with text1 as rows and text2 as columns
 * Here we have 3 choices for each cell: ↘, ←, ↑
 * text1 = "abcde" --- rows, text2 = "ace" --- columns
 *
 * If t2 char cell and t1 char cell are same then move to diagonal cell ↘ i.e ↖ value + 1 --- + 1 for current chars
 * else move to max of right cell → and down cell ↓, i.e we have to check the Max(left, up) Max(←,↑)
 * Here i index is diff in dp[][] and text
 * so, when i=1 than i-1 = 0 for char and we have to check the 0th index char but for dp it is 1
 *
 *                         0    1    2    3
 *
 *                             c0    c1   c2
 *                                                 -→ text2
 *                         ""   a    c    e
 *                       _____________________
 *        dp0       ""   | 0  | 0  | 0  | 0  |
 *        dp1   c0   a   | 0  | ↖ 1|←↑ 1|←↑ 1|
 *        dp2   c1   b   | 0  |←↑ 1|←↑ 1|←↑ 1|
 *        dp3   c2   c   | 0  |←↑ 1| ↖ 2|←↑ 2|
 *        dp4   c3   d   | 0  |←↑ 1|←↑ 2|←↑ 2|
 *        dp5   c4   e   | 0  |←↑ 1|←↑ 2| ↖ 3|
 *                       ---------------------
 *                |
 *                ↓
 *              text1
 *
 * Look diagonally only when str1[i] == str2[j] i.e characters matched && ++ the diagonal value
 *
 *
 * This Longest Common Subsequence problem looks like the combination of:
 * 1) Structurally like 0-1 Knapsack - {@link Algorithms.DynamicProgramming.Knapsack_01_DP_SubsetSumProblem}
 * 2) Semantically tied to Levenshtein Distance - {@link Algorithms.DynamicProgramming.EditDistance}
 *
 * It's like 0-1 Knapsack problem -- cause let's say we have multiple same chars in different positions in text1 & text2
 * so, we can consider the first occurrence of same char or not -- or second occurrence or not -- or third occurrence or not........
 *
 *
 * </pre>
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 07 Nov 2024
 * @link 1143. Longest Common Subsequence <a href="https://leetcode.com/problems/longest-common-subsequence/">LeetCode link</a>
 * @topics String, Dynamic Programming
 * @companies Salesforce, Amazon, Meta, Microsoft, Google, Bloomberg, Oracle, DoorDash, Adobe, ByteDance, Walmart Labs, TikTok, Yahoo, BP, Optum
 */
public class LongestCommonSubsequence {

    public static void main(String[] args) {
        String text1 = "abcdgh";
        String text2 = "aedfhr";
        System.out.println("longestCommonSubsequence BruteForce By AllPairs: " + longestCommonSubsequenceBruteForceByAllPairs(text1, text2));
        System.out.println("longestCommonSubsequence TopDownDp but TimeNotOptimized: " + longestCommonSubsequenceTopDownDpButTimeNotOptimized(text1, text2));
        System.out.println("longestCommonSubsequence TopDownDp: " + longestCommonSubsequenceTopDownDp(text1, text2));
        System.out.println("longestCommonSubsequence BottomUpTabulationDp: " + longestCommonSubsequenceBottomUpTabulationDp(text1, text2));
        System.out.println("longestCommonSubsequence BottomUpTabulationDp and SpaceOptimized: " + longestCommonSubsequenceBottomUpTabulationDpSpaceOptimized(text1, text2));
    }

    /**
     * @TimeComplexity O(m*n)
     * @SpaceComplexity O(m*n)
     *
     * Just like {@link Algorithms.DynamicProgramming.HouseRobber#robBottomUpNoMemory(int[])} maintain the max CommonSubsequence in dp[i][j] & ↖ ++ if same char

      Another example:

      "ylqpejqbalahwr"
      "yrkzavgdmdgtqpg"
       012345678901234

                     ""    y    l   q    p    e    j    q    b    a    l    a    h    w    r
                 _______|____|____|____|____|____|____|____|____|____|____|____|____|____|____|
                 ""|  0 | 0  | 0  | 0  | 0  | 0  | 0  | 0  | 0  | 0  | 0  | 0  | 0  | 0  | 0  |
                 y |  0 | ↖1 | 1  | 1  | 1  | 1  | 1  | 1  | 1  | 1  | 1  | 1  | 1  | 1  | 1  |
                 r |  0 | 1  | 1  | 1  | 1  | 1  | 1  | 1  | 1  | 1  | 1  | 1  | 1  | 1  | ↖2 |
                 k |  0 | 1  | 1  | 1  | 1  | 1  | 1  | 1  | 1  | 1  | 1  | 1  | 1  | 1  | 2  |
                 z |  0 | 1  | 1  | 1  | 1  | 1  | 1  | 1  | 1  | 1  | 1  | 1  | 1  | 1  | 2  |
                 a |  0 | 1  | 1  | 1  | 1  | 1  | 1  | 1  | 1  | ↖2 | 1  |↖2  | 1  | 1  | 2  |
                 v |  0 | 1  | 1  | 1  | 1  | 1  | 1  | 1  | 1  | 2  | 2  | 2  | 2  | 2  | 2  |
                 g |  0 | 1  | 1  | 1  | 1  | 1  | 1  | 1  | 1  | 2  | 2  | 2  | 2  | 2  | 2  |
                 d |  0 | 1  | 1  | 1  | 1  | 1  | 1  | 1  | 1  | 2  | 2  | 2  | 2  | 2  | 2  |
                 m |  0 | 1  | 1  | 1  | 1  | 1  | 1  | 1  | 1  | 2  | 2  | 2  | 2  | 2  | 2  |
                 d |  0 | 1  | 1  | 1  | 1  | 1  | 1  | 1  | 1  | 2  | 2  | 2  | 2  | 2  | 2  |
                 g |  0 | 1  | 1  | 1  | 1  | 1  | 1  | 1  | 1  | 2  | 2  | 2  | 2  | 2  | 2  |
                 t |  0 | 1  | 1  | 1  | 1  | 1  | 1  | 1  | 1  | 2  | 2  | 2  | 2  | 2  | 2  |
                 q |  0 | 1  | 1  | ↖2 | 2  | 2  | 2  | ↖2 | 2  | 2  | 2  | 2  | 2  | 2  | 2  |
                 p |  0 | 1  | 1  | 2  | ↖3 | 3  | 3  | 3  | 3  | 3  | 3  | 3  | 3  | 3  | 3  |    <--- understanding this row then you will understand this "Max(←,↑) RECURRENCE RELATION"
                 g |  0 | 1  | 1  | 1  | 3  | 3  | 3  | 3  | 3  | 3  | 3  | 3  | 3  | 3  | 3  |

     */
    public static int longestCommonSubsequenceBottomUpTabulationDp(String text1, String text2) {
        int m = text1.length();
        int n = text2.length();
        int[][] dp = new int[m+1][n+1]; // by default all values are 0 so we don't need to set 0 if (i == 0 || j == 0) again

        for(int i=1; i<=m; i++) {
            for(int j=1; j<=n; j++) {
                if (i==0 || j==0) dp[i][j] = 0; // optional as int[][] by default all values are 0
                else if(text1.charAt(i-1) == text2.charAt(j-1)) // --- characters matched
                    dp[i][j] = dp[i-1][j-1]+1; // ↖ + 1
                else
                    dp[i][j] = Math.max(dp[i-1][j], dp[i][j-1]); // Max(←,↑) RECURRENCE RELATION
            }
        }

        return dp[m][n];

    }




    /**
     * @TimeComplexity O(M*N)
     * @SpaceComplexity O(M)
     *
     * same like {@link Algorithms.DynamicProgramming.EditDistance#minDistanceUsingLevenshteinDistanceBottomUpTabulationDpOptimized}
     *
                so instead of 2d array

                             j-1  j
                            +---+---+
                        i-1 | a | b |   ← top row
                            +---+---+
                         i  | c | ❓|   ← curr row
                            +---+---+


                use 1d array
                               j-1      j
                            +-------+-------+
                        i-1 | prevA | prevB |   ← prev[] = previous row = topRow
                            +-------+-------+
                         i  | currC |   ❓  |   ← curr[] = current row = currRow
                            +-------+-------+
     */
    public static int longestCommonSubsequenceBottomUpTabulationDpSpaceOptimized(String text1, String text2) {

        if (text2.length() < text1.length()) { // make text2 bigger
            String temp = text1;
            text1 = text2;
            text2 = temp;
        }

        int m = text1.length();
        int n = text2.length();

        int[] previous = new int[m + 1];
        int[] current = new int[m + 1];

        for (int c = n-1 ; c >= 0; c--) { // col - text2
            for (int r = m-1; r >= 0; r--) { // row - text1
                if (text1.charAt(r) == text2.charAt(c)) {
                    current[r] = 1 + previous[r + 1];
                } else {
                    current[r] = Math.max(previous[r], current[r + 1]);
                }
            }
            int[] temp = previous;
            previous = current;
            current = temp;
        }

        return previous[0];
    }






    /**
     * <pre>
     *                         0    1    2    3
     *
     *                        c0    c1   c2
     *                                                 -→ text2
     *                         a    c    e    ""
     *                       _____________________
     *        dp0   c0   a   | 3 ↖|2 ↓→|1 ↓→|  0 |
     *        dp1   c1   b   |2 ↓→|2 ↓→|1 ↓→|  0 |
     *        dp2   c2   c   |2 ↓→| 2 ↖|1 ↓→|  0 |
     *        dp3   c3   d   |1 ↓→|1 ↓→|1 ↓→|  0 |
     *        dp4   c4   e   |1 ↓→|1 ↓→| 1 ↖|  0 |
     *        dp5        ""  | 0  |  0 |  0 |  0 |
     *                     ---------------------
     *                |
     *                ↓
     *              text1
     *
     * </pre>
     * @TimeComplexity O(m*n)
     * @SpaceComplexity O(m*n)
     */
    public static int longestCommonSubsequenceTopDownDp(String text1, String text2) {
        int[][] dp = new int[text1.length()+1][text2.length()+1];
        for(int i =0; i<text1.length()+1; i++){
            Arrays.fill(dp[i], -1);
        }
        return rec(text1, text2, 0, 0, dp); // dp[0][0] i.e inside the recursion it calculates form dp[m][n] to dp[0][0] unlike above tabulation
    }

    private static int rec(String text1, String text2, int i, int j, int[][] dp){
        if(i == text1.length() || j == text2.length()) // when m,n or in above -1 loop skip the m,n as by default dp[][] values are 0
            return 0;
        if(dp[i][j] != -1) return dp[i][j];

        int ans; // optional, we can directly set dp[i][j] in below conditions
        if(text1.charAt(i) == text2.charAt(j))
            ans =  rec(text1, text2, i+1,j+1, dp) + 1; // ↘ + 1
        else
            ans = Math.max(rec(text1, text2, i+1,j, dp), rec(text1, text2, i,j+1, dp)); // Max(↓,→)

        return dp[i][j] = ans;
    }











    /**
     * @TimeComplexity O(M*N^2) ---> TimeLimitExceeded / MemoryLimitExceeded
     * @SpaceComplexity O(N^2)
     * text1 = "ubmrapg" -> ""
     * text2 = "ezupkr"
     */
    public static int longestCommonSubsequenceBruteForceByAllPairs(String text1, String text2) {
        if (text1.length() < text2.length()) { // Make text1 the longer one for fewer subsequences
            String temp = text1;
            text1 = text2;
            text2 = temp;
        }

        List<String> allSubsequences = new ArrayList<>();
        generateSubsequences(text1, 0, new StringBuilder(), allSubsequences);

        int maxLen = 0;
        for (String sub : allSubsequences) {
            if (isSubsequence(sub, text2)) {
                maxLen = Math.max(maxLen, sub.length());
            }
        }

        return maxLen;
    }

    // Generate all subsequences of text
    private static void generateSubsequences(String text, int index, StringBuilder current, List<String> result) {
        if (index == text.length()) {
            result.add(current.toString());
            return;
        }

        // Exclude current character
        generateSubsequences(text, index + 1, current, result);

        // Include current character
        current.append(text.charAt(index));
        generateSubsequences(text, index + 1, current, result);
        current.deleteCharAt(current.length() - 1); // reset backtrack

    }

    // Check if sub is a subsequence of full
    private static boolean isSubsequence(String sub, String full) {
        int i = 0, j = 0;
        while (i < sub.length() && j < full.length()) {
            if (sub.charAt(i) == full.charAt(j)) {
                i++;
                j++;
            } else {
                j++;
            }
        }
        return i == sub.length();
    }













    private static int[][] memo;
    private static String text1;
    private static String text2;

    /**
     * @TimeComplexity O(M*N^2)
     * @SpaceComplexity O(M*N)
     */
    public static int longestCommonSubsequenceTopDownDpButTimeNotOptimized(String text1, String text2) {
        LongestCommonSubsequence.memo = new int[text1.length() + 1][text2.length() + 1];
        for (int i = 0; i < text1.length(); i++) {
            for (int j = 0; j < text2.length(); j++) {
                LongestCommonSubsequence.memo[i][j] = -1;
            }
        }
        LongestCommonSubsequence.text1 = text1;
        LongestCommonSubsequence.text2 = text2;
        return memoSolve(0, 0);
    }

    private static int memoSolve(int p1, int p2) {
        if (memo[p1][p2] != -1) {
            return memo[p1][p2];
        }

        int option1 = memoSolve(p1 + 1, p2); // Option 1: we don't include text1[p1] in the solution.

        int firstOccurence = text2.indexOf(text1.charAt(p1), p2); // Option 2: We include text1[p1] in the solution, as long as a match for it in text2 at or after p2 exists.
        int option2 = 0;
        if (firstOccurence != -1) {
            option2 = 1 + memoSolve(p1 + 1, firstOccurence + 1);
        }

        memo[p1][p2] = Math.max(option1, option2);
        return memo[p1][p2];
    }












    /**
     * Here, we're finding lcs for each index
     * But we're not sure whether to choose 2nd char are lcs or 3rd char in lcs so that we'll get bigger lcs
     */
    @SuppressWarnings("unused")
    public int longestCommonSubsequenceMyApproachNotWorking(String text1, String text2) {
        String min = "", max = "";

        if (text1.length()<text2.length()) {
            min = text1;
            max = text2;
        } else {
            min = text2;
            max = text1;
        }

        Map<Character, List<Integer>> minMap = new HashMap<>();
        for(int i=0; i<min.length(); i++) {
            minMap.computeIfAbsent(min.charAt(i), k->new ArrayList<>()).add(i);
        }

        int maxCount = 0;
        for(int start=max.length()-1; start>=0; start--) {
            int markI = -1;
            int count = 0;
            for (int i=start; i< max.length(); i++) {
                char c = max.charAt(i);

                if(!minMap.containsKey(c)) continue;

                List<Integer> lst = minMap.get(c);
                for(int idx: lst) {
                    if(idx > markI) {
                        markI = idx;
                        count++;
                        break;
                    }
                }
            }
            maxCount=Math.max(maxCount, count);
        }

        return maxCount;
    }


}
