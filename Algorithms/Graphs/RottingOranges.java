package Algorithms.Graphs;

import java.util.ArrayDeque;
import java.util.LinkedList;
import java.util.Queue;

/**
    [2,1,1]
    [0,1,1]
    [1,0,1]

    THOUGHTS:
    ---------
    1) Rots horizontally & vertically but not diagonally
    2) Some oranges don't rot at all --> return -1
    3) In some cases, all the oranges are already rotten --> return 0

 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 16 Jan 2025
 * same as {@link Algorithms.Graphs.NearestExitFromEntranceInMaze}
 */
@SuppressWarnings("unused")
public class RottingOranges {
    public static void main(String[] args) {
        int[][] grid = {{2,1,1},{1,1,0},{0,1,1}};
        System.out.println("orangesRotting(grid): " + orangesRottingMyApproach(grid));
    }



    /**
        APPROACH: -- bfs
        ---------
        1) Count num of 1s
        2) Store all 2s in queue
        3) Do while(!q.isEmpty()) with for(q size) to trav each level
        4) Return the level if no 1s left or if 1s left then return -1

     */
    public static int orangesRottingMyApproach(int[][] grid) {
        int m=grid.length, n=grid[0].length, ones=0, level=0, dirs[][]={{0,1}, {1,0}, {0,-1}, {-1,0}};
        Queue<int[]> q = new LinkedList<>();
        for(int r=0; r<m; r++) {
            for (int c=0; c<n; c++) {
                if(grid[r][c]==1) ones++;
                else if(grid[r][c]==2) q.add(new int[]{r,c});
            }
        }
        if(ones == 0) return 0;

        while(!q.isEmpty()) {
            int size = q.size();
            for(int i=0; i<size; i++) {
                int[] curr = q.poll();
                int r=curr[0], c=curr[1];
                // next
                for(int[] dir: dirs) {
                    int nr=r+dir[0], nc=c+dir[1];
                    if(isOutOfBounds(m,n,nr,nc) || grid[nr][nc]!=1) continue;
                    q.add(new int[]{nr, nc});
                    grid[nr][nc]=2;
                    ones--;
                    // System.out.printf("r:%s, c:%s, nr:%s, nc:%s, ones:%s, level:%s \n", r, c, nr, nc, ones, level);
                    if(ones==0) return level+1;
                }
            }
            level++;
            // System.out.println();
        }
        return -1;
    }
    private static boolean isOutOfBounds(int m, int n, int i, int j) {
        return i<0 || j<0 || i>=m || j>=n;
    }







    public int orangesRotting(int[][] grid) {
        int freshCount = 0;
        int n = grid.length;
        int m = grid[0].length;
        int[][] dirs = new int[][] { { 1, 0 }, { -1, 0 }, { 0, 1 }, { 0, -1 } };
        Queue<int[]> q = new LinkedList<>();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (grid[i][j] == 1)
                    freshCount++;
                else if (grid[i][j] == 2)
                    q.add(new int[] { i, j });
            }
        }
        int level=0;
        while (!q.isEmpty() && freshCount > 0) {
            int size = q.size();

            while(size-- > 0) { // decrement size
                int[] cur = q.poll();
                for(int[] dir :dirs){
                    int new_x=dir[0]+cur[0];
                    int new_y=dir[1]+cur[1];

                    if(new_x>=0 && new_x<n && new_y>=0 && new_y<m && grid[new_x][new_y]==1){
                        grid[new_x][new_y]=2;
                        freshCount--;
                        q.add(new int[]{new_x,new_y});
                    }
                }
            }
            level++;
        }
        return freshCount == 0 ? level : -1;
    }













    public int orangesRotting2(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        Queue<int[]> queue = new ArrayDeque<>();
        int goodOrange = 0;
        for(int i = 0; i < m; i++){
            for(int j = 0; j < n; j++){
                if(grid[i][j] == 1){
                    goodOrange++;
                }
                if(grid[i][j] == 2){
                    queue.offer(new int[]{i, j});
                }
            }
        }
        int[][] directions = new int[][]{{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        int count = 0;
        int time = 0;
        boolean[][] visited = new boolean[m][n];
        while(!queue.isEmpty()){
            int size = queue.size();
            for(int i = 0; i < size; i++){
                int[] current = queue.poll();
                int currentX = current[0];
                int currentY = current[1];
                for(int[] dir : directions){
                    int nextX = currentX + dir[0];
                    int nextY = currentY + dir[1];
                    if(nextX >= 0 && nextX < m && nextY >= 0 && nextY < n && grid[nextX][nextY] == 1 && !visited[nextX][nextY]){
                        queue.offer(new int[]{nextX, nextY});
                        visited[nextX][nextY] = true;
                        count++;
                    }
                }
            }
            if(queue.size() > 0){
                time++;
            }
        }
        return count == goodOrange ? time : -1;
    }













    public static boolean isValid(int rowVal, int colVal, int rsize, int csize) {return rowVal >= 0 && rowVal < rsize && colVal >= 0 && colVal < csize;}
    static class Pair { int row; int col; Pair(int row, int col) { this.row = row; this.col = col; } }
    public static int orangesRotting3(int[][] grid) {
        int[] rOffset = {-1, 0, 1, 0};
        int[] cOffset = {0, 1, 0, -1};
        int rsize = grid.length;
        int csize = grid[0].length;
        Queue<Pair> queue = new LinkedList<>();
        boolean isFresh = false;

        for (int i = 0; i < rsize; i++) {
            for (int j = 0; j < csize; j++) {
                if (grid[i][j] == 1) {
                    isFresh = true;
                }
            }
        }

        if (!isFresh) {
            return 0;
        }

        int time = 0;
        for (int i = 0; i < rsize; i++) {
            for (int j = 0; j < csize; j++) {
                if (grid[i][j] == 2) {
                    queue.offer(new Pair(i, j));
                }
            }
        }

        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                Pair curr = queue.poll();
                for (int j = 0; j < 4; j++) {
                    int rowVal = curr.row + rOffset[j];
                    int colVal = curr.col + cOffset[j];
                    if (isValid(rowVal, colVal, rsize, csize) && grid[rowVal][colVal] == 1) {
                        grid[rowVal][colVal] = 2;
                        queue.offer(new Pair(rowVal, colVal));
                    }
                }
            }
            if (!queue.isEmpty()) {
                time++;
            }
        }

        for (int i = 0; i < rsize; i++) {
            for (int j = 0; j < csize; j++) {
                if (grid[i][j] == 1) {
                    return -1;
                }
            }
        }

        return time;
    }






    public static int orangesRotting4(int[][] grid) {
        int rows = grid.length, cols = grid[0].length, count = 0, fresh = 0, minutes = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (grid[i][j] == 1) fresh++;
                if (grid[i][j] == 2) count++;
            }
        }
        while (count > 0 && fresh > 0) {
            minutes++;
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    if (grid[i][j] == 2) {
                        if (i - 1 >= 0 && grid[i - 1][j] == 1) {
                            grid[i - 1][j] = 2;
                            fresh--;
                        }
                        if (i + 1 < rows && grid[i + 1][j] == 1) {
                            grid[i + 1][j] = 2;
                            fresh--;
                        }
                        if (j - 1 >= 0 && grid[i][j - 1] == 1) {
                            grid[i][j - 1] = 2;
                            fresh--;
                        }
                        if (j + 1 < cols && grid[i][j + 1] == 1) {
                            grid[i][j + 1] = 2;
                            fresh--;
                        }
                    }
                }
            }
            count = 0;
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    if (grid[i][j] == 1) fresh++;
                    if (grid[i][j] == 2) count++;
                }
            }
        }
        return fresh == 0 ? minutes : -1;
    }

    public int orangesRottingMyApproachOld(int[][] grid) {
        int mins = 0;
        for (int[] r: grid) {
            for (int c: r) {
                if (c==2) return -1; // all oranges cannot rot
            }
        }


        // at last
        for (int[] r: grid) {
            for (int c: r) {
                if (c==1) return -1; // all oranges cannot rot
            }
        }
        return mins;
    }

    private void dfs() {}

}