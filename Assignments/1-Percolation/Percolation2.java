/******************************************************************************
 *  Name:    Murray Lynch 
 *  NetID:   mdlynch@princeton.edu 
 *  Precept: P04
 *
 *  Partner Name:    N/A
 *  Partner NetID:   N/A
 *  Partner Precept: N/A
 * 
 *  Operating system:  OS X 10.10.5
 *  Compiler:          DrJava
 *  Text editor / IDE: DrJava
 *
 *  Have you taken (part of) this course before: No
 *  Have you taken (part of) the Coursera course Algorithm, Part I: No
 *
 *  Hours to complete assignment (optional): 
 *
 *  Description: 
 *
*******************************************************************************/

import edu.princeton.cs.algs4.WeightedQuickUnionUF;
import edu.princeton.cs.algs4.Stopwatch;

public class Percolation {
    
    private boolean[][] grid;
    private int N;
    private WeightedQuickUnionUF sites;
    private int count; // number of open sites
    private int topNode;
    private int bottomNode;
    
    
    // converts 2d grid location to connection index used by eightedQuickUnionUF
    // uses 1 to N*N running left to right, top to bottom
    // ??own method at top?
    private int toL(int row, int col) {
        return N*row + col + 1; //to account for virtual top row
    }
    
    private void outOfBoundCheck(int row, int col) {
        // if outside of bounds ??? proper notation??
        if ((row > N-1)  || (col > N-1)  || (row < 0)  || (col < 0))
            throw new IndexOutOfBoundsException();
    }
    
    // create N-by-N grid, with all sites initially blocked
    public Percolation(int N) {
        if (N < 1) throw new IllegalArgumentException();
        
        grid = new boolean[N][N]; // initialized all sites to false

        sites = new WeightedQuickUnionUF(N*N+2); //to create two virtual rows
        
        topNode = 0; //at top declaration? as constant?
        bottomNode = N*N + 1;
        
        for (int i = 0; i < N; i++) { //connect them
            sites.union(i+1, topNode); 
            sites.union(bottomNode-1-i, bottomNode);
        }

        this.N = N;
    }
    
    // open the site (row, col) if it is not open already
    public void open(int row, int col) {
        outOfBoundCheck(row, col);
        
        if (grid[row][col] == false) {
            grid[row][col] = true;
            count++;
            
            // connect to adjacent sites ??not elegant
            if (row < N-1)
                if (grid[row+1][col] == true) 
                sites.union(toL(row, col), toL(row+1, col));
            if (row > 0)
                if (grid[row-1][col] == true) 
                sites.union(toL(row, col), toL(row-1, col));
            if (col < N-1)
                if (grid[row][col+1] == true) 
                sites.union(toL(row, col), toL(row, col+1));
            if (col > 0)
                if (grid[row][col-1] == true) 
                sites.union(toL(row, col), toL(row, col-1));
        }
    }
    
    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        outOfBoundCheck(row, col);
        return grid[row][col];
    }
    
    // is the site (row, col) full?
    //need to check if open first??? no
    public boolean isFull(int row, int col) {
        outOfBoundCheck(row, col);
        for (int i=0; i<N; i++) {
            if ((sites.connected(i, toL(row, col))) && (isOpen(row, col))) 
                return true;
        }
        
        return false;
    }
    
    // number of open sites    
    public int numberOfOpenSites() {
        return count;
    }
    
    // does the system percolate?
    public boolean percolates() {
        if (sites.connected(topNode, bottomNode)) return true;
        //else?
        return false;
    }
    
    // test client???
    public static void main(String[] args) {
        int N = Integer.parseInt(args[0]);
        
        Percolation perc = new Percolation(N);
        StdOut.println(perc.sites.connected(perc.topNode, perc.bottomNode));
        StdOut.println(perc.topNode);
        StdOut.println(perc.bottomNode);
        StdOut.println();
        
        for (int i = 0; i < N+1; i++) {
            StdOut.println(perc.sites.find(i));

        }
        StdOut.println();
        for (int i = 0; i < N+1; i++) {
            
            StdOut.println(perc.sites.find(N*N+1-i));
        }
        
        StdOut.println();
        for (int i = 0; i < N*N+2; i++) {
            StdOut.println(i + " -> " + perc.sites.find(i));
        }
        
        
    }
}