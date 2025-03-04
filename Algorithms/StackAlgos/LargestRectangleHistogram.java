package Algorithms.StackAlgos;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class LargestRectangleHistogram {
    public static void main(String[] args) {
        int[] heights = {2,1,5,6,2,3};
        System.out.println("largestRectangleArea: " + largestRectangleArea(heights));
        System.out.println("largestRectangleArea2: " + largestRectangleArea2(heights));
    }

    public static int largestRectangleArea(int[] heights) {
        int res = 0;
        int n = heights.length;
        Stack<Integer> stack = new Stack<>();
        for (int i = 0; i <= n; i++) {
            int h = (i == n) ? 0 : heights[i]; // current height
            while (!stack.isEmpty() && h < heights[stack.peek()]) { // currH < prevH
                int height = heights[stack.pop()];
                int width = stack.isEmpty() ? i : i - stack.peek() - 1;
                res = Math.max(res, height * width);
            }
            stack.push(i);
        }
        return res;
    }


    /**
        THOUGHTS:
        ---------
        1) Definitely one height has to extend to left and right -- which might be maxArea
        2) That means, we need to calculate left and right bound indices for each height
        3) Extend bound if nextHeight >= currentHeight
        3) So, (rightIndex - leftIndex + 1 == width) and the current height = height
        4) So, we can calculate the area = width * height
        5) Normally, we can use two pointers and calculate the bounds and area-- O(n^2)
        6) But, we can use stack with indices and calculate the area-- O(n)
        7) Here, maintain only the smaller heights indices.
        8) When traversing from left to right, we'll get the right bound index. So, that (next smaller-height-index - 1) will be right bound index for that height.
        9) When traversing from right to left, we'll get the left bound index. So, that (next smaller-height-index + 1) will be left bound index for that height.

     */
    public static int largestRectangleArea2(int[] heights) {
        int n = heights.length;
        int[] left = new int[n];
        int[] right = new int[n];
        Stack<Integer> stack = new Stack<>();
        for (int i = 0; i < n; i++) {
            while (!stack.isEmpty() && heights[stack.peek()] >= heights[i]) {
                stack.pop();
            }
            if (stack.isEmpty()) left[i] = 0; // reached the end index of heights[]
            else left[i] = stack.peek() + 1;
            stack.push(i);
        }
        stack.clear();
        for (int i = n - 1; i >= 0; i--) {
            while (!stack.isEmpty() && heights[stack.peek()] >= heights[i]) {
                stack.pop();
            }
            if (stack.isEmpty()) right[i] = n - 1; // reached the start index of heights[]
            else right[i] = stack.peek() - 1;
            stack.push(i);
        }
        int res = 0;
        for (int i = 0; i < n; i++) {
            res = Math.max(res, heights[i] * (right[i] - left[i] + 1));
        }
        return res;
    }


















        /**

        NOT WORKING -----

        THOUGHTS:
        ---------
        1) Need the largest height.
        2) Now travel to left or right
        3) Duplicate heights


     */
    public int largestRectangleAreaUsingMaxHeight(int[] heights) {
        // int maxH = Arrays.stream(heights).max().getAsInt();

        int mhi = 0, mh=-1; // maxHeightIndex; maxHeight
        List<Integer> iLst = new ArrayList<>();
        for (int i=0; i<heights.length; i++) {
            if (mh<heights[i]) {
                mh=heights[i];
                mhi=i;
                iLst.clear();
                iLst.add(i);
            }
            else if (mh==heights[i]) {
                iLst.add(i);
            }
        }

        System.out.println(iLst);
        System.out.println("mh: "+mh+" ,mhi: "+mhi);
        int max = 0;

        for (int i: iLst) {
            max = Math.max(max, findMax(heights, mh, i));
        }


        return max;
    }

    private int findMax(int[] heights, int mh, int mhi) {

        int maxArea = mh;
        int lMaxArea = mh;
        int rMaxArea = mh;

        // left
        int lc = 1;
        int lSmallH = mh;
        int lAvgH = 1;
        int i = mhi-1;
        while (i<heights.length && i>-1) {
            if (heights[i] == 0) break;
            lSmallH = Math.min(lSmallH, heights[i]);
            lc++;
            if (lMaxArea < lSmallH*lc) {
                lMaxArea = lSmallH*lc;
                lAvgH = lSmallH;
            }
            i--;
        }

        System.out.println("lAvgH: " + lAvgH +", maxArea: "+maxArea+" ,lMaxArea: "+lMaxArea+", rMaxArea: "+rMaxArea);

        // right
        int rc = 1;
        int rSmallH = mh;
        int rAvgH = 1;
        i = mhi+1;
        while (i<heights.length && i>-1) {
            if (heights[i] == 0) break;
            rSmallH = Math.min(rSmallH, heights[i]);
            rc++;
            if (rMaxArea < rSmallH*rc) {
                rMaxArea = rSmallH*rc;
                rAvgH = rSmallH;
            }
            i++;
        }

        maxArea = Math.max(lMaxArea, rMaxArea);

        int c = lc+rc-1;
        maxArea = Math.max(maxArea, c*(Math.min(lAvgH, rAvgH)));

        System.out.println("rAvgH: " + rAvgH +", maxArea: "+maxArea+" ,lMaxArea: "+lMaxArea+", rMaxArea: "+rMaxArea);

        return maxArea;
    }
}
