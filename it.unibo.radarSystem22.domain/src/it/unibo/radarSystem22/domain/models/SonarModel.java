package it.unibo.radarSystem22.domain.models;

import it.unibo.radarSystem22.domain.Distance;
import it.unibo.radarSystem22.domain.concrete.SonarConcrete;
import it.unibo.radarSystem22.domain.interfaces.IDistance;
import it.unibo.radarSystem22.domain.interfaces.ISonar;
import it.unibo.radarSystem22.domain.mock.SonarMock;
import it.unibo.radarSystem22.domain.utils.ColorsOut;
import it.unibo.radarSystem22.domain.utils.DomainSystemConfig;

public abstract class SonarModel implements ISonar {
	protected IDistance curSonarVal = new Distance(90);
	protected boolean active = false;
	
	public static ISonar create() {
		if( DomainSystemConfig.simulation )
			return createSonarMock();
		else
			return createSonarConcrete();
	}
	
	public static ISonar createSonarMock() {
		ColorsOut.out("createSonarMock", ColorsOut.BLUE);
		return new SonarMock();
	}
	
	public static ISonar createSonarConcrete() {
		ColorsOut.out("createSonarConcrete", ColorsOut.BLUE);
		return new SonarConcrete();
	}
	
	protected SonarModel() {
		ColorsOut.out("SonarModel | callign (specialized) sonarSetUp", ColorsOut.BLUE);
		sonarSetUp();
	}
	
	protected void updateDistance( int distance) {
		curSonarVal = new Distance( distance );
		ColorsOut.out("SonarModel | updateistance" + distance, ColorsOut.BLUE);
	}
	
	protected abstract void sonarSetUp();
	protected abstract void sonarProduce();
	
	@Override
	public void activate() {
		curSonarVal = new Distance(90);
		ColorsOut.out("SonarModel | activate");
		active = true;
		new Thread() {
			public void run() {
				while ( active ) {
					sonarProduce();
				}
				ColorsOut.out("SonarModel | ENDS");
			}
		}.start();
	}

	@Override
	public void deactivate() {
		ColorsOut.out("SonarModel | deactivate", ColorsOut.BgYellow);
		active = false;
	}

	@Override
	public IDistance getDistance() {
		return curSonarVal;
	}

	@Override
	public boolean isActive() {
		return active;
	}

	
}
