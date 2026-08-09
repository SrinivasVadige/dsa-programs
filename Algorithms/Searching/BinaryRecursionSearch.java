package Algorithms.Searching;

import java.util.Arrays;
import java.util.Random;
import java.util.stream.IntStream;

/**
 * @author Srinvas Vadige, srinivas.vadige@gmail.com
 * @since 21 Sept 2024
 * check {@link DataStructures.BinarySearch} 🔥
*/
public class BinaryRecursionSearch { // the input has to be sorted before the binary search
        public static void main(String[] args) {
        Random random = new Random();
        int[] intArr = new int[100];
        IntStream.rangeClosed(1, 100).forEach(i-> intArr[i-1]=i);
        System.out.println("Input Array: " + Arrays.toString(intArr));
        int target = random.nextInt(1, 200);
        System.out.println("target is: "+ target);
        int targetIdex = search(intArr, target, 0, intArr.length-1);
        System.out.println("Target index after the binary search is: "+targetIdex);
    }

    static int search(int[] intArr, int target, int firstItemIndex, int lastItemIndex){

        if (target < intArr[firstItemIndex] || target > intArr[lastItemIndex]) {
            return -1;
        }

        int middleItemIndex = (firstItemIndex + lastItemIndex)/2;

        if (target == intArr[middleItemIndex])
            return middleItemIndex;
        else if (target < intArr[middleItemIndex]) {
            return search(intArr, target, firstItemIndex, middleItemIndex-1);
        } else{
            return search(intArr, target, middleItemIndex+1, lastItemIndex);
        }
    }
}
