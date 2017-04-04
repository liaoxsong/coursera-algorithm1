import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.Scanner;

/**
 * Created by song on 3/11/17.
 */
public class Permutation {

    //running time must be linear in size of the input
    public static void main (String []args) throws FileNotFoundException {

    	RandomizedQueue<String> rq = new RandomizedQueue<String>();
  	    int k = Integer.parseInt(args[0]);
  		
  	    while (!StdIn.isEmpty()) {
             String item = StdIn.readString();
             if (!item.equals("-")) rq.enqueue(item);
             else break;
         }
  	    for(String s : rq)
  	    {
  		   if(k == 0) break;
  		   System.out.println(s);
  		   k--;
  	    }
    	
    	//testLocalFiles();
    }
    
    private static void testLocalFiles() throws FileNotFoundException {
    	
    	int k = 3;
    	
    	File file = new File("/Users/song/Documents/EclipseWorkspace/HelloEclipse/src/queues/distinct.txt");
    	
    	Scanner sc = new Scanner(file).useDelimiter("\\s*");
  
        RandomizedQueue<String> queue = new RandomizedQueue<>();
        while(sc.hasNextLine()) {
        	String nextLine = sc.next();
        	System.out.println("enqueue: "+ nextLine);
            queue.enqueue(nextLine);
        }

        for (int i=0;i<k;i++) {
        	System.out.println(queue.dequeue());
        }
    }
}
