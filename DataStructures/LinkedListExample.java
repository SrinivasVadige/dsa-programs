package DataStructures;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * @author Srinivas Vadige, srinivas.vadige@gmail.com
 * @since 11 Nov 2024
 */
public class LinkedListExample {

    private static class ListNode {
        int val;
        ListNode next;
        ListNode(){};
        ListNode(int x) { val = x; };
        ListNode(int x, ListNode next) { val = x; this.next = next; }
    }

    static class DummyNode { // separate static class or can have private class in LinkedListStack itself
        int value;
        DummyNode next; // next or head
    }

    public static void main(String[] args) {
        System.out.println("LinkedList with List Interface ----------");
        List<Integer> arrList = new ArrayList<>();
        List<Integer> list = new LinkedList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.remove(2);
        list.subList(0, 2);
        list.set(0, 5);
        list.get(0);
        list.size();
        list.clear();
        list.contains(1);
        list.containsAll(arrList);
        list.isEmpty();
        list.forEach(System.out::println);
        list.indexOf(1);
        list.lastIndexOf(1);
        list.remove(0);
        list.removeAll(arrList);
        list.retainAll(arrList);
        list.toArray();
        list.toString();
        // useful LinkedList & ArrayList methods
        list.addFirst(5);
        list.addLast(6);
        list.getFirst();
        list.getLast();
        list.removeFirst();
        list.removeLast();
        arrList.addFirst(1);

        // @since JDK 21 methods
        list.reversed(); // in LinkedList
        // note that in List interface added addFirst, addLast, removeFirst, removeLast methods directly and we still have the old methods in LinkedList and ArrayList as well

        // @since JDK 10
        list = arrList = List.copyOf(list); // ImmutableCollections

        // @since JDK 9
        list = arrList = List.of(1,2,3);





        System.out.println("LinkedList with Queue Interface ----------");
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




        System.out.println("LinkedList with Deque Interface ----------");
        Deque<Integer> deque = new LinkedList<>();
        deque.addFirst(1);
        deque.addLast(2);
        deque.addFirst(3);
        deque.addLast(4);
        deque.addFirst(5);
        deque.addLast(6);
        deque.element();




        System.out.println("Custom SinglyLinkedList class using Stack approach ----------");
        LinkedListStack stack = new LinkedListStack();
        stack.push(1);
        stack.push(2);
        stack.push(3);
        stack.push(4);
        stack.push(5);
        stack.display();
        System.out.println("Top element: " + stack.peek());
        System.out.println("Remove two elements from the stack:");
        stack.pop();
        stack.pop();
        stack.display();
        stack.size();
        System.out.println("Top element: " + stack.peek());





        System.out.println("Initialize SinglyLinkedList using ListNode class ----------");
        // Using val+next constructor
        ListNode head = new ListNode(1, new ListNode(2, new ListNode(3, new ListNode(4, new ListNode(5)))));
        // Using val constructor
        head = new ListNode(1);
        ListNode curr = head;
        curr.next = new ListNode(2);
        curr = curr.next;
        curr.next = new ListNode(3);
        // Using default constructor
        head = new ListNode();
        curr = head;
        curr.val = 1;
        curr.next = new ListNode();
        curr = curr.next;
        curr.val = 2;
        curr.next = new ListNode();
        curr = curr.next;
        curr.val = 3;


        System.out.println("SinglyLinkedList Traversal ----------");
        // for-loop
        for(ListNode trav=head; trav!=null; trav=trav.next)
            System.out.println(trav.val);
        // while-loop
        ListNode trav=head;
        while(trav != null){
            System.out.println(trav.val);
            trav=trav.next;
        }
        // recursive method
        /*
            void printSinglyLinkedList(ListNode head) {
                if(head == null) return;
                System.out.println(head.val);
                printSinglyLinkedList(head.next);
            }
        */



        System.out.println("Convert int[] to SinglyLinkedList ----------");
        int[] array = {1, 2, 3, 4, 5};

        // dummy node + prev approach
        ListNode dummy = new ListNode(-1), prev=dummy;
        for(Integer n: array) {
            prev.next = new ListNode(n);
            prev=prev.next;
        }
        for (trav = dummy.next; trav != null; trav = trav.next) System.out.println(trav.val);

        // head node + prev approach
        head = new ListNode(array[0]);
        prev = head;
        for (int i = 1; i < array.length; i++, prev = prev.next) {
            prev.next = new ListNode(array[i]);
        }
        for (trav = head; trav != null; trav = trav.next) System.out.println(trav.val);

        // head node + curr approach
        head = new ListNode();
        curr = head;
        for(int i=0; i<array.length; i++){
            curr.val = array[i];

            if(i != array.length-1) {
                curr.next = new ListNode();
                curr = curr.next;
            }
        }
        for (trav = head; trav != null; trav = trav.next) System.out.println(trav.val);





        System.out.println("SELF-REFERENTIAL / CIRCULAR LINKED LIST / INFINITE SINGULAR LINKED LIST ---------- 🔥🔥🔥");
        head = new ListNode( 1, new ListNode(2, new ListNode(3) ) );
        head.next.next.next = head; // or head.next = head
        ListNode current = head;

        // To print self referential / circular linked list
		do {
			System.out.println(current.val);
			current = current.next;
		} while (current != head);
        // or
        for (current = head; current != null; current = current.next) {
            System.out.println(current.val);
            if (current.next == head) break;
        }
        /*
        EXPLANATION:
        ListNode head = new ListNode( 1, new ListNode(2, new ListNode(3) ) );
        head.next = head; ✅ but ---> SELF-REFERENTIAL
        But we can do head.next = head.next.next; ✅
        And to traverse use head = head.next; ✅
        And we know that head.next = head.next; won't do anything but it's not an error

        Similarly

        ListNode trav = head.next;
        trav.next = head; // ---> this will create a self referential

        It's not a problem to have a self referential LinkedList -> It'll become Circular LinkedList

        But if we print it like a normal SingularLinedList, DoublyLinkedList it'll be a Self-Referential Infinite Loop
        When head.next=head;
        for(ListNode trav=head; trav!=null; trav=trav.next){ sout(trav.val); }
           __ 1 __
          |__ 2 __| ---> loop, at first 1 will be printed and it checks next node 2 -> this reference is the self-reference, now it goes inside this reference and we know thats 1 is already assigned and then it'll print 1 again and goes to next node 2 which is a self reference --- infante loop that prints 1
              3
        */







        System.out.println("Custom SinglyCircularLinkedList class ----------");
        CircularLinkedList cll = new CircularLinkedList();
        cll.addNode(13);
        cll.addNode(7);
        cll.addNode(24);
        cll.addNode(1);
        cll.addNode(8);
        cll.addNode(37);
        cll.addNode(46);
        cll.containsNode(8);
        cll.deleteNode(1);
        cll.traverseList();







        System.out.println("Custom DoublyLinkedList class ----------"); // same like Deque
        DoublyLinkedList<Integer> doublyLinkedList = new DoublyLinkedList<>();
        doublyLinkedList.addFirst(1);
        doublyLinkedList.addLast(2);
        doublyLinkedList.addFirst(3);
        doublyLinkedList.addLast(4);
        doublyLinkedList.addFirst(5);
        doublyLinkedList.addLast(6);
        doublyLinkedList.display();
    }










    /**
     * -------------------------------------------------------------------------
     * -------------------------------------------------------------------------
     * Custom SinglyLinkedList using Stack approach
     * -------------------------------------------------------------------------
     * -------------------------------------------------------------------------
    */
    private static class LinkedListStack { // or implements Queue & we can have it as static class or separate class
        // most of these methods are same as java.util.LinkedList class
        private Node head; // the first node
        private int size;

        // nest class to define LinkedList node
        private class Node {
            int value;
            Node next;
        }

        public LinkedListStack() {
            head = null;
            size = 0;
        }

        // Remove value from the beginning of the list for demonstrating behavior of stack
        public int pop() throws LinkedListEmptyException {
            if (head == null) {
                throw new LinkedListEmptyException();
            }
            int value = head.value;
            head = head.next;
            size--;
            return value;
        }

        // Add value to the beginning of the list for demonstrating behavior of stack
        public void push(int value) {
            Node oldHead = head;
            head = new Node();
            head.value = value;
            head.next = oldHead;
            size++;
        }

        public int size() {
            return size;
        }

        // Method to check if the stack is empty
        public boolean isEmpty() {
            return head == null;
        }

        // Method to display the stack
        public void display() {
            if (isEmpty()) {
            System.out.println("Stack is empty");
            } else {
            Node current = head;
            System.out.print("Stack elements: ");
            while (current != null) {
                System.out.print(current.value + " ");
                current = current.next;
            }
            System.out.println();
            }
        }

        // Method to get the top element of the stack
        public int peek() {
            if (isEmpty()) {
            System.out.println("Stack is empty");
            return -1;
            }
            return head.value;
        }
    }

    static class LinkedListEmptyException extends RuntimeException {
        private static final long serialVersionUID = 1L;

        public LinkedListEmptyException() {
            super();
        }

        public LinkedListEmptyException(String message) {
            super(message);
        }
    }









    /**
     * -------------------------------------------------------------------------
     * -------------------------------------------------------------------------
     * DoublyLinkedList
     * -------------------------------------------------------------------------
     * -------------------------------------------------------------------------
    */
    static class DoublyLinkedList <T> implements Iterable <T> {

        private Node <T> head = null;
        private Node <T> tail = null;
        int size;

        @SuppressWarnings("hiding")
        private class Node <T> {
            T data;
            Node <T> next, prev;

            Node(T val, Node <T> next, Node <T> prev) {
                this.data = val;
                this.next = next;
                this.prev = prev;
            }

            @SuppressWarnings("unused")
            Node() { // default constructor needs to be define manually as we have 3 parameter constructor. If no other param constructor then the compiler will construct a default constructor by default
            }

            @Override
            public String toString() {
                return data.toString();
            }
        }

        // O(n)
        public T get(int index) {
            Node <T> current = head;
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
            return current.data;
        }

        // O(1)
        public void addFirst(T data) {
            if(isEmpty()) {
                head = tail = new Node<>(data, null, null);
            } else {
                head.prev = new Node<>(data, null, head);
                head = head.prev;
            }
            size++;
        }

        // O(1)
        public void addLast(T data) {
            if(isEmpty()) {
                head = tail = new Node<>(data, null, null);
            } else {
                tail.next = new Node<>(data, null, tail); // by default tail.next is null
                tail = tail.next;
            }
            size++;
        }

        // O(1)
        public void add(T data){
            addLast(data);
        }

        // O(n)
        public void add(int index, T data) {
            if (index == 0) {
                addFirst(data);
            } else if (index == size) {
                addLast(data);
            } else if (index > size) {
                System.out.println("Index out of bounds");
            } else {
                Node <T> current = head;
                for (int i = 0; i < index-1; i++) {
                    current = current.next; // finally, we get i-1 node
                }
                // place newNode between current (i-1) and current.next (i) --> so, newNode will be at i
                Node <T> newNode = new Node<>(data, current.next, current);
                current.next.prev = newNode;
                current.next = newNode;

                size++;
            }
        }

        // O(1)
        public T peekFirst() {
            if(isEmpty()) throw new LinkedListEmptyException("List is empty");
            return head.data;
        }

        // O(1)
        public T peekLast() {
            if(isEmpty()) throw new LinkedListEmptyException("List is empty");
            return tail.data;
        }

        // O(1)
        public T removeFirst() {
            if(isEmpty()) throw new LinkedListEmptyException("List is empty");
            T data = head.data;
            head = head.next; // next can be null if only one node in the list
            size--;

            // what if we removed the node from the list with one node -- it'll become empty?
            if(isEmpty()) tail = null; // head is already null if empty from above head = head.next;
            else head.prev = null; // "!isEmpty()" means head != null

            return data;
        }

        // O(1)
        public T removeLast() {
            if(isEmpty()) throw new LinkedListEmptyException("List is empty");
            T data = tail.data;
            tail = tail.prev; // prev can be null if only one node in the list
            size--;

            // what if we removed the node from the list with one node -- it'll become empty?
            if(isEmpty()) head = null; // tail is already null if empty from above tail = tail.prev;
            else tail.next = null; // "!isEmpty()" means tail != null

            return data;
        }

        // O(1)
        public T remove(Node <T> node) { // we already know what that node is
            if(isEmpty()) throw new LinkedListEmptyException("List is empty");

            if (node.prev == null)  return removeFirst();
            else if (node.next == null) return removeLast();

            // skip this current node in adjacent nodes of it
            node.prev.next = node.next;
            node.next.prev = node.prev;
            T data = node.data;
            // memory clean up
            node.data = null;
            node = node.prev = node.next = null;

            size--;

            return data;
        }

        // O(n)
        public T removeAt(int index) {
            if(isEmpty()) throw new LinkedListEmptyException("List is empty");

            if(index < 0 || index >= size) throw new IndexOutOfBoundsException(); // or IllegalArgumentException

            // we can use one for loop or two for loops based on the index size like below
            int i;
            Node <T> trav;
            if(index < size/2) { // i.e first half
                for (i = 0, trav = head; i < index; i++) // loops up to i == index
                    trav = trav.next;
            } else {
                for (i=size-1, trav = tail; i > index; i--) // loops up to i == index
                    trav = trav.prev;
            }

            return remove(trav);
        }


        // O(n)
        public boolean remove(T data) {
            Node <T> trav = head;

            // Support Searching for null as data can be null but if Node is null then it has to be before head or after tail
            if(data == null) {
                for (trav = head; trav != null; trav = trav.next) { // similar to below commented while loop condition
                    if(trav.data == null) {
                        remove(trav); // remove(Node <T> node)
                        return true;
                    }
                }
            } else { // Search for non-null
                for (trav = head; trav != null; trav = trav.next) { // similar to below commented while loop condition
                    if(trav.data.equals(data) || trav.data == data) { // cause data can be Object
                        remove(trav); // remove(Node <T> node)
                        return true;
                    }
                }
            }

            return false;
        }

        // O(n*m)
        public boolean removeAll(T[] arr) { // array of data
            int removed = 0;
            Node <T> trav = head;
            while (trav != null) {
                for (int i = 0; i < arr.length; i++) {
                    if (trav.data == arr[i]) {
                        remove(trav);
                        removed++;
                    }
                }
                trav = trav.next;
            }
            return removed == arr.length;
        }

        // O(n)
        public void display() {
            Node <T> current = head;
            while (current != null) {
                System.out.print(current.data + " ");
                current = current.next;
            }
            System.out.println();
        }

        // O(1)
        public void clear() {
            Node <T> current = head; // trav / traverser
            while (current != null) { // use this while loop to clean up the memory but it is optional
                Node <T> next = current.next;
                current.data = null;
                current.prev = current.next = null;
                current = next;
            }
            head = tail = null;
            size = 0;
        }

        public int size() {
            return size;
        }

        public boolean isEmpty() {
            return size == 0; // head == null; won't work as non-circular linked list can have head == null
        }

        public int indexOf(T data) {
            Node <T> trav = head;
            int index = 0;

            // for null
            if (data == null) {
                for (trav = head; trav != null; trav = trav.next, index++) {
                    if (trav.data == null)
                        return index;
                }
            } else {
                while (trav != null) {
                    if (trav.data.equals(data))
                        return index;
                    trav = trav.next;
                    index++;
                }
            }
            return -1;
        }

        public int lastIndexOf(T data) {
            Node <T> current = head;
            int index = 0;
            int lastIndex = -1;
            while (current != null) {
                if (current.data == data) {
                    lastIndex = index;
                }
                current = current.next;
                index++;
            }
            return lastIndex;
        }

        public boolean contains(T data) {
            return indexOf(data) != -1;
        }

        public boolean contains2(T data) {
            Node <T> current = head;
            while (current != null) {
                if (current.data == data) {
                    return true;
                }
                current = current.next;
            }
            return false;
        }

        public boolean containsAll(T[] arr) {
            int found = 0;
            Node <T> current = head;
            while (current != null) {
                for (int i = 0; i < arr.length; i++) {
                    if (current.data == arr[i]) {
                        found++;
                    }
                }
                current = current.next;
            }
            return found == arr.length;
        }

        @Override public java.util.Iterator <T> iterator () {
            return new java.util.Iterator <T> () {
                private Node<T> trav = head;
                @Override public boolean hasNext() {
                    return trav != null;
                }

                @Override public T next() {
                    T data = trav.data;
                    trav = trav.next;
                    return data;
                }
            };
        }

        @Override public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("[");

            Node <T> trav = head;
            while (trav != null) {
                sb.append(trav.data + ", ");
                trav = trav.next;
            }

            sb.append("]");
            return sb.toString();
        }

        public void reverse() {
            Node <T> current = head;
            Node <T> previous = null;
            Node <T> next = null;
            while (current != null) {
                next = current.next;
                current.next = previous;
                previous = current;
                current = next;
            }
            head = previous;
        }

        @SuppressWarnings("unchecked")
        public T[] toArray() {
            T[] arr = (T[]) new Object[size];
            Node <T> current = head;
            int index = 0;
            while (current != null) {
                arr[index] = current.data;
                current = current.next;
                index++;
            }
            return arr;
        }

        public void sort() {
            Node <T> current = head;
            while (current != null) {
                Node <T> next = current.next;
                while (next != null) {
                    if (next.data instanceof Integer && current.data instanceof Integer) {
                        if ((int)current.data > (int)next.data) {
                            T temp = current.data;
                            current.data = next.data;
                            next.data = temp;
                        }
                    }
                    next = next.next;
                }
                current = current.next;
            }
        }
    }





    /**
     * -------------------------------------------------------------------------
     * -------------------------------------------------------------------------
     * SinglyCircularLinkedList
     * -------------------------------------------------------------------------
     * -------------------------------------------------------------------------
    */
    static class CircularLinkedList {
        private class Node {
            int value;
            Node nextNode;

            public Node(int value) {
                this.value = value;
            }
        }

        private Node head = null;
        private Node tail = null;

        public void addNode(int value) {
            Node newNode = new Node(value);
            if (head == null) {
                head = newNode;
            } else {
                tail.nextNode = newNode;
            }
            tail = newNode;
            tail.nextNode = head;
        }

        public boolean containsNode(int searchValue) {
            Node currentNode = head;

            if (head == null) {
                return false;
            } else {
                do {
                    if (currentNode.value == searchValue) {
                        return true;
                    }
                    currentNode = currentNode.nextNode;
                } while (currentNode != head);
                return false;
            }
        }

        public void deleteNode(int valueToDelete) {
            Node currentNode = head;
            if (head == null) { // the list is empty
                return;
            }
            do {
                Node nextNode = currentNode.nextNode;
                if (nextNode.value == valueToDelete) {
                    if (tail == head) { // the list has only one single element
                        head = null;
                        tail = null;
                    } else {
                        currentNode.nextNode = nextNode.nextNode;
                        if (head == nextNode) { //we're deleting the head
                            head = head.nextNode;
                        }
                        if (tail == nextNode) { //we're deleting the tail
                            tail = currentNode;
                        }
                    }
                    break;
                }
                currentNode = nextNode;
            } while (currentNode != head);
        }

        public void traverseList() {
            Node currentNode = head;

            if (head != null) {
                do {
                    System.out.print(currentNode.value + " ");
                    currentNode = currentNode.nextNode;
                } while (currentNode != head);
            }
        }
    }
}
