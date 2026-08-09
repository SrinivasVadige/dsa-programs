package Algorithms.DynamicProgramming;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * <pre>
 * Given an integer numRows, return the first numRows of Pascal's triangle.
 * In Pascal's triangle, each number is the sum of the two numbers directly above it as shown:
 *                                      1
 *                                     1.1
 *                                    1.2.1
 *                                   1.3.3.1
 *                                  1.4.6.4.1    => 5
 *                                1.5.10.10.5.1  => 6
 *                               1.6.15.20.15.6.1 =>7
 *                          or
 *                               1
 *                               1.1
 *                               1.2.1
 *                               1.3.3.1
 *                               1.4.6.4.1        => 5
 *                               1.5.10.10.5.1    => 6
 *                               1.6.15.20.15.6.1 => 7
 * </pre>
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 24 Oct 2024
 */
public class PascalsTriangle {

    public static void main(String[] args) {
        int n = 6;
        System.out.println(generateMyApproach(n));
    }

    /**
     * <pre>
     * Thoughts
     * --------
     * 1. sub-problem
     * 2. final list length == numRows
     * 3. min number is 1
     * 4. next min value is n-1
     * 5. First subList is [1]
     * 6. Add sequential pairs in before list i.e list.size()-1
     * 7. half of the subList is repeating i.e a mirror subLists
     * 8. even len subList or odd len
     * 9. nth row size is n --> so we can easily find the mid num in subList
     * 10. BU Tab with + Deque? or add two tempLists? or n size array and add on opposite sides? --- best
     * </pre>
     *
     * @TimeComplexity O(n * n/2) = O(n^2)
     * @Approach BottomUp Tabulation DP
     */
    public static List<List<Integer>> generateMyApproach(int numRows) {
        List<List<Integer>> list = new ArrayList<>();
        list.add(Arrays.asList(1));
        if(numRows == 1) return list;
        list.add(Arrays.asList(1,1));

        for (int i=2; i<numRows; i++){
            int n = i+1;
            Integer[] arr = new Integer[n];
            int mid = n%2!=0? n/2 +1: n/2;
            for(int j=0; j<mid; j++){
                if(j==0) arr[0] = arr[n-1] = 1;
                else if(j==1) arr[1] = arr[n-2] = n-1;
                else {
                    List<Integer> prevList = list.get(i-1);
                    if(j == n-1 && n%2!=0){
                        arr[n/2 - 1] = prevList.get(j-1) + prevList.get(j);
                        continue;
                    }
                    arr[j] = arr[n - j - 1] = prevList.get(j-1) + prevList.get(j);
                }
            }
            list.add( Arrays.asList(arr) );
            // or list.add( Arrays.stream(arr).collect(Collectors.toList()) ); or Arrays.stream(arr).toList();
            Arrays.stream(arr).collect(Collectors.toList());
        }
        return list;
    }

    public List<List<Integer>> generateTopDownDP(int numRows) {
        List<List<Integer>> list = new ArrayList<>();
        if (numRows == 0) {
            return list;
        }

        if (numRows == 1) {
            list.add(Arrays.asList(1));
            return list;
        }

        list = generateTopDownDP(numRows - 1);
        List<Integer> prevRow = list.get(numRows - 2);
        List<Integer> currentRow = new ArrayList<>();
        currentRow.add(1);

        for (int i = 1; i < numRows - 1; i++) {
            currentRow.add(prevRow.get(i - 1) + prevRow.get(i));
        }

        currentRow.add(1);
        list.add(currentRow);

        return list;
    }

    public List<List<Integer>> generateBottomUpTabulation2(int numRows) {
        List<List<Integer>>res= new ArrayList<>();
        if(numRows==0){
            return res;
        }
        List<Integer>row1=new ArrayList<>();
        row1.add(1);
        res.add(row1);
        for(int i=1;i<numRows;i++){
            List<Integer>prevRow=res.get(i-1);
            List<Integer>newRow= new ArrayList<>();
            newRow.add(1);
            for(int j=1;j<i;j++){
                newRow.add(prevRow.get(j-1)+prevRow.get(j));
            }
            newRow.add(1);
            res.add(newRow);
        }
        return res;
    }

    /*
     *
     * there is another approach where we treat before 1 and after 1 as 0 and we can add all the pairs of preList to get curList
     */
}
