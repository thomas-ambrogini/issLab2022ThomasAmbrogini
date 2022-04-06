package it.unibo.radarSystem22.domain.interfaces;

import java.util.Observer;

public interface IDistanceMeasured extends IDistance {
	public void setVal( int v );
	public void setDistance( IDistance d);
	public IDistance getDistance( );
	public void addObserver( Observer obs );
	public void deleteObserver( Observer obs );
}
