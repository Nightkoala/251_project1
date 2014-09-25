/**
 * Search.java
 * 
 * @author		Derek Brown <djb3718@rit.edu>
 * 
 * purpose:		Basic thread program.  Given several file names and
 *			words to search for, create two groups of threads.
 *			Group 1 reads the file and notifies the Group 2 thread
 *			that a word has been found and the Group 2 thread
 *			prints the first occurrence of the word for each file,
 *			and the file it was found.
 *
 */

import java.util.ArrayList;
import java.io.*;
import java.util.HashMap;

public class Search {
	
	/** Prevent Construction */
	private Search() { }
	
	// Methods
	
	/**
	 * Checks if words given to program from command line are valid.
	 * 
	 * @param wordsToCheck  The array of words received from the command
	 *			line
	 * 
	 * @return	if any word is found containing non-characters, the
	 *		program immediately displays an error message and
	 *		terminates.
	 * 		Otherwise, an ArrayList<String> of all the words is
	 *		returned.
	 * 
	 */
	public static ArrayList<String> validateWords( String[] wordsToCheck ) {
		ArrayList<String> lowerWords = new ArrayList<String>();
		String temp;
		for( String word : wordsToCheck ) {
			char[] chars = word.toCharArray();
			for( char c : chars ) {
				if( !Character.isLetter( c ) ) {
					System.err.println( "Invalid input. " +
						word + " contains " +
						"non-letter characters." );
					System.exit( 1 );
				}//end if
			}//end for
			temp = word.toLowerCase();
			lowerWords.add( temp );
		}//end for
		return lowerWords;
	}//end validateWords
	
	/**
	 * Checks if the files given to program from command line exist and
	 * creates a reader for each file.
	 * 
	 * @param files  The array of files received from the command line.
	 * 
	 * @return	If any of the files does not exist, the program will
	 *		immediately display an error message and terminate.
	 * 		Otherwise, an ArrayList<BufferedReader> containing the
	 *		reader for each file is returned.
	 * 
	 * @exception FileNotFound  if a file does not exist
	 */
	public static ArrayList<BufferedReader> validateFiles( String[] files )
	{
		ArrayList<BufferedReader> readers =
			new ArrayList<BufferedReader>();
		for( String file : files ) {
			File inFile;
			BufferedReader r;
			try {
				inFile = new File( file );
				r = new BufferedReader(
					new FileReader( inFile ) );
				readers.add(r);
			} catch( FileNotFoundException e ) {
				System.err.println( file +
					" not found. Abort." );
				e.printStackTrace( System.err );
				System.exit( 1 );
			}//end try/catch
		}//end for
		return readers;
	}//end validateFiles

	/**
	 * Validates the input and exits the program, with corresponding error
	 * message if an invalid input has been made, If no error is detected
	 * then program creates and starts the Group 2 threads,It then creates
	 * and starts the Group 1 threads and waits for all of them to terminate
	 * before exiting.
	 * 
	 * @param args	Command line arguments.
	 * 			args[0] contains the files to be read.
	 * 			args[1] contains the words to be searched for.
	 * 
	 * @exception InterruptedException	If one of the threads gets
	 *					unexpectedly interrupted.
	 */
	public static void main( String[] args ) {
		
		if( args.length != 2 ) {
			System.err.println( "Usage: java Search " +
				"<files> <words>" );
			System.exit( 1 );
		}//end if
		
		String[] files = args[0].split( "," );
		String[] words = args[1].split( "," );
		
		// Checks if words are valid, terminates program if invalid
		ArrayList<String> lowerWords = validateWords( words );
		
		// Checks if files valid, terminates program if invalid
		ArrayList<BufferedReader> readers = validateFiles( files );
		
		// Create HashMap of target words
		HashMap<String, Integer> targetWords =
			new HashMap<String, Integer>();
		for( int i = 0 ; i < lowerWords.size() ; i++ ) {
			targetWords.put( lowerWords.get( i ), i );
		}//end for
	
		// Create and start Group 2 Threads
		ArrayList<Thread> group2 = new ArrayList<Thread>();
		ArrayList<Group2Thread> g2 = new ArrayList<Group2Thread>();
		ArrayList<SharedQueue> queues = new ArrayList<SharedQueue>();
		for( int i = 0 ; i < lowerWords.size() ; i++ ) {
			// Create the monitor
			SharedQueue q = new SharedQueue();
			queues.add( q );
			
			// Create and start Group 2 thread
			Group2Thread g2t =
				new Group2Thread( lowerWords.get( i ), q );
			g2.add( g2t );
			Thread t = new Thread( g2t );
			t.start();
			group2.add( t );
		}//end for
		
		// Create and start Group 1 Threads
		ArrayList<Thread> group1 = new ArrayList<Thread>();
		for( int i = 0 ; i < files.length ; i++ ) {
			Thread t = new Thread( 
				new Group1Thread( files[i], readers.get(i), 
					targetWords, queues ) );
			t.start();
			group1.add( t );
		}//end for
		
		// Wait for threads to finish
		// Group 1
		for( Thread t : group1 ) {
			try {
				t.join();
			} catch ( InterruptedException e ) {
				System.err.println( 
					"Error joining threads. Abort." );
				e.printStackTrace();
				System.exit( 1 );
			}// end try/catch
		}//end for
		
		// Group2
		for( int i = 0 ; i < group2.size() ; i++ ) {
			Thread t = group2.get( i );
			Group2Thread g2t = g2.get( i );
			g2t.beforeExit();
			try {
				t.join();
			} catch ( InterruptedException e ) {
				System.err.println(
					"Error joining threads. Abort." );
				e.printStackTrace( System.err );
				System.exit( 1 );
			}//end try/catch
		}//end for
	}//end main
}//end Search
