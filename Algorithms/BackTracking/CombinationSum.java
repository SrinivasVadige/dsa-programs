package Algorithms.BackTracking;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 18 Feb 2025
 *
 * Here we can include duplicates i.e same index number multiple times
 *
 * We can solve this using three different backtracking approaches:
 *
 * 1) DFS Tree with Level Control (DFS with Index Tracking) ✅ -- {@link #combinationSum(int[], int)} and {@link #combinationSum2(int[], int)}
 *  -- binary tree one with runningSum and other with i++
 *
 * 2) Backtracking with pruning and index-controlled recursion ✅ -- {@link #combinationSum3(int[], int)}
 *  -- loop given array but keep track of fromIndex
 *
 * 3) Pure Recursive Enumeration + Manual Deduplication ❌ -- {@link #combinationSumMyApproach(int[], int)} and {@link #combinationSumMyApproach2(int[], int)}
 *  -- loop given array as a full brute force
 *  -- only choose this when you need all possible combinations not the possible unique combinations or sets
 */
public class CombinationSum {
    public static void main(String[] args) {
        int[] candidates = {2, 3, 6, 7};
        int target = 7;
        System.out.println("combinationSum(candidates, target) => " + combinationSum(candidates, target));
        System.out.println("combinationSum3(candidates, target) => " + combinationSum3(candidates, target));
        System.out.println("combinationSumMyApproach(candidates, target) => " + combinationSumMyApproach(candidates, target));
    }
    /**
     * @TimeComplexity O(2^T), where T is the target
     * @SpaceComplexity O(M * S) — M = number of valid combinations, S = avg size
     *
     * Approach: DFS Tree with Level Control (DFS with Index Tracking)
     * or
     * Tree-style Backtracking (Top-down with all choices at each step)
     * or
     * Brute-force Tree / Recursive Tree Search
     * This is always a binary tree i.e exactly two children no matter what
     *
     * Control Mechanism -> Index-based (uses i)
     * Duplicate Avoidance -> Built-in (by index logic)
     * Performance -> 	More efficient
     * Real-world label -> Combination Sum template
     * Use case fit -> Subset/Combination generation
     *
     *
     * 🔥 combinationSum() 🔥
     * ✅ RECOMMENDED APPROACH
     * 👍 Very faster than combinationSumMyApproach() and combinationSumMyApproach2()
        THOUGHTS:
        ---------
        1) Instead of above tree with sorting to check duplicates, we use binary tree structure like below
        2) when candidates = [2,3,6,7], target = 7
        3) Here in left child change (subLst & sum) but not i --- sum++  --- stop recursion by sum > target
        4) And in right child change i but not (subLst & sum) --- i++    --- stop recursion by i >= candidates.length
        5) Left and right child's "i" index is never the same


                               ↓                                                          ↓
                              sum++            [] --- i=0                               i++=1
                               ________________|__________________________________________
                       ↓      |                                                          |                 ↓
                      sum++  [2]                       i++=1                   sum++     [] --->i=1      i++=2
                        _____|___________________________                          ______|________________
              ↓        |                                |        ↓                |                       |               ↓
             sum++   [2,2]   i++               sum++   [2]     i++=2             [3]                      [] --->i=2     1++=3
               _______|_______                   _______|_______           _______|_______       __________|_____________
              |               |                 |               |          |              |      |                       |                  ↓
            [2,2,2]          [2,2]            [2,3]            [2]        [3,3]          [3]    [6]                      [] --->i=3        i++=4
        ______|______     _____|______    _______|_______   _____|_____                                     _____________|__________________
       |             |    |          |    |             |   |          |                                    |                          |
  [2,2,2,2]    [2,2,2]   [2,2,3]   [2,2]  [2,3,6]    [2,3]  [2,6]     [2]                                  [7]                         []   -------> i=4
      ❌                  ✅ ________|________          ____|____     _|________
                              |               |          |       |     |        |
                           [2,2,6]          [2,2]       [2,6,7] [2,6]  [2,7]   [2]
                             ❌       ________|________
                                      |                |
                                   [2,2,7]          [2,2]
                                      ❌

        3) So, using this binary tree approach, don't need to sort the list and check duplicates again


     */
     public static List<List<Integer>> combinationSum(int[] candidates, int target) {
        List<List<Integer>> lst = new ArrayList<>();
        backtrack(candidates, target, new ArrayList<>(), lst, 0, 0); // start == index
        return lst;
    }
    public static void backtrack(int[] candidates, int target, List<Integer> subLst, List<List<Integer>> lst, int i, int runningSum) {
        if (runningSum == target) {
            lst.add(new ArrayList<>(subLst));
            return;
        }
        if (i >= candidates.length || runningSum > target) return;

        subLst.add(candidates[i]);
        backtrack(candidates, target, subLst, lst, i, runningSum+candidates[i]); // CHANGE SUB-LIST & SUM
        subLst.remove(subLst.size() - 1);
        backtrack(candidates, target, subLst, lst, i+1, runningSum); // CHANGE I
    }




    /**
     * same as above combinationSum() but instead of runningSum, we use runningDiff here
     */
    public static List<List<Integer>> combinationSum2(int[] candidates, int target) {
        List<List<Integer>> lst = new ArrayList<>();
        backtrack2(candidates, target, new ArrayList<>(), lst, 0); // start == index
        return lst;
    }
    public static void backtrack2(int[] candidates, int runningDiff, List<Integer> subLst, List<List<Integer>> lst, int start) {
        if (runningDiff == 0) {
            lst.add(new ArrayList<>(subLst));
            return;
        }
        if (runningDiff < 0 || start >= candidates.length) return;

        for (int i = start; i < candidates.length; i++) {
            subLst.add(candidates[i]);
            backtrack2(candidates, runningDiff - candidates[i], subLst, lst, i);
            subLst.remove(subLst.size() - 1);
        }
    }









    /**
     * Approach: Backtracking with pruning and index-controlled recursion
     * or
     * Index-controlled backtracking (optimal form)
     *
     * Stop recursion when:
     * 1) sum > target
     * 2) i >= candidates.length
     *
     *
     *                    0 1 2 3
     * when candidates = [2,3,6,7], target = 7
     *
     *                                      i=0                                                                          []                                        i=1                i=2              i=3
     *                                      _____________________________________________________________________________|________________________________________________________________________________
     *                                      |                                                                                                                       |                   |                |
     *                       i=0          [2]                   i=1                               i=2       i=3                             i=1            i=2     [3]   i=3     i=2   [6]   i=3        [7]
     *                        _____________|_________________________________________________________________                               ________________________|______      _______|_______
     *                       |                                    |                                |         |                              |                |            |      |             |
     *   0             1   [2,2]    2           3          1    [2,3] 2        3          2       [2,6]  3  [2,7]         i=1     i=2    [3,3]  i=3       [3,6]        [3,7]   [6,6]        [6,7]
     *   ____________________|____________________          _______|____________          _________|______                  ________________|______     ______|________
     *   |             |            |            |         |          |        |          |              |                 |         |            |     |             |
     * [2,2,2]       [2,2,3]    [2,2,6]     [2,2,7]      [2,3,3]    [2,3,6] [2,3,7]     [2,6,6]     [2,6,7]             [3,3,3]   [3,3,6]      [3,3,7] [3,6,6]     [3,6,7]
     *
     *
     *
     */
    public static List<List<Integer>> combinationSum3(int[] candidates, int target) {
        List<List<Integer>> lst = new ArrayList<>();
        List<Integer> subLst = new ArrayList<>();
        combinationSumHelper(candidates, target, subLst, lst, 0);
        return lst;
    }
    private static void combinationSumHelper(int[] candidates, int runningDiff, List<Integer> subLst, List<List<Integer>> lst, int fromIndex) {
        if (runningDiff == 0) {
            lst.add(new ArrayList<>(subLst));
            return; // optional
        }
        for (int i = fromIndex; i < candidates.length; i++) { // toIndex loop
            if (candidates[i] > runningDiff) continue; // or if (runningDiff < 0) or if (runningDiff - candidates[i] < 0)
            subLst.add(candidates[i]);
            combinationSumHelper(candidates, runningDiff - candidates[i], subLst, lst, i);
            subLst.remove(subLst.size() - 1);
        }
    }







    /**
     * This approach looks similar to above combinationSum3() -- but here we didn't use any "fromIndex" variable
     *
     * Approach: Pure Recursive Enumeration + Manual Deduplication
     * Control Mechanism -> None — free recursion over all candidates
     * Duplicate Avoidance -> Manual (by sorting + checking lst.contains)
     * Performance -> Slower due to deduplication overhead
     * Real-world label -> Brute-force + post-processing
     * Use case fit -> Subset/Combination generation
     *
     * 👎 combinationSumMyApproach() and combinationSumMyApproach2() similar, works fine but slow
     * ❌ DON'T USE THIS APPROACH
     * because we get same target for same candidates but in different order. Eg: [2,2,3] and [2,3,2] are same targetSum but we only need one kind of same candidates.
     * We can compare and add to lst only if !lst.contains(sorted subLst)
     *
        THOUGHTS:
        ---------
        1) Unique ints but the same number can be used unlimited times
        2) when candidates = [2,3,6,7], target = 7

                                                                      []
                                        ______________________________|_______________________________
                                        |                  |                     |                    |
                                       [2]                [3]                   [6]                  [7]
              __________________________|___________________________                                 ✅
             |                                    |       |        |
           [2,2]                                [2,3]   [2,6]    [2,7]
    ________|____________                 ________|____________
   |       |        |    |               |       |        |    |
[2,2,2] [2,2,3] [2,2,6] [2,2,3]       [2,3,2] [2,3,3] [2,3,6] [2,3,7]
          ✅                            ✅

        3) This looks like a tree, with candidates.length number of children
        4) We have duplicate sums like [2,2,3], [2,3,2]. So, first check we have the same item is the list
        5) Dp memo? --> NO
        6) As we're sorting and comparing the duplicate entries, it's slower than below tree
        7) Use a pass by value temp subList or tempStr to stay the dedicated list at specific levels
     */
    public static List<List<Integer>> combinationSumMyApproach(int[] candidates, int target) {
        List<List<Integer>> lst = new ArrayList<>();
        backtrack(candidates, target, lst, "", 0);
        return lst;
    }
    private static void backtrack(int[] arr, int target, List<List<Integer>> lst, String tempStr, int tempSum) {
        for (int num: arr) {
            int currSum = num + tempSum;
            if (currSum > target) continue; // skipping odd ones recursion
            else if ( currSum < target) backtrack(arr, target, lst, tempStr+num+",", currSum); // recursion
            else { // (num + tempSum) == target
                List<Integer> subLst = new ArrayList<>();
                for (String s: (tempStr+num).split(",") )
                    if(!s.isEmpty()) subLst.add(Integer.parseInt(s));
                Collections.sort(subLst);
                if (!lst.contains(subLst)) lst.add(subLst);
            }
        }
    }


    static Set<Map<Integer, Integer>> set = new HashSet<>(); // instead of above list.contains(sorted subLst) use this set.contains(counter)
    public static List<List<Integer>> combinationSumMyApproach2(int[] candidates, int target) {
       List<List<Integer>> lst = new ArrayList<>();
       backtrack(candidates, target, lst, new ArrayList<>(), new HashMap<>());
       return lst;
    }
    private static void backtrack(int[] candidates, int target, List<List<Integer>>lst, List<Integer> subLst, Map<Integer, Integer> counter) {
        if(target<0) return;
        if(target==0 && !set.contains(counter)) {
            lst.add(new ArrayList<>(subLst));
            set.add(new HashMap<>(counter));
            return;
        }
        for(int c: candidates) {
            subLst.add(c);
            counter.merge(c, 1, Integer::sum);
            backtrack(candidates, target-c, lst, subLst, counter);
            subLst.remove(subLst.size()-1);
            counter.merge(c, -1, Integer::sum);
            if(counter.get(c)==0) counter.remove(c);
        }
    }
}
