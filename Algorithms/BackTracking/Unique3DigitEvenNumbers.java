package Algorithms.BackTracking;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 15 March 2025
 */
public class Unique3DigitEvenNumbers {
    public static void main(String[] args) {
        int[] digit = {1,2,3,4,5,6,7,8,9,0};
        System.out.println("unique3DigitEvenNumbers(digit) => " + unique3DigitEvenNumbers(digit));
    }

    public static int unique3DigitEvenNumbers(int[] digit) {
        int count = 0;
        for (int i = 0; i < digit.length; i++) {
            for (int j = 0; j < digit.length; j++) {
                for (int k = 0; k < digit.length; k++) {
                    if (digit[i] * 100 + digit[j] * 10 + digit[k] % 2 == 0) {
                        count++;
                    }
                }
            }
        }
        return count;
    }

    /**
        PATTERNS:
        --------
        1) The last 3rd digit has to be an even number
        2) When digits=[0,2,2];
        3) Even nums set = [0,2]
        4) Add them to the stack

                            []
                    _________|_________
                    |                 |
                   [,0]              [,1]   ----> for loop for evens
              ______|______
             |             |
            [1,0]         [2,0]
             |             |
         [2,1,0]         [2,0,1]

        5) or Prepare all possible outcomes and c++ for even
     */
    Set<String> set = new HashSet<>();
    public int totalNumbersMyApproach(int[] digits) {
        boolean[] arr = new boolean[10]; // arr indices
        for(int i=0; i<digits.length; i++) {
            if (digits[i]%2==0 && !arr[i]) arr[i]=true;
        }

        for (int i=0; i<digits.length; i++) {
            if(arr[i]) {
                boolean[] marked = new boolean[digits.length];
                marked[i]=true;
                String s = "";
                backtrack(i, digits, marked, s);
            }
        }
        // System.out.println(set);
        return set.size();
    }

    private void backtrack(int i, int[] digits, boolean[] marked, String s) {
        // add num to s
        s = digits[i]+s;
        if(s.length()==3) {
            if (s.charAt(0)!='0') set.add(s);
            return;
        }
        for(int j=0; j<marked.length; j++){
            if (!marked[j]) {
                boolean[] newMarked = marked.clone();
                newMarked[j]=true;
                backtrack(j, digits, newMarked, s);
            }
        }
    }
}
