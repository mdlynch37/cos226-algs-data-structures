/******************************************************************************
 *  Name: Murray Lynch    
 *  NetID: mdlynch   
 *  Precept: P04
 *
 *  Partner Name: Pritika Mehra      
 *  Partner NetID: pritikam
 *  Partner Precept: P05
 *
 *  Hours to complete assignment (optional):
 *
 ******************************************************************************/



/******************************************************************************
 *  Describe the Node data type you used to implement the
 *  2d-tree data structure.
 *****************************************************************************/
We used a private node class that contained a 2dPoint, a value, the rectangle 
    that the point is enclosed in, a reference to the left/bottom node, and a 
    reference to the right/top node. 
/******************************************************************************
 *  Describe your method for range search in a kd-tree.
 *****************************************************************************/
Our method for range search has a private method within it, which allows us 
take advantage of recursive calls. We first create the queue that will hold the 
    range of points within the given rectangle, and we establish a  boolean for 
    the orientation. 
    
    Our private method takes a node, the query rectangle and the orientation 
    as its arguments. In the private function we first check what our orientation
    is and then accordingly extract either the x or y max/min boundaries of our 
    rectangle. We compare our point to the boundaries of this rectangle to decide 
    whether we should explore the right subtree, the left subtree or both subtrees. 
    This process is done recursively and everytime we find a node that is within 
    our rectangle, we enqeue it to our range queue. Each time we make a recursive 
    call we flip the value of orienatation so that the function compares based on
    x values and y values in turn. 
/******************************************************************************
 *  Describe your method for nearest neighbor search in a kd-tree.
 *****************************************************************************/

    Using a similar method with an overloaded private method for recursion,
    we use two main instance variables: one to track the nearest distance 
    to our query point, and one to track the node that gave us the nearest 
    distance. Our private method took 3 arguments as inputs: a node, the 
    query point and the current orientation. Based on the orientation, we 
    compared either the x coordinates of our node to the x coordinates of 
    the point, or we did the same with y coordinates. 
    The results of our comparison determined whether we traversed the right 
    subtree or the left subtree first. If our query point was greater than the 
    node we were comparing it to, we traversed the right subtree first. If it 
    was smaller, we would traverse the left subtree first. Before we traversed
    any subtree however, we checked to see whether the distance to the rectangle 
    containing that subtree was smaller than the min distance we already had. 
    Only if the distance to the rectangle was smaller did we bother exploring
    that subtree. 
    

/******************************************************************************
 *  Using the 64-bit memory cost model from the textbook and lecture,
 *  give the total memory usage in bytes of your 2d-tree data structure
 *  as a function of the number of points N. Use tilde notation to
 *  simplify your answer (i.e., keep the leading coefficient and discard
 *  lower-order terms).
 *
 *  Include the memory for all referenced objects (including
 *  Node, Point2D, and RectHV objects) except for Value objects
 *  (because the type is unknown). Also, include the memory for
 *  all referenced objects.
 *
 *  Justify your answer below.
 *
 *****************************************************************************/

bytes per Point2D: According to the checklist, each point object takes 32 
    bytes of memory. A reference to a point in 8 bytes. So the total 
    memory assosciated with each point is 40 bytes. 

bytes per RectHV:
    Each Rect HV has 4 doubles. A double is 8 bytes. 
    4*8 = 32. 
    The object overhead is 16 bytes, and the reference to 
    the object is 8 bytes. 
    32 + 16 + 8 =  56 bytes per HV 

bytes per KdTree of N points:   ~
A reference to a node is 8 bytes, and a reference to a value is 8 bytes 
    too. 
   We have two node references and one value ref. 8*3 = 24 
    
    56 (memory for rect) + 40 (memory for point) + 24(memory for references) 
    = 120 bytes 
    
    The over head of a private inner class is 8 bytes 
    
    120 + 8 = 128 
    
    Total memory usage for an N size Kd Tree with tilde notation = 
    128N 

/******************************************************************************
 *  How many nearest neighbor calculations can your brute-force
 *  implementation perform per second for input100K.txt (100,000 points)
 *  and input1M.txt (1 million points), where the query points are
 *  random points in the unit square? Explain how you determined the
 *  operations per second. (Do not count the time to read in the points
 *  or to build the 2d-tree.)
 *
 *  Repeat the question but with the 2d-tree implementation.
 *****************************************************************************/

                       calls to nearest() per second
                     brute force               2d-tree
                     ---------------------------------
input100K.txt        186.04                    87719.29

input1M.txt          7.17                      36900.36
    
    For both test, we ran three samples (for loops) and averaged the ops/second 
    across them. Inside each sample, we called nearest() for points of randomly 
    generated x and y coordinates on the plane for N iterations of a for loop.
    For realistic wait times for results, N differed like so:
    For input100K.txt, we used N = 10000 for kdtree, N = 1000 for brute force.
    For input1M.txt, we used N = 1000 for kdtree, N = 100 for brute force.


/******************************************************************************
 *  Known bugs / limitations.
 *****************************************************************************/


/******************************************************************************
 *  Describe whatever help (if any) that you received.
 *  Don't include readings, lectures, and precepts, but do
 *  include any help from people (including course staff, lab TAs,
 *  classmates, and friends) and attribute them by name.
 *****************************************************************************/

    Tuesday night lab TAs

/******************************************************************************
 *  Describe any serious problems you encountered.                    
 *****************************************************************************/


/******************************************************************************
 *  If you worked with a partner, assert below that you followed
 *  the protocol as described on the assignment page. Give one
 *  sentence explaining what each of you contributed.
 *****************************************************************************/

Both my partner and I followed the protocol described on the assignment page. 
Together we both conceptualized the code, implemented it and debugged it. 




/******************************************************************************
 *  List any other comments here. Feel free to provide any feedback   
 *  on how much you learned from doing the assignment, and whether    
 *  you enjoyed doing it.                                             
 *****************************************************************************/