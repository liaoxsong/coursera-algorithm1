import java.util.Comparator;
import java.util.Iterator;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class Solver {
	
	private boolean mSolvable;
	
	private MinPQ<SearchNode> mPQ, mPQTwin;
	
	private static class SearchNode {
		Board board;
		SearchNode previous;
		int moves;
		int priority;//for testing
		
		SearchNode(Board current, SearchNode previous, int moves) {
			this.board = current;
			this.previous = previous;
			this.moves = moves;
			this.priority = moves + board.manhattan();
		}
		
		static Comparator<SearchNode> hammingComparator = new Comparator<SearchNode> () {
			@Override
			public int compare(SearchNode thisNode, SearchNode otherNode) {
				if (thisNode.board.hamming() + thisNode.moves < otherNode.board.hamming() + otherNode.moves)
				{
					return -1;
				} else if (thisNode.board.hamming() + thisNode.moves > otherNode.board.hamming() + otherNode.moves) {
					return 1;
				}
				
				if (thisNode.moves > otherNode.moves) {
					return -1;
				} else if (thisNode.moves < otherNode.moves) {
					return 1;
				}
				return 0;
			}
		};
		
		static Comparator<SearchNode> manhattanComparator = new Comparator<SearchNode> () {
			@Override
			public int compare(SearchNode thisNode, SearchNode otherNode) {
				if (thisNode.priority < otherNode.priority)
				{
					return -1;
				} else if (thisNode.priority > otherNode.priority) {
					return 1;
				}
			
				//same priority..check moves, moves less is on top
				return 0;
			}
		};
	  
	}
	
	public Solver(Board initial) {
		if (initial == null) throw new NullPointerException();
		
		mPQ = new MinPQ<SearchNode>(SearchNode.manhattanComparator);//homework default is manhanttan
		mPQ.insert(new SearchNode(initial, null, 0));
		
		mPQTwin = new MinPQ<SearchNode>(SearchNode.manhattanComparator);
		mPQTwin.insert(new SearchNode(initial.twin(), null, 0));

		while(!mPQ.min().board.isGoal()) {
			StdDraw.pause(20);
			//mCurrentMoves+=1;
		
			SearchNode node = mPQ.delMin();
			StdOut.println("moves:" + node.moves);
			StdOut.println("current min with priority:" + node.priority + "\n" + node.board) ;
			//interchanging this and twin node, see whoever find solution first
			int addMoves = node.moves + 1;
			for(Board b: node.board.neighbors()) {
				SearchNode previous = node.previous;		
				if (previous != null && b.equals(previous)) continue; //optimization
				
				SearchNode next = new SearchNode(b, node, addMoves);
				
				StdOut.println("inserting node with moves:" + next.moves + "\n" + next.board) ;
				mPQ.insert(next);
			}
			
//			SearchNode twinNode = mPQTwin.delMin();
//			
//			for(Board b: twinNode.board.neighbors()) {
//				SearchNode previous = twinNode.previous;
//				if (previous != null && b.equals(previous)) continue; 
//				
//				mPQTwin.insert(new SearchNode(b, previous, mCurrentMoves));
//			}
		}
		
		//only original board have a solution, then we have a solvable board
		this.mSolvable = mPQ.min().board.isGoal();
		System.out.println(this.mSolvable? "solution found": "not found");
	}
	public boolean isSolvable() {
		return this.mSolvable;
	}
	
	public int moves() {
		if (!isSolvable()) return 0;
		return mPQ.min().moves;
	}
	
	public Iterable<Board> solution(){
		if (!isSolvable()) return null;
		Stack<Board> stack = new Stack<>();
		SearchNode node = mPQ.min();
		while(node != null) {
			stack.push(node.board);
			node = node.previous;
		}
		return stack;
	}
	
	public static void main(String []args){ 
		   // create initial board from file
		
		String file = "/Users/song/Documents/EclipseWorkspace/HelloEclipse/src/8puzzle/puzzle3x3-unsolvable.txt";
	    In in = new In(file);
	    int n = in.readInt();
	    int[][] blocks = new int[n][n];
	    for (int i = 0; i < n; i++)
	        for (int j = 0; j < n; j++)
	            blocks[i][j] = in.readInt();
	    Board initial = new Board(blocks);

	    // solve the puzzle
	    Solver solver = new Solver(initial);

	    // print solution to standard output
	    if (!solver.isSolvable())
	        StdOut.println("No solution possible");
	    else {
	        StdOut.println("Minimum number of moves = " + solver.moves());
	        for (Board board : solver.solution())
	            StdOut.println(board);
	    }
	}
}
