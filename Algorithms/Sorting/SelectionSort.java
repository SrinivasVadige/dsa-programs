package Algorithms.Sorting;
import java.util.Arrays;

/**
* @author Srinvas Vadige, srinivas.vadige@gmail.com
* @since 21 Sept 2024

    @TimeComplexity O(n²) worst case and O(n²) best case - consistency
    @SpaceComplexity O(1)
*/
public class SelectionSort { // Select from left to right; j=i+1

    public static void main(String[] args) {
        System.out.println(Arrays.toString(selectionSort1(new int[]{3, 2, 4, -1, 1000, 100, 3, 1})));
    }

    /**
     * @TimeComplexity O(n²) worst case and O(n²) best case - consistency
     * @SpaceComplexity O(1)

       Move from left to right and sort is also from left to right and IN-PLACE

       APPROACH:
        FIND THE SMALLEST jItem FOR i POSITION
        -> choose the smallest item from all the right side items and place it in i-th position
        see {@link #selectionSort2} for easy understanding

       So it's looks like the combination of {@link Algorithms.Sorting.BubbleSort#bubbleSortUsingClassicApproach} and {@link Algorithms.Sorting.InsertionSort#insertionSortUsingSwap}
     */
    public static int[] selectionSort1(int[] items){
        for(int i=0; i<items.length; i++){
            //
            for(int j=i+1; j<items.length; j++){
                int iItem = items[i];
                int jItem = items[j];
                if(iItem > jItem){
                    int temp = items[i];
                    items[i] = items[j];
                    items[j] = temp;
                }
            }
        }
        return items;
    }


    public static int[] selectionSort2(int[] items){
        for (int i=0; i<items.length; i++) {
            int minNumIndex = i; // smallest item index
            for (int j=i+1; j<items.length; j++){
                if (items[minNumIndex] > items[j]){
                    minNumIndex = j;
                }
            }

            int temp = items[i];
            items[i] = items[minNumIndex];
            items[minNumIndex] = temp;
        }
        return items;
    }
}
