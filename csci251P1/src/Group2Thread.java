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

public class Group2Thread implements Runnable {
	
	//Attributes
	private String word;
	private SharedQueue filesToPrint; //will change
	
	//Constructor
	public Group2Thread( String word, SharedQueue queue ) {
		this.word = word;
		this.filesToPrint = queue;
	}//end Group2Thread constructor
	
	private synchronized void printResults( String fname ) {
		System.out.println(word + " " + fname);
	}//end printResults
	
	@Override
	public void run() {
		
	}//end run
}//end Group2Thread
