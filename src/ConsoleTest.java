import java.util.Scanner;

/**
 * A console implementation of the tester
 * The default methods in the template suffice for this
 * so no overriding is do
 * @author Oliver
 *
 */
public class ConsoleTest extends TesterTemplate
{

	public ConsoleTest(String filename)
	{
		super(filename);
	}
	
	@Override
	public void determineNumQ()
	{
		System.out.println("How many questions do you want?");
		Scanner s = new Scanner(System.in);
		numQ = Integer.parseInt(s.nextLine());
	}
	
	@Override
	public void testManager()
	{
		int score = test();
		System.out.println("Your score was " + score + " out of "+ numQ);		
	}
	
	@Override
	public String askQuestion(int qNum, String q)
	{
		System.out.println("Question " + (qNum + 1) + ": " + q);
		Scanner s = new Scanner(System.in);
		String theirAnswer = s.nextLine().trim().toLowerCase();
		return theirAnswer;
	}

	
	@Override
	public int judge(boolean correct, int score, String[] answerArray)
	{
		if (correct)
		{
			System.out.println("  Correct!");
			score++;
		}
		else
		{
			System.out.println("  Incorrect. Acceptable solutions were:");
			for (int j = 0; j < answerArray.length; j++)
			{
				System.out.println("    " + answerArray[j]);
			}
		}
		return score;
	}
	
	public static void main(String[] args)
	{
		new ConsoleTest("mock_vocab.txt");
	}
}
