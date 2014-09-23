import java.util.ArrayList;

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
 * Version:
 * $Id
 * 
 * Revision:
 * $Log
 *
 */
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
	}//end main
}//end Search
