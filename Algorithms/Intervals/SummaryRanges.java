package Algorithms.Intervals;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 27 September 2025
 * @link 228. Summary Ranges <a href="https://leetcode.com/problems/summary-ranges/">LeetCode Link</a>
 * @topics Array, Intervals
 */
public class SummaryRanges {
    public static void main(String[] args) {
        int[] nums = {0,1,2,4,5,7};
        System.out.println("summaryRanges => " + summaryRanges(nums));
    }


    public static List<String> summaryRanges(int[] nums) {
        int n = nums.length;
        List<String> ranges = new ArrayList<>();

        for (int i=0; i<n; i++) {
            StringBuilder sb = new StringBuilder();
            sb.append(nums[i]);
            int len = 1;

            while(i+1<n && nums[i]+1 == nums[i+1]) {
                i++;
                len++;
            }

            if(len > 1) {
                sb.append("->").append(nums[i]);
            }

            ranges.add(sb.toString());
        }
        return ranges;
    }





    public static List<String> summaryRanges2(int[] nums) {
        int n = nums.length;
        List<String> ranges = new ArrayList<>();

        for (int i=0; i<n; i++) {

            int start = nums[i];
            while(i+1<n && nums[i]+1 == nums[i+1]) {
                i++;
            }
            int end = nums[i];

            if(start == end) ranges.add(String.valueOf(start));
            else ranges.add(String.format("%s->%s", start, end));
        }
        return ranges;
    }
}
