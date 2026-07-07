package Algorithms.DynamicProgramming;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 <pre>

 A subsequence is a sequence that can be derived from an array by deleting some or no elements without changing the order of the remaining elements.
 It's not necessarily be contiguous
 Example:
    [3, 6, 2, 7] is an subsequence of [0, 3, 1, 6, 2, 2, 7, 10] array - but it's not the Increasing subsequence





 Old thoughts:
 --------------

 nums = {1, 2, 4, 3}

  initial sequence -->                                     {}
                        ____________________________________|______________________________
                        |                                   |                  |          |
  start sequence -->  i0 {1}                              i1 {2}             i2 {4}     i3 {3}
         _______________|_________________        __________|_______           |
         |              |                |        |                |          {4,3} ❌
     i1 {1, 2}      i2 {1, 4}        i3 {1, 3}  {2,4}            {2,3}
     _____|_______      |                |
     |           |   {1,4,3} ❌       {2,4,3} ❌
  {1, 2, 4}  {1, 2, 3}
     |
  {1, 2, 4, 3} ❌

 i.e we have to make what will be the next element in each and every sequence in increasing order
 Here it looks like a tree (not a binary tree) and non-fixed child notes length - but max child nodes is (i to n-1)
 i.e for {1} the nodes are {1, 2}, {1, 3} and {1, 4} and looks like the width of the child is always less than parent

 we don't need to calculate i1 {2} i.e "index 1" scenario again cause we already have at i0 {1}'s child i.e i1 {1,2} "index 1"
 So, don't calculate same index again

 So, from the graph we need the max valid depth i.e dfs
 Calculate from right to left and save it in dp. Eg: so that we can use index 1 scenario from {2} in index 1 scenario in {1,2}



 Here we can solve this problem like {@link Knapsack_01_DP_SubsetSumProblem}
    1. 01 Knapsack Include/Exclude
    2. For loop




 </pre>
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 03 Nov 2024
 * @link 300. Longest Increasing Subsequence <a href="https://leetcode.com/problems/longest-increasing-subsequence/">Leetcode link</a>
 * @topics Array, Dynamic Programming, Binary Search
 * @companies Google(10), Amazon(4), Microsoft(3), Bloomberg(2), Meta(4), PayPal(3), TCS(2), ByteDance(2), TikTok(39), Samsung(12), Oracle(6), Walmart Labs(6), Square(6), Infosys(3), Splunk(3), Yandex(3), Huawei(3), Squarepoint Capital(3)
 */
public class LongestIncreasingSubsequence {
    public static void main(String[] args) {
        int[] nums = {1, 2, 4, 3};
        System.out.println(" --- 01 Knapsack Include/Exclude solutions --- ");
        System.out.printf("lengthOfLIS using 01 Knapsack Include/Exclude backtracking: %s\n", lengthOfLIS_Using01KnapsackBacktracking_TLE(nums));
        System.out.printf("lengthOfLIS using 01 Knapsack Include/Exclude top down memo dp: %s\n", lengthOfLIS_Using01KnapsackTopDownMemoDp(nums));
        System.out.printf("lengthOfLIS using 01 Knapsack Include/Exclude bottom up dp: %s\n", lengthOfLIS_Using01KnapsackBottomUpDp(nums));
        System.out.printf("lengthOfLIS using 01 Knapsack Include/Exclude bottom up dp optimized space: %s\n", lengthOfLIS_Using01KnapsackBottomUpDpOptimizedSpace(nums));

        System.out.println(" --- EndI for loop BottomUp DP solutions --- ");
        System.out.printf("lengthOfLIS using EndI for loop Backtracking: %s\n", lengthOfLIS_UsingForLoopEndIndexBacktracking_TLE(nums));
        System.out.printf("lengthOfLIS using EndI for loop TopDown DP 2: %s\n", lengthOfLIS_UsingForLoopEndIndexTopDownMemoDp(nums));
        System.out.printf("lengthOfLIS using EndI for loop BottomUp DP 3: %s\n", lengthOfLIS_UsingForLoopEndIndexBottomUpDp1(nums));

        System.out.println(" --- Sorted Subsequence solutions --- ");
        System.out.printf("lengthOfLIS using sorted subsequence: %s\n", lengthOfLIS_UsingSortedSubSequence(nums));
        System.out.printf("lengthOfLIS using sorted subsequence with binary search: %s\n", lengthOfLIS_UsingSortedSubSequenceBinarySearch(nums));
    }




    /**

         0 1 2 3 4 5
        [0,1,0,3,2,3]



i=-1                                           [ ]
                                ________________|________________
                                |                               |
i=0                            [0]                             [ ]
                       _________|____________          _________|_________
                       |                    |          |                 |
i=1                  [0,1]                 [0]        [1]               [ ]
                       |                    |          |         ________|________
                       |                    |          |         |               |
i=2                  [0,1]                 [0]        [1]       [0]             [ ]
                _______|_______        _____|_____
                |             |        |         |
i=3         [0,1,3]         [0,1]    [0,3]      [0]
                |      _______|_______
                |      |             |
i=4         [0,1,3]  [0,1,2]      [0,1]


     * @TimeComplexity O(2^n)
     * @SpaceComplexity O(n)
     */
    public static int lengthOfLIS_Using01KnapsackBacktracking_TLE(int[] nums) {
        return backtrack(nums, -1, 0);
    }
    public static int backtrack(int[] nums, int lastI, int currI) {
        if (currI >= nums.length) return 0;

        int include = 0;
        if (lastI == -1 || nums[lastI] < nums[currI]) {
            include = 1 + backtrack(nums, currI, currI+1);
        }
        int exclude = backtrack(nums, lastI, currI+1);

        return Math.max(include, exclude);
    }




    /**
     * @TimeComplexity O(n^2)
     * @SpaceComplexity O(n^2)
     */
    public static int lengthOfLIS_Using01KnapsackTopDownMemoDp(int[] nums) {
        return backtrack(nums, -1, 0, new Integer[nums.length + 1][nums.length]);
    }
    public static int backtrack(int[] nums, int lastI, int currI, Integer[][] memo) {
        if (currI >= nums.length) return 0;
        else if (memo[lastI+1][currI] != null) return memo[lastI+1][currI];

        int include = 0;
        if (lastI == -1 || nums[lastI] < nums[currI]) {
            include = 1 + backtrack(nums, currI, currI+1, memo);
        }
        int exclude = backtrack(nums, lastI, currI+1, memo);

        return memo[lastI+1][currI] = Math.max(include, exclude);
    }




    /**
                0 1 2 3 4 5
        nums = [0,1,0,3,2,3]

                        0   1   2   3   4   5        currI →
                    ----------------------------
        lastI = -1 |     4   3   3   1   2   1
        lastI =  0 |     -   3   2   1   2   1
        lastI =  1 |     -   -   2   1   1   1
        lastI =  2 |     -   -   -   1   2   1
        lastI =  3 |     -   -   -   -   0   0
        lastI =  4 |     -   -   -   -   -   1
        lastI =  5 |     -   -   -   -   -   -


     * @TimeComplexity O(n^2)
     * @SpaceComplexity O(n^2)
     */
    public static int lengthOfLIS_Using01KnapsackBottomUpDp(int[] nums) {
        int n = nums.length;
        int[][] dp = new int[n+1][n+1];

        for (int currI = n-1; currI >= 0; currI--) {
            for (int lastI = currI-1; lastI >= -1; lastI--) {

                int include = 0;
                if (lastI == -1 || nums[lastI] < nums[currI]) {
                    include = 1 + dp[currI + 1][currI + 1];
                }
                int exclude = dp[lastI + 1][currI + 1];

                dp[lastI + 1][currI] = Math.max(include, exclude);
            }
        }

        return dp[0][0];
    }




    /**
     *
     * @TimeComplexity O(n^2)
     * @SpaceComplexity O(n)
     */
    public static int lengthOfLIS_Using01KnapsackBottomUpDpOptimizedSpace(int[] nums) {
        int n = nums.length;
        int[] next = new int[n + 1]; // Represents next column dp[LastI][currI + 1]


        for (int currI = n - 1; currI >= 0; currI--) { // Fill columns from right to left

            int[] curr = new int[n + 1]; // Represents current column dp[LastI][currI]

            for (int lastI = currI - 1; lastI >= -1; lastI--) { // lastI ranges from currI-1 down to -1

                int include = 0;
                if (lastI == -1 || nums[lastI] < nums[currI]) {
                    include = 1 + next[currI + 1];
                }
                int exclude = next[lastI + 1];
                curr[lastI + 1] = Math.max(include, exclude);
            }

            // Handle the row lastI == currI (needed for future include = 1 + next[currI + 1])
            curr[currI + 1] = next[currI + 1];

            next = curr;
        }

        return next[0];
    }


















    /**

                                                                                         " "
        __________________________________________________________________________________|_____________________________________________________
        |             |             |                      |                                       |                                           |
        0             1             2                      3                                       4                                           5
        |             |        _____|_____         ________|_________           ___________________|___________________        ________________|________________
        |             |        |         |         |       |        |           |             |             |         |        |       |       |       |       |
     return1          0        0         1         0       1        2           0             1             2         3        0       1       2       3       4
                      |        |         |         |       |    ____|____       |             |         ____|____ ____|____
                      |        |         |         |       |    |        |      |             |         |       | |   |   |
                   return1  return1   return1   return1    0    0        1   return1          0         0       1 0   1   2
                                                           |                                  |
                                                           |                                  |
                                                         return1                           return1


     */
    public static int lengthOfLIS_UsingForLoopEndIndexBacktracking_TLE(int[] nums) {
        int ans = 0;
        for (int endI = 0; endI < nums.length; endI++) {
            ans = Math.max(ans, dfs(nums, endI));
        }
        return ans;
    }
    private static int dfs(int[] nums, int endI) {
        int max = 1;

        for (int prevI = 0; prevI < endI; prevI++) {
            if (nums[prevI] < nums[endI]) {
                max = Math.max(max, 1 + dfs(nums, prevI));
            }
        }

        return max;
    }



    public static int lengthOfLIS_UsingForLoopEndIndexTopDownMemoDp(int[] nums) {
        Integer[] memo = new Integer[nums.length];
        int ans = 0;

        for (int endI = 0; endI < nums.length; endI++) {
            ans = Math.max(ans, dfs(nums, endI, memo));
        }

        return ans;
    }
    private static int dfs(int[] nums, int endI, Integer[] memo) {
        if (memo[endI] != null) return memo[endI];

        int max = 1;
        for (int prevI = 0; prevI < endI; prevI++) {
            if (nums[prevI] < nums[endI]) {
                max = Math.max(max, 1 + dfs(nums, prevI, memo));
            }
        }
        return memo[endI] = max;
    }



    /**
    <pre>
        Brute Force, but we use previously calculated LIS


        0 ---------> n-1

                i →
        |____________|
        |_____|
        j →

        0 ---> i-1




        here the "i" is the endI like above {@link #lengthOfLIS_UsingForLoopEndIndexBacktracking_TLE} dfs




                                                0  1  2  3  4  5
                                        nums = [0, 1, 0, 3, 2, 3]
                                          dp = [1, 1, 1, 1, 1, 1]

        i = 1 (value = 1)                         🔽
            j = 0 (value = 0) => 0 < 1 ✅ dp = [1, 2, 1, 1, 1, 1]

        i = 2 (value = 0)                            🔽
            j = 0 (value = 0) => 0 < 0 ❌ dp = [1, 2, 1, 1, 1, 1]
            j = 1 (value = 1) => 1 < 0 ❌ dp = [1, 2, 1, 1, 1, 1]

        i = 3 (value = 3)                               🔽
            j = 0 (value = 0) => 0 < 3 ✅ dp = [1, 2, 1, 2, 1, 1]
            j = 1 (value = 1) => 1 < 3 ✅ dp = [1, 2, 1, 3, 1, 1]
            j = 2 (value = 0) => 0 < 3 ✅ dp = [1, 2, 1, 3, 1, 1]

        i = 4 (value = 2)                                  🔽
            j = 0 (value = 0) => 0 < 2 ✅ dp = [1, 2, 1, 3, 2, 1]
            j = 1 (value = 1) => 1 < 2 ✅ dp = [1, 2, 1, 3, 3, 1]
            j = 2 (value = 0) => 0 < 2 ✅ dp = [1, 2, 1, 3, 3, 1]
            j = 3 (value = 3) => 3 < 2 ❌ dp = [1, 2, 1, 3, 3, 1]

        i = 5 (value = 3)                                     🔽
            j = 0 (value = 0) => 0 < 3 ✅ dp = [1, 2, 1, 3, 3, 2]
            j = 1 (value = 1) => 1 < 3 ✅ dp = [1, 2, 1, 3, 3, 3]
            j = 2 (value = 0) => 0 < 3 ✅ dp = [1, 2, 1, 3, 3, 3]
            j = 3 (value = 3) => 3 < 3 ❌ dp = [1, 2, 1, 3, 3, 3]
            j = 4 (value = 2) => 2 < 3 ✅ dp = [1, 2, 1, 3, 3, 4]

        Finally, dp = [1, 2, 1, 3, 3, 4], Answer = 4



    </pre>


     * @TimeComplexity O(n^2)
     * @SpaceComplexity O(n)
     */
    public static int lengthOfLIS_UsingForLoopEndIndexBottomUpDp1(int[] nums) {
        int[] dp = new int[nums.length]; // LIS - Longest Increasing Subsequence count
        Arrays.fill(dp, 1); // at each index we can have a LIS of length 1

        for (int i = 1; i < nums.length; i++) { // endI
            for (int j = 0; j < i; j++) {
                if (nums[j] < nums[i]) {
                    dp[i] = Math.max(dp[i], dp[j] + 1); // +1 for counting dp[endI] num in the new sequence
                }
            }
        }

        return Arrays.stream(dp).max().orElse(0); // .getAsInt();
    }





    /**

         ← i
        |____________|
             |_______|
             j=i+1 →

     * @TimeComplexity O(n^2)
     * @SpaceComplexity O(n)
     */
    public static int lengthOfLIS_UsingForLoopEndIndexBottomUpDp2(int[] nums) {
        int n = nums.length;
        int[] LIS = new int[n]; // Longest Increasing Subsequence for each index
        Arrays.fill(LIS, 1); // at each index we can have a LIS of length 1

        for (int i = n-1; i >= 0; i--) {
            for (int j = i+1; j < n; j++) {
                if (nums[i] < nums[j])
                    LIS[i] = Math.max(LIS[i], 1+LIS[j]); // we already calculated LIS[j] and +1 for LIS length increment
            // but we have to check the next all ints too cause we might get something like {1, 2, 5, 3, 4} -> {1, 2, 3, 4} is LIS
            // and by using max() we only taking the max of all possibilities
            // Eg: LIS[1] = Math.max(LIS[1], 1+LIS[2], 1+LIS[3], 1+LIS[4]); i.e max of all possibilities sub indices
            // and so we're avoiding n^3 or more graph traversals using max() and dp[]
            }
        }

        return Arrays.stream(LIS).max().orElse(0); // .getAsInt();
    }




    /**

                i →
        |____________|
        |_____|
      ← j=i-1

     * @TimeComplexity O(n^2)
     * @SpaceComplexity O(n)
     */
    public static int lengthOfLIS_UsingForLoopEndIndexBottomUpDp3(int[] nums) {
        int n = nums.length;
        int[] dp = new int[n];

        for (int i = 0; i < n; i++) {
            for (int j = i-1; j >= 0; j--) {
                if (nums[i] > nums[j]) // we're skipping for == and > scenarios
                    dp[i] = Math.max(dp[i], dp[j]); // or Math.max(LIS[i], 1+LIS[j]);
            }
            dp[i]++; // skip this if we used Math.max(LIS[i], 1+LIS[j]);
        }

        return Arrays.stream(dp).max().orElse(0); // .getAsInt();
    }








    /**
     <pre>
                                                        0  1  2  3  4  5
                                                nums = [0, 1, 0, 3, 2, 3]

                                                                sub = [0] - Initially


        i = 1 (value = 1)
            1 > last_sub=0 ✅ - Just add last                => sub = [0, 1]



        i = 2 (value = 0)
            0 > last_sub=1 ❌ - Replace sub[0]=0 with 0      => sub = [0, 1]     🔥 replace - not add

                - Find first element >= 0
                    j = 0 (sub[0] = 0) => 0 > 0 ❌


        i = 3 (value = 3)
            3 > last_sub=1 ✅ - Just add                     => sub = [0, 1, 3]


        i = 4 (value = 2)
            2 > last_sub=3 ❌ - Replace sub[2]=3 with 2      => sub = [0, 1, 2]  🔥 replace - not add

                - Find first element >= 2
                    j = 0 (sub[0] = 0) => 0 < 2 ✅
                    j = 1 (sub[1] = 1) => 1 < 2 ✅
                    j = 2 (sub[2] = 3) => 3 < 2 ❌


        i = 5 (value = 3)
            3 > last_sub=2 ✅ - Just add last                => sub = [0, 1, 2, 3]



        Finally sub = [0, 1, 2, 3], Answer = sub.size() = 4


        TAKE AWAY:
        ----------
        1. In i=4, we replaced [0, 1, 3] with [0, 1, 2] -> so in future we'll have bigger nums than 2

    </pre>

     * @TimeComplexity O(n^2)
     * @SpaceComplexity O(n)
     */
    public static int lengthOfLIS_UsingSortedSubSequence(int[] nums) {
        ArrayList<Integer> sub = new ArrayList<>(); // insert the eles in asc order
        sub.add(nums[0]);

        for (int i = 1; i < nums.length; i++) {
            if (nums[i] > sub.get(sub.size() - 1)) {
                sub.add(nums[i]);
            } else { // Find the nums[i] position -> "first num in sub" >= nums[i] -> replace
                int j = 0;
                while (sub.get(j) < nums[i]) j++;
                sub.set(j, nums[i]); // we're replacing - not adding 🔥
            }
        }

        return sub.size();
    }





    /**
     * @TimeComplexity O(nlogn)
     * @SpaceComplexity O(n)
     */
    public static int lengthOfLIS_UsingSortedSubSequenceBinarySearch(int[] nums) {
        ArrayList<Integer> sub = new ArrayList<>();
        sub.add(nums[0]);

        for (int i = 1; i < nums.length; i++) {
            int num = nums[i];
            if (num > sub.get(sub.size() - 1)) {
                sub.add(num);
            } else {
                int j = binarySearch(sub, num);
                /*
                    // or
                    int j = Collections.binarySearch(sub, num);
                    if (j<0) j = -j-1;
                 */
                sub.set(j, num);
            }
        }

        return sub.size();
    }
    private static int binarySearch(ArrayList<Integer> sub, int num) {
        int l = 0;
        int r = sub.size() - 1;
        int mid;

        while (l < r) {
            mid = (l + r) / 2;
            if (sub.get(mid) == num) return mid;
            else if (sub.get(mid) < num) l = mid + 1;
            else r = mid;
        }

        return l;
    }









    /**
        Won't work as we can't change the index position of the number
     */
    public static int lengthOfLIS_UsingSort_NOT_WORKING(int[] nums) {

        Arrays.sort(nums); // won't work

        // skip dups

        List<Integer> list = new ArrayList<>();

        List<Integer> tempList = new ArrayList<>();
        tempList.add(nums[0]);
        for(int i=1; i<nums.length; i++){
            if(tempList.get(tempList.size()-1) > nums[i]){
                tempList.clear();
                tempList.add(nums[i]);
            } else if(tempList.get(tempList.size()-1) == nums[i]){
                continue;
            } else {
                tempList.add(nums[i]);
            }

            if(tempList.size() > list.size()){
                list.clear();
                list.addAll(tempList);
            }

            System.out.println("tempList " + tempList);
            System.out.println("list " + list);
        }

        return list.size();
    }

}
