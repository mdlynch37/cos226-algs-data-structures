/******************************************************************************
 *  Name:    Murray Lynch
 *  NetID:   mdlynch@princeton.edu
 *  Precept: P04
 *
 *  Partner Name:    N/A
 *  Partner NetID:   N/A
 *  Partner Precept: N/A
 * 
 *  Operating system: 	OS X 10.10.5
 *  Compiler:		DrJava
 *  Text editor / IDE:	DrJava
 *
 *  Have you taken (part of) this course before: No
 *  Have you taken (part of) the Coursera course Algorithm, Part I: No
 *
 *  Hours to complete assignment (optional): 13
 *
 ******************************************************************************/


/******************************************************************************
 *  Describe how you implemented Percolation.java. How did you check
 *  whether the system percolates?
 *****************************************************************************/

Key was to use a virtual top and bottom row, each represented with one node
and connected to the top and bottom row res. at initialization.

/******************************************************************************
 *  Using Percolation with QuickFindUF.java, give a formula (using tilde
 *  notation) for the running time (in seconds) of PercolationStats.java
 *  as a function of both N and T.
 *
 *  Model the running time as a power law and be sure to give both the
 *  coefficient and exponent of the leading term. Your coefficients
 *  should be based on empirical data and rounded to two significant
 *  digits, such as 5.3*10^-8 * N^5.0 T^1.5.
 *****************************************************************************/

(keep T constant)  T = 1010

 N          time (seconds)
------------------------------
100	    1.034
200         6.033
400         47.443
800   	    > 300
1600        > 300


(keep N constant)  N = 505

 T          time (seconds)
------------------------------
10	    1.18
20          2.005
40          4.369
80   	    8.092
160         16.409


running time as a function of N and T:  ~ 


/******************************************************************************
 *  Repeat the previous question, but use WeightedQuickUnionUF.java.
 *****************************************************************************/

(keep T constant)  T = 2000

 N          time (seconds)
------------------------------
100         1.056
200	    4.621
400         110.41
800         21.066
1600   	    > 300


(keep N constant)  N = 200

 T          time (seconds)
------------------------------
500	    1.18
1000        2.359
2000        4.666
4000   	    9.026
8000        17.868


running time as a function of N and T:  ~ 




/******************************************************************************
 *  After reading the course collaboration policy, answer the
 *  following short quiz. This counts for a portion of your grade.
 *  Write down the answers in the space below.
 *****************************************************************************/
1. (b)
2. (c)

1. How much help can you give a fellow student taking COS 226?
(a) None. Only the preceptors and lab TAs can help.
(b) You can discuss ideas and concepts but students can get help
    debugging their code only from a preceptor, lab TA, or
    student who has already passed COS 226.
(c) You can help a student by discussing ideas, selecting data
    structures, and debugging their code.
(d) You can help a student by emailing him/her your code.

2. What are the rules when partnering?
 (a) You and your partner must both be present while writing code.
     But after that only one person needs to do the analysis.
 (b) You and your partner must both be present while writing code
     and during the analysis, but, after that, only one person
     needs to be present while submitting the code and the
     readme.
 (c) You and your partner must both be present while writing code,
     during the analysis, and while submitting the code and the
     readme. Failure to do so is a violation of the course
     collaboration policy.
 
/******************************************************************************
 *  Known bugs / limitations.
 *****************************************************************************/




/******************************************************************************
 *  Describe whatever help (if any) that you received.
 *  Don't include readings, lectures, and precepts, but do
 *  include any help from people (including course staff, lab TAs,
 *  classmates, and friends) and attribute them by name.
 *****************************************************************************/

Maia Ginsburg reminded me about the virtual top row discussed in precept.

/******************************************************************************
 *  Describe any serious problems you encountered.                    
 *****************************************************************************/




/******************************************************************************
 *  List any other comments here. Feel free to provide any feedback   
 *  on how much you learned from doing the assignment, and whether    
 *  you enjoyed doing it.                                             
 *****************************************************************************/

