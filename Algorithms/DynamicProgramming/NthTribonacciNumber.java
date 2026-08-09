package Algorithms.DynamicProgramming;

import java.util.Arrays;
import java.util.List;

/**
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 11 May 2025
 */
public class NthTribonacciNumber {
    public static void main(String[] args) {
        int n = 4;
        System.out.println("tribonacciTopDownMemoDp => " + tribonacciTopDownMemoDp(n));
        System.out.println("tribonacciRecursiveBacktracking => " + tribonacciRecursiveBacktracking(n));
        System.out.println("tribonacciBottomUpTabulationDp => " + tribonacciBottomUpTabulationDp(n));
        System.out.println("tribonacciBottomUpNoMemoryDp => " + tribonacciBottomUpNoMemoryDp(n));
    }

    public static int tribonacciTopDownMemoDp(int n) {
        if(n==0) return 0;
        else if(n<=2) return 1;

        int[] dp = new int[n+1];
        dp[1]=dp[2]=1;
        return dfs(n, dp);
    }
    public static int dfs(int n, int[] dp){
        if(n==0 || dp[n]>0) return dp[n];
        return dp[n] = dfs(n-1, dp) + dfs(n-2, dp) + dfs(n-3, dp);
    }




    public static int tribonacciRecursiveBacktracking(int n) {
        if(n==0) return 0;
        else if(n<=2) return 1;
        return tribonacciRecursiveBacktracking(n-1) + tribonacciRecursiveBacktracking(n-2) + tribonacciRecursiveBacktracking(n-3);
    }




    public static int tribonacciBottomUpTabulationDp(int n) {
        int[] res = new int[n+1];
        for(int i=1; i<=n; i++) {
            if (i==1 || i==2) res[i]=1;
            else res[i] = (res[i-1] + res[i-2] + res[i-3]);
        }
        return res[n];
    }



    public int tribonacciBottomUpTabulationDp2(int n) {
        if(n==0){
            return 0;
        }
        if(n==1 || n==2){
            return 1;
        }
        List<Integer> tribonacci = Arrays.asList(0, 1, 1);
        int indexToFill;
        for(int i = 3; i <= n; i++) {
            indexToFill = i%3;
            tribonacci.set(indexToFill ,tribonacci.stream().mapToInt(Integer::intValue).sum());
        }
        return tribonacci.get(n%3);
    }





    public static int tribonacciBottomUpNoMemoryDp(int n) {
        int t0=0, t1=1, t2=1;
        if(n==0) return t0;
        else if(n==1) return t1;
        else if(n==2) return t2;

        int tn = 0;
        for(int i=3; i<=n; i++) {
            tn = (t0 + t1 + t2);
            t0=t1;
            t1=t2;
            t2=tn;
        }
        return tn;
    }
}
