package sprint1.usecases;

import it.unibo.radarSystem22.domain.interfaces.IDistance;
import it.unibo.radarSystem22.domain.interfaces.IRadarDisplay;
import it.unibo.radarSystem22.domain.utils.ColorsOut;

public class RadarGuiUsecase {

	public static void doUseCase( IRadarDisplay radar, IDistance d ) {
		ColorsOut.out("RadarGuiUsecase | doUseCase d=" + d.getVal(), ColorsOut.ANSI_YELLOW);
		
		if( radar != null ) {
			int v = d.getVal();
			radar.update(""+v, "30");
		}
	}
	
}
