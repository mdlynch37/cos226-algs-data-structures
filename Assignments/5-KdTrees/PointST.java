/******************************************************************************
 *  Name:    Murray Lynch
 *  NetID:   mdlynch
 *  Precept: P04
 *
 *  Partner Name:    Pritika Mehra
 *  Partner NetID:   pritikam
 *  Partner Precept: p05
 * 
 *  Description:  Simple implementation of balanced BST with 2d point as key
 *  and value as a generic
 ******************************************************************************/

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RedBlackBST;

public class PointST<Value> {
    
    // object for BST arrangement of ST
    private RedBlackBST<Point2D, Value> bst;
    
    // construct an empty symbol table of points 
    public PointST() {
        bst = new RedBlackBST<Point2D, Value>(); 
    }
    
    // is the symbol table empty? 
    public boolean isEmpty() {                     
        return (size() == 0); 
    }
    
    // number of points
    public int size() {                       
        return bst.size();
    }
    
    // associate the value val with point p
    public void put(Point2D p, Value val) {
        if ((p == null) || (val == null)) throw new NullPointerException();
        bst.put(p, val); 
    }
    
    // value associated with point p 
    public Value get(Point2D p) {
        if (p == null) throw new NullPointerException();
        return bst.get(p);
    }
    
    // does the symbol table contain point p?
    public boolean contains(Point2D p) { 
        if (p == null) throw new NullPointerException();
        return bst.contains(p); 
    }
    
    // all points in the symbol table 
    public Iterable<Point2D> points() {
        if (isEmpty()) {
            return new Queue<Point2D>();
        }
        return bst.keys();
    }
    
    // all points that are inside the rectangle
    public Iterable<Point2D> range(RectHV rect) { 
        if (rect == null) throw new NullPointerException();
        Iterable<Point2D> allKeys = points();
        
        // iterable to be enqueued with points found within range
        Queue<Point2D> range = new Queue<Point2D>(); 
        
        for (Point2D p : allKeys) {
            if (rect.contains(p))
                range.enqueue(p);
        }
        return range;
    }
    
    // a nearest neighbor to point p; null if the symbol table is empty
    public Point2D nearest(Point2D p) {
        if (isEmpty()) return null;
        if (p == null) throw new NullPointerException();
        
        Iterable<Point2D> allKeys = points();
        double dist = Double.POSITIVE_INFINITY; 
        Point2D near = null;
        for (Point2D point : allKeys) { 
            double tempdist = point.distanceSquaredTo(p);
            if (tempdist < dist) { 
                dist = tempdist; 
                near = point;
            }
        }
        return near; 
    }
    
    // unit testing (not graded)
    public static void main(String[] args)  { 
        
    }
}