package DataStructures;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 01 May 2025
<pre>
 Graphs are a collection of nodes (or vertices) connected by edges.
 V = VERTEX / NODE
 E = EDGE

 Same like trees, graphs are also a non-linear data structure
 Graph have no root unless explicitly defined
 In graph problems, we usually call it as: starting node, source node, or entry point (instead of "root")
 they can be cyclic or acyclic.

 In the Binary tree, we only have a maximum of 2 children for each node.
 But a tree or a graph node can have any number of children.
 And all trees are acyclic.

 Graphs can be directed or undirected, weighted or unweighted.
 A directed graph has edges with a direction, while an undirected graph has edges without a direction.
 A weighted graph has edges with weights, while an unweighted graph has edges without weights.

 Graphs can be represented using
 1) Adjacency list
 2) Adjacency matrix
 3) GraphNode class

 And we have other Graph algorithms like
 1) Khan's algorithm for Topological sort for a Directed Acyclic Graph (DAG)
 2) Shortest path
 3) Minimum spanning tree
 4)


 1) Adjacency List:
 -------------------
 A map/list where each node stores a list of its neighbors
 or an adjacency list is a collection of lists or arrays, where each list corresponds to a vertex and contains the vertices that are adjacent to it.

 Eg:
 List<Integer>[] adj = new LinkedList[v]; // where v is the number of vertices / nodes
 List<List<Integer>> adj = new ArrayList<>(v);
 for (int i = 0; i < n; i++) adjList.add(new ArrayList<>());
 adjList.get(0).add(1);
 adjList.get(0).add(2);

 Graph:
              0 -- 1
              |    |
              2 -- 3

 The adjacency list representation would look like this:

               0 → [1, 2]
               1 → [0, 3]
               2 → [0, 3]
               3 → [1, 2]

 PROS:
 1) Space-efficient for sparse graphs (few edges)
 2) Easy to iterate over neighbors.
 3) More memory-friendly: O(V + E) space. V = vertices / nodes, E = edges.

 CONS:
 1) Checking if an edge exists takes O(degree) time.
 2) Not ideal for dense graphs.






 2) Adjacency Matrix:
 ---------------------
 A 2D array where matrix[i][j] = 1 if there's an edge from node i to node j, otherwise 0.

 Eg:
 int[][] adjMatrix = new int[v][v]; // where v is the number of vertices
 adjMatrix[0][1] = 1; // means there is an edge from node 0 to node 1
 adjMatrix[0][2] = 1; // means there is an edge from node 0 to node 2

 Graph:
              0 -- 1
              |    |
              2 -- 3

 The adjacency matrix representation would look like this:

             0  1  2  3
          0 [0, 1, 1, 0]
          1 [1, 0, 0, 1]
          2 [1, 0, 0, 1]
          3 [0, 1, 1, 0]

 Note that adjMatrix[i][i] i.e same index is always 0 -- self-node

 PROS:
 1) Fast lookup for edge existence: O(1)
 2) Easy to implement.
 3) Good for dense graphs (many edges).

 CONS:
 1) Uses O(V²) space, even if the graph has few edges.
 2) Inefficient for large sparse graphs.



 Feature           | Adjacency List | Adjacency Matrix
 ------------------|----------------|------------------
 Space Complexity  | O(V + E)       | O(V²)
 Check edge (u, v) | O(degree of u) | O(1)
 Iterate neighbors | Fast           | O(V)
 Good for          | Sparse graphs  | Dense graphs






 3) GraphNode class:
 ---------------------
 A GraphNode class represents a node in a graph -- same like TreeNode for trees

 Eg:
 GraphNode node1 = new GraphNode(1);
 GraphNode node2 = new GraphNode(2);
 GraphNode node3 = new GraphNode(3);
 node1.list.add(node2); // add edge from node1 to node2
 node1.list.add(node3);


 NOTES:
 ------
 1) Check out Khan's algorithm for topological sort ---> in-Degree concept {@link Algorithms.Graphs.CourseSchedule} & {@link Algorithms.Graphs.CourseSchedule2}



-----------------------------
 GRAPH ALGORITHMS LANDSCAPE
-----------------------------

Graph Algorithms
 ├── Traversal (BFS / DFS)
 ├── Topological Sort
 │    ├── Kahn’s Algorithm
 │    └── DFS-based topo
 ├── Shortest Paths
 ├── MST
 ├── SCC
 ├── Flow
 └── Matching


1) Traversal Algorithms (Foundation)
-------------------------------------
    Everything builds on these.

    - BFS (Breadth First Search)
        a) Level order traversal
        b) Shortest path in unweighted graphs
        c) Cycle detection in undirected graphs
        d) Bipartite checking

    - DFS (Depth First Search)
        a) Cycle detection in directed and undirected graphs
        b) Topological sort using DFS
        c) Connected components
        d) Backtracking style graph problems

    Note
    a) Kahns algorithm internally uses BFS using indegree and queue



2) Topological Sorting (DAG only)
----------------------------------
    Two standard algorithms

    - Kahns Algorithm (BFS based)
        a) Uses indegree
        b) Queue of zero indegree nodes
        c) Detects cycle if nodes remain unprocessed

    - DFS based Topological Sort
        a) Post order DFS
        b) Uses recursion stack
        c) Reverse finishing order

    Important
        a) Both are graph algorithms
        b) Only valid for DAGs



3) Cycle Detection Algorithms
------------------------------
    - Undirected Graph
        a) DFS with parent tracking
        b) Union Find

    - Directed Graph
        a) DFS with recursion stack
        b) Kahns algorithm when topological sort fails




4) Shortest Path Algorithms
---------------------------
    - Unweighted Graph
        a) BFS

    - Weighted Graph
        a) Dijkstra algorithm non negative weights
        b) Bellman Ford algorithm negative weights allowed
        c) Floyd Warshall algorithm all pairs shortest path



5) Minimum Spanning Tree (MST)
-------------------------------
    - Kruskal's Algorithm
        a) Uses Union Find
        b) Sort edges

    - Prims Algorithm
        a) Greedy approach
        b) Priority queue

    Note
    a) Applies to undirected weighted graphs





6) Connectivity Algorithms
------------------------------------
    - Connected Components (Undirected)
        a) DFS
        b) BFS

    - Strongly Connected Components (Directed)
        a) Kosaraju's Algorithm
        b) Tarjan's Algorithm



7) Bipartite Graph Algorithms
------------------------------
    a) BFS coloring
    b) DFS coloring
    c) Used in matching problems
    d) Used in scheduling
    e) Used in constraint satisfaction




8) Flow and Matching Algorithms (Advanced)
------------------------------------------
    - Max Flow Algorithms
        a) Ford Fulkerson
        b) Edmonds Karp
        c) Dinics Algorithm

    - Bipartite Matching
        a) Hopcroft Karp



9) Union Find (Disjoint Set)
-----------------------------
    a) Cycle detection
    b) Kruskal's MST (Minimum Spanning Tree)
    c) Dynamic connectivity

 </pre>
 */
public class Graphs {
    public static void main(String[] args) {
        int n = 5;
        // Example usage of the Adjacency List Graph class
        AdjacencyListGraph adjListGraph = new AdjacencyListGraph(n);
        adjListGraph.addEdge(0, 1);
        adjListGraph.addEdge(0, 4);
        adjListGraph.addEdge(1, 2);
        adjListGraph.addEdge(1, 3);
        adjListGraph.addEdge(1, 4);
        adjListGraph.addEdge(2, 3);
        adjListGraph.addEdge(3, 4);
        System.out.println("Adjacency List:");
        adjListGraph.printGraph();

        System.out.println("\nDFS Traversal in Adjacency List:");
        adjListGraph.dfs(0, new boolean[n], adjListGraph.adjList);

        System.out.println("\nBFS Traversal in Adjacency List:");
        adjListGraph.bfs(0, adjListGraph.adjList);




        // Example usage of the Adjacency Matrix Graph class
        AdjacencyMatrixGraph adjMatrixGraph = new AdjacencyMatrixGraph(5);
        adjMatrixGraph.addEdge(0, 1);
        adjMatrixGraph.addEdge(0, 4);
        adjMatrixGraph.addEdge(1, 2);
        adjMatrixGraph.addEdge(1, 3);
        adjMatrixGraph.addEdge(1, 4);
        adjMatrixGraph.addEdge(2, 3);
        adjMatrixGraph.addEdge(3, 4);
        System.out.println("\n\n\n\nAdjacency Matrix:");
        adjMatrixGraph.printGraph();
        System.out.println("\nDFS Traversal in Adjacency Matrix:");
        adjMatrixGraph.dfs(0, new boolean[n]);
        System.out.println("\nBFS Traversal in Adjacency Matrix:");
        adjMatrixGraph.bfs(0);




        // Example usage of the GraphNode class
        GraphNode node1 = new GraphNode(1);
        GraphNode node2 = new GraphNode(2);
        GraphNode node3 = new GraphNode(3);
        node1.list.add(node2); // add edge from node1 to node2
        node1.list.add(node3);
        System.out.println("\n\n\n\nGraphNode:");
        node1.printGraph();
    }




    @SuppressWarnings("unchecked")
    static class AdjacencyListGraph {
        private final int V; // Number of vertices == nodes
        private final List<Integer>[] adjList; // Adjacency list

        public AdjacencyListGraph(int v) {
            V = v;
            adjList = new LinkedList[v];
            for (int i = 0; i < v; i++) {
                adjList[i] = new LinkedList<>(); // or new ArrayList<>()
            }
        }

        public void addEdge(int u, int v) {
            adjList[u].add(v);
            adjList[v].add(u); // For undirected graph
        }


        public void printGraph() {
            for (int i = 0; i < V; i++) {
                System.out.print(i + ": ");
                for (Integer neighbor : adjList[i]) {
                    System.out.print(neighbor + " ");
                }
                System.out.println();
            }
        }

        void dfs(int node, boolean[] visited, List<Integer>[] adj) {
            visited[node] = true;
            System.out.print(node + " ");

            for (int neighbor : adj[node]) {
                if (!visited[neighbor]) {
                    dfs(neighbor, visited, adj);
                }
            }
        }
        // same as above dfs() but it's very slightly slower
        void dfs2(int node, boolean[] visited, List<Integer>[] adj) {
            if(visited[node]) return;
            visited[node] = true;
            System.out.print(node + " ");
            for (int neighbor : adj[node]) dfs2(neighbor, visited, adj);
        }


        void bfs(int start, List<Integer>[] adj) {
            boolean[] visited = new boolean[adj.length];
            Queue<Integer> queue = new LinkedList<>();

            queue.add(start);
            visited[start] = true;

            while (!queue.isEmpty()) {
                int node = queue.poll();
                System.out.print(node + " ");

                for (int neighbor : adj[node]) {
                    if (!visited[neighbor]) {
                        queue.add(neighbor);
                        visited[neighbor] = true;
                    }
                }
            }

        }

        void bfs2(int start, boolean[] visited, List<List<Integer>> adj) {
            LinkedList<Integer> queue = new LinkedList<>();
            visited[start] = true;
            queue.add(start);

            while (!queue.isEmpty()) {
                int node = queue.poll();
                System.out.print(node + " ");

                for (int neighbor : adj.get(node)) {
                    if (!visited[neighbor]) {
                        visited[neighbor] = true;
                        queue.add(neighbor);
                    }
                }
            }
        }
    }







    static class AdjacencyMatrixGraph {
        private final int V; // Number of vertices
        private final int[][] adjMatrix; // Adjacency matrix

        public AdjacencyMatrixGraph(int v) {
            V = v;
            adjMatrix = new int[v][v];
        }

        public void addEdge(int u, int v) {
            adjMatrix[u][v] = 1;
            adjMatrix[v][u] = 1; // For undirected graph
        }

        public void printGraph() {
            for (int i = 0; i < V; i++) {
                for (int j = 0; j < V; j++) {
                    System.out.print(adjMatrix[i][j] + " ");
                }
                System.out.println();
            }
        }

        public void dfs(int node, boolean[] visited) { // node == row index
            visited[node] = true;
            System.out.print(node + " ");

            for (int nei = 0; nei < V; nei++) { // col
                if (adjMatrix[node][nei] == 1 && !visited[nei]) {
                    dfs(nei, visited);
                }
            }
        }
        public void dfs2(int[][] isConnected, boolean[] visited, int i) { // isConnected == adjMatrix
            visited[i] = true;
            for (int nei = 0; nei < isConnected.length; nei++) {
                if (isConnected[i][nei] == 1 && !visited[nei]) {
                    dfs2(isConnected, visited, nei);
                }
            }
        }
        public void bfs(int start) { // row index i.e entry point
            boolean[] visited = new boolean[V];
            Queue<Integer> queue = new LinkedList<>();

            queue.add(start);
            visited[start] = true;

            while (!queue.isEmpty()) {
                int node = queue.poll();
                System.out.print(node + " ");

                for (int col = 0; col < V; col++) { // each col for each node i.e row
                    if (adjMatrix[node][col] == 1 && !visited[col]) {
                        queue.add(col);
                        visited[col] = true;
                    }
                }
            }
        }
    }







    static class GraphNode {
        int val;
        LinkedList<GraphNode> list; // Adjacency list

        public GraphNode(int val) {
            this.val = val;
            this.list = new LinkedList<>();
        }
        public void addEdge(GraphNode node) {
            list.add(node);
        }

        public void printGraph() {
            System.out.print(val + ": ");
            for (GraphNode neighbor : list) {
                System.out.print(neighbor.val + " ");
            }
            System.out.println();
        }
    }
}
