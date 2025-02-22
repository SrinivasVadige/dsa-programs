package Algorithms.BackTracking;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 22 Feb 2025
 */
public class WordSearch {
    public static void main(String[] args) {
        char[][] board = {{'A','B','C','E'},{'S','F','C','S'},{'A','D','E','E'}};
        String word = "ABCCED";
        System.out.println("existMyApproach(board, word) => " + existMyApproach(board, word));
        System.out.println("exist(board, word) => " + exist(board, word));
        System.out.println("existMyApproachUsingList(board, word) => " + existMyApproachUsingList(board, word));
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
    public static boolean existMyApproach(char[][] board, String word) {
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
            dfs(board, word, visited, i - 1, j, index + 1) ||
            dfs(board, word, visited, i + 1, j, index + 1) ||
            dfs(board, word, visited, i, j - 1, index + 1) ||
            dfs(board, word, visited, i, j + 1, index + 1);
        visited[i][j] = false; // reset
        return res;
    }





    public static boolean existMyApproachUsingList(char[][] board, String word) {
        // find first letter
        char c = word.charAt(0);
        List<List<Integer>> lst = new ArrayList<>();
        Set<Character> wordSet = new HashSet<>();
        Set<Character> boardSet = new HashSet<>();
        for (int i = 0; i<word.length(); i++) wordSet.add(word.charAt(i));

        for (int k = 0; k<board.length; k++) {
            for (int l = 0; l<board[0].length; l++) {
                boardSet.add(board[k][l]);
                if (board[k][l] == c) {
                    lst.add(new ArrayList<>(Arrays.asList(k,l)));
                }
            }
        }

        for (Character wc: wordSet) if(!boardSet.contains(wc)) return false;

        if (lst.size()==0) return false;

        // start recursion
        boolean bool = false;
        for (List<Integer> subLst: lst) {
            int i = subLst.get(0);
            int j = subLst.get(1);
            bool = backtrack(board, i, j, word, 0, new ArrayList<>());
            if (bool) break;
        }
        return bool;
    }

    private static boolean backtrack(char[][] board, int i, int j, String word, int start, List<String> memo) {  
        int m = board.length;
        int n = board[0].length;
        boolean bool = false;
        if (start == word.length()) return true;
        if (i<0 || i>(m-1) || j<0 || j>(n-1)) return false;
        if (board[i][j] != word.charAt(start)) return false;
        else {
            if (memo.contains(i+","+j)) return false;
            else {
            memo.add(i+","+j);
            int len = memo.size();
            bool = backtrack(board, i, j+1, word, start+1, memo);
            if(memo.size()>len) memo.remove(memo.size()-1);
            bool = bool || backtrack(board, i+1, j, word, start+1, memo);
            if(memo.size()>len) memo.remove(memo.size()-1);
            bool = bool || backtrack(board, i, j-1, word, start+1, memo);
            if(memo.size()>len) memo.remove(memo.size()-1);
            bool = bool || backtrack(board, i-1, j, word, start+1, memo);
            if(memo.size()>len) memo.remove(memo.size()-1);
            }
        }
        return bool;
    }
}
