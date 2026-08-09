package Algorithms.HeapAlgos;

import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;

/**
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 08 May 2025
 */
public class SmallestNumberInInfiniteSet {
    public static void main(String[] args) {
        SmallestInfiniteSet obj = new SmallestInfiniteSet();
        obj.addBack(2);
        System.out.println("added: " + 2);
        System.out.println("popped: " + obj.popSmallest());
        System.out.println("popped: " + obj.popSmallest());
        System.out.println("popped: " + obj.popSmallest());
        obj.addBack(1);
        System.out.println("added: " + 1);
        System.out.println("popped: " + obj.popSmallest());
        System.out.println("popped: " + obj.popSmallest());
        System.out.println("popped: " + obj.popSmallest());
    }

    /**
     * @TimeComplexity O(1) -- worst case scenario is O(n)
     * @SpaceComplexity O(n)
     *Hea
     * Here maintain the data of popped nums in a set
     */
    static class SmallestInfiniteSetMyApproach {
        int min = 1;
        Set<Integer> popped = new HashSet<>();

        public int popSmallest(){
            int res = min;
            while(popped.contains(min+1)) min++; // trav till next smallest num in infinite set i.e that smallest won't present in popped set
            min++;
            popped.add(res);
            return res;
        }

        public void addBack(int num) {
            popped.remove(num);
            min = Math.min(min, num);
        }
    }


    /**
     * @TimeComplexity O(1)
     * @SpaceComplexity O(n)
     *
     * Here maintain the data of addBacks in minHeap instead of popped. And add to minHeap only if num < smallest && num not present in minHeap
     */
    static class SmallestInfiniteSet {
        int smallest = 1;
        PriorityQueue<Integer> minHeap = new PriorityQueue<>(); // to save addBacks or manually inserted

        public int popSmallest() {
            if(!minHeap.isEmpty()) return minHeap.poll();
            return smallest++; // if minHeap is empty
        }

        public void addBack(int num) {
            if(num < smallest && !minHeap.contains(num)) minHeap.offer(num);
        }
    }

    static class SmallestInfiniteSetImproved {
        int smallest = 1;
        PriorityQueue<Integer> minHeap = new PriorityQueue<>(); // to save addBacks
        Set<Integer> addBacks = new HashSet<>(); // same eles in minHeap

        public int popSmallest() {
            if(!minHeap.isEmpty()) {
                int res = minHeap.poll();
                addBacks.remove(res);
                return res;
            }
            return smallest++;
        }

        public void addBack(int num) {
            if(smallest > num && !addBacks.contains(num)) {
                minHeap.offer(num);
                addBacks.add(num);
            }
        }
    }









    static class SmallestInfiniteSet2 {
        int cur;
        PriorityQueue<Integer>  minHeap;
        Set<Integer> addBacks;

        public SmallestInfiniteSet2() {
            cur = 1;
            minHeap = new PriorityQueue<>();
            addBacks = new HashSet<>();
        }

        public int popSmallest() {
            if(!minHeap.isEmpty()){
                int res = minHeap.poll();
                addBacks.remove(res);
                return res;
            }else{
                return cur++;
            }
        }

        public void addBack(int num) {
            if(num < cur && addBacks.add(num)){
                minHeap.offer(num);
            }
        }
    }




}
