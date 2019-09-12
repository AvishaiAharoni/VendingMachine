package VM;

/**
 * @author avishai
 * 
 * interface for the EState, that has default methods for the events.
 * the default is to return the current state (until overrite).
 *
 */
public interface EStateInterface {
	default EState insertCoin(VendingMachine Machine, double insertedMoney) {
		return Machine.getCurrentState();
	}
	
	default EState Choose(VendingMachine Machine, String product) {
		return Machine.getCurrentState();
	}
	
	default EState Cancel(VendingMachine Machine) {
		return Machine.getCurrentState();
	}

}
