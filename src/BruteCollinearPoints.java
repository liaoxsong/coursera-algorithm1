
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Quick;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class BruteCollinearPoints {

	private List<LineSegment> mLineSegments;
	// finds all line segments containing 4 points
	

   public BruteCollinearPoints(Point[] points) {
	   if (points == null) throw new NullPointerException("points array is null");
	   checkDuplicates(points);
	   //let the story begin
	   mLineSegments = new ArrayList<>();	   
	   Arrays.sort(points);
	   
	   for(int i=0;i<=points.length-4;i++) {
		   for(int j=i+1; j<=points.length-3;j++) {
			   for(int k=j+1; k<=points.length-2;k++) {
				   for(int d=k+1;d<=points.length-1;d++) {
					   
					   double slopeA = points[i].slopeTo(points[j]);
					   double slopeB = points[i].slopeTo(points[k]);
					   double slopeC = points[i].slopeTo(points[d]);
					   
					   if ( (slopeA == slopeB) && (slopeA == slopeC)) {
						//  System.out.println("found one motherfucker");
						 mLineSegments.add(new LineSegment(points[i], points[d]));
					   }
				   }
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

	    String localFile = "/Users/song/Documents/EclipseWorkspace/HelloEclipse/src/collinear/input8.txt";
	    // read the n points from a file
	    File file = new File(args[0]);
	    In in = new In(file);
	    int n = in.readInt();
	    
	    Point[] points = new Point[n];
	    for (int i = 0; i < n; i++) {
	        int x = in.readInt();
	        int y = in.readInt();
	        points[i] = new Point(x, y);
	    }
//	    points[0] = new Point(0, 1);
//	    points[1] = new Point(2, 2);
//	    points[2] = new Point(4, 4);
//	    points[3] = new Point(4, 3);
//	    points[4] = new Point(5, 5);
//	    points[5] = new Point(10, 10);
	    
	    // draw the points
	    StdDraw.enableDoubleBuffering();
	    StdDraw.setXscale(0, 32768);
	    StdDraw.setYscale(0, 32768);
	    for (Point p : points) {
	        p.draw();
	    }
	    StdDraw.show();

	    // print and draw the line segments
	    BruteCollinearPoints collinear = new BruteCollinearPoints(points);
	    StdOut.println("segments:" + collinear.numberOfSegments());
	    for (LineSegment segment : collinear.segments()) {
	        StdOut.println(segment);
	        segment.draw();
	    }
	    StdDraw.show();
	}
   
	private void checkDuplicates(Point[] points) {
		for (int i = 0; i < points.length - 1; i++) {
			if (points[i] == null) throw new NullPointerException("null point");
            for (int j = i + 1; j < points.length; j++) {
                if (points[i].compareTo(points[j]) == 0) {
                    throw new IllegalArgumentException("Duplicated entries in given points.");
                }
            }
        }
	}
}
