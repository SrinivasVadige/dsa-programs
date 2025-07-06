package Algorithms.Searching;

import java.util.Arrays;
import java.util.Random;
import java.util.stream.IntStream;

/**
 * @author Srinvas Vadige, srinivas.vadige@gmail.com
 * @since 21 Sept 2024
 * check {@link DataStructures.BinarySearch} 🔥
 */
public class BinarySearch {

    public static void main(String[] args) {
        Random random = new Random();
        int[] intArr = new int[100];
        IntStream.rangeClosed(1, 100).forEach(i-> intArr[i-1]=i);
        System.out.println("Input Array: " + Arrays.toString(intArr));
        int target = random.nextInt(1, 200);
        System.out.println("target is: "+ target);
        int targetIndex = search(intArr, target);
        System.out.println("Target index after the binary search is: " + targetIndex);
    }

    static int search(int[] intArr, int target){
        if (target < intArr[0] || target > intArr[intArr.length-1]) {
            return -1;
        }

        int l = 0;
        int r = intArr.length-1;

        while (l<=r) {
            int mid = (l + r) / 2;
            if (intArr[mid] == target)
                return mid;
            else if (intArr[mid] > target)
                r = mid-1;
            else
                l = mid+1;
        }
        return -1;
    }

}
