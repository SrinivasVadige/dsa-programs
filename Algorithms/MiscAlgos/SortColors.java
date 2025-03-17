package Algorithms.MiscAlgos;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 17 March 2025
 */
public class SortColors {
    public static void main(String[] args) {
        int[] nums = {2,0,2,1,1,0};
        sortColorsUsingBucketSortArr(nums);
        System.out.println("nums => " + Arrays.toString(nums));

        nums = new int[]{2,0,2,1,1,0};
        sortColorsUsingQuickSort(nums);
        System.out.println("nums => " + Arrays.toString(nums));
    }



    public static void sortColorsUsingBucketSortArr(int[] nums) {
        int[] colsCount = new int[3]; // colsCount's i is color and colsCount[i] val is that color's count (or) 3 variables like zero, one & two (or) hashMap
        for (int num: nums) colsCount[num]++; // increase the count of that num

        int i=0;
        int col=0;
        for(int count: colsCount) {
            for(; count>0; count--) nums[i++]=col;
            col++;
        }
    }


    /**
     * QuickSort
     * Here we don't need pivot separately.
     * We can use 1 as pivot, as we only have 3 colors --- 0,1,2
     *
     * Here use 3 pointers --> left, right & index vars. And trav i from 0 to r
     *
     * EDGE CASE:
     * when
     *          0  1  2  1  0  2
     *             l  i     r
     *
     * Now if we swap i & r -->
     *
     *           0  1  0  1  2  2
     *              l  i     r
     *
     * But this is wrong
     * so, don't increment i in this case
     *
     *            0  1  0  1  2  2
     *               l  i  r
     *
     *  check the i again now
     *
     * so, just keep i same for every right swap
     */
    public static void sortColorsUsingQuickSort(int[] nums) {
        int l=0, r=nums.length-1;
        int i=0;
        while (i<=r) {
            if (nums[i] == 0) { // move 0s to left, self swap is perfectly fine but update l++
                swap(nums, l, i);
                l++;
            } else if (nums[i] == 2) { // move 2s to right
                swap(nums, r, i);
                r--;
                i--; // edge case to keep i constant
            }
            i++; // we still need to check for 1s also. so, don't move this to "if (nums[i] == 0)"
        }
    }
    private static void swap(int[] nums, int i, int j) {
        // if (i == j) return; ---> to avoid self swap
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }



    public void sortColorsUsingQuickSort2(int[] nums) {
        int n = nums.length;
        int low=0;
        int mid=0;
        int high=n-1;
        while(mid<=high){
            if(nums[mid]==0){
                swap(nums, mid, low);
                mid++;
                low++;
            }
            else if(nums[mid]==1){
                mid++;
            }else{
                swap(nums, mid, high);
                high--;
                // keep mid constant in right swap for edge case
            }
        }
    }



    public void sortColorsBubbleSort(int[] nums) {
        int n=nums.length;
        for(int i=0;i<n;i++){
            for(int j=0;j<n-1;j++){
                if(nums[j]>nums[j+1]){
                    swap(nums,j,j+1);
                }
            }
        }
    }





    public static void sortColorsUsingBucketSortThreeVars(int[] nums) {
        int count0 = 0, count1 = 0, count2 = 0;
        for(int i=0; i<nums.length; i++){
            if(nums[i] == 0){
                count0++;
            } else if(nums[i] == 1){
                count1++;
            } else {
                count2++;
            }
        }
        for(int i=0; i<count0; i++){
            nums[i] = 0;
        }
        for(int i=count0; i<count0+count1; i++){
            nums[i] = 1;
        }
        for(int i=count0+count1; i<count0+count1+count2; i++){
            nums[i] = 2;
        }
    }



    public static void sortColorsUsingHashMap(int[] nums) {
        Map<Integer, Integer> map = Arrays.stream(nums).boxed().collect(Collectors.groupingBy(i->i, Collectors.summingInt(_->1)));// map of colors and their count

        int i=0;
        for(Map.Entry<Integer, Integer> e: map.entrySet()) {
            for(int j=0; j<e.getValue(); j++) nums[i++]=e.getKey(); // put the color in the nums array
        }
    }



    public static void sortColorsUsingSort(int[] nums) {
        Arrays.sort(nums); // sort the nums array
    }
}
