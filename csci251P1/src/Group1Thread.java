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
	private File file;
	private BufferedReader r;
	private String fname;
	private ArrayList<Group2Thread> group2;
	private HashMap<String,Integer> targetWords;
	
	//Constructor
	public Group1Thread( File file, String fname, BufferedReader r,
			ArrayList<Group2Thread> group2,
			HashMap<String, Integer> targetWords) {
		this.file = file;
		this.r = r;
		this.group2 = group2;
		this.targetWords = targetWords;
	}//end Group1ThreadConstructor
	
	@Override
	public void run() {
		String line;
		try {
			while( ( line=r.readLine() ) != null ) {
				String[] splitLine = line.split("\\s+|,\\s*|\\.\\s*|\\?\\s*" +
						"|\\!\\s*|\\:\\s*|\\;\\s*");
				for( String word : splitLine ) {
					if( targetWords.containsKey(word) ) {
						
					}//end if
				}//end for
			}//end while
		} catch( IOException e ) {
			System.err.println("Error reading file. Abort.");
			e.printStackTrace();
			System.exit(1);
		}//end try/catch
	}//end run
}//end Group1Thread
