package it.unibo.radarSystem22.domain.mock;

import it.unibo.radarSystem22.domain.Interfaces.ILed;

public class LedMock implements ILed{
	private boolean state;
	
	public LedMock() {
		this.state = false;
	}
	
	@Override
	public void turnOn() {
		this.state = true;
		
	}

	@Override
	public void turnOff() {
		this.state = false;
		
	}

	@Override
	public boolean getState() {
		return this.state;
	}

	
}
