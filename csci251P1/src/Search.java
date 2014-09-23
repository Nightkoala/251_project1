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
		
//		// Create and start Group 2 Threads
//		for( int i = 0 ; i < lowerWords.size() ; i++ ) {
//			// First create the monitor
//			SharedQueue q = new SharedQueue();
//			
//			// Create and start Group 2 thread
//			Thread t = new Thread( new Group2Thread( lowerWords.get(i), q ) );
//			t.start();
//			
//		}//end for
	}//end main
}//end Search