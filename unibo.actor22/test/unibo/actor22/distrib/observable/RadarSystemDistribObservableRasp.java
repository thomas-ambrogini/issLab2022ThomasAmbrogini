package unibo.actor22.distrib.observable;

import it.unibo.radarSystem22.domain.utils.BasicUtils;
import it.unibo.radarSystem22.domain.utils.DomainSystemConfig;
import unibo.actor22.common.ApplData;
import unibo.actor22.common.LedActor;

import unibo.actor22.common.SonarActorObservable;
import unibo.actor22.main.TemplateMain;
import unibo.actor22comm.context.EnablerContextForActors;
import unibo.actor22comm.utils.ColorsOut;

public class RadarSystemDistribObservableRasp extends TemplateMain{
	private EnablerContextForActors ctx;
	
	@Override
	protected void configure() {
		DomainSystemConfig.observable = true;
		ctx = new EnablerContextForActors( "ctx", ApplData.ctxPort , ApplData.protocol);
		new LedActor( ApplData.ledName );
		new SonarActorObservable( ApplData.sonarName );
		
	}

	@Override
	protected void execute() {
		ColorsOut.outappl("LedActorOnRasp | execute", ColorsOut.MAGENTA);
		ctx.activate();
	}
	
	public static void main( String[] args ) {
		BasicUtils.aboutThreads("Before start - ");
		new RadarSystemDistribObservableRasp().doJob(null, null);
 		BasicUtils.aboutThreads("Before end - ");
	}

}
