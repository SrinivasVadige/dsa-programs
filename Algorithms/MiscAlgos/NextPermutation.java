package Algorithms.MiscAlgos;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.TreeMap;
import java.util.stream.Collectors;

/**
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 19 March 2025
 */
public class NextPermutation {
    public static void main(String[] args) {
        int[] nums = {2, 1, 3};
        nextPermutation(nums);
        System.out.println("nums => " + Arrays.toString(nums));
    }
    public static void nextPermutation(int[] nums) {
        int n = nums.length;
        int i = n - 2;
        while (i >= 0 && nums[i] >= nums[i + 1]) i--;
        if (i >= 0) {
            int j = n - 1;
            while (j >= 0 && nums[i] >= nums[j]) j--;
            swap(nums, i, j);
        }
        reverse(nums, i + 1, n - 1);
    }
    private static void reverse(int[] nums, int start, int end) {
        while (start < end) {
            swap(nums, start, end);
            start++;
            end--;
        }
    }
    private static void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;

        // or
        // nums[i]^=nums[j];
        // nums[j]^=nums[i];
        // nums[i]^=nums[j];
    }








    public void nextPermutation2(int[] nums) {
        int len = nums.length;
        int index = -1;

        //Finding the breaking point from back to front
        for(int i = len-2;i>=0;i--) {
            if(nums[i] < nums[i+1]) {
                index = i;
                break;
            }
        }

        if(index == -1){
            Arrays.sort(nums);
        } else {
            //swapping the breaking point with an element least greatest to it
            for(int i = len-1;i>index;i--) {
                if(nums[i] > nums[index]){
                    int temp = nums[index];
                    nums[index] = nums[i];
                    nums[i] = temp;
                    break;
                }
            }
            Arrays.sort(nums, index+1, len);
        }
    }








    public void nextPermutation3(int[] nums) {
        TreeMap<Integer, Integer> numToIdx = new TreeMap<>();
        numToIdx.put(nums[nums.length - 1], nums.length - 1);
        for (int i = nums.length - 2; i >= 0; i--) {
            if (nums[i] < nums[i + 1]) {
                swap(nums, i, numToIdx.get(numToIdx.higherKey(nums[i])));
                Arrays.sort(nums, i + 1, nums.length);
                return;
            }
            numToIdx.put(nums[i], i);
        }

        Arrays.sort(nums);
    }







    public void nextPermutation4(int[] nums) {
        int n=nums.length;
        int idx=-1;
        for(int i=n-2;i>=0;i--){
            if(nums[i]<nums[i+1]){
                idx=i;
                break;
            }
        }

        if(idx==-1){
            reverse(nums,0,n-1);
        }
        else{
            for(int i=n-1;i>=idx;i--){
                if(nums[i]>nums[idx]){
                    int temp=nums[i];
                    nums[i]=nums[idx];
                    nums[idx]=temp;
                    break;
                }
            }
            reverse(nums,idx + 1, n-1);
        }
    }





    public void nextPermutation5(int[] nums) {
        int n = nums.length;
        int start = -1;

        for (int i=n-1;i>0;i--) {
            if (nums[i] > nums[i-1]) {
                start = i-1;
                break;
            }
        }

        if (start >= 0) {
            for (int i=n-1;i>start;i--) {
                if (nums[i] > nums[start]) {
                    swap(nums, i, start);
                    break;
                }
            }
        }

        int end = n-1;
        start = start + 1;
        while (start < end) {
            swap(nums, start, end);
            start++;
            end--;
        }
    }








    /**
     * WORKING BUT TLE
     * @TimeComplexity O(n!*n)
     * @SpaceComplexity O(n!*n)

    THOUGHTS:
    ---------
    1) If nums is DESC then return nums in ASC
    2) [1,2,3], [1,3,2], [2, 1, 3], [2, 3, 1], [3,1,2], [3,2,1] these numbers are in sorted order
    3) So, calculate all permutations and at last use for loop to return next permutation
    4)

                            []
                    _________|_________
                    |        |        |
                    [1]      [2]      [3]
                ____|____
                |         |
            [1,2]      [1,3]
                |         |
            [1,2,3]    [1,3,2]

    */
    List<List<Integer>> lst = new ArrayList<>();
    public void nextPermutationMyApproach(int[] nums) {
        int[] clone = nums.clone();
        Arrays.sort(clone);

        for (int i=0; i<nums.length; i++) {
            boolean[] marked = new boolean[nums.length];
            List<Integer> subL = new ArrayList<>();
            backtrack(clone, i, subL, marked);
        }
        lst = new ArrayList<>(new HashSet<>(lst)); // removing duplicates
        Collections.sort(lst, (a,b)->{
            int aNum = a.stream().reduce((i,j)->i*10+j).get();
            int bNum = b.stream().reduce((i,j)->i*10+j).get();
            return aNum-bNum;
        });
        // System.out.println(lst);
        List<Integer> og = Arrays.stream(nums).boxed().collect(Collectors.toList());

        for (int i=0; i<lst.size(); i++) {
            if (og.equals(lst.get(i))) {
                og=lst.get((lst.size()+i+1)%lst.size());
                break;
            }
        }

        for (int i=0; i<og.size(); i++) nums[i]=og.get(i);

    }

    private void backtrack(int[] nums, int i, List<Integer> subL, boolean[] marked) {
        subL.add(nums[i]);
        marked[i]=true;

        int n=nums.length;
        if (subL.size()==n) {
            lst.add(subL);
            return;
        }

        for (int start= (n+i+1)%n; start!=i; start=(n+start+1)%n){
            if(!marked[start]) {
                marked[start]=true;
                backtrack(nums, start, new ArrayList<>(subL), marked);
                marked[start]=false;
            }
        }
    }
}
