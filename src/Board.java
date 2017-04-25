import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdRandom;

public class Board {
	private int N;
	private final int [][] mBlocks;
	private Set<Board> mNeigbors;
	
	public Board(int[][] blocks) {
		mBlocks = blocks;
		N =  mBlocks.length;
		mNeigbors = new HashSet<>();
	}

	public int dimension() {
		return N;
	}
	
	public int hamming() {
		int wrongPositionCount = 0;
		
		for(int i=0;i<N;i++){
			for(int j=0;j<N;j++) {
				if (mBlocks[i][j] == 0) continue;
				int correctPosition = i * N + j + 1;
				if (mBlocks[i][j] != correctPosition) wrongPositionCount+=1;
			}
		}
	
		return wrongPositionCount;
	}

	
	public int manhattan() {
		int totalCount = 0;
		for(int i=0;i<N;i++){
			for(int j=0;j<N;j++) {
				int currentValue = mBlocks[i][j];
				if (currentValue == 0 ) continue;
				int targetRow = (currentValue - 1) / N;
				int targetCol = (currentValue - 1) % N ;
				int rowDelta = abs(i - targetRow);
				int colDelta = abs(j - targetCol);
				totalCount += rowDelta;
				totalCount += colDelta;
			}
		}
		return totalCount;
	}
	
	public boolean isGoal() {
		return hamming() == 0;
	}
	
	public Board twin() {
		
		int [][] twin = getNewCopy();

		while(true) {
			int [] indices = new int[4];
			for(int i=0;i<4;i++){
				indices[i] = StdRandom.uniform(N);			
			}
		
			int firstValue = mBlocks[indices[0]][indices[1]];
			int secondValue = mBlocks[indices[2]][indices[3]];
			
			//need two non-zero and distinct values
			if (firstValue != 0 && secondValue != 0 && firstValue != secondValue) {
				//swap
				twin[indices[0]][indices[1]] = secondValue;
				twin[indices[2]][indices[3]] = firstValue;
				

				return new Board(twin);
			}
		}
	}
		
	public  boolean equals(Object y) {
		if ( y == null) throw new NullPointerException("");
		
    	if (this == y) {
    		return true;
    	}
    	
    	if (this.getClass() != y.getClass()) {
    		return false;
    	}
    	
    	Board that = (Board) y;
    	
		if (N != that.dimension()) return false;
		
		for (int row = 0; row < this.dimension(); row++) {
    		for (int col = 0; col < this.dimension(); col++) {
    			if (this.mBlocks[row][col] != that.mBlocks[row][col]) {
    				return false;
    			}
    		}
    	}
		
		return this.toString().equals(that.toString());
	}
	
	//return an iterable interface e.g. ArrayList of size 2 to 4
	//make it immutable, run it only once
	
	public Iterable<Board> neighbors() {
		if (mNeigbors.size() != 0 ) {
			//System.out.println("just returning exisiting neighbors");
			return mNeigbors;
		}
		
		int blankRow = 0;
		int blankCol = 0;
		 for(int i=0;i<N;i++) {
			 for(int j=0;j<N;j++) {
				 if (mBlocks[i][j] == 0) {
					 blankRow = i;
					 blankCol = j;
				 }
			 }
		 }
		 
		 //can move up, 
		 if (blankRow > 0) mNeigbors.add(swapValue(new int[]{blankRow, blankCol}, new int[]{blankRow-1, blankCol}));
		 
		 //can move down
		 if (blankRow < N-1) mNeigbors.add(swapValue(new int[]{blankRow, blankCol}, new int[]{blankRow+1, blankCol}));
			
		 //can move left
		 if (blankCol > 0 ) mNeigbors.add(swapValue(new int[]{blankRow, blankCol}, new int[]{blankRow, blankCol-1}));
		 
		 //can move right
		 if (blankCol < N-1) mNeigbors.add(swapValue(new int[]{blankRow, blankCol}, new int []{blankRow, blankCol+1}));
		return mNeigbors;
	}
	
	public String toString() {
		String result = N  + "\n";
		for(int i=0;i<N;i++){
			for(int j=0;j<N;j++) {
				result += " " + mBlocks[i][j];
			}
			result += "\n";
		}
		return result;
	}
	
	
	//private helper methods
	private int abs(int value) {
		return value <= 0 ? -value : value;
	}
	
	private int [][] getNewCopy() {
		int [][]copy = new int[N][N];
		for(int i=0;i<N;i++) {
			for(int j=0;j<N;j++) {
				copy[i][j] = mBlocks[i][j];
			}
		}
		return copy;
	}
	
	private Board swapValue(int []first, int [] second) {
		int [][] copy = getNewCopy();
		
		int temp = copy[first[0]][first[1]];
		
		copy[first[0]][first[1]] = copy[second[0]][second[1]];
		copy[second[0]][second[1]] = temp;
		return new Board(copy);
	}
	
	public static void main(String []args){
	
		String file = "/Users/song/Documents/EclipseWorkspace/HelloEclipse/src/8puzzle/puzzle4x4-00.txt";
		
		In in = new In(args.length == 0 ? file : args[0]);
	    int n = in.readInt();
	    int[][] blocks = new int[n][n];
	    for (int i = 0; i < n; i++) {
	        for (int j = 0; j < n; j++) {
	            blocks[i][j] = in.readInt(); 
	        }
	    }
	
	 
	    Board initial = new Board(blocks);
	    System.out.println("board is\n"+initial);
	    System.out.println("hamming: " + initial.hamming());
	    System.out.println("manhattan: "+ initial.manhattan());
	    //System.out.println("twin\n"+ initial.twin());
	    System.out.println("isGoal:" + initial.isGoal());
	    Board other = new Board(blocks);
	    System.out.println("equal:" + initial.equals(other));
//	    for(Board neighbor: initial.neighbors()) {
//	    	System.out.println("neighbor\n" + neighbor);
//	    }   
	    
	    System.out.println("neighbors 0 \n" + initial.neighbors().iterator().next());
	    System.out.println("neighbors 0 \n" + initial.neighbors().iterator().next());
	    System.out.println("neighbors 0 \n" + initial.neighbors().iterator().next());
	    System.out.println("neighbors 0 \n" + initial.neighbors().iterator().next());
	    System.out.println("neighbors 0 \n" + initial.neighbors().iterator().next());
	    System.out.println("neighbors 0 \n" + initial.neighbors().iterator().next());
	    
	    
	}
}
