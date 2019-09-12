package VM;

/**
 * @author avishai
 * A class for a product
 *
 */
public class StandardProduct extends Product{

	/**
	 * @param name - the name of the product
	 * @param price - the price of the product
	 * @param numInStock - the number of products in the stock
	 */
	public StandardProduct(String name, double price, int numInStock) {
		super(name, price, numInStock);
	}

}
