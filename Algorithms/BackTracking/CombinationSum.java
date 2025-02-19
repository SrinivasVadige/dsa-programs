package Algorithms.BackTracking;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 18 Feb 2025
 */
public class CombinationSum {
    public static void main(String[] args) {
        int[] candidates = {2, 3, 6, 7};
        int target = 7;
        System.out.println("combinationSumMyApproach(candidates, target) => " + combinationSumMyApproach(candidates, target));
        System.out.println("combinationSum(candidates, target) => " + combinationSum(candidates, target));
    }

    /**
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
                List<Integer> res = new ArrayList<>();
                for (String s: (tempStr+num).split(",") )
                    if(!s.isEmpty()) res.add(Integer.parseInt(s));
                Collections.sort(res);
                if (!lst.contains(res)) lst.add(res);
            }
        }
    }

    /**
        THOUGHTS:
        ---------
        1) Instead of above tree with sorting to check duplicates, we use binary tree structure like below
        2) when candidates = [2,3,6,7], target = 7


                                               []
                               ________________|__________________________________________
                              |                                                          |
                             [2]                                                         []
                        _____|___________________________                          ______|________________
                       |                                |                         |                       |
                     [2,2]                             [2]                       [3]                      []
               _______|_______                   _______|_______           _______|_______       __________|__________
              |               |                 |               |          |              |      |                   |
            [2,2,2]          [2,2]            [2,3]            [2]        [3,3]          [3]    [6]                  []
        ______|______     _____|______    _______|_______   _____|_____                                     _________|______
       |             |    |          |    |             |   |          |                                    |              |
  [2,2,2,2]    [2,2,2]   [2,2,3]   [2,2]  [2,3,6]    [2,3]  [2,6]     [2]                                  [7]             []
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
    public static void backtrack(int[] candidates, int target, List<Integer> subLst, List<List<Integer>> lst, int i, int sum) {
        if (sum == target) {
            lst.add(new ArrayList<>(subLst));
            return;
        }
        if (i >= candidates.length || sum > target) return;

        subLst.add(candidates[i]);
        backtrack(candidates, target, subLst, lst, i, sum+candidates[i]);
        subLst.remove(subLst.size() - 1);
        backtrack(candidates, target, subLst, lst, i+1, sum);
    }





    public static List<List<Integer>> combinationSum2(int[] candidates, int target) {
        List<List<Integer>> lst = new ArrayList<>();
        backtrack2(candidates, target, new ArrayList<>(), lst, 0); // start == index
        return lst;
    }
    public static void backtrack2(int[] candidates, int target, List<Integer> subLst, List<List<Integer>> lst, int start) {
        if (target == 0) {
            lst.add(new ArrayList<>(subLst));
            return;
        }
        if (target < 0) return;

        for (int i = start; i < candidates.length; i++) {
            subLst.add(candidates[i]);
            backtrack2(candidates, target - candidates[i], subLst, lst, i);
            subLst.remove(subLst.size() - 1);
        }
    }





    public static List<List<Integer>> combinationSum3(int[] candidates, int target) {
        List<List<Integer>> lst = new ArrayList<>();
        List<Integer> subLst = new ArrayList<>();
        combinationSumHelper(candidates, target, subLst, lst, 0);
        return lst;
    }

    private static void combinationSumHelper(int[] candidates, int target, List<Integer> subLst, List<List<Integer>> lst, int index) {
        if (target == 0) lst.add(new ArrayList<>(subLst));
        for (int i = index; i < candidates.length; i++) {
            if (candidates[i] > target) continue;
            subLst.add(candidates[i]);
            combinationSumHelper(candidates, target - candidates[i], subLst, lst, i);
            subLst.remove(subLst.size() - 1);
        }
    }
}
