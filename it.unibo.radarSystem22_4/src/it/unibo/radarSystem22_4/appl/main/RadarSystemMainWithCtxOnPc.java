package it.unibo.radarSystem22_4.appl.main;

import it.unibo.radarSystem22.domain.*;
import it.unibo.radarSystem22.domain.interfaces.*;
import it.unibo.radarSystem22.domain.utils.DomainSystemConfig;
import it.unibo.radarSystem22_4.appl.ActionFunction;
import it.unibo.radarSystem22_4.appl.Controller;
import it.unibo.radarSystem22_4.appl.RadarSystemConfig;
import it.unibo.radarSystem22_4.appl.proxy.LedProxy;
import it.unibo.radarSystem22_4.appl.proxy.SonarProxy;
import it.unibo.radarSystem22_4.comm.ProtocolType;
import it.unibo.radarSystem22_4.comm.interfaces.IApplication;
import it.unibo.radarSystem22_4.comm.utils.CommSystemConfig;

public class RadarSystemMainWithCtxOnPc implements IApplication{
	private IRadarDisplay radar;
	private ISonar sonar;
	private ILed  led ;
	private Controller controller;
	
	@Override
	public String getName() {
		return this.getClass().getName() ; 
	}
	@Override
	public void doJob(String domainConfig, String systemConfig ) {
		setup(domainConfig,systemConfig);
		configure();
		execute();		
	}
	
	public void setup( String domainConfig, String systemConfig )  {
		if( domainConfig != null ) {
			DomainSystemConfig.setTheConfiguration(domainConfig);
		}
		if( systemConfig != null ) {
			RadarSystemConfig.setTheConfiguration(systemConfig);
		}
		if( systemConfig == null && domainConfig == null ) {
			DomainSystemConfig.testing      	= false;			
			DomainSystemConfig.sonarDelay       = 200;
			DomainSystemConfig.simulation   	= true;
			
			RadarSystemConfig.DLIMIT      		= 70;  
			RadarSystemConfig.RadarGuiRemote    = false;		
			RadarSystemConfig.raspAddr          = "raspThomasAmbrogini.local";
			RadarSystemConfig.ctxServerPort    	= 8756;
			RadarSystemConfig.protocolType      = ProtocolType.tcp;	
			
			CommSystemConfig.tracing            = false;
		}
	}
	
	protected void configure() {		
		String host           = RadarSystemConfig.raspAddr;
		ProtocolType protocol = CommSystemConfig.protcolType;
		String ctxport        = ""+RadarSystemConfig.ctxServerPort;
		led    		= new LedProxy("ledPxy",     host, ctxport, protocol );
  		sonar  		= new SonarProxy("sonarPxy", host, ctxport, protocol );
  		radar  		= DeviceFactory.createRadarGui();
  		controller 	= Controller.create(led, sonar, radar);
	}
	

	public void execute() {
	    ActionFunction endFun = (n) -> { System.out.println(n); terminate(); };
		controller.start(endFun, 30);
 	}

	public void terminate() {
		//BasicUtils.delay(20000);
		sonar.deactivate();
		System.exit(0);
	}	
	
	public static void main( String[] args) throws Exception {
		//ColorsOut.out("Please set RadarSystemConfig.pcHostAddr in RadarSystemConfig.json");
		new RadarSystemMainWithCtxOnPc().doJob(null,null);
 	}
	
}
