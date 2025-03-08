package Algorithms.HeapAlgos;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;

public class FindMedianFromDataStream {
    public static void main(String[] args) {
        System.out.println("MedianFinderUsing2Pqs: ");
        System.out.println("addNum(1), findMedian(), addNum(2), findMedian(), addNum(0), findMedian(), addNum(3)");
        MedianFinderUsing2Pqs medianFinder2pqs = new MedianFinderUsing2Pqs();
        medianFinder2pqs.addNum(1);
        System.out.println(medianFinder2pqs.findMedian());
        medianFinder2pqs.addNum(2);
        System.out.println(medianFinder2pqs.findMedian());
        medianFinder2pqs.addNum(0);
        System.out.println(medianFinder2pqs.findMedian());
        medianFinder2pqs.addNum(3);
        System.out.println(medianFinder2pqs.findMedian());


    }

    static class  MedianFinderUsing2Pqs {
        private PriorityQueue<Integer> small = new PriorityQueue<>(Collections.reverseOrder()); // largerNumber on top
        private PriorityQueue<Integer> large = new PriorityQueue<>();
        private boolean even = true; // initially 0 i.e total size (small + large) is 0

        public void addNum(int num) {
            if (even) {
                large.offer(num);
                small.offer(large.poll());
            } else {
                small.offer(num);
                large.offer(small.poll());
            }
            even = !even; // even = (smallSize+largeSize)%2; or even if smallSize==largeSize
        }

        public double findMedian() {
            if (even)
                return (small.peek() + large.peek()) / 2.0; // largeNumber in smallPQ and smallNumber in largePQ
            else
                return small.peek();
        }
    }


    // DO: maintain only one lst and use binary search to insert the new number



    class MedianFinderUsingPqLst {
        PriorityQueue<Integer> pq = new PriorityQueue<>((a,b)->a-b);


        public void addNum(int num) {
            pq.offer(num);
        }

        public double findMedian() {
            int n = pq.size();
            List<Integer> lst = new ArrayList<>();
            while(pq.size()>0) lst.add(pq.poll());
            pq = new PriorityQueue<>(lst);

            if ((n&1)==0) return ((lst.get(n/2-1)) + lst.get(n/2))/2.0;
            else return lst.get(n/2);
        }
    }






    class MedianFinderUsingPqArr {

        PriorityQueue<Integer> pq = new PriorityQueue<>((a,b)->a-b);

        public void addNum(int num) {
            pq.offer(num);
        }

        public double findMedian() {
            int n = pq.size();
            int[] arr = new int[n];
            int i=0;
            while(pq.size()>0) arr[i++]=pq.poll();
            for(i=0; i<n; i++) pq.offer(arr[i]);

            if ((n&1)==0) return ((arr[n/2-1]) + arr[n/2])/2.0;
            else return arr[n/2];
        }
    }
}
