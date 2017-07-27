/******************************************************************************
 *  Name:    Linhchi Nguyen
 *  NetID:   lbn
 *  Precept: P03
 *
 *  Partner Name:    Murray Lynch
 *  Partner NetID:   mdlynch
 *  Partner Precept: P04
 * 
 *  Description:  Class implements two binary search methods, one for finding
 *  the index of the first instance of a generic key value in the array, 
 *  the other method finding the last.
 *  These methods rely on the array having been sorted beforehand normally
 *  (e.g. lexicographic, ascending for Strings) with respect to the key
 ******************************************************************************/
import java.util.Comparator;

public class BinarySearchDeluxe {
    
    // nested class used to implement search methods
    private static class RecursiveSearch<Key> {
        private Key[] a;
        private Key key;
        private Comparator<Key> comparator;
        private boolean isFirst; // lastIndexOf() and firstIndex() variation
        
        private int mid; // middle of search range (rounded down)
        private int compareIndex; // stores result of compare()
        private int result; 
        // number that represents high and lo are next to each other in search
        // method - preventing magic number
        private int nextTo; 
        
        private boolean finished; // flag for exiting recursion
        
        private RecursiveSearch(
            Key[] a, Key key, Comparator<Key> comparator, boolean isFirst) {
            this.a = a; 
            this.key = key; 
            this.comparator = comparator;
            this.isFirst = isFirst;
            nextTo = 2;
        }
        
        private int search(int lo, int high) {
            if (finished) return result; // exits if search is finished
            
            mid = lo + (high-lo)/2; // assign midpoint of search bounds
            
            // finish search after resursion narrows to two elements
            // when high and lo are next to each other
            if ((high - lo) < nextTo) { 
                finished = true;
                // split case to control for instance where both elements 
                // match the key. This method ensures only
                //  only one compare method is called.                
                int highCompare = comparator.compare(key, a[high]);
                int lowCompare = comparator.compare(key, a[lo]);
                if ((highCompare != 0) && (lowCompare != 0)) 
                    result = -1;
                else {
                    if (isFirst) { 
                        if (lowCompare > 0) result = high;
                        else result = lo;
                    }
                    else { // if last
                        if (highCompare < 0) result = lo;
                        else result = high; 
                    }
                }
                return result;
            }

            compareIndex = comparator.compare(key, a[mid]);
            if (compareIndex < 0) search(lo, mid); // search lower in array
            if (compareIndex > 0) search(mid, high); // search higher in array
            if (compareIndex == 0) { // split case if key is found
                if (isFirst) search(lo, mid);
                else search(mid, high); 
            }
            
            return result; // needed to compile
        }
        
    }    
    
    // Returns the index of the first key in a[] 
    // that equals the search key, or -1 if no such key.
    public static <Key> int firstIndexOf(
                                         Key[] a, Key key, 
                                         Comparator<Key> comparator) {        
        //corner cases
        if ((a == null) || (key == null) || (comparator == null))
            throw new NullPointerException();
        if (a.length == 0) return -1;
        
        RecursiveSearch<Key> r = new RecursiveSearch<Key>(a, key, comparator, true);
        return r.search(0, a.length-1);
    }
    
    // Returns the index of the last key in a[] 
    // that equals the search key, or -1 if no such key.
    public static <Key> int lastIndexOf(
                                        Key[] a, Key key, 
                                        Comparator<Key> comparator) {
        //corner cases
        if ((a == null) || (key == null) || (comparator == null))
            throw new NullPointerException();
        if (a.length == 0) return -1;
        
        RecursiveSearch<Key> r = new RecursiveSearch<Key>(a, key, comparator, false);
        return r.search(0, a.length-1);
    }
    
    public static void main(String[] args) {
        
       String[] newArray = { "ABBA", "ABBB", "BAAA", "BAAA", "BAAB", "BAAB", 
           "BABA", "BABA", "BABA", "BABB", "BABB", "BBBA", "BBBA" };
       int first = firstIndexOf(newArray, "ABBA", String.CASE_INSENSITIVE_ORDER);
       int last = lastIndexOf(newArray, "ABBA", String.CASE_INSENSITIVE_ORDER);
       System.out.println("first = " + first);
       System.out.println("last = " + last);

    }
    
    
}
    
