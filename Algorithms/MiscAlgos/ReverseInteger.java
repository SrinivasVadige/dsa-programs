package Algorithms.MiscAlgos;

/**
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 26 July 2025
 * @link 7. Reverse Integer <a href="https://leetcode.com/problems/reverse-integer/">LeetCode link</a>
 * @topics Math
 * @companies Google, Amazon, Meta, Bloomberg, Microsoft, Oracle, Accenture, tcs, Apple, Adobe, Uber, Wipro, Yahoo, Infosys, Nvidia, Yandex, Deloitte, IBM, Tech Mahindra
 * @see BasicPrograms.ReverseNumber;
 */
public class ReverseInteger {
    public static void main(String[] args) {
        int x = Integer.MIN_VALUE;
        System.out.println("reverse using int sum: " + reverseUsingIntSum(x));
        System.out.println("reverse using long sum: " + reverseUsingLongSum(x));
        System.out.println("reverse using StringBuilder & parseLong: " + reverseUsingSbParseLong(x));
        System.out.println("reverse using StringBuilder & parseInt & try-catch : " +  reverseUsingSbParseIntAndTryCatch(x));
    }

    public static int reverseUsingIntSum(int x) {
        int reverse = 0;
        final int MIN = Integer.MIN_VALUE;
        final int MAX = Integer.MAX_VALUE;
        while (x != 0) {
            int lastDigit = x % 10; // NOTE: -11 % 10 = -1
            x /= 10;


            if (reverse > MAX/10 || reverse == MAX/10 && lastDigit > MAX%10) return 0; // lastDigit>7  or (lastDigit==8 || lastDigit==9)
            if (reverse < MIN/10 || reverse == MIN/10 && lastDigit == MIN%10) return 0; // lastDigit<-8  or  lastDigit==-9
            /*
            // or
            if ((long)reverse*10 > MAX || (long)reverse*10 + lastDigit > MAX) return 0;
            if ((long)reverse*10 < MIN || (long)reverse*10 + lastDigit < MIN) return 0;
            */

            reverse = reverse*10 + lastDigit;
        }
        return reverse;
    }



    public static int reverseUsingLongSum(int x) {
        long sum = 0;

        while( x != 0 ) {
            int lastDigit = x % 10;
            sum = sum*10 + lastDigit;
            x /= 10;
        }

        if (sum < Integer.MIN_VALUE || sum > Integer.MAX_VALUE) {
            return 0;
        }

        return (int) sum;
    }


    public static int reverseUsingSbParseLong(int x) {
        long l = Long.parseLong(new StringBuilder().append(Math.abs((long)x)).reverse().toString());
        if(l > Integer.MAX_VALUE) {
            return 0;
        }
        return (int) l * (x<0 ? -1 : 1);
    }


    public static int reverseUsingSbParseIntAndTryCatch(int x) {
        try{
            return Integer.parseInt(new StringBuilder().append(Math.abs(x)).reverse().toString()) * (x<0 ? -1 : 1);
        } catch(Exception ex) {
            return 0;
        }
    }
}
