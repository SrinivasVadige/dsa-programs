package Algorithms.IntegerArray;

import java.util.Arrays;

/**
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 14 June 2025
 * @link 274. H-Index <a href="https://leetcode.com/problems/h-index/">Leetcode link</a>
 * @topics Greedy, Counting Array, Arrays, Sort, Binary Search


    Points
    -------
    We will find the maximum value of h --- return max(h)
    where
    1. h == num of papers
    2. h <= n
    3. return max(h)
    4. at h, the researcher has at least h papers --- papersCount-at-h >= h
    5. at h, the papers (each paper) were cited at least times h --- currPaperCitations >= h

    For example,
    if a researcher has a "h-index" of 3, it means they have 3 papers that it means they have each been cited at least 3 times

 * @see #hIndexBruteForce
 * @see #hIndexBruteForce2
 * @see #hIndexSortAndBinarySearchEachH for easy understanding the problem
 */
public class HIndex {
    public static void main(String[] args) {
        int[] citations = {0, 1, 3, 5, 6};

        System.out.println("hIndexUsingGreedyAndCountingArray => " + hIndexUsingGreedyAndCountingArray(citations));
        System.out.println("hIndexUsingGreedyAndCountingArray 2 => " + hIndexUsingGreedyAndCountingArray2(citations));
        System.out.println("hIndexUsingGreedyAndCountingArray 3 => " + hIndexUsingGreedyAndCountingArray3(citations));
        System.out.println("hIndexUsingSortAndBinary => " + hIndexUsingSortAndBinary(citations));
        System.out.println("hIndexUsingSortAndLiner => " + hIndexUsingSortAndLiner(citations));
    }

    public static int hIndexUsingGreedyAndCountingArray(int[] citations) {
        int n = citations.length;
        int[] count = new int[n + 1]; // or frequencies

        for (int c : citations) {
            if (c >= n) {
                count[n]++;
            } else {
                count[c]++;
            }
        }

        int total = 0;
        for (int i = n; i >= 0; i--) {
            total += count[i];
            if (total >= i) return i; // Greedy choice
        }
        return 0;
    }



    /**
     * @TimeComplexity O(n)
     * @SpaceComplexity O(n)
     */
    public static int hIndexUsingGreedyAndCountingArray2(int[] citations) {
        int n = citations.length;
        int[] paperCounts = new int[n+1];

        for (int c : citations) {
            paperCounts[Math.min(c, n)]++;
        }

        int h = n;
        int papers = paperCounts[n];
        while (papers < h) {
            h--;
            papers += paperCounts[h];
        }
        return h;
    }




    /**
     * @TimeComplexity O(n)
     * @SpaceComplexity O(n)
     */
    public static int hIndexUsingGreedyAndCountingArray3(int[] citations) {
        int n = citations.length;
        int[] paperCounts = new int[n+1]; // or citationBuckets

        for (int c : citations) {
            paperCounts[Math.min(c, n)]++;
        }

        int cumulativePapers = 0;
        for (int hIndex = n; hIndex >= 0; hIndex--) {
            cumulativePapers += paperCounts[hIndex];
            if (cumulativePapers >= hIndex) return hIndex;
        }

        return 0;
    }



    /**
     * @TimeComplexity O(nlogn) + O(logn) = O(nlogn)
     * @SpaceComplexity O(1)

         Given
         [3, 0, 6, 1, 5]

         After sort
         [0, 1, 3, 5, 6]

         l     m     r
         ↓     ↓     ↓
         [0, 1, 3, 5, 6]
          0  1  2  3  4
         |______|
            3
         n-m = 5-2 = 3 = numOfPapers

     */
    public static int hIndexUsingSortAndBinary(int[] citations) {
        int n = citations.length;

        Arrays.sort(citations);

        int l=0, r=n-1;
        while(l<=r) {
            int m = l + (r-l)/2;
            int numOfPapers = n-m;

            if(citations[m] >= numOfPapers) r = m-1;
            else l = m+1;

            /*
             or

             if(citations[m] == numOfPapers) return citations[m];
             else if (citations[m] > numOfPapers) r = m-1;
             else l = m+1;

             */
        }
        return n-l;
    }

    /**
     * @TimeComplexity O(nlogn) + O(logn) = O(nlogn)
     * @SpaceComplexity O(1)
     */
    public static int hIndexUsingSortAndBinary2(int[] citations) {
        int n = citations.length, h = 0, l = 0, r = n - 1, mid;
        while (l <= r) {
            mid = l + (r - l) / 2;
            if (n - mid <= citations[mid]) {
                h = n - mid;
                r = mid - 1;
            } else {
                l = mid + 1;
            }
        }
        return h;
    }



    /**
     * @TimeComplexity O(nlogn) + O(n) = O(nlogn)
     * @SpaceComplexity O(1)
     */
    public static int hIndexUsingSortAndLiner(int[] citations) {
        int n = citations.length;
        Arrays.sort(citations);

        for(int i=0; i<n; i++) {
            if(citations[i] >= n-i) return n-i;
        }
        return 0;
    }



    /**
     * @TimeComplexity O(nlogn) + O(n) = O(nlogn)
     * @SpaceComplexity O(1)

     Given
     [3, 0, 6, 1, 5]

     After sort
     [6, 5, 3, 1, 0]   ---> nums[i] >= numOfPapers --> 6>=1 true --> continue
      i
     |_|
      1

     [6, 5, 3, 1, 0]   ---> nums[i] >= numOfPapers --> 5>=2 true --> continue
         i
     |____|
        2

     [6, 5, 3, 1, 0]   ---> nums[i] >= numOfPapers --> 3>=3 true --> continue
         i
     |_______|
         3

     [6, 5, 3, 1, 0]   ---> nums[i] >= numOfPapers --> 1>=4 false --> so return previous numOfPapers
            i
     |__________|
          4

     */

    public static int hIndexUsingSortAndLiner2(int[] citations) {
        int n = citations.length;
        Arrays.sort(citations);
        reverse(citations); // descending order

        for(int i=0; i<n; i++) {
            if (citations[i] >= i+1) continue; // i+1 is numOfPapers
            else return i; // return previous numOfPapers
            // or if (citations[i] < i+1) return i;
        }
        return n;
    }
    private static void reverse(int[] nums) {
        int n=nums.length, l=0, r=n-1;
        while(l<r) {
            int temp = nums[l];
            nums[l] = nums[r];
            nums[r] = temp;
            l++;
            r--;
        }
    }



    /**
     * @TimeComplexity O(nlogn) + O(n) = O(nlogn)
     * @SpaceComplexity O(1)
     */
    public int hIndexSortAndLiner3(int[] citations) {
        Arrays.sort(citations);
        int c = 0;
        int j =1;
        for (int i = citations.length-1; i >=0; i--) {
            if (citations[i] >= j) c++;
            j++;
        }
        return c;
    }












    /**
     * @TimeComplexity O(n*h) or O(n^2), cause h ranges from 1 to n
     * @SpaceComplexity O(1)

     trav each i from 1 to n
     with currPaperCitations >= h && papersCount-at-h >= h conditions
     And we traverse form 1 to n, maxH = h will always be the max of h

     */
    public int hIndexBruteForce(int[] citations) {
        int maxH = 0;
        int n = citations.length;

        /*
            h=0 is optional
            cause at h=0, if(papersCount >= h) maxH=h; --> if(n >= 0) maxH=0; --> always assign maxH=0 which is a default maxH value
            And we're going to find the maxH in next h for loop 0,1,2,3....n
         */
        for(int h=1; h<=n; h++) {
            int papersCount = 0;
            for(int currPaperCitations: citations) { // looping Set<Integer> set = new HashSet<>(); won't work cause [1,2,2] returns maxH=2
                if (currPaperCitations >= h) papersCount++;
            }
            if (papersCount >= h) maxH = h;
        }
        return maxH;
    }






    /**
     * @TimeComplexity O(n*h) or O(n^2), cause h ranges from 1 to n
     * @SpaceComplexity O(1)

         Given
         [3, 0, 6, 1, 5]

         After sort
         [0, 1, 3, 5, 6]

         NOTE:
         1) h <= n
         2) [1,11,15] returns maxH=2, so "h" may be or may not be the one of the number in citations[] array as we check "currPaperCitations >= h" condition
         3)

         n = 5
         [0, 1, 3, 5, 6]
         i

     */
    public int hIndexBruteForce2(int[] citations) {
        int maxH = 0;
        int n = citations.length;
        Arrays.sort(citations);

        for(int h=1; h<=n; h++) {
            int papersCount = 0;
            for(int i=0; i<n; i++) {
                if (citations[i] >= h) {
                    papersCount = n-i;
                    break;
                }
            }
            if (papersCount >= h) maxH = h;
        }
        return maxH;
    }





    /**
     * @TimeComplexity O(nlogn) + O(hlogn) or O(nlogn)
     * @SpaceComplexity O(1)


     Given
        [3, 0, 6, 1, 5]

     After sort
        [0, 1, 3, 5, 6]

     NOTE:
     1) h <= n
     2) [1,11,15] returns maxH=2, so "h" maybe or may not be the one of the number in citations[] array as we check "currPaperCitations >= h" condition

     n = 5
     [0, 1, 3, 5, 6]
         i

     */

    public int hIndexSortAndBinarySearchEachH(int[] citations) {
        int maxH = 0;
        int n = citations.length;
        Arrays.sort(citations);

        for(int h=1; h<=n; h++) {
            int papersCount = 0;
            int l = 0, r = n-1, m = n;
            while (l<=r) {
                m = l+(r-l)/2;
                if (citations[m] >= h) r = m - 1;
                else l = m + 1;
            }
            papersCount = n-l;
            if (papersCount >= h) maxH = h;
        }
        return maxH;
    }









    /**
     * NOT WORKING
     citations[i] >= h?

     num of papers = h

     num of big citations papers == citations[i] && h is max

     if(n-i >= nums[i]) {

     }

     [3, 0, 6, 1, 5]

     [0, 1, 3, 5, 6]
     i

     [0, 1, 3, 5, 6]
     i

     [0, 1, 3, 5, 6]
     i


     [1, 1, 3]
     i

     EDGE CASE:
     1) 0s
     2) Duplicates
     */
    public int hIndexMyApproachNotWorking(int[] citations) {
        Arrays.sort(citations);
        int n=citations.length;
        if(Arrays.stream(citations).allMatch(x->x==0)) return 0;
        int h = 1;
        for(int i=n-1; i>=0; i--) {
            if(n-i >= citations[i]) {
                h = Math.max(h, citations[i]);
            }
            // System.out.printf("h:%s \n", h);
        }
        return h;
    }
}
