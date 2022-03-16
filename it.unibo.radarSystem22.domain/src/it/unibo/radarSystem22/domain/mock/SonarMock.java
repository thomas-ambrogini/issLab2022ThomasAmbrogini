package it.unibo.radarSystem22.domain.mock;

import it.unibo.radarSystem22.domain.Distance;
import it.unibo.radarSystem22.domain.interfaces.ISonar;
import it.unibo.radarSystem22.domain.models.SonarModel;
import it.unibo.radarSystem22.domain.utils.BasicUtils;
import it.unibo.radarSystem22.domain.utils.ColorsOut;
import it.unibo.radarSystem22.domain.utils.DomainSystemConfig;

public class SonarMock extends SonarModel implements ISonar{
	private int delta = 1;
	
	@Override
	protected void sonarSetUp() {
		curSonarVal = new Distance(90);
		ColorsOut.out("SonarMock | sonarSetUp distance="+curSonarVal);
	}

	@Override
	protected void sonarProduce() {
		if( DomainSystemConfig.testing ) {
			updateDistance( DomainSystemConfig.testingDistance );
		}else {
			int distance = curSonarVal.getVal() - delta;
			updateDistance( distance );
			active = ( distance >= 0);
		}
		BasicUtils.delay(DomainSystemConfig.sonarDelay);
	}

}
