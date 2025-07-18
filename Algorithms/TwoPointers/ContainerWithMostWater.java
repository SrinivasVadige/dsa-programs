package Algorithms.TwoPointers;
/**
 You are given an integer array height of length n. There are n vertical lines drawn such that the two endpoints of the ith line are (i, 0) and (i, height[i]).

 Find two lines that together with the x-axis form a container, such that the container contains the most water.

 Return the maximum amount of water a container can store.

 Notice that you may not slant the container.

 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 30 Sept 2024
 * @link 11. Container With Most Water <a href="https://leetcode.com/problems/container-with-most-water/">LeetCode link</a>
 * @topics Array, Two Pointers
 * @companies Google, Amazon, Bloomberg, Microsoft, Meta, Oracle, Goldman Sachs, Deloitte, HashedIn, ServiceNow, TikTok, Nvidia, Myntra, Adobe, Apple, Yahoo, Flipkart, Zoho, Uber, SAP, tcs, Yandex, Accenture
 */
public class ContainerWithMostWater {
    public static void main(String[] args) {
        int[] height = new int[]{1,8,6,2,5,4,8,3,7};
        System.out.println("maxArea using Two Pointers => " + maxAreaUsingTwoPointers(height));
        System.out.println("maxArea using Two Pointers 2 => " + maxAreaUsingTwoPointers2(height));
    }


    /*
     * As we need only the min Height in calculating area, avoid it
     * i.e move the small height pointer
    */
    public static int maxAreaUsingTwoPointers(int[] height) {
        int l = 0;
        int r = height.length-1;
        int maxArea = 0;
        while(l <= r){
            System.out.println("l:"+l + " - " + height[l] + " | r:"+ r + " - " + height[r] );

            maxArea = Math.max( maxArea, (r-l)*Math.min(height[l],height[r]) );
            if(height[l] < height[r])
                l++;
            else
                r--;
        }
        return maxArea;
    }



    /*
     * Same as below maxArea2 method but here
     * finding the bigger height in both pointer with extra dedicated while loops
     */
    public static int maxAreaUsingTwoPointers2(int[] height) {
        int l = 0;
        int r = height.length - 1;
        int maxArea = 0;
        while (l < r) {
            int h = Math.min(height[l], height[r]);
            maxArea = Math.max(maxArea, h * (r - l));
            while (l < r && height[l] <= h) l++;
            while (l < r && height[r] <= h) r--;
        }
        return maxArea;
    }





    public int maxAreaUsingBruteForceTLE(int[] height) {
        int maxArea = 0;
        for (int left = 0; left < height.length; left++) {
            for (int right = left + 1; right < height.length; right++) {
                int width = right - left;
                maxArea = Math.max(
                    maxArea,
                    Math.min(height[left], height[right]) * width
                );
            }
        }
        return maxArea;
    }






    /**
     * NOT WORKING

    length = i2 - i1 and no need to +1 as it is the distance
    width = height = height[max(i2,i1)]
    rectangle area l*w
    move r to last while calculating each areas and then
    move l to each index and then subtract the previous areas
    */
    public static int maxAreaNotWorking(int[] height) {
        int l=0, r=0, area = 0;

        while(l<height.length-1){
            if (r < height.length-1){
                r++;
            }
            else {
                l++;
            }
            int tempArea = (r-l)*Math.min(height[r], height[l]) - (l==0? 0: area - l*height[l] );
            if (tempArea > area)
                area = tempArea;

            System.out.println("l: "+height[l] + ", r: "+ height[r]);
        }

        return area;
    }
}
