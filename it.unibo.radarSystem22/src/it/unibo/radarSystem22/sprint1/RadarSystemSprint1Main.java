package it.unibo.radarSystem22.sprint1;

import it.unibo.radarSystem22.IApplication;
import it.unibo.radarSystem22.domain.DeviceFactory;
import it.unibo.radarSystem22.domain.interfaces.ILed;
import it.unibo.radarSystem22.domain.interfaces.IRadarDisplay;
import it.unibo.radarSystem22.domain.interfaces.ISonar;
import it.unibo.radarSystem22.domain.utils.BasicUtils;
import it.unibo.radarSystem22.domain.utils.DomainSystemConfig;

public class RadarSystemSprint1Main implements IApplication {
	private IRadarDisplay radar;
	private ISonar sonar;
	private ILed led;
	private Controller controller;

	@Override
	public String getName() {
		return this.getClass().getName();
	}
	
	public void setup( String domainConfig, String systemConfig) {
		BasicUtils.aboutThreads(" Before setup ");
		
		if( domainConfig != null ) {
			DomainSystemConfig.setTheConfiguration(domainConfig);
		}
		
		if( systemConfig != null ) {
			RadarSystemConfig.setTheConfiguration(systemConfig);
		}
		
		if( domainConfig == null && systemConfig == null ) {
			DomainSystemConfig.testing 				= false;
			DomainSystemConfig.sonarDelay			= 200;
			//su PC
			DomainSystemConfig.simulation			= true;
			DomainSystemConfig.ledGui				= true;
			RadarSystemConfig.DLIMIT				= 70;
			RadarSystemConfig.RadarGuiRemote		= false;
			//Su Raspberry
			DomainSystemConfig.simulation			= false;
			DomainSystemConfig.ledGui				= false;
			RadarSystemConfig.DLIMIT				= 12;
			RadarSystemConfig.RadarGuiRemote		= true;
		}
	}
	
	@Override
	public void doJob(String domainConfig, String systemConfig) {
		BasicUtils.aboutThreads("Before doJob | ");
		setup( domainConfig, systemConfig );
		configure();
		BasicUtils.waitTheUser();
		
		ActionFunction endFun = (n) -> {
			System.out.println(n);
			terminate();
		};
		
		controller.start(endFun, 30);
	}
	
	protected void configure() {
		led			= DeviceFactory.createLed();
		radar		= RadarSystemConfig.RadarGuiRemote ? null : DeviceFactory.createRadarGui();
		BasicUtils.aboutThreads("Before controller creation | ");
		sonar		= DeviceFactory.createSonar();
		controller	= Controller.create(led, sonar, radar);
	}
	
	public void terminate() {
		BasicUtils.aboutThreads("Before deactivation | ");
		sonar.deactivate();
		System.exit(0);
	}

	public IRadarDisplay getRadar() {
		return radar;
	}

	public ISonar getSonar() {
		return sonar;
	}

	public ILed getLed() {
		return led;
	}

	public Controller getController() {
		return controller;
	}
	
	public static void main( String[] args ) throws Exception {
		//WITHOUT CONFIG FILE
//		BasicUtils.aboutThreads("At INIT with NO CONFIG files |");
//		new RadarSystemSprint1Main().doJob(null, null);
		
		//WITH CONFIG FILE
		BasicUtils.aboutThreads("At INIT with CONFIG files | ");
		new RadarSystemSprint1Main().doJob(
				"DomainSystemConfig.json", "RadarSystemConfig.json");
	}

}
