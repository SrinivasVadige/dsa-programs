package Algorithms.HeapAlgos;

import java.util.*;
import java.util.stream.IntStream;

/**
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 20 April 2026
 * @link 502. IPO <a href="https://leetcode.com/problems/ipo/">LeetCode Link</a>
 * @topics Principal, Array, Greedy, Sorting, Heap(PriorityQueue)
 * @companies Amazon(3), Uber(3), Bloomberg(2), Microsoft(8), Google(5), Meta(4), Zeta Global(3), PhonePe(2), Innovaccer(2), Stackline(2), Gameskraft(2)
 */
public class IPO {
    public static void main(String[] args) {
        int[] capital = {1, 2, 3};
        int[] profits = {6, 9, 8};
        int k = 2, w = 1;
        System.out.println("findMaximizedCapital TLE => " + findMaximizedCapitalTLE1(k, w, profits, capital));
        System.out.println("findMaximizedCapital => " + findMaximizedCapital1(k, w, profits, capital));
        System.out.println("findMaximizedCapital2 => " + findMaximizedCapital2(k, w, profits, capital));
    }


    /**
     * @TimeComplexity O(n^2)
     * @SpaceComplexity O(n)
     */
    public static int findMaximizedCapitalTLE1(int k, int w, int[] profits, int[] capital) {
        int n = profits.length;
        k = Math.min(k, n);
        Set<Integer> invested = new HashSet<>();

        int[][] projects = new int[n][2];
        for (int i = 0; i < n; i++) {
            projects[i][0] = profits[i];
            projects[i][1] = capital[i];
        }
        // Arrays.sort(projects, (a,b)-> a[0]==b[0]? b[1]-a[1] : b[0]-a[0]);
        // Arrays.sort(projects, Comparator.comparingInt(a -> a[0]));


        while (invested.size() < k) {
            int bestIndex = -1, bestProfit = -1;

            for (int i = 0; i < n; i++) {
                if (invested.contains(i) || projects[i][1] > w) continue;

                if (projects[i][0] > bestProfit) {
                    bestProfit = projects[i][0];
                    bestIndex = i;
                }
            }

            if (bestIndex == -1) break;

            w += projects[bestIndex][0];
            invested.add(bestIndex);
        }

        return w;
    }








    /**
     * @TimeComplexity O(nlogn + klogn) = O(nlogn)
     * @SpaceComplexity O(n)
     */
    public static int findMaximizedCapital1(int k, int w, int[] profits, int[] capital) {
        int n = profits.length;

        int[][] projects = new int[n][2];
        for (int i = 0; i < n; i++) {
            projects[i][0] = capital[i];
            projects[i][1] = profits[i];
        }

        Arrays.sort(projects, Comparator.comparingInt(a -> a[0])); // sort based on the capital not the profit

        PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Collections.reverseOrder()); // maintain profit

        int i = 0;

        while (k-- > 0) {
            while (i < n && projects[i][0] <= w) {
                maxHeap.offer(projects[i][1]);
                i++;
            }

            if (maxHeap.isEmpty()) break;

            w += maxHeap.poll();
        }

        return w;
    }







    /**
     * @TimeComplexity O(nlogn + klogn) = O(nlogn)
     * @SpaceComplexity O(n)
     */
    public static int findMaximizedCapital2(int k, int w, int[] profits, int[] capital) {
        int n = profits.length;
        Project[] projects = new Project[n];
        for (int i = 0; i < n; i++) {
            projects[i] = new Project(capital[i], profits[i]);
        }
        Arrays.sort(projects);
        PriorityQueue<Integer> q = new PriorityQueue<Integer>(n, Collections.reverseOrder());
        int ptr = 0;
        for (int i = 0; i < k; i++) {
            while (ptr < n && projects[ptr].capital <= w) {
                q.add(projects[ptr++].profit);
            }
            if (q.isEmpty()) {
                break;
            }
            w += q.poll();
        }
        return w;
    }
    static class Project implements Comparable<Project> {
        int capital, profit;

        public Project(int capital, int profit) {
            this.capital = capital;
            this.profit = profit;
        }

        public int compareTo(Project project) {
            return capital - project.capital;
        }
    }











    /**



        sort profits and capital

        create int[]{0 to n} ---> sort these i's based on profits
        now sort profits[] and capital[] based on these i's

        loop capital and check if we can invest in it ---> track Set<Integer> invested - for capitalIs

        till invested < k

        return invested.stream().map(i -> profits[i]).reduce(0, Integer::sum);



     */
    public int findMaximizedCapitalNotWorking1(int k, int w, int[] profits, int[] capital) {
        int n = profits.length;
        Set<Integer> invested = new HashSet<>();
        int[] indices = IntStream.range(0, n).boxed().sorted(Comparator.comparingInt(i -> profits[i])).mapToInt(i->i).toArray();

        k = Math.min(k, n);

        while(invested.size() < k) {
            for (int i : indices) {
                if (invested.contains(i) || capital[i] < w || invested.size() == k) continue;
                w += profits[i];
                invested.add(i);
            }
        }

        return w;
    }




    /**


        We're getting issue if we invested multiple projects in single for loop - "w" calculation

     */
    public int findMaximizedCapitalNotWorking2(int k, int w, int[] profits, int[] capital) {
        int n = profits.length;
        k = Math.min(k, n);
        Set<Integer> invested = new HashSet<>();

        int[][] projects = new int[n][2];
        for (int i = 0; i < n; i++) {
            projects[i][0] = profits[i];
            projects[i][1] = capital[i];
        }
        Arrays.sort(projects, (a,b)-> a[0]==b[0]? b[1]-a[1] : b[0]-a[0]);


        while(invested.size() < k) {
            int prevInvested = invested.size();
            for (int i = 0; i<n; i++) {
                int[] project = projects[i];
                if (invested.contains(i) || project[1] > w || invested.size() == k) continue;
                w += project[0];
                invested.add(i);
            }
            if (invested.size() == prevInvested) break;
        }

        return w;
    }



    public int findMaximizedCapitalNotWorking3(int k, int w, int[] profits, int[] capital) {
        int n = profits.length;
        k = Math.min(k, n);
        Set<Integer> invested = new HashSet<>();

        int[][] projects = new int[n][2];
        for (int i = 0; i < n; i++) {
            projects[i][0] = profits[i];
            projects[i][1] = capital[i];
        }
        Arrays.sort(projects, (a,b)-> a[0]==b[0]? b[1]-a[1] : b[0]-a[0]);


        while(invested.size() < k) {
            int currW = w;
            for (int i = 0; i<n; i++) {
                int[] project = projects[i];
                if (invested.contains(i) || project[1] > currW || invested.size() == k) continue;
                currW -= project[0];
                w += project[0];
                invested.add(i);
            }
            if (currW == w) break;
        }

        return w;
    }


}
