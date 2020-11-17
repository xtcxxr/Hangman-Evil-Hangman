package hangman;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import org.junit.jupiter.api.BeforeEach; 
import org.junit.jupiter.api.Test;

class HangmanEvilTest 
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
	void testHangmanEvil() {
		HangmanEvil evil = new HangmanEvil(numOfLetter, numOfGuesses, dictionary);
		assertEquals(0, evil.getNumofGuesses());
		assertEquals(0, evil.wordListSize());
		
		HangmanEvil evil1 = new HangmanEvil(3, numOfGuesses, dictionary);
		
		for(String word : dictionary)
		{
			if(word.length() == numOfLetter)
			{
				wordList.add(word); 
			}
		}
		
		System.out.println(wordList.size());
		assertEquals(4, evil1.wordListSize());
		assertEquals("___", evil1.showWord());
	}
	
	@Test
	void testCheck() {
		ArrayList<String> dic = new ArrayList<String>();
		dic.add("one");
		dic.add("echo");
		dic.add("heal");
		dic.add("belt");
		dic.add("host");
		dic.add("hello");

		HangmanEvil evil = new HangmanEvil(4, 4, dic);
		assertEquals(4, evil.wordListSize());
		//possible word choice: "echo", "heal", "belt", "host"
		//after 'e', possible word choice:"heal", "belt" since _,e_,_ pattern is longest 
		assertTrue(evil.check('e'));
		//since the user check the word, the number of guesses stay same.
		assertEquals(evil.numOfGuesses, 4);
		//until the game is over, the final answer is null
		assertNull(evil.getFinalAnswer());
		
		//after checking t, the final secret word will be "heal"
		//numOfGuesses decrease by 1
		evil.check('t');
		assertEquals(evil.numOfGuesses, 3);
		assertEquals(evil.finalAnswer,"heal");
		
		//the number of guess will stay same since the user guesses are correct 
		evil.check('l');
		evil.check('a');
		assertEquals(evil.numOfGuesses, 3);
				
		//after check 'h' since the user guessed the word, the game is over.
		assertTrue(evil.check('h'));
		assertTrue(evil.gameOver());	
		

		HangmanEvil evil1 = new HangmanEvil(4, 4, dic);
		assertEquals(4, evil1.wordListSize());
		//possible word choice: "echo", "heal", "belt", "host"
		//after 'e', possible word choice:"heal", "belt" since _,e_,_ pattern is longest 
		assertTrue(evil1.check('e'));
		evil1.check('b');
		evil1.check('x');
		evil1.check('w');
		evil1.check('y');
		//if the number of guesses becomes zero, the game is over
		assertEquals(evil1.numOfGuesses, 0);
		assertTrue(evil1.gameOver());	
		
	}
	
	@Test
	void testCheckRepeat() {
		HangmanEvil evil = new HangmanEvil(3, 3, dictionary);
		evil.check('r');
		assertFalse(evil.checkRepeat('r'));
		assertTrue(evil.checkRepeat('t'));
	}
	
	@Test
	void testGameOver() {
		HangmanEvil evil = new HangmanEvil(3, 1, dictionary);
		evil.check('e');
		assertTrue(evil.gameOver());
		
		HangmanEvil evil1 = new HangmanEvil (3, 1, dictionary);
		assertFalse(evil1.gameOver());
		
		ArrayList<String> dic = new ArrayList<String>();
		dic.add("one");
		HangmanEvil evil2 = new HangmanEvil (3, 4, dic);
		evil2.check('o');
		evil2.check('n');
		evil2.check('e');
		assertTrue(evil2.gameOver());
		
		HangmanEvil evil3 = new HangmanEvil (3, 4, dictionary);
		assertFalse(evil3.gameOver());
	}
	
	@Test
	void testShowWord() {
		HangmanEvil evil = new HangmanEvil(3, 1, dictionary);
		assertEquals(evil.showWord(), "___");
		
		HangmanEvil evil1 = new HangmanEvil(6, 1, dictionary);
		assertEquals(evil1.showWord(), "______");
	}
	
	@Test
	void testGetNumOfGuesses() {
		HangmanEvil evil = new HangmanEvil(3, 1, dictionary);
		assertEquals(evil.getNumofGuesses(), 1);
		
		ArrayList<String> dic = new ArrayList<String>();
		dic.add("one");
		HangmanEvil evil1 = new HangmanEvil(3, 6, dic);
		evil1.check('r');
		assertEquals(evil1.getNumofGuesses(), 5);
		evil1.check('o');
		evil1.check('n');
		assertEquals(evil1.getNumofGuesses(), 5);
	}
	
	@Test
	void testWordListSize() {
		HangmanEvil evil = new HangmanEvil(3, 1, dictionary);
		assertEquals(4, evil.wordListSize());
		
		HangmanEvil evil1 = new HangmanEvil(2, 1, dictionary);
		assertEquals(0, evil1.wordListSize());
	}
	
	@Test
	void testGetGuesses() {
		ArrayList<String> dic = new ArrayList<String>();
		dic.add("one");
		
		HangmanEvil evil = new HangmanEvil(3, 1, dic);
		assertEquals("[]", evil.getGuesses());
		evil.check('t');
		assertEquals("[t]", evil.getGuesses());
		evil.check('e');
		//The number of guesses goes zero, after checking t so it does not add e in the guesses list
		assertEquals("[t]", evil.getGuesses());
		
		HangmanEvil evil1 = new HangmanEvil(3, 4, dic);
		evil1.check('r');
		evil1.check('k');
		assertEquals("[r, k]", evil1.getGuesses());
		evil1.check('n');
		assertEquals("[r, k, n]", evil1.getGuesses());
	}
	
	@Test 
	void testGetFinalAnswer() {
		ArrayList<String> dic = new ArrayList<String>();
		dic.add("one");
		
		HangmanEvil evil = new HangmanEvil(3, 1, dic);
		assertEquals(null, evil.getFinalAnswer());
		
		evil.check('o');
		evil.check('e');
		evil.check('n');
		assertEquals("one", evil.getFinalAnswer());
	}
	
	@Test
	void testWin() {
		ArrayList<String> dic = new ArrayList<String>();
		dic.add("one");
		
		HangmanEvil evil = new HangmanEvil(3, 1, dic);
		assertFalse(evil.win());
		
		evil.check('o');
		evil.check('e');
		evil.check('n');
		assertTrue(evil.win());
	}

}
