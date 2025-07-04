package Algorithms.LinkedListAlgos;

import java.util.*;

/**
 * consider both put and get means recent used
 * evict LRU with using order or time

 so how to maintain the order?
 ---> ArrayList by removing from the middle and adding at end -- TLE
 ---> LinkedHashMap with accessOrder=true --> maintain the order or access
 ---> Custom Doubly LinkedList by removing from the middle and adding the recent key at tail or end --> internal implementation of LinkedHashMap with accessOrder=true
 ---> LinkedHashMap with accessOrder=false --> remove from the middle and add to the end
 ---> HashMap with LinkedHashSet ---> same as above LinkedHashMap with accessOrder=false
 *
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 04 Jan 2025
 * @link 146. LRU Cache <a href="https://leetcode.com/problems/lru-cache/">LeetCode link</a>
 * @topics Hash Table, Linked List, Design, Doubly-Linked List
 * @see DataStructures.HashMapExample JavaDoc -> to understand "HOW HASHMAP WORKS"
 * @companies amazon, facebook, apple, google, tiktok, oracle, palo, microsoft, goldman, bloomberg, walmart, uber, aurora, confluent, visa, paypal, snapchat, salesforce, bytedance, cloudflare, adobe, nvidia, yandex, samsung, linkedin, citadel, shopify, intuit, servicenow, rubrik
 */
public class LRUCache {

    public static void main(String[] args) {
        LRUCacheUsingCustomNode cache = new LRUCacheUsingCustomNode(2);
        cache.put(1, 1);
        cache.put(2, 2);
        cache.get(1);       // returns 1
        cache.put(3, 3);    // evicts key 2
        System.out.println( "cache.get(1): " + cache.get(2));       // returns -1 (not found)
        cache.put(4, 4);    // evicts key 1
        System.out.println( "cache.get(1): " + cache.get(1));       // returns -1 (not found)
        System.out.println( "cache.get(3): " + cache.get(3));       // returns 3
        System.out.println( "cache.get(4): " + cache.get(4));       // returns 4
    }


    /**
     * see {@link DataStructures.HashMapExample} javaDoc
     * accessOrder==true --> orderByAccess not by insertion
     */
    static class LRUCacheUsingLinkedHashMap extends LinkedHashMap<Integer, Integer> {
        int capacity;
        public LRUCacheUsingLinkedHashMap(int capacity) {
            super(capacity+1, 1f, true); // --> as capacity is not changing or use default super(capacity, 0.75f, true);
            /*
            // or
             Map<Integer, Integer> map = new LinkedHashMap<>(capacity, 0.75f, true) {
                @Override
                protected boolean removeEldestEntry(Map.Entry<Integer, Integer> eldest) {
                    return size() > capacity;
                }
               };
            */
            this.capacity = capacity;
        }

        @Override
        protected boolean removeEldestEntry(Map.Entry<Integer, Integer> eldest) {
            return size() > capacity;
        }

        public int get(int key) {
            return getOrDefault(key, -1);
        }

        public void put(int key, int value) {
            super.put(key, value);
        }
    }








    /**
     * Use (LinkedHashSet with HashMap) or (LinkedHashMap with insertionOrder)
     * ---> and then remove from the middle and add to the end
     */
    static class LRUCacheUsingHashMapAndLinkedHashSet {
        Map<Integer, Integer> map = new HashMap<>();
        Set<Integer> set = new LinkedHashSet<>();
        int size = 0;
        public LRUCacheUsingHashMapAndLinkedHashSet(int capacity) {
            size = capacity;
        }

        public int get(int key) {
            if(set.contains(key)) {
                set.remove(key);
                set.add(key);
            }
            return map.getOrDefault(key, -1);
        }

        public void put(int key, int value) {
            map.put(key, value);

            if(set.contains(key)) {
                set.remove(key);
            }
            set.add(key);

            if(map.size() > size) {
                int lru = set.iterator().next(); // set.stream().findFirst().orElse(null);
                set.remove(lru);
                map.remove(lru);
            }
        }
    }








    /**
     * It's just the internal implementation of LinkedHashMap with accessOrder instead of insertionOrder
     */
    static class LRUCacheUsingCustomNode {
        // "Custom Node" with prev and next or "Doubly Linked List"
        static class Node {
            int key, val;
            Node prev, next;
            public Node(int key, int val) {this.key = key; this.val = val;}
            public Node() {} // default noArgs constructor will be removed if create a constructor with parameters
        }

        private final int capacity;
        private final  Map<Integer, Node> cache; // key & it's node address... oldest is the head of that node and latest is the tail
        private final Node head; // least recently used (LRU) ---> HEAD
        private final Node tail; // most recently used (MRU) ---> TAIL


        public LRUCacheUsingCustomNode(int capacity) {
            this.capacity = capacity;
            cache = new HashMap<>();

            head = new Node(0,0); // or new Node(); or new Node(-1, -1);
            tail = new Node(0,0); // or new Node(); or new Node(-1, -1);

            head.next = tail;
            tail.prev = head;
        }

        public int get(int key) {
            if (!cache.containsKey(key)) {
                return -1;
            }

            Node node = cache.get(key);
            removeNode(node);
            insertNode(node); // make node as tail
            return node.val;
        }

        public void put(int key, int val) {
            if (cache.containsKey(key)) {
                Node currentNode = cache.get(key);
                removeNode(currentNode);
            }

            // as we removed the currentNode -- treat every Node as newNode
            Node newNode = new Node(key, val);
            insertNode(newNode); // make node as tail
            cache.put(key, newNode);

            if (capacity < cache.size()) {
                Node lruNode = head.next; // node to evict --> least recently used (LRU)
                removeNode(lruNode);
                cache.remove(lruNode.key);
            }
        }


        // "Custom node" or "Doubly Linked List" methods
        private void removeNode(Node node) {
            Node prev = node.prev;
            Node next = node.next;

            prev.next = next;
            next.prev = prev;
        }
        private void insertNode(Node node) { // newNode doesn't contains prev and next
            Node prev = tail.prev;
            prev.next = node;
            node.prev = prev;

            node.next = tail;
            tail.prev = node;
        }
    }













    /**
     * Remove from the middle and add to the end
     * Brute force
     */
    static class LRUCacheUsingHashMapAndArrayList {
        private final int capcity;
        private Map<Integer, Integer> map = new HashMap<>();
        private List<Integer> lst = new ArrayList<>();

        public LRUCacheUsingHashMapAndArrayList(int capacity) {
            this.capcity = capacity;
        }

        // Working but TLE
        public int get(int key) {
            if (map.containsKey(key)) {
                lst.remove(Integer.valueOf(key)); // --> remove from the middle
                lst.add(key);                     // --> add to the end
            }
            return map.getOrDefault(key, -1);
        }

        // Working but TLE
        public void put(int key, int value) {
            if (capcity == map.size() && !map.containsKey(key)) {
                map.remove(lst.get(0));
                lst.remove(0);
            }

            if (map.containsKey(key)) { // or use this in else case with above if case
                lst.remove(Integer.valueOf(key));
            }

            lst.add(key);
            map.put(key, value);

            System.out.println("map: " + map);
            System.out.println("lst: " + lst);
        }
    }






}
