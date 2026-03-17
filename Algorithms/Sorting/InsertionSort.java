package Algorithms.Sorting;

import java.util.Arrays;

/**
 * @author Srinvas Vadige, srinivas.vadige@gmail.com
 * @since 21 Sept 2024


    Here we sort (0 to 1), (0 to 2), (0 to 3) ... (0 to n-1) items by finding the iItem position in the sorted left subarray (0 to i-1)
*/
public class InsertionSort {

    public static void main(String[] args) {
        int[] items = new int[]{3, 2, 4, -1, 1000, 100, 3, 1};
        System.out.println("insertionSort Using Swap => " + Arrays.toString(insertionSortUsingSwap(Arrays.copyOf(items, items.length))));
        System.out.println("insertionSort Using Shift => " + Arrays.toString(insertionSortUsingShift(Arrays.copyOf(items, items.length))));
    }


    /**
     * @TimeComplexity O(n²)
     * @SpaceComplexity O(1)

        APPROACH:
            1. Work / Sort from left to right
            2. Examine each item and compare it with the items to its left
            3. Insert the small item into its correct position in the sorted left subarray
            4. So, finally we're sorting (0 to 1), (0 to 2), (0 to 3), ... (0 to n-1) items in right to left direction

        FIRST FOR i LOOP MOVES FROM LEFT TO RIGHT
        AND SECOND WHILE j LOOP MOVES FROM RIGHT TO LEFT i.e j always <= i and >= 0

        So this {@link #insertionSortUsingSwap} (right to left) is opposite of {@link Algorithms.Sorting.BubbleSort#bubbleSortUsingClassicApproach} (left to right)

        And this {@link #insertionSortUsingShift} is faster then {@link #insertionSortUsingSwap} cause in shift we're doing only one assignment
     */
    public static int[] insertionSortUsingSwap(int[] items){
        for(int i=1; i<items.length; i++){
            int j=i; // to sort (0 to i) items

            // FIND THE NEW I-ITEM POSITION
            while (j>0 && items[j-1]>items[j]) { // if jPrevItem > jItem then swap -> all small items to left
                int temp = items[j];
                items[j] = items[j-1];
                items[j-1] = temp;
                j--;
            }
        }
        return items;
    }







    /**
     * @TimeComplexity O(n²)
     * @SpaceComplexity O(1)


     instead of swapping like above {@link #insertionSortUsingSwap} we do shifting(copy)

     FIRST FOR i LOOP MOVES FROM LEFT TO RIGHT
     AND SECOND WHILE j LOOP MOVES FROM RIGHT TO LEFT i.e j always < i and max val of j+1 is i.

     In shifting, make a copy of jItem in jNextItem and j--
     i.e we have two same values here until
        1. we shift another jItem copy to jNextItem (the j pointer was already decreased in previous copy as j--) ==> this shifting continues until we find the correct position for the key
            or
        2. final jItem after iteration with key

     _________________________________
     |          |       |            |
     |  SORTED  |  KEY  |  UNSORTED  |
     |__________|_______|____________|

    in arr[ 0 to 0] array i.e., if we consider only first item as left sub-array it's already sorted. So, start from i=1
    second while loop iterates up to "j = (-1) or (original copy of small lefty index-1)" && checks if key is smaller than lefties in each while loop
    we're shifting j(left) index value to j+1(right), j won't change i.e original copy will be their until we shift again
    now after whole while loop, replace the j="(-1+1 val) or (original copy of small lefty val)" with our key
    to put it simply => all left side items of key iItem must be greater than or equal to key



     */
    public static int[] insertionSortUsingShift(int[] items){

        for(int i=1; i<items.length; i++){
            int key = items[i]; // iItem temp variable
            int j=i-1;

            // FIND KEY POSITION
            // Move elements of arr[0..i-1], that are greater than key, to one position ahead of their current position
            while (j>=0 && items[j]>key) { // if jItem < key iItem means it's already sorted
                items[j+1] = items[j]; // jItem copy in jNextItem i.e now we have two same value => original and copy
                j--; // right to left
            }

            items[j+1] = key; // replace final original jItem with key iItem temp variable
        }

        return items;
    }

}
