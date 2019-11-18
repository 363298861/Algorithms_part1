import java.util.Arrays;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
/*************************************************************************
 *  Compilation:  javac LineSegment.java
 *  Execution:    none
 *  Dependencies: Point.java
 *
 *  An immutable data type for Line segments in the plane.
 *  For use on Coursera, Algorithms Part I programming assignment.
 *
 *************************************************************************/

public class FastCollinearPoints {

    private int numOfSegments;
    private LineSegment[] segments;

    public FastCollinearPoints(Point[] points) {
        if(points == null)
            throw new IllegalArgumentException("No points!");

        for(int i = 0; i < points.length; i++){
            if(points[i] == null)
                throw new IllegalArgumentException("Invalid points!");

            for(int j = i + 1; j < points.length; j++){
                if(points[i].equals(points[j]))
                    throw new IllegalArgumentException("Duplicate points!");
            }
        }

        int num = 2;
        LineSegment[] temSegments = new LineSegment[points.length / 2];

        Point[] origin = new Point[points.length];
        for(int i = 0; i < points.length; i++){
            origin[i] = points[i];
        }

        for(Point p : origin) {
            Arrays.sort(points, p.slopeOrder());
            for(int i = 0; i < points.length - 2; i++){
                if(p.slopeTo(points[i]) == p.slopeTo(points[points.length - 1]) && points.length - i >= 3){
                    Arrays.sort(points, i, points.length);
                    LineSegment lineSegment = baseOnDistance(points[i], points[points.length - 1], p);
                    if(!contain(temSegments, lineSegment)) {
                        temSegments[numOfSegments++] = lineSegment;
                    }
                    break;
                }
                else if(p.slopeTo(points[i]) == p.slopeTo(points[i + num])){
                    while(p.slopeTo(points[i]) == p.slopeTo(points[i + num])){
                        num++;
                    }
                    Arrays.sort(points, i, i + num);
                    LineSegment lineSegment = baseOnDistance(points[i], points[i + num - 1], p);
                    if(!contain(temSegments, lineSegment)) {
                        temSegments[numOfSegments++] = lineSegment;
                    }
                    i = i + num - 1;
                    num = 2;
                }
            }
        }
            /*for(int i = 1; i < points.length; i++){
                for(int j = i + 1; j < points.length - 1; j++){
                    if(p.slopeTo(points[0]) == p.slopeTo(points[points.length - 1]) && points.length >3){
                        Arrays.sort(points);
                        LineSegment lineSegment = baseOnDistance(points[0], points[points.length - 1], p);
                        if(!contain(temSegments, lineSegment)) {
                            temSegments[num++] = lineSegment;
                        }
                    }
                    else if(p.slopeTo(points[0]) == p.slopeTo(points[j]) && j >= 2 && p.slopeTo(points[0]) != p.slopeTo(points[j + 1])){
                        Arrays.sort(points, 0, j + 1);
                        LineSegment lineSegment = baseOnDistance(points[0], points[j], p);
                        if(!contain(temSegments, lineSegment)) {
                            temSegments[num++] = lineSegment;
                        }
                    }
                    else if(p.slopeTo(points[i]) == p.slopeTo(points[points.length - 1]) && points.length - i >= 3
                            && p.slopeTo(points[i]) != p.slopeTo(points[i - 1])){
                        Arrays.sort(points, i, points.length);
                        LineSegment lineSegment = baseOnDistance(points[i], points[points.length - 1], p);
                        if(!contain(temSegments, lineSegment)) {
                            temSegments[num++] = lineSegment;
                        }
                    }
                    else if(p.slopeTo(points[i]) == p.slopeTo(points[j]) && j - i >= 2 && p.slopeTo(points[i]) != p.slopeTo(points[j + 1])
                            && p.slopeTo(points[i]) != p.slopeTo(points[i - 1])){
                        Arrays.sort(points, i, j + 1);
                        LineSegment lineSegment = baseOnDistance(points[i], points[j], p);
                        if(!contain(temSegments, lineSegment)) {
                            temSegments[num++] = lineSegment;
                        }
                    }
                }
            }*/


        segments = new LineSegment[numOfSegments];
        for(int i = 0; i< numOfSegments; i++){
            segments[i] = temSegments[i];
        }

    }    // finds all line segments containing 4 or more points

    public int numberOfSegments() {
        return numOfSegments;
    }   // the number of line segments

    private boolean contain(LineSegment[] segments, LineSegment segment){
        if(segments.length == 0) return false;

        for(int i = 0; i < segments.length; i++){
            if(segments[i] != null) {
                if (segments[i].toString().equals(segment.toString()))
                    return true;
            }
        }
        return false;
    }

    private LineSegment baseOnDistance(Point a, Point b, Point c){
        Point[] list = {c, a, b};
        Arrays.sort(list);
        return new LineSegment(list[0], list[2]);
    }

    public LineSegment[] segments() {
        return segments;
    }   // the line segments


    public static void main(String[] args) {
        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
