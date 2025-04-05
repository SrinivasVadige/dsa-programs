package Algorithms.IntegerArray;

import java.util.Arrays;

/**
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 05 April 2025
 */
public class CanPlaceFlowers {
    public static void main(String[] args) {
        int[] flowerbed = {1,0,0,0,1};
        int n = 1;
        System.out.println(canPlaceFlowers(flowerbed, n));
    }

    public static boolean canPlaceFlowers(int[] flowerbed, int n) {
        int count = 0;
        int len = flowerbed.length;
        for (int i = 0; i < len; i++) {
            if (flowerbed[i] == 0
            &&
            (i == 0 || flowerbed[i - 1] == 0) // before i is 0
            &&
            (i == len - 1 || flowerbed[i + 1] == 0) // after i is 0
            ) {
                flowerbed[i] = 1; // Plant a flower here
                count++;
            }
        }
        return count >= n;
    }





    public boolean canPlaceFlowers2(int[] flowerbed, int n) {
        int l=flowerbed.length;
        for(int i=0;i<l;i++){
            if(flowerbed[i] == 0){
                boolean prev = (i==0) || (flowerbed[i-1] == 0);
                boolean next = (i == l-1) || (flowerbed[i+1] == 0);
                if(prev && next){
                    flowerbed[i] = 1;
                    n--;
                    if(n<=0) return true;
                }
            }
        }
        return n<=0;
    }




    public boolean canPlaceFlowers3(int[] flowerbed, int n) {
        int len = flowerbed.length;
        int i = 0;
        while(i < len && n > 0) {
            if(flowerbed[i] == 1) i += 2; // skip the next 2 beds
            else if(i == len - 1 || flowerbed[i + 1] == 0) {
                n--;
                i += 2;
            }
            else i += 3;
        }
        return n <= 0;
    }

    /**
     *
     * 0 0 0 1 0 0 1 0 0 0 0 1 0 0 0 0 0 1 0
     *
     * 1 0 0 0 0 1    0 0 0 0 1
     * 3 0s -> 1
     * 4 0s -> 1
     * 5 0s -> 2
     * 6 0s -> 2
     * 7 0s -> 3
     * 8 0s -> 3
     * even (n-1)/2 odd n/2
     *
     * so, instead of checking edge cases like i=0 && i=len-1, we can add 0s to the start and end of the array.
     */
    public boolean canPlaceFlowersMyApproach(int[] flowerbed, int n) {
        int[] f =  new int[flowerbed.length + 2];
        for (int i=0; i<flowerbed.length; i++) f[i+1]=flowerbed[i];
        int c=0;
        for (int i=0; i<f.length; i++) {
            int zeros = 0;
            while(i<f.length && f[i]==0){
                zeros++;
                i++;
            }
            if((zeros & 1)==1) c += zeros/2;
            else c += (zeros-1)/2;
        }
        return c >= n;
    }






    /**
        NOT WORKING

        PATTERNS:
        ---------
        1) 1 flower -> B1 | ADD1 - B3, ADD2 - B5
        2) 2 flowers -> B3 | ADD1 - B5, ADD2 -  B7
        3) 3 flowers -> B5
        4) 4 flowers -> B7
        5) 5 flowers -> B9
        6) 6 flowers -> B11
        7) 7 flowers -> B13
        8) 8 flowers -> B15

        n flowers -> min beds n+2

        but it'll fail scenarios like [1,0,1,0,0,1,0] && n=1
     */
    public boolean canPlaceFlowersMyOldApproach(int[] flowerbed, int n) {
        int len = flowerbed.length;
        int ones = (int)Arrays.stream(flowerbed).filter(f->f==1).count() + n;
        if (len == 1) return ones<=1;
        if (flowerbed[0]==0 && flowerbed[1]==1) return (ones*2-1) <= len-1;
        else return (ones*2-1) <= len;
    }
}
