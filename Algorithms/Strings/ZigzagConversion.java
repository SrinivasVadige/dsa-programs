package Algorithms.Strings;

/**
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 20 June 2025
 * @link 6. Zigzag Conversion <a href="https://leetcode.com/problems/zigzag-conversion/">LeetCode link</a>
 * @topics String, Array, Simulation
 * Given s = "PAYPALISHIRING", numRows = 3
 * Return "PAHNAPLSIIGYIR"
    P   A   H   N
    A P L S I I G
    Y   I   R
 *
 */
public class ZigzagConversion {
    public static void main(String[] args) {
        String s = "PAYPALISHIRING"; // paypal is hiring
        int numRows = 3;
        System.out.println(convert(s, numRows));
    }


    /**

    Given s = "PAYPALISHIRING", numRows = 3

        P   A   H   N
        A P L S I I G
        Y   I   R

        n*m --> 2d array?
        ---------------
        |P| |A| |H| |N|
        ---------------
        |A|P|L|S|I|I|G|
        --------------
        |Y| |I| |R| | |
        ---------------
     */
    public String convertMyApproach(String s, int numRows) {
        int n = s.length(), m = numRows;
        if(m==1) {
            return s;
        }

        char[][] dp = new char[m][n];

        int i=0;
        int row = 0, col=0;
        while(i<n) {
            // down
            for(;row<m && i<n; row++, i++) {
                dp[row][col] = s.charAt(i);
            }
            row-=2;
            col++;

            // up-right diagonal
            for(; row>=0 && i<n; row--, col++, i++) {
                dp[row][col] = s.charAt(i);
            }
            col--;
            row+=2;
        }
        StringBuilder sBuilder = new StringBuilder();
        for(int r = 0; r<m; r++) {
            for(int c=0; c<n; c++) {
                if(dp[r][c] != 0) { // or dp[r][c] != '\u0000'
                    sBuilder.append(dp[r][c]);
                }
            }
        }
        return sBuilder.toString();
    }




    public static String convert(String s, int numRows) {
        if (numRows == 1) {
            return s;
        }

        StringBuilder res = new StringBuilder();

        for (int row = 0; row < numRows; row++) { // each row
            int increment = 2 * (numRows - 1); // The full cycle length
            for (int i=row; i < s.length(); i += increment) {
                res.append(s.charAt(i)); // Add the character at the current row

                if (row > 0 && row < numRows-1 && (i+increment - 2 * row) < s.length()) {
                    // Add the character in the middle of the zigzag
                    res.append(s.charAt(i + increment - 2 * row));
                }
            }
        }
        return res.toString();
    }









    public static String convert2(String s, int numRows) {
        if (numRows == 1 || numRows >= s.length()) {
            return s; // No zigzag conversion needed
        }

        StringBuilder[] rows = new StringBuilder[numRows]; // or new StringBuilder[Math.min(numRows, s.length())]
        for (int i = 0; i < rows.length; i++) {
            rows[i] = new StringBuilder();
        }

        int currentRow = 0;
        boolean goingDown = false;

        for (char c : s.toCharArray()) {
            rows[currentRow].append(c);
            if (currentRow == 0) {
                goingDown = true; // Start going down
            } else if (currentRow == numRows - 1) {
                goingDown = false; // Start going up
            }
            currentRow += goingDown ? 1 : -1; // Move to the next row
        }

        StringBuilder result = new StringBuilder();
        for (StringBuilder row : rows) {
            result.append(row);
        }

        return result.toString();
    }

}
