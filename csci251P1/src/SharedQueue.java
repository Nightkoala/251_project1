/**
 * SharedQueue.java
 * 
 * @author	Derek Brown <djb3718@rit.edu>
 * 
 * Purpose	Shared monitor class.  Will store the file names to be printed
 * 		by the Group 2 threads.
 *
 */

import java.util.LinkedList;

public class SharedQueue {
	
	// Attributes
	private LinkedList<String> filesToPrint;
	
	// Constructor
	/**
	 * Constructor for creating a SharedQueue
	 */
	public SharedQueue() {
		this.filesToPrint = new LinkedList<String>();
	}//end SharedQueue constructor
	
	// Methods
	
	/**
	 * Called by Group 1 threads, It adds the file name to the end of the
	 * linked list to be printed by the Group 2 thread.
	 * 
	 * @param fname	The file name to be added to linked list
	 */
	public synchronized void addToQueue( String fname ) {
		filesToPrint.add( fname );
	}//end addToQueue
	
	/**
	 * Called by Group 2 threads, It takes and removes the front of the
	 * linked list.
	 * 
	 * @return	The first element of the linked list.
	 */
	public synchronized String retrieveFirst() {
		return filesToPrint.poll();
	}//end retrieveFirst
	
	/**
	 * Called by Group 2 threads, Checks to see if the linked list is empty.
	 * 
	 * @return	true if the linked list is empty.
	 * 		false otherwise.
	 */
	public synchronized boolean isEmpty() {
		if( filesToPrint.peek() == null ) {
			return true;
		}//end if
		return false;
	}//end isEmpty
}//end SharedQueue
