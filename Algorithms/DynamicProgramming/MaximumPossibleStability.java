package Algorithms.DynamicProgramming;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.IntStream;

/**
<pre>
AWS provides a range of servers to meet the deployment needs of its clients. A client wants to choose a set of servers to deploy their application.
Each server is associated with an availability factor and a reliability factor.
The client defines the stability of a set of servers as the minimum availability amongst the servers multiplied by the sum of reliabilities of all the servers.
Given two arrays of integers, availability, and reliability, where the availability] and reliability] represent the availability and reliability factors of the ith server, find the maximum possible stability of any subset of servers.
Since the answer can be large, report the answer modulo % (10⁹ + 7) == 10^9 + 7 == 1,000,000,007

Example
Consider the set of servers where reliability = [1, 2, 2] and availability = [1, 1, 3].
The possible subsets of servers are:
Indices => Stability
[0] => only 0th server => 1 * 1 = 1
[1] => only 1st server => 1 * 2 = 2
[2] => only 2nd server => 3 * 2 = 6

[0, 1] => min(1, 1) * (1 + 2) = 3
[0, 2] => min(1, 3) * (1 + 2) = 3
[1, 2] => min(1, 3) * (2 + 2) = 4
[0, 1, 2] => min(1, 1, 3) * (1 + 2 + 2) = 5
             min(a0,a1,a2) * (r0+r1+r2)

availability[i], reliability[i] are integers
when single server => then a[i]*r[i]
else => min(a[i], a[j], .... ) * (r[i] + r[j] + .... )
Here, we have i,j,k...... many subsets like [i], [i, j], [i, j, k] and so on.

So answer is maximum stability for the set of index {2}, answer = 6 % 1000000007 = 6.

Function Description:
Complete the function getMaxStability in the editor below
getMaxStability has the following parameters:
int reliability[n]: server reliability values
int availability[n]: server availability values
Returns
int: the maximum stability above among all possible non-empty subsets, modulo (10⁹+7)

Constraints:
1 ≤ n ≤ 105
1 ≤ reliability[i], availability[i] ≤ 106
It is guaranteed that lengths of reliability and availability are the same

                                                0, 1, 2, 3 --> indices

                                                        []
                        ________________________________|____________________________
                        |                          |                     |           |
                       [0]                        [1]                   [2]         [3]
     ___________________|_______       ____________|______         ______|____       |
     |       |          |      |       |       |         |         |         |       |
    [0,0]  [0,1]      [0,2]   [0,3]   [1,1]  [1,2]      [1,3]    [2,2]     [2,3]   [3,3]
        ____|__         |                      |
        |     |         |                      |
    [0,1,2]  [0,1,3]  [0,2,3]                [1,2,3]
        |
    [0,1,2,3]


    use dedicated for loop for [i,i] => i*i scenario and for the rest, use below recursive approach
    [0,0] [1,1] [2,2] [3,3]
    the above sequence can be written as a binary tree like below
    leftNode => incrementNumber
    rightNode => addNumber

                                                        []
                            _____________________________|______________________________________________
                            |                                          |                   |           |
                           [0]                                        [1]                 [2]         [3]
                 ___________|_____________                      _______|____         ______|____       |
                 |                       |                      |           |        |          |      |
                [0,1]                 [0,1,2]                 [1,2]      [1,2,3]   [2,3]        ❌    ❌
       __________|________        _______|______            ____|_____
       |                 |        |            |            |        |
      [0,2]           [0,1,2]   [0,1,3]     [0,1,2,3]     [1,3]   [1,2,3]
    ___|____         ____|____
    |      |         |       |
  [0,3]  [0,2,3]  [0,1,3] [0,1,2,3]


 Memo->use Set/Map to check whether we crossed the same possibility or sub-tree like [0,1,2]


 🔥🔥🔥
or
without above for-loop
just use Math.max(max, rec(new int[]{0}, dp, reliability, availability));
and in rec() method, if(indices.length == 1) then max = r[i]*a[i];
and continue the incrementNumber and addNumber

                                        0, 1, 2, 3 --> indices

                                                  [0]
                         __________________________|____________________
                         |                                             |
                        [1]                                          [0,1]
                    _____|_________________                      _____|________________
                    |                     |                      |                    |
                   [2]                   [1,2]                  [0,2]               [0,1,2]
               _____|_____          ______|______          ______|_____         ______|______
               |         |          |           |          |          |         |           |
              [3]       [2,3]      [1,3]       [1,2,3]    [0,3]      [0,2,3]   [0,1,3]   [0,1,2,3]


</pre>
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 11 Jan 2025
 */
public class MaximumPossibleStability {
    public static void main(String[] args) {
        int[] reliability = {1, 2, 2};
        int[] availability = {5, 1, 3};
        System.out.println( "getMaxStability => " + getMaxStability(reliability, availability));
        System.out.println( "getMaxStabilityUsingSort => " + getMaxStabilityUsingSort(reliability, availability));
    }

    public static int getMaxStability(int[] reliability, int[] availability) {
        return rec(new int[]{0}, reliability, availability);
    }

    static int rec(int[] indices, int[] reliability, int[] availability) {
        final int MOD = (int)1e9+7;
        int max = Integer.MIN_VALUE;
        if(indices[indices.length-1] >= reliability.length) // BASE CASE: index > len IndexOutOfBound
            return max;

        if (indices.length == 1) {
            max = Math.max(max, (availability[indices[0]] * reliability[indices[0]])%MOD);
        } else {
            int aMin = Integer.MAX_VALUE;
            int rSum = 0;
            for (int i : indices) {
                aMin = Math.min(aMin, availability[i]);
                rSum += reliability[i];
            }
            max = Math.max(max, (aMin*rSum)%MOD);
        }

        int[] incrementNumber = Arrays.copyOf(indices, indices.length);
        incrementNumber[indices.length-1]++;
        int[] addNumber = Arrays.copyOf(indices, indices.length+1);
        addNumber[indices.length] = indices[indices.length-1]+1;

        max = Math.max(max, rec(incrementNumber, reliability, availability));
        max = Math.max(max, rec(addNumber, reliability, availability));
        return max;
    }

    public static int getMaxStabilityUsingSort(int[] reliability, int[] availability) {
        long ans = 0;
        int n = reliability.length;

        // sort indices based on availability
        Integer[] indices = IntStream.range(0, n).boxed().toArray(Integer[]::new); // or for (int i = 0; i < n; i++) {indices[i] = i;}
        Arrays.sort(indices, (a, b) -> availability[a] - availability[b]);
        /*
            indices = {0,1,2}
            availability = {5,1,3}
                            0 1 2
            indices will change to = {1,2,0} and it won't change availability
                                      1 3 5
            so, this indices will be sorted based on availability in ascending order
            so, we no need to use zip(availability, reliability) and sort() like python
         */

        // considering ith index corresponds to min Availability
        // and find maxima of suffixSum*availability[indices[i]]
        long sum = 0;
        final int MOD = (int)1e9 + 7;
        for (int i = n - 1; i >= 0; i--) {
            sum += reliability[indices[i]] % MOD;
            ans = Math.max(ans, sum * availability[indices[i]]) % MOD;
        }
        return (int) (ans % MOD);
    }






    public static int getMaxStabilityOld(int[] reliability, int[] availability) {
        int max = 0;

        // calculate when i==j
        for (int i = 0; i < reliability.length; i++) {
            max = Math.max(max, availability[i] * reliability[i]);
        }

        Set<String> dp = new HashSet<>(); // to check whether we crossed the same possibility or sub-tree like [0,1,2]

        // recursion
        for (int i = 0; i < reliability.length-1; i++) {
            max = Math.max(max, recOld(new int[]{i, i+1}, dp, reliability, availability));
            max = Math.max(max, recOld(new int[]{i, i+1, i+2}, dp, reliability, availability));
        }

        return max;
    }

    static int recOld(int[] indices, Set<String> dp, int[] reliability, int[] availability) {
        int max = Integer.MIN_VALUE;
        if(indices[indices.length-1] >= reliability.length // BASE CASE: index > len IndexOutOfBound
        || dp.contains(Arrays.toString(indices))) // skip if already calculated
            return max;

        int aMin = Integer.MAX_VALUE;
        int rSum = 0;
        for (int i : indices) {
            aMin = Math.min(aMin, availability[i]);
            rSum += reliability[i];
        }
        max = Math.max(max, aMin*rSum);

        int[] incrementNumber = Arrays.copyOf(indices, indices.length);
        incrementNumber[indices.length-1]++;
        int[] addNumber = Arrays.copyOf(indices, indices.length+1);
        addNumber[indices.length] = indices[indices.length-1]+1;

        dp.add(Arrays.toString(indices));
        max = Math.max(max, recOld(incrementNumber, dp, reliability, availability));
        max = Math.max(max, recOld(addNumber, dp, reliability, availability));
        return max;
    }



















    public static int getMaxStabilityBasicThoughts(int[] reliability, int[] availability) {
        int max = 0;

        // calculate when i==j
        for (int i = 0; i < reliability.length; i++) {
            max = Math.max(max, availability[i] * reliability[i]);
        }

        // calculate when i!=j using dynamic programming
        // Math.min(a[i], a[j], a[k], ...) * (r[i] + r[j] + r[k] + ...)
        for (int i = 0; i < availability.length-1; i++) { // ignore last index
            // int[] subArr = new int[availability.length-1-i];
            // subArr = Arrays.copyOfRange(availability, i+1, availability.length);
        }



        for (int i = 0; i < reliability.length; i++) {
            for (int j = i + 1; j < reliability.length; j++) {
                max = Math.max(max, Math.min(availability[i], availability[j]) * (reliability[i] + reliability[j]));
            }
        }
        return 0;
    }
}
