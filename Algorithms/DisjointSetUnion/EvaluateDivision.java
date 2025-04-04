package Algorithms.DisjointSetUnion;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 03 April 2025
 */
public class EvaluateDivision {
    public static void main(String[] args) {
        List<List<String>> equations = new ArrayList<>();
        // {{"a","b"},{"b","c"}}
        equations.add(new ArrayList<>(Arrays.asList("a","b")));
        equations.add(new ArrayList<>(Arrays.asList("b", "c")));
        double[] values = {2.0,3.0}; // {2.0,3.0}
        List<List<String>> queries = new ArrayList<>();
        // {{"a","c"},{"b","a"},{"a","e"},{"a","a"},{"x","x"}};
        queries.add(new ArrayList<>(Arrays.asList("a","c")));
        queries.add(new ArrayList<>(Arrays.asList("b","a")));
        queries.add(new ArrayList<>(Arrays.asList("a","e"))); // 0.5
        queries.add(new ArrayList<>(Arrays.asList("a","a"))); // 1.0
        queries.add(new ArrayList<>(Arrays.asList("x","x"))); // -1.0

        System.out.println("calcEquation -> \n" + Arrays.toString(calcEquation(equations, values, queries)));
    }




    public static double[] calcEquation(List<List<String>> equations, double[] values, List<List<String>> queries) {
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










    public double[] calcEquation2(List<List<String>> equations, double[] values, List<List<String>> queries) {
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











    public double[] calcEquation3(List<List<String>> equations, double[] values, List<List<String>> queries) {
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
        if(cost[a][b] > -1)
            return cost[a][b];

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
