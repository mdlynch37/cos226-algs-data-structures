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
 *  Dependencies: RandomizedQueue.java
 * 
 ******************************************************************************/

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdIn;


public class Subset {
    public static void main(String[] args) {
        int k = Integer.parseInt(args[0]);
        RandomizedQueue<String> rQueue = new RandomizedQueue<String>();
        
        while (!StdIn.isEmpty()) {
            String s = StdIn.readString();
            rQueue.enqueue(s); // one line of code?
        }
        
        for (int i = 0; i < k; i++) {
            StdOut.println(rQueue.dequeue());
        }

        
        
        
    }
}