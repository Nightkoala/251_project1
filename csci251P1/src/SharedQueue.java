/**
 * SharedQueue.java
 * 
 * @author 		Derek Brown <djb3718@rit.edu>
 * 
 * Purpose		Shared monitor class.  Will be stored in group 2 threads.
 *
 */

import java.util.LinkedList;

public class SharedQueue {
	
	// Attributes
	private LinkedList<String> filesToPrint;
	
	// Constructor
	public SharedQueue() {
		this.filesToPrint = new LinkedList<String>();
	}//end SharedQueue constructor
	
	public synchronized void addToQueue( String fname ) {
		filesToPrint.add(fname);
	}//end addToQueue
	
	public String retrieveFirst() {
		return filesToPrint.poll();
	}//end retrieveFirst
	
}//end SharedQueue
