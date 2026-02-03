package week3;
import java.util.ArrayList;

public class BruteCollinearPoints {
    private final ArrayList<LineSegment> lineSegments = new ArrayList<LineSegment>();

    public BruteCollinearPoints(Point[] points) {
        int arrSize = points.length;
        for (int i = 0; i < arrSize; i++) {
            for (int j = i + 1; j < arrSize; j++) {
                double slopePQ = points[i].slopeTo(points[j]);
                for (int k = j + 1; k < arrSize; k++) {
                    double slopeQR = points[j].slopeTo(points[k]);
                    for (int l = k + 1; l < arrSize; l++) {
                        double slopeRS = points[k].slopeTo(points[l]);
                        if (slopePQ == slopeQR && slopeRS == slopePQ && slopeQR == slopeRS) {
                            lineSegments.add(new LineSegment(points[i], points[l]));
                        }
                    }
                }
            }
        }
    }    // finds all line segments containing 4 points

    public int numberOfSegments() {
        return lineSegments.size();
    }        // the number of line segments

    public LineSegment[] segments() {
        return lineSegments.toArray(new LineSegment[0]);
    }                // the line segments

}