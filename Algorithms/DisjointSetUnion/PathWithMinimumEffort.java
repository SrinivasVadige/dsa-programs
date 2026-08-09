package Algorithms.DisjointSetUnion;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.TreeMap;

/**
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 01 April 2025
 */
public class PathWithMinimumEffort {
    public static void main(String[] args) {
        int[][] heights = {{1,2,2},{3,8,2},{5,3,5}};
        System.out.println("minimumEffortPath(heights) => " + minimumEffortPath(heights));
        System.out.println( "minimumEffortPathUsingUnionFind(heights) => " + minimumEffortPathUsingUnionFind(heights));
        System.out.println( "minimumEffortPathUsingUnionFindMyApproach(heights) => " + minimumEffortPathUsingUnionFindMyApproach(heights));
        System.out.println( "minimumEffortPathUsingUnionFindMyApproach2(heights) => " + minimumEffortPathUsingUnionFindMyApproach2(heights));
        System.out.println( "minimumEffortPathUsingDfs(heights) => " + minimumEffortPathUsingDfs(heights));

    }

    public static int minimumEffortPath(int[][] heights) {
        int m = heights.length, n = heights[0].length;
        int[][] dirs = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}}; // Directions: right, down, left, up
        int[][] effort = new int[m][n];
        for (int[] row : effort) Arrays.fill(row, Integer.MAX_VALUE);
        effort[0][0] = 0;

        // Priority queue to process cells in increasing order of effort
        PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparingInt(a -> a[2])); // [x, y, effort]
        pq.offer(new int[]{0, 0, 0}); // Start from the top-left corner with 0 effort

        while (!pq.isEmpty()) {
            int[] curr = pq.poll();
            int x = curr[0], y = curr[1], currEffort = curr[2];

            // If we reach the bottom-right corner, return the effort
            if (x == m - 1 && y == n - 1) return currEffort;

            // Explore neighbors
            for (int[] dir : dirs) {
                int nx = x + dir[0], ny = y + dir[1];
                if (nx >= 0 && nx < m && ny >= 0 && ny < n) {
                    int nextEffort = Math.max(currEffort, Math.abs(heights[x][y] - heights[nx][ny]));
                    if (nextEffort < effort[nx][ny]) {
                        effort[nx][ny] = nextEffort;
                        pq.offer(new int[]{nx, ny, nextEffort});
                    }
                }
            }
        }

        return -1; // This line should never be reached
    }













    public static int minimumEffortPathUsingUnionFind(int[][] heights) {
        int m = heights.length, n = heights[0].length;
        int[][] dirs = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}}; // Directions: right, down, left, up
        int[][] effort = new int[m][n];
        for (int[] row : effort) Arrays.fill(row, Integer.MAX_VALUE);
        effort[0][0] = 0;
        UnionFind uf = new UnionFind(m * n); // Union-Find to detect cycles

        // Priority queue to process cells in increasing order of effort
        PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparingInt(a -> a[2])); // [x, y, effort]
        pq.offer(new int[]{0, 0, 0}); // Start from the top-left corner with 0 effort

        while (!pq.isEmpty()) {
            int[] curr = pq.poll();
            int x = curr[0], y = curr[1], currEffort = curr[2];

            // If we reach the bottom-right corner, return the effort
            if (x == m - 1 && y == n - 1) return currEffort;

            // Explore neighbors
            for (int[] dir : dirs) {
                int nx = x + dir[0], ny = y + dir[1];
                if (nx >= 0 && nx < m && ny >= 0 && ny < n) {
                    int nextEffort = Math.max(currEffort, Math.abs(heights[x][y] - heights[nx][ny]));
                    if (nextEffort < effort[nx][ny]) {
                        effort[nx][ny] = nextEffort;
                        pq.offer(new int[]{nx, ny, nextEffort});
                        uf.union(x * n + y, nx * n + ny); // Union the current cell with its neighbor
                    }
                }
            }
        }

        return -1; // This line should never be reached
    }
    static class UnionFind {
        int[] parent, rank;

        UnionFind(int n) {
            parent = new int[n];
            rank = new int[n];
            for (int i = 0; i < n; i++) {
                parent[i] = i;
            }
        }

        int find(int x) {
            if (parent[x] != x) {
                parent[x] = find(parent[x]); // Path compression
            }
            return parent[x];
        }

        void union(int x, int y) {
            int rootX = find(x);
            int rootY = find(y);
            if (rootX == rootY) return;
            if (rank[rootX] > rank[rootY]) parent[rootY] = rootX;
            else if (rank[rootX] < rank[rootY]) parent[rootX] = rootY;
            else {
                parent[rootY] = rootX;
                rank[rootX]++;
            }
        }
    }


















    public static int minimumEffortPathUsingUnionFindMyApproach(int[][] heights) {
        int m = heights.length, n = heights[0].length;
        if (m==1 && n==1) return 0;
        Map<Integer, Set<int[]>> map = new TreeMap<>();
        for(int i=0; i<m; i++) {
            for (int j=0; j<n; j++) {
                int dist;
                // DOWN
                if (i<m-1) {
                    dist = Math.abs(heights[i][j]-heights[i+1][j]);
                    int a=i*n+j, b=(i+1)*n+j;
                    map.computeIfAbsent(dist, _-> new HashSet<>()).add(new int[]{a,b});

                }
                // RIGHT
                if(j<n-1) {
                    dist = Math.abs(heights[i][j]-heights[i][j+1]);
                    int a = i*n + j, b = i*n+(j+1);
                    map.computeIfAbsent(dist, _-> new HashSet<>()).add(new int[]{a,b});
                }
                // TOP AND LEFT ARE NOT NEEDED AS WE ALREADY COVERED THEM
            }
        }
        UF uf = new UF(m*n);
        int res = Integer.MAX_VALUE;
        main: for(int d: map.keySet()) {
            Set<int[]> edges = map.get(d);
            for(int[] e: edges) {
                uf.union(e[0], e[1]);
                if (uf.find(m*n-1)==uf.find(0)) {
                    res = d;
                    break main;
                }
            }
        }
        return res;
    }
    public static int minimumEffortPathUsingUnionFindMyApproach2(int[][] heights) {
        int m = heights.length, n = heights[0].length;
        int totalCells = m * n;

        // Create edges with weights - difference in heights
        List<int[]> edges = new ArrayList<>(); // {from, to, weight}
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (i < m - 1) { // Down
                    edges.add(new int[]{i * n + j, (i + 1) * n + j, Math.abs(heights[i][j] - heights[i + 1][j])});
                }
                if (j < n - 1) { // Right
                    edges.add(new int[]{i * n + j, i * n + (j + 1), Math.abs(heights[i][j] - heights[i][j + 1])});
                }
            }
        }

        // Sort edges by weight
        edges.sort(Comparator.comparingInt(e -> e[2]));

        // Union-Find to track connected components
        UF uf = new UF(totalCells);

        // Process edges in increasing order of weight
        for (int[] edge : edges) {
            uf.union(edge[0], edge[1]);
            if (uf.find(0) == uf.find(totalCells - 1)) {
                return edge[2]; // Return the minimum effort when start and end are connected
            }
        }

        return 0; // This line should never be reached
    }


    static class UF {
        int[] parent, rank;

        public UF(int n) {
            parent = new int[n];
            rank = new int[n];
            for (int i = 0; i < n; i++) parent[i] = i;
        }

        public void union(int a, int b) {
            int pa = find(a);
            int pb = find(b);

            if (pa == pb) return;

            if (rank[pb] < rank[pa]) parent[pb]=pa;
            else if(rank[pa] < rank[pb]) parent[pa]=pb;
            else {
                parent[pb]=pa;
                rank[pa]++;
            }
        }

        public int find(int i) {
            if (parent[i] == i) return i;
            return parent[i] = find(parent[i]);
        }
    }























    /**
     * WORKING BUT TLE
     *
        PATH:
        -----
        1) Max difference between nodes has to be smaller ---> max(List<nodeI - nodeJ>) small
        2) In graph nodeA.val - nodeB.val is the edge weight
        3)

        APPROACHES:
        -----------
        1) DFS --> each recursion must contain max of 4 directions --> TLE for 5*10
        2) graph --> union find? --> but we need proper edges
        3)


     */
    static List<Integer> set = new ArrayList<>();
    static int overallMin = Integer.MAX_VALUE;
    public static int minimumEffortPathUsingDfs(int[][] heights) {
        byte[][] visited = new byte[heights.length][heights[0].length];
        visited[0][0]++;
        dfs(heights, 0, 0, 0, visited);
        // System.out.println(set);
        if (set.isEmpty()) set.add(0);
        return overallMin;
    }
    private static void dfs(int[][] heights, int i, int j, int max, byte[][] visited) {
        int r = heights.length, c=heights[0].length;
        if (visited[i][j] > 1) return;

        if(i==r-1 && j==c-1) {
            overallMin = Math.min(overallMin, max);
            set.add(max);
            return;
        }

        int tempMax;
        // DOWN
        if(i<r-1) {
            tempMax = max;
            tempMax = Math.max(tempMax, Math.abs(heights[i][j]-heights[i+1][j]));
            // System.out.printf("DOWN -- max:%s, tempMax:%s, i:%s, j:%s\n", max, tempMax, i, j);
            if (overallMin >= tempMax) {
                visited[i+1][j]++;
                dfs(heights, i+1, j, tempMax, visited);
                visited[i+1][j]--;
            }
        }
        // RIGHT
        if(j<c-1) {
            tempMax = max;
            tempMax = Math.max(tempMax, Math.abs(heights[i][j]-heights[i][j+1]));
            // System.out.printf("RIGHT -- max:%s, tempMax:%s, i:%s, j:%s\n", max, tempMax, i, j);
            if (overallMin >= tempMax) {
                visited[i][j+1]++;
                dfs(heights, i, j+1, tempMax, visited);
                visited[i][j+1]--;
            }
        }
        // TOP
        if(i>0) {
            tempMax = max;
            tempMax = Math.max(tempMax, Math.abs(heights[i][j]-heights[i-1][j]));
            // System.out.printf("TOP -- max:%s, tempMax:%s, i:%s, j:%s\n", max, tempMax, i, j);
            if (overallMin >= tempMax) {
                visited[i-1][j]++;
                dfs(heights, i-1, j, tempMax, visited);
                visited[i-1][j]--;
            }
        }
        // LEFT
        if(j>0) {
            tempMax = max;
            tempMax = Math.max(tempMax, Math.abs(heights[i][j]-heights[i][j-1]));
            // System.out.printf("LEFT -- max:%s, tempMax:%s, i:%s, j:%s\n", max, tempMax, i, j);
            if (overallMin >= tempMax) {
                visited[i][j-1]++;
                dfs(heights, i, j-1, tempMax, visited);
                visited[i][j-1]--;
            }
        }
    }
}
