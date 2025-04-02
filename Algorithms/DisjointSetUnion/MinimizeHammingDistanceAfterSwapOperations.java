package Algorithms.DisjointSetUnion;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 02 April 2025
 */
public class MinimizeHammingDistanceAfterSwapOperations {
    public static void main(String[] args) {
        int[] source = {2,3,1}, target = {1,2,2}, allowedSwaps[] = {{0,2},{1,2}};
        System.out.println("minimumHammingDistance(source, target, allowedSwaps) => " + minimumHammingDistance(source, target, allowedSwaps));
        System.out.println("minimumHammingDistanceMyApproach(source, target, allowedSwaps) => " + minimumHammingDistanceMyApproach(source, target, allowedSwaps));
    }




    public static int minimumHammingDistance(int[] source, int[] target, int[][] allowedSwaps) {
        int n = source.length;
        UnionFind uf = new UnionFind(n);

        // Step 1: Union all indices based on allowedSwaps
        for (int[] swap : allowedSwaps) {
            uf.union(swap[0], swap[1]);
        }

        // Step 2: Group indices by their connected components
        Map<Integer, Map<Integer, Integer>> componentMap = new HashMap<>();
        for (int i = 0; i < n; i++) {
            int root = uf.find(i);
            componentMap.computeIfAbsent(root, _ -> new HashMap<>()).merge(source[i], 1, Integer::sum);
        }

        // Step 3: Calculate the Hamming distance
        int hammingDistance = 0;
        for (int i = 0; i < n; i++) {
            int root = uf.find(i);
            Map<Integer, Integer> freqMap = componentMap.get(root);

            if (freqMap.getOrDefault(target[i], 0) > 0) {
                freqMap.merge(target[i], -1, Integer::sum);
                if (freqMap.get(target[i]) == 0) {
                    freqMap.remove(target[i]);
                }
            } else {
                hammingDistance++;
            }
        }

        return hammingDistance;
    }








    /**
     * NOT WORKING
     */
    public static int minimumHammingDistanceMyApproach(int[] source, int[] target, int[][] allowedSwaps) {
        int n = source.length;
        int[] roots = new int[n];
        UnionFind uf = new UnionFind(n);
        for (int[] e: allowedSwaps) {
            uf.union(e[0], e[1]);
        }
        Map<Integer, Set<Integer>> comps = new HashMap<>();
        for (int i=0; i<n; i++) {
            int curRoot = uf.find(i);
            roots[i]=curRoot;
            comps.computeIfAbsent(curRoot, _->new HashSet<>()).add(i);
        }

        Map<Integer, Integer> sourceMap = new HashMap<>();
        for (int i=0; i<n; i++) sourceMap.put(source[i], i);
        Map<Integer, Integer> targetCounter = new HashMap<>();
        for (int t: target) targetCounter.merge(t, 1, Integer::sum);


        System.out.println("comps: " + comps);
        int res = 0;
        for (int i=0; i<n; i++) {
            int sourceNum = source[i];
            int targetNum = target[i];
            Integer targetNumIndexInSource = sourceMap.get(targetNum);
            // System.out.printf("i %s root is %s\n", i, roots[i]);
            // System.out.printf("sourceNum: %s, targetNum: %s, targetNumIndexInSource:%s\n", sourceNum, targetNum, targetNumIndexInSource);

            if(sourceNum == targetNum) {
                if (targetCounter.get(targetNum) > 0) {
                    targetCounter.merge(targetNum, -1, Integer::sum);
                } else res++;
                continue;
            }

            if(!sourceMap.containsKey(targetNum)) {
                res++;
                continue;
            }

            if (canTargetComeHere(comps, i, roots, targetNumIndexInSource)) {
                if (targetCounter.get(targetNum) > 0) {
                    targetCounter.merge(targetNum, -1, Integer::sum);
                } else res++;
            }
            else res++;
        }
        return res;
    }
    private static boolean canTargetComeHere(Map<Integer, Set<Integer>> comps, int i, int[] roots, int targetNumIndexInSource) {
        return roots[i]==roots[targetNumIndexInSource];
        // Set<Integer> set = comps.get(roots[i]);
        // return set.contains(targetNumIndexInSource);
    }













    public static int minimumHammingDistanceOlsSuggestionNotWorking(int[] source, int[] target, int[][] allowedSwaps) {
        int n = source.length;
        UnionFind uf = new UnionFind(n);

        for (int[] swap : allowedSwaps) {
            uf.union(swap[0], swap[1]);
        }

        int[] parent = new int[n];
        for (int i = 0; i < n; i++) {
            parent[i] = uf.find(i);
        }

        int[] freq = new int[n];
        for (int i = 0; i < n; i++) {
            freq[parent[i]]++;
        }

        int res = 0;
        for (int i = 0; i < n; i++) {
            if (source[i] != target[i]) {
                res += freq[parent[i]] - 1;
            }
        }

        return res;
    }







    static class UnionFind {
        int[] parent, rank;

        UnionFind(int n) {
            parent = new int[n];
            rank = new int[n];
            for (int i = 0; i < n; i++) {
                parent[i] = i;
            }
        }

        int find(int x) {
            if (parent[x] != x) {
                parent[x] = find(parent[x]); // Path compression
            }
            return parent[x];
        }

        void union(int x, int y) {
            int rootX = find(x);
            int rootY = find(y);
            if (rootX == rootY) return;
            if (rank[rootX] > rank[rootY]) parent[rootY] = rootX;
            else if (rank[rootX] < rank[rootY]) parent[rootX] = rootY;
            else {
                parent[rootY] = rootX;
                rank[rootX]++;
            }
        }
    }
}
