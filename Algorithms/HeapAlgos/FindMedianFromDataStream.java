package Algorithms.HeapAlgos;

import java.util.*;

/**
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 23 April 2026
 * @link 295. Find Median from Data Stream <a href="https://leetcode.com/problems/find-median-from-data-stream/">LeetCode Link</a>
 * @topics Two Pointers, Design, Array, Sorting, Heap(PriorityQueue), Data Stream
 * @companies Amazon(7), Microsoft(6), IXL(3), Google(5), Citadel(5), Apple(4), PayPal(3), Meta(2), Bloomberg(2), Tinder(2), Oracle(12), Pinterest(10), Anduril(9), TikTok(7), Goldman Sachs(6), Uber(6), Flipkart(4), Walmart Labs(3), Splunk(3), Nvidia(3)
 */
public class FindMedianFromDataStream {
    public static void main(String[] args) {
        System.out.println("MedianFinderUsing2Pqs: ");
        System.out.println("addNum(1), findMedian(), addNum(2), findMedian(), addNum(0), findMedian(), addNum(3)");
        Mf mf = new MedianFinderUsingTwoHeaps1();
        mf.addNum(1);
        System.out.println(mf.findMedian());
        mf.addNum(2);
        System.out.println(mf.findMedian());
        mf.addNum(0);
        System.out.println(mf.findMedian());
        mf.addNum(3);
        System.out.println(mf.findMedian());
    }

    public static interface Mf {
        public abstract void addNum(int num);
        public abstract double findMedian();
    }



    /**
     * @TimeComplexity O(logn)
     * @SpaceComplexity O(n)
     */
    public static class MedianFinderUsingTwoHeaps1 implements Mf {
        PriorityQueue<Integer> left = new PriorityQueue<>(Comparator.reverseOrder()); // maxHeap
        PriorityQueue<Integer> right = new PriorityQueue<>(); // minHeap

        public void addNum(int num) {
            // STEP 1: Balance by value (ordering)
            if (left.isEmpty() || num <= left.peek()) left.offer(num);
            else right.offer(num);

            // STEP 2: Balance by size
            if (left.size() > right.size() + 1) {
                right.offer(left.poll());
            } else if (right.size() > left.size()) { // always maintain left as biggest size
                left.offer(right.poll());
            }
        }

        public double findMedian() {
            boolean isEven = (left.size() + right.size())%2 == 0;
            if (isEven) {
                return (left.peek() + right.peek()) / 2.0;
            } else {
                return left.peek();
            }
        }
    }







    /**
     * @TimeComplexity O(logn)
     * @SpaceComplexity O(n)
     */
    public static class MedianFinderUsingTwoHeaps2 implements Mf {
        PriorityQueue<Integer> left = new PriorityQueue<>(Comparator.reverseOrder()); // maxHeap
        PriorityQueue<Integer> right = new PriorityQueue<>(); // minHeap

        public void addNum(int num) {
                left.add(num);
                right.add(left.poll());
                if(right.size() > left.size()){
                    left.add(right.poll());
                }
        }

        public double findMedian() {
            if(right.size() == left.size())
                return (double) (left.peek()+right.peek())*0.5 ;
            else
                return (double) left.peek();
        }
    }







    /**
     * @TimeComplexity O(logn)
     * @SpaceComplexity O(n)
     */
    public static class MedianFinderUsingTwoHeaps3 implements Mf {
        private PriorityQueue<Integer> small = new PriorityQueue<>(Collections.reverseOrder());
        private PriorityQueue<Integer> large = new PriorityQueue<>();
        private boolean even = true;

        public void addNum(int num) {
            if (even) {
                large.offer(num);
                small.offer(large.poll());
            } else {
                small.offer(num);
                large.offer(small.poll());
            }
            even = !even;
        }

        public double findMedian() {
            if (even)
                return (small.peek() + large.peek()) / 2.0;
            else
                return small.peek();
        }
    }






    /**
     * @TimeComplexity O(logn)
     * @SpaceComplexity O(n)
     */
    public static class MedianFinderUsingTwoHeaps4 implements Mf {

        private PriorityQueue<Integer> left = new PriorityQueue<>(Collections.reverseOrder()); // maxHeap
        private PriorityQueue<Integer> right = new PriorityQueue<>(); // minHeap

        public void addNum(int num) {
            if (left.size() == right.size()) { // push to right after balancing
                left.offer(num);
                right.offer(left.poll());
            } else { // push to left after balancing
                right.offer(num);
                left.offer(right.poll());
            }
        }

        public double findMedian() {
            if (left.size() == right.size()) {
                return (left.peek() + right.peek()) / 2.0;
            } else {
                return right.peek(); // right holds extra element
            }
        }
    }








    /**
     * @TimeComplexity O(logn)
     * @SpaceComplexity O(n)
     */
    public static class MedianFinderUsingTwoHeaps5 implements Mf {

        private PriorityQueue<Integer> small = new PriorityQueue<>(Collections.reverseOrder());; // maxHeap
        private PriorityQueue<Integer> large = new PriorityQueue<>(); // minHeap

        public void addNum(int num) {

            small.offer(num); // Step 1: add to small (maxHeap)

            if (!small.isEmpty() && !large.isEmpty() && small.peek() > large.peek()) { // Step 2: ensure ordering: max(small) <= min(large)
                large.offer(small.poll());
            }


            if (small.size() > large.size() + 1) { // Step 3: balance sizes
                large.offer(small.poll());
            }

            if (large.size() > small.size() + 1) {
                small.offer(large.poll());
            }
        }

        public double findMedian() {
            if (small.size() > large.size()) {
                return small.peek();
            }

            if (large.size() > small.size()) {
                return large.peek();
            }

            return (small.peek() + large.peek()) / 2.0;
        }
    }














    /**
     * @TimeComplexity O(nlogn)
     * @SpaceComplexity O(n)
     */
    public static class MedianFinderUsingListSort implements Mf {
        List<Integer> list = new ArrayList<>();

        public void addNum(int num) {
            list.add(num);
        }

        public double findMedian() {
            Collections.sort(list);
            int n = list.size();
            if (n % 2 == 0) return (list.get(n/2-1) + list.get(n/2))/2.00;
            else return list.get(n/2);
        }
    }






    /**
     * @TimeComplexity O(nlogn)
     * @SpaceComplexity O(n)
     */
    public static class MedianFinderUsingHeapAndList implements Mf {
        PriorityQueue<Integer> heap = new PriorityQueue<>();

        public void addNum(int num) {
            heap.add(num);
        }

        public double findMedian() { // same as sorting ---> cause new ArrayList<>(righteap); does not return the nums in sorted order
            List<Integer> list = new ArrayList<>();
            while (!heap.isEmpty()) list.add(heap.poll());
            heap = new PriorityQueue<>(list);
            int n = list.size();
            if (n % 2 == 0) return (list.get(n/2-1) + list.get(n/2))/2.00;
            else return list.get(n/2);
        }
    }
}
