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
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    
    private boolean[][] grid;
    private int N;
    private WeightedQuickUnionUF links; // tracks relationship between sites
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
        
        topVNode = 0;
        botVNode = N*N + 1;
        
        // connects virtual row nodes to top and bottom sites
        for (int i = 0; i < N; i++) {
            links.union(i+1, topVNode); 
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
    
    // open the site (row, col) if it is not open already
    public void open(int row, int col) {
        outOfBoundCheck(row, col);
        
        if (!grid[row][col]) {
            grid[row][col] = true;
            numSites++;
            
            // if an adjacent site exists and is open, link them
            if (row < N-1)
                if (grid[row+1][col]) 
                links.union(toLink(row, col), toLink(row+1, col));
            if (row > 0)
                if (grid[row-1][col]) 
                links.union(toLink(row, col), toLink(row-1, col));
            if (col < N-1)
                if (grid[row][col+1]) 
                links.union(toLink(row, col), toLink(row, col+1));
            if (col > 0)
                if (grid[row][col-1]) 
                links.union(toLink(row, col), toLink(row, col-1));
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
        if (isOpen(row, col) && links.connected(topVNode, toLink(row, col)))
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