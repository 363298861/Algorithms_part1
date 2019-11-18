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
 *  DO NOT MODIFY THIS CODE.
 *
 *************************************************************************/

public final class BruteCollinearPoints {

    private final int num;
    private final LineSegment[] segments;

    public BruteCollinearPoints(Point[] points) {
        if(points == null)
            throw new IllegalArgumentException("No points!");

        int numOfSegments = 0;
        LineSegment[] temSegments = new LineSegment[points.length / 2];

        for(int i = 0; i < points.length; i++){
            if(points[i] == null)
                throw new IllegalArgumentException("Invalid points!");

            for(int j = i + 1; j < points.length; j++){
                if(points[i].equals(points[j]))
                    throw new IllegalArgumentException("Duplicate points!");
            }
        }

        for(int i = 0; i < points.length; i++){
            for(int j = i + 1; j < points.length; j++){
                for(int k = j + 1; k < points.length; k++){
                    for(int h = k + 1; h < points.length; h++) {
                        if (points[i].slopeTo(points[j]) == points[j].slopeTo(points[k]) &&
                                points[j].slopeTo(points[k]) == points[k].slopeTo(points[h])) {
                            Point[] collinear = {points[i], points[j], points[k], points[h]};
                            Arrays.sort(collinear);
                            temSegments[numOfSegments++] = new LineSegment(collinear[0], collinear[3]);
                        }
                    }
                }
            }
        }
        segments = new LineSegment[numOfSegments];
        for(int i = 0; i< numOfSegments; i++){
            segments[i] = temSegments[i];
        }
        num = numOfSegments;
    }  // finds all line segments containing 4 points

    public int numberOfSegments() {
        return num;
    }  // the number of line segments

    public LineSegment[] segments() {
        return segments;
    }  // the line segments

}