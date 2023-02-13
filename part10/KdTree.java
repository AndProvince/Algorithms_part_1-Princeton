package part10;/* *****************************************************************************
 *  Name:              Andrei Ponomarev
 **************************************************************************** */

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class KdTree {

    private Node root;

    private class Node {
        private Point2D p;
        private Node left, right;
        private int count;
        private boolean div;

        public Node(Point2D p) {
            this.p = p;
        }
    }

    public KdTree() {
        root = null;
    }

    public boolean isEmpty() {
        return size(root) == 0;
    }

    public int size() {
        return size(root);
    }

    private int size(Node x) {
        if (x == null) return 0;
        return x.count;
    }

    public void insert(Point2D p) {
        if (p == null)
            throw new IllegalArgumentException();
        root = insert(root, p, true);
    }

    private Node insert(Node x, Point2D p, boolean div) {
        if (x == null) {
            Node nn = new Node(p);
            nn.div = div;
            nn.count = 1;
            return nn;
        }
        if (p.equals(x.p)) return x;
        if (x.div) {
            if ((p.x() < x.p.x()) || (p.x() == x.p.x() && p.y() < x.p.y()))
                x.left = insert(x.left, p, false);
            else
                x.right = insert(x.right, p, false);
        }
        else {
            if ((p.y() < x.p.y()) || (p.y() == x.p.y() && p.x() < x.p.x()))
                x.left = insert(x.left, p, true);
            else
                x.right = insert(x.right, p, true);
        }
        x.count = 1 + size(x.left) + size(x.right);
        return x;
    }

    public boolean contains(Point2D p) {
        if (p == null)
            throw new IllegalArgumentException();
        return contains(root, p);
    }

    private boolean contains(Node x, Point2D p) {
        if (x == null) return false;
        if (p.equals(x.p)) return true;
        if (x.div) {
            if ((p.x() < x.p.x()) || (p.x() == x.p.x() && p.y() < x.p.y()))
                return contains(x.left, p);
            else
                return contains(x.right, p);
        }
        else {
            if ((p.y() < x.p.y()) || (p.y() == x.p.y() && p.x() < x.p.x()))
                return contains(x.left, p);
            else
                return contains(x.right, p);
        }
    }

    public void draw() {
        draw(root);
    }

    private void draw(Node x) {
        if (x == null) return;
        x.p.draw();
        draw(x.left);
        draw(x.right);
    }

    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null)
            throw new IllegalArgumentException();
        Queue<Point2D> q = new Queue<>();
        range(root, rect, q, 0.0, 1.0, 0.0, 1.0);
        return q;
    }

    private void range(Node x, RectHV rect, Queue<Point2D> q, double xmin, double xmax, double ymin,
                       double ymax) {
        if (x == null) return;
        if (rect.contains(x.p))
            q.enqueue(x.p);
        if (x.div) {
            RectHV lrect = new RectHV(xmin, ymin, x.p.x(), ymax);
            if (rect.intersects(lrect))
                range(x.left, rect, q, xmin, x.p.x(), ymin, ymax);
            RectHV rrect = new RectHV(x.p.x(), ymin, xmax, ymax);
            if (rect.intersects(rrect))
                range(x.right, rect, q, x.p.x(), xmax, ymin, ymax);
        }
        else {
            RectHV lrect = new RectHV(xmin, ymin, xmax, x.p.y());
            if (rect.intersects(lrect))
                range(x.left, rect, q, xmin, xmax, ymin, x.p.y());
            RectHV rrect = new RectHV(xmin, x.p.y(), xmax, ymax);
            if (rect.intersects(rrect))
                range(x.right, rect, q, xmin, xmax, x.p.y(), ymax);
        }
    }

    public Point2D nearest(Point2D p) {
        if (p == null)
            throw new IllegalArgumentException();
        RectHV r = new RectHV(0.0, 0.0, 1.0, 1.0);
        return nearest(root, p, r);
    }

    private Point2D nearest(Node x, Point2D p, RectHV r) {
        if (x == null) return null;
        double dist = x.p.distanceSquaredTo(p);
        Point2D res = x.p;

        RectHV lrect;
        RectHV rrect;
        if (x.div) {
            lrect = new RectHV(r.xmin(), r.ymin(), x.p.x(), r.ymax());
            rrect = new RectHV(x.p.x(), r.ymin(), r.xmax(), r.ymax());
        }
        else {
            lrect = new RectHV(r.xmin(), r.ymin(), r.xmax(), x.p.y());
            rrect = new RectHV(r.xmin(), x.p.y(), r.xmax(), r.ymax());
        }

        Point2D lp = null;
        Point2D rp = null;
        if (lrect.contains(p)) {
            lp = nearest(x.left, p, lrect);
        }
        else {
            rp = nearest(x.right, p, rrect);
        }

        if ((lp != null) && rrect.distanceSquaredTo(p) < lp.distanceSquaredTo(p)) {
            rp = nearest(x.right, p, rrect);
        }
        else if ((rp != null) && lrect.distanceSquaredTo(p) < rp.distanceSquaredTo(p)) {
            lp = nearest(x.left, p, lrect);
        }

        if ((lp != null) && (dist > lp.distanceSquaredTo(p))) {
            res = lp;
            dist = lp.distanceSquaredTo(p);
        }
        if ((rp != null) && (dist > rp.distanceSquaredTo(p)))
            res = rp;

        return res;
    }

    public static void main(String[] args) {
        KdTree tree = new KdTree();
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
