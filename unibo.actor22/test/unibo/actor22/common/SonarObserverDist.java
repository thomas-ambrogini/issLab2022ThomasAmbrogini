package unibo.actor22.common;

import java.util.Observable;
import java.util.Observer;

import it.unibo.kactor.IApplMessage;
import it.unibo.radarSystem22.domain.interfaces.IDistance;
import it.unibo.radarSystem22.domain.utils.ColorsOut;
import unibo.actor22.Qak22Util;

public class SonarObserverDist implements Observer{
	private IDistance curVal;
	private String actorName;
	
	public SonarObserverDist(String actorName) {
		this.actorName = actorName;
	}

	@Override
	public void update(Observable arg0, Object val) {
		ColorsOut.outappl("update", ColorsOut.YELLOW);
		this.curVal = (IDistance) val;
		IApplMessage msg = Qak22Util.buildDispatch(ApplData.sonarName, ApplData.distanceUpdate, curVal.toString(), actorName);
		Qak22Util.sendAMsg( msg  );
		ColorsOut.out("Received new value: " + curVal, ColorsOut.MAGENTA);
	}
	
	public int getVal( ) {
		return curVal.getVal();
	}
}
