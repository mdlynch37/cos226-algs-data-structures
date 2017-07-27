/******************************************************************************
 *  Name:    Murray Lynch
 *  NetID:   mdlynch
 *  Precept: P04
 *
 *  Partner Name:    N/A
 *  Partner NetID:   N/A
 *  Partner Precept: N/A
 * 
 *  Description: Models a percolation system by creating a grid with a back-end
 *  class of WeightedQuickUnionUF to track which adjacent open sites allow 
 *  liquid to fill from the top, and check to see if model has percolation.
 ******************************************************************************/

import java.util.Iterator;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdOut;

public class Deque<Item> implements Iterable<Item> {
    private int N;
    private Node pre;
    private Node post;
    
    private class Node {
        private Item item;
        private Node next;
        private Node prev;
    }
    
    // construct an empty deque
    public Deque() {
        pre = new Node();
        post = new Node();
        pre.next = post;
        post.prev = pre;
        N = 0;
    }
    
    // is the deque empty?
    public boolean isEmpty()  {
        return (N == 0);
    }
    
      // return the number of items on the deque
    public int size() {
        return N;
    }
    
    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) 
            throw new NullPointerException();
        
        Node oldFirst = pre.next;
        Node first = new Node();
        first.item = item;
        first.next = oldFirst;
        first.prev = pre;
        pre.next = first;
        oldFirst.prev = first;
        N++;
    }
        
   // add the item to the end     
    public void addLast(Item item) {
        if (item == null) 
            throw new NullPointerException();
        
        Node oldLast = post.prev;
        Node last = new Node();
        last.item = item;
        last.next = post;
        last.prev = oldLast;
        oldLast.next = last;
        post.prev = last;
        N++;
    }
     
    // remove and return the item from the front
    public Item removeFirst()      {
        if (isEmpty())
            throw new NoSuchElementException();
        
        Node first = pre.next; // to avoid a null return
        Node newFirst = pre.next.next;
        pre.next = newFirst;
        newFirst.prev = pre;
        N--;
        return first.item;
    }
    
    // remove and return the item from the end
    public Item removeLast()   {
        if (isEmpty())
            throw new NoSuchElementException();
        
        Node last = post.prev; //to avoid null
        Node newLast = post.prev.prev;
        post.prev = newLast;
        newLast.next = post;
        N--;
        return last.item;
    }
    
    // return an iterator over items in order from front to end
    public Iterator<Item> iterator() {
        return new ListIterator();
    }
    
    private class ListIterator implements Iterator<Item> {
        private Node current = pre.next;
        public boolean hasNext() {
            return (current.item != null);
        }
        public void remove() {
            throw new UnsupportedOperationException();
        }
        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            
            Item item = current.item;
            current = current.next;
            return item;
        }
        
    }
    
    // unit testing (required)
    public static void main(String[] args) {
        int N = Integer.parseInt(args[0]);
              
        // two independent deque linked lists to test both stack-like 
        // and queue-like functionality
        Deque<Integer> dequeStack = new Deque<Integer>(); 
        Deque<Integer> dequeQueue = new Deque<Integer>(); 
        
        for (int i = 0; i < N; i++) {
            dequeStack.addFirst(i); // push behavior in stack
            dequeQueue.addLast(i); // queue behavior for a queue
        }
        
        
        StdOut.println("Popping after pushing values 0 through N: ");
        for (Integer i : dequeStack)
            StdOut.print(i + " "); 
        StdOut.println();
        
        StdOut.println("'Dequeueing' (without removing) "
                           + "after queueing values 0 through N: ");
        for (Integer i : dequeQueue)
            StdOut.print(i + " ");  
        StdOut.println();
        
        // empty to non-empty test 
        StdOut.println("empty to non-empty test, output should be 1 through N");
        for (int i = 0; i < N; i++) {
            dequeStack.addFirst(i);
            StdOut.print(dequeStack.removeFirst() + " ");
        }
        
        StdOut.println();
        
        //testing imdependence of iterators in nested call
        StdOut.println("Popping after pushing values 0 through N: ");
        for (Integer i : dequeStack)
        for (Integer k : dequeStack) {
            StdOut.print(i + " - " + k); 
            StdOut.println();   
        }
        
    }
}