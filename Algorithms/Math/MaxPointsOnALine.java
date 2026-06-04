package Algorithms.Math;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 03 June 2026
 * @link 149. Max Points on a Line <a href="https://leetcode.com/problems/max-points-on-a-line/"> LeetCode link</a>
 * @topics Math, Hash Table, Array, Geometry
 * @companies Amazon(5), LinkedIn(4), Google(3), Meta(12), Waymo(11), Apple(4), Cisco(4), Microsoft(3), Bloomberg(3), Zoho(2), Citadel(2), Nvidia(2), X(2)
 */
public class MaxPointsOnALine {
    public static void main(String[] args) {
        int[][] points = {{1, 1}, {2, 2}, {3, 3}};
        System.out.println("maxPoints1 => " + maxPoints1(points));
        System.out.println("maxPoints2 => " + maxPoints2(points));
        System.out.println("maxPoints3 => " + maxPoints3(points));
    }

    /**
     * @TimeComplexity O(n^2)
     * @SpaceComplexity O(n)


     [[3,2],[4,1],[2,3],[1,4]]
        1     3     1     3

        slope = (y2-y1)/(x2-x1) = dy/dx


    */
    public static int maxPoints1(int[][] points) {
        int max = 0;
        Map<Double, Integer> slopeFreqs;

        for (int i=0; i<points.length; i++) {
            slopeFreqs = new HashMap<>();
            for (int j=i+1; j<points.length; j++) {
                double dy = points[j][1] - points[i][1];
                double dx = points[j][0] - points[i][0];

                double slope;
                if (dx == 0) slope = Double.POSITIVE_INFINITY;
                else if (dy == 0) slope = 0; // in double 0/-5 = -0 but not 0
                else slope = dy/dx;
                slopeFreqs.merge(slope, 1, Integer::sum);
            }

            if (!slopeFreqs.isEmpty()) max = Math.max(max, Collections.max(slopeFreqs.values()));
        }

        return max+1;
    }






    /**




           |\
           | \
       opp |  \ hypotenuse
           |   \
           |____\ θ
             adj


     sin(θ) = opp/hyp
     cos(θ) = adj/hyp
     tan(θ) = opp/adj

     Example:
     Math.toDegrees(Math.atan(3.0/4.0)) = 36.87°

     Math.sin(Math.toRadians(30)) = 0.5
     sin(30°) = 1/2

     Math.asin(0.5) = 0.5236 radians
     Math.toDegrees(Math.asin(0.5)) = 30°

     Normal trig functions: Angle → Ratio
     sin(angle)
     cos(angle)
     tan(angle)

     Normal trig functions: Ratio → Angle
     asin(ratio)
     acos(ratio)
     atan(ratio)

     Math.asin2, acos2, atan2 -> we use the dy, dx as two params instead of dy/dx single param in Math.tan


     * @TimeComplexity O(n^2)
     * @SpaceComplexity O(n)
     */
    public static int maxPoints2(int[][] points) {
        int n = points.length;
        if (n == 1) return 1;
        int result = 2;

        for (int i = 0; i < n; i++) {
            Map<Double, Integer> counter = new HashMap<>();
            for (int j = 0; j < n; j++) {
                if (j != i) {
                    // Math.atan2(dy, dx) -> Java method that returns the angle (in radians) between the positive x-axis and the point (x, y).
                    counter.merge(Math.atan2(points[j][1] - points[i][1], points[j][0] - points[i][0]), 1, Integer::sum);
                }
            }
            result = Math.max(result, Collections.max(counter.values()) + 1);
        }
        return result;
    }





    public static int maxPoints3(int[][] points) {
        if (points.length <= 2) return points.length;

        int max = 0;

        for (int i = 0; i < points.length; i++) {
            Map<String, Integer> slopeFreqs = new HashMap<>();

            for (int j = i + 1; j < points.length; j++) {
                int dy = points[j][1] - points[i][1];
                int dx = points[j][0] - points[i][0];

                String key;

                if (dx == 0) key = "1/0";
                else if (dy == 0) key = "0/1";
                else {
                    int g = gcd(Math.abs(dy), Math.abs(dx));

                    dy /= g;
                    dx /= g;

                    if (dx < 0) {
                        dx = -dx;
                        dy = -dy;
                    }

                    key = dy + "/" + dx;
                }

                slopeFreqs.merge(key, 1, Integer::sum);
                max = Math.max(max, slopeFreqs.get(key));
            }
        }

        return max + 1;
    }

    private static int gcd(int a, int b) {
        return b == 0 ? a : gcd(b, a % b);
    }
}
