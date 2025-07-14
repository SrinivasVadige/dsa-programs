package DataStructures;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;

/**
<pre>
—————
—————
QUEUE
—————
—————
FIFO (First In, First Out)
Queue is an interface. So, use LinkedList for init
Queue<TreeNode> q = new LinkedList<>();
Can’t use index.

q.add(ele) —> adds at last of the queue
q.poll() —> removes the 1st ele and returns it and if queue is empty then returns null
q.remove(); —> removes the 1st ele and returns it and if queue is empty then throws NoSuchElementException

Note that Queue interface don’t have removeLast() method, only the LinkedList class does.




—————————————
—————————————
DEQUE (deck)
—————————————
—————————————
Double-ended queue = Mix of stack and queue
 Deque<Integer> dq = new ArrayDeque<>();
 or
 Deque<Integer> dq = new LinkedList<>();
 or
 ArrayDeque<Integer> dq = new ArrayDeque<>();
 or
 LinkedList<Integer> dq = new LinkedList<>(); ----> LinkedList is by default doubly linked list --> it's a Deque by default

 It has index like Stack -- only for LinkedList<Integer> dq = new LinkedList<>();

 ArrayDeque is circular buffer --> Buffer ring
 LinkedList is non-circular buffer.

 🔥
 Even though Deque extends Queue, it behaves like two stacks connected back-to-back — one growing from the front, the other from the back.

ADDING
dq.push(ele)
addFirst(E e)
addLast(E e)
offerFirst(E e) —> true if successful, or false
offerLast(E e)

REMOVING
.poll()
.pop()
.remove() —> NoSuchElementException if empty
removeFirst()
removeLast()
pollFirst()
pollLast()
clear()

GET
get(i)
getFirst() —> NoSuchElementException if empty
getLast()
peekFirst()
peekLast()
element()

CHECKING
isEmpty()
size()
contains(obj)
iterator()
descendingIterator()





——————————————
PRIORITY QUEUE
——————————————
It’s an Abstract Data Structure
PriorityQueue uses heap internally.
Arranges the push() values in asc order and poll() the smallest number accordingly as per the priority.
PriorityQueue<Integer> pq = new PriorityQueue<>();
pq.add(5);
pq.add(1);
pq.add(7);
pq.poll(); —> removes 1
pq.isEmpty();

Create a PriorityQueue with the custom comparator
PriorityQueue<Person> pq = new PriorityQueue<>(byAgeComparatorFuncInterfaceMethod);

This next smallest that gets pulled in the PQ is maintained by Heap.

PQ is used in
    Dijkstra’s Shortest Path Algorithm,
    Dynamically fetch ‘next best’ or ‘next worst’ ele
    Huffman Coding (lossless data compression)
    A* DFS algo to grab next most promising node
    Minimum Spanning Tree MST algo

Construction - O(n)
Polling - O(logn)
Peeking - O(n)
Adding - O(logn)
Removing - O(n)
Removing using HashTable - O(logn)
Contains - O(n)
Contains with HashTable - O(1)

As heap have min & max heap, we have min & max PQ. By default the PQ is min PQ i.e returns smallest number. We can convert min PQ to max PQ by negating the numbers so that bigger numbers will be the smallest number.

</pre>
 *
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 11 Jan 2025
 */
public class QueueDequeuePriorityQueue {

    @SuppressWarnings("unused")
    public static void main(String[] args) {
        List<Integer> arrList = new ArrayList<>();
        List<Integer> list = new LinkedList<>();

        System.out.println("Queue Interface with LinkedList implementation  ----------");
        Queue<Integer> queue = new LinkedList<>();
        queue.add(1);
        queue.addAll(list);
        queue.remove();
        queue.remove(1);
        queue.removeAll(arrList);
        queue.retainAll(arrList);
        queue.poll(); // remove the first element and returns it i. same as queue.remove()
        queue.offer(4); // add the element at the end i.e same as queue.add(4)
        queue.element(); // returns the first element
        queue.peek();
        queue.size();
        queue.clear();
        queue.contains(1);
        queue.containsAll(arrList);
        queue.isEmpty();
        queue.forEach(System.out::println);
        queue.iterator();
        queue.toArray();
        queue.toString();

        Queue<Integer> priorityQueueWithQueueInterface = new PriorityQueue<>();
        Queue<Integer> arrayDequeWithQueueInterface = new ArrayDeque<>();
        Queue<Integer> concurrentQueue = new ConcurrentLinkedQueue<>();
        BlockingQueue<Integer> blockingQueue = new LinkedBlockingQueue<>();
        BlockingQueue<Integer> arrayBlockingQueue = new ArrayBlockingQueue<>(5);
        BlockingQueue<Integer> priorityBlockingQueue = new PriorityBlockingQueue<>();
        BlockingQueue<Delayed> delayQueue = new DelayQueue<>();


        System.out.println("Dequeue interface with LinkedList & ArrayDeque implementation  ----------");
        Deque<Integer> dq = new LinkedList<>();
        Deque<Integer> dq2 = new ArrayDeque<>();
        ArrayDeque<Integer> arrayDeque = new ArrayDeque<>();
        LinkedList<Integer> linkedListDeque = new LinkedList<>(); // Doubly linked list by default
        dq.add(1);
        dq.addAll(list);
        dq.remove();
        dq.remove(1);
        dq.removeAll(arrList);
        dq.retainAll(arrList);
        dq.poll(); // remove the first element and returns it i. same as queue.remove()
        dq.offer(4); // add the element at the end i.e same as queue.add(4)
        dq.element(); // returns the first element
        dq.peek();
        dq.size();
        dq.clear();
        dq.contains(1);
        dq.containsAll(arrList);
        dq.isEmpty();
        dq.forEach(System.out::println);
        dq.iterator();
        dq.toArray();
        dq.toString();


        System.out.println("PriorityQueue class ----------");
        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>();
        priorityQueue.add(1);
        priorityQueue.addAll(list);
        priorityQueue.remove();
        priorityQueue.remove(1);
        priorityQueue.removeAll(arrList);
        priorityQueue.retainAll(arrList);
        priorityQueue.poll(); // remove the first element and returns it i. same as queue.remove()
        priorityQueue.offer(4); // add the element at the end i.e same as queue.add(4)
        priorityQueue.element(); // returns the first element
        priorityQueue.peek();
        priorityQueue.size();
        priorityQueue.clear();
        priorityQueue.contains(1);
        priorityQueue.containsAll(arrList);
        priorityQueue.isEmpty();
        priorityQueue.forEach(System.out::println);
        priorityQueue.iterator();
        priorityQueue.toArray();
        priorityQueue.toString();
    }
}
