package BasicPrograms;

import java.util.ArrayList;
import java.util.List;

/**
* Divisors or factors are the numbers that can divide the given number i.e num%i == 0 i.e not remainder
* @author Srinvas Vadige, srinivas.vadige@gmail.com
* @since 21 Sept 2024
*/
public class AllDivisors {
    public static void main(String[] args) {
        int n = 36;
        System.out.println("sorted divisors brute force " + getDivisorsBruteForce(n));
        System.out.println("sorted divisors with sqrt() approach " + getDivisorsWithSqrtLoop(n));
    }

    /*
    Note that i <= n/2 is bad approach for divisors, can use it for prime number
    or loop until i <= (int) Math.sqrt(n);
    Add the counterpart divisor if it's different from i
    => if (i != n / i) divisors.add(n / i);

    cause
    the divisors of 36 number are [1, 2, 3, 4, 6, 9, 12, 18, 36]
    1*36
    2*18
    3*12
    4*9
    6*6
    9*4
    12*3
    18*2
    36*1

    this is repeating after 6*6 in reverse order
 */

    /**
     * Time complexity: O(n)
     *
     * @param n
     * @return List<Integer>
    */
    static List<Integer> getDivisorsBruteForce(int n) {
        List<Integer> divs = new ArrayList<>(); // or get size[1] i.e 1st index ele from main method as reference variable get the divs size after the while loop and here create and return divisors array int[n] and start appending from 0th index ike divisors[count++] = i; and size[0] = count; return divisors;
        for (int i = 1; i <= n; i++) {
            if (n%i == 0) {
                divs.add(i);
            }
        }
        return divs;

    }

    /**
     * TimeComplexity: O(Sqrt(n))
     * @param n
     * @return
    */
    static List<Integer> getDivisorsWithSqrtLoop(int n) { // Note that for just sysout if else conditions won't print in order. So, use this method to get divs list and print it
        List<Integer> divs = new ArrayList<>();
        for (int i = 1; i <= Math.sqrt(n); i++) {
            if (n%i == 0) {
                // NORMAL ---------------
                if (divs.size() != 1) divs.add(divs.size()/2, i); else divs.add(i); // corner-case for 1
                ////PATTERM=> [] i=1 | [1,36] i=2 | [1,2,18,26] i=3 -- size is even

                // COUNTERPART: for Reciprocal factor ---------------
                int iMutiplier = n/i;
                if (i != iMutiplier) { // for "perfect squre" 6*6 = 36 scenario i.e don't add 6 two times. And we already know that iMultiplier is also a divisor because n%i == 0 means i and n/i are factors of n.
                    divs.add(divs.size()/2 +1, iMutiplier);
                    //PATTERN=> [1] iM=36 | [1,2,36] iM=16 | [1,2,3,18,36] iM=12 -- size is odd
                }

            }
        }
        return divs;

    }


    static List<Integer> getDivisorsWithSqrtLoopMoreConditions(int n) { // use getDivisorsWithSqrtLoop() instead of this
        List<Integer> divs = new ArrayList<>();
        for (int i = 1; i <= Math.sqrt(n); i++) {
            if (n%i == 0) {
                // NORMAL ---------------
                // divs.add(i); //unsort insertion [1, 36, 2, 18, 3, 12, 4, 9, 6] // or use sort insertion [1, 2, 3, 4, 6, 9, 12, 18, 36] with below logic by inserting the i in middle
                if (divs.size()>1 && i < divs.get(divs.size()/2)) divs.add(divs.size()/2, i); else divs.add(i); // else conditon is corener case for '1' and so all other nums inserted in if condition // or  if (divs.size()>1 && i < divs.get(divs.size()/2) )
                //[] i=1 | [1,36] i=2 | [1,2,18,26] i=3 -- size is even

                // COUNTERPART ---------------
                int iMutiplier = n/i;
                if (i != iMutiplier) { // just to avoid square root number i.e 6 and we already know that iMultiplier is divisor as n%i == 0 because n/i is nothing but iMultiplier
                    // divs.add(n/i); //unsort insertion [1, 36, 2, 18, 3, 12, 4, 9, 6] // or use sort insertion [1, 2, 3, 4, 6, 9, 12, 18, 36] with below logic by inserting the iMultiplier in middle
                    if (divs.size()>1 && iMutiplier < divs.get(divs.size()/2 +1)) divs.add(divs.size()/2 +1, iMutiplier); else divs.add(iMutiplier);  // --------- to place in the middle // else is corener case for 36 i.e self n           // or if(divs.size()>1) i.e for reading purpose gave 2 or if(divs.size()>2 && iMutiplier < divs.get(divs.size()/2 +1))
                    //[1] iM=36 | [1,2,36] iM=16 | [1,2,3,18,36] iM=12 -- size is odd
                }
            }
        }
        return divs;

    }
}
