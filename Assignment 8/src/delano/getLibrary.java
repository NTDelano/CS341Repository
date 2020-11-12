package delano;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * The Class getLibrary.
 * The puprpose is to find the file for the library, and load the content into a hashmap
 */
public class getLibrary {

	/** The file name for our library */
	public String filepath;
	
	/** The entire library within a hashmap */
	public HashMap<String, List<String>> library;

	/**
	 * Instantiates a new hashmap that will contain classify the sku's and the variables associated with it
	 *
	 * @param fileDirectory the name of the file to save the library to
	 */
	public getLibrary(String fileDirectory) {
		filepath = fileDirectory;
		library = new HashMap<String, List<String>>();
		loadFile();
	}

	/**
	 * Load the file containing the library components
	 */
	public void loadFile() {
		File fileOpen = new File(filepath);
		LinkedList<String> ListOfFileLines = new LinkedList<String>();
		if (fileOpen.exists() == false) {
			try {
				fileOpen.createNewFile(); //If file doesn't exist, create a new one
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			try {//else, read file, with each line representing a new key and its values
				FileReader fileReader = new FileReader(fileOpen); // FileReader reads text files in the default
				BufferedReader bufferedReader = new BufferedReader(fileReader); // always wrap the FileReader in BufferedReader
				String line = null;
				while ((line = bufferedReader.readLine()) != null) { // While examining each line in text file
					ListOfFileLines.add(line);
				}
				bufferedReader.close(); // always close the file after use
			} catch (IOException ex) { // Just in case file isn't readable
				ex.printStackTrace();
			}
			if (ListOfFileLines.isEmpty()) {
				System.out.println("file is empty");
			} else {
				for (int i = 0; i < ListOfFileLines.size(); i++) {
					String[] contents = ListOfFileLines.get(i).split(":");
					@SuppressWarnings("serial")
					List<String> valuesToAdd = new ArrayList<String>() {//create array of values for sku
						{
							add(contents[1]); //Book name
							add(contents[2]); //Price
							add(contents[3]); //Quantity
						}
					};
					library.put(contents[0], valuesToAdd);
				}
			}
		}
	}
	/**
	 * The full library retrieved from the file
	 *
	 * @return the hash map containing each book and its respective attributes
	 */
	public HashMap<String, List<String>> lib() {
		return library;
	}
}