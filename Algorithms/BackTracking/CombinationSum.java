package Algorithms.BackTracking;

import java.util.*;

/**
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 18 Feb 2025
 * @link 39. Combination Sum <a href="https://leetcode.com/problems/combination-sum/">LeetCode link</a>
 * @topics Array, Backtracking
 * @companies Amazon(6), Google(4), Meta(3), Microsoft(3), Bloomberg(3), Apple(2), NetApp(2), Airbnb(16), TikTok(10), Walmart Labs(7), ByteDance(5), Confluent(4), LinkedIn(3), Oracle(3), Zoho(3), Citadel(3), Pinterest(3)

 Here we can include duplicates i.e same index number multiple times

 We can solve this using two different backtracking approaches:

 1) Using Combination Tree

  1a) COMBINATION TREE: Backtracking with pruning and index-controlled recursion ✅ -- {@link #combinationSumUsingCombinationTreeWithStartIndexLoopBacktracking(int[], int)}
  -- loop given array but keep track of fromIndex

  1b) COMBINATION TREE: DFS Tree with Level Control (DFS with Index Tracking) ✅ -- {@link #combinationSumUsingCombinationTreeWithIncludeExcludeBacktracking(int[], int)}
  -- binary tree one with runningSum and other with i++


 2) Using Permutation Tree

  PERMUTATION TREE: Pure Recursive Enumeration + Manual Deduplication ❌ -- {@link #combinationSumUsingPermutationTreeBacktracking1(int[], int)}
  -- loop given array as a full brute force
  -- only choose this when you need all possible combinations not the possible unique combinations or sets

 NOTE: 🔥
 1. In Permutation (0 to n-1 as children) the order matters and in Combination (startI to n-1 as children) the order doesn't matter ---> so, here as we don't need order --> prefer combination tree
 2. n = total number of elements
    k = the size of each combination and
    k! = is the number of ways to arrange k elements = number of permutations of k distinct elements
 3. Permutation P(n,k) = n! / (n-k)!, lets say we have 54 cards & we choose 4 cards then P(54,4) = 54! * 53! * 52! * 51! = 54!/50! = 54!/(54-4)!
 4. Combination C(n,k) = P(n,k) / k! = n! / k! * (n-k)! ---> number of combinations of n distinct elements taken k at a time
 5. So P(k,k) = k!/(k-k)! = k!/0! = k! ---> number of permutations of k distinct elements is k! when combination size is k
 6. Similarly C(k,k) = P(k,k) / k! = k!/k! = 1 ---> number of combinations of k distinct elements is 1 when combination size is k

 */
public class CombinationSum {
    public static void main(String[] args) {
        int[] candidates = {2, 3, 6, 7};
        int target = 7;
        System.out.println("combinationSum using Permutation Tree => " + combinationSumUsingPermutationTreeBacktracking1(candidates, target));
        System.out.println("combinationSum using Combination Tree => " + combinationSumUsingCombinationTreeWithStartIndexLoopBacktracking(candidates, target));
    }

    /**
     * @TimeComplexity O(n * n^n * nlogn) or O(n * n^k * nlogn)
     * @SpaceComplexity O(n), where n is the subLst size but the actual space complexity is O(n * n!) for storing all permutations

     <pre>

     --> this backtracking is not n!.
     However, n^n / n^k cause each element can choose all n elements and we stop when target is reached
     here, the left side n is for cloning the subLst and middle n^n is for generating all possible combinations and right side nlogn is for sorting the subLst


        Approach: Permutation Tree with Index-controlled backtracking (optimal form)

        candidates = [2,3,4,7]
        target = 7

        Here we start from i=0 and iterate all candidates
        But here we have risk of duplicate subList due to Permutation Tree

                                                                                              []
                                        _______________________________________________________|_________________________________________
                                        |                                                      |                     |                    |
                                       [2]                                                    [3]                   [6]                  [7]
              __________________________|___________________________      _____________________|_____________________                                          ✅
             |                                    |       |        |      |               |             |           |
           [2,2]                                [2,3]   [2,6]    [2,7]   [3,2]          [3,3]         [3,6]       [3,7]
    ________|____________                 ________|____________
   |       |        |    |               |       |        |    |
[2,2,2] [2,2,3] [2,2,6] [2,2,3]       [2,3,2] [2,3,3] [2,3,6] [2,3,7]
          ✅                            ✅

     </pre>
     */
    public static List<List<Integer>> combinationSumUsingPermutationTreeBacktracking1(int[] candidates, int target) {
        List<List<Integer>> list = new ArrayList<>();
        backtrack(candidates, target, list, new ArrayList<>());
        return list;
    }
    private static void backtrack(int[] arr, int target, List<List<Integer>> list, List<Integer> subList) {
        if (target == 0 ) {
            List<Integer> subListCopy = new ArrayList<>(subList);
            Collections.sort(subListCopy); // fix duplicates
            if (!list.contains(subListCopy)) list.add(subListCopy);
            return;
        } else if (target < 0) {
            return;
        }

        for (int num: arr) {
            subList.add(num);
            backtrack(arr, target-num, list, subList);
            subList.remove(subList.size() - 1);
        }
    }




    /**
     * @TimeComplexity O (n * N ^ (t/m)) or O(n * N ^ (t/m + 1)) where t = target and m = min value in candidates
     * @SpaceComplexity O(k) or O(k + n^k), where k = maximum number of elements in any combination and n^k is for storing all combinations


       Approach: Combination Tree with Index-controlled backtracking (optimal form)

                     0 1 2 3
       candidates = [2,3,6,7]
       target = 7
       keep track of fromIndex / startIndex
                                                            i=0
                                  i0                        [ ]                                             i1                i2     i3
                                  ___________________________|_________________________________________________________________________
                                  |                                                                         |                 |       |
                       i0        [2]                  i1                        i2          i3      i1     [3] i2  i3     i2 [6] i3  [7]
                       ___________|__________________________________________________________        _______|_______      ____|____
                       |                              |                         |           |        |      |      |      |       |
         i0     i1   [2,2]   i2        i3     i1     [2,3] i2   i3       i2   [2,6] i3    [2,7]    [3,3]  [3,6]  [3,7]  [6,6]   [6,7]
         ______________|_________________     _________|__________       _______|______     ❌
         |       |            |         |     |        |         |       |            |
       [2,2,2] [2,2,3]    [2,2,6]  [2,2,7]  [2,3,3]  [2,3,6]  [2,3,7]  [2,6,6]   [2,6,7]



     */
    public static List<List<Integer>> combinationSumUsingCombinationTreeWithStartIndexLoopBacktracking(int[] candidates, int target) {
        List<List<Integer>> list = new ArrayList<>();
        List<Integer> subLst = new ArrayList<>();
        backtrack(candidates, target, subLst, 0, list); // here use target as runningDiff
        return list;
    }

    private static void backtrack(int[] candidates, int target, List<Integer> subLst, int startI, List<List<Integer>> list) {
        if (target == 0) {
            list.add(new ArrayList<>(subLst));
            return;
        } else if (target < 0) { // else if (target < 0 || startI >= candidates.length) return;
            return;
        }

        for (int i = startI; i < candidates.length; i++) {
            subLst.add(candidates[i]);
            backtrack(candidates, target-candidates[i], subLst, i, list); // as we want duplicates, we are not doing i+1
            subLst.remove(subLst.size() - 1);
        }
    }







    /**
     * @TimeComplexity O(N * 2^T), where T is the target and 2^ cause we use binary decision tree
     * @SpaceComplexity O(M * S) — M = number of valid combinations, S = avg size

      <pre>

      Include = Include same index element
      Exclude = Exclude current index element and move to next index


                              ↓ i0 sum++           [] --- i=0                            ↓ i++=1
                                  _________________|__________________________________________
                                  |                                                          |
                     ↓ i0 sum++  [2] ---> i=0            ↓ i++=1              ↓ i1 sum++     [] ---> i=1    ↓ i++=2
                            _____|___________________________                          ______|________________
                           |                                |                         |                       |
            ↓ i0 sum++   [2,2]  ↓ i++=1       ↓ i1 sum++   [2]   ↓ i++=2             [3]                      [] --->i=2   ↓ 1++=3
                   _______|_______                   _______|_______           _______|_______       __________|_____________
                  |               |                 |               |          |              |      |                       |
                [2,2,2]          [2,2]            [2,3]            [2]        [3,3]          [3]    [6]                      [] --->i=3     ↓ i++=4
            ______|______     _____|______    _______|_______   _____|_____                                     _____________|_______________
           |             |    |          |    |             |   |          |                                    |                           |
      [2,2,2,2]    [2,2,2]   [2,2,3]   [2,2]  [2,3,6]    [2,3]  [2,6]     [2]                                  [7]                         []   -------> i=4
          ❌                  ✅ ________|________          ____|____     _|________
                                  |               |          |       |     |        |
                               [2,2,6]          [2,2]       [2,6,7] [2,6]  [2,7]   [2]
                                 ❌       ________|________
                                          |                |
                                       [2,2,7]          [2,2]
                                          ❌


          or


                                                   [] ---> i=0
                                  _________________|__________________________________________
                                  |                                                          |
                        i=0 <--- [2]                                                         [] ---> i=1
                            _____|___________________________                          ______|________________
                           |                                |                         |                       |
                i=0 <--- [2,2]                             [2]                       [3]                      [] ---> i=2
                   _______|_______                   _______|_______           _______|_______       __________|_____________
                  |               |                 |               |          |              |      |                       |
      i=0 <--- [2,2,2]          [2,2]             [2,3]            [2]        [3,3]          [3]    [6]                      [] --->i=3
            ______|______     _____|______    _______|_______   _____|_____                                     _____________|_______________
           |             |    |          |    |             |   |          |                                    |                           |
      [2,2,2,2]    [2,2,2]   [2,2,3]   [2,2]  [2,3,6]    [2,3]  [2,6]     [2]                                  [7]                         []   -------> i=4
          ❌                  ✅ ________|________          ____|____     _|________
                                  |               |          |       |     |        |
                               [2,2,6]          [2,2]       [2,6,7] [2,6]  [2,7]   [2]
                                 ❌       ________|________
                                          |                |
                                       [2,2,7]          [2,2]
                                          ❌


         So, using the above Permutation tree or current binary tree approach -> then we don't need to sort the list and check duplicates again

      </pre>
     */
     public static List<List<Integer>> combinationSumUsingCombinationTreeWithIncludeExcludeBacktracking(int[] candidates, int target) {
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









    public static List<List<Integer>> combinationSumUsingPermutationTreeBacktracking2(int[] candidates, int target) {
        List<List<Integer>> list = new ArrayList<>();
        backtrack(candidates, target, list, "", 0);
        return list;
    }

    private static void backtrack(int[] arr, int target, List<List<Integer>> list, String tempStr, int tempSum) {
        for (int num: arr) {
            int currSum = num + tempSum;
            if (currSum > target) continue; // skipping odd ones recursion
            else if ( currSum < target) backtrack(arr, target, list, tempStr+num+",", currSum); // recursion
            else { // (num + tempSum) == target
                List<Integer> res = new ArrayList<>();
                for (String s: (tempStr+num).split(",") ) {
                    if(!s.isEmpty()) res.add(Integer.parseInt(s));
                }
                Collections.sort(res);
                if (!list.contains(res)) list.add(res);
            }
        }
    }



    public static List<List<Integer>> combinationSumUsingPermutationTreeBacktracking3(int[] candidates, int target) {
       List<List<Integer>> list = new ArrayList<>();
       backtrack(candidates, target, list, new ArrayList<>(), new HashMap<>(), new HashSet<>());
       return list;
    }
    private static void backtrack(int[] candidates, int target, List<List<Integer>>list, List<Integer> subLst, Map<Integer, Integer> counter, Set<Map<Integer, Integer>> seen) {
        if(target<0) return;
        if(target==0 && !seen.contains(counter)) {
            list.add(new ArrayList<>(subLst));
            seen.add(new HashMap<>(counter));
            return;
        }
        for(int c: candidates) {
            subLst.add(c);
            counter.merge(c, 1, Integer::sum);
            backtrack(candidates, target-c, list, subLst, counter, seen);
            subLst.remove(subLst.size()-1);
            counter.merge(c, -1, Integer::sum);
            if(counter.get(c)==0) counter.remove(c);
        }
    }




    public static List<List<Integer>> combinationSumUsingPermutationTreeBacktracking4(int[] candidates, int target) {
        List<List<Integer>> result = new ArrayList<>();
        backtrack(candidates, target, new TreeMap<>(), 0, result, new HashSet<>());
        return result;
    }
    private static void backtrack(int[] candidates, int target, Map<Integer, Integer> map, int sum, List<List<Integer>> result, Set<String> seen) {
        if (sum == target) {
            if (seen.add(map.toString())) {
                List<Integer> subList = new ArrayList<>();
                for (Integer num: map.keySet()) {
                    subList.addAll(Collections.nCopies(map.get(num), num));
                }
                result.add(subList);
            }
            return;
        } else if (sum > target) return;

        for (int candidate: candidates) {
            map.merge(candidate, 1, Integer::sum);
            backtrack(candidates, target, map, sum+candidate, result, seen);
            map.merge(candidate, -1, Integer::sum);
            if (map.get(candidate) == 0) map.remove(candidate);
        }
    }
}
