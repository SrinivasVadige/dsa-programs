package Algorithms.DisjointSetUnion;

import java.util.Arrays;

/**
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 22 March 2025
 */
public class NumberOfProvinces {
    static int[] par, rank;
    public static void main(String[] args) {
        int[][] isConnected = {
            {1, 1, 0},
            {1, 1, 0},
            {0, 0, 1}
        };
        System.out.println("findCircleNum(isConnected) => " + findCircleNum(isConnected));
        System.out.println("findCircleNum2(isConnected) => " + findCircleNum2(isConnected));
        System.out.println("findCircleNumUsingAdjacencyMatrix(isConnected) => " + findCircleNumUsingAdjacencyMatrix(isConnected));

    }

    public static int findCircleNum(int[][] isConnected) {
        int n = isConnected.length;
        par = new int[n];
        rank = new int[n];
        for (int i = 0; i < n; i++) par[i] = i;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (isConnected[i][j] == 1) union(i, j);
            }
        }
        int count = 0;
        for (int i = 0; i < n; i++) if (par[i] == i) count++;
        return count;
    }
    public static void union(int a, int b) {
        int pa = find(a);
        int pb = find(b);
        if (pa != pb) {
            if (rank[pa] > rank[pb]) {
                par[pb] = pa;
            } else if (rank[pa] < rank[pb]) {
                par[pa] = pb;
            } else {
                par[pb] = pa;
                rank[pa]++;
            }
        }
    }
    public static int find(int a) {
        if (par[a] == a) return a;
        return par[a] = find(par[a]); // or return find(par[a]);
        // or while(par[a] != a) i=par[a]; return a;
    }





    public static int findCircleNum2(int[][] isConnected) {
        int n=isConnected.length;
        par = new int[n];
        rank = new int[n];
        for(int i=0; i<n; i++) par[i]=-1;

        for (int i=0; i<n; i++) {
            for (int j=0; j<n; j++) if(isConnected[i][j]==1) union2(i,j);
        }
        return (int)Arrays.stream(par).filter(i->i==-1).count();
    }
    private static void union2(int a, int b){
        int rA=find2(a), rB=find2(b); // roots
        if (rA==rB) return;
        if(rank[rA] > rank[rB]) par[rB]=rA;
        else if(rank[rA] < rank[rB]) par[rA]=rB;
        else {
            par[rB]=rA;
            rank[rA]++;
        }
    }
    private static int find2(int i) {
        if(par[i]==-1) return i;
        return find2(par[i]);
    }







    public static int findCircleNumUsingAdjacencyMatrix(int[][] isConnected) {
        int n = isConnected.length;
        boolean[] visited = new boolean[n];
        int count = 0;
        for (int i = 0; i < n; i++) { // each city
            if (!visited[i]) {
                dfs(isConnected, visited, i);
                count++; // newly visited city
            }
        }
        return count;
    }
    // trav each connection and mark it visited
    private static void dfs(int[][] isConnected, boolean[] visited, int i) {
        visited[i] = true;
        for (int j = 0; j < isConnected.length; j++) { // each connection
            if (isConnected[i][j] == 1 && !visited[j]) dfs(isConnected, visited, j);
        }
    }


}
