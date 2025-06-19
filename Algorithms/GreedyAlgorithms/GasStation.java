package Algorithms.GreedyAlgorithms;

import java.util.Arrays;

/**
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 19 June 2025
 * @link 134. Gas Station <a href="https://leetcode.com/problems/gas-station/">Leetcode link</a>
 * @topics Greedy, Array

 * Given that we have unlimited gas-tank -- not unlimited gas just tank
 * gas[] contains the amount of gas in each station
 * cost[] contains the cost of gas in each station ---> this means the gas consumed to travel from i to i+1
 */
public class GasStation {
    public static void main(String[] args) {
        int[] gas = {1, 2, 3, 4, 5};
        int[] cost = {3, 4, 5, 1, 2};
        System.out.println(canCompleteCircuit(gas, cost));
    }

    public static int canCompleteCircuit(int[] gas, int[] cost) {
        int totalGas = 0, totalCost = 0;

        for (int i = 0; i < gas.length; i++) {
            totalGas += gas[i];
            totalCost += cost[i];
        }

        if (totalGas < totalCost) {
            return -1;
        }

        int availableGas = 0;
        int start = 0;

        for (int i = 0; i < gas.length; i++) {
            int availableLeftAfterI = gas[i] - cost[i]; // this is the gas left after we start from i and finish at i+1
            availableGas += availableLeftAfterI;
            if (availableGas < 0) {  // if availableGas is -ve, then it's confirmed that we can't go form start to i+1 i.e. "start" is not valid
                availableGas = 0;
                start = i + 1;
            }
        }

        return start;
    }



    public int canCompleteCircuit2(int[] gas, int[] cost) {
        int totalGas = 0, totalCost = 0;

        for (int i = 0; i < gas.length; i++) {
            totalGas += gas[i];
            totalCost += cost[i];
        }

        if (totalGas < totalCost) {
            return -1;
        }

        int currentGas = 0; // tatal gas
        int start = 0;

        for (int i = 0; i < gas.length; i++) {
            currentGas += gas[i] - cost[i];
            if (currentGas < 0) {  //if the previous solutions does not work, we should try next ones
                currentGas = 0;
                start = i + 1;
            }
        }

        return start;
    }




    /**
     * WORKING BUT TLE

        Given
        [1, 2, 3, 4, 5] ---> gas stations
        [3, 4, 5, 1, 2] ---> cost == gas consumes to travel to i+1 gas station


        [1, 2, 3, 4, 5]
        [3, 4, 5, 1, 2]
         i
         gasTank = 1-0 = 1 -- minus cost
         gasTank = 1   = 1 -- add gas

        [1, 2, 3, 4, 5]
        [3, 4, 5, 1, 2]
            i
         gasTank = 1-3 = -2  -- minus cost
         gasTank = -2+2 = 2  -- add gas

        [1, 2, 3, 4, 5]
        [3, 4, 5, 1, 2]
               i
         gasTank = 2-4 = -2  -- minus cost
         gasTank = -2+3 = 1  -- add gas

        [1, 2, 3, 4, 5]
        [3, 4, 5, 1, 2]
                  i
         gasTank = 1-5 = -4  -- minus cost
         gasTank = -4+4 = 0  -- add gas

        [1, 2, 3, 4, 5]
        [3, 4, 5, 1, 2]
                     i
         gasTank = 0-1 = -1  -- minus cost
         gasTank = -1+4 = 4  -- add gas

        [1, 2, 3, 4, 5]
        [3, 4, 5, 1, 2]
         i
         gasTank = 4-2 = 2  -- minus cost
         gasTank = 2+1 = 3  -- add gas --> not needed for circular loop


     */
    public int canCompleteCircuitUsingBruteForce(int[] gas, int[] cost) {
        int n = gas.length;
        for(int start=0; start<n; start++) {
            int gasTank = 0;
            int prevCost = 0;
            for(int curr=start, i=0; i<=n; i++, curr = (curr+1)%n) { // or curr++; curr %= n;
                gasTank -= prevCost;
                // System.out.printf("start: %s, curr: %s, gasTank: %s\n", start, curr, gasTank);
                if (gasTank < 0) break;

                gasTank += gas[curr];
                prevCost = cost[curr];
            }
            if(gasTank>0) return start;
        }
        return -1;
    }









    /**
        NOT WORKING for

        [5,1,2,3,4] gas
        [4,4,1,5,1] cost


        Given
        [1, 2, 3, 4, 5] ---> gas stations
        [3, 4, 5, 1, 2] ---> cost == gas consumes to travel to i+1 gas station


       [ 1, 2, 3, 4, 5] g --> sum = 15
       [14,13,12,11,10] ----> sum except self

       [ 3, 4, 5, 1, 2] c --> sum = 15
       [12,11,10,14,13] ----> sum except self
         gas



     */
    public int canCompleteCircuitNotWorking(int[] gas, int[] cost) {
        int gSum = Arrays.stream(gas).sum();
        int cSum = Arrays.stream(cost).sum();
        if(gSum < cSum) return -1;

        int n = gas.length;
        int[] gasSumExceptSelf = new int[n];
        int[] costSumExceptSelf = new int[n];

        for(int i=0; i<n; i++) {
            gasSumExceptSelf[i] = gSum - gas[i];
            costSumExceptSelf[i] = cSum - cost[i];

            if(gasSumExceptSelf[i] < costSumExceptSelf[i]) return i;
        }
        return -1;
    }

    public int canCompleteCircuitNotWorking2(int[] gas, int[] cost) {
        int n = gas.length;
        int gSum = Arrays.stream(gas).sum();
        int cSum = Arrays.stream(cost).sum();
        if(gSum < cSum) return -1;

        for(int i=0; i<n; i++) {
            if (gas[i] > cost[i]) return i;
        }
        return -1;

    }

}
