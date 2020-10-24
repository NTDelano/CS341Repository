package delano;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import java.awt.Font;
import javax.swing.JTextArea;

/**
 * The Class Main.
 */
public class Main {

	/** The frame. */
	private JFrame frame;
	
	/** The text area. */
	private JTextArea textArea;
	
	/** The collection of Keys button. */
	private JButton KeysButton;
	
	/** The collection of the words from the file we are examining btn. */
	private JButton statsBtn;
	
	/** The run btn. */
	private JButton runBtn;
	
	/** The Keyword file. */
	public File KeywordFile;
	
	/** The Text file. */
	public File TextFile;
	
	/** The keyword counter. */
	public static HashMap<String, Integer> keywordCounter = new HashMap<>();
	
	/** The g values map. */
	public static HashMap<Character, Integer> gValuesMap = new HashMap<>();
	
	/** The List of keywords. */
	List<String> ListOfKeywords = new ArrayList<String>();
	
	/** The List of textwords. */
	List<String> ListOfTextwords = new ArrayList<String>();

	/**
	 * Launch the application.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main window = new Main();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Main() {
		initialize();
		gatherKeys();
		gatherText();

		runBtn.setEnabled(false);

		runProgram();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	public void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 214, 488);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		KeysButton = new JButton("Select Keyword .txt File");
		KeysButton.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		KeysButton.setBounds(0, 42, 200, 50);
		frame.getContentPane().add(KeysButton);

		statsBtn = new JButton("Select .txt File");
		statsBtn.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		statsBtn.setBounds(0, 102, 200, 50);
		frame.getContentPane().add(statsBtn);

		runBtn = new JButton("Run Program");
		runBtn.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		runBtn.setBounds(0, 162, 200, 50);
		frame.getContentPane().add(runBtn);

		textArea = new JTextArea();
		textArea.setBounds(0, 237, 200, 204);
		frame.getContentPane().add(textArea);

		JScrollPane scrollPanel = new JScrollPane(textArea);
		scrollPanel.setBounds(0, 237, 200, 204);
		frame.getContentPane().add(scrollPanel);

	}

	/**
	 * Uses JFileChooser to select a txt file, which should contain the keywords we are wanting to examine from the text file
	 */
	public void gatherKeys() {

		KeysButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				// Sets the action of the button press to a JFileChooser
				JFileChooser chooseFile = new JFileChooser();
				chooseFile.showOpenDialog(null); // replace null with your swing container
				// The Selected file for which we are examining
				KeywordFile = chooseFile.getSelectedFile();

				if (KeywordFile != null && TextFile != null) {
					runBtn.setEnabled(true);
				}

			}

		});
	}

	/**
	 * Gathers the txt file with all of the words we are examining 
	 */
	public void gatherText() {

		statsBtn.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				// Sets the action of the button press to a JFileChooser
				JFileChooser chooseFile = new JFileChooser();
				chooseFile.showOpenDialog(null); // replace null with your swing container
				// The Selected file for which we are examining
				TextFile = chooseFile.getSelectedFile();

				// fileExaminer fileWords = new fileExaminer(fileToExamine);
				if (KeywordFile != null && TextFile != null) {
					runBtn.setEnabled(true);
				}
			}

		});

	}

	/**
	 * Run the full program, including gathering the keywords, gathering every word from the text file, apply the hash function
	 * to the key words, and ran the text file through it to see what values came out.
	 */
	public void runProgram() {

		runBtn.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				long start = System.currentTimeMillis();

				fileExaminer fileWords = new fileExaminer(KeywordFile);
				ListOfKeywords = fileWords.Words();
				MinimalPerfectHashFunction hashed = new MinimalPerfectHashFunction(ListOfKeywords);
				HashMap<String, Integer> analyzer = hashed.keywordValues();
				gValuesMap = hashed.gValues();

				fileExaminer textWords = new fileExaminer(TextFile);
				ListOfTextwords = textWords.Words();

				for (String c : ListOfKeywords) {
					keywordCounter.put(c, 0);
				}

				for (int i = 0; i < ListOfTextwords.size(); i++) {
					String w = ListOfTextwords.get(i);
					int function = hfunction(w);
					if (analyzer.containsValue(function) == false) {
						i += 1;
					} else {
						for (String word : analyzer.keySet()) {
							if (function == analyzer.get(word)) {
								keywordCounter.put(word, keywordCounter.get(word) + 1);
							}

						}
					}

				}
				int lineCount = fileWords.lineCounter;
				int wordCount = ListOfTextwords.size();
				long end = System.currentTimeMillis();
				int totalKeywords = 0;
				String post = "";
				for (String word : keywordCounter.keySet()) {
					post += "        " + word + ": " + keywordCounter.get(word) + "\r\n";
					totalKeywords += keywordCounter.get(word);
				}

				textArea.setText("*********************\r\n" + "**** Statistics *****\r\n" + "*********************\r\n"
						+ "Total Lines Read: " + lineCount + "\r\n" + "Total Words Read: " + wordCount + "\r\n" + post
						+ "Total Key Words: " + totalKeywords + "\r\n" + "Total Time of Program: " + (end - start)
						+ " milliseconds");
			}

		});
	}

	/**
	 * Hfunction. Using the hfunction created from the minimal perfect hash function, but used in this program for easier access
	 *
	 * @param word the word we are running through the minimal perfect hash function
	 * @return the int value that the h function determine the word correlates to
	 */
	public int hfunction(String word) {

		char first = word.charAt(0);
		boolean existenceFirst = gValuesMap.containsKey(first);
		int gFirst = 0;
		if (existenceFirst == true) {
			gFirst = gValuesMap.get(first);
		} else {
			gValuesMap.put(first, 0);
			gFirst = 0;
		}
		int gLast = 0;
		if (word.length() == 1) {
			gLast = 0;
		} else {
			char last = word.charAt(word.length() - 1);
			boolean existenceLast = gValuesMap.containsKey(last);

			if (existenceLast == true) {
				gLast = gValuesMap.get(last);
			} else {
				gValuesMap.put(last, 0);
				gLast = 0;
			}
		}

		return (word.length() + gFirst + gLast) % ListOfKeywords.size();

	}
}
