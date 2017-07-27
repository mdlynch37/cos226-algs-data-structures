/******************************************************************************
 *  Name:    Murray Lynch
 *  NetID:   mdlynch
 *  Precept: P04
 *
 *  Partner Name:    N/A
 *  Partner NetID:   N/A
 *  Partner Precept: N/A
 * 
 *  Description: Montecarlo simulation of T percolator models of size N by N.
 *  Tracks threshholds for percolation in each model to give an estimate of it
 *  in the end.
 ******************************************************************************/ 

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Stopwatch;


public class PercolationStats {
    //z-score for confidence interval (95%)
    private static final double DEGREE_OF_CONF = 1.96;
    
    private int T;
    private double[] percThresh; // results of each trial

    // perform T independent experiments on an N-by-N grid 
    public PercolationStats(int N, int T) {
        if ((N < 1) || (T < 1)) throw new IllegalArgumentException();
        
        this.T = T;
        percThresh = new double[T];
        
        for (int i = 0; i < T; i++) {
            Percolation perc = new Percolation(N);
            while (!perc.percolates()) {
                // selects a site at random and opens it
                int sx = StdRandom.uniform(N);
                int sy = StdRandom.uniform(N);
                perc.open(sx, sy);
            }  
            // records the percolation threshold for the experiment
            percThresh[i] = (double) perc.numberOfOpenSites()/(N*N);          
        }        
    }
     
    // calculates magnitude of half of confidence interval 
    private double partConf(double sd, int trials) {
        return sd*DEGREE_OF_CONF/Math.sqrt(trials);
    }
    
    // sample mean of percolation threshold
    public double mean() {             
        return StdStats.mean(percThresh);
    }
    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(percThresh);
    }
    
    // low  endpoint of 95% confidence interval
    public double confidenceLow() {
        return StdStats.mean(percThresh) - partConf(stddev(), T);
    }
    
    
    // high endpoint of 95% confidence interval
    public double confidenceHigh() {
        return StdStats.mean(percThresh) + partConf(stddev(), T);
    }

    // test client
    public static void main(String[] args) {
        Stopwatch stopwatch = new Stopwatch();
        
        int N = Integer.parseInt(args[0]);
        int T = Integer.parseInt(args[1]);
        PercolationStats percStats = new PercolationStats(N, T);
        
        StdOut.println();
        StdOut.println("mean()                  = " + percStats.mean()); 
        StdOut.println("stddev()                = " + percStats.stddev());
        StdOut.println("confidenceLow()         = " + percStats.confidenceLow());
        StdOut.println("confidenceHigh()        = " + percStats.confidenceHigh());
        StdOut.println();
        StdOut.println("Stopwatch()             = " + stopwatch.elapsedTime());
        StdOut.println();
        

    }   
}