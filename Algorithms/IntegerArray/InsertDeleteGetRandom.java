package Algorithms.IntegerArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Set;
import java.util.HashSet;

/**
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 16 June 2025
 * @link 380. Insert Delete GetRandom O(1) - <a href="https://leetcode.com/problems/insert-delete-getrandom-o1/">LeetCode link</a>
 * @topics Array, Hash Table, Math, Design, Randomized
 */
public class InsertDeleteGetRandom {
    public static void main(String[] args) {

        RandomizedSet obj = new RandomizedSet();
        System.out.println(obj.insert(1));
        System.out.println(obj.remove(2));
        System.out.println(obj.insert(2));
        System.out.println(obj.getRandom());
        System.out.println(obj.remove(1));
        System.out.println(obj.insert(2));
        System.out.println(obj.getRandom());
    }


    /**
     * NOTE:
     * 1. TimeComplexity of arrayList.remove(0) is O(n) and linkedList.remove(0) is O(1)
     * 2. Similarly, TimeComplexity of arrayList.remove(n-1) is O(1) and linkedList.remove(n-1) is O(n)
     */
    static class RandomizedSet{
        private final HashMap<Integer, Integer> map; // <val, valI>
        private final ArrayList<Integer> list;

        public RandomizedSet() {
            map = new HashMap<>();
            list = new ArrayList<>();
        }

        public boolean insert(int val) {
            if (map.containsKey(val)) return false;
            // eg: <val, valI> = <3, 5>
            map.put(val, list.size()); // <val, valI> -- because we insert "val" at the end of the list
            list.add(val); // the new size of the list is 6 i.e., --> 5 is the last element index i.e the position of 3 in the list is 5th index
            return true;
        }

        public boolean remove(int val) {
            if (!map.containsKey(val)) return false;

            // replace the val position with the lastVal and then remove the lastVal
            // --- so we decreased the list size by 1 by removing the lastVal
            int valI = map.get(val);
            int lastVal = list.get(list.size() - 1);

            //swap i.e Move lastVal to 'valI' index i.e the position of val --- this means we deleted the val from the list
            list.set(valI, lastVal);
            map.put(lastVal, valI);

            // Remove last
            list.remove(list.size() - 1);
            map.remove(val);

            return true;
        }

        public int getRandom() {
            return list.get(new Random().nextInt(list.size())); // create a class Random variable and just use random.nextInt(size);
        }
    }











    static class RandomizedSetUsingOnlySet {
        Set<Integer> set;
        public RandomizedSetUsingOnlySet() {
            set = new HashSet<>();
        }

        public boolean insert(int val) {
            return set.add(val);
        }

        public boolean remove(int val) {
            return set.remove(val);
        }

        public int getRandom() {
            int num = set.iterator().next(); // NOT RANDOM
            num = new ArrayList<Integer>(set).get(new Random().nextInt(0, set.size())); // O(1)
            return num;
        }
    }
}
