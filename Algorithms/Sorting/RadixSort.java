package Algorithms.Sorting;

import java.util.Arrays;

/**
* @author Srinvas Vadige, srinivas.vadige@gmail.com
* @since 21 Sept 2024

    @TimeComplexity O(d(n + k)) worst case, O(d(n + k)) best case, where n = elements, d = number of digits, k = base (like 10)
    @SpaceComplexity O(n + k)
*/
public class RadixSort {
    public static void main(String[] args) {
        System.out.println(Arrays.toString(sort(new int[]{3, 2, 4, -1, 1000, 100, 3, 1})));
    }


    public static int[] sort(int[] items){
        return items;
    }

}
