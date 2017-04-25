import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

public class PointSET {//are you fucking kidding me about this exercise? nothing we need to know about red black BST
	private TreeSet<Point2D> mSet;//TreeSet is implemented in Red-black BST

	public PointSET() {
		mSet = new TreeSet<>();
	}

	public boolean isEmpty() {
		return mSet.isEmpty();
	}

	public void insert(Point2D p) {
		mSet.add(p);
	}

	public boolean contains(Point2D p) {
		return mSet.contains(p);
	}

	public void draw() {
		Iterator<Point2D> iterator = mSet.iterator();
		while (iterator.hasNext()) {
			//System.out.println("what");
			iterator.next().draw();
		}
	}

	// range search here..
	public Iterable<Point2D> range(RectHV rect) {
		List<Point2D> inside = new ArrayList<>();
		for(Point2D point: mSet) {
			if (rect.contains(point)) inside.add(point);
		}
		
		return inside;
	}

	public Point2D nearest(Point2D p) {// only need one..
		Iterator<Point2D> iterator = mSet.iterator();
		Point2D nearest = iterator.next();
		double distance = nearest.distanceTo(p);
		while(iterator.hasNext()) {
			Point2D cur = iterator.next();
			if (cur.distanceTo(p) < distance) {
				nearest = cur;
				distance = cur.distanceTo(p);
			}
		}
		
		return nearest;
	}

	public static void main(String[] args) {

	}
}
