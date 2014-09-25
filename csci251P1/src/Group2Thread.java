/**
 * Group2Thread.java
 * 
 * @author 		Derek Brown <djb3718@rit.edu>
 * 
 * Purpose		Implementation for Group 2 threads.  Continuously checks to see
 * 				if queue is empty, if it is not empty it prints the word and the
 * 				file it was found in.
 *
 */

import java.util.HashSet;

public class Group2Thread implements Runnable {
	
	//Attributes
	private String word;
	private SharedQueue filesToPrint; //will change
	private boolean exit;
	private HashSet<String> processedFiles;
	
	//Constructor
	
	/**
	 * Constructor for creating a Group 2 thread
	 * 
	 * @param word	The target word that this thread is associated with
	 * @param queue	The shared queue object that contains the file names to 
	 * 				print if a file has found the target word for this thread.
	 */
	public Group2Thread( String word, SharedQueue queue ) {
		this.word = word;
		this.filesToPrint = queue;
		this.exit = false;
		this.processedFiles = new HashSet<String>();
	}//end Group2Thread constructor
	
	// Methods
	
	/**
	 * Synchronized method for displaying results.
	 * 
	 * @param fname	The file where the target word was found.
	 */
	private synchronized void printResults( String fname ) {
		System.out.println( word + " " + fname );
	}//end printResults
	
	/**
	 * Method that is called by Search.main when it is time for the thread to
	 * terminate.  If this method is never called the thread will infinitely
	 * loop.
	 */
	public void beforeExit() {
		exit = true;
	}//end beforeExit
	
	@Override
	/**
	 * The run() method for Group 2 threads.  It constantly checks to see if
	 * there is an item in the queue.  If there is it, and it is the first time
	 * that the thread has seen the file name, it prints the results.  Otherwise
	 * waits and loops until told to terminate.
	 */
	public void run() {
		String file;
		while( !exit ) {
			if( !filesToPrint.isEmpty() ) {
				file = filesToPrint.retrieveFirst();
				if( !processedFiles.contains( file ) ) {
					printResults( file );
					processedFiles.add( file );
				}//end if
			}//end if
		}//end while
		while( !filesToPrint.isEmpty() ) {
			file = filesToPrint.retrieveFirst();
			if( !processedFiles.contains( file ) ) {
				printResults( file );
				processedFiles.add( file );
			}//end if
		}//end while
	}//end run
}//end Group2Thread
