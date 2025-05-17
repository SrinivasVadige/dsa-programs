package Algorithms.DynamicProgramming;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
 * </pre>
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 07 Nov 2024
 */
public class LongestCommonSubsequence {

    public static void main(String[] args) {
        String text1 = "abcdgh";
        String text2 = "aedfhr";
        System.out.println("longestCommonSubsequenceBottomUpTabulationDp: " + longestCommonSubsequenceBottomUpTabulationDp(text1, text2));
        System.out.println("longestCommonSubsequenceTopDownDp: " + longestCommonSubsequenceTopDownDp(text1, text2));
    }

    /**
     * @TimeComplexity O(m*n)
     * @SpaceComplexity O(m*n)
     *
     * Just like {@link Algorithms.DynamicProgramming.HouseRobber#robBottomUpNoMemory(int[])} maintain the max CommonSubsequence in dp[i][j] & ↖ ++ if same char
     *
     * Another example:
     *
     * "ylqpejqbalahwr"
     * "yrkzavgdmdgtqpg"
     *  012345678901234
     *
     *                ""    y    l   q    p    e    j    q    b    a    l    a    h    w    r
     *        ________|____|____|____|____|____|____|____|____|____|____|____|____|____|____|
     *            ""|  0 | 0  | 0  | 0  | 0  | 0  | 0  | 0  | 0  | 0  | 0  | 0  | 0  | 0  | 0  |
     *            y |  0 | ↖1 | 1  | 1  | 1  | 1  | 1  | 1  | 1  | 1  | 1  | 1  | 1  | 1  | 1  |
     *            r |  0 | 1  | 1  | 1  | 1  | 1  | 1  | 1  | 1  | 1  | 1  | 1  | 1  | 1  | ↖2 |
     *            k |  0 | 1  | 1  | 1  | 1  | 1  | 1  | 1  | 1  | 1  | 1  | 1  | 1  | 1  | 2  |
     *            z |  0 | 1  | 1  | 1  | 1  | 1  | 1  | 1  | 1  | 1  | 1  | 1  | 1  | 1  | 2  |
     *            a |  0 | 1  | 1  | 1  | 1  | 1  | 1  | 1  | 1  | ↖2 | 1  |↖2 | 1  | 1  | 2  |
     *            v |  0 | 1  | 1  | 1  | 1  | 1  | 1  | 1  | 1  | 2  | 2  | 2  | 2  | 2  | 2  |
     *            g |  0 | 1  | 1  | 1  | 1  | 1  | 1  | 1  | 1  | 2  | 2  | 2  | 2  | 2  | 2  |
     *            d |  0 | 1  | 1  | 1  | 1  | 1  | 1  | 1  | 1  | 2  | 2  | 2  | 2  | 2  | 2  |
     *            m |  0 | 1  | 1  | 1  | 1  | 1  | 1  | 1  | 1  | 2  | 2  | 2  | 2  | 2  | 2  |
     *            d |  0 | 1  | 1  | 1  | 1  | 1  | 1  | 1  | 1  | 2  | 2  | 2  | 2  | 2  | 2  |
     *            g |  0 | 1  | 1  | 1  | 1  | 1  | 1  | 1  | 1  | 2  | 2  | 2  | 2  | 2  | 2  |
     *            t |  0 | 1  | 1  | 1  | 1  | 1  | 1  | 1  | 1  | 2  | 2  | 2  | 2  | 2  | 2  |
     *            q |  0 | 1  | 1  | ↖2 | 2  | 2  | 2  |↖2 | 2  | 2  | 2  | 2  | 2  | 2  | 2  |
     *            p |  0 | 1  | 1  | 1  |↖3  | 3  | 3  | 3 | 3  | 3  | 3  | 3  | 3  | 3  | 3  |
     *            g |  0 | 1  | 1  | 1  | 3  | 3  | 3  | 3 | 3  | 3  | 3  | 3  | 3  | 3  | 3  |
     *
     */
    public static int longestCommonSubsequenceBottomUpTabulationDp(String text1, String text2) {
        int[][] dp = new int[text1.length() + 1][text2.length() + 1]; // +1 for ""

        for (int i = 0; i <= text1.length(); i++) {
            for (int j = 0; j <= text2.length(); j++) {
                if (i == 0 || j == 0)
                    dp[i][j] = 0;
                else if (text1.charAt(i - 1) == text2.charAt(j - 1)) // --- characters matched
                    dp[i][j] = 1 + dp[i - 1][j - 1]; // ↖ + 1
                else
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]); // Max(←,↑) RECURRENCE EQUATION
            }
        }
        return dp[text1.length()][text2.length()];
    }

    public static int longestCommonSubsequenceBottomUpTabulationDp2(String text1, String text2) {
        int m = text1.length();
        int n = text2.length();
        int[][] dp = new int[m+1][n+1]; // by default all values are 0 so we don't need to set 0 if (i == 0 || j == 0) again

        for(int i=1; i<=m; i++)
            for(int j=1; j<=n; j++) {
                if(text1.charAt(i-1) == text2.charAt(j-1))
                    dp[i][j] = dp[i-1][j-1]+1; // ↖ + 1
                else
                    dp[i][j] = Math.max(dp[i-1][j], dp[i][j-1]); // Max(←,↑)
            }

        return dp[m][n];

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
        int dp[][] = new int[text1.length()+1][text2.length()+1];
        for(int i =0;i<text1.length()+1;i++){
            for(int j =0;j<text2.length()+1;j++)
                dp[i][j] = -1;
        }
        return rec(text1, text2, 0, 0, dp); // dp[0][0] i.e inside the recursion it calculates form dp[m][n] to dp[0][0] unlike above tabulation
    }

    private static int rec(String text1, String text2, int i, int j, int[][] dp){
        if(i == text1.length() || j == text2.length()) // when m,n or in above -1 loop skip the m,n as by default dp[][] values are 0
            return 0;
        if(dp[i][j] != -1)
            return dp[i][j];

        int ans = 0; // optional, we can directly set dp[i][j] in below conditions
        if(text1.charAt(i) == text2.charAt(j))
            ans =  1 + rec(text1, text2, i+1,j+1, dp);
        else
            ans = Math.max(rec(text1, text2, i,j+1, dp), rec(text1, text2, i+1,j, dp));

        return dp[i][j] = ans;
    }








    /**
     * <pre>
     * MY INITIAL THOUGHTS:
     * -------------------
     * abcde
     * ace
     * ANS: 3 (ace len)
     *
     * abde
     * ace
     * ANS: 2 (ae len)
     *
     * patterns
     * a
     * ac ❌ -- not matched so skip ac
     * ae
     *
     * c ❌ -- stop c sequence
     *
     * e
     *
     * same like non-contiguous sub arr sum / PartitionEqualSubsetSum --> but we donno where to start in other string, cause we have duplicate start chars
     *
     * -- to check subText in t2, convert them to two arrays and remove the char in subText if you found it
     *
     * memo hashMap<String, Boolean> each subText and use it in bigger subText
     *
     * 2^n graph
     *
     * abde
     * ace
     * 012
     * ANS: 2 (ae len)
     *
     * -1                                           ""
     * 0                                    a                 ""
     * 1                                ac      a         c        ""
     * 2                                     ae   a     ce c      e  ""
     *
     * i.e loop up to t1.length()
     * Note that we can have duplicate chars too
     * </pre>
     */
    @SuppressWarnings("unused")
    public int longestCommonSubsequenceMyApproach(String text1, String text2) {
        Map<String, Boolean> map = new HashMap<>();
        map.put("", true);

        Map<Character, Integer> t1chars = text1.chars().mapToObj(i->(char)i).collect(Collectors.groupingBy(i->i, Collectors.summingInt(e->1)));

        for (int i=text2.length()-1; i<=0; i--) {
            if(t1chars.containsKey(text2.charAt(i))){
                for (int j=i; j<text2.length()-1; j++) {
                    String temp = ""; // add the longest matching subStr and maintain all the possible subStrs in map
                    //checkSubStr(map, t1chars, i, j);
                    // we can have duplicate chars i.e multiple i char at multiple positions.. so, where do you start?
                    // it'll work


                }
            }

        }
        return 0;
    }


    /**
     * Here, we're finding lcs for each index
     * But we're not sure whether to choose 2nd char are lcs or 3rd char in lcs so that we'll get bigger lcs
     */
    @SuppressWarnings("unused")
    public int longestCommonSubsequenceMyApproach2(String text1, String text2) {
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
