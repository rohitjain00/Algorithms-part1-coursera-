public class RectHV {
    public    RectHV(double xmin, double ymin,      // construct the rectangle [xmin, xmax] x [ymin, ymax]
                     double xmax, double ymax)      // throw a java.lang.IllegalArgumentException if (xmin > xmax) or (ymin > ymax)
    public  double xmin()                           // minimum x-coordinate of rectangle
    public  double ymin()                           // minimum y-coordinate of rectangle
    public  double xmax()                           // maximum x-coordinate of rectangle
    public  double ymax()                           // maximum y-coordinate of rectangle
    public boolean contains(Point2D p)              // does this rectangle contain the point p (either inside or on boundary)?
    public boolean intersects(RectHV that)          // does this rectangle intersect that rectangle (at one or more points)?
    public  double distanceTo(Point2D p)            // Euclidean distance from point p to closest point in rectangle
    public  double distanceSquaredTo(Point2D p)     // square of Euclidean distance from point p to closest point in rectangle
    public boolean equals(Object that)              // does this rectangle equal that object?
    public    void draw()                           // draw to standard draw
    public  String toString()                       // string representation
}