/******************************************************************************
 *  Name:    Kevin Wayne
 *  NetID:   wayne
 *  Precept: P01
 *
 *  Partner Name:    N/A
 *  Partner NetID:   N/A
 *  Partner Precept: N/A
 * 
 *  Description:  Modeling Percolation like a boss.
 *                woot. woot.
 * 
 ******************************************************************************/

import java.util.Iterator;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;


public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] items;
    private int N; //     

    // construct an empty randomized queue
    public RandomizedQueue() {
        items = (Item[]) new Object[2];
    }
    
    // resize the underlying array holding the elements
    private void resize(int capacity) {
        assert capacity >= N;
        Item[] temp = (Item[]) new Object[capacity];
        for (int i = 0; i < N; i++) {
            temp[i] = items[i];
        }
        items = temp;
    }
    
    // is the queue empty?
    public boolean isEmpty() {
        return N == 0;
    }
    
    // add the item
    public void enqueue(Item item) {
        if (item == null) 
                throw new NullPointerException();

        // double size of array if necessary
        if (N == items.length) resize(2*items.length); 
        items[N++] = item; 
        
    }
    
    // return the number of items on the queue
    public int size() {
        return N;
    }
    
    // remove and return a random item
    public Item dequeue() {
            if (isEmpty()) 
                throw new NoSuchElementException("Stack underflow");
            
            int R = StdRandom.uniform(N);

            Item temp = items[R];
            items[R] = items[N-1];
            items[N-1] = null;  
            N--;
            
            // shrink size of array if necessary
            if (N > 0 && N == items.length/4) resize(items.length/2);
            return temp;
    }
    
    // return a random item (but do not remove it)
    public Item sample() {
        if (isEmpty()) 
                throw new NoSuchElementException("Stack underflow");
        
        int R = StdRandom.uniform(N);
            return items[R]; // one line of code?
    }
    
    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomQueueIterator();
    }

    // an iterator, doesn't implement remove() since it's optional
    private class RandomQueueIterator implements Iterator<Item> {
        private int k;
        private Integer[] index;


        public RandomQueueIterator() {
            index = new Integer[N];
            for (int i = 0; i < N; i++) {
                index[i] = i;
            }
            StdRandom.shuffle(index);
            k = 0;
        }

        public boolean hasNext() {
            return k < N;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            int a = index[k++];
            
            return items[a];
        }
    }
    
    // unit testing (required)
    public static void main(String[] args) {
        int N = Integer.parseInt(args[0]);
        
        // two independent deque linked lists to test both stack-like 
        // and queue-like functionality
        RandomizedQueue<Integer> rQueue1 = new RandomizedQueue<Integer>();
        RandomizedQueue<Integer> rQueue2 = new RandomizedQueue<Integer>(); 
        
        for (int i = 0; i < N; i++) {
            rQueue1.enqueue(i);
            rQueue2.enqueue(i);
        }
        
        StdOut.println();
        StdOut.println("Test deque for no repeat values: ");
        for (int i = 0; i < N; i++) {
            int a = rQueue1.dequeue(); 
            StdOut.print(a + " ");     
        }
        StdOut.println();
        
        StdOut.println("Test sample, can have repeat values: ");
        for (int i = 0; i < N; i++) {
            int a = rQueue2.sample(); 
            StdOut.print(a + " ");
        }
        
        StdOut.println();
        StdOut.println();
        
        String rQueue1Empty = Boolean.toString(rQueue1.isEmpty());
        StdOut.println("Dequeued queue empty (pass if true): " + rQueue1Empty);
        String rQueue2Empty = Boolean.toString(rQueue2.isEmpty());
        StdOut.println("Sampled queue empty (pass if false): " + rQueue2Empty);
        
        StdOut.println();
        
        StdOut.println("Test no repeating and random outputs after "
                           + "queueing values 0 through N: ");
        for (Integer i : rQueue2)
            StdOut.print(i + " ");  
        StdOut.println();
        StdOut.println();
        
        // empty to non-empty test 
        StdOut.println("Empty to non-empty test, output should be 1 through N");
        
        rQueue1Empty = Boolean.toString(rQueue1.isEmpty());
        StdOut.println("Starts empty (must be true): " + rQueue1Empty);
        for (int i = 0; i < N; i++) {
            rQueue1.enqueue(i);
            StdOut.print(rQueue1.dequeue() + " ");
        }
        StdOut.println();
        rQueue1Empty = Boolean.toString(rQueue1.isEmpty());
        StdOut.println("Ends with empty (must be true): " + rQueue1Empty);
        
        StdOut.println();
        
        //testing imdependence of iterators in nested call
        StdOut.println("Check independence of iterators, checking that: ");
            StdOut.println("1) no repeats in the first values of each set "
                           + "flowing down the column, and ");
            StdOut.println("2) no repeats in the second values of each set "
                           + "flowing right through the rows");
        for (Integer i : rQueue2) {
            for (Integer k : rQueue2) {
            StdOut.print(i + " - " + k + ", "); 
            
            }
            StdOut.println();
        }
        StdOut.println();
        StdOut.println("***TEST FINISHED***");
        StdOut.println();
    }
}