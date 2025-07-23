package Algorithms.DynamicProgramming;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * <pre>
 *
 * Given two strings word1 and word2, return the minimum number of operations required to convert word1 to word2.
 *
 *  You have the following three operations permitted on a word:
 *
 *  Insert a character
 *  Delete a character
 *  Replace a character
 *
 * Patterns:
 * It is a concept of "Levenshtein distance"
 * Can not change word2
 * One word1 char change at a time
 * word1="plasma" and word2="altruism"
 * and remove the unwanted char by iterating one char from beginning at a time
 * how can we find a matching sub-pattern?? or compare at each operation?
 * we have to reduce or increase the word1 len up-to word2 length
 * n^2 allowed
 * delete and insert changes the word1 length
 * no need to manipulate word1 in real-
 *
 * There are 3 ways to solve this problem:
 * 1. Backtracking - TLE - O(3^n) Time && O(n) Space for recursion stack
 * 2. Top Down Memo DP - same as Backtracking but with memo - O(m * n) && dp[m][n] matrix
 * 3. Bottom Up Tabulation using Levenshtein Distance - O(m * n) && dp[m+1][n+1] matrix
 *
 * </pre>
 *
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 22 Oct 2024
 * @link 72. Edit Distance <a href="https://leetcode.com/problems/edit-distance/">LeetCode link</a>
 * @topics String, Dynamic Programming
 * @companies Amazon, Google, Microsoft, LinkedIn, Swiggy, Meta, Bloomberg, Flipkart, TikTok, Zoho, HashedIn, tcs, Adobe, Oracle, Accenture, Snap, Infosys, Apple, DE Shaw, Arcesium, Rubrik
 * @see Algorithms.DynamicProgramming.LongestCommonSubsequence
 */
public class EditDistance {

    public static void main(String[] args) {

        String word1 = "plasma"; // or "horse"
        String word2 = "altruism"; // or "ros"

        System.out.println("minDistance using Backtracking: " + minDistanceUsingBacktracking(word1, word2));
        System.out.println("minDistance using TopDownMemoDp: " + minDistanceUsingTopDownMemoDp(word1, word2));
        System.out.println("minDistance using LevenshteinDistanceDp: " + minDistanceUsingLevenshteinDistanceBottomUpTabulationDp(word1, word2));
        System.out.println("minDistance using LevenshteinDistance BottomUpTabulationDp Optimized: " + minDistanceUsingLevenshteinDistanceBottomUpTabulationDpOptimized(word1, word2));
    }



    /**
     * @TimeComplexity O(3^n) for 3 operations
     * @SpaceComplexity O(m) for recursion call stack

        ---------------------
        REPLACE -> (i+1, j+1)
        ---------------------

                       i
                       h o r s e
                       r o s
                       j

        let's say we replace 'h' with 'r', then where will the next 'i' and 'j' pointers go ??-------> i+1 and j+1
        here both i and j comparisons are done

                         i
                       r o r s e
                       r o s
                         j


        ---------------------
        DELETE -> (i+1, j)
        ---------------------

                       i
                       h o r s e
                       r o s
                       j

        let's say we deleted 'h' from "horse" i position, but don't delete.... just assume
        only i correction 'h' char is done, but we still need to check j's 'r' char

                         i
                       h o r s e
                       r o s
                       j



        ---------------------
        INSERT -> (i, j+1)
        ---------------------

                       i
                       h o r s e
                       r o s
                       j

        let's say we inserted a new char 'r' in "horse" i position, but don't insert.... just assume
        only j correction 'r' char is done, but we still need to check i's 'h' char

                       i
                     r h o r s e
                       r o s
                         j






     TREE REPRESENTATION OF RECURSIVE CALLS AS BELOW:

                                                                                                                                                                                                                        i
                                                                                                                                                                                                                        h o r s e
                                                                                                                                                                                                                        r o s
                                                                                                                           distance=1                                                                                   j                                                                                         distance=1
                                                                                                                     REPLACE -> (i+1, j+1)                                                               distance=1   DELETE -> (i+1, j)                                                                      INSERT -> (i, j+1)
                                                                                                                                                                                                                           |
                                                                                                                           ________________________________________________________________________________________________|________________________________________________________________________________________________
                                                                                                                           |                                                                                               |                                                                                               |
                                                                                                                           |                                                                                               |                                                                                               |
                                                                                                                         i                                                                                               i                                                                                              i
                                                                                                                       h o r s e                                                                                       h o r s e                                                                                        h o r s e
                                                                                                                       r o s                                                                                           r o s                                                                                            r o s
                                                                                                                         j                                                                                             j                                                                                                  j
                                                                distance=0, as prev w1Char==w2Char                         |                                                                                               |                                  
                                                                ___________________________________________________________|___________________________________________________________                                    |              
                                                               |                                                           |                                                          |                                    |               
                                                               |                                                           |                                                          |                                    |           
                                                               i                                             No operation needed as prev w1Char==w2Char             No operation needed as prev w1Char==w2Char             |               
                                                           h o r s e                                                                                       ________________________________________________________________|________________________________________________________________                                                  
                                                           r o s                                                                                           |                                                               |                                                               |
                                                               j                                                                                           |                                                               |                                                               |   
                   distance=1                      distance=1  |                                    distance=1                                            i                                                                i                                                             i                                                                                              
                   ____________________________________________|____________________________________________                                          h o r s e                                                        h o r s e                                                       h o r s e                                                                        
                   |                                           |                                           |                                          r o s                                                            r o s                                                           r o s                                                                               
                   |                                           |                                           |                                            j                                                              j                                                                 j                                                                        
                     i                                            i                                           i             ________________________________|________________________________                                                                         ✅ Already calculated, no need to calculate again
               h o r s e                                    h o r s e                                   h o r s e           |                               |                               |
               r o s                                        r o s                                       r o s               |                               |                               |
                     j                                          j                                           j                  i                               i                           i
                 IOB ❌                                        |                                          IOB ❌        h o r s e                       h o r s e                      h o r s e
      totalDistance = 2 + charsLeft = 2 + 2 = 4 ✅             |       totalDistance = 2 + charsLeft = 2 + 2 = 4 ✅     r o s                           r o s                          r o s
                                                               |                                                            j                             j                                 j
                                                               |                                                     Already calculated ✅
                   distance=0, as prev w1Char==w2Char          |                                    
                   ____________________________________________|____________________________________________
                   |                                           |                                           |
                   |                                           |                                           |
                       i                     No operation needed as prev w1Char==w2Char     No operation needed as prev w1Char==w2Char             
               h o r s e                                  
               r o s                                      
                     j                                    
                 IOB ❌
       totalDistance = 2 + charsLeft = 2 + 1 = 3 ✅ 
    
    
    


     So, if w1Char==w2Char then no need to do add any distance, just increase both pointers --> i++ and j++
     And we see repeated sub-problems, so we can use memoization

     NOTE: Always maintain the "distanceFromCurrToEnd" in dp 🔥

     */
    public static int minDistanceUsingBacktracking(String word1, String word2) {
        int m = word1.length();
        int n = word2.length();

        return backtrack(word1, word2, m, n, 0, 0, 0);
    }

    private static int backtrack(String word1, String word2, int m, int n, int i, int j, int distance) {
        if(i==m && j==n) { // no chars left
            return distance;
        } else if(i==m && j < n) { // n-j chars left in w2 ---> i.e., n-j insert operations
            return distance + (n-j);
        } else if(i<m && j==n) { // m-i chars left in w1 ---> i.e., m-i delete operations
            return distance + (m-i);
        }


        int totalDistance; // distanceTillNow + distanceFromCurrToEnd
        if (word1.charAt(i) == word2.charAt(j)) {
            totalDistance = backtrack(word1, word2, m, n, i+1, j+1, distance);
        } else {
            int replace = backtrack(word1, word2, m, n, i+1, j+1, distance + 1);
            int delete = backtrack(word1, word2, m, n, i+1, j, distance + 1);
            int insert = backtrack(word1, word2, m, n, i, j+1, distance + 1);
            totalDistance = Math.min(Math.min(replace, delete), insert);
        }

        return totalDistance;
    }












    /**
     * @TimeComplexity O(nm)
     * @SpaceComplexity O(nm)
     * same as above backtracking approach but added memo for repeated sub-problems
     */
    public static int minDistanceUsingTopDownMemoDp(String word1, String word2) {
        int m = word1.length();
        int n = word2.length();
        if(m==0 || n==0) {
            return Math.max(m, n);
        }
        int[][] dp = new int[m][n];
        for(int i=0; i<m; i++) {
            Arrays.fill(dp[i], -1);
        }
        return dfs(word1, word2, m, n, 0, 0, dp, 0);
    }

    private static int dfs(String word1, String word2, int m, int n, int i, int j, int[][] dp, int distance) {
        if(i==m && j==n) { // no chars left
            return distance;
        } else if(i==m && j < n) { // n-j chars left in w2 ---> i.e., n-j insert operations
            return distance + (n-j);
        } else if(i<m && j==n) { // m-i chars left in w1 ---> i.e., m-i delete operations
            return distance + (m-i);
        } else if (dp[i][j] > -1) {
            return distance + dp[i][j]; // distanceTillNow + distanceFromCurrToEnd in memo
        }


        int totalDistance; // distanceTillNow + distanceFromCurrToEnd
        if (word1.charAt(i) == word2.charAt(j)) {
            totalDistance = dfs(word1, word2, m, n, i+1, j+1, dp, distance);
        } else {
            int replace = dfs(word1, word2, m, n, i+1, j+1, dp, distance + 1);
            int delete = dfs(word1, word2, m, n, i+1, j, dp, distance + 1);
            int insert = dfs(word1, word2, m, n, i, j+1, dp, distance + 1);
            totalDistance = Math.min(Math.min(replace, delete), insert);
        }

        dp[i][j] = totalDistance - distance; // memo only the distanceFromCurrToEnd 🔥
        return totalDistance;
    }









    public int minDistanceUsingTopDownMemoDp2(String word1, String word2) {
        int m = word1.length();
        int n = word2.length();
        if (m == 0 || n == 0) {
            return Math.max(m, n);
        }
        int[][] dp = new int[m][n];
        for (int i = 0; i < m; i++) {
            Arrays.fill(dp[i], -1);
        }
        return dfs(word1, word2, m, n, 0, 0, dp);
    }

    private int dfs(String word1, String word2, int m, int n, int i, int j, int[][] dp) {
        if (i == m) return n-j;
        if (j == n) return m-i;

        if (dp[i][j] != -1) return dp[i][j];

        if (word1.charAt(i) == word2.charAt(j)) {
            dp[i][j] = dfs(word1, word2, m, n, i+1, j+1, dp);
        } else { // 1+ dfs() --> here +1 is for curr operation
            int replace = 1 + dfs(word1, word2, m, n, i+1, j+1, dp);
            int delete = 1 + dfs(word1, word2, m, n, i+1, j, dp);
            int insert = 1 + dfs(word1, word2, m, n, i, j+1, dp);
            dp[i][j] = Math.min(Math.min(replace, delete), insert);
        }

        return dp[i][j];
    }







    public static int minDistanceUsingTopDownMemoDp3(String word1, String word2) {
        int n=word1.length();
        int m=word2.length();
        int[][] dp=new int[n][m];
        for(int[] r:dp)
            Arrays.fill(r,-1);
        return rec(word1,word2,n-1,m-1,dp);
    }
    public static int rec(String word1,String word2,int i,int j,int[][] dp) {
        if(i<0) return j+1;
        if(j<0) return i+1;
        if(dp[i][j]!=-1)
            return dp[i][j];
        if(word1.charAt(i)==word2.charAt(j))
            return dp[i][j]=rec(word1,word2,i-1,j-1,dp);
        else
            return dp[i][j]=1+Math.min( rec(word1,word2,i-1,j-1,dp),Math.min(rec(word1,word2,i-1,j,dp),rec(word1,word2,i,j-1,dp)) );
    }








    /**
     * see  {@link #minDistanceUsingBacktracking} for better understanding of "RECURRENCE RELATION" i.e., dp[i][j] = 1 + Math.min(dp[i-1][j-1], Math.min(dp[i-1][j], dp[i][j-1]))
     * <>
     * this recurrence equation is same like "Longest common subsequence" {@link Algorithms.DynamicProgramming.LongestCommonSubsequence#longestCommonSubsequenceBottomUpTabulationDp}
     *
     * <pre>
     * "Levenshtein Distance Algorithm" is the foundation of many search algorithms - also known as Edit Distance
     * In many APIs it is referred as Fuzziness(n)
     *
     * The Levenshtein Distance algorithm is a measure of the minimum number of single-character edits (insertions, deletions, or substitutions) required to change one word into another.
     * It is a widely used algorithm in natural language processing, spell checking, and data compression.
     * Example:
     * Distance between "Felipe" -> "Felipe" = 0
     * Distance between "Felipe" -> "Felixe" = 1
     * Distance between "Felipe" -> "Felixi" = 2
     *
     * NOTE: There is no "move" operation in LevenshteinDistance
     *
     * Using Levenshtein Distance: "STAR" -> "TSAR" = 2
     * But using Demerau-Levenshtein Distance: "STAR" -> "TSAR" = 1 -- i.e transpositions of adjacent chars i.e we move T forward
     * Demerau-Levenshtein Distance is the algorithm we use in "Edit Distance"
     *
     * The trick is that we do 2 searches
     * 1. to check target char is present in source string
     * 2. with fuzziness proportional to the size of the word .match("the").fuzziness(0); .match("fault").fuzziness(1); .match("abstract").fuzziness(2);
     *
     * HOW IT WORKS:
     * -------------
     * The algorithm works by creating a matrix to store the distances between the characters of the two input strings
     * The matrix is filled in row by row, starting from the top-left corner
     * Each cell in the matrix represents the minimum number of edits required to transform the substring of the first string into the substring of the second string
     * Then the last cell in the matrix will represents the minimum number of edits required to transform the first string into the second string
     *
     *
     * Here is a step-by-step explanation of the algorithm:
     * 1. Create a matrix: Create a matrix with (m+1) x (n+1) dimensions, where m and n are the lengths of the two input strings.
     * 2. Initialize the matrix: Initialize the first row and first column of the matrix with incremental values, starting from 0. This represents the number of edits required to transform an empty string into the corresponding substring of the other string.
     * 3. Fill in the matrix: Iterate through the matrix, starting from the top-left corner. For each cell, calculate the minimum number of edits required to transform the substring of the first string into the substring of the second string. This is done by considering three operations:
     * - Insertion: Insert a character from the second string into the first string.
     * - Deletion: Delete a character from the first string.
     * - Substitution / Replace: Replace a character in the first string with a character from the second string.
     * 4. Calculate the minimum distance: The minimum distance between the two strings is stored in the bottom-right cell of the matrix.
     *
     * Example:
     * Suppose we want to calculate the Levenshtein distance between the strings "kitten" and "sitting".
     *
     * in m+1 and n+1 => we have +1 because of dummy row and column "" i.e number of operations in one string to achieve "" because m can be empty and n can be empty
     * Here, we have 2 operations when "" in row "i" in column that means => "si" i.e 2 minimum operations required to achieve "si" to become ""
     *               ↓
     * |   | ""| s | i | t | t | i | n | g |
     * | ""| 0 | 1 |(2)| 3 | 4 | 5 | 6 | 7 |
     * | k | 1 | 1 | 2 | 3 | 4 | 5 | 6 | 7 |
     * | i | 2 | 2 | 1 | 2 | 3 | 4 | 5 | 6 |
     * | t | 3 | 3 | 2 | 1 | 2 | 3 | 4 | 5 |
     * | t | 4 | 4 | 3 | 2 | 1 | 2 | 3 | 4 |
     * | e | 5 | 5 | 4 | 3 | 2 | 2 | 3 | 4 |
     * | n | 6 | 6 | 5 | 4 | 3 | 3 | 2 |(3)|
     *
     * w1 in rows & w2 in columns & extra rol and col for "" i.e dummy or no char
     * dp[i][j] = min number of operations to convert word1[0 to i-1] → word2[0 to j-1]
     * dp[2][3] = 2 --> dp[2 chars in w1][3 chars in w2] --> dp["ki"]["sit"] ---> this means, the number of operations needed to convert "ki" to "sit"
     *
     * PATTERNS:
     * 1. If you see the 1st whole row & the 1st whole column, it's just the increasing sequence of numbers which are independent of any kind of string
     * 2. Now, we see a square box pattern with 4 sub-boxes one on each corner and all boxes are already filled except for the bottom-right box. So we have to fill the bottom-right cell of the matrix in the current operation
     *
     *          j-1  j
     *         +---+---+
     *     i-1 | a | b |   ← top row
     *         +---+---+
     *      i  | c | ❓|   ← curr row
     *         +---+---+
     *
     * 3. Now check if the current_m_string_char(eg: "s") is same as the current_target_n_string_char(eg: "k").
     * 4. If it is same, just fill the bottom-right current cell with minimum of three cells otherwise fill it with the minimum of three cells + 1
     * 5. There is also another pattern if current_m_char == current_n_char then bottom-right cell will always be top-left call as the previous sub-strings i.e m-1 to n-1 operations are already done
     *
     * For your understanding, manually count and fill the cells with min operations and look at the pattern.
     *
     * So, finally the minimum distance between the two strings is 3, which is stored in the bottom-right cell of the matrix.
     * This implementation uses dynamic programming to fill in the matrix and calculate the minimum distance between the two input strings.
     *
     *
     * HOW TO COME UP WITH THIS RECURSION EQUATION?
     * --------------------------------------------
     *
     *                            i
     *                   0    1   2   3
     *         w1=>      ''   a   b   d
     *         w2=>      ''   a   c   d
     *                   0    1   2   3
     *                            j
     *
     * Case 1: Last characters are equal ---> dp[i][j] = dp[i-1][j-1]
     * Case 2: Last characters are not equal
     *         - Insertion: dp[i][j] = dp[i][j-1] + 1 -----> when i=j=2, if we insert "c" in 1 "abd" i,e 1 operation. Total operations = 1 + num of prev operations to convert w1 "a" to w2 "a"
     *         - Deletion:  dp[i][j] = dp[i-1][j] + 1
     *         - Replace:   dp[i][j] = dp[i-1][j-1] + 1
     *
     * </pre>
     *
     * @TimeComplexity O(mn)
     * @SpaceComplexity O(mn)
     */
    public static int minDistanceUsingLevenshteinDistanceBottomUpTabulationDp(String word1, String word2) {
        int m = word1.length();
        int n = word2.length();
        int[][] dp = new int[m + 1][n + 1];
        for (int i = 0; i <= m; i++) {
            for (int j = 0; j <= n; j++) {
                if (i == 0) {
                    dp[i][j] = j;
                } else if (j == 0) {
                    dp[i][j] = i;
                } else if (word1.charAt(i-1) == word2.charAt(j-1)) {
                    dp[i][j] = dp[i-1][j-1];
                } else {
                    dp[i][j] = 1 + Math.min(dp[i-1][j-1], Math.min(dp[i-1][j], dp[i][j-1]));
                    // RECURRENCE RELATION for insert, delete, replace -- to understand this check the #minDistanceUsingBacktracking() documentation
                }

                /*
                    [a] [b]
                    [c] [?]
                 */
            }
        }
        return dp[m][n];
    }


    /**
     * @TimeComplexity O(mn)
     * @SpaceComplexity O(n) --- Optimized from O(mn) space to O(n) space

        Just replace
        dp[i-1] with prev
        dp[i] with curr

        for (int i = 0; i <= m; i++) dp[i][0] = i; // 1st whole row
        for (int j = 0; j <= n; j++) dp[0][j] = j; // 1st whole col

        whenever we have conditions like above for loop
        ---> we need one prev for topRow values
        ---> we can use curr for left side value

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


     Example:
        word1 = "kitten"
        word2 = "sitting"

     Base Initialization (prev[]) → word1 = "", word2 = "sitting"
        prev[] = [0, 1, 2, 3, 4, 5, 6, 7]   // "" vs s-i-t-t-i-n-g

     Now for each character of "kitten", we build curr[] and then swap
     🔁 i = 1 → word1[0] = 'k' -> curr[] = [1, 1, 2, 3, 4, 5, 6, 7]   // "k" vs s-i-t-t-i-n-g
     🔁 i = 2 → word1[1] = 'i' -> curr[] = [2, 2, 1, 2, 3, 4, 5, 6]
     🔁 i = 3 → word1[2] = 't' -> curr[] = [3, 3, 2, 1, 2, 3, 4, 5]
     🔁 i = 4 → word1[3] = 't' -> curr[] = [4, 4, 3, 2, 1, 2, 3, 4]
     🔁 i = 5 → word1[4] = 'e' -> curr[] = [5, 5, 4, 3, 2, 2, 3, 4]
     🔁 i = 6 → word1[5] = 'n' -> curr[] = [6, 6, 5, 4, 3, 3, 2, 3]

     Final Answer:
        prev[n] → prev[7] = 3

          ""  s  i  t  t  i  n  g
    "" →   0  1  2  3  4  5  6  7
    k  →   1  1  2  3  4  5  6  7
    i  →   2  2  1  2  3  4  5  6
    t  →   3  3  2  1  2  3  4  5
    t  →   4  4  3  2  1  2  3  4
    e  →   5  5  4  3  2  2  3  4
    n  →   6  6  5  4  3  3  2  3

     */
    public static int minDistanceUsingLevenshteinDistanceBottomUpTabulationDpOptimized(String word1, String word2) {
        int m = word1.length(), n = word2.length();

        int[] prev = new int[n + 1]; // topRow
        int[] curr = new int[n + 1]; // currRow

        for (int j = 0; j <= n; j++) { // Initialize - BASE CASE: converting "" to word2
            prev[j] = j;
        }

        for (int i = 1; i <= m; i++) { // we already have the topRow/prev -> start from (1,1) instead of (0,0)
            curr[0] = i; // BASE CASE: converting word1 to "" --> for (int i = 0; i <= m; i++) dp[i][0] = i;
            for (int j = 1; j <= n; j++) {
                if (word1.charAt(i - 1) == word2.charAt(j - 1)) {
                    curr[j] = prev[j-1];
                } else {
                    curr[j] = 1 + Math.min( prev[j-1], Math.min(prev[j], curr[j-1]) );
                }
                /*
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
            }
            // Swap rows
            int[] temp = prev;
            prev = curr;
            curr = temp;
        }

        return prev[n];
    }









    public static void minDistanceUsingLevenshteinDistanceBottomUpTabulationDp2(String s1, String s2) {
        int m = s1.length();
        int n = s2.length();
        int[][] dp = new int[m + 1][n + 1];
        for (int i = 0; i <= m; i++) { // 1st whole row
            dp[i][0] = i;
        }
        for (int j = 0; j <= n; j++) { // 1st whole col
            dp[0][j] = j;
        }
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                int cost = (s1.charAt(i - 1) == s2.charAt(j - 1)) ? 0 : 1; // isSameChar?
                dp[i][j] = Math.min(  Math.min( dp[i-1][j]+1, dp[i][j-1]+1 ), dp[i-1][j-1] + cost  );
                // or
                // dp[i][j] = Math.min(  Math.min( dp[i-1][j], dp[i][j-1] ), dp[i-1][j-1]  ) + cost;
            }
        }
        System.out.println("levenshteinDistance: " + dp[m][n]);
    }
























    /**
     * My Approach is converting the the source to target but not it's the minimum distance
     * Failed at word1="plasma" and word2="altruism" because we removed 'm' char and added it later
     */
    public static int minDistanceMyApproachOldNotWorking(String word1, String word2) {
        if(word1.isEmpty() && word2.isEmpty()) return 0;
        int count = 0;
        if(word1.length()==1 && word1.length()==word2.length()) return word1.equals(word2)?0:1;

        if(word1.isEmpty()){
            word1 += word2.charAt(0);
            count++;
            return count;
        }

        for(int i=0, j=0; i<Math.max(word1.length(), word2.length()); i++) {
            // System.out.println(word1);
            char c1 = i<word1.length()? word1.charAt(i):'\0';
            char c2 = j<word2.length()? word2.charAt(j):'\0';
            // IS CURR CHAR SAME?
            if (c1 == c2) {
                // System.out.println(word1 + ", " + word2 +  ", i:" + i
                // + ", c1:" + c1 + ",  j:" + j + ", c2:" + c2
                // + ", count:"+count);
                j++;
                continue;
            }

            // IS NEXT CHAR SAME in word1? then REPLACE
            // maintain length as per i and word2
            String midChar = "";
            if(c2!='\u0000' && i + 1 < word1.length() && c2!=word1.charAt(i+1)
            || c2!='\u0000' && i + 1 == word1.length()
            || c1=='\u0000' && c2!='\u0000'
            ){
                midChar=c2+"";
            }

            // IS NEXT CHAR SAME in word2? then ADD c2 in word1
            if(c2!='\u0000' && j + 1 < word2.length() && (c1==word2.charAt(j+1)) ){
                midChar=c2+""+c1;
            }

            word1 = word1.substring(0, i) + midChar + ( i+1<word1.length()? word1.substring(i+1):"" );
            count++;
            // System.out.println(word1 + ", " + word2 +  ", i:" + i
            // + ", c1:" + c1 + ",  j:" + j + ", c2:" + c2
            // + ", count:"+count);

            if (midChar.isEmpty()) // deleted the char
                i--;
            else j++;
        }
        return count;
    }


    /**
     * Failed at word1="plasma" and word2="altruism" because we removed 'm' char and added it later
     * so, use hashmap with decrease counter and compare with >0
    */

    public static int minDistanceMyApproachOld2NotWorking(String word1, String word2) {
        if(word1.isEmpty() && word2.isEmpty()) return 0;
        int count = 0;
        if(word1.length()==1 && word1.length()==word2.length()) return word1.equals(word2)?0:1;

        if(word1.isEmpty()){
            word1 += word2.charAt(0);
            count++;
        }

        Map<Character, Integer> map = new HashMap<>();

        for(int i=0; i< word2.length(); i++){
            map.put(word2.charAt(i), i);
        }

        for(int i=0, j=0; i<Math.max(word1.length(), word2.length()); i++) {
            System.out.println(word1);
            char c1 = i<word1.length()? word1.charAt(i):'\0';
            char c2 = j<word2.length()? word2.charAt(j):'\0';

            if (c1 == c2) {
                // System.out.println(word1 + ", " + word2 +  ", i:" + i
                // + ", c1:" + c1 + ",  j:" + j + ", c2:" + c2
                // + ", count:"+count);
                j++;
                continue;
            }


            // maintain length as per i and word2
            String midChar = "";

            // replace
            if(c2!='\u0000' && i + 1 < word1.length() && c2!=word1.charAt(i+1)
            || c2!='\u0000' && i + 1 == word1.length()
            || c1=='\u0000' && c2!='\u0000'
            ){
                midChar=c2+"";
            }
            // insert c1==word2.charAt(j+1)
            if(c2!='\u0000' && j + 1 < word2.length() && map.getOrDefault(c1, 0) > i

            ){
                midChar=c2+""+c1;
            }
            word1 = word1.substring(0, i) + midChar + ( i+1<word1.length()? word1.substring(i+1):"" );
            count++;
            // System.out.println(word1 + ", " + word2 +  ", i:" + i
            // + ", c1:" + c1 + ",  j:" + j + ", c2:" + c2
            // + ", count:"+count);

            if (midChar.isEmpty()) // deleted the char
                i--;
            else j++;
        }
        return count;
    }
}