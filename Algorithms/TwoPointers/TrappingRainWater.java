package Algorithms.TwoPointers;

import java.util.*;

/**
 * @author Srinvas Vadige, srinivas.vadige@gmail.com
 * @since 02 Oct 2024
 * @link 42. Trapping Rain Water <a href="https://leetcode.com/problems/trapping-rain-water/">LeetCode link</a>
 * @topics Array, Two Pointers, Dynamic Programming, Stack, Monotonic Stack
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
        System.out.println(trap(height));
    }

    /**
     * @TimeComplexity O(n)
     * @SpaceComplexity O(n)

      To calculate the water on the top of each height / block is == min(leftMax, rightMax) - currentHeight
      ---> this the amount of water we can trap on top of it

      So calculate leftMax and rightMax of each height
      and find the amount of water each height can trap using min(leftMax, rightMax)-currentHeight formula
     */
    public static int trap(int[] height) {
        int n = height.length;
        int[] leftMax = new int[n], rightMax = new int[n]; // dp arrays
        leftMax[0] = height[0];
        rightMax[n-1] = height[n-1];

        for (int i = 1; i < n; i++) {
            leftMax[i] = Math.max(leftMax[i-1], height[i]);
        }

        for (int i = n-2; i >= 0; i--) {
            rightMax[i] = Math.max(rightMax[i+1], height[i]);
        }

        int water = 0;
        for (int i = 0; i < n; i++) {
            water += Math.min(leftMax[i], rightMax[i]) - height[i];
        }
        return water;
    }






    /**
     * @TimeComplexity O(n)
     * @SpaceComplexity O(1)

     This is same as above solution {@link #trap(int[])}
     but use constant space with leftMax and rightMax int variables instead of int[] arrays
     To calculate the water on the top of each height / block is == min(leftMax, rightMax) - currentHeight
     ---> this the amount of water we can trap on top of it

     Given - [4, 2, 0, 3, 2, 5] height array

             4  2  0  3  2  5
                            |
             |              |
             |        |     |
             |  |     |  |  |
             |  |     |  |  |

     Here, instead of calculating "min(leftMax, rightMax) - currentHeight" for each height,
     use

     */
    public static int trapUsingTwoPointers(int[] height) {
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

     Given - [4, 2, 0, 3, 2, 5] height array

             4  2  0  3  2  5
                            |
             |              |
             |        |     |
             |  |     |  |  |
             |  |     |  |  |
             0  1  2  3  4  5

     here, the stack gets filled till 2nd index i.e stack=[0,1,2] as height[stack.peek()] < height[i]
     once we reached 3rd index then we'll go inside below while loop
     */
    public static int trapUsingStack(int[] height) {
        int n = height.length, water = 0;
        Stack<Integer> stack = new Stack<>(); // or Deque<Integer> stack = new LinkedList<>(); ---> to store indices not heights

        for (int i = 0; i < n; i++) { // or stack.push(0);  for (int i = 1; i < n; i++) {
            while (!stack.isEmpty() && height[stack.peek()] < height[i]) {
                int j = stack.pop();
                if (stack.isEmpty()) break;
                int width = i - stack.peek() - 1; // distance -- skip the curr j index for width
                int minHeight = Math.min(height[stack.peek()], height[i]);
                water += width * (minHeight - height[j]);
            }

            stack.push(i);
        }

        return water;
    }






    public static int trapUsingStack2(int[] height) {
        Deque<Integer> stack = new ArrayDeque<>();
        int result = 0;

        for(int i = 0; i < height.length; i++) {
            while(!stack.isEmpty() && height[stack.peekLast()] < height[i]) {
                //the current index is next greater.
                int top = stack.removeLast();

                if(!stack.isEmpty()) {
                    int prevGreater = stack.peekLast();
                    int currHeight = Math.min(height[i], height[prevGreater]) - height[top];
                    int width = i - prevGreater - 1;
                    result += currHeight * width;
                }
            }
            stack.add(i);
        }
        return result;
    }




    public static int trapUsingTwoPointers2(int[] height) {
        int left = 0;
        int right = height.length - 1;
        int leftMax = 0;
        int rightMax = 0;
        int total = 0;
        while (left < right) {
            if (height[left] < height[right]) {
                leftMax = Math.max(leftMax, height[left]);
                total += Math.min(leftMax, height[left]) - height[left];
                left++;
            } else {
                rightMax = Math.max(rightMax, height[right]);
                total += Math.min(rightMax, height[right]) - height[right];
                right--;
            }
        }
        return total;
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
