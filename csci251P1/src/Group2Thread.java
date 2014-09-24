/**
 * Group2Thread.java
 * 
 * @author 		Derek Brown <djb3718@rit.edu>
 * 
 * Purpose		Implementation for Group 2 threads.  Waits until notified
 * 				by a Group 1 thread.  Once notified it will print the word
 * 				and which file it was found.
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
	public Group2Thread( String word, SharedQueue queue ) {
		this.word = word;
		this.filesToPrint = queue;
		this.exit = false;
		this.processedFiles = new HashSet<String>();
	}//end Group2Thread constructor
	
	// Methods
	
	private synchronized void printResults( String fname ) {
		System.out.println(word + " " + fname);
	}//end printResults
	
	public void beforeExit() {
		exit = true;
	}//end beforeExit
	
	@Override
	public void run() {
		String file;
		while( !exit ) {
			if( !filesToPrint.isEmpty() ) {
				file = filesToPrint.retrieveFirst();
				if( !processedFiles.contains(file) ) {
					printResults( file );
					processedFiles.add(file);
				}//end if
			}//end if
		}//end while
		while( !filesToPrint.isEmpty() ) {
			file = filesToPrint.retrieveFirst();
			if( !processedFiles.contains(file) ) {
				printResults( file );
				processedFiles.add(file);
			}//end if
		}//end while
	}//end run
}//end Group2Thread
