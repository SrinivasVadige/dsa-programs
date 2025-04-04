package BasicPrograms;

/**
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 10 Oct 2024
 */
public class NumberOfStepsToReduceNumberToZero {
    public static void main(String[] args) {
        int num = 14;
        System.out.println(numberOfSteps(num));
    }
    public static int numberOfSteps(int num) {
        int step=0;
        while(num != 0) {
            System.out.println(num);
            if((num & 1)==1)
                num--;
            else
                num/=2;
            step++;
        }
        return step;
    }
}
