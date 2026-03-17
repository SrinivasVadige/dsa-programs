package Algorithms.Sorting;

import java.util.Arrays;

/**
 * @author Srinvas Vadige, srinivas.vadige@gmail.com
 * @since 21 Sept 2024


    @TimeComplexity O(n log n) both best and worst case
    @SpaceComplexity O(n), for extra space used in merge() method - two halves

    APPROACH:
        Divide & Conquer algorithm

      take the middle index, divide the into 2 sublists(0 to m and m to length) and continue so on
      recursion function and update the inputArr and it's sub halfs using pass by reference

*/
public class MergeSort {


    public static void main(String[] args) {
        int[] inputArr = new int[]{3, 2, 4, -1, 1000, 100, 3, 1, 0};
        System.out.println("------------------ Before sorting: " + Arrays.toString(inputArr));
        sort(inputArr);
        System.out.println("------------------ After sorting: " + Arrays.toString(inputArr));
    }


    /**
     * @TimeComplexity O(n log n) both best and worst case
     * @SpaceComplexity O(n), for extra space used in merge() method - two halves
     */
    public static void sort(int[] inputArr){

        // -------- DIVIDE ---------

        // no need to divide [e] or [] again into two halves
        if(inputArr.length < 2) return;

        // divide the input array into 2 halves using middle index
        int midIndex = inputArr.length/2;
        int[] leftHalf = Arrays.copyOfRange(inputArr, 0, midIndex); // or use for loop leftHalf[i]=inputArr[i] or System.arraycopy or IntStream
        int[] rightHalf = Arrays.copyOfRange(inputArr, midIndex, inputArr.length); // or use for loop rightHalf[i-midIndex]=inputArr[i]
        System.out.println("DIVIDING input array: " + Arrays.toString(inputArr)  + ", length: " + inputArr.length + " INTO TWO HALVES => " + "  leftHalf array: " + Arrays.toString(leftHalf) + ", length: " + leftHalf.length + "  rightHalf array: " + Arrays.toString(rightHalf) + ", length: " + rightHalf.length);

        // now again divide these sub half arrays into more two halves using recursion until single element array
        sort(leftHalf);
        sort(rightHalf);


        // --------- CONQUER / MERGE / COMBINE ----------
        // update the inputArr by comparing each elements with sub arrays (nested recursion left & right halves becomes [e1] [e2] then start comparing with merge() method )
        // we can use extra method and update inputArr using "pass by reference" (extra method is optional) 
        merge(inputArr, leftHalf, rightHalf);
        System.out.println("==========> inputArr after sorting => " + Arrays.toString(inputArr));
    }

    static void merge(int[] inputArr, int[] leftHalf, int[] rightHalf){

        // ----- loop leftHalf and rightHalf at once using while loop ----

        // left, right, input arrays indexes
        int l=0, r=0, i=0;
        int leftSize = leftHalf.length;
        int rightSize = rightHalf.length;

        // compare each left and right half elements and update the inputArr
        // we don't care about the previous values in inputArr, we just want that array length to be the sum of leftSize and rightSize
        // this while loop exits when l == leftSize or r == rightSize i.e we can't complete all the l or r values here
        // cause when inputArr[1,8,5] => unsorted in current recursion leftHalf[1,8] rightHalf[5]
        // => sorted after child recursion leftHalf[1,8] rightHalf[5] === after while loop ===> current recursion inputArr[1,5,5] l=1,r=1,i=1
        // but we need l to be 2 that means all l elements completed
        // or inputArr[1,0,9,8] => leftHalf[1,0] rightHalf[9,8] => leftHalf[0,1] rightHalf[8,9] ======> inputArr[0,1,9,8] l=2,r=0,i=2
        // i.e always only one sub half failed to increase index value upto it's length
        while (l<leftSize && r<rightSize) {
            if (leftHalf[l] <= rightHalf[r]) {
                inputArr[i] = leftHalf[l];
                l++;
            } else {
                inputArr[i] = rightHalf[r];
                r++;
            }
            i++;
        }

        // now, complete all the remaining (l and i) or (r and i) values
        while (l<leftSize) {
            inputArr[i] = leftHalf[l];
            l++;i++;
        }
        while (r<rightSize) {
            inputArr[i]=rightHalf[r];
            r++;i++;
        }
    }

}
