package Algorithms.Tries;

import java.util.*;

/**
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 27 April 2026
 * @link 212. Word Search II <a href="https://leetcode.com/problems/word-search-ii/">LeetCode Link</a>
 * @topics Array, String, Backtracking, Trie, Matrix
 * @companies Amazon(2), Zoom(2), Uber(6), Google(4), Meta(2), Microsoft(2), Snowflake(2), Cisco(18), TikTok(10), Two Sigma(6), Wix(5), DoorDash(5), Zoho(4), Bloomberg(3), Airbnb(3), Snap(3), Aurora(3)
 * @see Algorithms.BackTracking.WordSearch
 */
public class WordSearch2 {
    public static void main(String[] args) {
        char[][] board = {{'o','a','a','n'},{'e','t','a','e'},{'i','h','k','r'},{'i','f','l','v'}};
        String[] word = {"oath","pea","eat","rain"};
        System.out.println("findWords1 => " + findWords1(board, word));
    }





    /**
    🧠 Trie Node Design — Key Insight

    Each Trie node contains its own Map<Character, Trie> children;
    This map is local to that node, NOT global 🔥

    🔑 Core Concept
    A Trie node is uniquely identified by its prefix path, not just the character.

    ⚠️ Important Clarification
    Same character ≠ same node
    Nodes are reused only when the prefix path is the same

    🔍 Example
    For words: ["kat", "cat"]
    Trie structure:
    root
    ├── k → a → t (kat)
    └── c → a → t (cat)
    'a' under 'k' ≠ 'a' under 'c'
    't' nodes are also different
    No overwriting happens

    ✅ When Nodes Are Shared
    For: ["car", "cat"]
    root
    └── c → a
            ├── r (car)
            └── t (cat)
    'c' and 'a' are shared (same prefix)
    Split happens only when paths diverge

    So, here the last letter will not override the "String word" value even if the last letter is same in different words
    ---> cause This map is local to the parent node, NOT global for all parents

     */
    static class Trie {
        Map<Character, Trie> children = new HashMap<>();
        String word = null;
    }

    /**
     * @TimeComplexity O(M × N × L × 3^L) or  O(M(4 * 3^(L−1)))
     * @SpaceComplexity O(W × L)


        M × N = board size
        W = number of words
        L = average word length
        K = max possible path length on board (≈ M×N)

     */
    public static List<String> findWords1(char[][] board, String[] words) {
        List<String> result = new ArrayList<>();

        // STEP 1: Construct the Trie using WORDS but not the BOARD ---
        Trie root = new Trie();
        for (String word : words) {
            Trie node = root;
            for (char letter : word.toCharArray()) {
                node = node.children.computeIfAbsent(letter, k -> new Trie());
                // // or
                // if (node.children.containsKey(letter)) {
                //     node = node.children.get(letter);
                // } else {
                //     Trie newNode = new Trie();
                //     node.children.put(letter, newNode);
                //     node = newNode;
                // }``
            }
            node.word = word; // store words in Trie
        }

        // STEP 2: Backtracking starting for each cell in the board ---
        for (int r = 0; r < board.length; r++) {
            for (int c = 0; c < board[r].length; c++) {
                if (root.children.containsKey(board[r][c])) {
                    backtracking(board, r, c, root, result);
                }
            }
        }

        return result;
    }

    private static void backtracking(char[][] board, int r, int c, Trie parentNode, List<String> result) {
        char letter = board[r][c];
        Trie currNode = parentNode.children.get(letter);

        // check if there is any match
        if (currNode.word != null) { // ---> word found
            result.add(currNode.word);
            currNode.word = null; // mark the word by removing it. so that in the next iteration it will not be added again
        }

        // mark the current letter before the EXPLORATION
        board[r][c] = '#';

        // explore neighbor cells in around-clock directions: up, right, down, left
        int[][] dirs = {{0,1},{1,0},{0,-1},{-1,0}};
        for (int[] dir: dirs) {
            int nr = r+dir[0], nc = c+dir[1];
            if (nr < 0 || nr >= board.length || nc < 0 || nc >= board[0].length) continue;
            if (currNode.children.containsKey(board[nr][nc])) backtracking(board, nr, nc, currNode, result);
        }

        // this is the End of EXPLORATION, restore the original letter in the board.
        board[r][c] = letter;

        // Optimization: incrementally remove the leaf nodes
        if (currNode.children.isEmpty()) {
            parentNode.children.remove(letter);
        }
    }












    static class TrieNode {
        TrieNode[] children = new TrieNode[26];
        boolean isWord;

        @Override
        public String toString(){
            return Arrays.toString(children);
        }
    }


    /**
     * @TimeComplexity O(3^(MN))
     * @SpaceComplexity O(3^(MN))
     */
    public static List<String> findWordsTLE1(char[][] board, String[] words) {

        int m = board.length, n = board[0].length;
        TrieNode root = new TrieNode();
        boolean[][] seen =  new boolean[m][n];
        for (int r=0; r<m; r++) {
            for (int c=0; c<n; c++) {
                seen[r][c] = true;
                // System.out.println("start: " + board[r][c]);
                dfs(board, m, n, r, c, seen, root);
                // System.out.println("\n");
                seen[r][c] = false;
            }
        }

        List<String> res = new ArrayList<>();

        for (String word: words) {
            if (search(word, root)) res.add(word);
        }
        return res;
    }

    private static boolean search(String word, TrieNode node) {
        for (char c: word.toCharArray()) {
            int i = c - 'a';
            if (node.children[i] == null) return false;
            node = node.children[i];
        }
        return true;
    }

    private static void dfs(char[][] board, int m, int n, int r, int c, boolean[][] seen, TrieNode node) {
        int i = board[r][c] - 'a';
        if (node.children[i] == null) node.children[i] = new TrieNode();
        node = node.children[i];

        int[][] dirs = {{0,1},{1,0},{0,-1},{-1,0}};
        for (int[] dir: dirs) {
            int nr = r+dir[0], nc = c+dir[1];
            // if (nr>=m || nc>=n || nr<0 || nc<0) return;

            // System.out.printf("nr:%s, nc:%s, nChar:%s, seen:%s\n", nr, nc, board[nr][nc], seen[nr][nc]);

            if (nr>=m || nc>=n || nr<0 || nc<0 || seen[nr][nc]) continue;
            seen[nr][nc] = true;
            dfs(board, m, n, nr, nc, seen, node);
            seen[nr][nc] = false;
        }
    }









    /**
     * @TimeComplexity O(3^(MN) × W × L)
     * @SpaceComplexity O(W × MN)
     */
    public static List<String> findWordsTLE2(char[][] board, String[] words) {
        TrieNode root = new TrieNode();
        List<String> list = new ArrayList<>();
        Set<String> wordSet = new HashSet<>();
        for(String word: words) wordSet.add(word);

        int rows = board.length, cols = board[0].length;
        boolean[][] seen = new boolean[rows][cols];

        for (int r=0; r<rows; r++) {
            for (int c=0; c<cols; c++) {
                dfs(board, r, c, rows, cols, root, seen, wordSet, ""+board[r][c]);
            }
        }

        for (String word: words) {
            if (checkTrie(word, root)) list.add(word);
        }

        return list;
    }

    private static void dfs(char[][] board, int r, int c, int rows, int cols, TrieNode root, boolean[][] seen, Set<String> wordSet, String currWord) {

        TrieNode node = root.children[board[r][c] - 'a'] == null ? new TrieNode() : root.children[board[r][c] - 'a'];
        root.children[board[r][c] - 'a'] = node;

        Set<String> nextSet = new HashSet<>(wordSet); // preserving words - same as seen[][] in backtracking
        List<String> removeItems = new ArrayList<>();
        for (String word: nextSet) {
            if (!word.startsWith(currWord)) removeItems.add(word);
        }
        nextSet.removeAll(removeItems);
        if (nextSet.isEmpty()) return;
        // Mark full word
        if (nextSet.contains(currWord)) {
            node.isWord = true;
        }

        seen[r][c] = true;

        int[][] dirs = { {1,0}, {-1,0}, {0,1}, {0,-1} };
        for (int[] dir: dirs) {
            int i = r + dir[0], j = c + dir[1];
            if (i < 0 || j < 0 || i >= rows || j >= cols || seen[i][j]) continue;

            dfs(board, i, j, rows, cols, node, seen, nextSet, currWord + board[i][j]);
        }

        seen[r][c] = false;
    }

    private static boolean checkTrie(String word, TrieNode root) {
        for (char c : word.toCharArray()) {
            if (root.children[c-'a'] == null) return false;
            root = root.children[c-'a'];
        }
        return true;
    }
}
