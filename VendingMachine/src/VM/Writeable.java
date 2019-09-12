package VM;

/**
 * @author avishai
 * 
 * An interface to change print options
 *
 */
@FunctionalInterface
public interface Writeable {
	void write(String toWrite);
}	

