import java.util.HashSet;
import java.util.Set;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdRandom;

public class Board {
	private int N;
	private int [][] mBlocks;
	
	public Board(int[][] blocks) {
		mBlocks = blocks;
		N =  mBlocks.length;
	}

	public int dimension() {
		return N;
	}
	
	public int hamming() {
		int wrongPositionCount = 0;
		
		for(int i=0;i<N;i++){
			for(int j=0;j<N;j++) {
				if (mBlocks[i][j] == 0) continue;
				int correctPosition = i * 3 + j + 1;
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
		if (this.getClass() != y.getClass()) return false;
		
		Board otherBoard = (Board) y;
		if (N != otherBoard.dimension()) return false;
		
		return this.toString().equals(otherBoard.toString());
	}
	
	//return an iterable interface e.g. ArrayList of size 2 to 4
	public Iterable<Board> neighbors() {
		Set<Board> boards= new HashSet<>();
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
		 if (blankRow > 0) boards.add(swapValue(new int[]{blankRow, blankCol}, new int[]{blankRow-1, blankCol}));
		 
		 //can move down
		 if (blankRow < N-1) boards.add(swapValue(new int[]{blankRow, blankCol}, new int[]{blankRow+1, blankCol}));
			
		 //can move left
		 if (blankCol > 0 ) boards.add(swapValue(new int[]{blankRow, blankCol}, new int[]{blankRow, blankCol-1}));
		 
		 //can move right
		 if (blankCol < N-1) boards.add(swapValue(new int[]{blankRow, blankCol}, new int []{blankRow, blankCol+1}));
		return boards;
	}
	
	public String toString() {
		String result = "";
		for(int i=0;i<N;i++){
			for(int j=0;j<N;j++) {
				if (mBlocks[i][j] == 0) result += (j == N - 1 ? "": "   ");
				else result += mBlocks[i][j] + (j == N - 1 ? "": "  ");
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
	
		String file = "/Users/song/Documents/EclipseWorkspace/HelloEclipse/src/8puzzle/mypuzzle.txt";
		
		In in = new In(file);
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
	    Board other = new Board(blocks);
	    System.out.println("equal:" + initial.equals(other));
	    for(Board neighbor: initial.neighbors()) {
	    	//System.out.println("neighbor\n" + neighbor);
	    }                
	}
}
