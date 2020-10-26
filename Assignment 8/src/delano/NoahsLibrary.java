package delano;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;

public class NoahsLibrary {

	private JFrame frame;
	private JTextField SKU_Input;
	private JTextField bookTitle_Input;
	private JTextField Price_Input;
	private JTextField Quantity_Input;
	private JButton AddBookBtn;
	public File fileLib;
	public Serializer s;
	private static final String FILE_NAME = "Noah's_Library.txt";

	/**
	 * Launch the application.
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
	 * Create the application.
	 */
	public NoahsLibrary() {
		initialize();
		addBook();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		SKU_Input = new JTextField();
		SKU_Input.setBounds(98, 78, 216, 19);
		frame.getContentPane().add(SKU_Input);
		SKU_Input.setColumns(10);

		bookTitle_Input = new JTextField();
		bookTitle_Input.setBounds(98, 146, 216, 19);
		frame.getContentPane().add(bookTitle_Input);
		bookTitle_Input.setColumns(10);

		Price_Input = new JTextField();
		Price_Input.setColumns(10);
		Price_Input.setBounds(98, 217, 216, 19);
		frame.getContentPane().add(Price_Input);

		Quantity_Input = new JTextField();
		Quantity_Input.setColumns(10);
		Quantity_Input.setBounds(98, 290, 216, 19);
		frame.getContentPane().add(Quantity_Input);

		JLabel lblNewLabel = new JLabel("SKU");
		lblNewLabel.setBounds(43, 81, 45, 13);
		frame.getContentPane().add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("Title");
		lblNewLabel_1.setBounds(43, 149, 45, 13);
		frame.getContentPane().add(lblNewLabel_1);

		JLabel lblNewLabel_2 = new JLabel("Price");
		lblNewLabel_2.setBounds(43, 220, 45, 13);
		frame.getContentPane().add(lblNewLabel_2);

		JLabel lblNewLabel_3 = new JLabel("Quantity");
		lblNewLabel_3.setBounds(43, 293, 45, 13);
		frame.getContentPane().add(lblNewLabel_3);

		AddBookBtn = new JButton("New button");
		AddBookBtn.setBounds(160, 366, 85, 21);
		frame.getContentPane().add(AddBookBtn);

		s = new Serializer(FILE_NAME);
		
		/*frame.addWindowListener((WindowListener) new WindowAdapter() {
		    public void windowClosing(WindowEvent e) {
		        s.saveFile();
		    }
		});*/
		
		//fileLib = new Serializer("Noah's_Library.txt");
	}

	private void addBook() {
		// Creates an action listener for the button to be pressed
		AddBookBtn.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				String sku = SKU_Input.getText();
				int SKU = Integer.parseInt(sku);
				String bName = bookTitle_Input.getText().toLowerCase();
				String price = Price_Input.getText();
				double Price = Double.parseDouble(price);
				String quantity = Quantity_Input.getText();
				int Quan = Integer.parseInt(quantity);

				try {
					DefineBook book = new DefineBook();
					book.SKU = SKU;
					book.setNameOfBook(bName);
					book.setPrice(Price);
					book.quantity = Quan;

					s.loadFile();
					s.library.add(book);
					System.out.println(s.library);
					s.saveFile();
					
					
					//fileLib.append(book);
					// FileOutputStream lib = new FileOutputStream("Noah's_Library.txt");
					// ObjectOutputStream out = new ObjectOutputStream(lib);
					// out.writeObject(book);
					// out.flush();
					// closing the stream
					// out.close();
				} catch (Exception e1) {
					System.out.println(e1);
				}

				//System.out.println(fileLib.deserialize());

			}

		});
	}

}
