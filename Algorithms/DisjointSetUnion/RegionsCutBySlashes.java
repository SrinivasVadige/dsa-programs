package Algorithms.DisjointSetUnion;

import java.util.Arrays;
import java.util.Map;
import java.util.HashMap;

/**
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 25 March 2025
 */
public class RegionsCutBySlashes {
    public static void main(String[] args) {
        String[] grid = {" /", "/ "};
        System.out.println("regionsBySlashesUsingNorthEastWestSouth => " + regionsBySlashesUsingNorthEastWestSouth(grid));
        System.out.println( "regionsBySlashesUsingRootIndexes(grid) => " + regionsBySlashesUsingRootIndexes(grid));
    }

    /**
     * @TimeComplexity  O(N^2)
     * @SpaceComplexity O(N^2)
     *
     * INTUITION:
     * ----------
     * DIVIDING EACH CELL INTO 4 REGIONS:
     * The key idea is to divide each cell in the grid into 4 "triangular" regions
     * (top, right, bottom, left)
     * and use Union-Find to merge these regions based on the slashes (/ or \) or " "
     *
     * PRE-REQUISITES:
     * --------------
     * When String[] grid = new String[]{"/\","\/"};
     * n=2
     * ----
     * |/\|
     * |\/|
     * ----
     * Our first thought will be
     *
     * 1 - 2 - 3
     * | /   \ |
     * 4   5   6
     * | \   / |
     * 7 - 8 - 9
     *
     * 1 - 2        1 - 2           1 - 2          1 - 2
     * |   | =>     | / |     or    | \ |    or    | ""|
     * 4 - 5        4 - 5           4 - 5          4 - 5
     * is one cell and so on
     * but here, we can't calculate 2-4-8-6-2 cycle in the above diamond grid in Union Find
     *
     * 1. As we have /, \ => Divide Each Cell into 4 Regions:
     *  - Each cell is divided into 4 triangular regions:
     *      +---+
     *      |\0/|
     *      |3 1|
     *      |/2\|
     *      +---+
     *      Regions:
     *      0: Top
     *      1: Right
     *      2: Bottom
     *      3: Left
     * or
     *      +---+
     *      |\N/|
     *      |W.E|  ---> North, East, West, South
     *      |/S\|
     *      +---+
     * if we have "/"
     *      +---+
     *      | N/|
     *      |W/E|
     *      |/S |
     *      +---+
     * this divided the 1 cell into 2 regions(any shape) -> (N,W) and (E,S)
     *
     * Similarly, if we have two side by side grid={"//"} -- 1*2 grid
     *      +---++---+
     *      | N/   N/|
     *      |W/E  W/E|
     *      |/S   /S |
     *      +---++---+
     * this divided the 2 cells into 3 regions -> (N1,W1), (E1,S1,N2,W2) and (E2,S2)
     *
     * Similarly, consider 2*2 grid -> grid={"//","//"}
     *      +---++---+
     *      | N/   N/|
     *      |W/E  W/E|
     *      |/S   /S |
     *      | N/   N/|
     *      |W/E  W/E|
     *      |/S   /S |
     *      +---++---+
     * this divided the 4 cells into 4 regions -> (N1,W1), (E1,S1,N2,W2,N3,W3), (E2,S2,E3,S3,N4,W4) and (E4,S4)
     *
     * Now let's say (i,j) is a cell in the grid
     * and (i,j,direction) is the triangular region in that cell
     *
     * AND PUT THOSE 4 N,E,W,S SECTIONS IN 5 SCENARIOS:
     *
     * ----------
     * SCENARIO 1: If we have a single slash "/" in any of the cell
     * ----------
     *
     *      +---+
     *      | N/|
     *      |W/E|
     *      |/S |
     *      +---+
     *
     * union((i,j,N), (i,j,W))
     * union((i,j,E), (i,j,S))
     *
     *
     * ----------
     * SCENARIO 2: If we have a single slash "\" in any of the cell
     * ----------
     *
     *      +---+
     *      |\N |
     *      |W\E|
     *      | S\|
     *      +---+
     *
     * union((i,j,N), (i,j,E))
     * union((i,j,W), (i,j,S))
     *
     *
     * ----------
     * SCENARIO 3: If we have a empty space in any of the cell
     * ----------
     *
     *      +---+
     *      | N |
     *      |W E|
     *      | S |
     *      +---+
     *
     * union((i,j,N), (i,j,E))
     * union((i,j,E), (i,j,S))
     * union((i,j,S), (i,j,W))
     * union((i,j,W), (i,j,N)) // OPTIONAL
     *
     *
     * ----------
     * SCENARIO 4: Horizontally adjacent squares --> it can be "/" or "\" or ""
     * ----------
     *
     *      +---++---+
     *      |\N/  \N/|
     *      |W.E  W.E|
     *      |/S\  /S\|
     *      +---++---+
     *
     * always E1 and W2 will always be connected
     * union((i,j-1,E1), (i,j,W2))
     *
     *
     * ----------
     * SCENARIO 5: Vertically adjacent squares --> it can be "/" or "\" or ""
     * ----------
     *
     *      +---+
     *      |\N/|
     *      |W.E|
     *      |/S\|
     *      |\N/|
     *      |W.E|
     *      |/S\|
     *      +---+
     *
     * always S1 and N2 will always be connected
     * union((i-1,j,N1), (i,j,S2))
     *
     *
     */

    static Map<String, String> map=new HashMap<>();
    public static int regionsBySlashesUsingNorthEastWestSouth(String[] grid) {
        int n = grid.length;
        int count = 0;

        // Iterate through each cell in the grid
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                char ch = grid[i].charAt(j);

                // Handle internal connections within the cell
                if (ch == '/') {
                    union(new Pair(i, j, "N"), new Pair(i, j, "W"));
                    union(new Pair(i, j, "E"), new Pair(i, j, "S"));
                } else if (ch == '\\') {
                    union(new Pair(i, j, "N"), new Pair(i, j, "E"));
                    union(new Pair(i, j, "W"), new Pair(i, j, "S"));
                } else if (ch == ' ') {
                    union(new Pair(i, j, "N"), new Pair(i, j, "E"));
                    union(new Pair(i, j, "E"), new Pair(i, j, "S"));
                    union(new Pair(i, j, "S"), new Pair(i, j, "W"));
                    union(new Pair(i, j, "W"), new Pair(i, j, "N")); // OPTIONAL
                }

                // Handle connections with neighboring cells
                if (i > 0) { // VERTICAL CELLS
                    union(new Pair(i, j, "N"), new Pair(i - 1, j, "S"));
                }
                if (j > 0) { // HORIZONTAL CELLS
                    union(new Pair(i, j, "W"), new Pair(i, j - 1, "E"));
                }
            }
        }

        // Count the number of unique regions
        for (String key : map.keySet()) {
            if (key.equals( map.get(key)) ) count++; // disjoint sets i.e k==v
        }

        return count;
    }
    private static void union(Pair p1, Pair p2) {
        String key1 = getKey(p1);
        String key2 = getKey(p2);
        String root1 = find(key1);
        String root2 = find(key2);

        if (!root1.equals(root2)) {
            map.put(root1, root2);
        }
    }
    private static String find(String key) {
        if (!map.containsKey(key)) {
            map.put(key, key);
        }

        while (!key.equals(map.get(key))) key = map.get(key);
        return key;

        // or
        // if (!map.get(key).equals(key)) {
        //     map.put(key, find(map.get(key))); // Path compression
        // }
        // return map.get(key);
    }
    private static String getKey(Pair p) {
        return p.i + "," + p.j + "," + p.dir;
    }
    private static class Pair {
        int i, j;
        String dir;
        public Pair(int i, int j, String dir) {
            this.i = i;
            this.j = j;
            this.dir = dir;
        }
    }

    public static int regionsBySlashesUsingNorthEastWestSouth2(String[] grid) {
        int n = grid.length;
        int count = 0;

        // Iterate through each cell in the grid
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                char ch = grid[i].charAt(j);

                // Handle internal connections within the cell
                if (ch == '/') {
                    union(i+","+j+"N", i+","+j+"W");
                    union(i+","+j+"E", i+","+j+"S");
                } else if (ch == '\\') {
                    union(i+","+j+"N", i+","+j+"E");
                    union(i+","+j+"W", i+","+j+"S");
                } else if (ch == ' ') {
                    union(i+","+j+"N", i+","+j+"E");
                    union(i+","+j+"E", i+","+j+"S");
                    union(i+","+j+"S", i+","+j+"W");
                    union(i+","+j+"W", i+","+j+"N"); // OPTIONAL
                }

                // Handle connections with neighboring cells
                if (i > 0) { // VERTICAL CELLS
                    union(i+","+j+"N", (i-1)+","+j+"S");
                }
                if (j > 0) { // HORIZONTAL CELLS
                    union(i+","+j+"W", i+","+(j-1)+"E");
                }
            }
        }

        // Count the number of unique regions
        for (Map.Entry<String, String> e: map.entrySet()) {
            if (e.getKey().equals(e.getValue()) ) count++; // disjoint sets i.e k==v
        }

        return count;
    }
    // or
    // private static String key(int i, int j, String dir) { // ---> union(key(i,j,"N"))
    //     return i + "," + j + "," + dir;
    // }
    private static void union(String p1, String p2) {
        String root1 = find(p1);
        String root2 = find(p2);

        if (!root1.equals(root2)) {
            map.put(root1, root2);
        }
    }



















    /**
     * @TimeComplexity  O(N^2)
     * @SpaceComplexity O(N^2)
     *
     * INTUITION:
     * ----------
     * DIVIDING EACH CELL INTO 4 REGIONS:
     * The key idea is to divide each cell in the grid into 4 "triangular" regions
     * (top, right, bottom, left)
     * and use Union-Find to merge these regions based on the slashes (/ or \) or " "
     *
     * EXPLANATION:
     * ------------
     * 1. Dividing Each Cell into 4 Regions:
     *  - Each cell is divided into 4 triangular regions:
     *      +---+
     *      |\0/|
     *      |1 2|
     *      |/3\|
     *      +---+
     *      Regions:
     *      0: Top
     *      1: Left
     *      2: Right
     *      3: Down
     *
     *  - This allows us to handle slashes (/ and \) and empty spaces ( ) within the cell.
     * 2. Union Operations for Internal Connections:
     *  - Union-Find is used to merge regions based on slashes and neighboring connections.
     *  - For /, connect the top (0) to the left (3) and the right (1) to the bottom (2).
     *  - For \, connect the top (0) to the right (1) and the bottom (2) to the left (3).
     *  - For empty spaces, connect all 4 regions.
     * 3. Union Operations for Neighboring Cells:
     *  - Connect the top of the current cell to the bottom of the cell above.
     *  - Connect the left of the current cell to the right of the cell to the left.
     *
     * EXPLANATION:
     * ------------
     *
     * Consider a 3*3 grid
     *
     *      +---++---++----+
     *      |\0/||\4/||\8 /|
     *      |1 2||5 6||9 10|
     *      |/3\||/7\||/11\|
     *      +---++---++----+
     *      |\12/||\16/||\20/|
     *      |1314||1718||2122|
     *      |/15\||/19\||/23\|
     *      +----++----++----+
     *      |\24/||\28/||\32/|
     *      |2526||2930||3334|
     *      |/27\||/31\||/35\|
     *      +----++----++----+
     *
     * Here => 0,4,8 are cellIndex or base index or root index of the 1st, 2nd & 3rd cells
     * Similarly 12,16,20,24,28,32 are other root indices in the grid
     *
     *
     * Same like above N, S, E, W directions, her we have 0, 1, 2, 3 with 5 scenarios
     *
     * ----------
     * SCENARIO 1: If we have a single slash "/" in any of the cell
     * ----------
     *
     *      +---+
     *      | 0/|
     *      |1/2|
     *      |/3 |
     *      +---+
     *
     * union((i,j,0), (i,j,1))
     * union((i,j,2), (i,j,3))
     *
     *
     * ----------
     * SCENARIO 2: If we have a single slash "\" in any of the cell
     * ----------
     *
     *      +---+
     *      |\0 |
     *      |1\2|
     *      | 3\|
     *      +---+
     *
     * union((i,j,0), (i,j,2))
     * union((i,j,1), (i,j,3))
     *
     *
     * ----------
     * SCENARIO 3: If we have a empty space in any of the cell
     * ----------
     *
     *      +---+
     *      | 0 |
     *      |1 2|
     *      | 3 |
     *      +---+
     *
     * union((i,j,1), (i,j,1))
     * union((i,j,1), (i,j,3))
     * union((i,j,3), (i,j,2))
     * union((i,j,2), (i,j,0)) // OPTIONAL
     *
     *
     * ----------
     * SCENARIO 4: Horizontally adjacent squares --> it can be "/" or "\" or ""
     * ----------
     *
     *      +---++---+
     *      |\0/  \4/|
     *      |1.2  5.6|
     *      |/3\  /7\|
     *      +---++---+
     *
     * always 2 and 5 will always be connected
     * union((i,j-1,2), (i,j,1)) ---> 5 means 1st index of 2nd cell i.e 1
     *
     *
     * ----------
     * SCENARIO 5: Vertically adjacent squares --> it can be "/" or "\" or ""
     * ----------
     *
     *      +---+
     *      |\0/|
     *      |1.2|
     *      |/3\|
     *      |\8/|
     *      |9.10|
     *      |/11\|
     *      +---+
     *
     * always 3 and 8 will always be connected
     * union((i-1,j,3), (i,j,0)) ---> 8 means 0th index of 3rd cell i.e 0
     */
    public static int regionsBySlashesUsingRootIndexes(String[] grid) {
        int n = grid.length;
        UnionFind uf = new UnionFind(n * n * 4); // Each cell is divided into 4 parts
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                int cellIndex = (i * n + j) * 4; // Base index for the current cell

                // Connect internal parts of the cell
                if (grid[i].charAt(j) == '/') {
                    // '/' divides top-left and bottom-right
                    uf.union(cellIndex + 0, cellIndex + 1); // Top -> Left
                    uf.union(cellIndex + 2, cellIndex + 3); // Right -> Bottom
                } else if (grid[i].charAt(j) == '\\') {
                    // '\' divides top-right and bottom-left
                    uf.union(cellIndex + 0, cellIndex + 2); // Top -> Right
                    uf.union(cellIndex + 1, cellIndex + 3); // Bottom -> Left
                } else {
                    // Empty space connects all 4 parts
                    uf.union(cellIndex + 0, cellIndex + 1); // Top -> Right
                    uf.union(cellIndex + 1, cellIndex + 2); // Right -> Bottom
                    uf.union(cellIndex + 2, cellIndex + 3); // Bottom -> Left
                    // uf.union(cellIndex + 3, cellIndex + 0); // Left -> Top
                }

                // Connect with neighboring cells
                if (i > 0) {
                    // Connect top of current cell to bottom of the cell above
                    uf.union(cellIndex + 0, ((i - 1) * n + j) * 4 + 3);
                }
                if (j > 0) {
                    // Connect left of current cell to right of the cell to the left
                    uf.union(cellIndex + 1, (i * n + j - 1) * 4 + 2);
                }
            }
        }
        return uf.countGroups();
    }
    /**
     * same like above regionsBySlashesUsingRootIndexes() but here
     *
     *      +---+
     *      |\0/|
     *      |3 1|
     *      |/2\|
     *      +---+
     *      Regions:
     *      0: Top
     *      1: Right
     *      2: Bottom
     *      3: Left
     *
     * +---++---+
     * | 0/  \0 |
     * |3/1  3\1|
     * |/2    2\|
     * +---++---+
     */
    public static int regionsBySlashesUsingRootIndexes2(String[] grid) {
        int n = grid.length;
        UnionFind uf = new UnionFind(n * n * 4); // Each cell is divided into 4 parts
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                int cellIndex = (i * n + j) * 4; // Base index for the current cell

                // Connect internal parts of the cell
                if (grid[i].charAt(j) == '/') {
                    // '/' divides top-left and bottom-right
                    uf.union(cellIndex + 0, cellIndex + 3); // Top -> Left
                    uf.union(cellIndex + 1, cellIndex + 2); // Right -> Bottom
                } else if (grid[i].charAt(j) == '\\') {
                    // '\' divides top-right and bottom-left
                    uf.union(cellIndex + 0, cellIndex + 1); // Top -> Right
                    uf.union(cellIndex + 2, cellIndex + 3); // Bottom -> Left
                } else {
                    // Empty space connects all 4 parts
                    uf.union(cellIndex + 0, cellIndex + 1); // Top -> Right
                    uf.union(cellIndex + 1, cellIndex + 2); // Right -> Bottom
                    uf.union(cellIndex + 2, cellIndex + 3); // Bottom -> Left
                    uf.union(cellIndex + 3, cellIndex + 0); // Left -> Top
                }

                // Connect with neighboring cells
                if (i > 0) {
                    // Connect top of current cell to bottom of the cell above
                    uf.union(cellIndex + 0, ((i - 1) * n + j) * 4 + 2);
                }
                if (j > 0) {
                    // Connect left of current cell to right of the cell to the left
                    uf.union(cellIndex + 3, (i * n + j - 1) * 4 + 1);
                }
            }
        }

        return uf.countGroups();
    }
    static class UnionFind {
        int[] parent;
        int[] rank;
        int groups;

        public UnionFind(int n) {
            parent = new int[n];
            rank = new int[n];
            groups = n;
            for (int i = 0; i < n; i++) {
                parent[i] = i;
            }
        }

        public int find(int x) {
            if (parent[x] != x) {
                parent[x] = find(parent[x]);
            }
            return parent[x];
        }

        public void union(int x, int y) {
            int rootX = find(x);
            int rootY = find(y);
            if (rootX != rootY) {
                if (rank[rootX] > rank[rootY]) {
                    parent[rootY] = rootX;
                } else if (rank[rootX] < rank[rootY]) {
                    parent[rootX] = rootY;
                } else {
                    parent[rootY] = rootX;
                    rank[rootX]++;
                }
                groups--;
            }
        }

        public int countGroups() {
            return groups;
        }
    }











    /**
     * Given 2*2 grid={"//", "//"}
     *
     *  +--+--+         +--+--+
     *  | /| /|         |0/00/|
     *  +--+--+    =>   |00/00| => 4 regions
     *  |/ |/ |         |/00/0|
     *  +--+--+         +--+--+
     *
     * So, instead of dividing one cell into 4 parts, we divide one cell into 9 parts
     *
     * if char is '/'
     *  +--+--+--+
     *  | 0| 0| 1|
     *  +--+--+--+
     *  | 0| 1| 0|
     *  +--+--+--+
     *  | 1| 0| 0|
     *  +--+--+--+
     *
     * if char is '\'
     *  +--+--+--+
     *  | 1| 0| 0|
     *  +--+--+--+
     *  | 0| 1| 0|
     *  +--+--+--+
     *  | 0| 0| 1|
     *  +--+--+--+
     *
     * and now you can see that row count will increase by 3 and col count will increase by 3
     * so, 2*2 grid will become => (2*3)*(2*3) grid
     * so the above grid {"//","//"}
     *  +--+--+
     *  | /| /|
     *  +--+--+
     *  |/ |/ |
     *  +--+--+
     * will become
     *           .
     *  +--+--+--+--+--+--+
     *  | 0| 0| 1| 0| 0| 1|
     *  +--+--+--+--+--+--+
     *  | 0| 1| 0| 0| 1| 0|
     *  +--+--+--+--+--+--+
     *  | 1| 0| 0| 1| 0| 0|
     *. +--+--+--+--+--+--+ .
     *  | 0| 0| 1| 0| 0| 1|
     *  +--+--+--+--+--+--+
     *  | 0| 1| 0| 0| 1| 0|
     *  +--+--+--+--+--+--+
     *  | 1| 0| 0| 1| 0| 0|
     *  +--+--+--+--+--+--+
     *           .
     * Now it looks like we can traverse this newGrid just like Number of island {@Link Algorithms.Graphs.NumberOfIslands#numIslandsMyApproach(char[][] grid)}
     * and 0's will be the islands or regions
     */
    public int regionsBySlashesUsingDFS(String[] grid) {
        int n = grid.length;
        int[][] newGrid = new int[3*n][3*n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                char key = grid[i].charAt(j);
                int row = 3 * i, col = 3 * j;

                // mark 3 places in newGrid cells which is equal to marking 1 cell in original grid
                if (key == '/') {
                    newGrid[row][col + 2] = 1;
                    newGrid[row + 1][col + 1] = 1;
                    newGrid[row + 2][col] = 1;
                } else if (key == '\\') {
                    newGrid[row][col] = 1;
                    newGrid[row + 1][col + 1] = 1;
                    newGrid[row + 2][col + 2] = 1;
                }
            }
        }

        int count = 0;
        for(int i=0; i<3*n; i++){
            for(int j=0;j<3*n;j++){
                if(newGrid[i][j] == 0){
                    count++;
                    dfs(newGrid, i, j);
                }
            }
        }

        return count;
    }
    public void dfs(int[][]  newGrid, int row, int col, int n){
        if (row < 0 || row >= n || col < 0 || col >= n || newGrid[row][col] == 1) return;

        newGrid[row][col] = 1; // mark the current 0 to 1 as we don't want to trav again in above for loop

        dfs(newGrid, row+1, col, n); // down
        dfs(newGrid, row-1, col, n); // up
        dfs(newGrid, row, col+1, n); // right
        dfs(newGrid, row, col-1, n); // left
    }






    int[][] dim = {{-1,0},{0,-1},{1,0},{0,1}};
    public int regionsBySlashesUsingDFS2(String[] grid) {

        int n = grid.length;
        int[][] mat = new int[3*n][3*n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                char key = grid[i].charAt(j);
                int row = 3 * i, col = 3 * j;

                if (key == '/') {
                    mat[row][col + 2] = mat[row + 1][col + 1] = mat[row + 2][col] = 1;
                } else if (key == '\\') {
                    mat[row][col] = mat[row + 1][col + 1] = mat[row + 2][col + 2] = 1;
                }
            }
        }

        int count = 0;
        for(int i=0; i<3*n; i++){
            for(int j=0;j<3*n;j++){
                if(mat[i][j] == 0){
                    dfs(mat, i, j);
                    count++;}
            }
        }

        return count;
    }
    public void dfs(int[][]  mat, int row, int col){
        int n = mat.length;
        mat[row][col] = 1;
        for(int i=0;i<4;i++){
            int x = row + dim[i][0];
            int y = col + dim[i][1];

            if(x>=0 && x<n && y>=0 && y<n && mat[x][y] == 0)
                dfs(mat, x, y);
        }
    }


















    public int regionsBySlashes2(String[] grid) {
        int rows = grid.length;
        int cols = grid[0].length();
        int m = cols;

        UF uf = new UF(rows * cols * 4);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (i > 0) uf.connect(left(i, j, m), right(i - 1, j, m));
                if (i < rows - 1) uf.connect(right(i, j, m), left(i + 1, j, m));
                if (j > 0) uf.connect(top(i, j, m), bot(i, j - 1, m));
                if (j < cols - 1) uf.connect(bot(i, j, m), top(i, j + 1, m));

                if (grid[i].charAt(j) == ' ') {
                    uf.connect(top(i, j, m), left(i, j, m));
                    uf.connect(left(i, j, m), bot(i, j, m));
                    uf.connect(bot(i, j, m), right(i, j, m));
                } else if (grid[i].charAt(j) == '/') {
                    uf.connect(top(i, j, m), left(i, j, m));
                    uf.connect(bot(i, j, m), right(i, j, m));
                } else {
                    uf.connect(left(i, j, m), bot(i, j, m));
                    uf.connect(top(i, j, m), right(i, j, m));
                }
            }
        }

        // System.out.println(rows + " " + cols);
        return uf.components();
    }

    private int top(int i, int j, int cols) {
        return 4 * (i * cols + j) + 0;
    }

    private int left(int i, int j, int cols) {
        return 4 * (i * cols + j) + 1;
    }

    private int bot(int i, int j, int cols) {
        return 4 * (i * cols + j) + 2;
    }

    private int right(int i, int j, int cols) {
        return 4 * (i * cols + j) + 3;
    }

    static class UF {
        int[] uf;

        UF(int n) {
            uf = new int[n];
            for (int i = 0; i < n; i++) {
                uf[i] = i;
            }
        }

        int parent(int i) {
            while (uf[i] != i) {
                i = uf[i];
            }
            return i;
        }

        void connect(int i, int j) {
            int parentI = parent(i);
            int parentJ = parent(j);

            uf[Math.max(parentI, parentJ)] = Math.min(parentI, parentJ);
        }

        int components() {
            int ans = 0;
            for (int i = 0; i < uf.length; i++) {
                if (uf[i] == i) ans++;
            }
            return ans;
        }
    }




















    public int regionsBySlashes3(String[] grid) {
        int n = grid.length + 1;
        int[] g = new int[n * n];
        for(int i = 0 ; i < g.length; i++) {
            g[i] = i;
        }

        for(int i = 0; i < n; i++) {
            for(int j = 0; j < n; j++) {
                if(i == 0 || j == n - 1 || i == n - 1 || j == 0) {
                    g[i * n + j] = 0;
                }
                //System.out.print(g[i * n + j] + " ");
            }
        }

        int res = 1;
        for(int i = 0; i < grid.length; i++) {
            for(int j = 0; j < grid[0].length(); j++) {
                char c = grid[i].charAt(j);
                int leftdown = (i + 1) * n + j;
                int rightup = i * n + j + 1;
                int leftup = i *n + j;
                int rightdown =  (i + 1) * n + j + 1;
                if(c == '/') {
                    res += union(g, leftdown, rightup);
                }
                if(c == '\\') {
                    res += union(g, leftup, rightdown);
                }
                //System.out.println(Arrays.toString(g));
               // System.out.println(i + " " + j + " " + leftdown + " " + rightup + " " + leftup + " " + rightdown);
            }
        }
        return res;
    }
    private int find(int[] g, int i) {
        if(g[i] != i) {
            return find(g, g[i]);
        }
        else {
            return i;
        }
    }
    private int union(int[] g, int i, int j) {
        int ih = find(g, i);
        int jh = find(g, j);
        if(ih == jh) {
            return 1;
        }
        else {
            g[ih] = jh;
            return 0;
        }
    }













    public int regionsBySlashes4(String[] grid) {
        int n = grid.length;
        UnionFind4 uf = new UnionFind4(n * n * 4);
        int res = n * n * 4;

        for(int i=0;i<n;i++) {
            String row = grid[i];

            for(int j=0;j<n;j++) {
                char ch = row.charAt(j);
                if(ch != '/') {
                    res -= uf.union(indexOf(i, j, n, 0), indexOf(i, j, n, 1));
                    res -= uf.union(indexOf(i, j, n, 2), indexOf(i, j, n, 3));
                }
                if(ch != '\\') {
                    res -= uf.union(indexOf(i, j, n, 0), indexOf(i, j, n, 3));
                    res -= uf.union(indexOf(i, j, n, 1), indexOf(i, j, n, 2));
                }

                if(i > 0) {
                    res -= uf.union(indexOf(i -1, j, n, 2), indexOf(i, j, n, 0));
                }
                if(j > 0) {
                    res -= uf.union(indexOf(i, j-1, n, 1), indexOf(i, j, n, 3));
                }
            }
        }

        return res;
    }
    int indexOf(int i, int j, int n, int x) {
        return (i * n + j) * 4 + x;
    }
    static class UnionFind4 {
        int[] parents;

        UnionFind4(int size) {
            parents = new int[size];
            for(int i=0;i<size;i++) {
                parents[i] = i;
            }
        }

        int findRoot(int x) {
            int p = parents[x];
            if(p != parents[p]) parents[x] = findRoot(p);

            return parents[x];
        }

        int union(int x, int y) {
            int px = findRoot(x);
            int py = findRoot(y);

            if(px == py) return 0;
            parents[px] = py;
            return 1;
        }
    }



















    public int regionsBySlashes5(String[] grid) {
        int gridSize = grid.length;
        int totalTriangles = gridSize * gridSize * 4;
        int[] parentArray = new int[totalTriangles];
        Arrays.fill(parentArray, -1);

        // Initially, each small triangle is a separate region
        int regionCount = totalTriangles;

        for (int row = 0; row < gridSize; row++) {
            for (int col = 0; col < gridSize; col++) {
                // Connect with the cell above
                if (row > 0) {
                    regionCount -=
                    unionTriangles(
                        parentArray,
                        getTriangleIndex(gridSize, row - 1, col, 2),
                        getTriangleIndex(gridSize, row, col, 0)
                    );
                }
                // Connect with the cell to the left
                if (col > 0) {
                    regionCount -=
                    unionTriangles(
                        parentArray,
                        getTriangleIndex(gridSize, row, col - 1, 1),
                        getTriangleIndex(gridSize, row, col, 3)
                    );
                }

                // If not '/', connect triangles 0-1 and 2-3
                if (grid[row].charAt(col) != '/') {
                    regionCount -=
                    unionTriangles(
                        parentArray,
                        getTriangleIndex(gridSize, row, col, 0),
                        getTriangleIndex(gridSize, row, col, 1)
                    );
                    regionCount -=
                    unionTriangles(
                        parentArray,
                        getTriangleIndex(gridSize, row, col, 2),
                        getTriangleIndex(gridSize, row, col, 3)
                    );
                }

                // If not '\', connect triangles 0-3 and 1-2
                if (grid[row].charAt(col) != '\\') {
                    regionCount -=
                    unionTriangles(
                        parentArray,
                        getTriangleIndex(gridSize, row, col, 0),
                        getTriangleIndex(gridSize, row, col, 3)
                    );
                    regionCount -=
                    unionTriangles(
                        parentArray,
                        getTriangleIndex(gridSize, row, col, 2),
                        getTriangleIndex(gridSize, row, col, 1)
                    );
                }
            }
        }
        return regionCount;
    }

    // Calculate the index of a triangle in the flattened array
    // Each cell is divided into 4 triangles, numbered 0 to 3 clockwise from the top
    private int getTriangleIndex(
        int gridSize,
        int row,
        int col,
        int triangleNum
    ) {
        return (gridSize * row + col) * 4 + triangleNum;
    }

    // Union two triangles and return 1 if they were not already connected, 0 otherwise
    private int unionTriangles(int[] parentArray, int x, int y) {
        int parentX = findParent(parentArray, x);
        int parentY = findParent(parentArray, y);

        if (parentX != parentY) {
            parentArray[parentX] = parentY;
            return 1; // Regions were merged, so count decreases by 1
        }

        return 0; // Regions were already connected
    }

    // Find the parent (root) of a set
    private int findParent(int[] parentArray, int x) {
        if (parentArray[x] == -1) return x;

        return parentArray[x] = findParent(parentArray, parentArray[x]);
    }















    /**
     *
     * String[] grid = new String[] {"/\/","\\/","/\\"};
     * n=3
     * -----
     * |/\/|
     * |\\/|
     * |/\\|
     * -----
     *
     * 1 - 2 - 3 - 4
     * | /   \   / |
     * 5   6   7   8
     * | \   \   / |
     * 9   10  11  12
     * | /    \  \ |
     * 13- 14- 15- 16
     *
     *
     *
     * String[] grid = new String[]{" /","/ "};
     * n=2
     * ----
     * | /|
     * |/ |
     * ----
     *
     * 1 - 2 - 3             0 - 0 - 0
     * |     / |             |     / |
     * 4   5   6             0   0   0
     * | /     |             | /     |
     * 7 - 8 - 9             0 - 0 - 0
     *
     *
     *
     *
     * String[] grid = new String[]{"/\","\/"};
     * n=2
     * ----
     * |/\|
     * |\/|
     * ----
     *
     * 1 - 2 - 3
     * | /   \ |
     * 4   5   6
     * | \   / |
     * 7 - 8 - 9
     *
     *
     * so, form a (n+1)*(n+1) grid or parent[(n+1)*(n+1)] array, and it will form n*n -> 1*1 cells
     *
     * But to count all cycles (including larger ones), you need a DFS approach
     * Because Union-Find only detects cycles when merging disjoint sets.
     */
    int[] par, rank;
    public int regionsBySlashesMyApproach(String[] grid) {
        int n = grid.length+1;
        // int[][] arr = new int[n][n];
        par = new int[n*n];
        rank = new int[n*n];
        for (int i=0; i<n*n; i++) par[i]=i;

        // connect the square across
        for (int i=0; i<n-1; i++) union(i, i+1); // topLeft to toRight
        for (int i=n-1; i<(n*n); i+=n) union(i, i+n); // topRight to bRight
        for(int i=(n*n)-1, j=0; j<n-1; i--, j++) union(i-1, i); // bRight to bLeft
        for (int i=0; i<n*n; i+=n) union(i+n, i); // bLeft to topLeft
        return 0;
    }

    private void union(int a, int b) {
        int pa = find(a);
        int pb = find(b);
        pa = a;
        pb = b;

        // Ignore same parent case
        if (rank[pb]<rank[pa]) par[pb]=pa;
        else if (rank[pa]<rank[pb]) par[pa]=pb;
        else {
            par[pb]=pa;
            rank[pa]++;
        }
    }
    private int find(int i){
        while(i != par[i]) i=par[i];
        return i;
    }
}
