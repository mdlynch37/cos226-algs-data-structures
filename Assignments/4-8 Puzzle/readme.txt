/******************************************************************************
 *  Name: Murray Lynch      
 *  NetID: mdlynch
 *  Precept: P04
 *
 *  Partner Name: N/A      
 *  Partner NetID:      
 *  Partner Precept:    
 *
 *  Hours to complete assignment (optional):
 *
 ******************************************************************************/


/******************************************************************************
 *  Explain briefly how you implemented the board data type.
 *****************************************************************************/

In the contractor, most of the work is done. Priority functions are calculated,
and index of blank space saved as instance for later use by equals() function. 
Iterator function is straight-forward. Equals() saved time by breaking as soon
 as mismatch is found.



/******************************************************************************
 *  Explain briefly how you represented a search node
 *  (board + number of moves + previous search node).
 *****************************************************************************/

Using a linking node class nested in Solver, with instance variables for each
of the above characteristics.





/******************************************************************************
 *  Explain briefly how you detected unsolvable puzzles. What is the
 *  order of growth of the runtime of your isSolvable() method?
 *****************************************************************************/

Inversions were calculated by incrementing a counter for each tile pair where
the tile with a higher number is found before the other tile (in order of array
 index). An extra variable z with the various loops is used to make sure tiles 
located before the current tile on the same row are not compared. 

Boards with even N were treated slightly differently than those with 
odd N, as prescribed by the assignment description.

Because there are four levels of for loops, each nested within an outer,
and because each of those for loops could run a maximum of N times,
order of growth in the worst case is N^4.


/******************************************************************************
 *  For each of the following instances, give the minimal number of 
 *  moves to reach the goal state. Also give the amount of time
 *  it takes the A* heuristic with the Hamming and Manhattan
 *  priority functions to find the solution. If it can't find the
 *  solution in a reasonable amount of time (say, 5 minutes) or memory,
 *  indicate that instead. Note that you may be able to solve
 *  puzzlexx.txt even if you can't solve puzzleyy.txt even if xx > yy.
 *****************************************************************************/


                  number of          seconds
     instance       moves      Hamming     Manhattan
   ------------  ----------   ----------   ----------
   puzzle28.txt  28		0.83	   0.01
   puzzle30.txt  30		3.02	   0.03
   puzzle32.txt  32		*mem	   0.84
   puzzle34.txt  34			   0.20
   puzzle36.txt  36			   7.47
   puzzle38.txt  38			   3.97
   puzzle40.txt  40			   0.52
   puzzle42.txt  42			   2.30

* too much memory

/******************************************************************************
 *  If you wanted to solve random 4-by-4 or 5-by-5 puzzles, which
 *  would you prefer:  a faster computer (say, 2x as fast), more memory
 *  (say 2x as much), a better priority queue (say, 2x as fast),
 *  or a better priority function (say, one on the order of improvement
 *  from Hamming to Manhattan)? Why?
 *****************************************************************************/

A faster computer because this would increase speed performance the most.
Increased memory would not have any effect on the performance of such small
puzzles.
The binary heap technique in MinPQ already has logarithmic performance, so 
any improvements to it would be very negligible. 
A better priority function would not likely improvement selection so much as
to half processing time.





/******************************************************************************
 *  Known bugs / limitations.
 *****************************************************************************/



/******************************************************************************
 *  Describe whatever help (if any) that you received.
 *  Don't include readings, lectures, and precepts, but do
 *  include any help from people (including course staff, lab TAs,
 *  classmates, and friends) and attribute them by name.
 *****************************************************************************/

Used “s.append(String.format("%2d ", tileAt(i, j);” from checklist
Serena, lab TA.



/******************************************************************************
 *  Describe any serious problems you encountered.                    
 *****************************************************************************/

Submission page would occasionally give a dialogue box “taking too long”.
No feedback was displayed, but one check was add to the total. While I reached
the limit of 10, I actually only received feedback about 6 times.
As a result, final changes to both classes and readme not checked.


/******************************************************************************
 *  If you worked with a partner, assert below that you followed
 *  the protocol as described on the assignment page. Give one
 *  sentence explaining what each of you contributed.
 *****************************************************************************/







/******************************************************************************
 *  List any other comments here. Feel free to provide any feedback   
 *  on how much you learned from doing the assignment, and whether    
 *  you enjoyed doing it.                                             
 *****************************************************************************/
