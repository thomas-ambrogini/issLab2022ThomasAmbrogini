package it.unibo.radarSystem22.domain.mock;

import it.unibo.radarSystem22.domain.Interfaces.IDistance;
import it.unibo.radarSystem22.domain.Interfaces.ISonar;

public class SonarMock implements ISonar{
	private boolean active;
	
	public SonarMock() {
		this.active = false;
	}
	
	@Override
	public void activate() {
		this.active = true;
	}

	@Override
	public void deactivate() {
		this.active = false;
	}

	@Override
	public IDistance getDistance() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isActive() {
		return this.active;
	}

}
