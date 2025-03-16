package Algorithms.IntegerArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 15 March 2025
 */
public class ClosestEqualElementQueries {
    public static void main(String[] args) {
        int[] nums = {1, 2, 3, 4, 5};
        int[] queries = {3, 1, 2, 4, 2};
        System.out.println(solveQueries(nums, queries));
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
    public List<Integer> solveQueries2(int[] nums, int[] queries) {
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
}
