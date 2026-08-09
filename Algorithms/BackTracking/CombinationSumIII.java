package Algorithms.BackTracking;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 11 May 2025
 */
public class CombinationSumIII {
    public static void main(String[] args) {
        int k = 3, n = 7; // k=size of subLst, n=targetSum
        System.out.println("combinationSum3_1 => " + combinationSum3UsingRecursiveBackTracking1(k, n));
        System.out.println("combinationSum3_2 => " + combinationSum3UsingRecursiveBackTracking1(k, n));
    }
    /**

    [1,2,3,4,5,6,7,8,9], n=7


                                                                           []
                                            i=1 children  __________________|________________________________
                                                          |                                                 |
                                                         []                                                [1]
                          i=2 children ___________________|__________________________________         ______|_______ i=2 children
                                      |                                                    |          |            |
                                     []                                                   [2]         [1]          [1,2]
                  i=3  ______________|______________                         i=3 __________|__________________
                       |                           |                             |                            |
                      []                          [3]                          [2]                          [2,3]
            i=4________|________       i=4 ________|________         i=4________|________          i=4________|________
               |               |           |               |            |               |             |               |
               []              [4]         [3]            [3,4]         [2]            [2,4]         [2,3]            [2,3,4]
i=5         []   [5]       [4]  [4,5]   [3] [3,5]     [3,4][3,4,5]   [2]  [2,5]    [2,4] [2,4,5]   [2,3] [2,3,5]    [2,3,4] [2,3,4,5]

        OBS:
        ----
        1) All nums are unique in subLst
        2) Increase i++ in both
        3) sum++ in right
     */
    public static List<List<Integer>> combinationSum3UsingRecursiveBackTracking1(int k, int n) {
        List<List<Integer>> lst = new ArrayList<>();
        backtrack(k, 1, n, 0, lst, new ArrayList<>());
        return lst;
    }
    private static void backtrack(int size, int i, int sum, int runningSum, List<List<Integer>> lst, List<Integer> subLst) {
        // always keep sum==runningSum edge case at top. Cause, in prev recursion we added the sum & i++. so, it'll be i=k+1 instead of k
        if(sum==runningSum && subLst.size()==size) {
            lst.add(new ArrayList<>(subLst));
            return;
        }
        if(i>9 || subLst.size() > size || runningSum > sum) return;

        backtrack(size, i+1, sum, runningSum, lst, subLst);

        subLst.add(i);
        backtrack(size, i+1, sum, runningSum+i, lst, subLst);
        subLst.remove(subLst.size()-1);
    }


    /**

    [1,2,3,4,5,6,7,8,9], n=7
                                                                                      []
                     startNum=1_______________________________________________________|_________________________________________________________________________
                               |                  |               |                 |                   |                |             |            |          |
                              [1]                [2]             [3]               [4]                 [5]              [6]           [7]          [8]        [9]
        sN=2____________________|   sN=3___________|___________
           |                           |
          [1,2]  [1,3] [1,4] ......    [2,3]

     */
    public static List<List<Integer>> combinationSum3UsingRecursiveBackTracking2(int k, int n) {
        List<List<Integer>> lst = new ArrayList<>();
        backtrack(k, 1, n, lst, new ArrayList<>());
        return lst;
    }
    private static void backtrack(int size, int startNum, int runningDiff, List<List<Integer>> lst, List<Integer> subLst) {
        if(size==subLst.size() && runningDiff==0) {
            lst.add(new ArrayList<>(subLst));
            return;
        }
        if(subLst.size() > size || startNum > 9) return;

        for (int i = startNum; i<=9; i++) {
            subLst.add(i);
            backtrack(size, i+1, runningDiff-i, lst, subLst);
            subLst.remove(subLst.size()-1);
        }
    }


    public static List<List<Integer>> combinationSum3UsingRecursiveBackTracking3(int k, int n) {
        List<List<Integer>> lst = new ArrayList<>();
        backtrack3(k, n, 1, lst, new ArrayList<>());
        return lst;
    }
    private static void backtrack3(int k, int n, int startNum, List<List<Integer>> lst, List<Integer> subLst) {
        if(k==0 && n==0) {
            lst.add(new ArrayList<>(subLst));
            return;
        }
        if(k<=0) return;

        int maxSize=9; // maxSize = Math.min(9, n);
        for (int i = startNum; i<= maxSize; i++) {
            subLst.add(i);
            backtrack3(k-1, n-i, i+1, lst, subLst);
            subLst.remove(subLst.size()-1);
        }
    }














    public static int combinationSum3Count(int k, int n) {
        return combinationSum3Count(k, n, new int[k], 1, 0);
    }

    public static int combinationSum3Count(int k, int n, int[] arr, int index, int sum) {
        if (k == 0) return sum == n ? 1 : 0;
        int count = 0;
        for (int i = index; i <= 9; i++) {
            arr[k - 1] = i;
            count += combinationSum3Count(k - 1, n, arr, i + 1, sum + i);
        }
        return count;
    }

    public static int combinationSum3Count2(int k, int n) {
        int[][] dp = new int[k + 1][n + 1];
        dp[0][0] = 1;
        for (int i = 1; i <= k; i++) {
            for (int j = 1; j <= n; j++) {
                for (int l = 1; l <= 9; l++) {
                    if (j - l >= 0) {
                        dp[i][j] += dp[i - 1][j - l];
                    }
                }
            }
        }
        return dp[k][n];
    }
}
