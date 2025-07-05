package Algorithms.DynamicProgramming;

import java.util.Arrays;

/**
 * @author Srinvas Vadige, srinivas.vadige@gmail.com
 * @since 06 Oct 2024
 * @link 5. Longest Palindromic Substring <a href="https://leetcode.com/problems/longest-palindromic-substring/">LeetCode link</a>
 * @topics Two Pointers, Dynamic Programming, String
 * @companies amazon, google, facebook, microsoft, bloomberg, tiktok, cisco, accolite, deloitte, apple, walmart, epam, yandex, cognizant, salesforce, ibm, pure, uber, zoho, oracle, adobe, yahoo, goldman, accenture, tcs, jpmorgan, tinkoff, phonepe
 */
public class LongestPalindromicSubstring {

    public static void main(String[] args) {
        String s = "cbbd";
        System.out.println("longestPalindrome using Manacher's Algorithm => " + longestPalindromeUsingManachersAlgorithm(s));
        System.out.println("longestPalindrome using dp => " + longestPalindromeUsingDp(s));
        System.out.println("longestPalindrome using expand from centers => " + longestPalindromeUsingExpandFromCenters(s));
        System.out.println("longestPalindrome using BruteForce => " + longestPalindromeBruteForce(s));
    }


    /**
     * Approach: Manacher's Algorithm
     * @TimeComplexity O(n)
     * @SpaceComplexity O(n)
     * 4 CASES:
     * 1. Don't pick the char as the new center, if that char is a part of already calculated palindrome
     * 2. If current palindrome can be extended till th end of the input then we break out of the loop
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

            while ( i+1+palindromeRadii[i] < n && i-1-palindromeRadii[i] >= 0 && sPrime.charAt(i+1+palindromeRadii[i]) == sPrime.charAt(i-1-palindromeRadii[i])) {
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

        return s.substring( startIndex, startIndex + maxLength);
    }










    /**
     * Approach: Bottom-Up NoMemory DP in palindrome() method
     * @TimeComplexity O(n^2)
     * @SpaceComplexity O(n^2)
     * Just assume, dp[i][j] = S[i] == S[j] && dp[i+1][j-1]
     * where i and j are the start and end index of the substring.
     *    S = r a c e c a r
     *          i       j
     * So, in order to dp[i][j] become "true" then S[i]==S[j] and the previous S[i+1][j-1], S[i+2][j-2], S[i+3][j-3]..... is must be "true".
     * dp[i+1][j-1] = S[i+1] == S[j-1] && (S[i+2][j-2], S[i+3][j-3].....)
     * dp[i+1][j-1] = S[i+1] == S[j-1] && dp[i+2][j-2]
    */
    public static String longestPalindromeUsingDp(String s) {
        int n = s.length();
        boolean[][] dp = new boolean[n][n]; // by default -> all values are false

        int start = 0;
        int end = 0;

        for (int i=n-1; i>=0; i--) {
            for (int j=i+1; j<n; j++) {

                if(i==j) { dp[i][j] = true; continue; } // OPTIONAL & NOT NEEDED - single character

                dp[i][j] = s.charAt(i) == s.charAt(j) && (j-i <= 2 || dp[i+1][j-1]);
                if (dp[i][j] && j-i > end-start) { // if bigger palindrome?
                    start = i;
                    end = j;
                }
            }
        }
        return s.substring(start, end+1);
    }








    public static String longestPalindromeUsingDp2(String s) {
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
     * Approach: Expand From Centers
     */
    public static String longestPalindromeUsingExpandFromCenters(String s) {
        String res = "";

        for(int i=0; i<s.length(); i++){
            String str1 = extendPalindrome(s, i, i); // non-dup chars --> not even len odd len case
            String str2 = extendPalindrome(s, i, i+1); // dup chars or just skip the dup chars like while (r < n-1 && ch[r] == ch[r+1]) {r++;} ---> check longestPalindrome2Improved() and extendPalindrome2Improved

            if(str1.length() > res.length()) {
                res = str1;
            }
            if(str2.length() > res.length()) {
                res = str2;
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
        // At this point: [l+1, r) is the correct range
        // --> we as l-- and r++ already happend-> use prev values i.e l+1 and r-1
        return s.substring(l + 1, r);  // r-1 will become r as substring toIndex is explicit
    }










    static int start = 0; // -- only for longestPalindrome()
    static int end = 0; // or use dp = new int[2] instead or just store the maxLen string
    /**
     * Approach: Top-Down DP in palindrome() method
    */
    public static String longestPalindromeUsingDp3(String s) {
        char[] ch = s.toCharArray();
        palindrome(ch, 0); // or use dp = new int[2] array instead of start and end i.e palindrome(ch, 0, dp); --- still it would be the Bottom-Up NoMemory DP
        return s.substring(start, end+1);
    }

    // Top-Down DP -- as we are just storing the start and end index but not all node values in an dedicated array
    private static void palindrome(char[] ch, int i) { // chr[] or s in 1st param
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
        palindrome(ch, i+1); // to traverse each index i.e 0 to n-1
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

        while (
            left >= 0 && right < s.length() && s.charAt(left) == s.charAt(right)
        ) {
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









    public static String longestPalindromeBruteForce(String s) {
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





    public static String longestPalindromeBruteForce2(String s) {
        String res = "";
        int maxLength = 0;
        for(int i=0;i<s.length();i++) {
            for(int j=i+1;j<=s.length();j++) {
                String substr=s.substring(i,j);
                if(checkPalindrome(substr)) {
                    int nl = substr.length();
                    if (nl > maxLength) {
                        maxLength = nl;
                        res = substr;
                    }
                }
            }
        }
        return res;
    }
    public static boolean checkPalindrome(String str) {
        int first=0;
        int last=str.length()-1;
        while(first<last) {
            if(str.charAt(first)!=str.charAt(last)) {
                return false;
            }
            first++;
            last--;
        }
        return true;
    }

}
