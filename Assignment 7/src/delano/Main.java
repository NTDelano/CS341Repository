package delano;

import java.awt.EventQueue;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JFileChooser;

public class Main {

	private JFrame frame;
	private JButton KeysButton;
	
	List<String> ListOfKeywords = new ArrayList<String>();

	/**
	 * Launch the application.
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
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		KeysButton = new JButton("New button");
		KeysButton.setBounds(162, 37, 85, 21);
		frame.getContentPane().add(KeysButton);
	}
	
	private void gatherKeys() {

		// Sets the action of the button press to a JFileChooser
		JFileChooser chooseFile = new JFileChooser();
		chooseFile.showOpenDialog(null); // replace null with your swing container
		// The Selected file for which we are examining
		File fileToExamine = chooseFile.getSelectedFile();
		
		String fname = fileToExamine.getAbsolutePath();

		String line = null;
		try {
			/* FileReader reads text files in the default encoding */
			FileReader fileReader = new FileReader(fileToExamine);

			/* always wrap the FileReader in BufferedReader */
			BufferedReader bufferedReader = new BufferedReader(fileReader);

			while ((line = bufferedReader.readLine()) != null) {
				String[] tempList = line.split(" ");
				for(int i = 0; i < tempList.length; i++) {
					
				}
			}

			/* always close the file after use */
			bufferedReader.close();
		} catch (IOException ex) {
			System.out.println("Error: Unable to read the File " + fname);
		}
	}
}
