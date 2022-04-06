package it.unibo.radarSystem22.domain;

import java.util.Observable;
import java.util.Observer;

import it.unibo.radarSystem22.domain.interfaces.IDistance;
import it.unibo.radarSystem22.domain.utils.ColorsOut;


public class SonarObserver implements Observer{
	private IDistance curVal;

	@Override
	public void update(Observable arg0, Object val) {
		this.curVal = (IDistance) val;
		ColorsOut.out("Received new value: " + curVal, ColorsOut.MAGENTA);
	}
	
	public int getVal( ) {
		return curVal.getVal();
	}

}
