package Algorithms.Graphs;

import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

/**
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 07 May 2025
 */
public class NearestExitFromEntranceInMaze {
    public static void main(String[] args) {
        // Example usage
        char[][] maze = {
            {'+', '+', '+', '+'},
            {'+', '.', '.', '+'},
            {'+', '+', '.', '+'},
            {'.', '.', '.', '+'},
            {'.', '+', '.', '+'}
        };
        int[] entrance = {1, 1};
        System.out.println("nearestExit(maze, entrance): " + nearestExit(maze, entrance));

        maze = new char[][] {
            {'+', '+', '+', '+'},
            {'+', '.', '.', '+'},
            {'+', '+', '.', '+'},
            {'.', '.', '.', '+'},
            {'.', '+', '.', '+'}
        };
        System.out.println("nearestExitMyBfsApproach(maze, entrance): " + nearestExitMyBfsApproach(maze, entrance));
        System.out.println("nearestExitMyBfsApproach2(maze, entrance): " + nearestExitMyBfsApproach2(maze, entrance));
        System.out.println("nearestExitUsingMyDfsApproachTLE(maze, entrance): " + nearestExitUsingMyDfsApproachTLE(maze, entrance));
    }
    static int[][] dirs = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}}; // right, down, left, up

    public static int nearestExit(char[][] maze, int[] entrance) {
        int m = maze.length;
        int n = maze[0].length;
        Queue<int[]> queue = new LinkedList<>();
        queue.add(new int[]{entrance[0], entrance[1], 0}); // {row, col, steps}
        maze[entrance[0]][entrance[1]] = '+'; // Mark entrance as visited

        while (!queue.isEmpty()) {
            int[] current = queue.poll();
            int row = current[0], col = current[1], steps = current[2];

            for (int[] dir : dirs) {
                int x = row + dir[0];
                int y = col + dir[1];

                if (x >= 0 && x < m && y >= 0 && y < n && maze[x][y] == '.') {
                    if (x == 0 || x == m - 1 || y == 0 || y == n - 1) {
                        return steps + 1; // Exit found
                    }
                    queue.add(new int[]{x, y, steps + 1});
                    maze[x][y] = '+'; // Mark as visited
                }
            }
        }
        return -1; // No exit found
    }






    /**
        GIVEN:
        ------
        1) "." == empty cells
        2) "+" == wall

        PATTERNS:
        ----------
        1) Mark entrance i,j as visited
        2) move all 4 dirs until i==0 || j==0 || i==m-1 || j==n-1

        APPROACH 1: dfs
        -----------
        1) 4 dirs loop using helper() recursion
        2) MIN of those 4
        3) Will get TLE -- cause we trav all nodes and find minPath from all possibilities


        APPROACH 2: bfs
        -----------
        1) Trav each node till you find the boundary tile
        2) Mark the tile as visited (i.e. change it to wall or in extra seen variable)
        3) Stop traversing when you find the first boundary tile


        APPROACH 3: --- WON'T WORK
        -----------
        1) Adjacency Matrix Graph ---- number of node in rows != cols

        0 ["+","+",".","+"]
        1 [".",".",".","+"]
        2 ["+","+","+","."]
     */
    public static int nearestExitMyBfsApproach(char[][] maze, int[] entrance) {
        int m = maze.length, n=maze[0].length;
        Set<String> seen = new HashSet<>();
        Queue<int[]> q = new LinkedList<>();
        q.add(new int[]{entrance[0], entrance[1], 0});
        seen.add(entrance[0] + "," + entrance[1]);

        while(!q.isEmpty()) {
            int[] cell = q.poll();
            if ((cell[0]==0 || cell[0]==m-1 || cell[1]==0 || cell[1]==n-1) && cell[2]!=0) {
                return cell[2];
            }

            for(int[] dir: dirs) {
                int nextI = cell[0]+dir[0];
                int nextJ = cell[1]+dir[1];
                if ( seen.contains(nextI + "," + nextJ) || nextI<0 || nextJ<0 || nextI>=m || nextJ>=n || maze[nextI][nextJ]=='+')continue;
                q.add(new int[]{nextI, nextJ, cell[2]+1});
                seen.add(nextI + "," + nextJ);
                // If you add seen.add() at while loop entrance -- (just after .pop()) instead of here (just after .add())
                // That will mark the current cell as visited after dequeuing it, but by then it might have already been added to the queue multiple times from other directions.
                // i.e siblings will take that unmarked cell as it's child
            }
        }
        return -1;
    }






    public static int nearestExitMyBfsApproach2(char[][] maze, int[] entrance) {
        int rN = maze.length, cN = maze[0].length;
        boolean[][] visited = new boolean[rN][cN];
        int curR = entrance[0], curC = entrance[1];
        visited[curR][curC] = true;
        Queue<int[]> q = new ArrayDeque<>();
        q.offer(entrance);
        int level = 0;
        while(!q.isEmpty()) {
            int size = q.size();
            for(int k = 0; k<size; k++) { // for each level
                int[] cur = q.poll();
                for(int[] dir: dirs) {
                        int r = cur[0] + dir[0];
                        int c = cur[1] + dir[1];
                        if(r>=0 && r<rN && c>=0 && c<cN && maze[r][c]=='.' && !visited[r][c]) {
                            visited[r][c] = true;
                            if(r==0 || r==rN-1 || c==0 || c==cN - 1) {
                                return level + 1;
                            } else {
                                q.offer(new int[] {r, c});
                            }
                    }
                }
            }
            level++; // increment the level
        }
        return -1;
    }






    // TLE
    public static int nearestExitUsingMyDfsApproachTLE(char[][] maze, int[] entrance) {
        int m = maze.length, n=maze[0].length;
        int res = helper(maze, entrance[0], entrance[1], new HashSet<>(), 0, m, n);
        return res>100?-1:res;
    }
    private static int helper(char[][] maze, int i, int j, Set<String> seen, int steps, int m, int n){
        // System.out.printf("i:%s, j:%s\n", i, j);
        if((i==0 || j==0 || i==m-1 || j==n-1) & steps!=0) return steps; // BOUNDARY
        int res = Integer.MAX_VALUE;
        seen.add(i+","+j);
        for(int[] dir: dirs) {
            int nextI = i+dir[0];
            int nextJ = j+dir[1];
            if (
                seen.contains(nextI + "," + nextJ)
                || nextI<0 || nextJ<0 || nextI>=m || nextJ>=n || maze[nextI][nextJ]=='+'
            ) continue;
            int dirRes = helper(maze, nextI, nextJ, seen, steps+1, m, n);
            res = Math.min(res, dirRes);
            // System.out.printf("dirRes:%s, res:%s\n", dirRes, res);
            seen.remove(nextI + "," + nextJ);
        }
        return res;
    }
}
