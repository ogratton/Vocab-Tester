package auxil;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Splits the vocab file
 * 
 * @author Oliver
 */
public class VReader
{
	private BufferedReader br;
	private String line = "";
	private String comm = "#";
	private String csvSplitBy = ",";

	public VReader(String split, String comment)
	{
		comm = comment;
		csvSplitBy = split;
	}

	public ArrayList<String[]> readContents(String filename)
	{
		ArrayList<String[]> lines = new ArrayList<String[]>();
		try
		{
			br = new BufferedReader(new FileReader(filename));

			while ((line = br.readLine()) != null)
			{
				if (!line.startsWith(comm))
				{
					// use the separator
					String[] items = line.split(csvSplitBy);
					lines.add(items);
				}
			}
			return lines;
		}
		catch (IOException e)
		{
			System.out.println("Couldn't find file " + filename);
			return null;
		}
	}
}
