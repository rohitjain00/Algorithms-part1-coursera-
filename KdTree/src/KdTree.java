import edu.princeton.cs.algs4.*;

public class KdTree {
    private static final boolean RED   = true;
    private static final boolean BLACK = false;
    private KdNode root;
    private nearestPT nearestPT;
    private KdNode currNode;

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

        public KdNode(Point2D point, boolean c , final boolean v)
        {
            key = point;
            vertical = v;
            color = c;
        }


    }


    public         KdTree()  {
        this.root = null;
        this.currNode = null;
    }
    // construct an empty set of point

    public           boolean isEmpty(){
        return root == null;
    }                      // is the set empty?

    public               int size()  {
        int counttoSend = 0;
        return size(root, counttoSend);

    }                       // number of points in the set

    private int size(KdNode node, int count) {
        if (root == null ) return 0;
        else if (root.right != null) {return size(node.right,count++);}
        else if (root.left != null) {return  size(node.left,count++);}
        return count;
    }
    public void insert(Point2D p) {
        System.out.println("Insert function started");
        root = insertRec(root, p);

    }// add the point to the set (if it is not already in the set)

    private KdNode insertRec(KdNode node, Point2D key) {
            if (node == null) {
                System.out.println("New node formed");
                node = new KdNode(key,true,true);
                return node;
            }


            else if (node.vertical) {
                if (node.key.x() > key.x()) {
                    if (node.left!= null) {
                        currNode = node.left;
                    }
                    System.out.println("Left");
                    node.left = insertRec(node.right, key);
                } else {
                    if (node.right!= null) {
                        currNode = node.right;
                    }
                    node.right = insertRec(node.right, key);
                }
            }
            else {
                if (node.key.y() > key.y()) {
                    if (node.left!= null) {
                        currNode = node.left;
                    }
                    System.out.println("Left1");

                    node.left = insertRec(node.right, key);
                } else {
                    if (node.right!= null) {
                        currNode = node.right;
                    }
                    System.out.println("Right1");

                    node.right = insertRec(node.left, key);
                }
            }
            return node;
        }


    public           boolean contains(Point2D p) {
        return contains(root, p);
    }            // does the set contain point p?

    private boolean contains(KdNode node, Point2D p) {
        if (node.key.equals(p)) return true;
        else if (node.vertical) {
            if (p.x() > node.key.x()) {

                if (node.right == null) {
                    return false;
                }

                contains(node.right, p);
            } else if (p.x() < node.key.x()) {

                if (node.left == null) {
                    return false;
                }

                contains(node.left, p);
            }
        } else if (!node.vertical) {
            if (p.y() > node.key.y()) {

                if (node.right == null) {
                    return false;
                }

                contains(node.right, p);
            } else if (p.y() < node.key.y()) {

                if (node.left == null) {
                    return false;
                }

                contains(node.left, p);
            }
        }
        return false;
    }

    public              void draw() {
        StdDraw.clear();
        StdDraw.setCanvasSize(1,1);
        inOrder(root);
    }                        // draw all points to standard draw

    private void inOrder(KdNode node) {
        if (node == null) {
            return;
        }

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
            inOrder(node.right);
        }
    }
    public Iterable<Point2D> range(RectHV rect) {
        SET<Point2D> points = new SET<>();
        pointsInRnage(root,points,rect);
        return (Iterable<Point2D>) points.iterator();
    }             // all points that are inside the rectangle

    private void pointsInRnage(KdNode node, SET points, RectHV rect) {
        if (rect.contains(node.key)) {points.add(node.key);};
        if (node.vertical && node.key.x() < rect.xmin()) {pointsInRnage(node.left,points,rect);}
        if (node.vertical && node.key.x() > rect.xmax()) {pointsInRnage(node.right,points,rect);}
        if (!node.vertical && node.key.y() < rect.ymin()) {pointsInRnage(node.left,points,rect);}
        if (!node.vertical && node.key.y() > rect.ymax()) {pointsInRnage(node.right,points,rect);}
        else {
            pointsInRnage(node.right,points,rect);
        }

    }
    public Point2D nearest(Point2D p) {
        nearestPoint(root,p,nearestPT);
        return nearestPT.point;
    }
    // a nearest neighbor in the set to point p; null if the set is empty

    private void nearestPoint(KdNode node, Point2D p, nearestPT point) {
        if (node == null) return;

        if (nearestPT == null) {
            nearestPT.point = node.key;
            nearestPT.distance = p.distanceTo(node.key);
        } else {
            double dist = p.distanceSquaredTo(node.key);
            if (dist < nearestPT.distance) {
                nearestPT.point = node.key;
                nearestPT.distance = dist;
            }
        }

        if (node.vertical) {
            if (p.x() <= node.key.x()) {
                nearestPoint(node.left, p, nearestPT);
                if (node.right != null && node.right.key.distanceSquaredTo(p) < nearestPT.distance) {
                    nearestPoint(node.right, p, nearestPT);
                }
            } else if (p.x() >= node.key.x()) {
                nearestPoint(node.right, p, nearestPT);
                if (node.left != null && node.left.key.distanceSquaredTo(p) < nearestPT.distance) {
                    nearestPoint(node.left, p, nearestPT);
                }
            }
        } else {
            if (p.y() <= node.key.y()) {
                nearestPoint(node.left, p, nearestPT);
                if (node.right != null && node.right.key.distanceSquaredTo(p) < nearestPT.distance) {
                    nearestPoint(node.right, p, nearestPT);
                }
            } else if (p.y() >= node.key.y()) {
                nearestPoint(node.right, p, nearestPT);
                if (node.left != null && node.left.key.distanceSquaredTo(p) < nearestPT.distance) {
                    nearestPoint(node.left, p, nearestPT);
                }
            }
        }
    }
}