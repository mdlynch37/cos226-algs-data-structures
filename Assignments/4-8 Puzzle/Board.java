/******************************************************************************
 *  Name:    Murray Lynch
 *  NetID:   mdlynch
 *  Precept: P04
 *
 *  Partner Name:    N/A
 *  Partner NetID:   N/A
 *  Partner Precept: N/A
 * 
 *  Description:  This immutable data type creates an object that represents a
 *  tiled board in its current unsolved or solved state. In addition to the
 *  location of each tile on the board, this Board object stores the hamming
 *  and manhattan distances from the empty space on the baord (signified by 0)
 *  as well as the x and y coordinates of that empty space.
 * 
 ******************************************************************************/

import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.In;

public class Board 
{
    private final int[][] tiles; // N-by-N sized board, with co-ordinates [y][x]
    private final int N; // length and width of board
    private final int hamDist, manDist; // Manhattan and hamming function values
    private int emptyX, emptyY; // coordinates of empty space on board
    
    // construct a board from an N-by-N array of tiles
    // (where tiles[i][j] = tile at row i, column j)
    public Board(int[][] tiles)
    {
        // validate input
        if (tiles == null)
            throw new NullPointerException();
        
        N = tiles.length; // returns num of row which equal num of columns
        
        // creates defensive copy to maintain immutability of Board
        this.tiles = copyOfTiles(tiles);
        
        hamDist = calcHamming();
        manDist = calcManhattan();
    }
    
    // is the tile at [i][j] in its right position?
    private boolean hasGoalPosition(int i, int j)
    {
        // covers case when tile is blank, marked by 0
        if (tiles[i][j] != 0) return (tiles[i][j] == i*N + (j+1));
        else return (N*N == i*N + (j+1));
    }
    
    // returns the x-coordinate where val must be on goal board
    private int goalPosX(int val) 
    {
        return (val-1) % N;
    }
    
    // returns the y-coordinate where val must be on goal board
    private int goalPosY(int val) 
    {
        return (val-1) / N;
    }
    
    // returns the board's hamming number, 
    // i.e. number of tiles in the wrong position
    // (used at initialization of board)
    private int calcHamming()
    {
        int count = 0;
        
        // look at each space on the board for tiles out of place
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (tiles[i][j] == 0) continue; // skip if no tile at i, j
                if (!hasGoalPosition(i, j)) count++; // count tiles out of place
            }
        }
        
        return count;
    }
    
    // returns the sum of the board's manhattan distances,
    // i.e. the sum of the vertical and horizontal distances
    // from each tile's goal position
    // (used at initialization of board)
    private int calcManhattan()
    {
        int sum = 0;
        
        // look at each space on the board
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                int val = tiles[i][j];
                // increments sum counter with x and y distances from goal
                // unless empty space at [i][j], then assign coordinates 
                // to instance variables for later use
                if (val == 0) {
                    emptyY = i;
                    emptyX = j;
                }
                else {
                    sum += Math.abs(i - goalPosY(val));
                    sum += Math.abs(j - goalPosX(val));
                }
            }
        }
        
        return sum;
    }
    
    // make an exact copy of instance var, tiles[][]
    private int[][] copyOfTiles(int[][] tilesToCopy) {
        int[][] newTiles = new int[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                newTiles[i][j] = tilesToCopy[i][j];
            }
        }
        return newTiles;
    }
    
    // return tile at row i, column j (or 0 if blank)
    public int tileAt(int i, int j)
    {
        if (i < 0 || i > N-1) throw new IndexOutOfBoundsException();
        if (j < 0 || j > N-1) throw new IndexOutOfBoundsException();
        
        return tiles[i][j];
    }
    
    // board size N
    public int size()
    {
        return N;
    }
    
    // number of tiles out of place
    public int hamming()
    {
        return hamDist;
    }
    
    // sum of Manhattan distances between tiles and goal
    public int manhattan()
    {
        return manDist;
    }
    
    // is this board the goal board?
    public boolean isGoal()
    {
        return (manDist == 0);
    }
    
    // is this board solvable?
    public boolean isSolvable()
    {
        
        int inversions = 0; // inversion counter
        // 1st set of loops looks at each tile
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                int val = tiles[i][j];
                if (val == 0) continue; 
                int z = j+1; // start comparing values after [i][j] on same row
                // 2nd set compares tile at [i][j] to each tile after it
                for (int k = i; k < N; k++) {
                    for (int l = z; l < N; l++) {
                        int toCompare = tiles[k][l];
                        if (toCompare == 0) continue; // do not include empty spaces
                        if (val > toCompare) inversions++;
                    }
                    z = 0; // for rows following i, start at first column
                }
            }
        }
        
        
        // if N is odd, determine solvability only with number of inversions
        // solvable if even, unsolvable if odd
        if (Math.abs(N % 2) == 1) {
            return (Math.abs(inversions % 2) == 0);
        }
        // otherwise include the row of the empty space in determination
        // solvable if odd, unsolvable if even
        else 
        {
            return (Math.abs((inversions+emptyY) % 2) == 1);
        }
        
    }
    
    // does this board equal y?
    public boolean equals(Object y)
    {
        if (this == y) return true; // check reference to same object
        if (y == null) return false; // checks null reference
        if (this.getClass() != y.getClass()) return false; // checks same class
        Board that = (Board) y;
        if (this.N != that.size()) return false; // checks board size parity
        
        boolean equals = true; // default is equal until proven otherwise
        
        // check that each coordinate on both boards, returning false
        // right after first inequality between boards is found
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (that.tiles[i][j] != this.tiles[i][j]) {
                    equals = false;
                    break;
                }
            }
            if (!equals) break; // break outer loop if inner is broken
        }
        return equals;
    }
    
    // creates an iterable stack of the resulting boards created 
    // by eash possible move of a tile into the empty space
    public Iterable<Board> neighbors()
    {
        Stack<Board> neighbors = new Stack<Board>();
        
        // move tile right of empty space (if it exists)
        if (emptyX > 0) {
            int[][] newTiles = copyOfTiles(tiles);
            // swap tiles
            newTiles[emptyY][emptyX] = newTiles[emptyY][emptyX-1];
            newTiles[emptyY][emptyX-1] = 0;
            
            Board neighbor = new Board(newTiles);
            neighbors.push(neighbor);
        }
        
        // move tile left of empty space (if it exists)
        if (emptyX < N-1) {
            int[][] newTiles = copyOfTiles(tiles);
            // swap tiles
            newTiles[emptyY][emptyX] = newTiles[emptyY][emptyX+1];
            newTiles[emptyY][emptyX+1] = 0;
            
            Board neighbor = new Board(newTiles);
            neighbors.push(neighbor);
        }
        // move tile above empty space (if it exists)
        if (emptyY < N-1) {
            int[][] newTiles = copyOfTiles(tiles);
            // swap tiles
            newTiles[emptyY][emptyX] = newTiles[emptyY+1][emptyX];
            newTiles[emptyY+1][emptyX] = 0;
            
            Board neighbor = new Board(newTiles);
            neighbors.push(neighbor);
        }
        // move tile below empty space (if it exists)
        if (emptyY > 0) {
            int[][] newTiles = copyOfTiles(tiles);
            // swap tiles
            newTiles[emptyY][emptyX] = newTiles[emptyY-1][emptyX];
            newTiles[emptyY-1][emptyX] = 0;
            
            Board neighbor = new Board(newTiles);
            neighbors.push(neighbor);
        }
        
        return neighbors;
    }
    
    // string representation of this board
    public String toString()
    {
        StringBuilder s = new StringBuilder(N + "\n");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                s.append(String.format("%2d ", tileAt(i, j)));
            }
            s.append("\n");
        }
        
        return s.toString();
    }
    
    // unit testing (not graded)
    public static void main(String[] args)
    {        
        // create initial board from file
        In in = new In(args[0]);
        int N = in.readInt();
        int[][] tiles = new int[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
            tiles[i][j] = in.readInt();
        System.out.println(N);
        Board initial = new Board(tiles);
        
        System.out.println(initial.hamDist);
        
        /*
         Iterable<Board> neighbors = initial.neighbors();
         System.out.println(initial.toString());
         System.out.println("ham = " + initial.hamming());
         System.out.println("man = " + initial.manhattan());
         System.out.println();
         for (Board b : neighbors) {
         System.out.println(b.toString());
         System.out.println("ham = " + b.hamming());
         System.out.println("man = " + b.manhattan());
         System.out.println();
         }
         
         
         System.out.println(initial.isGoal());
         System.out.println(initial.tiles[1][0]);
         */
        
        
    }
}
