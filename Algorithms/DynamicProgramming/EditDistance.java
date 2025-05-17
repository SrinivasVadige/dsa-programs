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
 * no need to manipulate word1 in real-time
 *
*  Thoughts:
 * definitely bottom-up tabulation or 2 pointer
 * 1st step HashMap both words
 * </pre>
 *
 * @see "Longest common subsequence"
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 22 Oct 2024
 */
public class EditDistance {

    public static void main(String[] args) {

        String word1 = "plasma"; // or "horse"
        String word2 = "altruism"; // or "ros"

        levenshteinDistance(word1, word2);
        System.out.println("minDistanceBottomUpTabulation: " + minDistanceBottomUpTabulation(word1, word2));
        System.out.println("minDistanceTopDownMemoDp: " + minDistanceTopDownMemoDp(word1, word2));
        System.out.println("minDistanceMyApproach: " + minDistanceMyApproach(word1, word2));
    }

    /**
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
     *
     * Using Levenshtein Distance: "STAR" -> "TSAR" = 2
     * But using Demerau-Levenshtein Distance: "STAR" -> "TSAR" = 1 -- i.e transpositions of adjacent chars i.e we move T forward
     * Demerau-Levenshtein Distance is the algorithm we use in "Edit Distance"
     * The trick is that we do 2 searches 1. to check target char is present in source string 2. with fuzziness proportional to the size of the word .match("the").fuzziness(0); .match("fault").fuzziness(1); .match("abstract").fuzziness(2);
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
     * 2. Initialize the matrix: Initialize the first row and column of the matrix with incremental values, starting from 0. This represents the number of edits required to transform an empty string into the corresponding substring of the other string.
     * 3. Fill in the matrix: Iterate through the matrix, starting from the top-left corner. For each cell, calculate the minimum number of edits required to transform the substring of the first string into the substring of the second string. This is done by considering three operations:
     * - Insertion: Insert a character from the second string into the first string.
     * - Deletion: Delete a character from the first string.
     * - Substitution: Replace a character in the first string with a character from the second string.
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
     *     0   1
     *     1  (X)
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
     * @TimeComplexity: O(mn)
     * @SpaceComplexity: O(mn)
     */
    public static int minDistanceBottomUpTabulation(String word1, String word2) {
        int[][] dp = new int[word1.length() + 1][word2.length() + 1];
        for (int i = 0; i <= word1.length(); i++) {
            for (int j = 0; j <= word2.length(); j++) {
                if (i == 0) {
                    dp[i][j] = j;
                } else if (j == 0) {
                    dp[i][j] = i;
                } else if (word1.charAt(i - 1) == word2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1];
                } else {
                    dp[i][j] = 1 + Math.min(dp[i - 1][j - 1], Math.min(dp[i - 1][j], dp[i][j - 1])); // RECURRENCE RELATION for insert, delete, replace
                }
            }
        }
        return dp[word1.length()][word2.length()];
    }

    /**
     * @TimeComplexity: O(nm)
     * @SpaceComplexity: O(nm)
     */
    public static int minDistanceTopDownMemoDp(String word1, String word2) {
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





    public static void levenshteinDistance(String s1, String s2) {
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

    public static void levenshteinDistance2(String s1, String s2) {
        int[][] dp = new int[s1.length() + 1][s2.length() + 1];
        for (int i = 0; i <= s1.length(); i++) {
            for (int j = 0; j <= s2.length(); j++) {
                if (i == 0) {
                    dp[i][j] = j;
                } else if (j == 0) {
                    dp[i][j] = i;
                } else if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1];
                } else {
                    dp[i][j] = 1 + Math.min(dp[i - 1][j - 1], Math.min(dp[i - 1][j], dp[i][j - 1]));
                }
            }
        }
        System.out.println("levenshteinDistance2: " + dp[s1.length()][s2.length()]);
    }

    /**
     * My Approach is converting the the source to target but not it's the minimum distance
     * Failed at word1="plasma" and word2="altruism" because we removed 'm' char and added it later
     */
    public static int minDistanceMyApproach(String word1, String word2) {
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

    public static int minDistanceMyApproach2(String word1, String word2) {
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