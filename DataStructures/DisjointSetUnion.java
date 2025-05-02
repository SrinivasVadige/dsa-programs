package DataStructures;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 20 March 2025
 *
 * Disjoint Set Union (DSU) or Union Find Data Structure

   The Disjoint sets are sets that have no elements in common. Set A: {1, 2, 3}; Set B: {4, 5, 6};

   it represents a collection of sets, often visualized as a forest of trees, where each set is represented by a tree. This structure does not follow a strict linear arrangement like arrays or linked lists

   This data structure keeps track of elements split into one or more disjoint sets. It has two primary operations:
   1. Find(x)- find the rootNode of this node
   2. Union(x, y)- connect these two nodes

   * Here we don’t add, remove, or edit. We connect nodes and check a specific node’s parent.
   * But some implementations use hash maps or dynamic arrays instead of fixed arrays to allow dynamic elements.
   * DSU does not inherently allow cyclic connections. When given an edge then we primarily detect if those 2 nodes have the same root(representative), and then we skip that edge. So, no cycles.
   * Here we don’t need to maintain order like BST
   * Use rank to determine which one will be the parent. In the disjoint edge, the bigger rank node is the parent and we increase one node’s rank if those nodes have the same rank and now make the bigger rank node as a parent.

    Algorithms:
   	•	Detecting cycles in undirected graphs.
   	•	krushkal's Minimum Spanning Tree (MST)
   	•	Grid percolation
   	•	Least common ancestor in tree
   	•	Image processing
   Dynamic Connectivity Problems:
   	•	Network connectivity.
   	•	Social network friend groups.
   Clustering:
   	•	Grouping related data points.

   Example:
   1. Magnets 🧲 group — let’s say we have 15 different sizes of magnets and nearby magnets merge with each other and form a group. So, we have a few groups like blue, yellow, grey, and red. Later this blue group can be merged to yellow and so on.
   2. Social media add friend suggestions in FB, and LinkedIn.


   Here the union, find, get component, check if connected TimeComplexity is α(n) - Amortized constant time.

   CREATING UNION FIND:
   • First, construct a bijection (a mapping) between your objects and the integers in the range [0,n) --> to create an array-based unionFind --> but it's optional.
   • Using HashMap/HashTable, randomly assign a mapping bw the objects and the integers on the array --> Eg: {B-1, E-2, A-3}

   Krushkal's Minimum Spanning Tree MST Algorithm:
   It's an algorithm to connect all nodes(vertices) in the tree with total edges with minimal edge weight in a given graph G=(V, E). Or A subset of the edges that connect all the vertices in the graph with minimal total edge cost.
   Approach:
   1. Sort all edges by ascending edge weight (not node vertices).
   2. Then walk through this, if the nodes are already unified then we don't include this edge, otherwise include and unify the nodes.
   3. terminate when we run out of edges or all the vertices are unified into one big group.
   4. Don't create cycles

 */
public class DisjointSetUnion {
    // UnionFind class TO COPY PASTE THIS & USE IT IN YOUR CODE
    class UnionFind {
        int[] parent;
        int[] rank;
        public UnionFind(int n) {
            parent = new int[n];
            rank = new int[n];
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
                if (rank[rootX] < rank[rootY]) {
                    parent[rootX] = rootY;
                } else if (rank[rootX] > rank[rootY]) {
                    parent[rootY] = rootX;
                } else {
                    parent[rootY] = rootX;
                    rank[rootX]++;
                }
            }
        }
    }
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
        System.out.println(uf.calculateNumComponents()); // 7
        System.out.println(uf.largestComponentSize()); // 3
        System.out.println(uf.numComponentsOfSize(3)); // 1
        System.out.println(uf.numComponentsOfSize(1)); // 6

        int[] parents = uf.getParents();
        int[] ranks = uf.getRank();
        for (int i = 0; i < parents.length; i++) System.out.println(parents[i] + " " + ranks[i]);

        uf.clear();
        System.out.println(uf.getNumComponents()); // 10
        uf.clear(5);
        System.out.println(uf.calculateNumComponents()); // 5
    }

    private int[] parent;
    private int[] rank;
    private int numComponents;
    private int[] size; // To track the size of each component
    // private int[] root; // To track the root of each component
    // private List<Set<Integer>> components; // Tracks the each component
    // private Map<Integer, Integer> sizeFrequency; // Tracks the frequency of component sizes
    Map<Integer, Set<Integer>> comps = new HashMap<>(); // check union4() & refactorCompsAfterUnion() to maintain components



    public DisjointSetUnion(int size) {
        parent = new int[size];
        rank = new int[size];
        this.size = new int[size];
        numComponents = size;

        for (int i = 0; i < size; i++) {
            parent[i] = i;
            rank[i] = 0;
            this.size[i] = 1;
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
    // Union function with union by rank and size update
    public void union3(int x, int y) {
        int rootX = find(x);
        int rootY = find(y);

        if (rootX != rootY) {
            if (rank[rootX] > rank[rootY]) {
                parent[rootY] = rootX;
                size[rootX] += size[rootY]; // Update size of rootX
            } else if (rank[rootX] < rank[rootY]) {
                parent[rootX] = rootY;
                size[rootY] += size[rootX]; // Update size of rootY
            } else {
                parent[rootY] = rootX;
                size[rootX] += size[rootY]; // Update size of rootX
                rank[rootX]++;
            }
        }
    }
    public void union4(int a, int b){
        int pa = find(a);
        int pb = find(b);
        if (pa==pb) return;

        if (!comps.containsKey(pa)) {
            Set<Integer> aSet = new HashSet<>();
            aSet.add(a);
            aSet.add(pa);
            comps.put(pa, aSet);
        }
        if (!comps.containsKey(pb)) {
            Set<Integer> bSet = new HashSet<>();
            bSet.add(b);
            bSet.add(pb);
            comps.put(pb, bSet);
        }

        if (rank[pa]<rank[pb]) {
            parent[pa]=pb;
            comps.get(pb).addAll(comps.get(pa));
            comps.remove(pa);
        }
        else if (rank[pb]<rank[pa]) {
            parent[pb]=pa;
            comps.get(pa).addAll(comps.get(pb));
            comps.remove(pb);
        }
        else {
            parent[pb]=pa;
            rank[pa]++;
            comps.get(pa).addAll(comps.get(pb));
            comps.remove(pb);
        }
    }
    // comps that are not in edges[] in union4() method
    public void refactorCompsAfterUnion(){
        for (int i=0; i<parent.length; i++) {
            if (!comps.containsKey(i) && i == parent[i]) {
                comps.put(i, new HashSet<>(Arrays.asList(i)));
            }
        }
    }

    public boolean connected(int x, int y) {
        return find(x) == find(y);
    }

    public int getNumComponents() {
        return numComponents;
    }

    public int calculateNumComponents() {
        int count = 0;
        for (int i = 0; i < parent.length; i++) {
            if (parent[i] == i) count++;
        }
        return count;
    }
    // when default parent = -1
    public int calculateNumComponents2() {
        int count = 0;
        for (int p: parent) {
            if (p == -1) count++;
        }
        return count;
    }

    // or just use union3() and get size[root] for each node
    public int componentSize(int x) {
        int root = find(x); // Find the root of the component
        int size = 0;
        // Count how many nodes have the same root
        for (int i = 0; i < parent.length; i++) {
            if (find(i) == root) {
                size++;
            }
        }
        return size;
    }
    public int componentSize2(int x) { // won't work
        return rank[find(x)];
    }

    public int largestComponentSize() {
        int largest = 0;
        for (int i = 0; i < rank.length; i++) {
            largest = Math.max(largest, componentSize(i)); // use memo if you want to avoid recomputation
        }
        return largest;
    }
    public int largestComponentSize2() {
        int largest = 0;
        for (int i = 0; i < rank.length; i++) {
            largest = Math.max(largest, rank[i]); // won't work
        }
        return largest;
    }

    public int numComponentsOfSize(int size) {
        int count = 0;
        Set<Integer> set = new HashSet<>();
        for (int i = 0; i < parent.length; i++) {
            // USE SOME MEMO to avoid recomputation
            int root = find(i);
            if (!set.add(root)) continue; // skip the same root
            int tempSize = 0;
            // Count how many nodes have the same root
            for (int j = 0; j < parent.length; j++) {
                if (find(j) == root) {
                    tempSize++;
                }
            }
            if (tempSize == size) count++;
        }
        return count;
    }
    // WON'T WORK
    public int numComponentsOfSize2(int size) {
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

