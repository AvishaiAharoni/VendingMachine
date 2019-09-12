package Test;

import VM.Writeable;

public class Output implements Writeable {

	@Override
	public void write(String toWrite) {
		System.out.println(toWrite);
		
	}

	
}
