package DataStructures;

/**
 * Union Find / Disjoint Set Union DSU
 *
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 10 Jan 2025
 */
public class UnionFind {

  public static void main(String[] args) {

  }

  private int size; // The number of elements in this union find

  private int[] sz; // Used to track the size of each of the set / group / component

  private int[] id; // id[i] points to the parent of i, if id[i] = i then i is a root node

  private int numComponents; // Tracks the number of components (sets / groups) in the union find

  public UnionFind(int size) {

    if (size <= 0) throw new IllegalArgumentException("Size <= 0 is not allowed");

    this.size = numComponents = size;
    sz = new int[size];
    id = new int[size];

    for (int i = 0; i < size; i++) {
      id[i] = i; // Link to itself (self root)
      sz[i] = 1; // Each component is originally of size one
    }
  }

  public int find(int p) { // Find which component/set 'p' belongs to, takes amortized constant time.

    int root = p; // Find the root of the component/set
    while (root != id[root]) root = id[root];

    // Compress the path leading back to the root.
    // Doing this operation is called "path compression"
    // and is what gives us amortized time complexity.
    while (p != root) {
      int next = id[p];
      id[p] = root;
      p = next;
    }

    return root;
  }

  // This is an alternative recursive formulation for the find method
  // public int find(int p) {
  //   if (p == id[p]) return p;
  //   return id[p] = find(id[p]);
  // }

  // Return whether or not the elements 'p' and
  // 'q' are in the same components/set.
  public boolean connected(int p, int q) {
    return find(p) == find(q);
  }


  public int componentSize(int p) { // Return the size of the components/set 'p' belongs to
    return sz[find(p)];
  }

  public int size() { // Return the number of elements in this UnionFind/Disjoint set
    return size;
  }

  public int components() { // Returns the number of remaining components/sets
    return numComponents;
  }

  public void unify(int p, int q) { // Unify the components/sets containing elements 'p' and 'q'

    if (connected(p, q)) return; // or root1==root2 => return; These elements are already in the same group!

    int root1 = find(p); // Now find the root of each of the components
    int root2 = find(q);

    if (sz[root1] < sz[root2]) { // Merge smaller component/set into the larger one.
      sz[root2] += sz[root1];
      id[root1] = root2;
      sz[root1] = 0;
    } else {
      sz[root1] += sz[root2];
      id[root2] = root1;
      sz[root2] = 0;
    }

    // Since the roots found are different we know that the
    // number of components/sets has decreased by one
    numComponents--;
  }
}
