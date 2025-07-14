package Algorithms.TwoPointers;

import java.util.*;

/**
 * @author Srinvas Vadige, srinivas.vadige@gmail.com
 * @since 02 Oct 2024
 * @link 42. Trapping Rain Water <a href="https://leetcode.com/problems/trapping-rain-water/">LeetCode link</a>
 * @topics Array, Two Pointers, Dynamic Programming, Stack, Monotonic Stack
 * @companies Amazon, Google, Bloomberg, Zopsmart, Meta, Microsoft, TikTok, Walmart, Apple, Snowflake, Roblox, tcs, Zeta, Zoho, Tekion, ServiceNow, Qualcomm, Nutanix, Capgemini, Adobe, Yandex, Flipkart, Oracle, Uber, Samsung, Salesforce, Intel, Yahoo, Airbnb
 *
 * <pre>
 * reed trapMyThoughts() documentation at below



    3                                              ********
    |                                              *      *  ❌    ❌    ❌     ❌
    |                                              *      *
    2                  ********                    *      ********      ********
    |                  *      *                    *      *      *      *      *
    |                  *      *   💧    💧    💧  *      *      * 💧   *      *  ❌
    |                  *      *                    *      *      *      *      *
    1    ********      *       ********     ********      *      ********      ********
    | ❌ *      *  💧  *      *      * 💧  *      *      *      *      *      *      *
    |____*______*______*_______*______*_____*______*______*______*______*______*______*
       0     1      2      3      4      5      6      7      8      9      10     11   ----> indices

</pre>
*/
public class TrappingRainWater {

    public static void main(String[] args) {
        int[] height = new int[]{0,1,0,2,1,0,1,3,2,1,2,1};
        System.out.println("trap Using BruteForce => " + trapUsingBruteForce(height));
        System.out.println("trap Using Dp => " + trapUsingDp(height));
        System.out.println("trap Using Dp Improved => " + trapUsingDpImproved(height));
        System.out.println("trap Using Two Pointers => " + trapUsingTwoPointers(height));
        System.out.println("trap Using Monotonic Stack => " + trapUsingMonotonicStack(height));
    }



    /**
     * TLE when n=10732 -- but try this first to understand the intuition 🔥
     * @TimeComplexity O(n^2)
     * @SpaceComplexity O(1)
     * water we can trap at i is ---> Math.min(leftMax[i], rightMax[i]) - height[i];
     * So calculate leftMax and rightMax of each height
     * we use leftMax and rightMax variables which basically calculates the prefix max and suffix max at specific index
     * we know prefixSum at i-> sum(0 to i);
     * and suffixSum at i -> sum(n-1 to i);
     * here
     * prefixMax or leftMax at i = max(0 to i);
     * suffixMax or rightMax at i = max(n-1 to i);
     */
    public static int trapUsingBruteForce(int[] height) {
        int water = 0;
        int n = height.length;
        System.out.println(n);
        for(int i=0; i<n; i++) {
            int leftMax = Arrays.stream(height).limit(i+1).max().orElse(height[i]); // or prefixMax
            int rightMax = Arrays.stream(height).skip(i).max().orElse(height[i]);// or suffixMax
            water += Math.min(leftMax, rightMax) - height[i];
        }
        return water;
    }





    /**
     * @TimeComplexity O(n)
     * @SpaceComplexity O(n)
     * Same as {@link #trapUsingBruteForce}
     * water we can trap at i is ---> Math.min(leftMax[i], rightMax[i]) - height[i];
     * we use leftMax[] and rightMax[] dp arrays which basically stores the prefix max and suffix max at each index
     */
    public static int trapUsingDp(int[] height) {
        int n = height.length;
        int[] leftMax = new int[n], rightMax = new int[n]; // dp arrays
        leftMax[0] = height[0];
        rightMax[n-1] = height[n-1];

        for (int i = 1; i < n; i++) {
            leftMax[i] = Math.max(leftMax[i-1], height[i]); // prefix Max
        }

        for (int i = n-2; i >= 0; i--) {
            rightMax[i] = Math.max(rightMax[i+1], height[i]); // suffix Max
        }

        int water = 0;
        for (int i = 0; i < n; i++) {
            water += Math.min(leftMax[i], rightMax[i]) - height[i];
        }
        return water;
    }







    public static int trapUsingDpImproved(int[] height) {
        int n = height.length;
        int leftMax = 0;
        int[] rightMax = new int[n]; // dp array
        rightMax[n-1] = height[n-1];

        for (int i = n-2; i >= 0; i--) {
            rightMax[i] = Math.max(rightMax[i+1], height[i]);
        }

        int water = 0;
        for (int i = 0; i < n; i++) {
            leftMax = Math.max(leftMax, height[i]);
            water += Math.min(leftMax, rightMax[i]) - height[i];
        }
        return water;
    }






    /**
     * @TimeComplexity O(n)
     * @SpaceComplexity O(1)
     * same as above {@link #trapUsingDpImproved}
     * but calculate the water only on min height i.e height[l] or height[r] -- based on which is lower
     */
    public static int trapUsingTwoPointers(int[] height) {
        int n = height.length;
        int leftMax = 0;
        int rightMax = 0;

        int l=0, r=n-1;
        int water = 0;

        while(l<r) {
            leftMax = Math.max(leftMax, height[l]);
            rightMax = Math.max(rightMax, height[r]);

            if(height[l] < height[r]) {
                water += Math.min(leftMax, rightMax) - height[l];
                l++;
            } else {
                water += Math.min(leftMax, rightMax) - height[r];
                r--;
            }

        }

        return water;
    }




    public static int trapUsingTwoPointers2(int[] height) {
        int left = 0;
        int right = height.length - 1;
        int leftMax = height[left];
        int rightMax = height[right];
        int water = 0;

        while (left < right) {
            if (leftMax < rightMax) { // to avoid right most overflow ❌ in the diagram
                left++;
                leftMax = Math.max(leftMax, height[left]);
                water += leftMax - height[left]; // same like "min(leftMax, rightMax) - currentHeight"
            } else {
                right--;
                rightMax = Math.max(rightMax, height[right]);
                water += rightMax - height[right];
            }
        }

        return water;
    }







    /**
     * @TimeComplexity O(n)
     * @SpaceComplexity O(n)
     * Monotonic Stack - means stack that is either strictly increasing or strictly decreasing
     * here we store indices in increasing order --> so it's a monotonic increasing stack

     * To put it simply, here we calculate the area of same heights
     * or in other words,
     * we calculate the water above on curr index --> but not fully --> only partially
     * ---> cause, we calculate area rather the actual water on top like the above other methods
     * so, after popping from stack, curr will be the popped ---> calculate the area only for with the same "heights"

     Given - [4, 2, 0, 3, 2, 5] height array


               4   2   0    3   2   5
                                 _____
             _____ 💧 💧      💧|   |
             |   | 2   2 _____ 3 |   |
             |   |___  💧|   |___|   |
             |   |   | 1 |   |   |   |
             |___|___|___|___|___|___|
               0   1   2   3   4   5

      as shown in the graph, we calculate area1 and then area2 and then area3

     here, the stack gets filled till 2nd index i.e stack=[0,1,2] as height[stack.peek()] < height[i]
     once we reached 3rd index then we'll go inside below while loop

    Example:

              0 1 2 3 4 5 6 7 8 9 10 11
    height = [0,1,0,2,1,0,1,3,2,1,2,1]

    3                                              ********
    |                                              *      *  ❌    ❌    ❌     ❌
    |                                              *      *
    2                  ********                    *      ********      ********
    |                  *      *   💧    💧    💧  *      *      *   💧 *      *
    |                  *      *   3      3     3   *      *      *   4  *      *  ❌
    |                  *      *                    *      *      *      *      *
    1    ********  💧  *       ******** 💧 ********      *      ********      ********
    |    *      *  1   *       *      *  2  *      *      *      *      *      *      *
    |____*______*______*_______*______*_____*______*______*______*______*______*______*
       0     1      2      3      4      5      6      7      8      9      10     11   ----> indices

    as shown in the graph, we calculate area1 and then area2 and then area3 and then area4


    new stack.peek() == left index,
    pop == curr == curr index
    r from r-loop == right index
    
    and height[stack.peek()] <= height[r] means ---> currHeight <= height[r]

     initially, stack = [] --> stack is going to store only indices

     r=0: -> h=0
        stack = [] --- before
        stack = [0:0] --- after

     r=1: -> h=1
        stack = [0:0] ----------------------> currHeight <= height[r] i.e 0<=1 --> true
        stack = []    ---> curr = 0:0
                           stack.empty() = true --> break loop
        stack = [1:1]

     r=2: -> h=0
        stack = [1:1] ----------------------> currHeight <= height[r] i.e 1<=0 --> false
        stack = [1:1, 2:0]

     r=3: -> h=2
        stack = [1:1, 2:0] ----------------------> currHeight <= height[r] i.e 0<=2 --> true
        stack = [1:1] ---> curr = 2:0
                           width = r-l-1 = 3-1-1 = 1
                           minHeight = Math.min(height[l], height[r]) = Math.min(1, 2) = 1
                           area = width * (minHeight - height[curr]) = 1 * (1 - 0) = 1 ----> area1 💧

        stack = [1:1] ----------------------> currHeight <= height[r] i.e 1<=2 --> true
        stack = [] ---> j = 1:1
                        stack.empty() = true --> break loop
        stack = [3:2]

     r=4: -> h=1
        stack = [3:2]  ----------------------> currHeight <= height[r] i.e 2<=1 --> false
        stack = [3:2, 4:1]

     r=5: -> h=0
        stack = [3:2, 4:1]  ----------------------> currHeight <= height[r] i.e 1<=0 --> false
        stack = [3:2, 4:1, 5:0]

     r=6: -> h=1
        stack = [3:2, 4:1, 5:0]  ----------------------> currHeight <= height[r] i.e 0<=1 --> true
        stack = [3:2, 4:1] ---> curr = 5:0
                           width = r-l-1 = 6-4-1 = 1
                           minHeight = Math.min(height[l], height[r]) = Math.min(1, 1) = 1
                           area = width * (minHeight - height[curr]) = 1 * (1 - 0) = 1  ---------> area2 💧
        stack = [3:2, 4:1]  ----------------------> currHeight <= height[r] i.e 1<=1 --> true
        stack = [3:2] ---> curr = 4:1
                           width = r-l-1 = 6-3-1 = 2
                           minHeight = Math.min(height[l], height[r]) = Math.min(2, 1) = 1
                           area = width * (minHeight - height[curr]) = 2 * (1 - 1) = 0
        stack = [3:2] ----------------------> currHeight <= height[r] i.e 2<=1 --> false
        stack = [3:2, 6:1]

     r=7: -> h=3
        stack = [3:2, 6:1]  ----------------------> currHeight <= height[r] i.e 1<=3 --> true
        stack = [3:2] ---> curr = 6:1
                           width = r-l-1 = 7-3-1 = 3
                           minHeight = Math.min(height[l], height[r]) = Math.min(2, 3) = 2
                           area += width * (minHeight - height[curr]) = 3 * (2 - 1) = 3 -------> area3 💧
        stack = [3:2]  ----------------------> currHeight <= height[r] i.e 2<=3 --> true
        stack = [] ---> curr = 3:2
                        stack.empty() = true --> break loop
        stack = [7:3]

     r=8: -> h=2
        stack = [7:3]  ----------------------> currHeight <= height[r] i.e 3<=2 --> false
        stack = [7:3, 8:2]

     r=9: -> h=1
        stack = [7:3, 8:2]  ----------------------> currHeight <= height[r] i.e 2<=1 --> false
        stack = [7:3, 8:2, 9:1]

     r=10: -> h=2
        stack = [7:3, 8:2, 9:1]  ----------------------> currHeight <= height[r] i.e 1<=2 --> true
        stack = [7:3, 8:2] ---> curr = 9:1
                           width = r-l-1 = 10-8-1 = 1
                           minHeight = Math.min(height[l], height[r]) = Math.min(2, 2) = 2
                           area = width * (minHeight - height[curr]) = 1 * (2 - 1) = 1 -------> area4 💧

     water = area1 + area2 + area3 + area4 = 6

     */
    public static int trapUsingMonotonicStack(int[] height) {
        int n = height.length, water = 0;
        Stack<Integer> stack = new Stack<>(); // or Deque<Integer> stack = new LinkedList<>(); ---> to store indices not heights

        for (int r = 0; r < n; r++) { // or stack.push(0);  for (int r = 1; r < n; r++) {
            while (!stack.isEmpty() && height[stack.peek()] <= height[r]) { // or height[stack.peek()] < height[r]
                int curr = stack.pop(); // j == current
                if (stack.isEmpty()) { // to avoid EmptyStackException
                    break;
                }
                /*
                  curr popped == curr index
                  new stack.peek() == left index,
                  r from r-loop == right index
                 */
                int l = stack.peek();
                int width = r-l-1; // distance b/w l and r and "-1" to avoid r from distance
                int minHeight = Math.min(height[l], height[r]);
                int area = width * (minHeight - height[curr]);
                water += area;
            }

            stack.push(r);
        }

        return water;
    }






    /**
     * Using ArrayDeque instead of Stack --> but we only check the last element ele just like stack
     * Even though Deque extends Queue, it behaves like two stacks connected back-to-back — one growing from the front, the other from the back.
     */
    public static int trapUsingMonotonicStack2(int[] height) {
        Deque<Integer> dq = new ArrayDeque<>(); // or new LinkedList<>();
        int result = 0;

        for(int i = 0; i < height.length; i++) {
            while(!dq.isEmpty() && height[dq.peekLast()] < height[i]) {
                //the current index is next greater.
                int top = dq.pollLast();

                if(!dq.isEmpty()) {
                    int prevGreater = dq.peekLast(); // left one
                    int currHeight = Math.min(height[i], height[prevGreater]) - height[top];
                    int width = i - prevGreater - 1;
                    result += currHeight * width;
                }
            }
            dq.offerLast(i);
        }
        return result;
    }









    /**

        int l=0, r=n-1;

        int currTrap = Math.max(Math.min(lh, rh)-lh, 0)
    */
    public static int trapMyApproachNewNotWorking(int[] height) {
        int n=height.length;
        int l=0;
        int totalTrap = 0;
        int leftMax = height[0];
        int rightMax = height[0];

        for(int r=0; r<n; r++) {
            // rightMax=height[r];

            while(r<n) {
                rightMax = Math.max(rightMax, height[r]);
                // System.out.printf("l:%s, r:%s, lh:%s, rh:%s, leftMax:%s, rightMax:%s ------ \n", l, r, height[l], height[r], leftMax, rightMax);
                if(height[r]>=leftMax) {
                    break;
                }
                if(r==n-1) {
                    break;
                }
                r++;
            }
            while(l<=r) {
                leftMax = Math.max(leftMax, height[l]);
                if (l==r){
                    break;
                }
                int currTrap = Math.max(height[l], Math.min(leftMax, rightMax)) - height[l];
                totalTrap += currTrap;
                // System.out.printf("l:%s, r:%s, lh:%s, rh:%s, leftMax:%s, rightMax:%s, totalTrap:%s\n", l, r, height[l], height[r], leftMax, rightMax, totalTrap);
                l++;
            }
        }
        return totalTrap;
    }




    public int trapMyApproachNotWorking(int[] height) {
        int n = height.length;
        if (n <= 1) {
            return 0;
        }
        int l = 0;
        int[] min = {Integer.MAX_VALUE, 1};
        int blocks = 0;
        int area = 0;

        for(int r=1; r<n; r++) {
            System.out.printf("l:%s, r:%s, blocks:%s, area:%s, min:%s\n", l, r, blocks, area, Arrays.toString(min));

            // big blocks
            if(height[l] <= height[r]) {
                // calculate area
                int minHeight = Math.min(height[l], height[r]);
                int width = r-l-1;
                area += (minHeight*width - blocks);

                // shift l to r position, reset blocks, min=r;
                l = r;
                blocks = 0;
                min[0] = height[r];
                min[1] = 1;

                continue;
            }

            // small blocks
            if(min[0] < height[r]) {
                int currArea = (height[r]-min[0]) * min[1];
                area += currArea;
                blocks += currArea;
                min[0] = height[r];
                min[1]++;

            }

            blocks += height[r];
            min[0] = Math.min(min[0], height[r]);
        }
        return area;
    }







    /**
     * <pre>
        water stagnates only if lh>rh upto lh<=rh
        need bigger heights
        calculate hight diffs in each r loop with current l
        scenario is changing after r == height.length-1

        If we move both pointers to right side
        And now let's the r pointer just passed the biggest of all heights
        then we are keeping that biggest height as l
        Once we reached lh <= rh it makes us count the dummies even if we don't have lh <= rh height

        So, it's better to start with two pointers l == 0 and r == n-1
        And loop untill l index < r index by moving both pointers at once and
        have lMax height and rMax height.
        or l=0 and r=0 with lMax and rMax
    </pre>
    */
    public static int trapOldApproachNotWorking(int[] height) {

        int l = 0, r =1;

        int units = 0;

        int tempUnits = 0; // as we are not sure if we have bigger r in future
        //int tempHeight = 0; // my thoughts on this
        while(l < height.length){

            System.out.printf("l:%d, r:%d%n", l, r);

            if(height[l]>height[r]){
                tempUnits += height[l]-height[r];
                System.out.printf("inside tempUnits: %d%n", tempUnits);
            }

            if(height[l] <= height[r]){
                units += tempUnits;
                tempUnits=0;
                l = r;
                System.out.printf("--------- inside Units: %s\n", units);
            }
            if (r < height.length-1)
                r++;
            else l++;
        }

        return units;
    }
}
