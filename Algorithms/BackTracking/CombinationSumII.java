package Algorithms.BackTracking;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 11 May 2025
 * @link https://leetcode.com/problems/combination-sum-ii/
 *
 * Same like {@link Algorithms.BackTracking.CombinationSum -- Combination Sum I} but sort arr & and skip duplicates
 */
public class CombinationSumII {
    public static void main(String[] args) {
        int[] candidates = {10,1,2,7,6,1,5};
        int target = 8;
        System.out.println("combinationSum2_1 => " + combinationSum2_1(candidates, target));
        System.out.println("combinationSum2_2 => " + combinationSum2_2(candidates, target));
    }



    /**
     * APPROACH 1
     * @TimeComplexity O(nlogn) + O(2^T), where T is the target value.
     * @SpaceComplexity O(2^T)
     *
     * Here both left and right children index++ -- cause no dups
     * and add sum++ only in left child
     *
     * NOTE:
     * 1. If you want duplicate index numbers then don't index++ in left child 🔥
     *
     * when candidates=[10,1,2,7,6,1,5] and target=8
     * after sort candidates=[1,1,2,5,6,7,10]
     * The reason we sorted cause we get [1,7], [7,1], but in the given sum the order doesn't matter
     * And we skip dups because [1,7] and [1,7] -- here first 1 is 0th index and second 1 is 1st index. but we don't want multiple [1,7]s
     *                                                                             ↓
     *                                                   ↓                        nextI=0              ↓
     *                                                  i=0, nextI=1               []               i=0, nextI=2 ---> to skip duplicates
     *                                                   __________________________|____________________
     *                                    ↓             |                                               |            ↓
     *                                  i=1,nI=2       [1]     i=1,nI=2                 i=2, nI=3       []        i=2, nextI=3
     *                                   _______________|_____________                    _______________|_____________
     *                          ↓        |                           |      ↓       ↓     |                           |
     *                         2,3     [1,1]     2,3        2,3     [1]    2,3     3,4    [2]    3,4            3,4   []      3,4
     *                          _________|_________          _________|_________   _______|_______              _______|_______
     *                          |                 |          |                 |   |             |              |             |
     *                        [1,1,2]          [1,1]       [1,2]              [1]  [2,5]        [2]            [5]            []
     *
     *
     *
     *                                                                            []
     *                                                   __________________________|____________________
     *                                                  |                                               |
     *                                                 [1]                                             []
     *                                   _______________|_____________                    _______________|_____________
     *                                   |                           |                    |                           |
     *                                 [1,1]                        [1]                  [2]                          []
     *                          _________|_________          _________|_________   _______|_______              _______|_______
     *                          |                 |          |                 |   |             |              |             |
     *                        [1,1,2]          [1,1]       [1,2]              [1]  [2,5]        [2]            [5]            []
     *
     */
    public static List<List<Integer>> combinationSum2_1(int[] candidates, int target) {
        Arrays.sort(candidates);
        List<List<Integer>> lst = new ArrayList<>();
        int nextI = 0;
        backtrack(candidates, lst, new ArrayList<>(), target, nextI);
        return lst;
    }
    private static void backtrack(int[] candidates, List<List<Integer>> lst, List<Integer> subLst, int runningDiff, int i) { // currI
        if(runningDiff == 0) {
            lst.add(new ArrayList<>(subLst));
            return;
        }
        else if(runningDiff < 0 || i>=candidates.length) return;

        subLst.add(candidates[i]); // currI
        int nextI = i+1;
        backtrack(candidates, lst, subLst, runningDiff-candidates[i], nextI); // nextI
        subLst.remove(subLst.size()-1);

        while(i+1<candidates.length && candidates[i]==candidates[i+1]) i++; // last duplicates
        nextI = i+1;
        backtrack(candidates, lst, subLst, runningDiff, nextI); // nextI
    }







    public static List<List<Integer>> combinationSum2_2(int[] candidates, int target) {
        List<List<Integer>> lst = new ArrayList<>();
        Arrays.sort(candidates);
        backtrack(candidates, target, new ArrayList<>(), lst, 0); // start == index i.e toIndex
        return lst;
    }

    public static void backtrack(int[] candidates, int target, List<Integer> subLst, List<List<Integer>> lst, int i) {
        if (target == 0) {
            lst.add(new ArrayList<>(subLst));
            return;
        }
        if (i >= candidates.length || target < 0) return;

        for (int j = i; j < candidates.length; j++) {
            if (j > i && candidates[j] == candidates[j - 1]) continue; // skip duplicates & note that j > i condition skip 1st iteration
            subLst.add(candidates[j]);
            backtrack(candidates, target - candidates[j], subLst, lst, j + 1); // toIndex or nextI
            subLst.remove(subLst.size() - 1);
        }
    }













    Set<String> set = new HashSet<>();
    public List<List<Integer>> combinationSum2WorkingButTLE(int[] candidates, int target) {
        Arrays.sort(candidates);
        List<List<Integer>> lst = new ArrayList<>();
        backtrack(candidates, lst, new ArrayList<>(), target, 0, new HashMap<>());
        return lst;
    }
    private void backtrack(int[] cands, List<List<Integer>> lst, List<Integer> subLst, int runningDiff, int fromIndex, Map<Integer, Integer> counter) {
        if(runningDiff < 0) return;
        else if(runningDiff == 0) {
            StringBuilder sb = new StringBuilder();
            for(int k: counter.keySet()) {
                int v=counter.get(k);
                sb.append(k).append(":").append(v).append(",");
            }
            if(set.add(sb.toString())) lst.add(new ArrayList<>(subLst));
            return;
        }

        for(int i=fromIndex; i<cands.length; i++) {
            subLst.add(cands[i]);
            counter.merge(cands[i], 1, Integer::sum);
            backtrack(cands, lst, subLst, runningDiff-cands[i], i+1, counter);
            subLst.remove(subLst.size()-1);
            counter.merge(cands[i], -1, Integer::sum);
            if(counter.get(cands[i])==0) counter.remove(cands[i]);
        }
    }
}
