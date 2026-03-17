package Algorithms.Sorting;
import java.util.Arrays;

/**
* @author Srinvas Vadige, srinivas.vadige@gmail.com
* @since 21 Sept 2024
*/
public class SelectionSort { // Select from left to right; j=i+1

    public static void main(String[] args) {
        System.out.println(Arrays.toString(selectionSort(new int[]{3, 2, 4, -1, 1000, 100, 3, 1})));
    }

    /**
     * @TimeComplexity O(n²)
     * @SpaceComplexity O(1)
     * Move from left to right and sort from right to left
     * keep the current left item iItem constant and slide every right side items one by one till the iItem placed in it's correct position
     * So, it's looks like the combination of {@link Algorithms.Sorting.BubbleSort#bubbleSortUsingClassicApproach} and {@link Algorithms.Sorting.InsertionSort#insertionSortUsingSwap}
     */
    public static int[] selectionSort(int[] items){
        for(int i=0; i<items.length; i++){
            // PLACE THE iItem IN IT'S CORRECT POSITION
            for(int j=i+1; j<items.length; j++){
                int iItem = items[i];
                int jItem = items[j];
                if(iItem > jItem){ //swap every time or store the smallest item index and 'swap at last j'
                    int temp = items[i];
                    items[i] = items[j];
                    items[j] = temp;
                }
            }
        }
        return items;
    }
}
