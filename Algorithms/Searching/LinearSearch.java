package Algorithms.Searching;

import java.util.Arrays;
import java.util.Random;

/**
* @author Srinvas Vadige, srinivas.vadige@gmail.com
* @since 21 Sept 2024
*/
public class LinearSearch {
    public static void main(String[] args) {
        Random random = new Random();
        int[] intArr = new int[100];
        for (int i = 0; i < 100; i++)
            intArr[i] = random.nextInt(1, 100);

        System.out.println("Input Array: " + Arrays.toString(intArr));
        int target = random.nextInt(1, 100);
        int targetIdex = search(intArr, target);
        System.out.println("target is: "+ target + " and it's index after the linear search is: "+targetIdex);


    }

    static int search(int[] intArr, int target){
        for (int i=0; i<intArr.length; i++) {
            if(intArr[i] == target)
                return i;
        }
        return -1;
    }
}
