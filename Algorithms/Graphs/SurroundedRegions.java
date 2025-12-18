package Algorithms.Graphs;

import java.util.*;

/**
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 17 December 2025
 * @link 130. Surrounded Regions <a href='https://leetcode.com/problems/surrounded-regions/'>LeetCode Link</a>
 * @topics Array, Matrix, DFS, BFS, Union Find
 * @companies Google(5), Bloomberg(3), Meta(2), Amazon(2), Oracle(3), Microsoft(2), Adobe(4), TikTok(3), Flipkart(2), Goldman Sachs(2), Uber(2), Nutanix(2)
 */
public class SurroundedRegions {
    public static void main(String[] args) {
        char[][] board;


        /*
            {'O','X','X','O','X'},
            {'X','O','O','X','O'},
            {'X','O','X','O','X'},
            {'O','X','O','O','O'},
            {'X','X','O','X','O'}

             this will become

            {'O','X','X','O','X'},
            {'X',❌, ❌,'X','O'},
            {'X',❌, 'X','O','X'},
            {'O','X','O','O','O'},
            {'X','X','O','X','O'}

            Os connected with border Os are also border Os. And O's connected with this new border Os are also border Os. And so on...
            we should only conquer the finalized non-border 'O's

         */

        System.out.println("Using DFS 1");
        board = new char[][] {{'O','X','X','O','X'},{'X','O','O','X','O'},{'X','O','X','O','X'},{'O','X','O','O','O'},{'X','X','O','X','O'}};
        solveUsingDfs1(board);
        System.out.println(Arrays.deepToString(board));

        System.out.println("Using DFS 2");
        board = new char[][] {{'O','X','X','O','X'},{'X','O','O','X','O'},{'X','O','X','O','X'},{'O','X','O','O','O'},{'X','X','O','X','O'}};
        solveUsingDfs2(board);
        System.out.println(Arrays.deepToString(board));

        System.out.println("Using BFS");
        board = new char[][] {{'O','X','X','O','X'},{'X','O','O','X','O'},{'X','O','X','O','X'},{'O','X','O','O','O'},{'X','X','O','X','O'}};
        solveUsingBfs(board);
        System.out.println(Arrays.deepToString(board));

        System.out.println("Using Union Find");
        board = new char[][] {{'O','X','X','O','X'},{'X','O','O','X','O'},{'X','O','X','O','X'},{'O','X','O','O','O'},{'X','X','O','X','O'}};
        solveUsingUnionFind(board);
        System.out.println(Arrays.deepToString(board));
    }




    public static void solveUsingDfs1(char[][] board) {
        Set<Integer> border = new HashSet<>();
        int[][] dirs = { { 1, 0 }, { 0, 1 }, { -1, 0 }, { 0, -1 } };
        int rows = board.length, cols = board[0].length;

        for (int i = 0; i < Math.max(rows, cols); i++) {
            if (i < rows && board[i][0] == 'O')
                border.add(i * cols);
            if (i < cols && board[0][i] == 'O')
                border.add(i);
            if (i < rows && board[i][cols - 1] == 'O')
                border.add(i * cols + cols - 1);
            if (i < cols && board[rows - 1][i] == 'O')
                border.add((rows - 1) * cols + i);
        }

        for (int x : new ArrayList<>(border)) {
            dfs(board, x / cols, x % cols, dirs, border);
        }

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                if (board[r][c] == 'O' && !border.contains(r * cols + c)) {
                    board[r][c] = 'X';
                }
            }
        }
    }

    private static void dfs(char[][] board, int r, int c, int[][] dirs, Set<Integer> border) {
        int rows = board.length, cols = board[0].length;

        for (int[] dir : dirs) {
            int nr = r + dir[0], nc = c + dir[1];
            if (nr < 0 || nc < 0 || nr >= rows || nc >= cols || board[nr][nc] == 'X' || border.contains(nr * cols + nc))
                continue;

            border.add(nr * cols + nc);
            dfs(board, nr, nc, dirs, border);
        }
    }







    public static void solveUsingDfs2(char[][] board) {
        int rows = board.length, cols = board[0].length;

        for (int r = 0; r < rows; r++) {
            dfs(board, r, 0);
            dfs(board, r, cols - 1);
        }

        for (int c = 0; c < cols; c++) {
            dfs(board, 0, c);
            dfs(board, rows - 1, c);
        }

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                if (board[r][c] == 'O')
                    board[r][c] = 'X';
                else if (board[r][c] == '#')
                    board[r][c] = 'O';
            }
        }
    }

    private static void dfs(char[][] board, int r, int c) {
        int rows = board.length, cols = board[0].length;

        if (r < 0 || c < 0 || r >= rows || c >= cols || board[r][c] != 'O')
            return;

        board[r][c] = '#';

        dfs(board, r + 1, c);
        dfs(board, r - 1, c);
        dfs(board, r, c + 1);
        dfs(board, r, c - 1);
    }









    public static void solveUsingBfs(char[][] board) {
        int rows = board.length, cols = board[0].length;
        Queue<int[]> q = new LinkedList<>();

        // seed border O's
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                if (board[r][c] == 'O' && (r==0 || r==rows-1 || c==0 || c==cols-1)) {
                    board[r][c] = '#';
                    q.offer(new int[] { r, c });
                }

            }
        }

        bfs(board, q);

        // final flip
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                if (board[r][c] == 'O')
                    board[r][c] = 'X';
                else if (board[r][c] == '#')
                    board[r][c] = 'O';
            }
        }
    }

    private static void bfs(char[][] board, Queue<int[]> q) {
        int rows = board.length, cols = board[0].length;
        int[][] dirs = { { 1, 0 }, { -1, 0 }, { 0, 1 }, { 0, -1 } };

        while (!q.isEmpty()) {
            int[] cur = q.poll();
            for (int[] d : dirs) {
                int nr = cur[0] + d[0];
                int nc = cur[1] + d[1];

                if (nr < 0 || nc < 0 || nr >= rows || nc >= cols)
                    continue;
                if (board[nr][nc] != 'O')
                    continue;

                board[nr][nc] = '#';
                q.offer(new int[] { nr, nc });
            }
        }
    }









    public static void solveUsingUnionFind(char[][] board) {
        int rows = board.length, cols = board[0].length;
        UnionFind uf = new UnionFind(rows * cols + 1);
        int dummy = rows * cols;

        int[][] dirs = {{1,0}, {-1,0}, {0,1}, {0,-1}};

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                if (board[r][c] != 'O') continue;

                int id = r * cols + c;

                // border O → connect to dummy
                if (r == 0 || c == 0 || r == rows - 1 || c == cols - 1) {
                    uf.union(id, dummy);
                }

                // connect with adjacent O's
                for (int[] d : dirs) {
                    int nr = r + d[0], nc = c + d[1];
                    if (nr < 0 || nc < 0 || nr >= rows || nc >= cols) continue;
                    if (board[nr][nc] == 'O') {
                        uf.union(id, nr * cols + nc);
                    }
                }
            }
        }

        // flip surrounded regions
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                if (board[r][c] == 'O' &&
                    !uf.connected(r * cols + c, dummy)) {
                    board[r][c] = 'X';
                }
            }
        }
    }
    static class UnionFind {
        int[] parent, rank;

        UnionFind(int n) {
            parent = new int[n];
            rank = new int[n];
            for (int i = 0; i < n; i++) parent[i] = i;
        }

        int find(int x) {
            if (parent[x] != x)
                parent[x] = find(parent[x]);
            return parent[x];
        }

        void union(int a, int b) {
            int pa = find(a), pb = find(b);
            if (pa == pb) return;

            if (rank[pa] < rank[pb]) parent[pa] = pb;
            else if (rank[pa] > rank[pb]) parent[pb] = pa;
            else {
                parent[pb] = pa;
                rank[pa]++;
            }
        }

        boolean connected(int a, int b) {
            return find(a) == find(b);
        }
    }
}
