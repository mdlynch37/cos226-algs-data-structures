/******************************************************************************
 *  Name:    Murray Lynch
 *  NetID:   mdlynch
 *  Precept: P04
 *
 *  Partner Name:    N/A
 *  Partner NetID:   N/A
 *  Partner Precept: N/A
 * 
 *  Description:  Solver uses the A* search algorithm and a priority function 
 *  (Hamming or Manhattan) as a more effective method of finding a solution to
 *  the 8-puzzle than using brute force. Using linked nodes as well as a 
 *  priority queue, this searches branches of possible next moves, prioritizing
 *  those next moves that minimize the priority function.
 * 
 ******************************************************************************/

import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import java.util.Comparator;


public class Solver 
{
    private SearchNode step; // tracks steps toward goal board
    private int moves; // number of moves made from initial board to goal
    private SearchNode goalNode; // node with goal board
    
    // nodes create a linked branches of possible next moves
    private class SearchNode {
        private Board board;
        private int dist; // distance from initial board
        private SearchNode prev; // previous board
    }
    
    // compares two terms in descending order by hamming priority function
    private class HammingPriority implements Comparator<SearchNode> {
        public int compare(SearchNode sn1, SearchNode sn2) {
            // calc hamming priority function for each search node
            int hp1 = sn1.dist + sn1.board.hamming();
            int hp2 = sn2.dist + sn2.board.hamming();
            
            if (hp1 > hp2) return 1;
            if (hp1 < hp2) return -1;
            return 0;
        }
    }
    // compares two terms in descending order by manhattan priority function  
    private class ManhattanPriority implements Comparator<SearchNode> {
        public int compare(SearchNode sn1, SearchNode sn2) {
            // calc manhattan priority function for each search node
            int hp1 = sn1.dist + sn1.board.manhattan();
            int hp2 = sn2.dist + sn2.board.manhattan();
            
            if (hp1 > hp2) return 1;
            if (hp1 < hp2) return -1;
            return 0;
        }
    }
    
    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial)
    {
        // validate input to Solver
        if (initial == null) throw new NullPointerException();
        if (!initial.isSolvable()) throw new IllegalArgumentException();
        
        // create first search node with pointer used for each iteration of A*
        step = new SearchNode();
        step.board = initial;
        
        // create new priority queue specifying the priority function
        MinPQ<SearchNode> pq = new MinPQ<SearchNode>(new HammingPriority());

        // run A-star search algorithm until goal board is dequeued
        while (!step.board.isGoal()) {
            // runs iterable of neighbors and adds non duplicates to pq
            Iterable<Board> neighbors = step.board.neighbors();
            for (Board b : neighbors) {
                // skips if possible board is duplicate of 2nd board back
                if (step.prev != null) // check not needed for first iteration
                    if (b.equals(step.prev.board)) continue;
                
                // creates possible board's node and adds to pq
                SearchNode nextPossibleBoard = new SearchNode();
                nextPossibleBoard.board = b;
                nextPossibleBoard.prev = step;
                nextPossibleBoard.dist = step.dist + 1;
                pq.insert(nextPossibleBoard);
            }
            step = pq.delMin(); // dequeue next step toward goal board
        }
        
        moves = step.dist;
        goalNode = step;

        
    }
    
    // min number of moves to solve initial board
    public int moves()
    {
        return moves;
    }
    
    // sequence of boards in a shortest solution
    public Iterable<Board> solution()
    {
        Stack<Board> solution = new Stack<Board>();
        
        // for case where initial board is goal board
        if (moves == 0) {
            solution.push(goalNode.board);
            return solution;
        }

        step = goalNode;
        
        // add chain of previous node from goal node 
        while (step != null) {
            solution.push(step.board);
            step = step.prev;
        }
        
        return solution;
    }
    
    // solve a slider puzzle (given below)     
    public static void main(String[] args) {

    // create initial board from file
    In in = new In(args[0]);
    int N = in.readInt();
    int[][] tiles = new int[N][N];
    for (int i = 0; i < N; i++)
        for (int j = 0; j < N; j++)
            tiles[i][j] = in.readInt();
    Board initial = new Board(tiles);

    // check if puzzle is solvable; if so, solve it and output solution
    if (initial.isSolvable()) {
        Solver solver = new Solver(initial);
        StdOut.println("Minimum number of moves = " + solver.moves());
        for (Board board : solver.solution())
            StdOut.println(board);
    }

    // if not, report unsolvable
    else {
        StdOut.println("Unsolvable puzzle");
    }
}
    
}
