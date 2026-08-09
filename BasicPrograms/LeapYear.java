package BasicPrograms;

// import java.util.Scanner;

/**
* @author Srinvas Vadige, srinivas.vadige@gmail.com
* @since 21 Sept 2024
*/
public class LeapYear {

    public static void main(String[] args) {

        // Scanner sc=new Scanner(System.in);
        // System.out.println("Enter a Year");
        // int year = sc.nextInt();
        // or
        int year =  2012;

        // if year divisible by % 400 then it leap year => NO NEED TO CHECK ANYTHING
        // and if year % 100 then it is not a leap year (LEAP YEAR SHOULD NOT BE DIVISIBLE BY 100)
        // i.e the year should not be a century year except it is divisible by 400
        // and if year is not % 4 then it is not a leap year (LEAP YEAR MUST DIVISIBLE BY 4)
        // therefore CHECK %4 AND %100 or %400 . Here %400 is not optional. Eg: 2000
        if( (year%4 == 0 && year%100 != 0) || year%400 == 0 )
         System.out.println("Leap year");
        else System.out.println("Not a leap year");
    }

}
