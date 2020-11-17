package hangman;


import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/**
 * HangmanGame class
 * control the game play
 * randomly choose the game version
 * print out information for player
 * @author xiranxu
 */
public class HangmanGame {
	
	/**
	 * calculate the longest and shortest words in the dictionary
	 * randomly generate a word length within the range
	 * @param dictionary the dictionary to be checked
	 * @param wordLength the wordLength to be updated
	 * @return wordLength updated to be used
	 */
	public static int chooseWordLengthRandomly(ArrayList<String> dictionary, int wordLength) {
		   Random random = new Random();
		   int maxWordLength = 0;
		   int minWordLength = 0;
		   for(int i = 0; i < dictionary.size(); i++) {
				if (dictionary.get(i).length() > maxWordLength) {
					maxWordLength = dictionary.get(i).length();
				}
			}
		    String shortestWord = dictionary.get(0);
		    for (int i = 1; i < dictionary.size(); i++) {
		        if (dictionary.get(i).length() < shortestWord.length()) {
		        	shortestWord = dictionary.get(i);
		        	minWordLength = dictionary.get(i).length();
		        }
		    }
		   wordLength = random.nextInt(maxWordLength + 1 - minWordLength) + minWordLength;
		   	   
//		   System.out.println(maxWordLength);
//		   System.out.println(minWordLength);
//		   System.out.println(wordLength);
		   
		   return wordLength;
	   }

	public static void main(String [] args) {
		// TODO Auto-generated method stub
		Scanner sc = new Scanner(System.in);
		ArrayList<String> dictionary = new ArrayList<String>();
		
		int numOfLetter = 0;
		int numOfGuesses;
		boolean evilVersion;

		char guess;
		
		Words w = new Words();
		dictionary = w.getDictionary();
		System.out.println(dictionary);
		while(true) {
			System.out.println("Welcome to Hangman Game!");
			
			while(true) {	
				
				numOfLetter = chooseWordLengthRandomly(dictionary, numOfLetter);
				
			    //System.out.println(numOfLetter);
			    
				System.out.println("Please enter the number of guesses you want");
				System.out.print("Number of guesses: ");
				while (!sc.hasNextInt()) {
					sc.next();
					System.out.println("Invalid input, please try again!");
				}
				numOfGuesses = sc.nextInt();
			
				if(numOfGuesses > 0) {
					break;
				}
				else  {
					System.out.println("Invalid number of guesses.");
				}
				
			}
			System.out.println();
			
			Random evil = new Random();
			evilVersion = evil.nextBoolean();
			//System.out.println(evilVersion);
			
			/**
			 * randomly choose the game version
			 */
			String formattedOutput;
			if (evilVersion == true) {
				Hangman evilGame = new HangmanEvil(numOfLetter, numOfGuesses, dictionary);
				formattedOutput = evilGame.showWord();
				System.out.println(formattedOutput.replaceAll(".", "$0 "));
				
				while(!evilGame.gameOver()) {
					while(true) {
						System.out.println("Enter your guess: ");
						guess = sc.next().charAt(0);
						
						if(evilGame.checkRepeat(guess)) {
							break;
						}
						else {
							System.out.println();
							System.out.println("You alreay guessed that letter.");
						}
					}
					
					if(evilGame.check(guess)) {
						System.out.println();
						System.out.println("Nice guess!");
					}
					else {
						System.out.println();
						System.out.println("Sorry, try again!");
					}
					
					System.out.println();
					formattedOutput = evilGame.showWord();
					System.out.println(formattedOutput.replaceAll(".", "$0 "));
					System.out.println("Guesses left: " + evilGame.getNumofGuesses());
					System.out.println("Guessed letters: " + evilGame.getGuesses());
				}
				
				
				if(evilGame.win()) {
					System.out.println();
					System.out.println("Congratulations, You Won!");
					System.out.println();
					System.out.println("You played the evil version.");
				}
				else {
					System.out.println();
					System.out.println("You lost.., maybe next time.");
				}
				System.out.println();
				System.out.println("The Secret Word is " + evilGame.getFinalAnswer() + ".");
				break;
				
			} else {
				Hangman traditionalGame = new HangmanTraditional(numOfLetter, numOfGuesses, dictionary);
//				System.out.println(traditionalGame.showWord());
				formattedOutput = traditionalGame.showWord();
				System.out.println(formattedOutput.replaceAll(".", "$0 "));
				while(!traditionalGame.gameOver()) {
					while(true) {
						System.out.println("Enter your guess: ");
						guess = sc.next().charAt(0);
						
						if(traditionalGame.checkRepeat(guess)) {
							break;
						}
						else {
							System.out.println();
							System.out.println("You alreay guessed that letter.");
						}
					}
					
					if(traditionalGame.check(guess)) {
						System.out.println();
						System.out.println("Nice guess!");
					}
					else {
						System.out.println();
						System.out.println("Sorry, try again!");
					}
					
					System.out.println();
					formattedOutput = traditionalGame.showWord();
					System.out.println(formattedOutput.replaceAll(".", "$0 "));
					System.out.println();
					System.out.println("Guesses left: " + traditionalGame.getNumofGuesses());
					System.out.println("Guessed Letters: " + traditionalGame.getGuesses());
				}
				
				if(traditionalGame.win()) {
					System.out.println();
					System.out.println("Congratulations, You Won!");
				}
				else {
					System.out.println();
					System.out.println("You lost.., maybe next time.");
				}
				System.out.println();
				System.out.println("The Secret Word is " + traditionalGame.getFinalAnswer() + ".");
				break;
			}
		}
		sc.close();

	}
	
	
}