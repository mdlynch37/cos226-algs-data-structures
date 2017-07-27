/******************************************************************************
  *  Name:   Andy Guna 
  *  NetID:   guna@princeton.edu 
  *  Precept: P01
  *
  *  Partner Name:    N/A
  *  Partner NetID:   N/A
  *  Partner Precept: N/A
  * 
  *  Operating system: Windows
  *  Compiler: drjava
  *  Text editor / IDE: notepad++
  *
  *  Have you taken (part of) this course before: yes
  *  Have you taken (part of) the Coursera course Algorithm, Part I: no
  *
  *  Hours to complete assignment (optional):
  *
  * Description: This example demonstrates the use of WeightedUnionFindUF, StdRandom and Stopwatch
  * Classes. The code addresses the following problem. Given a set of N points, numbered
  * from 0 ... N-1, if any two points are randomly selected and merged, when will the
  * points 0 and N-1 be in the same set.
  *
  *******************************************************************************/
//???
import edu.princeton.cs.algs4.WeightedQuickUnionUF;
//import edu.princeton.cs.algs4.Stopwatch;
//import edu.princeton.cs.algs4.StdRandom;

public class Percolation {
    
    private boolean[][] grid;
    private int N;
    private WeightedQuickUnionUF sites;
    private int count; // number of open sites
    
    
    // converts 2d grid location to connection index used by eightedQuickUnionUF
    // uses 1 to N*N running left to right, top to bottom
    // ??own method at top?
    private int toL(int row, int col) {
        return N*row + col;
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

        sites = new WeightedQuickUnionUF(N*N);
        
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
        for (int i=N*N-N; i<N*N; i++) {
            for (int j=0; j<N; j++) {
                if (sites.connected(i, j)) return true;
            }
        }
        return false;
    }
    
    // test client???
    public static void main(String[] args) {

    }
}