package delano;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class Serializer {
	private String filename;
	public ArrayList<Object> library;

	// pass in "entries.bin"
	public Serializer(String filename) {
		this.filename = filename;
		//loadFile();
		// deserialize();
	}

	@SuppressWarnings("unchecked")
	public void loadFile() {
		File fileOpen = new File(filename);
		if (fileOpen.exists()) {
			if (fileOpen.length() == 0) {
				library = new ArrayList<Object>();
			} else {
				try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileOpen))) {
					library = (ArrayList<Object>) ois.readObject();
				} catch (IOException e) {
					fileOpen.delete();
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
					fileOpen.delete();
				}
			}
		} else {
			library = new ArrayList<Object>();
		}
		
		System.out.println(library);
	}

	public void saveFile() {
		File fileSave = new File(filename);
		fileSave.delete();
		try {
			fileSave.createNewFile();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileSave))) {
			oos.writeObject(library);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/*
	 * private void promptForName() { String input = ""; try (BufferedReader br =
	 * new BufferedReader(new InputStreamReader(System.in))) { while
	 * (!input.equals("Exit")) { System.out.println(
	 * "Press 1 to enter new name.\r\nPress 2 to save.\r\nPress 3 to display.\r\nType Exit to exit."
	 * ); input = br.readLine(); if (input.equals("1")) {
	 * System.out.println("Enter a name"); input = br.readLine(); Name n = new
	 * Name(); n.name = input; names.add(n); } else if (input.equals("2")) {
	 * saveFile(); } else if (input.equals("3")) { for (Name n : names) {
	 * System.out.println(n.name); } } } } catch (IOException e) {
	 * e.printStackTrace(); } }
	 */
	/*
	 * public void append(Object target) { // readfile will return at least empty
	 * arraylist // String filepath = filename.getAbsolutePath(); ArrayList<Object>
	 * entries = readFile(); entries.add(target); serialize(entries); }
	 * 
	 * public static ArrayList<Object> readFile() { ArrayList<Object>
	 * persistedEntries = new ArrayList<>(); String filename = "entries.bin";
	 * 
	 * FileInputStream fileIn = null; ObjectInputStream objIn = null; try { fileIn =
	 * new FileInputStream(filename); objIn = new ObjectInputStream(fileIn);
	 * persistedEntries = ((ArrayList<Object>) objIn.readObject()); objIn.close(); }
	 * catch (IOException ex) { ex.printStackTrace(); } catch
	 * (ClassNotFoundException ex) { ex.printStackTrace(); }
	 * 
	 * return persistedEntries; }
	 * 
	 * public void serialize(ArrayList entries) { FileOutputStream fileOut = null;
	 * ObjectOutputStream objOut = null; try { fileOut = new
	 * FileOutputStream(filename); objOut = new ObjectOutputStream(fileOut);
	 * objOut.writeObject(entries); objOut.close(); } catch (IOException ex) {
	 * ex.printStackTrace(); } }
	 * 
	 * // Reads the file and returns all entries in a list
	 * 
	 * @SuppressWarnings("rawtypes") public ArrayList deserialize() { // ArrayList
	 * persistedEntries = new ArrayList<>();
	 * 
	 * FileInputStream fileIn = null; ObjectInputStream objIn = null; try { fileIn =
	 * new FileInputStream(filename); objIn = new ObjectInputStream(fileIn);
	 * 
	 * Object result = objIn.readObject(); if (!(result instanceof ArrayList)) { //
	 * read object is not an arraylist }
	 * 
	 * persistedEntries = (ArrayList) objIn.readObject(); objIn.close(); } catch
	 * (IOException ex) { ex.printStackTrace(); } catch (ClassNotFoundException ex)
	 * { ex.printStackTrace(); }
	 * 
	 * return persistedEntries; }
	 */
}