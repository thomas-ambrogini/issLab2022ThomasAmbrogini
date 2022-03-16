package it.unibo.radarSystem22.domain;

import it.unibo.radarSystem22.domain.Interfaces.ILed;
import it.unibo.radarSystem22.domain.Interfaces.IRadarDisplay;
import it.unibo.radarSystem22.domain.Interfaces.ISonar;
import it.unibo.radarSystem22.domain.utils.ColorsOut;
import it.unibo.radarSystem22.domain.utils.DomainSystemConfig;

public class DeviceFactory {

    public static ILed createLed() {
            ColorsOut.out("DeviceFactory | createLed simulated="+DomainSystemConfig.simulation, ColorsOut.GREEN);
            if( DomainSystemConfig.simulation)  {
                    return LedModel.createLedMock();
            }else {
                    return LedModel.createLedConcrete();
            }
    }
    public static ISonar createSonar(boolean observable) {
            ColorsOut.out("DeviceFactory | createSonar simulated="+DomainSystemConfig.simulation, ColorsOut.GREEN);
//          if( observable ) return createSonarObservable();
//          else 
                    return createSonar();
    }

    public static ISonar createSonar() {
            ColorsOut.out("DeviceFactory | createSonar simulated="+DomainSystemConfig.simulation);
            if( DomainSystemConfig.simulation)  {
                    return SonarModel.createSonarMock();
            }else {
                    return SonarModel.createSonarConcrete();
            }
    }
//  public static ISonarObservable createSonarObservable() {
//          ColorsOut.out("DeviceFactory | createSonarObservable simulated="+DomainSystemConfig.simulation);
//          if( DomainSystemConfig.simulation)  {
//                  return new SonarMockObservable();
//          }else { 
//                  return new SonarConcreteObservable();
//          }       
//  }

    //We do not have mock for RadarGui
    public static IRadarDisplay createRadarGui() {
            return RadarDisplay.getRadarDisplay();
    }

}
