package it.unibo.radarSystem22.domain.concrete;


import java.io.IOException;

import it.unibo.radarSystem22.domain.Interfaces.ILed;
import it.unibo.radarSystem22.domain.models.LedModel;

public class LedConcrete extends LedModel implements ILed {
	private Runtime rt = Runtime.getRuntime();
	
	@Override
	protected void ledActivate(boolean turnOn) {
		try {
			if( turnOn )
				rt.exec("sudo bash led25GpioTurnOn.sh");
			else
				rt.exec("sudo bash led25GpioTurnOff.sh");
		} catch( IOException e) {
			System.out.println("LedConcrete | ERROR " + e.getMessage());
		}
	}

}
