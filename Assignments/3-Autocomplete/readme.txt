/******************************************************************************
 *  Name:       Linhchi Nguyen
 *  NetID:      lbn
 *  Precept:    P03
 *
 *  Partner Name:    Murray Lynch     
 *  Partner NetID:   mdlynch
 *  Partner Precept: P04
 *
 *  Hours to complete assignment (optional):
 *
 ******************************************************************************/


/******************************************************************************
 *  Describe how your firstIndexOf() method in BinarySearchDeluxe.java
 *  finds the first index of a key that equals the search key.
 *****************************************************************************/
We use a recursive search that halves its search range depending on the key’s 
relationship to the midpoint of the current search range. If the key is equal 
or less than the midpoint, the new range is the lower half of the current 
range. Otherwise, it is the upper half. Once there are two elements in the 
range, we use boolean logic to determine which element is the first index. 
This last step of determining the final result could have occurred when the 
recursive search method is applied to one element as both the lower and 
upper range if we coded two distinct search methods instead of one that could
be adapted for firstIndexOf() and lastIndexOf().

/******************************************************************************
 *  What is the order of growth of the number of compares (in the
 *  worst case) that each of the operations in the Autocomplete
 *  data type make, as a function of the number of terms N and the
 *  number of matching terms M?
 *
 *  Recall that with order-of-growth notation, you should discard
 *  leading coefficients and lower order terms, e.g., M^2 + M log N.
 *****************************************************************************/

constructor: N log N
from mergesort of terms

allMatches(): log N + M log M
from 2 * (log N) + 1 for search, M log M for mergesort of matching terms

numberOfMatches(): log N
from 2 * (log N) + 1 for search




/******************************************************************************
 *  Known bugs / limitations.
 *****************************************************************************/


/******************************************************************************
 *  Describe whatever help (if any) that you received.
 *  Don't include readings, lectures, and precepts, but do
 *  include any help from people (including course staff, lab TAs,
 *  classmates, and friends) and attribute them by name.
 *
 *  Also include any resources (including the web) that you may
 *  may have used in creating your design.
 *****************************************************************************/
We asked a question on piazza about the immutable type error.

/******************************************************************************
 *  Describe any serious problems you encountered.                    
 *****************************************************************************/
Making a recursive binary search that could be easily adapted to look for both
first and last instances of given key. We could not determine an elegant way 
of finishing the recursive search that would work for both types of search.

/******************************************************************************
 *  List any other comments here. Feel free to provide any feedback   
 *  on how much you learned from doing the assignment, and whether    
 *  you enjoyed doing it.                                             
 *****************************************************************************/

  
