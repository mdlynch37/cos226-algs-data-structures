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

public class Percolation {
    
    private boolean[][] grid;
    private int N;
    private WeightedQuickUnionUF links;
    private WeightedQuickUnionUF linksNoBackwash;
    private int numSites;
    
    // index numbers of first and last nodes (virtual sites)
    private int topVNode;
    private int botVNode;
    
    // create N-by-N grid, with all links initially blocked
    public Percolation(int N) {
        if (N < 1) throw new IllegalArgumentException();
        
        this.N = N;
        grid = new boolean[N][N]; // initialized all links to false (default)
        
        links = new WeightedQuickUnionUF(N*N+2); // incl. virtual row nodes
        // for backwash issue, mirror of links except without bottom virtual row
        linksNoBackwash = new WeightedQuickUnionUF(N*N+2);
        
        topVNode = 0;
        botVNode = N*N + 1;
        
        // connects virtual row nodes to top and bottom sites
        for (int i = 0; i < N; i++) {
            links.union(i+1, topVNode);
            linksNoBackwash.union(i+1, topVNode);
            links.union(botVNode-1-i, botVNode);
        }
    }
    
    // converts 2d grid location to 1d index used by WeightedQuickUnionUF
    private int toLink(int row, int col) {
        return N*row + col + 1; // accounts for virtual top row
    }
    
    // validates site location exists
    private void outOfBoundCheck(int row, int col) {
        if ((row > N-1)  || (col > N-1)  || (row < 0)  || (col < 0))
            throw new IndexOutOfBoundsException();
    }
    
    private void unionMirrored(int nodeA, int nodeB) {
        links.union(nodeA, nodeB);
        linksNoBackwash.union(nodeA, nodeB);
    }
    
    // open the site (row, col) if it is not open already
    public void open(int row, int col) {
        outOfBoundCheck(row, col);
        
        if (!grid[row][col]) {
            grid[row][col] = true;
            numSites++;
            
            // if an adjacent site exists and is open, link them
            if (row < N-1)
                if (grid[row+1][col]) 
                unionMirrored(toLink(row, col), toLink(row+1, col));
            if (row > 0)
                if (grid[row-1][col]) 
                unionMirrored(toLink(row, col), toLink(row-1, col));
            if (col < N-1)
                if (grid[row][col+1]) 
                unionMirrored(toLink(row, col), toLink(row, col+1));
            if (col > 0)
                if (grid[row][col-1]) 
                unionMirrored(toLink(row, col), toLink(row, col-1));
        }
    }
    
    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        outOfBoundCheck(row, col);
        return grid[row][col];
    }
    
    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        outOfBoundCheck(row, col);
        if (isOpen(row, col)) 
            if (linksNoBackwash.connected(topVNode, toLink(row, col)))
            return true;
        return false;
    }
    
    // number of open links    
    public int numberOfOpenSites() {
        return numSites;
    }
    
    // does the system percolate?
    public boolean percolates() {
        // for corner case N = 1
        if (N == 1)
            return grid[0][0];
        
        if (links.connected(topVNode, botVNode)) return true;
        return false;
    }
}