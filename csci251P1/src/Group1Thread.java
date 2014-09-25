/**
 * Group1Thread.java
 * 
 * @author 		Derek Brown <djb3718@rit.edu>
 * 
 * purpose:		Implementation for Group 1 threads.  Reads a file and notifies
 * 				corresponding Group 2 thread if word has been found.  Then 
 * 				continues reading file.
 *
 */

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Group1Thread implements Runnable {

	//Attributes
	private BufferedReader r;
	private String fname;
	private HashMap<String,Integer> targetWords;
	private ArrayList<SharedQueue> queues;
	
	//Constructor
	
	/**
	 * Constructor for creating a Group1Thread
	 * 
	 * @param fname			The name of the file that this thread is reading
	 * @param r				The reader responsible for reading the file
	 * @param targetWords	The collection of words to search for
	 * @param queues		The shared object which is used by Group2Threads to
	 * 						know which files to print.
	 */
	public Group1Thread( String fname, BufferedReader r,
			HashMap<String, Integer> targetWords,
			ArrayList<SharedQueue> queues ) {
		this.fname = fname;
		this.r = r;
		this.targetWords = targetWords;
		this.queues = queues;
	}//end Group1ThreadConstructor
	
	@Override
	/**
	 * The run() method for Group 1 threads.
	 * 
	 * Loops through each line of the file.  It then splits the line into an
	 * Array of words containing each word on that line.  It then compares each
	 * word in the line with the collection targetWords.  If a target words is
	 * found it adds its name to the queue for the corresponding Group 2 thread.
	 * 
	 * @exception IOException	If an error occurs reading the file.
	 */
	public void run() {
		String line;
		try {
			while( ( line=r.readLine() ) != null ) {
				String[] splitLine = line.split( "\\s+|,\\s*|\\.\\s*|\\?\\s*" +
						"|\\!\\s*|\\:\\s*|\\;\\s*" );
				for( String word : splitLine ) {
					String temp = word.toLowerCase();
					if( targetWords.containsKey( temp ) ) {
						int group2threadid = targetWords.get( temp );
						queues.get( group2threadid ).addToQueue( fname );
					}//end if
				}//end for
			}//end while
		} catch( IOException e ) {
			System.err.println( "Error reading file. Abort." );
			e.printStackTrace();
			System.exit( 1 );
		}//end try/catch
	}//end run
}//end Group1Thread
