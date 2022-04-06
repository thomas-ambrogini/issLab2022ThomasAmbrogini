package unibo.actor22.local;

 
import it.unibo.kactor.IApplMessage;
import it.unibo.radarSystem22.domain.utils.BasicUtils;
import it.unibo.radarSystem22.domain.utils.DomainSystemConfig;
import unibo.actor22.Qak22Util;
import unibo.actor22.common.ApplData;
import unibo.actor22.common.ControllerActor;
import unibo.actor22.common.RadarActor;
import unibo.actor22.common.SonarActor;
import unibo.actor22comm.utils.ColorsOut;
import unibo.actor22comm.utils.CommSystemConfig;
import unibo.actor22comm.utils.CommUtils; 
 
/*
 * Sistema che usa led come attore locale
 */
public class UsingSonarControllerAndRadarOnPc {
	//private SonarActor sonar;
	//private ControllerActor controller;
	private IApplMessage isActive ;
 
	public void doJob() {
		ColorsOut.outappl("UsingSonarAndControllerOnPc | Start", ColorsOut.BLUE);
		configure();
		BasicUtils.aboutThreads("Before execute - ");
		//BasicUtils.waitTheUser();
		execute();
		terminate();
	}
	
	protected void configure() {
		DomainSystemConfig.simulation   = true;			
		DomainSystemConfig.ledGui       = true;			
		DomainSystemConfig.tracing      = true;			
		
		CommSystemConfig.tracing        = true;
		
 		new SonarActor( ApplData.sonarName );
 		new ControllerActor( ApplData.controllerName );
 		new RadarActor( ApplData.radarName );
		
 		//isActive = CommUtils.buildRequest("main",  "ask", ApplData.reqIsActive, ApplData.sonarName); 
   	}
	
	//Accende-spegne il Led due volte
	protected void execute() {
		Qak22Util.sendAMsg( ApplData.activateCrtl );
		for(int i = 0; i < 30; i++) {
			Qak22Util.sendAMsg( ApplData.updateRadarCtrl );
			CommUtils.delay(DomainSystemConfig.sonarDelay);
		}
		
		//ColorsOut.outappl("UsingSonarAndControllerOnPc | execute", ColorsOut.MAGENTA);
		
		/*
		Qak22Util.sendAMsg( ApplData.activateSonar );
		Qak22Util.sendAMsg( isActive );
		CommUtils.delay(200);
		Qak22Util.sendAMsg( ApplData.deactivateSonar );
		Qak22Util.sendAMsg( isActive );
		CommUtils.delay(200);
		*/
 	} 

	public void terminate() {
		BasicUtils.aboutThreads("Before exit - ");
		CommUtils.delay(3000);
		System.exit(0);
	}
	
	public static void main( String[] args) {
		BasicUtils.aboutThreads("Before start - ");
		new UsingSonarControllerAndRadarOnPc().doJob();
 		BasicUtils.aboutThreads("Before end - ");
	}

}