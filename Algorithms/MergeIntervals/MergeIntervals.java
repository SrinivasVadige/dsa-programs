package Algorithms.MergeIntervals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Stack;

/**
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 18 March 2025
 * @link https://leetcode.com/problems/merge-intervals/ (56. Merge Intervals)
 */
public class MergeIntervals {
    public static void main(String[] args) {
        int[][] intervals = {{1, 4}, {5, 6}};
        System.out.println("merge(intervals) => ") ;
        intervals = merge(intervals);
        for (int[] i : intervals) System.out.print(Arrays.toString(i));

        System.out.println("\n\nmerge(intervals) => ") ;
        intervals = new int[][]{{1, 4}, {5, 6}};
        intervals = mergeMyApproach(intervals);
        for (int[] i : intervals) System.out.print(Arrays.toString(i));
    }

    public static int[][] merge(int[][] intervals) {
        Arrays.sort(intervals, (a, b) -> Integer.compare(a[0], b[0]));
        int[][] res = new int[intervals.length][2];
        res[0] = intervals[0]; // set first arr
        int idx = 0; // res index
        for (int[] arr: intervals) { // or start from i=1
            int start = arr[0], end = arr[1]; // current start and end
            int lastEnd = res[idx][1]; // recent end in res

            if (start <= lastEnd) res[idx][1] = Math.max(lastEnd, end); // overlapping [1,5], [3,8]
            else res[++idx] = arr; // non-overlapping [1,5], [8,10]

        }
        return Arrays.copyOfRange(res, 0, idx+1);
    }

    public static int[][] merge2(int[][] intervals) {
        Arrays.sort(intervals, (a, b) -> Integer.compare(a[0], b[0]));
        int[][] res = new int[intervals.length][2];
        int idx = 0; // res index
        for (int i = 0; i < intervals.length; i++) {
            if (idx == 0 || intervals[i][0] > res[idx - 1][1]) {
                res[idx++] = intervals[i];
            } else {
                res[idx - 1][1] = Math.max(res[idx - 1][1], intervals[i][1]); // don't increment idx
            }
        }
        return Arrays.copyOfRange(res, 0, idx);
    }




    public static int[][] mergeMyApproach(int[][] intervals) {
        int n=intervals.length;
        Arrays.sort(intervals, (a, b) -> a[0] - b[0]);
        System.out.println();
        int startI = 0;
        List<List<Integer>> lst = new ArrayList<>();
        int l=intervals[0][0], r=intervals[0][1];
        while(startI<n) {
            l=intervals[startI][0]; r=intervals[startI][1];
            int rCopy=r;
            if (startI==0) startI++;
            int tempI = startI;
            for(int i=startI; i<n; i++) {
                if(l>=intervals[i][0] || intervals[i][0]<=r) {
                    r=Math.max(r,intervals[i][1]);
                    tempI=i+1;
                } else break;
            }
            lst.add(Arrays.asList(l,r));
            startI=(tempI==startI && r!=rCopy)? startI+1 : tempI;
        }

        int min = Integer.MAX_VALUE, max= Integer.MIN_VALUE;
        for(int[] x: intervals) {
            min = Math.min(min, x[0]);
            max = Math.max(max, x[1]);
        }
        if(lst.isEmpty() || lst.get(0).get(0)!=min || lst.get(lst.size()-1).get(1)!=max) return intervals;

        int[][] res=new int[lst.size()][2];
        for(int i=0; i<res.length; i++)
            res[i]=new int[]{lst.get(i).get(0), lst.get(i).get(1)};
        return res;
    }








    public int[][] mergeUsingPq(int[][] intervals) {
        PriorityQueue<int[]> pq = new PriorityQueue<>((a,b)->(a[0]-b[0]));

        int[]prev = {-1,-1};

        for(int[]x:intervals) pq.add(x);
        List<int[]> lst = new ArrayList<>();

        while(!pq.isEmpty()){
            int[] curr = pq.poll();
            if(curr[0]>prev[1]){
                lst.add(curr);
                prev = curr;
            }
            else{
                prev[1] = Math.max(prev[1], curr[1]);
            }
        }
        int[][]ans = new int[lst.size()][];
        int i=0;
        for(int[] x: lst) ans[i++] = x;
        return ans;
    }










    public int[][] mergeUsingStack(int[][] intervals) {
        Arrays.sort(intervals, (a, b) -> Integer.compare(a[0], b[0]));
        Stack<int[]> s = new Stack<>();
        s.push(intervals[0]);
        for(int i=1;i<intervals.length;i++)
        {
            int[] peek = s.peek();
            if(peek[0]<=intervals[i][0] && peek[1]>=intervals[i][0] && peek[1]<=intervals[i][1])
            {
                int[] rem = s.pop();
                rem[1] = intervals[i][1];
                s.push(rem);
            }else if(peek[0]<=intervals[i][0] && peek[1]>=intervals[i][1])
            {
                continue;
            }
            else
            {
                s.push(intervals[i]);
            }
        }
        Collections.reverse(s);
        int[][] ans = new int[s.size()][2];
        int idx = 0;
        while(s.size()!=0)
        {
            ans[idx] = s.pop();
            idx++;
        }

        return ans;
    }





    // sort array based on first num
    // go through array update end of interval for every merge
    // stop when end of interval smaller than start of next
    public int[][] merge3(int[][] intervals) {
        //sort lower first
        Arrays.sort(intervals, (a, b) -> Integer.compare(a[0], b[0]));

        int[] currentInterval = intervals[0];
        List<int[]> intervalResults = new ArrayList<>();

        for(int i = 1; i < intervals.length; i++) {
            if(currentInterval[1] >= intervals[i][0]) {
                if(currentInterval[1] < intervals[i][1]) {
                   currentInterval[1] = intervals[i][1];
                }
            } else {
                intervalResults.add(currentInterval);
                currentInterval = intervals[i];
            }
        }

        //add end
        intervalResults.add(currentInterval);

        int[][] results = new int[intervalResults.size()][2];
        intervalResults.toArray(results);
        return results;
    }






    public int[][] merge4(int[][] intervals) {
        int max = 0;
        for (int i = 0; i < intervals.length; i++) {
            max = Math.max(intervals[i][0], max);
        }

        int[] mp = new int[max + 1];
        for (int i = 0; i < intervals.length; i++) {
            int start = intervals[i][0];
            int end = intervals[i][1];
            mp[start] = Math.max(end + 1, mp[start]);
        }

        int r = 0;
        int have = -1;
        int intervalStart = -1;
        for (int i = 0; i < mp.length; i++) {
            if (mp[i] != 0) {
                if (intervalStart == -1) intervalStart = i;
                have = Math.max(mp[i] - 1, have);
            }
            if (have == i) {
                intervals[r++] = new int[] {intervalStart, have};
                have = -1;
                intervalStart = -1;
            }
        }

        if (intervalStart != -1) {
            intervals[r++] = new int[] {intervalStart, have};
        }
        if (intervals.length == r) {
            return intervals;
        }

        int[][] res = new int[r][];
        for (int i = 0; i < r; i++) {
            res[i] = intervals[i];
        }
        return res;
    }




    public int[][] merge5(int[][] intervals) {
        int min = Integer.MAX_VALUE;
		int max = Integer.MIN_VALUE;

		for (int i = 0; i < intervals.length; i++) {
			min = Math.min(min, intervals[i][0]);
			max = Math.max(max, intervals[i][0]);
		}

		int[] range = new int[max - min + 1];
		for (int i = 0; i < intervals.length; i++) {
			range[intervals[i][0] - min] = Math.max(intervals[i][1] - min, range[intervals[i][0] - min]); 
		}

		int start = 0, end = 0;
		LinkedList<int[]> result = new LinkedList<>();
		for (int i = 0; i < range.length; i++) {
			if (range[i] == 0) {
				continue;
			}
			if (i <= end) {
				end = Math.max(range[i], end);
			} else {
				result.add(new int[] {start + min, end + min});
				start = i;
				end = range[i];
			}
		}
		result.add(new int[] {start + min, end + min});
		return result.toArray(new int[result.size()][]);
    }




    public int[][] merge6(int[][] intervals) {
        quicksort(intervals, 0, intervals.length-1);

        List<int[]> list = new ArrayList<>();
        int[] current_interval =intervals[0];
        list.add(current_interval);

        for(int[] interval: intervals ) {
            int current_end = current_interval[1];
            int next_begin = interval[0];
            int next_end = interval[1];

            if(current_end >= next_begin) {
                current_interval[1] = Math.max(current_end, next_end);
            } else{
                current_interval = interval;
                list.add(current_interval);
            }
        }
        return list.toArray(new int[list.size()][]);
    }

    public static void quicksort(int[][] arr,int li,int ri) {

        if(li >= ri) {
            return;
        }
        int pivot = arr[ri][0];

        int lp = li;
        int rp = ri;

        while( lp < rp ) {
            while(arr[lp][0] <= pivot && lp < rp) {
                lp++;
            }
            while(arr[rp][0] >= pivot && lp < rp ) {
                rp--;
            }

            swap(arr, lp, rp);
        }
        swap(arr, lp, ri);
        quicksort(arr, li,lp-1);
        quicksort(arr, lp+1, ri);
    }

    public static void swap(int[][] arr, int lp, int rp) {
        int[] temp = arr[lp];
        arr[lp] = arr[rp];
        arr[rp] = temp;
    }







    public int[][] merge7(int[][] intervals) {
        Arrays.sort(intervals, (a, b) -> Integer.compare(a[0], b[0]));

        List<int[]> merged = new ArrayList<>();
        int[] prev = intervals[0];

        for (int i = 1; i < intervals.length; i++) {
            int[] interval = intervals[i];
            if (interval[0] <= prev[1]) {
                prev[1] = Math.max(prev[1], interval[1]);
            } else {
                merged.add(prev);
                prev = interval;
            }
        }

        merged.add(prev);

        return merged.toArray(new int[merged.size()][]);
    }






    public int[][] merge8(int[][] intervals) {
        Arrays.sort(intervals, (a,b) -> a[0]-b[0]);
        int start = intervals[0][0];
        int end = intervals[0][1];
        ArrayList<int[]> list = new ArrayList<int[]>();
        int index = 1;
        while(index < intervals.length){
            if(intervals[index][0] <= end){
                start = Math.min(start, intervals[index][0]);
                end = Math.max(end, intervals[index][1]);
            }
            else{
                list.add(new int[]{start, end});
                start = intervals[index][0];
                end = intervals[index][1];
            }
            index++;
        }
        list.add(new int[]{start, end});

        return list.toArray(new int[list.size()][]);
    }










    public int[][] merge9(int[][] intervals) {
        Arrays.sort(intervals, Comparator.comparingInt((int[]x)->x[0]));
        List<int[]> ls = new ArrayList<>();
        ls.add(new int[]{intervals[0][0],intervals[0][1]});
        for(int[]x:intervals){
            int[]prev = ls.get(ls.size()-1);
            if(prev[1]>=x[0]){
                if(prev[1]<x[1])prev[1]=x[1];
            }else{
                ls.add(x);
            }
        }
        int[][]ans = new int[ls.size()][2];
        for(int i=0;i<ans.length;i++)ans[i]= ls.get(i);
        return ans;
    }
}
