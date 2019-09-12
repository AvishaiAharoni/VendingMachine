package Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.FixMethodOrder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runners.MethodSorters;

import VM.EState;
import VM.Product;
import VM.VendingMachine;
import VM.Writeable;


class Banana extends Product {
	
	protected Banana(String name, double price, int numInStock) {
		super("Banana", price, numInStock);
	}
}

class Apple extends Product {
	
	protected Apple(String name, double price, int numInStock) {
		super("Apple", price, numInStock);
	}
}


class Writer implements Writeable {
	
	@Override
	public void write(String toWrite) {
		System.out.println(toWrite);
	}
}

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class JUnitVending {
	VendingMachine machine;
	Writer writer;
	Map<String, Product> products;
	
	@BeforeEach
	void before() {
		writer = new Writer();
		
		products = new HashMap<String, Product>();
		products.put("apple", new Apple("apple", 3.2, 10));
		products.put("banana", new Banana("banana", 5.8, 1));
		
		machine = new VendingMachine(products, writer);
		assertSame(machine.getCurrentState(), EState.READY);	
	}
	
	@Test
	void begin() {
		System.out.println("start begin");
		
		assertSame(machine.getCurrentState(), EState.READY);
		machine.cancel();
		assertSame(machine.getCurrentState(), EState.READY);
		
		System.out.println("end begin");
	}
	
	@Test 
	void insert() {
		
		System.out.println("start insert");
		
		machine.insertCoins(2.0);
		assertSame(machine.getCurrentState(), EState.COLLECT_COINS);
		assertEquals(machine.getMoneyCounter(), 2.0);

		machine.cancel();
		assertSame(machine.getCurrentState(), EState.READY);
		assertEquals(machine.getMoneyCounter(), 0.0);
		
		System.out.println("end insert");
	}
	
	@Test 
	void cancel() {
		
		System.out.println("start cancel");
		
		machine.insertCoins(2.0);
		machine.cancel();
		assertSame(machine.getCurrentState(), EState.READY);
		assertEquals(machine.getMoneyCounter(), 0.0);
		
		System.out.println("end cancel");
	}
	
	@Test 
	void buy() {
		
		System.out.println("start buy");
		
		machine.insertCoins(10.0);
		
		machine.chooseProduct("banana");

		assertSame(machine.getCurrentState(), EState.TRANSACTION_COMPLETE);
		
		assertEquals(machine.getMoneyCounter(), 0.0);
		
		System.out.println("end buy");
	}
	
	@Test 
	void timerAfterBuy() {
		
		System.out.println("start timerAfterBuy");
		
		machine.insertCoins(10.0);
		
		machine.chooseProduct("apple");

		assertSame(machine.getCurrentState(), EState.TRANSACTION_COMPLETE);
		machine.cancel();
		machine.insertCoins(10.0);
		
		assertSame(machine.getCurrentState(), EState.TRANSACTION_COMPLETE);
		
		try        
		{
		    Thread.sleep(3000);
		} 
		catch(InterruptedException ex) 
		{
		    Thread.currentThread().interrupt();
		}
		
		assertSame(machine.getCurrentState(), EState.READY);
		
		System.out.println("end timerAfterBuy");
	}
	
	@Test 
	void emptyStock() {
		
		System.out.println("start emptyStock");
		
		machine.insertCoins(10.0);
		
		machine.chooseProduct("banana");
		
		try        
		{
		    Thread.sleep(6000);
		} 
		catch(InterruptedException ex) 
		{
		    Thread.currentThread().interrupt();
		}
		
		machine.insertCoins(7.0);
		machine.chooseProduct("banana");
		
		assertSame(machine.getCurrentState(), EState.COLLECT_COINS);
		
		assertEquals(machine.getMoneyCounter(), 7.0);
		
		System.out.println("end emptyStock");
	}
	
	@Test 
	void wrongName() {
		
		System.out.println("start wrongName");
		
		machine.insertCoins(7.0);
		
		machine.chooseProduct("janana");
		
		assertSame(machine.getCurrentState(), EState.COLLECT_COINS);
		assertEquals(machine.getMoneyCounter(), 7.0);
		
		System.out.println("end wrongName");
	}
	
	@Test 
	void notEnoughMoney() {
		
		System.out.println("start notEnoughMoney");
		
		machine.insertCoins(3.0);
		
		machine.chooseProduct("banana");
		
		assertSame(machine.getCurrentState(), EState.COLLECT_COINS);
		assertEquals(machine.getMoneyCounter(), 3.0);
		
		System.out.println("end notEnoughMoney");
	}
	
	

}
