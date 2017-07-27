/******************************************************************************
  *  Name:    Murray Lynch
  *  NetID:   mdlynch
  *  Precept: P04
  *
  *  Partner Name:    Pritika Mehra
  *  Partner NetID:   
  *  Partner Precept: 
  * 
  *  Description:  This immutable data type creates a BST with keys 
  * 
  ******************************************************************************/

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RedBlackBST;

public class PointST<Value> {
    
    private RedBlackBST<Point2D, Value> bst;
        
    // construct an empty symbol table of points
    public PointST() {
        bst = new RedBlackBST<Point2D, Value>();
    }
    // is the symbol table empty? 
    public boolean isEmpty() {
        return bst.isEmpty();
    }
    
    // number of points
    public int size() { 
        return bst.size();
    }
    
    // associate the value val with point p
    public void put(Point2D p, Value val) {
        bst.put(p, val);
    }
    
    // value associated with point p
    public Value get(Point2D p) {
        return bst.get(p);
    }
    // does the symbol table contain point p? 
    public boolean contains(Point2D p) {
        return bst.contains(p);
    }
    
    // all points in the symbol table
    public Iterable<Point2D> points() { 
        return bst.keys();
    }
    
    // all points that are inside the rectangle
    public Iterable<Point2D> range(RectHV rect) {
        Iterable<Point2D> allPoints = points();
        Queue<Point2D> range = new Queue<Point2D>();
        for (Point2D p : allPoints) {
            if (rect.contains(p)) range.enqueue(p);
        }
        return range;
    }
    
    // a nearest neighbor to point p; null if the symbol table is empty
    public Point2D nearest(Point2D p) {
        if (bst.size() == 0) return null;
        Iterable<Point2D> allPoints = points();
        double dist = Double.POSITIVE_INFINITY;
        Point2D nearest = null;
        for (Point2D x : allPoints) {
            if (p.distanceSquaredTo(x) < dist) {
                dist  = p.distanceSquaredTo(x);
                nearest = x;
            }   
        }
        return nearest;
    }
    
    // unit testing (not graded)
    public static void main(String[] args) {
        if (2>1) {boolean b = false;}
        System.out.print(b);
    }
}