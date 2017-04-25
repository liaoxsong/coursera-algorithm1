import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class Solver {
	
	private boolean mSolvable;
	private boolean mTwinSolvable;
	
	private MinPQ<SearchNode> mPQ, mPQTwin;
	private SearchNode mEndNode;
	
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
				//the more moves it has, the priorier we use?
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
			
				//the more moves it has, the priorier we use?
				if (thisNode.moves > otherNode.moves) {
					return -1;
				} else if (thisNode.moves < otherNode.moves) {
					return 1;
				}
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
		
		//StdOut.println("Twin board:\n" + mPQTwin.min().board);
		while( !mSolvable && !mTwinSolvable) {//while loop循环的前提下必须两个board都是unsolvable的，只要有一个提前解读了loop就会停止
			mSolvable = solveStep(mPQ);
			mTwinSolvable = solveStep(mPQTwin);
		}
	}
	
	private boolean solveStep(MinPQ<SearchNode> pq) {
		SearchNode node = pq.delMin();
		if (node.board.isGoal()) {
			mEndNode = node;
			return true;
		}
		
		int addMoves = node.moves + 1;
		for(Board b: node.board.neighbors()){
			if(node.previous == null || !b.equals(node.previous.board)) {
    			SearchNode neighbor = new SearchNode(b, node, addMoves);
    			pq.insert(neighbor);
    		}
		} 		
		return false;
	}
	
	public boolean isSolvable() {
		return this.mSolvable;
	}
	

	public int moves() {
		if(mSolvable) {
			int moves = 0;
			SearchNode temp = mEndNode;
			while(temp.previous != null) {
				moves++;
				
				temp = temp.previous;
			}
			return moves;
		}
		return -1;
	}
	
	public Iterable<Board> solution(){
		if (!isSolvable()) return null;
		SearchNode temp = mEndNode;
		if (mPQ.isEmpty()) return Arrays.asList(temp.board);
		
		Stack<Board> stack = new Stack<>();
		
		while(temp != null) {
			stack.push(temp.board);
			temp = temp.previous;
		}
		return stack;
	}
	
	public static void main(String []args){ 
		
		String file = "/Users/song/Documents/EclipseWorkspace/HelloEclipse/src/8puzzle/puzzle05.txt";
		
		In in = new In(args.length == 0 ? file : args[0]);
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
	            StdOut.println("solution:\n" + board);
	    }
	}
}
