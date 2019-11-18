import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;

/******************************************************************************
 *  Author:  Huang Zhiyuan
 *  Start Date: 4th Jan, 2019
 *  End Date:
 *
 *  What do I learn after I finish:
 ******************************************************************************/

public class PointSET {

    private final SET<Point2D> set;

    public PointSET() {
        set = new SET<>();
    } // construct an empty set of points

    public boolean isEmpty() {
        return set.isEmpty();
    } // is the set empty?

    public int size() {
        return set.size();
    } // number of points in the set

    public void insert(Point2D p) {
        set.add(p);
    } // add the point to the set (if it is not already in the set)

    public boolean contains(Point2D p) {
        return set.contains(p);
    } // does the set contain point p?

    public void draw() {
        for(Point2D p : set)
            p.draw();
    } // draw all points to standard draw

    public Iterable<Point2D> range(RectHV rect) {
        if(rect == null)
            throw new IllegalArgumentException();

        Stack<Point2D> s = new Stack<>();
        for(Point2D p : set){
            if(rect.contains(p))
                s.push(p);
        }
        return s;
    } // all points that are inside the rectangle (or on the boundary)

    public Point2D nearest(Point2D p) {
        if(p == null)
            throw new IllegalArgumentException();
        if(set.isEmpty())
            return null;
        MinPQ<Point2D> pq = new MinPQ<>(p.distanceToOrder());

        for(Point2D points : set)
            pq.insert(points);
        return pq.min();
    } // a nearest neighbor in the set to point p; null if the set is empty

}