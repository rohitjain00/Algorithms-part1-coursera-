import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.SET;
import java.lang.*;

import static edu.princeton.cs.algs4.StdDraw.point;

public class PointSET {
    private SET<Point2D> setOfPoints;

    public         PointSET() {
        setOfPoints = new SET<>();
    }                               // construct an empty set of point
    public           boolean isEmpty(){
        return setOfPoints.isEmpty();
    }                      // is the set empty?
    public               int size()  {
        return setOfPoints.size();
    }                       // number of points in the set
    public              void insert(Point2D p) {
        if(p == null) {
            throw new java.lang.NullPointerException();
        }
        if (!setOfPoints.contains(p)) {
            setOfPoints.add(p);
        }
    }             // add the point to the set (if it is not already in the set)
    public           boolean contains(Point2D p) {
        return setOfPoints.contains(p);
    }            // does the set contain point p?
    public              void draw() {
        while(setOfPoints.iterator().hasNext()) {
            Point2D point = setOfPoints.iterator().next();
            point(point.x(),point.y());
        }
    }                        // draw all points to standard draw
    public Iterable<Point2D> range(RectHV rect) {
        SET<Point2D> setOfPointsInRectangle = null;
        while(setOfPoints.iterator().hasNext()) {
            Point2D point = setOfPoints.iterator().next();
            if (rect.contains(point)) {
                setOfPointsInRectangle.add(point);
            }
        }
        return (Iterable<Point2D>) setOfPointsInRectangle.iterator();
    }             // all points that are inside the rectangle
    public           Point2D nearest(Point2D p) {
        Point2D point = setOfPoints.iterator().next();
        Point2D nearestPoint = point;
        double distance = point.distanceTo(p);
        while(setOfPoints.iterator().hasNext()) {
            point = setOfPoints.iterator().next();
            if (point.distanceTo(p) < distance) {
                distance = point.distanceTo(p);
                nearestPoint = point;
            }
        }
        return nearestPoint;
        }            // a nearest neighbor in the set to point p; null if the set is empty

    public static void main(String[] args) {
        
    }                 // unit testing of the methods (optional)
}