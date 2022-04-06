package it.unibo.radarSystem22.domain;

import java.util.Observable;
import java.util.Observer;

import it.unibo.radarSystem22.domain.interfaces.IDistance;
import it.unibo.radarSystem22.domain.interfaces.IDistanceMeasured;
import it.unibo.radarSystem22.domain.utils.ColorsOut;

public class DistanceMeasured extends Observable implements IDistanceMeasured{
	private IDistance d;
	private static int delta = 2;
	
	@Override
	public int getVal() {
		return d.getVal();
	}

	@Override
	public IDistance getDistance() {
		return d;
	}

	@Override
	public void setVal(int v) {
		
		if(notifyChange(v))
			setChanged();
		
		d = new Distance(v);
		ColorsOut.out("DistanceMeasured setVal="+d + " obsNum=" + countObservers() + " hasChanged=" + hasChanged(), ColorsOut.MAGENTA);
		notifyObservers( d ); 
	}

	@Override
	public void setDistance(IDistance d) {
		
		if(notifyChange(d.getVal()))
			setChanged();
		
		this.d = d;
		ColorsOut.out("DistanceMeasured setDistance="+d + " obsNum=" + countObservers() + " hasChanged=" + hasChanged(), ColorsOut.MAGENTA);
		notifyObservers( d ); 
		
	}
	
	@Override
	public void addObserver(Observer obs) {
		ColorsOut.out("DistanceMeasured addObserver="+obs , ColorsOut.MAGENTA);
		super.addObserver(obs);
		ColorsOut.out("DistanceMeasured addObserver obsNum=" + countObservers(), ColorsOut.RED);
 	}
	
	private boolean notifyChange( int v ) {
		return (d == null) || (Math.abs(v - d.getVal()) > delta);
	}

}
