package it.unibo.radarSystem22.domain.Interfaces;

public interface ISonar {

	public void activate();
	
	public void deactivate();
	
	public IDistance getDistance();
	
	public boolean isActive();
}
