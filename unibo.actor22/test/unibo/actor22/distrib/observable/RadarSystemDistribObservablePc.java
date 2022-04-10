package unibo.actor22.distrib.observable;

import it.unibo.kactor.IApplMessage;
import it.unibo.radarSystem22.domain.utils.BasicUtils;
import unibo.actor22.Qak22Context;
import unibo.actor22.Qak22Util;
import unibo.actor22.common.ApplData;
import unibo.actor22.common.ControllerActor;
import unibo.actor22.common.RadarActor;
import unibo.actor22.main.TemplateMain;
import unibo.actor22comm.context.EnablerContextForActors;
import unibo.actor22comm.utils.ColorsOut;
import unibo.actor22comm.utils.CommSystemConfig;

public class RadarSystemDistribObservablePc extends TemplateMain {
	private EnablerContextForActors ctx;
	
	@Override
	protected void configure() {
		String raspHostAddr      	 = "raspThomasAmbrogini.local";
		CommSystemConfig.hostAddress = "192.168.1.7";
		
		ctx = new EnablerContextForActors( "ctx", ApplData.ctxPortPc, ApplData.protocol);
		
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
  		ctx.activate();
  		IApplMessage activateController = Qak22Util.buildDispatch(getName(), ApplData.cmdActivate, ApplData.cmdActivate, ApplData.controllerName);
		Qak22Util.sendAMsg( activateController );
  		
	}
	
	public static void main( String[] args ) {
		BasicUtils.aboutThreads("Before start - ");
		new RadarSystemDistribObservablePc().doJob(null, null);
 		BasicUtils.aboutThreads("Before end - ");
	}

}
