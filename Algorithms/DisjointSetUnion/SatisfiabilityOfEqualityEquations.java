package Algorithms.DisjointSetUnion;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 27 March 2025
 */
public class SatisfiabilityOfEqualityEquations {
    public static void main(String[] args) {
        String[] equations = {"a==b","b!=c","a==c"};
        System.out.println(equationsPossibleMyApproach(equations));
    }

    static int par[], rank[];
    public static boolean equationsPossibleMyApproach(String[] equations) {
        par=new int[26];
        rank=new int[26];
        for (int i=0; i<26; i++) par[i]=i;

        // connect all positives
        for (String s: equations) {
            int a = s.charAt(0)-'a';
            int b = s.charAt(3)-'a';
            boolean isConnected = s.charAt(1)=='=';
            if (isConnected) union(a, b);
        }

        // check the negative cases if those are true or not
        for (String s: equations) {
            int a = s.charAt(0)-'a';
            int b = s.charAt(3)-'a';
            boolean isConnected = s.charAt(1)=='=';
            if (!isConnected && isSamePar(a,b)) return false;

        }

        return true;
    }
    private static boolean union(int a, int b) {
        a=find(a);
        b=find(b);

        if (a==b) return true;

        if(rank[b]<rank[a]) par[b]=a;
        else if(rank[a]<rank[b]) par[a]=b;
        else{
            par[b]=a;
            rank[a]++;
        }
        return false;
    }
    private static int find(int i) {
        while(i!=par[i]) i=par[i];
        return i;
    }
    private static boolean isSamePar(int a, int b) {
        return find(a)==find(b);
    }







    public static boolean equationsPossible2(String[] equations) {
        int[] parent = new int[26];
        int[] rank = new int[26];
        for (int i = 0; i < 26; i++) {
            parent[i] = i;
            rank[i] = 1;
        }
        for (String equation : equations) {
            if (equation.charAt(1) == '=') {
                union(equation.charAt(0) - 'a', equation.charAt(3) - 'a', parent, rank);
            }
        }
        for (String equation : equations) {
            if (equation.charAt(1) == '!' && find(equation.charAt(0) - 'a', parent) == find(equation.charAt(3) - 'a', parent)) {
                return false;
            }
        }
        return true;
    }

    public static void union(int x, int y, int[] parent, int[] rank) {
        int px = find(x, parent), py = find(y, parent);
        if (px == py) return;
        if (rank[px] > rank[py]) {
            parent[py] = px;
        } else if (rank[px] < rank[py]) {
            parent[px] = py;
        } else {
            parent[py] = px;
            rank[px] += 1;
        }
    }

    public static int find(int x, int[] parent) {
        if (parent[x] == x) return x;
        return parent[x] = find(parent[x], parent);
    }








    public boolean equationsPossible3(String[] strs) {
        int[] parent = new int[26]; // rank not needed here
        for(int i = 0; i<26; i++) parent[i] = i;

        for(String str : strs) {
            if(str.charAt(1) == '=') {
                int first = str.charAt(0)-'a';
                int second = str.charAt(3)-'a';
                int p1 = find(first, parent);
                int p2 = find(second, parent);
                parent[p2] = p1; // UNION
            }
        }

        for(String str : strs) {
            if(str.charAt(1) != '=') {
                int first = str.charAt(0)-'a';
                int second = str.charAt(3)-'a';
                int p1 = find(first, parent);
                int p2 = find(second, parent);
                if(p1 == p2) return false; // IS SAME GROUP
            }
        }
        return true;
    }







    @SuppressWarnings("unchecked")
    public boolean equationsPossibleUsingGraphDFS(String[] equations) {
        List<Integer>[] graph = new List[26]; // 2D array
        for (int i = 0; i < 26; ++i) graph[i] = new ArrayList<>();

        for (String eqn : equations) {
            if (eqn.charAt(1) == '=') {
                int x = eqn.charAt(0) - 'a';
                int y = eqn.charAt(3) - 'a';
                graph[x].add(y);
                graph[y].add(x);
            }
        }

        int[] color = new int[26];
        Arrays.fill(color, -1);

        for (int i = 0; i < 26; i++) {
            if (color[i] == -1) {
                dfs(i, i, color, graph);
            }
        }

        for (String eqn : equations) {
            if (eqn.charAt(1) == '!') {
                int x = eqn.charAt(0) - 'a';
                int y = eqn.charAt(3) - 'a';
                if (color[x] == color[y]) // same parent
                    return false;
            }
        }

        return true;
    }
    // mark the color of `node` as `c`
    // [0:[1,2], 1:[2,3]] --> set color as 0 for 0,1&2, and as we have recursion it'll go inside 1 & it's children and 2 & it's children
    // [-1,-1,-1,-1] -> [0,0,0,0]
    private static void dfs(int node, int c, int[] color, List<Integer>[] graph) {
        if (color[node] == -1) {
            color[node] = c;
            for (int nei : graph[node])
                dfs(nei, c, color, graph);
        }
    }
}
