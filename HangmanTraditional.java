package hangman;

import java.util.ArrayList;
import java.util.Random;

/**
 * Represent a HangmanTraditional class, subclass of Hangman
 * This class represents the traditional version of the hangman game
 * In the traditional version, the computer randomly choose a word with a randomly generated word length
 * the user have to guess the word with  
 * 
 * @author xiranxu
 *
 */

public class HangmanTraditional extends Hangman {
	
	/**
	 * Creates HangmanTraditional with given numOfLetter, numOfGuesses, and dictionary
	 * It creates the specific the word list that randomly choose one word that have same length with the given number of letter 
	 * 
	 * @param numOfLetter The number of letter in the secret word
	 * @param numOfGuesses The number of guesses allowed for the user
	 * @param dictionary The array list that contains all valid words for the game
	 */
	public HangmanTraditional(int numOfLetter, int numOfGuesses, ArrayList<String> dictionary)
	{
		super(numOfLetter, numOfGuesses,dictionary);
		
		for (String word : dictionary)
		{
			if(word.length() == numOfLetter)
			{
				
				wordList.add(word);
			}
		}
		
		Random random = new Random();
		if(!wordList.isEmpty()) {
			String secretWord = wordList.get(random.nextInt(wordList.size())); 
			wordList.removeAll(wordList);
			wordList.add(secretWord);
			//System.out.println(wordList);
		}

	}
}

