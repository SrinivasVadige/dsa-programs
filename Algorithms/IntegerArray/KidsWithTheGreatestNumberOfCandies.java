package Algorithms.IntegerArray;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 05 April 2025
 */
public class KidsWithTheGreatestNumberOfCandies {
    public static void main(String[] args) {
        int[] candies = {2,3,5,1,3};
        int extraCandies = 3;
        System.out.println(kidsWithCandies(candies, extraCandies));
    }

    public static List<Boolean> kidsWithCandies(int[] candies, int extraCandies) {
        int max = 0;
        for (int candy : candies) max = Math.max(max, candy); // or for (int c: candies) if(max < c) max = c; --> for more faster
        List<Boolean> result = new ArrayList<>(candies.length);
        for (int candy : candies) result.add(candy + extraCandies >= max);
        return result;
    }

    public List<Boolean> kidsWithCandiesUsingStreams(int[] candies, int extraCandies) {
        int max = Arrays.stream(candies).max().getAsInt();
        return Arrays.stream(candies)
            .boxed()
            .map(candy -> candy + extraCandies >= max)
            .collect(Collectors.toList());
    }
}
