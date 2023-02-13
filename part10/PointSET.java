package part10;/* *****************************************************************************
 *  Name:              Andrei Ponomarev
 **************************************************************************** */

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;
import java.util.TreeSet;

public class PointSET {
    private TreeSet<Point2D> ts;

    public PointSET() {
        ts = new TreeSet<>();
    }

    public boolean isEmpty() {
        return ts.isEmpty();
    }

    public int size() {
        return ts.size();
    }

    public void insert(Point2D p) {
        if (p == null)
            throw new IllegalArgumentException();
        if (ts.contains(p)) return;
        ts.add(p);
    }

    public boolean contains(Point2D p) {
        if (p == null)
            throw new IllegalArgumentException();
        return ts.contains(p);
    }

    public void draw() {
        Iterator<Point2D> iter = ts.iterator();
        while (iter.hasNext()) {
            iter.next().draw();
        }
    }

    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null)
            throw new IllegalArgumentException();
        Queue<Point2D> q = new Queue<>();
        Iterator<Point2D> iter = ts.iterator();
        while (iter.hasNext()) {
            Point2D p = iter.next();
            if (rect.contains(p))
                q.enqueue(p);
        }
        return q;
    }

    public Point2D nearest(Point2D p) {
        if (p == null)
            throw new IllegalArgumentException();
        if (ts.isEmpty()) return null;

        Iterator<Point2D> iter = ts.iterator();
        double dist = 1.0;
        Point2D res = p;
        while (iter.hasNext()) {
            Point2D t = iter.next();
            double dp = t.distanceSquaredTo(p);
            if (dp < dist) {
                dist = dp;
                res = t;
            }
        }
        return res;
    }

    public static void main(String[] args) {
        PointSET tree = new PointSET();
        tree.insert(new Point2D(0.4, 0.2));
        tree.insert(new Point2D(0.2, 0.1));
        tree.insert(new Point2D(0.7, 0.3));
        tree.insert(new Point2D(0.3, 0.6));

        StdOut.println("Find existing point: " + tree.contains(new Point2D(0.7, 0.3)));
        StdOut.println("Find non-existant p: " + !tree.contains(new Point2D(0.2, 0.6)));
        StdOut.println("Count of nodes = 4 : " + tree.size());

        tree.insert(new Point2D(0.3, 0.6));

        StdOut.println("Cannot insert same : " + tree.size());

        StdDraw.setPenRadius(0.01);
        tree.draw();

        RectHV rect = new RectHV(0.3, 0.1, 0.9, 0.9);
        StdDraw.setPenColor(StdDraw.BLUE);
        StdDraw.setPenRadius(0.002);
        rect.draw();

        for (Point2D point : tree.range(rect)) {
            StdOut.println(point.toString());
        }

        StdOut.println();

        StdDraw.setPenColor(StdDraw.RED);
        StdDraw.setPenRadius(0.01);
        Point2D comp = new Point2D(0.4, 0.5);
        comp.draw();

        StdOut.println(tree.nearest(comp).toString());
    }
}
