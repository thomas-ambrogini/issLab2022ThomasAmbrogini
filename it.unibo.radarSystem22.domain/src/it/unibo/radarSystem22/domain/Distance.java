package it.unibo.radarSystem22.domain;

import it.unibo.radarSystem22.domain.Interfaces.IDistance;

public class Distance implements IDistance {
	private int val;
	
	public Distance(int val) {
		this.val = val;s
	}

	@Override
	public int getVal() {
		return val;
	}
	
	@Override
	public String toString() {
		return ""+getVal();
	}

}
