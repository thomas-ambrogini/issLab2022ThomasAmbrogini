package unibo.actor22.local;

import unibo.actor22.Qak22Util;
import unibo.actor22.common.ApplData;
import unibo.actor22.common.ControllerActor;
import unibo.actor22.common.LedActor;
import unibo.actor22.main.TemplateMain;
import unibo.actor22comm.utils.CommUtils;

public class UsingLedAndControllerLocal extends TemplateMain {

	@Override
	protected void configure() {
		new LedActor( ApplData.ledName );
		new ControllerActor( ApplData.controllerName );
	}

	@Override
	protected void execute() {
		Qak22Util.sendAMsg( ApplData.activateCrtl );
	}
	
	public static void main(String [] args) {
		CommUtils.aboutThreads("Before start - ");
		new UsingLedAndControllerLocal().doJob( null, null );
		CommUtils.aboutThreads("Before end - ");
	}

}
