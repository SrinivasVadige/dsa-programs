package Algorithms.DynamicProgramming;

import java.util.Arrays;

/**
 * @author Srinvas Vadige, srinivas.vadige@gmail.com
 * @since 06 Oct 2024
 * @link 5. Longest Palindromic Substring <a href="https://leetcode.com/problems/longest-palindromic-substring/">LeetCode link</a>
 * @topics Two Pointers, Dynamic Programming, String
 * @companies Google(25), Amazon(23), Microsoft(12), Bloomberg(11), Meta(6), TCS(3), Infosys(2), Apple(2), Visa(2), Yandex(2), IBM(3), Uber(2), Cisco(43), TikTok(19), Zoho(17), Oracle(15), Accenture(8), Cognizant(7), PhonePe(7), Deloitte(6), Adobe(6), Walmart Labs(6)
 */
public class LongestPalindromicSubstring {

    public static void main(String[] args) {
        String s = "cbbd";
        System.out.println("longestPalindrome using Check All Substrings BruteForce 1 => " + longestPalindrome_Using_CheckAllSubstrings_BruteForce1(s));
        System.out.println("longestPalindrome using Check All Substrings BruteForce 2 => " + longestPalindrome_Using_CheckAllSubstrings_BruteForce2(s));

        System.out.println("longestPalindrome using Recursive Interval Shrinking Backtracking TLE => " + longestPalindrome_Using_RecursiveIntervalShrinking_Backtracking_TLE(s));
        System.out.println("longestPalindrome using Recursive Interval Shrinking TopDown Memo DP => " + longestPalindrome_Using_RecursiveIntervalShrinking_TopDownMemoDp(s));

        System.out.println("longestPalindrome using dp => " + longestPalindromeUsingBottomUpDp1(s));
        System.out.println("longestPalindrome using expand from centers => " + longestPalindromeUsingExpandFromCenters(s));
        System.out.println("longestPalindrome using Manacher's Algorithm => " + longestPalindromeUsingManachersAlgorithm(s));
    }


    /**
     * @TimeComplexity O(n^3)
     * @SpaceComplexity O(1)
     */
    public static String longestPalindrome_Using_CheckAllSubstrings_BruteForce1(String s) {
        String res = "";
        for (int i=0; i< s.length(); i++ ){
            for (int j=i+1; j<=s.length(); j++){
                String temp = s.substring(i,j);
                if(temp.length() > res.length() && temp.equals(new StringBuilder(temp).reverse().toString()) )
                    res = temp;
            }
        }

        return res;
    }


    /**
     * @TimeComplexity O(n^3)
     * @SpaceComplexity O(1)
     */
    public static String longestPalindrome_Using_CheckAllSubstrings_BruteForce2(String s) {
        String res = "";
        for(int i=0; i<s.length(); i++) {
            for(int j=i+1; j<=s.length(); j++) {
                String subStr = s.substring(i,j);
                if(checkPalindrome(subStr)) {
                    if (subStr.length() > res.length()) {
                        res = subStr;
                    }
                }
            }
        }
        return res;
    }
    public static boolean checkPalindrome(String str) {
        int first = 0;
        int last = str.length()-1;
        while(first < last) {
            if(str.charAt(first) != str.charAt(last)) return false;
            first++;
            last--;
        }
        return true;
    }









    /**


        "xyabcddcbaz" => "abcddcba"


                                            xyabcddcbazy
                                 yabcddcbazy         xyabcddcbaz
                           abcddcbazy yabcddcbaz
                     bcddcbazy   abcddcbaz
                             bcddcbaz abcddcba
                                 bcddcba  abcddcb


     * @TimeComplexity O(n * 2^n)
     * @SpaceComplexity O(n)
     */
    public static String longestPalindrome_Using_RecursiveIntervalShrinking_Backtracking_TLE(String s) {
        int[] points = backtrack(s, 0, s.length()-1);
        return s.substring(points[0], points[1]+1);
    }
    private static int[] backtrack(String s, int l, int r) {
        if (l>r) return new int[]{0,0};
        else if (isPalindrome(s, l, r)) return new int[]{l, r};

        int[] left = backtrack(s, l+1, r);
        int[] right = backtrack(s, l, r-1);

        if (left[1]-left[0] > right[1]-right[0]) return left;
        return right;
    }
    private static boolean isPalindrome(String s, int l, int r) {
        for (; l<r; l++, r--) {
            if (s.charAt(l) != s.charAt(r)) return false;
        }
        return true;
    }



    /**
     * @TimeComplexity O(n^3)
     * @SpaceComplexity O(n^2)
     */
    public static String longestPalindrome_Using_RecursiveIntervalShrinking_TopDownMemoDp(String s) {
        int n = s.length();
        int[] points = dfs(s, 0, n-1, n, new Integer[n][n]);
        return s.substring(points[0], points[1]+1);
    }
    private static int[] dfs(String s, int l, int r, int n, Integer[][] memo) {
        if (l>r) return new int[]{0,0};
        else if (memo[l][r] != null) {
            int num = memo[l][r];
            int maxL = num / n;
            int maxR = num % n;
            return new int[]{maxL, maxR};
        }
        else if (isPalindrome(s, l, r)) return new int[]{l, r};

        int[] left = dfs(s, l+1, r, n, memo);
        int[] right = dfs(s, l, r-1, n, memo);
        int[] max = (left[1]-left[0] > right[1]-right[0]) ? left : right;

        memo[l][r] = max[0]*n + max[1];

        return max;
    }



    /**

        dfs(l, r) == dp[l][r]

        dp[l][r] = maxDiff of (dp[l+1][r], dp[l][r+1]) && but need isPalindrome(s, l, r)

        so, like above longestPalindromeUsingTopDownMemoDp we can make longestPalindromeUsingBottomUpDp??

        yes we can => use boolean[][] pal instead of int[][] dp



    l = rows
    r = cols

    l > r || r < l == not valid


         0 1 2 3 4
        "b a b a d" => "bab"

         0 1 2 3 4
      0 [t f f f f] f
      1 [- t f f f] f
      2 [- - t f f] f
      3 [- - - t f] f
      4 [- - - - t] f
         t t t t t  t

       if (l >= r) pal[l][r] = true;
       else pal[l][r] = (s.charAt(l) == s.charAt(r)) && pal[l+1][r-1]


       pal[2][4] = "b" == "b" && pal[3][3]


     * @TimeComplexity O(n^2)
     * @SpaceComplexity O(n^2)
     */
    public static String longestPalindromeUsingBottomUpDp1(String s) {
        int n = s.length();
        boolean[][] pal = new boolean[n][n];
        Arrays.fill(pal[n-1], true);

        int maxL = 0, maxR = 0;

        for (int l = n-2; l>=0; l--) {
            for (int r = n-1; r>=0; r--) { // or for (int r=l+1; r<n; r++) { // along with  && (r-l <= 2 || pal[l+1][r-1]);
                    if (l >= r) pal[l][r] = true;
                    else pal[l][r] = (s.charAt(l) == s.charAt(r)) && pal[l+1][r-1];

                    if (pal[l][r] && r-l > maxR-maxL) {
                        maxL = l;
                        maxR = r;
                    }
            }
        }

        return s.substring(maxL, maxR+1);
    }



    /**
        Approach: Bottom-Up NoMemory DP in palindrome() method

      Just assume, dp[i][j] = S[i] == S[j] && dp[i+1][j-1]
      where i and j are the start and end index of the substring.
         S = r a c e c a r
               i       j
      So, in order to dp[i][j] become "true" then S[i]==S[j] and the previous S[i+1][j-1], S[i+2][j-2], S[i+3][j-3]..... is must be "true".
      dp[i+1][j-1] = S[i+1] == S[j-1] && (S[i+2][j-2], S[i+3][j-3].....)
      dp[i+1][j-1] = S[i+1] == S[j-1] && dp[i+2][j-2]

     * @TimeComplexity O(n^2)
     * @SpaceComplexity O(n^2)
    */
    public static String longestPalindromeUsingBottomUpDp2(String s) {
        int n = s.length();
        boolean[][] dp = new boolean[n][n]; // by default -> all values are false

        int start = 0;
        int end = 0;

        for (int l=n-1; l>=0; l--) {
            for (int r=l+1; r<n; r++) {

                if(l==r) { dp[l][r] = true; continue; } // OPTIONAL & NOT NEEDED - single character

                dp[l][r] = s.charAt(l) == s.charAt(r) && (r-l <= 2 || dp[l+1][r-1]);
                if (dp[l][r] && r-l > end-start) { // if bigger palindrome?
                    start = l;
                    end = r;
                }
            }
        }
        return s.substring(start, end+1);
    }








    /**
     * @TimeComplexity O(n^2)
     * @SpaceComplexity O(n^2)
     */
    public static String longestPalindromeUsingBottomUpDp3(String s) {
        int n = s.length();
        boolean[][] dp = new boolean[n][n];
        int[] ans = new int[] { 0, 0 };

        // dp[0][0] = dp[1][1] = dp[2][2].... dp[n-1][n-1] = true --> because all the single characters are palindromes
        for (int i = 0; i < n; i++) {
            dp[i][i] = true;
        }

        for (int i = 0; i < n - 1; i++) {
            if (s.charAt(i) == s.charAt(i + 1)) {
                dp[i][i + 1] = true;
                ans[0] = i;
                ans[1] = i + 1;
            }
        }

        for (int diff = 2; diff < n; diff++) {
            for (int i = 0; i < n - diff; i++) {
                int j = i + diff;
                if (s.charAt(i) == s.charAt(j) && dp[i + 1][j - 1]) { // dp[i][j] = S[i] == S[j] && dp[i+1][j-1]
                    dp[i][j] = true;
                    ans[0] = i;
                    ans[1] = j;
                }
            }
        }

        int i = ans[0];
        int j = ans[1];
        return s.substring(i, j + 1);
    }









    /**
     * @TimeComplexity O(n^2)
     * @SpaceComplexity O(n)
     */
    public static String longestPalindromeUsingBottomUpDpOptimizedSpace1(String s) {
        int n = s.length();
        boolean[] pal = new boolean[n];
        Arrays.fill(pal, true);

        int maxL = 0, maxR = 0;

        for (int l = n-2; l>=0; l--) {
            for (int r = n-1; r>=0; r--) {
                    if (l >= r) pal[r] = true;
                    else pal[r] = (s.charAt(l) == s.charAt(r)) && pal[r-1];

                    if (pal[r] && r-l > maxR-maxL) {
                        maxL = l;
                        maxR = r;
                    }
            }
        }

        return s.substring(maxL, maxR+1);
    }









    /**
     * Approach: Expand From Centers
     * @TimeComplexity O(n^2)
     * @SpaceComplexity O(1)
     */
    public static String longestPalindromeUsingExpandFromCenters(String s) {
        String res = "";

        for(int i=0; i<s.length(); i++){
            String oddLenPal = extendPalindrome(s, i, i); // ODD LEN WITH non-dup chars --> not even len odd len case
            String evenLenPal = extendPalindrome(s, i, i+1); // EVEN LEN WITH dup chars or just skip the dup chars like while (r < n-1 && ch[r] == ch[r+1]) {r++;} ---> check longestPalindrome2Improved() and extendPalindrome2Improved

            if(oddLenPal.length() > res.length()) {
                res = oddLenPal;
            }
            if(evenLenPal.length() > res.length()) {
                res = evenLenPal;
            }
        }
        return res;
    }
    /**
        s.substring() return String Object not the string pool intern. so, it's ok not to catch the value like String res = extendPalindrome(s, i, i);
     */
    public static String extendPalindrome(String s, int l, int r){
        while (l >= 0 && r < s.length() && s.charAt(l) == s.charAt(r)) {
            l--;
            r++;
        }
        // At this point: [l+1, r) is the correct range --> we as l-- and r++ already happened -> use prev values i.e., l+1 and r-1
        return s.substring(l + 1, r);  // r-1 will become r as substring toIndex is explicit
    }













    /**
     * Approach: Manacher's Algorithm - pronounced as Manekers
     * @TimeComplexity O(n)
     * @SpaceComplexity O(n)
     * 4 CASES:
     * 1. Don't pick the char as the new center, if that char is a part of already calculated palindrome
     * 2. If current palindrome can be extended till the end of the input then we break out of the loop
     * 3. Pick the char as the new center, if it expands all the way till the right end, and it's mirror palindrome is also expanded to the left end
     * 4. Don't pick the char as the new center if it expands till the right edge, but it's mirror palindrome expands beyond the left edge
     */
    public static String longestPalindromeUsingManachersAlgorithm(String s) {
        StringBuilder sPrime = new StringBuilder("#");
        for (char c : s.toCharArray()) {
            sPrime.append(c).append("#");
        }

        /*
                s = "cbbd";
                sPrime = "#c#b#b#d#";
         */

        int n = sPrime.length();
        int[] palindromeRadii = new int[n];
        int center = 0;
        int radius = 0;

        for (int i = 0; i < n; i++) {
            int mirror = 2 * center - i;

            if (i < radius) {
                palindromeRadii[i] = Math.min(radius-i, palindromeRadii[mirror]);
            }

            while ( i+1+palindromeRadii[i] < n &&
                    i-1-palindromeRadii[i] >= 0 &&
                    sPrime.charAt(i+1+palindromeRadii[i]) == sPrime.charAt(i-1-palindromeRadii[i])) {
                palindromeRadii[i]++;
            }

            if (i + palindromeRadii[i] > radius) {
                center = i;
                radius = i + palindromeRadii[i];
            }
        }

        int maxLength = 0;
        int centerIndex = 0;
        for (int i = 0; i < n; i++) {
            if (palindromeRadii[i] > maxLength) {
                maxLength = palindromeRadii[i];
                centerIndex = i;
            }
        }

        int startIndex = (centerIndex - maxLength) / 2;

        return s.substring(startIndex, startIndex + maxLength);
    }


























    static int start = 0; // -- only for longestPalindrome()
    static int end = 0; // or use dp = new int[2] instead or just store the maxLen string
    /**
     * Approach: Top-Down DP in palindrome() method
    */
    public static String longestPalindromeUsingTopDownDpDp2(String s) {
        char[] ch = s.toCharArray();
        dfs(ch, 0); // or use dp = new int[2] array instead of start and end i.e dfs(ch, 0, dp); --- still it would be the Bottom-Up NoMemory DP
        return s.substring(start, end+1);
    }

    // Top-Down DP -- as we are just storing the start and end index but not all node values in an dedicated array
    private static void dfs(char[] ch, int i) { // chr[] or s in 1st param
        int l = i;
        int r = i;
        int n = ch.length;
        if (i >= n - 1) { // base case
            return;
        }
        while (r < n-1 && ch[r] == ch[r+1]) { // dup char case --- mandatory otherwise we get errors in scenarios like extendPalindrome(i, i) extendPalindrome(i, i+1)
            r++;
        }
        i = r; /* ---- skipping dup chars for next recursive call */
        while (l > 0 && r < n-1 && ch[l-1] == ch[r+1]) { // palindrome case as both l & r starts from middle
            l--;
            r++;
        }
        if ((end-start) < (r-l)) { // maxLen case
            start = l;
            end = r;
        }
        dfs(ch, i+1); // to traverse each index i.e 0 to n-1
    }







    public String longestPalindromeUsingExpandFromCenters2(String s) {
        int[] ans = new int[] { 0, 0 }; // [start, end]

        for (int i = 0; i < s.length(); i++) {
            int oddLength = expand(i, i, s);
            if (oddLength > ans[1] - ans[0] + 1) {
                int dist = oddLength / 2;
                ans[0] = i - dist;
                ans[1] = i + dist;
            }

            int evenLength = expand(i, i + 1, s);
            if (evenLength > ans[1] - ans[0] + 1) {
                int dist = (evenLength / 2) - 1;
                ans[0] = i - dist;
                ans[1] = i + 1 + dist;
            }
        }

        int i = ans[0];
        int j = ans[1];
        return s.substring(i, j + 1);
    }

    private int expand(int i, int j, String s) {
        int left = i;
        int right = j;

        while (left >= 0 && right < s.length() && s.charAt(left) == s.charAt(right)) {
            left--;
            right++;
        }

        return right - left - 1;
    }










    public static String longestPalindromeUsingExpandFromCenters3(String s) {
        String res = "";
        for(int i=0; i<s.length(); i++){
            res = extendPalindrome2Improved(s, i, i, res);
        }
        return res;
    }

    public static String extendPalindrome2Improved(String s, int l, int r, String res){
        while (r < s.length()-1 && s.charAt(r) == s.charAt(r+1)) {r++;} // dup chars case
        while(l>=0 && r<s.length() && s.charAt(l) == s.charAt(r)){ // validate palindrome case
            if(r-l+1 > res.length()) {
                res = s.substring(l, r+1);
            }
            l--;
            r++;
        }
        return res;
    }



}
