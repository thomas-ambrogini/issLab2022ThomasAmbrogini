package it.unibo.radarSystem22.domain.mock;

import it.unibo.radarSystem22.domain.Distance;
import it.unibo.radarSystem22.domain.Interfaces.IDistance;
import it.unibo.radarSystem22.domain.Interfaces.ISonar;
import it.unibo.radarSystem22.domain.utils.BasicUtils;

public class SonarMock implements ISonar{
	private boolean active;
	private IDistance distance;
	
	public SonarMock() {
		this.active = false;
		this.distance = new Distance(90);
	}
	
	@Override
	public void activate() {
		this.active = true;
		
		new Thread() {
			public void run() {
				while(active) {
					distance = new Distance(getDistance().getVal() - 1);
					BasicUtils.delay(250);
					if(distance.getVal() == 0) {
						deactivate();
					}
				}
			}
		}.start();
	}

	@Override
	public void deactivate() {
		this.active = false;
	}

	@Override
	public IDistance getDistance() {
		return distance;
	}

	@Override
	public boolean isActive() {
		return this.active;
	}

}
