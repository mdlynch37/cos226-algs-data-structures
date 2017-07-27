/******************************************************************************
 *  Name:    Murray Lynch
 *  NetID:   mdlynch
 *  Precept: P04
 *
 *  Partner Name:    N/A
 *  Partner NetID:   N/A
 *  Partner Precept: N/A
 * 
 *  Description: This mutable data type is a generalization of a BST for 
 *  2-dimensional keys. Children of nodes are arranged according to their
 *  parents' x or y coordinates, each alternative down levels of the tree.
 *  Nodes include a Rectangle object that corresponds to the area in which 
 *  the node's children must exist. This allows more efficient range searches
 *  and nearest neighbor searches by eliminating roots to examine with the
 *  Nodes' rectangle data.
 ******************************************************************************/

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.Stopwatch;

public class KdTreeST<Value> {
    
    private int size; //size of Kd tree
    private Node root; //root of Kd tree
    private Queue<Point2D> rangeQ; // built in range()
    private Point2D nearestP; // found in both nearest()'s
    private double nearDist; // used in both nearest()'s
    
    private class Node {  
        private Point2D point; // 2d key
        private Value value; // value associated with key
        private RectHV rect; //the axis aligned rect corresponding to the node
        private Node lb; // left branch (lower or bottom depending on level)
        private Node rt; // right branch (right or top depending on level)
        
        // constructs node for ST
        public Node(Point2D p, Value v, RectHV r) {
            if ((p == null) || (v == null) || (r == null)) 
                throw new NullPointerException();
            point = p;
            value = v; 
            rect = r;
        }
    }
    
    // construct an empty symbol table of points
    public KdTreeST() { 
        size = 0; // initialize size counter
    }
    
    // is the symbol table empty? 
    public boolean isEmpty() {
        return (size == 0); 
    }
    
    // number of points 
    public int size() {
        return size; 
    }
    
    // associate the value val with point p
    public void put(Point2D p, Value val) {
        if ((p == null) || (val == null)) throw new NullPointerException();
        
        boolean orientX = true; // start with orientation of level at x coord
        
        // initialize first rectangle for root
        double negInf = Double.NEGATIVE_INFINITY;
        double posInf = Double.POSITIVE_INFINITY;
        RectHV rect1 = new RectHV(negInf, negInf, posInf, posInf);
        
        // initiate recursive method to place new node in appropriate branch
        root = put(root, p, val, rect1, orientX); 
    }
    
    private Node put(Node x, Point2D p, Value val, RectHV rect, boolean orientX) { 
        if (x == null) {
            size++; // on creation of new node, add to size
            return new Node(p, val, rect); 
        }
        
        // set up coordinates of each rect's corner for new rect construction
        double rxmin = rect.xmin(); 
        double rxmax = rect.xmax(); 
        double rymin = rect.ymin(); 
        double rymax = rect.ymax();
        
        // look at key's x coordinate
        if (orientX) { 
            double px = p.x(); // x val of key 
            double xpoint = x.point.x(); // x val of node 
            if (px < xpoint) { // branch left
                RectHV rectleft = new RectHV(rxmin, rymin, xpoint, rymax);
                x.lb = put(x.lb, p, val, rectleft, !orientX);
            }
            if (px > xpoint) {   // branch rigth              
                RectHV rectright = new RectHV(xpoint, rymin, rxmax, rymax); 
                x.rt = put(x.rt, p, val, rectright, !orientX);
            }
            else if (px == xpoint) { // branch right if equal
                if (p.y() != x.point.y()) { // determine is points are equal
                    RectHV rectright = new RectHV(xpoint, rymin, rxmax, rymax); 
                    x.rt = put(x.rt, p, val, rectright, !orientX);
                }
                else x.value = val; // overwrite value if key exists
            }
        } // look at key's y coordinate
        else if (!(orientX)) { 
            double py = p.y(); // y val of key 
            double ypoint = x.point.y(); // y val of node 
            if (py < ypoint) { // branch left
                RectHV rectleft = new RectHV(rxmin, rymin, rxmax, ypoint);
                x.lb = put(x.lb, p, val, rectleft, !orientX);
            }
            if (py > ypoint) { // branch right
                RectHV rectright = new RectHV(rxmin, ypoint, rxmax, rymax); 
                x.rt = put(x.rt, p, val, rectright, !orientX);
            }
            else if (py == ypoint) { // branch right if equal
                if (p.x() != x.point.x()) { // determine is points are equal
                    RectHV rectright = new RectHV(rxmin, ypoint, rxmax, rymax); 
                    x.rt = put(x.rt, p, val, rectright, !orientX);
                }
                else x.value = val; // overwrite value if key exists
            }
        }
        return x; // move up recursion
    }   
    
    // value associated with point p
    public Value get(Point2D p) {
        if (p == null) throw new NullPointerException();
        
        Node x = root; // initialize tracking pointer
        boolean orientX = true; // start with orientation of level at x coord
        double p1;
        double p2;
        
        // exit loop only if p does not exist in tree
        while (x != null) {
            // assign values to compare based on level's orientation
            if (orientX) {
                p1 = p.x();
                p2 = x.point.x();
            }
            else {
                p1 = p.y();
                p2 = x.point.y();  
            }
            // flip orientation for next level
            orientX = !(orientX);
            if (p1 > p2) x = x.rt; // branch right
            else if (p1 < p2) x = x.lb; // branch left
            else if (p1 == p2) { 
                if (p.equals(x.point)) return x.value;
                x = x.rt; // go down right if points not equal
            }
        }
        return null;
    }
    // does the symbol table contain point p?
    public boolean contains(Point2D p) { 
        if (p == null) throw new NullPointerException();
        return get(p) != null;
    }
    
    // all points in the symbol table 
    public Iterable<Point2D> points() { 
        Queue<Node> q = new Queue<Node>(); // holds next nodes to examine
        Queue<Point2D> order = new Queue<Point2D>(); // holds points, to return
        q.enqueue(root); // initialize q
        
        // iterate until there are as many points as there are nodes
        while (order.size() < this.size()) { 
            Node temp = q.dequeue(); 
            // if there is a child, add it to queue to continue examination
            if (temp.lb != null) { q.enqueue(temp.lb); }
            if (temp.rt != null) { q.enqueue(temp.rt); }
            order.enqueue(temp.point); // add point from examined queue
        }
        return order;
    }    
    
    // all points that are inside the rectangle 
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new NullPointerException();
        boolean orientX = true; // start orientation on x
        rangeQ = new Queue<Point2D>();
        range(root, rect, orientX); // initialize recursion
        return rangeQ;
    }
    
    private void range(Node x, RectHV rect, boolean orientX) {
        if (x == null) return;
        
        // set values to compare based on level of node
        double p = 0.0; //the x or y coordinate of the node we're on
        double max = 0.0; // x or y value max of rect
        double min = 0.0; // x or y value min of rect
        
        // set values depending on orientation
        if (orientX) {
            p = x.point.x();
            max = rect.xmax();
            min = rect.xmin();
        }
        else {
            p = x.point.y();
            max = rect.ymax();
            min = rect.ymin();
        }
        
        if (p > max) { // if line is to the right/up, look at left/bottom side
            range(x.lb, rect, !orientX);
        }
        else if (p < min) { // if line is to the left/bottom, look at right/top side
            range(x.rt, rect, !orientX);
        }
        else { // if line intersects rectangle (i.e. between min and max)
            if (rect.contains(x.point)) rangeQ.enqueue(x.point);
            range(x.lb, rect, !orientX);
            range(x.rt, rect, !orientX);
        }
    }  
    
    // a nearest neighbor to point p; null if the symbol table is empty
    public Point2D nearest(Point2D p) {
        if (p == null) throw new NullPointerException();
        if (root == null) return null;
        boolean orientX = true; // initialize
        nearDist = Double.POSITIVE_INFINITY; // initialize
        nearest(root, p, orientX); // start recursion
        return nearestP;
        
    }
    
    private void nearest(Node x, Point2D p, boolean orientX) {
        if (x == null) return;
        double dist = p.distanceSquaredTo(x.point);
        // update is near point is found
        if (dist < nearDist) {
            nearestP = x.point;
            nearDist = dist;
        }
        
        double split = 0.0;
        double target = 0.0;
        
        // set coordinate to compare based on level's orientX
        if (orientX) {
            split = x.point.x();
            target = p.x();
        }
        else {
            split = x.point.y();
            target = p.y();
        }
        
        // determine which child gets priority based point's position relative
        // to dividing line, vertical or horizontal depending on level's 
        // orientX
        Node first;
        Node last;
        if (split > target) {
            first = x.rt;
            last = x.lb;
        }
        else {
            first = x.lb;
            last = x.rt;
        }
        // check distance to rect of child is less current nearest distance
        if (first != null) { // only if child exists
            if (first.rect.distanceSquaredTo(p) < nearDist) { 
                nearest(first, p, orientX);
            }
        }
        // check distance to rect of child is less current nearest distance
        if (last != null) { // only if child exists
            if (last.rect.distanceSquaredTo(p) < nearDist) {
                nearest(last, p, orientX);
            }
        }
    }
    
    // unit testing (not graded) 
    public static void main(String[] args) {              
        String filename = args[0];
        In in = new In(filename);

        // initialize the two data structures with point from standard input
        PointST<Integer> brute = new PointST<Integer>();
        KdTreeST<Integer> kdtree = new KdTreeST<Integer>();
        for (int i = 0; !in.isEmpty(); i++) {
            double x = in.readDouble();
            double y = in.readDouble();
            Point2D p = new Point2D(x, y);
            kdtree.put(p, i);
            brute.put(p, i);
        }
        
        double average = 0.0;
        for (int i = 0; i < 3; i++) {
            int count = 0;
            Iterable<Point2D> q = brute.points();
            Stopwatch timer = new Stopwatch();
            for (Point2D n : q) {
                Point2D z = kdtree.nearest(n);
                count++;
                
                if (count >= 1000) break;
            }
        double time = timer.elapsedTime();
        System.out.println("KDTREE");
        System.out.println("# of calls: " + count);
        System.out.println("time : " + time);
        double opsSec = count/time;
        average =+ opsSec;
        System.out.println("ops/sec : " + opsSec);        
        System.out.println("-------");
        System.out.println();
        }
        
        System.out.println("KT---Average ops/sec : " + average);
        
        average = 0.0;
        for (int i = 0; i < 3; i++) {
            int count = 0;
            Iterable<Point2D> q = kdtree.points();
            Stopwatch timer = new Stopwatch();
            for (Point2D n : q) {
                Point2D z = brute.nearest(n);
                count++;
                
                if (count >= 100) break;
            }
        double time = timer.elapsedTime();
        System.out.println("BRUTE");
        System.out.println("# of calls: " + count);
        System.out.println("time : " + time);
        double opsSec = count/time;
        average += opsSec;
        System.out.println("ops/sec : " + opsSec);        
        System.out.println("-------");
        System.out.println();
        }
        
        
        
        System.out.println("BR---Average ops/sec : " + average);    

    }
}