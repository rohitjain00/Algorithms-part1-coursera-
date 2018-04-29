import java.util.ArrayList;
import java.util.Arrays;

public class FastCollinearPoints {
    ArrayList<LineSegment> Linesegments = new ArrayList<LineSegment>();

    public FastCollinearPoints(Point[] points) {
        int arrSize = points.length;
        Point[] copyPoint = new Point[arrSize];
        System.arraycopy(points,0,copyPoint,0,arrSize);
        for (int i = 0; i < arrSize; i++){
            Arrays.sort(copyPoint,points[i].slopeOrder());
            for (int j = 0; j < arrSize-2; j++) {
                Point start = copyPoint[j];
                Point end = null;
                int numberOfLines = 1;
                while (copyPoint[j].slopeTo(copyPoint[j+1]) == copyPoint[j+1].slopeTo(copyPoint[j+2])){
                    numberOfLines += 1;
                    end = copyPoint[j+2];
                    j++;
                }
                if (numberOfLines > 2) {
                    Linesegments.add(new LineSegment(start,end));
                }
            }
        }
    }     // finds all line segments containing 4 or more points
    public           int numberOfSegments() {
        return Linesegments.size();
    }       // the number of line segments
    public LineSegment[] segments() {
        return (LineSegment[]) Linesegments.toArray();
    }                // the line segments
}