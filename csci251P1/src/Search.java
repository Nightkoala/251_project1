/**
 * Search.java
 * 
 * @author		Derek Brown <djb3718@rit.edu>
 * 
 * purpose:		Basic thread program.  Given several file names and words to 
 * 				search for, create two groups of threads.  Group 1 reads the
 * 				file and notifies the Group 2 threads that a words has been
 * 				found and the Group 2 thread prints the word and the file it
 * 				was found.
 * 
 */

import java.util.ArrayList;
import java.io.*;
import java.util.HashMap;

public class Search {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		if( args.length != 2 ) {
			System.err.println("Usage: java Search <files> <words>");
			System.exit(1);
		}//end if
		
		String[] files = args[0].split(",");
		String[] words = args[1].split(",");
		
		// Checks if words are valid, terminates program if so
		ArrayList<String> lowerWords = new ArrayList<String>();
		for( String word : words ) {
			char[] chars = word.toCharArray();
			for( char c : chars ) {
				if( !Character.isLetter(c) ) {
					System.err.println("Invalid input.  Target words cannot" +
							" contain non-letter characters.");
					System.exit(1);
				}//end if
			}//end for
			String temp = word.toLowerCase();
			lowerWords.add(temp);
		}//end for
		
		// Checks if files valid, terminates program if so
		ArrayList<File> fFiles = new ArrayList<File>();
		ArrayList<BufferedReader> readers = new ArrayList<BufferedReader>();
		for( String file : files ) {
			File inFile;
			BufferedReader r;
			try {
				inFile = new File(file);
				r = new BufferedReader(
						new FileReader( inFile ) );
				fFiles.add(inFile);
				readers.add(r);
			} catch( FileNotFoundException e ) {
				System.err.println(file + " not found. Abort.");
				e.printStackTrace();
				System.exit(1);
			}//end try/catch
		}//end for
		
		// Create HashMap of target words
		HashMap<String, Integer> targetWords = new HashMap<String, Integer>();
		for( int i = 0 ; i < lowerWords.size() ; i++ ) {
			targetWords.put(lowerWords.get(i), i);
		}//end for
	
		// Create and start Group 2 Threads
		ArrayList<Thread> group2 = new ArrayList<Thread>();
		ArrayList<Group2Thread> g2 = new ArrayList<Group2Thread>();
		ArrayList<SharedQueue> queues = new ArrayList<SharedQueue>();
		for( int i = 0 ; i < lowerWords.size() ; i++ ) {
			// First create the monitor
			SharedQueue q = new SharedQueue();
			queues.add(q);
			
			// Create and start Group 2 thread
			Group2Thread g2t = new Group2Thread( lowerWords.get(i), q );
			g2.add(g2t);
			Thread t = new Thread( g2t );
			t.start();
			group2.add(t);
		}//end for
		
		// Create and start Group 1 Threads
		ArrayList<Thread> group1 = new ArrayList<Thread>();
		for( int i = 0 ; i < fFiles.size() ; i++ ) {
			Thread t = new Thread( new Group1Thread( fFiles.get(i),
					files[i], readers.get(i), group2, targetWords, queues ) );
			t.start();
			group1.add(t);
		}//end for
		
		// Wait for threads to finish
		// Group 1
		for( Thread t : group1 ) {
			try {
				t.join();
			} catch (InterruptedException e) {
				System.err.println("Error joining threads. Abort.");
				e.printStackTrace();
				System.exit(1);
			}// end try/catch
		}//end for
		
		// Group2
		for( int i = 0 ; i < group2.size() ; i++ ) {
			Thread t = group2.get(i);
			Group2Thread g2t = g2.get(i);
			g2t.beforeExit();
			try {
				t.join();
			} catch (InterruptedException e) {
				System.err.println("Error joining threads. Abort.");
				e.printStackTrace();
				System.exit(1);
			}//end try/catch
		}//end for
	}//end main
}//end Search