import javax.swing.JOptionPane;

public class GUITest extends TesterTemplate
{

	public GUITest(String filename)
	{
		super(filename);
	}

	@Override
	public void determineNumQ()
	{
		try
		{
			numQ = Integer.parseInt(JOptionPane.showInputDialog("How many questions do you want?"));
		}
		catch (NumberFormatException e)
		{
			JOptionPane.showMessageDialog(null, "Bye then :(");
			System.exit(1);
		}
		catch (NullPointerException e)
		{
			JOptionPane.showMessageDialog(null, "Bye then :(");
			System.exit(1);
		}
	}

	@Override
	public void testManager()
	{
		int score = 0;
		try
		{
			score = test();
		}
		catch (NullPointerException e)
		{
			JOptionPane.showMessageDialog(null, "Bye then :(");
			System.exit(1);
		}
		JOptionPane.showMessageDialog(null, "Your score was " + score + " out of " + numQ);
	}

	@Override
	public String askQuestion(int qNum, String q)
	{
		return JOptionPane.showInputDialog("Question " + (qNum + 1) + ":\n\t" + q).trim().toLowerCase();
	}

	@Override
	public int judge(boolean correct, int score, String[] answerArray)
	{
		if (correct)
		{
			JOptionPane.showMessageDialog(null, "Correct!");
			score++;
		}
		else
		{
			String message = "Incorrect! Acceptable solutions were:\n";
			for (int j = 0; j < answerArray.length; j++)
			{
				message += "  " + answerArray[j] + "\n";
			}
			JOptionPane.showMessageDialog(null, message);
		}
		return score;
	}

	public static void main(String[] args)
	{
		new GUITest("dotr_part3_vocab.txt");
	}
}
