package Algorithms.DisjointSetUnion;

/**
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 26 March 2025
 */
public class NumberOfOperationsToMakeNetworkConnected {
    public static void main(String[] args) {
        int n = 4, connections[][] = {{0,1},{0,2},{1,2}};
        System.out.println("makeConnected(n, connections) => " + makeConnected(n, connections));
    }

    public static int makeConnected(int n, int[][] connections) {
        if (connections.length < n - 1) return -1;
        UnionFind uf = new UnionFind(n);
        for (int[] c : connections) uf.union(c[0], c[1]);
        return uf.cables; // only one parent
    }
    static class UnionFind {
        int[] parent;
        int cables; // or groups=n=1
        public UnionFind(int n) {
            parent = new int[n];
            cables = n-1;
            for (int i = 0; i < n; i++) parent[i] = i;
        }
        public int find(int x) {
            if (parent[x] != x) parent[x] = find(parent[x]);
            return parent[x];
        }
        public void union(int x, int y) {
            int rootX = find(x);
            int rootY = find(y);
            if (rootX != rootY) {
                parent[rootX] = rootY;
                cables--; // used one cable if they don't have common parent
            }
        }
    }





    /**

    [[0,1],[0,2],[0,3],[1,2]]

            0--1 4
            |\/
            2 3  5

        Here we can't complete the connection with 4 & 5

        PATTERNS:
        ---------
        1) Finally connection should not be cyclic
        2) We can delete extra cable but we cannot add one
        3) We can take any one of the cyclic cable and attach it into non-connected nodes
        4) Only one parent in the final connection
     */
    int[] par, rank;
    public int makeConnectedMyApproach(int n, int[][] connections) {
        if (n-1 > connections.length) return -1; // not enough cables

        par=new int[n];
        rank=new int[n];
        for(int i=0; i<n; i++) par[i]=i;

        for (int[] c: connections) {
            union(c[0], c[1]);
        }

        int p=0;
        for (int i=0; i<n; i++) if (i==par[i]) p++;
        return p-1; // only one parent has to be there, if it's more than 1, then those are disjointSets / not connected
    }
    public int makeConnectedMyApproach2(int n, int[][] connections) {
        if (n-1 > connections.length) return -1; // not enough cables to connect all n nodes

        par=new int[n];
        rank=new int[n];
        for(int i=0; i<n; i++) par[i]=i;

        int extraCables=0; // optional as we already check "if (n-1 > connections.length) return -1;"
        for (int[] c: connections) {
            if(!union(c[0], c[1])) extraCables++;
        }

        int disjointSets=0;
        for (int i=0; i<n; i++) if (i==par[i]) disjointSets++;

        if (disjointSets-1 > extraCables) return -1; // one disjointSet is parent
        else return disjointSets-1;
    }

    private boolean union(int a, int b) {
        a=find(a);
        b=find(b);

        if (a==b) return false;

        if(rank[b] < rank[a]) par[b]=a;
        else if(rank[a] < rank[b]) par[a]=b;
        else {
            par[b]=a;
            rank[a]++;
        }
        return true;
    }
    private int find(int i){
        while(i != par[i]) i=par[i];
        return i;
    }
}
