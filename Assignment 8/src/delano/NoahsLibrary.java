package delano;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 * The Class NoahsLibrary. Manages the library through displaying inventory,
 * adding and removing books, or just searching if a book is in stock Books can
 * only be found using SKU
 */
public class NoahsLibrary {

	/** The frame. */
	private JFrame frame;

	/** The input for SKU */
	private JTextField SKU_Input;

	/** The book title input. */
	private JTextField bookTitle_Input;

	/** The Price input. */
	private JTextField Price_Input;

	/** The Quantity input. */
	private JTextField Quantity_Input;

	/** The Add book btn. */
	private JButton AddBookBtn;

	/** The file lib. */
	public File fileLib;

	/** The noahs library hashmap. */
	public HashMap<String, List<String>> noahsLibrary;

	/** The Constant FILE_NAME. */
	private static final String FILE_NAME = "Noah's_Library.txt";

	/** The tabbed pane for multiple features of library. */
	private JTabbedPane tabbedPane;

	/** The add book tab. */
	private JPanel addBookTab;

	/** The search book tab. */
	private JPanel searchBookTab;

	/** The add book text area. */
	private JTextArea addBookTextArea;

	/** The search by sku input. */
	private JTextField searchBySkuInput;

	/** The search by sku button. */
	private JButton searchBySkuAction;

	/** The found book info. */
	private JTable foundBookInfo;

	/** The error field. */
	private JTextField errorField;

	/** The remove book tab. */
	private JPanel removeBookTab;

	/** The sku of removable book. */
	private JTextField skuOfRemovableBook;

	/** The removable book search. */
	private JButton removableBookSearch;

	/** The removed book text area. */
	private JTextArea removedBookTextArea;

	/** The noahs library inventory. */
	private JTable noahsLibraryInventory;

	/** The updated inventory. */
	private JButton updateInventory;

	/**
	 * Launch the application.
	 *
	 * @param args launches the window and makes it visible
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					NoahsLibrary window = new NoahsLibrary();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application with 4 seperate tabs and functions
	 */
	public NoahsLibrary() {
		initialize(); // Creates our basic frame and features
		addBook(); // Process of adding one or more books to inventory
		searchBook(); // Process of finding a certain book based on the sku entered
		removeBook(); // process of removing a book from the inventory based on the sku inserted
		inventory(); // Displays all books and the information of them using a jtable
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(10, 10, 426, 450);
		frame.getContentPane().add(tabbedPane);

		addBookTab = new JPanel();
		tabbedPane.addTab("Add A Book", null, addBookTab, null);
		addBookTab.setLayout(null);

		JLabel lblNewLabel = new JLabel("SKU");
		lblNewLabel.setBounds(44, 38, 45, 13);
		addBookTab.add(lblNewLabel);

		SKU_Input = new JTextField();
		SKU_Input.setBounds(128, 35, 216, 19);
		addBookTab.add(SKU_Input);
		SKU_Input.setColumns(10);

		JLabel lblNewLabel_3 = new JLabel("Quantity");
		lblNewLabel_3.setBounds(44, 220, 45, 13);
		addBookTab.add(lblNewLabel_3);

		JLabel lblNewLabel_1 = new JLabel("Title");
		lblNewLabel_1.setBounds(44, 101, 45, 13);
		addBookTab.add(lblNewLabel_1);

		JLabel lblNewLabel_2 = new JLabel("Price");
		lblNewLabel_2.setBounds(44, 160, 45, 13);
		addBookTab.add(lblNewLabel_2);

		bookTitle_Input = new JTextField();
		bookTitle_Input.setBounds(128, 98, 216, 19);
		addBookTab.add(bookTitle_Input);
		bookTitle_Input.setColumns(10);

		Price_Input = new JTextField();
		Price_Input.setBounds(128, 157, 216, 19);
		addBookTab.add(Price_Input);
		Price_Input.setColumns(10);

		Quantity_Input = new JTextField();
		Quantity_Input.setBounds(128, 217, 216, 19);
		addBookTab.add(Quantity_Input);
		Quantity_Input.setColumns(10);

		AddBookBtn = new JButton("New button");
		AddBookBtn.setBounds(128, 275, 85, 21);
		addBookTab.add(AddBookBtn);

		addBookTextArea = new JTextArea();
		addBookTextArea.setBounds(10, 312, 401, 101);
		addBookTab.add(addBookTextArea);

		removeBookTab = new JPanel();
		tabbedPane.addTab("Remove a Book", null, removeBookTab, null);
		removeBookTab.setLayout(null);

		JLabel lblNewLabel_5 = new JLabel("What Book would you like to buy?");
		lblNewLabel_5.setBounds(125, 22, 179, 13);
		removeBookTab.add(lblNewLabel_5);

		skuOfRemovableBook = new JTextField();
		skuOfRemovableBook.setBounds(200, 66, 211, 19);
		removeBookTab.add(skuOfRemovableBook);
		skuOfRemovableBook.setColumns(10);

		removableBookSearch = new JButton("Search");
		removableBookSearch.setBounds(200, 151, 85, 21);
		removeBookTab.add(removableBookSearch);

		JLabel lblNewLabel_6 = new JLabel("SKU of the Book");
		lblNewLabel_6.setBounds(49, 69, 94, 13);
		removeBookTab.add(lblNewLabel_6);

		removedBookTextArea = new JTextArea();
		removedBookTextArea.setBounds(26, 238, 385, 142);
		removeBookTab.add(removedBookTextArea);

		searchBookTab = new JPanel();
		tabbedPane.addTab("Search for a Book", null, searchBookTab, null);
		searchBookTab.setLayout(null);

		JLabel lblNewLabel_4 = new JLabel("Search for Book (by SKU):");
		lblNewLabel_4.setBounds(33, 54, 136, 13);
		searchBookTab.add(lblNewLabel_4);

		searchBySkuInput = new JTextField();
		searchBySkuInput.setBounds(205, 51, 181, 19);
		searchBookTab.add(searchBySkuInput);
		searchBySkuInput.setColumns(10);

		searchBySkuAction = new JButton("Find");
		searchBySkuAction.setBounds(149, 99, 85, 21);
		searchBookTab.add(searchBySkuAction);

		// Initialized jtable
		String data[][] = { { " ", " ", " ", " " } };
		String columns[] = { "SKU", "Name", "Price", "Quantity" };
		foundBookInfo = new JTable(data, columns);

		JScrollPane scrollPane = new JScrollPane(foundBookInfo);
		scrollPane.setBounds(0, 175, 421, 67);
		searchBookTab.add(scrollPane);

		errorField = new JTextField();
		errorField.setBounds(0, 146, 421, 19);
		searchBookTab.add(errorField);
		errorField.setColumns(10);

		JPanel libraryInventory = new JPanel();
		tabbedPane.addTab("Inventory", null, libraryInventory, null);
		libraryInventory.setLayout(null);

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(10, 77, 401, 336);
		libraryInventory.add(scrollPane_1);

		// Initialized jtable
		noahsLibraryInventory = new JTable(data, columns);
		scrollPane_1.setViewportView(noahsLibraryInventory);

		updateInventory = new JButton("New button");
		updateInventory.setBounds(152, 46, 85, 21);
		libraryInventory.add(updateInventory);

		getLibrary library = new getLibrary(FILE_NAME);
		noahsLibrary = library.lib();

		frame.addWindowListener((WindowListener) new WindowAdapter() { // Action event for when the window is closed
			public void windowClosing(WindowEvent e) { // The goal is to take our updated library and save it to a file
														// for use next time
				// new file object
				File file = new File(FILE_NAME);
				BufferedWriter bf = null;
				try {
					// create new BufferedWriter for the output file
					bf = new BufferedWriter(new FileWriter(file));
					// iterate map entries
					for (Entry<String, List<String>> entry : noahsLibrary.entrySet()) {
						List<String> contents = entry.getValue();
						// put key and value separated by a colon
						bf.write(
								entry.getKey() + ":" + contents.get(0) + ":" + contents.get(1) + ":" + contents.get(2));
						// new line
						bf.newLine();
					}
					bf.flush();
				} catch (IOException e1) {
					e1.printStackTrace();
				} finally {
					try {
						// always close the writer
						bf.close();
					} catch (Exception e1) {
					}
				}
			}
		});
	}
	/**
	 * Adding 1 or more books to the inventory, whether or not it is in stock
	 */
	private void addBook() {
		// Creates an action listener for the button to be pressed
		AddBookBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addBookTextArea.setText(""); //Initialize empty text area
				//variables of the book being inserted into the system
				String sku = SKU_Input.getText();
				String bName = bookTitle_Input.getText().toLowerCase();
				String price = Price_Input.getText();
				String quantity = Quantity_Input.getText();

				if (sku.isBlank() || bName.isBlank() || price.isBlank() || quantity.isBlank()) { //if any of the fields are empty, then error
					addBookTextArea.setText("ERROR: All the fields above should not be blank for a new entry");
				} else if (isInteger(sku) == false) { //ensures sku is only an integer
					addBookTextArea.setText("ERROR: SKU must be an Integer");
				} else if (isDouble(price) == false) { //ensure price is a double
					addBookTextArea.setText("ERROR: Price must be a Double");
				} else if (isInteger(quantity) == false) { //ensures quantity is an integer
					addBookTextArea.setText("ERROR: Quantity must be an Integer");
				} else { //as long as none of the errors occur, then its time to analyze the inputs
					boolean existence = checkSKUForExistence(sku); //check to see if a book with the same sku already exists
					int Quan = Integer.parseInt(quantity);
					if (existence == true) { //if the sku already exists, then we just add the number of books being added to the invetory
						List<String> update = noahsLibrary.get(sku); //already stocked
						int qUpdate = Integer.parseInt(update.get(2));
						qUpdate += Quan;
						update.set(2, Integer.toString(qUpdate));
						noahsLibrary.put(sku, update);
						addBookTextArea.setText("The Book " + update.get(0)
								+ " already exists by the sku.\n So it has been added to that sku collection.");
					} else { //if the book sku isn't in the system, we create a new pathway to it
						@SuppressWarnings("serial")
						List<String> valuesToAdd = new ArrayList<String>() { //create array of variables for the sku key
							{
								add(bName);
								add(price);
								add(quantity);
							}
						};
						noahsLibrary.put(sku, valuesToAdd); //add new book or books to library
						addBookTextArea.setText("Book(s) have been added to the library");
					}
				}
			}
		});
	}
	/**
	 * Removes a book from the library
	 */
	private void removeBook() {
		removableBookSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				removedBookTextArea.setText(""); //initialize text area
				String sku = skuOfRemovableBook.getText();
				boolean existence = checkSKUForExistence(sku); //check if the book is instock
				if (sku.isBlank()) { //error if input is empty
					removedBookTextArea.setText("ERROR: Field can't be Empty");
				} else if (isInteger(sku) == false) { //error is sku isn't an integer
					removedBookTextArea.setText("ERROR: SKU must be an Integer");
				} else {
					if (existence == false) { //error if the book doesn't exist in the system
						removedBookTextArea.setText("Unfortunately, no book matches that SKU");
					} else { //remove the book from stock
						List<String> rValues = noahsLibrary.get(sku);
						int quan = Integer.parseInt(rValues.get(2));
						removedBookTextArea.setText("Book: " + rValues.get(0) + "\n This book will cost the user $"
								+ rValues.get(1) + "\n There is now " + (quan - 1) + " copies in stock");
						quan -= 1;
						rValues.set(2, Integer.toString(quan));
						noahsLibrary.put(sku, rValues);
					}
				}
			}
		});
	}

	/**
	 * Search to see if a certain book is in stock, given you have the sku
	 */
	private void searchBook() {
		searchBySkuAction.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String input = searchBySkuInput.getText();
				if (input.isBlank()) { //error if input is blank
					errorField.setText("Error: Input is blank");
				} else if (isInteger(input) == false) { //error if input isn't an integer (sku)
					errorField.setText("Error: Input doesn't work as SKU only contains numbers");
				} else {
					boolean existence = checkSKUForExistence(input);
					if (existence == false) {//check if the input is an sku in the system
						errorField.setText("Unfortunately, no book matches that SKU");
					} else { //if found, display in jtable
						foundBookInfo.setValueAt(input, 0, 0);
						List<String> valuesToDisplay = noahsLibrary.get(input);
						for (int i = 0; i < valuesToDisplay.size(); i++) {
							foundBookInfo.setValueAt(valuesToDisplay.get(i), 0, i + 1);
						}
					}
				}
			}
		});
	}
	/**
	 * The full inventory within the library, displayed in a nice jtable
	 */
	private void inventory() {
		updateInventory.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String[][] data = new String[noahsLibrary.keySet().size()][4]; //create a matrix for the jtable
				int k = 0;
				for (String sku : noahsLibrary.keySet()) { //iterate through keys
					List<String> iValues = noahsLibrary.get(sku); //get the values associated with the key
					data[k][0] = sku; //put the sku and the values within the row consecutively
					for (int j = 0; j < iValues.size(); j++) {
						data[k][j + 1] = iValues.get(j);
					}
					k += 1; //next row
				}
				String columns[] = { "SKU", "Name", "Price", "Quantity" };
				DefaultTableModel updatedData = new DefaultTableModel(data, columns); //update jtable based on data parameters
				noahsLibraryInventory.setModel(updatedData);
			}
		});
	}
	/**
	 * Check SKU for existence.
	 *
	 * @param sku the sku of a certain book
	 * @return true, if the book with the sku is within the library
	 */
	public boolean checkSKUForExistence(String sku) {
		if (noahsLibrary.containsKey(sku)) {
			return true;
		} else {
			return false;
		}
	}
	/**
	 * Checks if a string can be parsed into an integer
	 *
	 * @param strNum the str we are parsing
	 * @return true, if the string can be written as an integer
	 */
	public static boolean isInteger(String strNum) {
		if (strNum == null) {
			return false;
		}
		try {
			Integer.parseInt(strNum);
		} catch (NumberFormatException nfe) {
			return false;
		}
		return true;
	}
	/**
	 * Checks if a string can be parsed into a double
	 *
	 * @param strNum the str we are parsing
	 * @return true, if the string can be written as an integer
	 */
	public static boolean isDouble(String strNum) {
		if (strNum == null) {
			return false;
		}
		try {
			Double.parseDouble(strNum);
		} catch (NumberFormatException nfe) {
			return false;
		}
		return true;
	}
}