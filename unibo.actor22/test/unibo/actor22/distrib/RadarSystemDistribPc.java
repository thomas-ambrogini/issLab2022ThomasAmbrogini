package unibo.actor22.distrib;

import it.unibo.radarSystem22.domain.utils.BasicUtils;
import unibo.actor22.Qak22Context;
import unibo.actor22.Qak22Util;
import unibo.actor22.common.ApplData;
import unibo.actor22.common.ControllerActor;
import unibo.actor22.common.RadarActor;
import unibo.actor22.main.TemplateMain;
import unibo.actor22comm.utils.ColorsOut;

public class RadarSystemDistribPc extends TemplateMain{

	@Override
	protected void configure() {
		String raspHostAddr      = "localhost";
		
		Qak22Context.setActorAsRemote( 
				ApplData.ledName, ""+ApplData.ctxPort, raspHostAddr, ApplData.protocol);
		
		Qak22Context.setActorAsRemote( 
				ApplData.sonarName, ""+ApplData.ctxPort, raspHostAddr, ApplData.protocol);
		
		new ControllerActor( ApplData.controllerName );
		new RadarActor( ApplData.radarName );
		
	}

	@Override
	protected void execute() {
		ColorsOut.outappl("ControllerOnPcUsingLedRemote | execute", ColorsOut.MAGENTA);
  		Qak22Util.sendAMsg( ApplData.activateCrtl );
	}
	
	public static void main( String[] args ) {
		BasicUtils.aboutThreads("Before start - ");
		new RadarSystemDistribPc().doJob(null, null);
 		BasicUtils.aboutThreads("Before end - ");
	}

}
