package Algorithms.DynamicProgramming;

/**
 * <pre>
 * [2,7,9,3,1] -> Max of [2,0,9,0,1] and [0,7,0,3,0]
 * or can we skip more that 1 house?
 * [2,1,1,2] -- it's 4
 * so, two cases -> skip 1 or skip 2 that's how we can cover all big houses
 * no need to skip 3 or 4 houses as we include them in skip 1, skip 2 scenario
 * top down memo dp ---> Math.max(n-2, n-3) and base case? i < 0 => 0
 * save in dp
 * Note that we can also skip the 1st house
 * </pre>
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 29 Oct 2024
 */
public class HouseRobber {

    public static void main(String[] args) {

        int[] nums = {1, 2, 3, 1};
        System.out.println("robBottomUpNoMemory: " + robBottomUpNoMemory(nums));
        System.out.println("robBottomUpTabulation: " + robBottomUpTabulation(nums));
        System.out.println("robTopDownMemo: " + robTopDownMemo(nums));
    }

    public static int robBottomUpNoMemory(int[] nums) {
        int rob1=0, rob2=0; // dummy houses
        // [rob1, rob2, nums[i], nums[i+1], nums[i+2] ......]
        for(int i=0; i<nums.length; i++) {
            // [rob1, rob2, temp]
            int temp = Math.max(rob1 + nums[i], rob2); // till now how much money we can rob
            rob1 = rob2;
            rob2 = temp;
        }
        return rob2;
    }



    /**
     * <pre>
     *
     *   0   0   1   2   3   1
     * |___|___|___|___|___|___|
     *   p   c   n
     *       p   c   n
     *           p   c   n
     *
     * consider that we have two dummy houses before we start robbing
     * Note that we are just making decisions here based on "is rob 3rd house" condition & store the max robbed money in nth house even if we do npt rob there
     * Like, what if we rob 3rd house (n+p) and what if we rob 2nd house (c). And we keep the max robbed money in 3rd house
     * p is the previous max money we can rob if we choose 3rd house instead of 2nd house
     * If "is rob 3rd house" failed? i.e n+p < c 2nd house then we can change our decision to rob 2nd house and it predecessors
     * i.e temp = max money we can rob until this point - with our decision making tree
     *
     * Finally we store max money in c "nums.length - 1" house but can not say that p is second max
     * In for loop we go to next house then the previous 'c' house will become 'p' 3rd house for the new next house and 'c' as 2nd house
     * that is the reason why we assign c value to p and temp value to c
     *
     * </pre>
     * @see https://youtu.be/73r3KWiEvyk?si=3RWYu64W2GSjlXtq
     */
    public static int robBottomUpNoMemory2(int[] nums) {
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






    /**
     * <pre>
     * same as above
     *
     *   1   2   3   1
     * |___|___|___|___|
     *       p   c   n
     *
     * rob "n and p" or only rob "c" -- but here curr = next; Note that temp is different and nums[i] i.e n is different
     * </pre>
     */
    public static int robBottomUpTabulation(int[] nums) {
        if (nums.length == 1) return nums[0];
        int[] dp = new int[nums.length];
        dp[0] = nums[0];
        dp[1] = Math.max(dp[0], nums[1]);

        for (int i = 2; i < nums.length; i++){
            dp[i] = Math.max(dp[i - 2] + nums[i], dp[i - 1]); // next = Math.max(prev + n, curr);
        }
        return dp[nums.length - 1];
    }


    /**
        [2, 7, 9, 3, 1]
     */
    public static int robBottomUpTabulation2(int[] nums) {
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
     * Unlike Bottom Up approach, here we don't store max money in nth house
     * instead we only go to the eligible houses
     */
    public static int robTopDownMemo(int[] nums) {
        Integer[] dp = new Integer[nums.length]; // we use int[] instead of Integer[] because we can have [0,0,0,0] as input or initialize int[] and loop & set -1

        int max = rec(0, nums, dp);
        if(nums.length > 1) max = Math.max(rec(1, nums, dp), max);

        return max; // or Math.max(dp[0], dp[1]);
    }

    private static int rec(int i, int[] nums, Integer[] dp) {

        if(i >= nums.length) return 0;
        if (dp[i] != null) return dp[i];

        dp[i] = nums[i] + Math.max(rec(i+2, nums, dp), rec(i+3, nums, dp));

        System.out.println(dp[i] + " <= (" + ((i+2)>=nums.length?0:dp[i+2]) + " or " + ((i+3)>=nums.length?0:dp[i+3]) + ") + " +  nums[i]);

        return dp[i];
    }
}
