package hangman;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import org.junit.jupiter.api.BeforeEach; 
import org.junit.jupiter.api.Test;

class HangmanTraditionalTest 
{
	private int numOfLetter = 0;
	private int numOfGuesses = 0;
	
	private ArrayList<String> dictionary;
	private ArrayList<String> wordList = new ArrayList<String>();
	
	@BeforeEach
	void setUp() throws FileNotFoundException {
		Scanner sc = new Scanner(System.in);
		dictionary = new ArrayList<String>();
		Scanner readFile = new Scanner(new File("short_list.txt"));
		while(readFile.hasNextLine())
		{
			dictionary.add(readFile.nextLine());
		}
		readFile.close();
		sc.close();
	}
	

	@Test
	void testHangmanTraditional() {
		HangmanTraditional traditional = new HangmanTraditional(numOfLetter, numOfGuesses, dictionary);
		assertEquals(0, traditional.getNumofGuesses());
		assertEquals(0, traditional.wordListSize());
		
		HangmanTraditional traditional1 = new HangmanTraditional(4, numOfGuesses, dictionary);
		
		for(String word : dictionary)
		{
			if(word.length() == numOfLetter)
			{
				wordList.add(word); 
			}
		}
		
		System.out.println(wordList.size());
		assertEquals(1, traditional1.wordListSize());
		assertEquals("____", traditional1.showWord());
	}
	
	@Test
	void testCheck() {
		ArrayList<String> dic = new ArrayList<String>();
		dic.add("echo");
		HangmanTraditional traditional = new HangmanTraditional(4, 4, dic);
		assertEquals(1, traditional.wordListSize());
		assertTrue(traditional.check('e'));
		//since the user check the word, the number of guesses stay same.
		assertEquals(traditional.numOfGuesses, 4);
		
		//numOfGuesses decrease by 1
		traditional.check('t');
		assertEquals(traditional.numOfGuesses, 3);
		assertEquals(traditional.finalAnswer,"echo");
		
		//the number of guess will stay same since the user guesses are correct 
		traditional.check('c');
		traditional.check('h');
		assertEquals(traditional.numOfGuesses, 3);
				
		//after check 'o' since the user guessed the word, the game is over.
		traditional.check('o');
		assertTrue(traditional.gameOver());	
		
		ArrayList<String> dic1 = new ArrayList<String>();
		dic1.add("echo");
		dic1.add("late");
		dic1.add("book");
		HangmanTraditional traditional1 = new HangmanTraditional(4, 4, dic);
		assertEquals(1, traditional1.wordListSize());
		
	}
	
	@Test
	void testCheckRepeat() {
		HangmanTraditional traditional1 = new HangmanTraditional(3, 3, dictionary);
		traditional1.check('r');
		assertFalse(traditional1.checkRepeat('r'));
		assertTrue(traditional1.checkRepeat('t'));
	}
	
	@Test
	void testGameOver() {
		
		ArrayList<String> dic = new ArrayList<String>();
		dic.add("one");
		HangmanTraditional traditional = new HangmanTraditional(3, 1, dic);
		traditional.check('e');
		assertFalse(traditional.gameOver());
		
		HangmanTraditional traditional1 = new HangmanTraditional (3, 1, dic);
		assertFalse(traditional1.gameOver());

		HangmanTraditional traditional2 = new HangmanTraditional (3, 4, dic);
		traditional2.check('o');
		traditional2.check('n');
		traditional2.check('e');
		assertTrue(traditional2.gameOver());
		
		HangmanTraditional traditional3 = new HangmanTraditional (3, 2, dictionary);
		assertFalse(traditional3.gameOver());
	}
	
	@Test
	void testShowWord() {
		HangmanTraditional traditional = new HangmanTraditional (3, 1, dictionary);
		assertEquals(traditional.showWord(), "___");
		
		HangmanTraditional traditional1 = new HangmanTraditional (4, 1, dictionary);
		assertEquals(traditional1.showWord(), "____");
	}
	
	@Test
	void testGetNumOfGuesses() {
		HangmanTraditional traditional = new HangmanTraditional (3, 1, dictionary);
		assertEquals(traditional.getNumofGuesses(), 1);
		
		ArrayList<String> dic = new ArrayList<String>();
		dic.add("one");
		HangmanTraditional traditional1 = new HangmanTraditional(3, 6, dic);
		traditional1.check('r');
		assertEquals(traditional1.getNumofGuesses(), 5);
		traditional1.check('o');
		traditional1.check('n');
		assertEquals(traditional1.getNumofGuesses(), 5);
	}
	
	@Test
	void testWordListSize() {
		HangmanTraditional traditional = new HangmanTraditional (3, 1, dictionary);
		assertEquals(1, traditional.wordListSize());
		
		HangmanTraditional traditional1 = new HangmanTraditional (6, 1, dictionary);
		assertEquals(1, traditional1.wordListSize());

	}
	
	@Test
	void testGetGuesses() {
		ArrayList<String> dic = new ArrayList<String>();
		dic.add("one");
		
		HangmanTraditional traditional = new HangmanTraditional (3, 1, dic);
		assertEquals("[]", traditional.getGuesses());
		traditional.check('t');
		assertEquals("[t]", traditional.getGuesses());
		traditional.check('e');
		//The number of guesses goes zero, after checking t so it does not add e in the guesses list
		assertEquals("[t]", traditional.getGuesses());
		
		HangmanTraditional traditional1 = new HangmanTraditional (3, 4, dic);
		traditional1.check('r');
		traditional1.check('k');
		assertEquals("[r, k]", traditional1.getGuesses());
		traditional1.check('n');
		assertEquals("[r, k, n]", traditional1.getGuesses());
	}
	
	@Test 
	void testGetFinalAnswer() {
		ArrayList<String> dic = new ArrayList<String>();
		dic.add("one");
		
		HangmanTraditional traditional = new HangmanTraditional (3, 1, dic);
		assertEquals(null, traditional.getFinalAnswer());
		
		traditional.check('o');
		traditional.check('e');
		traditional.check('n');
		assertEquals("one", traditional.getFinalAnswer());
	}
	
	@Test
	void testWin() {
		ArrayList<String> dic = new ArrayList<String>();
		dic.add("one");
		
		HangmanTraditional traditional = new HangmanTraditional (3, 1, dic);
		assertFalse(traditional.win());
		
		traditional.check('o');
		traditional.check('e');
		traditional.check('n');
		assertTrue(traditional.win());
	}


}
