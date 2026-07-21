package Algorithms.DynamicProgramming;

/**

        s1 = abcdef
        s2 = XYZUVW
        s3 = aXYbcZdefUVW

        One valid partition is

        s1:  a | bc | def
             1   2     3

        s2:  XY | Z | UVW
             2    1    3




 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 20 July 2026
 * @link 97. Interleaving String <a href="https://leetcode.com/problems/interleaving-string/">Leetcode link</a>
 * @topics String, Dynamic Programming
 * @companies Amazon(4), Google(3), Microsoft(13), Bloomberg(9), Meta(6), Axon(6), Apple(5), eBay(3), Zoho(2), ThoughtSpot(2)
 */
public class InterleavingString {

    public static void main(String[] args) {
        String s1 = "abcdef", s2 = "XYXUVW", s3 = "aXYbcZdefUVW";
        System.out.println("isInterleave Using Backtracking 1: " + isInterleaveUsingBacktracking1(s1, s2, s3));
        System.out.println("isInterleave Using Backtracking 2: " + isInterleaveUsingBacktracking2(s1, s2, s3));
        System.out.println("isInterleave Using Backtracking 3: " + isInterleaveUsingBacktracking3(s1, s2, s3));
        System.out.println("isInterleave Using Backtracking 4: " + isInterleaveUsingBacktracking4(s1, s2, s3));

        System.out.println("isInterleave Using TopDownMemoDp 1: " + isInterleaveUsingTopDownMemoDp1(s1, s2, s3));
        System.out.println("isInterleave Using BottomUpDp 1: " + isInterleaveUsingBottomUpDp1(s1, s2, s3));
        System.out.println("isInterleave Using BottomUpDpOptimizedSpace 1: " + isInterleaveUsingBottomUpDpOptimizedSpace1(s1, s2, s3));

        System.out.println("isInterleave Using TopDownMemoDp 2: " + isInterleaveUsingTopDownMemoDp2(s1, s2, s3));
        System.out.println("isInterleave Using BottomUpDp 2: " + isInterleaveUsingBottomUpDp2(s1, s2, s3));
        System.out.println("isInterleave Using BottomUpDp OptimizedSpace 2: " + isInterleaveUsingBottomUpDpOptimizedSpace2(s1, s2, s3));

        System.out.println("isInterleave Using BottomUpDp 3 🔥: " + isInterleaveUsingBottomUpDp3(s1, s2, s3));
        System.out.println("isInterleave Using BottomUpDp OptimizedSpace 3: " + isInterleaveUsingBottomUpDpOptimizedSpace3(s1, s2, s3));
    }

    /**

        s1 = abcdef
        s2 = XYZUVW

        s3 = aXYbcZdefUVW

        One valid partition is

        s1:  a | bc | def
             1   2     3

        s2:  XY | Z | UVW
             2    1    3


                                        ""
                                        ""
                                ________|__________
                                |                 |
                                "a"               ""
                                ""               "x"
                                    _____________|_____________
                                    |                         |
                                    "a"                        ""
                                    "x"                       "xy"
                            ___________|___________    ___________|___________
                            |                     |    |                      |
                        "ab"                  "a"   "a"                    ""
                        "x"                   "xy"  "xy"                  "xyz"
                                        _____________|_____________
                                        |            |            |
                                        ""          "ab"         "a"
                                        ""          "xy"        "xyz"

        if matched then we'll have 1 extra recursion with s1 = "bcdef" instead of "abcdef" and s2 = "ZUVW" instead of "XYZUVW"

     * @TimeComplexity O(2^(m+n))
     * @SpaceComplexity O(m+n)
     */
    public static boolean isInterleaveUsingBacktracking1(String s1, String s2, String s3) {
        if (s1.length() + s2.length() != s3.length()) return false;
        return backtrack(s1, s2, s3, "", "", -1, -1);
    }

    private static boolean backtrack(String s1, String s2, String s3, String l, String r, int li, int ri) {
        if (s3.equals(l+r)) return true;

        if (l.length() + r.length() > 0) {
            if (!s3.startsWith(l+r)) return false;
            String nextS1 = s1.substring(l.length());
            String nextS2 = s2.substring(r.length());
            String nextS3 = s3.substring(l.length() + r.length());
            if (backtrack(nextS1, nextS2, nextS3, "", "", -1, -1)) return true;
        }

        if (li+1 < s1.length() && backtrack(s1, s2, s3, l+s1.charAt(li+1), r, li+1, ri)) return true;
        if (ri+1 < s2.length() && backtrack(s1, s2, s3, l, r+s2.charAt(ri+1), li, ri+1)) return true;

        return false;
    }






    /**

        s1 = abcdef
        s2 = XYZUVW

        s3 = aXYbcZdefUVW

        One valid partition is

        s1:  a | bc | def
             1   2     3

        s2:  XY | Z | UVW
             2    1    3


                                        ""
                                ________|_____________
                                |                    |
                               "a"                  "x"
                   _____________|_____________
                   |                         |
                  "ab"                      "ax"
        ___________|___________    ___________|___________
        |                     |    |                     |
    "abc"                  "abx" "axb"                 "axy"
                                                __________|__________
                                                |                   |
                                              "axyb"             "axyz"
                                        ________|________
                                        |               |
                                     "axybc"         "axybz"


     * @TimeComplexity O(2^(m+n))
     * @SpaceComplexity O(m+n)
     */
    public static boolean isInterleaveUsingBacktracking2(String s1, String s2, String s3) {
        if (s1.length() + s2.length() != s3.length()) return false;
        return backtrack(s1, s2, s3, "", -1, -1);
    }

    private static boolean backtrack(String s1, String s2, String s3, String interleaved, int li, int ri) {
        if (s3.equals(interleaved)) return true;
        else if (!interleaved.isEmpty() && !s3.startsWith(interleaved)) return false; // Pruning: If our current sequence doesn't match the start of s3, stop exploring this path

        if (li+1 < s1.length() && backtrack(s1, s2, s3, interleaved + s1.charAt(li+1), li+1, ri)) return true;
        if (ri+1 < s2.length() && backtrack(s1, s2, s3, interleaved + s2.charAt(ri+1), li, ri+1)) return true;

        return false;
    }



    /**
     * @TimeComplexity O(2^(m+n))
     * @SpaceComplexity O(m+n)
     */
    public static boolean isInterleaveUsingBacktracking3(String s1, String s2, String s3) {
        if (s1.length() + s2.length() != s3.length()) return false;
        return backtrack(s1, s2, 0, 0, "", s3);
    }
    public static boolean backtrack(String s1, String s2, int i, int j, String res, String s3) {
        if (res.equals(s3) && i == s1.length() && j == s2.length()) return true;

        boolean ans = false;
        if (i < s1.length()) ans |= backtrack(s1, s2, i+1, j, res+s1.charAt(i), s3);
        if (j < s2.length()) ans |= backtrack(s1, s2, i, j+1, res+s2.charAt(j), s3);
        return ans;
    }



    /**
     * @TimeComplexity O(2^(m+n))
     * @SpaceComplexity O(m+n)
     */
    public static boolean isInterleaveUsingBacktracking4(String s1, String s2, String s3) {
        if (s1.length() + s2.length() != s3.length()) return false;
        return backtrack(s1, s2, s3, 0, 0);
    }

    private static boolean backtrack(String s1, String s2, String s3, int i, int j) {
        if (i == s1.length() && j == s2.length()) return true;

        int k = i + j;
        if (i < s1.length() && s1.charAt(i) == s3.charAt(k) && backtrack(s1, s2, s3, i+1, j)) return true;
        if (j < s2.length() && s2.charAt(j) == s3.charAt(k) && backtrack(s1, s2, s3, i, j+1)) return true;

        return false;
    }





    /**
     * @TimeComplexity O(mn)
     * @SpaceComplexity O(mn)
     */
    public static boolean isInterleaveUsingTopDownMemoDp1(String s1, String s2, String s3) {
        if (s1.length() + s2.length() != s3.length()) return false;
        return dfs(s1, s2, s3, 0, 0, new Boolean[s1.length()+1][s2.length()+1]);
    }

    private static boolean dfs(String s1, String s2, String s3, int i, int j, Boolean[][] memo) {
        if (i == s1.length() && j == s2.length()) return true;
        else if (memo[i][j] != null) return memo[i][j];

        int k = i + j;

        memo[i][j] = i < s1.length() && s1.charAt(i) == s3.charAt(k) && dfs(s1, s2, s3, i+1, j, memo) ||
                    j < s2.length() && s2.charAt(j) == s3.charAt(k) && dfs(s1, s2, s3, i, j+1, memo);

        return memo[i][j];
    }


    /**


        dfs(i, j) becomes dp[i][j]

        dp[i][j] depends on dp[i+1][j] & dp[i][j+1]

        for(i = m-1,)
            for(j = n-1)
                k = i+j
                dp[i][j] =  s1.charAt(i) == s3.charAt(k) && dp[i+1][j] || s2.charAt(j) == s3.charAt(k) && dp[i][j+1]

     * @TimeComplexity O(mn)
     * @SpaceComplexity O(mn)
     */
    public static boolean isInterleaveUsingBottomUpDp1(String s1, String s2, String s3) {
        if (s1.length() + s2.length() != s3.length()) return false;
        int m = s1.length();
        int n = s2.length();
        boolean[][] dp = new boolean[m + 1][n + 1];
        dp[m][n] = true; // Base case: both s1 and s2 are fully matched


        for (int i = m-1; i >= 0; i--) { // Fill the last column: s2 is fully consumed, only s1 characters remain to match s3
            dp[i][n] = dp[i + 1][n] && s1.charAt(i) == s3.charAt(i + n);
        }

        for (int j = n-1; j >= 0; j--) { // Fill the last row: s1 is fully consumed, only s2 characters remain to match s3
            dp[m][j] = dp[m][j + 1] && s2.charAt(j) == s3.charAt(m + j);
        }

        for (int i = m-1; i >= 0; i--) {
            for (int j = n-1; j >= 0; j--) {
                int k = i + j;
                dp[i][j] = s1.charAt(i) == s3.charAt(k) && dp[i+1][j] || s2.charAt(j) == s3.charAt(k) && dp[i][j+1];
            }
        }

        return dp[0][0];
    }



    /**
     * @TimeComplexity O(mn)
     * @SpaceComplexity O(n)
     */
    public static boolean isInterleaveUsingBottomUpDpOptimizedSpace1(String s1, String s2, String s3) {
        if (s1.length() + s2.length() != s3.length()) return false;
        int m = s1.length();
        int n = s2.length();
        boolean[] dp = new boolean[n + 1];
        dp[n] = true; // Base case: both s1 and s2 are fully matched

        for (int j = n-1; j >= 0; j--) { // Fill the last row: s1 is fully consumed, only s2 characters remain to match s3
            dp[j] = dp[j + 1] && s2.charAt(j) == s3.charAt(m + j);
        }

        for (int i = m-1; i >= 0; i--) {
            // Update the rightmost boundary for current row i (s2 is fully consumed)
            dp[n] = dp[n] && s1.charAt(i) == s3.charAt(i + n);
            for (int j = n-1; j >= 0; j--) {
                int k = i + j;
                dp[j] = s1.charAt(i) == s3.charAt(k) && dp[j] || s2.charAt(j) == s3.charAt(k) && dp[j+1];
            }
        }

        return dp[0];
    }








    /**
     * @TimeComplexity O(mn)
     * @SpaceComplexity O(mn)
     */
    public static boolean isInterleaveUsingTopDownMemoDp2(String s1, String s2, String s3) {
        if (s1.length() + s2.length() != s3.length()) return false;
        return dfs(s1, s2, s3, 0, 0, 0, new Boolean[s1.length()][s2.length()]);
    }


    private static boolean dfs(String s1, String s2, String s3, int i, int j, int k, Boolean[][] memo) {
        if (i == s1.length()) return s2.substring(j).equals(s3.substring(k));
        else if (j == s2.length()) return s1.substring(i).equals(s3.substring(k));
        else if (memo[i][j] != null) return memo[i][j];

        boolean ans = false;
        if ((s3.charAt(k) == s1.charAt(i) && dfs(s1, s2, s3, i+1, j, k+1, memo)) ||
            (s3.charAt(k) == s2.charAt(j) && dfs(s1, s2, s3, i, j+1, k+1, memo))) {
            ans = true;
        }
        memo[i][j] = ans;
        return ans;
    }




    /**
     * @TimeComplexity O(mn)
     * @SpaceComplexity O(mn)
     */
    public static boolean isInterleaveUsingBottomUpDp2(String s1, String s2, String s3) {
        if (s3.length() != s1.length() + s2.length()) {
            return false;
        }
        boolean dp[][] = new boolean[s1.length() + 1][s2.length() + 1];
        for (int i = 0; i <= s1.length(); i++) {
            for (int j = 0; j <= s2.length(); j++) {
                if (i == 0 && j == 0) dp[i][j] = true;
                else if (i == 0) dp[i][j] = dp[i][j - 1] && s2.charAt(j - 1) == s3.charAt(i + j - 1);
                else if (j == 0) dp[i][j] = dp[i - 1][j] && s1.charAt(i - 1) == s3.charAt(i + j - 1);
                else dp[i][j] = (dp[i - 1][j] && s1.charAt(i - 1) == s3.charAt(i + j - 1)) || (dp[i][j - 1] && s2.charAt(j - 1) == s3.charAt(i + j - 1));
            }
        }
        return dp[s1.length()][s2.length()];
    }


    /**
     * @TimeComplexity O(mn)
     * @SpaceComplexity O(n)
     */
    public static boolean isInterleaveUsingBottomUpDpOptimizedSpace2(String s1, String s2, String s3) {
        if (s3.length() != s1.length() + s2.length()) return false;
        boolean dp[] = new boolean[s2.length() + 1];
        for (int i = 0; i <= s1.length(); i++) {
            for (int j = 0; j <= s2.length(); j++) {
                if (i == 0 && j == 0) dp[j] = true;
                else if (i == 0) dp[j] = dp[j - 1] && s2.charAt(j - 1) == s3.charAt(i + j - 1);
                else if (j == 0) dp[j] = dp[j] && s1.charAt(i - 1) == s3.charAt(i + j - 1);
                else dp[j] = (dp[j] && s1.charAt(i - 1) == s3.charAt(i + j - 1)) || (dp[j - 1] && s2.charAt(j - 1) == s3.charAt(i + j - 1));
            }
        }
        return dp[s2.length()];
    }




    /**
     * @TimeComplexity O(mn)
     * @SpaceComplexity O(mn)
     */
    public static boolean isInterleaveUsingBottomUpDp3(String s1, String s2, String s3) {
        if (s3.length() != s1.length() + s2.length()) {
            return false;
        }
        boolean[][] dp = new boolean[s1.length() + 1][s2.length() + 1];
        dp[0][0] = true;

        for (int i = 0; i <= s1.length(); i++) {
            for (int j = 0; j <= s2.length(); j++) {
                if (j > 0 && dp[i][j - 1] && s2.charAt(j - 1) == s3.charAt(i + j - 1)) dp[i][j] = true;
                if (i > 0 && dp[i - 1][j] && s1.charAt(i - 1) == s3.charAt(i + j - 1)) dp[i][j] = true;
            }
        }
        return dp[s1.length()][s2.length()];
    }


    /**
     * @TimeComplexity O(mn)
     * @SpaceComplexity O(n)
     */
    public static boolean isInterleaveUsingBottomUpDpOptimizedSpace3(String s1, String s2, String s3) {
        if (s3.length() != s1.length() + s2.length()) return false;

        int m = s1.length();
        int n = s2.length();
        boolean[] dp = new boolean[n + 1];

        for (int i = 0; i <= m; i++) {
            for (int j = 0; j <= n; j++) {
                if (i == 0 && j == 0) {
                    dp[0] = true;
                    continue;
                }

                // Look at s1: uses dp[j] from the PREVIOUS row (i - 1)
                boolean fromS1 = (i > 0) && dp[j] && (s1.charAt(i - 1) == s3.charAt(i + j - 1));

                // Look at s2: uses dp[j - 1] from the CURRENT row
                boolean fromS2 = (j > 0) && dp[j - 1] && (s2.charAt(j - 1) == s3.charAt(i + j - 1));

                dp[j] = fromS1 || fromS2; // Overwrite dp[j] with the fresh result for row i
            }
        }

        return dp[n];
    }



















    /**

        s1 = "aabcc", s2 = "dbbca", s3 = "aadbbcbcac"

        m = len from s1
        n = len from s2

        1 <= m < s1.length
        1 <= n < s2.length

        ms + ns + ms + ns + ....


                                                    s1 = "aabcc", s2 = "dbbca"
                                                             s3 = ""
              ____________________________________________________|____________________________________________________
              |                          |                        |                           |                       |
     s1="a", s2 = "dbbca"       s1="aa", s2 = "dbbca"       s1="aab", s2 = "dbbca"    s1="aabc", s2 = "dbbca"    s1="aabcc", s2 = "dbbca"
            m=1                         m=2                      m=3                         m=4                     m=5



        how about n?

            for (int m = 1; m < s1.length(); m++) {
                for (int n = 1; n < s2.length(); n++) {

                }
            }

           i   m = 2, M = 3
         012
        "aab"



        FALING AT THIS SCENARIO:

        s1 = abcdef
        s2 = XYZUVW
        s3 = aXYbcZdefUVW

        One valid partition is

        s1:  a | bc | def
             1   2     3

        s2:  XY | Z | UVW
             2    1    3



     */
    public static boolean isInterleaveUsingBruteForce_NOT_WORKING(String s1, String s2, String s3) {
        final int M = s1.length(), N = s2.length(), L = s3.length();

        if (M == 0 && N == 0 && L == 0) return true;
        else if (M + N != L) return false;
        else if (s3.equals(s1+s2) || s3.equals(s2+s1)) return true;

            for (int m = 0; m <= M; m++) {
                for (int n = 1; n <= N; n++) {
                    if (isValid(s1, s2, s3, m, n, m+n, M, N, L) || isValid(s2, s1, s3, m, n, m+n, N, M, L)) return true;
                }
            }

        return false;
    }

    private static boolean isValid(String s1, String s2, String s3, int m, int n, int l, int M, int N, int L) {
        int i = 0, j = 0, k = 0;
        while ((i < M || j < N) && k < s3.length()) {
            String ms = "", ns = "";

            int mNext = Math.min(m, M-i);
            int nNext = Math.min(n, N-j);
            int lNext = L-k;
            if (mNext + nNext > 0) lNext = Math.min(mNext+nNext, L-k);

            if (i < M) ms = s1.substring(i, i + mNext);
            if (j < N) ns = s2.substring(j, j + nNext);
            String s = s3.substring(k, k + lNext);

            if (!s.equals(ms+ns)) return false;

            i += mNext;
            j += nNext;
            k += lNext;

            // if (!ms.isEmpty()) k += mNext;
            // if (!ns.isEmpty()) k += nNext;
        }

        return true;
    }
}
