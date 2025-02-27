package Algorithms.GreedyAlgorithms;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 27 Feb 2025
 */
public class BestTimeToBuyAndSellStock {
    public static void main(String[] args) {
        int[] prices = {7,1,5,3,6,4};
        System.out.println("maxProfit(prices) => " + maxProfit(prices));
    }

    public static int maxProfit(int[] prices) {
        int maxProfit = 0, minPrice = Integer.MAX_VALUE;
        for (int i = 0; i < prices.length; i++) {
            minPrice = Math.min(minPrice, prices[i]);
            maxProfit = Math.max(maxProfit, prices[i] - minPrice);
        }
        return maxProfit;
    }

    public int maxProfitUsingTwoPointers(int[] prices) {
        int maxProfit = 0, l = 0, r = l+1;
        while(r<prices.length){
            if(prices[l] > prices[r]){
                l = r;
                r++;
            }
            else{
                maxProfit = Math.max(maxProfit, prices[r] - prices[l]);
                r++;
                // int tempProfit = prices[r] - prices[l];
                // if(tempProfit > maxProfit)
                //     maxProfit = tempProfit;
            }
        }
        return maxProfit;
    }


    // Working but all test cases are not passing
    /**
        THOUGHTS:
        ---------
        1) Need max diff at any point of day
        2) So, calculate at each i --> brute force n^2 => 10^10 which is not good
        3) or maintain the currMax
        3) Or use dp[] to maintain the max num
     */
    public int maxProfitMyApproach(int[] prices) {

        Map<Integer, List<Integer>> map = new HashMap<>();
        for (int i=0; i<prices.length; i++) {
            List<Integer> l = map.getOrDefault(prices[i], new ArrayList<>());
            l.add(i);
            map.put(prices[i], l);
        }

        List<Integer> lst = new ArrayList<>(map.keySet()); // or get sorted keys in reverse order

        int diff = 0;
        for (int p: prices) {
            // System.out.println(map);
            System.out.println(lst);

            boolean isPMax = p == lst.get(lst.size()-1);
            map.get(p).remove(0);
            if (map.get(p).size() == 0 ) {
                map.remove(p);
                lst=new ArrayList<>(map.keySet());
            }

            if (isPMax) continue;

            diff = Math.max(diff, lst.get(lst.size()-1)-p);// lst.get(n-1) is the max num


            System.out.println("p: " + p + ", diff: "+ diff + ", max: " + lst.get(lst.size()-1) + "\n");
        }

        return diff;
    }
}
