package BasicPrograms;

/**
* @author Srinvas Vadige, srinivas.vadige@gmail.com
* @since 21 Sept 2024
*/
public class PrimeNumber {
    public static void main(String[] args) {
        int n = 49;
        System.out.println("Brute force => isPrime: " + (isPrimeUsingBruteForce(n) ? "yes" : "no"));
        System.out.println("Sqrt Approach => isPrime: " + (isPrimeUsingSqrtLoop(n) ? "yes" : "no"));
        System.out.println("Optimized Trial Division => isPrime: " + (isPrimeUsingOptimizedTrialDivision(n)? "yes" : "no"));
        System.out.println("Sqrt Approach with NumOfFactors => isPrime: " + (isPrimeUsingSqrtLoopWithNumOfFactors(n) ? "yes" : "no"));
    }

    public static boolean isPrimeUsingBruteForce(int num) {
        if (num <= 1) return false; // Corner case as 1 is not prime and 0 & negatives are also not prime
        for (int i = 2; i < num; i++) // or i <= num/2
            if (num % i == 0)
                return false;
        return true;
    }


    /**
         Use i<Math.sqrt(num) instead of i<=num or i<=num/2
         ---> because the divisors of 36 number are [1, 2, 3, 4, 6, 9, 12, 18, 36]

         1*36
         2*18
         3*12
         4*9
         6*6
         9*4
         12*3
         18*2
         36*1

        this is repeated after 6*6 in reverse order

        Add the counterpart divisor if it's different from i
        => if (i != num / i) divisors.add(num / i);
     */
    public static boolean isPrimeUsingSqrtLoop(int n) {
        for (int i = 2; i <= Math.sqrt(n); i++) {
            if(n%i == 0) {
                return false;
            }
        }
        return true;
    }



    /**
     * Optimized Trial Division using square root and skipping evens
     * NOTE: i*i<=num means i<=Math.sqrt(num)
     */
    public static boolean isPrimeUsingOptimizedTrialDivision(int num) {
        if (num < 2) return false;
        if (num == 2) return true;
        if (num % 2 == 0) return false; // --> This will validate all even numbers of "num". All even numbers (except 2) are not prime
        for (int i = 3; i*i<=num; i += 2) { // check if num is divisible by odd numbers of i
            if (num % i == 0) return false;
        }
        return true;
    }




    public static boolean isPrimeUsingSqrtLoopWithNumOfFactors(int n) {
        if (n <= 1) return false; // Corner case as 1 is not prime and 0 & negatives are also not prime
        int numOfFactors = 0;
        for (int i = 1; i <= Math.sqrt(n); i++) {
            if (n % i == 0) {
                numOfFactors++;
                if (n/i != i) numOfFactors++; // for "perfect squre" 6*6 = 36 scenario i.e don't add 6 two times. The COUNTERPART or Reciprocal factor of i is n/i. Eg: 1 is 36. And skipping numOfFactors for 6*6 perfect square scenario i.e num != counterpart of num
            }
        }
        return numOfFactors == 2; // i.e number of factors is exactly 2
    }

}
