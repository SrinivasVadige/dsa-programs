package DataStructures;

/**
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 20 March 2025
 *
 * Disjoint Set Union (DSU) or Union Find Data Structure
 */
public class DisjointSetUnion {
    public static void main(String[] args) {
        DisjointSetUnion uf = new DisjointSetUnion(10);
        uf.union(0, 1);
        uf.union(2, 3);
        uf.union(4, 5);
        System.out.println(uf.connected(0, 1)); // true
        System.out.println(uf.connected(0, 2)); // false
        uf.union(0, 2);
        System.out.println(uf.connected(0, 1)); // true
        System.out.println(uf.componentSize(0)); // 3
        System.out.println(uf.numComponents()); // 7
        System.out.println(uf.largestComponentSize()); // 3
        System.out.println(uf.numComponentsOfSize(3)); // 1
        System.out.println(uf.numComponentsOfSize(1)); // 6

        int[] parents = uf.getParents();
        int[] ranks = uf.getRank();
        for (int i = 0; i < parents.length; i++) System.out.println(parents[i] + " " + ranks[i]);

        uf.clear();
        System.out.println(uf.numComponents()); // 10
        uf.clear(5);
        System.out.println(uf.numComponents()); // 5
    }

    private int[] parent;
    private int[] rank;
    private int numComponents;

    public DisjointSetUnion(int size) {
        parent = new int[size];
        rank = new int[size];
        numComponents = size;

        for (int i = 0; i < size; i++) {
            parent[i] = i;
            rank[i] = 0;
        }
    }

    public int find(int i) {
        if (i != parent[i]) parent[i] = find(parent[i]);
        return parent[i];
    }
    public int find2(int i) {
        return i == parent[i] ? i : (parent[i] = find(parent[i]));
    }
    public int find3(int i) {
        if (i == parent[i]) return i;
        return parent[i] = find(parent[i]); // or return find(parent[i]);
    }
    public int find4(int i) {
        while (i != parent[i]) {
            parent[i] = parent[parent[i]]; // optional: path compression
            i = parent[i];
        }
        return i;
    }
    // when default parent = -1
    public int find5(int i) {
        if (parent[i] == -1) return i;
        return parent[i] = find(parent[i]); // or return find(parent[i]);
    }

    // Get roots, compare roots and connect roots not x,y nodes. Increment rank of latest root
    public void union(int x, int y) {
        int rootX = find(x);
        int rootY = find(y);

        if (rootX == rootY) return;

        if (rank[rootX] < rank[rootY]) {
            parent[rootX] = rootY;
        } else if (rank[rootX] > rank[rootY]) {
            parent[rootY] = rootX;
        } else {
            parent[rootY] = rootX;
            rank[rootX]++;
        }

        numComponents--;
    }
    public int union2(int x, int y) { // returns if they're connected or not and use this count to find number of components
        int rootX = find(x);
        int rootY = find(y);

        if (rootX == rootY) return 0;

        if (rank[rootX] < rank[rootY]) {
            parent[rootX] = rootY;
        } else if (rank[rootX] > rank[rootY]) {
            parent[rootY] = rootX;
        } else {
            parent[rootY] = rootX;
            rank[rootX]++;
        }
        return 1;
    }

    public boolean connected(int x, int y) {
        return find(x) == find(y);
    }

    public int componentSize(int x) {
        return rank[find(x)];
    }

    public int getNumComponents() {
        return numComponents;
    }

    public int numComponents() {
        int count = 0;
        for (int i = 0; i < parent.length; i++) {
            if (parent[i] == i) {
                count++;
            }
        }
        return count;
    }


    public int largestComponentSize() {
        int largest = 0;
        for (int i = 0; i < rank.length; i++) {
            largest = Math.max(largest, rank[i]);
        }
        return largest;
    }

    public int numComponentsOfSize(int size) {
        int count = 0;
        for (int i = 0; i < rank.length; i++) {
            if (rank[i] == size) {
                count++;
            }
        }
        return count;
    }

    public int[] getParents() {
        return parent;
    }

    public int[] getRank() {
        return rank;
    }

    // no need for extra size variable
    public int getSize() {
        return parent.length;
    }

    public void clear() {
        for (int i = 0; i < parent.length; i++) {
            parent[i] = i;
            rank[i] = 0;
        }
    }

    public void clear(int size) {
        parent = new int[size];
        rank = new int[size];

        for (int i = 0; i < size; i++) {
            parent[i] = i;
            rank[i] = 0;
        }
    }

    public void clear(int size, int[] parents, int[] ranks) {
        parent = parents;
        rank = ranks;

        for (int i = 0; i < size; i++) {
            parent[i] = i;
            rank[i] = 0;
        }
    }

    public void clear(int size, int[] parents) {
        parent = parents;
        rank = new int[size];

        for (int i = 0; i < size; i++) {
            rank[i] = 0;
        }
    }

    public void clear(int size, int[] parents, int rankValue) {
        parent = parents;
        rank = new int[size];

        for (int i = 0; i < size; i++) {
            rank[i] = rankValue;
        }
    }

    public void clear(int size, int rankValue) {
        parent = new int[size];
        rank = new int[size];

        for (int i = 0; i < size; i++) {
            parent[i] = i;
            rank[i] = rankValue;
        }
    }

    public void clear(int size, int rankValue, int[] ranks) {
        parent = new int[size];
        rank = ranks;

        for (int i = 0; i < size; i++) {
            parent[i] = i;
            rank[i] = rankValue;
        }
    }

    public void clear(int size, int[] parents, int[] ranks, int rankValue) {
        parent = parents;
        rank = ranks;

        for (int i = 0; i < size; i++) {
            parent[i] = i;
            rank[i] = rankValue;
        }
    }

    public void clear(int size, int[] parents, int rankValue, int[] ranks) {
        parent = parents;
        rank = ranks;

        for (int i = 0; i < size; i++) {
            parent[i] = i;
            rank[i] = rankValue;
        }
    }

    public void clear(int size, int rankValue, int[] ranks, int[] parents) {
        parent = parents;
        rank = ranks;

        for (int i = 0; i < size; i++) {
            parent[i] = i;
            rank[i] = rankValue;
        }
    }


}

