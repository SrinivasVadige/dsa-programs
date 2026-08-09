package Algorithms.GreedyAlgorithms;

import java.util.Arrays;

/**
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 22 June 2025
 * @link 135. Candy <a href="https://leetcode.com/problems/candy/">Leetcode link</a>
 * @topics Array, Greedy
 * @description: You are giving candies to these children subjected to the following requirements:
 * 1. Each child must have at least one candy.
 * 2. Children with a higher rating get more candies than their neighbors.
 */
public class Candy {
    public static void main(String[] args) {
        int[] ratings = {1, 0, 2};
        System.out.println(candy(ratings)); // Output: 5
    }


    /**
     * @TimeComplexity O(n)
     * @SpaceComplexity O(n)
     *
        Here we have 3 cases:
        1. Left rating is equal to right rating     --- same [2, 2, 2]
        2. Left rating is less than right rating    --- increasing order [1, 2, 3]
        3. Right rating is less than left rating    --- decreasing order [3, 2, 1]

        Example 1:
        Given
        ratings = [5, 4, 3, 5, 6, 4, 3, 2, 1]
        initialize
        candies = [1, 1, 1, 1, 1, 1, 1, 1, 1]

        left to right pass
                  [5, 4, 3, 5, 6, 4, 3, 2, 1]
        candies = [1, 1, 1, 2, 3, 1, 1, 1, 1]
        right to left pass
                  [5, 4, 3, 5, 6, 4, 3, 2, 1]
        candies = [3, 2, 1, 2, 4, 4, 3, 2, 1]


        Example 2:
        Given
        ratings = [5, 4, 3, 5, 6, 7, 2, 1]
        initialize
        candies = [1, 1, 1, 1, 1, 1, 1, 1]

        left to right pass
                  [5, 4, 3, 5, 6, 7, 2, 1]
        candies = [1, 1, 1, 2, 3, 4, 1, 1]
        right to left pass
                  [5, 4, 3, 5, 6, 7, 2, 1]
        candies = [1, 1, 1, 2, 3, 4, 2, 1] ---> here at 7 rating, use Math.max(candies[i], candies[i+1]+1)

     */
    public static int candy(int[] ratings) {
        int n = ratings.length;

        int[] candies = new int[n];
        Arrays.fill(candies, 1);

        // Left to right pass
        for(int i=0; i<n; i++) {
            int leftRating = i==0? ratings[i] : ratings[i-1];
            int leftCandy = i==0? 1 : candies[i-1];

            if (leftRating < ratings[i]) { // is left smaller -- increasing?
                candies[i] = leftCandy+1;
            }
        }

        // Right to left pass
        for(int i=n-1; i>=0; i--) {
            int rightRating = i==n-1? ratings[i] : ratings[i+1];
            int rightCandy = i==n-1? 1 : candies[i+1];

            if (ratings[i] > rightRating) { // is right smaller -- decreasing?
                candies[i] = Math.max(candies[i], rightCandy + 1);;
            }
        }

        // Sum up the candies
        return Arrays.stream(candies).sum();
    }


    /**
     * @TimeComplexity O(n)
     * @SpaceComplexity O(n)
     *
     * It is same as above {@link #candy(int[])} but we can skip i==0 and i==n-1
     */
    public static int candy2(int[] ratings) {
        int n = ratings.length;
        int[] candies = new int[n];
        Arrays.fill(candies, 1);


        // Left to right pass
        for (int i = 1; i < n; i++) {
            if (ratings[i] > ratings[i - 1]) {
                candies[i] = candies[i - 1] + 1;
            }
        }

        int sum = candies[n - 1];
        // Right to left pass
        for (int i = n - 2; i >= 0; i--) {
            if (ratings[i] > ratings[i + 1]) {
                candies[i] = Math.max(candies[i], candies[i + 1] + 1);
            }
            // we can also sum up the candies
            sum += candies[i];
        }

        // Sum up the candies
        return Arrays.stream(candies).sum(); // or return sum;
    }


    // using n^2 approach
    public static int candy3(int[] ratings) {
        int n = ratings.length;
        int[] candies = new int[n];
        Arrays.fill(candies, 1);

        boolean changed = true;
        while (changed) {
            changed = false;
            for (int i = 0; i < n; i++) {
                if (i > 0 && ratings[i] > ratings[i - 1] && candies[i] <= candies[i - 1]) {
                    candies[i] = candies[i - 1] + 1;
                    changed = true;
                }
                if (i < n - 1 && ratings[i] > ratings[i + 1] && candies[i] <= candies[i + 1]) {
                    candies[i] = candies[i + 1] + 1;
                    changed = true;
                }
            }
        }
        return Arrays.stream(candies).sum();
    }










    /**
     * NOT WORKING


        1 [1, 0, 2] 2  --- ratings

          [2, 1, 2]    --- candies = 5




        1 [1, 2, 2] 2  --- ratings

          [1, 2, 1]    --- candies = 4




        1 [1, 0, 2, 9, 2, 1, 5, 6, 2] 2  --- ratings

          [1, 1, 1, 1, 1, 1, 1, 1, 1]    --- candies i=0
          [2, 1, 1, 1, 1, 1, 1, 1, 1]    --- candies i=1
          [2, 1, 2, 1, 1, 1, 1, 1, 1]    --- candies i=2
          [2, 1, 2, 3, 1, 1, 1, 1, 1]    --- candies i=3
          [2, 1, 2, 3, 1, 1, 1, 1, 1]    --- candies i=4
          [2, 1, 2, 3, 2, 1, 2, 3, 1]    --- candies = 17





          1 [1, 2, 9, 9, 9, 2, 1] 1

            [1, 1, 1, 1, 1, 1, 1] i=0
            [1, 2, 1, 1, 1, 1, 1] i=1
            [1, 2, 3, 1, 2, 2, 1] i=2

     */
    public int candyMyApproachNotWorking(int[] ratings) {
        int n = ratings.length;
        int[] candies = new int[n];
        Arrays.fill(candies, 1);

        for(int i=0; i<n; i++) {
            int curr = ratings[i];
            int left = i==0? ratings[i] : ratings[i-1];
            int right = i==n-1? ratings[i] : ratings[i+1];

            if (curr <= left && curr <= right) continue;

            int leftCandy = i==0? 1 : candies[i-1];
            int rightCandy = i==n-1? 1 : candies[i+1];
            // candies[i] = ((left<curr && curr>=right)? Math.max(leftCandy, rightCandy) : Math.min(leftCandy, rightCandy)) + 1;
            candies[i] = ((left<curr)? leftCandy : rightCandy) + 1;
        }

        System.out.println(Arrays.toString(candies));



        for(int i=n-1; i>=0; i--) {
            int curr = ratings[i];
            int left = i==0? ratings[i] : ratings[i-1];
            int right = i==n-1? ratings[i] : ratings[i+1];

            int currCandy = candies[i];
            int leftCandy = i==0? 1 : candies[i-1];
            int rightCandy = i==n-1? 1 : candies[i+1];

            if(curr>right && currCandy<=rightCandy) candies[i] += rightCandy;
        }
        System.out.println(Arrays.toString(candies));


        return Arrays.stream(candies).sum();
    }
}
