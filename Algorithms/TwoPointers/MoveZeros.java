package Algorithms.TwoPointers;

import java.util.*;
import java.util.stream.*;

/**
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 03 Sept 2024
 */
public class MoveZeros {
    public static void main(String[] args) {

        int[] nums = {0,
            1,0,3,12};
        moveZeroes(nums);
        System.out.println(Arrays.toString(nums));
    }

    /**
     * keep left pointer in 1st 0 num index
     *
     * Just move all non-zero elements to the left (or) move zeros to next non-zero element
     */
    public static void moveZeroes(int[] nums) {
        int l = 0; // always keep "left" at zero number

        for (int r = 0; r < nums.length; r++) {
            if (nums[r] != 0) {
                swap(nums, l, r);
                l++; // increment "left" if non-zero
            }
        }
    }
    private static void swap(int[] nums, int l, int r) {
        if (l == r) return;
        int temp = nums[l];
        nums[l] = nums[r];
        nums[r] = temp;
    }



    public static void moveZeroesEnhanced(int[] nums) {
        int l = -1;
        // trav until you find "0"
        for (int i = 0; i < nums.length; i++) {
            if(nums[i] == 0) {
                l = i;
                break;
            }
        }
        if (l == -1) return; // "0" not found

        for (int r = l+1; r < nums.length; r++) {
            if (nums[r] != 0) {
                swap(nums, l, r);
                l++;
            }
        }

    }

    // brute force with ===0
    public static void moveZeroesBruteForce(int[] nums) {
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == 0) {
                for (int j = i; j < nums.length - 1; j++) {
                    nums[j] = nums[j + 1];
                }
                nums[nums.length - 1] = 0;
            }
        }
    }
    public void moveZeroesBruteForce2(int[] nums) {
        int n = nums.length;
        int nonZeroI = 0;
        for(int i=0; i<n && nonZeroI<n; i++) {
            nonZeroI=i;
            if (nums[nonZeroI]==0) {
                while(nonZeroI<n && nums[nonZeroI]==0) nonZeroI++;
                if (nonZeroI<n && nonZeroI != i) move(nums, i, nonZeroI, n);
            }
        }
    }
    private void move(int[] nums, int zeroI, int nonZeroI, int n) {
            nums[zeroI]=nums[nonZeroI];
            nums[nonZeroI]=0;
    }

    // using list
    public static void moveZeroesList(int[] nums) {

        List<Integer> list = new ArrayList<Integer>();
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == 0) {
                list.add(0);
            }
        }

        List<Integer> nonZeroList = new ArrayList<Integer>();

        nonZeroList = IntStream.of(nums).filter(n -> n != 0).boxed().collect(Collectors.toList());

        nums = nonZeroList.stream().mapToInt(i -> i).toArray();
    }
}
