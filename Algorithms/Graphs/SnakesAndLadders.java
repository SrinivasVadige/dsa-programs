package Algorithms.Graphs;

import java.util.*;

/**
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 02 Jan 2026
 * @link 909. Snakes and Ladders <a href="https://leetcode.com/problems/snakes-and-ladders/">LeetCode Link</a>
 * @topics Array, Matrix, DFS, BFS
 * @companies Amazon(5), Google(2), Microsoft(2), Meta(5), Bloomberg(4), Apple(3), TikTok(3), Goldman Sachs(3), Cisco(2), On the sensor(2), Zomato(2)
 */
public class SnakesAndLadders {
    public static void main(String[] args) {
        int[][] board = {{-1,1,2,-1},{2,13,15,-1},{-1,10,-1,-1},{-1,6,2,8}};
        System.out.println("snakesAndLadders Using Bfs1 -> " + snakesAndLaddersUsingBfs1(board));
        System.out.println("snakesAndLadders Using Bfs Dijkstra's Algorithm -> " + snakesAndLaddersUsingBfs4DijkstrasAlgo(board));
    }




    /**
     * @TimeComplexity O(n^2)
     * @SpaceComplexity O(n^2)
    */
    static class Pair {int label; int steps; Pair(int label, int steps) {this.label = label; this.steps = steps;}public String toString(){return "("+label+ ", " + steps+")";}}
    public static int snakesAndLaddersUsingBfs1(int[][] board) {
        int n = board.length;

        Queue<Pair> q = new LinkedList<>(); // Pair or int[2]
        boolean[] seen = new boolean[n * n + 1];

        q.add(new Pair(1, 0));
        seen[1] = true;

        while (!q.isEmpty()) {
            Pair cur = q.poll();
            int curLabel = cur.label;
            int curSteps = cur.steps;

            if (curLabel == n * n) return curSteps;

            for (int dice = 1; dice <= 6 && curLabel+dice <= n*n; dice++) {

                int nextLabel = curLabel+dice;
                int[] nextPos = labelToPosition1(nextLabel, n); // or labelToPosition2InReversedBoard(nextLabel, n); ---> board[i] = board[n-1-i];
                int nr = nextPos[0], nc = nextPos[1];

                /*
                apply snake or ladder immediately
                    ---> that means right now, we don't care if the nextLabel has another ladder or snake
                And here, we check only the nextLabel position but not the currentLabel
                You must NOT chain jumps
                One dice roll ---> at most one jump

                 */
                if (board[nr][nc] != -1) nextLabel = board[nr][nc];

                if (seen[nextLabel]) continue;
                seen[nextLabel] = true;
                q.add(new Pair(nextLabel, curSteps+1));
            }
        }

        return -1;
    }




    /**
     * @TimeComplexity O(n^2)
     * @SpaceComplexity O(n^2)
    */
    public int snakesAndLaddersUsingBfs2(int[][] board) {
        int n = board.length;

        Queue<Integer> q = new LinkedList<>();
        boolean[] seen = new boolean[n * n + 1];

        q.add(1);
        seen[1] = true;
        int steps = 0;

        while (!q.isEmpty()) {
            int size = q.size();
            while(size-- > 0) {
                int curLabel = q.poll();
                if (curLabel == n * n) return steps;

                for (int dice = 1; dice <= 6 && curLabel+dice <= n*n; dice++) {

                    int nextLabel = curLabel+dice;
                    int[] nextPos = labelToPosition1(nextLabel, n);
                    int nr = nextPos[0], nc = nextPos[1];

                    /*
                    apply snake or ladder immediately
                        ---> that means right now, we don't care if the nextLabel has another ladder or snake
                    And here, we check only the nextLabel position but not the currentLabel
                    You must NOT chain jumps
                    One dice roll ---> at most one jump

                    */
                    if (board[nr][nc] != -1) nextLabel = board[nr][nc];

                    if (seen[nextLabel]) continue;
                    seen[nextLabel] = true;
                    q.add(nextLabel);
                }
            }
            steps++;
        }

        return -1;
    }



    /**

        n=2
                 r n-r
                 ↓ ↓
cols ->   0  1
        [ 4, 3 ] 0 2
        [ 1, 2 ] 1 1



        n=3
                   r n-r
                   ↓ ↓
cols ->   0  1  2
        [ 7, 8, 9] 0 3
        [ 6, 5, 4] 1 2
        [ 1, 2, 3] 2 1


        n=4
                       r n-r
                       ↓ ↓
cols ->   0  1  2  3
        [16,15,14,13]  0 4
        [ 9,10,11,12]  1 3
        [ 8, 7, 6, 5]  2 2
        [ 1, 2, 3, 4]  3 1


        even (n-r) ==> col nums are decreasing to right
        odd  (n-r) ==> col nums are increasing to right

        and we know that we can use r*cols+c to convert (r,c) to num
        and using, r=num/cols, c=num%cols to convert back num to (r,c)
     */
    private static int[] labelToPosition1(int label, int n) {
        label--;

        int r = n-1-(label/n); // num/cols
        int c = label%n; // num%cols
        boolean isDecreasingRow = (n-r) % 2 == 0;
        if (isDecreasingRow) c = n-1-c;

        return new int[]{r,c};
    }
    private static int[] labelToPosition2InReversedBoard(int label, int n) {
        int r = (label-1)/n; // num/cols
        int c = (label-1)%n; // num%cols
        if (r%2==1) c = n-1-c;
        return new int[]{r,c};
    }
    private static int[] labelToPosition3(int label, int n) {
        label--;
        int rowFromBottom = label/n;       // Which column is it from the bottom?
        int col = label % n;

        int r = n-1 - label/n;   // Convert to top-down r
        int c;

        // Even or odd row?
        if(rowFromBottom % 2 == 0){
            c = col;
        }else{
            c = n-1-col;
        }
        return new int[]{r, c};
    }
    private static int positionToLabel4(int r, int c, int n) {
        boolean isDecreasingRow = (n-r) % 2 == 0;
        if (isDecreasingRow) c = n-1-c;
        r = n-1-r;
        return r*n+c+1;
    }










    /**
     * @TimeComplexity O(n^2)
     * @SpaceComplexity O(n^2)
    */
    public static int snakesAndLaddersUsingBfs3(int[][] board) {
        int n = board.length;
        int[][] cells = new int[n*n+1][2]; // instead of labelToPosition1(), prepare the 2d array with custom (r,c) as per problem statement
        int label = 1;
        Integer[] columns = new Integer[n];
        for (int i = 0; i < n; i++) {
            columns[i] = i;
        }
        for (int row = n-1; row >= 0; row--) {
            for (int column : columns) {
                cells[label++] = new int[]{row, column};
            }
            Collections.reverse(Arrays.asList(columns)); // increasing order in odd row and vice-versa
        }
        // System.out.println(Arrays.deepToString(cells));
        int[] dist = new int[n * n + 1];
        Arrays.fill(dist, -1);
        Queue<Integer> q = new LinkedList<Integer>();
        dist[1] = 0;
        q.add(1);
        while (!q.isEmpty()) {
            int curr = q.remove();
            for (int next = curr + 1; next <= Math.min(curr + 6, n * n); next++) {
                int row = cells[next][0], column = cells[next][1];
                int destination = board[row][column] != -1 ? board[row][column] : next;
                if (dist[destination] == -1) {
                    dist[destination] = dist[curr] + 1;
                    q.add(destination);
                }
            }
        }
        return dist[n * n];
    }





    /**
     * @TimeComplexity O(n^2 * logn)
     * @SpaceComplexity O(n^2)
    */
    public static int snakesAndLaddersUsingBfs4DijkstrasAlgo(int[][] board) {
        int n = board.length;
        int[][] cells = new int[n*n + 1][2];
        int label = 1;
        Integer[] columns = new Integer[n];
        for (int i = 0; i < n; i++) {
            columns[i] = i;
        }
        for (int row = n - 1; row >= 0; row--) {
            for (int column : columns) {
                cells[label++] = new int[]{row, column};
            }
            Collections.reverse(Arrays.asList(columns));
        }
        int[] dist = new int[n * n + 1];
        Arrays.fill(dist, -1);

        class Vertex implements Comparable<Vertex> {
            final int distance, label;

            public Vertex(int distance, int label) {
                this.distance = distance;
                this.label = label;
            }

            public int compareTo(Vertex v) {
                return this.distance - v.distance;
            }
        }

        PriorityQueue<Vertex> pq = new PriorityQueue<>(); // or PriorityQueue<int[]> pq = new PriorityQueue<>((a,b)-> a[0]-b[0]);
        dist[1] = 0;
        pq.add(new Vertex(0, 1));
        while (!pq.isEmpty()) {
            Vertex vertex = pq.remove();
            int d = vertex.distance, curLabel = vertex.label;
            if (d != dist[curLabel]) {
                continue;
            }
            for (int nextLabel = curLabel+1; nextLabel <= Math.min(curLabel + 6, n * n); nextLabel++) {
                int row = cells[nextLabel][0], column = cells[nextLabel][1];
                int destination = board[row][column] != -1 ? board[row][column] : nextLabel;
                if (dist[destination] == -1 || dist[curLabel] + 1 < dist[destination]) {
                    dist[destination] = dist[curLabel] + 1;
                    pq.add(new Vertex(dist[destination], destination));
                }
            }
        }
        return dist[n * n];
    }












    static class PairOld{
        int label;
        int steps;
        boolean isPrevJump;
        PairOld(int label, int steps, boolean isPrevJump) {
            this.label = label;
            this.steps = steps;
            this.isPrevJump = isPrevJump;
        }
        @Override
        public String toString(){
            // return String.format("{label: %s, steps: %s, isPrevJump: %s}\n", label, steps, isPrevJump);
            // int[] pos = labelToPosition1(label);
            // return String.format("%s(%s) ", label, board[pos[0]][pos[1]]);
            return "("+label+ ", " + steps+")";
        }
    }
    /**

        board = [
        [-1,  1,  2, -1],
        [ 2, 13, 15, -1],
        [-1, 10, -1, -1],
        [-1,  6,  2,  8]
        ]
        n = 4, labels are:
        [16, 15, 14, 13]
        [ 9, 10, 11, 12]
        [ 8,  7,  6,  5]
        [ 1,  2,  3,  4]

        shortest path = 1->[7->10]->16

        label1=-1 ---> so, we can go to [2,3,4,5,6,7], steps till now = 0
        label7=10 ---> jump to 10, steps till now = 1
        label10=13 ---> but don't jump again ---> so, we can go to [11,12,13,14,15,16], steps till now = 1
        label16 ---> steps till now = 2

        (label, steps)
        [(8, 1), (9, 2), (10, 2), (11, 2), (12, 2), (10, 1)] ---> fix this --- multiple 10s are adding in q

        (10, 2) was added by label4
        (10, 1) was added by label7

        so, in BFS always jump when we see a snake or ladder


     */

    public int snakesAndLaddersUsingBfsNotWorking(int[][] board) {
        // Solution.board = board;
        int n = board.length;
        int minSteps = Integer.MAX_VALUE;
        Set<Integer> seen = new HashSet<>();

        // //System.out.println(Arrays.toString(labelToPosition1(1)));
        // //System.out.println(positionToLabel(3,4));

        Queue<PairOld> q = new LinkedList<>();
        q.add(new PairOld(1,0,false));
        seen.add(1);

        while (!q.isEmpty()) {
            // System.out.println(q);

            PairOld pair = q.poll();
            int label = pair.label;
            int steps = pair.steps;
            boolean isPrevJump = pair.isPrevJump;

            if (label >= n*n) {
                return steps;
            }

            int[] position = labelToPosition1(label, n);
            int r = position[0];
            int c = position[1];
            boolean isCurrJump = board[r][c] != -1 && !isPrevJump;

            //System.out.printf("board[%s][%s]=%s\n", r, c, board[r][c]);

            if (isCurrJump) {
                if(seen.add(board[r][c])) q.add(new PairOld(board[r][c], steps, true));
                continue;
            }


            for (int i=1; i<=6 && i+label<=n*n; i++) {
                if(seen.add(i+label)) q.add(new PairOld(i+label, steps+1, isCurrJump));
            }
        }

        return -1;
    }
}
