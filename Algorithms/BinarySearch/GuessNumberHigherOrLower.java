package Algorithms.BinarySearch;

/**
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 10 May 2025
 */
public class GuessNumberHigherOrLower {
    public static void main(String[] args) {
        int n = 6;
        System.out.println("guessNumber(n) => " + guessNumber(n));
    }

    public static int guessNumber(int n) {
        int low = 1;
        int high = n;
        while (low <= high) {
            int mid = low + (high - low) / 2;
            int guess = guess(mid);
            if (guess == 0) return mid;
            else if (guess == -1) high = mid - 1;
            else low = mid + 1;
        }
        return -1;
    }


    public int guessNumberMyApproach(int n) {
        long l = 1, r=n;

        while(l<=r) {
            long m = (l+r)/2; // or if we use this l + (r-l)/2 --- then we can avoid overflow the int range
            int gs = guess((int)m);
            if(gs==0) return (int)m;
            else if (gs==1) l=m+1;
            else r=m-1;
        }
        return -1;
    }





    public static int guess(int num) {
        int picked = 6;
        if (num > picked) return -1;
        else if (num < picked) return 1;
        else return 0;
    }
}
