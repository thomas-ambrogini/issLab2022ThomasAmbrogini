package unibo.actor22.main;

import it.unibo.radarSystem22.domain.utils.BasicUtils;
import it.unibo.radarSystem22.domain.utils.DomainSystemConfig;
import unibo.actor22comm.interfaces.IApplication;
import unibo.actor22comm.utils.CommSystemConfig;
import unibo.actor22comm.utils.CommUtils;


public abstract class TemplateMain implements IApplication {

	@Override
	public String getName() {
		return this.getClass().getName() ; 
	}
	
	@Override
	public void doJob(String domainConfig, String commConfig ) {
		setup(domainConfig, commConfig);
		configure();
		execute();
		terminate();
	}
	
	public void setup( String domainConfig, String commConfig )  {
	    BasicUtils.aboutThreads(getName() + " | Before setup ");
		if( domainConfig != null ) {
			DomainSystemConfig.setTheConfiguration(domainConfig);
		}
		if( commConfig != null ) {
			CommSystemConfig.setTheConfiguration(commConfig);
		}
		if( domainConfig == null && commConfig == null) {
 			DomainSystemConfig.simulation   = true;
 			DomainSystemConfig.testing		= false;
 			DomainSystemConfig.ledGui       = false;			
 			DomainSystemConfig.tracing      = true;
 			DomainSystemConfig.sonarDelay 	= 200;
 			
 			CommSystemConfig.tracing        = true;
		}
 
	}
	
	protected abstract void configure();
	
	protected abstract void execute();
	
	protected void terminate() {
		CommUtils.aboutThreads("Before exit - ");
//		CommUtils.delay(10000);
//		System.exit(0);
	}
	
}
