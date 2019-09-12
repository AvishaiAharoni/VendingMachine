package Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import VM.EState;
import VM.Product;
import VM.StandardProduct;
import VM.VendingMachine;

class VMTest {
	static VendingMachine vm;
	
	@BeforeEach
	private void init() {
		HashMap<String, Product> prod = new HashMap<>();
		
		StandardProduct bamba = new StandardProduct("bamba", 4.9, 4);
		StandardProduct bisli = new StandardProduct("bisli", 5.3, 7);
		StandardProduct oreo = new StandardProduct("oreo", 7, 2);
		StandardProduct baloons = new StandardProduct("baloons", 3, 10);
		
		prod.put("bamba", bamba);
		prod.put("bisli", bisli);
		prod.put("oreo", oreo);
		prod.put("baloons", baloons);
		
		Output write = new Output();
		
		vm = new VendingMachine(prod, write);
	}
	
	@DisplayName("start state")
	@Test
	void getStratStatesTest() {
		System.out.println("start test");
		assertEquals(EState.READY, vm.getCurrentState());
		assertEquals(vm.getMoneyCounter(), 0);
		assertEquals(vm.getProductList().get("oreo").getNumInStock(), 2);
		assertNotEquals(vm.getProductList().get("ballons"), 4);
		assertEquals(vm.getProductList().get("apple"), null);
		System.out.println();
	}
	
	@DisplayName("insert and cancell")
	@Test
	void getInsetrtCoinsAndCancellTest() {
		System.out.println("insert and cancell test");
		vm.insertCoins(4);
		assertEquals(EState.COLLECT_COINS, vm.getCurrentState());
		assertEquals(vm.getMoneyCounter(), 4);
		
		vm.cancel();
		assertEquals(EState.READY, vm.getCurrentState());
		assertEquals(vm.getMoneyCounter(), 0);
		System.out.println();
	}
	
	@DisplayName("insert and buy")
	@Test
	void getInsetrtCoinsAndBuyTest() {
		System.out.println("insert and buy");
		vm.insertCoins(1.2);
		assertEquals(EState.COLLECT_COINS, vm.getCurrentState());
		assertEquals(vm.getMoneyCounter(), 1.2);
		
		vm.insertCoins(1.2);
		assertEquals(vm.getMoneyCounter(), 2.4);
		
		vm.chooseProduct("ice-cream");

		vm.chooseProduct("baloons");
		assertEquals(EState.COLLECT_COINS, vm.getCurrentState());

		vm.insertCoins(0.6);
		assertEquals(vm.getMoneyCounter(), 3);
		
		vm.chooseProduct("baloons");
		assertEquals(vm.getMoneyCounter(), 0);
		assertEquals(EState.TRANSACTION_COMPLETE, vm.getCurrentState());
		System.out.println();
	}

	@DisplayName("get change")
	@Test
	void getChangeTest() {
		System.out.println("get change");
		vm.insertCoins(7.2);
		assertEquals(vm.getMoneyCounter(), 7.2);
		
		vm.chooseProduct("baloons");
		assertEquals(vm.getMoneyCounter(), 0);
		assertEquals(EState.TRANSACTION_COMPLETE, vm.getCurrentState());
		System.out.println();
	}
	
	@DisplayName("insert and buy all")
	@Test
	void getInsetrtCoinsAndBuyAllTest() throws InterruptedException {
		
		System.out.println("insert and buy all");
		vm.insertCoins(7.2);
		assertEquals(vm.getMoneyCounter(), 7.2);
		vm.chooseProduct("oreo");
		assertEquals(vm.getProductList().get("oreo").getNumInStock(), 1);
		assertEquals(vm.getMoneyCounter(), 0);
		
		vm.insertCoins(7);
		vm.chooseProduct("oreo");
		assertEquals(vm.getProductList().get("oreo").getNumInStock(), 1);
		
		TimeUnit.SECONDS.sleep(5);
		vm.insertCoins(7);
		vm.chooseProduct("oreo");
		assertEquals(vm.getProductList().get("oreo").getNumInStock(), 0);

		TimeUnit.SECONDS.sleep(5);
		vm.insertCoins(8.3);
		vm.chooseProduct("oreo");
		
		System.out.println();
	}
}
















