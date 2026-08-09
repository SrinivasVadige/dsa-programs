package Algorithms.BinarySearch;

import java.util.Arrays;

/**
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 10 May 2025
 */
public class KokoEatingBananas {
    public static void main(String[] args) {
        int[] piles = {3,6,7,11};
        int h = 8;
        System.out.println("minEatingSpeed(piles, h) => " + minEatingSpeed(piles, h));
    }


    /**
        OBS:
        ---
        1) k is speed = bananas/1hour  == b ---> so basically we need to find min num of bananas per 1 hour koko has to eat to finish all
        2) index is not an hour
        3) h >= piles.length
        4) Koko can eat all bananas <= h hours

        [3,6,7,11] --> total = 27 bananas & 8 hours

        k=1b/1h
                      3 pile                                          6 pile                                      7 pile                   11 pile
        (in 3b pile = 1b/1h + 1b/1h + 1b/1h) -- 3 hours   +  (6bs = 1b/1h + 1b/1h + 1b/1h ...) --- 6hours +  Math.ciel(7/1)= 7 hours   + c(11/1) 11 hours
        TOTAL =  3h + 6h + 7h + 11h = 27hours needed if koko eats 1banana/1hour speed -  27hrs > 8 hrs = Not enough time

        k=2b/1h
                      3                                                       6                         7                           11
        (in 3bs pile = 2b/1h + 1b/1h) -- 2 hours   +  (6bs = 2b/1h + 2b/1h + 2b/1h ...) --- 3hours +  Math.ciel(7/2) 4 hours   +  c(11/2)= 6 hours
        TOTAL =  2h + 3h + 4h + 6h = 15hours needed if koko eats 1banana/1hour speed - 15 hrs > 8 hrs = Not enough time

        k=3b/1h
                      3                             6                                7                   11
        (in 3bs pile = 3b/1h) -- 1 hours   +  (6bs = 3b/1h + 3b/1h) --- 2hours + c(7/3) 3 hours   +  (11/3) 4 hours
        TOTAL =  1h + 2h + 3h + 4h = 10hours needed if koko eats 1banana/1hour speed - 10hrs > 8 hrs = Not enough time

        4b/1h
                      3                             6                                7                   11
        (in 3bs pile = 3b/1h) -- 1 hours   +  (6bs = 4b/1h + 2b/1h) --- 2hours + c(7/4) 2 hours   +  (11/4) 3 hours
        TOTAL =  1h + 2h + 2h + 3h = 8hours needed if koko eats 1banana/1hour speed - 8 hrs 8hrs = GOOD

        k = 5,
        3/5 + 6/5 + 7/5 + 11/5 = 1 + 2 + 2 + 3 = 8hrs = GOOD, but not the minimum k

        k = 6,
        3/6 + 6/6 + 7/6 + 11/6 = 1 + 1 + 2 + 2 = 4hrs = GOOD, but not the minimum k




        APPROACHES:
        -----------
        1) Linear --> l=0, r=Math.max(nums
        2) BinarySearch --> l=0, r=max, m=l+(r-l)/2 ----> trav up to to prev of GOOD and return prev+1
        3)
     *
     * @TimeComplexity O(n) + O(log maxNum), where maxNum is the max num in nums
     * @SpaceComplexity O(1)
     */
    public static int minEatingSpeed(int[] piles, int h) {
        int low = 1, high = 0;
        for (int pile : piles) high = Math.max(high, pile); // O(n)

        while (low < high) {
            int mid = low + (high - low) / 2; // k
            if (canEatAllBananas(piles, mid, h)) {
                high = mid; // not mid-1, cause in while loop it's while(low<high), we usually have while(l<=r). And we need this valid high value
            } else {
                low = mid + 1;
            }
        }
        return low;
    }

    public static boolean canEatAllBananas(int[] piles, int speed, int h) { // can eat in 'h' hours?, k==speed
        int hours = 0;
        for (int pile : piles) {
            hours += (pile + speed-1) / speed; // or hours += Math.ceil((double)pile / speed); // Calculate ceiling of pile / speed --- note that "+ speed-1" is for ceiling
        }
        return hours <= h; // Return true if hours required is within the limit
    }



    public int minEatingMyApproach(int[] piles, int h) {
        int max = Arrays.stream(piles).max().getAsInt();
        int l=1, r=max, m;
        while(l<=r) {
            m = l+(r-l)/2;
            long kHours = calculateHoursUsingK(piles, m, h);
            if(h >= kHours) r=m-1; // if kHours is smaller than given h them, it or it's prev value (smaller) value must be the answer when l>r
            else l=m+1;


            // NOTE: this if,if else, else block scenario won't work
            // if(h == kHours) return m;
            // else if(h > kHours) r=m-1;
            // else l=m+1;
        }
        return l;
    }
    private long calculateHoursUsingK(int[] piles, int k, int h) {
        long sum = 0;
        for(int bananas: piles) {
            sum += Math.ceil(bananas/(double)k); // or sum += (bananas + k-1)/k;
            if(sum > h) return sum;
        }
        return sum;
    }







    public int minEatingSpeed2(int[] piles, int h) {
        int max = Arrays.stream(piles).max().getAsInt();
        int l=1, r=max, res=max;

        while(l<=r){
            int mid=l+(r-l)/2;
            int hours=0;
            for(int pile: piles){
                hours+=Math.ceil((double)pile/mid);
            }
            if(hours<=h){
                res=Math.min(res, mid); // or res=mid
                r=mid-1;
            } else {
                l=mid+1;
            }
        }
        return res;
    }




    public int minEatingSpeed3(int[] piles, int h) {
        int max=piles[0];
        for(int i=0; i<piles.length;i++){
            if(piles[i]>max){
                max=piles[i];
            }
        }
        int start=1;
        int end= max;

        while(start<=end){
            int mid= start + (end- start)/2;
            int k= mid;
            int sum=0;
            for(int pile: piles){
               sum+= Math.ceil((double)pile/k);
            }

            if(sum>h){
                start= mid+1;

            } else {
                end=mid-1;

            }
        }
        return start;
    }





    // Working, but TLE
    public int minEatingSpeedUsingLinearTLE(int[] piles, int h) {
        int max = Arrays.stream(piles).max().getAsInt();
        for(int k=1; k<max; k++) {
            if(calculateHoursUsingK(piles, k, h) <= h) return k;
        }
        return max;
    }
}
