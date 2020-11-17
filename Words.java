package hangman;

import java.io.*;
import java.util.*;
/**
 * This is the wordsClean class
 * reads the text file and clean the text
 * clean words with upper case letters, abbreviations, apostrophes, hyphens, and compound words
 * create the dictionary to be used 
 * @author xiranxu
 */

public class Words {
	protected static ArrayList<String> dictionary = new ArrayList<String>();
	
	/**
	 * wordsClean class constructor
	 */
	public Words() {
		
		readFileGetWords("short_list.txt");
	}
	   
	/**
	 * read the text and file and store all words in dictionary
	 * @param dictionary the dictionary to be edited
	 * @param fileName the file to be read and parse
	 */
    public static void readFileGetWords(String fileName){
    	File myFile = new File(fileName);
		try{
			Scanner scanner = new Scanner(myFile);
			while(scanner.hasNextLine()) {
				String word = scanner.nextLine();
				dictionary.add(word);
			}
			scanner.close();
		} catch(FileNotFoundException e) {
			e.printStackTrace();
		}
//		System.out.println("dictionary : "
//	               + dictionary);
    }

	public static ArrayList<String> getDictionary() {
		return dictionary;
	}

	public static void setDictionary(ArrayList<String> dictionary) {
		Words.dictionary = dictionary;
	}

}
