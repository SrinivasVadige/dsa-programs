package Algorithms.Graphs;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 13 Feb 2025
 * @link 200. Number of Islands <a href="https://leetcode.com/problems/number-of-islands/">LeetCode link</a>
 * @description Islands are connected horizontally or vertically but not diagonally
 * @topics Array, Matrix, DFS, BFS, Union Find
 * @companies amazon, bloomberg, google, facebook, microsoft, tiktok, snapchat, apple, linkedin, walmart, uber, oracle, anduril, goldman, zoho, paypal, tesla, visa, yandex, redfin, adobe, samsung, salesforce, nvidia, intel, bytedacolse, yahoo, siemens, ebay, citadel
 * NOTE:
 * Even though it looks like "Adjacency Matrix Graph"
 * but the nodes are not connected properly by edges like it used to connect in Adjacency Matrix
 * So, it's not Adjacency Matrix Graph
 */
public class NumberOfIslands {
    public static void main(String[] args) {
        // char[][] grid = {{1,1,0,0,0},{1,1,0,0,0},{0,0,1,0,0},{0,0,0,1,1}};
        /*
        1 1 0 0 0
        1 1 0 0 0
        0 0 1 0 0
        0 0 0 1 1
        */
        char[][] grid = {{'1','1','1'},{'0','1','0'},{'1','1','1'}};
        System.out.println("numIslands using DFS: " + numIslandsUsingDFS(grid));

        grid = new char[][]{{'1','1','1'},{'0','1','0'},{'1','1','1'}};
        System.out.println("numIslands using BFS: " + numIslandsUsingBFS(grid));

        grid = new char[][]{{'1','1','1'},{'0','1','0'},{'1','1','1'}};
        System.out.println("numIslands using Union Find: " + numIslandsUsingUnionFind(grid));
    }


    public static int numIslandsUsingDFS(char[][] grid) {
        int islands = 0;
        for(int r=0; r<grid.length; r++) {
            for(int c=0; c<grid[0].length; c++) {
                if(grid[r][c] == '1') {
                    islands++;
                    dfs(grid, r, c);
                }
            }
        }
        return islands;
    }

    private static void dfs(char[][] grid, int r, int c) {
        if(r<0 || r>=grid.length || c<0 || c>=grid[0].length || grid[r][c]=='0') {
            return;
        }

        grid[r][c]='0'; // mark as visited
        int[][] dirs = {{1,0}, {0,1}, {-1,0}, {0,-1}}; // {bottom, right, top, left}
        for(int[] dir: dirs) {
            dfs(grid, r+dir[0], c+dir[1]);
        }
    }









    public static int numIslandsUsingBFS(char[][] grid) {
        int rows = grid.length;
        int cols = grid[0].length;
        int islands = 0;

        for (int r = 0; r < rows; ++r) {
            for (int c = 0; c < cols; ++c) {
                if (grid[r][c] == '1') {

                    islands++;
                    grid[r][c] = '0'; // mark as visited

                    Queue<Integer> queue = new LinkedList<>(); // neighbors
                    queue.add(r * cols + c); // -> to access row = id/cols; and col = id%cols;... or use int[] {r, c}

                    while (!queue.isEmpty()) {
                        int id = queue.remove();
                        int row = id / cols;
                        int col = id % cols;

                        if (row - 1 >= 0 && grid[row - 1][col] == '1') { // topNeighbor
                            queue.add((row - 1) * cols + col);
                            grid[row - 1][col] = '0';
                        }
                        if (row + 1 < rows && grid[row + 1][col] == '1') { // downNeighbor
                            queue.add((row + 1) * cols + col);
                            grid[row + 1][col] = '0';
                        }
                        if (col - 1 >= 0 && grid[row][col - 1] == '1') { // leftNeighbor
                            queue.add(row * cols + col - 1);
                            grid[row][col - 1] = '0';
                        }
                        if (col + 1 < cols && grid[row][col + 1] == '1') { // rightNeighbor
                            queue.add(row * cols + col + 1);
                            grid[row][col + 1] = '0';
                        }
                    }
                }
            }
        }

        return islands;
    }











    public static int numIslandsUsingUnionFind(char[][] grid) { // Disjoint Sets Union DSU
        int rows = grid.length;
        int cols = grid[0].length;
        UnionFind uf = new UnionFind(grid);

        for (int r = 0; r < rows; ++r) {
            for (int c = 0; c < cols; ++c) {
                if (grid[r][c] == '1') {
                    grid[r][c] = '0';

                    // Add neighbors as children for current cell grid[r][c]
                    if (r - 1 >= 0 && grid[r - 1][c] == '1') {
                        uf.union(r * cols + c, (r - 1) * cols + c);
                    }
                    if (r + 1 < rows && grid[r + 1][c] == '1') {
                        uf.union(r * cols + c, (r + 1) * cols + c);
                    }
                    if (c - 1 >= 0 && grid[r][c - 1] == '1') {
                        uf.union(r * cols + c, r * cols + c - 1);
                    }
                    if (c + 1 < cols && grid[r][c + 1] == '1') {
                        uf.union(r * cols + c, r * cols + c + 1);
                    }
                }
            }
        }

        return uf.getCount();
    }



    static class UnionFind {
        int count; // # of connected components
        int[] parent;
        int[] rank;

        public UnionFind(char[][] grid) { // for problem 200
            count = 0;
            int m = grid.length;
            int n = grid[0].length;
            parent = new int[m * n];
            rank = new int[m * n];
            for (int i = 0; i < m; ++i) {
                for (int j = 0; j < n; ++j) {
                    if (grid[i][j] == '1') {
                        parent[i * n + j] = i * n + j;
                        ++count;
                    }
                    rank[i * n + j] = 0;
                }
            }
        }

        public int find(int i) { // path compression
            if (parent[i] != i) parent[i] = find(parent[i]);
            return parent[i];
        }

        public void union(int x, int y) { // union with rank
            int rootX = find(x);
            int rootY = find(y);
            if (rootX != rootY) {
                if (rank[rootX] > rank[rootY]) {
                    parent[rootY] = rootX;
                } else if (rank[rootX] < rank[rootY]) {
                    parent[rootX] = rootY;
                } else {
                    parent[rootY] = rootX;
                    rank[rootX] += 1;
                }
                --count;
            }
        }

        public int getCount() {
            return count;
        }
    }















    /**
     * this is unused --> just for understanding
     */
    @SuppressWarnings("unused")
    private static boolean isSameIsland(int m, int n, int[][] grid) {
        if (grid[m][n] != 1) return false;
        boolean isTrue = false;

        // left n-1
        if ((n-1)>=0 && grid[m][n-1]==1)
            isTrue = true;

        // right n+1
        if (!isTrue && (n+1) < grid[0].length && grid[m][n+1]==1)
            isTrue = true;

        // bottom m+1
        if ( !isTrue && (m+1) < grid.length && grid[m+1][n]==1)
            isTrue = true;

        // top m-1
        if (!isTrue && (m-1)>=0 && grid[m-1][n]==1)
            isTrue = true;

        // current
        if (!isTrue && grid[m][n] == 1)
            isTrue = true;

        return isTrue;
    }
}