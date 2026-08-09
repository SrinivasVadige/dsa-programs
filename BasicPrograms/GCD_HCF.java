package BasicPrograms;

import java.util.ArrayList;
import java.util.List;


/**
* GCD = Greatest Common Divisor of any two integers is the largest number that divides both integers
* HCF = Highest Common Factor (same as GCD)
* in our schooling we used LCM to find the GCD or HCF because a*b = HCF(a,b) * LCM(a,b)
* @author Srinvas Vadige, srinivas.vadige@gmail.com
* @since 21 Sept 2024
*/
public class GCD_HCF {
    public static void main(String[] args) {
        int a = 9, b = 12;
        System.out.println("gcdOfLongApproach => " + gcdOfLongApproach(a, b));
        System.out.println("gcdOfUsingMinNumLoopExtraVar => " + gcdOfUsingMinNumLoopExtraVar(a, b));
        System.out.println("gcdOfUsingMinNumLoopNoExtraVar => " + gcdOfUsingMinNumLoopNoExtraVar(a, b));
        System.out.println("Euclidean Algorithm while loop => " + euclideanAlgorithmWhileLoop(a, b));
    }

    static int gcdOfLongApproach(int a, int b) {
        List<Integer> aFactors = new ArrayList<>();
        List<Integer> bFactors = new ArrayList<>();
        int gcd = 1;
        for (int i = 1; i <= a; i++) {
            if( a%i == 0) aFactors.add(i);
        }
        for (int i = 1; i <= b; i++) {
            if( b%i == 0) bFactors.add(i);
        }
        for(int i : aFactors){ // or loop from n-1 to 0
            if(bFactors.contains(i) && gcd < i) {
                gcd = i;
            }
        }
        return gcd;
    }


    static int gcdOfUsingMinNumLoopExtraVar(int a, int b) {
        int gcd = 1;
        for (int i = 2; i <= Math.min(a, b); i++) { // or loop from minN-1 to 0
            if (a%i==0 && b%i==0) gcd = i;
        }
        return gcd;
    }

    static int gcdOfUsingMinNumLoopNoExtraVar(int a, int b) {
        for (int i = Math.min(a, b); i >= 1; i--) {
            if (a%i==0 && b%i==0) return i;
        }
        return 1;
    }


/*
    The Euclidean Algorithm is a method for finding the greatest common divisor of two numbers.
    It operates on the principle that the GCD of two numbers remains the same
    even if the smaller number is subtracted from the larger number.

    To find the GCD of n1 and n2 where n1 > n2:

    Repeatedly subtract the smaller number from the larger number until one of them becomes 0.
    Once one of them becomes 0, the other number is the GCD of the original numbers.
    Eg, n1 = 20, n2 = 15:

    gcd(20, 15) = gcd(20-15, 15) = gcd(5, 15)

    gcd(5, 15) = gcd(15-5, 5) = gcd(10, 5)

    gcd(10, 5) = gcd(10-5, 5) = gcd(5, 5)

    gcd(5, 5) = gcd(5-5, 5) = gcd(0, 5)

    Hence, return 5 as the gcd.

    or
    largestNum = smallNum*multiplier + remainder   // multiplier is quotient
    => prevSmallNumOrNewLarge = prevRemainderOrNewSmall*newMultiplier + newRemainder
    => prevRemainder will become the new smallNum another approach

    that means lNum % sNum = remainder
    so this Remainder be be our new sNum and previous sNum be will be new lNum
    now loop until, we get Remainder as 0

    but here in our code, we just assign the Remainder to largeNum so, this will become new small num
    then the other number will automatically become the new largeNum without doing anything

    REMAINDERS
    a=30; b=25 => a=5; b=25; a=5; b=0... therefore GCF is 5
    a=20; b=30 => a=20; b=10; a=0; b=10... therefore GCF is 10
  */
    static int euclideanAlgorithmWhileLoop(int a, int b) {
        while (a>0 && b>0) {
            if(a>b) a = a%b; else b = b%a;
        }

        if(a == 0) return b;
        return a;
    }

    static int euclideanAlgorithmRecursion(int a, int b) {
        if (b == 0) return a;
        return euclideanAlgorithmRecursion(b, a % b);
    }

    static int euclideanAlgorithmRecursion2(int a, int b) {
        return b == 0 ? a : euclideanAlgorithmRecursion2(b, a % b);
    }
}
