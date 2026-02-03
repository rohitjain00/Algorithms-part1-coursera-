package week5;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.StdDraw;

public class KdTree {
    private KdNode root;

    private static class NearestPoint {
        private Point2D point;
        private double distance;
    }

    private static class KdNode {
        private KdNode        left;
        private KdNode        right;
        private boolean vertical;
        private Point2D key;

        public KdNode(Point2D point, boolean v) {
            key = point;
            vertical = v;
        }
    }


    public KdTree()  {
        this.root = null;
    }
    // construct an empty set of point

    public boolean isEmpty(){
        return root == null;
    }                      // is the set empty?

    public int size()  {
        return size(root);

    }                       // number of points in the set

    private int size(KdNode node) {
        if (node == null)
            return 0;
        else
            return(size(node.left) + 1 + size(node.right));
    }

    public void insert(Point2D p) {
        if (p == null) throw new IllegalArgumentException("calls insert() with a null point");
        root = insertRec(root, p, true);
    }// add the point to the set (if it is not already in the set)

    private KdNode insertRec(KdNode node, Point2D key, boolean vertical) {
        if (node == null) {
            return new KdNode(key, vertical);
        }

        if (node.key.equals(key)) return node;

        if (node.vertical) {
            if (key.x() < node.key.x()) {
                node.left = insertRec(node.left, key, !vertical);
            } else {
                node.right = insertRec(node.right, key, !vertical);
            }
        } else {
            if (key.y() < node.key.y()) {
                node.left = insertRec(node.left, key, !vertical);
            } else {
                node.right = insertRec(node.right, key, !vertical);
            }
        }
        return node;
    }


    public boolean contains(Point2D p) {
        if (p == null) throw new IllegalArgumentException("calls contains() with a null point");
        return contains(root, p);
    }            // does the set contain point p?

    private boolean contains(KdNode node, Point2D p) {
        if (node == null) return false;
        if (node.key.equals(p)) {
            return true;
        }

        if (node.vertical) {
            if (p.x() < node.key.x()) {
                return contains(node.left, p);
            } else {
                return contains(node.right, p);
            }
        }
        else {
            if (p.y() < node.key.y()) {
                return contains(node.left, p);
            } else {
                return contains(node.right, p);
            }
        }
    }

    public void draw() {
        StdDraw.clear();
        inOrder(root);
    }                        // draw all points to standard draw

    private void inOrder(KdNode node) {
        if (node == null) {return;}
        inOrder(node.left);
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.01);
        node.key.draw();

        StdDraw.setPenRadius();
        if (node.vertical) {
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.line(node.key.x(), 0, node.key.x(), 1);
        } else {
            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.line(0, node.key.y(), 1, node.key.y());
        }
        inOrder(node.right);
    }

    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new IllegalArgumentException("calls range() with a null rect");
        SET<Point2D> points = new SET<>();
        pointsInRange(root, points, rect);
        return points;
    }             // all points that are inside the rectangle

    private void pointsInRange(KdNode node, SET<Point2D> points, RectHV rect) {
        if (node == null) {return;}
        if (rect.contains(node.key)) {points.add(node.key);}

        if (node.vertical) {
             if (rect.xmin() < node.key.x()) pointsInRange(node.left, points, rect);
             if (rect.xmax() >= node.key.x()) pointsInRange(node.right, points, rect);
        } else {
             if (rect.ymin() < node.key.y()) pointsInRange(node.left, points, rect);
             if (rect.ymax() >= node.key.y()) pointsInRange(node.right, points, rect);
        }
    }

    public Point2D nearest(Point2D p) {
        if (p == null) throw new IllegalArgumentException("calls nearest() with a null point");
        if (isEmpty()) return null;
        NearestPoint np = new NearestPoint();
        np.point = null;
        np.distance = Double.POSITIVE_INFINITY;

        nearestPoint(root, p, np);
        return np.point;
    }
    // a nearest neighbor in the set to point p; null if the set is empty

    private void nearestPoint(KdNode node, Point2D p, NearestPoint np) {
        if (node == null) return;

        double dist = p.distanceSquaredTo(node.key);
        if (dist < np.distance) {
            np.point = node.key;
            np.distance = dist;
        }

        KdNode first = null;
        KdNode second = null;

        if (node.vertical) {
            if (p.x() < node.key.x()) {
                first = node.left;
                second = node.right;
            } else {
                first = node.right;
                second = node.left;
            }
        } else {
            if (p.y() < node.key.y()) {
                first = node.left;
                second = node.right;
            } else {
                first = node.right;
                second = node.left;
            }
        }

        nearestPoint(first, p, np);

        // Pruning rule
        double distToSplit;
        if (node.vertical) {
            distToSplit = node.key.x() - p.x();
        } else {
            distToSplit = node.key.y() - p.y();
        }

        if (distToSplit * distToSplit < np.distance) {
            nearestPoint(second, p, np);
        }
    }
}
