import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.StdDraw;

public class KdTree {
    private static final boolean RED   = true;
    private static final boolean BLACK = false;
    private KdNode root;
    private nearestPT nearestPT;

    private static class nearestPT {
        private Point2D point;
        private double distance;
    }

    private class KdNode {
        private KdNode        left;
        private KdNode        right;
        private boolean vertical;
        private Point2D key;
        private boolean color;

        public KdNode(Point2D point, boolean c , final boolean v) {
            key = point;
            vertical = v;
            color = c;
        }
    }


    public         KdTree()  {
        this.root = null;
    }
    // construct an empty set of point

    public           boolean isEmpty(){
        return root == null;
    }                      // is the set empty?

    public               int size()  {
        return size(root);

    }                       // number of points in the set

    private int size(KdNode node) {
        if (node == null)
            return 0;
        else
            return(size(node.left) + 1 + size(node.right));
    }

    public void insert(Point2D p) {

        root = insertRec(root, p,true);

    }// add the point to the set (if it is not already in the set)

    private KdNode insertRec(KdNode node, Point2D key, boolean vertical) {
            if (node == null) {
                node = new KdNode(key,vertical,vertical);
                return node;
            } else if (node.vertical) {
                if (node.key.x() > key.x()) {
                    node.left = insertRec(node.right, key,!vertical);
                } else {
                    node.right = insertRec(node.right, key,!vertical);
                }
            } else {
                if (node.key.y() > key.y()) {
                    node.left = insertRec(node.right, key,!vertical);
                } else {
                    node.right = insertRec(node.left, key,!vertical);
                }
            }
            return node;
        }


    public boolean contains(Point2D p) {
        return contains(root, p);
    }            // does the set contain point p?

    private boolean contains(KdNode node, Point2D p) {
        if (node.key.equals(p)) {
            return true;
        }


        else if (node.vertical) {
            if (node.key.x() > p.x()) {
                contains(node.right, p);
            } else {
                contains(node.right, p);
            }
        }
        else {
            if (node.key.y() > p.y()) {
                contains(node.right, p);
            } else {
                contains(node.left, p);
            }
        }
        return false;
    }

    public void draw() {
        StdDraw.clear();
        StdDraw.setCanvasSize(1,1);
        inOrder(root);
    }                        // draw all points to standard draw

    private void inOrder(KdNode node) {
        if (node == null) {return;}
        inOrder(node.left);
        if (node.vertical) {
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.line(node.key.x(), 0, node.key.x(), 1);
            StdDraw.setPenColor(StdDraw.BLACK);
        } else {
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.line(0, node.key.y(), 1, node.key.y());
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.point(node.key.x(), node.key.y());
        }
        inOrder(node.right);
    }
    public Iterable<Point2D> range(RectHV rect) {
        SET<Point2D> points = new SET<>();
        pointsInRnage(root,points,rect);
        return points;
    }             // all points that are inside the rectangle

    private void pointsInRnage(KdNode node, SET<Point2D> points, RectHV rect) {
        if (node == null) {return;}
        if (rect.contains(node.key)) {points.add(node.key);};
        if (node.vertical && (node.key.x() <= rect.xmin())) {pointsInRnage(node.left,points,rect);}
        if (node.vertical && (node.key.x() > rect.xmax())) {pointsInRnage(node.right,points,rect);}
        if (!node.vertical && (node.key.y() <= rect.ymin())) {pointsInRnage(node.left,points,rect);}
        if (!node.vertical && (node.key.y() > rect.ymax())) {pointsInRnage(node.right,points,rect);}
    }
    public Point2D nearest(Point2D p) {
        nearestPoint(root,p,nearestPT);
        return nearestPT.point;
    }
    // a nearest neighbor in the set to point p; null if the set is empty

    private void nearestPoint(KdNode node, Point2D p, nearestPT NPoint) {
        if (node == null) return;

        if (NPoint == null) {
            NPoint.point = node.key;
            NPoint.distance = p.distanceTo(node.key);
        } else {
            double dist = p.distanceSquaredTo(node.key);
            if (dist < NPoint.distance) {
                NPoint.point = node.key;
                NPoint.distance = dist;
            }
        }

        if (node.vertical) {
            if (p.x() <= node.key.x()) {
                nearestPoint(node.left, p, NPoint);
                if (node.right != null && node.right.key.distanceSquaredTo(p) < NPoint.distance) {
                    nearestPoint(node.right, p, NPoint);
                }
            } else if (p.x() >= node.key.x()) {
                nearestPoint(node.right, p, NPoint);
                if (node.left != null && node.left.key.distanceSquaredTo(p) < NPoint.distance) {
                    nearestPoint(node.left, p, NPoint);
                }
            }
        } else {
            if (p.y() <= node.key.y()) {
                nearestPoint(node.left, p, NPoint);
                if (node.right != null && node.right.key.distanceSquaredTo(p) < NPoint.distance) {
                    nearestPoint(node.right, p, NPoint);
                }
            } else if (p.y() >= node.key.y()) {
                nearestPoint(node.right, p, NPoint);
                if (node.left != null && node.left.key.distanceSquaredTo(p) < NPoint.distance) {
                    nearestPoint(node.left, p, NPoint);
                }
            }
        }
    }
}