package it.unibo.radarSystem22.domain;

import it.unibo.radarSystem22.domain.concrete.RadarDisplay;
import it.unibo.radarSystem22.domain.interfaces.ILed;
import it.unibo.radarSystem22.domain.interfaces.IRadarDisplay;
import it.unibo.radarSystem22.domain.interfaces.ISonar;
import it.unibo.radarSystem22.domain.interfaces.ISonarObservable;
import it.unibo.radarSystem22.domain.models.LedModel;
import it.unibo.radarSystem22.domain.models.SonarModel;
import it.unibo.radarSystem22.domain.utils.ColorsOut;
import it.unibo.radarSystem22.domain.utils.DomainSystemConfig;

public class DeviceFactory {

	public static ILed createLed() {
		ColorsOut.out("DeviceFactory | createLed simulated="+DomainSystemConfig.simulation, ColorsOut.GREEN);
		return LedModel.create();
    }

	public static ISonar createSonar() {
	    ColorsOut.out("DeviceFactory | createSonar simulated="+DomainSystemConfig.simulation);
	    return SonarModel.create();
	}
	
	public static ISonarObservable createSonarObservable() {
		ColorsOut.out("DeviceFactory | createSonarObservable simulated="+DomainSystemConfig.simulation, ColorsOut.GREEN);
		return (ISonarObservable) SonarModel.create();
	}

	//We do not have mock for RadarGui
	public static IRadarDisplay createRadarGui() {
	    return RadarDisplay.getRadarDisplay();
	}

}
