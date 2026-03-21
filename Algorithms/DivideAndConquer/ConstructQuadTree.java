package Algorithms.DivideAndConquer;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 20 March 2026
 * @link 427. Construct Quad Tree <a href="https://leetcode.com/problems/construct-quad-tree/">LeetCode Link</a>
 * @topics Divide and Conquer, Tree, Matrix, Array, Recursion
 * @companies Uber(8), Google(7), Amazon(4), Bloomberg(2), Palantir Technologies(2)
 */
public class ConstructQuadTree {
    public static class Node {
        public boolean val;
        public boolean isLeaf;
        public Node topLeft;
        public Node topRight;
        public Node bottomLeft;
        public Node bottomRight;
        public Node() {} // val and isLeaf are false by default
        public Node(boolean val, boolean isLeaf) {this.val = val; this.isLeaf = isLeaf;}
        public Node(boolean val, boolean isLeaf, Node topLeft, Node topRight, Node bottomLeft, Node bottomRight) {
            this.val = val;
            this.isLeaf = isLeaf;
            this.topLeft = topLeft;
            this.topRight = topRight;
            this.bottomLeft = bottomLeft;
            this.bottomRight = bottomRight;
        }
    }
    public static void main(String[] args) {
        int[][] grid = {{1,1,1,1,0,0,0,0},{1,1,1,1,0,0,0,0},{1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1},{1,1,1,1,0,0,0,0},{1,1,1,1,0,0,0,0},{1,1,1,1,0,0,0,0},{1,1,1,1,0,0,0,0}};
        Node root = constructUsingDivideAndConquer1(grid);
        printTree(root);
    }

    private static void printTree(Node root) {
        Queue<Node> queue = new LinkedList<>();
        System.out.print("\nTree: ");
        queue.add(root);
        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                Node node = queue.poll();
                System.out.print(node == null ? "null " : node.val + " ");
                if (node == null) continue;
                queue.add(node.topLeft);
                queue.add(node.topRight);
                queue.add(node.bottomLeft);
                queue.add(node.bottomRight);
            }
        }

    }



    /**
     * @TimeComplexity O(n²logn) - logn cause we keep splitting the grid like n → n/2 → n/4 → ... → 1 ---> logn levels ---> At each level, you scan total n² cells
     * @SpaceComplexity O(n²) - for recursion stack
     */
    public static Node constructUsingDivideAndConquer1(int[][] grid){ // my custom approach - early thinking
        int n = grid.length;
        return dfs(grid, new int[]{0,0}, new int[]{0, n-1}, new int[]{n-1, 0}, new int[]{n-1, n-1});
    }

    private static Node dfs(int[][] grid, int[] tl, int[] tr, int[] bl, int[] br) {
        Boolean isAllOnes = isAllOnes(grid, tl, tr, bl, br);
        if (isAllOnes != null) {
            return new Node(isAllOnes, true);
        }

        Node node = new Node(true, false);
        // divide curr grid into 4 parts again
        int midRow = (tl[0] + bl[0]) / 2;
        int midCol = (tl[1] + tr[1]) / 2;

        node.topLeft = dfs(
            grid,
            new int[]{tl[0], tl[1]},
            new int[]{tl[0], midCol},
            new int[]{midRow, tl[1]},
            new int[]{midRow, midCol}
        );

        node.topRight = dfs(
            grid,
            new int[]{tl[0], midCol + 1},
            new int[]{tl[0], tr[1]},
            new int[]{midRow, midCol + 1},
            new int[]{midRow, tr[1]}
        );

        node.bottomLeft = dfs(
            grid,
            new int[]{midRow + 1, tl[1]},
            new int[]{midRow + 1, midCol},
            new int[]{bl[0], tl[1]},
            new int[]{bl[0], midCol}
        );

        node.bottomRight = dfs(
            grid,
            new int[]{midRow + 1, midCol + 1},
            new int[]{midRow + 1, br[1]},
            new int[]{br[0], midCol + 1},
            new int[]{br[0], br[1]}
        );

        return node;
    }

    private static Boolean isAllOnes(int[][] grid, int[] tl, int[] tr, int[] bl, int[] br) {
        int currRow = tl[0];
        int currCol = tl[1];
        int maxRow = bl[0];
        int maxCol = tr[1];

        int ones = 0, zeros = 0;
        for (int r=currRow; r<=maxRow; r++) {
            for (int c=currCol; c<=maxCol; c++) {
                if (grid[r][c] == 1) ones++; else zeros++;
            }
        }

        if (zeros == 0) return true; // only ones
        else if (ones == 0) return false;
        else return null;
    }












    /**
     * @TimeComplexity O(n²logn) - logn cause we keep splitting the grid like n → n/2 → n/4 → ... → 1 ---> logn levels ---> At each level, you scan total n² cells
     * @SpaceComplexity O(n²) - for recursion stack
     */
    public static Node constructUsingDivideAndConquer2(int[][] grid) {
        return dfs(grid, 0, 0, grid.length);
    }
    private static Node dfs(int[][] grid, int r, int c, int n) {
        // Return a leaf node if all values are the same.
        if (sameValue(grid, r, c, n)) {
            return new Node(grid[r][c] == 1, true);
        } else {
            Node root = new Node(false, false);

            // Recursive call for the four sub-matrices.
            root.topLeft = dfs(grid, r, c, n/2);
            root.topRight = dfs(grid, r, c + n/2, n/2);
            root.bottomLeft = dfs(grid, r + n/2, c, n/2);
            root.bottomRight = dfs(grid, r + n/2, c + n/2, n/2);

            return root;
        }
    }
    private static boolean sameValue(int[][] grid, int r, int c, int n) {
        for (int i = r; i < r + n; i++) {
            for (int j = c; j < c + n; j++)
                if (grid[i][j] != grid[r][c])
                    return false;
        }
        return true;
    }













    /**
     * @TimeComplexity O(n²) - 	No re-scanning, bottom-up
     * @SpaceComplexity O(n²)
     */
    public static Node constructUsingDivideAndConquer3(int[][] grid) {
        return dfsEnhanced(grid, 0, 0, grid.length);
    }
    private static Node dfsEnhanced(int[][] grid, int r, int c, int n) {
        // Return a leaf node if the matrix size is one.
        if (n == 1) {
            return new Node(grid[r][c] == 1, true);
        }

        // Recursive calls to the four sub-matrices.
        Node topLeft = dfsEnhanced(grid, r, c, n/2);
        Node topRight = dfsEnhanced(grid, r, c + n/2, n/2);
        Node bottomLeft = dfsEnhanced(grid, r + n/2, c, n/2);
        Node bottomRight = dfsEnhanced(grid, r + n/2, c + n/2, n/2);

        // If the four returned nodes are leaf and have the same values
        // Return a leaf node with the same value.
        if (topLeft.isLeaf && topRight.isLeaf && bottomLeft.isLeaf && bottomRight.isLeaf
                && topLeft.val == topRight.val && topLeft.val == bottomLeft.val && topLeft.val == bottomRight.val) {
            return new Node(topLeft.val, true);
        }

        // If the four nodes aren't identical, return a non-leaf node with corresponding child pointers.
        return new Node(false, false, topLeft, topRight, bottomLeft, bottomRight);
    }
}
