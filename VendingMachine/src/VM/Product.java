package VM;

/**
 * @author avishai
 *
 */
public abstract class Product {
	protected String name;			
	protected double price;			
	protected int numInStock;
	
	/**
	 * a constractor
	 * @param name - the name of the product
	 * @param price - the price of the product
	 * @param numInStock - the number of products in the stock
	 */
	protected Product(String name, double price, int numInStock) {
		this.name = name;
		this.price = price;
		this.numInStock = numInStock;		
	}	

	/**
	 * @return - the name of the product
	 */
	String getName() {
		return name;
	}
	
	/**
	 * to set the name of the product to a given name
	 * @param name - the new name
	 */
	final void setName(String name) {
		this.name = name;
	}
	
	/**
	 * @return - the price of the product
	 */
	double getPrice() {
		return price;
	}
	
	/**
	 * to set a new price for a product
	 * @param price - the new price
	 */
	void setPrice(double price) {
		this.price = price;
	}
	
	/**
	 * @return - how many items of the product are in the stock
	 */
	public int getNumInStock() {
		return numInStock;
	}
	
	/**
	 * to set a new num for a product in the stock
	 * @param numInStock - the new num
	 */
	void setNumInStock(int numInStock) {
		this.numInStock = numInStock;
	}
}