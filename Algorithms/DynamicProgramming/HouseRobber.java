package Algorithms.DynamicProgramming;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
    <pre>
    [2,7,9,3,1] -> Max of [2,0,9,0,1] and [0,7,0,3,0]
    or can we skip more that 1 house?
    [2,1,1,2] -- it's 4
    so, two cases -> skip 1 or skip 2 that's how we can cover all big houses
    no need to skip 3 or 4 houses as we include them in skip 1, skip 2 scenario
    top down memo dp ---> Math.max(n-2, n-3) and base case? i < 0 => 0
    save in dp
    Note that we can also skip the 1st house
    </pre>




 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 29 Oct 2024
 * @link 198. House Robber <a href="https://leetcode.com/problems/house-robber/">LeetCode link</a>
 * @topics Array, Dynamic Programming, Memoization
 * @companies Amazon(16), Meta(6), Google(5), Microsoft(3), Bloomberg(3), Agoda(2), Infosys(3), Nvidia(3), TCS(2), Databricks(2), Expedia(2), Freecharge(2), Cisco(44), TikTok(14), Goldman Sachs(6), Uber(6), DE Shaw(5), Datadog(5), PhonePe(5), LinkedIn(4), Apple(4), Oracle(4)
 */
public class HouseRobber {

    public static void main(String[] args) {

        int[] nums = {1, 2, 3, 1};
        System.out.println("robTopDownMemo 1: " + robTopDownMemo1(nums));
        System.out.println("robTopDownMemo 2: " + robTopDownMemo2(nums));
        System.out.println("robTopDownMemo 3: " + robTopDownMemo3(nums));
        System.out.println("robBottomUpTabulation 1: " + robBottomUpTabulation1(nums));
        System.out.println("robBottomUpNoMemory 1: " + robBottomUpNoMemory1(nums));
    }



    /**

        INTUITION: Max of (evenI sum, oddI sum)

        But it's failing at
        [2,1,1,2] => ans = 4 not 3
         0 1 2 3 - [0,3] - we skipped 2 steps not just 1

    */
    public int robNotWorking(int[] nums) {
        int startI0 = 0;
        int startI1 = 0;

        for (int i=0; i<nums.length; i++) {
            if (i % 2 == 0) {
                startI0 += nums[i];
            } else {
                startI1 += nums[i];
            }
        }

        return Math.max(startI0, startI1);
    }



    /**

        i = curr
        i+1 = robNext
        i+2 = robNextPlusOne

        so, we'll add the curr nums[i] to i+2, as we cannot add to i+1 - adjacent alarm triggers

     * @TimeComplexity O(n)
     * @SpaceComplexity O(n)
    */
    public static int robTopDownMemo1(int[] nums) {
        int[] memo = new int[nums.length];
        Arrays.fill(memo, -1);
        return robFrom(0, nums, memo);
    }

    private static int robFrom(int i, int[] nums, int[] memo) {
        if (i >= nums.length) return 0;
        else if (memo[i] > -1) return memo[i];

        int ans = Math.max(
            robFrom(i + 1, nums, memo),             // skip currNum - robNext
            robFrom(i + 2, nums, memo) + nums[i]    // add currNum - robNextPlusOne
        );

        return memo[i] = ans;
    }





    /**

        <pre>
        [0, 1, 2, 3, 4]


                                                     [ ]                                                ---> i=-1
                                     _________________|______________________
                                   [0]                                     [ ]                          ---> i=0
                                    |                              _________|_________
                                   [0]                            [1]                [ ]                ---> i=1
                            ________|________                      |                  |
                         [0,2]              [0]                   [1]                [2]                ---> i=2
                           |              ___|___               ___|___               |
                         [0,2]        [0,3]     [0]          [1,3]    [1]            [2]                ---> i=3
                           |            |        |             |       |              |
                    [0,2,4][0,2]      [0,3]  [0,4][0]       [1,3]  [1,4][1]         [2,4]               ---> i=4

        </pre>

     * @TimeComplexity O(n) - not O(2^n) as we used memo
     * @SpaceComplexity O(n) - recursion stack
     */
    public static int robTopDownMemo2(int[] nums) {
        // or MEMO KEY ---> List<Integer> memoKey = Arrays.asList(prevI, i);
        return dp(nums, -1, 0, new HashMap<>());
    }
    private static int dp(int[] nums, int prevI, int i, Map<Integer, Integer> memo) {
        if (i >= nums.length) return 0;
        else if (memo.containsKey(prevI)) return memo.get(prevI);

        int max = 0;
        if (prevI==-1 || i-prevI>1) {
            max = nums[i] + dp(nums, i, i+1, memo);         // add currNum
        }
        max = Math.max(max, dp(nums, prevI, i+1, memo));    // skip currNum

        memo.put(prevI, max);
        return max;
    }





    /**
     * Unlike Bottom Up approach, here we don't store max money in nth house
     * instead we only go to the eligible houses
     * @TimeComplexity O(n)
     * @SpaceComplexity O(n)
     */
    public static int robTopDownMemo3(int[] nums) {
        Integer[] dp = new Integer[nums.length]; // or int[n] and set [0,0,0,0] as [-1,-1,-1,-1] by looping or Arrays.fill()
        return Math.max(dfs(0, nums, dp), dfs(1, nums, dp));
    }

    private static int dfs(int i, int[] nums, Integer[] dp) {
        if(i >= nums.length) return 0;
        else if (dp[i] != null) return dp[i];

        dp[i] = nums[i] + Math.max(dfs(i+2, nums, dp), dfs(i+3, nums, dp));

        // System.out.println(dp[i] + " <= (" + ((i+2)>=nums.length?0:dp[i+2]) + " or " + ((i+3)>=nums.length?0:dp[i+3]) + ") + " +  nums[i]);

        return dp[i];

    }









    /**
     * @TimeComplexity O(n)
     * @SpaceComplexity O(n)
     */
    public static int robBottomUpTabulation1(int[] nums) {
        final int N = nums.length;
        if (N == 0) return 0;

        int[] dp = new int[N+1]; //maxRobbedAmount

        dp[N] = 0;
        dp[N-1] = nums[N-1];

        for (int i = N-2; i >= 0; i--) {
            dp[i] = Math.max(
                dp[i + 1],                  // skip currNum - robNext
                dp[i + 2] + nums[i]         // add currNum - robNextPlusOne
            );
        }

        return dp[0];
    }








    /**
      <pre>
      same as above

        1   2   3   1
      |___|___|___|___|
            p   c   n

      rob "n and p" or only rob "c" -- but here curr = next; Note that temp is different and nums[i] i.e n is different
      </pre>

        same as above curr, robNext, robNextPlusOne

        i = curr
        i-1 = robPrev
        i-2 = robPrevMinusOne

        so, we must not add currNum to the robPrev

     * @TimeComplexity O(n)
     * @SpaceComplexity O(n)
     */
    public static int robBottomUpTabulation2(int[] nums) {
        if (nums.length == 1) return nums[0];
        int[] dp = new int[nums.length];
        dp[0] = nums[0];
        dp[1] = Math.max(dp[0], nums[1]);

        for (int i = 2; i < nums.length; i++){
            dp[i] = Math.max(
                    dp[i-2] + nums[i],          // robPrevMinusOne + curr
                    dp[i-1]);                   // robPrev
        }
        return dp[nums.length - 1];
    }




    /**
     * @TimeComplexity O(n)
     * @SpaceComplexity O(n)
     */
    public static int robBottomUpTabulation3(int[] nums) {
        int n=nums.length;
        if(n==1) return nums[0];
        else if(n==2) return Math.max(nums[0], nums[1]);
        // [9, 3, 1][0] -- [0] is robbed[n] which is dummy house in robbed array -> robbed[robbed.length-1]
        int[] dp = new int[n+1];
        dp[n-1]=nums[n-1];
        dp[n-2]=nums[n-2];
        for(int i=n-3; i>=0; i--) {
            dp[i] = nums[i] + Math.max(dp[i+2], dp[i+3]);
        }
        return Math.max(dp[0], dp[1]);
    }






    /**

        Initially,

        [0, 1, 2, 3], robNext, robNextPlusOne
        ---> int robNext = 0, robNextPlusOne = 0; & for (int i = N-1; i >= 0; i--) {

        or

        [0, 1, 2, robNext(3)], robNextPlusOne
        ---> int robNext = nums[N-1], robNextPlusOne = 0; & for (int i = N-2; i >= 0; i--) {


        [currentMax, robNext, robNextPlusOne]

     * @TimeComplexity O(n)
     * @SpaceComplexity O(n)
     */
    public static int robBottomUpNoMemory1(int[] nums) {
        int N = nums.length;
        if (N == 0) return 0;

        int robNext = nums[N-1], robNextPlusOne = 0;

        for (int i = N-2; i >= 0; i--) {
            int current = Math.max(
                    robNext,                        // i+1
                    robNextPlusOne + nums[i]);      // i+2

            robNextPlusOne = robNext;
            robNext = current;
        }

        return robNext;
    }



    /**
        Initially

        rob1, rob2, [nums[i], nums[i+1], nums[i+2] ......]

        i.e same as above {@link #robBottomUpNoMemory1} --- instead of right to left (<-), here we go from left to right (->)

     * @link <a href="https://youtu.be/73r3KWiEvyk?si=3RWYu64W2GSjlXtq">YouTube Link</a>
     * @TimeComplexity O(n)
     * @SpaceComplexity O(1)
     */
    public static int robBottomUpNoMemory2(int[] nums) {
        int rob1=0, rob2=0; // dummy houses
        // [rob1, rob2, nums[i], nums[i+1], nums[i+2] ......]
        for (int num : nums) {
            // [rob1, rob2, temp]
            int temp = Math.max(rob1 + num, rob2); // till now how much money we can rob
            rob1 = rob2;
            rob2 = temp;
        }
        return rob2;
    }





    /**
     <pre>

     0   0   1   2   3   1
     |___|___|___|___|___|___|
     p   c   n
     p   c   n
     p   c   n

     consider that we have two dummy houses before we start robbing
     Note that we are just making decisions here based on "is rob 3rd house" condition & store the max robbed money in nth house even if we do npt rob there
     Like, what if we rob 3rd house (n+p) and what if we rob 2nd house (c). And we keep the max robbed money in 3rd house
     p is the previous max money we can rob if we choose 3rd house instead of 2nd house
     If "is rob 3rd house" failed? i.e n+p < c 2nd house then we can change our decision to rob 2nd house and it predecessors
     i.e temp = max money we can rob until this point - with our decision making tree

     Finally we store max money in c "nums.length - 1" house but can not say that p is second max
     In for loop we go to next house then the previous 'c' house will become 'p' 3rd house for the new next house and 'c' as 2nd house
     that is the reason why we assign c value to p and temp value to c

     </pre>

     * @TimeComplexity O(n)
     * @SpaceComplexity O(n)
     */
    public static int robBottomUpNoMemory3(int[] nums) {
        int prev = 0;
        int curr = 0; // dummy houses money
        for (int n : nums) {
            // ➕©️🔵 -> ©️🔵
            int next = Math.max(prev + n, curr); // "is rob 3rd house?" + max we can rob until this point
            prev = curr;
            curr = next;
        }
        return curr;
    }




}
