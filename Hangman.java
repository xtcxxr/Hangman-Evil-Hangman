package hangman;

import java.util.*;
/**
 * Represents an abstract class Hangman.
 *  @author xiranxu
 */
public abstract class Hangman {
	
	protected int numOfLetter = 0;
	protected int numOfGuesses = 0;
	
	protected ArrayList<String> wordList = new ArrayList<String>();
	protected ArrayList<Character> guesses = new ArrayList<Character>();
	protected HashMap<String, ArrayList<String>> wordPattern;
	
	protected String output;
	protected String finalAnswer;
	protected boolean correctGuess;
	
	/**
	 * This constructor sets up the number of letters and guesses for each Hangman game
	 * The secret word that match with the given number of letter is taken from the given dictionary
	 * Initially sets the secret word as array of underscores. 
	 * 
	 * @param numOfLetter The number of letter in the secret word
	 * @param numOfGuesses The number of guesses allowed for the user
	 * @param dictionary The array list that contains all valid words for the game
	 */
	public Hangman (int numOfLetter, int numOfGuesses, ArrayList<String> dictionary) {
		
		this.numOfLetter = numOfLetter;
		this.numOfGuesses = numOfGuesses;
		
		char[] outputArray = new char[numOfLetter];
		for(int i = 0; i < numOfLetter; i++)
		{
			outputArray[i] = '_';
		}
		output = new String(outputArray);

		
	}
	
	
	/**
	 * This methods check the guess by the user and changes the secret word (taken from the word list) using HashMap
	 * The word pattern HashMap is used to keep track of "word families"
	 * Each key to the word pattern HashMap is a string (e.g "e___" for "echo" after guessing 'e')
	 * 
	 * This method also checks if the user's guess letter is correct
	 * and return false if the game is over or guess letter is incorrect
	 * 
	 * @param guess The user's guess letter
	 * @return true if the user guessed the letter of the secret word
	 * 
	 */
	public boolean check(char guess) {
		String secretWord = output;
		wordPattern = new HashMap<String, ArrayList<String>>();
		
		//if game is over return win to check if the user won the game
		if(gameOver())
		{
			return win();
		}
		
		//Parse all words in word list to create keys for the hash map
		for(String word : wordList)
		{
			char[] key = new char[numOfLetter];
			
			for(int index = 0; index < numOfLetter; index++)
			{
				if(word.charAt(index) == guess)
				{
					key[index] = guess;
				}
				else
				{
					key[index] = output.charAt(index); 
				}
			}
			
			String keyString = new String(key);
			addWordChoice(keyString, word, wordPattern);
		}
		
		/*
		 * Implements the last guess letter situation
		 * When the number of guesses equal to one, 
		 * it choose the first word in the the current wordPattern 
		 * and assign that word to be the final secret word
		 * It also make the number of guesses equal to zero (game over) and return false 
		 */
		if(wordPattern.keySet().contains(output) && numOfGuesses == 1) {
			numOfGuesses --;
			guesses.add(guess);
			
			wordList = new ArrayList<String>(wordPattern.get(output));
			finalAnswer = wordPattern.get(output).get(0);
			return false;
		}
		
		/*
		 * If not in the above situation:
		 */
		for(String key : wordPattern.keySet()) {
			if(!(wordPattern.keySet().contains(secretWord)))
			{
				secretWord = key;
			}
			
			//To through all word families to check word pattern with the most possible words
			if(wordPattern.get(key).size() >  wordPattern.get(secretWord).size())
			{
				secretWord = key;
			}
		}
		
	 	//If the word list is empty, change the dictionary
		if(wordPattern.keySet().contains(secretWord)) {
			// Shallow copy of the max value in word choices 
			 wordList = new ArrayList<String>(wordPattern.get(secretWord));
			 correctGuess = !(output.equals(secretWord));
			 
			 if(!correctGuess)
			 {
				 numOfGuesses --;

			 }
			 
			 output = secretWord;
			 finalAnswer = wordPattern.get(output).get(0);
			 guesses.add(guess);
			 return correctGuess;
		}
		else {
			// when word list is empty, just return false
			wordList = new ArrayList<String>();
			guesses.add(guess);
			return false;
		}
	}
	
	/**
	 * Return true if the game is over
	 * The game is over when:
	 * The number of guesses is equal to zero
	 * If there is no word in word list 
	 * If the player guessed all the letters of the secret word
	 * 
	 * @return true if the game is over
	 */
	public boolean  gameOver() {
		return numOfGuesses == 0 || wordList.isEmpty() || !(output.contains("_")) ;
	}
	
	/**
	 * Returns the number guesses left in the game
	 * 
	 * @return the number of guesses
	 */
	public int getNumofGuesses() {
		return numOfGuesses;
	}
	
	/**
	 * Returns the number of words in the words list
	 * 
	 * @return Returns the number of words in the words list
	 */
	public int wordListSize() {
		return wordList.size();
	}
	
	/**
	 * Returns the list of all guessed letters
	 * This method stores and prints all of guessed letters by the user
	 * 
	 * @return A string that shows all guessed letters
	 */
	public String getGuesses() {
		return guesses.toString();
	}
	
	/**
	 * Return the final answer (the given secret word) when game is over
	 * Otherwise, return null so the user does not know the final secret word until the game is over 
	 * 
	 * @return the final chosen secret word when the game is over
	 */
	public String getFinalAnswer() {
		if(gameOver()) {
			return finalAnswer;
		}
		return null;
	}
	
	/**
	 *This method print out the current status of guesses
	 *It will create result string like "__...__ or a_..._j_"
	 *
	 *@return The result string that shows the user's correct guess and unknown letter of secret word
	 */
	public String showWord() {	
		return output;
	}
	
	/**
	 * Returns true if the user has won the game
	 * The user has won the game if  the user guessed all the letters of the secret word
	 *
	 * @return false if the game is not over or if the user could not guess all the letters
	 */
	public boolean win() {
		if(gameOver()) {
			return !(output.contains("_"));
					
		}
		return false;
	}
	
	/**
	 * This methods check if the user's guess is already found in the list of guesses
	 * 
	 * @param guess The user's guess
	 * @return true if the lit of guesses contains the given guess 
	 */
	public boolean checkRepeat(char guess) {
		for (char letter : guesses) {
			if(letter == guess) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Helper function to append a word to the hash map
	 * If the key does not exist in the map, it creates an ArrayList and append the word
	 * Otherwise simply append the word to the appropriate ArrayList
	 * 
	 * @param key The key string that is used to find the target ArrayList in the given HashMap
	 * @param word The string of value for the given key to be append to the ArrayList in the given HashMap
	 * @param wordPattern The HashMap of string keys and ArrayList word (values)
	 */
	public void addWordChoice(String key, String word, HashMap<String, ArrayList<String>> wordPattern) {
		if(wordPattern.get(key) == null) {
			wordPattern.put(key, new ArrayList<String>());
		}
		wordPattern.get(key).add(word);
	}
}	

