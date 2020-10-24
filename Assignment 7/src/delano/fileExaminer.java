package delano;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * The Class fileExaminer. Used to take files and put every word contained within the file into an array
 */
public class fileExaminer {

	/** Array containing every word found in file. */
	public static List<String> everyWord = new ArrayList<>();
	
	/** Counter for recording the number of lines used in file */
	public int lineCounter;

	/**
	 * Instantiates a new file examiner by taking the file, reading it line by line, and gathering the content from  within those lines
	 *
	 * @param recipient the recipient
	 */
	public fileExaminer(File recipient) {
		LinkedList<String> ListOfFileLines = new LinkedList<String>();
		lineCounter = 0;
		try {
			/* FileReader reads text files in the default encoding */
			FileReader fileReader = new FileReader(recipient);

			/* always wrap the FileReader in BufferedReader */
			BufferedReader bufferedReader = new BufferedReader(fileReader);

			String line = null;

			while ((line = bufferedReader.readLine()) != null) {
				lineCounter++;
				if (line.isBlank()) { // If the line is empty or only contains white spaces
					lineCounter -= 1; // Don't include that line in the counter
				} else {
					//To avoid complications in hash function, all strings are converted to lowercase
					ListOfFileLines.add(line.toLowerCase());
				}
			}

			/* always close the file after use */
			bufferedReader.close();
		} catch (IOException ex) {
			System.out.println("Error: Unable to read the File");
		}
		// If the file is empty
		if (ListOfFileLines.isEmpty()) {
			System.out.println("Error: Nothing is in the File Selected");
		} else {
			//as long as the file isn't empty, then we can start compiling the lines into an array with each word in each line
			everyWord = contentsCompilier(ListOfFileLines);
		}
	}

	/**
	 * Contents compiler.
	 *
	 * @param c The array of all the lines from analyzing the file
	 * @return the list of words gathered from every line
	 */
	private List<String> contentsCompilier(LinkedList<String> c) {
		List<String> contents = new ArrayList<>();

		for (int i = 0; i < c.size(); i++) {
			String[] temp = c.get(i).split("\\W+");
			for (int j = 0; j < temp.length; j++) {
				contents.add(temp[j]);
			}
		}
		return contents;
	}

	/**
	 * Total word count within the file we are examining.
	 *
	 * @return the int total word count
	 */
	public int totalWordCount() {
		return everyWord.size();
	}

	/**
	 * the list of all the words extracted from the file examined.
	 *
	 * @return the list of every word
	 */
	public List<String> Words() {
		return everyWord;
	}
}
