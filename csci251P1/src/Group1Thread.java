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
	private ArrayList<Thread> group2;
	private HashMap<String,Integer> targetWords;
	private ArrayList<SharedQueue> queues;
	
	//Constructor
	public Group1Thread( File file, String fname, BufferedReader r,
			ArrayList<Thread> group2,
			HashMap<String, Integer> targetWords,
			ArrayList<SharedQueue> queues) {
		this.file = file;
		this.fname = fname;
		this.r = r;
		this.group2 = group2;
		this.targetWords = targetWords;
		this.queues = queues;
	}//end Group1ThreadConstructor
	
	@Override
	public void run() {
		String line;
		try {
			while( ( line=r.readLine() ) != null ) {
				String[] splitLine = line.split("\\s+|,\\s*|\\.\\s*|\\?\\s*" +
						"|\\!\\s*|\\:\\s*|\\;\\s*");
				for( String word : splitLine ) {
					String temp = word.toLowerCase();
					if( targetWords.containsKey(temp) ) {
						int group2threadid = targetWords.get(temp);
						queues.get(group2threadid).addToQueue(fname);
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
