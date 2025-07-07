package Algorithms.BackTracking;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 22 Feb 2025
 * @link 79. Word Search <a href="https://leetcode.com/problems/word-search/">LeetCode link</a>
 * @topics Array, Backtracking, Matrix, DFS, String
 * @companies Amazon, PayPal, Google, Bloomberg, Uber, Karat, Microsoft, Faire, Meta, Netflix, Atlassian, Walmart, Goldman, Arista, Salesforce, Grammarly, TikTok, Oracle, Apple, Adobe, Snap, Zoho, Epic, Capital, Wix, Cisco
 */
public class WordSearch {
    public static void main(String[] args) {
        char[][] board = {{'A','B','C','E'},{'S','F','C','S'},{'A','D','E','E'}};
        String word = "ABCCED";
        System.out.println("exist => " + exist(board, word));
        System.out.println("exist MyApproach New => " + existMyApproachNew(board, word));
        System.out.println("exist MyApproach Old => " + existMyApproachOld(board, word));
    }

    public static boolean exist(char[][] board, String word) {
        int m = board.length;
        int n = board[0].length;
        boolean[][] visited = new boolean[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (board[i][j] == word.charAt(0) && dfs(board, word, visited, i, j, 0)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean dfs(char[][] board, String word, boolean[][] visited, int i, int j, int index) {
        if (index == word.length()) return true;
        if (
            i < 0 || j < 0 || i >= board.length || j >= board[0].length ||
            visited[i][j] ||
            board[i][j] != word.charAt(index)
        ) return false;

        visited[i][j] = true;
        boolean res =
            dfs(board, word, visited, i - 1, j, index + 1) || // Inlining is JVM-friendly in version 1
            dfs(board, word, visited, i + 1, j, index + 1) ||
            dfs(board, word, visited, i, j - 1, index + 1) ||
            dfs(board, word, visited, i, j + 1, index + 1);
        visited[i][j] = false; // reset
        return res;
    }







    public static boolean existMyApproachNew(char[][] board, String word) {
        int rows = board.length;
        int cols = board[0].length;
        int c1 = word.charAt(0);
        for(int r=0; r<rows; r++) {
            for(int c=0; c<cols; c++) {
                if(c1 == board[r][c]) {
                    if(backtrack(board, r, c, word, 0)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private static boolean backtrack(char[][] board, int r, int c, String word, int i) {
        if(i==word.length()) {
            return true;
        }

        int rows = board.length;
        int cols = board[0].length;
        if(r<0 || r>=rows || c<0 || c>=cols || word.charAt(i)!=board[r][c]) {
            return false;
        }
        board[r][c] = '#'; // instead of seen[][] or visited[][] boolean values -> just modify and revert back board[r][c] = word.charAt(i);

        int[][] dirs = new int[][]{{1,0}, {0,1}, {-1,0}, {0,-1}}; // Redundant allocation of dirs[][]
        for(int[] dir: dirs) { // Loop overhead + array access
            if( backtrack(board, r+dir[0], c+dir[1], word, i+1) ) {
                return true;
            }
        }
        board[r][c] = word.charAt(i); // ✅ reset seen -> dfs only after all directions failed
        return false;
    }











    /**
        THOUGHTS:
        ---------
        1) First find the first char in word in board
        2) Then check right, down, left, top
        3) When ABA case, it's better to maintain a memo to cache that we don't traverse the same A again.
        4) But need 4 children for very node
        5) When board = [["A","B","C","E"],["S","F","C","S"],["A","D","E","E"]], word = "ABCCED"
        ["A","B","C","E"]
        ["S","F","C","S"]
        ["A","D","E","E"]

                                                        "A"
                                     ____________________|____________________
                                    |             |              |            |
                                  r"AB"          d""            l""          t""
                  __________________|______________________
                 |             |              |            |
                r"ABC"       d"ABF"         l"ABA"      t"AB*"
                              ❌            ❌           ❌

        6) So, we need 4 backtrack() methods in word for loop
     */
    public static boolean existMyApproachOld(char[][] board, String word) {
        // find first letter
        char c = word.charAt(0);
        List<List<Integer>> lst = new ArrayList<>();
        Set<Character> wordSet = new HashSet<>();
        Set<Character> boardSet = new HashSet<>();
        for (int i = 0; i<word.length(); i++) wordSet.add(word.charAt(i));

        for (int k = 0; k<board.length; k++) {
            for (int l = 0; l<board[0].length; l++) {
                boardSet.add(board[k][l]);
                if (board[k][l] == c) lst.add(new ArrayList<>(Arrays.asList(k,l)));
            }
        }

        // to check if all chars in word are in board or not
        for (Character wc: wordSet) if(!boardSet.contains(wc)) return false;

        // to check if we found first char of word in board
        if (lst.size()==0) return false;

        // start recursion
        boolean bool = false;
        for (List<Integer> subLst: lst) {
            int i = subLst.get(0);
            int j = subLst.get(1);
            bool = backtrack(board, i, j, word, 0, new HashSet<>());
            if (bool) break;
        }
        return bool;
    }

    private static boolean backtrack(char[][] board, int i, int j, String word, int start, Set<String> memo) {
        if (start == word.length()) return true;
        int m = board.length;
        int n = board[0].length;
        boolean bool = true;
        if (i<0 || i>(m-1) || j<0 || j>(n-1)) return false;
        if (board[i][j] != word.charAt(start)) return false;
        else {
            if (memo.contains(i+","+j)) return false;
            else {
                memo.add(i+","+j);
                bool =
                backtrack(board, i, j+1, word, start+1, memo) || // right
                backtrack(board, i+1, j, word, start+1, memo) || // down
                backtrack(board, i, j-1, word, start+1, memo) || // left
                backtrack(board, i-1, j, word, start+1, memo);   // top
                memo.remove(i+","+j); // or backtrack(board, i, j, word, start+1, new HashSet<>(memo) ) --- but memo.remove() is more efficient
            }
        }
        return bool;
    }


}
