package unibo.actor22.distrib;

import it.unibo.radarSystem22.domain.utils.BasicUtils;
import unibo.actor22.common.ApplData;
import unibo.actor22.common.LedActor;
import unibo.actor22.common.SonarActor;
import unibo.actor22.main.TemplateMain;
import unibo.actor22comm.context.EnablerContextForActors;
import unibo.actor22comm.utils.ColorsOut;

public class RadarSystemDistribRasp extends TemplateMain{
	private EnablerContextForActors ctx;
	
	@Override
	protected void configure() {
		ctx = new EnablerContextForActors( "ctx", ApplData.ctxPort , ApplData.protocol);
		new LedActor( ApplData.ledName );
		new SonarActor( ApplData.sonarName );
		
	}

	@Override
	protected void execute() {
		ColorsOut.outappl("LedActorOnRasp | execute", ColorsOut.MAGENTA);
		ctx.activate();
	}
	
	public static void main( String[] args ) {
		BasicUtils.aboutThreads("Before start - ");
		new RadarSystemDistribRasp().doJob(null, null);
 		BasicUtils.aboutThreads("Before end - ");
	}

}
