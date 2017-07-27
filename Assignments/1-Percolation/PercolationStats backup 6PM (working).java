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
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private int T;
    private double mean;
    private double[] percThresh;
    private double DEGREE_OF_CONF = 1.96; //z-score for confidence interval 95%
    
    // calculates half size of confidence interval 
    private double partConf(double sd, int T) {
        return sd*DEGREE_OF_CONF/Math.sqrt(T);
    }

    // perform T independent experiments on an N-by-N grid 
    public PercolationStats(int N, int T) {
        if ((N < 1) || (T < 1)) throw new IllegalArgumentException();
        this.T = T;
        
        percThresh = new double[T];
        
        for (int i = 0; i<T; i++) {
            
            Percolation perc = new Percolation(N);
            
            boolean percolates;
            
            
            while (!perc.percolates()) {
                //?? first decl. could be outside loop
                int sx = StdRandom.uniform(N);
                int sy = StdRandom.uniform(N);
                
                //StdOut.println(sy);
          
                
                perc.open(sx, sy);
            }
            
            
            percThresh[i] = (double)perc.numberOfOpenSites()/(N*N);
            
        }
        
        //for (int j=0;j<T;j++) StdOut.println(percThresh[j]);
        mean = StdStats.mean(percThresh);
    }
    
    // sample mean of percolation threshold
    public double mean() {             
        return mean;
    }
    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(percThresh);
    }
    
    // low  endpoint of 95% confidence interval
    public double confidenceLow() {
        return mean - partConf(stddev(), T);
    }
    
    
    // high endpoint of 95% confidence interval
    public double confidenceHigh() {
        return mean + partConf(stddev(), T);
    }
    
    
    
    // test client???
    public static void main(String[] args) {
        int N = Integer.parseInt(args[0]);
        int T =Integer.parseInt(args[1]);
        PercolationStats percStats = new PercolationStats(N, T);
        
        StdOut.println();
        StdOut.println("mean()                  = " + percStats.mean()); 
        StdOut.println("stddev()                = " + percStats.stddev());
        StdOut.println("confidenceLow()         = " + percStats.confidenceLow());
        StdOut.println("confidenceHigh()        = " + percStats.confidenceHigh());
        StdOut.println();

    }   
}