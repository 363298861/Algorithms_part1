import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdDraw;

/******************************************************************************
 *  Author:  Huang Zhiyuan
 *  Start Date: 4th Jan, 2019
 *  End Date:
 *
 *  What do I learn after I finish:
 ******************************************************************************/

public class KdTree {

    private int num;
    private final Node root;

    private static class Node {
        private Point2D p;
        private RectHV leftBottom, rightTop;
        private Node lb;
        private Node rt;
        private final int level;
        private final Node predecessor;
        private double x0, y0;
        private double x1, y1;
        private Stack<Point2D> inside;
        private Point2D nearPoint;

        private Node(Point2D p, int level, Node predecessor){
            this.p = p;
            this.level = level;
            this.predecessor = predecessor;
        }

        private Node insert(Point2D p){
            if(this.p == null) {
                this.p = p;
                if(this.predecessor == null){
                    this.x0 = this.x1 = this.p.x();
                    this.y0 = 0; this.y1 = 1;
                    this.leftBottom = new RectHV(0, 0, this.p.x(), 1);
                    this.rightTop = new RectHV(this.p.x(), 0, 1, 1);
                }else if(this.predecessor.predecessor == null){
                    if(this.equals(predecessor.lb)){
                        this.x0 = 0; this.x1 = this.predecessor.x1;
                        this.y0 = this.y1 = this.p.y();
                        this.leftBottom = new RectHV(this.x0, 0, this.x1, this.y1);
                        this.rightTop = new RectHV(this.x0, this.y0, 1, 1);
                    }
                    else{
                        this.x1 = 1; this.x0 = this.predecessor.x1;
                        this.y0 = this.y1 = this.p.y();
                        this.leftBottom = new RectHV(this.x0, 0, this.x1, this.y1);
                        this.rightTop = new RectHV(this.x0, y0, 1, 1);
                    }
                }else{
                    if(this.level % 2 == 0){
                        this.x0 = this.x1 = this.p.x();
                        if(this.equals(this.predecessor.lb)){
                            this.y0 = this.predecessor.predecessor.y0;
                            this.y1 = this.predecessor.y1;
                            this.leftBottom = new RectHV(this.predecessor.x0, this.predecessor.predecessor.y0, this.x1, this.y1);
                            this.rightTop = new RectHV(this.x0, this.y0, this.predecessor.x1, this.predecessor.y1);
                        }else{
                            this.y1 = this.predecessor.predecessor.y1;
                            this.y0 = this.predecessor.y0;
                            this.leftBottom = new RectHV(this.predecessor.x0, this.predecessor.y0, this.x1, this.y1);
                            this.rightTop = new RectHV(this.x0, this.y0, this.predecessor.x1, this.predecessor.predecessor.y1);
                        }
                    }else{
                        this.y0 = this.y1 = this.p.y();
                        if(this.equals(this.predecessor.lb)){
                            this.x0 = this.predecessor.predecessor.x0;
                            this.x1 = this.predecessor.x1;
                            this.leftBottom = new RectHV(this.predecessor.predecessor.x0, this.predecessor.y0, this.x1, this.y1);
                            this.rightTop = new RectHV(this.x0, this.y0, this.predecessor.x1, this.predecessor.y1);
                        }else{
                            this.x1 = this.predecessor.predecessor.x1;
                            this.x0 = this.predecessor.x0;
                            this.leftBottom = new RectHV(this.predecessor.x0, this.predecessor.y0, this.x1, this.y1);
                            this.rightTop = new RectHV(this.x0, this.y0, this.predecessor.predecessor.x1, this.predecessor.y1);
                        }
                    }
                }
                return this;
            }
            else if(this.p.equals(p)){
                return null;
            }
            else if (this.level % 2 == 0){
                if(p.x() < this.p.x()){
                    if(this.lb == null)
                        this.lb = new Node(null, this.level + 1, this);
                    return this.lb.insert(p);
                }else{
                    if(this.rt == null)
                        this.rt = new Node(null, this.level + 1, this);
                    return this.rt.insert(p);
                }
            }else {
                if(p.y() < this.p.y()){
                    if(this.lb == null)
                        this.lb = new Node(null, this.level + 1, this);
                    return this.lb.insert(p);
                }else{
                    if(this.rt == null)
                        this.rt = new Node(null, this.level + 1, this);
                    return this.rt.insert(p);
                }
            }
        }

        private Node contain(Point2D p){
            if(this.p == null) {
                return null;
            }
            else if(this.p.equals(p)){
                return this;
            }
            else if (this.level % 2 == 0){
                if(p.x() < this.p.x()){
                    if(this.lb == null)
                        return null;
                    return this.lb.contain(p);
                }else{
                    if(this.rt == null)
                        return null;
                    return this.rt.contain(p);
                }
            }else {
                if(p.y() < this.p.y()){
                    if(this.lb == null)
                        return null;
                    return this.lb.contain(p);
                }else{
                    if(this.rt == null)
                        return null;
                    return this.rt.contain(p);
                }
            }
        }

        private void draw(){
            if(this.p != null)
                StdDraw.point(p.x(), p.y());
            if(this.lb != null)
                lb.draw();
            if(this.rt != null)
                rt.draw();
        }

        private void drawLines(){
            StdDraw.setPenRadius();

            if(this.p != null){
                if(this.level % 2 == 0){
                    StdDraw.setPenColor(StdDraw.RED);
                    StdDraw.line(this.x0, this.y0, this.x1, this.y1);
                }else{
                    StdDraw.setPenColor(StdDraw.BLUE);
                    StdDraw.line(this.x0, this.y0, this.x1, this.y1);
                }
            }
            if(this.lb != null)
                lb.drawLines();
            if(this.rt != null)
                rt.drawLines();
        }

        private void drawRect(){
            StdDraw.setPenRadius(0.01);
            if(this.p != null){
                StdDraw.setPenColor(StdDraw.RED);
                this.leftBottom.draw();
                StdDraw.setPenColor(StdDraw.BLUE);
                this.rightTop.draw();
            }
            if(this.lb != null)
                lb.drawRect();
            if(this.rt != null)
                rt.drawRect();
        }

        private Iterable<Point2D> search(RectHV rect, Node tree){
            if(tree != null) {
                if (rect.contains(tree.p))
                    inside.push(tree.p);
                if (rect.intersects(tree.leftBottom))
                    search(rect, tree.lb);
                if (rect.intersects(tree.rightTop))
                    search(rect, tree.rt);
            }
            return inside;
        }

        private Point2D findNearest(Point2D p, Node tree){

            if(tree != null) {
                if (nearPoint == null)
                    nearPoint = tree.p;
                else if(nearPoint.distanceSquaredTo(p) > tree.p.distanceSquaredTo(p)){
                    nearPoint = tree.p;
                }

                if(tree.lb == null)
                    findNearest(p, tree.rt);
                else if(tree.rt == null)
                    findNearest(p, tree.lb);
                else if(tree.leftBottom.contains(p)) {
                    if(tree.level % 2 == 0 && tree.lb.p.distanceTo(p) < Math.abs(tree.p.x() - p.x()))
                        findNearest(p, tree.lb);
                    else if(tree.level % 2 != 0 && tree.lb.p.distanceTo(p) < Math.abs(tree.p.y() - p.y()))
                        findNearest(p, tree.lb);
                    else{
                        findNearest(p, tree.lb);
                        findNearest(p, tree.rt);
                    }
                }
                else {
                    if(tree.level % 2 == 0 && tree.rt.p.distanceTo(p) < Math.abs(tree.p.x() - p.x()))
                        findNearest(p, tree.rt);
                    else if(tree.level % 2 != 0 && tree.rt.p.distanceTo(p) < Math.abs(tree.p.y() - p.y()))
                        findNearest(p, tree.rt);
                    else{
                        findNearest(p, tree.lb);
                        findNearest(p, tree.rt);
                    }
                }
            }
            return nearPoint;
        }

        @Override
        public String toString(){
            return this.p.toString();
        }

    } // this curly bracket is the end of the inner class

    public KdTree() {
        root = new Node(null, 0, null);
        num = 0;
    } // construct an empty set of points

    public boolean isEmpty() {
        return num == 0;
    } // is the set empty?

    public int size() {
        return num;
    } // number of points in the set

    public void insert(Point2D p) {
        if (p == null)
            throw new IllegalArgumentException("Invalid argument");

        if(root.insert(p) != null)
            num++;
    } // add the point to the set (if it is not already in the set)

    public boolean contains(Point2D p) {
        if (p == null)
            throw new IllegalArgumentException("Invalid argument");

        return root.contain(p) != null;
    } // does the set contain point p?

    public void draw() {
        StdDraw.setPenRadius(0.01);
        StdDraw.setPenColor(StdDraw.BLACK);
        root.draw();
        root.drawLines();
        //root.drawRect();
    } // draw all points to standard draw

    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null)
            throw new IllegalArgumentException("Invalid argument");
        root.inside = new Stack<>();
        if(root.p != null)
            return root.search(rect, root);
        else
            return null;
    } // all points that are inside the rectangle (or on the boundary)

    public Point2D nearest(Point2D p) {
        if (p == null)
            throw new IllegalArgumentException("Invalid argument");
        root.nearPoint = null;
        return root.findNearest(p, root);
    } // a nearest neighbor in the set to point p; null if the set is empty

    public static void main(String[] args) {
        KdTree tree = new KdTree();
        tree.insert(new Point2D(0.7, 0.2));
        tree.insert(new Point2D(0.5, 0.4));
        tree.insert(new Point2D(0.2, 0.3));
        tree.insert(new Point2D(0.4, 0.7));
        tree.insert(new Point2D(0.9, 0.6));
        tree.insert(new Point2D(0.84, 0.73));
        Point2D find1 = new Point2D(0, 0);
        Point2D find2 = new Point2D(0.8, 0.6);
        StdDraw.setCanvasSize(1024, 1024);
        tree.draw();
        RectHV r1 = new RectHV(0.4, 0.4, 0.6, 0.6);
        RectHV r2 = new RectHV(0.4, 0.4, 1, 1);
        RectHV r3 = new RectHV(0.2, 0.2, 0.8, 0.8);
        RectHV r4 = new RectHV(0.3, 0.3, 0.7, 0.7);
        RectHV r5 = new RectHV(0.4, 0.4, 0.6, 0.6);
        RectHV r6 = new RectHV(0, 0, 0.6, 0.6);
        StdDraw.setPenRadius(0.01);
        StdDraw.setPenColor(StdDraw.PRINCETON_ORANGE);
        System.out.println(tree.nearest(find2));
    }
}