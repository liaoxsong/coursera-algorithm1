
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

//91 out 100 score
public class FastCollinearPoints {

	private List<LineSegment> mLineSegments;
	private final int BINGO = 4;
	
	public FastCollinearPoints(Point[] points) {
		if (points == null)
			throw new NullPointerException("points array is null");
		checkDuplicates(points);

		mLineSegments = new ArrayList<>();
		// now starts finding
		findCollinearPoints(points);
	}

	private void findCollinearPoints(Point[] points) {
		int N = points.length;

		for (int i = 0; i < N - 1; i++) {
			HashMap<Double, List<Point>> map = new HashMap<>();
			Arrays.sort(points, i + 1, points.length, points[i].slopeOrder());

			double currentSlope = Double.NEGATIVE_INFINITY;
			for (int j = i + 1; j < N; j++) {
				double slope = points[i].slopeTo(points[j]);
				//System.out.println("at " + j + "|" + points[j] + " 's slope:" + slope);
				if (slope == currentSlope) {
					List<Point> pts = map.get(slope);
					pts.add(points[j]);
					map.put(slope, pts);
				} else {
					currentSlope = slope;
					map.put(currentSlope, new ArrayList<Point>(Arrays.asList(points[i], points[j])));
				}
			}
			/** 
			 * sub-segments are still added for 5 points or longer.. leave if for now
			 * */
			for (Double d : map.keySet()) {
				List<Point> pts = map.get(d);
				if (pts.size() >= BINGO) {
					Collections.sort(pts);
					//System.out.println("adding " + pts.get(0) + " ," + pts.get(pts.size() - 1));
					mLineSegments.add(new LineSegment(pts.get(0), pts.get(pts.size() - 1)));
				}
			}
		}
	}

	// the number of line segments
	public int numberOfSegments() {
		return mLineSegments.size();
	}

	// the line segments
	public LineSegment[] segments() {
		return mLineSegments.toArray(new LineSegment[mLineSegments.size()]);
	}

	public static void main(String[] args) {
		// read the n points from a file

		String localFile = "/Users/song/Documents/EclipseWorkspace/HelloEclipse/src/collinear/input6.txt";
		// File file = new File(args[0]);
		File file = new File(args[0]);
		In in = new In(file);
		int n = in.readInt();

		Point[] points = new Point[n];
		for (int i = 0; i < n; i++) {
			int x = in.readInt();
			int y = in.readInt();
			points[i] = new Point(x, y);
		}

		// draw the points
		StdDraw.enableDoubleBuffering();
		StdDraw.setXscale(0, 32768);
		StdDraw.setYscale(0, 32768);
		StdDraw.setPenRadius(0.01);
		for (Point p : points) {

			p.draw();
		}
		StdDraw.show();

		// print and draw the line segments
		FastCollinearPoints collinear = new FastCollinearPoints(points);
		StdOut.println("segments:" + collinear.numberOfSegments());
		for (LineSegment segment : collinear.segments()) {
			StdOut.println(segment);
			segment.draw();
		}
		StdDraw.show();
	}

	private void checkDuplicates(Point[] points) {
		for (int i = 0; i < points.length - 1; i++) {
			if (points[i] == null)
				throw new NullPointerException("null point");
			for (int j = i + 1; j < points.length; j++) {
				if (points[i].compareTo(points[j]) == 0) {
					throw new IllegalArgumentException("Duplicated entries in given points.");
				}
			}
		}
	}

}
