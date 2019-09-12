package VM;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @author avishai
 *
 */
public class VendingMachine {				
	private EState currentState;			
	private double moneyCounter;			
	private Map<String, Product> productList;
	private Writeable writer;
	
	/**
	 * constructor to the vending machine
	 */
	public VendingMachine(Map<String, Product> productList, Writeable writer) {
		this.currentState = EState.READY;
		this.productList = productList;
		this.writer = writer;
	}
	
	/**
	 * to the chooseProduct event.
	 * if the state was changed from COLLECT_COINS to TRANSACTION_COMPLETE -
	 * stays until the product and the change are delivered (2 seconds).
	 * @param productName - the name of the product in the VM
	 */
	public void chooseProduct(String productName) {
		EState oldState = this.getCurrentState();
		final int delay = 2000;
		
		setCurrentState(this.currentState.Choose(this, productName));
		
		// to stay some time after the buying succeed
		if ((oldState == EState.COLLECT_COINS) && (this.getCurrentState() == EState.TRANSACTION_COMPLETE)) {
	  		Timer timer = new Timer();
	  		
	        timer.schedule(new TimerTask() {
				
				@Override
				public void run() {
					setCurrentState(EState.READY);
					
					return;
		    	}
				
		    } , delay);			// 2 seconds
		}
	}
	
	/**
	 * to the insertCoins event
	 * @param coins - the value of the coins to insert
	 */
	public void insertCoins(double coins) {
		setCurrentState(this.currentState.insertCoin(this, coins));
	}
	
	/**
	 * to the cancel event
	 */
	public void cancel() {
		setCurrentState(this.currentState.Cancel(this));
	}
	
	/**
	 * to get the current state
	 */
	public EState getCurrentState() {
		return this.currentState;
	}

	/**
	 * to set the state to a given state
	 * @param newtState - the given state
	 */
	void setCurrentState(EState newtState) {
		this.currentState = newtState;
	}

	/**
	 * to get the value of the money in the VM
	 */
	public double getMoneyCounter() {
		return this.moneyCounter;
	}

	/**
	 * to set the moneyCounter to a given newMoneyCounter
	 * @param moneyCounter
	 */
	void setMoneyCounter(double newMoneyCounter) {
		this.moneyCounter = newMoneyCounter;
	}

	/**
	 * to get a list of the products in the VM
	 * @return - a Map of the products
	 */
	public Map<String, Product> getProductList() {
		Map<String, Product> products = new HashMap<String, Product>();
		products = this.productList;
		
		return products;
	}

	/**
	 * to get the print option from the {@link Writeable} interface
	 * @return
	 */
	Writeable getWriter() {
		return this.writer;
	}
}							