public class Point2D implements Comparable<Point2D> {
    public Point2D(double x, double y)              // construct the point (x, y)
    public  double x()                              // x-coordinate
    public  double y()                              // y-coordinate
    public  double distanceTo(Point2D that)         // Euclidean distance between two points
    public  double distanceSquaredTo(Point2D that)  // square of Euclidean distance between two points
    public     int compareTo(Point2D that)          // for use in an ordered symbol table
    public boolean equals(Object that)              // does this point equal that object?
    public    void draw()                           // draw to standard draw
    public  String toString()                       // string representation
}