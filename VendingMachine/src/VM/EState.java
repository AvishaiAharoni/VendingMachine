package VM;

import java.math.BigDecimal;

/**
 * @author avishai
 *
 */
public enum EState implements EStateInterface{
	READY {
		
		@Override
		public EState insertCoin(VendingMachine Machine, double insertedMoney) {
			Machine.setMoneyCounter(insertedMoney);
			
			return COLLECT_COINS;	
		}		
	},			
		
	COLLECT_COINS {			
			
		@Override
		public EState insertCoin(VendingMachine Machine, double insertedMoney) {
			Machine.setMoneyCounter(Machine.getMoneyCounter() + insertedMoney);

			return COLLECT_COINS;	
		}		
				
		/* 
		 * to choose a product from the machine.
		 * if the produce doesn't exist - sending an error massage.
		 * if the product was finished - sending a massage.
		 * if he didn't insert enough money - sending a massage with the rest of the money.
		 * 
		 * if all is right - calling a function to deliver the product and the change.
		 * 
		 * @return value - if succeed - changes the state to TRANSACTION_COMPLETE,
		 * 				   else - stays in COLLECT_COINS.
		 */
		@Override		
		public EState Choose(VendingMachine Machine, String product) {
			Product prod = Machine.getProductList().get(product);
			
			// the product is match
			if (prod != null) {
				// there is at least one product in the stock
				if (prod.getNumInStock() > 0) {
					// there is enough money to buy
					if (prod.getPrice() <= Machine.getMoneyCounter()) {
						
						deliverProduct(Machine, prod);
						
						return TRANSACTION_COMPLETE;
					}
					else {
						BigDecimal bd = new BigDecimal(prod.getPrice() - Machine.getMoneyCounter());
						bd = bd.setScale(1, BigDecimal.ROUND_HALF_DOWN);
						
						Machine.getWriter().write("no enough money! you need " + bd + " coins to buy this product");
					}
				}
				else {
					Machine.getWriter().write("product is out of stock.... sorry");
				}
			}
			else
			{
				Machine.getWriter().write("wrong choice... try again");
			}

			return COLLECT_COINS;
	}
				
		/* 
		 * returns to the user its change, and changes the state of the machine to READY.
		 */
		@Override		
		public EState Cancel(VendingMachine Machine) {
			Machine.getWriter().write("get your money back: " + Machine.getMoneyCounter());
			Machine.setMoneyCounter(0);
			
			return READY;	
		}
	},
			
	TRANSACTION_COMPLETE {
		
		/*
		 *  can't insert money when the machine in this state
		 */
		@Override
		public EState insertCoin(VendingMachine Machine, double insertedMoney) {
			Machine.getWriter().write("plese wait... get your money back: " + insertedMoney);

			return TRANSACTION_COMPLETE;
		}
	};
	
	/**
	 * @param Machine
	 * @param prod - the product to buy from the machine
	 */
	private static void deliverProduct(VendingMachine Machine, Product prod) {
		Machine.getWriter().write("thanks for buying " + prod.getName());
		prod.setNumInStock(prod.getNumInStock() - 1);
		
		BigDecimal bd = new BigDecimal(Machine.getMoneyCounter() - prod.getPrice());
		bd = bd.setScale(1, BigDecimal.ROUND_HALF_DOWN);
		
		Machine.setMoneyCounter(bd.doubleValue());

		// there is money to return as change
		if (Machine.getMoneyCounter() > 0) {
			Machine.getWriter().write("get your change: " + Machine.getMoneyCounter());
			Machine.setMoneyCounter(0);
		}
	}
}