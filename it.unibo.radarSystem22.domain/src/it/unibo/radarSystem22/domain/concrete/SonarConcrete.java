package it.unibo.radarSystem22.domain.concrete;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import it.unibo.radarSystem22.domain.Distance;
import it.unibo.radarSystem22.domain.interfaces.ISonar;
import it.unibo.radarSystem22.domain.models.SonarModel;
import it.unibo.radarSystem22.domain.utils.ColorsOut;
import it.unibo.radarSystem22.domain.utils.DomainSystemConfig;

public class SonarConcrete extends SonarModel implements ISonar {
	private BufferedReader reader;
	private Process sonarProcess;
	
	@Override
	protected void sonarSetUp() { //called by SonarModel constructor
		curSonarVal = new Distance(90);
	}
	
	@Override
	public void activate() {
		ColorsOut.out("SonarConcrete | activate");
		if( sonarProcess == null ) {
			try {
				sonarProcess = Runtime.getRuntime().exec("sudo ./SonarAlone");
				reader       = new BufferedReader( new InputStreamReader(sonarProcess.getInputStream()));
			}catch(Exception e) {
				ColorsOut.outerr("SonarConcrete | sonarSetUp ERROR " + e.getMessage());
			}
		}
		super.activate();
	}
	
	@Override
	protected void sonarProduce() {
		try {
			String data = reader.readLine();
			if ( data == null)
				return ;
			int distanceMeasured = Integer.parseInt(data);
			ColorsOut.out("SonarConcrete | distance=" + distanceMeasured);
			int lastSonarVal = curSonarVal.getVal();
			if ( lastSonarVal != distanceMeasured && distanceMeasured < DomainSystemConfig.sonarDistanceMax) {
				updateDistance(distanceMeasured);
			}
		}catch( NumberFormatException nfe) {
			ColorsOut.outerr("SonarConcrete | " + nfe.getMessage());
		}catch( IOException ioe) {
			ColorsOut.outerr("SonarConcrete | " + ioe.getMessage());
		}
	}
	
	@Override
	public void deactivate() {
		ColorsOut.out("SonarConcrete | deactivate", ColorsOut.GREEN);
		super.deactivate();
		curSonarVal = new Distance(90);
		if (sonarProcess != null ) {
			sonarProcess.destroy();
			sonarProcess = null;
		}
		
	}
}
