package Algorithms.IntegerArray;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 06 April 2025
 */
public class MinimumPairRemovalToSortArrayI {
    public static void main(String[] args) {
        int[] nums = {5,2,3,1};
        System.out.println("minimumPairRemovalMyApproach(nums) => " + minimumPairRemovalMyApproach(nums)); // Output: 2
    }

    /**
        PATTERNS:
        ---------
        1) Final arr should be in ASC order
    */
    public static int minimumPairRemovalMyApproach(int[] nums) {
        List<Integer> lst = new ArrayList<>();
        for(int num: nums) lst.add(num);

        int c = 0;
        // if not ordered then loop until it's ordered
        while(!isOrdered(lst)) {
            int addI = 0;
            int minSum = Integer.MAX_VALUE;
            for(int i=0; i<lst.size()-1; i++) {
                int currSum = lst.get(i) + lst.get(i+1);
                if (minSum > currSum) {
                    addI=i;
                    minSum = currSum;
                }
            }

            // update the list --> remove the i+1 element and add it to i element
            // e.g. [5,2,3,1] --> [5,2,4] --> [5,6] => now it's in order
            int temp = lst.get(addI+1);
            lst.remove(addI+1);
            lst.set(addI, lst.get(addI)+temp);

            // as we updated the lst then update the counter
            c++;
        }
        return c;
    }

    private static boolean isOrdered(List<Integer> lst) {
        int n = lst.size();
        if(n==1) return true;
        for (int i = 0; i< n-1; i++) {
            if(lst.get(i) > lst.get(i+1)) return false;
        }
        return true;
    }
}
