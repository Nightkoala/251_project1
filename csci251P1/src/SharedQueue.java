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
	
	// Methods
	
	public synchronized void addToQueue( String fname ) {
		filesToPrint.add(fname);
	}//end addToQueue
	
	public synchronized String retrieveFirst() {
		return filesToPrint.poll();
	}//end retrieveFirst
	
	public synchronized boolean isEmpty() {
		if( filesToPrint.peek() == null ) {
			return true;
		}//end if
		return false;
	}//end isEmpty
	
}//end SharedQueue
