package it.unibo.radarSystem22.sprint1;

import it.unibo.radarSystem22.domain.DeviceFactory;
import it.unibo.radarSystem22.domain.interfaces.IDistance;
import it.unibo.radarSystem22.domain.interfaces.ILed;
import it.unibo.radarSystem22.domain.interfaces.IRadarDisplay;
import it.unibo.radarSystem22.domain.interfaces.ISonar;
import it.unibo.radarSystem22.domain.utils.BasicUtils;
import it.unibo.radarSystem22.domain.utils.ColorsOut;
import it.unibo.radarSystem22.domain.utils.DomainSystemConfig;
import it.unibo.radarSystem22.sprint1.usecases.LedAlarmUsecase;
import it.unibo.radarSystem22.sprint1.usecases.RadarGuiUsecase;

public class Controller {
	private ILed led;
	private ISonar sonar;
	private IRadarDisplay radar;
	private ActionFunction endFun;
	
	public static Controller create( ILed led, ISonar sonar, IRadarDisplay radar ) {
		return new Controller( led, sonar, radar );
	}
	
	public static Controller create( ILed led, ISonar sonar) {
		return new Controller( led, sonar, DeviceFactory.createRadarGui());
	}
	
	private Controller( ILed led, ISonar sonar, IRadarDisplay radar ) {
		this.led 	= led;
		this.sonar	= sonar;
		this.radar	= radar;
	}
	
	public void start( ActionFunction endFun, int limit ) {
		this.endFun = endFun;
		ColorsOut.outappl("Controller | start with endFun=" + endFun, ColorsOut.GREEN);
		sonar.activate();
		activate( limit );
	}
	
	//Il controller riceve dati dal sonar e attiva gli use cases
	protected void activate( int limit ) {
		new Thread() {
			public void run() {
				BasicUtils.aboutThreads("Controller activated | ");
				try {
					boolean sonarActive = sonar.isActive();
					if( sonarActive ) {
						for( int i = 1; i <= limit; i++ ) {
							IDistance d = sonar.getDistance();
							ColorsOut.outappl("Controller | d" + d + " i=" + i, ColorsOut.GREEN);
							
							if( radar != null)
								RadarGuiUsecase.doUseCase(radar, d);
							
							LedAlarmUsecase.doUseCase( led, d );
							BasicUtils.delay(DomainSystemConfig.sonarDelay);
						}
					}
					ColorsOut.outappl("Controller | BYE", ColorsOut.BLUE);
					sonar.deactivate();
					endFun.run("Controller | BYE");
				}catch( Exception e) {
					ColorsOut.outerr("Controller | ERROR" + e.getMessage());
				}
			}
		}.start();
	}
	
}
