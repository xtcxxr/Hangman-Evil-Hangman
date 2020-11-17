package hangman;

import java.util.ArrayList;

/**
 * Represent a HangmanEvil class, subclass of Hangman
 * This class represents the evil version of the hangman game
 * In this version, the computer keeps changing the word in order to make the user's task harder
 * 
 * @author xiranxu
 *
 */
public class HangmanEvil extends Hangman{
	
	/**
	 * Creates HangmanEvil with given numOfLetter, numOfGuesses, and dictionary
	 * It creates the specific the word list that only adds words that have same length with the given number of letter 
	 * 
	 * @param numOfLetter The number of letter in the secret word
	 * @param numOfGuesses The number of guesses allowed for the user
	 * @param dictionary The word list that contains all valid words for the game
	 */
	
	public HangmanEvil(int numOfLetter, int numOfGuesses, ArrayList<String> dictionary) {
		super(numOfLetter, numOfGuesses,dictionary);
		
		for(String word : dictionary) {
			if(word.length() == numOfLetter) {
				wordList.add(word); 
				//System.out.println(wordList);
			}
		}
			
	}
	
}
