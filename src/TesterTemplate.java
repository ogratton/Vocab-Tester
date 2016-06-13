import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import auxil.VReader;

/**
 * Generic vocab tester
 * Extend this and overwrite all public methods to your needs
 * 
 * TODO allow user to combine dictionaries
 * TODO allow user to select range of questions from dict (e.g. only first 10)
 * TODO allow user to appeal a wrong answer and suggest another
 * TODO allow user to add words from within the program
 * @author Oliver
 *
 */
public abstract class TesterTemplate
{
	protected Map<String, String[]> vocab;
	protected String[] qs;

	protected int 	numQ;
	
	private String splitVoc = ":";
	private String splitDef = ";";
	private String comment = "#";
	
	private Random random = new Random();

	public TesterTemplate(String filename)
	{
		// read the file
		parseVocab(filename);
		determineNumQ();

		// make a simple array out of the questions
		Set<String> questionSet = vocab.keySet();
		qs = new String[questionSet.size()];
		int i = 0;
		for (Iterator<String> iterator = questionSet.iterator(); iterator.hasNext();)
		{
			String string = (String) iterator.next();
			qs[i] = string;
			i++;
		}
//		// print all the words
//		for(int j = 0; j<qs.length; j++)
//		{
//			System.out.println(qs[j]);
//		}
		testManager();
	}
	
	/**
	 * Set the number of questions in the test
	 */
	public abstract void determineNumQ();

	
	/**
	 * Starts the test and does whatever you want at the end
	 */
	public abstract void testManager();

	/**
	 * Vocab test
	 * 
	 * @return score
	 */
	public int test()
	{
		Set<Integer> questionsAsked = new TreeSet<Integer>();
		int score = 0;

		for (int i = 0; i < numQ; i++)
		{			
			// if we've run out of unique questions to ask, empty the set
			if (questionsAsked.size() == qs.length)
			{
				questionsAsked.clear();
			}
			
			int r = random.nextInt(qs.length);
			
			// ensures no duplicate questions
			while (questionsAsked.contains(r))
			{
				r = random.nextInt(qs.length);
			}

			// get the word at position r
			String q = qs[r];
			// get all the solutions
			String[] answerArray = vocab.get(q);
			// mark it as done so we don't ask it again
			questionsAsked.add(r);
			
			// ask question and get their answer
			String theirAnswer = askQuestion(i,q);
			
			// check if what they say is right
			boolean correct = arrayContains(theirAnswer, answerArray);
			
			score = judge(correct, score, answerArray);
		}
		return score;
	}
	
	/**
	 * Returns true if an item is in an array
	 * @param item
	 * @param array
	 * @return
	 */
	private boolean arrayContains(String item, String[] array)
	{
		boolean contains = false;
		for (int i = 0; i < array.length && !contains; i++)
		{
			contains = (item.trim().equals(array[i].trim()));
		}
		return contains;
	}
	
	private String[] addToArray(String item, String[] array)
	{
		String[] newArray = new String[array.length+1];
		for (int i = 0; i < array.length; i++)
		{
			newArray[i] = array[i]; 
		}
		newArray[newArray.length-1] = item;
		
		return newArray;
	}
	
//	private static <T> void printArray(T[] array)
//	{
//		for (int i = 0; i < array.length; i++)
//		{
//			System.out.println(array[i]);
//		}
//	}
	
	/**
	 * shows the question to the user and get their answer
	 */
	public abstract String askQuestion(int qNum, String q);
	
	/**
	 * Feedback to the user based on the correctness of their answer
	 * 
	 * @param correct if the user was right
	 * @param score what they currently have scored
	 * @param answerArray the correct answers
	 */
	public abstract int judge(boolean correct, int score, String[] answerArray);

	/**
	 * remove whitespace from String[]
	 * Also puts everything to lower case to be safe
	 * 
	 * @param array to be trimmed
	 */
	private String[] trim(String[] a)
	{
		for (int i = 0; i < a.length; i++)
		{
			a[i] = a[i].trim().toLowerCase();
		}
		return a;
	}

	/**
	 * Reads the data file and makes a map out of it
	 */
	private void parseVocab(String filename)
	{
		VReader csv = new VReader(splitVoc, comment);
		ArrayList<String[]> contents = csv.readContents(filename);

		vocab = new TreeMap<String, String[]>();
		for (int i = 0; i < contents.size(); i++)
		{
			// if the word is already in the map
			if (vocab.containsKey(contents.get(i)[0]))
			{
				// add it if it's new
				String[] currentAnswers = vocab.get(contents.get(i)[0].trim());
				String[] newAnswers = contents.get(i)[1].split(splitDef);
				for (int j = 0; j < newAnswers.length; j++)
				{
					if (!arrayContains(newAnswers[j],currentAnswers))
					{
						currentAnswers = addToArray(newAnswers[j], trim(currentAnswers));
					}
				}
				vocab.put(contents.get(i)[0], trim(currentAnswers));
			}
			else
			{
				vocab.put(contents.get(i)[0], trim(contents.get(i)[1].split(splitDef)));
			}
		}
		System.out.println("There are "+vocab.size()+" words in this set");
	}
}
