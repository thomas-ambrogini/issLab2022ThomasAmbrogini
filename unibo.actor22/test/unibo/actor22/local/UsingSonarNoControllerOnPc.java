package unibo.actor22.local;

 
import it.unibo.kactor.IApplMessage;
import it.unibo.radarSystem22.domain.utils.BasicUtils;
import it.unibo.radarSystem22.domain.utils.DomainSystemConfig;
import unibo.actor22.Qak22Util;
import unibo.actor22.common.ApplData;
import unibo.actor22.common.LedActor;
import unibo.actor22.common.SonarActor;
import unibo.actor22comm.utils.ColorsOut;
import unibo.actor22comm.utils.CommSystemConfig;
import unibo.actor22comm.utils.CommUtils; 
 
/*
 * Sistema che usa led come attore locale
 */
public class UsingSonarNoControllerOnPc {
	private SonarActor sonar;
	private IApplMessage isActive ;
 
	public void doJob() {
		ColorsOut.outappl("UsingSonarNoControllerOnPc | Start", ColorsOut.BLUE);
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
		
		CommSystemConfig.tracing        = false;
		
 		sonar = new SonarActor( ApplData.sonarName );
		isActive = CommUtils.buildRequest("main",  "ask", ApplData.reqIsActive, ApplData.sonarName); 
   	}
	
	//Accende-spegne il Led due volte
	protected void execute() {
		ColorsOut.outappl("UsingSonarNoControllerOnPc | execute", ColorsOut.MAGENTA);
		Qak22Util.sendAMsg( ApplData.activateSonar );
		Qak22Util.sendAMsg( isActive );
		CommUtils.delay(200);
		Qak22Util.sendAMsg( ApplData.deactivateSonar );
		Qak22Util.sendAMsg( isActive );
		CommUtils.delay(200);
 	} 

	public void terminate() {
		BasicUtils.aboutThreads("Before exit - ");
		System.exit(0);
	}
	
	public static void main( String[] args) {
		BasicUtils.aboutThreads("Before start - ");
		new UsingSonarNoControllerOnPc().doJob();
 		BasicUtils.aboutThreads("Before end - ");
	}

}