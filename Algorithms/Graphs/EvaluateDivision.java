package Algorithms.Graphs;

import java.util.*;

/**
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 03 April 2025
 * @link 399. Evaluate Division <a href="https://leetcode.com/problems/evaluate-division/">LeetCode Link</a>
 * @topics Array, String, Graph, Union Find, DFS, BFS, the Shortest Path
 * @companies Amazon(7), Bloomberg(7), Google(4), Uber(4), TikTok(2), Snap(2), Microsoft(2), Meta(10), Citadel(9), Stripe(8), Rippling(6), GE Healthcare(6), Nuro(4), Flipkart(3), LinkedIn(2), Snowflake(2), PhonePe(2)

    HINT: <a href="https://leetcode.com/problems/evaluate-division/description/comments/1899742/">LC Hint link</a>

    GIVEN:
    ------
    1) equations[i] = [Ai, Bi]
    2) values[i] = Ai / Bi
    3) queries[j]=[Cj, Dj]
    4) queriesVales[j] = ??


    PATTERNS:
    ---------
    a/b=1.5; b/c=2.5 ---> a/b * b/c = a/c -- both b cancels -- 1.5 * 2.5 = 3.75
    a/b=1.5; b/c=2.5 ---> c/b * b/a = c/a -- (1/2.5) * (1/1.5) = 0.26
    This looks like graph nodes with edge weights
    In ab/c, consider "ab" as a single node and c as another node. So, we can have a graph with nodes ab and c.
    Don't assume "ab" means a*b.


    APPROACH 1: ------- WRONG APPROACH
    -----------
    1) Save all possibilities in a map
    2) In a/b, b/c
    3) a/b, b/c, b/a, c/b
    4) (a/b * b/c = a/c), c/a | (a/b * b/a = "") | (a/b * c/b = ac/bb), bb/ac | ()
    5) As "1 <= Ai.length, Bi.length <= 5", loop until numerator=5/denominator=5
    6)


    APPROACH 2: ------ Adjacent List Graph - DFS & BFS
    -----------
    1) a/b = 2.0 means a to b edge weight = 2.0
    2) bc/cd != b/d ----> dc and cd are nodes. Here they don't cancel c like the Math problem
    3) bc != cb
    4) for +ve direction --> multiply
    5) for -ve direction --> divide or inverse



    APPROACH 3: ------ Union Find
    -----------
    1) We might find multiple DSUs in the graph. So, we need to find the root of each node.


 */
public class EvaluateDivision {
    public static void main(String[] args) {
        List<List<String>> equations = new ArrayList<>();
        // {{"a","b"},{"b","c"}, {"bc","cd"}, {"c", "cd"}};
        equations.add(Arrays.asList("a","b"));
        equations.add(Arrays.asList("b", "c"));
        equations.add(Arrays.asList("bc","cd"));
        equations.add(Arrays.asList("cd", "c"));
        double[] values = {1.5, 2.5, 5.0, 1.0};

        List<List<String>> queries = new ArrayList<>();
        // {{"bc","b"},{"a","d"},{"a","c"},{"c","a"},{"c","b"},{"bc","cd"},{"cd","bc"}}
        queries.add(Arrays.asList("bc","b"));
        queries.add(Arrays.asList("a","d"));
        queries.add(Arrays.asList("a","c"));
        queries.add(Arrays.asList("c","a"));
        queries.add(Arrays.asList("c","b"));
        queries.add(Arrays.asList("bc","cd"));
        queries.add(Arrays.asList("cd","bc"));

        System.out.println("calcEquation using Union Find -> \n" +
        Arrays.toString(calcEquationUsingUnionFind1(equations, values, queries)));

        System.out.println("calcEquation using Adjacent List Graph DFS 1 -> \n" +
        Arrays.toString(calcEquationUsingDfs1(equations, values, queries)));
    }


    /**

    GIVEN:

        Equations: [a, b], [b, c], [b, x], [y, z]
        Values:    [ 2,      3,      2,      5]
        Queries: [a, c], [c, x], [c, y]


    GRAPH REPRESENTATION:

        a --(2)--> b --(3)--> c
                   |
                  (2)
                   ↓
                   x

        y --(5)--> z


    THOUGHTS:
         1) a/c ---> a = 2b = 2*3c = 6c -> a/c = 6
         2) c/x ---> b = 3c; b = 2x -> c/x = 2/3
         3) c/y ---> y = 5c -> we can't go from c to y because they two are in two different disjoint sets = -1


    IN UNION FIND:
     [a,b] ---> uf = [a -> b]
     [b,c] ---> uf = [a -> b -> c]
     [b,x] ---> uf = [a -> b -> c -> x] ---> here we're making x or x's parent as b's parent's parent
     [y,z] ---> uf = [a -> b -> c -> x] [y -> z]


     */
    static class Parent {String name; double weight; Parent(String p, double w) {name = p; weight = w;}}
    static Map<String, Parent> parentMap = new HashMap<>(); // parentMap
    public static double[] calcEquationUsingUnionFind1(List<List<String>> equations, double[] values, List<List<String>> queries) {

        // 1. Build Union-Find
        for (int i = 0; i < equations.size(); i++) {
            String a = equations.get(i).get(0);
            String b = equations.get(i).get(1);
            union(a, b, values[i]);
        }

        // 2. Answer queries
        double[] res = new double[queries.size()];

        for (int i = 0; i < queries.size(); i++) {
            String a = queries.get(i).get(0);
            String b = queries.get(i).get(1);

            if (!parentMap.containsKey(a) || !parentMap.containsKey(b)) {
                res[i] = -1.0;
                continue;
            }

            Parent pa = findParent(a);
            Parent pb = findParent(b);

            if (!pa.name.equals(pb.name)) {
                res[i] = -1.0;
            } else {
                res[i] = pa.weight / pb.weight; // A/C * 1(B/C) = A/C * C/B = A/B
            }
        }

        return res;
    }
    private static void union(String dividend, String divisor, double value) {
        Parent pa = findParent(dividend);
        Parent pb = findParent(divisor);

        if (!pa.name.equals(pb.name)) {
            parentMap.put(pa.name, new Parent(pb.name, value * pb.weight / pa.weight)); // pa.name / pb.name = value * (divisor/g2) / (dividend/g1)
            /*

            weight update explanation:

                here we have two different disjoint sets and need to connect them using a/y

                 [a -> b -> c -> x] [y -> z]

                 to connect a & y, we have to connect x & z where x is a's parent and z is y's parent
                 x/z = y/z * 1/(a/x) * a/y = y/z * x/a * a/y = a/z ---> here using a/y then all the other values cancel out
                 we already know the y/z and a/x values.
            */
        }
    }
    private static Parent findParent(String x) {
        parentMap.putIfAbsent(x, new Parent(x, 1.0)); // self parent & x/x = 1

        Parent px = parentMap.get(x);
        if (!px.name.equals(x)) { // is x not its own parent?
            Parent root = findParent(px.name);

            // UPDATE x's PARENT
            px.weight *= root.weight;
            px.name = root.name;
            // or parentMap.put(x, new Parent(root.name, px.weight * root.weight));
            /*

            weight update explanation:
                 if we have a/b ---> a's parent is b ---> a->b
                 and then if we have b/c ---> b's parent is c ---> b->c

                 now find pa = findParent("a")
                 as "b" is not self parent we'll get pa="c"

                 and then update a's parent as well ---> now a->c and b->c
                 a/c = a/b * b/c;
                 as a's parent is now c not b, then update the weight accordingly

            */
        }
        return px; // or parentMap.get(x)
    }










    public static class EntryPair extends AbstractMap.SimpleEntry<String,Double>{EntryPair(String name, double wt){super(name,wt);}}
    @SuppressWarnings("unused")
    public static double[] calcEquationUsingDfs1(List<List<String>> equations, double[] values, List<List<String>> queries) {
        Map<String, List<EntryPair>> al = new HashMap<>(); // <"node", [<nei1, weight>, <nei2, weight>, ...]>;
        // Map<String, List<Map.Entry<String, Double>>> al = new HashMap<>();

        for(int i=0; i<values.length; i++) {
            List<String> edge = equations.get(i);
            String node = edge.get(0);
            String nei = edge.get(1);

            al.computeIfAbsent(node, k -> new ArrayList<>())
                .add(new EntryPair(nei, values[i]));
            al.computeIfAbsent(nei, k -> new ArrayList<>())
                .add(new EntryPair(node, 1/values[i]));
        }

        double[] res = new double[queries.size()];
        for(int i=0; i<queries.size(); i++) {
            List<String> edge = queries.get(i);
            String node = edge.get(0);
            String nei = edge.get(1);

            if(al.containsKey(node) && al.containsKey(nei))
                res[i] = dfs(al, node, nei, 1.00, new HashSet<>());
            else res[i]=-1;
        }
        return res;
    }
    private static double dfs(Map<String, List<EntryPair>> al, String from, String to, Double product, Set<String> seen) {
        if(to.equals(from)) return product;

        seen.add(from);
        for(EntryPair neighbor: al.get(from) ) {
            String nei = neighbor.getKey();
            double wt = neighbor.getValue();
            if(seen.contains(nei)) continue;
            double newProduct = product * wt;
            double res = dfs(al, nei, to, newProduct, seen);
            if(res != -1) return res; // to continue with sibling neighbors
        }
        return -1;
    }







    static class Pair { double wt; Node node; Pair(double w, Node n) {wt=w; node=n;}}
    static class Node { List<Pair> neighbors = new ArrayList<>();} // or List<Map.Entry<Double, Node>>
    public static double[] calcEquationUsingBfs(List<List<String>> equations, double[] values, List<List<String>> queries) {

        // 1. prepare graph
        Map<String, Node> nameToNode = new HashMap<>();

        for (int i=0; i<values.length; i++) {
            String a = equations.get(i).get(0);
            String b = equations.get(i).get(1);

            nameToNode.putIfAbsent(a, new Node());
            nameToNode.putIfAbsent(b, new Node());

            nameToNode.get(a).neighbors.add(new Pair(values[i], nameToNode.get(b))); // or new AbstractMap.SimpleEntry<>();
            nameToNode.get(b).neighbors.add(new Pair(1/values[i], nameToNode.get(a)));
        }

        // 2. bfs queries
        double[] res = new double[queries.size()];
        for (int i=0; i<queries.size(); i++) {
            String a = queries.get(i).get(0);
            String b = queries.get(i).get(1);

            if (!nameToNode.containsKey(a) || !nameToNode.containsKey(b)) {
                res[i] = -1;
            } else if (a.equals(b)) {
                res[i] = 1;
            } else {
                res[i] = bfs(nameToNode.get(a), nameToNode.get(b));
            }
        }
        return res;
    }
    private static double bfs(Node nodeA, Node nodeB) {
        Queue<Pair> q = new LinkedList<>();
        Set<Node> seen = new HashSet<>();
        q.add(new Pair(1, nodeA));
        seen.add(nodeA);

        while (!q.isEmpty()) {
            Pair p = q.poll();
            Node node = p.node;
            double total = p.wt;

            for (Pair pair : node.neighbors) {
                Node nei = pair.node;
                double wt = pair.wt;

                if (!seen.add(nei)) continue;
                if (nei == nodeB) return total * wt;

                q.add(new Pair(total * wt, nei));
            }
        }
        return -1; // nodeB isNotFound
    }














    Map<String, String> parMap = new HashMap<>();
    Map<String, Double> wtMap = new HashMap<>();// weight[x] = x / parent[x]
    public double[] calcEquationUsingUnionFind2(List<List<String>> equations, double[] values, List<List<String>> queries) {

        for (int i = 0; i < equations.size(); i++) {// build unions
            String a = equations.get(i).get(0);
            String b = equations.get(i).get(1);
            union2(a, b, values[i]);
        }

        double[] res = new double[queries.size()];

        for (int i = 0; i < queries.size(); i++) { // process queries
            String a = queries.get(i).get(0);
            String b = queries.get(i).get(1);

            if (!parMap.containsKey(a) || !parMap.containsKey(b)) {
                res[i] = -1.0;
                continue;
            }

            if (a.equals(b)) {
                res[i] = 1.0;
                continue;
            }

            Parent pa = find2(a);
            Parent pb = find2(b);

            if (!pa.name.equals(pb.name)) {
                res[i] = -1.0;
            } else {
                res[i] = pa.weight / pb.weight;
            }
        }

        return res;
    }
    private Parent find2(String x) {
        if (!parMap.containsKey(x)) {
            parMap.put(x, x);
            wtMap.put(x, 1.0);
        }

        String p = parMap.get(x);
        double w = wtMap.get(x);

        if (!x.equals(p)) {
            Parent res = find2(p);
            parMap.put(x, res.name);
            wtMap.put(x, w * res.weight);
        }

        return new Parent(parMap.get(x), wtMap.get(x));
    }


    private void union2(String a, String b, double value) { // union(dividend, divisor, value)
        Parent pa = find2(a);
        Parent pb = find2(b);

        String rootA = pa.name;
        double wA = pa.weight; // a / rootA
        String rootB = pb.name;
        double wB = pb.weight; // b / rootB

        if (!rootA.equals(rootB)) {
            // rootA / rootB = (value * wB) / wA
            parMap.put(rootA, rootB);
            wtMap.put(rootA, (value * wB) / wA);
        }
    }










    public static double[] calcEquationUsingUnionFind3(List<List<String>> equations, double[] values, List<List<String>> queries) {
        Map<String, String> parent = new HashMap<>();
        Map<String, Double> weight = new HashMap<>(); // distance

        for (int i = 0; i < equations.size(); i++) {
            String u = equations.get(i).get(0);
            String v = equations.get(i).get(1);
            double value = values[i];

            union3(u, v, value, parent, weight);
        }

        double[] result = new double[queries.size()];
        for (int i = 0; i < queries.size(); i++) {
            String u = queries.get(i).get(0);
            String v = queries.get(i).get(1);

            if (!parent.containsKey(u) || !parent.containsKey(v)) {
                result[i] = -1.0;
            } else {
                String rootU = findParent3(u, parent, weight);
                String rootV = findParent3(v, parent, weight);
                if (!rootU.equals(rootV)) {
                    result[i] = -1.0; // Different connected components
                } else {
                    result[i] = weight.get(u) / weight.get(v);
                }
            }
        }

        return result;
    }
    private static void union3(String u, String v, double value, Map<String, String> parent, Map<String, Double> weight) {
        String rootU = findParent3(u, parent, weight);
        String rootV = findParent3(v, parent, weight);

        if (!rootU.equals(rootV)) {
            parent.put(rootU, rootV);
            weight.put(rootU, value * weight.get(v) / weight.get(u)); // rootU's weight = rootU's weight * rootV's weight / rootU's weight
        }
    }
    private static String findParent3(String u, Map<String, String> parent, Map<String, Double> weight) {
        if (!parent.containsKey(u)) { // when u not found in parent -> create the u
            parent.put(u, u);
            weight.put(u, 1.0);
            return u;
        }
        if (!parent.get(u).equals(u)) { // u is not its own parent -> find the root of u
            String originalParent = parent.get(u);
            String root = findParent3(originalParent, parent, weight);
            weight.put(u, weight.get(u) * weight.get(originalParent)); // u's weight = u's weight * originalParent's weight
            parent.put(u, root);
        }
        return parent.get(u);
    }




















    public static double[] calcEquationUsingDfs2(List<List<String>> equations, double[] values, List<List<String>> queries) {
        // Step 1: Build the graph
        Map<String, Map<String, Double>> graph = new HashMap<>();
        for (int i = 0; i < equations.size(); i++) {
            String u = equations.get(i).get(0);
            String v = equations.get(i).get(1);
            double value = values[i];

            graph.computeIfAbsent(u, _ -> new HashMap<>()).put(v, value);
            graph.computeIfAbsent(v, _ -> new HashMap<>()).put(u, 1 / value);
        }
        // Step 2: Process each query
        double[] result = new double[queries.size()];
        for (int i = 0; i < queries.size(); i++) {
            String start = queries.get(i).get(0);
            String end = queries.get(i).get(1);

            if (!graph.containsKey(start) || !graph.containsKey(end)) {
                result[i] = -1.0; // If either variable is not in the graph, return -1.0
            } else if (start.equals(end)) {
                result[i] = 1.0; // If start and end are the same, the result is 1.0
            } else {
                result[i] = dfs(graph, start, end, new HashSet<>(), 1.0);
            }
        }
        return result;
    }
    private static double dfs(Map<String, Map<String, Double>> graph, String current, String target, Set<String> visited, double product) {
        if (current.equals(target)) {
            return product; // Found the target, return the accumulated product
        }
        visited.add(current);
        for (Map.Entry<String, Double> neighbor : graph.get(current).entrySet()) {
            String nextNode = neighbor.getKey();
            double value = neighbor.getValue();
            if (!visited.contains(nextNode)) {
                double result = dfs(graph, nextNode, target, visited, product * value);
                if (result != -1.0) {
                    return result; // If a valid path is found, return the result
                }
            }
        }
        return -1.0; // No valid path found
    }










    public double[] calcEquationUsingDfs3(List<List<String>> equations, double[] values, List<List<String>> queries) {
        Map<String, Map<String, Double>> graph = new HashMap<>();
        // Build the graph
        for (int i = 0; i < equations.size(); i++) {
            String num = equations.get(i).get(0);
            String denom = equations.get(i).get(1);
            double value = values[i];

            graph.putIfAbsent(num, new HashMap<>());
            graph.putIfAbsent(denom, new HashMap<>());

            graph.get(num).put(denom, value);
            graph.get(denom).put(num, 1.0 / value);
        }
        // Process queries
        double[] results = new double[queries.size()];
        for (int i = 0; i < queries.size(); i++) {
            String start = queries.get(i).get(0);
            String end = queries.get(i).get(1);
            if (!graph.containsKey(start) || !graph.containsKey(end)) {
                results[i] = -1.0;
            } else {
                Set<String> visited = new HashSet<>();
                results[i] = dfs(graph, start, end, visited);
            }
        }

        return results;
    }

    private double dfs(Map<String, Map<String, Double>> graph, String current, String target, Set<String> visited) {
        if (current.equals(target)) return 1.0;
        if (visited.contains(current)) return -1.0;

        visited.add(current);

        for (Map.Entry<String, Double> neighbor : graph.get(current).entrySet()) {
            double result = dfs(graph, neighbor.getKey(), target, visited);
            if (result != -1.0) {
                return result * neighbor.getValue();
            }
        }

        return -1.0;
    }








    public double[] calcEquationUsingCostMatrix(List<List<String>> equations, double[] values, List<List<String>> queries) {
        double[] result = new double[queries.size()];
        List<String> variables = getVariables(equations);
        double[][] cost = getCostMatrix(equations, values, variables);
        for(int i=0; i<queries.size(); i++) {
            boolean[] visited = new boolean[variables.size()];
            List<String> q = queries.get(i);
            if(!variables.contains(q.get(0)) || !variables.contains(q.get(1))) {
                result[i] = -1.0;
            } else {
                int a = variables.indexOf(q.get(0));
                int b = variables.indexOf(q.get(1));
                result[i] = getPath(cost, a, b, visited);
            }
        }
        return result;
    }
    private double getPath(double[][] cost, int a, int b, boolean[] visited) {
        if(cost[a][b] > -1) return cost[a][b];
        visited[a] = true;
        for(int i=0; i<cost.length; i++) {
            if(visited[i] || cost[a][i] == -1)
                continue;
            double c = getPath(cost, i, b, visited);
            if(c > -1) {
                return cost[a][i] * c;
            }
        }
        return -1;
    }
    private double[][] getCostMatrix(List<List<String>> equations, double[] values, List<String> variables) {
        int varSize = variables.size();
        double[][] cost = new double[varSize][varSize];
        for(int i=0; i<varSize; i++) {
            for(int j=0; j<varSize; j++) {
                cost[i][j] = i == j ? 1 : -1;
            }
        }
        for(int i=0; i<equations.size(); i++) {
            List<String> eq = equations.get(i);
            int a = variables.indexOf(eq.get(0));
            int b = variables.indexOf(eq.get(1));
            cost[a][b] = values[i];
            cost[b][a] = 1/values[i];
        }
        return cost;
    }
    private List<String> getVariables(List<List<String>> equations) {
        List<String> variables = new ArrayList<>();
        for(List<String> eq : equations) {
            if(!variables.contains(eq.get(0)))
                variables.add(eq.get(0));
            if(!variables.contains(eq.get(1)))
                variables.add(eq.get(1));
        }
        return variables;
    }











    /**
     * NOT WORKING

        THOUGHTS:
        ---------
        1) If all chars in equation are in set. Then if one of the char in queries str[] are not present in that set then return -1;
        2) My be equations[i] * equations[j] will cancel out one numerator and one denominator and gives a new combination -->
        a/b * b/c == a/c and similar if we have a/b and a/c then (a/b)/(a/c)
        a/b * c/a == c/b
        1/(a/b) == b/a
        3) But "1 <= Ai.length, Bi.length <= 5" so, do we need to calculate a,b,c,d values separately?
     */
    public double[] calcEquationMyOldApproach(List<List<String>> equations, double[] values, List<List<String>> queries) {

        Map<String, Double> map = new HashMap<>();
        for (int eqI=0; eqI<equations.size(); eqI++) {
            List<String> eq = equations.get(eqI);
            Set<String> x = new HashSet<>(Arrays.asList(eq.get(0).split("")));
            Set<String> y = new HashSet<>(Arrays.asList(eq.get(0).split("")));

            for (int i=0; i<eq.get(0).length(); i++) {
                String str = eq.get(0).charAt(i)+"";
                if(y.contains(str)) {
                    x.remove(str);
                    y.remove(str);
                    continue;
                }
            }

            String xStr = x.stream().reduce("",(a,b)->a+b);
            String yStr = y.stream().reduce("",(a,b)->a+b);
            String finalEq = xStr + "/" + yStr;
            map.put(finalEq, values[eqI]);
            finalEq = yStr + "/" + xStr;
            map.put(finalEq, 1/values[eqI]);
            // what about a/b * b/c == a/c, a/b * c/a == c/b
        }


        return new double[0];
    }
}
