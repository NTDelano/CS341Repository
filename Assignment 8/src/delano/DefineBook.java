package delano;

import java.io.Serializable;

public class DefineBook implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5453306222592914421L;
	public int SKU;
	public String nameOfBook;
	public double price;
	public int quantity;
/*
	public DefineBook(int sku, String bookTitle, double cost, int inStock) {
		int min = 100000000;
		int max = 999999999;
		// Generate random double value from 50 to 100
		System.out.println("Random value in double from " + min + " to " + max + ":");
		SKU = (int) (Math.random() * (max - min + 1) + min);

	}*/
	
	public String getNameOfBook() {
		return nameOfBook;
	}
	public void setNameOfBook(String book) {
		this.nameOfBook = book;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double cost) {
		this.price = cost;
	}
	
}
