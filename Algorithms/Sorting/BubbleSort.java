package Algorithms.Sorting;
import java.util.Arrays;
import java.util.stream.IntStream;

/**
* @author Srinvas Vadige, srinivas.vadige@gmail.com
* @since 21 Sept 2024



    APPROACH: Compare "adjacent pairs" and move the greater item (in the unsorted array section) to right
        start at j=0
        compare j & j+1
        sort from right to left (<-)
        trav from 0 to length-i-1 cause last i items already sorted

        Use second j-for-loop condition as -> for(int j=0; j < items.length-i-1; j++)
        In j < n-i-1;
        -1 cause we use both curr items[j] and next items[j+1] items
        and -i cause last items already been sorted. So, -i is optional & we add this to make less iterations

*/
public class BubbleSort {

    public static void main(String[] args) {
        System.out.println(Arrays.toString(bubbleSort2(new int[]{3, 2, 4, -1, 1000, 100, 3, 1})));
    }



    /**
     * @TimeComplexity O(n²)
     * @SpaceComplexity O(1)
     */
    public static int[] bubbleSort1(int[] items){
        System.out.println("Given array: " + Arrays.toString(items));

        for(int i=0; i<items.length; i++){
            System.out.printf("\ni: %s\n", i);
            System.out.println(Arrays.toString(IntStream.range(0, items.length-i).toArray()));

            for(int j=0; j<items.length-i-1; j++){ // for every i-iteration -> j starts from 0th index till the "unsorted" length
                int jItem = items[j];
                int jNextItem = items[j+1];
                System.out.println(Arrays.toString(items) + " - " + j + " & " + (j+1));
                // or just like #bubbleSort2() or #bubbleSort3() -> keep track of the final maxIndex j+1 index and swap values after the j-loop
                if(jNextItem < jItem){
                    int temp = items[j];
                    items[j] = items[j+1];
                    items[j+1] = temp;
                }
            }

            System.out.println(Arrays.toString(items) + " - done");
        }

        return items;
    }






    public static int[] bubbleSort2(int[] items) {
        int n = items.length;

        for (int i = 0; i < n-1; i++) {
            int maxIndex = 0; // next biggest item index

            for (int j = 0; j < n-i-1; j++) {
                if (items[maxIndex] < items[j+1]) maxIndex = j+1;
            }

            int lastUnsortedItem = items[n-i-1];
            items[n-i-1] = items[maxIndex];
            items[maxIndex] = lastUnsortedItem;

        }
        return items;
    }



    public static int[] bubbleSort3(int[] items) {
        int n = items.length;

        for (int i = 0; i < n-1; i++) {
            int maxIndex = 0;

            for (int j = 1; j < n-i; j++) {
                if (items[maxIndex] < items[j]) maxIndex = j;
            }

            int lastUnsortedItem = items[n-i-1];
            items[n-i-1] = items[maxIndex];
            items[maxIndex] = lastUnsortedItem;
        }

        return items;
    }
}
