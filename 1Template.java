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
 * Description: 
 *
*******************************************************************************/
import edu.princeton.cs.algs4.WeightedQuickUnionUF;
import edu.princeton.cs.algs4.Stopwatch;
import edu.princeton.cs.algs4.StdRandom;
 
public class UFExample1 {
    public static void main(String[] args) { 
        Stopwatch Clock = new Stopwatch();
        int N = Integer.parseInt(args[0]);
        int nexttolast = N-1;
        WeightedQuickUnionUF UF1 = new WeightedQuickUnionUF(N);
        while (true) {
           int i = StdRandom.uniform(N);
           int j = StdRandom.uniform(N);
           if (!UF1.connected(i,j)){
              UF1.union(i,j);
              System.out.println("connecting " + i + " and " + j);
           }
           if (UF1.connected(0,N-1)) {
             System.out.println("\n EXITING ... now  0 and " + nexttolast + " are connected ");
             break;
           }
        } 
        System.out.println("The elapsed time is " + Clock.elapsedTime());
       
    }
}