package BasicPrograms;
/**
An Armstrong number is a number that is equal to the sum of its own digits each raised to the power of the number of digits.
* @author Srinvas Vadige, srinivas.vadige@gmail.com
* @since 21 Sept 2024
 */
public class ArmstrongNumber {
    public static void main(String[] args) {
        int n = 9474;
        System.out.println(isArmstrong(n)? "yes" : "no");
    }

    /**
     *
     * @param n
     * @return
    */
    static boolean isArmstrong(int n) {
        int sum = 0;
        int orgNum = n;
        int noOfDigits = (int) Math.log10(n) + 1; // or String.valueOf(num).length();
        while (n>0) {
            int lastDigit = n%10; // modulus returns remainder
            n = n/10; // division returns quotient
            sum += (int) Math.pow(lastDigit, noOfDigits); // in java ^ is bitwise XOR operator and ** don't work as power like js
        }
        return sum == orgNum;
    }
}
