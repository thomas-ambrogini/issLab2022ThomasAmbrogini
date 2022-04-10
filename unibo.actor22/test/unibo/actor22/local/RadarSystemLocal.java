package unibo.actor22.local;

 

import it.unibo.radarSystem22.domain.utils.BasicUtils;
import unibo.actor22.Qak22Util;
import unibo.actor22.common.ApplData;
import unibo.actor22.common.ControllerActor;
import unibo.actor22.common.LedActor;
import unibo.actor22.common.SonarActor;
import unibo.actor22.main.TemplateMain;


/*
 * Sistema che usa led come attore locale
 */
public class RadarSystemLocal extends TemplateMain{
	
	@Override
	protected void configure() {
		new LedActor( ApplData.ledName );
		new SonarActor( ApplData.sonarName );
		new ControllerActor( ApplData.controllerName );
	}
	
	protected void execute() {
		Qak22Util.sendAMsg( ApplData.activateCrtl );
 	} 
	
	public static void main( String[] args) {
		BasicUtils.aboutThreads("Before start - ");
		new RadarSystemLocal().doJob( null, null );
 		BasicUtils.aboutThreads("Before end - ");
	}

}