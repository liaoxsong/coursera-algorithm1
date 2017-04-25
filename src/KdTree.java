import java.util.TreeSet;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

public class KdTree {
	
	private TreeSet<Point2D> mSet;//ordered set in a tree

	public KdTree() {
		mSet = new TreeSet<>();
	}
	
	public boolean isEmpty() {
		return mSet.isEmpty();
	}
	
	public void insert(Point2D p ) {
		
	}
	public boolean contains(Point2D p) {
		return mSet.contains(p);
	}
	
	public void draw() {}
	
	public Iterable<Point2D> range(RectHV rect) {
		return null;
	}
	
	public Point2D nearest(Point2D p) {//only need one..
		return null;
	}
	
	public static void main (String [] args) {
		TreeSet s = new TreeSet();
		s.add(22);
		s.add(2);
		s.add(5);	
		
		s.add(5);
		System.out.println(s);
	}
}
