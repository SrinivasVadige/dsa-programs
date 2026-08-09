package Algorithms.IntegerArray;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 15 March 2025
 */
public class ClosestEqualElementQueries {
    public static void main(String[] args) {
        int[] nums = {1, 2, 3, 4, 5};
        int[] queries = {3, 1, 2, 4, 2};
        System.out.println("solveQueries(nums, queries) => " + solveQueries(nums, queries));
        nums = new int[]{1, 2, 3, 4, 5};
        queries = new int[]{3, 1, 2, 4, 2};
        System.out.println("solveQueriesMyApproach(nums, queries) => " + solveQueriesMyApproach(nums, queries));
    }

    public static List<Integer> solveQueries(int[] nums, int[] queries) {
        int sz = nums.length;
        Map<Integer, List<Integer>> indices = new HashMap<>();
        for (int i = 0; i < sz; i++) {
            indices.computeIfAbsent(nums[i], _ -> new ArrayList<>()).add(i);
        }
        for (List<Integer> iList : indices.values()) {
            int m = iList.size();
            if (m == 1) {
                nums[iList.get(0)] = -1;
                continue;
            }
            for (int i = 0; i < m; i++) {
                int j = iList.get(i);
                int f = iList.get((i + 1) % m), b = iList.get((i - 1 + m) % m);
                int forward = Math.min((sz - j - 1) + f + 1, Math.abs(j - f));
                int backward = Math.min(Math.abs(b - j), j + (sz - b));
                nums[j] = Math.min(backward, forward);
            }
        }
        List<Integer> res = new ArrayList<>();
        for (int q : queries) {
            res.add(nums[q]);
        }
        return res;
    }



        /**
        PATTERNS:
        ---------
        1) nums is circular iList ---> i.e n-1 is connected to 0
        2) We can find distance in two ways (1) l to r (2) circular r to l
        3) so min(1,2)
        4)
    */
    public static List<Integer> solveQueriesButTLE(int[] nums, int[] queries) {
        Map<Integer, List<Integer>> map = new HashMap<>();
        for (int i=0; i<nums.length; i++) {
            List<Integer> l = map.getOrDefault(nums[i], new ArrayList<>());
            l.add(i);
            map.put(nums[i], l);
        }

        List<Integer> lst = new ArrayList<>();
        for (int i: queries) {
            int dist = Integer.MAX_VALUE;

            if (map.get(nums[i]).size()==1) dist=-1; // no-dups
            else {
                for (int j: map.get(nums[i])) { // iList
                    if (i==j) continue; // same index
                    // dist from left side
                    dist = Math.min(dist, Math.abs(j-i));
                    // dist from circular right side
                    dist = Math.min(dist, Math.min(j+nums.length-i, nums.length-j+i) );

                }
            }

            lst.add(dist);
        }
        return lst;
    }

    // same as above solveQueriesButTLE() but little optimized
    public static List<Integer> solveQueriesMyApproach(int[] nums, int[] queries) {
        Map<Integer, List<Integer>> map = new HashMap<>();
        for (int i=0; i<nums.length; i++) {
            map.computeIfAbsent(nums[i], _ -> new ArrayList<>()).add(i);
            // List<Integer> l = map.getOrDefault(nums[i], new ArrayList<>());
            // l.add(i);
            // map.put(nums[i], l);
        }

        List<Integer> lst = new ArrayList<>();
        for (int i: queries) {
            int dist = Integer.MAX_VALUE;

            if (map.get(nums[i]).size()==1) dist=-1; // no-dup
            else {
                // now consider only left, left-circular, right, right-circular
                // i.e max of 4 js
                Set<Integer> jList = new HashSet<>();
                List<Integer> iList = map.get(nums[i]);
                int iInIList = Collections.binarySearch(iList, i);

                // check left
                if ( 0 != iInIList ) jList.add(iList.get(iInIList-1));
                // check left circularly
                jList.add(iList.get(iList.size()-1));
                // check right
                if (iInIList != iList.size()-1) jList.add(iList.get(iInIList+1));
                // check right circularly
                jList.add(iList.get(0));

                jList.remove(i);
                for (int j: jList) {
                    // dist from left side
                    dist = Math.min(dist, Math.abs(j-i));
                    // dist from circular right side
                    dist = Math.min(dist, i>j? nums.length-i+j : nums.length-j+i);
                }
            }
            lst.add(dist);
        }
        return lst;
    }
}
