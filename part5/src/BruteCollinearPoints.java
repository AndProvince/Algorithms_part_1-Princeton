/* *****************************************************************************
 *  Name:              Andrei Ponomarev
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;

public class BruteCollinearPoints {
    private ArrayList<LineSegment> lines = new ArrayList<>();
    private int n;

    public BruteCollinearPoints(Point[] points) {
        checkNull(points);
        Arrays.sort(points);
        if (hasRepeat(points))
            throw new IllegalArgumentException();

        n = points.length;

        for (int first = 0; first < n - 3; first++) {
            for (int second = first + 1; second < n - 2; second++) {
                double slFS = points[first].slopeTo(points[second]);
                for (int third = second + 1; third < n - 1; third++) {
                    double slFT = points[first].slopeTo(points[third]);
                    if (slFS == slFT) {
                        for (int forth = third + 1; forth < n; forth++) {
                            if (slFS == points[first].slopeTo(points[forth]))
                                lines.add(new LineSegment(points[first], points[forth]));
                        }
                    }
                }
            }

        }
    }

    public int numberOfSegments() {
        return lines.size();
    }

    public LineSegment[] segments() {
        return lines.toArray(new LineSegment[lines.size()]);
    }

    private static boolean hasRepeat(Point[] points) {
        for (int i = 0; i < points.length - 1; i++)
            if (points[i].compareTo(points[i + 1]) == 0)
                return true;
        return false;
    }

    private static void checkNull(Point[] points) {
        if (points == null)
            throw new IllegalArgumentException();
        for (Point point : points)
            if (point == null)
                throw new IllegalArgumentException();
    }

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
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
