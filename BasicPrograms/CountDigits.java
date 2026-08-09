package BasicPrograms;

/**
* @author Srinvas Vadige, srinivas.vadige@gmail.com
* @since 21 Sept 2024
*/
public class CountDigits {

    public static void main(String[] args) {

        int n = 1322;
        if(n < 0) n = Math.abs(n);
        System.out.printf("Brute force approach => %s ", bruteForce(n));
        System.out.printf("\nLogarithmic base 10 approach => %s ", logarithmicBase10(n));
        System.out.printf("\nString length approach => %s ", stringLength(n));

    }

    static int bruteForce(int n){
        if(n == 0) return 1;

        int count=0;
        while (n>0) {
            count++;
            n /= 10;  // as number is int even the fractions will floor.
        }
        return count;
    }


    // log₁₀ 1000 = 3, i.e 1000 =10^3 where this 10 is logarithmic base i.e count digits is 4.
    static int logarithmicBase10(int n){
        if(n == 0) return 1;
        // Math.log accepts double (we can also provide int as it implicit casts) and returns double.
        return (int) Math.log10(n) + 1;  // i.e (int)decimal or Math.round(100.9 * 10)/10 but we cannot use Math.floor(decimal) as it again returns double. And "decimal % 1" also don't work
        // so, we need to "+ 1" cause to ensure that the count is correct even for numbers that are powers of 10
    }

    static int stringLength(int n) {
        return String.valueOf(n).length();
    }


}
